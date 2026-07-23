# ADR-0001: Architecture — Saint Vincent and the Grenadines market-entry compliance actor (`marketentry`)

**Status**: accepted
**Date**: 2026-07-23

## Context

`cloud-itonami-iso3166-vct` was published as a `:blueprint` (docs +
`blueprint.edn` + `deps.edn`, then a country-level `culture.facts`
catalog in a separate Wave 1 batch) but carried ZERO `src/marketentry`
or `src/statute` content -- its `:public-sector/market-entry-
compliance` domain, declared in `blueprint.edn`, was unimplemented, and
`CONTRIBUTING.md`/`GOVERNANCE.md` still carried leftover template text
for a different country (`cloud-itonami-iso3166-khm` / Cambodia --
"the official source for Cambodia" / "Cambodian-licensed counsel" /
"the law of Cambodia"). This ADR closes both gaps, following the
pattern established by `cloud-itonami-iso3166-jpn` (origin) and the
OECS/Eastern-Caribbean siblings `cloud-itonami-iso3166-atg` (Antigua
and Barbuda), `cloud-itonami-iso3166-dma` (Dominica),
`cloud-itonami-iso3166-grd` (Grenada), `cloud-itonami-iso3166-lca`
(Saint Lucia) and `cloud-itonami-iso3166-kna` (Saint Kitts and Nevis,
all six now-complete OECS full-member siblings) -- the simpler,
no-`goyoukiki` shape this blueprint also uses (`blueprint.edn`'s
`:required-technologies` does not list `:ontology`).

## Decision

Build the full governed-actor architecture for `marketentry`, mirroring
JPN/ATG/DMA/GRD/LCA/KNA's harness verbatim (StateGraph node names,
governor hard/escalate contract, phase 0-3 rollout, `Store` protocol
with MemStore + DatomicStore parity) and researching Saint Vincent and
the Grenadines' own real market-entry rules from scratch for the
country-specific content.

- **Store**: `marketentry.store`, MemStore + DatomicStore, proven parity
  via contract test.
- **Registry**: `marketentry.registry`, pure DRAFT-certificate
  construction via `unsigned-certificate`, jurisdiction-scoped sequence
  numbering (`VCT-DFT-000000`, `VCT-SUB-000000`), plus the flagship
  debarment-lifecycle recompute (see below).
- **Governor**: `:market-entry-compliance-governor` (family keyword from
  `blueprint.edn`).
- **Entity shape**: `engagement`, sequential draft -> submit on the same
  record. `high-stakes` = `#{:actuation/draft-filing
  :actuation/submit-filing}`.
- **Phase**: 0->3; `:filing/draft` and `:filing/submit` NEVER auto-
  commit at any phase.

### WebSearch budget was exhausted fleet-wide; discovery used direct curl/WebFetch hostname probing

Before this task began, this session's WebSearch budget was already at
200/200 (the same constraint the Saint Lucia sibling's own ADR
documents hitting). Discovery therefore proceeded by direct `curl`/
`WebFetch` probing of a candidate-hostname list against the `.gov.vc`
namespace, following links from confirmed-live pages rather than
search-engine discovery.

### `svglaws.com` is NOT an official source -- a domain-parking lander page, confirmed by fetching it directly

The plausible-looking candidate `svglaws.com` resolves (HTTP 200) to a
page whose own raw HTML is `window.LANDER_SYSTEM="PW"` /
`window._trfd.push({ap:"parking"})` -- a GoDaddy-style for-sale/parking
lander, NOT a Government of Saint Vincent and the Grenadines property.
Confirmed by fetching the page directly and inspecting its markup, the
same honest-negative discipline the Saint Lucia sibling's own
`NXDOMAIN` finding for `slucode.saintlucia.gov.lc` uses. The real
discovery path was the official government portal `www.gov.vc` (HTTP
200), whose own homepage links dozens of live `*.gov.vc` department
subdomains (finance, legal, cipo, ird, dol via mobilization, customs,
assembly, procurement, etc.) plus a House of Assembly Acts archive
(`assembly.gov.vc/assembly/index.php/acts-sp-1308988606`) going back
to 1993, one combined PDF per year.

### Which body administers procurement, and the flagship HARD check: `debarment-disqualifying` — a state-and-kind-dispatched lifecycle, not a date or threshold

The Public Procurement Act, 2018 (Act No. 34 of 2018, assented 31
December 2018 by Governor-General Dr. Frederick Ballantyne, "[By
Proclamation]") establishes a Central Procurement Board (Division 2,
ss.7-11) and a Central Procurement Office headed by a Chief
Procurement Officer (Division 3, ss.12-14). Its own s.1(3) provides an
automatic-commencement backstop: "If a provision of this Act has not
come into force within two years after the day on which this Act is
assented to, the provision automatically comes into force on the next
day" -- so regardless of whether an actual Proclamation issued, the
Act was in force by 31 December 2020 at the latest. Independently, the
live e-procurement portal (`procurement.gov.vc/eprocure/index.php/
current-bids`, fetched directly this session) shows real 2026-dated
solicitations (e.g. a Central Water and Sewerage Authority desalination
consultancy, deadline 10 July 2026; a "Purchase of 4 compactor Trucks
and 1 Tipper Truck", deadline 17 July 2026) -- genuine operational
evidence, not merely a paper provision.

s.64 ("Suspension and debarment"), read directly from the Act's own
78-page OCR'd text (`pdftoppm` 200dpi + `tesseract`, since the PDF is
scanned with no text layer), together with its own Schedule 4
("Suspension and Debarment Procedure", ss.64(5)/(9)/(10)), describe a
bidder's debarment status as a LIFECYCLE with distinct STATES, not a
single date window or fixed constant:

1. **Interim, purely state-based leg (s.64(3))**: "Once the Board
   initiates the debarment proceedings by establishing a Debarment
   Committee, the bidder subject to the debarment investigation shall
   be automatically suspended from participation in the contract award
   procedure." No date arithmetic at all -- the bidder is disqualified
   for as long as the case is open, full stop, confirmed by Schedule 4
   §6(5)(b): once a Debarment Committee determines no sanction is
   warranted, the Board "shall ... immediately lift the automatic
   suspension imposed under section 64(3) of the Act."
2. **Final-sanction leg (Schedule 4 §7 "Debarment sanction")**: FOUR
   qualitatively different kinds, not one mode:
   - (a) **Reprimand** -- "falls short of debarment", never usable for
     a second offence, never disqualifying;
   - (b) **Conditional Non-Debarment** -- a threatened debarment that
     "would automatically become effective for a period of time
     established by the Debarment Committee ... converted into a
     Temporary Debarment" ONLY if the Respondent breaches imposed
     conditions;
   - (c) **Temporary Debarment** -- "for a specific period of time not
     to exceed five (5) years ... one, three or five years" -- a
     DISCRETE three-value enum {1, 3, 5}, genuinely different in shape
     from Saint Lucia's own CONTINUOUS 6-60-month range (s.114(3)) and
     from Grenada's own SINGLE fixed constant (a 2-year lookback);
   - (d) **Permanent Debarment** -- "used rarely", requiring "at least
     two orders of temporary debarment" as a precondition, with NO
     expiry date at all.

This is a genuinely different check SHAPE than every prior iso3166
sibling: Bulgaria's ЗОП Art. 54(5) de-minimis is a PERCENTAGE-OF-
TURNOVER formula, Albania's Neni 76(2)(c) carve-out is a FLAT STATUTORY
CONSTANT, Azerbaijan's/Armenia's/Mozambique's own flagship checks are
plain BOOLEAN registry-membership reads, Antigua and Barbuda's is a
discrete 3-TIER THRESHOLD classification, Dominica's is a DUAL
independently-escalating AUTHORITY ladder, Grenada's own conviction
recompute is anchored to a SINGLE FIXED constant, Saint Lucia's own
s.114 recompute is a single CONTINUOUS statutorily-bounded duration
window, Saint Kitts and Nevis's notice-period check is a COMPOUND
duration-and-multiplicity recompute over two fields, and Guyana's
flagship is a local-content compliance percentage -- none of these has
more than ONE mode of disqualification. VCT's own s.64 + Schedule 4
instead require a STATE-AND-KIND DISPATCH: `marketentry.registry/
debarment-disqualifying?` checks the interim leg first (pure state, no
dates), then dispatches on `:debarment-sanction-kind`
(nil/:reprimand/:conditional-non-debarment/:temporary/:permanent),
only the `:temporary` branch (and `:conditional-non-debarment` when
breached, which falls through to the same logic) involving any date
arithmetic at all, and that arithmetic itself validates a DISCRETE SET
membership {1, 3, 5} rather than a min/max bound. Grep-verified absent
fleet-wide as a governor check function name at build time (`gh api
search/code -f q='debarment-disqualifying org:cloud-itonami'` and two
related queries all returned zero hits across the entire `cloud-
itonami` GitHub org, not merely the six OECS siblings studied
directly).

`compute-debarment-end` needed only a whole-YEAR bump (Schedule 4
§7(1)(c)'s own duration unit), simpler than Saint Lucia's own MONTH-
granularity `compute-suspension-end` (which needs to carry overflow
into the year component) -- a genuinely different, and simpler,
arithmetic shape reached because VCT's own statute measures duration in
years, not months.

Operational cross-check: Regulation 7 of the Public Procurement
Regulations, 2019 (S.R.O. No. 23 of 2019, gazetted 22 November 2019)
independently confirms the same mechanism from the Secretary's own
side: "Where the Secretary has notice of any circumstance that may
lead to the suspension or debarment of a person under Part 8 of the
Act, such information shall be forwarded to the Board forthwith and
the Board may take such information into account in determining
whether or not to initiate proceedings under the Fourth Schedule of the
Act."

### Thresholds, read directly from the Regulations' own Regulation 5

Not estimated or borrowed: Regulation 5(1)-(2) EC$45,000 (Board
approval before pre-qualification invitations and before contract
award, under s.9(1)(c)/(d) of the Act); (3) EC$20,000 (above which
open competitive bidding is the default over request-for-quotations,
s.49(1)); (4) EC$10 million (works) / EC$500,000 (goods and services)
(international advertising threshold, s.44(3)); (5) EC$500 or less
("small value" procurement, s.47(1)(e)(v)); (6) EC$15,000 or less
("small value" consultancy services, reg.43(5)(a)).

### Business registration: CIPO administers the Companies Act, 1994 (Act No. 9 of 1994) -- confirmed via TWO independent sources

CIPO's own `Legislation` page states in its own words: "CIPO currently
administers the following laws: Companies Act, 1994 ... Registration of
Business Names Act, Cap. 111 ..." among others. Independently
cross-validated via the House of Assembly's own 1994 legislative
register (`assembly.gov.vc/assembly/images/Acts/1994.pdf` -- a
genuinely text-layer-native, Microsoft-Word-generated PDF, NOT scanned,
unlike the Act/Regulations PDFs above): "Bill for an Act to revise and
amend the law relating to companies ... ACT. NO. 9 of 1994", Assent
28.12.94, Publication 28.12.94. Unlike Saint Lucia's/Antigua and
Barbuda's/Grenada's own TWO-Act model (a dedicated registry-
establishment Act separate from the substantive Companies Act), this
session found no evidence of a separate 'CIPO Act' -- CIPO's own page
describes itself as administering a LIST of laws, a genuinely different
(single-registrar-multiple-Acts) shape, reported honestly rather than
assumed to mirror the OECS two-Act pattern.

### The TIN question: CONFIRMED real for VCT, unlike Saint Lucia's own honest gap

The Tax Administration Act, 2019 (Act No. 30 of 2019, assented 31
December 2019 by Governor-General Susan Dougan, "[BY PROCLAMATION]",
OCR'd from its own scanned PDF) s.5 establishes the Comptroller of
Inland Revenue; s.9(1), read directly and quoted verbatim: "The
Comptroller must assign a unique taxpayer identification number
("TIN") to every taxpayer." This Act's own s.1 was checked and does
NOT carry a 2-year automatic-commencement backstop clause the way the
Public Procurement Act's own s.1(3) does -- an honestly-noted
structural difference between the two 2018/2019 statutes rather than
an assumed-uniform commencement mechanism. The Income Tax Act, Cap.
435 (`Cap435.pdf`, downloaded directly -- genuinely text-layer native,
NOT scanned, 9249 lines) confirms: "Act No. 2 of 1979 ... [Date of
commencement: 1st January, 1979.]"

### Labour law: a genuinely fragmented, thirteen-Act regime -- more fragmented than any prior sibling

The Department of Labour's own `Legislation` page (`dol.gov.vc/dol/
index.php/legislation`, under the Ministry of National Mobilization)
lists THIRTEEN separate historic Acts, not one consolidated labour
code: Wages Councils Act 1953, Trade Unions Act 1950, Trade Disputes
(Arbitration and Inquiry) Act 1940, Shop (Hours of Opening and
Employment) Act 1942, Recruiting of Workers Act 1940, Protection of
Employment Act 2003, Essential Services Act 1965, Equal Pay Act 1994,
Employment of Women, Young Persons and Children Act 1935, Employment
of Foreign Nationals and Commonwealth Citizens Act 1973, Employment
Exchanges Act 1956, Employers and Servants Act 1937, and Accidents and
Occupational Diseases (Notification) Act 1952. This is MORE fragmented
than even Dominica's own two-separate-statutes split documented in
this family. `statute.facts` cites the Protection of Employment Act,
2003 (termination, severance, dispute settlement) as the single most
general-compliance-relevant entry. **Honest access gap**: the
Department's own page states "Access to Acts: A copy of the Acts can
be purchased at the Government Printery" -- no free full-text PDF was
available online this session, so this catalog cites the Department's
own official description rather than a specific section number.
Cross-validation: the House of Assembly's own 1994 register
independently confirms the Equal Pay Act, 1994 is genuinely Act No. 3
of 1994 (matching the DOL's own description in substance), even though
this catalog does not carry it as its own separate entry.

### Fiscal Incentives Act, Chapter 468 — checked, found real, deliberately kept out of scope

Confirmed via the Customs & Excise Department's own live page
(`customs.gov.vc/fiscal-incentives-act`, an embedded PDF titled
"Fiscal Incentives Act Chapter 468"). **Honest scope limit**: this
session did not extract the Act's own PDF text (the embed exposed no
direct downloadable link in the page's static HTML this session
fetched) -- the chapter number and administering authority are
confirmed, but specific incentive provisions/thresholds are NOT
catalogued. A related "Hotel Aid Act" is named on the same site but was
not investigated further. Investment incentives are, in any case,
downstream of this blueprint's actual domain (bidding into public
contracts, not general foreign-direct-investment facilitation) --
Invest SVG (`investsvg.com`, formerly National Investment Promotions
Inc./NIPI, the government's own investment-promotion agency) names no
specific incentive Act on its own site, so it is documented only as a
supplementary finding, the same discipline Saint Lucia's own
`marketentry.facts` applies to its International Business Companies
Act finding.

## Consequences

- `src/` now genuinely exists with real, tested, curl/WebFetch/OCR-cited
  content for this blueprint's declared domain (`:public-sector/
  market-entry-compliance`) -- moves this repo's
  `manifest/itonami-fleet-audit.edn` `:prod-ready?` signal from `:stub`
  to `:active`.
- `CONTRIBUTING.md`/`GOVERNANCE.md`'s leftover Cambodia (`iso3166-khm`)
  template text is corrected to Saint Vincent and the Grenadines
  throughout.
- The existing `culture.facts` catalog (Wave 1, unrelated batch) is
  untouched.
- The Act's own EC$45,000/EC$20,000/EC$10M/EC$500K/EC$500/EC$15,000
  threshold ladder (Regulation 5) and the Debarment Committee's own
  full appeal/review procedure (Schedule 4 §9) are genuine, verified,
  NOT-fully-implemented extension points for a future iteration.
- The Fiscal Incentives Act, Chapter 468 and the Hotel Aid Act are
  genuine, verified, deliberately NOT-catalogued extension points -- a
  future iteration targeting an investment-incentives blueprint (a
  different domain than this one) could build on the citation already
  gathered here rather than re-researching it.
- The Department of Labour's own "purchase at the Government Printery"
  access gap, and the thirteen-Act fragmentation of SVG labour law
  generally, are honest, disclosed research limits -- this ADR cites
  the confirmed Act name/year/administering-department in their place,
  rather than inventing full section text.
- `svglaws.com` being a parked domain (not `NXDOMAIN`, but equally not
  an official source) is a genuinely different negative-finding SHAPE
  from Saint Lucia's own `NXDOMAIN` result for its own candidate host
  -- both are honestly disclosed rather than silently treated as
  "checked, nothing there."
- Sibling country blueprints can continue forking JPN/ATG/DMA/GRD/LCA/
  KNA/VCT and swapping in their own genuinely-researched
  `marketentry.facts` / `statute.facts` content and whichever flagship
  check their own law actually supports -- this ADR is itself further
  evidence that even a six-sibling-deep OECS family has room for a
  genuinely new flagship-check SHAPE (a state-and-kind dispatch, not a
  date or threshold) when the underlying statute's own structure calls
  for it.

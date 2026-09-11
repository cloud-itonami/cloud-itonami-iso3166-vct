# cloud-itonami-iso3166-vct

Open ISO 3166 Blueprint for **VCT**: Saint Vincent and the Grenadines -- **`:implemented`**.

This repository designs **and implements** a forkable OSS business for
an independent public-sector market-entry consultant: an already-
incorporated operator (e.g. a `cloud-itonami-cofog-{code}`,
`cloud-itonami-isco-{code}`, `cloud-itonami-unspsc-{segment}` or
`cloud-itonami-{ISIC}` blueprint fork) gets a Compliance Advisor +
independent **Market-Entry Compliance Governor** to navigate public-
procurement registration, local business/tax registration, and
regulatory-compliance rules in Saint Vincent and the Grenadines, so the
operator can win and service a government contract without hiring a
full in-house compliance department.

## Official surface (curl/WebFetch-verified 2026-07-23 -- `svglaws.com` is confirmed to be a domain-parking lander page, NOT a government property; the real discovery path was `www.gov.vc` and its many live `*.gov.vc` department subdomains, plus the House of Assembly's own Acts archive back to 1993)

- Procurement: the Central Procurement Board (Public Procurement Act,
  2018, Act No. 34 of 2018, ss.7-11) and the Central Procurement Office
  headed by a Chief Procurement Officer (ss.12-14). Day-to-day tenders
  are published on the live e-procurement portal
  (`procurement.gov.vc/eprocure/index.php/current-bids` -- real
  2026-dated solicitations confirmed this session). Thresholds are set
  by the Public Procurement Regulations, 2019 (S.R.O. No. 23 of 2019),
  Regulation 5: EC$45,000 for Board approval (both pre-qualification
  invitations and contract award), EC$20,000 for the open-competitive-
  bidding default, EC$10 million (works) / EC$500,000 (goods/services)
  for international advertising, EC$500 / EC$15,000 for 'small value'
  goods-works / consultancy respectively.
- Business registration: the Commerce and Intellectual Property Office
  (CIPO) administers the Companies Act, 1994 (Act No. 9 of 1994,
  cross-validated via the House of Assembly's own 1994 legislative
  register) and the Registration of Business Names Act, Cap. 111,
  among other IP statutes.
- Tax: the Comptroller of Inland Revenue (Tax Administration Act, 2019,
  Act No. 30 of 2019, s.5) assigns a Taxpayer Identification Number
  ("TIN") to every taxpayer (s.9(1)) -- VCT's own CONFIRMED TIN
  terminology, unlike the Saint Lucia sibling's own honest gap. The
  Income Tax Act, Cap. 435 (Act No. 2 of 1979, in force 1 January 1979)
  is the general income-tax legal basis.
- Fiscal Incentives Act, Chapter 468 (confirmed real via the Customs &
  Excise Department's own page) is a genuine, currently-administered
  investment-incentive instrument but OUT OF SCOPE for this blueprint's
  public-procurement market-entry domain -- documented as a
  supplementary finding in `src/marketentry/facts.cljk`, its own
  provisions not extracted this session (an honest, disclosed gap).

## Implementation (R0)

| Piece | Location |
|---|---|
| Actor namespaces | `src/marketentry/*` |
| Governor | `:market-entry-compliance-governor` |
| Ops | `:engagement/intake` · `:jurisdiction/assess` · `:filing/draft` · `:filing/submit` |
| Flagship HARD check | `debarment-disqualifying` (Public Procurement Act, 2018, Act No. 34 of 2018 s.64 'Suspension and debarment' + Schedule 4 'Suspension and Debarment Procedure' -- a STATE-AND-KIND-DISPATCHED disqualification lifecycle: an interim automatic suspension the moment a Debarment Committee is established (no date arithmetic), plus four qualitatively different final sanction kinds -- Reprimand (never disqualifying), Conditional Non-Debarment (disqualifying only if breached), Temporary Debarment (a discrete {1,3,5}-year enum window), Permanent Debarment (always disqualifying, no expiry) -- independently recomputed from the engagement's own declared ground truth, see `docs/adr/0001-architecture.md`) |
| Compliance catalog | `src/statute/facts.cljk` -- Companies Act, Protection of Employment Act, Income Tax Act, Tax Administration Act |
| Tests | `clojure -M:dev:test` |
| Demo | `clojure -M:dev:run` |
| Architecture ADR | [`docs/adr/0001-architecture.md`](docs/adr/0001-architecture.md) |

`:filing/submit` is never in any phase's `:auto` set -- human sign-off
is structural, not a rollout milestone.

## No robotics premise -- digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) -- the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set -- it always requires human sign-off.

## What this is NOT

- **Not the government of Saint Vincent and the Grenadines.** This
  blueprint is an independent operator the government contracts with or
  that bids into its procurement -- never the government itself, and
  never an official channel.
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Saint Vincent and the
  Grenadines-licensed counsel or a registered agent where the law
  requires licensed representation.

## Capability layer

Required capabilities (`blueprint.edn`):

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## License

AGPL-3.0-or-later.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Saint Vincent and the Grenadines:

- `src/culture/facts.cljk` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.

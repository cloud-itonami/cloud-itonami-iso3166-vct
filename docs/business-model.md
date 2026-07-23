# Business Model: Independent Public-Sector Market-Entry & Procurement Compliance Service — Saint Vincent and the Grenadines

## Classification

- Repository: `cloud-itonami-iso3166-vct`
- ISO 3166: `VCT` (Saint Vincent and the Grenadines)
- Activity: public-procurement market-entry and ongoing regulatory-
  compliance navigation for an already-incorporated operator

## Customer

- an already-incorporated `cloud-itonami-cofog-{code}` /
  `cloud-itonami-isco-{code}` / `cloud-itonami-unspsc-{segment}` /
  `cloud-itonami-{ISIC}` operator wanting to bid on a Saint Vincent and
  the Grenadines public contract
- a foreign SME or civic-tech vendor entering the public sector in
  Saint Vincent and the Grenadines for the first time
- a `cloud-itonami-M6910` client that has just completed incorporation
  and now needs public-sector market access

## Offer

- registration walkthrough for public procurement above the Public
  Procurement Regulations, 2019's own thresholds (EC$45,000 for Board
  approval of pre-qualification invitations and contract award,
  EC$20,000 for the open-competitive-bidding default) requiring Central
  Procurement Board approval, and day-to-day tender monitoring via the
  live e-procurement portal (`procurement.gov.vc/eprocure/index.php/
  current-bids`)
- business/tax registration checklist: Certificate of Incorporation
  from the Commerce and Intellectual Property Office (CIPO, Companies
  Act, 1994), followed by TIN registration with the Comptroller of
  Inland Revenue (Tax Administration Act, 2019, s.9) -- a separate,
  subsequent act
- debarment screening: independent verification that the operator (or
  any of its named affiliates, per s.64(7)(b)-(c) and Schedule 4 §8(1))
  does not carry an active Debarment Committee case or an unexpired
  Temporary/Permanent Debarment sanction under the Public Procurement
  Act, 2018 s.64 before any filing submission -- including validating
  that a declared Temporary Debarment's own duration actually falls
  within Schedule 4 §7(1)(c)'s statutory {1, 3, 5}-year enum
- ongoing regulatory-change monitoring subscription
- compliance-audit export package for the client's own records

## Revenue

- per-engagement market-entry fee (one-time registration + checklist
  completion)
- recurring regulatory-change monitoring subscription
- compliance-audit export package

## Trust Controls

- any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off (`:filing/submit` is never automated at any phase)
- a false or fabricated regulatory-requirement claim is a HARD hold that
  cannot be overridden by human approval alone -- it must be corrected
  against a cited official source first
- an active, unresolved Debarment Committee case under s.64(3) (an
  INTERIM automatic suspension, no date required), an unexpired
  Temporary/Permanent Debarment sanction as of the engagement's own
  declared submission date, OR a Temporary Debarment whose own declared
  duration falls outside Schedule 4 §7(1)(c)'s statutory {1, 3, 5}-year
  enum, is a HARD hold on `:filing/submit` -- never trusted from a
  self-reported "clear" claim
- this service does **not** provide legal or tax advice; characterization
  and filing on the client's behalf beyond checklist/draft assistance
  routes to Saint Vincent and the Grenadines-licensed counsel or a
  registered agent

## Boundary with adjacent actors (read before forking)

- **`cloud-itonami-M6910`**: helps a client BECOME a legal entity
  (incorporation, ISIC 6910) -- a prior, different regulatory phase
  (company law). This blueprint assumes incorporation is already done and
  handles public-procurement market entry (a different regulatory domain).
- **`cloud-itonami-cofog-{code}`**: a jurisdiction-agnostic operator
  template for ONE public function. This blueprint is the orthogonal
  jurisdiction-specific axis -- the two compose (fork a COFOG-function
  blueprint AND this one to operate in Saint Vincent and the Grenadines).
- **Fiscal Incentives Act, Chapter 468**: a DIFFERENT market-entry act
  entirely (investment/import-duty incentives administered by the
  Customs & Excise Department, not public-procurement bidding) --
  deliberately out of scope for this blueprint. See
  `src/marketentry/facts.cljc` for the honest research finding on that
  Act (chapter number and administering authority confirmed, specific
  provisions not extracted this session).

# Operator Guide

## First Deployment

1. Confirm the client's incorporation/legal-entity status is complete
   (route to `cloud-itonami-M6910` or local counsel first if not).
2. Register the client's intake: business type, target public function,
   prior filing history in Saint Vincent and the Grenadines if any.
3. Run the advisor in read-only mode against the live e-procurement
   portal (`procurement.gov.vc/eprocure/index.php/current-bids`),
   governed by the Public Procurement Act, 2018 (Act No. 34 of 2018)
   and the Public Procurement Regulations, 2019 (S.R.O. No. 23 of
   2019).
4. Compare the checklist against the client's current documentation
   (CIPO Certificate of Incorporation, TIN registration record from
   the Comptroller of Inland Revenue, and confirmation that neither
   the operator nor any named affiliate carries an active Debarment
   Committee case or unexpired debarment sanction under s.64 --
   including that any declared Temporary Debarment's own duration is
   itself lawful under Schedule 4 §7(1)(c)'s {1, 3, 5}-year enum).
5. Enable gated filing-draft assistance once the Market-Entry Compliance
   Governor contract is trusted; actual submission always requires human
   sign-off.

## Minimum Production Controls

- client-owned data store for business/tax registration documents
- clear provenance (official portal/regulation citation) for every
  requirement surfaced
- approval workflow for any portal registration or filing submission
- independent re-verification that no active Debarment Committee case
  or unexpired debarment sanction covers the declared submission date,
  and that any declared Temporary Debarment duration is itself within
  Schedule 4 §7(1)(c)'s {1, 3, 5}-year enum, before any `:filing/submit`
  -- never trust a self-reported "clear" claim
- named referral relationship with Saint Vincent and the Grenadines-
  licensed counsel or a registered agent for anything beyond
  checklist/draft assistance
- monthly audit export
- a debarred bidder's own remedy, per Schedule 4 §9, is to seek review
  with the Central Procurement Board (e.g. on newly discovered material
  evidence, a reversed conviction/civil judgment, a bona fide change in
  ownership/management, or successful compliance with imposed
  remedial measures) -- this actor has no standing to make that
  application on the client's behalf; it only reports the debarment
  record it was given

## Certification

Certified operators must prove data provenance, audit traceability, that
automated actions cannot bypass the Market-Entry Compliance Governor, and
a working referral relationship with Saint Vincent and the Grenadines-
licensed counsel or a registered agent for whatever licensed
representation the law of Saint Vincent and the Grenadines requires for
actual public-procurement filings.

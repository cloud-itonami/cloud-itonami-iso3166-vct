(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Saint Vincent and the Grenadines' real market-entry surface (curl/
  WebFetch-verified 2026-07-23 -- every citation below was fetched
  directly this session; this session's WebSearch budget was already
  exhausted fleet-wide before this task began (200/200), the same
  constraint the OECS siblings in this family document hitting, so
  discovery used direct curl/WebFetch probing of candidate hostnames
  rather than search-engine discovery):

  - **`svglaws.com`, a plausible-looking consolidated-law candidate
    host, is NOT an official source -- it resolves to a GoDaddy-style
    domain-parking lander page (`window.LANDER_SYSTEM=\"PW\"`, a
    `parking` ad-signal tag), not a Government of SVG property.**
    Confirmed by fetching it directly and inspecting the raw HTML,
    not merely assumed absent -- the same honest-negative discipline
    LCA's `slucode.saintlucia.gov.lc` `NXDOMAIN` finding used. The
    real discovery path was the official government portal
    `www.gov.vc` (HTTP 200), whose own homepage links dozens of live
    `*.gov.vc` ministry/department subdomains plus a House of Assembly
    Acts archive (`assembly.gov.vc`) going back to 1993.
  - **Which body administers procurement -- investigated via SVG's own
    live e-procurement portal, not assumed to mirror any sibling.**
    `procurement.gov.vc/eprocure/` (the 'Central Procurement Portal',
    developed by the Information Technology Services Division) has a
    live '/index.php/current-bids' tender board with real 2026-dated
    solicitations (e.g. 'Consultancy Services for Project Audit ...
    Strengthening Response, Recovery and Resilience in the Health
    Sector Project', deadline 14 August 2026; 'Purchase of 4 compactor
    Trucks and 1 Tipper Truck', deadline 17 July 2026) -- genuine
    operational-liveness proof, not merely a paper provision. Its own
    'Legal Framework' page
    (`/eprocure/index.php/laws-and-regulations`) links two PDFs, both
    downloaded directly: `Public_Procurement_Act_34_of_2018.pdf`
    (scanned, 78 pages, OCR'd via `pdftoppm` 200dpi + `tesseract`) and
    `Public_Procurement_Regs_23_of_2019.pdf` (scanned, OCR'd for the
    relevant pages). The Act's own text: 'SAINT VINCENT AND THE
    GRENADINES PUBLIC PROCUREMENT ACT 2018 ... ACT NO. 34 OF 2018 ...
    31st December, 2018 ... [By Proclamation]' -- and CRITICALLY,
    s.1(3): 'If a provision of this Act has not come into force within
    two years after the day on which this Act is assented to, the
    provision automatically comes into force on the next day' -- an
    automatic-commencement BACKSTOP this catalog reports honestly
    rather than assuming the Act needed an actual Proclamation to take
    effect (either way, by 31 December 2020 at the very latest, and
    the live 2026-dated tenders above independently confirm the system
    is operating today). The Act establishes a Central Procurement
    Board (Division 2, ss.7-11) AND a Central Procurement Office
    headed by a Chief Procurement Officer (Division 3, ss.12-14) -- a
    Board-plus-Office two-body structure, a genuinely different shape
    from Saint Lucia's Director-plus-Board split (LCA's own
    `marketentry.facts`) even though both are two-authority models.
  - **Thresholds -- read directly from the Regulations' own
    Regulation 5 ('Thresholds'), NOT estimated or borrowed from a
    sibling.** S.R.O. No. 23 of 2019 (gazetted 22 November 2019, 'made
    ... [i]n exercise of the powers conferred by section 73 of the
    Public Procurement Act, No. 34 of 2018', commencing 'on the date of
    commencement of the Act'), reg.5: (1)/(2) EC$45,000 -- Central
    Procurement Board approval required both before invitations to bid
    are sent to pre-qualified bidders (s.9(1)(c) of the Act) AND before
    a contract is awarded (s.9(1)(d)); (3) EC$20,000 -- above this,
    open competitive bidding is the default procedure instead of
    request-for-quotations (s.49(1)); (4) EC$10 million (works) /
    EC$500,000 (goods and services) -- above this, international media
    advertising is required (s.44(3)); (5) EC$500 or less -- statutory
    'small value' procurement (s.47(1)(e)(v)); (6) EC$15,000 or less --
    'small value' consultancy services (reg.43(5)(a)). This catalog
    does NOT assume the currency is EC$ beyond what the Regulations'
    own text states (no explicit currency symbol was legible in the
    OCR'd text; SVG's own currency is the Eastern Caribbean dollar, a
    contextual fact this catalog states as likely but does not present
    as directly quoted from the Regulations' own text).
  - **Flagship mechanism -- s.64 'Suspension and debarment' PLUS
    Schedule 4 'Suspension and Debarment Procedure' (ss.64(5), 64(9)
    and 64(10)), read directly from the Act's own OCR'd text (both
    provisions, in full, this session).** This is a genuinely
    different check SHAPE from every prior sibling this catalog's
    family has implemented -- not a turnover-scaled formula
    (Bulgaria), not a flat statutory threshold (Albania), not a
    boolean registry-membership read (Azerbaijan/Armenia/Mozambique's
    Cadastro Único), not a 3-tier vendor-class classification (Antigua
    and Barbuda), not a dual independently-escalating authority ladder
    (Dominica), not a conviction-disqualification-expiry-date recompute
    anchored to a single fixed constant (Grenada's 2-year lookback), not
    a continuous statutorily-bounded DURATION window (Saint Lucia's
    6-60-month s.114 recompute), not a compound duration-and-multiplicity
    recompute (Saint Kitts and Nevis's notice-period check), and not a
    local-content compliance percentage (Guyana) -- it is a
    STATE-AND-KIND-DISPATCHED recompute over the bidder's own
    debarment-case LIFECYCLE, only one branch of which even involves
    date arithmetic. Grep-verified absent fleet-wide as a governor
    check function name at build time (`gh api search/code` across the
    entire `cloud-itonami` GitHub org for
    `debarment-disqualifying`/`debarment-committee-active`/the literal
    phrase 'Debarment Committee' all returned zero hits). See
    `marketentry.registry` for the independent recompute and
    `docs/adr/0001-architecture.md` for the full derivation.

    The Act's own text: s.64(3) 'Once the Board initiates the
    debarment proceedings by establishing a Debarment Committee, the
    bidder subject to the debarment investigation shall be
    AUTOMATICALLY SUSPENDED from participation in the contract award
    procedure' -- an INTERIM suspension triggered purely by a PROCESS
    STATE (a committee having been established), with NO date
    arithmetic at all, unlike every prior sibling's date- or
    threshold-shaped flagship check. Schedule 4 (the Act's own
    'Suspension and Debarment Procedure', ss.1-9) spells out the full
    state machine this catalog's `marketentry.registry` mirrors: a
    Debarment Officer investigates (§1-2), the Central Procurement
    Board reviews a Draft Notice of Proposed Debarment within 10 days
    and either declines or 'initiate[s] the debarment proceedings by
    establishing a Debarment Committee ... and immediately enter[s] the
    name of the accused bidder in the List of Suspended Bidders' (§2(7)
    -- the s.64(3) trigger), a 3-member Debarment Committee (Chief
    Procurement Officer as chair, an Attorney-General's Office legal
    officer, and another procuring entity's head, §3) hears the case
    (§4-6), and §7 'Debarment sanction' gives FOUR qualitatively
    different sanction KINDS, not a single mode: (a) Reprimand ('falls
    short of debarment', never usable for a second offence); (b)
    Conditional Non-Debarment (a threatened debarment that 'would
    automatically become effective for a period of time established by
    the Debarment Committee ... converted into a Temporary Debarment'
    ONLY if the Respondent fails to meet imposed conditions); (c)
    Temporary Debarment, 'for a specific period of time not to exceed
    five (5) years ... one, three or five years' -- a DISCRETE
    three-value ENUM {1, 3, 5}, genuinely different in shape from Saint
    Lucia's own CONTINUOUS 6-60-month range and from Grenada's own
    SINGLE fixed constant; (d) Permanent Debarment ('used rarely',
    requiring 'at least two orders of temporary debarment' as a
    precondition) -- has NO expiry date at all. §8(2): 'the name of the
    debarred bidder ... shall be included in the List of Debarred
    Bidders maintained by the Central Procurement Office'. §6(5)(b):
    once the Committee determines no sanction is warranted, the Board
    'shall ... immediately lift the automatic suspension imposed under
    section 64(3) of the Act' -- confirming the interim leg is lifted
    on a CLEARED outcome, not merely expires.

    Operational-liveness cross-check: Regulation 7 of the 2019
    Regulations independently confirms the same mechanism from the
    other direction -- 'Where the Secretary has notice of any
    circumstance that may lead to the suspension or debarment of a
    person under Part 8 of the Act, such information shall be
    forwarded to the Board forthwith and the Board may take such
    information into account in determining whether or not to initiate
    proceedings under the Fourth Schedule of the Act.'
  - **Business registration: the Commerce and Intellectual Property
    Office (CIPO), confirmed via CIPO's own official 'Laws administered
    by CIPO' page, cross-validated against the House of Assembly's own
    1994 legislative register.** `cipo.gov.vc/index.php/legislation`
    (fetched directly) states in its own words: 'CIPO currently
    administers the following laws: Companies Act, 1994 ... Companies
    Regulations, 1996 ... Registration of Business Names Act, Cap. 111
    ... Registration of Business Names Fees Regulations, 1981 ...
    Societies Act, Cap. 330 ... Trade Marks Act, 2003 ... Patents Act,
    Cap. 110 ... Copyright Act, 2003 ... Geographical Indications Act,
    2004 ... Plant Breeders Protection ACT, 2019'. **Independently
    cross-validated**, not merely assumed from CIPO's own
    self-description: the House of Assembly's own annual Acts register
    (`assembly.gov.vc`, `/assembly/images/Acts/1994.pdf` -- a genuinely
    text-layer-native PDF, not scanned, generated by Microsoft Word,
    unlike the Act/Regulations PDFs above) lists, among 1994's
    legislative business: 'Bill for an Act to revise and amend the law
    relating to companies and to provide for related and consequential
    matters ... ACT. NO. 9 of 1994', Assent 28.12.94, Publication
    28.12.94 -- confirming the Companies Act, 1994 is genuinely **Act
    No. 9 of 1994**. Unlike Saint Lucia/Antigua and Barbuda/Grenada's
    own TWO-Act model (a dedicated registry-establishment Act separate
    from the substantive Companies Act), this session found no evidence
    of a separate 'CIPO Act' distinct from the Companies Act itself --
    CIPO's own page describes itself as the administering department
    for a LIST of laws, not as an entity established by its own
    dedicated statute; this catalog reports this honestly as a
    genuinely different (single-registrar-multiple-Acts) shape rather
    than assuming the OECS two-Act pattern applies here too.
  - **Tax registration and the TIN question, confirmed real for VCT
    (unlike Saint Lucia's own honest gap) via the Tax Administration
    Act, 2019's own primary text.** `ird.gov.vc/index.php/legislation`
    (fetched directly) lists: 'Cap435 Income Tax Act', 'VAT Act Cap.
    445', 'Tax Administration Act, 2019' among others. The Income Tax
    Act PDF (`Cap435.pdf`, downloaded directly -- genuinely text-layer
    native, 9249 lines, NOT scanned) confirms: 'CHAPTER 435 INCOME TAX
    ACT ... Act No. 2 of 1979 ... [Date of commencement: 1st January,
    1979.] This Act may be cited as the Income Tax Act.' The Tax
    Administration Act, 2019 PDF (scanned, OCR'd) confirms: 'SAINT
    VINCENT AND THE GRENADINES TAX ADMINISTRATION ACT, 2019 ... ACT NO.
    30 OF 2019 ... 31st December, 2019 ... [BY PROCLAMATION]' (no
    2-year automatic-commencement backstop clause was found in this
    Act's own s.1, unlike the Public Procurement Act's own s.1(3) --
    an honestly-noted structural difference between the two 2018/2019
    statutes rather than an assumed-uniform commencement mechanism).
    s.5: 'The Governor-General must appoint a Comptroller of Inland
    Revenue'. **s.9(1), read directly and quoted verbatim: 'The
    Comptroller must assign a unique taxpayer identification number
    (\"TIN\") to every taxpayer.'** -- this catalog therefore DOES use
    the term 'TIN' for Saint Vincent and the Grenadines, a positive
    finding the honest LCA sibling could not make for its own
    jurisdiction.
  - **Investment law: the Fiscal Incentives Act, confirmed real via the
    Customs & Excise Department's own live page, NOT via Invest SVG
    (the investment-promotion agency's own site names no specific
    incentive Act).** `customs.gov.vc/fiscal-incentives-act` (fetched
    directly) displays an embedded PDF titled 'Fiscal Incentives Act
    Chapter 468' -- confirmed real and current, administered by the
    Customs & Excise Department (a related but separate 'Hotel Aid
    Act' is also named on the same site, not investigated further this
    session). **Honest scope limit**: this session did not extract the
    Fiscal Incentives Act's own PDF text (the embed did not expose a
    direct downloadable link in the page's static HTML this session
    tried) -- the chapter number and administering authority are
    confirmed from the Customs & Excise Department's own official page,
    but specific incentive provisions/thresholds are NOT catalogued
    here, the same 'confirmed name/authority, not full text' honesty
    this catalog also applies to the Department of Labour's older Acts
    (see `statute.facts`). Investment via public-procurement market
    entry is, in any case, downstream of this blueprint's actual domain
    (bidding into public contracts, not general foreign-direct-investment
    facilitation) -- Invest SVG (`investsvg.com`, formerly National
    Investment Promotions Inc./NIPI) is documented here only as a
    supplementary finding, the same discipline Saint Lucia's own
    `marketentry.facts` applies to its International Business
    Companies Act finding.
  - `rep-spec-basis`: POPULATED for VCT (like GRD's/LCA's/DMA's/BRB's
    own positive findings, unlike ATG's honest nil) -- grounded in
    s.64(7)(b)-(c) and Schedule 4 §8(1) below (the Act's own broader
    'affiliate' control-test extension, genuinely different in
    definition-shape from Saint Lucia's own simpler
    directors/shareholders/staff list).

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit.
  `:debarment-*` grounds this vertical's flagship governor check
  (`debarment-disqualifying-spec-basis` / `marketentry.registry`'s
  independent recompute) -- a STATE-AND-KIND-DISPATCHED disqualification
  lifecycle, the mirror image of neither Saint Lucia's own continuous
  duration-window nor Grenada's own fixed-constant date-recompute
  shapes. `:rep-owner-authority` etc. are, like GRD's/LCA's/DMA's own
  findings and unlike ATG's honest nil, POPULATED for VCT."
  {"VCT" {:name "Saint Vincent and the Grenadines"
          :owner-authority "Central Procurement Board (Public Procurement Act, 2018, Act No. 34 of 2018, ss.7-11) and Central Procurement Office headed by a Chief Procurement Officer (ss.12-14)"
          :legal-basis "Public Procurement Act, 2018 (Act No. 34 of 2018, assented 31 December 2018; s.1(3) provides a 2-year automatic-commencement backstop even absent Proclamation); Public Procurement Regulations, 2019 (S.R.O. No. 23 of 2019, gazetted 22 November 2019, commencing with the Act)"
          :national-spec "Central Procurement Board approval required before invitations to pre-qualified bidders are sent AND before contract award, both above EC$45,000 (reg.5(1)-(2)); open competitive bidding is the default procedure above EC$20,000 (reg.5(3)); international media advertising required above EC$10 million (works) or EC$500,000 (goods/services) (reg.5(4)); 'small value' procurement is EC$500 or less for goods/works (reg.5(5)) or EC$15,000 or less for consultancy services (reg.5(6))"
          :provenance "https://procurement.gov.vc/eprocure/index.php/current-bids"
          :required-evidence ["Certificate of Incorporation (Companies Act, 1994, Act No. 9 of 1994, administered by the Commerce and Intellectual Property Office (CIPO))"
                              "TIN registration record (Tax Administration Act, 2019, Act No. 30 of 2019, s.9 -- Comptroller of Inland Revenue)"
                              "Confirmation the bidder, and any named affiliate, is not currently subject to an active Debarment Committee investigation or an unexpired Temporary/Permanent Debarment sanction under s.64 of the Public Procurement Act, 2018"
                              "Authorized-representative confirmation record"]
          :corporate-number-owner-authority "Comptroller of Inland Revenue, Inland Revenue Department"
          :corporate-number-legal-basis "Tax Administration Act, 2019 (Act No. 30 of 2019) s.9(1): 'The Comptroller must assign a unique taxpayer identification number (\"TIN\") to every taxpayer.' Income Tax Act, Cap. 435 (Act No. 2 of 1979, in force 1 January 1979) is the Department's general legal basis for income tax administration"
          :corporate-number-provenance "https://ird.gov.vc/index.php/legislation"
          :business-registration-owner-authority "Commerce and Intellectual Property Office (CIPO), Government of Saint Vincent & the Grenadines"
          :business-registration-legal-basis "Companies Act, 1994 (Act No. 9 of 1994, assented 28 December 1994) -- confirmed both via CIPO's own 'Laws administered by CIPO' page and independently via the House of Assembly's own 1994 legislative register"
          :business-registration-provenance "https://cipo.gov.vc/index.php/legislation ; https://assembly.gov.vc/assembly/images/Acts/1994.pdf"
          :debarment-owner-authority "Central Procurement Board (initiates debarment proceedings, s.64(3)) and a 3-member Debarment Committee (Chief Procurement Officer as chair, an Attorney-General's Office legal officer, and another procuring entity's head, Schedule 4 §3) -- Public Procurement Act, 2018, Act No. 34 of 2018"
          :debarment-legal-basis "Public Procurement Act, 2018, Act No. 34 of 2018, s.64 'Suspension and debarment': s.64(3) 'Once the Board initiates the debarment proceedings by establishing a Debarment Committee, the bidder ... shall be automatically suspended'; Schedule 4 (ss.64(5),(9),(10)) 'Suspension and Debarment Procedure' §7 'Debarment sanction': four kinds -- Reprimand, Conditional Non-Debarment, Temporary Debarment ('one, three or five years', not to exceed five years), Permanent Debarment (requires at least two prior temporary debarments)"
          :debarment-provenance "https://procurement.gov.vc/eprocure/images/pdf/acts/Public_Procurement_Act_34_of_2018.pdf ; https://procurement.gov.vc/eprocure/index.php/laws-and-regulations"
          :rep-owner-authority "Central Procurement Board / Debarment Committee, Public Procurement Act, 2018 s.64(7)(b)-(c) and Schedule 4 §8(1)"
          :rep-legal-basis "s.64(7)(b): an 'affiliate' means business concerns, organisations or individuals which, directly or indirectly, either control or have the power to control the bidder, or are controlled by or may be subject to the control of the bidder; Schedule 4 §8(1): the Board may extend a Notice of Debarment to any affiliates of the bidder, provided they are specifically named in the Notice of Proposed Debarment and given an opportunity to respond"
          :rep-provenance "https://procurement.gov.vc/eprocure/images/pdf/acts/Public_Procurement_Act_34_of_2018.pdf"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-vct R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative/affiliate-related requirement map,
  or nil when this catalog has no such regime. For VCT this is
  POPULATED -- see the `catalog` docstring's s.64(7)/Schedule 4 §8(1)
  finding."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-registration (TIN) regime,
  or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn business-registration-spec-basis
  "The jurisdiction's business (state) registration regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:business-registration-owner-authority sb)
      (select-keys sb [:business-registration-owner-authority
                       :business-registration-legal-basis
                       :business-registration-provenance]))))

(defn debarment-spec-basis
  "The jurisdiction's tenderer/contractor debarment regime, or nil. For
  VCT this is HIGH confidence, grounded directly in the Public
  Procurement Act, 2018's own primary text (s.64) AND its own Schedule
  4 'Suspension and Debarment Procedure' -- the flagship check this
  vertical adds (a state-and-kind-dispatched disqualification lifecycle,
  see `marketentry.registry`) is grounded here, not copied from a
  sibling's citation."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:debarment-owner-authority sb)
      (select-keys sb [:debarment-owner-authority
                       :debarment-legal-basis
                       :debarment-provenance]))))

(ns statute.facts
  "General-law compliance catalog for Saint Vincent and the Grenadines
  (VCT) -- extends this repo's existing `marketentry.facts`
  (public-procurement market-entry only, narrow scope) with a second,
  orthogonal catalog of statutes a company operating in this
  jurisdiction must generally track for compliance. Mirrors
  cloud-itonami-iso3166-jpn/-deu/-bgr/-aze/-alb/-arm/-atg/-brb/-dma/
  -grd/-lca/-kna's `statute.facts` (ADR-2607141700,
  cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL Saint Vincent and the Grenadines
  government-hosted URL -- never fabricated. Unlike Saint Lucia's own
  single consolidated 'Revised Laws' law browser, SVG has NO single
  consolidated-law host this session could find -- `svglaws.com` is
  confirmed to be a domain-parking lander page, NOT a government
  property (see `marketentry.facts` namespace docstring). Instead,
  each department publishes its own 'Legislation' page naming the Acts
  it administers, and the House of Assembly's own Acts archive
  (`assembly.gov.vc/assembly/index.php/acts-sp-1308988606`, by year,
  1993-2025) independently cross-validates specific Act numbers. Every
  entry below was fetched directly this session:

  - Companies Act, 1994 (Act No. 9 of 1994) -- see `marketentry.facts`
    for the full two-source (CIPO + House of Assembly 1994 register)
    confirmation. Repeated here for the general-compliance catalog
    since every incorporated company must track its own constitutive
    statute, not only at initial market entry.
  - Protection of Employment Act, 2003 -- confirmed via the Department
    of Labour's own 'Legislation' page (`dol.gov.vc/dol/index.php/
    legislation`, fetched directly), which describes it in its own
    words: 'This Act provides for the maintenance and promotion of
    good employment relationships between employers and employees. It
    also addresses matters of severance and settlement of disputes.'
    **HONEST STRUCTURAL FINDING, genuinely different from every OECS
    sibling's own consolidated Labour Code/Employment Act shape**: SVG
    does NOT have one consolidated labour code -- the Department of
    Labour's own page lists THIRTEEN separate historic Acts governing
    different aspects of employment law (Wages Councils Act 1953,
    Trade Unions Act 1950, Trade Disputes (Arbitration and Inquiry)
    Act 1940, Shop (Hours of Opening and Employment) Act 1942,
    Recruiting of Workers Act 1940, Protection of Employment Act 2003,
    Essential Services Act 1965, Equal Pay Act 1994, Employment of
    Women, Young Persons and Children Act 1935, Employment of Foreign
    Nationals and Commonwealth Citizens Act 1973, Employment Exchanges
    Act 1956, Employers and Servants Act 1937, Accidents and
    Occupational Diseases (Notification) Act 1952) -- a MORE fragmented
    shape than even Dominica's own two-separate-statutes split
    documented in this family. This catalog cites the Protection of
    Employment Act, 2003 as the single most general-compliance-relevant
    entry (termination, severance, dispute settlement -- functionally
    closest to what ATG's Labour Code/GRD's Employment Act/LCA's Labour
    Act cover in one consolidated instrument) but does not claim the
    other twelve Acts are out of scope, only that they are not
    catalogued here yet. **Honest access gap**: the DOL's own page
    states 'Access to Acts: A copy of the Acts can be purchased at the
    Government Printery' -- no free full-text PDF was available online
    this session for the Protection of Employment Act itself, so this
    catalog cites the Department's own official description rather
    than a specific section number, the same 'confirmed name/authority,
    not full text' honesty this catalog also applies to the Fiscal
    Incentives Act (see `marketentry.facts`). **Cross-validation**: the
    House of Assembly's own 1994 register independently confirms the
    Equal Pay Act, 1994 is genuinely Act No. 3 of 1994 ('Bill for an
    Act to make provision for the removal and prevention of
    discrimination based on the sex of the employee, in the rates of
    remuneration for males and females in paid employment', Assent
    14.3.94) -- matching the DOL's own description verbatim in
    substance, even though this catalog does not carry the Equal Pay
    Act as its own separate entry.
  - Income Tax Act, Cap. 435 (Act No. 2 of 1979) -- confirmed via its
    own primary text (`Cap435.pdf`, downloaded directly from
    `ird.gov.vc`, a genuinely text-layer-native PDF, NOT scanned):
    '[Date of commencement: 1st January, 1979.] This Act may be cited
    as the Income Tax Act.' The general income-tax legal basis for
    every company operating in SVG, separate from the Tax
    Administration Act's own procedural/TIN-assignment machinery (see
    `marketentry.facts` for the s.9 TIN finding).
  - Tax Administration Act, 2019 (Act No. 30 of 2019) -- confirmed via
    its own primary text (OCR'd from `Tax_Administration_Act_2019.pdf`,
    `finance.gov.vc`): 'AN ACT to revise and consolidate the law
    relating to the administration of taxation laws ... 31st December,
    2019 ... [BY PROCLAMATION]'. s.5 establishes the Comptroller of
    Inland Revenue; s.9(1) requires the Comptroller to assign a TIN to
    every taxpayer. Listed here (in addition to `marketentry.facts`'s
    corporate-number entry) because the Act's own Part IX ('Penalties')
    and Part X ('Criminal Proceedings') make it a general
    compliance-relevant statute beyond initial tax registration alone.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.

  See `marketentry.facts` for this session's separate findings on the
  Fiscal Incentives Act, Chapter 468 (confirmed real via the Customs &
  Excise Department's own page, chapter number and administering
  authority only -- full provisions not extracted this session) and
  the VAT Act, Cap. 445 (confirmed via IRD's own legislation page,
  listed but not deeply read this session) -- documented there rather
  than duplicated in this general-compliance catalog, since investment
  incentives and VAT registration are both elective/transactional
  regimes a company engages with situationally, not a law every SVG
  company must track from day one the way the Companies Act, the
  Protection of Employment Act and the Income Tax Act are.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"VCT"
   [{:statute/id "vct.companies-act"
     :statute/title "Companies Act"
     :statute/jurisdiction "VCT"
     :statute/kind :law
     :statute/law-number "Act No. 9 of 1994 (assented 28 December 1994) -- confirmed both via CIPO's own 'Laws administered by CIPO' page and independently via the House of Assembly's own 1994 legislative register"
     :statute/url "https://cipo.gov.vc/index.php/legislation"
     :statute/url-provenance :official-cipo
     :statute/enacted-date "1994-12-28"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "vct.protection-of-employment-act"
     :statute/title "Protection of Employment Act"
     :statute/jurisdiction "VCT"
     :statute/kind :law
     :statute/law-number "2003 -- one of THIRTEEN separate historic labour Acts the Department of Labour's own page lists (no consolidated labour code exists for SVG, see namespace docstring); full text not available online this session (DOL's own page: 'A copy of the Acts can be purchased at the Government Printery')"
     :statute/url "https://dol.gov.vc/dol/index.php/legislation"
     :statute/url-provenance :official-department-of-labour
     :statute/enacted-date "2003-01-01"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:labor :employment :termination}}
    {:statute/id "vct.income-tax-act"
     :statute/title "Income Tax Act"
     :statute/jurisdiction "VCT"
     :statute/kind :law
     :statute/law-number "Cap. 435 (Act No. 2 of 1979, in force 1 January 1979)"
     :statute/url "https://ird.gov.vc/images/pdf/Cap435.pdf"
     :statute/url-provenance :official-inland-revenue-department
     :statute/enacted-date "1979-01-01"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:tax :income-tax}}
    {:statute/id "vct.tax-administration-act"
     :statute/title "Tax Administration Act"
     :statute/jurisdiction "VCT"
     :statute/kind :law
     :statute/law-number "2019 (Act No. 30 of 2019, assented 31 December 2019, by Proclamation -- no 2-year automatic-commencement backstop clause found in this Act's own s.1, unlike the Public Procurement Act's own s.1(3), see `marketentry.facts`); s.5 Comptroller of Inland Revenue; s.9(1) TIN assignment"
     :statute/url "https://ird.gov.vc/index.php/legislation"
     :statute/url-provenance :official-inland-revenue-department
     :statute/enacted-date "2019-12-31"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:tax :tax-administration}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-vct statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "VCT")) " VCT statutes seeded with an "
                 "official government-hosted citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :tax)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))

(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `debarment-disqualifying?` (plus its helpers) is THIS vertical's own
  new ground-truth recompute, grounding VCT's flagship governor check
  (`marketentry.governor/debarment-disqualifying-violations`): the
  Public Procurement Act, 2018 (Act No. 34 of 2018) s.64 'Suspension
  and debarment' (own primary text, see `marketentry.facts`) and its
  own Schedule 4 'Suspension and Debarment Procedure'.

  This is a DIFFERENT check SHAPE from every prior sibling this repo's
  family has implemented, and specifically from every prior DATE- or
  THRESHOLD-shaped check: Saint Lucia's s.114 recompute is a single
  CONTINUOUS statutorily-bounded duration window (6-60 months); Grenada's
  director-conviction recompute is anchored to a SINGLE FIXED constant
  (a 2-year lookback); Saint Kitts and Nevis's notice-period check is a
  COMPOUND duration-and-multiplicity recompute over two independent
  numeric fields; none of these three has more than ONE mode of
  disqualification. VCT's own s.64 + Schedule 4 instead describe a
  bidder's debarment status as a LIFECYCLE with FOUR qualitatively
  different states, only one of which is even date-shaped:

    1. an INTERIM, PURELY STATE-BASED automatic suspension the moment
       the Central Procurement Board 'initiates the debarment
       proceedings by establishing a Debarment Committee' (s.64(3)) --
       NO date arithmetic is involved in this leg at all: the bidder is
       disqualified for as long as a case is open, full stop, until
       either cleared (s.64 Schedule 4 §6(5)(b): the suspension is
       'immediately lift[ed]') or converted into a final sanction;
    2. a Reprimand sanction (Schedule 4 §7(1)(a)) -- NEVER disqualifying
       ('falls short of debarment' by the Act's own words);
    3. a Temporary Debarment sanction (§7(1)(c)) -- a window bounded by
       a DISCRETE THREE-VALUE ENUM {1, 3, 5} years, not a continuous
       range (Saint Lucia's own shape) and not a single constant
       (Grenada's own shape) -- an out-of-set value is treated as an
       untrustworthy record, the same conservative-hold discipline
       Saint Lucia's own out-of-range check uses, but validated against
       SET MEMBERSHIP rather than a MIN/MAX bound;
    4. a Permanent Debarment sanction (§7(1)(d)) -- ALWAYS disqualifying,
       with NO expiry date at all, a genuinely different (unbounded)
       shape from every date-recompute sibling.

  A fifth wrinkle, Conditional Non-Debarment (§7(1)(b)), is a sanction
  that behaves like case 2 (not disqualifying) UNLESS the engagement's
  own declared `:conditional-non-debarment-breached?` flag is true, in
  which case the Act's own text says it 'would automatically become
  effective ... converted into a Temporary Debarment' -- so this
  recompute falls through to case 3's own duration-window logic using
  the SAME `:debarment-sanction-years` / `:debarment-sanction-start-date`
  fields.

  Dates are plain ISO-8601 \"YYYY-MM-DD\" strings -- deliberately no
  external date/calendar library and no host date API (`java.time` /
  `js/Date`), the same technique this family's Barbados/Grenada/Saint
  Lucia siblings established for their own (different) date-shaped
  checks. `compute-debarment-end` bumps only the YEAR component (the
  Act's own Schedule 4 §7(1)(c) duration unit is whole years, not
  months, so no month-carry arithmetic is needed here, a genuinely
  simpler computation than Saint Lucia's own month-granularity
  `compute-suspension-end`), compared with plain string `compare`,
  which sorts zero-padded ISO-8601 dates in chronological order.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real Central Procurement Board, Central Procurement
  Office, or Debarment Committee system. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [kotoba.lang.text :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(def ^:private money-scale
  "Sub-minor-unit scale used when comparing two money amounts: 1/10000 of
  a unit. Coarser than double representation error by many orders of
  magnitude, finer than any real currency's minor unit (2 decimals for
  most, 3 for KWD/BHD/OMR, 0 for JPY/KRW)."
  10000)

(defn- money=
  "Exact-at-money-precision equality for two amounts.

  `==` on raw doubles is NOT the right comparison for money. With
  whole-unit fees the two agree, but as soon as an amount carries
  cents the sum `base + rate x months` is routinely not the double
  nearest the true total, and a CORRECT claim compares false: measured
  on this exact shape, 40,989 of 327,060 cent-denominated combinations
  (12.5%) were rejected while being right, against 0 of 327,060 in
  whole units.

  Rounding both sides to `money-scale` before comparing removes the
  representation error while preserving every distinction money can
  actually carry."
  [x y]
  (and (number? x) (number? y)
       (= (Math/round (* money-scale (double x)))
          (Math/round (* money-scale (double y))))))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  ;; nil when any field is not a number: an un-recomputable engagement is
  ;; un-verifiable, which is neither `correct` nor a ClassCastException
  ;; thrown out of the caller.
  (when (and (number? base-fee) (number? monthly-rate) (number? monitoring-months))
    (+ (double base-fee)
       (* (double monthly-rate) (double monitoring-months)))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (money= claimed-fee (compute-engagement-fee engagement)))

(def lawful-temporary-debarment-years
  "Schedule 4 §7(1)(c)'s own statutory ENUM (not a range): 'the
  Debarment Committee may impose a temporary debarment of one, three
  or five years.' A discrete SET, deliberately not a continuous
  min/max bound."
  #{1 3 5})

(defn lawful-temporary-debarment-years?
  "Is `years` a member of Schedule 4 §7(1)(c)'s own statutory enum
  {1, 3, 5}? Anything else (including a plausible-looking value like 2
  or 4) is an untrustworthy record here -- this catalog does not
  interpolate a 'nearest lawful value'."
  [years]
  (boolean (and (number? years) (contains? lawful-temporary-debarment-years years))))

(defn compute-debarment-end
  "The ground-truth date on which a Temporary Debarment sanction
  starting on `start-date` (\"YYYY-MM-DD\") for `years` STOPS
  disqualifying a bidder under Schedule 4 §7 -- `years` calendar years
  later, same month and day. Returns nil if either input is missing."
  [start-date years]
  (when (and start-date years (>= (count start-date) 4))
    (let [year (#?(:clj Integer/parseInt :cljs js/parseInt) (subs start-date 0 4))
          rest-of-date (subs start-date 4)]
      (str (zero-pad (+ year (long years)) 4) rest-of-date))))

(defn temporary-debarment-disqualifying?
  "Two legs, independently recomputed, mirroring Schedule 4 §7(1)(c):

    1. an OUT-OF-SET `:debarment-sanction-years` (not one of {1, 3, 5})
       is treated as an untrustworthy record -- conservatively
       disqualifying pending correction, the same discipline this
       family's Saint Lucia sibling applies to an out-of-range
       duration; and
    2. otherwise, does `submission-date` fall ON OR AFTER
       `debarment-sanction-start-date` and ON OR BEFORE the computed
       `debarment-sanction-start-date + years`?

  No `:debarment-sanction-start-date` on file -> never disqualifying by
  this leg (no temporary sanction recorded)."
  [{:keys [debarment-sanction-start-date debarment-sanction-years submission-date]}]
  (boolean
   (when debarment-sanction-start-date
     (or (not (lawful-temporary-debarment-years? debarment-sanction-years))
         (when-let [end (compute-debarment-end debarment-sanction-start-date debarment-sanction-years)]
           (and submission-date
                (<= (compare debarment-sanction-start-date submission-date) 0)
                (<= (compare submission-date end) 0)))))))

(defn debarment-committee-active?
  "The INTERIM, purely state-based leg of s.64(3): has the Central
  Procurement Board initiated debarment proceedings against this
  bidder by establishing a Debarment Committee, with no final sanction
  kind recorded yet (i.e. the case is still open)? No date arithmetic
  at all -- this is a pure process-state read, the genuinely new shape
  this vertical's flagship check adds to the family."
  [{:keys [debarment-committee-established? debarment-sanction-kind]}]
  (boolean (and debarment-committee-established?
                (nil? debarment-sanction-kind))))

(defn debarment-disqualifying?
  "Does `engagement`'s own declared debarment lifecycle STILL
  disqualify it under s.64/Schedule 4 as of its own declared
  `:submission-date`? Dispatches on TWO ground-truth fields --
  `:debarment-committee-established?` (the interim leg) and
  `:debarment-sanction-kind` (one of nil/:reprimand/
  :conditional-non-debarment/:temporary/:permanent, the final-sanction
  leg) -- rather than a single date or threshold, the genuinely new
  multi-mode shape this vertical's flagship check adds to the family:

    - interim leg first: an ACTIVE, unresolved Debarment Committee
      case is ALWAYS disqualifying, full stop, regardless of any date
      (s.64(3));
    - `nil` kind (no case, or already resolved with no committee
      active) -> not disqualifying;
    - `:reprimand` -> never disqualifying (Schedule 4 §7(1)(a));
    - `:permanent` -> ALWAYS disqualifying, no expiry (§7(1)(d));
    - `:temporary` -> `temporary-debarment-disqualifying?`'s own
      duration-window recompute;
    - `:conditional-non-debarment` -> only disqualifying if
      `:conditional-non-debarment-breached?` is true, in which case it
      falls through to the SAME duration-window recompute as
      `:temporary` (§7(1)(b)'s own auto-conversion)."
  [{:keys [debarment-sanction-kind conditional-non-debarment-breached?]
    :as engagement}]
  (boolean
   (or (debarment-committee-active? engagement)
       (case debarment-sanction-kind
         nil false
         :reprimand false
         :permanent true
         :temporary (temporary-debarment-disqualifying? engagement)
         :conditional-non-debarment (and (true? conditional-non-debarment-breached?)
                                          (temporary-debarment-disqualifying? engagement))
         false))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  system."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))

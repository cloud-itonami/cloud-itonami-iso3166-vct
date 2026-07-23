(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest vct-has-spec-basis
  (let [sb (facts/spec-basis "VCT")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "VCT")))
    (is (some? (facts/business-registration-spec-basis "VCT")))
    (is (some? (facts/debarment-spec-basis "VCT")))))

(deftest vct-rep-spec-basis-is-populated
  (testing "s.64(7)(b)-(c) + Schedule 4 §8(1) extend debarment to affiliates -- genuinely populated, unlike ATG's honest nil"
    (is (some? (facts/rep-spec-basis "VCT")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "VCT")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "VCT" all)))
    (is (not (facts/required-evidence-satisfied? "VCT" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["VCT" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

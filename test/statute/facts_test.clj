(ns statute.facts-test
  (:require [kotoba.lang.text :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest vct-has-spec-basis
  (let [sb (facts/spec-basis "VCT")]
    (is (= 4 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["VCT" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["vct.protection-of-employment-act"]
         (mapv :statute/id (facts/by-topic "VCT" :labor))))
  (is (= ["vct.income-tax-act" "vct.tax-administration-act"]
         (sort (mapv :statute/id (facts/by-topic "VCT" :tax)))))
  (is (empty? (facts/by-topic "VCT" :environment)))
  (is (empty? (facts/by-topic "ATL" :labor))))

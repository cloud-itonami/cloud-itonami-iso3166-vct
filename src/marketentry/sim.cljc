(ns marketentry.sim
  "Demo driver -- `clojure -M:dev:run`. Walks a clean engagement
  through intake -> jurisdiction assessment -> filing draft
  (escalate/approve/commit) -> filing submit (escalate/approve/
  commit), then shows every HARD-hold scenario the debarment-lifecycle
  flagship check and its siblings defend against."
  (:require [langgraph.graph :as g]
            [marketentry.store :as store]
            [marketentry.operation :as op]))

(def operator {:actor-id "op-1" :actor-role :market-entry-operator :phase 3})

(defn- exec-op [actor tid request context]
  (g/run* actor {:request request :context context} {:thread-id tid}))

(defn- approve! [actor tid]
  (g/run* actor {:approval {:status :approved :by "op-1"}} {:thread-id tid :resume? true}))

(defn -main [& _]
  (let [db (store/seed-db)
        actor (op/build db)]
    (println "== engagement/intake eng-1 (VCT, clean) ==")
    (println (exec-op actor "t1" {:op :engagement/intake :subject "eng-1"
                                  :patch {:id "eng-1" :operator "Bequia Marine Supplies Ltd"}} operator))

    (println "== jurisdiction/assess eng-1 (escalates -- human approves) ==")
    (println (exec-op actor "t2" {:op :jurisdiction/assess :subject "eng-1"} operator))
    (println (approve! actor "t2"))

    (println "== filing/draft eng-1 (always escalates -- actuation/draft-filing) ==")
    (let [r (exec-op actor "t3" {:op :filing/draft :subject "eng-1"} operator)]
      (println r)
      (println "-- human market-entry operator approves --")
      (println (approve! actor "t3")))

    (println "== filing/submit eng-1 (always escalates -- actuation/submit-filing) ==")
    (let [r (exec-op actor "t4" {:op :filing/submit :subject "eng-1"} operator)]
      (println r)
      (println "-- human market-entry operator approves --")
      (println (approve! actor "t4")))

    (println "== jurisdiction/assess eng-2 (no spec-basis -> HARD hold) ==")
    (println (exec-op actor "t5" {:op :jurisdiction/assess :subject "eng-2" :no-spec? true} operator))

    (println "== jurisdiction/assess eng-3 (sets up fee-mismatch) ==")
    (println (exec-op actor "t6" {:op :jurisdiction/assess :subject "eng-3"} operator))
    (println (approve! actor "t6"))
    (println (exec-op actor "t6b" {:op :filing/draft :subject "eng-3"} operator))
    (println (approve! actor "t6b"))
    (println "== filing/submit eng-3 (fee mismatch -> HARD hold) ==")
    (println (exec-op actor "t7" {:op :filing/submit :subject "eng-3"} operator))

    (println "== jurisdiction/assess eng-4 (sets up active Debarment Committee -- interim leg) ==")
    (println (exec-op actor "t8" {:op :jurisdiction/assess :subject "eng-4"} operator))
    (println (approve! actor "t8"))
    (println (exec-op actor "t8b" {:op :filing/draft :subject "eng-4"} operator))
    (println (approve! actor "t8b"))
    (println "== filing/submit eng-4 (active Debarment Committee, no sanction kind yet -> HARD hold, s.64(3)) ==")
    (println (exec-op actor "t9" {:op :filing/submit :subject "eng-4"} operator))

    (println "== jurisdiction/assess eng-5 (sets up tin-unverified) ==")
    (println (exec-op actor "t10" {:op :jurisdiction/assess :subject "eng-5"} operator))
    (println (approve! actor "t10"))
    (println (exec-op actor "t10b" {:op :filing/draft :subject "eng-5"} operator))
    (println (approve! actor "t10b"))
    (println "== filing/submit eng-5 (tin-unverified -> HARD hold) ==")
    (println (exec-op actor "t11" {:op :filing/submit :subject "eng-5"} operator))

    (println "== jurisdiction/assess eng-6 (lawful 1-year temporary debarment, already expired -- clean) ==")
    (println (exec-op actor "t12" {:op :jurisdiction/assess :subject "eng-6"} operator))
    (println (approve! actor "t12"))
    (println (exec-op actor "t12b" {:op :filing/draft :subject "eng-6"} operator))
    (println (approve! actor "t12b"))
    (println "== filing/submit eng-6 (temporary debarment expired long before submission-date -> escalates, clean) ==")
    (let [r (exec-op actor "t13" {:op :filing/submit :subject "eng-6"} operator)]
      (println r)
      (println (approve! actor "t13")))

    (println "== jurisdiction/assess eng-7 (sets up out-of-set debarment-sanction-years) ==")
    (println (exec-op actor "t14" {:op :jurisdiction/assess :subject "eng-7"} operator))
    (println (approve! actor "t14"))
    (println (exec-op actor "t14b" {:op :filing/draft :subject "eng-7"} operator))
    (println (approve! actor "t14b"))
    (println "== filing/submit eng-7 (debarment-sanction-years=2, not in Schedule 4 §7(1)(c)'s {1,3,5} -> HARD hold) ==")
    (println (exec-op actor "t15" {:op :filing/submit :subject "eng-7"} operator))

    (println "== jurisdiction/assess eng-8 (sets up permanent debarment) ==")
    (println (exec-op actor "t16" {:op :jurisdiction/assess :subject "eng-8"} operator))
    (println (approve! actor "t16"))
    (println (exec-op actor "t16b" {:op :filing/draft :subject "eng-8"} operator))
    (println (approve! actor "t16b"))
    (println "== filing/submit eng-8 (permanent debarment -- no expiry, ALWAYS disqualifying -> HARD hold) ==")
    (println (exec-op actor "t17" {:op :filing/submit :subject "eng-8"} operator))

    (println "== jurisdiction/assess eng-9 (sets up breached conditional-non-debarment) ==")
    (println (exec-op actor "t18" {:op :jurisdiction/assess :subject "eng-9"} operator))
    (println (approve! actor "t18"))
    (println (exec-op actor "t18b" {:op :filing/draft :subject "eng-9"} operator))
    (println (approve! actor "t18b"))
    (println "== filing/submit eng-9 (conditional-non-debarment BREACHED -> converts to temporary, active window -> HARD hold) ==")
    (println (exec-op actor "t19" {:op :filing/submit :subject "eng-9"} operator))

    (println "== jurisdiction/assess eng-10 (sets up unbreached conditional-non-debarment) ==")
    (println (exec-op actor "t20" {:op :jurisdiction/assess :subject "eng-10"} operator))
    (println (approve! actor "t20"))
    (println (exec-op actor "t20b" {:op :filing/draft :subject "eng-10"} operator))
    (println (approve! actor "t20b"))
    (println "== filing/submit eng-10 (conditional-non-debarment NOT breached -> never disqualifying -> escalates, clean) ==")
    (let [r (exec-op actor "t21" {:op :filing/submit :subject "eng-10"} operator)]
      (println r)
      (println (approve! actor "t21")))

    (println "== jurisdiction/assess eng-11 (sets up reprimand) ==")
    (println (exec-op actor "t22" {:op :jurisdiction/assess :subject "eng-11"} operator))
    (println (approve! actor "t22"))
    (println (exec-op actor "t22b" {:op :filing/draft :subject "eng-11"} operator))
    (println (approve! actor "t22b"))
    (println "== filing/submit eng-11 (reprimand -- 'falls short of debarment', never disqualifying -> escalates, clean) ==")
    (let [r (exec-op actor "t23" {:op :filing/submit :subject "eng-11"} operator)]
      (println r)
      (println (approve! actor "t23")))

    (println "== filing/draft eng-1 AGAIN (double-draft -> HARD hold) ==")
    (println (exec-op actor "t24" {:op :filing/draft :subject "eng-1"} operator))

    (println "== filing/submit eng-1 AGAIN (double-submit -> HARD hold) ==")
    (println (exec-op actor "t25" {:op :filing/submit :subject "eng-1"} operator))

    (println "== audit ledger ==")
    (doseq [f (store/ledger db)] (println f))

    (println "== draft records ==")
    (doseq [r (store/draft-history db)] (println r))

    (println "== submit records ==")
    (doseq [r (store/submit-history db)] (println r))))

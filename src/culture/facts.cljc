(ns culture.facts
  "Country-level regional-culture catalog for Saint Vincent and the
  Grenadines (VCT) -- national dishes, protected products, beverages,
  crafts, festivals and heritage sites, per ADR-2607171400 addendum 2
  (cloud-itonami-municipality-culture-catalog Wave 1, in
  com-junkawasaki/root). Sibling namespace to `marketentry.facts` /
  `statute.facts` (ADR-2607141700); city-level counterparts live in the
  cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"VCT"
   [{:culture/id "vct.dish.breadfruit"
     :culture/name "Breadfruit"
     :culture/country "VCT"
     :culture/kind :dish
     :culture/summary "Roasted breadfruit served with fried jackfish is Saint Vincent and the Grenadines' national dish; the tree was famously carried to St. Vincent aboard William Bligh's 1791 expedition."
     :culture/url "https://en.wikipedia.org/wiki/Breadfruit"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.dish.ducana"
     :culture/name "Ducana"
     :culture/country "VCT"
     :culture/kind :dish
     :culture/summary "Sweet potato dumpling or pudding, made with grated sweet potato, coconut, sugar, flour and spices boiled in salted water; eaten in Saint Vincent and the Grenadines and other Caribbean islands."
     :culture/url "https://en.wikipedia.org/wiki/Ducana"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.dish.pelau"
     :culture/name "Pelau"
     :culture/country "VCT"
     :culture/kind :dish
     :culture/summary "Traditional West Indian rice dish made by caramelizing meat in brown sugar before adding rice, legumes and coconut milk; Wikipedia categorizes it as part of Saint Vincent and the Grenadines cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Pelau"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.product.arrowroot"
     :culture/name "Saint Vincent arrowroot"
     :culture/country "VCT"
     :culture/kind :product
     :culture/summary "Saint Vincent has a long history of arrowroot production, growing from Carib and Garifuna food/medicine use into a major export contributing close to 50% of the country's foreign export earnings between 1900 and 1965."
     :culture/url "https://en.wikipedia.org/wiki/Arrowroot"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.craft.moko-jumbie"
     :culture/name "Moko jumbie"
     :culture/country "VCT"
     :culture/kind :craft
     :culture/summary "Traditional stilt-walking masquerade costume of West African origin; records from the 1870s describe early Moko Jumbie costumes in Saint Vincent and the Grenadines made from European women's dresses over petticoats with headdresses."
     :culture/url "https://en.wikipedia.org/wiki/Moko_jumbie"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.craft.bequia-whaling"
     :culture/name "Bequia whaling"
     :culture/country "VCT"
     :culture/kind :craft
     :culture/summary "Bequia natives are permitted up to four humpback whales per year using traditional hand-thrown harpoons from small open sailboats, a practice introduced by Yankee whalers in the 19th century and classified by the IWC as aboriginal whaling."
     :culture/url "https://en.wikipedia.org/wiki/Bequia"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.festival.vincy-mas"
     :culture/name "Vincy Mas"
     :culture/country "VCT"
     :culture/kind :festival
     :culture/summary "Carnival of Saint Vincent and the Grenadines, held late June/early July, listed among the Caribbean's national carnivals."
     :culture/url "https://en.wikipedia.org/wiki/List_of_Caribbean_carnivals_around_the_world"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "vct.heritage.la-soufriere"
     :culture/name "La Soufrière"
     :culture/country "VCT"
     :culture/kind :heritage
     :culture/summary "Active volcano on the island of Saint Vincent standing 1,235 meters, the highest peak in Saint Vincent and the Grenadines, with eight recorded eruptions since 1718 including December 2020-April 2021."
     :culture/url "https://en.wikipedia.org/wiki/La_Soufri%C3%A8re_(Saint_Vincent)"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-vct culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "VCT"))
                 " VCT entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))

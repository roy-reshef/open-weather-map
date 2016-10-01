(ns open-weather-map.http-client
  (:require
    [clj-http.client :as client]
    [clojure.string :as str]))


(def cities-info-url "http://download.geonames.org/export/dump/admin1CodesASCII.txt")

;; parses cities info response body and returns a map of
;; where key is city name and value is code
(defn parse-cities-result [result]
  (println "parsing cities info raw data")
  ;;(println (str "raw data:\n" result))
  ;; creates list of arrays. each array represetns a city info
  (def a (re-seq #"(\S+)\t+([A-Za-z ]+)\t+([A-Za-z ]+)\t+(\S+)" result))
  ;;(println (str "match:" a))
  (reduce #(assoc %1 (str/lower-case (nth %2 2)) (nth %2 4)) {} a))

;; performs an http request to get cities info
;; returns map with city name as key and code as value
(defn get-city-codes
  "returns cities list and codes"
  []
  (println "fetching cities list and code from the net")
  (parse-cities-result (get (client/get cities-info-url) :body)))

(def get-city-codes-memo (memoize get-city-codes))


;; currently prints out only weather info from body
;; expecting full result and using destructor on the parameter level
(defn print-city-data-response [{body :body}]
  (println "response data body:" body))


(defn get-city-info-by-code [code]
  (println "fetching data for city by code code:" code)

  (let [a (str "http://api.openweathermap.org/data/2.5/weather?id="
               code
               "&APPID=9623ee0f87c52dff219723a1a9cf8ff4")
        data (client/get a)]
    (println "performing http get to:" a)
    (println "response data:" data)
    (print-city-data-response data)))

;; get weather info for data entry [<city name> <city code>]
(defn get-city-info-by-entry [city-entry]
  (println "fetching data for city:" (first city-entry))
  (get-city-info-by-code (second city-entry)))




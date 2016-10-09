(ns open-weather-map.core
  (:gen-class)
  (:use [clojure.xml])
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str]
            [open-weather-map.http-client :refer :all]
            [open-weather-map.web.server :refer [start-server]]))

(defn -main
  "prints out weather information for requested city"
  [& args]

  ;; get requested city from args
  (def requested-city (first args))
  (println (str "requested city name:" requested-city))

  ;; get cities data
  (def cities-data (get-city-codes-memo))
  (println "there are " (count cities-data) "cities ")
  ;;(doseq [entry city-cities-data] (println entry))

  (if (nil? requested-city)
    (do
      (println "city not specified on comman line. starting web server")
      (let [port (Integer. (or (System/getenv "open.weather.server.port") "8080"))]
                (start-server port)))
    (do
      (println "city was specified on comman line. getting city weather info")
      ;; get requested city code
      (def city-code (get cities-data (str/lower-case requested-city)))
      (println "requested city code:" city-code)
      (if (nil? city-code)
        (println "requested city code could not be resolved")
        (loop [] ;; get city weather info in loop
          (print-city-info-by-code city-code)
          (println "next execution in one minute")
          (Thread/sleep 60000)
          (recur))))))
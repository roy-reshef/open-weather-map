(ns open-weather-map.web.server
  (:require
    [compojure.core :refer [defroutes GET]]
    [compojure.handler :as handler]
    [compojure.route :as route]
    [ring.adapter.jetty :as ring]
    [open-weather-map.web.views :as views]
    [open-weather-map.http-client :as service]))

(defroutes routes
           (GET "/" [] (views/index))
           (GET "/city-weather-info" {params :query-params} (service/get-city-info-by-name (get params "city")))

  (route/not-found (views/four-oh-four)))


(defn start-server [port]
  (ring/run-jetty (handler/site routes) {:port port :join? false}))



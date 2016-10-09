(ns open-weather-map.web.views
  (:require [hiccup.page :as page]
            [hiccup.form :as form]))

(defn index []
  (page/html5
    [:head
     [:title "Hello World"]]
    [:body
     [:div {:id "content"}
      (form/form-to [:get "/city-weather-info"]
                    (form/label "form-label" "Please enter city name to get weather info")
                    (form/text-field "city")
                    (form/submit-button "Submit"))]]))


(defn four-oh-four []
  (page/html5
    [:head
     [:title "Page Not Found"]]
    [:body
          [:div {:id "four-oh-four"}
           "The page you requested could not be found"]]))

(ns open-weather-map.core-test
  (:require [clojure.test :refer :all]
            [open-weather-map.http-client :refer :all]))

(deftest parse-cities-result-test
  (testing "testing cities info response body parsing"
    (let [cities-info "VE.16\tMonagas\tMonagas\t3632100\nVE.15\tMiranda\tMiranda\t3632191"]
      (is (= (parse-cities-result cities-info) {"Monagas" "3632100", "Miranda" "3632191"})))))

;; this test includes a city name with space
(deftest parse-city-wth-space-result-test
  (testing "testing city info response body parsing when city name has spaces"
    (let [cities-info "VE.24\tDependencias Federales\tDependencias Federales\t3640846\nVE.25\tCapital\tCapital\t3640847"]
      (is (= (parse-cities-result cities-info) {"Dependencias Federales" "3640846", "Capital" "3640847"})))))
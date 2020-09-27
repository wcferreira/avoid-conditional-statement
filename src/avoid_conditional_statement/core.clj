(ns avoid-conditional-statement.core
  (:gen-class)
  (:import (clojure.lang Keyword)))

(defn soma
  [a b]
  (+ a b))

;; Purposeless condition
(defn eh-de-maior?
  [idade sexo]
  (and (>= idade 18) (= sexo "M")))

;; Decompose condition
(defn is-not-empty?
  [field]
  (not= (clojure.string/trim field) ""))

(defn is-field-greater-than?
  [length field]
  (>= (count (clojure.string/trim field)) length))

(def gte (partial is-field-greater-than? 3))

(defn is-field-less-than?
  [length field]
  (<= (count (clojure.string/trim field)) length))

(def lte (partial is-field-less-than? 30))

(defn is-valid-name?
  [name]
  (and (is-not-empty? name) (is-field-greater-than? name 3) (is-field-less-than? name 30)))

(def validations { :name [is-not-empty? gte lte] })

(defn is-valid-name2?
  [name]
  (every? (fn [f] (f name)) (:name validations)))

(defn test-name
  [name]
  (if (is-valid-name? name)
    (println "Nome válido")))

;; const operator = "+"
;; if (operator === "+" || operator === "-" || operator === "*" || operator === "/") {
;;     console.log("Operador válido")
;; }
(def valid-operators #{"+" "-" "*" "/"})

(defn is-valid-operator? [operator]
  (contains? valid-operators operator))


;; novo exemplo
;; function getDiscount(people) {
;;   let prices;
;;   if (people < 10) {
;;     price = 500
;;   } else if (people >= 10 && people < 25) {
;;     price = 350
;;   } else if (people >= 25 && people < 100) {
;;     price = 250
;;   } else {
;;     price = 200
;;   }
;;   return price
;; }
(def my-array-fn [{:function (fn [v] (and (>= v 0) (< v 10)))   :return 500}
                  {:function (fn [v] (and (>= v 10) (< v 25)))  :return 350}
                  {:function (fn [v] (and (>= v 25) (< v 100))) :return 250}
                  {:function (fn [v] (>= v 100))                :return 200}])



(defn get-discount-ret [people]
  (:return (get my-array-fn 0)))

(defn get-discount2-fn [people]
  ((:function (get my-array-fn 0)) people))

(defn get-discount [people]
  (some (fn [v] (if ((:function v) people) (:return v))) my-array-fn))


;; mais um exemplo
;; function setAccType(accType) {
;;   if (accType === "PLATINUM") {
;;     return "Platinum Customer"
;;   } else if (accType === "GOLD") {
;;     return "Gold Customer"
;;   } else if (accType === "SILVER") {
;;     return "Silver Customer"
;;   }
(def types {:PLATINUM "Platinum Customer"
            :GOLD "Gold Customer"
            :SILVER "Silver Customer"})

(def types2 {:PLATINUM (fn [] "Platinum Customer")
            :GOLD      (fn [] "Gold Customer")
            :SILVER    (fn [] "Silver Customer")})

(defn set-acc-type [type]
  ((keyword type) types))

(defn set-acc-type2 [type]
  (((keyword type) types2)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


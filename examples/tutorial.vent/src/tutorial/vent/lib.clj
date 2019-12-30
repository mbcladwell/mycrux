(ns tutorial.vent.lib
  (:require
    [tutorial.vent.db :as db]))

;; (defn favorites
;;   []
;;   [{:id "1"
;;     :text "A favorited tweet"
;;     :author {:name "John smith"}
;;     :username "john_smith"
;;     :favorite? true}
;;    {:id "2"
;;     :text "Another favorite tweet"
;;     :author {:name "Jane Smith"}
;;     :username "jane_smith"
;;     :favorite? true}])



(defn all
  []
  (get  (db/read) :vents) )

(defn favorites
  ;;supposed to be anonymous??
  []
  (filter :favorite?  (get (all) :vents)))


(defn followers
  [{:keys [username]}]
  {"john_smith"  {:name "John Smith"}
   "jane_smith"  {:name "Jane Smith"
                  :following? true}})

;;dev=> (followers "developer")
;;{"john_smith" {:name "John Smith"},
;; "jane_smith" {:name "Jane Smith", :following? true}}


;;(defn followers
;;  [username]
;;(select-keys (get (get (all) :users) username)  [:follows]))

;; (defn following
;;   [{:keys [username]}]
;;   {"jane_smith"
;;    {:name "Jane Smith"
;;     :following? true}})

(defn following
  [{:keys [username]}]
 ;; (select-keys (get (all) :users) ["john_smith"])
 ;; (get (get (get (all) :users) "developer") :follows)
 (select-keys (get (all) :users) (get (get (get (all) :users) username) :follows) ))


(defn toggle-favorite
  [{:keys [vent-id]}]
  (println "toggling favorite on" vent-id))

(defn- generate-id
  []
  (str (java.util.UUID/randomUUID)))

;; (defn add-vent
;;   [{:keys [text username]}]
;;   (println username
;;            "is venting about" text
;;            "with id" (generate-id)))

 (defn add-vent
   [{:keys [text]}]
 (db/store  (update (all) :vents (conj  {:id (generate-id) :username "developer"  :text text}))
   ))


(defn toggle-follow
  [{:keys [to-follow username]}]
  (println username "is following or unfollowing" to-follow))

(defn add-author
  []
  (let [all-users (get (all) :users)
        all-vents (get (all) :vents) ]
    (map #(assoc % :author (get (get all-users  (get % :username)) :name) )  all-vents)))


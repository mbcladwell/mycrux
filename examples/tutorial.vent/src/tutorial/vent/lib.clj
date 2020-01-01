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
  (let [all-users (get (db/read) :users)
        all-vents (get (db/read) :vents) ]
    (map #(assoc % :author  (get all-users (get % :username) ))   all-vents)))

;;    (map #(assoc % :author (get (get all-users  (get % :username)) :name) )  all-vents)))


(defn favorites
  ;;supposed to be anonymous??
  []
  (filter :favorite?  (all) ))


;; (defn followers
;;   [{:keys [username]}]
;;   {"john_smith"  {:name "John Smith"}
;;    "jane_smith"  {:name "Jane Smith"
;;                   :following? true}})

;;dev=> (followers "developer")
;;{"john_smith" {:name "John Smith"},
;; "jane_smith" {:name "Jane Smith", :following? true}}


;;select-keys on users, based on a keep on the :users which have username in their :follows.
;; (map #(keep % [:follows]) (map #(val %) (:users (db/read))))
;; (map #(select-keys (val %) [:name])    (:users (db/read)) )
;; (some #(= "b" %) ["d" "a" "b"])
;; (def a (map #(val %) (:users (db/read))))
;; (keep (some #()   (:follows  %))   a)

;; (map #(some (partial = "developer") (:follows %))  a)

(defn followers
 [{:keys [username]}]
(:follows (get (:users (db/read)) username) ))



(defn following
  [{:keys [username]}]
 ;; (select-keys (get (all) :users) ["john_smith"])
 ;; (get (get (get (all) :users) "developer") :follows)
 (select-keys (get (db/read) :users) (get (get (get (db/read) :users) username) :follows) ))


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
   [{:keys [text username]}]
   (db/store  (update (db/read) :vents conj {:id (generate-id) :username username  :text text}  ))
              )


(defn toggle-follow
  [{:keys [to-follow username]}]
  (println username "is following or unfollowing" to-follow))

(defn add-author
  []
  (let [all-users (get (db/read) :users)
        all-vents (get (db/read) :vents) ]
    (map #(assoc % :author (get (get all-users  (get % :username)) :name) )  all-vents)))

;;

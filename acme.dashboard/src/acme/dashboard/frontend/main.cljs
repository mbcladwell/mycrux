(ns ^:figwheel-hooks acme.dashboard.frontend.main)

(js/console.log "Hello, world")

;; This is called once
(defonce init
  (do (set! (.-innerHTML (js/document.getElementById "app"))
            "<p>Loaded dashboard!</p>
            <p>Edit <strong><code>src/acme/dashboard/frontend/main.cljs</code></strong> to change this message.</p>")
      true))

;; This is called every time you make a code change
(defn ^:after-load reload []
  (set! (.-innerText (js/document.getElementById "app")) "Hot Reloaded dashboard!"))

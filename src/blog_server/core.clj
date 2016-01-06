(ns blog-server.core
  (:require
   [liberator.core :as liber]
   [clojure.pprint :as pp]
   [compojure.core :as compj]
   [compojure.route :as route]
   [compojure.handler :as handler]
   [compojure.response :as response]
   [ring.middleware.params :as param]
   [ring.middleware.cors :as cors]
   [io.clojure.liberator-transit :as transit]
   [ring.middleware.transit :as ring-transit]
   [ring.adapter.jetty :refer [run-jetty]]
   [clojure.string :as str]
   [clojure.edn :as edn]
   [environ.core :refer [env]])
  (:gen-class)
  (:import java.io.File))

;; ======================
;; Persist
;; ======================

(def blog-file (env :blog-file))

(defn read-file [file]
  (ref (edn/read-string (slurp file))))

(defn save-file [data file append-mode]
  (with-open [f (clojure.java.io/writer file :append append-mode)]
    (pp/pprint @data f)))

(defn get-posts [file]
  (if (not (.exists (File. file)))
    (save-file (ref {}) file true))
  (read-file file))

(def blog-posts (get-posts blog-file))

;; ======================
;; Resources
;; ======================
(liber/defresource posts [userid]
  :available-media-types ["application/transit+json"
                          "application/transit+msgpack"
                          "application/json"]
  :handle-ok (fn [_] (get @blog-posts (keyword userid))))

;; ======================
;; Routes/Hndlers
;; ======================

(compj/defroutes main-routes
  (compj/GET "/posts/:userid" [userid] (posts userid))
  (route/not-found "<h1>Page not found</h1>"))

(def handler
  (-> main-routes
      param/wrap-params
      ring-transit/wrap-transit-params
      (cors/wrap-cors :access-control-allow-origin [#".*"]
                      :access-control-allow-methods [:get :post :options]
                      :access-control-allow-headers ["content-type"])))

;; ======================
;; Main
;; ======================

(def port (env :port))

(defn -main [& args]
  (run-jetty handler {:port port :join? false}))

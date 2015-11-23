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
   [clojure.string :as str])
  (:import java.io.File))

(def blog-posts
  {:ecallen
   {:dd6bff92-8e31-11e5-acb0-ffbf3d8c2695dd6bff92-8e31-11e5-acb0-ffbf3d8c2695
    {:title "Bright Paper Werewolves"
     :author "ECAllen"
     :post-timestamp "1447878296"
     :revision 0
     :last-update "1447878296"
     :text "c'mon polluted eyeballs
stop scouting out the field
jump up bright paper werewolves
and everybody everywhere

anyone can scratch
and anyone can win
so bring out another batch

they want to get out of here
but they can't find the exit
they cling to the cinema
and they can't find security
then they finally got recognized
so they left in obscurity and misery
-Guided By Voices, Under the Bushes, Under the Stars"}}})

(liber/defresource posts [userid]
  :available-media-types ["application/transit+json"
                          "application/transit+msgpack"
                          "application/json"]
  :handle-ok (fn [_] (get blog-posts (keyword userid))))

;; ======================
;; Routes
;; ======================

(compj/defroutes main-routes
  (compj/GET "/posts/:userid" [userid] (posts userid)))

(def handler
  (-> main-routes
      param/wrap-params
      ring-transit/wrap-transit-params
      (cors/wrap-cors :access-control-allow-origin [#".*"]
                      :access-control-allow-methods [:get :post :options]
                      :access-control-allow-headers ["content-type"])))

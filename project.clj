(defproject blog-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [liberator "0.14.0"]
                 [compojure "1.4.0"]
                 [io.clojure/liberator-transit "0.3.0"]
                 [ring-transit "0.1.4"]
                 [liberator "0.14.0"]
                 [ring-cors "0.1.7"]
                 [ring/ring-core "1.4.0"]]
  :ring {:handler blog-server.core/handler}
  :plugins [[lein-ring "0.8.11"]])

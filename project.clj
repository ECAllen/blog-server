(defproject blog-server "0.1.0-SNAPSHOT"
  :description "server for blog posts"
  :url "https://github.com/ECAllen/blog-server.git"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [liberator "0.14.0"]
                 [compojure "1.4.0"]
                 [io.clojure/liberator-transit "0.3.0"]
                 [ring-transit "0.1.4"]
                 [liberator "0.14.0"]
                 [ring-cors "0.1.7"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [juxt/dirwatch "0.2.3"]
                 [environ "1.0.2"]]
  :ring {:handler blog-server.core/handler}
  :main blog-server.core
  :uberjar-name "blog-server.jar"
  :profiles {:uberjar {:env {:production true}
                       :aot :all
                       :omit-source true}}
 :plugins [[lein-ring "0.9.7"]
           [lein-environ "1.0.2"]])

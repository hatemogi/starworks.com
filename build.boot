(set-env!
 :source-paths #{"src"}
 :resource-paths #{"res"}
 :dependencies '[[org.clojure/clojure "1.9.0"]
                 [hiccup "1.0.5"]
                 [instaparse "1.4.7"]])

(require '[starworks.boot :refer :all])

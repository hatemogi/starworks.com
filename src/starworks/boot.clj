(ns starworks.boot
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [boot.core :refer :all]
            [boot.task.built-in :as task]
            [boot.util :refer [info]]
            [starworks.view :as v]))

(defn convert [f in out]
  (io/make-parents out)
  (spit out (f (slurp in))))

(deftask cover-html
  "본문만 있는 걸 커버를 감싼다"
  []
  (let [tmp (tmp-dir!)]
    (with-pre-wrap fileset
      (let [파일들 (by-ext ["._html"] (input-files fileset))]
        (info (str "covering " (s/join ", " (map tmp-path 파일들)) "\n"))
        (doseq [body 파일들]
          (let [in-path  (tmp-path body)
                out-path (s/replace in-path #"\._html$" ".html")
                out-file (io/file tmp out-path)]
            (convert v/cover (tmp-file body) out-file)))
        (commit! (add-resource fileset tmp))))))

(deftask generate-html
  "HTML 생성"
  []
  (let [tmp (tmp-dir!)
        생성 (fn [파일명 내용] (spit (io/file tmp 파일명) 내용))
        파일셋 {"index._html" v/index-page
                "done.html" v/done-page
                "404._html" v/not-found}]
    (with-pre-wrap fileset
      (info (str "writing " (s/join ", " (keys 파일셋)) "\n"))
      (dorun (for [[파일명 생성함수] 파일셋]
               (생성 파일명 (생성함수))))
      (commit! (add-resource fileset tmp)))))

(deftask build
  "빌드 태스크"
  []
  (comp (generate-html)
        (cover-html)
        (task/sift :to-resource [#"CNAME"])
        (task/target :dir ["docs"])))

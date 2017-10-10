(ns starworks.markdown
  (:require [instaparse.core :refer [transform]]
            [tufdown.block :as block]
            [tufdown.span :as span]))

(defn- make-text-end-with-LF [text]
  (if (= \newline (last text))
    text
    (str text "\n")))

(defn- 분석 [텍스트]
  (some->> 텍스트
           make-text-end-with-LF
           block/parse
           (transform {:문장 span/parse})
           (transform {:링크텍스트 span/parse})))

(defn- 추출 [트리 태그]
  (some-> (filter #(= 태그 (first %)) 트리)
          first
          rest))

(def ^:private 텍스트추출
  (comp (partial apply str) 추출))

(defn- 변환 [트리]
  (let [교체 (fn [tag] (fn [& xs] (into [tag] xs)))]
    (transform {:문서     (교체 :main)
                :문단     (교체 :p)
                :큰제목   (교체 :h1)
                :작은제목 (교체 :h2)
                :굵게     (교체 :b)
                :기울임   (교체 :em)
                :코드     (교체 :code)
                :일반목록 (교체 :ul)
                :숫자목록 (교체 :ol)
                :항목     (교체 :li)
                :인용     (교체 :blockquote)
                :일반링크 (fn [& 나머지]
                          (into [:a {:href (텍스트추출 나머지 :주소)}] (추출 나머지 :문장)))
                :자동링크 (fn [& xs] [:a {:href xs} xs])
                :아이콘 (fn [& xs] [:i {:class (str "fa fa-" (first xs))}])}
               트리)))

(defn- 큰제목나누기
  "[:큰제목] 기준으로 그룹지어 나눈다"
  [[첫태그 & 나머지]]
  (assert (= :main 첫태그))
  (reverse (reduce (fn [[섹션 & 이전 :as 결과] [태그 & 나머지 :as 블럭]]
                     (if (= :h1 태그)
                       (cons [블럭] 결과)
                       (cons (conj 섹션 블럭) 이전)))
                   () 나머지)))

(defn- 섹션변환 [섹션]
  (let [큰제목 (first (추출 섹션 :h1))]
    [:section (if 큰제목 {:id 큰제목} {})
     (into [:div] 섹션)]))

(defn- 문장추출 [트리]
  (if (vector? 트리)
    (let [[tag & xs] 트리]
        (if (= :문장 tag)
          xs
          [(into [tag] (mapcat 문장추출 xs))]))
    트리))

(defn 문서변환 [텍스트]
  (->> 텍스트
       분석
       변환
       문장추출
       first
       큰제목나누기
       (map 섹션변환)))

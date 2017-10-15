(ns starworks.view
  (:require [hiccup.core :refer [h html]]
            [hiccup.page :refer [html5 include-css include-js]]
            [starworks.markdown :as md]))

(defn layout
  "HTML 기본 레이아웃"
  [& contents]
  (let [설명 "스타벅스 와이파이 연결러"
        타이틀 "스타웍스 - 스타벅스 WiFi 연결러"]
    (html5 [:head
            [:meta {:charset "utf-8"}]
            [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
            [:meta {:name "description" :content 설명}]
            [:meta {:name "author" :content "김대현"}]

            [:meta {:property "og:site_name" :content "스타웍스"}]
            [:meta {:property "og:url" :content "http://xn--9t4ba803ac1m.com"}]
            [:meta {:property "og:title" :content 타이틀}]
            [:meta {:property "og:locale" :content "ko_KR"}]
            [:meta {:property "og:description" :content 설명}]
            [:meta {:property "fb:app_id" :content "1587341957982820"}]
            [:meta {:property "og:image" :content "http://xn--9t4ba803ac1m.com/img/256@2x.png"}]
            [:meta {:property "og:image:type" :content "image/png"}]
            [:meta {:property "og:image:width" :content "1200"}]
            [:meta {:property "og:image:height" :content "630"}]

            [:meta {:name "twitter:card" :content "summary"}]
            [:meta {:name "twitter:site" :content "@starworksapp"}]
            [:meta {:name "twitter:title" :content 타이틀}]
            [:meta {:name "twitter:image" :content "xn--9t4ba803ac1m.com/img/256@2x.png"}]
            [:meta {:name "twitter:description" :content 설명}]

            [:title 타이틀]]
           (map include-css ["css/bootstrap.min.css"
                             "css/font-awesome.min.css"
                             "css/starworks.css"])
           [:body contents]
           (map include-js ["js/starworks.js"]))))

(defn cover
  "커버 페이지 레이아웃"
  [& contents]
  (layout [:nav
           (for [주제 ["스타웍스" "다운로드" "사용법" "만든이"]]
             [:a {:href (str "/#" 주제)} 주제])]
          contents
          [:footer
           [:span "Copyright (c) 2017 "
            [:a {:href "https://medium.com/@hatemogi"} "Daehyun Kim"]
            ". All rights reserved."]]))

(defn index-page
  []
  (let [문서 (md/문서변환 (slurp "src/index.md"))]
    (html (into [:main
                 [:section#스타웍스
                  [:div
                   [:h1 "스타웍스"]
                   [:p
                    [:img {:src "img/starworks.svg" :width "300px"}]]]]
                 [:section#다운로드
                  [:div
                   [:h1 "다운로드"]
                   [:a.btn.btn-large.btn-primary {:href "/downloads/starworks-0.1.0.dmg"} "다운로드"]]]]
                문서))))

(defn done-page
  []
  (html [:main [:section#완료 "연결됐어요"]]))

(defn not-found
  []
  (html [:main [:section#404 [:div [:h1 "Not Found"]
                              [:a {:href "/"} "첫페이지로"]]]]))

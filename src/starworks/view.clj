(ns starworks.view
  (:require [hiccup.core :refer [h html]]
            [hiccup.page :refer [html5 include-css include-js]]
            [starworks.markdown :as md]))

(defn layout
  "HTML 기본 레이아웃"
  [& contents]
  (html5 [:head
          [:meta {:charset "utf-8"}]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
          [:title "스타웍스 - 스타벅스 WiFi 연결러"]
          (map include-css ["css/bootstrap.min.css"
                            "css/font-awesome.min.css"
                            "css/starworks.css"])]
         [:body contents
          (map include-js ["js/starworks.js"])]))

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
    (html (into [:main] 문서))))

(defn done-page
  []
  (html [:main [:section#완료 "연결됐어요"]]))

(defn not-found
  []
  (html [:main [:section#404 [:div [:h1 "Not Found"]
                              [:a {:href "/"} "첫페이지로"]]]]))

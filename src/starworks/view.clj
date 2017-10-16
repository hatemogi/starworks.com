(ns starworks.view
  (:require [clojure.string :as s]
            [hiccup.core :refer [h html]]
            [hiccup.page :refer [html5 include-css include-js]]
            [starworks.markdown :as md]))

(defn layout
  "HTML 기본 레이아웃"
  [& contents]
  (let [설명 (str
              "스타벅스에서 무료로 제공하는 와이파이에 연결할 때, 번거로운 입력 절차를 자동으로 "
              "진행해주는 macOS 애플리케이션입니다.")
        타이틀 "스타웍스 - 스타벅스 WiFi 연결러"]
    (html5 [:head
            [:meta {:charset "utf-8"}]
            [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
            [:meta {:name "description" :content 설명}]
            [:meta {:name "author" :content "김대현"}]

            [:meta {:property "og:site_name" :content "스타웍스"}]
            [:meta {:property "og:type" :content "website"}]
            [:meta {:property "og:url" :content "http://xn--9t4ba803ac1m.com"}]
            [:meta {:property "og:title" :content 타이틀}]
            [:meta {:property "og:locale" :content "ko_KR"}]
            [:meta {:property "og:description" :content 설명}]
            [:meta {:property "fb:app_id" :content "1587341957982820"}]
            [:meta {:property "og:image:url" :content "http://xn--9t4ba803ac1m.com/img/256@2x.png"}]
            [:meta {:property "og:image:type" :content "image/png"}]
            [:meta {:property "og:image:width" :content "256"}]
            [:meta {:property "og:image:height" :content "256"}]

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
           (map include-js ["js/starworks.js"])
           [:script {:async true :src "https://www.googletagmanager.com/gtag/js?id=UA-108134101-1"}])))

(defn cover
  "커버 페이지 레이아웃"
  [& contents]
  (layout [:nav
           (for [주제 ["스타웍스" "사용법" "만든이" "개발기"]]
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
                   [:p.text-center
                    [:img {:src "img/256@2x.png" :width 256 :height 256}]]
                   [:p "스타벅스에서 무료 와이파이를 쓸 때 거치는 "
                    "번거로운 동의 절차를 자동으로 진행해주는 macOS용 앱입니다."]
                   [:p.text-center
                    [:a.btn.btn-large.btn-primary {:href "downloads/starworks-0.1.0.dmg"}
                     [:i.fa.fa-lg.fa-download]
                     " 다운로드 v0.1.0"]]]]
                 [:section#사용법
                  [:div
                   [:h1 "사용법"]
                   [:p.text-center
                     [:img {:src "img/dmg.png" :width 428 :height 279}]]
                   [:p "다운로드한 .dmg 파일을 열어서 '스타웍스'를 응용프로그램 폴더로 옭기고, "
                    "실행합니다. 앱이 실행된 상태에서 스타벅스에서 와이파이에 연결되면, 평소대로 동의 절차를 "
                    "거치고 이후 자동으로 연결해 줍니다."]
                   [:p "항상 편리하게 쓰시려면 메뉴에 있는 '로그인 시 자동실행'을 체크해둡니다."]]]]
                문서))))

(defn done-page
  []
  (html [:main [:section#완료
                [:div
                 [:h1 "연결 성공"]
                 [:p.text-center "자동으로 스타벅스 WiFi에 연결했습니다."]]]]))

(defn not-found
  []
  (html [:main [:section#404 [:div [:h1 "Not Found"]
                              [:a {:href "/"} "첫페이지로"]]]]))

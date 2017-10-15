"use strict";

window.addEventListener("load", function() {
  document.querySelectorAll("a[href*='downloads']").forEach(function(a) {
    let match = a.href.match(/starworks-(.*)\.dmg/)
    let version = "unknown"
    if (match && match[1]) version = match[1]
    a.addEventListener("click", function() {
      gtag && gtag('event', 'download', {"version": version})
      return true
    })
  });
});

window.dataLayer = window.dataLayer || [];
function gtag(){dataLayer.push(arguments);}
gtag('js', new Date());
gtag('config', 'UA-108134101-1');

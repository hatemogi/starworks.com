"use strict";

window.addEventListener("load", function() {
  document.querySelectorAll("a[href*='downloads']").forEach(function(a) {
    const match = a.href.match(/starworks-(.*)\.dmg/);
    let version = "unknown";
    if (match && match[1]) version = match[1]
    a.addEventListener("click", function(e) {
      e.preventDefault();
      gtag('event', 'download', {
        "version": version,
        "event_callback": function() {
          a.onclick();
        }
      })
    })
  })
})

window.dataLayer = window.dataLayer || [];
function gtag(){dataLayer.push(arguments);}
gtag('js', new Date());
gtag('config', 'UA-108134101-1');

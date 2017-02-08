function streamMusic() {
  Amplitude.init({
    "songs": [
        {
                "call_sign": "WHMD",
                "station_name": "The Monkey",
                "location": "Terre Haute, IN",
                "url": "http://icecast.wmhdradio.org:8000/wmhd",
                "live": true
        }
    ],
    "autoplay": true
  });
}  

function updateNowPlaying() {
  console.log("NowPlaying Updated");
  $.get("https://dj.wmhdradio.org/api/live-info/", function(data) {
    document.getElementById("song-title").innerHTML = data.current.name;
    var timeTilNext = Date.parse(data.next.starts) - (new Date()) - 18000000;
    setTimeout(updateNowPlaying, timeTilNext);
  })
  .done(function() {
      console.log("request made");
  })
  .fail(function() {
      console.log("request failed");
  });
}

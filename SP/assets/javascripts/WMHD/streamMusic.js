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
  $.get("http://dj.wmhdradio.org/api/live-info", function(data) {
    document.getElementById("song-title").innerHTML = data.previous.name;
  })
  .done(function() {
      console.log("request made");
  })
  .fail(function() {
      console.log("request failed");
  });
}

//var npRef = new Firebase(FIREBASE_REF).child("nowPlaying");
//npRef.on("value", function(snapshot) {
//  var main = snapshot.val();
//  document.getElementById("title").innerHTML = main.title + " by " + main.artist;
//}, function (errorObject) {
//  console.log("The read failed: " + errorObject.code);
//});

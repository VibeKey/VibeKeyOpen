$(document).ready(function() {
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
});  

//function updateNowPlaying() {
//  var npRef = new Firebase(FIREBASE_REF).child("nowPlaying");
//  npRef.on("value", function(snapshot) {
//    var main = snapshot.val();
//    document.getElementById("title").innerHTML = main.title + " by " + main.artist;
//  }, function (errorObject) {
//    console.log("The read failed: " + errorObject.code);
//  });
//}



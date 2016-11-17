function streamMusic() {
  Amplitude.init({
    "songs": [
        {
                "call_sign": "WHMD",
                "station_name": "WHMD Online Radio",
                "location": "Terre Haute, IN",
                "url": "http://149.56.18.84:2000/wmhd",
                "live": true
        }
    ],
    "autoplay": true
  });
}

$(document).ready(function() {

    $("#jquery_jplayer_1").jPlayer({
        ready: function(event) {
            $(this).jPlayer("setMedia", {
				title: "WMHD",
				oga: "http://airtime-test.reshall.rose-hulman.edu:8000/wmhd"
            });
        },
        swfPath: "http://jplayer.org/latest/dist/jplayer",
        supplied: "oga",
		wmode: "window",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true,
		remainingDuration: true,
		toggleDuration: true
    });
});  

function updateNowPlaying() {
  var npRef = new Firebase(FIREBASE_REF).child("nowPlaying");
  npRef.on("value", function(snapshot) {
    var main = snapshot.val();
    document.getElementById("jp-title").innerHTML = main.title + " by " + main.artist;
  }, function (errorObject) {
    console.log("The read failed: " + errorObject.code);
  });
}



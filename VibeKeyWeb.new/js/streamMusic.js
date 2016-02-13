function streamMusic() {
  Amplitude.init({
    "songs": [
        {
                "call_sign": "WHMD",
                "station_name": "WHMD Online Radio",
                "location": "Terre Haute, IN",
                "url": "http://137.112.138.182:8000/wmhd",
                "live": true
        }
    ],
    "autoplay": true,
    //"use_visualizations": true
  });

  // Amplitude.registerVisualization( MichaelBromleyVisualization, {
  //   width: '314',
  //   height: '314'
  // });
}

function updateNowPlaying() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var npRef = fireRef.child('prod').child("nowPlaying");
  npRef.on("value", function(snapshot) {
    var main = snapshot.val();
    document.getElementById("curPlayingTitle").innerHTML = main.title;
    document.getElementById("curPlayingArtist").innerHTML = main.artist;
    //document.getElementById("curPlayingTags").innerHTML="Tags: ";
  }, function (errorObject) {
    console.log("The read failed: " + errorObject.code);
  });
}

function nextSong() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var controls = fireRef.child("prod").child("controls");
  var command = createCommand(true, "nextSong", {});
  controls.set(command);
}

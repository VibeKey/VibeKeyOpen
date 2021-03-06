function streamMusic() {
  Amplitude.init({
    "songs": [
        {
                "call_sign": "WHMD",
                "station_name": "WHMD Online Radio",
                "location": "Terre Haute, IN",
                "url": "http://149.56.18.84:2000",
                "live": true
        }
    ],
    "autoplay": true
  });
}

function updateNowPlaying() {
  var npRef = new Firebase(FIREBASE_REF).child("nowPlaying");
  npRef.on("value", function(snapshot) {
    var main = snapshot.val();
    document.getElementById("curPlayingTitle").innerHTML = main.title;
    document.getElementById("curPlayingArtist").innerHTML = main.artist;
    //document.getElementById("curPlayingTags").innerHTML="Tags: ";
  }, function (errorObject) {
    console.log("The read failed: " + errorObject.code);
  });
}

function pressPlayerButton(){
  var playButton = document.getElementById("playButton");
  var pauseButton = document.getElementById("pauseButton");
  var playImage = document.getElementById("playImage");
  var pauseImage = document.getElementById("pauseImage");

  if (playButton.style["display"] != "none"){
    // Removes the play button and adds the pause button.
    playButton.style["display"] = "none";
    pauseButton.style["display"] = null;

    playImage.style["display"] = null;
    pauseImage.style["display"] = "none";
  } else {
    // Removes the play button and adds the pause button.
    pauseButton.style["display"] = "none";
    playButton.style["display"] = null;

    pauseImage.style["display"] = null;
    playImage.style["display"] = "none";
  }
}
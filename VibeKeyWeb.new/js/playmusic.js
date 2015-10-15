Amplitude.init({
    "songs": [
        {
                "call_sign": "WHMD",
                "station_name": "WHMD Online Radio",
                "location": "Terre Haute, IN",
                "url": "http://137.112.138.229:8000/wmhd",
                "live": true
        }
    ],
  "autoplay": true
});

var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");

var npRef = fireRef.child("nowPlaying");
npRef.on("value", function(snapshot) {
  var main = snapshot.val();
  document.getElementById("curPlayingTitle").innerHTML=" Now playing: " + main.title + " by " + main.artist;
  document.getElementById("curPlayingGenre").innerHTML=" Genre: " + main.genre;
  //document.getElementById("curPlayingTags").innerHTML="Tags: ";
}, function (errorObject) {
  console.log("The read failed: " + errorObject.code);
});

var nextSongRef = fireRef.child("nextSong");
function nextSong(){
  nextSongRef.set(true);
}
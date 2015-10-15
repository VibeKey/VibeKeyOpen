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
var genresRef = fireRef.child("genres");
var curGenreRef = fireRef.child("curGenre");
var nextSongRef = fireRef.child("nextSong");
/*
genresRef.on("value", function(snapshot) {
  document.getElementById("genresList").innerHTML="";
  snapshot.forEach(function(data) {
    document.getElementById("genresList").innerHTML = document.getElementById("genresList").innerHTML + "<button type='button' class='btn btn-default' onclick='genreClicked(this)' id='" + data.val() + "'>" + data.val() + "</button>";
  });
});
*/
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
function genreClicked(elmnt){
  curGenreRef.set(elmnt.id);
}
function clearGenre(){
  curGenreRef.set("none");
}
function nextSong(){
  nextSongRef.set(true);
}
var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
var genreListRef = fireRef.child("songs").child("genreList");
var songListRef = fireRef.child("songs").child("allSongs");
var controls = fireRef.child("controls");

//populate genre drop down in schedule genre
genreListRef.on("value", function(snapshot) {
  var genresDropdown = document.getElementById("genresList");
  snapshot.forEach(function(data) {
    var genre = data.val();
    var option = document.createElement("option");
    option.text = option.value = genre;
    // genresDropdown.appendChild(option);
    $('.genresList').append(option);
  });
});

//populate song drop down in schedule song
songListRef.once("value", function(snapshot) {
  var genresDropdown = document.getElementById("songsList");
  snapshot.forEach(function(childSnapshot) {
    var songs = childSnapshot.val();
    var songName = songs.title;
    var option = document.createElement("option");
    option.text = option.value = songName;
    $('.songsList').append(option);
  });
});

function scheduleGenre(list){
  // var genresDropdown = document.getElementById("genresList");
  // var selectedGenre = genresDropdown.options[genresDropdown.selectedIndex].value;
  // console.log(list.selectedIndex);
  // console.log(list.value);
  var selectedGenre = list.value;
  var command = createCommand(false, "setGenre", {"genre" : selectedGenre});
  controls.set(command);
}

function scheduleSong(list){
  var selectedSong = list.value;
  var command = createCommand(false, "setSong", {"song" : selectedSong});
  controls.set(command);
}
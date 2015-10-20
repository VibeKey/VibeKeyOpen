var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
var genreListRef = fireRef.child("songs").child("genreList");
var songListRef = fireRef.child("songs").child("allSongs");
var playlistListRef = fireRef.child("playlists");
var controls = fireRef.child("controls");

//populate genre drop down in schedule genre
genreListRef.on("value", function(snapshot) {
  //var genresDropdown = document.getElementById("genresList");
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
  //var playsDropdown = document.getElementById("playsList");
  snapshot.forEach(function(childSnapshot) {
    var songs = childSnapshot.val();
    var songName = songs.title;
    var songPath = songs.path;
    var option = document.createElement("option");
    option.text = songName;
    option.value = songPath;
    $('.songsList').append(option);
  });
});

//populate playlist drop down in schedule song
playlistListRef.once("value", function(snapshot) {
  //var songsDropdown = document.getElementById("songsList");
  snapshot.forEach(function(childSnapshot) {
    var lists = childSnapshot.val();
    var listName = lists.name;
    var option = document.createElement("option");
    option.text = option.value = listName;
    $('.playsList').append(option);
  });
});

function scheduleGenre(list){
  // var genresDropdown = document.getElementById("genresList");
  // var selectedGenre = genresDropdown.options[genresDropdown.selectedIndex].value;
  // console.log(list.selectedIndex);
  // console.log(list.value);
  var selectedGenre = list.value;
  var command = createCommand(true, "setGenre", {"genre" : selectedGenre});
  controls.set(command);
}

function scheduleSong(list){
  var selectedSongName = list.options[list.selectedIndex].text;
  var selectedSongPath = list.value;
  var command = createCommand(true, "addToFrontOfQueue", {"songPath" : selectedSongPath, "song" : selectedSongName});
  controls.set(command);
}

function scheduleList(list) {
  var selectedList = list.options[list.selectedIndex].text;
  var command = createCommand(true, "setPlaylist", {"playlist" : selectedList});
  controls.set(command);
}
function nextSong() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var command = createCommand(true, "nextSong", {});
  controls.push(command);
}

function populateSongList1() {
  var songList = document.getElementById("songList1");
  var allsongsRef = new Firebase(FIREBASE_REF).child("songs").child("allSongs");
  allsongsRef.on("value", function(snapshot) {
    var main = snapshot.val();
    snapshot.forEach(function(childSnapshot) {
      var id = childSnapshot.key();
      var childData = childSnapshot.val();
      var songAlbum = childData.album;
        var songName = childData.title;
        var songBand = childData.artist;
        var songGenre = childData.genre;
        var songPath = childData.path;
        var votes = childData.netVotes;
        
        var option = document.createElement('option');
        option.id = songPath;
        option.text = songName + ' by ' + songBand + " (" + songGenre + ")" + "(votes: "+ votes + ")";
        songList.appendChild(option);
    });
  }, function (errorObject) {
    console.log("The read failed: " + errorObject.code);
  });
}

function playSong(){
  var allSongsList = document.getElementById("songList1");
  var selectedSong = allSongsList.value;
  var controls = new Firebase(FIREBASE_REF).child("controls/command");
  var command = createCommand(true, "setGenre", {"genre" : selectedSong});
  controls.push(command);
}

function populateGenreList() {
  var genresList = document.getElementById("streamgenresList");
  var genresListRef = new Firebase(FIREBASE_REF).child("songs/genreList");
  genresListRef.on("value", function(snapshot) {
    genresList.innerHTML = "";
      snapshot.forEach(function(data) {
          var id = data.key();
          var genre = data.val();
          var option = document.createElement('option');
          option.id = option.value = id;
          option.text =  genre;
          genresList.appendChild(option);
      });
  });
}

function playGenre(){
  var genresList = document.getElementById("streamgenresList");
  var selectedGenre = genresList.value;
  var controls = new Firebase(FIREBASE_REF).child("controls/command");
  var command = createCommand(true, "setGenre", {"genre" : selectedGenre});
  controls.push(command);
}

function populatePlaylistList() {
  var playslistList = document.getElementById("streamplayslistList");
  var playslistRef = new Firebase(FIREBASE_REF).child("playlists");
  playslistRef.on("value", function(snapshot) {
    playslistList.innerHTML = "";
      snapshot.forEach(function(data) {
          var id = data.key();
          var playlist = data.val();
          var name = playlist.name;
          var option = document.createElement('option');
          option.id = option.value = id;
          option.text =  name;
          playslistList.appendChild(option);
      });
  });
}

function playPlaylist() {
  var playslistList = document.getElementById("streamplayslistList");
  var selectedPlaylist = playslistList.value;
  var controls = new Firebase(FIREBASE_REF).child("controls/command");
  var command = createCommand(true, "setGenre", {"genre" : selectedPlaylist});
  controls.push(command);
}
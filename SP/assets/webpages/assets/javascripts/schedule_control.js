function nextSong() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var command = createCommand(true, "nextSong", {});
  controls.push(command);
}

function scheduleSong(){
  var allSongsList = document.getElementById("allSongsList");
  var selectedSong = allSongsList.value;
  var controls = new Firebase(FIREBASE_REF).child("controls/command");
  var command = createCommand(true, "setGenre", {"genre" : selectedSong});
  controls.push(command);
}

function populateGenreList() {
  var genresList = document.getElementById("genresList");
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

function scheduleGenre(){
  var genresList = document.getElementById("genresList");
  var selectedGenre = genresList.value;
  var controls = new Firebase(FIREBASE_REF).child("controls/command");
  var command = createCommand(true, "setGenre", {"genre" : selectedGenre});
  controls.push(command);
}

function populatePlaylistList() {
  var playslistList = document.getElementById("playslistList");
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

function schedulePlaylist() {
  var playslistList = document.getElementById("playslistList");
  var selectedPlaylist = playslistList.value;
  var controls = new Firebase(FIREBASE_REF).child("controls/command");
  var command = createCommand(true, "setGenre", {"genre" : selectedPlaylist});
  controls.push(command);
}

function schedule() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var playMode = document.getElementsByClassName('playModeList')[1].value;
  var repeatMode = document.getElementsByClassName('repeatModeList')[1].value;
  var startTime = document.getElementsByClassName('startTime')[1].value;
  var endTime = document.getElementsByClassName('endTime')[1].value;
  var DJName = document.getElementsByClassName('djNameList')[1].value;
  var genre = document.getElementsByClassName('scheduleGenresList')[1].value;
  var playlist = document.getElementsByClassName('schedulePLList')[1].value;

  var command = createCommand(false, "addToSchedule", {"playMode":playMode, "repeatMode":repeatMode, 
    "startTime":startTime, "endTime":endTime, "DJName":DJName, "genre":genre, "playlist":playlist});
  controls.push(command);
}
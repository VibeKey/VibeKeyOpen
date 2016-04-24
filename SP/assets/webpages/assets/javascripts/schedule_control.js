function populateScheduleGenreList() {
  var genresList = document.getElementById("scheduleGenresList");
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

function populateSchedulePlaylistList() {
  var playslistList = document.getElementById("schedulePLList");
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

function schedule() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var playMode = document.getElementById('playModeList').value;
  var repeatMode = document.getElementById('repeatModeList').value;
  var startTime = document.getElementById('startTime').value;
  var endTime = document.getElementById('endTime').value;
  var DJName = document.getElementById('djNameList').value;
  var genre = document.getElementById('scheduleGenresList').value;
  var playlist = document.getElementById('schedulePLList').value;

  var command = createCommand(false, "addToSchedule", {"playMode":playMode, "repeatMode":repeatMode, 
    "startTime":startTime, "endTime":endTime, "DJName":DJName, "genre":genre, "playlist":playlist});
  controls.push(command);
}
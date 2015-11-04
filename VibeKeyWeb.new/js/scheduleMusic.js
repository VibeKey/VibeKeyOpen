function populateCalenderLists() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var genreListRef = fireRef.child("songs").child("genreList");
  var songListRef = fireRef.child("songs").child("allSongs");
  var playlistListRef = fireRef.child("playlists");
  var controls = fireRef.child("controls");

  //populate genre drop down in schedule genre
  genreListRef.on("value", function(snapshot) {
    snapshot.forEach(function(data) {
      var genre = data.val();
      var option = document.createElement("option");
      option.text = option.value = genre;
      $('.genresList').append(option);
    });
  });

  //populate song drop down in schedule from all songs
  songListRef.once("value", function(snapshot) {
    snapshot.forEach(function(childSnapshot) {
      var songs = childSnapshot.val();
      var songName = songs.title;
      var songPath = songs.path;
      var option = document.createElement("option");
      option.text = songName;
      option.value = songPath;
      $('.allSongsList').append(option);
    });
  });

  //populate playlist drop down in schedule song
  playlistListRef.once("value", function(snapshot) {
    snapshot.forEach(function(childSnapshot) {
      var lists = childSnapshot.val();
      var listName = lists.name;
      var option = document.createElement("option");
      option.text = option.value = listName;
      $('.playsList').append(option);
    });
  });
}

function scheduleGenre(list){
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var controls = fireRef.child("controls");
  var selectedGenre = list.value;
  var command2 = createCommand(true, "setGenre", {"genre" : selectedGenre});
  controls.set(command2);
}

function scheduleSong(list){
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var controls = fireRef.child("controls");
  var selectedSongPath = list.value;
  var command = createCommand(true, "addToFrontOfQueue", {"songPath" : selectedSongPath});
  controls.set(command);
}

function scheduleList(list) {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var controls = fireRef.child("controls");
  var selectedList = list.options[list.selectedIndex].text;
  var command2 = createCommand(true, "setPlaylist", {"playlist" : selectedList});
  controls.set(command2);
}

function schedule() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var controls = fireRef.child("controls");
  var playMode = document.getElementsByClassName('playModeList')[1].value;
  var repeatMode = document.getElementsByClassName('repeatModeList')[1].value;
  var startTime = document.getElementsByClassName('startTime')[1].value;
  var endTime = document.getElementsByClassName('endTime')[1].value;
  var DJName = document.getElementsByClassName('djNameList')[1].value;
  var genre = document.getElementsByClassName('scheduleGenresList')[1].value;
  var playlist = document.getElementsByClassName('schedulePLList')[1].value;

  var command = createCommand(false, "addToSchedule", {"playMode":playMode, "repeatMode":repeatMode, 
    "startTime":startTime, "endTime":endTime, "DJName":DJName, "genre":genre, "playlist":playlist});
  controls.set(command);
}
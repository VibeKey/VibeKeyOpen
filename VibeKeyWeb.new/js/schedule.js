function populateSchedule() {
  //populate upcoming programs
  var upcomingProgramsList = document.getElementsByClassName("upcomingProgramsList")[1];

  var upcomingProgramsRef = new Firebase(FIREBASE_REF).child("schedule");
  upcomingProgramsRef.on("value", function(snapshot) {
    upcomingProgramsList.innerHTML = "";
    snapshot.forEach(function(data) {
      var scheduledItem = data.val();
      var djname = scheduledItem.djname;
      var li = document.createElement('li');
      li.className = 'scheduledProgram';
      li.innerHTML = djname;
      var br = document.createElement('br');
        $(".upcomingProgramsList").append(li);
        $(".upcomingProgramsList").append(br);
    });
  });

  //populate upcoming songs
  var upcomingSongsList = document.getElementsByClassName("upcomingSongsList")[1];

  var upcomingSongsRef = new Firebase(FIREBASE_REF).child("queue");
  upcomingSongsRef.on("value", function(snapshot) {
    upcomingSongsList.innerHTML = "";
    snapshot.forEach(function(data) {
      var scheduledItem = data.val();
      var title = scheduledItem.title;
      var artist = scheduledItem.artist;
      var li = document.createElement('li');
      li.className = 'scheduledSong';
      li.innerHTML = title + " by " + artist;
      var br = document.createElement('br');
        $(".upcomingSongsList").append(li);
        $(".upcomingSongsList").append(br);
    });
  });
}

function populateCalenderLists() {
  var fireRef = new Firebase(FIREBASE_REF);
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
  songListRef.on("value", function(snapshot) {
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
  playlistListRef.on("value", function(snapshot) {
    snapshot.forEach(function(childSnapshot) {
      var lists = childSnapshot.val();
      var listName = lists.name;
      var option = document.createElement("option");
      option.text = option.value = listName;
      $('.playsList').append(option);
    });
  });
}

function nextSong() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var command = createCommand(true, "nextSong", {});
  controls.push(command);
}

function scheduleGenre(list){
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var selectedGenre = list.value;
  var command2 = createCommand(true, "setGenre", {"genre" : selectedGenre});
  controls.push(command2);
}

function scheduleSong(list){
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var selectedSongPath = list.value;
  var command = createCommand(true, "addToFrontOfQueue", {"songPath" : selectedSongPath});
  controls.push(command);
}

function scheduleList(list) {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var selectedList = list.options[list.selectedIndex].text;
  var command2 = createCommand(true, "setPlaylist", {"playlist" : selectedList});
  controls.push(command2);
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
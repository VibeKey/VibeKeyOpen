function createPlaylist(){
	var name = document.getElementById('playlistTitle').value;
	if (name != "") {
		var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
		var songs = document.getElementById("playlist").getElementsByClassName("pl_song");
		var songPath;
		var thesongs = [];
		for (var i = 0; i < songs.length; i++) {
			songPath = songs[i].id;
			thesongs = thesongs.concat([songPath]);
		};
		var command = createCommand(true, "addPlaylist", {"name" : name, "songs" : thesongs});
		controls.push(command);
		window.alert("Playlist " + name + " created.");
  } else {
    	window.alert("Please enter playlist name.");
   }
}

function populateSongList2() {
  var songList = document.getElementById("songList2");
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

function addToPlaylist() {
	var search_result = document.getElementById('songList2');
	var name = search_result.options[search_result.selectedIndex].value;
	var path = search_result.options[search_result.selectedIndex].id;
	var playlist = document.getElementById('playlist');
	var item = document.createElement('li');
	item.id = path;
	item.className = "pl_song"
	item.innerHTML = name;
	playlist.appendChild(item);
}
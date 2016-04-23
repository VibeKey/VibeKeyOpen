function createPlaylist(){
	var name = document.getElementById('playlistTitle').value;
	if (name != null) {
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
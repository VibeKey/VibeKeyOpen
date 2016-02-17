function createPlaylist(){
	var name = prompt("Please enter playlist name", "Default List");
	if (name != null) {
		var controls = new Firebase(FIREBASE_REF).child("controls");
		var songs = document.getElementById("playlist").getElementsByClassName("song");
		var songPath;
		var thesongs = [];
		for (var i = 0; i < songs.length; i++) {
			songPath = songs[i].getElementsByClassName("path")[0].textContent;
			thesongs = thesongs.concat([songPath]);
		};
		var command = createCommand(true, "addPlaylist", {"name" : name, "songs" : thesongs});
		controls.push(command);
    }
}

function clickBottomButton() {
	var playsongsImg = document.getElementById("playsongsImg");
	var playlist = document.getElementById("playsongsBorder");

	if(playsongsImg.style["display"] != "none") {
		console.log("hi");
		playsongsImg.style["display"] = "none";
		playlist.style["display"] = null;
	} else {
		playsongsImg.style["display"] = null;
		playlist.style["display"] = "none";
	}
}
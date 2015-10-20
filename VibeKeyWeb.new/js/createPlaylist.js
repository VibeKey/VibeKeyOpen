function createPlaylist(){
	var songs = document.getElementById("playlist").getElementsByClassName("song");
	var songName;
	var songBand;
	for (var i = 0; i < songs.length; i++) {
		songName = songs[i].getElementsByClassName("name")[0].textContent;
		songBand = songs[i].getElementsByClassName("band")[0].textContent;
		console.log(songName);
		console.log(songBand);
	};
}
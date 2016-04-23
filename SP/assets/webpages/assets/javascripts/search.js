function search() {
	var searchWord = document.getElementById('search_key').value;
	var allsongsRef = new Firebase(FIREBASE_REF).child("songs").child("allSongs");
	var search_result = document.getElementById('search_result');
	search_result.innerHTML = "";
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
		  	if(songAlbum.indexOf(searchWord) > -1 || songName.indexOf(searchWord) > -1 || songBand.indexOf(searchWord) > -1 || songGenre.indexOf(searchWord) > -1) {
		  		var option = document.createElement('option');
		  		option.id = songPath;
		  		option.text = songName + ' by ' + songBand;
		  		search_result.appendChild(option);
		  	}
		});
	}, function (errorObject) {
		console.log("The read failed: " + errorObject.code);
	});
}

function addToPlaylist() {
	var search_result = document.getElementById('search_result');
	var name = search_result.options[search_result.selectedIndex].value;
	var path = search_result.options[search_result.selectedIndex].id;

	var playlist = document.getElementById('playlist');
	var item = document.createElement('li');
	item.id = path;
	item.className = "pl_song"
	item.innerHTML = name;
	playlist.appendChild(item);
}
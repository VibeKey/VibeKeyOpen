function populateSearchLists() {
	var fireRef = new Firebase(FIREBASE_REF);
	var byArtistRef = fireRef.child("songs").child("artistList");
	var byGenreRef = fireRef.child("songs").child("genreList");

	//populate genre drop down in search by genre
	byGenreRef.on("value", function(snapshot) {
	  snapshot.forEach(function(data) {
	    var genre = data.val();
	    var option = document.createElement("option");
	    option.text = option.value = genre;
	    $('.searchByGenreList').append(option);
	  });
	});

	//populate artist drop down in search by artist
	byArtistRef.on("value", function(snapshot) {
	  snapshot.forEach(function(data) {
	    var artist = data.val();
	    var option = document.createElement("option");
	    option.text = option.value = artist;
	    $('.searchByArtistList').append(option);
	  });
	});
}


function searchByArtist(list) {
	var allsongsRef = new Firebase(FIREBASE_REF).child("songs").child("allSongs");
	var selectedArtist = list.value;
	var searchTab = document.getElementById('searchSongs');
	searchTab.innerHTML = "";

	allsongsRef.on("value", function(snapshot) {
		snapshot.forEach(function(selectedGenreData) {
			var id = selectedGenreData.key();
			var childData = selectedGenreData.val();
			var songName = childData.title;
			var songBand = childData.artist;
			var songPath = childData.path;
			var votes = childData.netVotes;
			var song = {
				"id" : [id],
				"name" : [songName],
				"artist" : [songBand],
				"path" : [songPath],
				"votes" : [votes]
			};
			if(songBand == selectedArtist) {
				addSongToTab(song, searchTab);
			}
		});
	});
}

function searchByGenre(list) {
	var allsongsRef = new Firebase(FIREBASE_REF).child("songs").child("allSongs");
	var selectedGenre = list.value;
	var searchTab = document.getElementById('searchSongs');
	searchTab.innerHTML = "";

	allsongsRef.on("value", function(snapshot) {
		snapshot.forEach(function(selectedGenreData) {
			var id = selectedGenreData.key();
			var childData = selectedGenreData.val();
			var songGenre = childData.genre;
			var songName = childData.title;
			var songBand = childData.artist;
			var songPath = childData.path;
			var votes = childData.netVotes;
			var song = {
				"id" : [id],
				"name" : [songName],
				"artist" : [songBand],
				"path" : [songPath],
				"votes" : [votes]
			};
			if(songGenre == selectedGenre) {
				addSongToTab(song, searchTab);
			}
		});
	});
}

function showAllSongs() {
	var allsongsRef = new Firebase(FIREBASE_REF).child("songs").child("allSongs");
	var searchTab = document.getElementById('searchSongs');
	searchTab.innerHTML = "";

	allsongsRef.once("value", function(snapshot) {
		var main = snapshot.val();
		snapshot.forEach(function(childSnapshot) {
			var id = childSnapshot.key();
		 	var childData = childSnapshot.val();
		  	var songName = childData.title;
		  	var songBand = childData.artist;
		  	var songPath = childData.path;
		  	var votes = childData.netVotes;
		  	var song = {
		  		"id" : [id],
		 		"name" : [songName],
		  		"artist" : [songBand],
		 		"path" : [songPath],
		  		"votes" : [votes]
		  	};
		  	addSongToTab(song, searchTab);
	});
	}, function (errorObject) {
		console.log("The read failed: " + errorObject.code);
	});
}

function search() {
	var searchWord = document.getElementsByClassName('searchByName')[1].value;
	var allsongsRef = new Firebase(FIREBASE_REF).child("songs").child("allSongs");
	var searchTab = document.getElementById('searchSongs');
	searchTab.innerHTML = "";

	allsongsRef.once("value", function(snapshot) {
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
		  	var song = {
		  		"id" : [id],
		 		"name" : [songName],
		  		"artist" : [songBand],
		 		"path" : [songPath],
		  		"votes" : [votes]
		  	};
		  	if(songAlbum.indexOf(searchWord) > -1 || songName.indexOf(searchWord) > -1 || songBand.indexOf(searchWord) > -1 || songGenre.indexOf(searchWord) > -1) {
		  		addSongToTab(song, searchTab);
		  	}
	});
	}, function (errorObject) {
		console.log("The read failed: " + errorObject.code);
	});
}
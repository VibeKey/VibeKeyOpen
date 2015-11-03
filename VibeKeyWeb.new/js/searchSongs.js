var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
var byArtistRef = fireRef.child("songs").child("byArtist");
var byGenreRef = fireRef.child("songs").child("byGenre");

//populate genre drop down in search by genre
byGenreRef.on("value", function(snapshot) {
  snapshot.forEach(function(data) {
    var genre = data.child('genre').val();
    var option = document.createElement("option");
    option.text = option.value = genre;
    $('.searchByGenreList').append(option);
  });
});

//populate artist drop down in search by artist
byArtistRef.on("value", function(snapshot) {
  snapshot.forEach(function(data) {
    var artist = data.child('artist').val();
    var option = document.createElement("option");
    option.text = option.value = artist;
    $('.searchByArtistList').append(option);
  });
});

function searchByArtist(list) {
	var selectedArtist = list.value;
	var searchTab = document.getElementById('searchSongs');
	searchTab.innerHTML = "";

	byArtistRef.on("value", function(snapshot) {
		snapshot.forEach(function(data) {
			if(selectedArtist == data.child('artist').val()) {
				data.forEach(function(selectedArtistData) {
					var id = selectedArtistData.key();
				 	var childData = selectedArtistData.val();
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
				  	if(songBand != null) {
				  		addSongToTab(song, searchTab);
					}
				});
			}
		});
	});
}

function searchByGenre(list) {
	var selectedGenre = list.value;
	var searchTab = document.getElementById('searchSongs');
	searchTab.innerHTML = "";

	byGenreRef.on("value", function(snapshot) {
		snapshot.forEach(function(data) {
			if(selectedGenre == data.child('genre').val()) {
				data.forEach(function(selectedGenreData) {
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
				  	if(songBand != null) {
				  		addSongToTab(song, searchTab);
					}
				});
			}
		});
	});
}

function showAllSongs() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var songsRef = fireRef.child("songs");
	var allsongsRef = songsRef.child("allSongs");
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
	var searchWord = document.getElementById('searchByName').value;
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var songsRef = fireRef.child("songs");
	var allsongsRef = songsRef.child("allSongs");
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
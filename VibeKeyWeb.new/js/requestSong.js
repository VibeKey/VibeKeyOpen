function requestSong(request) {
		var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
		var requestSongsRef = fireRef.child("requestedSongs");
		
		var requestSong = requestSongsRef.push();
		requestSong.set(request.title);
}
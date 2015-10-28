function requestSong(request) {
		var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
		var controls = fireRef.child("controls");
		var path = request.path;
		var command = createCommand(false, "requestSong", {"songPath" : path});
		controls.set(command);
}
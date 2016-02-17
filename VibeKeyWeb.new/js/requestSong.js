function requestSong(request) {
		var fireRef = new Firebase("https://vibekey-open.firebaseio.com/prod");
		var controls = fireRef.child("controls");
		var path = request.id;
		var command = createCommand(true, "requestSong", {"songPath" : path});
		controls.push(command);
}
function requestSong(request) {
		var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
		var path = request.id;
		var command = createCommand(true, "requestSong", {"songPath" : path});
		controls.push(command);
}
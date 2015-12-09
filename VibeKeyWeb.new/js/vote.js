function upVote() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var npRef = fireRef.child("nowPlaying");
	var controls = fireRef.child("controls");
	npRef.on("value", function(snapshot) {
		var main = snapshot.val();
		var path = main.path;
		var command = createCommand(true, "upvoteSong", {"songPath" : path});
		controls.set(command);
	});
}

function downVote() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var npRef = fireRef.child("nowPlaying");
	var controls = fireRef.child("controls");
	npRef.on("value", function(snapshot) {
		var main = snapshot.val();
		var path = main.path;
		var command = createCommand(true, "downvoteSong", {"songPath" : path});
		controls.set(command);
	});
}
function upVote() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var controls = fireRef.child("controls").child("command");

	var command = createCommand(true, "upvoteSong", {"songPath" : path});
	controls.push(command);
}

function downVote() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var controls = fireRef.child("controls").child("command");

	var command = createCommand(true, "downvoteSong", {"songPath" : path});
	controls.push(command);
}

function containUser(uid) {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var npRef = fireRef.child("prod").child("nowPlaying");
	var peopleVoteRef = npRef.child("peopleVote");

	var found = false;

	peopleVoteRef.once("value", function(snapshot) {
		snapshot.forEach(function(childsnapshot) {
			var childData = childsnapshot.val();
			var theuid = childData.uid;
			console.log(uid);
			console.log(theuid);
			if (uid == theuid) {
				found = true;
			}
		});
	});
	console.log(found);
	return found;
}
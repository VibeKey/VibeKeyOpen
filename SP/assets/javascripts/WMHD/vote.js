function upVote() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var npRef = fireRef.child("prod").child("nowPlaying");
	var peopleVoteRef = npRef.child("peopleVote");
	var controls = new Firebase(FIREBASE_REF).child("controls").child("command");

	peopleVoteRef.onAuth(function(authData) {
		if (authData) {
			if(!containUser(authData.uid)){
				// add to peopleLike list
			  	var newLikeRef = peopleVoteRef.push();
			  	newLikeRef.set({"uid":authData.uid, "vote": "like"});

			  	//actual upvote
				npRef.on("value", function(snapshot) {
					var main = snapshot.val();
					var path = main.path;
					var command = createCommand(true, "upvoteSong", {"songPath" : path});
					controls.push(command);
				});
			} else {
				alert("You have already voted.");
			}
		} else {
		  alert("You must login to vote.")
		}
	});
}

function downVote() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var npRef = fireRef.child("prod").child("nowPlaying");
	var peopleVoteRef = npRef.child("peopleVote");
	var controls = new Firebase(FIREBASE_REF).child("controls").child("command");

	peopleVoteRef.onAuth(function(authData) {
		if (authData) {
			if(!containUser(authData.uid)){
				// add to peopleLike list
			  	var newDislikeRef = peopleVoteRef.push();
			  	newDislikeRef.set({"uid":authData.uid, "vote": "dislike"});

			  	//actual upvote
				npRef.on("value", function(snapshot) {
					var main = snapshot.val();
					var path = main.path;
					var command = createCommand(true, "downvoteSong", {"songPath" : path});
					controls.push(command);
				});
			} else {
				alert("You have already voted.");
			}
		} else {
		  alert("You must login to vote.")
		}
	});
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
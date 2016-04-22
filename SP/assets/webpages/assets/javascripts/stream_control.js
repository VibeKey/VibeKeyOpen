function nextSong() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var command = createCommand(true, "nextSong", {});
  controls.push(command);
}
function schedule() {
  var controls = new Firebase(FIREBASE_REF).child("controls").child("command");
  var playMode = document.getElementsByClassName('playModeList')[1].value;
  var repeatMode = document.getElementsByClassName('repeatModeList')[1].value;
  var startTime = document.getElementsByClassName('startTime')[1].value;
  var endTime = document.getElementsByClassName('endTime')[1].value;
  var DJName = document.getElementsByClassName('djNameList')[1].value;
  var genre = document.getElementsByClassName('scheduleGenresList')[1].value;
  var playlist = document.getElementsByClassName('schedulePLList')[1].value;

  var command = createCommand(false, "addToSchedule", {"playMode":playMode, "repeatMode":repeatMode, 
    "startTime":startTime, "endTime":endTime, "DJName":DJName, "genre":genre, "playlist":playlist});
  controls.push(command);
}
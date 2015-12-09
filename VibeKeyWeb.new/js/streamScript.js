var isPlaying = true;
var firstPlay = true;
	
function pressPlayerButton(){
	var player = document.getElementById("player");
	var audio = document.getElementById("audioStream");
	if (!isPlaying){
		// Resumes audio.
		audio.play();
		isPlaying = true;
		
		// Resumes animations.
		//for (i=1; i <= 5; i++) {
		//	var name = "line" + i;
		//	document.getElementById(name).style["-webkit-animation-play-state"] = "running";
		//	document.getElementById(name).style["animation-play-state"] = "running";
		//}
		
		// Removes the play button and adds the pause button.
		document.getElementById("playButton").style["display"] = "none";
		document.getElementById("pauseButton").style["display"] = null;
	} else {
		// Pauses audio.
		audio.pause();
		isPlaying = false;
		
		// Pauses Animations
		//for (i=1; i <= 5; i++) {
		//	var name = "line" + i;
		//	document.getElementById(name).style["-webkit-animation-play-state"] = "paused";
		//	document.getElementById(name).style["animation-play-state"] = "paused";
		//}
		
		// Removes the play button and adds the pause button.
		document.getElementById("pauseButton").style["display"] = "none";
		document.getElementById("playButton").style["display"] = null;
	}
}

function failed(e) {
	// audio playback failed - show a message saying why
	// to get the source of the audio element use $(this).src
	switch (e.target.error.code) {
		case e.target.error.MEDIA_ERR_ABORTED:
			alert('You aborted the video playback.');
			break;
		case e.target.error.MEDIA_ERR_NETWORK:
			alert('A network error caused the audio download to fail.');
			break;
		case e.target.error.MEDIA_ERR_DECODE:
			alert('The audio playback was aborted due to a corruption problem or because the video used features your browser did not support.');
			break;
		case e.target.error.MEDIA_ERR_SRC_NOT_SUPPORTED:
			alert('The video audio not be loaded, either because the server or network failed or because the format is not supported.');
			break;
		default:
			alert('An unknown error occurred.');
			break;
		}
}
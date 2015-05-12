var isPlaying = false;
	
function pressPlayerButton(){
	var player = document.getElementById("player");
	if (!isPlaying){
		$(document.body).append("<audio id='audioStream' autoplay preload=none source src='http://wmhd-test.csse.rose-hulman.edu:8000/radio' ></audio>");
		isPlaying = true;
		
		var pauseButton = document.createElement('img');
		pauseButton.src = "http://wmhd2.rose-hulman.edu/pausebutton.png";
		pauseButton.className = "playerButton";
		pauseButton.id = "pauseButton";
		
		player.removeChild(document.getElementById("playButton"));
		player.appendChild(pauseButton);
	} else {
		$("#audioStream").remove();
		isPlaying = false;
		
		var playButton = document.createElement('img');
		playButton.src = "http://wmhd2.rose-hulman.edu/playbutton.png";
		playButton.className = "playerButton";
		playButton.id = "playButton";
		
		player.removeChild(document.getElementById("pauseButton"));
		player.appendChild(playButton);
	}
}
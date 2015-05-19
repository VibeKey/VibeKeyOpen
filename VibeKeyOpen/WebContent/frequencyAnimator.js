$(document).ready(function() {
	var audio = new Audio();
	audio.id = "audioStream";
	audio.src = "http://wmhd-test.csse.rose-hulman.edu:8000/radio";
	audio.autoplay = true;
	audio.preload = "none";
	audio.crossOrigin = "anonymous";
	
	document.body.appendChild(audio);
	audio.addEventListener("canplay", function() {
		initAudio(audio);
	});
});

function initAudio(audio) {
	window.AudioContext = window.AudioContext || window.webkitAudioContext;

    window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
        window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
	
	var context = new AudioContext();
	var analyser = context.createAnalyser(); 
	analyser.smoothingTimeConstant = 0.85;
	
	var source = context.createMediaElementSource(audio); 
	source.connect(analyser);
	analyser.connect(context.destination);
	
	analyser.fftSize = 32;
	var bufferLength = analyser.frequencyBinCount;
	var dataArray = new Uint8Array(bufferLength);
	
	var visualisation = document.getElementById("bounceAnimation");
	var barSpacingPercent = 100 / bufferLength;
    for (var i = 0; i < bufferLength; i++) {
		var div = document.createElement('div');
		div.style["left"] = i * barSpacingPercent + "%";
		div.style["width"] = barSpacingPercent + "%";
		div.style["height"] = "5%";
		div.className = "lineDance";
		
    	visualisation.appendChild(div);
    }
    var bars = visualisation.children;
	
	function draw(){
		requestAnimationFrame(draw);
		analyser.getByteFrequencyData(dataArray);
		
		for (i = 0; i < bars.length; i++) {
			bars[i].style["height"] = (dataArray[i] * -1) + '%';
			if(dataArray[i] != 0) {
                alert(dataArray[i]);
            }
		};
	}
	
	draw();
}


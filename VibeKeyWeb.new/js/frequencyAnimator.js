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
	analyser.minDecibels = -90;
	analyser.maxDecibels = -10;
	
	var source = context.createMediaElementSource(audio); 
	source.connect(analyser);
	analyser.connect(context.destination);
	
	analyser.fftSize = 64;
	var bufferLength = analyser.frequencyBinCount;
	var dataArray = new Uint8Array(bufferLength);
	var canvas = document.querySelector('.bounce');
	var canvasCtx = canvas.getContext("2d");
	
	var intendedWidth = document.querySelector('.playhead').clientWidth;
	canvas.setAttribute('width',intendedWidth);
	
	canvasCtx.clearRect(0, 0, canvas.width, canvas.height);
	
	function draw() {
		  drawVisual = requestAnimationFrame(draw);

		  analyser.getByteFrequencyData(dataArray);

		  canvasCtx.fillStyle = 'white';
		  canvasCtx.fillRect(0, 0, canvas.width, canvas.height);

		  var barWidth = (canvas.width / bufferLength) * 2.5;
		  var barHeight;
		  var x = 0;

		  for(var i = 0; i < bufferLength; i++) {
			barHeight = dataArray[i];

			canvasCtx.fillStyle = 'rgba(153, 000, 000, 0.05)';
			canvasCtx.fillRect(x,canvas.height-barHeight/2,
				barWidth,barHeight/2);

			x += barWidth + 1;
		  }
		};
	
	draw();
}


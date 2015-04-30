$(document).ready(function() {
	
	window.onload = function(){
	
		var height = (($(window).height()-155) / (10.5));
		var heightHalf = height/2;
		var container = $(".tabLinkContainer");
	 
		$(".tabLinkContainer").css('height',height);
		//$(".tabLinkContainer").css('line-height',heightHalf);
			
		var arrows = document.querySelectorAll(".arrow-right");
	 
		$(".arrow-right").css('border-top-width',.5*height);
		$(".arrow-right").css('border-top-style',"solid");
		$(".arrow-right").css('border-top-color',"transparent");
		$(".arrow-right").css('border-bottom',.52*height);
		$(".arrow-right").css('border-bottom-style',"solid");
		$(".arrow-right").css('border-bottom-color',"transparent");
		$(".arrow-right").css('border-left',.33*height);
		$(".arrow-right").css('border-left-style',"solid");
		$(".arrow-right").css('border-left-color',"rgb(147,147,147)");
	
		var createSong = function(awesomeSong,awesomeArtist,likes){
			var songItem = $('<div></div');
			songItem.attr("class","songItem");
			songItem.attr("draggable","true");
			var votingPart = $('<span></span>');
			votingPart.attr("class","votingPart");
			var basicPart = $('<span></span>');
			basicPart.attr("class","basicPart");
			
			var up = $('<div></div>');
			up.attr("class","songUpArrow");
			var down = $('<div></div>');
			down.attr("class","songDownArrow");
			var likeNumber = $('<div>' + likes + '</div>');
			likeNumber.attr("class","likeNumber ");
			
			var songName = $('<div>' + awesomeSong + '</div>');
			songName.attr("class","songName");
			var bandName = $('<div>' + awesomeArtist + '</div>');
			bandName.attr("class","bandName");
			
			$(".playlist").append(songItem);
			
			songItem.append(votingPart);
			songItem.append(basicPart);
			
			votingPart.append(up);
			votingPart.append(likeNumber);
			votingPart.append(down);
			
			basicPart.append(songName);
			basicPart.append(bandName);
		}
			createSong("Shake It Off", "Taylor Swift", "9000+");
			createSong("Roses", "Black Eyed Peas", "3000");
	
	}
	
});
	
	
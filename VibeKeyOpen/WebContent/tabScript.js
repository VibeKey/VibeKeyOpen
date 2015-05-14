$(document).ready(function() {
	window.addEventListener("resize", changeTabs); 
	window.onload = changeTabs;
});
	
function changeTabs(){
	
		var height = (($(window).height()-155) / (10.5));
		var heightHalf = height/2;
		var container = $(".tabLinkContainer");
	 
		$(".tabLinkContainer").css('height',height + 'px');
		$(".tabLinkContainer").css('line-height',height + 'px');
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
	}
	
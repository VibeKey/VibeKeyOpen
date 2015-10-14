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
		$(".tabLinkImg").css('height',height + 'px');
		$(".tabLinkImg").css('width',height + 'px');
		//$(".tabLinkContainer").css('line-height',heightHalf);
			
		var arrows = document.querySelectorAll(".arrow-right");
	 
		$(".arrow-right").css('border-top-width',.5*height);
		$(".arrow-right").css('border-bottom-width',.52*height);
		$(".arrow-right").css('border-left-width',.33*height);
	}
	
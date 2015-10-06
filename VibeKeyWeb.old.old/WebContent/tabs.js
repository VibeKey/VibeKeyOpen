window.onload = main;

/* The currently selected tab */
	var activeTab = null;

	function main() {
		var tabs = document.getElementsByClassName("single-tab");
		for(var i = 0; i < tabs.length; i++) {
			tabs[i].onclick = selectTab;
		}
	}
			
	
	function selectTab() {
		if(this === activeTab) {
			activeTab = null;
			collapseTabs();
		}
		else {
			activeTab = this;
			expandTabs(this);
		}
	}
	
	/* Collapses the open tab frame */
	function collapseTabs(){
		var content = document.getElementById("tab-content");
		content.style.display = "none";
		//TODO: set width and animation duration
		/*$('#tab-content').animate({width: "0px"}, 1000, function(){
			$(this).hide();
		});*/
	}
	
	/* Expands the closed tab frame */
	function expandTabs(selected){
		var content = document.getElementById("tab-content");
		content.style.display = "inline";
		/*$('#tab-content').show();
		//TODO: set width and animation duration
		$('#tab-content').animate({width: "500px"}, 1000, function(){
			$(this).show();
		});*/
	}
	/*
	function animate(target, targetWidth, targetHeight, duration){
		var tInterval = duration / 10;
		var wInterval = (targetWidth - target.width) / tInterval;
		var hInterval = (targetHeight - target.height) / tInterval;
		var timer = setInterval(move(target, wInterval, hInterval), 10);
	}
	
	function move(target, width, height) {
		target.style.width += width;
		target.style.height += height;
	}*/
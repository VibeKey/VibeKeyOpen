var first = true;

function makeTab(tabName) {
	buildTab(tabName);
	
	var span = document.getElementById(tabName + "Span");
	var arrow = document.getElementById(tabName + "Arrow");
	
	$('#' + tabName).sidr({
		name: tabName + 'Func',
		source: '#' + tabName + 'Tab',
		renaming: false,
		onOpen: function(){
			span.style["width"] = "75%";
			span.style["background-color"] = "rgb(153, 000, 000)";
			arrow.style["border-left-color"] = "rgb(153, 000, 000)";
			
			if (first) {
				document.dispatchEvent(new UIEvent("resize",
					{ bubbles: true, cancelable: false }));
				first = false;
			}
		},
		onClose: function(){
			span.style["width"] = null;
			span.style["background-color"] = null;
			arrow.style["border-left-color"] = null;
		},
		body:document.getElementById("stuffBody")
	});
}

function buildTab(tabName) {
	// Build the physical tab.
	// Make the link.
	var a = document.createElement('a');
	a.id = tabName;
	a.href = "#" + tabName + "Func";
	
	// Make the container.
	var contain = document.createElement('div');
	contain.className = "tabLinkContainer";
	a.appendChild(contain);
	
	// Make the image container.
	var imgContain = document.createElement('div');
	imgContain.className = "tabLink";
	imgContain.id = tabName + "Span";
	contain.appendChild(imgContain);
	
	// Make the image.
	var img = document.createElement('img');
	img.className = "tabLinkImg";
	img.src = "images/" + tabName + ".png";
	imgContain.appendChild(img);
	
	// Create the arrow.
	var arrow = document.createElement('div');
	arrow.className = "arrow-right";
	arrow.id = tabName + "Arrow";
	contain.appendChild(arrow);
	
	// Append it to the container on the side.
	document.getElementById("container").appendChild(a);
}

$(document).ready(function() {
	// Defines the tabs.
	var tabs = ["search", "news", "soundboard", "calendar",
				"djChat", "audience", "settings"]
					
	// Creates the tabs.
	for (i = 0, len = tabs.length; i < len; i++) {
		makeTab(tabs[i]);
	}
		
	// Makes the hover functionality.
	$('.tabLinkContainer').hover(function(){
		$(this).addClass('tabLinkContainerHover');
	}, function() {
		$(this).removeClass('tabLinkContainerHover');
	});
});
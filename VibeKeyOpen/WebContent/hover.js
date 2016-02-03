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
		}
	});
}

function buildTab(tabName) {
	var a = document.createElement('a');
	a.id = tabName;
	a.href = "#" + tabName + "Func";
	
	var contain = document.createElement('div');
	contain.className = "tabLinkContainer";
	a.appendChild(contain);
	
	var imgContain = document.createElement('div');
	imgContain.className = "tabLink";
	imgContain.id = tabName + "Span";
	contain.appendChild(imgContain);
	
	var img = document.createElement('img');
	img.className = "tabLinkImg";
	img.src = "/images/" + tabName + ".png";
	imgContain.appendChild(img);
	
	var arrow = document.createElement('div');
	arrow.className = "arrow-right";
	arrow.id = tabName + "Arrow";
	contain.appendChild(arrow);
	
	document.getElementById("container").appendChild(a);
}

$(document).ready(function() {
		//makes the search tab pull out
		var tabs = ["search", "news", "soundboard", "calendar",
						"djChat", "audience", "settings"]
		for (i = 0, len = tabs.length; i < len; i++) {
			makeTab(tabs[i]);
		}
		
		$('.tabLinkContainer').hover(function(){
			$(this).addClass('tabLinkContainerHover');
		}, function() {
			$(this).removeClass('tabLinkContainerHover');
		});
	});
	
	
	
function trig() {
	
}
var button = "playlist";

// Code minhnc
// Found at: https://gist.github.com/minhnc/2333095
function registerEventListener(obj, params) {
	if ( typeof obj._eventListeners == 'undefined' ) {
		obj._eventListeners = [];	
	}
	
	obj.addEventListener(params.event, params.callback);
	
	var eventListeners = obj._eventListeners;
	eventListeners.push(params);
	obj._eventListeners = eventListeners;
	
	//Ti.API.info( JSON.stringify(obj._eventListeners) );
}

function unRegisterAllEventListeners(obj) {
	if ( typeof obj._eventListeners == 'undefined' || obj._eventListeners.length == 0 ) {
		return;	
	}
	
	for(var i = 0, len = obj._eventListeners.length; i < len; i++) {
		var e = obj._eventListeners[i];
		obj.removeEventListener(e.event, e.callback);
	}
 
	obj._eventListeners = [];
}

// Begin original code.
// Upload button functionality.
function clickUpload() {
	if (button != "up") {
		var oldButton = button;
		button = "up";
		collapseAndExpand(document.getElementById(oldButton), 
			document.getElementById(button));	
	} else {
		var oldButton = button;
		button = "playlist";
		collapseAndExpand(document.getElementById(oldButton), 
			document.getElementById(button));
	}
	
	// Swap between the down and normal.
	if (oldButton != "playlist") 
		document.getElementById(oldButton + "Button").innerHTML = oldButton;
	if (button != "playlist") 
		document.getElementById(button + "Button").innerHTML = "v";
}

function clickList() {
	if (button != "list") {
		var oldButton = button;
		button = "list";
		collapseAndExpand(document.getElementById(oldButton), 
			document.getElementById(button));
	} else {
		var oldButton = button;
		button = "playlist";
		collapseAndExpand(document.getElementById(oldButton), 
			document.getElementById(button));
	}
	
	// Swap between the down and normal.
	if (oldButton != "playlist") 
		document.getElementById(oldButton + "Button").innerHTML = oldButton;
	if (button != "playlist") 
		document.getElementById(button + "Button").innerHTML = "v";
}

function collapseAndExpand(prev, next) {
	var body = document.getElementById("playSongs");
	
	// Hide the scrollbar.
	body.style["overflow-y"] = "hidden";
	
	function listenForCollapse() { listenPlay(body, prev, next); }
		
	// Register event listeners.
	registerEventListener(body, {event: "animationend", callback: listenForCollapse});
	registerEventListener(body, {event: "webkitAnimationEnd", callback: listenForCollapse});
			
	// Add the collapse animations.
	body.style["animation-name"] = "collapse";
	body.style["-webkit-animation-name"] = "collapse";
}

function listenPlay(body, prev, next) {
	// Remove the listeners.
	unRegisterAllEventListeners(body);
	
	// Makes the previous item disappear without killing it.
	prev.style["display"] = "none";
	
	function listenForExp() { listenForExpansion(body); }
	
	// Add listeners for the expand end.
	registerEventListener(body, {event:"animationend", callback: listenForExp});
	registerEventListener(body, {event:"webkitAnimationEnd", callback: listenForExp});
	
	// Begin expand animation.
	body.style["animation-name"] = "expand";
	body.style["-webkit-animation-name"] = "expand";
	
	// Displays the next item.
	next.style["display"] = null;
}

function listenForExpansion(body) {
	// Changes the body and border back to normal after animations are over.
	body.style["overflow-y"] = "auto";
	
	
	unRegisterAllEventListeners(body);
}
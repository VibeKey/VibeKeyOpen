
//this JavaScript file is written in mostly JavaScript with a little bit of jQuery thrown in
//Its main purpose is to create all elements related to songs displayed on the website and to append them wherever they need to be

function createSong(id, name, artist) {
	var result = new Object();
	result["id"] = id;
	result["name"] = name;
	result["artist"] = artist;
	
	// Call for this data.
	result["votes"] = 50;
	
	return result;
}

function addSongToTab(song, tab) {
	// Create overall layout.
	var listItem = document.createElement('li');
	var songItem = document.createElement('div');
	var containBox = document.createElement('div');
	var containInfo = document.createElement('div');
	var votingItem = document.createElement('span');
	var basicItem = document.createElement('span');
	var buttonItem = document.createElement('span');
	
	// Add draggable things.
	listItem.className = "sortableli";
	songItem.className = "song";
	containBox.className = "containBox";
	votingItem.className = "voting";
	basicItem.className = "basic";
	buttonItem.className = "button";
	
	listItem.appendChild(songItem);
	//this is the code that makes the song objects draggable using jQuery UI
	$('.sortable').sortable();
	$('.sortable').disableSelection();
	
	songItem.appendChild(containBox);
	containBox.appendChild(containInfo);
	containInfo.appendChild(votingItem);
	containInfo.appendChild(basicItem);
	containInfo.appendChild(buttonItem);
	
	// Create all voting items.
	var up = document.createElement('div');
	var number = document.createElement('div');
	var down = document.createElement('div');
	
	up.className = "upArrow";
	number.className = "number";
	down.className = "downArrow";
	
	number.innerHTML = song["votes"];
	
	votingItem.appendChild(up);
	votingItem.appendChild(number);
	votingItem.appendChild(down);
	
	// Create all basic song items.
	var name = document.createElement('div');
	var band = document.createElement('div');
	
	name.className = "name";
	band.className = "band";
	
	name.innerHTML = song["name"];
	band.innerHTML = song["artist"];
	
	basicItem.appendChild(name);
	basicItem.appendChild(band);
	
	// Add all the buttons.
	var info = document.createElement('span');
	var add = document.createElement('span');
	var del = document.createElement('span');
	
	info.className = "circle";
	add.className = "circle";
	del.className = "circle";
	
	info.innerHTML = "<img id='" + song["name"]
		+ "InfoButton' src='/images/info_circle.png' style='width:24px;height:24px;'>";
	add.innerHTML = "<img id='" + song["name"]
		+ "PlusButton' src='/images/add_circle.png' style='width:24px;height:24px;'>";
	
	var delButton = document.createElement('img');
	delButton.className = "img";
	delButton.src = '/images/trash.png';
	delButton.id = song["name"] + "DelButton";
	
	var xButton = document.createElement('img');
	xButton.className = "img";
	xButton.src = '/images/x_circle.png';
	xButton.id = song["name"] + "XButton";
	
	if (tab == document.getElementById("playlist")) {
   		add.style["display"] = "none";
		delButton.style["display"] = "none";
	} else {
		xButton.style["display"] = "none";
	}
	
	del.appendChild(delButton);
	del.appendChild(xButton);
		
	
	buttonItem.appendChild(info);
	buttonItem.appendChild(add);
	buttonItem.appendChild(del);
	
	// Create functionality.
	add.onclick = function addButtonFunctionality() {
		var play = document.getElementById("playlist");
		
		// Remove and append.
		add.style["display"] = "none";
		tab.removeChild(listItem);
		play.appendChild(listItem);
		
		// Change the remove function to reflect the new parent.
		del.onclick = function remove() {
			removeMore(play, listItem, containBox, containInfo);
		}
		
		// Change the button look.
		delButton.style["display"] = "none";
		xButton.style["display"] = null;
		
		// Do server call to add to playlist.
	}
	
	del.onclick = function remove() {
		removeMore(tab, listItem, containBox, containInfo);
	}
	
	// Make info box that comes down when the info button is clicked on
	var infoBox = document.createElement('div');
	infoBox.className = "infoBox";
	
	info.onclick = function info() {
		if (infoBox.parentNode == songItem) {
			infoBox.addEventListener("animationend", function listen() {
				listener(infoBox, listen);
			});
			infoBox.addEventListener("webkitAnimationEnd", function listen() {
				listener(infoBox, listen);
			});
			
			infoBox.style["animation-name"] = "moveIn";
			infoBox.style["-webkit-animation-name"] = "moveIn";
		} else {
			infoBox.innerHTML = "Some words!";
		
			songItem.appendChild(infoBox);
		}
	}
	
	// Append to body for now.
	tab.appendChild(listItem);
	
	// Change font size to fit.
	
	function listenForResize(){
		changeText(basicItem, name, band, song, tab);
	}
	registerEventListener(window, {event: "resize", callback: listenForResize});
	registerEventListener(songItem, {event: "onload", callback: listenForResize});
	
	
}

function changeText(basicItem, name, band, song, tab) {
	var parentWidth;
	parentWidth = basicItem.offsetWidth;
	
	var nameSize = name.scrollWidth;
	var leftover = nameSize - parentWidth;
	if (leftover > 0) {
		name.style["transition-property"] = "text-indent";
		name.style["transition-duration"] = song["name"].length / 2 + "s";
		
		name.onmouseover = function() {
			name.style["text-indent"] = "-" + leftover + "px";
		};
		name.onmouseout = function() {
			name.style["text-indent"] = null;
		};
		
	}
	
	var bandSize = band.scrollWidth;
	var leftoverBand = bandSize - parentWidth;
	if (leftoverBand > 0) {
		band.style["transition-property"] = "text-indent";
		band.style["transition-duration"] = song["artist"].length / 2 + "s";
		
		band.onmouseover = function() {
			band.style["text-indent"] = "-" + leftoverBand + "px";
		};
		band.onmouseout = function() {
			band.style["text-indent"] = null;
		};
		
	}
}

function listener(infoBox, listen) {
	infoBox.parentNode.removeChild(infoBox);
	infoBox.removeEventListener("animationend", listen);
	infoBox.removeEventListener("webkitAnimationEnd", listen);
	
	infoBox.style["animation-name"] = "moveOut";
	infoBox.style["-webkit-animation-name"] = "moveOut";
}

function removeMore(parent, listItem, 
					containBox, containInfo) {
	containInfo.style["display"] = "none";

	var con = document.createElement('div');
	var desc = document.createElement('div');
	var but = document.createElement('div');
	var yes = document.createElement('span');
	var no = document.createElement('span');
		
	containBox.appendChild(con);
		
	con.className = "confirm";
	but.className = "button";
	yes.className = "circle";
	no.className = "circle";
		
		
	if (parent == document.getElementById("playlist")) {
		desc.innerHTML = "Are you sure you want to remove this song from the playlist?";
	} else {
		desc.innerHTML = "Are you sure you want to remove this song from the database?";
	}
	yes.innerHTML = "Yes.";
	no.innerHTML = "No.";
		
	yes.onclick = function yes() {
		parent.removeChild(listItem);
		
		if (parent == document.getElementById("playlist")) {
			// Add remove from playlist functionality
		} else {
			// Also remove from server functionality here!
		}
	}
		
	no.onclick = function no() {
		containBox.removeChild(con);
		containInfo.style["display"] = null;
	}
		
	con.appendChild(desc);
	con.appendChild(but);
	but.appendChild(yes);
	but.appendChild(no);
}

function loadInformation(song) {
	// Load in the information for this. 
	song["tags"] = "I, Have, None!";
	
	return song;
}

function displayInformation(song, infoBox) {
	
}
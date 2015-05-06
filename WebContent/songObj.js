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
	var songItem = document.createElement('div');
	var containBox = document.createElement('div');
	var containInfo = document.createElement('div');
	var votingItem = document.createElement('span');
	var basicItem = document.createElement('span');
	var buttonItem = document.createElement('span');
	
	// Add draggable things.
	
	songItem.className = "song";
	containBox.className = "containBox";
	votingItem.className = "voting";
	basicItem.className = "basic";
	buttonItem.className = "button";
	
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
	
	// Create all basic items.
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
	
	info.innerHTML = "i";
	add.innerHTML = "+";
	del.innerHTML = "x";
	
	add.style.background = '#008000';
	add.style.color = "white";
	del.style.background = "red";
	del.style.color = "white";
	
	buttonItem.appendChild(info);
	if (tab != document.getElementById("playlist")) buttonItem.appendChild(add);
	buttonItem.appendChild(del);
	
	// Create functionality.
	add.onclick = function add() {
		var play = document.getElementById("playlist");
		
		// Remove and append.
		buttonItem.removeChild(buttonItem.childNodes[1]);
		tab.removeChild(songItem);
		play.appendChild(songItem);
		
		// Change the remove function to reflect the new parent.
		del.onclick = function remove() {
			removeMore(play, songItem, containBox, containInfo);
		}
		
		// Do server call to add to playlist.
	}
	
	del.onclick = function remove() {
		removeMore(tab, songItem, containBox, containInfo);
	}
	
	// Make info box.
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
	tab.appendChild(songItem);
}



function listener(infoBox, listen) {
	infoBox.parentNode.removeChild(infoBox);
	infoBox.removeEventListener("animationend", listen);
	infoBox.removeEventListener("webkitAnimationEnd", listen);
	
	infoBox.style["animation-name"] = "moveOut";
	infoBox.style["-webkit-animation-name"] = "moveOut";
}

function removeMore(parent, songItem, 
					containBox, containInfo) {
	containBox.removeChild(containInfo);

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
		parent.removeChild(songItem);
		
		if (parent == document.getElementById("playlist")) {
			// Add remove from playlist functionality
		} else {
			// Also remove from server functionality here!
		}
	}
		
	no.onclick = function no() {
		containBox.removeChild(con);
		containBox.appendChild(containInfo);
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
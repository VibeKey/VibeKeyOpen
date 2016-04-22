function populateDeleteEventList() {
	var deleteeventList = document.getElementById("deleteeventList");
	var newsRef = new Firebase(FIREBASE_REF).child("news");
	newsRef.on("value", function(snapshot) {
		deleteeventList.innerHTML = "";
	    snapshot.forEach(function(data) {
	    	var id = data.key();
	        var news = data.val();
	        var title = news.title;
	        var desc = news.description;
	        var date = news.date;
	        var option = document.createElement('option');
	        option.id = option.value = id;
	        option.text =  title + " on " +date;
	        deleteeventList.appendChild(option);
	    });
	});
}

function addevent() {
	var newsRef = new Firebase(FIREBASE_REF).child("news");
	var title = document.getElementById("eventTitle").value;
	var description = document.getElementById("eventDescription").value;
	var date = document.getElementById("eventDate").value;
	var newsItem = newsRef.push();
	newsItem.set({'title' : title, 'description' : description, "date" : date});
}

function deleteevent() {
	var newsRef = new Firebase(FIREBASE_REF).child("news");
	var deleteNews = document.getElementById("deleteeventList").value;
	var deleteNewsRef = newsRef.child(deleteNews);
	deleteNewsRef.remove();
}
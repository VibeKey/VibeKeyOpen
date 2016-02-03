function addNews() {
	var title = document.getElementById("newsTitle").value;
	var description = document.getElementById("newsDescription").value;
	var date = document.getElementById("newsDate").value;
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var newsRef = fireRef.child("news");
	var newsItem = newsRef.push();
	newsItem.set({'title' : title, 'description' : description, "date" : date});
}
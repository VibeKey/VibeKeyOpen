function populateNewsList() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var newsRef = fireRef.child("news");

	//populate delete news drop down in news tab
	var newsList = document.getElementsByClassName("newsList")[1];
	newsList.innerHTML = "";
	var defaultOption = document.createElement('option');
	defaultOption.id = "defaultOption";
	defaultOption.value = "none";
	defaultOption.text =  "No news selected";
	newsList.appendChild(defaultOption);
	newsRef.on("value", function(snapshot) {
	    snapshot.forEach(function(data) {
	    	var id = data.key();
	        var news = data.val();
	        var title = news.title;
	        var desc = news.description;
	        var date = news.date;
	        var option = document.createElement('option');
	        option.id = option.value = id;
	        option.text =  title + " on " +date;
	        newsList.appendChild(option);
	    });
	});
}

function addNews() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var newsRef = fireRef.child("news");
	var title = document.getElementsByClassName("newsTitle")[1].value;
	var description = document.getElementsByClassName("newsDescription")[1].value;
	var date = document.getElementsByClassName("newsDate")[1].value;
	var newsItem = newsRef.push();
	newsItem.set({'title' : title, 'description' : description, "date" : date});

	populateNewsList();
}

function deleteNews() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var newsRef = fireRef.child("news");
	var deleteNews = document.getElementsByClassName("newsList")[1].value;
	var deleteNewsRef = newsRef.child(deleteNews);
	deleteNewsRef.remove();

	populateNewsList();
}
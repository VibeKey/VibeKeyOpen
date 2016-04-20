function populateNews() {
	//clear old content
	var newsList = document.getElementsByClassName("newsList")[1];

	//populate news
    var newsRef = new Firebase(FIREBASE_REF).child("news");
    newsRef.on("value", function(snapshot) {
    	newsList.innerHTML = "";
        snapshot.forEach(function(data) {
            var news = data.val();
            var title = news.title;
            var desc = news.description;
            var date = news.date;
            var dt = document.createElement('dt');
            dt.className = 'news';
            dt.innerHTML = date + " : " + title;
            var dd = document.createElement('dd');
            dd.innerHTML = "-" + desc;
            var br = document.createElement('br');
            $(".newsList").append(dt);
            $(".newsList").append(dd);
            $(".newsList").append(br);
        });
    });
}

function populateDeleteNewsList() {
	//clear old content
	var deletenewsList = document.getElementsByClassName("deletenewsList")[1];

	//populate delete news drop down in news tab
	var newsRef = new Firebase(FIREBASE_REF).child("news");
	newsRef.on("value", function(snapshot) {
		deletenewsList.innerHTML = "";
		var defaultOption = document.createElement('option');
		defaultOption.id = "defaultOption";
		defaultOption.value = "none";
		defaultOption.text =  "No news selected";
		deletenewsList.appendChild(defaultOption);
	    snapshot.forEach(function(data) {
	    	var id = data.key();
	        var news = data.val();
	        var title = news.title;
	        var desc = news.description;
	        var date = news.date;
	        var option = document.createElement('option');
	        option.id = option.value = id;
	        option.text =  title + " on " +date;
	        deletenewsList.appendChild(option);
	    });
	});
}

function addNews() {
	var newsRef = new Firebase(FIREBASE_REF).child("news");
	var title = document.getElementsByClassName("newsTitle")[1].value;
	var description = document.getElementsByClassName("newsDescription")[1].value;
	var date = document.getElementsByClassName("newsDate")[1].value;
	var newsItem = newsRef.push();
	newsItem.set({'title' : title, 'description' : description, "date" : date});
}

function deleteNews() {
	var newsRef = new Firebase(FIREBASE_REF).child("news");
	var deleteNews = document.getElementsByClassName("deletenewsList")[1].value;
	var deleteNewsRef = newsRef.child(deleteNews);
	deleteNewsRef.remove();
}
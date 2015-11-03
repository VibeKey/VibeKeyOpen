function populateNewsList() {
var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
var newsRef = fireRef.child("news");

//populate delete news drop down in news tab
$('.newsList').empty();
$('.newsList').append("<option value='none'>No news selected</option>");
newsRef.once("value", function(snapshot) {
    snapshot.forEach(function(data) {
    	var id = data.key();
        var news = data.val();
        var title = news.title;
        var desc = news.description;
        var date = news.date;
        var option = document.createElement('option');
        option.id = option.value = id;
        option.text =  title + " on " +date;
        $('.newsList').append(option);
    });
});
}

function addNews() {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var newsRef = fireRef.child("news");
	var title = document.getElementById("newsTitle").value;
	var description = document.getElementById("newsDescription").value;
	var date = document.getElementById("newsDate").value;
	var newsItem = newsRef.push();
	newsItem.set({'title' : title, 'description' : description, "date" : date});

	populateNewsList();
}

function deleteNews(list) {
	var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
	var newsRef = fireRef.child("news");
	var deleteNews = list.value;
	var deleteNewsRef = newsRef.child(deleteNews);
	deleteNewsRef.remove();

	populateNewsList();
}
var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
var newsRef = fireRef.child("news");

newsRef.once("value", function(snapshot) {
  snapshot.forEach(function(data) {
    var news = data.val();
    var title = news.title;
    var desc = news.description;
    var date = news.date;
    var newsItem = document.createElement('div');
    newsItem.className = 'news';
    newsItem.innerHTML = title + " " + desc + " " + date;
    $("h1").append(newsItem);
  });
});
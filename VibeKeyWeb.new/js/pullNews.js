function populateNews() {
    var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
    var newsRef = fireRef.child("news");

    newsRef.on("value", function(snapshot) {
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

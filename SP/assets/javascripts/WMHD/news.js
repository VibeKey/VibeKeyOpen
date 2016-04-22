function populateNews() {
	//clear old content
	var newsList = document.getElementById("newsList");

	//populate news
    var newsRef = new Firebase(FIREBASE_REF).child("news");
    newsRef.on("value", function(snapshot) {
    	newsList.innerHTML = "";
        snapshot.forEach(function(data) {
            var news = data.val();
            var title = news.title;
            var desc = news.description;
            var date = news.date;
            
            var item = document.createElement('div');
            item.className = 'schedrow wow animated fadeIn';
            
            var etitle = document.createElement('div');
            etitle.className='event';
            etitle.innerHTML = '<i class="fa fa-music"></i><span>'+title+'</span>'
            var edesc = document.createElement('div');
            edesc.className='description';
            edesc.innerHTML = '<i class="fa fa-info-circle"></i><span>'+desc+'</span>'
            var edate = document.createElement('div');
            edate.className='date';
            edate.innerHTML = '<i class="fa fa-calendar"></i><span>'+date+'</span>'

            item.appendChild(etitle);
            item.appendChild(edesc); 
            item.appendChild(edate);           
            newsList.appendChild(item);
        });
    });
}
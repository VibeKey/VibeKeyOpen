$(window).load(function(){
    var ref = new Firebase("https://vibekey-open.firebaseio.com/");
    var auth = ref.getAuth();
    if(auth) {
        var uid = auth.uid;
        console.log(uid);
    }
	populateDeleteEventList();
    populateGenreList();
    populatePlaylistList();
});
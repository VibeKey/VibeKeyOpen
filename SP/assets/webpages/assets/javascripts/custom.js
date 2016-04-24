$(document).ready(function() {
  //  -------------- select2 --------------
    $(".songList1").select2();
    $(".playslistList").select2();
    $(".genresList").select2();
    $(".deleteeventList").select2();
    $(".songList2").select2();
    $(".playModeList").select2();
    $(".repeatModeList").select2();
    $(".djNameList").select2();
    $(".scheduleGenresList").select2();
    $(".schedulePLList").select2();
});

$(window).load(function(){
    var ref = new Firebase("https://vibekey-open.firebaseio.com/");
    var auth = ref.getAuth();
    if(auth) {
        var uid = auth.uid;
        console.log(uid);
    }
    //Stream Control
    populateSongList1();
    populateGenreList();
    populatePlaylistList();
    //Event Control
	populateDeleteEventList();
    //Playlist Control
    populateSongList2();
    //Schedule Control
    populateScheduleGenreList();
    populateSchedulePlaylistList();
});
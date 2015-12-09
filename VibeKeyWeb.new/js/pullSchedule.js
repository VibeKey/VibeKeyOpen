function populateSchedule() {
    var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
    var queueRef = fireRef.child("queue");

    queueRef.on("value", function(snapshot) {
        snapshot.forEach(function(data) {
            var scheduledItem = data.val();
            var title = scheduledItem.title;
            var artist = scheduledItem.artist;
            var li = document.createElement('li');
            li.className = 'scheduledItem';
            li.innerHTML = title + " by " + artist;
            var br = document.createElement('br');
            $(".scheduleList").append(li);
            $(".scheduleList").append(br);
        });
    });
}

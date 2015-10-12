public class DJBot {
	SongQueue queue;
	SongDatabase songDB;
	FirebaseCommunicator fbc;
	StreamController streamController;
	
	public DJBot() {
		fbc = new FirebaseCommunicator("https://vibekey-open.firebaseio.com/");
		songDB = new SongDatabase("/home/radio3/Music", fbc);
		queue = new SongQueue(fbc);

		
		streamController = new StreamController(songDB, fbc);
		fbc.setupFirebaseListeners(streamController);
	}
	
	
	public void play(){
		streamController.playNextSong();
	}
	
	public void close(){
		streamController.close();
	}

}

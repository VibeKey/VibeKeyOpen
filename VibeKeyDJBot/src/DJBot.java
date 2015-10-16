import com.firebase.client.Firebase;

public class DJBot {
	SongQueue queue;
	SongDatabase songDB;
	StreamController streamController;
	
	public DJBot() {
		FirebaseCommunicator.rootRef = new Firebase("https://vibekey-open.firebaseio.com/");
		songDB = new SongDatabase("/home/radio3/MusicDev");
		queue = new SongQueue();

		
		streamController = new StreamController(songDB);
		FirebaseCommunicator.setupFirebaseListeners(streamController);
	}
	
	
	public void play(){
		streamController.playNextSong();
	}
	
	public void close(){
		streamController.close();
	}

}

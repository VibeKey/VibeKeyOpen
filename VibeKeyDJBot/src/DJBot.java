import com.firebase.client.Firebase;

public class DJBot {
	SongQueue queue;
	StreamController streamController;
	FirebaseCommandParser fbCommandParser;
	
	public DJBot() {
		FirebaseCommunicator.rootRef = new Firebase("https://vibekey-open.firebaseio.com/");
		SongDatabase.musicPath = "/home/radio3/MusicDev";
		SongDatabase.loadDatabase();
		queue = new SongQueue();

		
		streamController = new StreamController(queue);
		fbCommandParser = new FirebaseCommandParser(queue, streamController);
		FirebaseCommunicator.setupFirebaseListeners(fbCommandParser);
	}
	
	
	public void play(){
		streamController.playNextSong();
	}
	
	public void close(){
		streamController.close();
	}

}

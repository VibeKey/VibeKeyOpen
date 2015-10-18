import com.firebase.client.Firebase;

public class DJBot {
	StreamController streamController;
	FirebaseCommandParser fbCommandParser;
	
	public DJBot() {
		FirebaseCommunicator.rootRef = new Firebase("https://vibekey-open.firebaseio.com/");
		SongDatabase.musicPath = "/home/radio3/MusicDev";
		SongDatabase.loadDatabase();

		
		streamController = new StreamController();
		fbCommandParser = new FirebaseCommandParser(streamController);
		FirebaseCommunicator.setupFirebaseListeners(fbCommandParser);
	}
	
	
	public void play(){
		streamController.playNextSong();
	}
	
	public void close(){
		streamController.close();
	}

}

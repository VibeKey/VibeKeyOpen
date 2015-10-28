import com.firebase.client.Firebase;

public class DJBot {
	StreamController streamController;
	FirebaseCommandParser fbCommandParser;
	
	static final String FIREBASE_SECRET = "1a67VqyArJO54DIBBDe1T4W3V7Rhb7XrXLylVTAE";
	
	public DJBot() {
		FirebaseCommunicator.rootRef = new Firebase("https://vibekey-open.firebaseio.com/");
		SongDatabase.musicPath = "/home/radio3/MusicDev";
		SongDatabase.loadDatabase();

		
		streamController = new StreamController();
		fbCommandParser = new FirebaseCommandParser(streamController);
		FirebaseCommunicator.setupFirebaseListeners(fbCommandParser);
		FirebaseCommunicator.authenticateServer(FIREBASE_SECRET);
	}
	
	
	public void play(){
		streamController.playNextSong();
	}
	
	public void close(){
		streamController.close();
	}

}

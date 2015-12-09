import com.firebase.client.Firebase;

public class DJBot {
	StreamController streamController;
	FirebaseCommandParser fbCommandParser;
	
	static final String FIREBASE_SECRET = "1a67VqyArJO54DIBBDe1T4W3V7Rhb7XrXLylVTAE";
	static final String FIREBASE_ROOT_URL = "https://vibekey-open.firebaseio.com/";
	static final String MUSIC_PATH = "/home/radio3/MusicDev";
	
	public DJBot() {
		FirebaseCommunicator.rootRef = new Firebase(FIREBASE_ROOT_URL);
		SongDatabase.musicPath = MUSIC_PATH;
		SongDatabase.loadDatabase();

		
		streamController = new StreamController();
		fbCommandParser = new FirebaseCommandParser(streamController);
		FirebaseCommunicator.setupFirebaseListeners(fbCommandParser);
		FirebaseCommunicator.authenticateServer(FIREBASE_SECRET);
	}
	
	
	public void play(){
		streamController.play();
	}
	
	public void close(){
		streamController.close();
	}

}

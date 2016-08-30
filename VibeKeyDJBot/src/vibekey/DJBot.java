package vibekey;
import com.firebase.client.Firebase;

import vibekey.firebase.BasicCommandParser;
import vibekey.firebase.FirebaseCommandParser;
import vibekey.firebase.FirebaseCommunicator;
import vibekey.picker.NoPickException;
import vibekey.song.SongDatabase;
import vibekey.stream.StreamController;

public class DJBot {
	StreamController streamController;
	FirebaseCommandParser fbCommandParser;
	SongDatabase songDatabase;
	
	static final String FIREBASE_SECRET = "1a67VqyArJO54DIBBDe1T4W3V7Rhb7XrXLylVTAE";
	static final String FIREBASE_ROOT_URL = "https://vibekey-open.firebaseio.com/prod/";
	static final String MUSIC_PATH = "/home/music";
	
	public DJBot() {
		FirebaseCommunicator.rootRef = new Firebase(FIREBASE_ROOT_URL);
		songDatabase = new SongDatabase(MUSIC_PATH, FirebaseCommunicator.rootRef);
		streamController = new StreamController(songDatabase);

		/*fbCommandParser = new CompoundCommandParser(streamController);
		fbCommandParser.addParser(new UserFirebaseCommandParser(streamController));
		fbCommandParser.addParser(new DJFirebaseCommandParser(streamController));
		fbCommandParser.addParser(new AdminFirebaseCommandParser(streamController));
		
		*/
		fbCommandParser = new BasicCommandParser(streamController);
		
		fbCommandParser.setupFirebaseListeners(FirebaseCommunicator.rootRef.child("controls"));
		FirebaseCommunicator.authenticateServer(FIREBASE_SECRET);
	}
	
	
	public void play(){
		try {
			streamController.play();
		} catch (NoPickException e) {
			System.out.println("DJBot detected no pick, even after default fallback!");
			e.printStackTrace();
		}
	}
	
	public void close(){
		streamController.close();
	}

}

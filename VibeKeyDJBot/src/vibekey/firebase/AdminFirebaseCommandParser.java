package vibekey.firebase;

import com.firebase.client.DataSnapshot;

import vibekey.stream.StreamController;

@SuppressWarnings("unused")
public class AdminFirebaseCommandParser extends FirebaseCommandParser {
	public AdminFirebaseCommandParser(StreamController streamController) {
		super(streamController, "admins");
	}

	private void stopServer(DataSnapshot params){
		streamController.stopServer();
	}
}

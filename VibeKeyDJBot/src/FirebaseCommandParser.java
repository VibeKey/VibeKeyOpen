import com.firebase.client.DataSnapshot;

public class FirebaseCommandParser {
	SongQueue queue;
	StreamController streamController;
	
	public FirebaseCommandParser( SongQueue queue, StreamController streamController){
		this.queue = queue;
		this.streamController = streamController;
	}
	
	public void parseCommand(String cmdString, Iterable<DataSnapshot> params){
		if(cmdString != null){
			if(cmdString == "addToQueue"){
				String path = "";
				for(DataSnapshot param : params){
					if(param.getKey().equals("songPath")){
						path = (String) param.getValue();
					}
				}
				Song song = SongDatabase.getSongFromPath(path);
				
				queue.addToQueue(song);
			}else if(cmdString == "addToFrontOfQueue"){
				String path = "";
				for(DataSnapshot param : params){
					if(param.getKey().equals("songPath")){
						path = (String) param.getValue();
					}
				}
				Song song = SongDatabase.getSongFromPath(path);
				
				queue.addToQueueAt(0, song);
			}else if(cmdString == "nextSong"){
				streamController.curPlaying.playing = false; //force current song to stop
			}else if(cmdString == "setPlayMode"){
				for(DataSnapshot param : params){
					if(param.getKey().equals("playMode")){
						streamController.playMode = (String) param.getValue();
					}
				}
			}else if(cmdString == "setGenre"){
				for(DataSnapshot param : params){
					if(param.getKey().equals("genre")){
						streamController.genreFilter = (String) param.getValue();
					}
				}
			}
		}
		
		
		FirebaseCommunicator.clearCommand();
	}
}

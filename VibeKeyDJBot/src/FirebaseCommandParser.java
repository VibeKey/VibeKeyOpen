import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.firebase.client.DataSnapshot;

@SuppressWarnings("unused")
public class FirebaseCommandParser {
	StreamController streamController;
	
	public FirebaseCommandParser(StreamController streamController){
		this.streamController = streamController;
	}
	
	public void parseCommand(String cmdString, Iterable<DataSnapshot> params) {
		if (cmdString != null) {
			switch (cmdString) {
			case "addToQueue":
				addToQueue(params);
				break;

			case "addToFrontOfQueue":
				addToFrontOfQueue(params);
				break;

			case "nextSong":
				nextSong(params);
				break;

			case "setPlayMode":
				setPlayMode(params);
				break;

			case "setGenre":
				setGenre(params);
				break;
				
			case "setPlaylist":
				setPlaylist(params);
				break;

			default:
				break;
			}
			/*
			try {
				Class[] classes = new Class[1];
				classes[0]= Iterable.class;
				
				System.out.println(classes[0].getName());
				Method method = this.getClass().getMethod(cmdString, classes);
				method.invoke(this, params);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		
		FirebaseCommunicator.clearCommand();
	}

	private void setGenre(Iterable<DataSnapshot> params) {
		for(DataSnapshot param : params){
			if(param.getKey().equals("genre")){
				streamController.genreFilter = (String) param.getValue();
			}
		}
	}

	private void setPlaylist(Iterable<DataSnapshot> params) {
		for(DataSnapshot param : params){
			if(param.getKey().equals("playlist")){
				streamController.playlistName = (String) param.getValue();
			}
		}
	}

	private void setPlayMode(Iterable<DataSnapshot> params) {
		for(DataSnapshot param : params){
			if(param.getKey().equals("playMode")){
				streamController.playMode = (String) param.getValue();
			}
		}
	}

	private void nextSong(Iterable<DataSnapshot> params) {
		streamController.curPlaying.playing = false; //force current song to stop
	}

	private void addToFrontOfQueue(Iterable<DataSnapshot> params) {
		String path = "";
		for(DataSnapshot param : params){
			if(param.getKey().equals("songPath")){
				path = (String) param.getValue();
			}
		}
		Song song = SongDatabase.getSongFromPath(path);
		streamController.queue.addToQueueAt(0, song);
	}

	private void addToQueue(Iterable<DataSnapshot> params) {
		String path = "";
		for (DataSnapshot param : params) {
			if (param.getKey().equals("songPath")) {
				path = (String) param.getValue();
			}
		}
		Song song = SongDatabase.getSongFromPath(path);
		streamController.queue.addToQueue(song);
	}
}

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import com.firebase.client.DataSnapshot;

@SuppressWarnings("unused")
public class FirebaseCommandParser {
	StreamController streamController;
	
	public FirebaseCommandParser(StreamController streamController){
		this.streamController = streamController;
	}

	@SuppressWarnings("rawtypes")
	public void parseCommand(String cmdString, Iterable<DataSnapshot> params) {
		if (cmdString != null) {
			
			try {
				Class[] classes = new Class[1];
				classes[0]=Iterable.class;
				
				System.out.println(classes[0].getName());
				Method method = this.getClass().getDeclaredMethod(cmdString, classes);	
				method.invoke(this, params);
			} catch (NoSuchMethodException e) {
				System.out.println("ERROR: Invalid command \"" + cmdString + "\" from Firebase.");
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
			}
			
		}
		
		FirebaseCommunicator.clearCommand();
	}

	private void setGenre(Iterable<DataSnapshot> params) {
		for(DataSnapshot param : params){
			if(param.getKey().equals("genre")){
				streamController.genreFilter = (String) param.getValue();
			}
		}
		streamController.refillQueue();
	}

	private void setPlaylist(Iterable<DataSnapshot> params) {
		for(DataSnapshot param : params){
			if(param.getKey().equals("playlist")){
				streamController.playlistName = (String) param.getValue();
			}
		}
		streamController.refillQueue();
	}

	private void setPlayMode(Iterable<DataSnapshot> params) {
		for(DataSnapshot param : params){
			if(param.getKey().equals("playMode")){
				streamController.playMode = (String) param.getValue();
			}
		}
		streamController.refillQueue();
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
	
	private void addToSchedule(Iterable<DataSnapshot> params){
		int playMode = 0;
		int repeatMode = 0;
		Date startTime = new Date();
		Date endTime = new Date();
		String DJName = "";
		String genre = ""; //ignored if not in genre play mode
		String playlist = ""; //ignored if not in playlist play mode
		
		for (DataSnapshot param : params) {
			if (param.getKey().equals("playMode")) {
				playMode = (int) param.getValue();
			}
			if (param.getKey().equals("repeatMode")) {
				repeatMode = (int) param.getValue();
			}
			if (param.getKey().equals("startTime")) {
				startTime = (Date) param.getValue();
			}
			if (param.getKey().equals("endTime")) {
				endTime = (Date) param.getValue();
			}
			if (param.getKey().equals("DJName")) {
				DJName = (String) param.getValue();
			}
			if (param.getKey().equals("genre")) {
				genre = (String) param.getValue();
			}
			if (param.getKey().equals("playlist")) {
				playlist = (String) param.getValue();
			}
		}
		ScheduleItem newScheduleItem = new ScheduleItem(playMode, repeatMode, startTime, endTime, DJName, genre, playlist);
		streamController.playSchedule.addToSchedule(newScheduleItem);
	}
	
	private void addPlaylist(Iterable<DataSnapshot> params){
		Playlist newPlaylist = new Playlist();
		for (DataSnapshot param : params) {
			if (param.getKey().equals("name")) {
				newPlaylist.setName(param.getValue(String.class));
			}
			if (param.getKey().equals("songs")) {
		    	for(DataSnapshot songSnapshot : param.getChildren()){
		    		String songPath = songSnapshot.getValue(String.class);
		    		newPlaylist.addSong(SongDatabase.getSongFromPath(songPath));
		    	}
			}
		}
		streamController.playlistController.allPlaylists.add(newPlaylist);
		
		
	}
}

package vibekey.firebase;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import com.firebase.client.DataSnapshot;

import vibekey.playlist.Playlist;
import vibekey.schedule.ScheduleItem;
import vibekey.song.SimplifiedSong;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.stream.StreamController;

@SuppressWarnings("unused")
public class FirebaseCommandParser {
	StreamController streamController;
	
	public FirebaseCommandParser(StreamController streamController){
		this.streamController = streamController;
	}

	@SuppressWarnings("rawtypes")
	public Object parseCommand(DataSnapshot snapshot) {
  	  	String cmdString = (String) snapshot.child("cmdString").getValue();
  	  	DataSnapshot params = snapshot.child("params");
		if (cmdString != null) {
			
			try {
				Class[] classes = new Class[1];
				classes[0]=DataSnapshot.class;
				
				System.out.println(classes[0].getName());
				Method method = this.getClass().getDeclaredMethod(cmdString, classes);	
				return method.invoke(this, params);
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
		FirebaseCommunicator.clearCommand(snapshot);
		return null;
	}

//	private void setGenre(DataSnapshot params) {
//		streamController.genreFilter = params.child("genre").getValue(String.class);
//		streamController.refillQueue();
//	}
//
//	private void setPlaylist(DataSnapshot params) {
//		streamController.playlistName = params.child("playlist").getValue(String.class);
//		streamController.refillQueue();
//	}

	private void setPlayMode(DataSnapshot params) {
		streamController.playMode = params.child("playMode").getValue(String.class);
		streamController.refillQueue();
	}

	private void nextSong(DataSnapshot params) {
		streamController.curPlaying.playing = false; //force current song to stop
	}

	private void addToFrontOfQueue(DataSnapshot params) {
		String path = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(path);
		streamController.queue.addToQueueAt(0, song);
	}

	private void addToQueue(DataSnapshot params) {
		String path = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(path);
		streamController.queue.addToQueue(song);
	}
	
	private void addToSchedule(DataSnapshot params){
		int playMode = params.child("playMode").getValue(Integer.class);
		int repeatMode = params.child("repeatMode").getValue(Integer.class);
		Date startTime = params.child("startTime").getValue(Date.class);
		Date endTime = params.child("endTime").getValue(Date.class);
		String DJName = params.child("DJName").getValue(String.class);
		String genre = params.child("genre").getValue(String.class);
		String playlist = params.child("playlist").getValue(String.class);
		ScheduleItem newScheduleItem = new ScheduleItem(playMode, repeatMode, startTime, endTime, DJName, genre, playlist);
		streamController.playSchedule.addToSchedule(newScheduleItem);
	}
	
	private void addPlaylist(DataSnapshot params){
		Playlist newPlaylist = new Playlist();
		newPlaylist.setName(params.child("name").getValue(String.class));
		for(DataSnapshot songSnapshot : params.child("songs").getChildren()){
			String songPath = songSnapshot.getValue(String.class);
		    newPlaylist.addSong(SongDatabase.getSongFromPath(songPath));
		}
		streamController.playlistController.allPlaylists.add(newPlaylist);
		streamController.playlistController.updateFirebase();
	}
	
	private void requestSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(songPath);
		streamController.queue.addToQueue(song); //TODO: add limits for user requests
	}
	
	private void upvoteSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(songPath);
		song.upvote();
	}
		
	private void downvoteSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(songPath);
		song.downvote();
	}
	
	private ArrayList<SimplifiedSong> searchSongs(DataSnapshot params){
		String searchString = params.child("searchString").getValue(String.class);
		Integer searchMaxItems = params.child("searchMaxItems").getValue(Integer.class);
		ArrayList<SimplifiedSong> returnSongs = new ArrayList<SimplifiedSong>();
		String[] searchStringWords = searchString.split(" ");
		
		for(Song song : SongDatabase.songs){
			if(searchMaxItems != null && searchMaxItems > 0 && returnSongs.size() >= searchMaxItems){
				break;
			}
			ArrayList<String> songSearchableParams = new ArrayList<String>();
			songSearchableParams.add(song.getTitle());
			songSearchableParams.add(song.getAlbum());
			songSearchableParams.add(song.getArtist());
			songSearchableParams.add(song.getGenre());
			for(String searchStringWord : searchStringWords){
				boolean found = false;
				for(String songSearchableParam : songSearchableParams){
					if(searchStringWord.contains(songSearchableParam) || songSearchableParam.contains(searchStringWord)){
						returnSongs.add(song.getSimplifiedSong());
						found = true;
						break;
					}
				}
				if(found)
					break;
			}
		}
		
		return returnSongs;
	}
	
	private void stopServer(DataSnapshot params){
		streamController.stopServer();
	}
}

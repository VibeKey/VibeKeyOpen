package vibekey.firebase;

import java.util.Date;

import com.firebase.client.DataSnapshot;

import vibekey.filter.Filter;
import vibekey.filter.GenreFilter;
import vibekey.filter.NoFilter;
import vibekey.filter.PlaylistFilter;
import vibekey.playlist.InvalidPlaylistException;
import vibekey.playlist.Playlist;
import vibekey.schedule.ScheduleItem;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.stream.StreamController;

@SuppressWarnings("unused")
public class DJFirebaseCommandParser extends FirebaseCommandParser {
	public DJFirebaseCommandParser(StreamController streamController) {
		super(streamController, "djs");
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
		Filter filter = new NoFilter();
		if(genre != null && !genre.equals("")){
			filter = new GenreFilter(genre);
		}else if(playlist != null && !playlist.equals("")){
			try {
				filter = new PlaylistFilter(streamController.playlistController.search(playlist));
			} catch (InvalidPlaylistException e) {}
		}
		ScheduleItem newScheduleItem = new ScheduleItem(playMode, repeatMode, startTime, endTime, DJName, filter);
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
}

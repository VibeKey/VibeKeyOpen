package vibekey.firebase;

import java.util.ArrayList;
import java.util.Date;

import com.firebase.client.DataSnapshot;

import vibekey.filter.Filter;
import vibekey.filter.GenreFilter;
import vibekey.filter.NoFilter;
import vibekey.filter.PlaylistFilter;
import vibekey.playlist.InvalidPlaylistException;
import vibekey.playlist.Playlist;
import vibekey.schedule.ScheduleItem;
import vibekey.song.SimplifiedSong;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.stream.StreamController;

@SuppressWarnings("unused")
public class BasicCommandParser extends FirebaseCommandParser {
	public BasicCommandParser(StreamController streamController) {
		super(streamController, "command");
	}

	public void stopServer(DataSnapshot params){
		streamController.stopServer();
	}

	public void nextSong(DataSnapshot params) {
		streamController.curPlaying.playing = false; //force current song to stop
	}

	public void addToFrontOfQueue(DataSnapshot params) {
		String path = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(path);
		streamController.queue.addToQueueAt(0, song);
	}

	public void addToQueue(DataSnapshot params) {
		String path = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(path);
		streamController.queue.addToQueue(song);
	}
	
	public void addToSchedule(DataSnapshot params){
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
	
	public void addPlaylist(DataSnapshot params){
		Playlist newPlaylist = new Playlist();
		newPlaylist.setName(params.child("name").getValue(String.class));
		for(DataSnapshot songSnapshot : params.child("songs").getChildren()){
			String songPath = songSnapshot.getValue(String.class);
		    newPlaylist.addSong(SongDatabase.getSongFromPath(songPath));
		}
		streamController.playlistController.allPlaylists.add(newPlaylist);
		streamController.playlistController.updateFirebase();
	}
	public void requestSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(songPath);
		streamController.queue.addToQueue(song); //TODO: add limits for user requests
	}
	
	public void upvoteSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(songPath);
		song.upvote();
	}
		
	public void downvoteSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = SongDatabase.getSongFromPath(songPath);
		song.downvote();
	}
	
	public ArrayList<SimplifiedSong> searchSongs(DataSnapshot params){
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
}

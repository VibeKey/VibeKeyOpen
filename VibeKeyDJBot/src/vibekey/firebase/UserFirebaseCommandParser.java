package vibekey.firebase;

import java.util.ArrayList;

import com.firebase.client.DataSnapshot;

import vibekey.song.SimplifiedSong;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.stream.StreamController;

@SuppressWarnings("unused")
public class UserFirebaseCommandParser extends FirebaseCommandParser {

	public UserFirebaseCommandParser(StreamController streamController) {
		super(streamController, "users");
	}
	
	private void requestSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = streamController.songDatabase.getSongFromPath(songPath);
		streamController.queue.addToQueue(song); //TODO: add limits for user requests
	}
	
	private void upvoteSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = streamController.songDatabase.getSongFromPath(songPath);
		song.upvote();
	}
		
	private void downvoteSong(DataSnapshot params){
		String songPath = params.child("songPath").getValue(String.class);
		Song song = streamController.songDatabase.getSongFromPath(songPath);
		song.downvote();
	}
	
	private ArrayList<SimplifiedSong> searchSongs(DataSnapshot params){
		String searchString = params.child("searchString").getValue(String.class);
		Integer searchMaxItems = params.child("searchMaxItems").getValue(Integer.class);
		ArrayList<SimplifiedSong> returnSongs = new ArrayList<SimplifiedSong>();
		String[] searchStringWords = searchString.split(" ");
		
		for(Song song : streamController.songDatabase.songs){
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

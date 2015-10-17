import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseCommunicator {
	
	static Firebase rootRef;
	
	
	public static void setFirebaseNowPlaying(Song song){
		String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
		rootRef.child("nowPlaying").setValue(song.simplifiedSong);
		rootRef.child("nowPlaying").child("compiledPlayString").setValue(nowPlaying);
	}
	public static void setupFirebaseListeners(FirebaseCommandParser fbCommandParser){
		Firebase commandRef = rootRef.child("controls").child("command");
		commandRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  boolean doCmd = (boolean) snapshot.child("doCmd").getValue();
		    	  if(doCmd){
			    	  String cmdString = (String) snapshot.child("cmdString").getValue();
			    	  fbCommandParser.parseCommand(cmdString, snapshot.child("params").getChildren());
			    	  commandRef.child("doCmd").setValue(false);
		    	  }
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
		
	}
	
	public static void clearSongsList(){
		Firebase songsRef = rootRef.child("songs");
		songsRef.setValue(null);
	}

	public static void clearCommand(){
		Firebase commandRef = rootRef.child("controls").child("command");
		commandRef.setValue(null);
	}
	
	public static void addGenresToFirebase(ArrayList<String> genres){
		Firebase genresListRef = rootRef.child("songs").child("genreList");
		genresListRef.setValue(genres);
	}
	
	public static void updateQueue(LinkedList<Song> queue){
		Firebase queueRef = rootRef.child("queue");
		LinkedList<SimplifiedSong> queueSimplified = new LinkedList<SimplifiedSong>();
		for(Song song : queue){
			queueSimplified.add(song.simplifiedSong);
		}
		queueRef.setValue(queueSimplified);
	}
	
	public static void addSongsToFirebaseByArtistMap(Map<String, ArrayList<Song>> artistMap){
		Firebase songsByArtistRef = rootRef.child("songs").child("byArtist");
		songsByArtistRef.setValue(null);
		Set<String> artistMapKeySet = artistMap.keySet();
		for(String artist : artistMapKeySet){
			Firebase artistRef = songsByArtistRef.push();
			artistRef.child("artist").setValue(artist);
			ArrayList<Song> songsForArtist = artistMap.get(artist);
			for(Song song : songsForArtist){
				Firebase songRef = artistRef.push();
				songRef.setValue(song.simplifiedSong);
			}
		}
	}
	
	public static void addSongsToFirebaseByGenre(Map<String, ArrayList<Song>> genreMap){
		Firebase songsByGenreRef = rootRef.child("songs").child("byGenre");
		songsByGenreRef.setValue(null);
		Set<String> genreMapKeySet = genreMap.keySet();
		for(String genre : genreMapKeySet){
			Firebase genreRef = songsByGenreRef.push();
			genreRef.child("genre").setValue(genre);
			ArrayList<Song> songsForGenre = genreMap.get(genre);
			for(Song song : songsForGenre){
				Firebase songRef = genreRef.push();
				songRef.setValue(song.simplifiedSong);
			}
		}
	}
	
	
	
	public static boolean addSongsToFirebase(ArrayList<Song> songs){
		Firebase allSongsRef = rootRef.child("songs").child("allSongs");
		allSongsRef.setValue(null);
		for(Song song : songs){
			
			SimplifiedSong simplifiedSong = song.simplifiedSong;
			allSongsRef.push().setValue(simplifiedSong);
		}
		return true;
	}
}

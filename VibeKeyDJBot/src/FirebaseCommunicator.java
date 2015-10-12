import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseCommunicator {
	
	Firebase rootRef;
	
	public FirebaseCommunicator(String rootPath){
		rootRef = new Firebase("https://vibekey-open.firebaseio.com/");
	}
	
	public void setFirebaseNowPlaying(Song song){
		String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
		rootRef.child("nowPlaying").setValue(song.simplifiedSong);
		rootRef.child("nowPlaying").child("compiledPlayString").setValue(nowPlaying);
	}
	public void setupFirebaseListeners(StreamController streamController){
		Firebase genreFilterRef = rootRef.child("controls").child("forceGenre");
		genreFilterRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  streamController.genreFilter = (String)snapshot.getValue();
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
		
		Firebase playModeRef = rootRef.child("controls").child("playMode");
		playModeRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  streamController.playMode = (String)snapshot.getValue();
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
		
		Firebase endSongRef = rootRef.child("controls").child("endSong");
		endSongRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  boolean endSong = (Boolean)snapshot.getValue();
		    	  if(endSong){
		    		  streamController.curPlaying.playing=false; //should trigger the stop of the current song
		    		  endSongRef.setValue(false);
		    	  }
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
		
	}
	
	public void addGenresToFirebase(ArrayList<String> genres){
		Firebase genresListRef = rootRef.child("songs").child("genreList");
		genresListRef.setValue(genres);
	}
	
	public void updateQueue(LinkedList<Song> queue){
		Firebase queueRef = rootRef.child("queue");
		LinkedList<SimplifiedSong> queueSimplified = new LinkedList<SimplifiedSong>();
		for(Song song : queue){
			queueSimplified.add(song.simplifiedSong);
		}
		queueRef.setValue(queueSimplified);
	}
	
	public void addSongsToFirebaseByArtistMap(Map<String, ArrayList<Song>> artistMap){
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
	
	public void addSongsToFirebaseByGenre(Map<String, ArrayList<Song>> genreMap){
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
	
	
	
	public boolean addSongsToFirebase(ArrayList<Song> songs){
		Firebase allSongsRef = rootRef.child("songs").child("allSongs");
		allSongsRef.setValue(null);
		for(Song song : songs){
			
			SimplifiedSong simplifiedSong = song.simplifiedSong;
			allSongsRef.push().setValue(simplifiedSong);
		}
		return true;
	}
}

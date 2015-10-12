import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gmail.kunicins.olegs.libshout.Libshout;

public class DJBot {
	Firebase firebaseRef =  new Firebase("https://vibekey-open.firebaseio.com/");
	Libshout icecast;
	ArrayList<Song> songs = new ArrayList<Song>();
	Map<String, ArrayList<Song>> genreMap;
	Map<String, ArrayList<Song>> artistMap;
	ArrayList<String> genres;
	Random rand = new Random(System.currentTimeMillis());
	boolean genreFilterOn=false;
	String genreFilter;
	Song curPlaying;
	
	public DJBot() {
		System.out.println("Loading songs... (this might take a while)");
		loadAllSongs("/home/radio3/Music",songs);
		StringBuilder sb = new StringBuilder (String.valueOf ("Songs loaded: "));
		sb.append (songs.size());
		System.out.println(sb.toString());
		
		
		addSongsToFirebase(songs);
		genreMap = addSongsToFirebaseByGenre(songs);
		artistMap = addSongsToFirebaseByArtist(songs);
		

		genres = addGenresToFirebase(genreMap);

		icecast = initializeIcecast();
		setupFirebaseListeners(firebaseRef);
		
	}
	
	
	public void setupFirebaseListeners(Firebase firebaseRef){
		Firebase genreFilterRef = firebaseRef.child("curGenre");
		genreFilterRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  genreFilter = (String)snapshot.getValue();
		    	  if(genreFilter.equals("none")){
		    		  genreFilterOn=false;
		    	  }else{
		    		  genreFilterOn=true;
		    	  }
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
		
		Firebase nextSongRef = firebaseRef.child("nextSong");
		nextSongRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  boolean nextSong = (Boolean)snapshot.getValue();
		    	  if(nextSong){
		    		  curPlaying.playing=false; //should trigger the stop of the current song
		    		  nextSongRef.setValue(false);
		    	  }
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
	}
	
	public ArrayList<String> addGenresToFirebase(Map<String, ArrayList<Song>> genreMap){
		ArrayList<String> genres = new ArrayList<String>(genreMap.keySet());
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(!genre.equals("") && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		
		Firebase genresListRef = firebaseRef.child("songs").child("genreList");
		genresListRef.setValue(genres);
		return genres;
	}
	
	public Map<String, ArrayList<Song>> addSongsToFirebaseByArtist(ArrayList<Song> songs){
		Firebase songsByArtistRef = firebaseRef.child("songs").child("byArtist");
		Map<String, ArrayList<Song>> artistMap = new HashMap<String, ArrayList<Song>>();
		for(Song song : songs){
			if(artistMap.containsKey(song.getArtist())){
				ArrayList<Song> artistSongList = artistMap.get(song.getArtist());
				artistSongList.add(song);
			}else{
				ArrayList<Song> artistSongList = new ArrayList<Song>();
				artistSongList.add(song);
				artistMap.put(song.getArtist(), artistSongList);
			}
		}
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
		return artistMap;
	}
	
	
	public Map<String, ArrayList<Song>> addSongsToFirebaseByGenre(ArrayList<Song> songs){
		Firebase songsByGenreRef = firebaseRef.child("songs").child("byGenre");
		Map<String, ArrayList<Song>> genreMap = new HashMap<String, ArrayList<Song>>();
		for(Song song : songs){
			if(genreMap.containsKey(song.getGenre())){
				ArrayList<Song> genreSongList = genreMap.get(song.getGenre());
				genreSongList.add(song);
			}else{
				ArrayList<Song> genreSongList = new ArrayList<Song>();
				genreSongList.add(song);
				genreMap.put(song.getGenre(), genreSongList);
			}
		}
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
		return genreMap;
	}
	
	
	
	public boolean addSongsToFirebase(ArrayList<Song> songs){
		
		Firebase allSongsRef = firebaseRef.child("songs").child("allSongs");
		for(Song song : songs){
			
			SimplifiedSong simplifiedSong = song.simplifiedSong;
			allSongsRef.push().setValue(simplifiedSong);
		}
		return true;
	}
	
	
	public void playRandom(){
		Song song;
		int songNum;
		
		if(genreFilterOn){
			songNum = rand.nextInt(genreMap.get(genreFilter).size());
			song = genreMap.get(genreFilter).get(songNum);
		}else{
			songNum = rand.nextInt(songs.size());
			song = songs.get(songNum);
		}
		
		String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
		System.out.println(nowPlaying);
		setFirebaseNowPlaying(song);
		curPlaying = song;
		//boolean finishedSucessfully = song.streamSong(icecast);
		song.streamSong(icecast);
	}
	
	public void setFirebaseNowPlaying(Song song){
		String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
		firebaseRef.child("nowPlaying").setValue(song.simplifiedSong);
		firebaseRef.child("nowPlaying").child("compiledPlayString").setValue(nowPlaying);
	}
	
	public void closeIcecast(){
		icecast.close();
	}
	
	public void close(){
		closeIcecast();
	}
	
	Libshout initializeIcecast(){
		try {
			Libshout icecast;
			icecast = new Libshout();
			icecast.setHost("localhost");
			icecast.setPort(8000);
			icecast.setProtocol(Libshout.PROTOCOL_HTTP);
			icecast.setPassword("ImASource!");
			icecast.setMount("/djbot");
			icecast.setFormat(Libshout.FORMAT_MP3);
			icecast.open();
			return icecast;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void loadAllSongs(String directoryName, ArrayList<Song> songs) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile() && getFileExtension(file).equals("mp3")) {
	            songs.add(new Song(file));
	        } else if (file.isDirectory()) {
	        	loadAllSongs(file.getAbsolutePath(), songs);
	        }
	    }
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
}

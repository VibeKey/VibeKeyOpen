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
	Firebase firebaseRef;
	Libshout icecast;
	ArrayList<Song> songs;
	Random rand;
	boolean genreFilterOn=false;
	String genreFilter;
	Song curPlaying;
	
	public DJBot() {
		firebaseRef = new Firebase("https://vibekey-open.firebaseio.com/");
		rand = new Random(System.currentTimeMillis());
		
		System.out.println("Loading songs... (this might take a while)");
		songs = new ArrayList<Song>();
		listMp3s("/home/radio3/Music",songs);
		
		
		StringBuilder sb = new StringBuilder (String.valueOf ("Songs loaded: "));
		sb.append (songs.size());
		System.out.println(sb.toString());
		
		
		System.out.println("Loading genres...");
		ArrayList<String> genres = getAllGenres(songs);
		sb = new StringBuilder (String.valueOf ("Genres loaded: "));
		for(String genre : genres){
			sb.append(genre);
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		System.out.println(sb.toString());
		
		System.out.println("Loading all songs into database...");
		Map<String, ArrayList<Map<String, String>>> songArtistMap = getAllSongsByArtist(songs);
		
		Set<String> artists = songArtistMap.keySet();
		sb = new StringBuilder (String.valueOf ("Artists loaded: "));
		for(String artist : artists){
			sb.append(artist);
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		System.out.println(sb.toString());
		
		System.out.println("Songs done loading!  Uploading...");
		firebaseRef.child("songs").setValue(songArtistMap);
		System.out.println("Song uploading done.");
		
		firebaseRef.child("genres").setValue(genres);
		

		icecast = initializeIcecast();
		setupFirebaseListeners(firebaseRef);
		
	}
	
	
	public void setupFirebaseListeners(Firebase rootRef){
		Firebase genreFilterRef = rootRef.child("curGenre");
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
		
		Firebase nextSongRef = rootRef.child("nextSong");
		nextSongRef.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  boolean nextSong = (Boolean)snapshot.getValue();
		    	  if(nextSong){
		    		  curPlaying.playing=false;
		    		  nextSongRef.setValue(false);
		    	  }
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
	}
	
	public ArrayList<String> getAllGenres(ArrayList<Song> songs){
		ArrayList<String> genres = new ArrayList<String>();
		for(Song song : songs){
			String genre = song.getGenre();
			if(!genre.equals("") && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		return genres;
	}
	
	public Map<String, ArrayList<Map<String, String>>> getAllSongsByArtist(ArrayList<Song> songs){
		Map<String, ArrayList<Map<String, String>>> ret = new HashMap<String, ArrayList<Map<String, String>>>();
		for(Song song : songs){
			
			//construct map of metadata
			Map<String, String> songData = new HashMap<String, String>();
			songData.put("title", song.getTitle());
			songData.put("artist", song.getArtist());
			songData.put("album", song.getAlbum());
			songData.put("genre", song.getGenre());
			
			
			String artistKey = song.getArtist(); //have to take precautions putting artists as keys in firebase
			artistKey = artistKey.replaceAll("\\/", "\\"); //replace forward slashes with back slashes to appease firebase
			artistKey = artistKey.replaceAll("[\\.\\#\\$]", ""); //get rid of more special characters
			artistKey = artistKey.replaceAll("\\[", "(");
			artistKey = artistKey.replaceAll("\\]", "]");
			if(artistKey.equals("")){
				artistKey = " "; //fix for non-empty firebase keys
			}
			
			if(ret.containsKey(artistKey)){
				ArrayList<Map<String, String>> artistSongList = ret.get(artistKey);
				artistSongList.add(songData);
			}else{
				ArrayList<Map<String, String>> artistSongList = new ArrayList<Map<String, String>>();
				artistSongList.add(songData);
				ret.put(artistKey, artistSongList);
			}
		}
		return ret;
	}
	
	
	public void playRandom(){
		Song song;
		int songNum;
		do{
			songNum = rand.nextInt(songs.size());
			song = songs.get(songNum);
		} while(genreFilterOn && !song.getGenre().equals(genreFilter)); //hacky, will be removed very soon
		
		String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
		System.out.println(nowPlaying);
		firebaseRef.child("nowPlaying").child("title").setValue(song.getTitle());
		firebaseRef.child("nowPlaying").child("artist").setValue(song.getArtist());
		firebaseRef.child("nowPlaying").child("genre").setValue(song.getGenre());
		firebaseRef.child("nowPlaying").child("compiledPlayString").setValue(nowPlaying);
		curPlaying = song;
		//boolean finishedSucessfully = song.streamSong(icecast);
		song.streamSong(icecast);
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
	
	public void listMp3s(String directoryName, ArrayList<Song> songs) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile() && getFileExtension(file).equals("mp3")) {
	            songs.add(new Song(file));
	        } else if (file.isDirectory()) {
	        	listMp3s(file.getAbsolutePath(), songs);
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

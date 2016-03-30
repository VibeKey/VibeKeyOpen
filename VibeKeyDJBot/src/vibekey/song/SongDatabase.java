package vibekey.song;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.firebase.client.Firebase;

import vibekey.firebase.FirebaseCommunicator;
import vibekey.firebase.Syncable;

public class SongDatabase implements Syncable {
	public static ArrayList<Song> songs = new ArrayList<Song>();
	public static String musicPath;
	public static ArrayList<String> genres;
	public static HashMap<String, Song> pathToSongMap;
	
	
	static public void loadDatabase(){
		songs.clear();
		loadAllSongs(musicPath ,songs);
		genres = getGenres();
		pathToSongMap = buildPathToSongMap();
		pushToFirebase();
	}
	
	static public void pushToFirebase(){
		FirebaseCommunicator.clearSongsList();
		FirebaseCommunicator.syncSongsWithFirebase(songs, pathToSongMap);
		FirebaseCommunicator.addGenresToFirebase(genres);
	}

	static private void loadAllSongs(String directoryName, ArrayList<Song> songs) {
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
	
	static private HashMap<String, Song> buildPathToSongMap(){
		HashMap<String, Song> ret = new HashMap<String, Song>();
		
		for(Song song : songs){
			ret.put(song.getPath(), song);
		}
		
		return ret;
	}
	
	static private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	static private ArrayList<String> getGenres(){
		ArrayList<String> genres = new ArrayList<String>();
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(!genre.equals("") && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		return genres;
	}
	
	static public ArrayList<Song> getSongsInGenre(String genreFilter){
		ArrayList<Song> genreSongs = new ArrayList<Song>();
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(genre.equals(genreFilter)){
				genreSongs.add(song);
			}
		}
		
		return genreSongs;
	}
	
	
	
	static public Song getSongFromPath(String path){
		return pathToSongMap.get(path);
	}

	@Override
	public void setupSyncable(Firebase ref) {
		// TODO Auto-generated method stub
		
	}
}

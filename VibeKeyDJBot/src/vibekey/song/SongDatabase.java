package vibekey.song;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.firebase.client.Firebase;

import vibekey.syncable.SyncableContainer;

public class SongDatabase extends SyncableContainer{
	
	public ArrayList<Song> songs = new ArrayList<Song>();
	public String musicPath;
	public ArrayList<String> genres;
	public HashMap<String, Song> pathToSongMap;

	public SongDatabase(String musicPath, Firebase ref) {
		super(ref.child("songs"));
		this.musicPath = musicPath;
		loadDatabase();
	}
	
	private void loadDatabase(){
		songs.clear();
		loadAllSongs(musicPath ,songs);
		genres = getGenres();
		pathToSongMap = buildPathToSongMap();
		this.setChanged(true);
	}
	
//	public void pushToFirebase(){
//		FirebaseCommunicator.syncSongsWithFirebase(songs, pathToSongMap);
//		FirebaseCommunicator.addGenresToFirebase(genres);
//	}

	private void loadAllSongs(String directoryName, ArrayList<Song> songs) {
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
	
	private HashMap<String, Song> buildPathToSongMap(){
		HashMap<String, Song> ret = new HashMap<String, Song>();
		
		for(Song song : songs){
			ret.put(song.getPath(), song);
		}
		
		return ret;
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	private ArrayList<String> getGenres(){
		ArrayList<String> genres = new ArrayList<String>();
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(!genre.equals("") && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		return genres;
	}
	
	public ArrayList<Song> getSongsInGenre(String genreFilter){
		ArrayList<Song> genreSongs = new ArrayList<Song>();
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(genre.equals(genreFilter)){
				genreSongs.add(song);
			}
		}
		
		return genreSongs;
	}
	
	public Song getSongFromPath(String path){
		return pathToSongMap.get(path);
	}
}

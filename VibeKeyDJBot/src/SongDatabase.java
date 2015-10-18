import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongDatabase {
	static ArrayList<Song> songs = new ArrayList<Song>();
	static String musicPath;
	static Map<String, ArrayList<Song>> genreMap;
	static Map<String, ArrayList<Song>> artistMap;
	static ArrayList<String> genres;
	static HashMap<String, Song> pathToSongMap;
	
	
	static public void loadDatabase(){
		songs.clear();
		loadAllSongs(musicPath ,songs);
		artistMap = getSongArtistMap();
		genreMap = getSongGenreMap();
		genres = getGenres();
		pathToSongMap = buildPathToSongMap();
		pushToFirebase();
	}
	
	static public void pushToFirebase(){
		FirebaseCommunicator.clearSongsList();
		FirebaseCommunicator.addSongsToFirebase(songs);
		FirebaseCommunicator.addSongsToFirebaseByGenre(genreMap);
		FirebaseCommunicator.addSongsToFirebaseByArtistMap(artistMap);
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
		ArrayList<String> genres = new ArrayList<String>(genreMap.keySet());
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(!genre.equals("") && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		return genres;
	}
	
	static private Map<String, ArrayList<Song>> getSongArtistMap(){
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
		return artistMap;
	}
	
	
	static private Map<String, ArrayList<Song>> getSongGenreMap(){
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
		return genreMap;
	}
	
	
	
	static public Song getSongFromPath(String path){
		return pathToSongMap.get(path);
	}
}

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongDatabase {
	ArrayList<Song> songs = new ArrayList<Song>();
	String musicPath;
	Map<String, ArrayList<Song>> genreMap;
	Map<String, ArrayList<Song>> artistMap;
	ArrayList<String> genres;
	FirebaseCommunicator fbc;
	
	public SongDatabase(String musicPath, FirebaseCommunicator fbc){
		this.musicPath = musicPath;
		this.fbc = fbc;
		loadDatabase();
	}
	
	public void loadDatabase(){
		songs.clear();
		loadAllSongs(musicPath ,songs);
		artistMap = getSongArtistMap();
		genreMap = getSongGenreMap();
		genres = getGenres();
		pushToFirebase();
	}
	
	public void pushToFirebase(){
		fbc.addSongsToFirebase(songs);
		fbc.addSongsToFirebaseByGenre(genreMap);
		fbc.addSongsToFirebaseByArtistMap(artistMap);
		fbc.addGenresToFirebase(genres);
	}

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
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	private ArrayList<String> getGenres(){
		ArrayList<String> genres = new ArrayList<String>(genreMap.keySet());
		
		for(Song song : songs){
			String genre = song.getGenre();
			if(!genre.equals("") && !genres.contains(genre)){
				genres.add(genre);
			}
		}
		return genres;
	}
	
	private Map<String, ArrayList<Song>> getSongArtistMap(){
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
	
	
	private Map<String, ArrayList<Song>> getSongGenreMap(){
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
}

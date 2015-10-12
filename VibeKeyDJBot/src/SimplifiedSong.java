import java.util.ArrayList;

/**
 * Class used to represent a Song class in a simplified manner for Firebase
 * @author kneislsj
 *
 */
public class SimplifiedSong {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private String path;
	
	public SimplifiedSong(){
		
	}
	
	public SimplifiedSong(String title, String artist, String album, String genre, String path){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.path = path;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getAblum(){
		return album;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public String getPath(){
		return path;
	}
	
	public Song getSong(ArrayList<Song> songs){ //expensive, but considering we're saving to firebase, can't rely on song reference
		for(Song song : songs){
			if(song.getPath().equals(this.path)){
				return song;
			}
		}
		return null;
	}
}

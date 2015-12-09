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
	private int netVotes;
	private int totalVotes;
	private long length;
	
	
	public SimplifiedSong(){
		
	}
	
	public SimplifiedSong(String title, String artist, String album, String genre, String path, int netVotes, int totalVotes, long length){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.path = path;
		this.netVotes = netVotes;
		this.totalVotes = totalVotes;
		this.length = length;
		
	}
	
	/*
	@Override
	public int hashCode(){
		return title.hashCode() ^ artist.hashCode() ^ album.hashCode() ^ genre.hashCode() ^ path.hashCode();
	}
	*/
	
	public String getTitle(){
		return title;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getAlbum(){
		return album;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public String getPath(){
		return path;
	}
	

	public long getLength(){
		return length;
	}
	
	public int getTotalVotes(){
		return totalVotes;
	}
	public int getNetVotes(){
		return netVotes;
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

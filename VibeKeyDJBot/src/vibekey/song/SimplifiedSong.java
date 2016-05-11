package vibekey.song;
import java.util.ArrayList;

/**
 * Class used to represent a Song class in a simplified manner for Firebase
 * @author kneislsj
 *
 */
public class SimplifiedSong{
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
		return title != null ? title : "";
	}
	
	public String getArtist(){
		return artist != null ? artist : "";
	}
	
	public String getAlbum(){
		return album != null ? album : "";
	}
	
	public String getGenre(){
		return genre != null ? genre : "";
	}
	
	public String getPath(){
		return path != null ? path : "";
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + (int) (length ^ (length >>> 32));
		result = prime * result + netVotes;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + totalVotes;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimplifiedSong))
			return false;
		SimplifiedSong other = (SimplifiedSong) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (length != other.length)
			return false;
		if (netVotes != other.netVotes)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (totalVotes != other.totalVotes)
			return false;
		return true;
	}
}

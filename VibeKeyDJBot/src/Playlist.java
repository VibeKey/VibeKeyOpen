import java.util.ArrayList;

public class Playlist {
	String name;
	ArrayList<Song> songs = new ArrayList<Song>();
	
	
	
	
	public long getTotalPlayTime(){
		long totalTime = 0;
		for(Song song : songs){
			totalTime+=song.getLength();
		}
		return totalTime;
	}
	
	public void addSong(Song newSong){
		songs.add(newSong);
	}
	
	public void removeSongAtIndex(int index){
		songs.remove(index);
	}
	
	public ArrayList<Song> getSongs(){
		return songs;
	}
	
	public void setSongs(ArrayList<Song> newSongs){
		this.songs = newSongs;
	}
	
	public Song getRandomSong(){
		return songs.get(RandomWrapper.nextInt(songs.size()));
	}
	
	
	
}

package vibekey.playlist;
import java.util.ArrayList;

import com.firebase.client.Firebase;

import vibekey.firebase.Syncable;
import vibekey.song.Song;
import vibekey.song.SongList;
import vibekey.util.RandomWrapper;

public class Playlist extends SongList implements Syncable {
	private String name;
	private ArrayList<Song> songs = new ArrayList<Song>();
	
	
	
	
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
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
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

	@Override
	public void setupSyncable(Firebase ref) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

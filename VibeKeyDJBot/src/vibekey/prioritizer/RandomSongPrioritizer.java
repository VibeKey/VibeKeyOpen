package vibekey.prioritizer;

import java.util.ArrayList;

import vibekey.song.Song;
import vibekey.util.RandomWrapper;

public class RandomSongPrioritizer extends SongPrioritizer {
	public Song getSong(ArrayList<Song> availableSongs){
		int songNum = RandomWrapper.nextInt(availableSongs.size());
		return availableSongs.get(songNum);
	}
}

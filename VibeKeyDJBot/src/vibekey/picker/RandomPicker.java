package vibekey.picker;

import java.util.ArrayList;

import vibekey.song.Song;
import vibekey.util.RandomWrapper;

public class RandomPicker extends Picker {
	public Song getSong(ArrayList<Song> availableSongs){
		int songNum = RandomWrapper.nextInt(availableSongs.size());
		return availableSongs.get(songNum);
	}
}

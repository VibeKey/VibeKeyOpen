package vibekey.picker;

import java.util.ArrayList;

import vibekey.song.Song;

public abstract class Picker {
	public abstract Song getSong(ArrayList<Song> availableSongs);
}

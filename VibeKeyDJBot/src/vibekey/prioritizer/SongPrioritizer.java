package vibekey.prioritizer;

import java.util.ArrayList;

import vibekey.song.Song;

public abstract class SongPrioritizer {
	public abstract Song getSong(ArrayList<Song> availableSongs);
}

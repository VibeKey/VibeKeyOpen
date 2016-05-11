package vibekey.filter;

import java.util.ArrayList;

import vibekey.song.Song;

public abstract class Filter {
	
	public abstract ArrayList<Song> filter(ArrayList<Song> songs);
	
}
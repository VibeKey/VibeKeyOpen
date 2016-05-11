package vibekey.filter;

import java.util.ArrayList;

import vibekey.song.Song;

public class NoFilter extends Filter{

	@Override
	public ArrayList<Song> filter(ArrayList<Song> songs) {
		return songs;
	}

}

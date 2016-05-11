package vibekey.filter;

import java.util.ArrayList;

import vibekey.song.Song;

public class GenreFilter extends Filter{
	
	public String genre;
	
	public GenreFilter (String genre){
		this.genre = genre;
	}

	@Override
	public ArrayList<Song> filter(ArrayList<Song> songs) {
		ArrayList<Song> ret = new ArrayList<Song>();
		for(Song song : songs){
			if(song.getGenre().equals(this.genre)){
				ret.add(song);
			}
		}
		
		return ret;
	}

}

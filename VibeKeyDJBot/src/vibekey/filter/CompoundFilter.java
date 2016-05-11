package vibekey.filter;

import java.util.ArrayList;
import java.util.LinkedList;

import vibekey.song.Song;

public class CompoundFilter extends Filter {
	
	public LinkedList<Filter> filters = new LinkedList<Filter>();
	
	public void addFilter(Filter f){
		filters.add(f);
	}
	
	public void clearFilters(){
		filters.clear();
	}

	@Override
	public ArrayList<Song> filter(ArrayList<Song> songs) {
		for(Filter f : filters){
			songs = f.filter(songs);
		}
		return songs;
	}

}

package vibekey.filter;

import java.util.ArrayList;
import java.util.Comparator;

import vibekey.song.Song;

public class VotesFilter extends Filter {
	
	int num;
	
	public VotesFilter(int num){
		this.num = num;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Song> filter(ArrayList<Song> songs) {
		ArrayList<Song> songsClone = (ArrayList<Song>)songs.clone(); //Just an extra safety!
		songsClone.sort( new Comparator<Song>(){
		    public int compare(Song x, Song y) {
		        return Integer.compare(x.netVotes,y.netVotes);
		    }
		});
		
		return new ArrayList<Song>(songsClone.subList(0, num));
	}

}

package vibekey.filter;

import java.util.ArrayList;

import vibekey.playlist.Playlist;
import vibekey.song.Song;

public class PlaylistFilter extends Filter {
	
	public Playlist playlist;
	
	public PlaylistFilter(Playlist p){
		this.playlist = p;
	}

	@Override
	public ArrayList<Song> filter(ArrayList<Song> songs) {
		ArrayList<Song> ret = new ArrayList<Song>();
		for(Song song : songs){
			if(this.playlist.getSongs().contains(song)){
				ret.add(song);
			}
		}
		
		return ret;
	}
	
}

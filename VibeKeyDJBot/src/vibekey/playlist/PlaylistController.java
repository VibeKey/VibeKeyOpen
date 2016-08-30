package vibekey.playlist;
import java.util.ArrayList;

import com.firebase.client.Firebase;

import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.syncable.SyncableContainer;
import vibekey.util.RandomWrapper;

public class PlaylistController extends SyncableContainer{
	public ArrayList<Playlist> allPlaylists = new ArrayList<Playlist>();
	
	
	
	public PlaylistController(SongDatabase songDatabase, Firebase ref){
		super(ref.child("playlists"));
		for(int i=0;i<3;i++){ //temporary adding of random playlists for testing
			ArrayList<Song> playlistSongs = new ArrayList<Song>();
			for(int j=0;j<5;j++){
				playlistSongs.add(songDatabase.songs.get(RandomWrapper.nextInt(songDatabase.songs.size())));
			}
			Playlist newPlaylist = new Playlist();
			
			newPlaylist.setName("Playlist " + (i + 1));
			
			newPlaylist.setSongs(playlistSongs);
			allPlaylists.add(newPlaylist);
		}
		this.hasChanged();
	}
	
	public Playlist search(String name) throws InvalidPlaylistException {
		for(Playlist p : allPlaylists){
			if(p.getName().equals(name)){
				return p;
			}
		}
		throw new InvalidPlaylistException();
	}
}

import java.util.ArrayList;

public class PlaylistController {
	ArrayList<Playlist> allPlaylists = new ArrayList<Playlist>();
	
	
	
	public PlaylistController(){
		for(int i=0;i<3;i++){ //temporary adding of random playlists for testing
			ArrayList<Song> playlistSongs = new ArrayList<Song>();
			for(int j=0;j<5;j++){
				playlistSongs.add(SongDatabase.songs.get(RandomWrapper.nextInt(SongDatabase.songs.size())));
			}
			Playlist newPlaylist = new Playlist();
			
			newPlaylist.setName("Playlist " + (i + 1));
			
			newPlaylist.setSongs(playlistSongs);
			allPlaylists.add(newPlaylist);
		}
		updateFirebase();
	}
	
	public void updateFirebase(){
		FirebaseCommunicator.setPlaylists(allPlaylists);
	}
}

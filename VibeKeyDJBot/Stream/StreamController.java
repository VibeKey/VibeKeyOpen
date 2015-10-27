import java.io.IOException;
import java.util.ArrayList;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class StreamController {
	String genreFilter;
	String playMode;
	Song curPlaying;
	Libshout icecast;
	SongQueue queue;
	PlaylistController playlistController;
	PlaySchedule playSchedule;
	String playlistName;
	
	public StreamController(){
		icecast = initializeIcecast();
		this.queue = new SongQueue();
		this.playlistController = new PlaylistController();
		this.playSchedule = new PlaySchedule();
	}
	
	private boolean isInGenreMode(){
		return playMode != null && genreFilter != null && playMode.equals("genre");
	}

	private boolean isInPlaylistMode(){
		return playMode != null && playlistName != null && playMode.equals("playlist");
	}
	
	public void fillQueue(){
		while(queue.size() < 5){
			
			int playMode = playSchedule.getCurPlayMode();
			//if(playMode == ScheduleItem.PLAYMODE_NONE)
			if(isInGenreMode()){
				ArrayList<Song> genreSongList = SongDatabase.genreMap.get(genreFilter);
				if(genreSongList != null){
					int songNum = RandomWrapper.nextInt(genreSongList.size());
					queue.addToQueue(genreSongList.get(songNum));
					continue;
				}
			}
			
			if(isInPlaylistMode()){
				boolean foundSong = false;
				for(Playlist playlist : playlistController.allPlaylists){
					if(playlist.name.equals(playlistName)){
						if(playlist.songs.size() > 0){
							int songNum = RandomWrapper.nextInt(playlist.songs.size());
							foundSong=true;
							queue.addToQueue(playlist.songs.get(songNum));
							break;
						}
					}
				}
				if(foundSong){
					continue;
				}
			}
			
			int songNum = RandomWrapper.nextInt(SongDatabase.songs.size()); //TODO: add more than random songs
			queue.addToQueue(SongDatabase.songs.get(songNum));
		}
	}
	
	public Song getNextSong(){
		fillQueue();
		Song song = queue.popFromQueue();
		fillQueue();
		return song;
	}
	
	public void playNextSong(){
		Song song = getNextSong();
		
		String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
		System.out.println(nowPlaying);
		FirebaseCommunicator.setFirebaseNowPlaying(song);
		curPlaying = song;
		//boolean finishedSucessfully = song.streamSong(icecast);
		
		song.streamSong(icecast);
	}
	
	
	public void close(){
		icecast.close();
	}
	
	Libshout initializeIcecast(){
		try {
			Libshout icecast;
			icecast = new Libshout();
			icecast.setHost("localhost");
			icecast.setPort(8000);
			icecast.setProtocol(Libshout.PROTOCOL_HTTP);
			icecast.setPassword("ImASource!");
			icecast.setMount("/djbot");
			icecast.setFormat(Libshout.FORMAT_MP3);
			icecast.open();
			return icecast;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

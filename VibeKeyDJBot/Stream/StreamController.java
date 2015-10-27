import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
	
	public void refillQueue(){
		queue.emptyQueue();
		this.fillQueue();
	}
	
	public void fillQueue(){
		while(queue.size() < 5){
			if(isInGenreMode()){
				ArrayList<Song> genreSongList = SongDatabase.genreMap.get(genreFilter);
				if(genreSongList != null){
					int songNum = RandomWrapper.nextInt(genreSongList.size());
					queue.addToQueue(genreSongList.get(songNum));
					continue;
				}
			} else if(isInPlaylistMode()){
				boolean foundSong = false;
				for(Playlist playlist : playlistController.allPlaylists){
					if(playlist.getName().equals(playlistName)){
						if(playlist.getSongs().size() > 0){
							int songNum = RandomWrapper.nextInt(playlist.getSongs().size());
							foundSong=true;
							queue.addToQueue(playlist.getSongs().get(songNum));
							break;
						}
					}
				}
				if(foundSong){
					continue;
				}
			} else { //else, check the schedule if not in a forced mode
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.SECOND, queue.getTotalTime());
				ScheduleItem curSched = playSchedule.getScheduleItemAtTime(calendar.getTime());
				if(curSched != null){
					if(curSched.getPlayMode() == ScheduleItem.PLAYMODE_GENRE && curSched.getGenre() != null && !curSched.getGenre().equals("")){
						ArrayList<Song> genreSongList = SongDatabase.genreMap.get(curSched.getGenre());
						if(genreSongList != null){
							int songNum = RandomWrapper.nextInt(genreSongList.size());
							queue.addToQueue(genreSongList.get(songNum));
							continue;
						}
					} else if(curSched.getPlayMode() == ScheduleItem.PLAYMODE_PLAYLIST && curSched.getPlaylist() != null && !curSched.getPlaylist().equals("")){
						boolean foundSong = false;
						for(Playlist playlist : playlistController.allPlaylists){
							if(playlist.getName().equals(curSched.getPlaylist())){
								if(playlist.getSongs().size() > 0){
									int songNum = RandomWrapper.nextInt(playlist.getSongs().size());
									foundSong=true;
									queue.addToQueue(playlist.getSongs().get(songNum));
									break;
								}
							}
						}
						if(foundSong){
							continue;
						}
					}
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
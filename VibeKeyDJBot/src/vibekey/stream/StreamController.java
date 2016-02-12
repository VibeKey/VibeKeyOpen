package vibekey.stream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.gmail.kunicins.olegs.libshout.Libshout;

import vibekey.firebase.FirebaseCommunicator;
import vibekey.playlist.Playlist;
import vibekey.playlist.PlaylistController;
import vibekey.schedule.PlaySchedule;
import vibekey.schedule.ScheduleItem;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.song.SongQueue;
import vibekey.util.RandomWrapper;

public class StreamController {
	public String playMode;
	public Song curPlaying;
	public Libshout icecast;
	public SongQueue queue;
	public PlaylistController playlistController;
	public PlaySchedule playSchedule;
	public Boolean stopServer = false; //easy way to stop the server, turn it to true
	
	public StreamController(){
		icecast = initializeIcecast();
		this.queue = new SongQueue();
		this.playlistController = new PlaylistController();
		this.playSchedule = new PlaySchedule();
	}
	
	public void refillQueue(){
		queue.emptyQueue();
		this.fillQueue();
	}
	
	public void fillQueue(){
		while(queue.size() < SongQueue.FILL_SIZE){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, queue.getTotalTime());
			ScheduleItem curSched = playSchedule.getScheduleItemAtTime(calendar.getTime());
			if(curSched != null){
				if(curSched.getPlayMode() == ScheduleItem.PLAYMODE_GENRE && curSched.getGenre() != null && !curSched.getGenre().equals("")){
					ArrayList<Song> genreSongList = SongDatabase.getSongsInGenre(curSched.getGenre());
					if(genreSongList != null){
						Song song = RandomWrapper.nextSongWeighted(genreSongList);
						queue.addToQueue(song);
						continue;
					}
				} else if(curSched.getPlayMode() == ScheduleItem.PLAYMODE_PLAYLIST && curSched.getPlaylist() != null && !curSched.getPlaylist().equals("")){
					boolean foundSong = false;
					for(Playlist playlist : playlistController.allPlaylists){
						if(playlist.getName().equals(curSched.getPlaylist())){
							if(playlist.getSongs().size() > 0){
								Song song = RandomWrapper.nextSongWeighted(playlist.getSongs());
								foundSong=true;
								queue.addToQueue(song);
								break;
							}
						}
					}
					if(foundSong){
						continue;
					}
				}
			}
			/*
			int totalNetVotes = 0;
			
			for(Song song : SongDatabase.songs){
				totalNet+=song.netVotes;
			}
			for(Song song : SongDatabase.songs){
				if(RandomWrapper.nextDouble() < ((Double) song.netVotes)
			}
			*/	
			Song song = RandomWrapper.nextSongWeighted(SongDatabase.songs); //TODO: add more than random songs
			queue.addToQueue(song);
		}
	}
	
	public Song getNextSong(){
		fillQueue();
		Song song = queue.popFromQueue();
		fillQueue();
		return song;
	}
	
	public void play(){
		while(!this.stopServer){
			Song song = getNextSong();
			
			String nowPlaying = "Now playing:  \"" + song.getTitle() + "\" by " + song.getArtist() + "   (" + song.getGenre() + ")";
			System.out.println(nowPlaying);
			FirebaseCommunicator.setFirebaseNowPlaying(song);
			curPlaying = song;
			//boolean finishedSucessfully = song.streamSong(icecast);
			
			song.streamSong(icecast);
		}
	}
	
	
	public void close(){
		icecast.close();
	}
	
	public Libshout initializeIcecast(){
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
	
	public void stopServer(){
		this.stopServer = true; //tell stream controller to throw stop exception
		this.curPlaying.stop();
	}
}
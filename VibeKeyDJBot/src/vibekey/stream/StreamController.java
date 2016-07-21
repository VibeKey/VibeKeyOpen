package vibekey.stream;
import java.io.IOException;
import java.util.Date;

import com.gmail.kunicins.olegs.libshout.Libshout;

import vibekey.firebase.FirebaseCommunicator;
import vibekey.picker.NoPickException;
import vibekey.picker.VotePicker;
import vibekey.playlist.PlaylistController;
import vibekey.schedule.PlaySchedule;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.song.SongQueue;

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
		this.queue = new SongQueue(FirebaseCommunicator.rootRef);
		this.playlistController = new PlaylistController(FirebaseCommunicator.rootRef);
		this.playSchedule = new PlaySchedule(FirebaseCommunicator.rootRef,new VotePicker());
	}
	
	public void refillQueue() throws NoPickException{
		queue.emptyQueue();
		this.fillQueue();
		queue.setChanged(true);
	}
	
	public void fillQueue() throws NoPickException{
		while(queue.size() < SongQueue.FILL_SIZE){
			long startTime = queue.getTotalTime()*1000 + System.currentTimeMillis();
			Song song = playSchedule.getSongAtTime(SongDatabase.songs, new Date(startTime));
			queue.addToQueue(song);
		}
		queue.setChanged(true);
	}
	
	public Song getNextSong() throws NoPickException{
		fillQueue();
		Song song = queue.popFromQueue();
		fillQueue();
		queue.setChanged(true);
		return song;
	}
	
	public void play() throws NoPickException{
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

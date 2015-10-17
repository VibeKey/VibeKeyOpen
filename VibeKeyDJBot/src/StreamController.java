import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class StreamController {
	String genreFilter;
	String playMode;
	Song curPlaying;
	Random rand;
	Libshout icecast;
	SongQueue queue;
	
	public StreamController(SongQueue queue){
		rand = new Random(System.currentTimeMillis());
		icecast = initializeIcecast();
		this.queue = queue;
	}
	
	public void fillQueue(){
		while(queue.size() < 5){
			if(playMode != null && genreFilter != null && playMode.equals("genre")){ //if in genre mode, play random song from that genre
				ArrayList<Song> genreSongList = SongDatabase.genreMap.get(genreFilter);
				if(genreSongList != null){
					int songNum = rand.nextInt(genreSongList.size());
					queue.addToQueue(genreSongList.get(songNum));
					continue;
				}
			}
			
			
			
			
			int songNum = rand.nextInt(SongDatabase.songs.size()); //TODO: add more than random songs
			queue.addToQueue(SongDatabase.songs.get(songNum));
		}
	}
	
	public void playNextSong(){
		fillQueue();
		Song song = queue.popFromQueue();
		
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

import java.io.IOException;
import java.util.Random;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class StreamController {
	String genreFilter;
	String playMode;
	Song curPlaying;
	Random rand;
	SongDatabase songDB;
	Libshout icecast;
	
	public StreamController(SongDatabase songDB){
		this.songDB = songDB;
		rand = new Random(System.currentTimeMillis());
		icecast = initializeIcecast();
	}
	
	public void playNextSong(){
		Song song;
		int songNum;
		
		if(playMode != null && genreFilter != null && playMode.equals("forceGenre")){
			songNum = rand.nextInt(songDB.genreMap.get(genreFilter).size());
			song = songDB.genreMap.get(genreFilter).get(songNum);
		}else{
			songNum = rand.nextInt(songDB.songs.size());
			song = songDB.songs.get(songNum);
		}
		
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

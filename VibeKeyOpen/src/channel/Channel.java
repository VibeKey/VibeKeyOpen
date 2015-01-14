package channel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.gmail.kunicins.olegs.libshout.Libshout;

public class Channel {

	
	// Demo code to play a song through icecast
	public static void addSong(String filename) {
		byte[] buffer = new byte[1024];
		InputStream mp3 = null;
		try {
			mp3 = new BufferedInputStream(new FileInputStream(new File(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Libshout icecast;
		try {
			icecast = new Libshout();
		
			icecast.setHost("localhost");
			icecast.setPort(8000);
			icecast.setProtocol(Libshout.PROTOCOL_HTTP);
			icecast.setPassword("MotherDJBot");
			icecast.setMount("/radio");
			icecast.setFormat(Libshout.FORMAT_MP3);
			icecast.open();
			int read = mp3.read(buffer);
			while (read > 0) {
			    icecast.send(buffer, read);
			    read = mp3.read(buffer);
			}
			icecast.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mp3.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
/* For testing
	public static void main(String[] args) {
		Channel.addSong(args[0]);
	}
*/
}

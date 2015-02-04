package channel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.gmail.kunicins.olegs.libshout.Libshout;

import core_objects_abstract.Stream;
import djbot.DJBot;

public class Channel extends Stream {

	private String cName;
	private enum State {CLOSE, IDLE, PLAY, PAUSE};
	private State state;
	
	/** Constructor that sets the name of the channel. */
	public Channel(String name, DJBot bot) {
		super(bot);
		this.cName = name;
		this.state = State.CLOSE;
	}
	
	
	// Demo code to play a song through icecast
	@Override
	public void play() {
		String filename = this.bot.getSong().getFilePath();
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
			icecast.setMount("/" + this.cName);
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

	@Override
	public void pause() {
		if (this.state == State.PLAY)
			this.state = State.PAUSE;		
	}

	@Override
	public void resume() {
		if (this.state == State.PAUSE)
			this.state = State.PLAY;
	}

	@Override
	public void stop() {
		if (this.state != State.CLOSE)
			this.state = State.IDLE;		
	}


	@Override
	public void open() {
		if (this.state == State.CLOSE)
			this.state = State.IDLE;
	}
}

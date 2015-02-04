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
//	private enum State {CLOSE, IDLE, PLAY, PAUSE};
	private State closedState;
	private State idleState;
	private State playState;
	private State pauseState;
	
	private State state = closedState;
	
	Libshout icecast;
	
	/** Constructor that sets the name of the channel. */
	public Channel(String name, DJBot bot) {
		super(bot);
		this.cName = name;
//		this.state = new ClosedState();
		closedState = new ClosedState(this);
		idleState = new IdleState(this);
		playState = new PlayState(this);
		pauseState = new PauseState(this);
	}
	
	
	// Demo code to play a song through icecast
//	@Override
//	public void play() {
//		String filename = this.bot.getSong().getFilePath();
//		byte[] buffer = new byte[1024];
//		InputStream mp3 = null;
//		try {
//			mp3 = new BufferedInputStream(new FileInputStream(new File(filename)));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Libshout icecast;
//		try {
//			icecast = new Libshout();
//		
//			icecast.setHost("localhost");
//			icecast.setPort(8000);
//			icecast.setProtocol(Libshout.PROTOCOL_HTTP);
//			icecast.setPassword("MotherDJBot");
//			icecast.setMount("/" + this.cName);
//			icecast.setFormat(Libshout.FORMAT_MP3);
//			icecast.open();
//			int read = mp3.read(buffer);
//			while (read > 0) {
//			    icecast.send(buffer, read);
//			    read = mp3.read(buffer);
//			}
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			mp3.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
/* For testing
	public static void main(String[] args) {
		Channel.addSong(args[0]);
	}
*/
	
	public State getClosedState() {
		return this.closedState;
	}
	
	public State getIdleState() {
		return this.idleState;
	}
	
	public State getPlayState() {
		return this.playState;
	}
	
	public State getPauseState() {
		return this.pauseState;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return this.cName;
	}
	
	public void setIcecast(Libshout icecast) {
		this.icecast = icecast;
	}
	
	public Libshout getIcecast() {
		return this.icecast;
	}
	
	public DJBot getDJBot() {
		return this.bot;
	}
}

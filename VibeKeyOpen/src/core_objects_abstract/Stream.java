package core_objects_abstract;

import djbot.DJBot;

public abstract class Stream {

	/** Channel ID as referenced by database */
	protected int channelID;
	/** Local Data */
	// List of songs to stream.
	protected DJBot bot;

	/** Generic constructor. Calls final constructor with an empty list. */
	public Stream(int ChannelID) {
		this(ChannelID, new DJBot());
	}

	/** Constructor sets the list of songs based on input parameters. */
	public Stream(int channelID, DJBot bot) {
		this.bot = bot;
		this.channelID = channelID;
		
	}
	
	public DJBot getDJBot(){
		return bot;
	}

}

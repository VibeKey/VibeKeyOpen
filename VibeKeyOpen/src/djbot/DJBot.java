package djbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core_objects_abstract.Song;

/**
 * AI that intelligently selects songs based on event and
 * preference handlers.
 * 
 * @author Rose Reatherford
 * @date Created: 12/17/2014; Last Updated: 12/31/2014
 */
public class DJBot {
	/** Constants **/
	private static int MIN_SIZE = 10;
	
	/** Handlers **/
	// The list of preference handlers.
	private List<BotPlugin> independentPlugins;
	// The list of events that override other behavior.
	private List<DependentBotPlugin> dependentPlugins;
	
	/** Song Planning **/
	// The queue of song ids that DJ Bot will play.
	private List<Song> songList;
	// A HashMap of media search terms that DJ-Bot will use.
	private HashMap<String, String> mediaTerms;
	
	public DJBot() {
		this.independentPlugins = new ArrayList<BotPlugin>();
		this.dependentPlugins = new ArrayList<DependentBotPlugin>();
	}
	
	public String wake() {
		if (songList.size() < MIN_SIZE) addSongs();
		
		return songList.remove(0).getFilePath();
	}
	
	public void addSongs() {
		while (songList.size() < MIN_SIZE) {
			
		}
	}
}

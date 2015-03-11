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
	public static String CHANGE = "needNewSearch";
	
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
	
	/** Song Holding */
	// Buffer of extra songs so they do not clog the queue.
	private List<Song> buffer;
	
	public DJBot() {
		this.independentPlugins = new ArrayList<BotPlugin>();
		this.dependentPlugins = new ArrayList<DependentBotPlugin>();
	}
	
	/**
	 * Gets the next song in queue.
	 * @return
	 */
	public Song getSong() {
		wake();
		Song newSong = songList.remove(0);
		changeRankings();
		return newSong;
	}
	
	/**
	 * Updates the rank 
	 */
	private void changeRankings() {
		for (int i = 0; i < songList.size(); i++) 
			songList.get(i).changeRank(i);
	}

	/**
	 * Creates a thread to add new songs, run plugins, and searches.
	 */
	private void wake() {
		runPlugins();
		if (songList.size() < MIN_SIZE) addSongs();
	}
	
	private void addSongs() {
		while (songList.size() < MIN_SIZE) {
			if (buffer.size() > 0) songList.add(buffer.remove(0));
			else runSearch();
		}
	}
	
	private void runSearch() {
		System.out.println("I am running a search.");
	}

	private void runPlugins() {
		for (BotPlugin plugin : this.dependentPlugins) plugin.modifyTerms(mediaTerms, songList);
		for (BotPlugin plugin : this.independentPlugins) plugin.modifyTerms(mediaTerms, songList);
		
		if (mediaTerms.containsKey(CHANGE)) {
			mediaTerms.remove(CHANGE);
			runSearch();
		} 
		
		if (buffer.size() < MIN_SIZE) runSearch();
	}
}

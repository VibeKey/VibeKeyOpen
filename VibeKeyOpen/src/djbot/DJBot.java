package djbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import primary_manager.VibeKey;
import threads.controlled.ControlledRunner;
import core_objects_abstract.Song;

/**
 * AI that intelligently selects songs based on event and
 * preference handlers.
 * 
 * @author Rose Reatherford
 * @date Created: 12/17/2014; Last Updated: 12/31/2014
 */
public class DJBot extends ControlledRunner {
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
		return songList.remove(0);
	}
	
	/**
	 * Updates the rank 
	 */
	private void changeRankings() {
		for (int i = 0; i < songList.size(); i++) 
			songList.get(i).changeRank(i);
	}

	
	private void wake() {
		VibeKey.manager.takeThread(this);
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
		boolean changed = false;
		
		for (BotPlugin plugin : this.dependentPlugins)
			if (plugin.modifyTerms(mediaTerms, songList) && !changed) changed = true;
		for (BotPlugin plugin : this.independentPlugins) plugin.modifyTerms(mediaTerms, songList);
		
		if (changed) {
			runSearch();
		} 
		
		if (buffer.size() < MIN_SIZE) runSearch();
	}

	@Override
	/**
	 * Creates a thread to add new songs, run plugins, and searches.
	 */
	public void execute() {
		runPlugins();
		if (songList.size() < MIN_SIZE) addSongs();
		changeRankings();
	}
}

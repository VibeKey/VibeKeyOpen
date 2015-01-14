package djbot;

import java.util.HashMap;
import java.util.List;

import core_objects_abstract.Song;

/**
 * Independent bot plugin that does not rely on other plugin functionality.
 * 
 * @author Rose Reatherford
 * @date Created 12/31/2014
 *
 */
public abstract class BotPlugin {
	/**
	 * Used by DJ Bot to modify search behavior.
	 * @param terms 		The list of current search terms, 
	 * 						should be modified.
	 * @param currentSongs 	The current list of songs, 
	 * 						typically not modified.
	 */
	public abstract void modifyTerms(HashMap<String,String> terms, List<Song> currentSongs);
	
	/**
	 * Changes the behavior of the plugin, called by dependent plugins.
	 * @param changeTerms 	List of terms that notify this plugin 
	 * 						about what changes to make.
	 */
	protected abstract void changePluginBehavior(HashMap<String, String> changeTerms);
}

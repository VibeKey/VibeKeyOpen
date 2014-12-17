package djbot;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

/**
 * AI that intelligently selects songs based on event and
 * preference handlers.
 * 
 * @author Rose Reatherford
 * @date Created: 12/17/2014; Last Updated: 12/17/2014
 */
public class DJBot {
	/** Handlers **/
	// The list of preference handlers.
	private List<Preferences> preferenceBehaviors;
	// The list of events that override other behavior.
	private List<OverrideEventBehavior> overrideEventBehaviors;
	
	/** Preferences **/
	// Events that effect how DJ Bot plans the song list.
	private List<BotEvent> events;
	
	/** Song Planning **/
	// The queue of song ids that DJ Bot will play.
	private Queue<String> songList;
	// A HashMap of media search terms that DJ-Bot will use.
	private HashMap<String, String> mediaTerms;
}

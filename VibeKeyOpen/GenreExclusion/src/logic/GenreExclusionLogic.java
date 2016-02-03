package logic;

import java.util.HashMap;
import java.util.List;

import core_objects_abstract.Song;
import djbot.BotPlugin;

public class GenreExclusionLogic extends BotPlugin {
	private String genre;

	@Override
	public void modifyTerms(HashMap<String, String> terms,
			List<Song> currentSongs) {
		
	}

	@Override
	protected void changePluginBehavior(HashMap<String, String> changeTerms) {
		
	}

}

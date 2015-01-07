package djbot;

import java.util.ArrayList;
import java.util.List;

public abstract class DependentBotPlugin extends BotPlugin {
	protected List<BotPlugin> dependencies;
	
	protected DependentBotPlugin () {
		dependencies = new ArrayList<BotPlugin>();
	}

	protected abstract void run();
}

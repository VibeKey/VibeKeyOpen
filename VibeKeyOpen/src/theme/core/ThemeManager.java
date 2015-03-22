package theme.core;

import java.util.HashMap;

public final class ThemeManager {
	/** List of all registered object. */
	private HashMap<String, Class<? extends ThemeObject>> objects;
	/** The global theme. */
	private ThemeObject globalTheme;
	
	public ThemeManager() {
		objects = new HashMap<String, Class<? extends ThemeObject>>();
		globalTheme = new GlobalTheme();
	}
	
	/**
	 * Registers the new object type.
	 * @return True if this succeeded, false otherwise. 
	 * 			Should only return false if object is already registered.
	 */
	public boolean registerNewObjectType(String name, Class<? extends ThemeObject> obj) {
		if (objects.containsKey(name)) return false;
		
		objects.put(name, obj);
		return true;
	}
	
	/**
	 * Renders the JSP of all the pages. Should be called infrequently. 
	 */
	public void render() {
		globalTheme.convert();
	}
}

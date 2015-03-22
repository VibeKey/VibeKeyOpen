package theme.core;

import java.util.HashMap;

public abstract class ThemeObject {
	// Object Properties
	/** The x-coordinate of the upper left-hand corner. */
	protected int x;
	/** The y-coordinate of the upper left-hand corner. */
	protected int y;
	protected int height;
	protected int width;
	
	// Misc. Properties
	/** ID of this object. */
	protected int id;
	/** HashMap of all children, given by their IDs */
	protected HashMap<String, ThemeObject> children;
	
	/**
	 * Does all basic JSP conversions.
	 */
	protected void convert() {
		convertToJSP();
	}
	
	/**
	 * Does individual JSP conversions.
	 */
	protected abstract void convertToJSP();
}

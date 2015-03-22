package theme.core;

import java.util.HashMap;

public abstract class ThemeObject {
	// Object Properties
	/** The x-coordinate of the upper left-hand corner. */
	protected int x;
	/** The y-coordinate of the upper left-hand corner. */
	protected int y;
	/** The height of the object, in pixels. */
	protected int height;
	/** The width of the object, in pixels. */
	protected int width;
	
	// Misc. Properties
	/** The parent of this object. */
	protected ThemeObject parent;
	/** ID of this object. */
	protected String id;
	/** HashMap of all children, given by their IDs */
	protected HashMap<String, ThemeObject> children;
	
	protected ThemeObject() {
		this.children = new HashMap<String, ThemeObject>();
		this.x = 0;
		this. y = 0;
		this.height = 0;
		this.width = 0;
	}
	
	public void makeObject(String info, ThemeObject parent) {
		this.parent = parent;
		
		String temp;
		if ((temp = findParam(info, " id:")) != null) id = temp;
		if ((temp = findParam(info, " x:")) != null) x = Integer.parseInt(temp);
		if ((temp = findParam(info, " y:")) != null) y = Integer.parseInt(temp);
		if ((temp = findParam(info, " width:")) != null) width = Integer.parseInt(temp);
		if ((temp = findParam(info, " height:")) != null) height = Integer.parseInt(temp);
		
		parseUniqueFields(info);
		if (parent != null) parent.registerChild(this); 
	}
	
	/**
	 * Finds the given param and returns it.
	 * @param info The data it is searching through.
	 * @param toFind The field to find the param for.
	 * @return The param if it exists, null otherwise.
	 */
	protected String findParam(String info, String toFind) {
		if (info.contains(toFind)) {
			
			int start = info.indexOf(toFind) + toFind.length();
			int end = info.length();
			for (int i = start; i < info.length(); i++) {
				if (info.charAt(i) == ' ' || info.charAt(i) == '>') {
					end = i;
					break;
				}
			}
			
			String param = info.substring(start, end);
			
			System.out.print(toFind + " " + param);
			return param;
		}
		
		return null;
	}
	
	protected abstract void parseUniqueFields(String info);
	
	/**
	 * Gets the ID of the function.
	 * @return The ID of the function.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Gets the parent object of this object.
	 * @return The parent of this object or null if this is a global theme.
	 */
	public ThemeObject getParent() {
		return this.parent;
	}
	
	/**
	 * Registers the child to the parent.
	 * @param child
	 */
	public void registerChild(ThemeObject child) {
		String childID = child.getID();
		if (childID == null) childID = "unknown" + children.values().size();
		
		children.put(childID, child);
	}
	
	/**
	 * Changes the ID and notifies the parent unless the parent 
	 * 		was the caller.
	 * @param newID
	 * @param parent
	 */
	public void setID(String newID, Object caller) {
		if (!caller.equals(parent) && parent != null) 
			parent.childIDChange(newID, this);
		this.id = newID;
	}
	
	/**
	 * Notify the parent that the id of this object was changed.
	 * @param newID the new ID of the child.
	 * @param child The child whose ID is changing.
	 */
	protected void childIDChange(String newID, ThemeObject child) {
		children.remove(child.getID());
		children.put(newID, child);
	}
	
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

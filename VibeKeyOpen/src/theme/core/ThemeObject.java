package theme.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ThemeObject {
	// Global Variables
	protected String ID = "id";
	protected String X = "x";
	protected String Y = "y";
	protected String HEIGHT = "height";
	protected String WIDTH = "width";
	
	// Object Properties
	protected HashMap<String, String> fields;
	protected List<String> searchFields;
	
	// Misc. Properties
	/** The parent of this object. */
	protected ThemeObject parent;
	/** HashMap of all children, given by their IDs */
	protected HashMap<String, ThemeObject> children;
	
	protected ThemeObject() {
		this.children = new HashMap<String, ThemeObject>();
		this.fields = new HashMap<String, String>();
		this.searchFields = new ArrayList<String>();
		
		this.searchFields.add(ID);
		this.searchFields.add(X);
		this.searchFields.add(Y);
		this.searchFields.add(HEIGHT);
		this.searchFields.add(WIDTH);
		registerUniqueFields();
	}
	
	public void makeObject(String info, ThemeObject parent) {
		this.parent = parent;
		
		for (String field : searchFields) {
			String param = findParam(info, field);
			if (param != null) fields.put(field, param);
		}
		
		if (parent != null) parent.registerChild(this); 
	}
	
	/**
	 * Finds the given param and returns it.
	 * @param info The data it is searching through.
	 * @param toFind The field to find the param for.
	 * @return The param if it exists, null otherwise.
	 */
	private String findParam(String info, String toFind) {
		String find = " " + toFind + ":";
		if (info.contains(find)) {
			
			int start = info.indexOf(find) + find.length();
			int end = info.length();
			for (int i = start; i < info.length(); i++) {
				if (info.charAt(i) == ' ' || info.charAt(i) == '>') {
					end = i;
					break;
				}
			}
			
			String param = info.substring(start, end);
			
			System.out.print(find + " " + param);
			return param;
		}
		
		return null;
	}
	
	/**
	 * Register the name of all the unique fields to the searchField map.
	 */
	protected abstract void registerUniqueFields();
	
	/**
	 * Gets the ID of the function.
	 * @return The ID of the function.
	 */
	public String getID() {
		return fields.get(ID);
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
		fields.remove(ID);
		fields.put(ID, newID);
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

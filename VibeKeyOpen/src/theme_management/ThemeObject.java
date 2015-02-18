package src.theme_management;

import java.awt.Shape;
import java.util.Collection;

public interface ThemeObject {
	
	/**
	 * 
	 * @return the ThemeObjects Id as a string
	 */
	public String getId();
	
	/**
	 * adds a ThemeObject to the list of children
	 * 
	 * @param to the ThemeObject to add
	 * @return whether or not his method worked as expected
	 */
	public boolean addChild(ThemeObject to);
	
	/**
	 * removes a ThemeObject from the list of children
	 * 
	 * @param to the ThemeObject to remove
	 * @return whether or not his method worked as expected
	 */
	public boolean removeChild(ThemeObject to);
	
	/**
	 * moves a ThemeObject down in the list of children (affects depth)
	 * 
	 * @param to the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemDown(String themeItemId);
	
	/**
	 * moves a ThemeObject up in the list of children (affects depth)
	 * 
	 * @param to the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemUp(String themeItemId);
	
	/**
	 * moves a ThemeObject to the back of the list of children (affects depth)
	 * 
	 * @param to the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemToBack(String themeItemId);
	
	/**
	 * moves a ThemeObject to the front of the list of children (affects depth)
	 * 
	 * @param to the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemToFront(String themeItemId);
	
	public ThemeStyle findStyle();

	public Collection<? extends Shape> getShapes();

	public String primitivePartialRender();
	
}

package theme_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ThemeManager {

	//the styles to use
	private List<ThemeStyle> styles;
	
	//the ThemeItems to use
	private List<ThemeItem> themeItems;
	
	//a map used to access styles by id
	private HashMap<String,ThemeStyle> styleIds;
	
	//a map used to access theme items by id
	private HashMap<String,ThemeItem> themeItemIds;
	
	public ThemeManager(){
		styles = new ArrayList<ThemeStyle>();
		themeItems = new ArrayList<ThemeItem>();
		styleIds = new HashMap<String,ThemeStyle>();
		themeItemIds = new HashMap<String,ThemeItem>();
	}
	
	public ThemeManager(List<ThemeStyle> sl, List<ThemeItem> til){
		this();
		for(ThemeStyle s : sl){
			styles.add(s);
			styleIds.put(s.getId(),s);
		}
		for(ThemeItem ti : til){
			themeItems.add(ti);
			themeItemIds.put(ti.getId(),ti);
		}
	}
	
	public boolean registerThemeItem(ThemeItem ti){
		//TODO stuff
		return true;
	}
	
	public boolean registerStyle(ThemeStyle ts){
		//TODO stuff
		return true;
	}
	
	/**
	 * 
	 * @param styleId
	 * @return the style corresponding to the given styleId string
	 */
	public ThemeStyle getStyle(String styleId){
		return styleIds.get(styleId);
	}
	
	/**
	 * 
	 * @param themeItemId
	 * @return the theme item corresponding to the given themeItemId string
	 */
	public ThemeItem getTheme(String themeItemId){
		return themeItemIds.get(themeItemId);
	}
	
	/**
	 * moves a ThemeItem down in the list of ThemeItems (affects depth)
	 * 
	 * @param themeItemId the Id of the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemDown(String themeItemId){
		ThemeItem t = getTheme(themeItemId);
		for(int i=0;i<themeItems.size();i++){
			if(themeItems.get(i)==t){
				if(i==0)return false;
				Collections.swap(themeItems, i, i-1);
			}
		}
		return true;
	}
	
	/**
	 * moves a ThemeItem up in the list of ThemeItems (affects depth)
	 * 
	 * @param themeItemId the Id of the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemUp(String themeItemId){
		ThemeItem t = getTheme(themeItemId);
		for(int i=0;i<themeItems.size();i++){
			if(themeItems.get(i)==t){
				if(i==themeItems.size()-1)return false;
				Collections.swap(themeItems, i, i+1);
			}
		}
		return true;
	}
	
	/**
	 * moves a ThemeItem to the back of the list of ThemeItems (affects depth)
	 * 
	 * @param themeItemId the Id of the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemToBack(String themeItemId){
		ThemeItem t = getTheme(themeItemId);
		themeItems.add(0,t);
		return themeItems.remove(t);
	}
	
	/**
	 * moves a ThemeItem to the front of the list of ThemeItems (affects depth)
	 * 
	 * @param themeItemId the Id of the ThemeObject to move
	 * @return whether or not his method worked as expected
	 */
	public boolean moveThemeItemToFront(String themeItemId){
		ThemeItem t = getTheme(themeItemId);
		return themeItems.remove(t) && themeItems.add(t);
	}
	
	/**
	 * draws all themeItems by calling their 'drawSelf()' method
	 */
	public void drawItems(){
		for(ThemeItem ti : themeItems){
			ti.drawSelf();
		}
	}
	
}

package theme.old;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ThemeItem implements ThemeObject{

	//this item's id
	private String id;
	
	//this item's parent
	private ThemeObject parentItem;
	
	//a list of this item's children
	private List<ThemeObject> children;
	
	//the ThemeManager this item will be interacting with
	private ThemeManager manager;
	
	//private Shape shape;
	
	
	public ThemeItem(){
		this.id=this.hashCode()+"";
		this.parentItem = null;
		this.children = new ArrayList<ThemeObject>();
		setManager(null);
	}
	
	public ThemeItem(ThemeManager myManager){
		this();
		setManager(myManager);
	}
	
	public ThemeItem(ThemeManager myManager, String myId){
		this(myManager);
		this.id=myId;
	}
	
	public ThemeItem(ThemeManager myManager, String myId, ThemeObject parent){
		this(myManager, myId);
		this.parentItem = parent;
	}
	
	public ThemeItem(ThemeManager myManager, String myId, ThemeObject parent, List<ThemeObject> myChildren){
		this(myManager, myId, parent);
		this.children = myChildren;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	/**
	 * sets the parent to a given ThemeObject
	 * @param to the new parent object
	 * @return whether or not this method worked as expected
	 */
	public boolean setParent(ThemeObject to){
		this.parentItem = to;
		return true;
	}
	
	/**
	 * 
	 * @return this item's parent
	 */
	public ThemeObject getParent(){
		return this.parentItem;
	}

	@Override
	public boolean addChild(ThemeObject to) {
		return this.children.add(to);
	}

	@Override
	public boolean removeChild(ThemeObject to) {
		return this.children.remove(to);
	}

	@Override
	public boolean moveThemeItemDown(String themeItemId) {
		for(int i=0;i<children.size();i++){
			if(children.get(i).getId().equals(themeItemId)){
				if(i==0)return false;
				Collections.swap(children, i, i-1);
			}
		}
		return true;
	}

	@Override
	public boolean moveThemeItemUp(String themeItemId) {
		for(int i=0;i<children.size();i++){
			if(children.get(i).getId().equals(themeItemId)){
				if(i==children.size()-1)return false;
				Collections.swap(children, i, i+1);
			}
		}
		return true;
	}

	@Override
	public boolean moveThemeItemToBack(String themeItemId) {
		ThemeObject to = null;
		for(int i=0;i<children.size();i++){
			if(children.get(i).getId().equals(themeItemId)){
				to = children.get(i);
				break;
			}
		}
		if(to == null)return false;
		else{
		children.add(0, to);
		return children.remove(to);}
	}

	@Override
	public boolean moveThemeItemToFront(String themeItemId) {
		ThemeObject to = null;
		for(int i=0;i<children.size();i++){
			if(children.get(i).getId().equals(themeItemId)){
				to = children.get(i);
				break;
			}
		}
		if(to == null)return false;
		else{
		return children.remove(to) && children.add(to);}
	}

	@Override
	public abstract ThemeStyle findStyle();

	

	/**
	 * 
	 * @return the manager this item is interacting with
	 */
	public ThemeManager getManager() {
		return this.manager;
	}

	/**
	 * sets the manager to a given ThemeManager
	 * @param manager the new manager
	 */
	public void setManager(ThemeManager manager) {
		this.manager = manager;
	}

	protected abstract Shape getShape();

	/*public void setShape(Shape shape) {
		this.shape = shape;
	}*/
	
	public List<Shape> render(){
		List<Shape> shapes = new ArrayList<Shape>();
		shapes.add(getShape());
		for(ThemeObject to : children){
			shapes.addAll(to.getShapes());
		}
		//TODO make JSP stuff here???
		return shapes;
	}

	public String primitivePartialRender() {
		String s = "";
		
		String[] data = this.getShapeData();
		
		s+=data[0];
		
		for(ThemeObject to : children){
			s+=to.primitivePartialRender();
		}
		
		s+=data[1];
		
		return s;
	}

	protected abstract String[] getShapeData();

}

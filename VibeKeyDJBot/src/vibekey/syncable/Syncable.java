package vibekey.syncable;

public abstract class Syncable {
	private boolean changed = false;
	
	public boolean hasChanged(){
		return changed;
	}
	
	public void setChanged(boolean changed){
		this.changed = changed;
	}
	
}

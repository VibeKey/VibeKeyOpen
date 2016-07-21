package vibekey.syncable;

import java.util.ArrayList;

public class SyncerContainerVisitor {
	public static ArrayList<SyncableContainer> syncableContainers = new ArrayList<SyncableContainer>();
	
	public static void add(SyncableContainer syncableContainer){
		syncableContainers.add(syncableContainer);
	}
	
	public static void sync(){
		for(SyncableContainer syncableContainer : syncableContainers){
			syncableContainer.sync();
		}
	}
}

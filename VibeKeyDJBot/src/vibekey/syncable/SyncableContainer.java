package vibekey.syncable;

import java.util.HashMap;
import java.util.Map;

import com.firebase.client.Firebase;

public abstract class SyncableContainer extends Syncable {
	
	Map<Syncable, Firebase> syncablePaths = new HashMap<Syncable, Firebase>();
	
	Firebase ref;
	
	public SyncableContainer(Firebase ref){
		this.ref = ref;
	}
	
	
	public void sync(){
		for(Syncable syncable : syncablePaths.keySet()){
			if(syncable.hasChanged()){
				syncablePaths.get(syncable).setValue(syncable);
			}
		}
	}
	
	public void addSyncable(Syncable s, Firebase parentRef){
		Firebase newRef = parentRef.push();
		syncablePaths.put(s, newRef);
		newRef.setValue(s);
	}
	
	public void removeSyncable(Syncable s){
		Firebase ref = syncablePaths.get(s);
		ref.setValue(null);
		syncablePaths.remove(s);
	}
	
}

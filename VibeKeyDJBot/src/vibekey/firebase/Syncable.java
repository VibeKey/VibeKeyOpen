package vibekey.firebase;

import com.firebase.client.Firebase;

/**
 * Defines if a class is able to be converted back and forth with Firebase's API.  
 * 
 * Any object that implements this interface is expected to be able to keep itself up to date with the server after setupSyncable is called.
 * @author kneislsj
 *
 */
public interface Syncable {
	//public void syncWithFirebase(Firebase ref);
	public void setupSyncable(Firebase ref);  //Usually used for initializing the DB, and setting up Firebase listeners.
}

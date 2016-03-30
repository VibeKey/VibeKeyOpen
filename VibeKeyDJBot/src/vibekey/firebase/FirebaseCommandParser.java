package vibekey.firebase;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import vibekey.playlist.Playlist;
import vibekey.schedule.ScheduleItem;
import vibekey.song.SimplifiedSong;
import vibekey.song.Song;
import vibekey.song.SongDatabase;
import vibekey.stream.StreamController;

@SuppressWarnings("unused")
public class FirebaseCommandParser {
	StreamController streamController;
	String commandAccess;
	
	public FirebaseCommandParser(StreamController streamController, String commandAccess){
		this.streamController = streamController;
		this.commandAccess = commandAccess;
	}

	@SuppressWarnings("rawtypes")
	public Object parseCommand(DataSnapshot snapshot) {
  	  	String cmdString = (String) snapshot.child("cmdString").getValue();
  	  	DataSnapshot params = snapshot.child("params");
		if (cmdString != null) {
			
			try {
				Class[] classes = new Class[1];
				classes[0]=DataSnapshot.class;
				
				System.out.println(classes[0].getName());
				Method method = this.getClass().getDeclaredMethod(cmdString, classes);
				return method.invoke(this, params);
			} catch (NoSuchMethodException e) {
				System.out.println("ERROR: Invalid command \"" + cmdString + "\" from Firebase.");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		FirebaseCommunicator.clearCommand(snapshot);
		return null;
	}
	
	public void setupFirebaseListeners(Firebase ref){
		Firebase commandRef;
		if(this.commandAccess.equals("")){
			commandRef = ref;
		}else{
			commandRef = ref.child(this.commandAccess);
		}
		commandRef.addChildEventListener(new ChildEventListener() {
		      @Override
		      public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
		    	  Boolean doCmd = (Boolean) snapshot.child("doCmd").getValue();
		    	  if(doCmd != null && doCmd){
			    	  Object ret = parseCommand(snapshot);
			    	  snapshot.getRef().child("doCmd").setValue(false);
			    	  snapshot.getRef().child("return").setValue(ret);
		    	  }
		      }
		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
		    	  Boolean doCmd = (Boolean) snapshot.child("doCmd").getValue();
		    	  if(doCmd != null && doCmd){
			    	  Object ret = parseCommand(snapshot);
			    	  snapshot.getRef().child("doCmd").setValue(false);
			    	  snapshot.getRef().child("return").setValue(ret);
		    	  }
				
			}
			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				//Do nothing
				
			}
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				//Do nothing
				
			}
		  });
		
	}
}

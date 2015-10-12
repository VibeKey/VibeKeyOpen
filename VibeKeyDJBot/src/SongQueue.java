import java.util.LinkedList;

import com.firebase.client.Firebase;

public class SongQueue {
	LinkedList<Song> queue = new LinkedList<Song>();
	Firebase rootRef;
	
	public SongQueue(Firebase rootRef){
		this.rootRef = rootRef;
	}
	
	private void pushToFirebase(){
		Firebase queueRef = rootRef.child("queue");
		LinkedList<SimplifiedSong> queueSimplified = new LinkedList<SimplifiedSong>();
		for(Song song : queue){
			queueSimplified.add(song.simplifiedSong);
		}
		queueRef.setValue(queueSimplified);
		
	}
	
	public void addToQueue(Song song){
		queue.offer(song);
		pushToFirebase();
	}
	
	public Song popFromQueue(){
		Song nextSong = queue.poll();
		pushToFirebase();
		return nextSong;
	}
	
	public void emptyQueue(){
		queue.clear();
		pushToFirebase();
	}
	
	public void removeFromQueueAt(int index){
		queue.remove(index);
		pushToFirebase();
	}
	
	public void addToQueueAt(int index, Song song){
		queue.add(index, song);
		pushToFirebase();
	}
}

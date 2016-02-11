package vibekey.song;
import java.util.LinkedList;

import vibekey.firebase.FirebaseCommunicator;

public class SongQueue {
	
	static final public int FILL_SIZE = 5;
	
	LinkedList<Song> queue = new LinkedList<Song>();
	
	public SongQueue(){
	}
	
	
	public void pushToFirebase(){
		FirebaseCommunicator.updateQueue(queue);
	}
	
	public int getTotalTime(){
		int totalTime = 0;
		for(Song song : queue){
			totalTime+= song.getLength();
		}
		return totalTime;
	}
	
	public void addToQueue(Song song){
		queue.offer(song);
		pushToFirebase();
	}
	
	public int size(){
		return queue.size();
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

import java.util.LinkedList;

public class SongQueue {
	LinkedList<Song> queue = new LinkedList<Song>();
	FirebaseCommunicator fbc;
	
	public SongQueue(FirebaseCommunicator fbc){
		this.fbc = fbc;
	}
	
	
	public void pushToFirebase(){
		fbc.updateQueue(queue);
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

import java.util.LinkedList;

public class SongQueue {
	LinkedList<Song> queue = new LinkedList<Song>();
	
	public SongQueue(){
	}
	
	
	public void pushToFirebase(){
		FirebaseCommunicator.updateQueue(queue);
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

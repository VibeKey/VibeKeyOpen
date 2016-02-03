import java.util.ArrayList;
import java.util.Random;

public class RandomWrapper {
	static Random random = new Random(System.currentTimeMillis());
	
	static int nextInt(int upper){
		return random.nextInt(upper);
	}
	
	static double nextDouble(){
		return random.nextDouble();
	}
	
	static Song nextSongWeighted(ArrayList<Song> songs){
		int songNum = random.nextInt(songs.size());
		return songs.get(songNum);
	}
}

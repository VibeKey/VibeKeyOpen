import java.util.Random;

public class RandomWrapper {
	static Random random = new Random(System.currentTimeMillis());
	
	static int nextInt(int upper){
		return random.nextInt(upper);
	}
}

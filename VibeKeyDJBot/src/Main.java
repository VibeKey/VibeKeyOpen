

public class Main {

	public static void main(String[] args) {
		DJBot djbot = new DJBot();
		for(int i=0;i<100000;i++){ //silly hack for now to have it keep playing, eventually move to firebase stop
			djbot.playRandom();
		}
		djbot.close();
	}

}


package primary_manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import djbot.DJBot;

public class VibeKey {
	public static PrimaryManager manager = new PrimaryManager();
	public static ExecutorService threadExecutor = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		System.out.println(new DJBot().getSong().getId());
		System.out.println(new DJBot().getSong().getId());
		System.out.println(new DJBot().getSong().getId());
	}

	public static ExecutorService getNewExecutor(int maxSize){
		return new ThreadPoolExecutor(0, maxSize,
                60L, TimeUnit.SECONDS,
			    new SynchronousQueue<Runnable>()); // queue with a size
	}
	
}

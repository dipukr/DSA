package thread;

import java.util.ArrayList;

public class Threads {
	public static void task() {
		System.out.println("Entering...."+Thread.currentThread().getName());
		System.out.println("Leaving....."+Thread.currentThread().getName());
	}
	public static void main(String[] args) throws Exception {
		final int N = 10;
		var threads = new ArrayList<Thread>();
		for (var i = 0; i < N; i++)
			threads.add(new Thread(Threads::task));
		for (var thread: threads)
			thread.start();
		for (var thread: threads)
			thread.join();
		System.out.println("Exiting.....");
	}
}

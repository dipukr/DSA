package maven;

import java.util.concurrent.*;

public class Coll {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		for (int i = 1; i <= 5; i++) {
			int taskId = i;
			executor.submit(() -> {
				System.out.println("Task " + taskId + " started");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				System.out.println("Task " + taskId + " completed");
			});
		}
		executor.shutdownNow();
		System.out.println("Shutdown initiated");
	}
}

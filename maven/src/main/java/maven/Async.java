package maven;

import java.util.concurrent.CompletableFuture;

public class Async {
	public static void busyWait(long millis) {
		long now = System.currentTimeMillis();
		while (System.currentTimeMillis() - now < millis) {
		}
	}
	public static void main(String[] args) throws Exception {
		CompletableFuture.supplyAsync(() -> {
		    return "Payment completed";
		}).thenApplyAsync(result -> {
			busyWait(400);
		    return result.toUpperCase();
		}).thenAcceptAsync(result -> {
		    System.out.println(result);
		});
		for (int i = 0; i < 10; i++) {
			busyWait(100);
			System.out.println("Count"+i);
		}
	}
}

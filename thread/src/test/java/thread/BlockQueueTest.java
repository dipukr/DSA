package thread;

import com.github.javafaker.Faker;

public class BlockQueueTest {
	public static void main(final String[] args) throws Exception {
		var queue = new BlockQueue<String>(100);
		var faker = new Faker();
		Runnable producer = () -> {
			try {
				while (true) {
					Thread.sleep(100);
					String name = faker.animal().name();
					queue.put(name);
					System.out.printf("Produced: %s\n", name);
				}	
			} catch (Exception e) {
				System.out.printf("Producer[%s] interrupted.\n", Thread.currentThread().getName());
			}
		};
		Runnable consumer = () -> {
			try {
				while (true) {
					Thread.sleep(100);
					System.out.printf("Consumed: %s\n", queue.take());
				}
			} catch (Exception e) {
				System.out.printf("Consumer[%s] interrupted.\n", Thread.currentThread().getName());
			}
		};
		final int SIZE = 4;
		Thread[] producers = new Thread[SIZE];
		Thread[] consumers = new Thread[SIZE];
		
		for (int i = 0; i < SIZE; i++) {
			producers[i] = new Thread(producer);
			consumers[i] = new Thread(consumer);
		}
		for (int i = 0; i < SIZE; i++) {
			producers[i].start();
			consumers[i].start();
		}
		for (Thread thread: producers)
			thread.join();
		for (Thread thread: consumers)
			thread.join();
	}
}

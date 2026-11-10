package thread;

public class CounterTest {
	public static boolean testSync() {
		Counter counter = new Counter(0);
		Runnable first = () -> {
			for (int i = 0; i < 1_000_000; i++)
				counter.increment();
		};
		Runnable second = () -> {
			for (int i = 0; i < 1_000_000; i++)
				counter.increment();
		};
		Thread thread1 = new Thread(first);
		Thread thread2 = new Thread(second);
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {}
		return counter.getCount() == 2_000_000;
	}
	
	public static boolean testAsync() {
		Counter counter = new Counter(0);
		Runnable first = () -> {
			for (int i = 0; i < 1_000_000; i++)
				counter.incrementAsync();
		};
		Runnable second = () -> {
			for (int i = 0; i < 1_000_000; i++)
				counter.incrementAsync();
		};
		Thread thread1 = new Thread(first);
		Thread thread2 = new Thread(second);
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {}
		return counter.getCount() == 2_000_000;
	}
	
	public static void main(final String[] args) {
		int totalTest = 100;
		int failedTest = 0;
		int passedTest = 0;
		for (int i = 0; i < totalTest; i++) {
			if (testAsync())
				passedTest++;
			else failedTest++;
		}
		System.out.printf("Total  tests: %d\n", totalTest);
		System.out.printf("Passed tests: %d\n", passedTest);
		System.out.printf("Failed tests: %d\n", failedTest);
	}
}

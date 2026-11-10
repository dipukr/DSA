package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedData {
	private ReentrantLock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	private boolean dataAvailable = false;

	public void produceData() {
		lock.lock();
		try {
			dataAvailable = true;
			System.out.println("Producer: Data produced, signaling consumers.");
			cond.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public void consumeData() {
		lock.lock();
		try {
			while (!dataAvailable) {
				System.out.println("Consumer: No data, waiting...");
				cond.await();
			}
			System.out.println("Consumer: Data consumed");
			dataAvailable = false;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		SharedData demo = new SharedData();
		Thread producer = new Thread(() -> {while (true) demo.produceData();});
		Thread consumer = new Thread(() -> {while (true) demo.consumeData();});
		producer.start();
		consumer.start();
	}
}

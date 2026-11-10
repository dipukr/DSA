package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EvenOdd {
	private int N;
	private int counter = 1;
	private Lock lock = new ReentrantLock();
	private Condition oddTurn = lock.newCondition();
	private Condition evenTurn = lock.newCondition();

	public EvenOdd(int N) {
		this.N = N;
	}

	public void printOdd() {
		while (true) {
			lock.lock();
			try {
				while (counter <= N && counter % 2 == 0)
					oddTurn.await(); // wait for odd turn
				if (counter > N) {
					evenTurn.signal(); // wake up even thread to exit
					break;
				}
				System.out.print(counter + "\t");
				counter += 1;
				evenTurn.signal(); // signal even thread
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				lock.unlock();
			}
		}
	}

	public void printEven() {
		while (true) {
			lock.lock();
			try {
				while (counter <= N && counter % 2 != 0)
					evenTurn.await(); // wait for even turn
				if (counter > N) {
					oddTurn.signal(); // wake up odd thread to exit
					break;
				}
				System.out.print(counter + "\t");
				counter += 1;
				oddTurn.signal(); // signal odd thread
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		var printer = new EvenOdd(100);
		Thread oddThread = new Thread(printer::printOdd);
		Thread evenThread = new Thread(printer::printEven);
		oddThread.start();
		evenThread.start();
	}
}

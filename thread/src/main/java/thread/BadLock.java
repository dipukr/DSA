package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BadLock implements Locker {
	private volatile boolean locked;
	
	public BadLock() {
		this.locked = false;
	}
	
	@Override
	public void lock() {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		while (locked)
			System.out.println("Another thread holding the lock.");
		this.locked = true;
	}

	@Override
	public void unlock() {
		this.locked = false;
	}
}

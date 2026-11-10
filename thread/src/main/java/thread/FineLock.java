package thread;

import java.util.concurrent.atomic.AtomicBoolean;

public class FineLock implements Locker {
	private AtomicBoolean locked = new AtomicBoolean(false);

	@Override
	public void lock() {
		while (!locked.compareAndSet(false, true))
			System.out.println("Busy waiting");
	}

	@Override
	public void unlock() {
		locked.set(false);
	}
}

package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
	private ReentrantLock lock = new ReentrantLock(true);
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();
	private Object[] items = new Object[100];
	private int putptr, takeptr, count;

	public void put(Object item) throws Exception {
		lock.lock();
		try {
			while (count == items.length)
				notFull.await();
			items[putptr++] = item;
			if (putptr == items.length)
				putptr = 0;
			count += 1;
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws Exception {
		lock.lock();
		try {
			while (count == 0)
				notEmpty.await();
			Object item = items[takeptr++];
			if (takeptr == items.length)
				takeptr = 0;
			count -= 1;
			notFull.signalAll();
			return item;
		} finally {
			lock.unlock();
		}
	}
}

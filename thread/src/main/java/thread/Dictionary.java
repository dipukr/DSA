package thread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class Dictionary<K, V> {
	private Map<K, V> data;
	private ReadWriteLock rw = new ReentrantReadWriteLock();

	public Dictionary() {
		this.data = new HashMap<>();
	}

	public Dictionary(int initialCapacity) {
		this.data = new HashMap<>(initialCapacity);
	}

	public V get(K key) {
		rw.readLock().lock();
		try {
			return data.get(key);
		} finally {
			rw.readLock().unlock();
		}
	}

	public boolean containsKey(K key) {
		rw.readLock().lock();
		try {
			return data.containsKey(key);
		} finally {
			rw.readLock().unlock();
		}
	}

	public int size() {
		rw.readLock().lock();
		try {
			return data.size();
		} finally {
			rw.readLock().unlock();
		}
	}

	public Set<K> keySetSnapshot() {
		rw.readLock().lock();
		try {
			return new HashSet<>(data.keySet());
		} finally {
			rw.readLock().unlock();
		}
	}

	public Map<K, V> snapshot() {
		rw.readLock().lock();
		try {
			return new HashMap<>(data);
		} finally {
			rw.readLock().unlock();
		}
	}

	public V put(K key, V value) {
		rw.writeLock().lock();
		try {
			return data.put(key, value);
		} finally {
			rw.writeLock().unlock();
		}
	}

	public V remove(K key) {
		rw.writeLock().lock();
		try {
			return data.remove(key);
		} finally {
			rw.writeLock().unlock();
		}
	}

	public void clear() {
		rw.writeLock().lock();
		try {
			data.clear();
		} finally {
			rw.writeLock().unlock();
		}
	}

	public V computeIfAbsent(K key, Function<? super K, ? extends V> mapper) {
		rw.readLock().lock();
		try {
			if (data.containsKey(key))
				return data.get(key);
		} finally {
			rw.readLock().unlock();
		}
		rw.writeLock().lock();
		try {
			// another writer may have inserted while we waited
			V existing = data.get(key);
			if (existing != null)
				return existing;
			V newValue = mapper.apply(key);
			if (newValue != null)
				data.put(key, newValue);
			return newValue;
		} finally {
			rw.writeLock().unlock();
		}
	}

	@Override
	public String toString() {
		rw.readLock().lock();
		try {
			return data.toString();
		} finally {
			rw.readLock().unlock();
		}
	}
}

package main;

import java.util.HashMap;
import java.util.Map;

public class Times {
	private record Value(String data, long time, long ttl) {}
	private Map<String, Value> data = new HashMap<>();

	public void put(String key, String val, long ttl) {
		long now = System.currentTimeMillis();
		data.put(key, new Value(val, now, ttl));
	}

	public String get(String key) {
		long now = System.currentTimeMillis();
		Value value = data.get(key);
		if (value == null || (now > value.time + value.ttl)) {
			data.remove(key);
			return null;
		}
		return value.data;
	}
}

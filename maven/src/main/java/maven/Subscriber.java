package maven;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber {
	public static void main(String[] args) {
		try (var jedis = new Jedis("localhost", 6379)) {
			jedis.subscribe(new JedisPubSub() {
				@Override
				public void onMessage(String channel, String message) {
					System.out.println("Received: " + message);
				}
			}, "chatroom");
		}
	}
}

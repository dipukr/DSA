package maven;

import redis.clients.jedis.Jedis;

public class Publisher {
	public static void main(String[] args) {
		try (var jedis = new Jedis("localhost", 6379)) {
			for (int i = 1; i <= 3; i++) {
				jedis.publish("chatroom", "Message " + i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

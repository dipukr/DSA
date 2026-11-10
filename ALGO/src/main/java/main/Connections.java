package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Connections {
	private String url;
	private String username;
	private String password;
	private BlockingQueue<Connection> connections;

	public Connections(String url, String username, String password, int maxPoolSize) throws Exception {
		this.url = url;
		this.username = username;
		this.password = password;
		this.connections = new ArrayBlockingQueue<>(maxPoolSize);
		for (int i = 0; i < maxPoolSize; i++)
			connections.add(createConnection());
	}

	public Connection createConnection() throws Exception {
		return DriverManager.getConnection(url, username, password);
	}

	public Connection borrowConnection() throws Exception {
		return connections.take();
	}

	public void returnConnection(Connection connection) throws Exception {
		if (connection != null && !connection.isClosed())
			connections.offer(connection);
	}

	public void shutdown() throws Exception {
		for (Connection conn: connections)
			conn.close();
	}
}

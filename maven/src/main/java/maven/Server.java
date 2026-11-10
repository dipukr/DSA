package maven;

import java.io.ObjectInputStream;
import java.net.ServerSocket;

public class Server {
	public static void main(String[] args) throws Exception {
		var server = new ServerSocket(8780);
		System.out.println("Server running...");
		var client = server.accept();
		System.out.println("Accepted connection");
		var inStream = client.getInputStream();
		var reader = new ObjectInputStream(inStream);
		Packet packet = (Packet) reader.readObject();
		System.out.println(packet);
		client.close();
		server.close();
	}
}
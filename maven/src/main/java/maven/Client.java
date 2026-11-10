package maven;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		var body = String.format("{x: %d, y: %d}", 10, 20);
		var headers = new HashMap<String, String>();
		headers.put("Content-Type", "JSON");
		headers.put("Cookie", "ccc");
		var packet = new Packet(headers, body);
		var server = new Socket("127.0.0.1", 8780);
		var outStream = server.getOutputStream();
		var writer = new ObjectOutputStream(outStream);
		writer.writeObject(packet);
		writer.flush();
		server.close();
	}
} 


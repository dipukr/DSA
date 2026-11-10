package maven;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class TCPServer {
	private DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private Function<String, String> function;
	private int port;

	public TCPServer(Function<String, String> function, int port) {
		this.function = function;
		this.port = port;
	}

	public void start() {
		try (var serverSocket = new ServerSocket(port);
			var writer = new FileWriter("/tmp/log/tcp-serverlog", true)) {
			while (true) {
				System.out.printf("Listening at port %d.%n", port);
				Socket clientSocket = serverSocket.accept();
				logger(writer, clientSocket);
				Thread.ofVirtual().start(() -> handle(clientSocket));
			}
		} catch (Exception e) {
			System.out.printf("ERROR: %s%n", e.getMessage());
		}
	}

	public void handle(Socket client) {
		try {
			InputStream inStream = client.getInputStream();
			OutputStream outStream = client.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			DataOutputStream writer = new DataOutputStream(outStream);
			String data = reader.readLine();
			writer.writeBytes(function.apply(data));
			writer.writeChar('\n');
		} catch (Exception e) {
			System.out.printf("ERROR: %s%n", e.getMessage());
		}
	}
	
	public void logger(FileWriter writer, Socket client) throws Exception {
		InetAddress inetAddr = client.getInetAddress();
		String hostAddr = inetAddr.getHostAddress();
		var msg = new StringBuilder();
		msg.append('[');
		msg.append(formatter.format(new Date()));
		msg.append("] ");
		msg.append(hostAddr);
		msg.append('\n');
		writer.write(msg.toString());
		writer.flush();
	}
}
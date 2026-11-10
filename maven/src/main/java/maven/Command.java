package maven;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Command {
	public static void main(String[] args) throws Exception {
		var processBuilder = new ProcessBuilder("find");
		Process process = processBuilder.start();
		InputStream inputStream = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		var data = new StringBuilder();
		while (true) {
			String line = reader.readLine();
			if (line == null) break;
			data.append(line).append('\n');
		}
		process.destroyForcibly();
		int retval = process.waitFor();
		if (retval == 0)
			System.out.println(data);
	}
}
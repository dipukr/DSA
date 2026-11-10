package maven;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class Loader {
	public static void main(String[] args) throws Exception {
		var urls = "https://www.sfml-dev.org/index.html";
		URL url = URI.create(urls).toURL();
		InputStream inputStream = url.openStream();
		String file = url.getFile().substring(1);
		var reader = new BufferedInputStream(inputStream);
		var writer = new FileOutputStream(file);
		final int BUF_SZ = 1 << 12;
		byte[] buffer = new byte[BUF_SZ];
		while (true) {
			int count = reader.read(buffer, 0, BUF_SZ);
			if (count == -1) break;
			writer.write(buffer, 0, count);
		}
		writer.close();
	}
}

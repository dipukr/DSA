package maven;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Downloader {
	private URL url;
	private int size;
	private int count;

	public Downloader(URL url) {
		this.url = url;
		this.size = -1;
		this.count = 0;
	}

	public void download() {
		try (var file = new RandomAccessFile("output.data", "rw")) {
			var connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Range", String.format("bytes=%d-", count));
			connection.connect();
			this.size = connection.getContentLength();
			file.seek(count);
			var stream = connection.getInputStream();
			byte[] buffer = new byte[1024];
			while (true) {
				int read = stream.read(buffer, 0, buffer.length);
				if (read == -1) break;
				file.write(buffer, 0, read);
				this.count += read;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public String getUrl() {
		return url.toString();
	}
	
	public double getProgress() {
		return ((double) count / size) * 100.0;
	}
	
	public String getFileName() {
		return url.getFile().substring(1);
	}

	public static void main(String[] args) throws Exception {
		var urls = "https://www.sfml-dev.org/index.html";
		URL url = URI.create(urls).toURL();
		var downloader = new Downloader(url);
		downloader.download();
	}
}

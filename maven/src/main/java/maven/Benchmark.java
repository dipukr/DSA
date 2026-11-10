package maven;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Benchmark {
	public static void main(String[] args) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:80"))
				.build();
		var handler = HttpResponse.BodyHandlers.ofString();
		final long millis = 1000;
		int counter = 0;
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < millis) {
			var response = client.send(request, handler);
			response.body();
			counter += 1;
		}
		System.out.printf("Served %d requests in %d millis.\n", counter, millis);
	}
}

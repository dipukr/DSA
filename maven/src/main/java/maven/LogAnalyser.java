package maven;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.json.JSONArray;

public class LogAnalyser {
	public static final Pattern LOG_PATTERN = Pattern.compile(
			"^(\\d{2}-\\d{2}-\\d{4}\\s+\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\s+" +
			"\\[(.*?)\\].*?-\\s+" +
			"(REQUEST|RESPONSE).*?" +
			"path=\\[(.*?)]" +
			".*?body=\\[(.*)]$"
	);

	public static final DateTimeFormatter FORMATTER = 
			DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");

	public static final String REQ = "REQUEST";
	public static final String RESP = "RESPONSE";

	public record Record(String time, String threadName, String lineType, String url, String body) {}
	public record Key(String threadName, String url) {}
	
	public int getRequestBodySize(String body) {
	    if (body == null) return -1;
	    if (body.trim().startsWith("{"))
	        return 1;
	    try {
	        return new JSONArray(body.trim()).length();
	    } catch (Exception e) {
	        return -1;
	    }
	}

	public void printLatency(List<Record> records) {
		var pendingRequests = new HashMap<Key, Record>();

		for (Record record: records) {
			var key = new Key(record.threadName(), record.url());

			if ("REQUEST".equals(record.lineType())) {
				pendingRequests.put(key, record);
			} else if ("RESPONSE".equals(record.lineType())) {
				Record request = pendingRequests.remove(key);

				if (request == null) {
					System.out.printf("No matching request found for response. Thread=%s Path=%s%n",
							record.threadName(), record.url());
					System.exit(0);;
				}

				LocalDateTime requestTime = LocalDateTime.parse(request.time(), FORMATTER);
				LocalDateTime responseTime = LocalDateTime.parse(record.time(), FORMATTER);
				long latencyMs = Duration.between(requestTime, responseTime).toMillis();
				
				System.out.printf("%s | %03d | %6d millis%n", record.url(),
						getRequestBodySize(request.body()), latencyMs);
			}
		}

		if (!pendingRequests.isEmpty()) {
			System.out.println("\nPending Requests:");
			pendingRequests.values().forEach(r -> System.out.printf("%s | %s%n", r.threadName(), r.url()));
		}
	}

	public void analyse(List<String> lines, String url) throws Exception {
		lines = lines.stream().filter(l -> l.contains(url))
				.filter(l -> l.contains(REQ) || l.contains(RESP)).toList();

		var result = new ArrayList<Record>();

		for (String line: lines) {
			Matcher matcher = LOG_PATTERN.matcher(line);
			if (!matcher.find())
				continue;

			result.add(new Record(
				matcher.group(1),
				matcher.group(2),
				matcher.group(3),
				matcher.group(4),
				matcher.group(5)
			));
		}
		printLatency(result);
	}

	public static void main(String[] args) throws Exception {
		var url = "Common/deleteManageInvoice";
		var log = new LogAnalyser();
		var allLines = new ArrayList<String>();
		for (int i = 0; i <= 29; i++) {
		    // Path path = Path.of("portal-03-08-2026", "portal-03-08-2026-" + i + ".log");
			Path path = Path.of("C:\\Users\\dipu.kumar\\Downloads\\LOGS\\RAW", "portal-17-08-2026-" + i + ".log");
		    try (Stream<String> lines = Files.lines(path)) {
		        lines.forEach(allLines::add);
		    }
		}
		System.out.println(allLines.size());
		log.analyse(allLines, url);
	}
}

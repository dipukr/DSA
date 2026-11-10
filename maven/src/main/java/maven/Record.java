package maven;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class Record {
	public LocalDateTime time;
	public String thread;
	public String kind;
	public String method;
	public String path;
	public String body;
	public Map<String, String> params;
	public Map<String, String> headers;
}

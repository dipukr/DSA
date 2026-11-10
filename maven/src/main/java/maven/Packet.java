package maven;

import java.io.Serializable;
import java.util.Map;

public class Packet implements Serializable {
	public Map<String, String> headers;
	public String data;

	public Packet(Map<String, String> headers, String data) {
		this.headers = headers;
		this.data = data;
	}

	@Override
	public String toString() {
		return String.format("Packet[headers=%s, body=%s]", headers, data);
	}
}

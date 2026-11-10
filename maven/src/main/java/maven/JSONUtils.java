package maven;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {
	public static void main(String[] args) throws Exception {
		var filePath = Path.of("C:\\Users\\dipu.kumar\\json3");
		String fileData = Files.readString(filePath);
		System.out.println(fileData.length());
		JSONObject obj = new JSONObject(fileData);
		JSONArray arr = (JSONArray) obj.get("data");
		System.out.println(arr.length());
		var set = new TreeSet<Integer>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject ob = arr.getJSONObject(i);
			String val = ob.optString("invoices", null);
			if (val != null && !val.isBlank()) {
				if (val.length() >= 32768) {
					String[] ss = val.split(",", -1);
					for (String s: ss)
						set.add(s.length());
				}
			}
		}
		System.out.println(set);
	}
}

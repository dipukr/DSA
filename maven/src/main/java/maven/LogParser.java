package maven;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

public class LogParser {
	record SummaryData(String companyName, String userName, int likes, int dislikes) {}
	record DataElem(String companyName, String userName, boolean liked) {}
	record Key(String companyName, String userName) {}

	private static String extractBlock(String src, String start, String end) {
		int s = src.indexOf(start);
		if (s < 0)
			return null;
		s += start.length();
		int e = src.indexOf(end, s);
		if (e < 0)
			e = src.length();
		return src.substring(s, e);
	}

	private static String extractHeaderValue(String headers, String key) {
		Pattern p = Pattern.compile(key + "=([^,]+(?:,(?!\\s*[a-zA-Z0-9-]+=)[^,]*)*)");
		Matcher m = p.matcher(headers);
		return m.find() ? m.group(1).trim() : null;
	}

	public static void exportToExcel(List<SummaryData> summaryData, String filePath) throws Exception {
		String[] headers = { "Company Name", "User Name", "Likes", "Dislikes" };

		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(filePath)) {
			Sheet sheet = workbook.createSheet("Summary");

			CellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerStyle.setFont(headerFont);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(headerFont);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerStyle);
			}

			int rowIdx = 1;
			for (SummaryData d: summaryData) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(d.companyName());
				row.createCell(1).setCellValue(d.userName());
				row.createCell(2).setCellValue(d.likes());
				row.createCell(3).setCellValue(d.dislikes());
			}

			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
				sheet.setDefaultColumnStyle(i, cellStyle);
			}

			workbook.write(out);
		}
	}

	public static List<DataElem> ingestData(List<String> requestLines) throws Exception {
		var data = new ArrayList<DataElem>();
		var objectMapper = new ObjectMapper();

		for (String requestData : requestLines) {
			String bodyJson = extractBlock(requestData, "body=[", "]");
			JsonNode body = objectMapper.readTree(bodyJson);

			JsonNode whNode = body.get("wasHelpful");
			Boolean wasHelpful = (whNode == null || whNode.isNull()) ? null : whNode.asBoolean();

			String headers = extractBlock(requestData, "headers=[", "}]");
			String token = extractHeaderValue(headers, "authorization");

			String unsignedToken = token.substring(0, token.lastIndexOf('.') + 1);

			Jwt<?, Claims> jwt = Jwts.parserBuilder().setClock(() -> new Date(0L)).build()
					.parseClaimsJwt(unsignedToken);

			Claims claims = jwt.getBody();

			String userName = claims.getSubject();
			String companyName = claims.get("companyName", String.class);
			String participantType = claims.get("participantType", String.class);

			if ("Financier".equals(participantType) && wasHelpful != null)
				data.add(new DataElem(companyName, userName, wasHelpful));
		}
		return data;
	}

	public static List<SummaryData> getSummary(List<DataElem> data) {
		if (data == null || data.isEmpty())
			return Collections.emptyList();

		Map<Key, int[]> counts = new LinkedHashMap<>();

		for (DataElem elem : data) {
			var key = new Key(elem.companyName(), elem.userName());
			int[] c = counts.computeIfAbsent(key, k -> new int[2]);
			if (elem.liked())
				c[0]++;
			else
				c[1]++;
		}

		return counts.entrySet().stream().map(en -> new SummaryData(en.getKey().companyName(), en.getKey().userName(),
				en.getValue()[0], en.getValue()[1])).toList();
	}

	public static void main(String[] args) throws Exception {
		Predicate<String> pred1 = (arg) -> arg.contains("REQUEST");
		Predicate<String> pred2 = (arg) -> arg.contains("/whatsNewDocument/feedback");
		Predicate<String> cond = pred1.and(pred2);
		
		File currDir = new File(".");
		File[] files = currDir.listFiles();
		List<String> requestLines = new ArrayList<>();
		
		for (File file: files) {
			if (file.isFile() && file.getName().endsWith(".log")) {
				List<String> lines = Files.lines(file.toPath()).filter(cond).toList();
				requestLines.addAll(lines);
			}
		}

		System.out.println(requestLines.size());
		requestLines.forEach(System.out::println);

		List<DataElem> data = ingestData(requestLines);
		// data.forEach(System.out::println);

		List<SummaryData> summaryData = getSummary(data);
		summaryData.forEach(System.out::println);
		exportToExcel(summaryData, "data.xlsx");
	}
}

package maven;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Converter {
	public static void main(String[] args) {
		String inputPath = "Book10.xlsx";
		String outputPath = "myndprodbportal.html";
		
		var converter = new Converter();

		try (var fis = new FileInputStream(inputPath); var workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(0);
			var formatter = new DataFormatter();

			List<String> headers = new ArrayList<>();
			List<List<String>> rows = new ArrayList<>();

			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();

			Row headerRow = sheet.getRow(firstRow);
			int maxCols = 0;
			if (headerRow != null) {
				maxCols = headerRow.getLastCellNum();
				for (int c = 0; c < maxCols; c++) {
					Cell cell = headerRow.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					headers.add(formatter.formatCellValue(cell).trim());
				}
			}

			// Data rows
			for (int r = firstRow + 1; r <= lastRow; r++) {
				Row row = sheet.getRow(r);
				if (row == null)
					continue;

				List<String> cells = new ArrayList<>();
				boolean allBlank = true;
				for (int c = 0; c < maxCols; c++) {
					Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					String val = formatter.formatCellValue(cell).trim();
					if (!val.isEmpty())
						allBlank = false;
					cells.add(val);
				}
				if (!allBlank)
					rows.add(cells);
			}

			String html = converter.buildHtml(headers, rows, inputPath);

			try (FileWriter fw = new FileWriter(outputPath)) {
				fw.write(html);
			}

			System.out.println("HTML written to: " + outputPath + " (" + rows.size() + " rows)");

		} catch (IOException e) {
			System.err.println("Error processing file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String buildHtml(List<String> headers, List<List<String>> rows, String source) {
		var sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n");
		sb.append("<meta charset=\"UTF-8\">\n");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
		sb.append("<title>Index Report</title>\n");
		sb.append("<style>\n");
		sb.append("  * { box-sizing: border-box; }\n");
		sb.append("  body { font-family: -apple-system, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;");
		sb.append(" margin: 0; padding: 32px; background: #f4f6f8; color: #1a1a2e; }\n");
		sb.append("  h1 { font-size: 22px; margin: 0 0 4px; }\n");
		sb.append("  .meta { color: #6b7280; font-size: 13px; margin-bottom: 20px; }\n");
		sb.append("  .table-wrap { overflow-x: auto; background: #fff; border-radius: 10px;");
		sb.append(" box-shadow: 0 1px 3px rgba(0,0,0,0.1), 0 1px 2px rgba(0,0,0,0.06); }\n");
		sb.append("  table { border-collapse: collapse; width: 100%; font-size: 13px; }\n");
		sb.append("  thead th { background: #1f2937; color: #fff; text-align: left; padding: 12px 14px;");
		sb.append(" position: sticky; top: 0; font-weight: 600; white-space: nowrap; }\n");
		sb.append("  tbody td { padding: 10px 14px; border-bottom: 1px solid #eceff1; vertical-align: top; }\n");
		sb.append("  tbody tr:nth-child(even) { background: #f9fafb; }\n");
		sb.append("  tbody tr:hover { background: #eef2ff; }\n");
		sb.append("  .badge { display: inline-block; padding: 2px 8px; border-radius: 12px;");
		sb.append(" font-size: 11px; font-weight: 600; }\n");
		sb.append("  .badge-yes { background: #dcfce7; color: #166534; }\n");
		sb.append("  .badge-no  { background: #fee2e2; color: #991b1b; }\n");
		sb.append("  .mono { font-family: 'SF Mono', Consolas, monospace; font-size: 12px; }\n");
		sb.append("</style>\n</head>\n<body>\n");

		sb.append("<h1>Index Report</h1>\n");
		sb.append("<div class=\"meta\">Source: ").append(escape(source));
		sb.append(" &middot; ").append(rows.size()).append(" rows</div>\n");

		sb.append("<div class=\"table-wrap\">\n<table>\n<thead>\n<tr>\n");
		for (String h: headers) {
			sb.append("<th>").append(escape(prettify(h))).append("</th>\n");
		}
		sb.append("</tr>\n</thead>\n<tbody>\n");

		for (List<String> row: rows) {
			sb.append("<tr>\n");
			for (int i = 0; i < row.size(); i++) {
				String header = i < headers.size() ? headers.get(i).toLowerCase() : "";
				String val = row.get(i);
				sb.append("<td>").append(renderCell(header, val)).append("</td>\n");
			}
			sb.append("</tr>\n");
		}

		sb.append("</tbody>\n</table>\n</div>\n</body>\n</html>\n");
		return sb.toString();
	}

	public String renderCell(String header, String val) {
		if (header.equals("is_unique")) {
			String v = val.trim().toLowerCase();
			boolean yes = v.equals("1") || v.equals("true") || v.equals("yes");
			boolean no = v.equals("0") || v.equals("false") || v.equals("no");
			if (yes) return "<span class=\"badge badge-yes\">Yes</span>";
			if (no) return "<span class=\"badge badge-no\">No</span>";
		}
		if (header.equals("key_columns") || header.equals("included_columns") || header.equals("index_name")
				|| header.equals("table_name") || header.equals("database_name")) {
			return "<span class=\"mono\">" + escape(val) + "</span>";
		}
		return escape(val);
	}

	public String prettify(String header) {
		if (header == null || header.isEmpty()) return header;
		String[] parts = header.replace('_', ' ').split(" ");
		StringBuilder sb = new StringBuilder();
		for (String p : parts) {
			if (!p.isEmpty())
				sb.append(Character.toUpperCase(p.charAt(0)))
					.append(p.length() > 1 ? p.substring(1) : "")
					.append(' ');
		}
		return sb.toString().trim();
	}

	public String escape(String s) {
		if (s == null) return "";
		return s.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;");
	}
}

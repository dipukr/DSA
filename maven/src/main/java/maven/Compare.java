package maven;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Compare {
	public void compare(File file1, File file2, File file3, String sheetName, int rowStart, int rowEnd, int colStart, int colEnd) {
		try (FileInputStream fileInputStream1 = new FileInputStream(file1);
			FileInputStream fileInputStream2 = new FileInputStream(file2);
			Workbook workbook1 = new HSSFWorkbook(fileInputStream1);
			Workbook workbook2 = new HSSFWorkbook(fileInputStream2);
			Workbook resultWorkbook = new HSSFWorkbook()) {

			Sheet sheet1 = workbook1.getSheet(sheetName);
			Sheet sheet2 = workbook2.getSheet(sheetName);

			assert sheet1 != null && sheet1 != null;

			Sheet resultSheet = resultWorkbook.createSheet(sheetName);

			for (int row = rowStart; row <= rowEnd; row++) {
				Row row1 = sheet1.getRow(row);
				Row row2 = sheet2.getRow(row);
				Row resultRow = resultSheet.createRow(row);

				for (int col = colStart; col <= colEnd; col++) {
					Cell resultCell = resultRow.createCell(col);

					Cell cell1 = (row1 != null) ? row1.getCell(col) : null;
					Cell cell2 = (row2 != null) ? row2.getCell(col) : null;

					if (cell1 == null || cell2 == null) {
						resultCell.setCellValue("");
						continue;
					}

					String cell1Str = cell1.toString();
					String cell2Str = cell2.toString();

					try {
						BigDecimal cell1Val = new BigDecimal(cell1Str);
						BigDecimal cell2Val = new BigDecimal(cell2Str);
						BigDecimal diff = cell1Val.subtract(cell2Val);
						resultCell.setCellValue(diff.doubleValue());
					} catch (NumberFormatException e) {
						resultCell.setCellValue(cell1Str + " vs " + cell2Str);
					}
				}
			}

			try (FileOutputStream fos = new FileOutputStream(file3)) {
				resultWorkbook.write(fos);
			} catch (IOException e) {
				throw new RuntimeException("Failed to write result file: " + file3.getAbsolutePath(), e);
			}

		} catch (IOException e) {
			throw new RuntimeException("Failed to read input files", e);
		}
	}

	public static void main(String[] args) {
		var file1 = new File("file0.xlsx");
		var file2 = new File("file1.xlsx");
		var file3 = new File("filer.xlsx");
		
		try {
			Compare compare = new Compare();
			compare.compare(file1, file2, file3, "Sheet1", 0, 10, 0, 5);
			System.out.println("Comparison complete. Result written to: " + file3.getAbsolutePath());
		} catch (RuntimeException e) {
			System.err.println("Comparison failed: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
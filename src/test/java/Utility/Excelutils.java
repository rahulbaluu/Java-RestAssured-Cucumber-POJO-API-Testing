package Utility;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class Excelutils {

	public static String getCellData(String sheetName, int rowNum, int colNum) throws IOException {

		FileInputStream file = new FileInputStream("src/test/resources/testExcel/Signup_Registration_Detail.xlsx");
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheet(sheetName);


		if (sheet == null) {
			workbook.close();
			throw new RuntimeException("Sheet NOT found in Excel: " + sheetName);
		}


		Row row = sheet.getRow(rowNum);
		if (row == null) {
			workbook.close();
			throw new RuntimeException("Row NOT found: " + rowNum);
		}

		Cell cell = row.getCell(colNum);
		if (cell == null) {
			workbook.close();
			throw new RuntimeException("Column NOT found: " + colNum);
		}

		DataFormatter formatter = new DataFormatter();
		String value = formatter.formatCellValue(cell);


		workbook.close();
		file.close();
		return value;

	}
}


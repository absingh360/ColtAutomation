package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.constants.GlobalConstant.FileNames;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class ExcelReader {
	File file = null;
	FileInputStream inputStream = null;
	Workbook workbook = null;
	String fileExtensionName = null;
	Sheet sheet = null;

	public ExcelReader(String fileName, String sheetName) throws IOException {
		file = new File(getPath() + "\\ExcelFile\\" + fileName);
		inputStream = new FileInputStream(file);
		workbook = null;

		fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		sheet = workbook.getSheet(sheetName);
	}

	public ExcelReader() {
		// TODO Auto-generated constructor stub
	}

	// Get absolute path
	public String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("/", "//");
		return path;
	}

	@SuppressWarnings({ "deprecation", "null" })
	public String readExcel(int row, int col) throws IOException {
		String value = "";
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		if (row > rowCount) {
			return "INVALID ROW...";
		}
		Row row1 = sheet.getRow(row);
		if (col > row1.getLastCellNum()) {
			return "INVALID COLUMN...";
		} else {
			row1 = sheet.getRow(row);
			switch (row1.getCell(col).getCellTypeEnum()) {
			case NUMERIC:
				value = String.valueOf(new DataFormatter().formatCellValue(row1.getCell(col)));
				break;
			case STRING:
				value = String.valueOf(row1.getCell(col).getStringCellValue());
				break;
			case FORMULA:
				value = String.valueOf(row1.getCell(col).getCellFormula());
				break;
			case BOOLEAN:
				value = String.valueOf(row1.getCell(col).getBooleanCellValue());
				break;
			case BLANK:
				value = null;
				break;
			default:
				Assert.fail("Invalid Format is found");
				break;
			}
		}
		return value;
	}

	public int getRowCount() {
		return sheet.getLastRowNum() - sheet.getFirstRowNum();
	}

	public int getColCount() {
		Row row1 = sheet.getRow(0);
		return row1.getLastCellNum();
	}

	public int getRows(String fileName, String sheetName) throws IOException {
		String excelFilePath = "D://MyWorkRepo//MyTest//MyTest//Web//colt_project_swapnil//ExcelFile//" + fileName
				+ ".xlsx";
		System.out.println(excelFilePath);
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet(sheetName);
		int totalRows = sheet.getLastRowNum();
		return totalRows;

	}

	public static void deleteFile() {
		try {

			File file = new File(Utilities.getPath() + "\\ExcelFile\\" + "Result.xlsx");

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("File not exist.");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static void createExcelFile() {
		try {
			String filename = Utilities.getPath() + "\\ExcelFile\\" + "Result.xlsx";
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sheet1");

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("file created");
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("resource")
	public void writeData(String value, int row, int col) // throws Exception
	{

		try {
			File fileName = new File(Utilities.getPath() + "\\ExcelFile\\" + "Result.xlsx");
			FileInputStream fsIP = new FileInputStream(fileName);

			// Access the workbook
			XSSFWorkbook wb = new XSSFWorkbook(fsIP);

			// Access the worksheet, so that we can update / modify it.
			XSSFSheet worksheet = wb.getSheet("Sheet1");

			// declare a Cell object
			XSSFCell cell = null;// row.getCell(1)
			XSSFRow row1 = null;

			try {

				if (worksheet.getRow(row) == null) {
					row1 = worksheet.createRow(row);
				}
			} catch (Exception e) {
				row1 = worksheet.createRow(row);
			}

			// Cell cell = null;
			try {
				if (worksheet.getRow(row).getCell(col) == null) {
					cell = worksheet.getRow(row).createCell(col);
				}
			} catch (Exception e) {
				cell = worksheet.getRow(row).createCell(col);
			}

			// Access the second cell in second row to update the value
			cell = worksheet.getRow(row).getCell(col);

			// Get current cell value value and overwrite the value
			cell.setCellValue(value);

			// Close the InputStream
			fsIP.close();

			// Open FileOutputStream to write updates
			FileOutputStream output_file = new FileOutputStream(new File(getPath() + "\\ExcelFile\\" + "Result.xlsx"));

			// write changes
			wb.write(output_file);

			// close the stream
			output_file.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@SuppressWarnings({ "resource", "deprecation" })
	public void updateDealPricingcsvValues(String value, int row, int col) // throws Exception
	{
		File fileName = null;

		try {

			 fileName = new File(FileNames.TestDataRelativePath + "\\" + "DealPricingCSVupload.csv");

			// Read existing file
			CSVReader reader = new CSVReader(new FileReader(fileName), ',');
			List<String[]> csvBody = reader.readAll();
			// get CSV row column and replace with by using row and column
			csvBody.get(row)[col] = value;
			System.out.println(csvBody.get(row)[col]);
			reader.close();

			// Write to CSV file which is open
			CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',');
			writer.writeAll(csvBody);
			writer.flush();
			writer.close();
			
			

		} catch (Exception e) {
			fileName.setReadable(true);

			e.printStackTrace();

		}
	}

	public int getNumberofRows(String sheetName) throws IOException {
		String excelFilePath = "C://Users//abhishekbs//eclipse-workspace//colt_project_swapnil//ExcelFile//fetchBuildingType.xlsx";

		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet(sheetName);
		int totalRows = sheet.getLastRowNum();
		return totalRows;
	}

	@SuppressWarnings("deprecation")
	public static String getColumnValueFromExcel(String sheetName, int rownum, int columnnum) throws IOException {

		String excelFilePath = "D://MyWorkRepo//MyTest//MyTest//Web//colt_project_swapnil//ExcelFile//fetchBuildingType.xlsx";
		Row row;
		String value = "";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rownum);
		if (row.getCell(columnnum).getCellType() == Cell.CELL_TYPE_NUMERIC) {
			int i = (int) row.getCell(columnnum).getNumericCellValue();
			value = String.valueOf(i);
		} else {
			value = row.getCell(columnnum).getStringCellValue();
		}

		return value;
	}

	@SuppressWarnings("deprecation")
	public static String getColumnValueFromExcel(String filename, String sheetName, int rownum, int columnnum)
			throws IOException {

		String excelFilePath = "D://MyWorkRepo//MyTest//MyTest//Web//colt_project_swapnil//ExcelFile//" + filename
				+ ".xlsx";

		System.out.println(excelFilePath);
		Row row;
		String value = "";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rownum);
		System.out.println(rownum);
		System.out.println(columnnum);

		if (row.getCell(columnnum).getCellType() == Cell.CELL_TYPE_NUMERIC) {
			int i = (int) row.getCell(columnnum).getNumericCellValue();
			value = String.valueOf(i);
		} else {
			value = row.getCell(columnnum).getStringCellValue();
		}

		return value;
	}

}

package com.me.abubi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileManager {

	private static XSSFCellStyle fCellStyle;
	private static XSSFCellStyle pCellStyle;
	private static XSSFCellStyle kCellStyle;

	public static void exportExcel(ArrayList<Year> data, File file) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
			initializeStyles(workbook);
			for (Year year : data) {
				Sheet sheet = workbook.createSheet(year.getYearName());
				int rowCounter = 0;
				for (Month month : year.getMonths()) {
					Row monthRow = sheet.createRow(rowCounter++);
					Cell cell = monthRow.createCell(0);
					cell.setCellValue(month.getMonthName());
					cell = monthRow.createCell(1);
					System.out.println("Creating month: " + cell.getStringCellValue());
					cell.setCellValue(month.getTotalEntrance());
					sheet.createRow(rowCounter++);
					int cellCounter = 0;
					for (Day d : month.getDays()) {
						Row dayRow = sheet.createRow(rowCounter++);
						Cell dayCell = dayRow.createCell(cellCounter++);
						dayCell.setCellValue(d.getDayNumber());
						System.out.println("Adding day: " + dayCell.getNumericCellValue());
						dayCell = dayRow.createCell(cellCounter++);
						dayCell.setCellValue(d.getEntries().size());
						for (Entry entry : d.getEntries()) {
							Cell entryCell = dayRow.createCell(cellCounter++);
							entryCell.setCellValue(entry.getDate());
							entryCell.setCellStyle(getCellStyleFromUser(entry.getUser()));
							System.out.println("Adding entry : " + entryCell.getDateCellValue());
						}
						cellCounter = 0;
					}
					sheet.createRow(rowCounter++);
				}
			}
			System.out.println("File saved successfully");
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.flush();
			workbook.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initializeStyles(XSSFWorkbook workbook) {
		CreationHelper createHelper = workbook.getCreationHelper();
		fCellStyle = workbook.createCellStyle();
		fCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		fCellStyle.setFillForegroundColor(new XSSFColor(Entry.fColor));
		fCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm:ss"));

		pCellStyle = workbook.createCellStyle();
		pCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		pCellStyle.setFillForegroundColor(new XSSFColor(Entry.pColor));
		pCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm:ss"));

		kCellStyle = workbook.createCellStyle();
		kCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		kCellStyle.setFillForegroundColor(new XSSFColor(Entry.kColor));
		kCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm:ss"));
	}

	private static CellStyle getCellStyleFromUser(String user) {
		switch (user) {
		case Entry.kNick:
			return kCellStyle;
		case Entry.pNick:
			return pCellStyle;
		default:
			return fCellStyle;
		}
	}

	public static ArrayList<Year> importCsv(File file) {
		ArrayList<Year> toReturn = null;
		try {
			toReturn = new ArrayList<>();
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			while (sheetIterator.hasNext()) {
				Sheet sheet = sheetIterator.next();
				System.out.println("Trying to parse year: " + sheet.getSheetName());
				int yearNumber = Integer.valueOf(sheet.getSheetName());
				Year year = new Year(sheet.getSheetName());
				parseYear(sheet, year);
				toReturn.add(year);
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	private static void parseYear(Sheet sheet, Year year) throws Exception {
		Month month = null;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell firstCell = row.getCell(0);
			CellType cellType = firstCell.getCellTypeEnum();
			switch (cellType) {
			case NUMERIC:
				Day day = new Day((int) firstCell.getNumericCellValue());
				Iterator<Cell> cellIterator = row.cellIterator();
				if (row.getPhysicalNumberOfCells() > 1) {
					cellIterator.next();// skip day number
					cellIterator.next();// skip entries number
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String user = getUserFromColor(cell);
						Entry entry = new Entry(user, DateUtil.getJavaDate(cell.getNumericCellValue()));
						day.addEntry(entry);
					}
				}
				month.addDay(day);
				break;
			case STRING:
				month = new Month(row.getCell(0).getStringCellValue());
				year.addMonth(month);
				break;
			default:
				break;
			}
		}
	}

	private static String getUserFromColor(Cell cell) {
		CellStyle cellStyle = cell.getCellStyle();
		XSSFColor color = (XSSFColor) cellStyle.getFillForegroundColorColor();
		if (color.equals(new XSSFColor(Entry.kColor))) {
			return Entry.kNick;
		} else if (color.equals(new XSSFColor(Entry.pColor))) {
			return Entry.pNick;
		} else {
			return Entry.fNick;
		}
	}

	public static ArrayList<Year> parseData(File file) {
		ArrayList<Year> collection = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			collection = (ArrayList<Year>) ois.readObject();
			System.out.println("Data correctly loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	public static ArrayList<Year> loadData(File file) {
		ArrayList<Year> data = null;
		try {
			data = FileManager.parseData(file);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				data = FileManager.importCsv(file);
			} catch (Exception ex2) {
				ex2.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: file not recognized", "ERROR", JOptionPane.ERROR_MESSAGE);
				throw ex2;
			}
		}
		return data;
	}

	public static void saveData(ArrayList<Year> data, File file) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(data);
			System.out.println("Data correctly saved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<Year> exampleData() {
		Day d1 = new Day(1);
		Day d2 = new Day(2);
		Day d3 = new Day(1);

		d1.addEntry("f");
		d1.addEntry("p");
		d1.addEntry("f");
		d1.addEntry("p");
		d1.addEntry("f");
		d1.addEntry("f");

		d2.addEntry("p");
		d2.addEntry("p");
		d2.addEntry("f");
		d2.addEntry("p");

		d3.addEntry("f");
		d3.addEntry("f");
		d3.addEntry("f");

		Year year = new Year();
		Month month1 = new Month(5);
		month1.addDay(d1);
		month1.addDay(d2);
		Month month2 = new Month(6);
		month2.addDay(d3);

		year.addMonth(month1);
		year.addMonth(month2);

		ArrayList<Year> years = new ArrayList<>();
		years.add(year);
		return years;
	}

	private static void testSaveAndLoad() {
		try {
			ArrayList<Year> years = exampleData();
			System.out.println("Before save: " + years);
			File test = new File("/Users/otacon94/Desktop/data.dat");
			saveData(years, test);
			ArrayList<Year> afterParse = parseData(test);
			System.out.println("After load: " + afterParse);
			System.out.println(afterParse.equals(years));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testSaveExcel() {
		try {
			ArrayList<Year> years = exampleData();
			File test = new File("/Users/otacon94/Desktop/test.xlsx");
			exportExcel(years, test);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testLoadExcel() {
		try {
			ArrayList<Year> years = exampleData();
			System.out.println("saved: " + years);
			File test = new File("/Users/otacon94/Desktop/test.xlsx");
			ArrayList<Year> retrieved = importCsv(test);
			System.out.println("retrieved: " + retrieved);
			System.out.println(years.equals(retrieved));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testLoadExcel();
	}

}

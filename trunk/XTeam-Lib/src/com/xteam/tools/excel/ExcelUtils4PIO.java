package com.xteam.tools.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xteam.tools.CommonUtils;


/**
 * <p>
 * 以 PIO 为核心的 EXCEL 操作工具类
 * </p>
 * 
 * @author 汤垲峰
 */
public class ExcelUtils4PIO implements ExcelUtils {

	/**
	 * <P>
	 * 效率高
	 * </P>
	 * 
	 * @author 汤垲峰 2009-3-17
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		long a = System.currentTimeMillis();
		List<String> st = new ArrayList<String>();
		st.add("111111111111");
		st.add("22222222");
		st.add("3333333333");
		ExcelUtils eu = new ExcelUtils4PIO("f:/Book1.xls");
		for (int i = 0; i < 10; i++) {
			eu.appendRow(st, "Sheet1");
		}
		eu.close();
		long b = System.currentTimeMillis();

		System.err.println((b - a));
	}

	private final HSSFWorkbook workBook;
	
	OutputStream out;

	/**
	 * 文件路径
	 * @param filePath
	 * @throws IOException
	 */
	public ExcelUtils4PIO(String filePath) throws IOException {
		this(new File(filePath));
	}

	/**
	 * 文件对象
	 * @param file
	 * @throws IOException
	 */
	public ExcelUtils4PIO(File file) throws IOException {
		this(new FileInputStream(file));
	}

	/**
	 * 输入流
	 * @param fis
	 * @throws IOException
	 */
	public ExcelUtils4PIO(InputStream fis) throws IOException {
		workBook = new HSSFWorkbook(fis);
	}
	
	/**
	 * <P>
	 * 获取制定ID 的工作表
	 * </P>
	 * 
	 * @author 汤垲峰 2009-3-17
	 * @param sheetIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> getExcelSheetByIndex(int sheetIndex) {
		List<List<String>> sheetList = new ArrayList<List<String>>();
		HSSFSheet sheet = null;
		if (workBook != null) {
			sheet = workBook.getSheetAt(sheetIndex);
			if (sheet == null)
				sheet = workBook.getSheetAt(0);
		}

		if (sheet != null) {
			Iterator<HSSFRow> its = sheet.rowIterator();
			while (its.hasNext()) {
				List<String> scell = new ArrayList<String>();
				HSSFRow row = its.next();
				Iterator<HSSFCell> cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = cells.next();
					String value = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getRichStringCellValue().getString();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						value = String.valueOf(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						value = cell.getCellComment().toString();
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "o";
						break;
					default:
						value = cell.getCellComment().toString();
					}
					scell.add(value);
				}
				if (CommonUtils.notEmpty(scell))
					sheetList.add(scell);
			}
		}
		return sheetList;
	}

	/**
	 * <P>
	 * 读取工作表 返回为链表数组
	 * </P>
	 * 
	 * @author 汤垲峰 2009-3-17
	 * @param is
	 * @param sheetName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> getExcelSheetByName(String sheetName) {
		List<List<String>> sheetList = new ArrayList<List<String>>();
		HSSFSheet sheet = null;
		if (workBook != null)
			if (CommonUtils.isEmpty(sheetName)) {
				sheet = workBook.getSheetAt(0);
			} else {
				sheet = workBook.getSheet(sheetName);
			}

		if (sheet != null) {
			Iterator<HSSFRow> its = sheet.rowIterator();
			while (its.hasNext()) {
				List<String> scell = new ArrayList<String>();
				HSSFRow row = its.next();
				Iterator<HSSFCell> cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = cells.next();
					String value = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getRichStringCellValue().getString();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						value = String.valueOf(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						value = " ";
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = " ";
						break;
					default:
						value = " ";
					}
					scell.add(value);
				}
				if (CommonUtils.notEmpty(scell))
					sheetList.add(scell);
			}
		}
		return sheetList;
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#close()
	 */
	public void close() {
		try {
			out = new FileOutputStream("f:/Book1.xls");
			workBook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#getExcelSheet(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object>> getExcelSheet(String sheetName) {
		List<List<Object>> sheetList = new ArrayList<List<Object>>();
		HSSFSheet sheet = null;
		if (workBook != null)
			if (CommonUtils.isEmpty(sheetName)) {
				sheet = workBook.getSheetAt(0);
			} else {
				sheet = workBook.getSheet(sheetName);
			}

		if (sheet != null) {
			Iterator<HSSFRow> its = sheet.rowIterator();
			while (its.hasNext()) {
				List<Object> scell = new ArrayList<Object>();
				HSSFRow row = its.next();
				Iterator<HSSFCell> cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = cells.next();
					Object value = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getRichStringCellValue().getString();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						value = cell.getNumericCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					default:
						value = "";
					}
					scell.add(value);
				}
				if (CommonUtils.notEmpty(scell))
					sheetList.add(scell);
			}
		}
		return sheetList;
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#getExcelSheet(int)
	 */
	@SuppressWarnings("unchecked")
	public List<List<Object>> getExcelSheet(int index) {
		List<List<Object>> sheetList = new ArrayList<List<Object>>();
		HSSFSheet sheet = null;
		if (workBook != null) {
			sheet = workBook.getSheetAt(index);

			if (sheet == null)
				sheet = workBook.getSheetAt(0);
		}

		if (sheet != null) {
			Iterator<HSSFRow> its = sheet.rowIterator();
			while (its.hasNext()) {
				List<Object> scell = new ArrayList<Object>();
				HSSFRow row = its.next();
				Iterator<HSSFCell> cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = cells.next();
					Object value = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getRichStringCellValue().getString();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						value = cell.getNumericCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					default:
						value = "";
					}
					scell.add(value);
				}
				if (CommonUtils.notEmpty(scell))
					sheetList.add(scell);
			}
		}
		return sheetList;
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#appendRow(java.util.List,
	 *      java.lang.String)
	 */
	public void appendRow(List<String> row, String sheetName) {
		HSSFSheet sheet = workBook.getSheet(sheetName);
		int number  = sheet.getPhysicalNumberOfRows();
		int lastRow = sheet.getLastRowNum();
		lastRow = (lastRow == 0) ? number : (lastRow + 1);
		HSSFRow r = sheet.createRow(lastRow);
		for(int i = 0, size = row.size(); i < size; i++ ) {
			String str = row.get(i);
			HSSFCell cell = r.createCell(i, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(str));
		}
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#appendRow(java.util.List, int)
	 */
	public void appendRow(List<String> row, int sheetIndex) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#wirteCell(java.lang.String, int,
	 *      int, int)
	 */
	public void wirteCell(String cvalue, int rowNum, int colNum, int sheetIndex) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#wirteCell(java.lang.String, int,
	 *      int, java.lang.String)
	 */
	public void wirteCell(String cvalue, int rowNum, int colNum,
			String sheetName) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeRow(java.util.List, int, int)
	 */
	public void writeRow(List<String> row, int rowNum, int sheetIndex) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeRow(java.util.List, int,
	 *      java.lang.String)
	 */
	public void writeRow(List<String> row, int rowNum, String sheetName) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#createSheet(java.lang.String)
	 */
	public void createSheet(String sheetName) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#save()
	 */
	public void save() {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeCell(java.lang.String, int,
	 *      int, int)
	 */
	public void writeCell(String cvalue, int rowNum, int colNum, int sheetIndex) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeCell(java.lang.String, int,
	 *      int, java.lang.String)
	 */
	public void writeCell(String cvalue, int rowNum, int colNum,
			String sheetName) {
		/* TODO Auto-generated method stub */

	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#write(java.io.OutputStream)
	 */
	public void write(OutputStream os) {
		/* TODO Auto-generated method stub */

	}
}

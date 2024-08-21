package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader {

	HashMap<String, String> storevalues = new HashMap();

	public static List<HashMap<String, String>> data(String filePath, String sheetName) throws IOException {
		List<HashMap<String, String>> myData = new ArrayList();
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		XSSFSheet workSheet = workBook.getSheet(sheetName);
		int totalRows = workSheet.getLastRowNum();

		XSSFRow headerRow = workSheet.getRow(0);

		for (int i = 1; i <= totalRows; i++) {
			XSSFRow currentRow = workSheet.getRow(i);
			HashMap<String, String> currentMap = new HashMap<String, String>();
			for (int j = 0; j < currentRow.getLastCellNum(); j++) {
				XSSFCell currentCell = currentRow.getCell(j);
				currentMap.put(headerRow.getCell(j).toString(), currentCell.toString());
			}
			myData.add(currentMap);
		}
		fis.close();
		return myData;
	}
}

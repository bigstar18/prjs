package gnnt.MEBS.common.front.statictools.filetools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gnnt.MEBS.common.front.statictools.Tools;

public class POIExcelUtil {
	private int totalRows = 0;
	private int totalCells = 0;

	public List<ArrayList<String>> read(FileInputStream paramFileInputStream, String paramString) {
		Object localObject = new ArrayList();
		if ((paramString == null) || (!paramString.matches("^.+\\.(?i)((xls)|(xlsx))$"))) {
			return (List<ArrayList<String>>) localObject;
		}
		boolean bool = true;
		if (paramString.matches("^.+\\.(?i)(xlsx)$")) {
			bool = false;
		}
		if (paramFileInputStream == null) {
			File localFile = new File(paramString);
			if ((localFile == null) || (!localFile.exists())) {
				return (List<ArrayList<String>>) localObject;
			}
			try {
				localObject = read(new FileInputStream(localFile), bool);
			} catch (Exception localException2) {
				localException2.printStackTrace();
			}
		} else {
			try {
				localObject = read(paramFileInputStream, bool);
			} catch (Exception localException1) {
				localException1.printStackTrace();
			}
		}
		return (List<ArrayList<String>>) localObject;
	}

	public List<ArrayList<String>> read(InputStream paramInputStream, boolean paramBoolean) {
		List localList = null;
		try {
			Workbook localXSSFWorkbook = paramBoolean ? new HSSFWorkbook(paramInputStream) : new XSSFWorkbook(paramInputStream);
			localList = read(localXSSFWorkbook);
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
		return localList;
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public int getTotalCells() {
		return this.totalCells;
	}

	private List<ArrayList<String>> read(Workbook paramWorkbook) {
		ArrayList localArrayList1 = new ArrayList();
		Sheet localSheet = paramWorkbook.getSheetAt(0);
		this.totalRows = localSheet.getPhysicalNumberOfRows();
		if ((this.totalRows >= 1) && (localSheet.getRow(0) != null)) {
			this.totalCells = localSheet.getRow(0).getPhysicalNumberOfCells();
		}
		for (int i = 0; i < this.totalRows; i++) {
			Row localRow = localSheet.getRow(i);
			if (localRow != null) {
				ArrayList localArrayList2 = new ArrayList();
				for (int j = 0; j < getTotalCells(); j = (short) (j + 1)) {
					Cell localCell = localRow.getCell(j);
					String str = "";
					if (localCell == null) {
						localArrayList2.add(str);
					} else {
						if (0 == localCell.getCellType()) {
							if (HSSFDateUtil.isCellDateFormatted(localCell)) {
								str = Tools.fmtDate(localCell.getDateCellValue());
							} else {
								str = getRightStr(localCell.getNumericCellValue() + "");
							}
						} else if (1 == localCell.getCellType()) {
							str = localCell.getStringCellValue();
						} else if (4 == localCell.getCellType()) {
							str = localCell.getBooleanCellValue() + "";
						} else {
							str = localCell.toString() + "";
						}
						localArrayList2.add(str);
					}
				}
				localArrayList1.add(localArrayList2);
			}
		}
		return localArrayList1;
	}

	private String getRightStr(String paramString) {
		DecimalFormat localDecimalFormat = new DecimalFormat("#.000000");
		String str = localDecimalFormat.format(new Double(paramString));
		if (str.matches("^[-+]?\\d+\\.[0]+$")) {
			str = str.substring(0, str.indexOf("."));
		}
		return str;
	}

	public static void main(String[] paramArrayOfString) throws Exception {
		for (int i = 0; i < 20; i++) {
			List localList = new POIExcelUtil().read(null, "E://我的平台页面计划xlsx.xlsx");
			Iterator localIterator1 = localList.iterator();
			while (localIterator1.hasNext()) {
				ArrayList localArrayList = (ArrayList) localIterator1.next();
				StringBuffer localStringBuffer = new StringBuffer();
				Iterator localIterator2 = localArrayList.iterator();
				while (localIterator2.hasNext()) {
					String str = (String) localIterator2.next();
					localStringBuffer.append(",").append(str);
				}
				if (localStringBuffer.length() > 0) {
					System.out.println(localStringBuffer.deleteCharAt(0).toString());
				}
			}
		}
		Thread.sleep(100000L);
	}
}

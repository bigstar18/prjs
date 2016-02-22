package gnnt.trade.bank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelReader
{
  private String excelURI = "";
  private String sheetName = "";
  private HSSFSheet sheet = null;
  
  public ExcelReader() {}
  
  public ExcelReader(String excelURI, String sheetName)
  {
    getExcelURI(excelURI);
    if ((sheetName == null) || (sheetName.trim().equals(""))) {
      sheetName = "page1";
    }
    this.sheetName = sheetName;
    init();
  }
  
  public String readPoint(int rowNum, int cellNum)
  {
    int row = this.sheet.getPhysicalNumberOfRows();
    if (rowNum >= row) {
      return "";
    }
    HSSFRow rows = this.sheet.getRow(rowNum);
    int cell = rows.getPhysicalNumberOfCells();
    if (cellNum >= cell) {
      return "";
    }
    HSSFCell cells = rows.getCell((short)cellNum);
    if (cells == null) {
      return "";
    }
    return cells.getStringCellValue();
  }
  
  public String[] readCell(int cellNum)
  {
    int rowNum = this.sheet.getPhysicalNumberOfRows();
    String[] values = new String[rowNum];
    for (int i = 0; i < rowNum; i++)
    {
      HSSFRow row = this.sheet.getRow(i);
      String value = "";
      short cell = (short)cellNum;
      HSSFCell cells = row.getCell(cell);
      if (cells != null) {
        value = cells.getStringCellValue();
      }
      values[i] = value;
    }
    return values;
  }
  
  public String[] readRow(int rowNum)
  {
    int rows = this.sheet.getPhysicalNumberOfRows();
    if (rowNum >= rows) {
      return new String[0];
    }
    HSSFRow row = this.sheet.getRow(rowNum);
    int cellNum = row.getPhysicalNumberOfCells();
    String[] values = new String[cellNum];
    for (short i = 0; i < cellNum; i = (short)(i + 1))
    {
      HSSFCell cell = row.getCell(i);
      String value = "";
      if (cell != null) {
        value = cell.getStringCellValue();
      }
      values[i] = value;
    }
    return values;
  }
  
  public List<String[]> readPage()
    throws FileNotFoundException, IOException
  {
    List<String[]> valueList = new ArrayList();
    
    int rows = this.sheet.getPhysicalNumberOfRows();
    for (int i = 0; i < rows; i++)
    {
      HSSFRow row = this.sheet.getRow(i);
      if (row != null)
      {
        int cellNum = row.getPhysicalNumberOfCells();
        
        String[] cells = new String[cellNum];
        for (short j = 0; j < cellNum; j = (short)(j + 1))
        {
          HSSFCell cell = row.getCell(j);
          String value = " ";
          if (cell != null) {
            try
            {
              value = cell.getStringCellValue();
            }
            catch (Exception localException) {}
          }
          cells[j] = value;
        }
        valueList.add(cells);
      }
    }
    return valueList;
  }
  
  public void writeExcel(List<String[]> list, String excelURI, String sheetName)
    throws IOException
  {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet(sheetName);
    for (short t = 0; t < list.size(); t = (short)(t + 1))
    {
      String[] cells = (String[])list.get(t);
      
      HSSFRow daterow = sheet.createRow(t);
      
      HSSFCell[] datacell = new HSSFCell[cells.length];
      for (short i = 0; i < cells.length; i = (short)(i + 1))
      {
        datacell[i] = daterow.createCell(i);
        
        datacell[i].setEncoding((short)1);
        
        datacell[i].setCellValue(cells[i]);
      }
    }
    FileOutputStream out = new FileOutputStream(excelURI);
    wb.write(out);
    out.close();
  }
  
  public void writeExcel(List<String[]> list)
    throws IOException
  {}
  
  private void init()
  {
    if ((this.excelURI != null) && (!this.excelURI.trim().equals(""))) {
      try
      {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(this.excelURI));
        this.sheet = workbook.getSheet(this.sheetName);
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private void getExcelURI(String excelURI)
  {
    if ((excelURI != null) && (excelURI.trim().length() != 0) && 
      (excelURI.indexOf('.') <= 0)) {
      excelURI = excelURI + ".xls";
    }
    if (isAbsoluteURI(excelURI))
    {
      this.excelURI = excelURI;
    }
    else
    {
      File f = new File(".");
      this.excelURI = (f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 1) + excelURI);
    }
  }
  
  private boolean isAbsoluteURI(String excelURI)
  {
    Pattern pattern = Pattern.compile("\\w:");
    if (pattern.matcher(excelURI.substring(0, 2)).find()) {
      return true;
    }
    return false;
  }
  
  public static List<String[]> help()
  {
    List<String[]> valueList = new ArrayList();
    String excelURI = "F:\\ce\\228.xls";
    String sheetName = "Sheet1";
    HSSFSheet sheet = null;
    File f = new File(".");
    FileWriter writer = null;
    try
    {
      HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelURI));
      sheet = workbook.getSheet(sheetName);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    StringBuffer ss = new StringBuffer();
    
    int rows = sheet.getPhysicalNumberOfRows();
    for (int i = 1; i <= rows; i++)
    {
      HSSFRow row = sheet.getRow(i);
      if (row != null)
      {
        int cellNum = row.getPhysicalNumberOfCells();
        
        String[] val = new String[cellNum];
        for (short j = 0; j < cellNum; j = (short)(j + 1))
        {
          HSSFCell cell = row.getCell(j);
          String value = " ";
          if (cell != null) {
            if (cell.getCellType() == 0) {
              value = cell.getNumericCellValue();
            } else {
              value = cell.getStringCellValue();
            }
          }
          val[j] = value;
        }
        String sql = "insert into nh_f_b_firmidandaccount (BANKID, FIRMID, ACCOUNT, ACCOUNT1, STATUS, ACCOUNTNAME, BANKNAME, BANKPROVINCE, BANKCITY, MOBILE, EMAIL, ISOPEN, CARDTYPE, CARD, ACCOUNTNAME1, AREACODE, BRANCHNO, OPENDATE)values ('01', '" + 
          val[1].replace(".0", "") + 
          "', '" + 
          val[4] + 
          "', '', 0, '" + 
          val[2] + 
          "', '', '', '', '" + val[8] + "', '', 1, '" + val[6] + "', '" + val[7] + "', '', '', '', to_date('2014-02-28','yyyy-MM-dd'));";
        String sql2 = "insert into NH_F_B_FirmUser(firmid, name, maxpertransmoney, maxpertranscount,  status, registerdate, logoutdate,maxPerSglTransMoney,maxAuditMoney,password) values('" + 
        
          val[1].replace(".0", "") + "', '" + val[2] + "', '50000000.00', '5', 0, sysdate, '', '10000000.00', '0', '');";
        

        ss.append(sql);
        ss.append("\n");
        ss.append(sql2);
        ss.append("\n");
      }
    }
    try
    {
      writer = new FileWriter("F:\\ce\\1.txt");
      writer.write(ss.toString());
      writer.flush();
      writer.close();
      System.out.println("写入成功");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return valueList;
  }
  
  public static void main(String[] args)
    throws IOException
  {
    help();
  }
}

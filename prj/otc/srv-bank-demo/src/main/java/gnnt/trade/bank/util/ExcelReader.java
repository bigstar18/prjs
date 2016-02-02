package gnnt.trade.bank.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
public class ExcelReader {
	/**Excel存放路径*/
	private String excelURI = "";
	/**当前要作用的页码*/
	private String sheetName = "";
	/**Excel对象*/
	private HSSFSheet sheet=null;
	/**空构造方法*/
	public ExcelReader(){
	}
	/**带参构造方法*/
	public ExcelReader(String excelURI,String sheetName){
		this.getExcelURI(excelURI);
		if(sheetName==null || sheetName.trim().equals("")){
			sheetName="page1";
		}
		this.sheetName=sheetName;
		this.init();
	}
	/**读取文件中的定点信息*/
	@SuppressWarnings("deprecation")
	public String readPoint(int rowNum,int cellNum){
		int row=this.sheet.getPhysicalNumberOfRows();
		if(rowNum>=row){
			return "";
		}
		HSSFRow rows = this.sheet.getRow(rowNum);
		int cell=rows.getPhysicalNumberOfCells();
		if(cellNum>=cell){
			return "";
		}
		HSSFCell cells = rows.getCell((short)cellNum);
		if(cells==null){
			return "";
		}
		return cells.getStringCellValue();
	}
	/**读取Excel文件的某一列信息*/
	@SuppressWarnings("deprecation")
	public String[] readCell(int cellNum){
		int rowNum=this.sheet.getPhysicalNumberOfRows();
		String[] values=new String[rowNum];
		for(int i=0;i<rowNum;i++){
			HSSFRow row = this.sheet.getRow(i);
			String value="";
			short cell=(short) cellNum;
			HSSFCell cells=row.getCell(cell);
			if(cells!=null){
				value=cells.getStringCellValue();
			}
			values[i]=value;
		}
		return values;
	}
	/**读取Excel文件的某一行信息*/
	@SuppressWarnings("deprecation")
	public String[] readRow(int rowNum){
		int rows = this.sheet.getPhysicalNumberOfRows();
		if(rowNum>=rows){
			return new String[0];
		}
		HSSFRow row = this.sheet.getRow(rowNum);
		int cellNum = row.getPhysicalNumberOfCells();
		String [] values = new String[cellNum];
		for(short i = 0 ; i < cellNum ; i++){
			HSSFCell cell = row.getCell(i);
			String value = "";
			if(cell != null){
				value=cell.getStringCellValue();
			}
			values[i]=value;
		}
		return values;
	}
	/**读取Excel文件的当前页信息*/
	@SuppressWarnings("deprecation")
	public List<String[]> readPage()throws FileNotFoundException,IOException{
		//返回用的值
		List<String[]> valueList=new ArrayList<String[]>();
		//获取文件中的行数
		int rows = this.sheet.getPhysicalNumberOfRows();
		//便利各行
		for(int i=0;i<rows;i++){
			//获取当前行
			HSSFRow row = this.sheet.getRow(i);
			//如果本行不为空
			if(row != null){
				//获取当前行的列数
				int cellNum = row.getPhysicalNumberOfCells();
				//收集当前行的信息
				String [] cells=new String[cellNum];
				//便利当前行
				for(short j = 0;j<cellNum;j++){
					//获取当前行的当前列
					HSSFCell cell = row.getCell(j);
					String value = " ";
					if(cell != null){
						try{
						value=cell.getStringCellValue();
						}catch(Exception e){
							
						}
					}
					cells[j]=value;
				}
				valueList.add(cells);
			}
		}
		return valueList;
	}
	/**向指定文件中写入信息*/
	@SuppressWarnings("deprecation")
	public void writeExcel(List<String[]> list ,String excelURI,String sheetName)throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		for(short t=0;t<list.size();t++){
			//获取第t行
			String[] cells=list.get(t);
			//在文件中建立行
			HSSFRow daterow = sheet.createRow((short)t);
			//定义当前行的各个列
			HSSFCell datacell[] = new HSSFCell[cells.length];
			for(short i=0;i<cells.length;i++){
				datacell[i] = daterow.createCell((short)i);
				//设置编码，为例处理中文
				datacell[i].setEncoding(HSSFCell.ENCODING_UTF_16);
				//为当前行的当前列赋值
				datacell[i].setCellValue(cells[i]);
			}
		}
		//写入输出结果
		FileOutputStream out = new FileOutputStream(excelURI);
		wb.write(out);
		out.close();
	}
	/**向文件中写入信息*/
	public void writeExcel(List<String[]> list) throws IOException{
		
	}
	/**获取Excel的对象*/
	private void init(){
		if(this.excelURI==null || this.excelURI.trim().equals("")){
			
		}else{
			try {
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(this.excelURI));
				this.sheet = workbook.getSheet(this.sheetName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**获取Excel路径*/
	private void getExcelURI(String excelURI){
		if(excelURI==null || excelURI.trim().length()==0){
		}else if(excelURI.indexOf('.')<=0){
			excelURI=excelURI+".xls";
		}
		if(this.isAbsoluteURI(excelURI)){
			this.excelURI=excelURI;
		}else{
			File f = new File(".");
			this.excelURI = f.getAbsolutePath().substring(0,f.getAbsolutePath().length()-1)+excelURI;
			
		}
	}
	/**查看所传入的路径是否为绝对路径*/
	private boolean isAbsoluteURI(String excelURI){
		Pattern pattern=Pattern.compile("\\w:");
		if(pattern.matcher(excelURI.substring(0, 2)).find()){
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static List<String[]> help(){
		List<String[]> valueList=new ArrayList<String[]>();
		String excelURI="src\\com\\bj\\lzx\\reader\\help.xls";
		String sheetName="hello";
		HSSFSheet sheet=null;
		File f = new File(".");
		excelURI=f.getAbsolutePath().substring(0,f.getAbsolutePath().length()-1)+excelURI;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelURI));
			sheet = workbook.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获取文件中的行数
		int rows = sheet.getPhysicalNumberOfRows();
		//便利各行
		for(int i=0;i<rows;i++){
			//获取当前行
			HSSFRow row = sheet.getRow(i);
			//如果本行不为空
			if(row != null){
				//获取当前行的列数
				int cellNum = row.getPhysicalNumberOfCells();
				//收集当前行的信息
				String [] cells=new String[cellNum];
				//便利当前行
				for(short j = 0;j<cellNum;j++){
					//获取当前行的当前列
					HSSFCell cell = row.getCell(j);
					String value = " ";
					if(cell != null){
						value=cell.getStringCellValue();
					}
					cells[j]=value;
				}
				valueList.add(cells);
			}
		}
		return valueList;
	}
	public static void main(String args[])throws IOException{
		List<String[]> list=help();
		ExcelReader er = new ExcelReader();
		er.writeExcel(list, "D:\\myexcel.xls", "help");
		
		
		
//		for(int i=0;i<list.size();i++){
//			for(int j=0;j<list.get(i).length;j++){
//				System.out.print(list.get(i)[j]);
//			}
//			System.out.println();
//		}
	}
}

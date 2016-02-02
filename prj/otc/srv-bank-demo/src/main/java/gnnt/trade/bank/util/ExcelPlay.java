package gnnt.trade.bank.util;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
public class ExcelPlay {
	/**
	 * 导出Excel
	 * @param name 页名称
	 * @param vec 导入信息
	 * @param os 输出流
	 */
	public void getExcel(String name,Vector vec,OutputStream os){
		HSSFWorkbook work = new HSSFWorkbook();
		HSSFSheet sheet=work.createSheet(name);
		getBody(sheet,vec);
		outPut(work,os);
	}
	/**
	 * 将传入信息赋值到表格中
	 * @param sheet 表格
	 * @param vec 传入信息
	 */
	private void getBody(HSSFSheet sheet,@SuppressWarnings("rawtypes") Vector vec){
		if(vec == null || vec.size()<=0){
			return;
		}
		Object obj = vec.get(0);
		if(obj instanceof CorrespondValue){
			getHead(sheet,CorrespondHead());//写入表头
			for(int i=0;i<vec.size();i++){
				CorrespondBody(sheet,(CorrespondValue)vec.get(i),i+1);
			}
		}
		if(obj instanceof CapitalValue){
			getHead(sheet,CapitalHead());//写入表头
			for(int i=0;i<vec.size();i++){
				CapitalBody(sheet,(CapitalValue)vec.get(i),i+1);
			}
		}
	}
	/**
	 * 向某一行赋值
	 * @param sheet 表格
	 * @param value 待赋值
	 * @param rownum 行标
	 */
	private void CapitalBody(HSSFSheet sheet,CapitalValue value,int rownum){
		HSSFRow row=sheet.createRow(rownum);
		putCell(row,Tool.fmtTime(value.createtime),0);
		putCell(row,""+value.actionID,1);
		putCell(row,Tool.delNull(value.funID),2);
		putCell(row,Tool.delNull(value.bankName),3);
		putCell(row,Tool.delNull(value.firmID),4);
		if("C".equals(value.firmType)) {
			putCell(row,"客户",5);
		}else if("M".equals(value.firmType)){
			putCell(row,"会员",5);
		}else if("S".equals(value.firmType)){
			putCell(row,"特别会员",5);
		}
		putCell(row,Tool.delNull(value.accountName),6);
		putCell(row,Tool.delNull(value.contact),7);
		putCell(row,Tool.fmtMoney(value.money),8);
		if(value.type==ProcConstants.inMoneyType){
			putCell(row,"入金",9);
		}else if(value.type==ProcConstants.outMoneyType){
			putCell(row,"出金",9);
		}else if(value.type==ProcConstants.inMoneyBlunt){
			putCell(row,"入金冲正",9);
		}else if(value.type==ProcConstants.outMoneyBlunt){
			putCell(row,"出金冲正",9);
		}
		if(value.status==ProcConstants.statusSuccess){
			putCell(row,"成功",10);
		}else if(value.status==ProcConstants.statusFailure || value.status==ProcConstants.statusBlunt){
			putCell(row,"失败",10);
		}else{
			putCell(row,"待处理",10);
		}
		putCell(row,Tool.delNull(value.note),11);
	}
	/**
	 * 向某一行赋值
	 * @param sheet 表格
	 * @param value 待赋值
	 * @param rownum 行标
	 */
	private void CorrespondBody(HSSFSheet sheet,CorrespondValue value,int rownum){
		HSSFRow row=sheet.createRow(rownum);
		putCell(row,value.firmID,0);
		putCell(row,value.contact,1);
		putCell(row,value.name,2);
		putCell(row,value.account,3);
		putCell(row,value.accountName,4);
		if("C".equalsIgnoreCase(value.firmType)){
			putCell(row,"客户",5);
		}else if("M".equalsIgnoreCase(value.firmType)){
			putCell(row,"会员",5);
		}else if("S".equalsIgnoreCase(value.firmType)){
			putCell(row,"特别会员",5);
		}
		putCell(row,value.getCardType(),6);
		if(0 == value.isOpen){
			putCell(row,"未签约",7);
		}else if(1==value.isOpen){
			putCell(row,"已签约",7);
		}else if(2==value.isOpen){
			putCell(row,"已解约",7);
		}
		if(0 == value.status){
			putCell(row,"可用",8);
		}else if(1 == value.status){
			putCell(row,"不可用",8);
		}
		putCell(row,Tool.fmtMoney(value.frozenFuns),9);
	}
	/**
	 * 获取转账流水导出表头
	 * @return Vector<String>
	 */
	private Vector<String> CapitalHead(){
		Vector<String> result = new Vector<String>();
		result.add("时间");
		result.add("交易所流水号");
		result.add("银行流水号");
		result.add("银行名称");
		result.add("客户或会员编号");
		result.add("帐户类型");
		result.add("银行账户名称");
		result.add("资金账号");
		result.add("转账金额");
		result.add("转账类型");
		result.add("处理状态");
		result.add("备注");
		return result;
	}
	/**
	 * 获取资金帐号导出表头
	 * @return Vector<String>
	 */
	private Vector<String> CorrespondHead(){
		Vector<String> result = new Vector<String>();
		result.add("客户或会员编号");
		result.add("资金账号");
		result.add("银行");
		result.add("银行卡号");
		result.add("银行账户名称");
		result.add("账号类型");
		result.add("签约证件类型");
		result.add("签约状态");
		result.add("是否可用");
		result.add("在途资金");
		return result;
	}
	/**
	 * 向表格中添加表头
	 * @param sheet 表格信息
	 */
	private void getHead(HSSFSheet sheet,Vector<String> head){
		HSSFRow row=sheet.createRow(0);
		if(head != null && head.size()>0){
			for(int i=0;i<head.size();i++){
				putCell(row,head.get(i),i);
			}
		}
	}
	/**
	 * 向某一行的第colnum列赋值
	 * @param row Excel的行
	 * @param value 要付给的值
	 * @param colnum 要赋值当前行的列标
	 */
	@SuppressWarnings("deprecation")
	private void putCell(HSSFRow row,String value,int colnum){
		HSSFCell cell=row.createCell((short)colnum);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
	}
	/**
	 * 向页面输出信息
	 * @param work Excel表格
	 * @param os 输出流
	 */
	private void outPut(HSSFWorkbook work,OutputStream os){
		try {
			
			work.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
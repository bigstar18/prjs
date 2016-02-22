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

public class ExcelPlay
{
  public void getExcel(String name, Vector vec, OutputStream os)
  {
    HSSFWorkbook work = new HSSFWorkbook();
    HSSFSheet sheet = work.createSheet(name);
    super.getBody(sheet, vec);
    outPut(work, os);
  }
  
  void getBody(HSSFSheet sheet, Vector vec)
  {
    if ((vec == null) || (vec.size() <= 0)) {
      return;
    }
    Object obj = vec.get(0);
    if ((obj instanceof CorrespondValue))
    {
      getHead(sheet, CorrespondHead());
      for (int i = 0; i < vec.size(); i++) {
        CorrespondBody(sheet, (CorrespondValue)vec.get(i), i + 1);
      }
    }
    if ((obj instanceof CapitalValue))
    {
      getHead(sheet, CapitalHead());
      for (int i = 0; i < vec.size(); i++) {
        CapitalBody(sheet, (CapitalValue)vec.get(i), i + 1);
      }
    }
  }
  
  private void CapitalBody(HSSFSheet sheet, CapitalValue value, int rownum)
  {
    HSSFRow row = sheet.createRow(rownum);
    putCell(row, Tool.fmtTime(value.createtime), 0);
    putCell(row, value.actionID, 1);
    putCell(row, Tool.delNull(value.funID), 2);
    putCell(row, Tool.delNull(value.bankName), 3);
    putCell(row, Tool.delNull(value.firmID), 4);
    if ("C".equals(value.firmType)) {
      putCell(row, "客户", 5);
    } else if ("M".equals(value.firmType)) {
      putCell(row, "会员", 5);
    } else if ("S".equals(value.firmType)) {
      putCell(row, "特别会员", 5);
    }
    putCell(row, Tool.delNull(value.accountName), 6);
    putCell(row, Tool.delNull(value.contact), 7);
    putCell(row, Tool.fmtMoney(value.money), 8);
    if (value.type == 0) {
      putCell(row, "入金", 9);
    } else if (value.type == 1) {
      putCell(row, "出金", 9);
    } else if (value.type == 4) {
      putCell(row, "入金冲正", 9);
    } else if (value.type == 5) {
      putCell(row, "出金冲正", 9);
    }
    if (value.status == 0) {
      putCell(row, "成功", 10);
    } else if ((value.status == 1) || (value.status == 9)) {
      putCell(row, "失败", 10);
    } else {
      putCell(row, "待处理", 10);
    }
    putCell(row, Tool.delNull(value.note), 11);
  }
  
  private void CorrespondBody(HSSFSheet sheet, CorrespondValue value, int rownum)
  {
    HSSFRow row = sheet.createRow(rownum);
    putCell(row, value.firmID, 0);
    putCell(row, value.contact, 1);
    putCell(row, value.name, 2);
    putCell(row, value.account, 3);
    putCell(row, value.accountName, 4);
    if ("C".equalsIgnoreCase(value.firmType)) {
      putCell(row, "客户", 5);
    } else if ("M".equalsIgnoreCase(value.firmType)) {
      putCell(row, "会员", 5);
    } else if ("S".equalsIgnoreCase(value.firmType)) {
      putCell(row, "特别会员", 5);
    }
    putCell(row, value.getCardType(), 6);
    if (value.isOpen == 0) {
      putCell(row, "未签约", 7);
    } else if (1 == value.isOpen) {
      putCell(row, "已签约", 7);
    } else if (2 == value.isOpen) {
      putCell(row, "已解约", 7);
    }
    if (value.status == 0) {
      putCell(row, "可用", 8);
    } else if (1 == value.status) {
      putCell(row, "不可用", 8);
    }
    putCell(row, Tool.fmtMoney(value.frozenFuns), 9);
  }
  
  private Vector<String> CapitalHead()
  {
    Vector<String> result = new Vector();
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
  
  private Vector<String> CorrespondHead()
  {
    Vector<String> result = new Vector();
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
  
  private void getHead(HSSFSheet sheet, Vector<String> head)
  {
    HSSFRow row = sheet.createRow(0);
    if ((head != null) && (head.size() > 0)) {
      for (int i = 0; i < head.size(); i++) {
        putCell(row, (String)head.get(i), i);
      }
    }
  }
  
  private void putCell(HSSFRow row, String value, int colnum)
  {
    HSSFCell cell = row.createCell((short)colnum);
    cell.setEncoding((short)1);
    cell.setCellValue(value);
  }
  
  private void outPut(HSSFWorkbook work, OutputStream os)
  {
    try
    {
      work.write(os);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}

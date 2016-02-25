package gnnt.trade.bank.data.boc.vo;

import gnnt.trade.bank.data.boc.BOCExDataImpl;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class FileProcessor
{
  private String path;
  private String dqjym = "";
  private String bankCode = "004";
  private static final int[] fieldlen_STL01 = { 4, 8, 8, 20, 19, 30, 60, 1, 1, 3, 1, 14, 10, 10, 20 };
  private static final int[] fieldlen_STL02 = { 4, 8, 8, 19, 30, 60, 3, 1, 14, 10, 10, 20 };
  private static final int[] fieldlen_STL03 = { 4, 8, 8, 20, 30, 60, 3, 1, 1, 10, 10, 20 };
  
  private static String getFileType(String name)
  {
    String fileType = name.split("_")[1];
    return fileType;
  }
  
  private String getContent()
  {
    StringBuffer content = new StringBuffer();
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.path)));
      String data = null;
      while ((data = br.readLine()) != null) {
        content.append("\n" + data);
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return content.toString();
  }
  
  public Vector<DTL01> getDTL01()
    throws NumberFormatException
  {
    Vector vector = new Vector();
    String content = getContent();
    String[] contentArr = content.split("\\|");
    int unitLenth = 17;
    for (int i = 0; i < contentArr.length / unitLenth; i++)
    {
      DTL01 dtl01 = new DTL01();
      dtl01.bankId = contentArr[(i * unitLenth + 0)].trim();
      dtl01.futuresId = contentArr[(i * unitLenth + 1)].trim();
      dtl01.transferDate = contentArr[(i * unitLenth + 2)].trim();
      dtl01.transfeTime = contentArr[(i * unitLenth + 3)].trim();
      dtl01.bkSeq = contentArr[(i * unitLenth + 4)].trim();
      dtl01.bankTransferDate = contentArr[(i * unitLenth + 5)].trim();
      dtl01.ftSeq = contentArr[(i * unitLenth + 6)].trim();
      dtl01.bkAcct = contentArr[(i * unitLenth + 7)].trim();
      dtl01.ftAcct = contentArr[(i * unitLenth + 8)].trim();
      dtl01.custName = contentArr[(i * unitLenth + 9)].trim();
      dtl01.tradSrc = contentArr[(i * unitLenth + 10)].trim();
      dtl01.busCd = contentArr[(i * unitLenth + 11)].trim();
      dtl01.ccy = contentArr[(i * unitLenth + 12)].trim();
      dtl01.cashExCd = contentArr[(i * unitLenth + 13)].trim();
      dtl01.trfAmt = Long.parseLong(contentArr[(i * unitLenth + 14)].trim());
      dtl01.trfFee1 = Long.parseLong(contentArr[(i * unitLenth + 15)].trim());
      dtl01.trfFee2 = Long.parseLong(contentArr[(i * unitLenth + 16)].trim());
      vector.add(dtl01);
    }
    return vector;
  }
  
  public DTL02 getDTL02()
  {
    String content = getContent();
    String[] contentArr = content.split("\\|");
    DTL02 dtl02 = new DTL02();
    dtl02.bankId = contentArr[0].trim();
    dtl02.futuresId = contentArr[1].trim();
    dtl02.sumDate = contentArr[2].trim();
    dtl02.sumTime = contentArr[3].trim();
    dtl02.ccy = contentArr[4].trim();
    dtl02.cashExCd = contentArr[5].trim();
    dtl02.trfAmt = Long.parseLong(contentArr[6].trim());
    dtl02.trfFlag = contentArr[7].trim();
    dtl02.trfFee1 = Long.parseLong(contentArr[8].trim());
    dtl02.trfFee2 = Long.parseLong(contentArr[9].trim());
    
    return dtl02;
  }
  
  public Vector<DTL03> getDTL03()
  {
    Vector vector = new Vector();
    String content = getContent();
    String[] contentArr = content.split("\\|");
    int unitLenth = 13;
    for (int i = 0; i < contentArr.length / unitLenth; i++)
    {
      DTL03 dtl03 = new DTL03();
      dtl03.bankId = contentArr[(i * unitLenth + 0)].trim();
      dtl03.futuresId = contentArr[(i * unitLenth + 1)].trim();
      dtl03.date = contentArr[(i * unitLenth + 2)].trim();
      dtl03.ccy = contentArr[(i * unitLenth + 3)].trim();
      dtl03.cashExCd = contentArr[(i * unitLenth + 4)].trim();
      dtl03.ftAcct = contentArr[(i * unitLenth + 5)].trim();
      dtl03.custName = contentArr[(i * unitLenth + 6)].trim();
      dtl03.reason = Integer.parseInt(contentArr[(i * unitLenth + 7)].trim());
      dtl03.BkBal = Long.parseLong(contentArr[(i * unitLenth + 8)].trim());
      dtl03.FtBal = Long.parseLong(contentArr[(i * unitLenth + 9)].trim());
      dtl03.info1 = contentArr[(i * unitLenth + 10)].trim();
      dtl03.info2 = contentArr[(i * unitLenth + 11)].trim();
      dtl03.info3 = contentArr[(i * unitLenth + 12)].trim();
      vector.add(dtl03);
    }
    return vector;
  }
  
  public DTL04 getDTL04()
  {
    String content = getContent();
    String[] contentArr = content.split("\\|");
    DTL04 dtl04 = new DTL04();
    
    dtl04.bankId = contentArr[0].trim();
    dtl04.futuresId = contentArr[1].trim();
    dtl04.date = contentArr[2].trim();
    dtl04.ccy = contentArr[3].trim();
    dtl04.cashExCd = contentArr[4].trim();
    dtl04.bkManageSumBlance = Long.parseLong(contentArr[5].trim());
    dtl04.bkSumBlanceLocal = Long.parseLong(contentArr[6].trim());
    dtl04.bkSumBlanceOther = Long.parseLong(contentArr[7].trim());
    
    return dtl04;
  }
  
  public String setSTL01(Vector<STL01> vector)
    throws IOException
  {
    String content = "";
    for (int i = 0; i < vector.size(); i++)
    {
      STL01 stl01 = (STL01)vector.get(i);
      
      content = content + Common.fmtStrField(Common.delNull(stl01.bankId), fieldlen_STL01[0], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.futuresId), fieldlen_STL01[1], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.date), fieldlen_STL01[2], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.ftSeq), fieldlen_STL01[3], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.bkAcct), fieldlen_STL01[4], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.ftAcct), fieldlen_STL01[5], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.custName), fieldlen_STL01[6], " ", 1) + "|";
      content = content + Common.fmtStrField(stl01.busType, fieldlen_STL01[7], "0", 0) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.trfFlag), fieldlen_STL01[8], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.ccy), fieldlen_STL01[9], " ", 1) + "|";
      content = content + Common.fmtStrField(stl01.cashExCd, fieldlen_STL01[10], "0", 0) + "|";
      content = content + Common.fmtStrField(stl01.trfAmt, fieldlen_STL01[11], "0", 0) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.info1), fieldlen_STL01[12], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.info2), fieldlen_STL01[13], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl01.info3), fieldlen_STL01[14], " ", 1) + "|";
      
      content = content + "\n";
    }
    return content;
  }
  
  public String setSTL02(Vector<STL02> vector)
    throws IOException
  {
    String content = "";
    for (int i = 0; i < vector.size(); i++)
    {
      STL02 stl02 = (STL02)vector.get(i);
      
      content = content + Common.fmtStrField(Common.delNull(stl02.bankId), fieldlen_STL02[0], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.futuresId), fieldlen_STL02[1], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.date), fieldlen_STL02[2], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.bkAcct), fieldlen_STL02[3], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.ftAcct), fieldlen_STL02[4], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.custName), fieldlen_STL02[5], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.ccy), fieldlen_STL02[6], " ", 1) + "|";
      content = content + Common.fmtStrField(stl02.cashExCd, fieldlen_STL02[7], "0", 0) + "|";
      content = content + Common.fmtStrField(stl02.FtBal, fieldlen_STL02[8], "0", 0) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.info1), fieldlen_STL02[9], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.info2), fieldlen_STL02[10], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl02.info3), fieldlen_STL02[11], " ", 1) + "|";
      
      content = content + "\n";
    }
    String fileName = "F_STL02_" + Common.df7.format(Common.getDate());
    return content;
  }
  
  public String setSTL03(Vector<STL03> vector)
    throws IOException
  {
    String content = "";
    for (int i = 0; i < vector.size(); i++)
    {
      STL03 stl03 = (STL03)vector.get(i);
      
      content = content + Common.fmtStrField(Common.delNull(stl03.bankId), fieldlen_STL03[0], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.futuresId), fieldlen_STL03[1], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.date), fieldlen_STL03[2], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.ftSeq), fieldlen_STL03[3], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.ftAcct), fieldlen_STL03[4], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.custName), fieldlen_STL03[5], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.ccy), fieldlen_STL03[6], " ", 1) + "|";
      content = content + Common.fmtStrField(stl03.cashExCd, fieldlen_STL03[7], "0", 0) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.busType), fieldlen_STL03[8], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.info1), fieldlen_STL03[9], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.info2), fieldlen_STL03[10], " ", 1) + "|";
      content = content + Common.fmtStrField(Common.delNull(stl03.info3), fieldlen_STL03[11], " ", 1) + "|";
      
      content = content + "\n";
    }
    String fileName = "F_STL03_" + Common.df7.format(Common.getDate());
    return content;
  }
  
  public String setS01(List<TransferAccountsTransactionDetailed> tatd, Date tradeDate)
    throws IOException
  {
    BOCExDataImpl.log("=====生成资金交易对账明细文档====begin");
    StringBuffer value = new StringBuffer();
    for (TransferAccountsTransactionDetailed obj : tatd)
    {
      value.append(fmtString(this.bankCode, 4) + "|");
      value.append(fmtString(BOCConstant.MarketCode, 8) + "|");
      value.append(fmtString(this.dqjym, 4) + "|");
      value.append(fmtString(Common.df7.format(Tool.strToDate(obj.getTransDateTime())), 8) + "|");
      value.append(fmtString(fmtTime(obj.getTransTime()), 6) + "|");
      value.append(fmtString(fmtSerial(obj.getBankSerialNumber(), 8, "0"), 20) + "|");
      value.append(fmtString(fmtSerial(obj.getLaunchSerialNumber(), 8, "0"), 20) + "|");
      value.append(fmtString(obj.getBankAccount(), 32) + "|");
      value.append(fmtString(obj.getBondAcc(), 14) + "|");
      value.append(fmtString("", 32) + "|");
      value.append(fmtString("", 1) + "|");
      value.append(fmtString(obj.getTransferDirection(), 1) + "|");
      value.append(fmtString(obj.getMoneyType(), 3) + "|");
      value.append(fmtString(obj.getCashExCode(), 1) + "|");
      value.append(fmtMoney(String.valueOf(Math.round(obj.getMoney() * 100.0D)), 14) + "|");
      value.append(" \n");
    }
    String fileName = "S01" + Common.df7.format(tradeDate);
    BOCExDataImpl.log("=======FileName:" + fileName);
    return value.toString();
  }
  
  public String setS02(List<AccountStatusReconciliation> asr, Date tradeDate)
    throws IOException
  {
    BOCExDataImpl.log("=====生成客户账户状态对账文档====begin");
    StringBuffer value = new StringBuffer();
    for (AccountStatusReconciliation obj : asr)
    {
      value.append(fmtString(this.bankCode, 4) + "|");
      value.append(fmtString(BOCConstant.MarketCode, 8) + "|");
      value.append(fmtString(this.dqjym, 4) + "|");
      value.append(fmtString(Common.df7.format(Tool.strToDate(obj.getTransDateTime())), 8) + "|");
      value.append(fmtString(obj.getBankAccount(), 32) + "|");
      value.append(fmtString(obj.getBondAcc(), 14) + "|");
      value.append(fmtString(obj.getCertificationName(), 32) + "|");
      value.append(fmtString(obj.getMoneyType(), 3) + "|");
      value.append(fmtString(obj.getCashExCode(), 1) + "|");
      value.append(fmtString(obj.getStatus(), 1) + "|");
      value.append(" \n");
    }
    String fileName = "S02" + Common.df7.format(tradeDate);
    BOCExDataImpl.log("=======FileName:" + fileName);
    return value.toString();
  }
  
  public String setS06(List<StorageMoneySettlementList> smsl, Date tradeDate)
    throws IOException
  {
    BOCExDataImpl.log("=====生成存管客户资金交收明细文档====begin");
    StringBuffer value = new StringBuffer();
    for (StorageMoneySettlementList obj : smsl)
    {
      value.append(fmtString(this.bankCode, 4) + "|");
      value.append(fmtString(BOCConstant.MarketCode, 8) + "|");
      value.append(fmtString(this.dqjym, 4) + "|");
      value.append(fmtString(Common.df7.format(Tool.strToDate(obj.getTransDateTime())), 8) + "|");
      value.append(fmtString(new StringBuilder(String.valueOf(BOCConstant.MarketCode)).append(obj.getBondAcc()).toString(), 22) + "|");
      value.append(fmtString(obj.getBondAcc(), 14) + "|");
      value.append(fmtString(obj.getCertificationName(), 32) + "|");
      value.append(fmtString(obj.getTradeDifference(), 1) + "|");
      value.append(fmtString(obj.getMoneyType(), 3) + "|");
      value.append(fmtString(obj.getCashExCode(), 1) + "|");
      value.append(fmtMoney(String.valueOf(Math.abs(Math.round(obj.getMoney() * 100.0D))), 14) + "|");
      value.append(" \n");
    }
    String fileName = "S06" + Common.df7.format(tradeDate);
    BOCExDataImpl.log("=======FileName:" + fileName);
    return value.toString();
  }
  
  public String setS07(List<StorageMoneyLedgerBalanceList> smlbl, Date tradeDate)
    throws IOException
  {
    BOCExDataImpl.log("=====生成存管客户资金台账资金余额明细文档====begin");
    StringBuffer value = new StringBuffer();
    for (StorageMoneyLedgerBalanceList obj : smlbl)
    {
      value.append(fmtString(this.bankCode, 4) + "|");
      value.append(fmtString(BOCConstant.MarketCode, 8) + "|");
      value.append(fmtString(this.dqjym, 4) + "|");
      value.append(fmtString(Common.df7.format(Tool.strToDate(obj.getTransDateTime())), 8) + "|");
      value.append(fmtString(new StringBuilder(String.valueOf(BOCConstant.MarketCode)).append(obj.getBondAcc()).toString(), 22) + "|");
      value.append(fmtString(obj.getBondAcc(), 14) + "|");
      value.append(fmtString(obj.getCertificationName(), 32) + "|");
      value.append(fmtString(obj.getMoneyType(), 3) + "|");
      value.append(fmtString(obj.getCashExCode(), 1) + "|");
      value.append(fmtString(String.valueOf(Math.round(obj.getMoney() * 100.0D)), 14) + "|");
      value.append(" \n");
    }
    String fileName = "S07" + Common.df7.format(tradeDate);
    BOCExDataImpl.log("=======FileName:" + fileName);
    return value.toString();
  }
  
  public String fmtString(String str, int i)
  {
    int t = 0;
    if ((str != null) && (str.getBytes().length < i)) {
      t = i - str.getBytes().length;
    }
    StringBuilder sb = new StringBuilder();
    if (str != null) {
      sb.append(str);
    } else {
      sb.append(" ");
    }
    for (int j = 0; j < t; j++) {
      sb.append(" ");
    }
    return sb.toString();
  }
  
  public String fmtTime(String str)
  {
    StringBuilder sb = new StringBuilder();
    String[] time = str.split(":");
    for (String s : time) {
      sb.append(s);
    }
    return sb.toString();
  }
  
  public String fmtMoney(String str, int i)
  {
    int t = 0;
    if ((str != null) && (str.getBytes().length < i)) {
      t = i - str.getBytes().length;
    }
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < t; j++) {
      sb.append("0");
    }
    sb.append(str);
    return sb.toString();
  }
  
  public String fmtSerial(String serial, int i, String h)
  {
    int t = 0;
    if ((serial != null) && (serial.getBytes().length < i)) {
      t = i - serial.getBytes().length;
    }
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < t; j++) {
      sb.append(h);
    }
    sb.append(serial);
    return sb.toString();
  }
  
  public static void main(String[] args)
    throws IOException
  {
    System.out.println(Math.abs(-1000));
  }
}

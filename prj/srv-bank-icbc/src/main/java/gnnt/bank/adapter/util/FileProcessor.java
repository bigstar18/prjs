package gnnt.bank.adapter.util;

import gnnt.bank.adapter.bankBusiness.dayData.DTL01;
import gnnt.bank.adapter.bankBusiness.dayData.DTL02;
import gnnt.bank.adapter.bankBusiness.dayData.DTL03;
import gnnt.bank.adapter.bankBusiness.dayData.DTL04;
import gnnt.bank.adapter.bankBusiness.dayData.STL01;
import gnnt.bank.adapter.bankBusiness.dayData.STL02;
import gnnt.bank.adapter.bankBusiness.dayData.STL03;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Vector;

public class FileProcessor
{
  private String path;
  private static final int[] fieldlen_STL01 = { 4, 8, 8, 20, 19, 30, 60, 1, 1, 3, 1, 14, 10, 10, 20 };

  private static final int[] fieldlen_STL02 = { 4, 8, 8, 19, 30, 60, 3, 1, 14, 10, 10, 20 };

  private static final int[] fieldlen_STL03 = { 4, 8, 8, 20, 30, 60, 3, 1, 1, 10, 10, 20 };

  public FileProcessor(String path) {
    this.path = path;
  }

  private String getContent()
  {
    StringBuffer content = new StringBuffer();
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.path)));
      String data = null;
      while ((data = br.readLine()) != null)
      {
        content.append("\n" + data);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
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
    for (int i = 0; i < contentArr.length / unitLenth; i++) {
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
    DTL02 dtl02 = new DTL02();
    String content = getContent();
    if (content.length() > 0) {
      String[] contentArr = content.split("\\|");

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
    } else {
      return null;
    }
    return dtl02;
  }

  public Vector<DTL03> getDTL03()
  {
    System.out.println("进入getDTL03方法");
    Vector vector = new Vector();
    String content = getContent();
    String[] contentArr = content.split("\\|");
    System.out.println(contentArr.length);
    int unitLenth = 13;
    for (int i = 0; i < contentArr.length / unitLenth; i++) {
      System.out.println(i);
      if (i != 4)
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
        dtl03.BkBal = Long.parseLong(trimString(contentArr[(i * unitLenth + 8)].trim()));
        dtl03.FtBal = Long.parseLong(trimString(contentArr[(i * unitLenth + 9)].trim()));
        dtl03.info1 = contentArr[(i * unitLenth + 10)].trim();
        dtl03.info2 = contentArr[(i * unitLenth + 11)].trim();
        dtl03.info3 = contentArr[(i * unitLenth + 12)].trim();
        vector.add(dtl03);
      }
    }
    return vector;
  }

  public String trimString(String strTmp)
  {
    String s = "";
    if ((strTmp != null) && (!"".equals(strTmp))) {
      for (int i = 0; i < strTmp.length(); i++) {
        char c = strTmp.charAt(i);
        if (c == '0') {
          if (i == strTmp.length() - 1)
            return strTmp;
        }
        else
        {
          if (c == '-') {
            return strTmp.substring(i, strTmp.length());
          }
          return strTmp;
        }
      }
    }
    return s;
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
    StringBuffer info = new StringBuffer();
    for (int i = 0; i < vector.size(); i++) {
      STL01 stl01 = (STL01)vector.get(i);
      info.append(Common.fmtStrField(Common.delNull(stl01.bankId), fieldlen_STL01[0], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.futuresId), fieldlen_STL01[1], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.date), fieldlen_STL01[2], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.ftSeq), fieldlen_STL01[3], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.bkAcct), fieldlen_STL01[4], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.ftAcct), fieldlen_STL01[5], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.custName), fieldlen_STL01[6], " ", 1) + "|");
      info.append(Common.fmtStrField(stl01.busType, fieldlen_STL01[7], "0", 0) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.trfFlag), fieldlen_STL01[8], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.ccy), fieldlen_STL01[9], " ", 1) + "|");
      info.append(Common.fmtStrField(stl01.cashExCd, fieldlen_STL01[10], "0", 0) + "|");
      info.append(Common.fmtStrField(stl01.trfAmt, fieldlen_STL01[11], "0", 0) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.info1), fieldlen_STL01[12], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.info2), fieldlen_STL01[13], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl01.info3), fieldlen_STL01[14], " ", 1) + "|");

      info.append("\n");
    }

    String fileName = "F_STL01_" + FileUtil.readline("loginDate.txt");
    try
    {
      FileUtil.write(info.toString(), this.path + "/" + fileName);
    } catch (IOException e) {
      throw e;
    }

    return fileName;
  }

  public String setSTL02(Vector<STL02> vector)
    throws IOException
  {
    StringBuffer info = new StringBuffer();
    for (int i = 0; i < vector.size(); i++) {
      STL02 stl02 = (STL02)vector.get(i);
      info.append(Common.fmtStrField(Common.delNull(stl02.bankId), fieldlen_STL02[0], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.futuresId), fieldlen_STL02[1], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.date), fieldlen_STL02[2], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.bkAcct), fieldlen_STL02[3], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.ftAcct), fieldlen_STL02[4], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.custName), fieldlen_STL02[5], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.ccy), fieldlen_STL02[6], " ", 1) + "|");
      info.append(Common.fmtStrField(stl02.cashExCd, fieldlen_STL02[7], "0", 0) + "|");
      info.append(Common.fmtStrField(stl02.FtBal, fieldlen_STL02[8], "0", 0) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.info1), fieldlen_STL02[9], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.info2), fieldlen_STL02[10], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl02.info3), fieldlen_STL02[11], " ", 1) + "|");

      info.append("\n");
    }

    String fileName = "F_STL02_" + FileUtil.readline("loginDate.txt");
    try
    {
      FileUtil.write(info.toString(), this.path + "/" + fileName);
    } catch (IOException e) {
      throw e;
    }

    return fileName;
  }

  public String setSTL03(Vector<STL03> vector)
    throws IOException
  {
    StringBuffer info = new StringBuffer();
    for (int i = 0; i < vector.size(); i++) {
      STL03 stl03 = (STL03)vector.get(i);
      info.append(Common.fmtStrField(Common.delNull(stl03.bankId), fieldlen_STL03[0], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.futuresId), fieldlen_STL03[1], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.date), fieldlen_STL03[2], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.ftSeq), fieldlen_STL03[3], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.ftAcct), fieldlen_STL03[4], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.custName), fieldlen_STL03[5], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.ccy), fieldlen_STL03[6], " ", 1) + "|");
      info.append(Common.fmtStrField(stl03.cashExCd, fieldlen_STL03[7], "0", 0) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.busType), fieldlen_STL03[8], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.info1), fieldlen_STL03[9], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.info2), fieldlen_STL03[10], " ", 1) + "|");
      info.append(Common.fmtStrField(Common.delNull(stl03.info3), fieldlen_STL03[11], " ", 1) + "|");

      info.append("\n");
    }

    String fileName = "F_STL03_" + FileUtil.readline("loginDate.txt");
    try
    {
      FileUtil.write(info.toString(), this.path + "/" + fileName);
    } catch (IOException e) {
      throw e;
    }

    return fileName;
  }

  public static void main(String[] args)
  {
    FileProcessor fp2 = new FileProcessor("");
    String s = "00000000000000";
    System.out.println(fp2.trimString(s));
  }

  private static String getFileType(String name)
  {
    String fileType = name.split("_")[1];
    return fileType;
  }
}
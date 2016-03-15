package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EtellerExcelPub
  implements Serializable
{
  private String excelname;
  private String reportcode;
  private String optdate;
  private String info;
  private String note1;
  private String note2;
  private String tradetype;
  
  public EtellerExcelPub(String excelname, String reportcode, String optdate, String info, String note1, String note2, String tradetype)
  {
    this.excelname = excelname;
    this.reportcode = reportcode;
    this.optdate = optdate;
    this.info = info;
    this.note1 = note1;
    this.note2 = note2;
    this.tradetype = tradetype;
  }
  
  public EtellerExcelPub() {}
  
  public EtellerExcelPub(String excelname, String reportcode, String optdate, String tradetype)
  {
    this.excelname = excelname;
    this.reportcode = reportcode;
    this.optdate = optdate;
    this.tradetype = tradetype;
  }
  
  public String getExcelname()
  {
    return this.excelname;
  }
  
  public void setExcelname(String excelname)
  {
    this.excelname = excelname;
  }
  
  public String getReportcode()
  {
    return this.reportcode;
  }
  
  public void setReportcode(String reportcode)
  {
    this.reportcode = reportcode;
  }
  
  public String getOptdate()
  {
    return this.optdate;
  }
  
  public void setOptdate(String optdate)
  {
    this.optdate = optdate;
  }
  
  public String getInfo()
  {
    return this.info;
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public String getNote1()
  {
    return this.note1;
  }
  
  public void setNote1(String note1)
  {
    this.note1 = note1;
  }
  
  public String getNote2()
  {
    return this.note2;
  }
  
  public void setNote2(String note2)
  {
    this.note2 = note2;
  }
  
  public String getTradetype()
  {
    return this.tradetype;
  }
  
  public void setTradetype(String tradetype)
  {
    this.tradetype = tradetype;
  }
  
  public String toString()
  {
    return 
    
      new ToStringBuilder(this).append("excelname", getExcelname()).toString();
  }
}

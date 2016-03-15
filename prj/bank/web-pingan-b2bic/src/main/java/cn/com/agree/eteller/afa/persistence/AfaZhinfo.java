package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaZhinfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String zhno;
  private String sname;
  private String name;
  private String upzoneno;
  private String brzhid;
  private String brdate;
  private String note1;
  private String note2;
  
  public AfaZhinfo(String zhno, String sname, String name, String upzoneno, String brzhid, String brdate, String note1, String note2)
  {
    this.zhno = zhno;
    this.sname = sname;
    this.name = name;
    this.upzoneno = upzoneno;
    this.brzhid = brzhid;
    this.brdate = brdate;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaZhinfo() {}
  
  public AfaZhinfo(String zhno)
  {
    this.zhno = zhno;
  }
  
  public String getZhno()
  {
    return this.zhno;
  }
  
  public void setZhno(String zhno)
  {
    this.zhno = zhno;
  }
  
  public String getSname()
  {
    return this.sname;
  }
  
  public void setSname(String sname)
  {
    this.sname = sname;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getUpzoneno()
  {
    return this.upzoneno;
  }
  
  public void setUpzoneno(String upzoneno)
  {
    this.upzoneno = upzoneno;
  }
  
  public String getBrzhid()
  {
    return this.brzhid;
  }
  
  public void setBrzhid(String brzhid)
  {
    this.brzhid = brzhid;
  }
  
  public String getBrdate()
  {
    return this.brdate;
  }
  
  public void setBrdate(String brdate)
  {
    this.brdate = brdate;
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
  
  public String toString()
  {
    return 
    





      new ToStringBuilder(this).append("zhno", getZhno()).append("sname", getSname()).append("name", getName()).append("upzoneno", getUpzoneno()).append("brzhid", getBrzhid()).append("brdate", getBrdate()).toString();
  }
}

package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaZoneinfo
  implements Serializable
{
  private String zoneno;
  private String zonesname;
  private String zonename;
  private String note1;
  private String note2;
  
  public AfaZoneinfo(String zoneno, String zonesname, String zonename, String note1, String note2)
  {
    this.zoneno = zoneno;
    this.zonesname = zonesname;
    this.zonename = zonename;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaZoneinfo() {}
  
  public AfaZoneinfo(String zoneno)
  {
    this.zoneno = zoneno;
  }
  
  public String getZoneno()
  {
    return this.zoneno;
  }
  
  public void setZoneno(String zoneno)
  {
    this.zoneno = zoneno;
  }
  
  public String getZonesname()
  {
    return this.zonesname;
  }
  
  public void setZonesname(String zonesname)
  {
    this.zonesname = zonesname;
  }
  
  public String getZonename()
  {
    return this.zonename;
  }
  
  public void setZonename(String zonename)
  {
    this.zonename = zonename;
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
    

      new ToStringBuilder(this).append("zoneno", getZoneno()).append("zonesname", getZonesname()).append("zonename", getZonename()).toString();
  }
}

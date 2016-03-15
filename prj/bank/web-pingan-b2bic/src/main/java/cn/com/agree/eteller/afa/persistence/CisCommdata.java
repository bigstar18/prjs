package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CisCommdata
  implements Serializable
{
  private String datacode;
  private String dataname;
  private String datavalue;
  private String updatedatavalue;
  private String efficienttype;
  private String latelyopertype;
  private String efficientdate;
  private String cisupdatedate;
  private String updatetime;
  private String postscript;
  private String leavedate;
  private String status;
  private String remark1;
  private String remark2;
  
  public CisCommdata(String datacode, String dataname, String datavalue, String updatedatavalue, String efficienttype, String latelyopertype, String efficientdate, String cisupdatedate, String updatetime, String postscript, String leavedate, String status, String remark1, String remark2)
  {
    this.datacode = datacode;
    this.dataname = dataname;
    this.datavalue = datavalue;
    this.updatedatavalue = updatedatavalue;
    this.efficienttype = efficienttype;
    this.latelyopertype = latelyopertype;
    this.efficientdate = efficientdate;
    this.cisupdatedate = cisupdatedate;
    this.updatetime = updatetime;
    this.postscript = postscript;
    this.leavedate = leavedate;
    this.status = status;
    this.remark1 = remark1;
    this.remark2 = remark2;
  }
  
  public CisCommdata() {}
  
  public CisCommdata(String datacode, String dataname, String datavalue, String cisupdatedate, String updatetime)
  {
    this.datacode = datacode;
    this.dataname = dataname;
    this.datavalue = datavalue;
    this.cisupdatedate = cisupdatedate;
    this.updatetime = updatetime;
  }
  
  public String getDatacode()
  {
    return this.datacode;
  }
  
  public void setDatacode(String datacode)
  {
    this.datacode = datacode;
  }
  
  public String getDataname()
  {
    return this.dataname;
  }
  
  public void setDataname(String dataname)
  {
    this.dataname = dataname;
  }
  
  public String getDatavalue()
  {
    return this.datavalue;
  }
  
  public void setDatavalue(String datavalue)
  {
    this.datavalue = datavalue;
  }
  
  public String getUpdatedatavalue()
  {
    return this.updatedatavalue;
  }
  
  public void setUpdatedatavalue(String updatedatavalue)
  {
    this.updatedatavalue = updatedatavalue;
  }
  
  public String getEfficienttype()
  {
    return this.efficienttype;
  }
  
  public void setEfficienttype(String efficienttype)
  {
    this.efficienttype = efficienttype;
  }
  
  public String getLatelyopertype()
  {
    return this.latelyopertype;
  }
  
  public void setLatelyopertype(String latelyopertype)
  {
    this.latelyopertype = latelyopertype;
  }
  
  public String getEfficientdate()
  {
    return this.efficientdate;
  }
  
  public void setEfficientdate(String efficientdate)
  {
    this.efficientdate = efficientdate;
  }
  
  public String getCisupdatedate()
  {
    return this.cisupdatedate;
  }
  
  public void setCisupdatedate(String cisupdatedate)
  {
    this.cisupdatedate = cisupdatedate;
  }
  
  public String getUpdatetime()
  {
    return this.updatetime;
  }
  
  public void setUpdatetime(String updatetime)
  {
    this.updatetime = updatetime;
  }
  
  public String getPostscript()
  {
    return this.postscript;
  }
  
  public void setPostscript(String postscript)
  {
    this.postscript = postscript;
  }
  
  public String getLeavedate()
  {
    return this.leavedate;
  }
  
  public void setLeavedate(String leavedate)
  {
    this.leavedate = leavedate;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getRemark1()
  {
    return this.remark1;
  }
  
  public void setRemark1(String remark1)
  {
    this.remark1 = remark1;
  }
  
  public String getRemark2()
  {
    return this.remark2;
  }
  
  public void setRemark2(String remark2)
  {
    this.remark2 = remark2;
  }
  
  public String toString()
  {
    return 
    
      new ToStringBuilder(this).append("datacode", getDatacode()).toString();
  }
}

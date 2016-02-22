package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.sql.Timestamp;

public class FirmBankReport
  extends Clone
{
  private String id;
  private String firmId;
  private String contact;
  private String name;
  private String bankId;
  private String bankname;
  private String firmtype;
  private Timestamp signtime;
  private Timestamp cancletime;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getContact()
  {
    return this.contact;
  }
  
  public void setContact(String contact)
  {
    this.contact = contact;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String bankId)
  {
    this.bankId = bankId;
  }
  
  public String getBankname()
  {
    return this.bankname;
  }
  
  public void setBankname(String bankname)
  {
    this.bankname = bankname;
  }
  
  @ClassDiscription(name="账户类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="S", value="特别会员"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="M", value="综合会员"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="交易客户"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="B", value="经纪会员")})
  public String getFirmtype()
  {
    return this.firmtype;
  }
  
  public void setFirmtype(String firmtype)
  {
    this.firmtype = firmtype;
  }
  
  public Timestamp getSigntime()
  {
    return this.signtime;
  }
  
  public void setSigntime(Timestamp signtime)
  {
    this.signtime = signtime;
  }
  
  public Timestamp getCancletime()
  {
    return this.cancletime;
  }
  
  public void setCancletime(Timestamp cancletime)
  {
    this.cancletime = cancletime;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setPrimary(String primary) {}
}

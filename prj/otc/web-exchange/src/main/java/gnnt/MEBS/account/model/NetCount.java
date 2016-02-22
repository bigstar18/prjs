package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class NetCount
  extends Clone
{
  private String monicustomno;
  private Date createtime;
  
  public NetCount() {}
  
  public NetCount(String monicustomno, Date createtime)
  {
    this.monicustomno = monicustomno;
    this.createtime = createtime;
  }
  
  public String getMonicustomno()
  {
    return this.monicustomno;
  }
  
  public void setMonicustomno(String monicustomno)
  {
    this.monicustomno = monicustomno;
  }
  
  public Date getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(Date createtime)
  {
    this.createtime = createtime;
  }
  
  public String getId()
  {
    return this.monicustomno;
  }
  
  public void setPrimary(String primary)
  {
    this.monicustomno = primary;
  }
}

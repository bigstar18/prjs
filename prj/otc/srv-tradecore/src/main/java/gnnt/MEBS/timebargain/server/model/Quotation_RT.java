package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.sql.Timestamp;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Quotation_RT
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654069813L;
  public String commodityID;
  public String m_FirmID;
  public double curPrice_B;
  public double curPrice_S;
  public double clearPrice_B;
  public double clearPrice_S;
  public double y_ClearPrice_B;
  public double y_ClearPrice_S;
  public Timestamp updateTime;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getM_FirmID()
  {
    return this.m_FirmID;
  }
  
  public void setM_FirmID(String firmID)
  {
    this.m_FirmID = firmID;
  }
  
  public double getCurPrice_B()
  {
    return this.curPrice_B;
  }
  
  public void setCurPrice_B(double curPrice_B)
  {
    this.curPrice_B = curPrice_B;
  }
  
  public double getCurPrice_S()
  {
    return this.curPrice_S;
  }
  
  public void setCurPrice_S(double curPrice_S)
  {
    this.curPrice_S = curPrice_S;
  }
  
  public double getClearPrice_B()
  {
    return this.clearPrice_B;
  }
  
  public void setClearPrice_B(double clearPrice_B)
  {
    this.clearPrice_B = clearPrice_B;
  }
  
  public double getClearPrice_S()
  {
    return this.clearPrice_S;
  }
  
  public void setClearPrice_S(double clearPrice_S)
  {
    this.clearPrice_S = clearPrice_S;
  }
  
  public double getY_ClearPrice_B()
  {
    return this.y_ClearPrice_B;
  }
  
  public void setY_ClearPrice_B(double clearPrice_B)
  {
    this.y_ClearPrice_B = clearPrice_B;
  }
  
  public double getY_ClearPrice_S()
  {
    return this.y_ClearPrice_S;
  }
  
  public void setY_ClearPrice_S(double clearPrice_S)
  {
    this.y_ClearPrice_S = clearPrice_S;
  }
  
  public Timestamp getUpdateTime()
  {
    return this.updateTime;
  }
  
  public void setUpdateTime(Timestamp updateTime)
  {
    this.updateTime = updateTime;
  }
}

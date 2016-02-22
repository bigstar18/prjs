package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Fee_RT
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049212L;
  private String commodityID;
  private String firmID;
  private short feeAlgr;
  public static final short FEEALGR_PERCENT = 1;
  public static final short FEEALGR_FIXVALUE = 2;
  private double feeRate;
  private short feeMode;
  public static final short FEEMODE_ONEOPEN = 1;
  public static final short FEEMODE_ONECLOSE = 2;
  public static final short FEEMODE_TWO = 3;
  public static final short FEEMODE_OVERNIGHT = 4;
  
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
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public short getFeeAlgr()
  {
    return this.feeAlgr;
  }
  
  public void setFeeAlgr(short feeAlgr)
  {
    this.feeAlgr = feeAlgr;
  }
  
  public double getFeeRate()
  {
    return this.feeRate;
  }
  
  public void setFeeRate(double feeRate)
  {
    this.feeRate = feeRate;
  }
  
  public short getFeeMode()
  {
    return this.feeMode;
  }
  
  public void setFeeMode(short feeMode)
  {
    this.feeMode = feeMode;
  }
}

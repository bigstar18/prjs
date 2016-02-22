package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trade
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049815L;
  private Long orderNo;
  private Long holdingNO;
  private Double contractFactor;
  private Date tradeDate;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
  }
  
  public Long getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(Long orderNo)
  {
    this.orderNo = orderNo;
  }
  
  public Double getContractFactor()
  {
    return this.contractFactor;
  }
  
  public void setContractFactor(Double contractFactor)
  {
    this.contractFactor = contractFactor;
  }
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
  }
  
  public Long getHoldingNO()
  {
    return this.holdingNO;
  }
  
  public void setHoldingNO(Long holdingNO)
  {
    this.holdingNO = holdingNO;
  }
}

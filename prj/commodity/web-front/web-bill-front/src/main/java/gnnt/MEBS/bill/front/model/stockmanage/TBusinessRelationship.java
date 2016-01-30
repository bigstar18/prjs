package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class TBusinessRelationship
  extends StandardModel
{
  private static final long serialVersionUID = -4100861460186632902L;
  private String stockId;
  private String buyer;
  private String seller;
  private String received;
  private Date receivedDate;
  private Date sellTime;
  
  public Date getSellTime()
  {
    return this.sellTime;
  }
  
  public void setSellTime(Date sellTime)
  {
    this.sellTime = sellTime;
  }
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String stockId)
  {
    this.stockId = stockId;
  }
  
  public String getBuyer()
  {
    return this.buyer;
  }
  
  public void setBuyer(String buyer)
  {
    this.buyer = buyer;
  }
  
  public String getSeller()
  {
    return this.seller;
  }
  
  public void setSeller(String seller)
  {
    this.seller = seller;
  }
  
  public String getReceived()
  {
    return this.received;
  }
  
  public void setReceived(String received)
  {
    this.received = received;
  }
  
  public Date getReceivedDate()
  {
    return this.receivedDate;
  }
  
  public void setReceivedDate(Date receivedDate)
  {
    this.receivedDate = receivedDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}

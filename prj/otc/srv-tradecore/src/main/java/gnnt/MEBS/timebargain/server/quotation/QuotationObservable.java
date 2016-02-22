package gnnt.MEBS.timebargain.server.quotation;

import java.util.Observable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationObservable
  extends Observable
{
  private Log log = LogFactory.getLog(getClass());
  private String commodityID;
  private Double curPrice = Double.valueOf(0.0D);
  
  public QuotationObservable(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public void changeCurPrice(Double curPrice)
  {
    if (this.curPrice.doubleValue() != curPrice.doubleValue())
    {
      this.curPrice = curPrice;
      setChanged();
      notifyObservers();
    }
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public Double getCurPrice()
  {
    return this.curPrice;
  }
  
  public void setCurPrice(Double curPrice)
  {
    this.curPrice = curPrice;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}

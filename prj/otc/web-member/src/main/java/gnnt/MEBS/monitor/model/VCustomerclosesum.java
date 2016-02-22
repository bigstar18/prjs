package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VCustomerclosesum
  extends Clone
{
  private String commodityname;
  private Long buyqtyint;
  private String buyqty;
  private Double buyopenpricedbl;
  private String buyopenprice;
  private Double buyholdpricedbl;
  private String buyholdprice;
  private Double sellholdpricedbl;
  private String sellholdprice;
  private Double buyclosepricedbl;
  private String buycloseprice;
  private Double buyclosepldbl;
  private String buyclosepl;
  private Long sellqtyint;
  private String sellqty;
  private Double sellopenpricedbl;
  private String sellopenprice;
  private Double sellclosepricedbl;
  private String sellcloseprice;
  private Double sellclosepldbl;
  private String sellclosepl;
  private Double tradefeedbl;
  private String tradefee;
  private Long netqtyint;
  private String netqty;
  private String allpl;
  private String netpl;
  private Double allpldbl;
  private Double netpldbl;
  
  public String getId()
  {
    return getCommodityname();
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(String buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public String getBuyopenprice()
  {
    return this.buyopenprice;
  }
  
  public void setBuyopenprice(String buyopenprice)
  {
    this.buyopenprice = buyopenprice;
  }
  
  public String getBuycloseprice()
  {
    return this.buycloseprice;
  }
  
  public void setBuycloseprice(String buycloseprice)
  {
    this.buycloseprice = buycloseprice;
  }
  
  public String getBuyclosepl()
  {
    return this.buyclosepl;
  }
  
  public void setBuyclosepl(String buyclosepl)
  {
    this.buyclosepl = buyclosepl;
  }
  
  public String getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(String sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public String getSellopenprice()
  {
    return this.sellopenprice;
  }
  
  public void setSellopenprice(String sellopenprice)
  {
    this.sellopenprice = sellopenprice;
  }
  
  public String getSellcloseprice()
  {
    return this.sellcloseprice;
  }
  
  public void setSellcloseprice(String sellcloseprice)
  {
    this.sellcloseprice = sellcloseprice;
  }
  
  public String getSellclosepl()
  {
    return this.sellclosepl;
  }
  
  public void setSellclosepl(String sellclosepl)
  {
    this.sellclosepl = sellclosepl;
  }
  
  public String getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(String tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public String getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(String netqty)
  {
    this.netqty = netqty;
  }
  
  public String getAllpl()
  {
    return this.allpl;
  }
  
  public void setAllpl(String allpl)
  {
    this.allpl = allpl;
  }
  
  public String getNetpl()
  {
    return this.netpl;
  }
  
  public void setNetpl(String netpl)
  {
    this.netpl = netpl;
  }
  
  public void setPrimary(String primary) {}
  
  public void setBuyholdprice(String buyholdprice)
  {
    this.buyholdprice = buyholdprice;
  }
  
  public String getBuyholdprice()
  {
    return this.buyholdprice;
  }
  
  public void setSellholdprice(String sellholdprice)
  {
    this.sellholdprice = sellholdprice;
  }
  
  public String getSellholdprice()
  {
    return this.sellholdprice;
  }
  
  public void setAllpldbl(Double allpldbl)
  {
    this.allpldbl = allpldbl;
    setAllpl(new DecimalFormat(",##0.00").format(allpldbl));
  }
  
  public Double getAllpldbl()
  {
    return this.allpldbl;
  }
  
  public void setNetpldbl(Double netpldbl)
  {
    this.netpldbl = netpldbl;
    setNetpl(new DecimalFormat(",##0").format(netpldbl));
  }
  
  public Double getNetpldbl()
  {
    return this.netpldbl;
  }
  
  public void setNetqtyint(Long netqtyint)
  {
    this.netqtyint = netqtyint;
    setNetqty(new DecimalFormat(",##0").format(netqtyint));
  }
  
  public Long getNetqtyint()
  {
    return this.netqtyint;
  }
  
  public void setTradefeedbl(Double tradefeedbl)
  {
    this.tradefeedbl = tradefeedbl;
    setTradefee(new DecimalFormat(",##0.00").format(tradefeedbl));
  }
  
  public Double getTradefeedbl()
  {
    return this.tradefeedbl;
  }
  
  public void setBuyqtyint(Long buyqtyint)
  {
    this.buyqtyint = buyqtyint;
    setBuyqty(new DecimalFormat(",##0").format(buyqtyint));
  }
  
  public Long getBuyqtyint()
  {
    return this.buyqtyint;
  }
  
  public void setBuyopenpricedbl(Double buyopenpricedbl)
  {
    this.buyopenpricedbl = buyopenpricedbl;
    setBuyopenprice(new DecimalFormat(",##0.00").format(buyopenpricedbl));
  }
  
  public Double getBuyopenpricedbl()
  {
    return this.buyopenpricedbl;
  }
  
  public void setBuyholdpricedbl(Double buyholdpricedbl)
  {
    this.buyholdpricedbl = buyholdpricedbl;
    setBuyholdprice(new DecimalFormat(",##0.00").format(buyholdpricedbl));
  }
  
  public Double getBuyholdpricedbl()
  {
    return this.buyholdpricedbl;
  }
  
  public void setSellholdpricedbl(Double sellholdpricedbl)
  {
    this.sellholdpricedbl = sellholdpricedbl;
    setSellholdprice(new DecimalFormat(",##0.00").format(sellholdpricedbl));
  }
  
  public Double getSellholdpricedbl()
  {
    return this.sellholdpricedbl;
  }
  
  public void setBuyclosepricedbl(Double buyclosepricedbl)
  {
    this.buyclosepricedbl = buyclosepricedbl;
    setBuycloseprice(new DecimalFormat(",##0.00").format(buyclosepricedbl));
  }
  
  public Double getBuyclosepricedbl()
  {
    return this.buyclosepricedbl;
  }
  
  public void setBuyclosepldbl(Double buyclosepldbl)
  {
    this.buyclosepldbl = buyclosepldbl;
    setBuyclosepl(new DecimalFormat(",##0.00").format(buyclosepldbl));
  }
  
  public Double getBuyclosepldbl()
  {
    return this.buyclosepldbl;
  }
  
  public void setSellqtyint(Long sellqtyint)
  {
    this.sellqtyint = sellqtyint;
    setSellqty(new DecimalFormat(",##0").format(sellqtyint));
  }
  
  public Long getSellqtyint()
  {
    return this.sellqtyint;
  }
  
  public void setSellopenpricedbl(Double sellopenpricedbl)
  {
    this.sellopenpricedbl = sellopenpricedbl;
    setSellopenprice(new DecimalFormat(",##0.00").format(sellopenpricedbl));
  }
  
  public Double getSellopenpricedbl()
  {
    return this.sellopenpricedbl;
  }
  
  public void setSellclosepricedbl(Double sellclosepricedbl)
  {
    this.sellclosepricedbl = sellclosepricedbl;
    setSellcloseprice(new DecimalFormat(",##0.00").format(sellclosepricedbl));
  }
  
  public Double getSellclosepricedbl()
  {
    return this.sellclosepricedbl;
  }
  
  public void setSellclosepldbl(Double sellclosepldbl)
  {
    this.sellclosepldbl = sellclosepldbl;
    setSellclosepl(new DecimalFormat(",##0.00").format(sellclosepldbl));
  }
  
  public Double getSellclosepldbl()
  {
    return this.sellclosepldbl;
  }
}

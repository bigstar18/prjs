package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;

public class Market
  extends Clone
{
  public String marketCode;
  public String marketName;
  public String shortName;
  public int runMode;
  public int deliveryMode;
  public int marginFBFlag;
  public int floatingLossComputeType;
  public int closeAlgr;
  public int tradePriceAlgr;
  public int tradeFlowType;
  public int floatingProfitSubTax;
  public int tradeTimeType;
  public int quotationTwoSide;
  
  @ClassDiscription(name="市场代码", key=true, keyWord=true)
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.marketCode = marketCode;
  }
  
  @ClassDiscription(name="市场名称")
  public String getMarketName()
  {
    return this.marketName;
  }
  
  public void setMarketName(String marketName)
  {
    this.marketName = marketName;
  }
  
  @ClassDiscription(name="市场名称简称", key=true)
  public String getShortName()
  {
    return this.shortName;
  }
  
  public void setShortName(String shortName)
  {
    this.shortName = shortName;
  }
  
  @ClassDiscription(name="运行模式", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="手动"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="自动")})
  public Integer getRunMode()
  {
    return Integer.valueOf(this.runMode);
  }
  
  public void setRunMode(int runMode)
  {
    this.runMode = runMode;
  }
  
  @ClassDiscription(name="交收模式", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="自动"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="手动")})
  public Integer getDeliveryMode()
  {
    return Integer.valueOf(this.deliveryMode);
  }
  
  public void setDeliveryMode(int deliveryMode)
  {
    this.deliveryMode = deliveryMode;
  }
  
  @ClassDiscription(name="保证金调整方式", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="结算时调整"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="开市时调整")})
  public Integer getMarginFBFlag()
  {
    return Integer.valueOf(this.marginFBFlag);
  }
  
  public void setMarginFBFlag(int marginFBFlag)
  {
    this.marginFBFlag = marginFBFlag;
  }
  
  @ClassDiscription(name="浮亏计算方式", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="商品分买卖"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="商品不分买卖"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="不分商品"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="盘中算盈亏"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="盘中不算盈亏")})
  public Integer getFloatingLossComputeType()
  {
    return Integer.valueOf(this.floatingLossComputeType);
  }
  
  public void setFloatingLossComputeType(int floatingLossComputeType)
  {
    this.floatingLossComputeType = floatingLossComputeType;
  }
  
  @ClassDiscription(name="平仓算法", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="先开先平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="后开先平"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="持仓均价平仓")})
  public Integer getCloseAlgr()
  {
    return Integer.valueOf(this.closeAlgr);
  }
  
  public void setCloseAlgr(int closeAlgr)
  {
    this.closeAlgr = closeAlgr;
  }
  
  @ClassDiscription(name="成交价算法", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="先入价"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="中间价")})
  public Integer getTradePriceAlgr()
  {
    return Integer.valueOf(this.tradePriceAlgr);
  }
  
  public void setTradePriceAlgr(int tradePriceAlgr)
  {
    this.tradePriceAlgr = tradePriceAlgr;
  }
  
  @ClassDiscription(name="写成交流水类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="写汇总流水"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="写明细流水")})
  public Integer getTradeFlowType()
  {
    return Integer.valueOf(this.tradeFlowType);
  }
  
  public void setTradeFlowType(int tradeFlowType)
  {
    this.tradeFlowType = tradeFlowType;
  }
  
  @ClassDiscription(name="浮动盈亏是否扣税", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="不扣税"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="扣税")})
  public Integer getFloatingProfitSubTax()
  {
    return Integer.valueOf(this.floatingProfitSubTax);
  }
  
  public void setFloatingProfitSubTax(int floatingProfitSubTax)
  {
    this.floatingProfitSubTax = floatingProfitSubTax;
  }
  
  @ClassDiscription(name="交易时间类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="同一天交易"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="跨天交易")})
  public Integer getTradeTimeType()
  {
    return Integer.valueOf(this.tradeTimeType);
  }
  
  public void setTradeTimeType(int tradeTimeType)
  {
    this.tradeTimeType = tradeTimeType;
  }
  
  @ClassDiscription(name="行情单双边", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="单边"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="双边")})
  public Integer getQuotationTwoSide()
  {
    return Integer.valueOf(this.quotationTwoSide);
  }
  
  public void setQuotationTwoSide(int quotationTwoSide)
  {
    this.quotationTwoSide = quotationTwoSide;
  }
  
  public String getId()
  {
    return this.marketCode;
  }
  
  public void setPrimary(String primary)
  {
    this.marketCode = primary;
  }
}

package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;
import java.util.Date;

public class Commodity
  extends Clone
{
  private String id;
  private String name;
  private Integer status;
  private String tradeMode;
  private BigDecimal contractFactor;
  private BigDecimal minHQMove;
  private BigDecimal minPriceMove;
  private BigDecimal stepMove;
  private Integer spreadAlgr;
  private BigDecimal spreadUpLmt;
  private BigDecimal spreadDownLmt;
  private Integer delayFeeAlgr;
  private Date marketDate;
  private BigDecimal lastPrice;
  private Integer displayNum;
  private String pauseType;
  private BigDecimal quoteRate;
  private String contractUnit;
  
  @ClassDiscription(name="合约重量单位:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="g", value="克"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="kg", value="千克"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="t", value="吨")})
  public String getContractUnit()
  {
    return this.contractUnit;
  }
  
  public void setContractUnit(String contractUnit)
  {
    this.contractUnit = contractUnit;
  }
  
  @ClassDiscription(name="换算单位:")
  public BigDecimal getQuoteRate()
  {
    return this.quoteRate;
  }
  
  public void setQuoteRate(BigDecimal quoteRate)
  {
    this.quoteRate = quoteRate;
  }
  
  public String getPauseType()
  {
    return this.pauseType;
  }
  
  public void setPauseType(String pauseType)
  {
    this.pauseType = pauseType;
  }
  
  public Integer getDisplayNum()
  {
    return this.displayNum;
  }
  
  public void setDisplayNum(Integer displayNum)
  {
    this.displayNum = displayNum;
  }
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getId()
  {
    return this.id;
  }
  
  @ClassDiscription(name="商品名称:", keyWord=true)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="商品状态:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="编辑中"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="上市"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="退市")})
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  @ClassDiscription(name="交易状态:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="正常交易"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="P", value="停止交易")})
  public String getTradeMode()
  {
    return this.tradeMode;
  }
  
  public void setTradeMode(String tradeMode)
  {
    this.tradeMode = tradeMode;
    if (tradeMode.equals("P")) {
      setPauseType("M");
    }
    if (tradeMode.equals("N")) {
      setPauseType("");
    }
  }
  
  @ClassDiscription(name="合约单位:")
  public BigDecimal getContractFactor()
  {
    return formatDecimals(this.contractFactor, NumberDigits.CONTRACTFACTOR - 2);
  }
  
  public void setContractFactor(BigDecimal contractFactor)
  {
    this.contractFactor = contractFactor;
  }
  
  @ClassDiscription(name="最小行情单位:")
  public BigDecimal getMinHQMove()
  {
    return formatDecimals(this.minHQMove, NumberDigits.MINHQMOVE - 2);
  }
  
  public void setMinHQMove(BigDecimal minHQMove)
  {
    this.minHQMove = minHQMove;
  }
  
  @ClassDiscription(name="最小变动单位:")
  public BigDecimal getMinPriceMove()
  {
    return formatDecimals(this.minPriceMove, NumberDigits.MINPRICEMOVE - 2);
  }
  
  public void setMinPriceMove(BigDecimal minPriceMove)
  {
    this.minPriceMove = minPriceMove;
  }
  
  @ClassDiscription(name="步进值:")
  public BigDecimal getStepMove()
  {
    return formatDecimals(this.stepMove, NumberDigits.STEPMOVE - 2);
  }
  
  public void setStepMove(BigDecimal stepMove)
  {
    this.stepMove = stepMove;
  }
  
  @ClassDiscription(name="涨跌幅算法:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="按百分比"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="按绝对值"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="不限制")})
  public Integer getSpreadAlgr()
  {
    return this.spreadAlgr;
  }
  
  public void setSpreadAlgr(Integer spreadAlgr)
  {
    this.spreadAlgr = spreadAlgr;
  }
  
  @ClassDiscription(name="涨幅上限:")
  public BigDecimal getSpreadUpLmt()
  {
    return formatDecimals(this.spreadUpLmt, NumberDigits.SPREADUPLMT);
  }
  
  public void setSpreadUpLmt(BigDecimal spreadUpLmt)
  {
    this.spreadUpLmt = spreadUpLmt;
  }
  
  @ClassDiscription(name="跌幅下限:")
  public BigDecimal getSpreadDownLmt()
  {
    return formatDecimals(this.spreadDownLmt, NumberDigits.SPREADDOWNLMT);
  }
  
  public void setSpreadDownLmt(BigDecimal spreadDownLmt)
  {
    this.spreadDownLmt = spreadDownLmt;
  }
  
  @ClassDiscription(name="上市日期:")
  public Date getMarketDate()
  {
    Date newMarketDate = this.marketDate;
    if (newMarketDate != null) {
      newMarketDate = transformData(this.marketDate);
    }
    return newMarketDate;
  }
  
  public void setMarketDate(Date marketDate)
  {
    this.marketDate = marketDate;
  }
  
  @ClassDiscription(name="基准价:")
  public BigDecimal getLastPrice()
  {
    return formatDecimals(this.lastPrice, NumberDigits.LASTPRICE);
  }
  
  public void setLastPrice(BigDecimal lastPrice)
  {
    this.lastPrice = lastPrice;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setPrimary(String primary)
  {
    this.id = primary;
  }
  
  public Integer getDelayFeeAlgr()
  {
    return this.delayFeeAlgr;
  }
  
  public void setDelayFeeAlgr(Integer delayFeeAlgr)
  {
    this.delayFeeAlgr = delayFeeAlgr;
  }
  
  public Commodity(String id, String name, Integer status, String tradeMode, BigDecimal contractFactor, BigDecimal minHQMove, BigDecimal minPriceMove, BigDecimal stepMove, Integer spreadAlgr, BigDecimal spreadUpLmt, BigDecimal spreadDownLmt, Integer delayFeeAlgr, Date marketDate, BigDecimal lastPrice, Integer displayNum, String pauseType, BigDecimal quoteRate, String contractUnit)
  {
    this.id = id;
    this.name = name;
    this.status = status;
    this.tradeMode = tradeMode;
    this.contractFactor = contractFactor;
    this.minHQMove = minHQMove;
    this.minPriceMove = minPriceMove;
    this.stepMove = stepMove;
    this.spreadAlgr = spreadAlgr;
    this.spreadUpLmt = spreadUpLmt;
    this.spreadDownLmt = spreadDownLmt;
    this.delayFeeAlgr = delayFeeAlgr;
    this.marketDate = marketDate;
    this.lastPrice = lastPrice;
    this.displayNum = displayNum;
    this.pauseType = pauseType;
    this.quoteRate = quoteRate;
    this.contractUnit = contractUnit;
  }
  
  public Commodity() {}
}

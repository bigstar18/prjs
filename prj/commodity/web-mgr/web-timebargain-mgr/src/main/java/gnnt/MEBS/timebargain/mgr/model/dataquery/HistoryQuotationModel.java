package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.sql.Timestamp;
import java.util.Date;

public class HistoryQuotationModel extends StandardModel
{
  private static final long serialVersionUID = -7298857862165308352L;

  @ClassDiscription(name="结算日期", description="")
  private Date clearDate;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name=" 昨结算价", description="")
  private Double yesterBalancePrice;

  @ClassDiscription(name="昨收盘价", description="")
  private Double closePrice;

  @ClassDiscription(name="今开市场", description="")
  private Double openPrice;

  @ClassDiscription(name="最高价", description="")
  private Double highPrice;

  @ClassDiscription(name="最低价", description="")
  private Double lowPrice;

  @ClassDiscription(name="最新价", description="")
  private Double curPrice;

  @ClassDiscription(name="最新价所对应的成交量", description="")
  private Long curAmount;

  @ClassDiscription(name="开仓", description="")
  private Long openAmount;

  @ClassDiscription(name="买开仓", description="")
  private Long buyOpenAmount;

  @ClassDiscription(name="卖开仓", description="")
  private Long sellOpenAmount;

  @ClassDiscription(name="平仓", description="")
  private Long closeAmount;

  @ClassDiscription(name="买平仓", description="")
  private Long buyCloseAmount;

  @ClassDiscription(name="卖平仓", description="")
  private Long sellCloseAmount;

  @ClassDiscription(name="行情订货量", description="")
  private Long reserveCount;

  @ClassDiscription(name="仓差", description="")
  private Long reserveChange;

  @ClassDiscription(name="结算价", description="")
  private Double price;

  @ClassDiscription(name="总成交额", description="")
  private Double totalMoney;

  @ClassDiscription(name="总成交量", description="")
  private Long totalAmount;

  @ClassDiscription(name="涨跌", description="")
  private Double spread;

  @ClassDiscription(name="买入价1", description="")
  private Double buyPrice1;

  @ClassDiscription(name="卖出价1", description="交易保证金系数：每批固定值或百分比")
  private Double sellPrice1;

  @ClassDiscription(name="申买量1", description="")
  private Long buyAmount1;

  @ClassDiscription(name="申卖量1", description="交收日期")
  private Long sellAmount1;

  @ClassDiscription(name="买入价2", description="")
  private Double buyPrice2;

  @ClassDiscription(name="卖出价2", description="交收前一月首款标准")
  private Double sellPrice2;

  @ClassDiscription(name="申买量2", description="")
  private Long buyAmount2;

  @ClassDiscription(name="申卖量2", description="交收月第一日首款")
  private Long sellAmount2;

  @ClassDiscription(name="买入价3 ", description="")
  private Double buyPrice3;

  @ClassDiscription(name="卖出价3", description="交收月第十个日首款标准")
  private Double sellPrice3;

  @ClassDiscription(name="申买量3", description="")
  private Long buyAmount3;

  @ClassDiscription(name="申卖量3", description="")
  private Long sellAmount3;

  @ClassDiscription(name="买入价4", description="")
  private Double buyPrice4;

  @ClassDiscription(name="卖出价4", description="")
  private Double sellPrice4;

  @ClassDiscription(name="申买量4", description="")
  private Long buyAmount4;

  @ClassDiscription(name="申卖量4", description="")
  private Long sellAmount4;

  @ClassDiscription(name="买入价5", description="")
  private Double buyPrice5;

  @ClassDiscription(name="卖出价5", description="")
  private Double sellPrice5;

  @ClassDiscription(name="申买量5", description="")
  private Long buyAmount5;

  @ClassDiscription(name="申卖量5", description="")
  private Long sellAmount5;

  @ClassDiscription(name="买入价6", description="")
  private Double buyPrice6;

  @ClassDiscription(name="卖出价6", description="")
  private Double sellPrice6;

  @ClassDiscription(name="申买量6", description="")
  private Long buyAmount6;

  @ClassDiscription(name="申卖量6", description="")
  private Long sellAmount6;

  @ClassDiscription(name="买入价7", description="")
  private Double buyPrice7;

  @ClassDiscription(name="卖出价7", description="")
  private Double sellPrice7;

  @ClassDiscription(name="申买量7", description="")
  private Long buyAmount7;

  @ClassDiscription(name="申卖量7", description="")
  private Long sellAmount7;

  @ClassDiscription(name="买入价8", description="")
  private Double buyPrice8;

  @ClassDiscription(name="卖出价8", description="")
  private Double sellPrice8;

  @ClassDiscription(name="申买量8", description="")
  private Long buyAmount8;

  @ClassDiscription(name="申卖量8", description="")
  private Long sellAmount8;

  @ClassDiscription(name="买入价9", description="")
  private Double buyPrice9;

  @ClassDiscription(name="卖出价9", description="")
  private Double sellPrice9;

  @ClassDiscription(name="申买量9", description="")
  private Long buyAmount9;

  @ClassDiscription(name="申卖量9", description="")
  private Long sellAmount9;

  @ClassDiscription(name="买入价10", description="")
  private Double buyPrice10;

  @ClassDiscription(name="卖出价10", description="")
  private Double sellPrice10;

  @ClassDiscription(name="申买量10", description="")
  private Long buyAmount10;

  @ClassDiscription(name="申卖量10", description="")
  private Long sellAmount;

  @ClassDiscription(name="外盘", description="")
  private Long outAmount;

  @ClassDiscription(name="内盘", description="")
  private Long inAmount;

  @ClassDiscription(name="交易提示", description="")
  private Long tradeCue;

  @ClassDiscription(name="计数字段", description="")
  private Long no;

  @ClassDiscription(name="创建时间", description="")
  private Timestamp createTime;

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public double getYesterBalancePrice()
  {
    return this.yesterBalancePrice.doubleValue();
  }

  public void setYesterBalancePrice(double yesterBalancePrice)
  {
    this.yesterBalancePrice = Double.valueOf(yesterBalancePrice);
  }

  public Double getClosePrice()
  {
    return this.closePrice;
  }

  public void setClosePrice(Double closePrice)
  {
    this.closePrice = closePrice;
  }

  public Double getOpenPrice()
  {
    return this.openPrice;
  }

  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }

  public Double getHighPrice()
  {
    return this.highPrice;
  }

  public void setHighPrice(Double highPrice)
  {
    this.highPrice = highPrice;
  }

  public Double getLowPrice()
  {
    return this.lowPrice;
  }

  public void setLowPrice(Double lowPrice)
  {
    this.lowPrice = lowPrice;
  }

  public Double getCurPrice()
  {
    return this.curPrice;
  }

  public void setCurPrice(Double curPrice)
  {
    this.curPrice = curPrice;
  }

  public Long getCurAmount()
  {
    return this.curAmount;
  }

  public void setCurAmount(Long curAmount)
  {
    this.curAmount = curAmount;
  }

  public Long getOpenAmount()
  {
    return this.openAmount;
  }

  public void setOpenAmount(Long openAmount)
  {
    this.openAmount = openAmount;
  }

  public Long getBuyOpenAmount()
  {
    return this.buyOpenAmount;
  }

  public void setBuyOpenAmount(Long buyOpenAmount)
  {
    this.buyOpenAmount = buyOpenAmount;
  }

  public Long getSellOpenAmount()
  {
    return this.sellOpenAmount;
  }

  public void setSellOpenAmount(Long sellOpenAmount)
  {
    this.sellOpenAmount = sellOpenAmount;
  }

  public Long getCloseAmount()
  {
    return this.closeAmount;
  }

  public void setCloseAmount(Long closeAmount)
  {
    this.closeAmount = closeAmount;
  }

  public Long getBuyCloseAmount()
  {
    return this.buyCloseAmount;
  }

  public void setBuyCloseAmount(Long buyCloseAmount)
  {
    this.buyCloseAmount = buyCloseAmount;
  }

  public Long getSellCloseAmount()
  {
    return this.sellCloseAmount;
  }

  public void setSellCloseAmount(Long sellCloseAmount)
  {
    this.sellCloseAmount = sellCloseAmount;
  }

  public Long getReserveCount()
  {
    return this.reserveCount;
  }

  public void setReserveCount(Long reserveCount)
  {
    this.reserveCount = reserveCount;
  }

  public Long getReserveChange()
  {
    return this.reserveChange;
  }

  public void setReserveChange(Long reserveChange)
  {
    this.reserveChange = reserveChange;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }

  public Double getTotalMoney()
  {
    return this.totalMoney;
  }

  public void setTotalMoney(Double totalMoney)
  {
    this.totalMoney = totalMoney;
  }

  public Long getTotalAmount()
  {
    return this.totalAmount;
  }

  public void setTotalAmount(Long totalAmount)
  {
    this.totalAmount = totalAmount;
  }

  public Double getSpread()
  {
    return this.spread;
  }

  public void setSpread(Double spread)
  {
    this.spread = spread;
  }

  public Double getBuyPrice1()
  {
    return this.buyPrice1;
  }

  public void setBuyPrice1(Double buyPrice1)
  {
    this.buyPrice1 = buyPrice1;
  }

  public Double getSellPrice1()
  {
    return this.sellPrice1;
  }

  public void setSellPrice1(Double sellPrice1)
  {
    this.sellPrice1 = sellPrice1;
  }

  public Long getBuyAmount1()
  {
    return this.buyAmount1;
  }

  public void setBuyAmount1(Long buyAmount1)
  {
    this.buyAmount1 = buyAmount1;
  }

  public Long getSellAmount1()
  {
    return this.sellAmount1;
  }

  public void setSellAmount1(Long sellAmount1)
  {
    this.sellAmount1 = sellAmount1;
  }

  public Double getBuyPrice2()
  {
    return this.buyPrice2;
  }

  public void setBuyPrice2(Double buyPrice2)
  {
    this.buyPrice2 = buyPrice2;
  }

  public Double getSellPrice2()
  {
    return this.sellPrice2;
  }

  public void setSellPrice2(Double sellPrice2)
  {
    this.sellPrice2 = sellPrice2;
  }

  public Long getBuyAmount2()
  {
    return this.buyAmount2;
  }

  public void setBuyAmount2(Long buyAmount2)
  {
    this.buyAmount2 = buyAmount2;
  }

  public Long getSellAmount2()
  {
    return this.sellAmount2;
  }

  public void setSellAmount2(Long sellAmount2)
  {
    this.sellAmount2 = sellAmount2;
  }

  public Double getBuyPrice3()
  {
    return this.buyPrice3;
  }

  public void setBuyPrice3(Double buyPrice3)
  {
    this.buyPrice3 = buyPrice3;
  }

  public Double getSellPrice3()
  {
    return this.sellPrice3;
  }

  public void setSellPrice3(Double sellPrice3)
  {
    this.sellPrice3 = sellPrice3;
  }

  public Long getBuyAmount3()
  {
    return this.buyAmount3;
  }

  public void setBuyAmount3(Long buyAmount3)
  {
    this.buyAmount3 = buyAmount3;
  }

  public Long getSellAmount3()
  {
    return this.sellAmount3;
  }

  public void setSellAmount3(Long sellAmount3)
  {
    this.sellAmount3 = sellAmount3;
  }

  public Double getBuyPrice4()
  {
    return this.buyPrice4;
  }

  public void setBuyPrice4(Double buyPrice4)
  {
    this.buyPrice4 = buyPrice4;
  }

  public Double getSellPrice4()
  {
    return this.sellPrice4;
  }

  public void setSellPrice4(Double sellPrice4)
  {
    this.sellPrice4 = sellPrice4;
  }

  public Long getBuyAmount4()
  {
    return this.buyAmount4;
  }

  public void setBuyAmount4(Long buyAmount4)
  {
    this.buyAmount4 = buyAmount4;
  }

  public Long getSellAmount4()
  {
    return this.sellAmount4;
  }

  public void setSellAmount4(Long sellAmount4)
  {
    this.sellAmount4 = sellAmount4;
  }

  public Double getBuyPrice5()
  {
    return this.buyPrice5;
  }

  public void setBuyPrice5(Double buyPrice5)
  {
    this.buyPrice5 = buyPrice5;
  }

  public Double getSellPrice5()
  {
    return this.sellPrice5;
  }

  public void setSellPrice5(Double sellPrice5)
  {
    this.sellPrice5 = sellPrice5;
  }

  public Long getBuyAmount5()
  {
    return this.buyAmount5;
  }

  public void setBuyAmount5(Long buyAmount5)
  {
    this.buyAmount5 = buyAmount5;
  }

  public Long getSellAmount5()
  {
    return this.sellAmount5;
  }

  public void setSellAmount5(Long sellAmount5)
  {
    this.sellAmount5 = sellAmount5;
  }

  public Double getBuyPrice6()
  {
    return this.buyPrice6;
  }

  public void setBuyPrice6(Double buyPrice6)
  {
    this.buyPrice6 = buyPrice6;
  }

  public Double getSellPrice6()
  {
    return this.sellPrice6;
  }

  public void setSellPrice6(Double sellPrice6)
  {
    this.sellPrice6 = sellPrice6;
  }

  public Long getBuyAmount6()
  {
    return this.buyAmount6;
  }

  public void setBuyAmount6(Long buyAmount6)
  {
    this.buyAmount6 = buyAmount6;
  }

  public Long getSellAmount6()
  {
    return this.sellAmount6;
  }

  public void setSellAmount6(Long sellAmount6)
  {
    this.sellAmount6 = sellAmount6;
  }

  public Double getBuyPrice7()
  {
    return this.buyPrice7;
  }

  public void setBuyPrice7(Double buyPrice7)
  {
    this.buyPrice7 = buyPrice7;
  }

  public Double getSellPrice7()
  {
    return this.sellPrice7;
  }

  public void setSellPrice7(Double sellPrice7)
  {
    this.sellPrice7 = sellPrice7;
  }

  public Long getBuyAmount7()
  {
    return this.buyAmount7;
  }

  public void setBuyAmount7(Long buyAmount7)
  {
    this.buyAmount7 = buyAmount7;
  }

  public Long getSellAmount7()
  {
    return this.sellAmount7;
  }

  public void setSellAmount7(Long sellAmount7)
  {
    this.sellAmount7 = sellAmount7;
  }

  public Double getBuyPrice8()
  {
    return this.buyPrice8;
  }

  public void setBuyPrice8(Double buyPrice8)
  {
    this.buyPrice8 = buyPrice8;
  }

  public Double getSellPrice8()
  {
    return this.sellPrice8;
  }

  public void setSellPrice8(Double sellPrice8)
  {
    this.sellPrice8 = sellPrice8;
  }

  public Long getBuyAmount8()
  {
    return this.buyAmount8;
  }

  public void setBuyAmount8(Long buyAmount8)
  {
    this.buyAmount8 = buyAmount8;
  }

  public Long getSellAmount8()
  {
    return this.sellAmount8;
  }

  public void setSellAmount8(Long sellAmount8)
  {
    this.sellAmount8 = sellAmount8;
  }

  public Double getBuyPrice9()
  {
    return this.buyPrice9;
  }

  public void setBuyPrice9(Double buyPrice9)
  {
    this.buyPrice9 = buyPrice9;
  }

  public Double getSellPrice9()
  {
    return this.sellPrice9;
  }

  public void setSellPrice9(Double sellPrice9)
  {
    this.sellPrice9 = sellPrice9;
  }

  public Long getBuyAmount9()
  {
    return this.buyAmount9;
  }

  public void setBuyAmount9(Long buyAmount9)
  {
    this.buyAmount9 = buyAmount9;
  }

  public Long getSellAmount9()
  {
    return this.sellAmount9;
  }

  public void setSellAmount9(Long sellAmount9)
  {
    this.sellAmount9 = sellAmount9;
  }

  public Double getBuyPrice10()
  {
    return this.buyPrice10;
  }

  public void setBuyPrice10(Double buyPrice10)
  {
    this.buyPrice10 = buyPrice10;
  }

  public Double getSellPrice10()
  {
    return this.sellPrice10;
  }

  public void setSellPrice10(Double sellPrice10)
  {
    this.sellPrice10 = sellPrice10;
  }

  public Long getBuyAmount10()
  {
    return this.buyAmount10;
  }

  public void setBuyAmount10(Long buyAmount10)
  {
    this.buyAmount10 = buyAmount10;
  }

  public Long getSellAmount()
  {
    return this.sellAmount;
  }

  public void setSellAmount(Long sellAmount)
  {
    this.sellAmount = sellAmount;
  }

  public Long getOutAmount()
  {
    return this.outAmount;
  }

  public void setOutAmount(Long outAmount)
  {
    this.outAmount = outAmount;
  }

  public Long getInAmount()
  {
    return this.inAmount;
  }

  public void setInAmount(Long inAmount)
  {
    this.inAmount = inAmount;
  }

  public Long getTradeCue()
  {
    return this.tradeCue;
  }

  public void setTradeCue(Long tradeCue)
  {
    this.tradeCue = tradeCue;
  }

  public Long getNo()
  {
    return this.no;
  }

  public void setNo(Long no)
  {
    this.no = no;
  }

  public Timestamp getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Timestamp createTime)
  {
    this.createTime = createTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
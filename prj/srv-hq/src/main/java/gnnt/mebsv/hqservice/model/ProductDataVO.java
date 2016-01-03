package gnnt.mebsv.hqservice.model;

import java.util.Date;
import java.util.Vector;

public class ProductDataVO
{
  public long lUpdateTime;
  public Date time;
  public String marketID;
  public String code;
  public String name;
  public float yesterBalancePrice;
  public float closePrice;
  public float openPrice;
  public float highPrice;
  public float lowPrice;
  public float curPrice;
  public float upRate;
  public float shakeRate;
  public int curAmount;
  public int openAmount;
  public int closeAmount;
  public int reserveCount;
  public int reserveChange;
  public float balancePrice;
  public double totalMoney;
  public long totalAmount;
  public float[] buyPrice = new float[5];
  public float[] sellPrice = new float[5];
  public int[] buyAmount = new int[5];
  public int[] sellAmount = new int[5];
  public int outAmount;
  public int inAmount;
  public int tradeCue;
  public int no;
  public long averAmount5;
  public boolean bUpdated = true;
  public float amountRate;
  public float consignRate;
  public float upRate5min;
  public Vector billData = new Vector();
  public String expStr = "";

  public Object clone()
  {
    ProductDataVO localProductDataVO = new ProductDataVO();
    localProductDataVO.amountRate = this.amountRate;
    localProductDataVO.averAmount5 = this.averAmount5;
    localProductDataVO.balancePrice = this.balancePrice;
    localProductDataVO.billData = this.billData;
    for (int i = 0; i < 5; i++)
    {
      localProductDataVO.buyAmount[i] = this.buyAmount[i];
      localProductDataVO.buyPrice[i] = this.buyPrice[i];
      localProductDataVO.sellAmount[i] = this.sellAmount[i];
      localProductDataVO.sellPrice[i] = this.sellPrice[i];
    }
    localProductDataVO.closeAmount = this.closeAmount;
    localProductDataVO.closePrice = this.closePrice;
    localProductDataVO.code = this.code;
    localProductDataVO.marketID = this.marketID;
    localProductDataVO.consignRate = this.consignRate;
    localProductDataVO.curAmount = this.curAmount;
    localProductDataVO.curPrice = this.curPrice;
    localProductDataVO.highPrice = this.highPrice;
    localProductDataVO.inAmount = this.inAmount;
    localProductDataVO.lowPrice = this.lowPrice;
    localProductDataVO.name = this.name;
    localProductDataVO.no = this.no;
    localProductDataVO.openAmount = this.openAmount;
    localProductDataVO.openPrice = this.openPrice;
    localProductDataVO.outAmount = this.outAmount;
    localProductDataVO.reserveChange = this.reserveChange;
    localProductDataVO.reserveCount = this.reserveCount;
    localProductDataVO.lUpdateTime = this.lUpdateTime;
    localProductDataVO.time = this.time;
    localProductDataVO.totalAmount = this.totalAmount;
    localProductDataVO.totalMoney = this.totalMoney;
    localProductDataVO.tradeCue = this.tradeCue;
    localProductDataVO.upRate = this.upRate;
    localProductDataVO.shakeRate = this.shakeRate;
    localProductDataVO.upRate5min = this.upRate5min;
    localProductDataVO.yesterBalancePrice = this.yesterBalancePrice;
    return localProductDataVO;
  }

  public boolean equals(ProductDataVO paramProductDataVO)
  {
    if (paramProductDataVO.balancePrice != this.balancePrice)
      return false;
    for (int i = 0; i < 5; i++)
    {
      if (paramProductDataVO.buyAmount[i] != this.buyAmount[i])
        return false;
      if (paramProductDataVO.buyPrice[i] != this.buyPrice[i])
        return false;
      if (paramProductDataVO.sellAmount[i] != this.sellAmount[i])
        return false;
      if (paramProductDataVO.sellPrice[i] != this.sellPrice[i])
        return false;
    }
    if (paramProductDataVO.closeAmount != this.closeAmount)
      return false;
    if (paramProductDataVO.closePrice != this.closePrice)
      return false;
    if (!paramProductDataVO.code.equals(this.code))
      return false;
    if (paramProductDataVO.curAmount != this.curAmount)
      return false;
    if (paramProductDataVO.curPrice != this.curPrice)
      return false;
    if (paramProductDataVO.highPrice != this.highPrice)
      return false;
    if (paramProductDataVO.lowPrice != this.lowPrice)
      return false;
    if (paramProductDataVO.openAmount != this.openAmount)
      return false;
    if (paramProductDataVO.openPrice != this.openPrice)
      return false;
    if (paramProductDataVO.reserveCount != this.reserveCount)
      return false;
    if (!paramProductDataVO.time.equals(this.time))
      return false;
    if (paramProductDataVO.totalAmount != this.totalAmount)
      return false;
    if (paramProductDataVO.totalMoney != this.totalMoney)
      return false;
    return paramProductDataVO.yesterBalancePrice == this.yesterBalancePrice;
  }

  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("MarketID:" + this.marketID + str);
    localStringBuffer.append("Time:" + this.time + str);
    localStringBuffer.append("Code:" + this.code + str);
    localStringBuffer.append("Name:" + this.name + str);
    localStringBuffer.append("YesterPrice:" + this.yesterBalancePrice + str);
    localStringBuffer.append("ClosePrice:" + this.closePrice + str);
    localStringBuffer.append("OpenPrice:" + this.openPrice + str);
    localStringBuffer.append("HighPrice:" + this.highPrice + str);
    localStringBuffer.append("LowPrice:" + this.lowPrice + str);
    localStringBuffer.append("CurPrice:" + this.curPrice + str);
    localStringBuffer.append("CurAmount:" + this.curAmount + str);
    localStringBuffer.append("OpenAmount:" + this.openAmount + str);
    localStringBuffer.append("CloseAmount:" + this.closeAmount + str);
    localStringBuffer.append("ReserveCount:" + this.reserveCount + str);
    localStringBuffer.append("AverageValue:" + this.balancePrice + str);
    localStringBuffer.append("TotalMoney:" + this.totalMoney + str);
    localStringBuffer.append("TotalAmount:" + this.totalAmount + str);
    for (int i = 0; i < 5; i++)
    {
      localStringBuffer.append("BuyPrice" + (i + 1) + ":" + this.buyPrice[i] + str);
      localStringBuffer.append("SellPrice" + (i + 1) + ":" + this.sellPrice[i] + str);
      localStringBuffer.append("BuyAmount" + (i + 1) + ":" + this.buyAmount[i] + str);
      localStringBuffer.append("SellAmount" + (i + 1) + ":" + this.sellAmount[i] + str);
    }
    localStringBuffer.append("OutAmount:" + this.outAmount + str);
    localStringBuffer.append("InAmount:" + this.inAmount + str);
    localStringBuffer.append("TradeCue:" + this.tradeCue + str);
    localStringBuffer.append("NO:" + this.no + str);
    localStringBuffer.append("AverAmount5:" + this.averAmount5 + str);
    localStringBuffer.append("AmountRate:" + this.amountRate + str);
    localStringBuffer.append("consignRate:" + this.consignRate + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
package gnnt.MEBS.vendue.manage.report;

import gnnt.MEBS.vendue.manage.util.ManaUtil;
import java.math.BigDecimal;

public class VariLiftedGoods
{
  public String vari;
  public BigDecimal tradeAmount;
  public BigDecimal liftedAmount;
  public BigDecimal tradeMoney;
  public BigDecimal liftedMoney;
  public BigDecimal lkMoney;
  public BigDecimal lkAmount;
  public BigDecimal liftedRatio;
  public BigDecimal lkRatio;
  
  public void setVari(String paramString)
  {
    this.vari = paramString;
  }
  
  public String getVari()
  {
    return this.vari;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setLiftedAmount(BigDecimal paramBigDecimal)
  {
    this.liftedAmount = paramBigDecimal;
  }
  
  public BigDecimal getLiftedAmount()
  {
    return this.liftedAmount;
  }
  
  public void setLKMoney(BigDecimal paramBigDecimal)
  {
    this.lkMoney = paramBigDecimal;
  }
  
  public BigDecimal getLKMoney()
  {
    return this.lkMoney;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public void setLiftedMoney(BigDecimal paramBigDecimal)
  {
    this.liftedMoney = paramBigDecimal;
  }
  
  public BigDecimal getLiftedMoney()
  {
    return this.liftedMoney;
  }
  
  public void setLKAmount(BigDecimal paramBigDecimal)
  {
    this.lkAmount = paramBigDecimal;
  }
  
  public BigDecimal getLKAmount()
  {
    return this.lkAmount;
  }
  
  public BigDecimal getLiftedRatio()
  {
    BigDecimal localBigDecimal1 = ManaUtil.disBD(this.liftedAmount);
    BigDecimal localBigDecimal2 = ManaUtil.disBD(this.tradeAmount);
    if (localBigDecimal2.compareTo(new BigDecimal(0)) <= 0) {
      localBigDecimal2 = new BigDecimal(1);
    }
    this.liftedRatio = localBigDecimal1.divide(localBigDecimal2, 4, 4).multiply(new BigDecimal(100));
    return this.liftedRatio;
  }
  
  public BigDecimal getLKRatio()
  {
    BigDecimal localBigDecimal1 = ManaUtil.disBD(this.lkMoney);
    BigDecimal localBigDecimal2 = ManaUtil.disBD(this.tradeMoney);
    if (localBigDecimal2.compareTo(new BigDecimal(0)) <= 0) {
      localBigDecimal2 = new BigDecimal(1);
    }
    this.lkRatio = localBigDecimal1.divide(localBigDecimal2, 4, 4).multiply(new BigDecimal(100));
    return this.lkRatio;
  }
}

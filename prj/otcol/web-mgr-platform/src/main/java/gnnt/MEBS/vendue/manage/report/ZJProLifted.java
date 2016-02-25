package gnnt.MEBS.vendue.manage.report;

import gnnt.MEBS.vendue.manage.util.ManaUtil;
import java.math.BigDecimal;

public class ZJProLifted
{
  public BigDecimal zjTradeAmount;
  public BigDecimal zjTradeMoney;
  public BigDecimal zjLiftedAmount;
  public BigDecimal zjLiftedMoney;
  public BigDecimal zjLKAmount;
  public BigDecimal zjLKMoney;
  public BigDecimal zjLiftedRatio;
  public BigDecimal zjLKRatio;
  
  public void setZJTradeAmount(BigDecimal paramBigDecimal)
  {
    this.zjTradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getZJTradeAmount()
  {
    return this.zjTradeAmount;
  }
  
  public void setZJLiftedAmount(BigDecimal paramBigDecimal)
  {
    this.zjLiftedAmount = paramBigDecimal;
  }
  
  public BigDecimal getZJLiftedAmount()
  {
    return this.zjLiftedAmount;
  }
  
  public void setZJLKMoney(BigDecimal paramBigDecimal)
  {
    this.zjLKMoney = paramBigDecimal;
  }
  
  public BigDecimal getZJLKMoney()
  {
    return this.zjLKMoney;
  }
  
  public void setZJTradeMoney(BigDecimal paramBigDecimal)
  {
    this.zjTradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getZJTradeMoney()
  {
    return this.zjTradeMoney;
  }
  
  public void setZJLiftedMoney(BigDecimal paramBigDecimal)
  {
    this.zjLiftedMoney = paramBigDecimal;
  }
  
  public BigDecimal getZJLiftedMoney()
  {
    return this.zjLiftedMoney;
  }
  
  public void setZJLKAmount(BigDecimal paramBigDecimal)
  {
    this.zjLKAmount = paramBigDecimal;
  }
  
  public BigDecimal getZJLKAmount()
  {
    return this.zjLKAmount;
  }
  
  public BigDecimal getZJLiftedRatio()
  {
    BigDecimal localBigDecimal1 = ManaUtil.disBD(this.zjLiftedAmount);
    BigDecimal localBigDecimal2 = ManaUtil.disBD(this.zjTradeAmount);
    if (localBigDecimal2.compareTo(new BigDecimal(0)) <= 0) {
      localBigDecimal2 = new BigDecimal(1);
    }
    this.zjLiftedRatio = localBigDecimal1.divide(localBigDecimal2, 4, 4).multiply(new BigDecimal(100));
    return this.zjLiftedRatio;
  }
  
  public BigDecimal getZJLKRatio()
  {
    BigDecimal localBigDecimal1 = ManaUtil.disBD(this.zjLKMoney);
    BigDecimal localBigDecimal2 = ManaUtil.disBD(this.zjTradeMoney);
    if (localBigDecimal2.compareTo(new BigDecimal(0)) <= 0) {
      localBigDecimal2 = new BigDecimal(1);
    }
    this.zjLKRatio = localBigDecimal1.divide(localBigDecimal2, 4, 4).multiply(new BigDecimal(100));
    return this.zjLKRatio;
  }
}

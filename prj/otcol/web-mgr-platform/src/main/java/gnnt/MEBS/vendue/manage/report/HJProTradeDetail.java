package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HJProTradeDetail
{
  private String proName;
  private String grade;
  private String vari;
  private BigDecimal tradeAmount;
  private BigDecimal tradeMoney;
  private BigDecimal price;
  private BigDecimal buyCost;
  private BigDecimal loanMoney;
  private BigDecimal actualAmount;
  private BigDecimal actualMoney;
  private BigDecimal haveLoanMoney;
  private BigDecimal concelContract;
  private BigDecimal exhauAmount;
  private BigDecimal fellBackAmount;
  private BigDecimal buyerFellBackMoney;
  private BigDecimal returnLoanMoney;
  private BigDecimal notLoanMoney;
  private BigDecimal notLoanInter;
  private BigDecimal delayLoanInter;
  private int delayDays;
  private String payDay;
  private String confirmListDay;
  private BigDecimal poundage;
  private BigDecimal income;
  private BigDecimal expenditure;
  private BigDecimal diffProfitLoss;
  private BigDecimal approFinance;
  private BigDecimal payed;
  private BigDecimal notpay;
  private BigDecimal payedTotal;
  private ArrayList tradeDetail;
  private BigDecimal kuDianMoney;
  private String marketName;
  
  public String getMarketName()
  {
    return this.marketName;
  }
  
  public void setMarketName(String paramString)
  {
    this.marketName = paramString;
  }
  
  public BigDecimal getKuDianMoney()
  {
    return this.kuDianMoney;
  }
  
  public void setKuDianMoney(BigDecimal paramBigDecimal)
  {
    this.kuDianMoney = paramBigDecimal;
  }
  
  public ArrayList getTradeDetail()
  {
    return this.tradeDetail;
  }
  
  public void setTradeDetail(ArrayList paramArrayList)
  {
    this.tradeDetail = paramArrayList;
  }
  
  public BigDecimal getActualAmount()
  {
    return this.actualAmount;
  }
  
  public void setActualAmount(BigDecimal paramBigDecimal)
  {
    this.actualAmount = paramBigDecimal;
  }
  
  public BigDecimal getActualMoney()
  {
    return this.actualMoney;
  }
  
  public void setActualMoney(BigDecimal paramBigDecimal)
  {
    this.actualMoney = paramBigDecimal;
  }
  
  public BigDecimal getApproFinance()
  {
    return this.approFinance;
  }
  
  public void setApproFinance(BigDecimal paramBigDecimal)
  {
    this.approFinance = paramBigDecimal;
  }
  
  public BigDecimal getBuyCost()
  {
    return this.buyCost;
  }
  
  public void setBuyCost(BigDecimal paramBigDecimal)
  {
    this.buyCost = paramBigDecimal;
  }
  
  public BigDecimal getBuyerFellBackMoney()
  {
    return this.buyerFellBackMoney;
  }
  
  public void setBuyerFellBackMoney(BigDecimal paramBigDecimal)
  {
    this.buyerFellBackMoney = paramBigDecimal;
  }
  
  public BigDecimal getConcelContract()
  {
    return this.concelContract;
  }
  
  public void setConcelContract(BigDecimal paramBigDecimal)
  {
    this.concelContract = paramBigDecimal;
  }
  
  public String getConfirmListDay()
  {
    return this.confirmListDay;
  }
  
  public void setConfirmListDay(String paramString)
  {
    this.confirmListDay = paramString;
  }
  
  public int getDelayDays()
  {
    return this.delayDays;
  }
  
  public void setDelayDays(int paramInt)
  {
    this.delayDays = paramInt;
  }
  
  public BigDecimal getDelayLoanInter()
  {
    return this.delayLoanInter;
  }
  
  public void setDelayLoanInter(BigDecimal paramBigDecimal)
  {
    this.delayLoanInter = paramBigDecimal;
  }
  
  public BigDecimal getDiffProfitLoss()
  {
    return this.diffProfitLoss;
  }
  
  public void setDiffProfitLoss(BigDecimal paramBigDecimal)
  {
    this.diffProfitLoss = paramBigDecimal;
  }
  
  public BigDecimal getExhauAmount()
  {
    return this.exhauAmount;
  }
  
  public void setExhauAmount(BigDecimal paramBigDecimal)
  {
    this.exhauAmount = paramBigDecimal;
  }
  
  public BigDecimal getExpenditure()
  {
    return this.expenditure;
  }
  
  public void setExpenditure(BigDecimal paramBigDecimal)
  {
    this.expenditure = paramBigDecimal;
  }
  
  public BigDecimal getFellBackAmount()
  {
    return this.fellBackAmount;
  }
  
  public void setFellBackAmount(BigDecimal paramBigDecimal)
  {
    this.fellBackAmount = paramBigDecimal;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String paramString)
  {
    this.grade = paramString;
  }
  
  public BigDecimal getHaveLoanMoney()
  {
    return this.haveLoanMoney;
  }
  
  public void setHaveLoanMoney(BigDecimal paramBigDecimal)
  {
    this.haveLoanMoney = paramBigDecimal;
  }
  
  public BigDecimal getIncome()
  {
    return this.income;
  }
  
  public void setIncome(BigDecimal paramBigDecimal)
  {
    this.income = paramBigDecimal;
  }
  
  public BigDecimal getLoanMoney()
  {
    return this.loanMoney;
  }
  
  public void setLoanMoney(BigDecimal paramBigDecimal)
  {
    this.loanMoney = paramBigDecimal;
  }
  
  public BigDecimal getNotLoanInter()
  {
    return this.notLoanInter;
  }
  
  public void setNotLoanInter(BigDecimal paramBigDecimal)
  {
    this.notLoanInter = paramBigDecimal;
  }
  
  public BigDecimal getNotLoanMoney()
  {
    return this.notLoanMoney;
  }
  
  public void setNotLoanMoney(BigDecimal paramBigDecimal)
  {
    this.notLoanMoney = paramBigDecimal;
  }
  
  public BigDecimal getNotpay()
  {
    return this.notpay;
  }
  
  public void setNotpay(BigDecimal paramBigDecimal)
  {
    this.notpay = paramBigDecimal;
  }
  
  public String getPayDay()
  {
    return this.payDay;
  }
  
  public void setPayDay(String paramString)
  {
    this.payDay = paramString;
  }
  
  public BigDecimal getPayed()
  {
    return this.payed;
  }
  
  public void setPayed(BigDecimal paramBigDecimal)
  {
    this.payed = paramBigDecimal;
  }
  
  public BigDecimal getPayedTotal()
  {
    return this.payedTotal;
  }
  
  public void setPayedTotal(BigDecimal paramBigDecimal)
  {
    this.payedTotal = paramBigDecimal;
  }
  
  public BigDecimal getPoundage()
  {
    return this.poundage;
  }
  
  public void setPoundage(BigDecimal paramBigDecimal)
  {
    this.poundage = paramBigDecimal;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
  
  public void setPrice(BigDecimal paramBigDecimal)
  {
    this.price = paramBigDecimal;
  }
  
  public String getProName()
  {
    return this.proName;
  }
  
  public void setProName(String paramString)
  {
    this.proName = paramString;
  }
  
  public BigDecimal getReturnLoanMoney()
  {
    return this.returnLoanMoney;
  }
  
  public void setReturnLoanMoney(BigDecimal paramBigDecimal)
  {
    this.returnLoanMoney = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public String getVari()
  {
    return this.vari;
  }
  
  public void setVari(String paramString)
  {
    this.vari = paramString;
  }
}

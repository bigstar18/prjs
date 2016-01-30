package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class TotalBalanceRegulation
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankCode;
  private String MarketCode;
  private String TransDateTime;
  private String MoneyType;
  private String CashExCode;
  private int AccountSummary;
  private int TransSettlementMoneySummary;
  private int ReservationReserve;
  private int TradeDifferenceStatus;
  private int GenerationOfSettlement;

  public String getBankCode()
  {
    return this.BankCode;
  }
  public void setBankCode(String bankCode) {
    this.BankCode = bankCode;
  }
  public String getMarketCode() {
    return this.MarketCode;
  }
  public void setMarketCode(String marketCode) {
    this.MarketCode = marketCode;
  }
  public String getTransDateTime() {
    return this.TransDateTime;
  }
  public void setTransDateTime(String transDateTime) {
    this.TransDateTime = transDateTime;
  }
  public String getMoneyType() {
    return this.MoneyType;
  }
  public void setMoneyType(String moneyType) {
    this.MoneyType = moneyType;
  }
  public String getCashExCode() {
    return this.CashExCode;
  }
  public void setCashExCode(String cashExCode) {
    this.CashExCode = cashExCode;
  }
  public int getAccountSummary() {
    return this.AccountSummary;
  }
  public void setAccountSummary(int accountSummary) {
    this.AccountSummary = accountSummary;
  }
  public int getTransSettlementMoneySummary() {
    return this.TransSettlementMoneySummary;
  }
  public void setTransSettlementMoneySummary(int transSettlementMoneySummary) {
    this.TransSettlementMoneySummary = transSettlementMoneySummary;
  }
  public int getReservationReserve() {
    return this.ReservationReserve;
  }
  public void setReservationReserve(int reservationReserve) {
    this.ReservationReserve = reservationReserve;
  }
  public int getTradeDifferenceStatus() {
    return this.TradeDifferenceStatus;
  }
  public void setTradeDifferenceStatus(int tradeDifferenceStatus) {
    this.TradeDifferenceStatus = tradeDifferenceStatus;
  }
  public int getGenerationOfSettlement() {
    return this.GenerationOfSettlement;
  }
  public void setGenerationOfSettlement(int generationOfSettlement) {
    this.GenerationOfSettlement = generationOfSettlement;
  }
}
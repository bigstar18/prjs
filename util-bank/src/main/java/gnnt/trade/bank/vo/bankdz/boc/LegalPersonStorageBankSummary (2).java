package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class LegalPersonStorageBankSummary
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankCode;
  private String MarketCode;
  private String TransDateTime;
  private String BankAbbreviation;
  private String LegalPersonSettlementName;
  private String LegalPersonSettlementAccount;
  private String LegalPersonSettlementOpenBank;
  private String StorageTubeBankSummaryName;
  private String StorageTubeBankSummaryAccount;
  private String StorageTubeBankSummaryOpenBank;
  private String TradeDifference;
  private String MoneyType;
  private String CashExCode;
  private int money;

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
  public String getBankAbbreviation() {
    return this.BankAbbreviation;
  }
  public void setBankAbbreviation(String bankAbbreviation) {
    this.BankAbbreviation = bankAbbreviation;
  }
  public String getLegalPersonSettlementName() {
    return this.LegalPersonSettlementName;
  }
  public void setLegalPersonSettlementName(String legalPersonSettlementName) {
    this.LegalPersonSettlementName = legalPersonSettlementName;
  }
  public String getLegalPersonSettlementAccount() {
    return this.LegalPersonSettlementAccount;
  }
  public void setLegalPersonSettlementAccount(String legalPersonSettlementAccount) {
    this.LegalPersonSettlementAccount = legalPersonSettlementAccount;
  }
  public String getLegalPersonSettlementOpenBank() {
    return this.LegalPersonSettlementOpenBank;
  }

  public void setLegalPersonSettlementOpenBank(String legalPersonSettlementOpenBank) {
    this.LegalPersonSettlementOpenBank = legalPersonSettlementOpenBank;
  }
  public String getStorageTubeBankSummaryName() {
    return this.StorageTubeBankSummaryName;
  }
  public void setStorageTubeBankSummaryName(String storageTubeBankSummaryName) {
    this.StorageTubeBankSummaryName = storageTubeBankSummaryName;
  }
  public String getStorageTubeBankSummaryAccount() {
    return this.StorageTubeBankSummaryAccount;
  }

  public void setStorageTubeBankSummaryAccount(String storageTubeBankSummaryAccount) {
    this.StorageTubeBankSummaryAccount = storageTubeBankSummaryAccount;
  }
  public String getStorageTubeBankSummaryOpenBank() {
    return this.StorageTubeBankSummaryOpenBank;
  }

  public void setStorageTubeBankSummaryOpenBank(String storageTubeBankSummaryOpenBank) {
    this.StorageTubeBankSummaryOpenBank = storageTubeBankSummaryOpenBank;
  }
  public String getTradeDifference() {
    return this.TradeDifference;
  }
  public void setTradeDifference(String tradeDifference) {
    this.TradeDifference = tradeDifference;
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
  public int getMoney() {
    return this.money;
  }
  public void setMoney(int money) {
    this.money = money;
  }
}
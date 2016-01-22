package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class BranchAccountAbnormalBalance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankCode;
  private String MarketCode;
  private String TransDateTime;
  private String MoneyType;
  private String CashExCode;
  private String TaiZhangZhangHao;
  private String bondAcc;
  private String certificationName;
  private int DepositAmountMoney;
  private int StockFundAccountMoney;

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
  public String getTaiZhangZhangHao() {
    return this.TaiZhangZhangHao;
  }
  public void setTaiZhangZhangHao(String taiZhangZhangHao) {
    this.TaiZhangZhangHao = taiZhangZhangHao;
  }
  public String getBondAcc() {
    return this.bondAcc;
  }
  public void setBondAcc(String bondAcc) {
    this.bondAcc = bondAcc;
  }
  public String getCertificationName() {
    return this.certificationName;
  }
  public void setCertificationName(String certificationName) {
    this.certificationName = certificationName;
  }
  public int getDepositAmountMoney() {
    return this.DepositAmountMoney;
  }
  public void setDepositAmountMoney(int depositAmountMoney) {
    this.DepositAmountMoney = depositAmountMoney;
  }
  public int getStockFundAccountMoney() {
    return this.StockFundAccountMoney;
  }
  public void setStockFundAccountMoney(int stockFundAccountMoney) {
    this.StockFundAccountMoney = stockFundAccountMoney;
  }
}
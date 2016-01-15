package gnnt.MEBS.xhzc.manage;

public class MarketConfigValue
{
  public String marketName;
  public String weekRest;
  public String yearRest;
  public String description;
  public float tradeUnit;
  public float bail;
  public float charge;
  public float transferFee;
  public int maxStorage;
  public int tradePeriod;

  public float getBail()
  {
    return this.bail;
  }

  public float getCharge() {
    return this.charge;
  }

  public String getDescription() {
    return this.description;
  }

  public String getMarketName() {
    return this.marketName;
  }

  public float getTradeUnit() {
    return this.tradeUnit;
  }

  public float getTransferFee() {
    return this.transferFee;
  }

  public String getWeekRest() {
    return this.weekRest;
  }

  public String getYearRest() {
    return this.yearRest;
  }

  public int getMaxStorage() {
    return this.maxStorage;
  }

  public int getTradePeriod() {
    return this.tradePeriod;
  }

  public void setBail(float bail) {
    this.bail = bail;
  }

  public void setCharge(float charge) {
    this.charge = charge;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setMarketName(String marketName) {
    this.marketName = marketName;
  }

  public void setTradeUnit(float tradeUnit) {
    this.tradeUnit = tradeUnit;
  }

  public void setTransferFee(float transferFee) {
    this.transferFee = transferFee;
  }

  public void setWeekRest(String weekRest) {
    this.weekRest = weekRest;
  }

  public void setYearRest(String yearRest) {
    this.yearRest = yearRest;
  }

  public void setMaxStorage(int maxStorage) {
    this.maxStorage = maxStorage;
  }

  public void setTradePeriod(int tradePeriod) {
    this.tradePeriod = tradePeriod;
  }
}
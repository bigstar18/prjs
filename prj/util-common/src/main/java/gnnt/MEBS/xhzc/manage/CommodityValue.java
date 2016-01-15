package gnnt.MEBS.xhzc.manage;

import java.sql.Timestamp;

public class CommodityValue
{
  public String code;
  public String name;
  public Timestamp createtime;
  public int status;
  public int type;
  public Timestamp modifytime;
  public int tradeUnit;
  public int tradePeriod;
  public int maxStorage;
  public float bail;
  public float charge;
  public float transferFee;
  public String Description;

  public float getBail()
  {
    return this.bail;
  }

  public float getCharge() {
    return this.charge;
  }

  public String getCode() {
    return this.code;
  }

  public int getType() {
    return this.type;
  }

  public float getTransferFee() {
    return this.transferFee;
  }

  public int getTradeUnit() {
    return this.tradeUnit;
  }

  public int getTradePeriod() {
    return this.tradePeriod;
  }

  public int getStatus() {
    return this.status;
  }

  public Timestamp getModifytime() {
    return this.modifytime;
  }

  public String getDescription() {
    return this.Description;
  }

  public Timestamp getCreatetime() {
    return this.createtime;
  }

  public String getName() {
    return this.name;
  }

  public int getMaxStorage() {
    return this.maxStorage;
  }

  public void setBail(float bail) {
    this.bail = bail;
  }

  public void setCharge(float charge) {
    this.charge = charge;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }

  public void setDescription(String Description) {
    this.Description = Description;
  }

  public void setModifytime(Timestamp modifytime) {
    this.modifytime = modifytime;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setTradePeriod(int tradePeriod) {
    this.tradePeriod = tradePeriod;
  }

  public void setTradeUnit(int tradeUnit) {
    this.tradeUnit = tradeUnit;
  }

  public void setTransferFee(float transferFee) {
    this.transferFee = transferFee;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setMaxStorage(int maxStorage) {
    this.maxStorage = maxStorage;
  }
}
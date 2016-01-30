package gnnt.MEBS.common.broker.model;

public class TradeModule extends StandardModel
{
  private static final long serialVersionUID = -3662370087483814027L;
  private Integer moduleId;
  private String cnName;
  private String enName;
  private String shortName;
  private String addFirmFn;
  private String updateFirmStatusFn;
  private String delFirmFn;
  private String isFirmSet;

  public Integer getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }

  public String getCnName()
  {
    return this.cnName;
  }

  public void setCnName(String paramString)
  {
    this.cnName = paramString;
  }

  public String getEnName()
  {
    return this.enName;
  }

  public void setEnName(String paramString)
  {
    this.enName = paramString;
  }

  public String getShortName()
  {
    return this.shortName;
  }

  public void setShortName(String paramString)
  {
    this.shortName = paramString;
  }

  public String getAddFirmFn()
  {
    return this.addFirmFn;
  }

  public void setAddFirmFn(String paramString)
  {
    this.addFirmFn = paramString;
  }

  public String getUpdateFirmStatusFn()
  {
    return this.updateFirmStatusFn;
  }

  public void setUpdateFirmStatusFn(String paramString)
  {
    this.updateFirmStatusFn = paramString;
  }

  public String getDelFirmFn()
  {
    return this.delFirmFn;
  }

  public void setDelFirmFn(String paramString)
  {
    this.delFirmFn = paramString;
  }

  public String getIsFirmSet()
  {
    return this.isFirmSet;
  }

  public void setIsFirmSet(String paramString)
  {
    this.isFirmSet = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("moduleId", this.moduleId);
  }
}
package gnnt.MEBS.timebargain.manage.model;

import java.util.List;

public class Tariff
{
  private String TariffID;
  private String CommodityID;
  private String TariffName;
  private double TariffRate;
  private short FeeAlgr;
  private double FeeRate_B;
  private double FeeRate_S;
  private double TodayCloseFeeRate_B;
  private double TodayCloseFeeRate_S;
  private double HistoryCloseFeeRate_B;
  private double HistoryCloseFeeRate_S;
  private short SettleFeeAlgr;
  private double SettleFeeRate_B;
  private double SettleFeeRate_S;
  private short ForceCloseFeeAlgr;
  private double ForceCloseFeeRate_B;
  private double ForceCloseFeeRate_S;
  private String CreateTime;
  private String CreateUser;
  private String ModifyTime;
  private String ModifyUser;
  private String BrokerID;
  private List commodityList;
  private short status;
  
  public String getTariffID()
  {
    return this.TariffID;
  }
  
  public void setTariffID(String paramString)
  {
    this.TariffID = paramString;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.CommodityID = paramString;
  }
  
  public String getTariffName()
  {
    return this.TariffName;
  }
  
  public void setTariffName(String paramString)
  {
    this.TariffName = paramString;
  }
  
  public double getTariffRate()
  {
    return this.TariffRate;
  }
  
  public void setTariffRate(double paramDouble)
  {
    this.TariffRate = paramDouble;
  }
  
  public double getFeeRate_B()
  {
    return this.FeeRate_B;
  }
  
  public void setFeeRate_B(double paramDouble)
  {
    this.FeeRate_B = paramDouble;
  }
  
  public double getFeeRate_S()
  {
    return this.FeeRate_S;
  }
  
  public void setFeeRate_S(double paramDouble)
  {
    this.FeeRate_S = paramDouble;
  }
  
  public double getTodayCloseFeeRate_B()
  {
    return this.TodayCloseFeeRate_B;
  }
  
  public void setTodayCloseFeeRate_B(double paramDouble)
  {
    this.TodayCloseFeeRate_B = paramDouble;
  }
  
  public double getTodayCloseFeeRate_S()
  {
    return this.TodayCloseFeeRate_S;
  }
  
  public void setTodayCloseFeeRate_S(double paramDouble)
  {
    this.TodayCloseFeeRate_S = paramDouble;
  }
  
  public double getHistoryCloseFeeRate_B()
  {
    return this.HistoryCloseFeeRate_B;
  }
  
  public void setHistoryCloseFeeRate_B(double paramDouble)
  {
    this.HistoryCloseFeeRate_B = paramDouble;
  }
  
  public double getHistoryCloseFeeRate_S()
  {
    return this.HistoryCloseFeeRate_S;
  }
  
  public void setHistoryCloseFeeRate_S(double paramDouble)
  {
    this.HistoryCloseFeeRate_S = paramDouble;
  }
  
  public double getSettleFeeRate_B()
  {
    return this.SettleFeeRate_B;
  }
  
  public void setSettleFeeRate_B(double paramDouble)
  {
    this.SettleFeeRate_B = paramDouble;
  }
  
  public double getSettleFeeRate_S()
  {
    return this.SettleFeeRate_S;
  }
  
  public void setSettleFeeRate_S(double paramDouble)
  {
    this.SettleFeeRate_S = paramDouble;
  }
  
  public long getForceCloseFeeAlgr()
  {
    return this.ForceCloseFeeAlgr;
  }
  
  public void setForceCloseFeeAlgr(short paramShort)
  {
    this.ForceCloseFeeAlgr = paramShort;
  }
  
  public double getForceCloseFeeRate_B()
  {
    return this.ForceCloseFeeRate_B;
  }
  
  public void setForceCloseFeeRate_B(double paramDouble)
  {
    this.ForceCloseFeeRate_B = paramDouble;
  }
  
  public double getForceCloseFeeRate_S()
  {
    return this.ForceCloseFeeRate_S;
  }
  
  public void setForceCloseFeeRate_S(double paramDouble)
  {
    this.ForceCloseFeeRate_S = paramDouble;
  }
  
  public String getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(String paramString)
  {
    this.CreateTime = paramString;
  }
  
  public String getCreateUser()
  {
    return this.CreateUser;
  }
  
  public void setCreateUser(String paramString)
  {
    this.CreateUser = paramString;
  }
  
  public String getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.ModifyTime = paramString;
  }
  
  public String getModifyUser()
  {
    return this.ModifyUser;
  }
  
  public void setModifyUser(String paramString)
  {
    this.ModifyUser = paramString;
  }
  
  public String getBrokerID()
  {
    return this.BrokerID;
  }
  
  public void setBrokerID(String paramString)
  {
    this.BrokerID = paramString;
  }
  
  public List getCommodityList()
  {
    return this.commodityList;
  }
  
  public void setCommodityList(List paramList)
  {
    this.commodityList = paramList;
  }
  
  public short getFeeAlgr()
  {
    return this.FeeAlgr;
  }
  
  public void setFeeAlgr(short paramShort)
  {
    this.FeeAlgr = paramShort;
  }
  
  public short getSettleFeeAlgr()
  {
    return this.SettleFeeAlgr;
  }
  
  public void setSettleFeeAlgr(short paramShort)
  {
    this.SettleFeeAlgr = paramShort;
  }
  
  public short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(short paramShort)
  {
    this.status = paramShort;
  }
}

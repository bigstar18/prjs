package gnnt.trade.bank.vo;

import java.io.Serializable;

public class CityValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String cityID;
  public String cityName;
  public String cityCode;
  public String parentID;
  
  public String getCityID()
  {
    return this.cityID;
  }
  
  public void setCityID(String cityID)
  {
    this.cityID = cityID;
  }
  
  public String getCityName()
  {
    return this.cityName;
  }
  
  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }
  
  public String getCityCode()
  {
    return this.cityCode;
  }
  
  public void setCityCode(String cityCode)
  {
    this.cityCode = cityCode;
  }
  
  public String getParentID()
  {
    return this.parentID;
  }
  
  public void setParentID(String parentID)
  {
    this.parentID = parentID;
  }
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("cityID:" + this.cityID + sep);
    sb.append("cityName:" + this.cityName + sep);
    sb.append("cityCode:" + this.cityCode + sep);
    sb.append("parentID:" + this.parentID + sep);
    sb.append(sep);
    return sb.toString();
  }
}

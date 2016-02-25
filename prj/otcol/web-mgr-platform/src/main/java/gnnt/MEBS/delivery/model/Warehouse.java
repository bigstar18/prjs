package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Set;

public class Warehouse
  extends Clone
{
  private String id;
  private int ability;
  private String name;
  private String fullName;
  private String address;
  private String linkman;
  private String tel;
  private String fax;
  private String email;
  private String postcode;
  private double max_Capacity;
  private double used_Capacity;
  private double bail;
  private Set<Commodity> commoditySet;
  
  public int getAbility()
  {
    return this.ability;
  }
  
  public void setAbility(int paramInt)
  {
    this.ability = paramInt;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String paramString)
  {
    this.address = paramString;
  }
  
  public double getBail()
  {
    return this.bail;
  }
  
  public void setBail(double paramDouble)
  {
    this.bail = paramDouble;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String paramString)
  {
    this.email = paramString;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String paramString)
  {
    this.fax = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getLinkman()
  {
    return this.linkman;
  }
  
  public void setLinkman(String paramString)
  {
    this.linkman = paramString;
  }
  
  public double getMax_Capacity()
  {
    return this.max_Capacity;
  }
  
  public void setMax_Capacity(double paramDouble)
  {
    this.max_Capacity = paramDouble;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getPostcode()
  {
    return this.postcode;
  }
  
  public void setPostcode(String paramString)
  {
    this.postcode = paramString;
  }
  
  public String getTel()
  {
    return this.tel;
  }
  
  public void setTel(String paramString)
  {
    this.tel = paramString;
  }
  
  public double getUsed_Capacity()
  {
    return this.used_Capacity;
  }
  
  public void setUsed_Capacity(double paramDouble)
  {
    this.used_Capacity = paramDouble;
  }
  
  public Set<Commodity> getCommoditySet()
  {
    return this.commoditySet;
  }
  
  public void addCommoditySet(Set<Commodity> paramSet)
  {
    this.commoditySet = paramSet;
  }
  
  public String getFullName()
  {
    return this.fullName;
  }
  
  public void setFullName(String paramString)
  {
    this.fullName = paramString;
  }
}

package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;
import java.util.List;

public class Commodity
  extends Clone
{
  private String id;
  private int ability;
  private String name;
  private double minJS;
  private double yshort;
  private double ylong;
  private String countType;
  private Date createtime;
  private Date modifyDate;
  private String operationId;
  private List<CommodityExpansion> qualityList;
  private List<CommodityExpansion> gradeList;
  private List<CommodityExpansion> originList;
  private List<CommodityExpansion> sortList;
  
  public List<CommodityExpansion> getSortList()
  {
    return this.sortList;
  }
  
  public void addSortList(List<CommodityExpansion> paramList)
  {
    this.sortList = paramList;
  }
  
  public List<CommodityExpansion> getGradeList()
  {
    return this.gradeList;
  }
  
  public void addGradeList(List<CommodityExpansion> paramList)
  {
    this.gradeList = paramList;
  }
  
  public List<CommodityExpansion> getOriginList()
  {
    return this.originList;
  }
  
  public void addOriginList(List<CommodityExpansion> paramList)
  {
    this.originList = paramList;
  }
  
  public List<CommodityExpansion> getQualityList()
  {
    return this.qualityList;
  }
  
  public void addQualityList(List<CommodityExpansion> paramList)
  {
    this.qualityList = paramList;
  }
  
  public int getAbility()
  {
    return this.ability;
  }
  
  public void setAbility(int paramInt)
  {
    this.ability = paramInt;
  }
  
  public String getCountType()
  {
    return this.countType;
  }
  
  public void setCountType(String paramString)
  {
    this.countType = paramString;
  }
  
  public Date getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(Date paramDate)
  {
    this.createtime = paramDate;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public double getMinJS()
  {
    return this.minJS;
  }
  
  public void setMinJS(double paramDouble)
  {
    this.minJS = paramDouble;
  }
  
  public Date getModifyDate()
  {
    return this.modifyDate;
  }
  
  public void setModifyDate(Date paramDate)
  {
    this.modifyDate = paramDate;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getOperationId()
  {
    return this.operationId;
  }
  
  public void setOperationId(String paramString)
  {
    this.operationId = paramString;
  }
  
  public double getYlong()
  {
    return this.ylong;
  }
  
  public void setYlong(double paramDouble)
  {
    this.ylong = paramDouble;
  }
  
  public double getYshort()
  {
    return this.yshort;
  }
  
  public void setYshort(double paramDouble)
  {
    this.yshort = paramDouble;
  }
}

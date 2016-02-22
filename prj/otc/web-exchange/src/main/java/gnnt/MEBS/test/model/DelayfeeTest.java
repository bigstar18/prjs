package gnnt.MEBS.test.model;

import gnnt.MEBS.base.model.Clone;
import java.util.List;

public class DelayfeeTest
  extends Clone
{
  private Long id;
  private String commodityId;
  private String firmId;
  private List<Double> list;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public List<Double> getList()
  {
    return this.list;
  }
  
  public void setList(List<Double> list)
  {
    this.list = list;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}

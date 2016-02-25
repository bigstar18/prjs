package gnnt.MEBS.member.broker.model;

import java.util.List;

public class BrokerMenu
  extends Cloneable
{
  private Integer menuId;
  private String url;
  private String title;
  private Integer parentId;
  private Integer seq;
  private List<BrokerMenu> brokerMenuList;
  
  public Integer getMenuId()
  {
    return this.menuId;
  }
  
  public void setMenuId(Integer paramInteger)
  {
    this.menuId = paramInteger;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public Integer getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(Integer paramInteger)
  {
    this.parentId = paramInteger;
  }
  
  public Integer getSeq()
  {
    return this.seq;
  }
  
  public void setSeq(Integer paramInteger)
  {
    this.seq = paramInteger;
  }
  
  public List<BrokerMenu> getBrokerMenuList()
  {
    return this.brokerMenuList;
  }
  
  public void setBrokerMenuList(List<BrokerMenu> paramList)
  {
    this.brokerMenuList = paramList;
  }
}

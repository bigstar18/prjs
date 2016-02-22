package gnnt.MEBS.base.query.hibernate;

import org.hibernate.CacheMode;

public class CacheSet
{
  private boolean able = false;
  private String region = null;
  private CacheMode mode = CacheMode.NORMAL;
  
  public CacheSet()
  {
    this.able = true;
  }
  
  public boolean getAble()
  {
    return this.able;
  }
  
  public void setAble(boolean able)
  {
    this.able = able;
  }
  
  public String getRegion()
  {
    return this.region;
  }
  
  public void setRegion(String region)
  {
    this.region = region;
  }
  
  public CacheMode getMode()
  {
    return this.mode;
  }
  
  public void setMode(CacheMode mode)
  {
    this.mode = mode;
  }
}

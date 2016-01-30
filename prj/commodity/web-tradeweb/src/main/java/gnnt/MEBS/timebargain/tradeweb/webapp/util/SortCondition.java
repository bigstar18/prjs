package gnnt.MEBS.timebargain.tradeweb.webapp.util;

public class SortCondition
{
  private String startNu;
  private String reccnt;
  private String sortfLD;
  private int isdesc;
  
  public int getIsdesc()
  {
    return this.isdesc;
  }
  
  public void setIsdesc(int paramInt)
  {
    this.isdesc = paramInt;
  }
  
  public String getReccnt()
  {
    return this.reccnt;
  }
  
  public void setReccnt(String paramString)
  {
    this.reccnt = paramString;
  }
  
  public String getSortfLD()
  {
    return this.sortfLD;
  }
  
  public void setSortfLD(String paramString)
  {
    this.sortfLD = paramString;
  }
  
  public String getStartNu()
  {
    return this.startNu;
  }
  
  public void setStartNu(String paramString)
  {
    this.startNu = paramString;
  }
}

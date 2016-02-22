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
  
  public void setIsdesc(int isdesc)
  {
    this.isdesc = isdesc;
  }
  
  public String getReccnt()
  {
    return this.reccnt;
  }
  
  public void setReccnt(String reccnt)
  {
    this.reccnt = reccnt;
  }
  
  public String getSortfLD()
  {
    return this.sortfLD;
  }
  
  public void setSortfLD(String sortfLD)
  {
    this.sortfLD = sortfLD;
  }
  
  public String getStartNu()
  {
    return this.startNu;
  }
  
  public void setStartNu(String startNu)
  {
    this.startNu = startNu;
  }
}

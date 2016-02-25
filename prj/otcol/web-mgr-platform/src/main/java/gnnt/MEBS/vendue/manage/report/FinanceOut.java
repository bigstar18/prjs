package gnnt.MEBS.vendue.manage.report;

import java.util.ArrayList;

public class FinanceOut
{
  private String proName;
  private String proAmount;
  private ArrayList planDetail;
  
  public String getProAmount()
  {
    return this.proAmount;
  }
  
  public void setProAmount(String paramString)
  {
    this.proAmount = paramString;
  }
  
  public ArrayList getPlanDetail()
  {
    return this.planDetail;
  }
  
  public void setPlanDetail(ArrayList paramArrayList)
  {
    this.planDetail = paramArrayList;
  }
  
  public String getProName()
  {
    return this.proName;
  }
  
  public void setProName(String paramString)
  {
    this.proName = paramString;
  }
}

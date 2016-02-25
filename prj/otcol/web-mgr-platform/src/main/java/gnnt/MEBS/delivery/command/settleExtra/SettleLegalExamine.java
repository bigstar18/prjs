package gnnt.MEBS.delivery.command.settleExtra;

import java.util.List;

public class SettleLegalExamine
{
  private List<Integer> statusList;
  private List<Integer> typeList;
  private boolean isCheckRegStock = false;
  
  public List<Integer> getStatusList()
  {
    return this.statusList;
  }
  
  public void setStatusList(List<Integer> paramList)
  {
    this.statusList = paramList;
  }
  
  public List<Integer> getTypeList()
  {
    return this.typeList;
  }
  
  public void setTypeList(List<Integer> paramList)
  {
    this.typeList = paramList;
  }
  
  public boolean isCheckRegStock()
  {
    return this.isCheckRegStock;
  }
  
  public void setCheckRegStock(boolean paramBoolean)
  {
    this.isCheckRegStock = paramBoolean;
  }
}

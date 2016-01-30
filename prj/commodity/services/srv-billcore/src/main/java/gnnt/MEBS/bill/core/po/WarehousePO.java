package gnnt.MEBS.bill.core.po;

import java.io.Serializable;

public class WarehousePO
  extends Clone
  implements Serializable
{
  private static final long serialVersionUID = -4657645850372675084L;
  private long id;
  private String warehouseID;
  private String warehouseName;
  private int status;
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getWarehouseID()
  {
    return this.warehouseID;
  }
  
  public void setWarehouseID(String paramString)
  {
    this.warehouseID = paramString;
  }
  
  public String getWarehouseName()
  {
    return this.warehouseName;
  }
  
  public void setWarehouseName(String paramString)
  {
    this.warehouseName = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}

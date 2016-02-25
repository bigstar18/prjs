package gnnt.MEBS.zcjs.model;

public class SystemState
{
  private int state;
  private boolean isAuto;
  
  public int getState()
  {
    return this.state;
  }
  
  public void setState(int paramInt)
  {
    this.state = paramInt;
  }
  
  public boolean isAuto()
  {
    return this.isAuto;
  }
  
  public void setAuto(boolean paramBoolean)
  {
    this.isAuto = paramBoolean;
  }
}

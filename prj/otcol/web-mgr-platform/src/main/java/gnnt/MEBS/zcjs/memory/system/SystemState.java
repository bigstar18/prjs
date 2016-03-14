package gnnt.MEBS.zcjs.memory.system;

public class SystemState
{
  private int state;
  private boolean isAuto;
  
  public int getState()
  {
    return this.state;
  }
  
  public void setState(int state)
  {
    this.state = state;
  }
  
  public boolean isAuto()
  {
    return this.isAuto;
  }
  
  public void setAuto(boolean isAuto)
  {
    this.isAuto = isAuto;
  }
}

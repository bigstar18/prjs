package gnnt.MEBS.activeUser.util;

public enum LogonMode
{
  SINGLE_MODE(1),  MULTI_MODE(2);
  
  private final int value;
  
  public int getValue()
  {
    return this.value;
  }
  
  private LogonMode(int value)
  {
    this.value = value;
  }
}

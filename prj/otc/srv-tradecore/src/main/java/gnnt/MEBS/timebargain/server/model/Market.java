package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Market
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private Short runMode;
  private short clearRunMode;
  private int initPreSecs;
  private int clearDelaySecs;
  public static final short RUNMODE_HAND = 0;
  public static final short RUNMODE_AUTO = 1;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Short getRunMode()
  {
    return this.runMode;
  }
  
  public void setRunMode(Short runMode)
  {
    this.runMode = runMode;
  }
  
  public Short getClearRunMode()
  {
    return Short.valueOf(this.clearRunMode);
  }
  
  public void setClearRunMode(Short clearRunMode)
  {
    this.clearRunMode = clearRunMode.shortValue();
  }
  
  public int getInitPreSecs()
  {
    return this.initPreSecs;
  }
  
  public void setInitPreSecs(int initPreDelaySecs)
  {
    this.initPreSecs = initPreDelaySecs;
  }
  
  public int getClearDelaySecs()
  {
    return this.clearDelaySecs;
  }
  
  public void setClearDelaySecs(int clearDelaySecs)
  {
    this.clearDelaySecs = clearDelaySecs;
  }
}

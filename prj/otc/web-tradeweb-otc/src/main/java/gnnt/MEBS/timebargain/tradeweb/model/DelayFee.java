package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;

public class DelayFee
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = -5179244494322055841L;
  private int StepNo;
  private long DelayFee;
  private int StepValue;
  
  public int getStepNo()
  {
    return this.StepNo;
  }
  
  public void setStepNo(int stepNo)
  {
    this.StepNo = stepNo;
  }
  
  public long getDelayFee()
  {
    return this.DelayFee;
  }
  
  public void setDelayFee(long delayFee)
  {
    this.DelayFee = delayFee;
  }
  
  public int getStepValue()
  {
    return this.StepValue;
  }
  
  public void setStepValue(int stepValue)
  {
    this.StepValue = stepValue;
  }
  
  public boolean equals(Object o)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public String toString()
  {
    return null;
  }
}

package gnnt.MEBS.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public abstract class Clone
  implements Cloneable, Serializable
{
  public Object clone()
  {
    Object o = null;
    try
    {
      o = super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      e.printStackTrace();
    }
    return o;
  }
  
  public BigDecimal formatDecimals(BigDecimal value, int scale)
  {
    BigDecimal valueFormat = null;
    if (value != null) {
      valueFormat = value.setScale(scale, 4);
    }
    return valueFormat;
  }
  
  public Date transformData(Date date)
  {
    return new Date(date.getTime());
  }
  
  public boolean[] algorithmArray = { true, true, true, true, true };
  
  public abstract <T extends Serializable> T getId();
  
  public abstract void setPrimary(String paramString);
}

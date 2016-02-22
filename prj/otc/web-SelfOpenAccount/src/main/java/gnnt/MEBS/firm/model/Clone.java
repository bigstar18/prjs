package gnnt.MEBS.firm.model;

import java.io.Serializable;

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
}

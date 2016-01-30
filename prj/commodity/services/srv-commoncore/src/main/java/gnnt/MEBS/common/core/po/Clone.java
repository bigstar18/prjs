package gnnt.MEBS.common.core.po;

public abstract class Clone
  implements Cloneable
{
  public Object clone()
  {
    Object localObject = null;
    try
    {
      localObject = super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      localCloneNotSupportedException.printStackTrace();
    }
    return localObject;
  }
}

package gnnt.MEBS.common.util.query.exception;

public class BaseException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  private int errorCode;
  private Object[] params;
  
  public BaseException(int paramInt, Object[] paramArrayOfObject)
  {
    this.errorCode = paramInt;
    this.params = paramArrayOfObject;
  }
  
  public int getErrorCode()
  {
    return this.errorCode;
  }
  
  public Object[] getParams()
  {
    return this.params;
  }
}

package gnnt.MEBS.base.exception;

public class BaseException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  private int errorCode;
  private Object[] params;
  
  public BaseException(int errorCode, Object[] params)
  {
    this.errorCode = errorCode;
    this.params = params;
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

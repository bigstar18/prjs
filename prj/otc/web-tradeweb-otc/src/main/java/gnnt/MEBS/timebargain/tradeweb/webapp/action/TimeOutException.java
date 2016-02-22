package gnnt.MEBS.timebargain.tradeweb.webapp.action;

public class TimeOutException
  extends Exception
{
  private static final long serialVersionUID = 3403741881108175979L;
  
  public TimeOutException() {}
  
  public TimeOutException(String msg)
  {
    super(msg);
  }
  
  public TimeOutException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public TimeOutException(Throwable cause)
  {
    super(cause);
  }
}

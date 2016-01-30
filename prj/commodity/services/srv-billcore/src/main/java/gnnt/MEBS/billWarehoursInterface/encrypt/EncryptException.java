package gnnt.MEBS.billWarehoursInterface.encrypt;

public class EncryptException
  extends Exception
{
  private static final long serialVersionUID = -8781809465023618932L;
  
  public EncryptException() {}
  
  public EncryptException(String msg)
  {
    super(msg);
  }
  
  public EncryptException(Throwable ex)
  {
    super(ex);
  }
}

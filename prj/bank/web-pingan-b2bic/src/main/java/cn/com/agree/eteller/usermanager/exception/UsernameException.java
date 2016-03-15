package cn.com.agree.eteller.usermanager.exception;

public class UsernameException
  extends Exception
{
  private static final long serialVersionUID = 4859921023160623570L;
  
  public UsernameException() {}
  
  public UsernameException(String arg0)
  {
    super(arg0);
  }
  
  public UsernameException(Throwable arg0)
  {
    super(arg0);
  }
  
  public UsernameException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
  }
}

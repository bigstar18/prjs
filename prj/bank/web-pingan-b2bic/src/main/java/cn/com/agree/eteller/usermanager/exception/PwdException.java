package cn.com.agree.eteller.usermanager.exception;

public class PwdException
  extends Exception
{
  private static final long serialVersionUID = 203724859113188696L;
  
  public PwdException() {}
  
  public PwdException(String arg0)
  {
    super(arg0);
  }
  
  public PwdException(Throwable arg0)
  {
    super(arg0);
  }
  
  public PwdException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
  }
}

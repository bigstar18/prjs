package cn.com.agree.eteller.generic.exception;

public class DuplicateEntityException
  extends Exception
{
  private static final long serialVersionUID = -8911206239816230061L;
  
  public DuplicateEntityException() {}
  
  public DuplicateEntityException(String message)
  {
    super(message);
  }
  
  public DuplicateEntityException(Throwable cause)
  {
    super(cause);
  }
  
  public DuplicateEntityException(String message, Throwable cause)
  {
    super(message, cause);
  }
}

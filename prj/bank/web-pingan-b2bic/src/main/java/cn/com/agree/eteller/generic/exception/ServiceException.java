package cn.com.agree.eteller.generic.exception;

public class ServiceException
  extends Exception
{
  private static final long serialVersionUID = -7053406703455941078L;
  private String errorCode = "";
  private String errorMsg = "";
  
  public ServiceException() {}
  
  public ServiceException(String message)
  {
    super(message);
    this.errorMsg = message;
  }
  
  public ServiceException(String errCode, String errMsg)
  {
    super("错误代码:" + errCode + ", 错误信息:" + errMsg);
    this.errorCode = errCode;
    this.errorMsg = errMsg;
  }
  
  public ServiceException(Throwable cause)
  {
    super(cause);
  }
  
  public ServiceException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public String getErrorCode()
  {
    return this.errorCode;
  }
  
  public void setErrorCode(String errorCode)
  {
    this.errorCode = errorCode;
  }
  
  public String getErrorMsg()
  {
    return this.errorMsg;
  }
  
  public void setErrorMsg(String errorMsg)
  {
    this.errorMsg = errorMsg;
  }
}

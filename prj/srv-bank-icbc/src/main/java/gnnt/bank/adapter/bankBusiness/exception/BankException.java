package gnnt.bank.adapter.bankBusiness.exception;

public class BankException extends RuntimeException
{
  private int ErrorInfo;

  public BankException()
  {
  }

  public BankException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public BankException(String message) {
    super(message);
  }

  public BankException(Throwable cause) {
    super(cause);
  }

  public void setErrorInfo(int ErrorInfo) {
    this.ErrorInfo = ErrorInfo;
  }

  public int getErrorInfo() {
    return this.ErrorInfo;
  }
}
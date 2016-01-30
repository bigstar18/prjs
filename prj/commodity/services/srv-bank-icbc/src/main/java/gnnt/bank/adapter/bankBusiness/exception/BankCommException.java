package gnnt.bank.adapter.bankBusiness.exception;

public class BankCommException extends BankException
{
  public BankCommException()
  {
  }

  public BankCommException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public BankCommException(String message) {
    super(message);
  }

  public BankCommException(Throwable cause) {
    super(cause);
  }
}
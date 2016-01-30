package gnnt.bank.adapter.bankBusiness.exception;

public class BankActionDoubtedException extends BankException
{
  public BankActionDoubtedException()
  {
  }

  public BankActionDoubtedException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public BankActionDoubtedException(String message) {
    super(message);
  }

  public BankActionDoubtedException(Throwable cause) {
    super(cause);
  }
}
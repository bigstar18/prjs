package gnnt.MEBS.activeUser.vo;

public class AUResultVO extends AUBaseVO
{
  private static final long serialVersionUID = 1753313027635011919L;
  private int result;
  private String recode;
  private String message;

  public int getResult()
  {
    return this.result;
  }

  public void setResult(int result)
  {
    this.result = result;
  }

  public String getRecode()
  {
    return this.recode;
  }

  public void setRecode(String recode)
  {
    this.recode = recode;
  }

  public String getMessage()
  {
    return this.message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }
}
package gnnt.MEBS.logonService.vo;

public class ResultVO extends BaseVO
{
  private static final long serialVersionUID = 1753313027635011919L;
  private int result;
  private String recode;
  private String message;

  public int getResult()
  {
    return this.result;
  }

  public void setResult(int paramInt)
  {
    this.result = paramInt;
  }

  public String getRecode()
  {
    return this.recode;
  }

  public void setRecode(String paramString)
  {
    this.recode = paramString;
  }

  public String getMessage()
  {
    return this.message;
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
}
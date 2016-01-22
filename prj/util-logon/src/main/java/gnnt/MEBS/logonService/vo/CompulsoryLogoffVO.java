package gnnt.MEBS.logonService.vo;

import java.util.List;

public class CompulsoryLogoffVO extends BaseVO
{
  private static final long serialVersionUID = 8274913725839151940L;
  private List<String> userIDList;
  private String operator;
  private String logonIP;

  public List<String> getUserIDList()
  {
    return this.userIDList;
  }

  public void setUserIDList(List<String> paramList)
  {
    this.userIDList = paramList;
  }

  public String getOperator()
  {
    return this.operator;
  }

  public void setOperator(String paramString)
  {
    this.operator = paramString;
  }

  public String getLogonIP()
  {
    return this.logonIP;
  }

  public void setLogonIP(String paramString)
  {
    this.logonIP = paramString;
  }
}
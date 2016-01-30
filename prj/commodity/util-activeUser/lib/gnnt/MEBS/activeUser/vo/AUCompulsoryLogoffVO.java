package gnnt.MEBS.activeUser.vo;

import java.util.List;

public class AUCompulsoryLogoffVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 8274913725839151940L;
  private List<String> userIDList;
  private String operator;
  private String logonIP;
  
  public List<String> getUserIDList()
  {
    return this.userIDList;
  }
  
  public void setUserIDList(List<String> userIDList)
  {
    this.userIDList = userIDList;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public String getLogonIP()
  {
    return this.logonIP;
  }
  
  public void setLogonIP(String logonIP)
  {
    this.logonIP = logonIP;
  }
}

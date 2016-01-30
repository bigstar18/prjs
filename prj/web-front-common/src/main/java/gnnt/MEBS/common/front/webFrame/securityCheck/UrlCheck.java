package gnnt.MEBS.common.front.webFrame.securityCheck;

import gnnt.MEBS.common.front.model.integrated.User;
import java.util.Iterator;
import java.util.List;

public class UrlCheck
{
  private List<IUrlCheck> urlCheckList;
  
  public List<IUrlCheck> getUrlCheckList()
  {
    return this.urlCheckList;
  }
  
  public void setUrlCheckList(List<IUrlCheck> paramList)
  {
    this.urlCheckList = paramList;
  }
  
  public UrlCheckResult check(String paramString, User paramUser)
  {
    UrlCheckResult localUrlCheckResult = UrlCheckResult.SUCCESS;
    if ((this.urlCheckList != null) && (this.urlCheckList.size() > 0))
    {
      Iterator localIterator = this.urlCheckList.iterator();
      while (localIterator.hasNext())
      {
        IUrlCheck localIUrlCheck = (IUrlCheck)localIterator.next();
        localUrlCheckResult = localIUrlCheck.check(paramString, paramUser);
        if (localUrlCheckResult != UrlCheckResult.SUCCESS) {
          break;
        }
      }
    }
    return localUrlCheckResult;
  }
}

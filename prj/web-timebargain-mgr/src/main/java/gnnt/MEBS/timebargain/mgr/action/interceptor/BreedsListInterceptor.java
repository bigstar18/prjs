package gnnt.MEBS.timebargain.mgr.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.MBreed;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BreedsListInterceptor extends AbstractInterceptor
{

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;

  public void setStandardService(StandardService paramStandardService)
  {
    this.standardService = paramStandardService;
  }

  public String intercept(ActionInvocation paramActionInvocation)
    throws Exception
  {
    HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
    String str1 = "select * from m_breed where BeLongModule like '%15%' and status=1 order by breedID";
    List localList = this.standardService.getListBySql(str1);
    String str2 = "[?]";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Map localMap = (Map)localIterator.next();
      MBreed localMBreed = new MBreed();
      localMBreed.setBreedID(localMap.get("BREEDID").toString());
      localMBreed.setBreedName(localMap.get("BREEDNAME").toString());
      localMBreed.setCategoryID(localMap.get("CATEGORYID").toString());
      localMBreed.setUnit(localMap.get("UNIT").toString());
      localStringBuilder.append(new StringBuilder().append(localMBreed.toString()).append(",").toString());
    }
    if (localList.size() > 0)
      str2 = str2.replace("?", localStringBuilder.toString().substring(0, localStringBuilder.length() - 1));
    else
      str2 = str2.replace("?", "");
    localHttpServletRequest.setAttribute("breedsList", str2);
    return paramActionInvocation.invoke();
  }
}
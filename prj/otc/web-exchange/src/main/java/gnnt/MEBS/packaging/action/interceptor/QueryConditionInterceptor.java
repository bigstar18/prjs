package gnnt.MEBS.packaging.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class QueryConditionInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QueryConditionInterceptor.class);
  private List<String> keyWordList;
  
  public void setKeyWordList(List<String> keyWordList)
  {
    this.keyWordList = keyWordList;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    Map<String, Object> keys = WebUtils.getParametersStartingWith(request, ActionConstant.GNNT_);
    if ((this.keyWordList != null) && (this.keyWordList.size() > 0) && (keys != null) && (keys.size() > 0))
    {
      Iterator localIterator2;
      for (Iterator localIterator1 = keys.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
      {
        String key = (String)localIterator1.next();
        
        this.logger.debug("key:" + key);
        localIterator2 = this.keyWordList.iterator(); continue;String keyWord = (String)localIterator2.next();
        
        this.logger.debug("keyWord:" + keyWord);
        if (key.toUpperCase().indexOf("[" + keyWord.toUpperCase() + "]") > 0) {
          throw new Exception("illegality query");
        }
      }
    }
    String result = invocation.invoke();
    return result;
  }
}

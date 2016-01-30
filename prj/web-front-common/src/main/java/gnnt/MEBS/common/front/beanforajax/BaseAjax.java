package gnnt.MEBS.common.front.beanforajax;

import com.opensymphony.xwork2.ActionContext;
import gnnt.MEBS.common.front.service.StandardService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BaseAjax
{
  protected final transient Log logger = LogFactory.getLog(getClass());
  protected final String SUCCESS = "success";
  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;
  protected JSONArray jsonValidateReturn;
  private static final String AJAX = "ajax";
  private InputStream inputStream;
  
  protected StandardService getService()
  {
    return this.standardService;
  }
  
  public JSONArray getJsonValidateReturn()
  {
    return this.jsonValidateReturn;
  }
  
  protected HttpServletRequest getRequest()
  {
    ActionContext localActionContext = ActionContext.getContext();
    HttpServletRequest localHttpServletRequest = (HttpServletRequest)localActionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
    return localHttpServletRequest;
  }
  
  protected JSONArray createJSONArray(Object... paramVarArgs)
  {
    JSONArray localJSONArray = new JSONArray();
    for (Object localObject : paramVarArgs) {
      localJSONArray.add(localObject);
    }
    return localJSONArray;
  }
  
  public InputStream getAjaxInputStream()
  {
    return this.inputStream;
  }
  
  public void setAjaxInputStream(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("utf-8");
      this.inputStream = new ByteArrayInputStream(arrayOfByte);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      this.logger.error("设置ajax input stream 失败! 错误原因 : {}");
    }
  }
  
  public String result()
  {
    String str = getRequest().getParameter("jsoncallback");
    if ((str != null) && (str.length() > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(str);
      localStringBuffer.append("(");
      localStringBuffer.append(this.jsonValidateReturn);
      localStringBuffer.append(")");
      setAjaxInputStream(localStringBuffer.toString());
      return "ajax";
    }
    return "success";
  }
  
  protected class AjaxJSONArrayResponse
  {
    private JSONArray jsonArray = new JSONArray();
    
    public AjaxJSONArrayResponse(Object... paramVarArgs)
    {
      addJSON(paramVarArgs);
    }
    
    public void addJSONList(List<Object> paramList)
    {
      if ((paramList != null) && (paramList.size() > 0))
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Object localObject = localIterator.next();
          this.jsonArray.add(localObject);
        }
      }
    }
    
    public void addJSON(Object... paramVarArgs)
    {
      for (Object localObject : paramVarArgs) {
        this.jsonArray.add(localObject);
      }
    }
    
    public JSONArray toJSONArray()
    {
      return this.jsonArray;
    }
    
    public int size()
    {
      return this.jsonArray.size();
    }
  }
}

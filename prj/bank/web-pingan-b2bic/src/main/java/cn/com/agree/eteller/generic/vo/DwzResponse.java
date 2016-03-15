package cn.com.agree.eteller.generic.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

public class DwzResponse
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String STATUSCODE_OK = "200";
  public static final String STATUSCODE_ERROR = "300";
  public static final String STATUSCODE_TIMEOUT = "301";
  public static final String CALLBACKTYPE_CLOSE_CURRENT = "closeCurrent";
  public static final String CALLBACKTYPE_FORWARD = "forward";
  private String statusCode = "200";
  private String message;
  private String navTabId = "";
  private String rel = "";
  private String callbackType = "closeCurrent";
  private String forwardUrl = "";
  private Boolean alertClose = Boolean.valueOf(true);
  private Map<String, Object> params;
  
  public void successForward(String message)
  {
    successForward(message, (String)ServletActionContext.getRequest().getAttribute("navTabId"));
  }
  
  public void successForward(String message, String navTabId)
  {
    this.message = message;
    this.navTabId = navTabId;
  }
  
  public void errorForward(String message)
  {
    this.message = message;
    this.statusCode = "300";
    this.callbackType = null;
  }
  
  public void exceptionForward(Exception e)
  {
    errorForward(e.getMessage());
  }
  
  public void ajaxSuccessForward(String message, String navTabId)
  {
    successForward(message, navTabId);
    this.callbackType = null;
  }
  
  public void ajaxSuccessForward(String message)
  {
    ajaxSuccessForward(message, (String)ServletActionContext.getRequest().getAttribute("navTabId"));
  }
  
  public void ajaxErrorForward(String message)
  {
    ajaxSuccessForward(message);
    this.statusCode = "300";
  }
  
  public void addParam(String name, Object value)
  {
    if (this.params == null) {
      this.params = new HashMap();
    }
    this.params.put(name, value);
  }
  
  public String getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(String statusCode)
  {
    this.statusCode = statusCode;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public String getNavTabId()
  {
    return this.navTabId;
  }
  
  public void setNavTabId(String navTabId)
  {
    this.navTabId = navTabId;
  }
  
  public String getRel()
  {
    return this.rel;
  }
  
  public void setRel(String rel)
  {
    this.rel = rel;
  }
  
  public String getCallbackType()
  {
    return this.callbackType;
  }
  
  public void setCallbackType(String callbackType)
  {
    this.callbackType = callbackType;
  }
  
  public String getForwardUrl()
  {
    return this.forwardUrl;
  }
  
  public void setForwardUrl(String forwardUrl)
  {
    this.forwardUrl = forwardUrl;
  }
  
  public Boolean getAlertClose()
  {
    return this.alertClose;
  }
  
  public void setAlertClose(Boolean alertClose)
  {
    this.alertClose = alertClose;
  }
  
  public Map<String, Object> getParams()
  {
    return this.params;
  }
  
  public void setParams(Map<String, Object> params)
  {
    this.params = params;
  }
}

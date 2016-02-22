package gnnt.MEBS.packaging.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

public abstract class BaseAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(BaseAction.class);
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  protected List resultList;
  protected Class classType;
  protected Clone obj;
  protected HttpServletRequest request;
  
  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }
  
  public Clone getObj()
  {
    return this.obj;
  }
  
  public void setObj(Clone obj)
  {
    this.obj = obj;
  }
  
  public abstract InService getService();
  
  public List getResultList()
  {
    return this.resultList;
  }
  
  public void setResultList(List resultList)
  {
    this.resultList = resultList;
  }
  
  public void addResultMsg(HttpServletRequest request, int resultValue)
  {
    String resultMsg = (String)this.returnOperationMap.get(Integer.valueOf(resultValue));
    request.setAttribute(ActionConstant.RESULTMSG, resultMsg);
    request.setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(resultValue));
  }
  
  public void addResultSessionMsg(HttpServletRequest request, int resultValue)
  {
    String resultMsg = (String)this.returnOperationMap.get(Integer.valueOf(resultValue));
    request.getSession().setAttribute(ActionConstant.RESULTMSG, resultMsg);
    request.getSession().setAttribute(ActionConstant.RESULTVAULE, Integer.valueOf(resultValue));
  }
  
  public String getReturnValue()
  {
    String type = this.request.getParameter("type") != null ? this.request.getParameter("type") : (String)this.request.getAttribute("type");
    type = (type == null) || ("".equals(type.trim())) ? "page" : type;
    String returnValue = (String)this.returnValueMap.get(type);
    this.logger.debug("returnValue:" + returnValue);
    this.request.removeAttribute("type");
    return returnValue;
  }
  
  public String list()
  {
    this.logger.debug("enter list");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    if (this.request.getAttribute("sortString") != null)
    {
      String orderString = (String)this.request.getAttribute("sortString");
      ThreadStore.put("orderString", orderString);
    }
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    

    QueryConditions qc = getQueryConditions(map);
    this.resultList = getService().getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
  
  protected PageInfo getPageInfo(Map<String, Object> map)
  {
    return (PageInfo)map.get(ActionConstant.PAGEINFO);
  }
  
  protected QueryConditions getQueryConditions(Map<String, Object> map)
  {
    return (QueryConditions)map.get(ActionConstant.QUERYCONDITIONS);
  }
  
  protected void returnBaseMsg(PageInfo pageInfo)
  {
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    this.request.setAttribute(ActionConstant.PAGEINFO, pageInfo);
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        this.obj.setPrimary(id);
        Clone clone = getService().get(this.obj);
        resultValue = getService().delete(this.obj);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String forwardAdd()
  {
    return getReturnValue();
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    int resultValue = getService().add(this.obj);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.logger.debug("enter viewById");
    this.obj = getService().get(this.obj);
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    int resultValue = getService().update(this.obj);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  @PostConstruct
  public void init()
  {
    this.logger.debug("enter init");
    this.logger.debug("this.name:" + getClass().getName());
    if (getService() != null)
    {
      this.classType = getService().getDao().getEntityClass();
      this.logger.debug("class.name:" + this.classType.getName());
      try
      {
        this.obj = ((Clone)this.classType.newInstance());
      }
      catch (InstantiationException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
      this.logger.debug("end if");
    }
  }
  
  public void forwardAttribute() {}
  
  public String getAu()
  {
    return (String)this.request.getSession().getAttribute("LOGINIDS");
  }
}

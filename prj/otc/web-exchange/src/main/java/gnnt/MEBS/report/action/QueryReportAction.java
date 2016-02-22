package gnnt.MEBS.report.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.report.service.QueryReportService;
import gnnt.MEBS.report.service.TradeDateListService;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QueryReportAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(QueryReportAction.class);
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  protected List list;
  protected HttpServletRequest request;
  private QueryReportService queryReportService;
  @Resource(name="bankMap")
  private Map bankMap;
  @Autowired
  @Qualifier("tradeDateListService")
  protected TradeDateListService tradeDateListService;
  
  public void setQueryReportService(QueryReportService queryReportService)
  {
    this.queryReportService = queryReportService;
  }
  
  public QueryReportService getQueryReportService()
  {
    return this.queryReportService;
  }
  
  public Map getBankMap()
  {
    return this.bankMap;
  }
  
  public List getList()
  {
    return this.list;
  }
  
  public void setList(List list)
  {
    this.list = list;
  }
  
  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }
  
  public HttpServletRequest getRequest()
  {
    return this.request;
  }
  
  public String getReportList()
  {
    this.logger.debug("enter getList");
    List listAll = new ArrayList();
    String date = strDate();
    String queryType = this.request.getParameter("queryType");
    if (queryType == null) {
      queryType = "D";
    }
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "clearDate";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    String noQuery = this.request.getParameter("noQuery") != null ? this.request.getParameter("noQuery") : "false";
    if (this.request.getAttribute("orderString") != null)
    {
      String orderString = (String)this.request.getAttribute("orderString");
      ThreadStore.put("orderString", orderString);
    }
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    if (!new Boolean(noQuery).booleanValue())
    {
      this.list = this.queryReportService.getList(qc, pageInfo, queryType);
      listAll = this.queryReportService.getList(qc, null, queryType);
    }
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.request.setAttribute("listAll", listAll);
    setParameterAttributes(this.request);
    this.request.setAttribute("date", date);
    this.request.setAttribute("queryType", queryType);
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
  
  public String getReturnValue()
  {
    String type = this.request.getParameter("type") != null ? this.request.getParameter("type") : (String)this.request.getAttribute("type");
    type = type == null ? "page" : type;
    String returnValue = (String)this.returnValueMap.get(type);
    this.logger.debug("returnValue:" + returnValue);
    return returnValue;
  }
  
  public String strDate()
  {
    List<Map<String, Timestamp>> tradeDateList = this.tradeDateListService.getList(null);
    Timestamp date1 = new Timestamp(new Date().getTime());
    date1 = (Timestamp)((Map)tradeDateList.get(0)).get("a");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(date1);
    return date;
  }
  
  public void setParameterAttributes(HttpServletRequest request)
  {
    List list = (List)request.getAttribute("listAll");
    if ((list != null) && (list.size() > 0))
    {
      Field[] fields = list.get(0).getClass().getDeclaredFields();
      for (Field f : fields) {
        if ((f.getType() == Double.class) || (f.getType() == Integer.class) || 
          (f.getType() == Integer.TYPE))
        {
          double sum = 0.0D;
          for (int i = 0; i < list.size(); i++)
          {
            Object cf = list.get(i);
            f.setAccessible(true);
            try
            {
              if ((f.get(cf) != null) && (!f.get(cf).toString().equals(""))) {
                sum += Double.valueOf(f.get(cf).toString()).doubleValue();
              }
            }
            catch (NumberFormatException e)
            {
              e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
              e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
              e.printStackTrace();
            }
          }
          request.setAttribute(f.getName() + "All", f.getType() == Double.class ? new DecimalFormat("#,##0.00").format(sum) : new DecimalFormat("#0").format(sum));
        }
      }
      request.setAttribute("ifHas", Integer.valueOf(1));
    }
  }
}

package gnnt.MEBS.report.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.report.assemble.AssembleData;
import gnnt.MEBS.report.model.CompleteData;
import gnnt.MEBS.report.service.TradeDateListService;
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

public class BaseReportAction
  extends ActionSupport
  implements ServletRequestAware
{
  private final transient Log logger = LogFactory.getLog(BaseReportAction.class);
  @Resource(name="returnValueMap")
  protected Map<String, String> returnValueMap;
  protected List<CompleteData> completeDataList = new ArrayList();
  protected List<String> tableList = new ArrayList();
  protected AssembleData assembleData;
  protected HttpServletRequest request;
  protected String reportType;
  protected List dataList;
  protected Map<String, Object> primaryMap;
  @Autowired
  @Qualifier("tradeDateListService")
  protected TradeDateListService tradeDateListService;
  
  public void setDataList(List dataList)
  {
    this.dataList = dataList;
  }
  
  public List<CompleteData> getCompleteDataList()
  {
    return this.completeDataList;
  }
  
  public String getReportType()
  {
    return this.reportType;
  }
  
  public void setReportType(String reportType)
  {
    this.reportType = reportType;
  }
  
  public void setAssembleData(AssembleData assembleData)
  {
    this.assembleData = assembleData;
  }
  
  public List<String> getTableList()
  {
    return this.tableList;
  }
  
  public void setTableList(List<String> tableList)
  {
    this.tableList = tableList;
  }
  
  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }
  
  public List dataList()
  {
    return this.dataList;
  }
  
  public void addDataList(List list)
  {
    this.dataList = list;
  }
  
  public Map<String, Object> primaryMap()
  {
    return this.primaryMap;
  }
  
  public void addPrimaryMap(Map<String, Object> primaryMap)
  {
    this.primaryMap = primaryMap;
  }
  
  public String reportQuery()
  {
    String date = strDate();
    this.request.setAttribute("date", date);
    return "success";
  }
  
  protected void finalize() {}
  
  public String getReportData()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    this.completeDataList = this.assembleData.getData(qc, this.tableList);
    
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    return getReturnValue();
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
  
  public Object format(Object num)
  {
    DecimalFormat df = new DecimalFormat("0.00");
    return df.format(num);
  }
  
  public String strDate()
  {
    List<Map<String, Timestamp>> tradeDateList = this.tradeDateListService.getList(null);
    Timestamp date1 = (Timestamp)((Map)tradeDateList.get(0)).get("max(tradeDate)");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    if (date1 == null) {
      date1 = new Timestamp(new Date().getTime());
    }
    String date = df.format(date1);
    return date;
  }
}

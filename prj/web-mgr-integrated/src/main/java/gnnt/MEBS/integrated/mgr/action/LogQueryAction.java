package gnnt.MEBS.integrated.mgr.action;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.LogCatalog;
import gnnt.MEBS.common.mgr.model.OperateLog;
import gnnt.MEBS.common.mgr.model.OperateLogHis;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.service.QueryService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("logQueryAction")
@Scope("request")
public class LogQueryAction
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="com_operatorTypeMap")
  Map<String, String> com_operatorTypeMap;
  private String logMethod;
  private Integer logtype;
  private JSONArray jsonValidateReturn;
  
  public Map<String, String> getCom_operatorTypeMap()
  {
    return this.com_operatorTypeMap;
  }
  
  public void setLogMethod(String paramString)
  {
    this.logMethod = paramString;
  }
  
  public void setLogtype(Integer paramInteger)
  {
    this.logtype = paramInteger;
  }
  
  public String operateLogList()
    throws Exception
  {
    this.logger.debug("enter operateLogList");
    String str1 = this.request.getParameter("type");
    if ((str1 != null) && (str1.equals("H")))
    {
      this.entity = new OperateLogHis();
      str1 = "H";
    }
    else
    {
      this.entity = new OperateLog();
      str1 = "D";
    }
    PageRequest localPageRequest = getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    Map localMap = getParametersStartingWith(this.request, "gnnt_");
    this.logger.debug("logtype: " + this.logtype);
    this.logger.debug("logMethod: " + this.logMethod);
    if (this.logtype != null) {
      localQueryConditions.addCondition("logtype", "=", this.logtype);
    }
    if ((localMap != null) && (localMap.size() == 0))
    {
      String str2 = Tools.fmtDate(new Date());
      String str3 = Tools.combineDateTime(str2);
      String str4 = Tools.combineDateTime(str2, 1);
      localQueryConditions.addCondition("primary.operateTime", ">=", Tools.strToDateTime(str3));
      localQueryConditions.addCondition("primary.operateTime", "<=", Tools.strToDateTime(str4));
    }
    localPageRequest.setSortColumns("order by operateTime desc");
    listByLimit(localPageRequest);
    this.request.setAttribute("type", str1);
    this.request.setAttribute("logMethod", this.logMethod);
    this.request.setAttribute("logtype", this.logtype);
    return "success";
  }
  
  public String frontLogList()
    throws Exception
  {
    this.logger.debug("enter frontLogList");
    String str1 = this.request.getParameter("type");
    if ((str1 != null) && (str1.equals("H")))
    {
      this.entity = new OperateLogHis();
      str1 = "H";
    }
    else
    {
      this.entity = new OperateLog();
      str1 = "D";
    }
    PageRequest localPageRequest = getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    Map localMap = getParametersStartingWith(this.request, "gnnt_");
    if ((localMap != null) && (localMap.size() == 0))
    {
      String str2 = Tools.fmtDate(new Date());
      String str3 = Tools.combineDateTime(str2);
      String str4 = Tools.combineDateTime(str2, 1);
      localQueryConditions.addCondition("primary.operateTime", ">=", Tools.strToDateTime(str3));
      localQueryConditions.addCondition("primary.operateTime", "<=", Tools.strToDateTime(str4));
    }
    localPageRequest.setSortColumns("order by operateTime desc");
    localQueryConditions.addCondition("primary.logCatalog.catalogID", "=", Integer.valueOf(3201));
    listByLimit(localPageRequest);
    this.request.setAttribute("type", str1);
    return "success";
  }
  
  public String mgrLogList()
    throws Exception
  {
    this.logger.debug("enter mgrLogList");
    String str1 = this.request.getParameter("type");
    if ((str1 != null) && (str1.equals("H")))
    {
      this.entity = new OperateLogHis();
      str1 = "H";
    }
    else
    {
      this.entity = new OperateLog();
      str1 = "D";
    }
    PageRequest localPageRequest = getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    Map localMap = getParametersStartingWith(this.request, "gnnt_");
    if ((localMap != null) && (localMap.size() == 0))
    {
      String str2 = Tools.fmtDate(new Date());
      String str3 = Tools.combineDateTime(str2);
      String str4 = Tools.combineDateTime(str2, 1);
      localQueryConditions.addCondition("primary.operateTime", ">=", Tools.strToDateTime(str3));
      localQueryConditions.addCondition("primary.operateTime", "<=", Tools.strToDateTime(str4));
    }
    localPageRequest.setSortColumns("order by operateTime desc");
    localQueryConditions.addCondition("primary.logCatalog.catalogID", "=", Integer.valueOf(3203));
    listByLimit(localPageRequest);
    this.request.setAttribute("type", str1);
    return "success";
  }
  
  public JSONArray getJsonValidateReturn()
  {
    return this.jsonValidateReturn;
  }
  
  public String getLogMoudleList()
  {
    this.logger.debug("enter getLogMoudleList");
    this.jsonValidateReturn = new JSONArray();
    int i = Tools.strToInt(this.request.getParameter("logType"));
    PageRequest localPageRequest = null;
    if (i == 2) {
      localPageRequest = new PageRequest(1, 100, " and primary.moduleId not in (10,11,12,32,27,98,99)", " order by moduleId");
    } else if (i == 3) {
      localPageRequest = new PageRequest(1, 100, " and primary.moduleId not in (10,11,12,32,98,99)", " order by moduleId");
    } else {
      localPageRequest = new PageRequest(1, 100, " and primary.moduleId not in (12)", " order by moduleId");
    }
    Page localPage = getQueryService().getPage(localPageRequest, new TradeModule());
    if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0))
    {
      Iterator localIterator = localPage.getResult().iterator();
      while (localIterator.hasNext())
      {
        StandardModel localStandardModel = (StandardModel)localIterator.next();
        if (localStandardModel != null) {
          this.jsonValidateReturn.add(localStandardModel);
        }
      }
    }
    return "success";
  }
  
  public String getLogCateLogByMoudleID()
  {
    this.logger.debug("enter getLogCateLogByMoudleID");
    int i = Tools.strToInt(this.request.getParameter("logType"));
    int j = Tools.strToInt(this.request.getParameter("moudleId"), 64536);
    this.jsonValidateReturn = new JSONArray();
    if (j > 0)
    {
      PageRequest localPageRequest = null;
      if (i == 2) {
        localPageRequest = new PageRequest(1, 100, " and primary.tradeModule.moduleId=" + j + " and  primary.catalogID not in (1320) and primary.catalogName not like '%统计查询%' and  primary.catalogName not like '%参数设置%' ", " order by catalogID ");
      } else if (i == 3) {
        localPageRequest = new PageRequest(1, 100, " and primary.tradeModule.moduleId=" + j + " and primary.catalogName like '%核心%'", " order by catalogID ");
      } else {
        localPageRequest = new PageRequest(1, 100, " and primary.tradeModule.moduleId=" + j + " ", " order by catalogID ");
      }
      Page localPage = getQueryService().getPage(localPageRequest, new LogCatalog());
      if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0))
      {
        Iterator localIterator = localPage.getResult().iterator();
        while (localIterator.hasNext())
        {
          StandardModel localStandardModel = (StandardModel)localIterator.next();
          if (localStandardModel != null)
          {
            JSONArray localJSONArray = new JSONArray();
            localJSONArray.add(((LogCatalog)localStandardModel).getCatalogName());
            localJSONArray.add(((LogCatalog)localStandardModel).getCatalogID());
            this.jsonValidateReturn.add(localJSONArray);
          }
        }
      }
    }
    this.logger.debug(this.jsonValidateReturn.toString());
    return "success";
  }
}

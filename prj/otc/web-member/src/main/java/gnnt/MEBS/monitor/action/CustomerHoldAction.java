package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.service.MonitorIntervalService;
import gnnt.MEBS.monitor.service.VCustomerhodsumService;
import gnnt.MEBS.monitor.service.VCustomerholdService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerHoldAction")
@Scope("request")
public class CustomerHoldAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldAction.class);
  @Autowired
  @Qualifier("vCustomerholdService")
  protected VCustomerholdService vCustomerholdService;
  @Autowired
  @Qualifier("vCustomerhodsumService")
  protected VCustomerhodsumService vCustomerhodsumService;
  @Autowired
  @Qualifier("mIntervalService")
  protected MonitorIntervalService mIntervalService;
  
  public InService getService()
  {
    return this.vCustomerholdService;
  }
  
  public String list()
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    int interval = this.mIntervalService.getMInterval();
    request.setAttribute("mInterval", Integer.valueOf(interval));
    return "list";
  }
  
  public String sum()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      prd = URLDecoder.decode(prd, "UTF-8");
      dir = URLDecoder.decode(dir, "UTF-8");
      
      List data = this.vCustomerhodsumService.getListAll(mid, cstid, prd, dir);
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONArray.fromObject(data).toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String hold()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String page = request.getParameter("page");
      String pagesize = request.getParameter("pagesize");
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String orderField = request.getParameter("order");
      String desc = request.getParameter("desc");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      prd = URLDecoder.decode(prd, "UTF-8");
      dir = URLDecoder.decode(dir, "UTF-8");
      
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      

      Rights rts = new Rights();
      Rights.addsearch(qc, "organizationno", mid);
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("customerno", " like ", "%" + cstid + "%");
      }
      if ((prd != "") && (prd != null) && (prd.trim().length() > 0)) {
        qc.addCondition("commodityname", " = ", prd);
      }
      if ((dir != "") && (dir != null) && (dir.trim().length() > 0)) {
        qc.addCondition("bsFlag", " = ", dir);
      }
      qc = Rights.addqc(qc);
      qc = Rights.addsearchorg(qc);
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vCustomerholdService.getList(qc, pg);
      
      Map map = new HashMap();
      map.put("pageInfo", pg);
      map.put("data", data);
      map.put("mInterval", Integer.valueOf(this.mIntervalService.getMInterval()));
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONObject.fromObject(map).toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
}

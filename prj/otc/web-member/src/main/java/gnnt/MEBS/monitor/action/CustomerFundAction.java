package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.model.VCustomerfundTip;
import gnnt.MEBS.monitor.service.MonitorIntervalService;
import gnnt.MEBS.monitor.service.VCustomerfundService;
import gnnt.MEBS.monitor.service.VCustomerfundServiceSimple;
import gnnt.MEBS.monitor.service.VCustomerfundTipService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerFundAction")
@Scope("request")
public class CustomerFundAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerFundAction.class);
  @Autowired
  @Qualifier("vCustomerfundService")
  protected VCustomerfundService vCustomerfundService;
  @Autowired
  @Qualifier("mIntervalService")
  protected MonitorIntervalService mIntervalService;
  @Autowired
  @Qualifier("vCustomerfundServiceSimple")
  protected VCustomerfundServiceSimple vCustomerfundServiceSimple;
  @Autowired
  @Qualifier("vCustomerfundTipService")
  protected VCustomerfundTipService vCustomerfundTipService;
  
  public InService getService()
  {
    return this.vCustomerfundService;
  }
  
  public String list()
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    int interval = this.mIntervalService.getMInterval();
    request.setAttribute("mInterval", Integer.valueOf(interval));
    return "list";
  }
  
  public String data()
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
      
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      
      Rights rts = new Rights();
      Rights.addsearch(qc, "organizationno", mid);
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("customerno", " like ", "%" + cstid + "%");
      }
      qc = Rights.addqc(qc);
      qc = Rights.addsearchorg(qc);
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vCustomerfundService.getList(qc, pg);
      
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
  
  public String dataSimple()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String page = request.getParameter("page");
      String pagesize = request.getParameter("pagesize");
      String cstid = request.getParameter("cstid");
      String orderField = request.getParameter("order");
      String desc = request.getParameter("desc");
      
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      
      String memid = request.getSession().getAttribute("ORGANIZATIONID").toString();
      if ((memid == null) || (memid.trim().length() <= 0)) {
        memid = request.getParameter("mid");
      }
      if ((memid != null) && (memid.trim().length() > 0))
      {
        String orzs = "";
        String[] ors = memid.split(",");
        for (String or : ors) {
          if ((or != null) && (or.trim().length() > 0))
          {
            if ((orzs != null) && (orzs.trim().length() > 0)) {
              orzs = orzs + " or ";
            }
            orzs = orzs + " view.organizationNo='" + or.trim() + "'";
          }
        }
        if ((orzs == null) || (orzs.trim().length() <= 0)) {
          orzs = " 1=1 ";
        }
        String tmpstr = "(customerno in (select view.traderId from gnnt.MEBS.common.model.CustomerAdminRelateOrganization  view where  " + orzs + " ))";
        qc.addCondition("customerno", "filter", tmpstr);
      }
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("customerno", " like ", "%" + cstid + "%");
      }
      qc = Rights.addqc(qc);
      
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vCustomerfundServiceSimple.getList(qc, pg);
      
      Map map = new HashMap();
      map.put("pageInfo", pg);
      map.put("data", data);
      
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
  
  public String dataInfo()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    
    String cstid = request.getParameter("cstid");
    Map<String, Object> map = new HashMap();
    
    VCustomerfundTip tip = new VCustomerfundTip();
    tip.setCustomerno(cstid);
    tip = (VCustomerfundTip)this.vCustomerfundTipService.get(tip);
    if (tip != null)
    {
      map.put("customerno", tip.getCustomerno());
      map.put("customername", tip.getCustomername());
      map.put("lastbalance", fmtDouble(tip.getLastbalance().doubleValue()));
      map.put("netbalance", fmtDouble(tip.getNetbalance().doubleValue()));
      map.put("floatingloss", fmtDouble(tip.getFloatingloss().doubleValue()));
      map.put("frozenmargin", fmtDouble(tip.getFrozenmargin().doubleValue()));
      map.put("margin", fmtDouble(tip.getMargin().doubleValue()));
      map.put("livemargin", fmtDouble(tip.getLivemargin().doubleValue()));
      map.put("riskvalue", fmtDouble(tip.getRiskvalue().doubleValue()));
      map.put("membername", tip.getMembername());
      map.put("organizationname", tip.getOrganizationname());
      map.put("managerno", tip.getManagerno());
    }
    String json = JSONObject.fromObject(map).toString();
    try
    {
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(json);
      out.flush();
    }
    catch (Exception localException) {}
    return null;
  }
  
  private String fmtDouble(double num)
  {
    String result = "0.00";
    try
    {
      DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
      nf.applyPattern("###0.00");
      result = nf.format(num);
    }
    catch (Exception localException) {}
    return result;
  }
}

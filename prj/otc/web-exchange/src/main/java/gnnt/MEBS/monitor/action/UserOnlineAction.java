package gnnt.MEBS.monitor.action;

import com.opensymphony.xwork2.ActionSupport;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.service.VOnlinemonitorService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.io.PrintWriter;
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

@Component("userOnlineAction")
@Scope("request")
public class UserOnlineAction
  extends ActionSupport
{
  private final transient Log logger = LogFactory.getLog(UserOnlineAction.class);
  @Autowired
  @Qualifier("vOnlinemonitorService")
  protected VOnlinemonitorService vOnlinemonitorService;
  
  public String list()
  {
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
      Rights.addsearch(qc, "memberno", mid);
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("traderid", " like ", "%" + cstid + "%");
      }
      qc = Rights.addqc(qc);
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "20"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vOnlinemonitorService.getList(qc, pg);
      
      Map map = new HashMap();
      map.put("pageInfo", pg);
      map.put("data", data);
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONObject.fromObject(map).toString());
      out.flush();
    }
    catch (Exception ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String kick()
  {
    try
    {
      HttpServletResponse response = ServletActionContext.getResponse();
      HttpServletRequest request = ServletActionContext.getRequest();
      
      String id = request.getParameter("mid");
      AgencyRMIBean remObject = new AgencyRMIBean(null);
      remObject.kickOnlineTrader(id);
      

      String userId = request.getSession().getAttribute("CURRENUSERID").toString();
      String operatorContent = String.format("强制用户 %s 下线", new Object[] { id });
      this.vOnlinemonitorService.logkick(userId, "3002", request.getRemoteAddr(), operatorContent, 1);
    }
    catch (Exception ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
}

package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.service.VCustomerfundtryService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerFundtryAction")
@Scope("request")
public class CustomerFundtryAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerFundtryAction.class);
  @Autowired
  @Qualifier("vCustomerfundtryService")
  protected VCustomerfundtryService vCustomerfundtryService;
  
  public InService getService()
  {
    return this.vCustomerfundtryService;
  }
  
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
      String mid = request.getParameter("memberno");
      String cstid = request.getParameter("customerno");
      String orderField = request.getParameter("order");
      String desc = request.getParameter("desc");
      String com = request.getParameter("com");
      String flag = request.getParameter("flag");
      
      String icom = "";
      String iprice = "";
      if (com == null) {
        com = "0";
      }
      if (com == "") {
        com = "0";
      }
      int comcount = Integer.parseInt(com);
      int i = 0;
      if ((comcount > 0) && ("1".equals(flag))) {
        for (i = 1; i <= comcount; i++)
        {
          icom = request.getParameter("com" + String.valueOf(i));
          iprice = request.getParameter("iprice" + String.valueOf(i));
          this.vCustomerfundtryService.updateprice(icom, Double.parseDouble(iprice));
        }
      }
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      if ((mid != null) && (mid.trim().length() > 0) && (!mid.equals("all"))) {
        qc.addCondition("memberno", " like ", "%" + mid + "%");
      }
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("customerno", " like ", "%" + cstid + "%");
      }
      new Rights();qc = Rights.addqc(qc);
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vCustomerfundtryService.getList(qc, pg);
      
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
}

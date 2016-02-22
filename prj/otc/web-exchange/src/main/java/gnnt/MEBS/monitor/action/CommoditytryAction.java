package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.service.VCommoditytryService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commoditytryAction")
@Scope("request")
public class CommoditytryAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommoditytryAction.class);
  @Autowired
  @Qualifier("vCommoditytryService")
  protected VCommoditytryService vCommoditytryService;
  
  public InService getService()
  {
    return this.vCommoditytryService;
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
      String page = "1";
      String pagesize = "100";
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String orderField = "commodityid";
      String desc = "1";
      
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      

      new Rights();qc = Rights.addqc(qc);
      
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, true);
      pg.setPageSize(40);
      pg.setPageNo(1);
      
      List data = this.vCommoditytryService.getList(qc, pg);
      




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
}

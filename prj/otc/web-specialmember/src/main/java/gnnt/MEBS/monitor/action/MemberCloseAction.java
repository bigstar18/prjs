package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.service.VMembercloseService;
import gnnt.MEBS.monitor.service.VMemberclosesumService;
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

@Component("memberCloseAction")
@Scope("request")
public class MemberCloseAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberCloseAction.class);
  @Autowired
  @Qualifier("vMembercloseService")
  protected VMembercloseService vMembercloseService;
  @Autowired
  @Qualifier("vMemberclosesumService")
  protected VMemberclosesumService vMemberclosesumService;
  
  public InService getService()
  {
    return this.vMembercloseService;
  }
  
  public String list()
  {
    return "list";
  }
  
  public String sum()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String mid = request.getParameter("mid");
      String pid = request.getParameter("pid");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      if (prd != null) {
        prd = URLDecoder.decode(prd, "UTF-8");
      }
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      List data = this.vMemberclosesumService.getListAll(pid, mid, prd, dir);
      

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
  
  public String close()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String page = request.getParameter("page");
      String pagesize = request.getParameter("pagesize");
      String mid = request.getParameter("mid");
      String pid = request.getParameter("pid");
      String orderField = request.getParameter("order");
      String desc = request.getParameter("desc");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      if (prd != null) {
        prd = URLDecoder.decode(prd, "UTF-8");
      }
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      
      Rights rts = new Rights();
      Rights.addsearch(qc, "memberno", mid);
      Rights.addsearch(qc, "smemberno", pid);
      qc = Rights.addqc(qc);
      if ((prd != "") && (prd != null) && (prd.trim().length() > 0)) {
        qc.addCondition("commodityname", " = ", prd);
      }
      if ((dir != "") && (dir != null) && (dir.trim().length() > 0)) {
        qc.addCondition("close_bs_flag", " = ", dir);
      }
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vMembercloseService.getList(qc, pg);
      
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

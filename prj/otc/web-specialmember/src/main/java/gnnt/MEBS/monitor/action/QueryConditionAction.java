package gnnt.MEBS.monitor.action;

import gnnt.MEBS.monitor.model.QMemberVO;
import gnnt.MEBS.monitor.model.QOrganizationVO;
import gnnt.MEBS.monitor.service.QMemberVOService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("queryConditionAction")
@Scope("request")
public class QueryConditionAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(QueryConditionAction.class);
  @Autowired
  @Qualifier("qMemberVOService")
  protected QMemberVOService qMemberVOService;
  
  public InService getService()
  {
    return this.qMemberVOService;
  }
  
  public String list()
  {
    return "list";
  }
  
  public String member()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String memid = request.getSession().getAttribute("REGISTERID").toString();
      List<QMemberVO> data = this.qMemberVOService.getMemberList("s_memberno='" + memid + "'");
      
      StringBuilder sb = new StringBuilder();
      sb.append("<?xml version='1.0' encoding='utf-8'?><result><object state='unchecked' label='所有' value=''>");
      for (QMemberVO mem : data) {
        sb.append(String.format("<object state='unchecked' label='(%s) %s' value='%s'/>", new Object[] { mem.getMemberno(), mem.getName(), mem.getMemberno() }));
      }
      sb.append("</object></result>");
      
      response.setContentType("text/xml; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(sb.toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String agency()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String memid = "220";
      
      List<QOrganizationVO> data = this.qMemberVOService.getAgencyList("memberno='" + memid + "'");
      
      QOrganizationVO vo = new QOrganizationVO();
      vo.setMemberno(memid);
      vo.setName(memid);
      data.add(0, vo);
      
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
  
  public String specialmember()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String memid = request.getSession().getAttribute("REGISTERID").toString();
      
      List<QMemberVO> data = this.qMemberVOService.getSpecialMemberList("m.memberno='" + memid + "'");
      
      StringBuilder sb = new StringBuilder();
      sb.append("<?xml version='1.0' encoding='utf-8'?><result><object state='unchecked' label='所有' value=''>");
      for (QMemberVO mem : data) {
        sb.append(String.format("<object state='unchecked' label='%s' value='%s'/>", new Object[] { mem.getName(), mem.getMemberno() }));
      }
      sb.append("</object></result>");
      
      response.setContentType("text/xml; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(sb.toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String product()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      List<QMemberVO> data = this.qMemberVOService.getProductList();
      
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

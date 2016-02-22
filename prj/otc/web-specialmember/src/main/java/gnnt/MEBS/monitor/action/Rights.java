package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class Rights
  extends BaseAction
{
  public InService getService()
  {
    return null;
  }
  
  public static QueryConditions addsearch(QueryConditions qc, String pfield, String searchstr)
  {
    String tmpall = searchstr;
    String tmpstr = "";
    if ((tmpall != null) && (tmpall != ""))
    {
      while (tmpall.indexOf(",") != -1)
      {
        tmpstr = tmpstr + pfield + "='" + tmpall.substring(0, tmpall.indexOf(",")) + "' or ";
        tmpall = tmpall.substring(tmpall.indexOf(",") + 1);
      }
      tmpstr = "(" + tmpstr + pfield + "='" + tmpall + "')";
      
      qc.addCondition(pfield, "filter", tmpstr);
    }
    return qc;
  }
  
  public static String addsearchsql(String pfield, String searchstr)
  {
    String tmpall = searchstr;
    String tmpstr = "";
    if ((tmpall != null) && (tmpall != ""))
    {
      while (tmpall.indexOf(",") != -1)
      {
        tmpstr = tmpstr + pfield + "='" + tmpall.substring(0, tmpall.indexOf(",")) + "' or ";
        tmpall = tmpall.substring(tmpall.indexOf(",") + 1);
      }
      tmpstr = "(" + tmpstr + pfield + "='" + tmpall + "')";
    }
    else
    {
      tmpstr = " 1=1 ";
    }
    return tmpstr;
  }
  
  public static QueryConditions addqc(QueryConditions qc)
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    String memid = request.getSession().getAttribute("REGISTERID").toString();
    qc.addCondition("smemberno", " = ", memid);
    return qc;
  }
  
  public static QueryConditions addmemberqc(QueryConditions qc)
  {
    qc.addCondition("tradercatalog", " = ", "会员");
    return qc;
  }
  
  public static String addsql()
  {
    String tmpsql = "";
    
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    String memid = request.getSession().getAttribute("REGISTERID").toString();
    tmpsql = " smemberno='" + memid + "'";
    

    return tmpsql;
  }
}

package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;

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
    return qc;
  }
  
  public static String addsql()
  {
    String tmpsql = "";
    tmpsql = " 1=1 ";
    
    return tmpsql;
  }
}

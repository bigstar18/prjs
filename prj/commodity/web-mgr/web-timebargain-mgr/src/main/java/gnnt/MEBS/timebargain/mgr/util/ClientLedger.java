package gnnt.MEBS.timebargain.mgr.util;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientLedger
{
  public static Map queryClientLedger(String startDate, String endDate, String startFirm, String endFirm, String type)
  {
    Map all = new HashMap();
    QueryConditions qc = new QueryConditions();
    if (startDate != null)
    {
      qc.addCondition("b_date", ">=", Date.valueOf(startDate));
    }
    if (endDate != null)
    {
      qc.addCondition("b_date", "<=", Date.valueOf(endDate));
    }
    if (startFirm != null)
    {
      qc.addCondition("firmId", ">=", startFirm);
    }
    if (endFirm != null)
    {
      qc.addCondition("firmId", "<=", endFirm);
    }

    String fiter = "";
    if ((type != null) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "')";
    }
    ViewService viewService = (ViewService)SysData.getBean("f_viewService");
    all = viewService.queryClientLedgerOutside(qc, null, fiter);
    return all;
  }

  public static Map queryClientLedgerTotal(String startDate, String endDate, String startFirm, String endFirm, String type, String categoryId, String brokerId)
  {
    Map all = new HashMap();
    QueryConditions qc = new QueryConditions();
    if (startDate != null)
    {
      qc.addCondition("b_date", ">=", Date.valueOf(startDate));
    }
    if (endDate != null)
    {
      qc.addCondition("b_date", "<=", Date.valueOf(endDate));
    }
    if (startFirm != null)
    {
      qc.addCondition("firmId", ">=", startFirm);
    }
    if (endFirm != null)
    {
      qc.addCondition("firmId", "<=", endFirm);
    }
    String fiter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "') ";
    }
    else if ("1".equals(type))
    {
      fiter = " ModuleID='11' ";
    }
    else
    {
      fiter = " 1=1 ";
    }
    ViewService viewService = (ViewService)SysData.getBean("f_viewService");
    all = viewService.queryClientLedgerSumOutside2(qc, null, fiter, categoryId, brokerId);
    return all;
  }

  public static Map queryClientLedger(QueryConditions conditions, PageInfo pageInfo, String type)
  {
    Map all = new HashMap();
    String fiter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "')";
    }
    else if ("1".equals(type))
    {
      fiter = " ModuleID='11'";
    }
    else
    {
      fiter = " 1=1 ";
    }
    ViewService viewService = (ViewService)SysData.getBean("f_viewService");
    all = viewService.queryClientLedgerOutside(conditions, pageInfo, fiter);
    return all;
  }

  public static Map queryClientLedgerTotal(QueryConditions conditions, PageInfo pageInfo, String type)
  {
    Map all = new HashMap();
    String fiter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "')";
    }
    else if ("1".equals(type))
    {
      fiter = " ModuleID='11'";
    }
    else
    {
      fiter = " 1=1 ";
    }
    ViewService viewService = (ViewService)SysData.getBean("f_viewService");
    all = viewService.queryClientLedgerOutside(conditions, pageInfo, fiter);
    return all;
  }

  public static Map queryClientLedgerTotal(QueryConditions conditions, PageInfo pageInfo, String type, String filter)
  {
    Map all = new HashMap();
    String fiter = "";
    if ((type != null) && (!"0".equals(type)) && (!"1".equals(type)))
    {
      fiter = " ModuleID in ('11','" + type + "')";
    }
    else if ("1".equals(type))
    {
      fiter = " ModuleID='11'";
    }
    else
    {
      fiter = " 1=1 ";
    }
    if (filter != null)
      fiter = fiter + filter;
    ViewService viewService = (ViewService)SysData.getBean("f_viewService");
    all = viewService.queryClientLedgerSumOutside(conditions, pageInfo, fiter);
    return all;
  }

  public static List queryFiledMap(String type) {
    ViewService viewService = (ViewService)SysData.getBean("f_viewService");
    List list = viewService.queryFiledMap(type);
    return list;
  }
}
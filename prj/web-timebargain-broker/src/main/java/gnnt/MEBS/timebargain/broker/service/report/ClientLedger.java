package gnnt.MEBS.timebargain.broker.service.report;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnnt.MEBS.timebargain.broker.dao.report.PageInfo;
import gnnt.MEBS.timebargain.broker.dao.report.SysData;
import gnnt.MEBS.timebargain.broker.util.QueryConditions;

public class ClientLedger {
	public static Map queryClientLedger(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
		Map localObject = new HashMap();
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null)
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		if (paramString2 != null)
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		if (paramString3 != null)
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		if (paramString4 != null)
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		String str = "";
		if ((paramString5 != null) && (!"1".equals(paramString5)))
			str = " ModuleID in ('11','" + paramString5 + "')";
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		localObject = localViewService.queryClientLedgerOutside(localQueryConditions, null, str);
		return localObject;
	}

	public static Map queryClientLedgerTotal(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5,
			String paramString6) {
		Map localObject = new HashMap();
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null)
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		if (paramString2 != null)
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		if (paramString3 != null)
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		if (paramString4 != null)
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		if (paramString5 != null)
			localQueryConditions.addCondition("firmId", "in", paramString5);
		String str = "";
		if ((paramString6 != null) && (!"0".equals(paramString6)) && (!"1".equals(paramString6)))
			str = " ModuleID in ('11','" + paramString6 + "') ";
		else if ("1".equals(paramString6))
			str = " ModuleID='11' ";
		else
			str = " 1=1 ";
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		localObject = localViewService.queryClientLedgerSumOutside(localQueryConditions, null, str);
		return localObject;
	}

	public static Map queryClientLedger(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		Map localObject = new HashMap();
		String str = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString)))
			str = " ModuleID in ('11','" + paramString + "')";
		else if ("1".equals(paramString))
			str = " ModuleID='11'";
		else
			str = " 1=1 ";
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		localObject = localViewService.queryClientLedgerOutside(paramQueryConditions, paramPageInfo, str);
		return localObject;
	}

	public static Map queryClientLedgerTotal(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		Map localObject = new HashMap();
		String str = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString)))
			str = " ModuleID in ('11','" + paramString + "')";
		else if ("1".equals(paramString))
			str = " ModuleID='11'";
		else
			str = " 1=1 ";
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		localObject = localViewService.queryClientLedgerSumOutside(paramQueryConditions, paramPageInfo, str);
		return localObject;
	}

	public static Map queryClientLedgerTotal(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString1, String paramString2) {
		Map localObject = new HashMap();
		String str = "";
		if ((paramString1 != null) && (!"0".equals(paramString1)) && (!"1".equals(paramString1)))
			str = " ModuleID in ('11','" + paramString1 + "')";
		else if ("1".equals(paramString1))
			str = " ModuleID='11'";
		else
			str = " 1=1 ";
		if (paramString2 != null)
			str = str + paramString2;
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		localObject = localViewService.queryClientLedgerSumOutside(paramQueryConditions, paramPageInfo, str);
		return localObject;
	}

	public static List queryFiledMap(String paramString) {
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		List localList = localViewService.queryFiledMap(paramString);
		return localList;
	}
}
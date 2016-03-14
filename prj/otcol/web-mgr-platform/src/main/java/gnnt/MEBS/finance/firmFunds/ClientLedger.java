package gnnt.MEBS.finance.firmFunds;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.service.ViewService;
import gnnt.MEBS.finance.util.SysData;

public class ClientLedger {
	public static Map queryClientLedger(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
		Map localObject = new HashMap();
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null) {
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		}
		if (paramString2 != null) {
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		}
		if ((paramString3 != null) && (!"null".equals(paramString3))) {
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		}
		if ((paramString4 != null) && (!"null".equals(paramString4))) {
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		}
		String str = "";
		if ((paramString5 != null) && (!"1".equals(paramString5))) {
			str = " ModuleID in ('1','" + paramString5 + "')";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		localObject = localViewService.queryClientLedgerOutside(localQueryConditions, null, str);
		return localObject;
	}

	public static Map queryClientLedgerTotal(String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5) {
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null) {
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		}
		if (paramString2 != null) {
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		}
		if ((paramString3 != null) && (!"null".equals(paramString3))) {
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		}
		if ((paramString4 != null) && (!"null".equals(paramString4))) {
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		}
		String str = "";
		if ((paramString5 != null) && (!"0".equals(paramString5)) && (!"1".equals(paramString5))) {
			str = " ModuleID in ('1','" + paramString5 + "') ";
		} else if ("1".equals(paramString5)) {
			str = " ModuleID='1' ";
		} else {
			str = " 1=1 ";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		Map localObject = localViewService.queryClientLedgerSumOutside(localQueryConditions, null, str);
		return localObject;
	}

	public static Map queryClientLedgerTotalCateGory(String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6, String paramString7) {
		Object localObject = new HashMap();
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null) {
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		}
		if (paramString2 != null) {
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		}
		if ((paramString3 != null) && (!"null".equals(paramString3))) {
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		}
		if ((paramString4 != null) && (!"null".equals(paramString4))) {
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		}
		String str = "";
		if ((paramString5 != null) && (!"0".equals(paramString5)) && (!"1".equals(paramString5))) {
			str = " ModuleID in ('1','" + paramString5 + "') ";
		} else if ("1".equals(paramString5)) {
			str = " ModuleID='1' ";
		} else {
			str = " 1=1 ";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		return localViewService.queryClientLedgerSumOutside2(localQueryConditions, null, str, paramString6, paramString7);

	}

	public static Map queryClientLedgerTotal2(String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5) {
		Object localObject = new HashMap();
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null) {
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		}
		if (paramString2 != null) {
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		}
		if ((paramString3 != null) && (!paramString3.equals("null"))) {
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		}
		if ((paramString4 != null) && (!paramString4.equals("null"))) {
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		}
		String str = "";
		if ((paramString5 != null) && (!"0".equals(paramString5)) && (!"1".equals(paramString5))) {
			str = " ModuleID in ('1','" + paramString5 + "') ";
		} else if ("1".equals(paramString5)) {
			str = " ModuleID='1' ";
		} else {
			str = " 1=1 ";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		return localViewService.queryClientLedgerSumOutside(localQueryConditions, null, str);

	}

	public static Map queryClientLedgerTotals(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5,
			String paramString6, String paramString7) {
		Object localObject = new HashMap();
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramString1 != null) {
			localQueryConditions.addCondition("b_date", ">=", Date.valueOf(paramString1));
		}
		if (paramString2 != null) {
			localQueryConditions.addCondition("b_date", "<=", Date.valueOf(paramString2));
		}
		if ((paramString3 != null) && (!"null".equals(paramString3))) {
			localQueryConditions.addCondition("firmId", ">=", paramString3);
		}
		if ((paramString4 != null) && (!"null".equals(paramString4))) {
			localQueryConditions.addCondition("firmId", "<=", paramString4);
		}
		String str = "";
		if ((paramString5 != null) && (!"0".equals(paramString5)) && (!"1".equals(paramString5))) {
			str = " ModuleID in ('1','" + paramString5 + "') ";
		} else if ("1".equals(paramString5)) {
			str = " ModuleID='1' ";
		} else {
			str = " 1=1 ";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		return localViewService.queryClientLedgerOutside2(localQueryConditions, null, str, paramString6, paramString7);

	}

	public static Map queryClientLedger(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		Object localObject = new HashMap();
		String str = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString))) {
			str = " ModuleID in ('1','" + paramString + "')";
		} else if ("1".equals(paramString)) {
			str = " ModuleID='1'";
		} else {
			str = " 1=1 ";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		return localViewService.queryClientLedgerOutside(paramQueryConditions, paramPageInfo, str);
	}

	public static Map queryClientLedgerTotal(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		Object localObject = new HashMap();
		String str = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString))) {
			str = " ModuleID in ('1','" + paramString + "')";
		} else if ("1".equals(paramString)) {
			str = " ModuleID='1'";
		} else {
			str = " 1=1 ";
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		return localViewService.queryClientLedgerSumOutside(paramQueryConditions, paramPageInfo, str);
	}

	public static Map queryClientLedgerTotal(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString1, String paramString2) {
		Object localObject = new HashMap();
		String str = "";
		if ((paramString1 != null) && (!"0".equals(paramString1)) && (!"1".equals(paramString1))) {
			str = " ModuleID in ('1','" + paramString1 + "')";
		} else if ("1".equals(paramString1)) {
			str = " ModuleID='1'";
		} else {
			str = " 1=1 ";
		}
		if (paramString2 != null) {
			str = str + paramString2;
		}
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		return localViewService.queryClientLedgerSumOutside(paramQueryConditions, paramPageInfo, str);

	}

	public static List queryFiledMap(String paramString) {
		ViewService localViewService = (ViewService) SysData.getBean("f_viewService");
		List localList = localViewService.queryFiledMap(paramString);
		return localList;
	}
}

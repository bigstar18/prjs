package gnnt.MEBS.timebargain.broker.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.timebargain.broker.dao.report.PageInfo;
import gnnt.MEBS.timebargain.broker.dao.report.SysData;
import gnnt.MEBS.timebargain.broker.dao.report.ViewDao;
import gnnt.MEBS.timebargain.broker.util.QueryConditions;

@Service("f_viewService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ViewService {
	private static final transient Log logger = LogFactory.getLog(ViewService.class);
	private ViewDao viewDao;

	public ViewDao getViewDao() {
		return this.viewDao;
	}

	public void setViewDao(ViewDao paramViewDao) {
		this.viewDao = paramViewDao;
	}

	public List getVoucherBase(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select * from f_Voucher where status='accounted'";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		return this.viewDao.queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public List queryAccountBook(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select t.*,(select name from f_account where code=t.debitcode) debitname,(select name from f_account where code=t.creditcode) creditname from f_AccountBook t";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return this.viewDao.queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public BigDecimal getBeginBalance(String paramString, Date paramDate, boolean paramBoolean) {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("accountCode", "=", paramString);
		localQueryConditions.addCondition("b_date", "=", paramDate, "date");
		List localList = queryDailyBalance(localQueryConditions, null);
		if ((localList != null) && (localList.size() > 0))
			return (BigDecimal) ((Map) localList.get(0)).get("lastDayBalance");
		if (paramBoolean) {
			localQueryConditions = new QueryConditions();
			localQueryConditions.addCondition("accountCode", "=", paramString);
			localQueryConditions.addCondition("b_date", "<", paramDate, "date");
			PageInfo localPageInfo = new PageInfo();
			localPageInfo.addOrderField("b_date", true);
			localPageInfo.setPageSize(1);
			localPageInfo.setPageNo(1);
			localList = queryDailyBalance(localQueryConditions, localPageInfo);
			if ((localList != null) && (localList.size() > 0))
				return (BigDecimal) ((Map) localList.get(0)).get("todayBalance");
		}
		return null;
	}

	public List queryDailyBalance(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select * from (select a.b_date,a.accountCode,b.name,a.lastDayBalance,a.DebitAmount,a.CreditAmount,a.todayBalance,b.accountLevel,b.dCFlag from f_DailyBalance a,f_Account b where a.accountCode=b.Code) db ";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return this.viewDao.queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public List queryMarketBalance(Date paramDate) {
		String str = "select u.marketid,m.name,sum(bailB) bailB,sum(feeB) feeB,sum(bailB)+sum(feeB) balance from userinfo u,marketinfo m,(select substr(d.accountcode,5) firmid,d.todaybalance bailB,d2.todaybalance feeB from dailybalance d,dailybalance d2 where d.occurdate=d2.occurdate and d.accountcode like '201-%' and d2.accountcode like '206-%' and substr(d.accountcode,5)=substr(d2.accountcode,5) and d.occurdate=?) subv where u.userid=subv.firmid and u.marketid=m.id group by u.marketid,m.name order by marketid";
		Object[] arrayOfObject = { paramDate };
		int[] arrayOfInt = { 91 };
		return this.viewDao.queryBySQL(str, arrayOfObject, arrayOfInt, null);
	}

	public Map queryClientLedgerOutside(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		String str1 = "select * from f_ledgerfield t where " + paramString
				+ " and ModuleID in (select moduleid from v_c_trademodule ) order by t.ordernum";
		Object[] arrayOfObject = null;
		ArrayList localArrayList1 = new ArrayList();
		List localList1 = this.viewDao.queryBySQL(str1, arrayOfObject, null);
		String[] arrayOfString1 = null;
		String[] arrayOfString2 = null;
		ArrayList localArrayList2 = null;
		String str2;
		if ((localList1 != null) && (localList1.size() > 0)) {
			arrayOfString1 = new String[localList1.size()];
			arrayOfString2 = new String[localList1.size()];
			localArrayList2 = new ArrayList();
			localArrayList2.add("b_date");
			localArrayList2.add("firmId");
			localArrayList2.add("lastBalance");
			for (int i = 0; i < localList1.size(); i++) {
				String localObject = (String) ((Map) localList1.get(i)).get("CODE");
				str2 = (String) ((Map) localList1.get(i)).get("NAME");
				String str3 = ((BigDecimal) ((Map) localList1.get(i)).get("FIELDSIGN")).toString();
				arrayOfString1[i] = localObject;
				arrayOfString2[i] = str3;
				localArrayList2.add(localObject);
			}
			if (!" 1=1 ".equals(paramString))
				localArrayList2.add("other");
			localArrayList2.add("todayBalance");
		}
		List localList2 = null;
		if (arrayOfString1 != null) {
			String localObject = ", sum(decode(code,'e_x_t',value,0)) e_x_t";
			str2 = "";
			for (int j = 0; j < arrayOfString1.length; j++) {
				String str5 = arrayOfString1[j];
				str2 = str2 + "nvl(ledgerSum." + str5 + ",0) " + str5 + ",";
			}
			str2 = str2.substring(0, str2.lastIndexOf(","));
			str1 = "select lastBalance,todayBalance,lastWarranty,todayWarranty,firmDate.firmId,firmDate.b_date," + str2
					+ " from (select to_char(b_date,'yyyy-MM-dd') b_date,firmId,lastBalance,todayBalance,lastWarranty,todayWarranty "
					+ "from f_firmbalance t ";
			if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
				arrayOfObject = paramQueryConditions.getValueArray();
				for (int j = 0; j < arrayOfObject.length; j++)
					localArrayList1.add(arrayOfObject[j]);
				str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
			}
			str1 = str1 + " ) firmDate,(select firmid,to_char(b_date,'yyyy-MM-dd') b_date ";
			String str4 = "todayBalance-lastBalance-(";
			for (int k = 0; k < arrayOfString1.length; k++) {
				String str7 = arrayOfString1[k];
				String str8 = arrayOfString2[k];
				str4 = str4 + "+" + str8 + "*" + str7;
				String str9 = ((String) localObject).replaceAll("e_x_t", str7);
				str1 = str1 + str9;
			}
			str1 = str1 + " from f_clientledger f ";
			if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
				arrayOfObject = paramQueryConditions.getValueArray();
				for (int k = 0; k < arrayOfObject.length; k++)
					localArrayList1.add(arrayOfObject[k]);
				str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
			}
			str1 = str1 + " group by firmid,b_date) ledgerSum where firmDate.firmid=ledgerSum.firmid(+) and  firmDate.b_date=ledgerSum.b_date(+)";
			arrayOfObject = localArrayList1.toArray();
			String str6 = "select a.*";
			if ((paramString != null) && (!" 1=1 ".equals(paramString)))
				str6 = str6 + ",(" + str4 + ")) other";
			str6 = str6 + " from (" + str1 + ") a order by b_date,firmId";
			System.out.println(str6);
			logger.debug("sql:" + str1);
			if (arrayOfObject != null)
				for (int m = 0; m < arrayOfObject.length; m++)
					logger.debug("params[" + m + "]: " + arrayOfObject[m]);
			localList2 = this.viewDao.queryBySQL(str6, arrayOfObject, paramPageInfo);
		}
		Map localObject = new HashMap();
		((Map) localObject).put("filed", localArrayList2);
		((Map) localObject).put("value", localList2);
		return localObject;
	}

	public List queryFiledMap(String paramString) {
		String str1 = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString)))
			str1 = " ModuleID in ('11','" + paramString + "') ";
		else if ("1".equals(paramString))
			str1 = " ModuleID='11' ";
		else
			str1 = " 1=1 ";
		String str2 = "select * from f_ledgerfield t where " + str1 + " and ModuleID in (select moduleid from v_c_trademodule) order by t.ordernum";
		Object[] arrayOfObject = null;
		List localList = this.viewDao.queryBySQL(str2, arrayOfObject, null);
		ArrayList localArrayList = null;
		HashMap localHashMap = null;
		if ((localList != null) && (localList.size() > 0)) {
			localArrayList = new ArrayList();
			localHashMap = new HashMap();
			localHashMap.put("name", SysData.getConfig("firmId"));
			localHashMap.put("code", "FIRMID");
			localArrayList.add(localHashMap);
			localHashMap = new HashMap();
			localHashMap.put("name", SysData.getConfig("lastBalance"));
			localHashMap.put("code", "LASTBALANCE");
			localArrayList.add(localHashMap);
			for (int i = 0; i < localList.size(); i++) {
				String str3 = (String) ((Map) localList.get(i)).get("CODE");
				String str4 = (String) ((Map) localList.get(i)).get("NAME");
				int j = ((BigDecimal) ((Map) localList.get(i)).get("FIELDSIGN")).intValue();
				if (j == 1)
					str4 = "+" + str4;
				else
					str4 = "-" + str4;
				localHashMap = new HashMap();
				localHashMap.put("name", str4);
				localHashMap.put("code", str3);
				localArrayList.add(localHashMap);
			}
			localHashMap = new HashMap();
			localHashMap.put("name", SysData.getConfig("todayBalance"));
			localHashMap.put("code", "TODAYBALANCE");
			localArrayList.add(localHashMap);
		}
		return localArrayList;
	}

	public Map queryClientLedgerSumOutside(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		String str1 = "select * from f_ledgerfield t where " + paramString
				+ " and ModuleID in (select moduleid from v_c_trademodule) order by t.ordernum";
		Object[] arrayOfObject = null;
		ArrayList localArrayList1 = new ArrayList();
		List localList1 = this.viewDao.queryBySQL(str1, arrayOfObject, null);
		String[] arrayOfString1 = null;
		String[] arrayOfString2 = null;
		ArrayList localArrayList2 = null;
		String str2;
		String str3;
		if ((localList1 != null) && (localList1.size() > 0)) {
			arrayOfString2 = new String[localList1.size()];
			arrayOfString1 = new String[localList1.size()];
			localArrayList2 = new ArrayList();
			localArrayList2.add("firmId");
			localArrayList2.add("lastBalance");
			for (int i = 0; i < localList1.size(); i++) {
				String localObject = (String) ((Map) localList1.get(i)).get("CODE");
				str2 = (String) ((Map) localList1.get(i)).get("name");
				str3 = ((BigDecimal) ((Map) localList1.get(i)).get("FIELDSIGN")).toString();
				arrayOfString2[i] = localObject;
				arrayOfString1[i] = str3;
				localArrayList2.add(localObject);
			}
			localArrayList2.add("todayBalance");
		}
		List localList2 = null;
		if (arrayOfString2 != null) {
			String localObject = "";
			str2 = "todayBalance-lastBalance-(";
			str3 = ", sum(decode(code,'e_x_t',value,0)) e_x_t";
			str1 = "select lastData.firmid,lastBalance,todayBalance ";
			String str5;
			for (int j = 0; j < arrayOfString2.length; j++) {
				str5 = arrayOfString2[j];
				str1 = str1 + " ,nvl(ledgerSum." + str5 + ",0) " + str5 + " ";
			}
			str1 = str1 + " from (select lastBalance,lastWarranty,firmId,b_date from f_firmbalance ";
			if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
				arrayOfObject = paramQueryConditions.getValueArray();
				for (int j = 0; j < arrayOfObject.length; j++)
					localArrayList1.add(arrayOfObject[j]);
				str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
			}
			str1 = str1 + ") lastData,(select todayBalance,todayWarranty,firmId,b_date from f_firmbalance ";
			if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
				arrayOfObject = paramQueryConditions.getValueArray();
				for (int j = 0; j < arrayOfObject.length; j++)
					localArrayList1.add(arrayOfObject[j]);
				str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
			}
			str1 = str1 + ") todayData,(select  firmId,max(b_date) maxDate,min(b_date) minDate from f_firmbalance ";
			if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
				arrayOfObject = paramQueryConditions.getValueArray();
				for (int j = 0; j < arrayOfObject.length; j++)
					localArrayList1.add(arrayOfObject[j]);
				str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
			}
			str1 = str1 + " group by firmId) firmDate,(select firmid lfirmid";
			for (int j = 0; j < arrayOfString2.length; j++) {
				str5 = arrayOfString2[j];
				String str6 = arrayOfString1[j];
				String str7 = str3.replaceAll("e_x_t", str5);
				str1 = str1 + str7;
				str2 = str2 + "+" + str6 + "*" + str5;
			}
			str1 = str1 + " from f_clientledger f ";
			if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
				arrayOfObject = paramQueryConditions.getValueArray();
				for (int j = 0; j < arrayOfObject.length; j++)
					localArrayList1.add(arrayOfObject[j]);
				str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
			}
			str1 = str1
					+ " group by firmid) ledgerSum where firmDate.firmid=ledgerSum.lfirmid(+)  and lastData.firmId=firmDate.firmId and firmDate.minDate=lastData.b_Date and firmDate.firmId=todayData.Firmid and firmDate.maxDate=todayData.b_Date";
			arrayOfObject = localArrayList1.toArray();
			String str4 = "select a.*";
			if ((paramString != null) && (!" 1=1 ".equals(paramString)))
				;
			str4 = str4 + " from (" + str1 + ") a order by firmId";
			logger.debug(str4);
			if (arrayOfObject != null)
				for (int k = 0; k < arrayOfObject.length; k++)
					logger.debug("params[" + k + "]: " + arrayOfObject[k]);
			localList2 = this.viewDao.queryBySQL(str4, arrayOfObject, paramPageInfo);
			logger.debug("size:" + localList2.size());
		}
		Map localObject = new HashMap();
		((Map) localObject).put("filed", localArrayList2);
		((Map) localObject).put("value", localList2);
		return localObject;
	}

	public List queryContractAccount(String paramString1, String paramString2) {
		String str = "select * from (select id,voucherDate,voucherNo,voucherSummary,creditAccountCode accountCode,creditAccountName accountName,amount debitAmount,0 creditAmount,debitAccountDCFlag dCFlag from accountbook where debitaccountcode like ? and contractNo=? union select id,voucherDate,voucherNo,voucherSummary,debitaccountcode accountCode,debitAccountName accountName,0 debitAmount,amount creditAmount,creditAccountDCFlag dCFlag from accountbook where creditaccountcode like ? and contractNo=?) queryView order by id";
		Object[] arrayOfObject = { paramString1 + "%", paramString2, paramString1 + "%", paramString2 };
		return this.viewDao.queryBySQL(str, arrayOfObject, null);
	}

	public List queryContractBalance(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select contractno,dSum,cSum,dSum-cSum balance from (select a.contractno,sum(b.debitamount) dSum,sum(b.creditamount) cSum from voucher a,voucherentry b where a.voucherno=b.voucherno and a.voucherstatus='accounted' and b.accountcode in ('202','205')";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " and " + paramQueryConditions.getFieldsSqlClause();
		}
		str = str + " group by a.contractno ) queryView";
		return this.viewDao.queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public List queryFirmBanlance(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str1 = null;
		String str2 = SysData.getConfig("systemType");
		str1 = "select * from (select z.*,f_balance-l_balance-y_balance balanceSubtract,f_balance+FrozenFunds user_balance from (select f.firmid,(select name from m_firm where firmId=f.firmId) name,f.balance f_balance,nvl(h.todaybalance,0) l_balance,nvl(e.y_balance,0) y_balance,nvl(f.lastwarranty,0) lastwarranty,nvl(-1*FrozenFunds,0) FrozenFunds from F_FirmFunds f,(select h1.firmid,h1.todaybalance from f_firmbalance h1 where h1.b_date =(select nvl(max(b_date),to_date('2009-01-01','yyyy-MM-dd')) from f_firmbalance)) h,(select d.firmid,0+nvl(b.c_balance,0)-nvl(c.d_balance,0) y_balance from F_FirmFunds d,(select a.firmid,sum(a.amount) c_balance from f_fundflow a where a.oprcode in (select t.summaryno from f_summary t where t.funddcflag='C') group by firmid) b,(select a.firmid,sum(a.amount) d_balance from f_fundflow a where a.oprcode in (select t.summaryno from f_summary t where t.funddcflag='D') group by firmid) c where d.firmid=b.firmid(+) and d.firmid=c.firmid(+)) e where f.firmid=h.firmid(+) and f.firmid=e.firmid(+)) z ) ";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str1 = str1 + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return this.viewDao.queryBySQL(str1, arrayOfObject, paramPageInfo);
	}

	public List getTrademoduleList() {
		String str1 = "select moduleid,name from M_trademodule where Enabled='Y' order by to_number(moduleid)";
		String str2 = "";
		Object[] arrayOfObject = null;
		return this.viewDao.queryBySQL(str1, arrayOfObject, null);
	}

	public List getLedgerFieldList() {
		String str1 = "select code,name from F_LedgerField order by OrderNum";
		String str2 = "";
		Object[] arrayOfObject = null;
		return this.viewDao.queryBySQL(str1, arrayOfObject, null);
	}

	public List queryFirmFunds(String paramString) {
		ArrayList localArrayList = null;
		String str = "select (case when s.funddcflag='C' then '+' when s.funddcflag='D' then '-' end)||s.summary name,a.money value from (select t.oprcode,sum(t.amount) money from f_fundflow t where firmID='"
				+ paramString + "' group by t.oprcode ) a,f_summary s where a.oprcode=SummaryNo";
		Object[] arrayOfObject = null;
		List localList1 = this.viewDao.queryBySQL(str, arrayOfObject, null);
		str = "select firmId,lastBalance,balance from f_firmfunds t where firmId='" + paramString + "'";
		List localList2 = this.viewDao.queryBySQL(str, arrayOfObject, null);
		Map localMap = null;
		if ((localList2 != null) && (localList2.size() > 0))
			localMap = (Map) localList2.get(0);
		localArrayList = new ArrayList();
		localArrayList.add(localMap);
		localArrayList.add(localList1);
		return localArrayList;
	}

	public List queryFundflow(String paramString, QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		List localList = null;
		String str = "";
		if ("h".equals(paramString))
			str = "select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_H_FundFlow";
		else if ("d".equals(paramString))
			str = "select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_FundFlow";
		else if ("hd".equals(paramString))
			str = "(select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_FundFlow union select FundFlowID,FirmID,(select Summary from F_Summary where SummaryNo=OprCode) OprCode,ContractNo,CommodityID,Amount,Balance,AppendAmount,VoucherNo,CreateTime,B_Date from F_H_FundFlow)";
		else
			return null;
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			if (paramQueryConditions.getFieldsSqlClause().contains("OprCode like  '%' || ? || '%'")) {
				StringBuffer localStringBuffer = new StringBuffer(" ");
				String[] arrayOfString = paramQueryConditions.getFieldsSqlClause().split("and");
				for (int i = 0; i < arrayOfString.length; i++)
					if (arrayOfString[i].trim().equals("OprCode like  '%' || ? || '%'"))
						localStringBuffer.append(" OprCode like ? and ");
					else
						localStringBuffer.append(arrayOfString[i] + " and ");
				str = str + " where " + localStringBuffer.toString().substring(0, localStringBuffer.toString().length() - 4);
			} else {
				str = str + " where " + paramQueryConditions.getFieldsSqlClause();
			}
		}
		localList = this.viewDao.queryBySQL(str, arrayOfObject, paramPageInfo);
		return localList;
	}

	public List queryLog(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		List localList = null;
		String str = "select * from f_log";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		localList = this.viewDao.queryBySQL(str, arrayOfObject, paramPageInfo);
		return localList;
	}

	public Map queryAccountBalance(String paramString1, String paramString2, String paramString3) {
		Map localMap = null;
		String str = "select b4.code,b4.name,b4.dcflag,nvl(b2.lastdaybalance,0) lastdaybalance,nvl(b3.todaybalance,0) todaybalance,nvl(b3.debitAmount,0) debitAmount,nvl(b3.creditAmount,0) creditAmount from (select min(t.b_date) mindate,max(b_date) maxdate,accountcode from f_dailybalance t where t.b_date>=to_date('"
				+ paramString2 + "','yyyy-MM-dd') and t.b_date<=to_date('" + paramString3 + "','yyyy-MM-dd') and t.accountcode='" + paramString1
				+ "' group by accountcode) b1," + "(select lastdaybalance,b_date from f_dailybalance where accountcode='" + paramString1 + "' ) b2,"
				+ "(select todaybalance,debitAmount,creditAmount,b_date from f_dailybalance where accountcode='" + paramString1 + "')b3, "
				+ "(select * from f_account where code='" + paramString1 + "' ) b4"
				+ " where b1.mindate=b2.b_date and b1.maxdate=b3.b_date and b1.accountcode=b4.code";
		List localList = this.viewDao.queryBySQL(str);
		if ((localList != null) && (localList.size() > 0))
			localMap = (Map) localList.get(0);
		return localMap;
	}

	public Map queryClientLedgerTotal(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		Map localObject = new HashMap();
		String str = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString)))
			str = " ModuleID in ('11','" + paramString + "')";
		else if ("1".equals(paramString))
			str = " ModuleID='11'";
		else
			str = " 1=1 ";
		localObject = queryClientLedgerSumOutside(paramQueryConditions, paramPageInfo, str);
		return localObject;
	}

	public Map queryClientLedger(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		Map localObject = new HashMap();
		String str = "";
		if ((paramString != null) && (!"0".equals(paramString)) && (!"1".equals(paramString)))
			str = " ModuleID in ('11','" + paramString + "')";
		else if ("1".equals(paramString))
			str = " ModuleID='11'";
		else
			str = " 1=1 ";
		localObject = queryClientLedgerOutside(paramQueryConditions, paramPageInfo, str);
		return localObject;
	}
}
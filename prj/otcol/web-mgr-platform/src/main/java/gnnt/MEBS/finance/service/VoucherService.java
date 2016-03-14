package gnnt.MEBS.finance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.Utils;
import gnnt.MEBS.finance.base.util.SysData;
import gnnt.MEBS.finance.dao.VoucherDao;
import gnnt.MEBS.finance.unit.Channel;
import gnnt.MEBS.finance.unit.Summary;
import gnnt.MEBS.finance.unit.Voucher;

@Service("f_voucherService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class VoucherService {
	private static final transient Log logger = LogFactory.getLog(VoucherService.class);
	@Autowired
	@Qualifier("f_voucherDao")
	private VoucherDao voucherDao;

	public void createSummary(Summary paramSummary) {
		this.voucherDao.createSummary(paramSummary);
	}

	public void updateSummary(Summary paramSummary) {
		this.voucherDao.updateSummary(paramSummary);
	}

	public int deleteSummary(String paramString1, String paramString2) {
		return this.voucherDao.deleteSummary(paramString1, paramString2);
	}

	public Summary getSummaryByNo(String paramString) {
		return this.voucherDao.getSummaryByNo(paramString);
	}

	public int getSummary(String paramString) {
		return this.voucherDao.getSummary(paramString);
	}

	public List getSummarys(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.voucherDao.getSummarys(paramQueryConditions, paramPageInfo);
	}

	public void createVoucher(Voucher paramVoucher) {
		this.voucherDao.createVoucher(paramVoucher);
	}

	public void updateVoucherNotEntrys(Voucher paramVoucher) {
		this.voucherDao.updateVoucherNotEntrys(paramVoucher);
	}

	public void updateVoucher(Voucher paramVoucher) {
		this.voucherDao.updateVoucher(paramVoucher);
	}

	public void deleteVoucher(Long paramLong, String paramString) {
		this.voucherDao.deleteVoucher(paramLong, paramString);
	}

	public Voucher getVoucherByNo(Long paramLong) {
		return this.voucherDao.getVoucherByNo(paramLong);
	}

	public List getVouchers(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString) {
		return this.voucherDao.getVouchers(paramQueryConditions, paramPageInfo, paramString);
	}

	public int auditVoucher(Long paramLong, boolean paramBoolean) {
		int i = -1;
		if (paramLong != null) {
			Voucher localVoucher = getVoucherByNo(paramLong);
			if (localVoucher != null) {
				if (paramBoolean) {
					i = this.voucherDao.callStoredProcedure("FN_F_auditVoucherPass(" + paramLong + ")");
				} else {
					localVoucher.setStatus("editing");
					updateVoucherNotEntrys(localVoucher);
					i = 1;
				}
			}
		}
		return i;
	}

	public void submitAllVoucherForAudit() {
		String str = "update f_Voucher set status='auditing' where status='editing'";
		this.voucherDao.updateBySQL(str);
	}

	public void submitVoucherForAudit(Long paramLong) {
		if (paramLong == null) {
			return;
		}
		String str = "update f_Voucher set status='auditing' where voucherNo=" + paramLong + " and status='editing'";
		this.voucherDao.updateBySQL(str);
	}

	public boolean isReadyForAccountBook() {
		String str = "select count(1) from f_Voucher where status='editing' or status='auditing'";
		int i = this.voucherDao.queryForInt(str, null);
		return i <= 0;
	}

	public boolean voucherToBalance(Date paramDate) {
		if (!isReadyForAccountBook()) {
			return false;
		}
		String str1 = null;
		if (paramDate != null) {
			str1 = Utils.formatDate("yyyy-MM-dd", paramDate);
		}
		String str3 = SysData.getConfig("moneyType");
		String str2;
		if ((str3 != null) && (str3.length() > 0)) {
			str2 = "VoucherToBalance(null," + str3 + ")";
			if (str1 != null) {
				str2 = "VoucherToBalance('" + str1 + "'," + str3 + ")";
			}
		} else {
			str2 = "VoucherToBalance(null)";
			if (str1 != null) {
				str2 = "VoucherToBalance('" + str1 + "')";
			}
		}
		this.voucherDao.executeStoredSubprogram(str2);
		return true;
	}

	public Date getMaxBalanceDate() {
		String str = "select max(B_Date) maxDate from f_DailyBalance";
		List localList = this.voucherDao.queryBySQL(str, null);
		if ((localList != null) && (localList.size() > 0)) {
			return (Date) ((Map) localList.get(0)).get("maxDate");
		}
		return null;
	}

	public int fundFlowIntoVoucher() {
		int i = this.voucherDao.callStoredProcedure("FN_F_ExtractVoucher()");
		return i;
	}

	public List voucherCheckDateList() {
		ArrayList localArrayList = null;
		String str1 = "select min(to_char(t.audittime,'yyyy-MM-dd')) balanceDate from f_voucher t where t.status='audited' and t.b_date is null and t.summaryno not like '2%' order by balanceDate ";
		String str2 = "select max(to_char(t.b_date,'yyyy-MM-dd')) balanceDate from f_voucher t where t.b_date is not null and t.summaryno not like '2%' order by balanceDate ";
		List localList1 = this.voucherDao.queryBySQL(str1, null);
		List localList2 = this.voucherDao.queryBySQL(str2, null);
		Object localObject1 = "";
		Object localObject2 = "";
		int i = 0;
		if ((localList1 != null) && (localList1.size() > 0)) {
			localArrayList = new ArrayList();
			for (int j = 0; j < localList1.size(); j++) {
				String localObject4 = (String) ((Map) localList1.get(j)).get("balanceDate");
				if ((localObject4 != null) && (!"".equals(localObject4))) {
					i = 1;
					localObject1 = localObject4;
				}
			}
		}
		if ((localList2 != null) && (localList2.size() > 0) && (i != 0)) {
			List localObject3 = new ArrayList();
			String localObject4 = (String) ((Map) localList2.get(0)).get("balanceDate");
			localObject2 = localObject4;
		}
		if ((localObject2 == null) || ("".equals(localObject2))) {
			localObject2 = "2001-01-01";
		}
		Object localObject3 = "select balanceDate balanceDate from (select to_char(sysdate,'yyyy-MM-dd') balanceDate from dual union select to_char(t.audittime,'yyyy-MM-dd') balanceDate from f_voucher t where t.status='audited' and t.b_date is null and t.audittime>to_date('"
				+ (String) localObject2 + " 23:59:59','yyyy-MM-dd hh24:mi:ss') and t.summaryno not like '2%' order by balanceDate)";
		Object localObject4 = this.voucherDao.queryBySQL((String) localObject3, null);
		if ((localObject4 != null) && (((List) localObject4).size() > 0) && (i != 0)) {
			for (int k = 0; k < ((List) localObject4).size(); k++) {
				String str3 = (String) ((Map) ((List) localObject4).get(k)).get("balanceDate");
				localArrayList.add(str3);
			}
		}
		return localArrayList;
	}

	public String voucherCheckMinDate() {
		String str1 = "";
		String str2 = "select min(to_char(t.audittime,'yyyy-MM-dd')) balanceDate from f_voucher t where t.status='audited' and t.b_date is null and t.summaryno not like '2%' order by balanceDate";
		List localList = this.voucherDao.queryBySQL(str2, null);
		if ((localList != null) && (localList.size() > 0)) {
			str1 = (String) ((Map) localList.get(0)).get("balanceDate");
		}
		return str1;
	}

	public String voucherCheckMaxDate() {
		String str1 = "";
		String str2 = "select max(to_char(t.b_date,'yyyy-MM-dd')) balanceDate from f_voucher t where t.b_date is not null order by balanceDate";
		List localList = this.voucherDao.queryBySQL(str2, null);
		if ((localList != null) && (localList.size() > 0)) {
			str1 = (String) ((Map) localList.get(0)).get("balanceDate");
		}
		return str1;
	}

	public List voucherCheckList() {
		String str1 = "";
		String str2 = "select distinct(to_char(t.createtime,'yyyy-MM-dd')) createTime,to_char(t.b_date,'yyyy-MM-dd') b_date from f_fundflow t where t.b_date is not null";
		List localList = this.voucherDao.queryBySQL(str2, null);
		return localList;
	}

	public List voucherCheckNoList() {
		String str1 = "";
		String str2 = "select distinct(to_char(t.createtime,'yyyy-MM-dd')) createTime from f_fundflow t where t.b_date is null and oprcode not like '2%'";
		List localList = this.voucherDao.queryBySQL(str2, null);
		return localList;
	}

	public int voucherCheckMaxDateIsToday() {
		int i = 2;
		String str = "select case when a.today=b.balanceDate then 1 else 2 end sign from (select to_char(sysdate,'yyyy-MM-dd') today from dual) a,(select max(to_char(t.b_date,'yyyy-MM-dd')) balanceDate from f_voucher t where t.b_date is not null order by balanceDate) b";
		List localList = this.voucherDao.queryBySQL(str, null);
		if ((localList != null) && (localList.size() > 0)) {
			int j = ((BigDecimal) ((Map) localList.get(0)).get("sign")).intValue();
			if (j == 1) {
				i = 1;
			}
		}
		return i;
	}

	public int setVoucherBDate(String paramString1, String paramString2, String paramString3) {
		int i = this.voucherDao.setVoucherBDate(paramString1, paramString2, paramString3);
		return i;
	}

	public int balanceVoucher(String paramString1, String paramString2) {
		int i = this.voucherDao.balanceVoucher(paramString1, paramString2);
		return i;
	}

	public List getVoucherModel() {
		String str = "select v.code,v.name,v.debitCode,v.creditCode,v.needContractNo,v.summaryNo,s.summary from f_vouchermodel v,F_Summary s where v.summaryNo=s.SummaryNo";
		List localList = this.voucherDao.queryBySQL(str, null);
		return localList;
	}

	public int createVoucherFast(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5,
			String paramString6, String paramString7) {
		int i = 0;
		double d = 0.0D;
		try {
			d = Double.parseDouble(paramString7);
		} catch (Exception localException) {
			i = -1;
		}
		if (paramString5 != null) {
			paramString5 = "'" + paramString5 + "'";
		}
		if (i == 0) {
			i = this.voucherDao.callStoredProcedure("FN_F_CreateVoucher('" + paramString1 + "','" + paramString2 + "','" + paramString3 + "','"
					+ paramString4 + "'," + d + "," + paramString5 + ",'" + paramString6 + "')");
		}
		return i;
	}

	public List getChannels(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select * from f_vouchermodel";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return this.voucherDao.queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public void createChannel(Channel paramChannel) {
		String str = "insert into f_vouchermodel values(?,?,?,?,?,?,?)";
		logger.debug("sql: " + str);
		Object[] arrayOfObject = { paramChannel.getCode(), paramChannel.getName(), paramChannel.getSummaryNo(), paramChannel.getDebitCode(),
				paramChannel.getCreditCode(), paramChannel.getNeedContractNo(), paramChannel.getNote() };
		for (int i = 0; i < arrayOfObject.length; i++) {
			logger.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		int[] arrayOfInt = { 12, 12, 1, 12, 12, 1, 12 };
		this.voucherDao.updateBySQL(str, arrayOfObject, arrayOfInt);
	}

	public Channel getChannelByCode(String paramString) {
		Channel localChannel = this.voucherDao.getChannelByCode(paramString);
		return localChannel;
	}

	public void updateChannel(Channel paramChannel) {
		String str = "update f_vouchermodel set name=?,SummaryNo=?,DebitCode=?,CreditCode=?,NeedContractNo=?,Note=? where code=?";
		logger.debug("sql: " + str);
		Object[] arrayOfObject = { paramChannel.getName(), paramChannel.getSummaryNo(), paramChannel.getDebitCode(), paramChannel.getCreditCode(),
				paramChannel.getNeedContractNo(), paramChannel.getNote(), paramChannel.getCode() };
		for (int i = 0; i < arrayOfObject.length; i++) {
			logger.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		int[] arrayOfInt = { 12, 1, 12, 12, 1, 12, 12 };
		this.voucherDao.updateBySQL(str, arrayOfObject, arrayOfInt);
	}

	public void deleteChannel(String paramString) {
		String str = "delete from f_vouchermodel where code='" + paramString + "'";
		this.voucherDao.updateBySQL(str);
	}
}

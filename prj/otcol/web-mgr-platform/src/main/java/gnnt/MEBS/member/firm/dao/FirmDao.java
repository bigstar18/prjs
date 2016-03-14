package gnnt.MEBS.member.firm.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.common.util.query.Utils;
import gnnt.MEBS.member.firm.unit.Firm;
import gnnt.MEBS.member.firm.unit.FirmCategory;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.FirmModule;
import gnnt.MEBS.member.firm.unit.TradeModule;

public class FirmDao extends DaoHelperImpl {
	private final transient Log logger = LogFactory.getLog(FirmDao.class);
	private DataSource dataSource;
	private PlatformTransactionManager transactionManager;

	public PlatformTransactionManager getTransactionManager() {
		return this.transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public List getFirmList(QueryConditions conditions, PageInfo pageInfo) {
		String sql = "select distinct m.*,t.tariffname,mb.brokerId from m_firm m,t_a_tariff t,m_b_firmandbroker mb where m.tariffID=t.tariffID(+) and m.firmid=mb.firmid(+)";

		Object[] params = (Object[]) null;
		String LRphoneNo = "";
		String legalRepresentative = "";
		String LRSql = "%<keyValue><key><![CDATA[LRphoneNo]]></key><value><![CDATA[";
		String LeSql = "%<keyValue><key><![CDATA[legalRepresentative]]></key><value><![CDATA[%";
		if (conditions != null) {
			if ((conditions.getConditionValue("legalRepresentative") != null) && (conditions.getConditionValue("LRphoneNo") == null)) {
				legalRepresentative = conditions.getConditionValue("legalRepresentative").toString();
				LeSql = LeSql + legalRepresentative + "%]]></value></keyValue>%";
				sql = sql + " and extenddata like '" + LeSql + "'";
			}
			if ((conditions.getConditionValue("LRphoneNo") != null) && (conditions.getConditionValue("legalRepresentative") == null)) {
				LRphoneNo = conditions.getConditionValue("LRphoneNo").toString();
				LRSql = LRSql + LRphoneNo + "%]]></value></keyValue>%";
				sql = sql + " and extenddata like '" + LRSql + "'";
			}
			if ((conditions.getConditionValue("legalRepresentative") != null) && (conditions.getConditionValue("LRphoneNo") != null)) {
				legalRepresentative = conditions.getConditionValue("legalRepresentative").toString();
				LeSql = LeSql + legalRepresentative + "%]]></value></keyValue>%";
				LRphoneNo = conditions.getConditionValue("LRphoneNo").toString();
				LRSql = LRSql + LRphoneNo + "%]]></value></keyValue>%";
				sql = sql + " and (extenddata like '" + LeSql + LRSql + "' or extenddata like '" + LRSql + LeSql + "')";
			}
			conditions.removeCondition("LRphoneNo");
			conditions.removeCondition("legalRepresentative");
			if ((conditions.getConditionValue("m.createTime", ">=") != null) && (conditions.getConditionValue("m.createTime", "<=") != null)) {
				String beginDate = Utils.formatDate("yyyy-MM-dd", (Date) conditions.getConditionValue("m.createTime", ">="));
				String endDate = Utils.formatDate("yyyy-MM-dd", (Date) conditions.getConditionValue("m.createTime", "<="));

				sql = sql + " and to_char(m.createTime,'yyyy-MM-dd') >= '" + beginDate + "' and to_char(m.createTime,'yyyy-MM-dd') <= '" + endDate
						+ "' ";
			}
			conditions.removeCondition("m.createTime", ">=");
			conditions.removeCondition("m.createTime", "<=");
		}
		if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
			params = conditions.getValueArray();
			sql = sql + " and " + conditions.getFieldsSqlClause();
		}
		this.logger.debug("---------sql:-------- " + sql);

		return queryBySQL(sql, params, pageInfo);
	}

	public List getFirmLists(QueryConditions conditions, PageInfo pageInfo, String brokerId) {
		String sql = "select distinct m.*,t.tariffname,mb.brokerId from m_firm m,t_a_tariff t,m_b_firmandbroker mb where m.tariffID=t.tariffID(+) and m.firmid=mb.firmid(+)";
		if (brokerId != null) {
			sql = sql + " and m.firmId in(select b.firmId from m_b_firmandbroker b where b.brokerId like '%" + brokerId + "%' )";
		}
		Object[] params = (Object[]) null;
		String LRphoneNo = "";
		String legalRepresentative = "";
		String LRSql = "%<keyValue><key><![CDATA[LRphoneNo]]></key><value><![CDATA[";
		String LeSql = "%<keyValue><key><![CDATA[legalRepresentative]]></key><value><![CDATA[%";
		if (conditions != null) {
			if ((conditions.getConditionValue("legalRepresentative") != null) && (conditions.getConditionValue("LRphoneNo") == null)) {
				legalRepresentative = conditions.getConditionValue("legalRepresentative").toString();
				LeSql = LeSql + legalRepresentative + "%]]></value></keyValue>%";
				sql = sql + " and extenddata like '" + LeSql + "'";
			}
			if ((conditions.getConditionValue("LRphoneNo") != null) && (conditions.getConditionValue("legalRepresentative") == null)) {
				LRphoneNo = conditions.getConditionValue("LRphoneNo").toString();
				LRSql = LRSql + LRphoneNo + "%]]></value></keyValue>%";
				sql = sql + " and extenddata like '" + LRSql + "'";
			}
			if ((conditions.getConditionValue("legalRepresentative") != null) && (conditions.getConditionValue("LRphoneNo") != null)) {
				legalRepresentative = conditions.getConditionValue("legalRepresentative").toString();
				LeSql = LeSql + legalRepresentative + "%]]></value></keyValue>%";
				LRphoneNo = conditions.getConditionValue("LRphoneNo").toString();
				LRSql = LRSql + LRphoneNo + "%]]></value></keyValue>%";
				sql = sql + " and (extenddata like '" + LeSql + LRSql + "' or extenddata like '" + LRSql + LeSql + "')";
			}
			conditions.removeCondition("LRphoneNo");
			conditions.removeCondition("legalRepresentative");
			if ((conditions.getConditionValue("m.createTime", ">=") != null) && (conditions.getConditionValue("m.createTime", "<=") != null)) {
				String beginDate = Utils.formatDate("yyyy-MM-dd", (Date) conditions.getConditionValue("m.createTime", ">="));
				String endDate = Utils.formatDate("yyyy-MM-dd", (Date) conditions.getConditionValue("m.createTime", "<="));

				sql = sql + " and to_char(m.createTime,'yyyy-MM-dd') >= '" + beginDate + "' and to_char(m.createTime,'yyyy-MM-dd') <= '" + endDate
						+ "' ";
			}
			conditions.removeCondition("m.createTime", ">=");
			conditions.removeCondition("m.createTime", "<=");
		}
		if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
			params = conditions.getValueArray();
			sql = sql + " and " + conditions.getFieldsSqlClause();
		}
		this.logger.debug("---------sql:-------- " + sql);

		return queryBySQL(sql, params, pageInfo);
	}

	public List getFirms(QueryConditions conditions) {
		String sql = "select m.*,t.tariffname from m_firm m,(select distinct tariffid,tariffname from t_a_tariff) t where m.tariffid=t.tariffid(+)";
		Object[] params = (Object[]) null;
		if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
			params = conditions.getValueArray();
			sql = sql + " and " + conditions.getFieldsSqlClause();
		}
		return queryBySQL(sql, params, null, new CommonRowMapper(new Firm()));
	}

	public Firm getFirmLock(String id) {
		Firm rirmForupdate = new Firm();
		String sql = "select * from m_firm where firmId='" + id + "' for update";
		List<Firm> list = queryBySQL(sql, null, null, new CommonRowMapper(new Firm()));
		if ((list != null) && (list.size() > 0)) {
			rirmForupdate = (Firm) list.get(0);
		}
		return rirmForupdate;
	}

	public int verifyRepeat(String firmId) {
		String sql = "select count(firmId) from m_firm where firmId='" + firmId + "'";
		return queryForInt(sql, null);
	}

	public void createFirm(Firm firm) {
		String sql = "insert into m_firm (firmId,name,firmcategoryid,fullname,bank,bankAccount,address,contactMan,phone,fax,postCode,eMail,createTime,modifyTime,note,status,zoneCode,industryCode,type,EXTENDDATA,tariffID)  values(?,?,?,?,?,?,?,?,?,?,?,?,";

		sql = sql + "sysdate,sysdate,?,'N',?,?,?,?,?)";
		Object[] params = { firm.getFirmId(), firm.getName(), firm.getFirmCategoryId(), firm.getFullname(), firm.getBank(), firm.getBankAccount(),
				firm.getAddress(), firm.getContactMan(), firm.getPhone(), firm.getFax(), firm.getPostCode(), firm.getEmail(), firm.getNote(),
				firm.getZoneCode(), firm.getIndustryCode(), Integer.valueOf(firm.getType()), firm.getExtendData(), firm.getTariffID() };

		this.logger.debug("sql: " + sql);
		int[] dataTypes = { 12, 12, 2, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };

		System.out.println("sql:" + sql);
		updateBySQL(sql, params, dataTypes);
	}

	public void log(Map map) {
		String sql = " insert into f_b_log (logid,logopr,logcontent,logdate,logIp) values (SEQ_F_B_LOG.NEXTVAL,?,?,sysdate,?) ";

		Object[] params = { (String) map.get("logopr"), (String) map.get("logcontent"), (String) map.get("logIp") };

		int[] dataTypes = { 12, 12, 12 };

		getJdbcTemplate().update(sql, params, dataTypes);
	}

	public List getTradeModules(QueryConditions conditions) {
		String sql = "select * from m_TradeModule where 1=1 ";
		Object[] params = (Object[]) null;
		if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
			params = conditions.getValueArray();
			sql = sql + " and " + conditions.getFieldsSqlClause();
		}
		return queryBySQL(sql, params, null, new CommonRowMapper(new TradeModule()));
	}

	public List<FirmModule> getFirmModuleByFirmId(String firmId) {
		String sql = "select * from m_firmmodule t where t.enabled = 'Y' and t.firmid = '" + firmId + "'";
		return queryBySQL(sql, null, null, new CommonRowMapper(new FirmModule()));
	}

	public void addFirmModule(FirmModule module) {
		String sql = "insert into m_firmmodule values(?,?,?)";
		Object[] params = { module.getModuleId(), module.getFirmId(), module.getEnabled() };
		for (int a = 0; a < params.length; a++) {
			this.logger.debug("params[" + a + "]: " + params[a]);
		}
		updateBySQL(sql, params);
	}

	public void addFirmLog(FirmLog firmLog) {
		String sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,?,?,?)";
		Object[] params = { firmLog.getUserId(), firmLog.getFirmId(), firmLog.getAction() };
		this.logger.debug(sql);
		updateBySQL(sql, params);
	}

	public int updateFirm(Firm firm) {
		int result = -1;
		String sql = "update m_firm set name=?,fullname=?,bank=?,bankAccount=?,address=?,contactMan=?,phone=?,fax=?,postCode=?,eMail=?,modifyTime=sysdate,note=?,firmcategoryid=?";

		sql = sql + ",zoneCode=?,industryCode=?,type=?,EXTENDDATA=?,tariffID=? where firmId=?";
		Object[] params = { firm.getName(), firm.getFullname(), firm.getBank(), firm.getBankAccount(), firm.getAddress(), firm.getContactMan(),
				firm.getPhone(), firm.getFax(), firm.getPostCode(), firm.getEmail(), firm.getNote(), firm.getFirmCategoryId(), firm.getZoneCode(),
				firm.getIndustryCode(), Integer.valueOf(firm.getType()), firm.getExtendData(), firm.getTariffID(), firm.getFirmId() };

		int[] dataTypes = { 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 2, 12, 12, 12, 12, 12, 12 };

		updateBySQL(sql, params, dataTypes);
		result = 1;
		return result;
	}

	public void initFirmModule(String firmId) {
		String sql = "update m_firmmodule set enabled='N' where firmId='" + firmId + "'";
		updateBySQL(sql);
	}

	public void updateFirmModule(FirmModule module) {
		String sql = "update m_firmmodule set enabled='Y' where firmId='" + module.getFirmId() + "' and moduleID='" + module.getModuleId() + "'";
		updateBySQL(sql);
	}

	public void updateTraderModule(String per, String firmId) {
		if ("".equals(per)) {
			String sql = "update m_TraderModule set enabled='N' where traderId in (select traderId from m_trader where firmId='" +

			firmId + "') " + " and enabled='Y'";
			this.logger.debug(sql);
			updateBySQL(sql);
		} else {
			String sql = "update m_TraderModule set enabled='N' where traderId in (select traderId from m_trader where firmId='" +

			firmId + "') " + "and moduleID not in (" + per + ") and enabled='Y'";
			this.logger.debug(sql);
			updateBySQL(sql);
		}
	}

	public void updateFirmLog(Firm firm, String logonUser) {
		String remark = "交易商" + firm.getFirmId() + "修改";
		String sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,'" + logonUser + "','" + firm.getFirmId() + "','"
				+ remark + "')";
		this.logger.debug(sql);
		updateBySQL(sql);
	}

	public void setStatusFirm(Firm firm) {
		String sql = "update m_firm set status='" + firm.getStatus() + "' where firmId='" + firm.getFirmId() + "'";
		updateBySQL(sql);
	}

	public void setStatusTrader(Firm firm) {
		String sql = "update m_Trader set status='D' where firmId='" + firm.getFirmId() + "'";
		updateBySQL(sql);
	}

	public int callStored(String firmId) {
		FirmExitCheck func = new FirmExitCheck(getDataSource());
		Map inputs = new HashMap();
		inputs.put("p_firmid", firmId);
		Map results = func.execute(inputs);
		int result = ((BigDecimal) results.get("ret")).intValue();
		return result;
	}

	public void addFirm(Firm firm) {
		M_FirmAdd func = new M_FirmAdd(getDataSource());
		Map inputs = new HashMap();
		inputs.put("p_firmid", firm.getFirmId());
		func.execute(inputs);
	}

	public void deleteFirm(Firm firm) {
		FirmDel func = new FirmDel(getDataSource());
		Map inputs = new HashMap();
		inputs.put("p_firmid", firm.getFirmId());
		func.execute(inputs);
	}

	public boolean checkTariffStatus(Firm user) {
		String sql = "";
		sql = "select count(tariffid) from (select * from t_a_tariff where status=0) t where tariffId='" + user.getTariffID() + "'";
		return getJdbcTemplate().queryForInt(sql) > 0;
	}

	public void updateFirmStatus(String firmId, String status) {
		String sql = "update m_firm set status=? where firmId=?";
		Object[] params = { status, firmId };
		getJdbcTemplate().update(sql, params);
	}

	public List getFirms(QueryConditions conditions, PageInfo pageInfo) {
		String sql = "select a.*,b.BrokerID from m_firm a,M_B_FirmAndBroker b where a.firmid=b.firmid(+) and a.status in ('N','U','D','E')";
		Object[] params = (Object[]) null;
		if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
			String bankid = (String) conditions.getConditionValue("zztype");
			if (bankid != null) {
				conditions.removeCondition("zztype");
				if (!bankid.equals("all")) {
					sql = sql + " and a.firmId in (select firmid from F_B_FIRMIDANDACCOUNT where bankid='" + bankid + "')  ";
				}
			}
			if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
				params = conditions.getValueArray();
				sql = sql + " and " + conditions.getFieldsSqlClause();
			}
		}
		this.logger.debug("---------sql:-------- " + sql);
		return queryBySQL(sql, params, pageInfo);
	}

	public int setStatusFirm(String firmId, String firmStatus, String logonUser) {
		int result = -1;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {
			this.dataSource = getDataSource();
			JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
			String sql = "";
			if (!"R".equals(firmStatus)) {
				sql = "update m_firm set status='" + firmStatus + "' where firmId='" + firmId + "'";
				jdbcTemplate.update(sql);
				if ("U".equals(firmStatus)) {
					sql = "update m_Trader set status='U' where firmId='" + firmId + "'";
					jdbcTemplate.update(sql);
				} else if ("N".equals(firmStatus)) {
					sql = "update m_Trader set status='N' where firmId='" + firmId + "'";
					jdbcTemplate.update(sql);
				}
				String remark = "交易商" + firmId + "设为";
				if ("U".equals(firmStatus)) {
					remark = remark + "禁止";
				} else {
					remark = remark + "正常";
				}
				sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,'" + logonUser + "','" + firmId + "','" + remark + "')";
				jdbcTemplate.update(sql);

				result = 1;
			} else {
				Connection conn = jdbcTemplate.getDataSource().getConnection();
				CallableStatement cstmt = conn.prepareCall("{?=call FN_M_FirmExitCheck('" + firmId + "')}");
				cstmt.registerOutParameter(1, 4);
				cstmt.execute();
				int r = cstmt.getInt(1);
				if (r == 1) {
					sql = "update m_firm set status='" + firmStatus + "' where firmId='" + firmId + "'";
					jdbcTemplate.update(sql);

					sql = "update m_Trader set status='U' where firmId='" + firmId + "'";
					jdbcTemplate.update(sql);

					String remark = "交易商" + firmId + "设为退市";
					sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,'" + logonUser + "','" + firmId + "','" + remark
							+ "')";
					jdbcTemplate.update(sql);
					result = 1;
				} else {
					result = -2;
				}
			}
			this.transactionManager.commit(status);
		} catch (DataAccessException ex) {
			result = -1;
		} catch (Exception ex) {
			this.transactionManager.rollback(status);
			result = -1;
		}
		return result;
	}

	public int updatefirmbankMaxPertransMoney(String firmId, String firmStatus, String logonUser) {
		int result = -1;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {
			this.dataSource = getDataSource();
			JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
			String sql = "";
			if ("N".equals(firmStatus)) {
				sql = "update F_B_FIRMIDANDACCOUNT set STATUS=0 where ISOPEN=1 and STATUS=1 and firmId='" + firmId + "'";
				jdbcTemplate.update(sql);
				String remark = "交易商" + firmId + "银行接口状态设为0";
				sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,'" + logonUser + "','" + firmId + "','" + remark + "')";
				jdbcTemplate.update(sql);

				result = 1;
			} else if ("U".equals(firmStatus)) {
				sql = "update F_B_FIRMIDANDACCOUNT set STATUS=1 where ISOPEN=1 and STATUS=0 and firmId='" + firmId + "'";
				jdbcTemplate.update(sql);
				String remark = "交易商" + firmId + "银行接口状态设为1";
				sql = "insert into m_firmLog (occurTime,userId,firmId,action) values (sysdate,'" + logonUser + "','" + firmId + "','" + remark + "')";
				jdbcTemplate.update(sql);
				result = 1;
			}
			this.transactionManager.commit(status);
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			result = -1;
		} catch (Exception ex) {
			ex.printStackTrace();
			this.transactionManager.rollback(status);
			result = -1;
		}
		return result;
	}

	public int createBrokerAndFirm(Firm firm) {
		int result = 0;
		String sql = "insert into m_b_firmandbroker(firmid,brokerid) values(?,?)";
		Object[] params = { firm.getFirmId(), firm.getBrokerId() };
		result = getJdbcTemplate().update(sql, params);
		return result;
	}

	private class FirmExitCheck extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_M_FirmExitCheck";

		public FirmExitCheck(DataSource ds) {
			super(ds, "FN_M_FirmExitCheck");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_firmid", 12));
			compile();
		}

		public Map execute(Map map) {
			return super.execute(map);
		}
	}

	private class M_FirmAdd extends StoredProcedure {
		private static final String SFUNC_NAME = "SP_M_FirmAdd";

		public M_FirmAdd(DataSource ds) {
			super(ds, "SP_M_FirmAdd");

			declareParameter(new SqlParameter("p_firmid", 12));
			compile();
		}

		public Map execute(Map map) {
			return super.execute(map);
		}
	}

	private class FirmDel extends StoredProcedure {
		private static final String SFUNC_NAME = "SP_M_FirmDel";

		public FirmDel(DataSource ds) {
			super(ds, "SP_M_FirmDel");

			declareParameter(new SqlParameter("p_firmid", 12));
			compile();
		}

		public Map execute(Map map) {
			return super.execute(map);
		}
	}

	public List getFirmCategoryList(String firmId, String name, PageInfo pageInfo) {
		String sql = "select  m.* from m_firmcategory m where 1=1";
		if ((name != null) && (!"".equals(name))) {
			sql = sql + " and m.name like '" + name + "%'";
		}
		if ((firmId != null) && (!"".equals(firmId))) {
			sql = sql + " and m.id like '" + firmId + "%'";
		}
		sql = sql + "  order by m.id";

		this.logger.debug("---------sql:-------- " + sql);
		return queryBySQL(sql, null, pageInfo);
	}

	public List getFirmCategoryById(String firmId) {
		String sql = "select  m.* from m_firmcategory m where id = " + firmId;
		System.out.println(sql + "---------------------------执行aql");
		this.logger.debug("---------sql:-------- " + sql);
		return queryBySQL(sql);
	}

	public void createFirmCategory(FirmCategory firmCategory) {
		String sql = "insert into m_firmcategory (id, name,note)  values(?,?,?)";

		Object[] params = { firmCategory.getId(), firmCategory.getName(), firmCategory.getNote() };

		this.logger.debug("sql: " + sql);
		int[] dataTypes = { 2, 12, 12 };
		System.out.println("sql:" + sql);
		updateBySQL(sql, params, dataTypes);
	}

	public int verifyRepeatCategory(Long id) {
		String sql = "select count(id) from m_firmcategory where id='" + id + "'";
		return queryForInt(sql, null);
	}

	public void updateFirmCategory(FirmCategory firmCategory, String oldId) {
		String sql = "update m_firmcategory  set id = ?, name = ?,note=? where id = " + oldId;

		Object[] params = { firmCategory.getId(), firmCategory.getName(), firmCategory.getNote() };

		this.logger.debug("sql: " + sql);
		int[] dataTypes = { 2, 12, 12 };
		System.out.println("sql:" + sql);
		int[] dataType = { 2 };
		updateBySQL(sql, params, dataTypes);
		sql = "update m_firm set firmcategoryid = ? where firmcategoryid = " + oldId;
		this.logger.debug("sql: " + sql);
		updateBySQL(sql, new Object[] { firmCategory.getId() }, dataType);
	}

	public void deleteFirmCategory(String firmId) {
		String sql = "delete from m_firmcategory where id = " + firmId;
		System.out.println(sql);

		updateBySQL(sql, new Object[0], new int[0]);
		String sql1 = "update m_firm set firmcategoryid = 0 where firmcategoryid = '" + firmId + "'";
		updateBySQL(sql1, new Object[0], new int[0]);
	}

	public List getBankList() {
		String sql = "select * from F_B_banks  where validFlag=0 ";
		return queryBySQL(sql);
	}

	public List getBrokerByFirmId(String firmId) {
		String sql = "select * from m_b_broker where brokerId in (select brokerId from M_B_FIRMANDBROKER where firmId ='" + firmId + "')";
		return queryBySQL(sql);
	}
}

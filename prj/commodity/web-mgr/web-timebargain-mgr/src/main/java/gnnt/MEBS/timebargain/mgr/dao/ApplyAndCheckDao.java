package gnnt.MEBS.timebargain.mgr.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import gnnt.MEBS.timebargain.mgr.model.apply.Apply;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityFee;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_CommodityMargin;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply_T_PledgeMoney;

@Repository("com_applyAndCheckDao")
public class ApplyAndCheckDao extends JdbcDaoSupport {
	private final transient Log logger = LogFactory.getLog(ApplyAndCheckDao.class);
	private LobHandler lobHandler;
	private DataFieldMaxValueIncrementer incre;
	public static Object LOCK = new Object();

	public LobHandler getLobHandler() {
		return this.lobHandler;
	}

	@Resource(name = "lobHandler")
	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	@Resource(name = "jdbcTemplate")
	public void setSuperJdbcTemplate(JdbcTemplate jdbcTemplate) {
		super.setJdbcTemplate(jdbcTemplate);
	}

	public Apply getApplyById(Apply apply, boolean lock) {
		long id = apply.getId();
		String filter = lock ? " for update" : "";
		String sql = "select id, applytype, status, proposer, applytime, approver, approvetime, note from t_apply t where id=" + id + " " + filter;
		this.logger.debug(sql);
		List list = getJdbcTemplate().queryForList(sql);
		if (list.size() > 0) {
			Map map = (Map) list.get(0);

			apply.setId(((BigDecimal) map.get("ID")).longValue());
			apply.setApplyType(((BigDecimal) map.get("APPLYTYPE")).intValue());
			apply.setStatus(((BigDecimal) map.get("STATUS")).intValue());
			apply.setProposer((String) map.get("PROPOSER"));
			apply.setApplytime((Date) map.get("APPLYTIME"));
			apply.setApprover((String) map.get("approver"));
			apply.setNote((String) map.get("note"));
			apply.setApprovetime((Date) map.get("APPROVETIME"));
			sql = "select t.content.getclobval() content from t_apply t where id=" + id;
			List l = getJdbcTemplate().query(sql, new Object[0], new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String content = ApplyAndCheckDao.this.lobHandler.getClobAsString(rs, 1);
					return content;
				}
			});
			apply.setContent((String) l.get(0));
		}

		return apply;
	}

	public void updateApply(Apply apply) {
		String sql = "update t_apply t set status=" + apply.getStatus() + ",t.approver='" + apply.getApprover() + "',"
				+ "t.approvetime=sysdate,t.note='" + apply.getNote() + "' where id=" + apply.getId();
		getJdbcTemplate().update(sql);
	}

	public void updateCommodityFee(Apply_T_CommodityFee app) {
		String sql = "update t_commodity set feeAlgr=?,feeRate_B=?,feeRate_S=?,historyCloseFeeRate_B=?,historyCloseFeeRate_S=?,todayCloseFeeRate_B=?,todayCloseFeeRate_S=?,forceCloseFeeRate_B=?,forceCloseFeeRate_S=?,settleFeeAlgr=?,settleFeeRate_B=?,settleFeeRate_S=?,lowestSettleFee=? where commodityID = ?";

		Object[] params = { app.getFeeAlgr(), app.getFeeRate_B(), app.getFeeRate_S(), app.getHistoryCloseFeeRate_B(), app.getHistoryCloseFeeRate_S(),
				app.getTodayCloseFeeRate_B(), app.getTodayCloseFeeRate_S(), app.getForceCloseFeeRate_B(), app.getForceCloseFeeRate_S(),
				app.getSettleFeeAlgr(), app.getSettleFeeRate_B(), app.getSettleFeeRate_S(), app.getLowestSettleFee(), app.getCommodityID() };

		this.logger.debug("sql: " + sql);
		getJdbcTemplate().update(sql, params);
	}

	public void updateCommodityMargin(Apply_T_CommodityMargin app) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date relSettleDate = null;
		Date relSettleDate1 = null;
		Date relSettleDate2 = null;
		Date relSettleDate3 = null;
		Date relSettleDate4 = null;
		Date relSettleDate5 = null;
		try {
			if ((app.getSettleDate() != "null") && (!"".equals(app.getSettleDate()))) {
				relSettleDate = df.parse(app.getSettleDate());
			}
			if ((app.getSettleDate1() != "null") && (!"".equals(app.getSettleDate1()))) {
				relSettleDate1 = df.parse(app.getSettleDate1());
			}
			System.out.println("app.getSettleDate2(): " + app.getSettleDate2());
			if ((app.getSettleDate2() != null) && (app.getSettleDate2() != "null") && (!"".equals(app.getSettleDate2()))) {
				System.out.println("app.getSettleDate2() in: " + app.getSettleDate2());
				relSettleDate2 = df.parse(app.getSettleDate2());
			}
			if ((app.getSettleDate3() != "null") && (!"".equals(app.getSettleDate3()))) {
				relSettleDate3 = df.parse(app.getSettleDate3());
			}
			if ((app.getSettleDate4() != "null") && (!"".equals(app.getSettleDate4())))
				relSettleDate4 = df.parse(app.getSettleDate4());
			if ((app.getSettleDate5() != "null") && (!"".equals(app.getSettleDate5())))
				relSettleDate5 = df.parse(app.getSettleDate5());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String sql = "update t_commodity set marginAlgr=?,marginPriceType=?,settleDate1=?,marketDate=?,settleDate2=?,settleDate3=?,settleDate4=?,settleDate5=?,settleDate=?,marginItem1=?,marginItem2=?,marginItem3=?,marginItem4=?,marginItem5=?,marginItem1_S=?,marginItem2_S=?,marginItem3_S=?,marginItem4_S=?,marginItem5_S=?,marginItemAssure1=?,marginItemAssure2=?,marginItemAssure3=?,marginItemAssure4=?,marginItemAssure5=?,marginItemAssure1_S=?,marginItemAssure2_S=?,marginItemAssure3_S=?,marginItemAssure4_S=?,marginItemAssure5_S=?,settleMarginAlgr_B=?,settleMarginAlgr_S=?,settleMarginRate_B=?,settleMarginRate_S=?,payoutAlgr=?,payoutRate=? where commodityID = ?";

		Object[] params = { app.getMarginAlgr(), app.getMarginPriceType(), relSettleDate1, relSettleDate1, relSettleDate2, relSettleDate3,
				relSettleDate4, relSettleDate5, relSettleDate, app.getMarginItem1(), app.getMarginItem2(), app.getMarginItem3(), app.getMarginItem4(),
				app.getMarginItem5(), app.getMarginItem1_S(), app.getMarginItem2_S(), app.getMarginItem3_S(), app.getMarginItem4_S(),
				app.getMarginItem5_S(), app.getMarginItemAssure1(), app.getMarginItemAssure2(), app.getMarginItemAssure3(),
				app.getMarginItemAssure4(), app.getMarginItemAssure5(), app.getMarginItemAssure1_S(), app.getMarginItemAssure2_S(),
				app.getMarginItemAssure3_S(), app.getMarginItemAssure4_S(), app.getMarginItemAssure5_S(), app.getSettleMarginAlgr_B(),
				app.getSettleMarginAlgr_S(), app.getSettleMarginRate_B(), app.getSettleMarginRate_S(), app.getPayoutAlgr(), app.getPayoutRate(),
				app.getCommodityID() };

		this.logger.debug("sql: " + sql);
		getJdbcTemplate().update(sql, params);
	}

	public int checkCommodityID(Apply_T_CommodityFee app) {
		int result = 0;
		String sql = "select count(*) from t_commodity where commodityID = '" + app.getCommodityID() + "'";
		int i = getJdbcTemplate().queryForInt(sql);
		if (i > 0)
			result = 0;
		else {
			result = 1;
		}
		return result;
	}

	public int checkMarginCommodityID(Apply_T_CommodityMargin app) {
		int result = 0;
		String sql = "select count(*) from t_commodity where commodityID = '" + app.getCommodityID() + "'";
		int i = getJdbcTemplate().queryForInt(sql);
		if (i > 0)
			result = 0;
		else {
			result = 1;
		}
		return result;
	}

	public int CheckFirmAndBillFund(Apply_T_PledgeMoney apply) {
		String sql = "select t.MaxOverdraft MaxOverdraft from T_Firm t where t.firmID = '" + apply.getFirmId() + "'";
		int cnt = 0;

		List list = getJdbcTemplate().queryForList(sql);
		if ((list != null) && (list.size() > 0)) {
			Map map = (Map) list.get(0);
			if (map.get("MaxOverdraft") != null) {
				double maxOverdraft = Double.parseDouble(map.get("MaxOverdraft").toString());
				if (maxOverdraft + apply.getBillFund() < 0.0D)
					cnt = 2;
			}
		} else {
			cnt = 1;
		}
		return cnt;
	}

	public void updatePledge(Apply_T_PledgeMoney app) {
		String sqlStatus = "select count(*) from T_E_ApplyBill a where (a.status = 1 or a.status = 2) and a.BillID = '" + app.getBillID() + "'";
		this.logger.debug("sql: " + sqlStatus);
		if (getJdbcTemplate().queryForInt(sqlStatus) > 0) {
			throw new RuntimeException("此仓单号已有记录，不能重复添加！");
		}

		Long relQuantity = new Long(0L);
		if (app.getQuantity() != 0.0D) {
			relQuantity = Long.valueOf(app.getQuantity() + "");
		}
		String sql = "insert into T_E_Pledge (ID,BillID,BillFund,FirmID,CommodityName,Quantity,CreateTime,Creator,ModifyTime,Modifier) values (SEQ_T_E_PLEDGE.nextval,?,?,?,?,?,sysdate,?,sysdate,?)";

		Object[] params = { app.getBillID(), Double.valueOf(app.getBillFund()), app.getFirmId(), app.getCommodityName(), relQuantity,
				app.getApprover(), app.getApprover() };

		this.logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++)
			this.logger.debug("params[" + i + "]: " + params[i]);
		try {
			getJdbcTemplate().update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("此仓单号已有记录，不能重复添加！");
		}

		sql = "update T_Firm set MaxOverdraft = (MaxOverdraft + ?) where firmID = ?";
		params = new Object[] { Double.valueOf(app.getBillFund()), app.getFirmId() };

		this.logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++)
			this.logger.debug("params[" + i + "]: " + params[i]);
		try {
			getJdbcTemplate().update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException("修改交易商质押资金失败！");
		}
	}

	public int deletePledgeChe(Apply_T_PledgeMoney apply) {
		int result = 0;
		String sql = "select nvl(BillFund,0) from T_E_Pledge where billID = '" + apply.getBillID() + "'";
		this.logger.debug(sql);
		Object obj = null;
		try {
			obj = getJdbcTemplate().queryForObject(sql, Object.class);
		} catch (Exception localException1) {
		}
		Double billFund = new Double(0.0D);
		if (obj != null) {
			billFund = Double.valueOf(Double.parseDouble(obj.toString()));
		} else {
			result = 3;
			return result;
		}
		sql = "select firmID from T_E_Pledge where billID = '" + apply.getBillID() + "'";
		Object objF = null;
		try {
			objF = getJdbcTemplate().queryForObject(sql, Object.class);
		} catch (Exception localException2) {
		}
		String relFirmID = "";
		if (objF != null) {
			relFirmID = objF.toString();
		} else {
			result = 3;
			return result;
		}
		sql = "update T_firm set MaxOverdraft = (MaxOverdraft - " + billFund + " ) where firmID = '" + relFirmID + "'";
		try {
			getJdbcTemplate().update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改交易商最大透支额度失败！");
		}

		sql = "delete from T_E_Pledge where billID = ?";
		Object[] params = { apply.getBillID() };

		this.logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++)
			this.logger.debug("params[" + i + "]: " + params[i]);
		try {
			getJdbcTemplate().update(sql, params);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("删除失败！");
	}

	public Apply_T_PledgeMoney getApply_T_PledgeMoneyByBillID(Apply_T_PledgeMoney apply) {
		String sql = "select t.* from T_E_Pledge t where t.billID = '" + apply.getBillID() + "'";
		this.logger.debug("sql: " + sql);
		List list = getJdbcTemplate().queryForList(sql);
		if ((list != null) && (list.size() > 0)) {
			Map map = (Map) list.get(0);
			if (map.get("firmID") != null) {
				apply.setFirmId(map.get("firmID").toString());
			}
			if (map.get("billID") != null) {
				apply.setBillID(map.get("billID").toString());
			}
			if (map.get("billFund") != null) {
				apply.setBillFund(Double.parseDouble(map.get("billFund").toString()));
			}
			if (map.get("commodityName") != null) {
				apply.setCommodityName(map.get("commodityName").toString());
			}
			if (map.get("quantity") != null) {
				apply.setQuantity(Double.parseDouble(map.get("quantity").toString()));
			}
		}
		return apply;
	}
}
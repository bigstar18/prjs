package gnnt.MEBS.timebargain.manage.dao.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import gnnt.MEBS.timebargain.manage.dao.TraderDAO;
import gnnt.MEBS.timebargain.manage.model.Consigner;
import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.model.Trader;
import gnnt.MEBS.timebargain.manage.util.StringUtil;

public class TraderDAOJdbc extends BaseDAOJdbc implements TraderDAO {
	private Log log = LogFactory.getLog(TraderDAOJdbc.class);

	public Trader getTraderById(String paramString) {
		Assert.hasText(paramString);
		String str = "select * from T_TRADER where TraderID=?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		this.log.debug("TraderID:" + arrayOfObject[0]);
		Trader localTrader = null;
		try {
			localTrader = (Trader) getJdbcTemplate().queryForObject(str, arrayOfObject, new TraderRowMapper());
			return localTrader;
		} catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException) {
			throw new RuntimeException("交易员代码[" + paramString + "]不存在！");
		}
	}

	public List getTraders(Trader paramTrader) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select t.* from M_TRADER t,T_firm f where t.FirmID=f.FirmID");
		ArrayList localArrayList = new ArrayList();
		if (paramTrader != null) {
			if ((paramTrader.getFirmID() != null) && (!"".equals(paramTrader.getFirmID()))) {
				localStringBuffer.append(" and t.firmID = ?");
				localArrayList.add(paramTrader.getFirmID());
			}
			if ((paramTrader.getFirmName() != null) && (!"".equals(paramTrader.getFirmName()))) {
				localStringBuffer.append(" and f.firmName like ? || '%'");
				localArrayList.add(paramTrader.getFirmName());
			}
			if ((paramTrader.getTraderID() != null) && (!"".equals(paramTrader.getTraderID()))) {
				localStringBuffer.append(" and t.traderID like ? || '%'");
				localArrayList.add(paramTrader.getTraderID());
			}
			if ((paramTrader.getName() != null) && (!"".equals(paramTrader.getName()))) {
				localStringBuffer.append(" and t.Name like ? || '%'");
				localArrayList.add(paramTrader.getName());
			}
		}
		Object[] arrayOfObject = localArrayList.toArray();
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public List getTradeTimes(TradeTime paramTradeTime) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(
				"select GatherBid,BidStartTime,BidEndTime,sectionID,Name,StartTime,EndTime,ModifyTime, status ,case when status in(0,1) then case when status=1 then '正常' when status=0 then '无效' else '' end else '' end as status from T_A_TradeTime");
		ArrayList localArrayList = new ArrayList();
		if ((paramTradeTime != null) && (paramTradeTime.getSectionID() != null) && (!"".equals(paramTradeTime.getSectionID()))) {
			localStringBuffer.append(" where SectionID like ? || '%'");
			localArrayList.add(paramTradeTime.getSectionID());
		}
		localStringBuffer.append(" order by SectionID");
		Object[] arrayOfObject = localArrayList.toArray();
		this.log.debug("sql: " + localStringBuffer.toString());
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
	}

	public TradeTime getTradeTime(String paramString) {
		String str = "select t.* from T_A_TradeTime t where t.sectionid = ?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		return (TradeTime) getJdbcTemplate().queryForObject(str, arrayOfObject, new TradeTimeRowMapper2());
	}

	public List getTradeTimeBreed(String paramString) {
		String str = "select b.breedid,b.breedname,c.CommodityID,c.name from T_A_TradeTime tt,T_A_Breed b, T_A_CommodityTradeProp ctp, T_Commodity c where tt.sectionid = ctp.sectionid and tt.sectionid = ? and ctp.CommodityID = c.CommodityID and c.breedid = b.breedid ";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		return getJdbcTemplate().queryForList(str, arrayOfObject);
	}

	public List getTradeTimeRelBreed(String paramString) {
		String str = "select b.breedid,b.breedname from T_A_TradeTime tt,T_A_Breed b, T_A_BreedTradeProp btp where tt.sectionid = btp.sectionid and tt.sectionid = ? and b.breedid = btp.breedid";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		return getJdbcTemplate().queryForList(str, arrayOfObject);
	}

	public List getCodeNotChoose(Trader paramTrader) {
		String str1 = "select t.OperateCode OperateCode from T_TRADER t where t.traderID=?";
		Object[] arrayOfObject = { paramTrader.getTraderID() };
		this.log.debug("sql: " + str1);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
		String str2 = "";
		if ((localList != null) && (localList.size() > 0)) {
			Map localObject1 = (Map) localList.get(0);
			if (((Map) localObject1).get("OperateCode") != null) {
				str2 = ((Map) localObject1).get("OperateCode").toString();
			}
		}
		Object localObject1 = new StringBuffer();
		if ((str2 != null) && (!"".equals(str2))) {
			String[] localObject2 = str2.split(",");
			for (int i = 0; i < localObject2.length; i++) {
				if (i != localObject2.length - 1) {
					((StringBuffer) localObject1).append("'").append(localObject2[i]).append("',");
				} else {
					((StringBuffer) localObject1).append("'").append(localObject2[i]).append("'");
				}
			}
		}
		Object localObject2 = ((StringBuffer) localObject1).toString();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select c.Code code from M_TRADER t,T_CUSTOMER c where t.firmID=c.firmID");
		if ((localObject2 != null) && (!"".equals(localObject2))) {
			localStringBuffer.append(" and c.Code not in(" + (String) localObject2 + ")");
		}
		if ((paramTrader.getFirmID() != null) && (!"".equals(paramTrader.getFirmID()))) {
			localStringBuffer.append(" and t.firmID = '" + paramTrader.getFirmID() + "'");
		}
		if ((paramTrader.getTraderID() != null) && (!"".equals(paramTrader.getTraderID()))) {
			localStringBuffer.append(" and t.traderID = '" + paramTrader.getTraderID() + "'");
		}
		try {
			return getJdbcTemplate().queryForList(localStringBuffer.toString());
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("没有记录！");
		}
	}

	public List getNotOperateFirm(Consigner paramConsigner) {
		String str1 = "select c.OperateFirm operateFirm from T_CONSIGNER c where consignerID=?";
		Object[] arrayOfObject = { paramConsigner.getConsignerID() };
		this.log.debug("sql: " + str1);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		String str2 = "";
		List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
		if ((localList != null) && (localList.size() > 0)) {
			Map localObject = (Map) localList.get(0);
			if (((Map) localObject).get("operateFirm") != null) {
				str2 = ((Map) localObject).get("operateFirm").toString();
			}
		}
		Object localObject = new StringBuffer();
		String[] arrayOfString = null;
		if ((str2 != null) && (!"".equals(str2))) {
			arrayOfString = str2.split(",");
		} else {
			String str3 = "select f.firmID firmID from T_FIRM f";
			return getJdbcTemplate().queryForList(str3);
		}
		for (int i = 0; i < arrayOfString.length; i++) {
			if (i != arrayOfString.length - 1) {
				((StringBuffer) localObject).append("'").append(arrayOfString[i]).append("',");
			} else {
				((StringBuffer) localObject).append("'").append(arrayOfString[i]).append("'");
			}
		}
		String str4 = ((StringBuffer) localObject).toString();
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("select f.firmID firmID from T_FIRM f ");
		if ((str4 != null) && (!"".equals(str4))) {
			localStringBuffer.append(" where f.firmID not in (" + str4 + ")");
		}
		try {
			return getJdbcTemplate().queryForList(localStringBuffer.toString());
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("没有记录！");
		}
	}

	public String getOperateCode(Trader paramTrader) {
		String str1 = "select t.OperateCode OperateCode from T_TRADER t where t.traderID=?";
		Object[] arrayOfObject = { paramTrader.getTraderID() };
		this.log.debug("sql: " + str1);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		try {
			List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
			String str2 = "";
			if ((localList != null) && (localList.size() > 0)) {
				Map localMap = (Map) localList.get(0);
				if (localMap.get("OperateCode") != null) {
					str2 = localMap.get("OperateCode").toString();
				}
			}
			return str2;
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("没有已用代码记录！");
		}
	}

	public String getOperateFirm(Consigner paramConsigner) {
		String str = "select c.OperateFirm operateFirm from T_CONSIGNER c where c.consignerID=?";
		Object[] arrayOfObject = { paramConsigner.getConsignerID() };
		this.log.debug("sql: " + str);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		return (String) getJdbcTemplate().queryForObject(str, arrayOfObject, String.class);
	}

	public void updateOperateCode(Trader paramTrader) {
		String str = "select count(*) from T_TRADER where traderID = '" + paramTrader.getTraderID() + "'";
		if (getJdbcTemplate().queryForInt(str) > 0) {
			str = "update T_TRADER set OperateCode = ? where traderID = ?";
		} else {
			str = "insert into T_TRADER (OperateCode, traderID, modifytime) values (?,?,sysdate)";
		}
		Object[] arrayOfObject = { paramTrader.getPermission(), paramTrader.getTraderID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void updateOperateFirm(Consigner paramConsigner) {
		String str = "update T_CONSIGNER set OperateFirm=? where consignerID=?";
		Object[] arrayOfObject = { paramConsigner.getPermission(), paramConsigner.getConsignerID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("修改失败！");
		}
	}

	public void insertTrader(Trader paramTrader) {
		String str = "insert into T_TRADER(TraderID,Name,Password,Status,FirmID,OperateCode,RegWord,KeyCode,KeyStatus,CreateTime,ModifyTime) values(?,?,?,?,?,?,?,?,?,sysdate,sysdate)";
		Object[] arrayOfObject = { paramTrader.getTraderID(), paramTrader.getName(), paramTrader.getPassword(), paramTrader.getStatus(),
				paramTrader.getFirmID(), paramTrader.getOperateCode() == null ? "" : paramTrader.getOperateCode(),
				paramTrader.getRegWord() == null ? "" : paramTrader.getRegWord(), paramTrader.getKeyCode(), paramTrader.getKeyStatus() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
			localDataIntegrityViolationException.printStackTrace();
			throw new RuntimeException("主键重复，不能插入相同的记录！");
		}
	}

	public void insertTradeTime(TradeTime paramTradeTime) {
		String str = "insert into T_A_TradeTime (SectionID,Name,StartTime,EndTime,Status,ModifyTime,GatherBid,BidStartTime,BidEndTime) values (?,?,?,?,?,sysdate,?,?,?)";
		Object[] arrayOfObject = { paramTradeTime.getSectionID(), paramTradeTime.getName(), paramTradeTime.getStartTime(),
				paramTradeTime.getEndTime(), paramTradeTime.getStatus(), paramTradeTime.getGatherBid(),
				paramTradeTime.getBidStartTime() == null ? "" : paramTradeTime.getBidStartTime(),
				paramTradeTime.getBidEndTime() == null ? "" : paramTradeTime.getBidEndTime() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException1) {
			localException1.printStackTrace();
			throw new RuntimeException("交易节添加失败！");
		}
		str = "insert into T_A_DaySection (WeekDay,SectionID,Status,ModifyTime) values (?,?,0,sysdate)";
		try {
			for (int j = 1; j < 8; j++) {
				arrayOfObject = new Object[] { Integer.valueOf(j), paramTradeTime.getSectionID() };
				getJdbcTemplate().update(str, arrayOfObject);
			}
		} catch (Exception localException2) {
			localException2.printStackTrace();
			throw new RuntimeException("每日交易节添加失败！");
		}
	}

	public void updateTradeTime(TradeTime paramTradeTime) {
		String str = "update T_A_TradeTime set Name=?,StartTime=?,EndTime=?,Status=?,ModifyTime=sysdate,GatherBid=?,BidStartTime=?,BidEndTime=? where SectionID = ?";
		Object[] arrayOfObject = { paramTradeTime.getName(), paramTradeTime.getStartTime(), paramTradeTime.getEndTime(), paramTradeTime.getStatus(),
				paramTradeTime.getGatherBid(), paramTradeTime.getBidStartTime() == null ? "" : paramTradeTime.getBidStartTime(),
				paramTradeTime.getBidEndTime() == null ? "" : paramTradeTime.getBidEndTime(), paramTradeTime.getSectionID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void deleteTradeTimeById(String paramString) {
		String str = "delete from T_A_TradeTime where sectionID=?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
		str = "delete from T_A_DaySection where SectionID = ?";
		arrayOfObject = new Object[] { paramString };
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void updateTrader(Trader paramTrader) {
		String str = "update T_TRADER set Name=?,Status=?,keyCode=?,keyStatus=? where TraderID=?";
		Object[] arrayOfObject = { paramTrader.getName(), paramTrader.getStatus(), paramTrader.getKeyCode(), paramTrader.getKeyStatus(),
				paramTrader.getTraderID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void deleteTraderById(String paramString) {
		Assert.hasText(paramString);
		String str = "delete from T_TRADER where TraderID=?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		this.logger.debug("TraderID: " + arrayOfObject[0]);
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public int login(String paramString1, String paramString2) {
		Assert.hasText(paramString1);
		Assert.hasText(paramString2);
		String str = "select * from T_TRADER where TraderID=?";
		Object[] arrayOfObject = { paramString1 };
		this.log.debug("sql: " + str);
		this.log.debug("traderID:" + arrayOfObject[0]);
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if ((localList == null) || (localList.size() <= 0)) {
			return 1;
		}
		Map localMap = (Map) localList.get(0);
		if (!((String) localMap.get("Password")).equals(StringUtil.encodePassword(paramString2, "MD5"))) {
			return 2;
		}
		if (((BigDecimal) localMap.get("Status")).intValue() == 1) {
			return 3;
		}
		return 0;
	}

	public void updateTraderPassword(String paramString1, String paramString2, String paramString3) {
		String str = "update T_TRADER set Password=? where traderID=?";
		Object[] arrayOfObject = { StringUtil.encodePassword(paramString2, "MD5"), paramString3 };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void updateConsignerPassword(String paramString1, String paramString2, String paramString3) {
		String str1 = "select Password from T_CONSIGNER where ConsignerID = '" + paramString3 + "'";
		String str2 = (String) getJdbcTemplate().queryForObject(str1, String.class);
		if ((paramString1 != null) && (!"".equals(paramString1))
				&& ((str2 == null) || (!str2.equals(StringUtil.encodePassword(paramString1, "MD5"))))) {
			throw new RuntimeException("原口令输入错误！");
		}
		str1 = "update T_CONSIGNER set Password = ? where ConsignerID = '" + paramString3 + "'";
		Object[] arrayOfObject = { StringUtil.encodePassword(paramString2, "MD5") };
		this.log.debug("sql: " + str1);
		this.log.debug("sql: " + str1);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str1, arrayOfObject);
	}

	public void updateStatusT(TradeTime paramTradeTime) {
		String str = "update T_A_TradeTime set status=? where sectionID=?";
		Object[] arrayOfObject = { paramTradeTime.getStatus(), paramTradeTime.getSectionID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void updateStatusTrader(Trader paramTrader) {
		String str = "update T_TRADER set status=? where traderID=?";
		Object[] arrayOfObject = { paramTrader.getStatus(), paramTrader.getTraderID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public List getConsigner() {
		String str = "select c.* ,case when c.Status in (0,1) then case when c.Status=0 then '正常' when c.Status=1 then '禁止登陆' else '' end else '' end as Status from T_CONSIGNER c where c.consignerid <>'gnnt_condi'";
		this.log.debug("sql: " + str);
		return getJdbcTemplate().queryForList(str);
	}

	public Consigner getConsigner(String paramString) {
		String str = "select * from T_CONSIGNER where consignerID=?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		this.log.debug("params[0]: " + arrayOfObject[0]);
		return (Consigner) getJdbcTemplate().queryForObject(str, arrayOfObject, new ConsignerRowMapper());
	}

	public void insertConsigner(Consigner paramConsigner) {
		String str1 = paramConsigner.getPassword();
		if ((str1 != null) && (!"".equals(str1))) {
			str1 = paramConsigner.getPassword();
		}
		String str2 = "insert into T_CONSIGNER(ConsignerID,Name,Password,Status,OperateFirm,CreateTime,ModifyTime,Type) values(?,?,?,?,?,sysdate,sysdate,?)";
		Object[] arrayOfObject = { paramConsigner.getConsignerID(), paramConsigner.getName(), str1, paramConsigner.getStatus(), "",
				paramConsigner.getType() };
		this.log.debug("sql: " + str2);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str2, arrayOfObject);
		} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
			localDataIntegrityViolationException.printStackTrace();
			throw new RuntimeException("主键重复，不能插入相同的记录！");
		}
	}

	public void updateConsigner(Consigner paramConsigner) {
		String str = "update T_CONSIGNER set name=?,status=?,Type=? where consignerID=?";
		Object[] arrayOfObject = { paramConsigner.getName(), paramConsigner.getStatus(), paramConsigner.getType(), paramConsigner.getConsignerID() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (DataIntegrityViolationException localDataIntegrityViolationException) {
			localDataIntegrityViolationException.printStackTrace();
			throw new RuntimeException("修改失败！");
		}
	}

	public void deleteConsignerById(String paramString) {
		String str = "delete from T_CONSIGNER where consignerID=?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("删除失败！");
		}
	}

	public void updateStatusConsigner(Consigner paramConsigner) {
		String str = "update T_CONSIGNER set status=? where ConsignerID=?";
		Object[] arrayOfObject = { paramConsigner.getStatus(), paramConsigner.getConsignerID() };
		this.log.debug("sql" + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("修改失败！");
		}
	}

	public TradeTime getNotTradeDay() {
		String str1 = "select * from T_A_NotTradeDay where id = ?";
		Object[] arrayOfObject = { new Short("1") };
		String str2 = "select count(*) from T_A_NotTradeDay where id = 1";
		this.log.debug("sql: " + str1);
		if (getJdbcTemplate().queryForInt(str2) > 0) {
			return (TradeTime) getJdbcTemplate().queryForObject(str1, arrayOfObject, new NotTradeDayRowMapper());
		}
		return null;
	}

	public void insertNotTradeDay(TradeTime paramTradeTime) {
		String[] arrayOfString = paramTradeTime.getWeek();
		StringBuffer localStringBuffer = new StringBuffer();
		if (arrayOfString != null) {
			int i = 0;
			for (int j = 1; j < 8; j++) {
				for (int k = 0; k < arrayOfString.length; k++) {
					String str3 = arrayOfString[k];
					if ((j + "").equals(str3)) {
						i = 1;
					}
				}
				String str2 = "update T_A_DaySection set Status = " + i + " where WeekDay = " + j;
				getJdbcTemplate().update(str2);
				i = 0;
			}
			for (int j = 0; j < arrayOfString.length; j++) {
				if (j != arrayOfString.length - 1) {
					localStringBuffer.append(arrayOfString[j]).append(",");
				} else {
					localStringBuffer.append(arrayOfString[j]);
				}
			}
		} else {
			String str1 = "update T_A_DaySection set Status = 0";
			getJdbcTemplate().update(str1);
		}
		String str1 = "insert into T_A_NotTradeDay(ID,Week,Day,ModifyTime) values (1,?,?,sysdate)";
		Object[] arrayOfObject = { localStringBuffer.toString(), paramTradeTime.getDay() };
		this.log.debug("sql: " + str1);
		for (int m = 0; m < arrayOfObject.length; m++) {
			this.log.debug("params[" + m + "]: " + arrayOfObject[m]);
		}
		getJdbcTemplate().update(str1, arrayOfObject);
	}

	public void updateNotTradeDay(TradeTime paramTradeTime) {
		String[] arrayOfString = paramTradeTime.getWeek();
		StringBuffer localStringBuffer = new StringBuffer();
		if (arrayOfString != null) {
			int i = 0;
			for (int j = 1; j < 8; j++) {
				for (int k = 0; k < arrayOfString.length; k++) {
					String str2 = arrayOfString[k];
					if ((j + "").equals(str2)) {
						i = 1;
						String str3 = "update T_A_DaySection set Status = " + i + " where WeekDay = " + j;
						getJdbcTemplate().update(str3);
						i = 0;
					}
				}
			}
			for (int j = 0; j < arrayOfString.length; j++) {
				if (j != arrayOfString.length - 1) {
					localStringBuffer.append(arrayOfString[j]).append(",");
				} else {
					localStringBuffer.append(arrayOfString[j]);
				}
			}
		}
		String str1 = "update T_A_NotTradeDay set Week=?,Day=?,ModifyTime=sysdate where id = 1";
		Object[] arrayOfObject = { localStringBuffer.toString(), paramTradeTime.getDay() };
		this.log.debug("sql: " + str1);
		for (int k = 0; k < arrayOfObject.length; k++) {
			this.log.debug("params[" + k + "]: " + arrayOfObject[k]);
		}
		getJdbcTemplate().update(str1, arrayOfObject);
	}

	public List getTraderPrivilege(String paramString) {
		String str = "select t.*,b.breedName breedName from T_A_TRADEPRIVILEGE t,t_a_breed b where t.kindID = b.breedID and t.Type = 3 and t.Kind = 1 and t.TypeID = ?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		return getJdbcTemplate().queryForList(str, arrayOfObject);
	}

	public void deleteTraderPrivilege(String paramString) {
		String str = "delete from T_A_TRADEPRIVILEGE where TypeID = ?";
		Object[] arrayOfObject = { paramString };
		this.log.debug("sqlD: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			throw new RuntimeException("修改失败！");
		}
	}

	public void insertTraderPrivilege(Trader paramTrader) {
		String str = "insert into T_A_TRADEPRIVILEGE (ID,Type,TypeID,PrivilegeCode_B) values (SEQ_T_A_TRADEPRIVILEGE.nextval,3,?,?)";
		Object[] arrayOfObject = { paramTrader.getTraderID(), paramTrader.getPrivilegeCode_B() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			throw new RuntimeException("修改失败！");
		}
	}

	public Trader getTraderPrivilegeById(Trader paramTrader) {
		String str = "select a.* from T_A_TradePrivilege a where id = ?";
		Object[] arrayOfObject = { paramTrader.getId() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			return (Trader) getJdbcTemplate().queryForObject(str, arrayOfObject, new TraderPrivilegeMapRowMapper());
		} catch (Exception localException) {
			throw new RuntimeException("此记录不存在！");
		}
	}

	public void insertNewTraderPrivilege(Trader paramTrader) {
		String str = "insert into T_A_TradePrivilege (ID,Type,TypeID,Kind,KindID,PrivilegeCode_B,PrivilegeCode_S) values (SEQ_T_A_TRADEPRIVILEGE.nextval,3,?,1,?,?,?)";
		Object[] arrayOfObject = { paramTrader.getTypeID(), paramTrader.getKindID(), paramTrader.getPrivilegeCode_B(),
				paramTrader.getPrivilegeCode_S() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			localException.printStackTrace();
			throw new RuntimeException("添加失败！");
		}
	}

	public void updateNewTraderPrivilege(Trader paramTrader) {
		String str = "update T_A_TradePrivilege set PrivilegeCode_B = ?, PrivilegeCode_S = ? where id = ?";
		Object[] arrayOfObject = { paramTrader.getPrivilegeCode_B(), paramTrader.getPrivilegeCode_S(), paramTrader.getId() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			throw new RuntimeException("修改失败！");
		}
	}

	public void deleteTraderPrivilegeById(Trader paramTrader) {
		String str = "delete from T_A_TradePrivilege where id = ?";
		Object[] arrayOfObject = { paramTrader.getId() };
		this.log.debug("sql: " + str);
		for (int i = 0; i < arrayOfObject.length; i++) {
			this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
		}
		try {
			getJdbcTemplate().update(str, arrayOfObject);
		} catch (Exception localException) {
			throw new RuntimeException("删除失败！");
		}
	}

	public String getSysdate() {
		String str = "select to_char(sysdate,'hh24:mi:ss') sysdata from dual";
		return (String) getJdbcTemplate().queryForObject(str, String.class);
	}

	public List getTradeTimeId(String paramString) {
		String str = "select max(t.sectionid) as id from t_a_Tradetime t where t.sectionid < " + paramString;
		return getJdbcTemplate().queryForList(str);
	}

	public Map getDaySectionInfo() {
		String str1 = "select * from T_A_DaySection";
		HashMap localHashMap1 = new HashMap();
		HashMap localHashMap2 = null;
		HashMap localHashMap3 = null;
		HashMap localHashMap4 = null;
		HashMap localHashMap5 = null;
		HashMap localHashMap6 = null;
		HashMap localHashMap7 = null;
		HashMap localHashMap8 = null;
		ArrayList localArrayList1 = new ArrayList();
		ArrayList localArrayList2 = new ArrayList();
		ArrayList localArrayList3 = new ArrayList();
		ArrayList localArrayList4 = new ArrayList();
		ArrayList localArrayList5 = new ArrayList();
		ArrayList localArrayList6 = new ArrayList();
		ArrayList localArrayList7 = new ArrayList();
		List localList = getJdbcTemplate().queryForList(str1);
		if ((localList != null) && (localList.size() > 0)) {
			for (int i = 0; i < localList.size(); i++) {
				Map localMap = (Map) localList.get(i);
				String str2 = localMap.get("WeekDay") + "";
				String str3 = localMap.get("SectionID") + "";
				String str4 = localMap.get("Status") + "";
				if ("1".equals(str2)) {
					localHashMap2 = new HashMap();
					localHashMap2.put("weekDay", str2);
					localHashMap2.put("sectionID", str3);
					localHashMap2.put("status", str4);
					localArrayList1.add(localHashMap2);
				}
				if ("2".equals(str2)) {
					localHashMap3 = new HashMap();
					localHashMap3.put("weekDay", str2);
					localHashMap3.put("sectionID", str3);
					localHashMap3.put("status", str4);
					localArrayList2.add(localHashMap3);
				}
				if ("3".equals(str2)) {
					localHashMap4 = new HashMap();
					localHashMap4.put("weekDay", str2);
					localHashMap4.put("sectionID", str3);
					localHashMap4.put("status", str4);
					localArrayList3.add(localHashMap4);
				}
				if ("4".equals(str2)) {
					localHashMap5 = new HashMap();
					localHashMap5.put("weekDay", str2);
					localHashMap5.put("sectionID", str3);
					localHashMap5.put("status", str4);
					localArrayList4.add(localHashMap5);
				}
				if ("5".equals(str2)) {
					localHashMap6 = new HashMap();
					localHashMap6.put("weekDay", str2);
					localHashMap6.put("sectionID", str3);
					localHashMap6.put("status", str4);
					localArrayList5.add(localHashMap6);
				}
				if ("6".equals(str2)) {
					localHashMap7 = new HashMap();
					localHashMap7.put("weekDay", str2);
					localHashMap7.put("sectionID", str3);
					localHashMap7.put("status", str4);
					localArrayList6.add(localHashMap7);
				}
				if ("7".equals(str2)) {
					localHashMap8 = new HashMap();
					localHashMap8.put("weekDay", str2);
					localHashMap8.put("sectionID", str3);
					localHashMap8.put("status", str4);
					localArrayList7.add(localHashMap8);
				}
			}
			localHashMap1.put("1", localArrayList1);
			localHashMap1.put("2", localArrayList2);
			localHashMap1.put("3", localArrayList3);
			localHashMap1.put("4", localArrayList4);
			localHashMap1.put("5", localArrayList5);
			localHashMap1.put("6", localArrayList6);
			localHashMap1.put("7", localArrayList7);
		} else {
			localHashMap1 = null;
		}
		return localHashMap1;
	}

	public void updateDaySection(Map paramMap) {
		String str1 = "";
		for (int i = 1; i < 8; i++) {
			String str2 = i + "";
			String[] arrayOfString = (String[]) paramMap.get(str2);
			if (arrayOfString != null) {
				String str3 = "";
				for (int j = 0; j < arrayOfString.length; j++) {
					String str4 = arrayOfString[j];
					if (j != arrayOfString.length - 1) {
						str3 = str3 + str4 + ",";
					} else {
						str3 = str3 + str4;
					}
					str1 = "update T_A_DaySection set Status = 0, ModifyTime = sysdate where WeekDay = " + str2 + " and SectionID = " + str4;
					getJdbcTemplate().update(str1);
				}
				str1 = "select sectionID from T_A_DaySection where WeekDay = " + str2 + " and SectionID not in (" + str3 + ")";
				List localList = getJdbcTemplate().queryForList(str1);
				if ((localList != null) && (localList.size() > 0)) {
					for (int k = 0; k < localList.size(); k++) {
						Map localMap = (Map) localList.get(k);
						String str5 = localMap.get("sectionID") + "";
						str1 = "update T_A_DaySection set Status = 1, ModifyTime = sysdate where WeekDay = " + str2 + " and SectionID = " + str5;
						getJdbcTemplate().update(str1);
					}
				}
			} else {
				str1 = "update T_A_DaySection set Status = 1, ModifyTime = sysdate where WeekDay = " + str2;
				getJdbcTemplate().update(str1);
			}
		}
	}

	class TraderPrivilegeMapRowMapper implements RowMapper {
		TraderPrivilegeMapRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToFirmPrivilegeMap(paramResultSet);
		}

		private Trader rsToFirmPrivilegeMap(ResultSet paramResultSet) throws SQLException {
			Trader localTrader = new Trader();
			localTrader.setId(new Short(paramResultSet.getShort("ID")));
			localTrader.setType(new Short(paramResultSet.getShort("Type")));
			localTrader.setTypeID(paramResultSet.getString("TypeID"));
			localTrader.setPrivilegeCode_B(new Short(paramResultSet.getShort("PrivilegeCode_B")));
			localTrader.setPrivilegeCode_S(new Short(paramResultSet.getShort("PrivilegeCode_S")));
			localTrader.setKind(new Short(paramResultSet.getShort("Kind")));
			localTrader.setKindID(paramResultSet.getString("KindID"));
			return localTrader;
		}
	}

	class NotTradeDayRowMapper implements RowMapper {
		NotTradeDayRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToTradeTime(paramResultSet);
		}

		private TradeTime rsToTradeTime(ResultSet paramResultSet) throws SQLException {
			TradeTime localTradeTime = new TradeTime();
			localTradeTime.setId(new Short(paramResultSet.getShort("ID")));
			String str = paramResultSet.getString("week");
			if ((str != null) && (!"".equals(str))) {
				String[] arrayOfString = str.split(",");
				localTradeTime.setWeek(arrayOfString);
			}
			localTradeTime.setDay(paramResultSet.getString("day"));
			return localTradeTime;
		}
	}

	class ConsignerRowMapper implements RowMapper {
		ConsignerRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToTradeTime(paramResultSet);
		}

		private Consigner rsToTradeTime(ResultSet paramResultSet) throws SQLException {
			Consigner localConsigner = new Consigner();
			localConsigner.setConsignerID(paramResultSet.getString("ConsignerID"));
			localConsigner.setName(paramResultSet.getString("Name"));
			localConsigner.setPassword(paramResultSet.getString("Password"));
			localConsigner.setStatus(new Short(paramResultSet.getShort("Status")));
			localConsigner.setOperateFirm(paramResultSet.getString("OperateFirm"));
			localConsigner.setCreateTime(paramResultSet.getTimestamp("CreateTime"));
			localConsigner.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
			localConsigner.setType(new Short(paramResultSet.getShort("Type")));
			return localConsigner;
		}
	}

	class TradeTimeRowMapper2 implements RowMapper {
		TradeTimeRowMapper2() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToTradeTime(paramResultSet);
		}

		private TradeTime rsToTradeTime(ResultSet paramResultSet) throws SQLException {
			TradeTime localTradeTime = new TradeTime();
			localTradeTime.setSectionID(new Integer(paramResultSet.getInt("SectionID")));
			localTradeTime.setName(paramResultSet.getString("Name"));
			localTradeTime.setStartTime(paramResultSet.getString("StartTime"));
			localTradeTime.setEndTime(paramResultSet.getString("EndTime"));
			localTradeTime.setStatus(new Short(paramResultSet.getShort("Status")));
			localTradeTime.setModifyTime(paramResultSet.getTimestamp("ModifyTime").toString());
			localTradeTime.setGatherBid(new Short(paramResultSet.getShort("GatherBid")));
			localTradeTime.setBidStartTime(paramResultSet.getString("BidStartTime"));
			localTradeTime.setBidEndTime(paramResultSet.getString("BidEndTime"));
			return localTradeTime;
		}
	}

	class TradeTimeRowMapper implements RowMapper {
		TradeTimeRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToTradeTime(paramResultSet);
		}

		private TradeTime rsToTradeTime(ResultSet paramResultSet) throws SQLException {
			TradeTime localTradeTime = new TradeTime();
			localTradeTime.setSectionID(new Integer(paramResultSet.getInt("SectionID")));
			localTradeTime.setName(paramResultSet.getString("Name"));
			localTradeTime.setStartTime(paramResultSet.getString("StartTime"));
			localTradeTime.setEndTime(paramResultSet.getString("EndTime"));
			localTradeTime.setStatus(new Short(paramResultSet.getShort("Status")));
			localTradeTime.setModifyTime(paramResultSet.getTimestamp("ModifyTime").toString());
			localTradeTime.setBreedID(new Long(paramResultSet.getLong("BreedID")));
			localTradeTime.setBreedName(paramResultSet.getString("BreedName"));
			return localTradeTime;
		}
	}

	class TraderRowMapper implements RowMapper {
		TraderRowMapper() {
		}

		public Object mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
			return rsToTrader(paramResultSet);
		}

		private Trader rsToTrader(ResultSet paramResultSet) throws SQLException {
			Trader localTrader = new Trader();
			localTrader.setTraderID(paramResultSet.getString("TraderID"));
			localTrader.setName(paramResultSet.getString("Name"));
			localTrader.setPassword(paramResultSet.getString("Password"));
			localTrader.setStatus(new Short(paramResultSet.getShort("Status")));
			localTrader.setFirmID(paramResultSet.getString("FirmID"));
			localTrader.setOperateCode(paramResultSet.getString("OperateCode"));
			localTrader.setRegWord(paramResultSet.getString("RegWord"));
			localTrader.setKeyCode(paramResultSet.getString("KeyCode"));
			localTrader.setKeyStatus(new Short(paramResultSet.getShort("KeyStatus")));
			return localTrader;
		}
	}
}

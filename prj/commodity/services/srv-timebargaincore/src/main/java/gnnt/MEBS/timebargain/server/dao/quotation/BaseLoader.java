package gnnt.MEBS.timebargain.server.dao.quotation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.model.quotation.TradeTimeVO;
import gnnt.MEBS.timebargain.server.quotation.HQEngine;
import gnnt.MEBS.timebargain.server.quotation.config.Config;
import gnnt.MEBS.timebargain.server.util.DateUtil;

public class BaseLoader {
	private final transient Log log = LogFactory.getLog(BaseLoader.class);
	protected Connection conn = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	public BaseLoader(Config paramConfig) {
		getConnection(HQEngine.config);
	}

	public void getConnection() throws SQLException {
		if ((this.conn == null) || (this.conn.isClosed())) {
			getConnection(HQEngine.config);
		}
	}

	public void getConnection(Config paramConfig) {
		try {
			String str1 = (String) paramConfig.sourceMap.get("DBURL");
			String str2 = (String) paramConfig.sourceMap.get("DBDriver");
			Class.forName(str2);
			this.conn = DriverManager.getConnection(str1);
			this.conn.setAutoCommit(true);
		} catch (Exception localException) {
			this.log.error("DB error!");
			throw new RuntimeException(localException);
		}
	}

	public List loadCommodity() {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("select a.commodityid,a.name,decode(a.status,0,'N',1,'D','S') status,a.contractfactor,a.minpricemove,")
				.append("case when a.SpreadUpLmt>1 then a.SpreadUpLmt else Trunc(a.LastPrice*a.SpreadUpLmt) end as maxspread ,")
				.append("a.feerate_b,a.settlefeerate_b,a.forceclosefeerate_b,a.marginrate_b,a.ReserveCount,a.lastprice,to_char(a.marketdate,'yyyyMMdd'),")
				.append("to_char(a.settledate+1,'yyyyMMdd') settledate,b.BreedID ,b.BreedName,a.FirmMaxHoldQty from t_commodity a,T_A_Breed b ")
				.append("where a.BreedID = b.BreedID");
		return executeQuery(localStringBuilder.toString());
	}

	public long loadQuotationNO() {
		String str = "select nvl(MAX(NO), 0) NO from T_QUOTATION";
		long l = 0L;
		this.log.debug("getQuotationNO sql :" + str);
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				l = Long.parseLong(this.rs.getString("NO"));
			}
		} catch (Exception localException3) {
			localException3.printStackTrace();
		} finally {
			try {
				this.rs.close();
			} catch (Exception localException6) {
				localException6.printStackTrace();
			}
			try {
				this.ps.close();
			} catch (Exception localException7) {
				localException7.printStackTrace();
			}
		}
		return l;
	}

	public List loadHisCommodity() {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("select a.commodityid,a.name,'D' status,a.contractfactor,a.minpricemove,")
				.append("case when a.SpreadUpLmt>1 then a.SpreadUpLmt else Trunc(a.LastPrice*a.SpreadUpLmt) end as maxspread ,")
				.append("a.feerate_b,a.settlefeerate_b,a.forceclosefeerate_b,a.marginrate_b,a.ReserveCount,a.lastprice,to_char(a.marketdate,'yyyyMMdd') marketdate,")
				.append("to_char(a.settledate+1,'yyyyMMdd') settledate,a.FirmMaxHoldQty from T_SettleCommodity a ");
		return executeQuery(localStringBuilder.toString());
	}

	public List<TradeTimeVO> loadTradeTime() {
		String str = "Select * from T_A_TradeTime Where status = 1 Order by sectionid";
		ArrayList localArrayList = new ArrayList();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				TradeTimeVO localTradeTimeVO = new TradeTimeVO();
				localTradeTimeVO.orderID = this.rs.getInt("SECTIONID");
				if (this.rs.getString("STARTDATE") != null) {
					localTradeTimeVO.beginDate = Integer.parseInt(TradeTimeVO.formatStr2(this.rs.getString("STARTDATE")));
				}
				int i = Integer.parseInt(TradeTimeVO.formatStr(this.rs.getString("STARTTIME")));
				localTradeTimeVO.beginTime = (i / 100);
				if (this.rs.getString("ENDDATE") != null) {
					localTradeTimeVO.endDate = Integer.parseInt(TradeTimeVO.formatStr2(this.rs.getString("ENDDATE")));
				}
				int j = Integer.parseInt(TradeTimeVO.formatStr(this.rs.getString("ENDTIME")));
				localTradeTimeVO.endTime = (j / 100);
				localTradeTimeVO.status = this.rs.getInt("STATUS");
				localTradeTimeVO.modifytime = this.rs.getTimestamp("MODIFYTIME");
				localArrayList.add(localTradeTimeVO);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
		return localArrayList;
	}

	public Date loadSysTime() {
		String str1 = "Select to_char(SYSDATE,'yyyymmddhh24miss') from dual";
		String str2 = "";
		Date localDate = null;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str1);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				str2 = this.rs.getString(1);
			}
			localDate = DateUtil.convertStringToDate("yyyyMMddHHmmss", str2);
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
		return localDate;
	}

	public List loadProductData(Calendar paramCalendar) {
		String str = "Select * from T_QUOTATION Where CreateTime>? Order By CreateTime";
		ArrayList localArrayList = new ArrayList();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.ps.setTimestamp(1, new Timestamp(paramCalendar.getTimeInMillis()));
			this.rs = this.ps.executeQuery();
			ResultSetMetaData localResultSetMetaData = this.rs.getMetaData();
			int i = localResultSetMetaData.getColumnCount();
			while (this.rs.next()) {
				HashMap localHashMap = new HashMap();
				for (int j = 0; j < i; j++) {
					localHashMap.put(localResultSetMetaData.getColumnName(j + 1), this.rs.getObject(j + 1));
				}
				localArrayList.add(localHashMap);
			}
		} catch (Exception localException3) {
			localException3.printStackTrace();
		} finally {
			try {
				this.rs.close();
			} catch (Exception localException6) {
			}
			try {
				this.ps.close();
			} catch (Exception localException7) {
			}
		}
		return localArrayList;
	}

	public List loadProductData() {
		String str = "Select * from T_QUOTATION  Order By commodityid";
		ArrayList localArrayList = new ArrayList();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			ResultSetMetaData localResultSetMetaData = this.rs.getMetaData();
			int i = localResultSetMetaData.getColumnCount();
			while (this.rs.next()) {
				HashMap localHashMap = new HashMap();
				for (int j = 0; j < i; j++) {
					localHashMap.put(localResultSetMetaData.getColumnName(j + 1), this.rs.getObject(j + 1));
				}
				localArrayList.add(localHashMap);
			}
		} catch (Exception localException3) {
			localException3.printStackTrace();
		} finally {
			try {
				this.rs.close();
			} catch (Exception localException6) {
			}
			try {
				this.ps.close();
			} catch (Exception localException7) {
			}
		}
		return localArrayList;
	}

	public List loadAllProductData() {
		String str = "Select * from T_QUOTATION Order By CreateTime";
		ArrayList localArrayList = new ArrayList();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			ResultSetMetaData localResultSetMetaData = this.rs.getMetaData();
			int i = localResultSetMetaData.getColumnCount();
			while (this.rs.next()) {
				HashMap localHashMap = new HashMap();
				for (int j = 0; j < i; j++) {
					localHashMap.put(localResultSetMetaData.getColumnName(j + 1), this.rs.getObject(j + 1));
				}
				localArrayList.add(localHashMap);
			}
		} catch (Exception localException3) {
			localException3.printStackTrace();
		} finally {
			try {
				this.rs.close();
			} catch (Exception localException6) {
			}
			try {
				this.ps.close();
			} catch (Exception localException7) {
			}
		}
		return localArrayList;
	}

	public int getMarketStatus() {
		String str = "Select Status from T_SystemStatus";
		return queryForInt(str);
	}

	public Timestamp getQuotationTime() {
		this.log.debug("获取QUOTATION日期");
		String str = "Select MAX(CreateTime) From T_Quotation ";
		Timestamp localTimestamp = null;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				localTimestamp = this.rs.getTimestamp(1);
			}
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
		return localTimestamp;
	}

	public void loadCodeSet(HashSet paramHashSet) {
		String str = "select commodityid from t_commodity ";
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				paramHashSet.add(this.rs.getString("COMMODITYID"));
			}
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
		return;
	}

	public List executeQuery(String paramString) {
		this.log.debug(paramString);
		ArrayList localArrayList = new ArrayList();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(paramString);
			this.rs = this.ps.executeQuery(paramString);
			ResultSetMetaData localResultSetMetaData = this.rs.getMetaData();
			int i = localResultSetMetaData.getColumnCount();
			while (this.rs.next()) {
				HashMap localHashMap = new HashMap();
				for (int j = 0; j < i; j++) {
					localHashMap.put(localResultSetMetaData.getColumnName(j + 1), this.rs.getObject(j + 1));
				}
				localArrayList.add(localHashMap);
			}
		} catch (Exception localException3) {
			localException3.printStackTrace();
		} finally {
			try {
				this.rs.close();
			} catch (Exception localException6) {
			}
			try {
				this.ps.close();
			} catch (Exception localException7) {
			}
		}
		return localArrayList;
	}

	public Map getOneComty(String paramString) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("select a.commodityid,a.name,decode(a.status,0,'N',1,'D','S') status,a.contractfactor,a.minpricemove,")
				.append("case when a.SpreadUpLmt>1 then a.SpreadUpLmt else Trunc(a.LastPrice*a.SpreadUpLmt) end as maxspread ,")
				.append("a.feerate_b,a.settlefeerate_b,a.forceclosefeerate_b,a.marginrate_b,a.ReserveCount,a.lastprice,to_char(a.marketdate,'yyyyMMdd'),")
				.append("to_char(a.settledate+1,'yyyyMMdd') settledate,b.BreedID ,b.BreedName,a.FirmMaxHoldQty from t_commodity a,T_A_Breed b ")
				.append("where a.BreedID = b.BreedID and a.commodityid='").append(paramString).append("'");
		List localList = executeQuery(localStringBuilder.toString());
		Map localMap = null;
		if ((localList != null) && (localList.size() > 0)) {
			localMap = (Map) localList.get(0);
		}
		return localMap;
	}

	public String getOneCmdtyTradeSec(String paramString) {
		String str1 = "select CommodityID,SectionID from T_A_CommodityTradeProp where COMMODITYID=?  order by SectionID";
		this.log.debug("getOneCmdtyTradeSec sql:" + str1);
		StringBuilder localStringBuilder = new StringBuilder();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str1);
			this.ps.setString(1, paramString);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				String str2 = this.rs.getString("SECTIONID");
				localStringBuilder.append(str2 + ",");
			}
			if (localStringBuilder.length() > 0) {
				localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			try {
				this.rs.close();
				this.ps.close();
			} catch (Exception localException3) {
				localException3.printStackTrace();
			}
		}
		return localStringBuilder.toString();
	}

	public HashMap<String, String> getCommodityTradeSec() {
		String str1 = "select CommodityID,SectionID from T_A_CommodityTradeProp ";
		HashMap localHashMap = new HashMap();
		this.log.debug("getCommodityTradeSec sql :" + str1);
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str1);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				String str2 = this.rs.getString("COMMODITYID");
				String str3 = this.rs.getString("SECTIONID");
				String str4 = (String) localHashMap.get(str2);
				if (str4 == null) {
					str4 = str3;
				} else {
					str4 = str4 + "," + str3;
				}
				localHashMap.put(str2, str4);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			try {
				this.rs.close();
				this.ps.close();
			} catch (Exception localException3) {
				localException3.printStackTrace();
			}
		}
		return localHashMap;
	}

	public int queryForInt(String paramString) {
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(paramString);
			this.rs = this.ps.executeQuery(paramString);
			if (this.rs.next()) {
				int i = this.rs.getInt(1);
				return i;
			}
		} catch (Exception localException3) {
			localException3.printStackTrace();
		} finally {
			try {
				this.rs.close();
			} catch (Exception localException8) {
			}
			try {
				this.ps.close();
			} catch (Exception localException9) {
			}
		}
		return -1;
	}
}

package gnnt.MEBS.timebargain.server.dao.quotation.loder;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.dao.quotation.BaseLoader;
import gnnt.MEBS.timebargain.server.quotation.config.Config;

public class LoaderTimeAndDelay extends BaseLoader {
	private final transient Log log = LogFactory.getLog(LoaderTimeAndDelay.class);

	public LoaderTimeAndDelay(Config paramConfig) {
		super(paramConfig);
	}

	public List loadCommodity() {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("select a.commodityid,a.name,decode(a.status,0,'N',1,'D','S') status,a.contractfactor,a.minpricemove,")
				.append("case when a.SpreadUpLmt>1 then a.SpreadUpLmt else Trunc(a.LastPrice*a.SpreadUpLmt) end as maxspread ,")
				.append("a.feerate_b,a.settlefeerate_b,a.forceclosefeerate_b,a.marginrate_b,a.ReserveCount,a.lastprice,to_char(a.marketdate,'yyyyMMdd'),")
				.append("to_char(a.settledate+1,'yyyyMMdd') settledate,b.BreedID ,b.BreedName,a.FirmMaxHoldQty from t_commodity a,T_A_Breed b ")
				.append("where a.BreedID = b.BreedID").append(" and a.SettleWay in(0,1) ");
		return executeQuery(localStringBuilder.toString());
	}

	public List loadHisCommodity() {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("select a.commodityid,a.name,'D' status,a.contractfactor,a.minpricemove,")
				.append("case when a.SpreadUpLmt>1 then a.SpreadUpLmt else Trunc(a.LastPrice*a.SpreadUpLmt) end as maxspread ,")
				.append("a.feerate_b,a.settlefeerate_b,a.forceclosefeerate_b,a.marginrate_b,a.ReserveCount,a.lastprice,to_char(a.marketdate,'yyyyMMdd') marketdate,")
				.append("to_char(a.settledate+1,'yyyyMMdd') settledate,a.FirmMaxHoldQty from T_SettleCommodity a ")
				.append(" where a.SettleWay in(0,1) ");
		return executeQuery(localStringBuilder.toString());
	}

	public List loadProductData(Calendar paramCalendar) {
		String str = "Select q.* from T_QUOTATION q,T_Commodity c Where q.CommodityID=c.CommodityID and c.SettleWay in(0,1) and q.CreateTime>? Order By q.CreateTime ";
		List localArrayList = new ArrayList();
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

	public List loadAllProductData() {
		String str = "Select q.* from T_QUOTATION q,T_Commodity c Where q.CommodityID=c.CommodityID and c.SettleWay in(0,1) Order By q.CreateTime ";
		List localArrayList = new ArrayList();
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

	public void loadCodeSet(HashSet paramHashSet) {
		String str = "select commodityid from t_commodity where SettleWay in(0,1) ";
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

	public HashMap<String, String> getCommodityTradeSec() {
		String str1 = "select a.CommodityID,a.SectionID from T_A_CommodityTradeProp a,T_Commodity c where a.CommodityID=c.CommodityID and c.SettleWay in(0,1) ";
		HashMap<String, String> localHashMap = new HashMap();
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
}

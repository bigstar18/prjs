package gnnt.MEBS.timebargain.hqweb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import gnnt.MEBS.timebargain.hqweb.dao.QTAcquisitionDao;

public class QTAcquisitionDaoJdbc extends JdbcDaoSupport implements QTAcquisitionDao {
	private final transient Log log = LogFactory.getLog(QTAcquisitionDaoJdbc.class);
	Connection conn = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	public QTAcquisitionDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	public String loadCommodity() {
		StringBuilder sb = new StringBuilder();
		sb.append("select commodityId,name,'N',contractfactor,minPricemove,0,0,0,0,0,0,0,0,0,0,0,0 from t_commodity where status = 1");

		return executeQuery(sb.toString());
	}

	public String loadTradeTime() {
		String sql = "Select SECTIONID,STARTDATE,STARTTIME,ENDDATE,ENDTIME,STATUS,MODIFYTIME from T_A_TradeTime Where status = 1 Order by sectionid";

		return executeQueryWithDate(sql, null);
	}

	public String getCommodityTradeSec() {
		String sql = "select CommodityID,SectionID from T_A_CommodityTradeProp where commodityId in (select commodityId from t_commodity where status =1)";

		return executeQuery(sql);
	}

	public String executeQuery(String sql) {
		this.log.debug(sql);
		StringBuffer sb = new StringBuffer();
		try {
			this.ps = this.conn.prepareStatement(sql);
			this.rs = this.ps.executeQuery();
			ResultSetMetaData rsm = this.rs.getMetaData();
			int colcnt = rsm.getColumnCount();
			while (this.rs.next()) {
				for (int i = 0; i < colcnt - 1; i++) {
					sb.append(this.rs.getObject(i + 1) + ",");
				}
				sb.append(this.rs.getObject(colcnt) + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (this.rs != null) {
					this.rs.close();
					this.rs = null;
				}
				if (this.ps != null) {
					this.ps.close();
					this.ps = null;
				}
				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
				}
			} catch (Exception e1) {
				this.log.error("error:  " + e1);
			}
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
					this.rs = null;
				}
				if (this.ps != null) {
					this.ps.close();
					this.ps = null;
				}
				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
				}
			} catch (Exception e) {
				this.log.error("error:  " + e);
			}
		}
		return sb.toString();
	}

	public String executeQueryWithDate(String sql, Timestamp param) {
		this.log.debug(sql);
		StringBuffer sb = new StringBuffer();
		try {
			this.ps = this.conn.prepareStatement(sql);
			if (param != null) {
				this.ps.setTimestamp(1, param);
			}
			this.rs = this.ps.executeQuery();
			ResultSetMetaData rsm = this.rs.getMetaData();
			int colcnt = rsm.getColumnCount();
			while (this.rs.next()) {
				for (int i = 0; i < colcnt - 1; i++) {
					sb.append(this.rs.getString(i + 1) + ",");
				}
				sb.append(this.rs.getTimestamp(colcnt).getTime() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (this.rs != null) {
					this.rs.close();
					this.rs = null;
				}
				if (this.ps != null) {
					this.ps.close();
					this.ps = null;
				}
				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
				}
			} catch (Exception e1) {
				this.log.error("error:  " + e1);
			}
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
					this.rs = null;
				}
				if (this.ps != null) {
					this.ps.close();
					this.ps = null;
				}
				if (this.conn != null) {
					this.conn.close();
					this.conn = null;
				}
			} catch (Exception e) {
				this.log.error("error:  " + e);
			}
		}
		return sb.toString();
	}
}

package gnnt.MEBS.xhzc.manage.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.NamingException;

import gnnt.MEBS.xhzc.manage.CommodityCodeList;
import gnnt.MEBS.xhzc.manage.CommodityParameterValue;
import gnnt.MEBS.xhzc.manage.CommodityPropertiesValue;
import gnnt.MEBS.xhzc.manage.CommodityValue;
import gnnt.MEBS.xhzc.manage.FirmValue;
import gnnt.MEBS.xhzc.manage.GroupValue;
import gnnt.MEBS.xhzc.manage.ManagerValue;
import gnnt.MEBS.xhzc.manage.ManagerlogValue;
import gnnt.MEBS.xhzc.manage.MarketConfigValue;
import gnnt.MEBS.xhzc.manage.QueryValue;
import gnnt.MEBS.xhzc.manage.ReadCommodity;
import gnnt.MEBS.xhzc.manage.TradeDateValue;
import gnnt.MEBS.xhzc.manage.TradeTimeValue;
import gnnt.MEBS.xhzc.manage.UserLogValue;

public class ManageDAOImpl extends ManageDAO {
	protected ManageDAOImpl() throws NamingException {
	}

	public void addCommodity(CommodityValue cv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into commodity(Code,Name,Createtime,Status,Type,Modifytime,TradeUnit,TradePeriod,Bail,Charge,TransferFee,MaxStorage,Description) values(?,?,sysdate,?,?,sysdate,?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, cv.getCode());
			state.setString(2, cv.getName());
			state.setInt(3, cv.getStatus());
			state.setInt(4, cv.getType());
			state.setInt(5, cv.getTradeUnit());
			state.setInt(6, cv.getTradePeriod());
			state.setFloat(7, cv.getBail());
			state.setFloat(8, cv.getCharge());
			state.setFloat(9, cv.getTransferFee());
			state.setInt(10, cv.getMaxStorage());
			state.setString(11, cv.getDescription());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public CommodityValue getCommodity(String code) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityValue cv = null;
		try {
			conn = getConnection();
			sql = "select Code,Name,Createtime,Status,Type,Modifytime,TradeUnit,TradePeriod,Bail,Charge,TransferFee,Description,MaxStorage from commodity where code=?";
			state = conn.prepareStatement(sql);
			state.setString(1, code);
			rs = state.executeQuery();
			while (rs.next()) {
				cv = new CommodityValue();
				cv.setCode(rs.getString(1));
				cv.setName(rs.getString(2));
				cv.setCreatetime(rs.getTimestamp(3));
				cv.setStatus(rs.getInt(4));
				cv.setType(rs.getInt(5));
				cv.setModifytime(rs.getTimestamp(6));
				cv.setTradeUnit(rs.getInt(7));
				cv.setTradePeriod(rs.getInt(8));
				cv.setBail(rs.getFloat(9));
				cv.setCharge(rs.getFloat(10));
				cv.setTransferFee(rs.getFloat(11));
				cv.setDescription(rs.getString(12));
				cv.setMaxStorage(rs.getInt(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cv;
	}

	public CommodityValue getCommodityInfo(String code) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityValue cv = null;
		try {
			conn = getConnection();
			sql = "select Code,Name,Createtime,Status,Type,Modifytime,TradeUnit,TradePeriod,Bail,Charge,TransferFee,Description,MaxStorage from commodity where code=?";
			state = conn.prepareStatement(sql);
			state.setString(1, code);
			rs = state.executeQuery();
			while (rs.next()) {
				cv = new CommodityValue();
				cv.setCode(rs.getString(1));
				cv.setName(rs.getString(2));
				cv.setCreatetime(rs.getTimestamp(3));
				cv.setStatus(rs.getInt(4));
				cv.setType(rs.getInt(5));
				cv.setModifytime(rs.getTimestamp(6));
				cv.setTradeUnit(rs.getInt(7));
				cv.setTradePeriod(rs.getInt(8));
				cv.setBail(rs.getFloat(9));
				cv.setCharge(rs.getFloat(10));
				cv.setTransferFee(rs.getFloat(11));
				cv.setDescription(rs.getString(12));
				cv.setMaxStorage(rs.getInt(13));
			}
			if (cv == null) {
				cv = new CommodityValue();
				sql = "select TradeUnit,Bail,Charge,TransferFee,MaxStorage,TradePeriod from marketconfig";
				state = conn.prepareStatement(sql);
				rs = state.executeQuery();
				while (rs.next()) {
					cv.setCode(code);
					cv.setTradeUnit(rs.getInt(1));
					cv.setBail(rs.getFloat(2));
					cv.setCharge(rs.getFloat(3));
					cv.setTransferFee(rs.getFloat(4));
					cv.setMaxStorage(rs.getInt(5));
					cv.setTradePeriod(rs.getInt(6));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cv;
	}

	public void modifyCommodity(CommodityValue cv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update commodity set Name=?,Status=?,Type=?,Modifytime=sysdate,TradeUnit=?,TradePeriod=?,Bail=?,Charge=?,TransferFee=?,Description=?,MaxStorage=? where code=?";
			state = conn.prepareStatement(sql);
			state.setString(11, cv.getCode());
			state.setString(1, cv.getName());
			state.setInt(2, cv.getStatus());
			state.setInt(3, cv.getType());
			state.setInt(4, cv.getTradeUnit());
			state.setInt(5, cv.getTradePeriod());
			state.setFloat(6, cv.getBail());
			state.setFloat(7, cv.getCharge());
			state.setFloat(8, cv.getTransferFee());
			state.setString(9, cv.getDescription());
			state.setInt(10, cv.getMaxStorage());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public CommodityValue[] getCommodityList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityValue[] cvs = null;
		CommodityValue cv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select Code,Name,Createtime,Status,Type,Modifytime,TradeUnit,TradePeriod,Bail,Charge,TransferFee,Description,MaxStorage,rown from (select Code,Name,Createtime,Status,Type,Modifytime,TradeUnit,TradePeriod,Bail,Charge,TransferFee,Description,MaxStorage,rownum rown from (select * from commodity where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				cv = new CommodityValue();
				cv.setCode(rs.getString(1));
				cv.setName(rs.getString(2));
				cv.setCreatetime(rs.getTimestamp(3));
				cv.setStatus(rs.getInt(4));
				cv.setType(rs.getInt(5));
				cv.setModifytime(rs.getTimestamp(6));
				cv.setTradeUnit(rs.getInt(7));
				cv.setTradePeriod(rs.getInt(8));
				cv.setBail(rs.getFloat(9));
				cv.setCharge(rs.getFloat(10));
				cv.setTransferFee(rs.getFloat(11));
				cv.setDescription(rs.getString(12));
				cv.setMaxStorage(rs.getInt(13));
				vec.add(cv);
			}
			int size = vec.size();
			cvs = new CommodityValue[size];
			for (int i = 0; i < size; i++)
				cvs[i] = ((CommodityValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cvs;
	}

	public int getCommodityCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from commodity where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getCommodityPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from commodity where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteCommodity(String code) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from commodity where code=?";
			state = conn.prepareStatement(sql);
			state.setString(1, code);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteCommodity() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from commodity";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addFirm(FirmValue fv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into firmUser(FirmID,UserID,Password,FirmStatus,Createtime,Type,ParentID,GroupID,Modifytime) values(?,?,?,?,sysdate,?,?,?,sysdate)";
			state = conn.prepareStatement(sql);
			state.setString(1, fv.getFirmID());
			state.setString(2, fv.getUserID());
			state.setString(3, fv.getPassword());
			state.setInt(4, fv.getFirmStatus());
			state.setInt(5, fv.getType());
			state.setString(6, fv.getParentID());
			state.setString(7, fv.getGroupID());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public FirmValue getFirm(String firmID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		FirmValue fv = null;
		try {
			conn = getConnection();
			sql = "select FirmID,UserID,Password,FirmStatus,Createtime,Type,ParentID,GroupID,Modifytime from FirmUser where firmID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, firmID);
			rs = state.executeQuery();
			while (rs.next()) {
				fv = new FirmValue();
				fv.setFirmID(rs.getString(1));
				fv.setUserID(rs.getString(2));
				fv.setPassword(rs.getString(3));
				fv.setFirmStatus(rs.getInt(4));
				fv.setCreatetime(rs.getTimestamp(5));
				fv.setType(rs.getInt(6));
				fv.setParentID(rs.getString(7));
				fv.setGroupID(rs.getString(8));
				fv.setModifytime(rs.getTimestamp(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return fv;
	}

	public void modifyFirm(FirmValue fv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update FirmUser set UserID=?,Password=?,FirmStatus=?,Type=?,ParentID=?,GroupID=?,Modifytime=sysdate where firmID=?";
			state = conn.prepareStatement(sql);
			state.setString(7, fv.getFirmID());
			state.setString(1, fv.getUserID());
			state.setString(2, fv.getPassword());
			state.setInt(3, fv.getFirmStatus());
			state.setInt(4, fv.getType());
			state.setString(5, fv.getParentID());
			state.setString(6, fv.getGroupID());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public FirmValue[] getFirmList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		FirmValue[] fvs = null;
		FirmValue fv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select FirmID,UserID,Password,FirmStatus,Createtime,Type,ParentID,GroupID,Modifytime,rown from (select FirmID,UserID,Password,FirmStatus,Createtime,Type,ParentID,GroupID,Modifytime,rownum rown from (select * from FirmUser where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				fv = new FirmValue();
				fv.setFirmID(rs.getString(1));
				fv.setUserID(rs.getString(2));
				fv.setPassword(rs.getString(3));
				fv.setFirmStatus(rs.getInt(4));
				fv.setCreatetime(rs.getTimestamp(5));
				fv.setType(rs.getInt(6));
				fv.setParentID(rs.getString(7));
				fv.setGroupID(rs.getString(8));
				fv.setModifytime(rs.getTimestamp(9));

				vec.add(fv);
			}
			int size = vec.size();
			fvs = new FirmValue[size];
			for (int i = 0; i < size; i++)
				fvs[i] = ((FirmValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return fvs;
	}

	public int getFirmCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from FirmUser where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getFirmPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from FirmUser where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteFirm(String firmID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from FirmUser where firmID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, firmID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteFirm() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from FirmUser";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addGroup(GroupValue gv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into GroupInfo(GroupID,GroupName,Description) values(?,?,?)";

			state = conn.prepareStatement(sql);
			state.setString(1, gv.getGroupID());
			state.setString(2, gv.getGroupName());
			state.setString(3, gv.getDescription());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public GroupValue getGroup(String groupID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		GroupValue gv = null;
		try {
			conn = getConnection();
			sql = "select GroupID,GroupName,Description from GroupInfo where groupID=?";

			state = conn.prepareStatement(sql);
			state.setString(1, groupID);
			rs = state.executeQuery();
			while (rs.next()) {
				gv = new GroupValue();
				gv.setGroupID(rs.getString(1));
				gv.setGroupName(rs.getString(2));
				gv.setDescription(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return gv;
	}

	public void modifyGroup(GroupValue gv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update GroupInfo set GroupName=?,Description=? where groupID=?";

			state = conn.prepareStatement(sql);
			state.setString(3, gv.getGroupID());
			state.setString(1, gv.getGroupName());
			state.setString(2, gv.getDescription());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public GroupValue[] getGroupList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		GroupValue[] gvs = null;
		GroupValue gv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select GroupID,GroupName,Description,rown from (select GroupID,GroupName,Description,rownum rown from (select * from GroupInfo where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				gv = new GroupValue();
				gv.setGroupID(rs.getString(1));
				gv.setGroupName(rs.getString(2));
				gv.setDescription(rs.getString(3));

				vec.add(gv);
			}
			int size = vec.size();
			gvs = new GroupValue[size];
			for (int i = 0; i < size; i++)
				gvs[i] = ((GroupValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return gvs;
	}

	public int getGroupCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from GroupInfo where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getGroupPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from GroupInfo where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteGroup(String groupID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from GroupInfo where groupID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, groupID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteGroup() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from GroupInfo";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addManager(ManagerValue mv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into MarketUser(UserID,Name,Password,Createtime,Userlevel,UserAcl) values(?,?,?,sysdate,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, mv.getUserID());
			state.setString(2, mv.getName());
			state.setString(3, mv.getPassword());
			state.setInt(4, mv.getUserlevel());
			state.setString(5, mv.getUserAcl());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public ManagerValue getManager(String userID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		ManagerValue mv = null;
		try {
			conn = getConnection();
			sql = "select UserID,Name,Password,Createtime,Userlevel,UserAcl from marketUser where UserID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, userID);
			rs = state.executeQuery();
			while (rs.next()) {
				mv = new ManagerValue();
				mv.setUserID(rs.getString(1));
				mv.setName(rs.getString(2));
				mv.setPassword(rs.getString(3));
				mv.setCreatetime(rs.getTimestamp(4));
				mv.setUserlevel(rs.getInt(5));
				mv.setUserAcl(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return mv;
	}

	public void modifyManager(ManagerValue mv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update marketUser set Name=?,Password=?,Userlevel=?,UserAcl=? where userID=?";

			state = conn.prepareStatement(sql);
			state.setString(5, mv.getUserID());
			state.setString(1, mv.getName());
			state.setString(2, mv.getPassword());
			state.setInt(3, mv.getUserlevel());
			state.setString(4, mv.getUserAcl());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public ManagerValue[] getManagerList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		ManagerValue[] mvs = null;
		ManagerValue mv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select UserID,Name,Password,Createtime,Userlevel,UserAcl,rown from (select UserID,Name,Password,Createtime,Userlevel,UserAcl,rownum rown from (select * from marketUser where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				mv = new ManagerValue();
				mv.setUserID(rs.getString(1));
				mv.setName(rs.getString(2));
				mv.setPassword(rs.getString(3));
				mv.setCreatetime(rs.getTimestamp(4));
				mv.setUserlevel(rs.getInt(5));
				mv.setUserAcl(rs.getString(6));

				vec.add(mv);
			}
			int size = vec.size();
			mvs = new ManagerValue[size];
			for (int i = 0; i < size; i++)
				mvs[i] = ((ManagerValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return mvs;
	}

	public int getManagerCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from marketUser where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getManagerPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from marketUser where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteManager(String userID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from marketUser where userID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, userID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteManager() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from marketUser";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public boolean checkManager(String mid) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "select count(*) from marketUser where userid='" + mid.replaceAll(" or ", "").replaceAll("'", "") + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				if (rs.getInt(1) > 0)
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return false;
	}

	public boolean checkManager(String mid, String password) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "select count(*) from marketUser where userid='" + mid.replaceAll(" or ", "").replaceAll("'", "") + "' and password='"
					+ password.replaceAll(" or ", "").replaceAll("'", "") + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				if (rs.getInt(1) > 0)
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return false;
	}

	public boolean checkFirm(String fid) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "select count(*) from firmUser where FirmID='" + fid.replaceAll(" or ", "").replaceAll("'", "") + "' ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				if (rs.getInt(1) > 0)
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return false;
	}

	public boolean checkFirm(String fid, String password) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "select count(*) from firmUser where FirmID='" + fid.replaceAll(" or ", "").replaceAll("'", "") + "' and password='"
					+ password.replaceAll(" or ", "").replaceAll("'", "") + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				if (rs.getInt(1) > 0)
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return false;
	}

	public void addManagerLog(ManagerlogValue mlv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into marketOperate(LogID,OperatorID,OperateTime,Commond,FirmID,Content) values(seq_marketOperate.nextVal,?,sysdate,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, mlv.getOperatorID());
			state.setString(2, mlv.getCommond());
			state.setString(3, mlv.getFirmID());
			state.setString(4, mlv.getContent());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public ManagerlogValue getManagerLog(int logID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		ManagerlogValue mlv = null;
		try {
			conn = getConnection();
			sql = "select LogID,OperatorID,OperateTime,Commond,FirmID,Content from marketOperate where logID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, logID);
			rs = state.executeQuery();
			while (rs.next()) {
				mlv = new ManagerlogValue();
				mlv.setLogID(rs.getInt(1));
				mlv.setOperatorID(rs.getString(2));
				mlv.setOperateTime(rs.getTimestamp(3));
				mlv.setCommond(rs.getString(4));
				mlv.setFirmID(rs.getString(5));
				mlv.setContent(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return mlv;
	}

	public void modifyManagerLog(ManagerlogValue mlv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update marketOperate set OperatorID=?,Commond=?,FirmID=?,Content=? where LogID=?";
			state = conn.prepareStatement(sql);
			state.setInt(5, mlv.getLogID());
			state.setString(1, mlv.getOperatorID());
			state.setString(2, mlv.getCommond());
			state.setString(3, mlv.getFirmID());
			state.setString(4, mlv.getContent());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public ManagerlogValue[] getManagerLogList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		ManagerlogValue[] mlvs = null;
		ManagerlogValue mlv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select LogID,OperatorID,OperateTime,Commond,FirmID,Content,rown from (select LogID,OperatorID,OperateTime,Commond,FirmID,Content,rownum rown from (select * from marketOperate where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				mlv = new ManagerlogValue();
				mlv.setLogID(rs.getInt(1));
				mlv.setOperatorID(rs.getString(2));
				mlv.setOperateTime(rs.getTimestamp(3));
				mlv.setCommond(rs.getString(4));
				mlv.setFirmID(rs.getString(5));
				mlv.setContent(rs.getString(6));

				vec.add(mlv);
			}
			int size = vec.size();
			mlvs = new ManagerlogValue[size];
			for (int i = 0; i < size; i++)
				mlvs[i] = ((ManagerlogValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return mlvs;
	}

	public int getManagerLogCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from marketOperate where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getManagerLogPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from marketOperate where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteManagerLog(int logID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from marketOperate where logID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, logID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteManagerLog() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from marketOperate";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addUserLog(UserLogValue ulv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into traderLogon(LogID,FirmID,TraderID,Onoff,Logtime,Logtype) values(seq_traderLogon.nextVal,?,?,?,sysdate,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, ulv.getFirmID());
			state.setString(2, ulv.getTraderID());
			state.setInt(3, ulv.getOnoff());
			state.setInt(4, ulv.getLogtype());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public UserLogValue getUserLog(int logID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		UserLogValue ulv = null;
		try {
			conn = getConnection();
			sql = "select LogID,FirmID,TraderID,Onoff,Logtime,Logtype from traderLogon where logID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, logID);
			rs = state.executeQuery();
			while (rs.next()) {
				ulv = new UserLogValue();
				ulv.setLogID(rs.getInt(1));
				ulv.setFirmID(rs.getString(2));
				ulv.setTraderID(rs.getString(3));
				ulv.setOnoff(rs.getInt(4));
				ulv.setLogtime(rs.getTimestamp(5));
				ulv.setLogtype(rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return ulv;
	}

	public void modifyUserLog(UserLogValue ulv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update traderLogon set FirmID=?,TraderID=?,Onoff=?,Logtype=? where LogID=?";

			state = conn.prepareStatement(sql);
			state.setInt(5, ulv.getLogID());
			state.setString(1, ulv.getFirmID());
			state.setString(2, ulv.getTraderID());
			state.setInt(3, ulv.getOnoff());
			state.setInt(4, ulv.getLogtype());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public UserLogValue[] getUserLogList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		UserLogValue[] ulvs = null;
		UserLogValue ulv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select LogID,FirmID,TraderID,Onoff,Logtime,Logtype,rown from (select LogID,FirmID,TraderID,Onoff,Logtime,Logtype,rownum rown from (select * from traderLogon where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ulv = new UserLogValue();
				ulv.setLogID(rs.getInt(1));
				ulv.setFirmID(rs.getString(2));
				ulv.setTraderID(rs.getString(3));
				ulv.setOnoff(rs.getInt(4));
				ulv.setLogtime(rs.getTimestamp(5));
				ulv.setLogtype(rs.getInt(6));

				vec.add(ulv);
			}
			int size = vec.size();
			ulvs = new UserLogValue[size];
			for (int i = 0; i < size; i++)
				ulvs[i] = ((UserLogValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return ulvs;
	}

	public int getUserLogCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from traderLogon where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getUserLogPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from traderLogon where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteUserLog(int logID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from traderLogon where logID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, logID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteUserLog() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from traderLogon";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addTradeDate(TradeDateValue tdv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into TradeDate(ID,WeekRest,YearRest) values(seq_TradeDate.nextVal,?,?)";

			state = conn.prepareStatement(sql);
			state.setString(1, tdv.getWeekRest());
			state.setString(2, tdv.getYearRest());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public TradeDateValue getTradeDate(int ID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		TradeDateValue tdv = null;
		try {
			conn = getConnection();
			sql = "select ID,WeekRest,YearRest from TradeDate where ID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, ID);
			rs = state.executeQuery();
			while (rs.next()) {
				tdv = new TradeDateValue();
				tdv.setID(rs.getInt(1));
				tdv.setWeekRest(rs.getString(2));
				tdv.setYearRest(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return tdv;
	}

	public void modifyTradeDate(TradeDateValue tdv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update TradeDate set WeekRest=?,YearRest=? where ID=?";
			state = conn.prepareStatement(sql);
			state.setInt(3, tdv.getID());
			state.setString(1, tdv.getWeekRest());
			state.setString(2, tdv.getYearRest());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public TradeDateValue[] getTradeDateList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		TradeDateValue[] tdvs = null;
		TradeDateValue tdv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select ID,WeekRest,YearRest,rown from (select ID,WeekRest,YearRest,rownum rown from (select * from TradeDate where 1=1 " + filter
					+ " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				tdv = new TradeDateValue();
				tdv.setID(rs.getInt(1));
				tdv.setWeekRest(rs.getString(2));
				tdv.setYearRest(rs.getString(3));

				vec.add(tdv);
			}
			int size = vec.size();
			tdvs = new TradeDateValue[size];
			for (int i = 0; i < size; i++)
				tdvs[i] = ((TradeDateValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return tdvs;
	}

	public int getTradeDateCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from TradeDate where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getTradeDatePGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from TradeDate where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteTradeDate(int ID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from TradeDate where ID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, ID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteTradeDate() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from TradeDate";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addTradeTime(TradeTimeValue ttv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into TradeTime(OrderID,Type,StartTime,SpanTime,Status,Modifytime,runType) values(?,?,?,?,?,sysdate,?)";
			state = conn.prepareStatement(sql);
			state.setInt(1, ttv.getOrderID());
			state.setInt(2, ttv.getType());
			state.setString(3, ttv.getStartTime());
			state.setInt(4, ttv.getSpanTime());
			state.setInt(5, ttv.getStatus());
			state.setInt(6, ttv.getRunType());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public TradeTimeValue getTradeTime(int orderID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		TradeTimeValue ttv = null;
		try {
			conn = getConnection();
			sql = "select OrderID,Type,StartTime,SpanTime,Status,Modifytime,runType from TradeTime where orderID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, orderID);
			rs = state.executeQuery();
			while (rs.next()) {
				ttv = new TradeTimeValue();
				ttv.setOrderID(rs.getInt(1));
				ttv.setType(rs.getInt(2));
				ttv.setStartTime(rs.getString(3));
				ttv.setSpanTime(rs.getInt(4));
				ttv.setStatus(rs.getInt(5));
				ttv.setModifytime(rs.getTimestamp(6));
				ttv.setRunType(rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return ttv;
	}

	public void modifyTradeTime(TradeTimeValue ttv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update TradeTime set Type=?,StartTime=?,SpanTime=?,Status=?,RunType=?,Modifytime=sysdate where orderID=?";
			state = conn.prepareStatement(sql);
			state.setInt(6, ttv.getOrderID());
			state.setInt(1, ttv.getType());
			state.setString(2, ttv.getStartTime());
			state.setInt(3, ttv.getSpanTime());
			state.setInt(4, ttv.getStatus());
			state.setInt(5, ttv.getRunType());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public TradeTimeValue[] getTradeTimeList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		TradeTimeValue[] ttvs = null;
		TradeTimeValue ttv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select OrderID,Type,StartTime,SpanTime,Status,Modifytime,RunType,rown from (select OrderID,Type,StartTime,SpanTime,Status,Modifytime,RunType,rownum rown from (select * from TradeTime where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ttv = new TradeTimeValue();
				ttv.setOrderID(rs.getInt(1));
				ttv.setType(rs.getInt(2));
				ttv.setStartTime(rs.getString(3));
				ttv.setSpanTime(rs.getInt(4));
				ttv.setStatus(rs.getInt(5));
				ttv.setModifytime(rs.getTimestamp(6));
				ttv.setRunType(rs.getInt(7));

				vec.add(ttv);
			}
			int size = vec.size();
			ttvs = new TradeTimeValue[size];
			for (int i = 0; i < size; i++)
				ttvs[i] = ((TradeTimeValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return ttvs;
	}

	public TradeTimeValue[] getAllTradeTime() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		TradeTimeValue[] ttvs = null;
		TradeTimeValue ttv = null;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select OrderID,Type,StartTime,SpanTime,Status,Modifytime,RunType from tradetime order by OrderID,Type";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ttv = new TradeTimeValue();
				ttv.setOrderID(rs.getInt(1));
				ttv.setType(rs.getInt(2));
				ttv.setStartTime(rs.getString(3));
				ttv.setSpanTime(rs.getInt(4));
				ttv.setStatus(rs.getInt(5));
				ttv.setModifytime(rs.getTimestamp(6));
				ttv.setRunType(rs.getInt(7));

				vec.add(ttv);
			}
			int size = vec.size();
			ttvs = new TradeTimeValue[size];
			for (int i = 0; i < size; i++)
				ttvs[i] = ((TradeTimeValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return ttvs;
	}

	public int getTradeTimeCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from TradeTime where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getTradeTimePGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from TradeTime where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteTradeTime(int orderID) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from TradeTime where orderID=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, orderID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteTradeTime() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from TradeTime";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addMarketConfig(MarketConfigValue mcv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into MarketConfig(MarketName,WeekRest,YearRest,Description,TradeUnit,Bail,Charge,TransferFee,TradePeriod,MaxStorage) values(?,?,?,?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, mcv.getMarketName());
			state.setString(2, mcv.getWeekRest());
			state.setString(3, mcv.getYearRest());
			state.setString(4, mcv.getDescription());
			state.setFloat(5, mcv.getTradeUnit());
			state.setFloat(6, mcv.getBail());
			state.setFloat(7, mcv.getCharge());
			state.setFloat(8, mcv.getTransferFee());
			state.setInt(9, mcv.getTradePeriod());
			state.setInt(10, mcv.getMaxStorage());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public MarketConfigValue getMarketConfig(String marketName) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		MarketConfigValue mcv = null;
		try {
			conn = getConnection();
			sql = "select MarketName,WeekRest,YearRest,Description,TradeUnit,Bail,Charge,TransferFee,TradePeriod,MaxStorage from marketconfig where marketName=?";
			state = conn.prepareStatement(sql);
			state.setString(1, marketName);
			rs = state.executeQuery();
			while (rs.next()) {
				mcv = new MarketConfigValue();
				mcv.setMarketName(rs.getString(1));
				mcv.setWeekRest(rs.getString(2));
				mcv.setYearRest(rs.getString(3));
				mcv.setDescription(rs.getString(4));
				mcv.setTradeUnit(rs.getFloat(5));
				mcv.setBail(rs.getFloat(6));
				mcv.setCharge(rs.getFloat(7));
				mcv.setTransferFee(rs.getFloat(8));
				mcv.setTradePeriod(rs.getInt(9));
				mcv.setMaxStorage(rs.getInt(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return mcv;
	}

	public int[] getWeekRest() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int[] weekRest = null;
		String weekRestStr = "";
		try {
			conn = getConnection();
			sql = "select WeekRest from marketconfig";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				weekRestStr = rs.getString(1);
			}
			if (weekRestStr == null) {
				weekRestStr = "";
			}

			StringTokenizer st = new StringTokenizer(weekRestStr, ",");
			Vector vec = new Vector();
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				try {
					vec.add(s);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			int size = vec.size();
			weekRest = new int[size];
			for (int i = 0; i < size; i++) {
				weekRest[i] = Integer.parseInt((String) vec.get(i));
			}

			for (int i = 0; i < size - 1; i++) {
				for (int j = 0; j < size - i - 1; j++)
					if (weekRest[j] > weekRest[(j + 1)]) {
						int t = 0;
						t = weekRest[j];
						weekRest[j] = weekRest[(j + 1)];
						weekRest[(j + 1)] = t;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return weekRest;
	}

	public int[] getYearRest() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int[] yearRest = null;
		String yearRestStr = "";
		try {
			conn = getConnection();
			sql = "select YearRest from marketconfig";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				yearRestStr = rs.getString(1);
			}
			if (yearRestStr == null)
				yearRestStr = "";
			StringTokenizer st = new StringTokenizer(yearRestStr, ",");
			Vector vec = new Vector();
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				try {
					vec.add(s);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			int size = vec.size();
			yearRest = new int[size];
			for (int i = 0; i < size; i++) {
				yearRest[i] = Integer.parseInt((String) vec.get(i));
			}

			for (int i = 0; i < size - 1; i++) {
				for (int j = 0; j < size - i - 1; j++)
					if (yearRest[j] > yearRest[(j + 1)]) {
						int t = 0;
						t = yearRest[j];
						yearRest[j] = yearRest[(j + 1)];
						yearRest[(j + 1)] = t;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return yearRest;
	}

	public void modifyMarketConfig(MarketConfigValue mcv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update marketconfig set MarketName=?,WeekRest=?,YearRest=?,Description=?,TradeUnit=?,Bail=?,Charge=?,TransferFee=?,TradePeriod=?,MaxStorage=? where marketname=?";
			state = conn.prepareStatement(sql);
			state.setString(1, mcv.getMarketName());
			state.setString(2, mcv.getWeekRest());
			state.setString(3, mcv.getYearRest());
			state.setString(4, mcv.getDescription());
			state.setFloat(5, mcv.getTradeUnit());
			state.setFloat(6, mcv.getBail());
			state.setFloat(7, mcv.getCharge());
			state.setFloat(8, mcv.getTransferFee());
			state.setInt(9, mcv.getTradePeriod());
			state.setInt(10, mcv.getMaxStorage());
			state.setString(11, mcv.getMarketName());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public MarketConfigValue[] getMarketConfigList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		MarketConfigValue[] mcvs = null;
		MarketConfigValue mcv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select MarketName,WeekRest,YearRest,Description,TradeUnit,Bail,Charge,TransferFee,TradePeriod,MaxStorage,rown from (select MarketName,WeekRest,YearRest,Description,TradeUnit,Bail,Charge,TransferFee,TradePeriod,MaxStorage,rownum rown from (select * from marketconfig where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				mcv = new MarketConfigValue();
				mcv.setMarketName(rs.getString(1));
				mcv.setWeekRest(rs.getString(2));
				mcv.setYearRest(rs.getString(3));
				mcv.setDescription(rs.getString(4));
				mcv.setTradeUnit(rs.getFloat(5));
				mcv.setBail(rs.getFloat(6));
				mcv.setCharge(rs.getFloat(7));
				mcv.setTransferFee(rs.getFloat(8));
				mcv.setTradePeriod(rs.getInt(9));
				mcv.setMaxStorage(rs.getInt(10));

				vec.add(mcv);
			}
			int size = vec.size();
			mcvs = new MarketConfigValue[size];
			for (int i = 0; i < size; i++)
				mcvs[i] = ((MarketConfigValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return mcvs;
	}

	public int getMarketConfigCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from MarketConfig where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getMarketConfigPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from MarketConfig where 1=1 " + qv.filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteMarketConfig(String marketName) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from MarketConfig where marketname=?";
			state = conn.prepareStatement(sql);
			state.setString(1, marketName);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteMarketConfig() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from marketConfig";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addCommodityProperties(CommodityPropertiesValue cpv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into commodityProperties(ID,Name,Description,ParentID) values(seq_commodityProperties.nextVal,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, cpv.getName());
			state.setString(2, cpv.getDescription());
			state.setInt(3, cpv.getParentID());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public CommodityPropertiesValue getCommodityProperties(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityPropertiesValue cpv = null;
		try {
			conn = getConnection();
			sql = "select ID,Name,Description,ParentID from commodityProperties where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			rs = state.executeQuery();
			while (rs.next()) {
				cpv = new CommodityPropertiesValue();
				cpv.setID(rs.getInt(1));
				cpv.setName(rs.getString(2));
				cpv.setDescription(rs.getString(3));
				cpv.setParentID(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cpv;
	}

	public void modifyCommodityProperties(CommodityPropertiesValue cpv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update commodityProperties set Name=?,Description=?,ParentID=? where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(4, cpv.getID());
			state.setString(1, cpv.getName());
			state.setString(2, cpv.getDescription());
			state.setInt(3, cpv.getParentID());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public CommodityPropertiesValue[] getCommodityPropertiesList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityPropertiesValue[] cpvs = null;
		CommodityPropertiesValue cpv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select ID,Name,Description,ParentID,rown from (select ID,Name,Description,ParentID,rownum rown from (select * from commodityProperties where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				cpv = new CommodityPropertiesValue();
				cpv.setID(rs.getInt(1));
				cpv.setName(rs.getString(2));
				cpv.setDescription(rs.getString(3));
				cpv.setParentID(rs.getInt(4));

				vec.add(cpv);
			}
			int size = vec.size();
			cpvs = new CommodityPropertiesValue[size];
			for (int i = 0; i < size; i++)
				cpvs[i] = ((CommodityPropertiesValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cpvs;
	}

	public int getCommodityPropertiesCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from commodityProperties where 1=1 " + qv.filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getCommodityPropertiesPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from commodityProperties where 1=1 " + qv.filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteCommodityProperties(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from commodityProperties where id=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, id);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteCommodityProperties() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from commodityProperties";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void addCommodityParameter(CommodityParameterValue cpv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "insert into commodityParameter(ID,ClassID,Name,Description,RelatedID) values(?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, cpv.getID());
			state.setInt(2, cpv.getClassID());
			state.setString(3, cpv.getName());
			state.setString(4, cpv.getDescription());
			state.setString(5, cpv.getRelatedID());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public CommodityParameterValue getCommodityParameter(String id, int cid) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityParameterValue cpv = null;
		try {
			conn = getConnection();
			sql = "select ID,ClassID,Name,Description,RelatedID from commodityParameter where id=? and classid=?";
			state = conn.prepareStatement(sql);
			state.setString(1, id);
			state.setInt(2, cid);
			rs = state.executeQuery();
			while (rs.next()) {
				cpv = new CommodityParameterValue();
				cpv.setID(rs.getString(1));
				cpv.setClassID(rs.getInt(2));
				cpv.setName(rs.getString(3));
				cpv.setDescription(rs.getString(4));
				cpv.setRelatedID(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cpv;
	}

	public void modifyCommodityParameter(CommodityParameterValue cpv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "update commodityParameter set ID=?,ClassID=?,Name=?,Description=?,RelatedID=? where id=? and classid=?";
			state = conn.prepareStatement(sql);
			state.setString(1, cpv.getID());
			state.setInt(2, cpv.getClassID());
			state.setString(3, cpv.getName());
			state.setString(4, cpv.getDescription());
			state.setString(5, cpv.getRelatedID());
			state.setString(6, cpv.getID());
			state.setInt(7, cpv.getClassID());

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public CommodityParameterValue[] getCommodityParameterList(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		CommodityParameterValue[] cpvs = null;
		CommodityParameterValue cpv = null;
		int pageIndex = qv.pageIndex;
		int pageSize = qv.pageSize;
		String filter = qv.filter;
		int pageStart = (pageIndex - 1) * pageSize + 1;
		int pageEnd = pageIndex * pageSize;
		Vector vec = new Vector();
		try {
			conn = getConnection();
			sql = "select ID,ClassID,Name,Description,RelatedID,rown from (select ID,ClassID,Name,Description,RelatedID,rownum rown from (select * from commodityParameter where 1=1 "
					+ filter + " )) where rown between " + pageStart + " and " + pageEnd + " ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				cpv = new CommodityParameterValue();
				cpv.setID(rs.getString(1));
				cpv.setClassID(rs.getInt(2));
				cpv.setName(rs.getString(3));
				cpv.setDescription(rs.getString(4));
				cpv.setRelatedID(rs.getString(5));

				vec.add(cpv);
			}
			int size = vec.size();
			cpvs = new CommodityParameterValue[size];
			for (int i = 0; i < size; i++)
				cpvs[i] = ((CommodityParameterValue) vec.get(i));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cpvs;
	}

	public int getCommodityParameterCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from commodityParameter where 1=1 " + qv.filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return cnt;
	}

	public int getCommodityParameterPGCount(QueryValue qv) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		int cnt = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from commodityParameter where 1=1 " + qv.filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next())
				cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		if (cnt % qv.pageSize > 0) {
			return cnt / qv.pageSize + 1;
		}
		return cnt / qv.pageSize;
	}

	public void deleteCommodityParameter(String id, int cid) throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from commodityParameter where id=? and classid=?";
			state = conn.prepareStatement(sql);
			state.setString(1, id);
			state.setInt(2, cid);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void deleteCommodityParameter() throws SQLException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "delete from commodityParameter";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public void initCommodityCodeList() throws Exception {
		try {
			ReadCommodity rc = new ReadCommodity();
			rc.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	public boolean checkCommodityCode(String code) throws Exception {
		boolean result = false;
		try {
			CommodityCodeList ccl = new CommodityCodeList();
			if (ccl.getValue(code) != null) {
				result = true;
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
		// return result;
	}

	public boolean checkCommodityFinished() throws Exception {
		boolean result = false;
		try {
			CommodityCodeList ccl = new CommodityCodeList();
			if (ccl.getFlag() == 1) {
				result = true;
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
		// return result;
	}
}
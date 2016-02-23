package gnnt.bank.adapter.dao;
import gnnt.bank.adapter.AdapterFirmVO;
import gnnt.bank.adapter.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
public abstract class AdapterDAO {
	/** 数据库访问地址 */
	private String _DBUrl;
	/** 数据库访问用户 */
	private String _DBUser;
	/** 数据库访问口令 */
	private String _DBPwd;
	/**构造函数*/
	protected AdapterDAO(){
		_DBUrl = Tool.getConfig(Tool.DBUrl);
		_DBUser = Tool.getConfig(Tool.DBUser);
		_DBPwd = Tool.getConfig(Tool.DBPwd);
	}
	/**
	 * 获取数据库连接
	 * @return Connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException,ClassNotFoundException{
		Connection result = null;
		try {
			Class.forName(Tool.oracle);
			result = DriverManager.getConnection(_DBUrl,_DBUser,_DBPwd);
		} catch(ClassNotFoundException e) {
			throw e;
		} catch(SQLException e){
			throw e;
		}
		return result;
	}
	/**
	 * 关闭数据库连接
	 * @param rs ResultSet
	 * @param state Statement
	 * @param conn Connection
	 */
	public void closeStatement(ResultSet rs, Statement state,Connection conn) {
		try {
			if (rs != null){
				rs.close();
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    try {
	    	if (state != null){
	    		state.close();
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    try {
	    	if (conn != null){
	    		conn.close();
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
	/**
	 * 交易商签约，增加交易商
	 * @param value 交易商对象
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract int addFirm(AdapterFirmVO value)throws SQLException,ClassNotFoundException;
	/**
	 * 交易商解约，删除交易商
	 * @param firmID 交易商代码
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract int delFirm(String bankID,String firmID)throws SQLException,ClassNotFoundException;
	/**
	 * 交易商入金，减少交易商资金
	 * @param value 交易商对象
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract int inMoney(AdapterFirmVO value)throws SQLException,ClassNotFoundException;
	/**
	 * 交易商出金，增加交易商资金
	 * @param value 交易商对象
	 * @return int
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract int outMoney(AdapterFirmVO value)throws SQLException,ClassNotFoundException;
	/**
	 * 查询交易商余额
	 * @param firmID 交易商代码
	 * @return AdapterFirmVO
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract AdapterFirmVO getFirmVO(String bankID,String firmID) throws SQLException,ClassNotFoundException;
	/**
	 * 取得交易商代码
	 */
	public abstract String getfirmID(java.util.Date date,String bankID) throws SQLException,ClassNotFoundException; 
	/**
	 * 查询市场流水信息
	 */
	public abstract Vector<CapitalValue> getCapitalValue(String bankID,java.util.Date date,String firmID) throws SQLException,ClassNotFoundException;
}

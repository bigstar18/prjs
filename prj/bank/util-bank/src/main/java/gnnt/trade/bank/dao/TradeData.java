package gnnt.trade.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 交易系统数据
 * @author 秦盼龙
 *
 */
public class TradeData 
{
	
	/**
	 * 判断是否闭市
	 * @param conn   
	 * @return true：闭市 false：交易中
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("finally")
	public boolean getTradeFlag(Connection conn) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		PreparedStatement state = null;
		ResultSet rs = null;	
		boolean flag=false;
		try
		{
//			String sql="select value from awmts_flag where name='ACCOUNT_STATUS'";
//			state = conn.prepareStatement(sql);
//			rs = state.executeQuery();
//			while (rs.next()) 
//			{
//				String status=rs.getString(1);	//1:为交易中，2：为闭市		
//				if(status.equals("2")) flag=true;
//			}	
			flag=true;
		}catch(Exception a){
			a.printStackTrace();
		}finally{
			closeStatement(rs,state,null);		
		}
		return flag;
	}
	/**
	 * 关闭数据库连接
	 * @param rs ResultSet
	 * @param state Statement
	 * @param conn Connection
	 */
	public void closeStatement(ResultSet rs, Statement state,Connection conn) 
	{
		try {
		  if (rs != null)
		    rs.close();
		}
		catch (SQLException ex) {
		  ex.printStackTrace();
		}
		
		try {
		  if (state != null)
		    state.close();
		}
		catch (SQLException ex) {
		  ex.printStackTrace();
		}
		try {
		  if (conn != null)
		    conn.close();
		}
		catch (SQLException ex) {
		  ex.printStackTrace();
		}
	}
}

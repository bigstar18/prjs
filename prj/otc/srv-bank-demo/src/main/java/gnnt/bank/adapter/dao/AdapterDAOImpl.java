package gnnt.bank.adapter.dao;
import gnnt.bank.adapter.AdapterFirmVO;
import gnnt.bank.adapter.util.Common;
import gnnt.bank.adapter.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 * 数据库实现类
 * @author Administrator
 *
 */
class AdapterDAOImpl extends AdapterDAO{
	protected AdapterDAOImpl(){
		super();
	}
	@Override
	public int addFirm(AdapterFirmVO value) throws SQLException,ClassNotFoundException {
		Tool.log("增加交易商");
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try{
			conn = this.getConnection();
			sql = "insert into f_b_adapterFirm (bankID,firmID,money) values (?,?,?)";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++, value.getBankID());
			state.setString(n++, value.getFirmID());
			state.setDouble(n++, value.getMoney());
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, state, conn);
		}
		return result;
	}
	@Override
	public int delFirm(String bankID,String firmID) throws SQLException,ClassNotFoundException {
		Tool.log("删除交易商");
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try{
			conn = this.getConnection();
			sql = "delete f_b_adapterFirm where bankID=? and  firmid=?";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++, bankID);
			state.setString(n++, firmID);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, state, conn);
		}
		return result;
	}
	@Override
	public int inMoney(AdapterFirmVO value) throws SQLException,ClassNotFoundException {
		Tool.log("减少交易商资金 value："+value.toString());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try{
			conn = this.getConnection();
			sql = "update f_b_adapterFirm set money=money-? where bankID=? and firmID=?";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setDouble(n++, value.getMoney());
			state.setString(n++, value.getBankID());
			state.setString(n++, value.getFirmID());
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, state, conn);
		}
		return result;
	}
	@Override
	public int outMoney(AdapterFirmVO value) throws SQLException,ClassNotFoundException {
		Tool.log("增加交易商交易商资金");
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try{
			conn = this.getConnection();
			sql = "update f_b_adapterFirm set money=money+? where bankID=? and firmID=?";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setDouble(n++, value.getMoney());
			state.setString(n++, value.getBankID());
			state.setString(n++, value.getFirmID());
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, state, conn);
		}
		return result;
	}
	@Override
	public AdapterFirmVO getFirmVO(String bankID,String firmID) throws SQLException,ClassNotFoundException {
		Tool.log("获取交易商信息bankID["+bankID+"]firmID["+firmID+"]");
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		AdapterFirmVO result = null;
		try{
			conn = this.getConnection();
			sql = "select * from f_b_adapterFirm where bankID=? and firmID=?";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++,bankID);
			state.setString(n++, firmID);
			rs = state.executeQuery();
			if(rs.next()){
				result = new AdapterFirmVO();
				result.setFirmID(firmID);
				result.setBankID(bankID);
				result.setMoney(rs.getDouble("money"));
				result.setCreateTime(rs.getDate("createtime"));
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	@Override
	public String getfirmID(java.util.Date date,String bankID) throws SQLException,ClassNotFoundException{
		Tool.log("查询市场流水中符合条件的客户");
		String result = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			conn = this.getConnection();
//			sql = "select * from f_b_capitalinfo fbc where exists (select bankID from f_b_adapterfirm fba where fbc.bankid=fba.bankid) and trunc(createtime) = to_date('"+Common.df2.format(date)+"', 'yyyy-MM-dd') and (mod(money - 2011.83, 10000) = 0 or money = 2011.83) and rownum = 1";
			sql = "select * from f_b_capitalinfo fbc where bankID='"+bankID+"' and trunc(createtime) = to_date('"+Common.df2.format(date)+"', 'yyyy-MM-dd') and (mod(money - 2011.83, 10000) = 0 or money = 2011.83) and rownum = 1";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()){
				result = rs.getString("firmID");
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	@Override
	public Vector<CapitalValue> getCapitalValue(String bankID,java.util.Date date,String firmID) throws SQLException,ClassNotFoundException{
		Tool.log("查询市场流水信息date["+date+"]");
		Vector<CapitalValue> result = new Vector<CapitalValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		try{
			conn = this.getConnection();
			sql = "select * from f_b_capitalinfo fbc where fbc.bankID='"+bankID+"' and (type=1 or type=0) and status=0 " +
			"and trunc(banktime)=to_date('"+Common.df2.format(date)+"','yyyy-MM-dd') ";
			if(firmID != null && firmID.trim().length()>0){
				sql += "and firmid<>'"+firmID+"' ";
				sql += " union all ";
				sql += "select * from f_b_capitalinfo fbc where bankID='"+bankID+"' and (type=1 or type=0) and status not in (1,9) " +
					" and (trunc(banktime)=to_date('"+Common.df2.format(date)+"','yyyy-MM-dd') or ((mod(money - 2011.83, 10000) = 0 or money = 2011.83) " +
					" and trunc(createtime)=to_date('"+Common.df2.format(date)+"','yyyy-MM-dd'))) and firmid='"+firmID+"' ";
			}
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()){
				value = new CapitalValue();
				value.bankID = rs.getString("bankID");
				value.account = "1111";
				value.funID = rs.getString("funID2");
				value.actionID = rs.getLong("actionID");
				value.firmID = rs.getString("firmID");
				value.contact = rs.getString("contact");
				value.type = rs.getInt("type");
				value.money = rs.getDouble("money");
				value.status = rs.getInt("status");
				value.bankTime = rs.getTimestamp("bankTime");
				value.createtime = rs.getTimestamp("createtime");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
}

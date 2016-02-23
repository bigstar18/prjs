package gnnt.trade.bank;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
/**
 * 处理与中远期有关的数据
 * @author 薛计涛
 */
public class DataProcess {
	protected int openAccount(String firmID,String bankID,Connection conn)throws SQLException{
		int result = -1;
		CallableStatement proc = null;
		try{
			proc = conn.prepareCall("{?=call FN_CUSTOMERADDANDACTIVE(?,?)}");
			proc.setString(2, firmID);
			proc.setString(3, bankID);
			proc.registerOutParameter(1, Types.INTEGER);
			proc.executeQuery();
			result = proc.getInt(1);
		}catch(SQLException e){
			throw e;
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}finally{
			if(proc != null) {
				try {
					proc.close();
					proc = null;
				} catch(Exception e) {
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		log("调用交易系统签约激活存储firmID["+firmID+"]bankID["+bankID+"]result["+result+"]");
		return result;
	}
	/**
	 * 判断客户是否可以解约
	 * @param firmID 交易商代码
	 * @param bankID 银行编号
	 * @param conn 数据库连接
	 * @return int 结果
	 * @throws SQLException
	 */
	protected int ifFirmDelAccount(String firmID,String bankID,Connection conn) throws SQLException {
		int result = -1;
		CallableStatement proc = null;
		try{
			proc = conn.prepareCall("{?=call FN_F_CanUnSign(?,?)}");
			proc.setString(2, firmID);
			proc.setString(3, bankID);
			proc.registerOutParameter(1, Types.INTEGER);
			proc.executeQuery();
	        result = proc.getInt(1);
		}catch(SQLException e){
			throw e;
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}finally{
			if(proc != null) {
				try {
					proc.close();
					proc = null;
				} catch(Exception e) {
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		log("判断客户是否可以解约   ifFirmDelAccount   firmID["+firmID+"]bankID["+bankID+"]   FN_F_CanUnSign   返回结果["+result+"]");
		return result;
	}
	/**
	 * 修改交易商签约状态
	 * @param firmID 交易账号
	 * @param flag 签约情况(1 签约，2 解约)
	 * @param bankID 银行编号
	 * @param conn   数据库连接
	 * @return       交易账号可用余额
	 * @throws SQLException
	 */
	protected ReturnValue changeFirmIsOpen(String firmID,int flag,String bankID,Connection conn) throws SQLException {
		log("修改客户签约状态 FN_M_FirmSign firmID["+firmID+"]flag["+flag+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_M_FirmSign(?,?,?) }");
	        proc.setString(2, firmID);
	        proc.setInt(3, flag);
	        proc.setString(4, bankID);
	        proc.registerOutParameter(1, Types.INTEGER);
	        proc.executeQuery();
	        rv.result = proc.getInt(1);
	        if(rv.result>=0){
	        	rv.remark = "交易系统签约";
	        }else if(rv.result == -1){
	        	if(flag==1){
	        		rv.remark = "客户已终止";
	        	}else{
	        		rv.remark = "会员未终止";
	        	}
	        }else if(rv.result == -900){
				rv.remark = "客户状态不正确";
			}else if(rv.result == -901){
				rv.remark = "资金不为0";
			}else if(rv.result == -902){
				rv.remark = "持仓不为0";
			}else if(rv.result == -903){
				rv.remark = "不存在的客户";
			}else if(rv.result == -904){
				rv.remark = "会员状态不正确";
			}else if(rv.result == -905){
				rv.remark = "资金不为0";
			}else if(rv.result == -906){
				rv.remark = "客户数不为0";
			}else if(rv.result == -907){
				rv.remark = "持仓不为0";
			}else if(rv.result == -908){
				rv.remark = "会员状态不正确";
			}else if(rv.result == -909){
				rv.remark = "资金不为0";
			}else if(rv.result == -910){
				rv.remark = "客户数不为0";
			}else if(rv.result == -911){
				rv.remark = "持仓不为0";
			}else if(rv.result == -912){
				rv.remark = "会员不存在";
			}else if(rv.result == -1000){
				rv.remark = "资金不足";
			}else if(rv.result == -1002){
				rv.remark = "机构数不为0";
			}else if(rv.result == -1003){
				rv.remark = "佣金不为0";
			}else{
				rv.remark = "交易系统签(解)约失败";
			}
		} catch(SQLException e) {
			throw e;
		} catch(Exception e) {
			rv.result = ErrorCode.SystemError;
			rv.remark = "调用交易系统时系统异常";
			log(Tool.getExceptionTrace(e));
		} finally {
			if(proc != null) {
				try {
					proc.close();
					proc = null;
				} catch(Exception e) {
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		log("修改客户签约状态 FN_M_FirmSign firmID["+firmID+"]flag["+flag+"]bankID["+bankID+"]返回值["+rv.toString()+"]");
		return rv;
	}
	/**
	 * 获取可用资金
	 * @param bankID 银行编号
	 * @param firmID 交易账号
	 * @param conn   数据库连接
	 * @return       交易账号可用余额
	 * @throws SQLException
	 */
	protected double getRealFunds(String bankID,String firmID,int type,Connection conn) throws SQLException {
		log("获取可出金额 getRealFunds bankID["+bankID+"]firmID["+firmID+"]");
		double result=0;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_F_CanOutFunds(?,?,?) }");
	        proc.setString(2, firmID);
	        proc.setInt(3, type);
	        proc.setString(4, bankID);
	        proc.registerOutParameter(1, Types.DOUBLE);
	        proc.executeQuery();
	        result = proc.getDouble(1); 
		} catch(SQLException e) {
			throw e;
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			if(proc != null) {
				try {
					proc.close();
					proc = null;
				} catch(Exception e) {
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		return result;
	}
	/**
	 * 更新冻结资金
	 * @param firmID 交易账号
	 * @param money  发生额（正值增加，负值减少）
	 * @param conn
	 * @return       冻结资金余额
	 * @throws SQLException
	 */
	protected double updateFrozenFunds(String firmID,double money,Connection conn) throws SQLException {
		double result=0;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
            proc.setString(2, firmID);
            proc.setDouble(3, money);
            proc.setString(4, "1");
            proc.registerOutParameter(1, Types.DOUBLE);
            proc.executeUpdate();
	        result = proc.getDouble(1); 
		} catch(SQLException e) {
			throw e;
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			if(proc != null) {
				try {
					proc.close();
					proc = null;
				} catch(Exception e) {
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		return result;
	}
	/**
	 * 修改交易账号资金，调用交易系统修改交易系统资金方法
	 * @param firmID 交易账号
	 * @param money 出入金金额
	 * @param type 转账类型 (0 入金,1 出金,4 入金冲正,5 出金冲正)
	 * @param conn 数据库连接
	 * @return double 当前余额
	 * @throws SQLException
	 */
	protected double updateFundsFull(String bankID,String firmID,double money,int type,Connection conn)throws SQLException{
		log("修改交易账号资金bankID["+bankID+"] firmID["+firmID+"]money["+money+"]type["+type+"]");
		double result = 0;
		if(type == ProcConstants.inMoneyType){//入金
			result = this.inMoneyFunds(bankID,firmID, money, conn);
		}else if(type == ProcConstants.outMoneyType){//出金
			result = this.outMoneyFunds(bankID,firmID, money, conn);
		}else if(type == ProcConstants.inMoneyBlunt){//入金冲正
			result = this.inMoneyFunds(bankID,firmID, -1 * money, conn);
		}else if(type == ProcConstants.outMoneyBlunt){//出金冲正
			result = this.outMoneyFunds(bankID,firmID, -1 * money, conn);
		}else{
			throw new SQLException("修改交易账号["+firmID+"]资金，传入的类型为["+type+"]不识别");
		}
		return result;
	}
	/**
	 * 入金调用交易系统修改交易系统资金方法
	 * @param firmID 交易账号
	 * @param money 入金金额
	 * @param conn 数据库连接
	 * @return double 当前可用资金
	 * @throws SQLException
	 */
	private double inMoneyFunds(String bankID,String firmID,double money,Connection conn)throws SQLException{
		log("调用交易系统入金存储过程 bankID["+bankID+"]firmID["+firmID+"]money["+money+"]");
		double result = 0;
		CallableStatement proc = null;
		try{
			proc = conn.prepareCall("{ ?=call FN_F_Fund_In(?,?,?)}");
			proc.setString(2, firmID);
			proc.setDouble(3, money);
			proc.setString(4, bankID);
			proc.registerOutParameter(1, Types.DOUBLE);
			proc.executeQuery();
			result = proc.getDouble(1);
		}catch(SQLException e){
			throw e;
		}finally{
			if(proc != null){
				try{
					proc.close();
					proc = null;
				}catch(Exception e){
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		return result;
	}
	/**
	 * 出金调用交易系统修改交易系统资金方法
	 * @param firmID 交易账号
	 * @param money 出金金额
	 * @param conn 数据库连接
	 * @return double 当前可用资金
	 * @throws SQLException
	 */
	private double outMoneyFunds(String bankID,String firmID,double money,Connection conn)throws SQLException{
		log("调用交易系统出金存储过程bankID["+bankID+"]firmID["+firmID+"]money["+money+"]");
		double result = 0;
		CallableStatement proc = null;
		try{
			proc = conn.prepareCall("{ ?=call FN_F_Fund_Out(?,?,?)}");
			proc.setString(2, firmID);
			proc.setDouble(3, money);
			proc.setString(4, bankID);
			proc.registerOutParameter(1, Types.DOUBLE);
			proc.executeQuery();
			result = proc.getDouble(1);
		}catch(SQLException e){
			throw e;
		}finally{
			if(proc != null){
				try{
					proc.close();
					proc = null;
				}catch(Exception e){
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		return result;
	}
	/**
	 * 向指定日志文件写入日志信息
	 * @param content 日志内容
	 * @return void
	 */
	private void log(String content) {
		Logger plog = Logger.getLogger(ProcConstants.dpLog);
		plog.debug(content);		
	}
}

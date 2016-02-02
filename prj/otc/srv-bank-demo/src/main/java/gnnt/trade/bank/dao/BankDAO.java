package gnnt.trade.bank.dao;
import gnnt.trade.bank.util.Configuration;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalMoneyVO;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ClearingStatusVO;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CompareSumMoney;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmFundsBankValue;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmUserValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.SystemStatusVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 * <p>Title: 银行接口数据库访问对象</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 * @author zhangzhongli
 * @version 1.0
 */
public abstract class BankDAO {
	/** 数据库访问地址 */
	private String DBUrl;
	/** 数据库访问用户 */
	private String DBUser;
	/** 数据库访问口令 */
	private String DBPwd;
	/** 主库JNDI名称 */
	private String JNDIName;
	/** 查询库JNDI名称 */
	private String QueryJNDIName;
	/** 数据库连接类型 DBUrl：url连接  JNDIName：数据源 */
	private String DBConnType;
	/** 主库数据源 */
	protected DataSource dataSource = null;
	/** 查询库数据源 */
	protected DataSource queryDataSource = null;
	/**
	 * 构造函数
	 */
	protected BankDAO() throws Exception {
		try {
			Properties props = new Configuration().getSection(ProcConstants.xmlName);
			DBUrl = "" + props.getProperty(ProcConstants.dbUrl);
			DBUser = "" + props.getProperty(ProcConstants.dbUser);
			DBPwd = "" + props.getProperty(ProcConstants.dbPwd);
			JNDIName = "" + props.getProperty(ProcConstants.jndiName);
			QueryJNDIName = "" + props.getProperty(ProcConstants.QueryJNDIName);
			DBConnType = "" + props.getProperty(ProcConstants.dbConnType);
		} catch (Exception e) {
			throw e;
		}
		if(DBConnType.trim().equalsIgnoreCase("JNDI")) {
			InitialContext ic = null;
			try {
				ic = new InitialContext();
				dataSource = (DataSource) ic.lookup(JNDIName);
				queryDataSource=(DataSource) ic.lookup(QueryJNDIName);
			} catch (NamingException e) {
				throw e;
			}
		}
	}
	/**
	 * 获取数据源
	 * @return DataSource
	 */
	public DataSource getDataSource(){
		return dataSource;
	}
	/**
	 * 获得主库jdbc数据库联接
	 * @return Connection
	 */
	public Connection getConnection() throws SQLException,ClassNotFoundException  {
		if(DBConnType.trim().equalsIgnoreCase("JNDI")) {
			return dataSource.getConnection();
		} else {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch(ClassNotFoundException e) {
				throw e;
			}	    
			return DriverManager.getConnection(DBUrl,DBUser,DBPwd);
		}
	}
	/**
	 * 获得备库jdbc数据库联接
	 * @return Connection
	 */
	public Connection getQueryConnection() throws SQLException,ClassNotFoundException  {
		if(DBConnType.trim().equalsIgnoreCase("JNDI")) {
			return queryDataSource.getConnection();
		} else {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch(ClassNotFoundException e) {
				throw e;
			}	    
			return DriverManager.getConnection(DBUrl,DBUser,DBPwd);
		}
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
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
	    try {
	    	if (state != null){
	    		state.close();
	    	}
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
	    try {
	    	if (conn != null){
	    		conn.close();
	    	}
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    }
	}
	/**
	 * 取得市场业务流水号
	 * @return long 市场业务流水号
	 */
	public abstract long getActionID() throws SQLException,ClassNotFoundException;
	/**
	 * 取得市场业务流水号
	 * @param conn 数据库联接
	 * @return long 市场业务流水号
	 * @throws SQLException
	 */
	public abstract long getActionID(Connection conn) throws SQLException;
	/**
	 * 查看系统当前状态
	 * @return SystemStatusVO 系统状态信息
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract SystemStatusVO getSystemStatus() throws SQLException,ClassNotFoundException;
	/**
	 * 查看系统当前状态
	 * @param conn 数据库连接
	 * @return SystemStatusVO 系统状态信息
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract SystemStatusVO getSystemStatus(Connection conn) throws SQLException;
	/**
	 * 增加银行清算表记录
	 * @param value 清算记录信息
	 * @return int 增加条数
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int addClearing(ClearingStatusVO value)throws SQLException,ClassNotFoundException;
	/**
	 * 修改银行清算表记录信息
	 * @param value 清算记录信息
	 * @return int 修改条数
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modClearing(ClearingStatusVO value)throws SQLException,ClassNotFoundException;
	/**
	 * 查询当个银行清算最大日期记录信息
	 * @param bankID 银行编号
	 * @return ClearingStatusVO 记录信息
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract ClearingStatusVO getMaxClearing(String bankID)throws SQLException,ClassNotFoundException;
	/**
	 * 查询字典表
	 * @param filter 查询条件
	 * @return Vector 每项为DicValue
	 * @throws SQLException
	 */
	public abstract Vector<DicValue> getDicList(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 查询字典表
	 * @param filter 查询条件
	 * @param conn 数据库联接
	 * @return Vector 每项为DicValue
	 * @throws SQLException
	*/
	public abstract Vector<DicValue> getDicList(String filter,Connection conn) throws SQLException;
	/**
	 * 取得银行信息列表
	 * @param filter 查询条件
	 * @return Vector<BankValue>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<BankValue> getBankList(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 取得银行信息列表
	 * @param filter 查询条件
	 * @return Vector<BankValue>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<BankValue> getBankList2(String filter,int[] pageinfo) throws SQLException,ClassNotFoundException;
	/**
	 * 取得银行信息列表
	 * @param filter 查询条件
	 * @param conn 数据库联接
	 * @return Vector 每一项为BankValue 
	 * @throws SQLException
	 */
	public abstract Vector<BankValue> getBankList(String filter,Connection conn) throws SQLException;
	/**
	 * 修改银行
	 * @param val BankValue
	 * @return void
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modBank(BankValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 修改银行可用状态
	 * @param bankIDs 银行编号集合
	 * @param value 修改成的状态
	 * @return int 修改银行条数
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int chBankValid(Vector<String> bankIDs,int value)throws SQLException,ClassNotFoundException;
	/**
	 * 修改银行入金可用状态
	 * @param bankIDs 银行编号集合
	 * @param value 修改成的状态
	 * @return int 修改银行条数
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int chBankInMoney(Vector<String> bankIDs,int value)throws SQLException,ClassNotFoundException;
	/**
	 * 修改银行出金可用状态
	 * @param bankIDs 银行编号集合
	 * @param value 修改成的状态
	 * @return int 修改银行条数
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int chBankOutMoney(Vector<String> bankIDs,int value)throws SQLException,ClassNotFoundException;
	/**
	 * 取得银行信息
	 * @param bankID 银行代码
	 * @return BankValue 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract BankValue getBank(String bankID) throws SQLException,ClassNotFoundException;
	/**
	 * 取得银行信息
	 * @param bankID 银行代码
	 * @param conn 数据库联接
	 * @return BankValue 
	 * @throws SQLException
	 */
	public abstract BankValue getBank(String bankID,Connection conn) throws SQLException;
	/**
	 * 取得交易账号信息列表
	 * @param filter 查询条件
	 * @return Vector 每一项为FirmValue 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<FirmValue> getFirmList(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 取得交易账号信息列表
	 * @param filter 查询条件
	 * @return Vector<FirmValue>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<FirmValue> getFirmList2(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 取得交易账号
	 * @param firmID 交易账号代码
	 * @return FirmValue 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract FirmValue getFirm(String firmID) throws SQLException,ClassNotFoundException;
	/**
	 * 取得交易账号
	 * @param firmID 交易账号代码
	 * @param conn 数据库联接
	 * @return FirmValue 
	 * @throws SQLException
	 */
	public abstract FirmValue getFirm(String firmID,Connection conn) throws SQLException;
	/**
	 * 修改交易账号状态
	 * @param val 交易账号对象
	 * @return int 操作结果 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modFirmStatus(FirmValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 修改交易账号状态
	 * @param val 交易账号对象
	 * @param conn 数据库联接
	 * @return int 操作结果
	 * @throws SQLException
	 */
	public abstract int modFirmStatus(FirmValue val,Connection conn) throws SQLException;
	/**
	 * 删除交易账号
	 * @param firmID 交易账号代码
	 * @return int 操作结果 0：成功 非0:失败 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int delFirm(String firmID) throws SQLException,ClassNotFoundException;
	/**
	 * 修改交易账号密码
	 * @param val 交易账号对象
	 * @return int 操作结果
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modFirmPassword(FirmValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 修改交易账号密码
	 * @param val 交易账号对象
	 * @param conn 数据库联接
	 * @return int 操作结果
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modFirmPassword(FirmValue val,Connection conn) throws SQLException;
	/**
	 * 查询交易账号市场可用资金
	 * @param filter 查询条件
	 * @return FirmBalanceValue
	 */
	public abstract FirmBalanceValue availableBalance(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 查询交易账号市场可用资金
	 * @param filter 查询条件
	 * @param conn 数据库连接
	 * @return FirmBalanceValue
	 * @throws SQLException
	 */
	public abstract FirmBalanceValue availableBalance(String filter,Connection conn) throws SQLException;
	/**
	 * 查询账号对应关系列表
	 * bankId  银行代码，
	 * firmId  交易账号代码，
	 * cardType交易账号证件类型(1是个人,8是企业)，
	 * isOpen  是否签约(1是已签约,0是未签约)，
	 * status  是否可用(0是可用,1是不可用)，
	 */
	public abstract Vector<CorrespondValue> getCorrespondList(String bankId,String firmId,String cardType,String isOpen,String status);
	/**
	 * 查询账号对应关系列表
	 * @param filter 查询条件
	 * @return Vector 每项为CorrespondValue
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<CorrespondValue> getCorrespondList(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 查询账号对应关系列表
	 * @param filter 查询条件
	 * @param conn 数据库联接
	 * @return Vector 每项为CorrespondValue
	 * @throws SQLException
	 */
	public abstract Vector<CorrespondValue> getCorrespondList(String filter,Connection conn) throws SQLException;
	/**
	 * 查询账号对应关系列表
	 * @param filter 查询条件
	 * @return Vector<CorrespondValue>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<CorrespondValue> getCorrespondList2(String filter,int[] pageinfo) throws SQLException,ClassNotFoundException;
	/**
	 * 查询交易商银行信息
	 * @param filter 查询条件
	 * @return Vector<FirmBankMsg>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<FirmBankMsg> getfirmBankMsg(String filter)throws SQLException,ClassNotFoundException;
	/**
	 * 查询账号对应关系
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param account 交易账号银行账号
	 * @return CorrespondValue
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract CorrespondValue getCorrespond(String bankID, String firmID, String account) throws SQLException,ClassNotFoundException;
	/**
	 * 查询账号对应关系
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param account 交易账号银行账号
	 * @param conn 数据库联接
	 * @return CorrespondValue
	 * @throws SQLException
	 */
	public abstract CorrespondValue getCorrespond(String bankID, String firmID, String account, Connection conn) throws SQLException;
	/**
	 * 查询交易账号和银行绑定的个数
	 * bankId  银行代码，
	 * firmId  交易账号代码，
	 * cardType交易账号证件类型(1是个人,8是企业)，
	 * isOpen  是否签约(1是已签约,0是未签约)，
	 * status  是否可用(0是可用,1是不可用)，
	 */
	public abstract int countFirmAccount(String bankId,String firmId,String cardType,String isOpen,String status) throws SQLException,ClassNotFoundException;
	/**
	 * 查询交易账号和银行绑定的个数
	 * @param file 查询条件
	 * @return int
	 */
	public abstract int countFirmAccount(String file) throws SQLException,ClassNotFoundException;
	/**
	 * 添加账号对应关系
	 * @param CorrespondValue 对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int addCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 添加账号对应关系
	 * @param CorrespondValue 对应关系对象
	 * @param conn 数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int addCorrespond(CorrespondValue val,Connection conn) throws SQLException;
	/**
	 * 修改账号对应关系
	 * @param CorrespondValue 对应关系对象
	 * @return int 操作结果
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 修改账号对应关系
	 * @param CorrespondValue 对应关系对象
	 * @param conn 数据库联接
	 * @return int 操作结果
	 * @throws SQLException
	 */
	public abstract int modCorrespond(CorrespondValue val,Connection conn) throws SQLException;
	/**
	 * 交易账号签约
	 * @param val 对应关系对象
	 * @param 操作结果
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int openCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 交易账号签约
	 * @param val 对应关系对象
	 * @param conn 数据库连接
	 * @param 操作结果
	 * @throws SQLException
	 */
	public abstract int openCorrespond(CorrespondValue val,Connection conn) throws SQLException;
	/**
	 * 修改交易账号绑定表状态
	 * @param val 交易账号签解约信息
	 * @return int 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int modCorrespondStatus(CorrespondValue val)throws SQLException,ClassNotFoundException;
	/**
	 * 修改交易账号银行冻结资金
	 * filter 查询条件，
	 * money 修改金额，
	 * conn 数据库连接对象
	 */
	public abstract int modBankFrozenFuns(String filter,double money,Connection conn) throws SQLException;
	/**
	 * 删除账号对应关系
	 * @param CorrespondValue 对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int delCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 删除账号对应关系
	 * @param CorrespondValue 对应关系对象
	 * @param conn 数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int delCorrespond(CorrespondValue val,Connection conn) throws SQLException;
	/**
	 * 注销账号对应关系
	 * @param val (交易账号代码银行账户对应关系对象类)
	 * @return int 注销状态 >=0成功，其余失败
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int destroyAccount(CorrespondValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 注销账号对应关系
	 * @param val (交易账号代码银行账户对应关系对象类)
	 * @param conn 数据库连接
	 * @return int 注销状态 >=0成功，其余失败
	 * @throws SQLException
	 */
	public abstract int destroyAccount(CorrespondValue val,Connection conn) throws SQLException;
	/**
	 * 判断现在是否可以转账
	 * @param String bankID
	 * @return int n (0 可以转账,1 非交易日,2 未到交易时间,3 交易时间已过,4 禁止交易)
	 */
	public abstract int useBank(String bankID)throws SQLException,ClassNotFoundException;
	/**
	 * 判断当天是否是交易日
	 * true 可以交易,false 不可以交易
	 */
	public abstract boolean getTradeDate(java.util.Date tradeDate)throws SQLException,ClassNotFoundException;
	/**
	 * 取交易系统结算状态
	 * @return int 系统状态
	 * @throws SQLException
	 */
	public abstract boolean getTraderStatus() throws SQLException,ClassNotFoundException;
	/**
	 * 查询交易账号名下交易员信息
	 * @param firmid 交易账号id
	 * @return string 密码
	 */
	public abstract FirmMessageVo getFirmMSG(String firmid)throws SQLException,ClassNotFoundException ;
	/**
	 * 增加资金流水记录
	 * @param val CapitalValue
	 * @param conn 数据库联接
	 * @return long 资金流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public abstract long addCapitalInfo(CapitalValue val,Connection conn) throws SQLException;
	/**
	 * 取得资金流水记录列表
	 * @param filter 
	 * @return Vector<CapitalInfo>
	 * @throws SQLException
	 */
	public abstract Vector<CapitalValue> getCapitalInfoList(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 取得资金流水记录列表
	 * @param filter 
	 * @param conn 数据库联接
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public abstract Vector<CapitalValue> getCapitalInfoList(String filter,Connection conn) throws SQLException;
	/**
	 * 取得单个资金流水记录
	 * @param filter 查询条件
	 * @return 返回一个资金流水对象
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	  public abstract CapitalValue getCapitalInfo(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 修改资金流水记录状态
	 * @param id 资金流水号
	 * @param funID 银行业务流水号
	 * @param status 状态 0：成功 1：失败
	 * @param bankTime 银行端操作时间
	 * @param conn 数据库联接
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public abstract int modCapitalInfoStatus(long id, String funID,int status, Timestamp bankTime,Connection conn) throws SQLException;
	/**
	 * 修改资金流水记录描述
	 * @param id
	 * @param describe 描述信息
	 * @param conn
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public abstract int modCapitalInfoNote(long id, String note,Connection conn) throws SQLException;
	/**
	 * 修改资金流水记录交易员
	 * @param id
	 * @param trader 交易员信息
	 * @param conn
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public abstract int modCapitalInfoTrader(long id, String trader,Connection conn) throws SQLException;
	/**
	 * 取得市场资金流水记录列表
	 * @param filter 
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public abstract Vector<CapitalValue> getCapitalInfoList2(String filter,int[] pageinfo) throws SQLException,ClassNotFoundException;
	/**
	 * 根据市场资金流水记录列表的条件查询总查询信息金额
	 * @param filter
	 * @return Vector<CapitalMoneyVO>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<CapitalMoneyVO> getCapitalInfoMoney(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 取得对账信息列表
	 * @param filter 查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<MoneyInfoValue> getMoneyInfoList(String filter) throws SQLException,ClassNotFoundException;
	/**
	 * 取得对账信息列表
	 * @param filter 查询条件
	 * @param conn 数据库联接
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<MoneyInfoValue> getMoneyInfoList(String filter,Connection conn) throws SQLException;
	/**
	 * 查看日志
	 * @param filter
	 * @return Vector<LogValue>
	 */
	public abstract Vector<LogValue> logList(String filter,int[] pageinfo);
	/**
	 * 操作写日志
	 * @param lv
	 */
	public abstract void log(LogValue lv);
	/**
	 * 查询银行接口和银行通讯信息
	 * @param filter 查询条件
	 * @return Vector<InterfaceLog> 日志信息
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<InterfaceLog> interfaceLogList(String filter,int[] pageinfo)throws SQLException,ClassNotFoundException;
	/**
	 * 插入银行接口和银行通讯信息
	 * @param log 日志信息
	 * @return int 插入条数
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract int interfaceLog(InterfaceLog log)throws SQLException,ClassNotFoundException;
	/**
	 * 合计资金流水金额
	 * @param filter 
	 * @param conn 数据库联接
	 * @return double 
	 * @throws SQLException
	 */
	public abstract double sumCapitalInfo(String filter,Connection conn) throws SQLException;
	/**
	 * 添加对账信息
	 * @param val 对账信息
	 * @return long 对账信息流水号,返回<0的值表示添加失败 
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract long addMoneyInfo(MoneyInfoValue val) throws SQLException,ClassNotFoundException;
	/**
	 * 添加对账信息
	 * @param val 对账信息
	 * @param conn 数据库联接
	 * @return long 对账信息流水号,返回<0的值表示添加失败 
	 * @throws SQLException
	 */
	public abstract long addMoneyInfo(MoneyInfoValue val,Connection conn) throws SQLException;
	/**
	 * 删除对账信息
	 * @param filter 删除条件
	 * @param conn 数据库联接
	 * @return int 操作结果 大于等于0：成功 
	 * @throws SQLException
	 */
	public abstract int delMoneyInfo(String filter,Connection conn) throws SQLException;
	/**
	 * 查询对账表
	 * @param date 日期
	 * @return Vector<MoneyInfoValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<MoneyInfoValue> qureyBankCompareInfo(String date) throws SQLException,ClassNotFoundException;
	/**
	 * 获取银行没有市场有的流水
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param conn 数据库连接
	 * @return Vector<CompareResult>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date date,int[] pageinfo) throws SQLException,ClassNotFoundException;
	/**
	 * 获取银行没有市场有的流水
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param conn 数据库连接
	 * @return Vector<CompareResult>
	 * @throws SQLException
	 */
	public abstract Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date date,Connection conn,int[] pageinfo) throws SQLException;
	/**
	 * 获取银行有市场没有的流水
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param conn 数据库连接
	 * @return Vector<CompareResult>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date date,int[] pageinfo) throws SQLException,ClassNotFoundException;
	/**
	 * 获取银行有市场没有的流水
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param conn 数据库连接
	 * @return Vector<CompareResult>
	 * @throws SQLException
	 */
	public abstract Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date date,Connection conn,int[] pageinfo) throws SQLException;
	/**
	 * 
	 * @param bankID 银行编号
	 * @param date 对账日期
	 * @return Vector<CompareSumMoney>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<CompareSumMoney> sumCompareMoney(String bankID,java.util.Date date) throws SQLException,ClassNotFoundException;
	/**
	 * 查询交易账号当天出入金求和数据
	 * @param bankID 银行编号
	 * @param firmIDs 交易账号代码集
	 * @param date 转账日期
	 * @return Vector<CapitalCompare>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws SQLException,ClassNotFoundException;
	
	/**
	 * 查询客户在每个银行的分资金信息
	 * @param firmID 交易商代码
	 * @return Vector<FirmFundsBankValue>
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract Vector<FirmFundsBankValue> getFirmFundsBank(String firmID)throws SQLException,ClassNotFoundException;
	/**
	 * 查询客户总资金信息
	 * @param firmID 交易商代码
	 * @return FirmFundsValue
	 * @throws SQLException,ClassNotFoundException
	 */
	public abstract FirmFundsValue getFirmFunds(String firmID)throws SQLException,ClassNotFoundException;
	/**
	 * 查询f_b_firmuser列表
	 * @param filter 查询条件
	 * @param pageinfo
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<FirmUserValue> getFirmUserList2(String filter,int[] pageinfo,String bankId,String strKey) throws SQLException,ClassNotFoundException;
}


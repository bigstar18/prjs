package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.Configuration;
import gnnt.trade.bank.vo.Account;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankQSNetChild;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.BanksInfoValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeInfoVO;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmOpenCloseBank;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.FrozenBalanceVO;
import gnnt.trade.bank.vo.FundsAndInterests;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.Trademodule;
import gnnt.trade.bank.vo.TransMnyObjValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.TransferBank;
import gnnt.trade.bank.vo.bankdz.boc.AccountStatusReconciliation;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneySettlementList;
import gnnt.trade.bank.vo.bankdz.boc.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.sfz.sent.child.BatQsChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * <p>
 * Title: 银行接口数据库访问对象
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: gnnt
 * </p>
 * 
 * @author zhangzhongli
 * @version 1.0
 */
public abstract class BankDAO
{

	/*
	 * 数据库访问地址 *
	 */
	private String DBUrl;

	/*
	 * 数据库访问用户 *
	 */
	private String DBUser;
	/*
	 * 数据库访问口令 *
	 */
	private String DBPwd;

	/*
	 * JNDI名称 *
	 */
	private String JNDIName;
	/*
	 * 数据库连接类型 DBUrl：url连接 JNDIName：数据源 *
	 */
	private String DBConnType;

	/**
	 * 数据源
	 */
	protected DataSource dataSource = null;

	/**
	 * 构造函数
	 */
	protected BankDAO() throws Exception
	{
		try
		{
			Properties props = new Configuration().getSection("BANK.Processor");
			DBUrl = "" + props.getProperty("DBUrl");
			DBUser = "" + props.getProperty("DBUser");
			DBPwd = "" + props.getProperty("DBPassword");
			JNDIName = "" + props.getProperty("JNDIName");
			DBConnType = "" + props.getProperty("DBConnType");
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		if (DBConnType.equalsIgnoreCase("JNDIName"))
		{
			InitialContext ic = null;
			try
			{
				// System.out.println(JNDIName);
				ic = new InitialContext();
				dataSource = (DataSource) ic.lookup(JNDIName);
			} catch (NamingException e)
			{
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public DataSource getDataSource()
	{
		return dataSource;
	}

	/**
	 * 获得jdbc数据库联接
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException
	{
		if (DBConnType.equalsIgnoreCase("JNDIName"))
		{
			return dataSource.getConnection();
		} else
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				throw e;
			}
			return DriverManager.getConnection(DBUrl, DBUser, DBPwd);
		}

	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param rs
	 *            ResultSet
	 * @param state
	 *            Statement
	 * @param conn
	 *            Connection
	 */
	public void closeStatement(ResultSet rs, Statement state, Connection conn)
	{
		try
		{
			if (rs != null)
				rs.close();
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}

		try
		{
			if (state != null)
				state.close();
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		try
		{
			if (conn != null)
				conn.close();
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * 通过模块编号获取系统模块
	 * <br/><br/>
	 * @param moduleID
	 * @return
	 */
	public abstract Trademodule getTrademodule(int moduleID);

	/**
	 * 取得市场业务流水号
	 * 
	 * @param conn
	 *            数据库联接
	 * @return long 市场业务流水号
	 * @throws SQLException
	 */
	public abstract long getActionID(Connection conn) throws SQLException;

	/**
	 * 取得市场业务流水号
	 * 
	 * @return long 市场业务流水号
	 */
	public abstract long getActionID();

	/**
	 * 增加资金流水记录
	 * 
	 * @param val
	 *            CapitalValue
	 * @param conn
	 *            数据库联接
	 * @return long 资金流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public abstract long addCapitalInfo(CapitalValue val, Connection conn) throws SQLException;

	/**
	 * 修改资金流水记录状态
	 * 
	 * @param id
	 *            资金流水号
	 * @param funID
	 *            银行业务流水号
	 * @param status
	 *            状态 0：成功 1：失败
	 * @param bankTime
	 *            银行端操作时间
	 * @param conn
	 *            数据库联接
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public abstract int modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime, Connection conn) throws SQLException;

	/**
	 * 修改资金流水记录状态
	 * 
	 * @param actionid
	 *            市场流水号
	 * @param funID
	 *            银行业务流水号
	 * @param conn
	 *            数据库联接
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public abstract int modCapitalInfoStatus(long actionid, String funID, Connection conn) throws SQLException;

	/**
	 * 判断现在是否可以转账
	 * 
	 * @param String
	 *            bankID
	 * @return int n (0 可以转账,1 非交易日,2 未到交易时间,3 交易时间已过,4 禁止交易)
	 */
	public abstract int useBank(String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 判断当天是否是交易日 true 可以交易,false 不可以交易
	 */
	public abstract boolean getTradeDate(java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	/**
	 * 取得交易商浮动盈亏数据
	 * 
	 * @param firmIDs
	 *            交易商代码集
	 * @param conn
	 *            数据库连接
	 * @return Vector<TholdPositionValue>
	 * @throws SQLException
	 */
	public abstract Vector<FirmBalanceValue> getFlote(String[] firmIDs) throws SQLException, ClassNotFoundException;

	/**
	 * 修改资金流水记录描述
	 * 
	 * @param id
	 * @param describe
	 *            描述信息
	 * @param conn
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public abstract int modCapitalInfoNote(long id, String note, Connection conn) throws SQLException;

	/**
	 * 取得资金流水记录列表
	 * 
	 * @param filter
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public abstract Vector<CapitalValue> getCapitalInfoList(String filter, Connection conn) throws SQLException;

	/**
	 * 取得资金流水记录列表
	 * 
	 * @param filter
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public abstract Vector<CapitalValue> getCapitalInfoList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 取得市场资金流水记录列表
	 * 
	 * @param filter
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public abstract Vector<CapitalValue> getCapitalInfoList2(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 合计资金流水金额
	 * 
	 * @param filter
	 * @param conn
	 *            数据库联接
	 * @return double
	 * @throws SQLException
	 */
	public abstract double sumCapitalInfo(String filter, Connection conn) throws SQLException;

	/**
	 * 增加银行
	 * 
	 * @param val
	 *            BankValue
	 * @return void
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract void addBank(BankValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 修改银行
	 * 
	 * @param val
	 *            BankValue
	 * @return void
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract void modBank(BankValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 删除银行
	 * 
	 * @param bankID
	 *            String
	 * @return void
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract void delBank(String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 取得银行信息
	 * 
	 * @param bankID
	 *            银行代码
	 * @param conn
	 *            数据库联接
	 * @return BankValue
	 * @throws SQLException
	 */
	public abstract BankValue getBank(String bankID, Connection conn) throws SQLException;

	/**
	 * 取得银行信息
	 * 
	 * @param bankID
	 *            银行代码
	 * @return BankValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract BankValue getBank(String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 取得银行信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每一项为BankValue
	 * @throws SQLException
	 */
	public abstract Vector<BankValue> getBankList(String filter, Connection conn) throws SQLException;

	/**
	 * 取得银行信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每一项为BankValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract Vector<BankValue> getBankList(String filter) throws SQLException, ClassNotFoundException;

	/** 根据交易商Id查询交易商信息 */
	public abstract Firm getMFirmByFirmId(String firmId) throws SQLException, ClassNotFoundException;

	/**
	 * 增加交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int addFirm(FirmValue val, Connection conn) throws SQLException;

	/**
	 * 增加交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int addFirm(FirmValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 删除交易商
	 * 
	 * @param firmID
	 *            交易商代码
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int delFirm(String firmID) throws SQLException, ClassNotFoundException;

	/**
	 * 修改交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int modFirm(FirmValue val, Connection conn) throws SQLException;

	/**
	 * 修改交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int modFirm(FirmValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 取得交易商信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每一项为FirmValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract Vector<FirmValue> getFirmList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 取得交易商
	 * 
	 * @param firmID
	 *            交易商代码
	 * @param conn
	 *            数据库联接
	 * @return FirmValue
	 * @throws SQLException
	 */
	public abstract FirmValue getFirm(String firmID, Connection conn) throws SQLException;

	/**
	 * 取得交易商
	 * 
	 * @param firmID
	 *            交易商代码
	 * @return FirmValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract FirmValue getFirm(String firmID) throws SQLException, ClassNotFoundException;

	/**
	 * 添加对账信息
	 * 
	 * @param val
	 *            对账信息
	 * @param conn
	 *            数据库联接
	 * @return long 对账信息流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public abstract void addMoneyInfo(MoneyInfoValue val, Connection conn) throws SQLException;

	/**
	 * 添加对账信息
	 * 
	 * @param val
	 *            对账信息
	 * @return long 对账信息流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public abstract long addMoneyInfo(MoneyInfoValue val) throws SQLException;

	/**
	 * 删除对账信息，不从数据库删除数据而是修改记录状态为已删除
	 * 
	 * @param id
	 *            对账信息流水号
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int delMoneyInfo(String id, Connection conn) throws SQLException;

	/**
	 * 取得对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<MoneyInfoValue> getMoneyInfoList(String filter, Connection conn) throws SQLException;

	/**
	 * 取得对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<MoneyInfoValue> getMoneyInfoList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 连表取得对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<MoneyInfoValue> getUnionMoneyInfoList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 添加帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int addCorrespond(CorrespondValue val, Connection conn) throws SQLException;

	/**
	 * 删除帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int delCorrespond(CorrespondValue val, Connection conn) throws SQLException;

	/**
	 * 修改帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int modCorrespond(CorrespondValue val, Connection conn) throws SQLException;

	/**
	 * 添加帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int addCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 删除帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int delCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 交易商解约
	 */
	public abstract int closeCorrespond(String bankID, String firmID, String card, Connection conn) throws SQLException;

	/**
	 * 修改帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int modCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 注销账号对应关系
	 * 
	 * @param val
	 *            (交易商代码银行账户对应关系对象类)
	 * @return int 注销状态 >=0成功，其余失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int destroyAccount(CorrespondValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 注销账号对应关系
	 * 
	 * @param val
	 *            (交易商代码银行账户对应关系对象类)
	 * @param conn
	 *            数据库连接
	 * @return int 注销状态 >=0成功，其余失败
	 * @throws SQLException
	 */
	public abstract int destroyAccount(CorrespondValue val, Connection conn) throws SQLException;

	/**
	 * 查询帐号对应关系列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为CorrespondValue
	 * @throws SQLException
	 */
	public abstract Vector<CorrespondValue> getCorrespondList(String filter, Connection conn) throws SQLException;

	/**
	 * 查询帐号对应关系列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为CorrespondValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract Vector<CorrespondValue> getCorrespondList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 查询帐号对应关系
	 * 
	 * @param bankID
	 *            银行代码
	 * @param firmID
	 *            交易商代码
	 * @param account
	 *            交易商银行帐号
	 * @param conn
	 *            数据库联接
	 * @return CorrespondValue
	 * @throws SQLException
	 */
	public abstract CorrespondValue getCorrespond(String bankID, String firmID, String account, Connection conn) throws SQLException;

	/**
	 * 查询帐号对应关系
	 * 
	 * @param bankID
	 *            银行代码
	 * @param firmID
	 *            交易商代码
	 * @param account
	 *            交易商银行帐号
	 * @return CorrespondValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract CorrespondValue getCorrespond(String bankID, String firmID, String account) throws SQLException, ClassNotFoundException;

	/**
	 * 查询字典表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为DicValue
	 * @throws SQLException
	 */
	public abstract Vector<DicValue> getDicList(String filter, Connection conn) throws SQLException;

	/**
	 * 查询字典表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为DicValue
	 * @throws SQLException
	 */
	public abstract Vector<DicValue> getDicList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 查询资金划转对象列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为TransMnyObjValue
	 * @throws SQLException
	 */
	public abstract Vector<TransMnyObjValue> getTransMnyObjList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 查询资金划转对象
	 * 
	 * @param id
	 *            业务序号
	 * @return TransMnyObjValue
	 * @throws SQLException
	 */
	public abstract TransMnyObjValue getTransMnyObj(int id) throws SQLException, ClassNotFoundException;

	/**
	 * 查询对账表
	 * 
	 * @param date
	 *            日期
	 * @return vector对象，每一项是一个moneyinfovalue
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<MoneyInfoValue> qureyBankCompareInfo(String date) throws SQLException, ClassNotFoundException;

	/**
	 * 对账结果信息
	 * 
	 * @param bankID
	 *            银行ID
	 * @param date
	 *            对账日期
	 * @return vector对象，每一项是一个CompareResult
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<CompareResult> compareResultInfo(String bankID, String date) throws SQLException, ClassNotFoundException;

	/**
	 * 查询交易商当天出入金求和数据
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集
	 * @param date
	 *            转账日期
	 * @return Vector<CapitalCompare>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, java.util.Date date) throws SQLException, ClassNotFoundException;

	/**
	 * 取得单个资金流水记录
	 * 
	 * @param filter
	 *            查询条件
	 * @return 返回一个资金流水对象
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract CapitalValue getCapitalInfo(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 更新出金资金流水
	 * 
	 * @param cVal
	 *            资金流水对象
	 * @param conn
	 *            数据库连接对象
	 * @return 更新成功返回0 更新失败返回负值
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract long updateOutMoneyCapitalInfo(CapitalValue cVal, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 添加收费标准
	 * 
	 * @param CFeeInfoVO
	 *            收费标准对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int addFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 添加收费标准
	 * 
	 * @param CFeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int addFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException;

	/**
	 * 删除收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int delFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 删除收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int delFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException;

	/**
	 * 删除收费标准
	 * 
	 * @param filter
	 *            删除条件
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int delFeeInfo(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 修改收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int modFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 修改收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public abstract int modFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException;

	/**
	 * 查询收费标准列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为FeeInfoVO
	 * @throws SQLException
	 */
	public abstract Vector<FeeInfoVO> getFeeInfoList(String filter, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 查询收费标准列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为FeeInfoVO
	 * @throws SQLException
	 */
	public abstract Vector<FeeInfoVO> getFeeInfoList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 查询资金流水总笔数
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总笔数 1：查询成功笔数 2:失败笔数 3：处理中笔数 4：待审核笔数
	 * @return int 笔数
	 * @throws SQLException
	 */
	public abstract int countCapitalInfo(String filter, int status) throws SQLException, ClassNotFoundException;

	/**
	 * 查询资金流水总金额
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总金额 1：查询成功金额 2:失败金额 3：处理中金额 4：待审核金额
	 * @return double 金额
	 * @throws SQLException
	 */
	public abstract double countCapitalInfoTotalMoney(String filter, int status) throws SQLException, ClassNotFoundException;

	/**
	 * 查询资金流水总笔数
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总笔数 1：查询成功笔数 2:失败笔数 3：处理中笔数 4：待审核笔数
	 * @return int 笔数
	 * @throws SQLException
	 */
	public abstract int countBankCompareInfo(String filter, int status) throws SQLException, ClassNotFoundException;

	/**
	 * 查询资金流水总金额
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总金额 1：查询成功金额 2:失败金额 3：处理中金额 4：待审核金额
	 * @return double 金额
	 * @throws SQLException
	 */
	public abstract double countBankCompareInfoTotalMoney(String filter, int status) throws SQLException, ClassNotFoundException;

	/**
	 * 银行端出金，市场记录该笔出金信息以等待审核
	 * 
	 * @param id
	 *            资金流水id
	 * @return CapitalValue 资金流水对象
	 * @throws SQLException
	 */
	public abstract CapitalValue handleOutmoenyFromBank(long id) throws SQLException, ClassNotFoundException;

	/**
	 * 取交易系统结算状态
	 * 
	 * @return int 系统状态
	 * @throws SQLException
	 */
	public abstract boolean getTraderStatus() throws SQLException, ClassNotFoundException;

	/**
	 * 取交易系统结算后的数据
	 * 
	 * @param filter
	 * @param moduleid
	 *            板块号 -1全部 2中远期 3现货 4竞价
	 * @return List
	 * @throws SQLException
	 */
	public abstract List<TradeResultValue> getTradeDataInList(String filter, int moduleid) throws SQLException, ClassNotFoundException;

	/**
	 * 取交易系统结算后的数据
	 * 
	 * @param filter
	 * @param moduleid
	 *            板块号 -1全部 2中远期 3现货 4竞价
	 * @return hashtable
	 * @throws SQLException
	 */
	public abstract Hashtable<String, TradeResultValue> getTradeDataInHashTable(String filter, int moduleid) throws SQLException, ClassNotFoundException;

	/**
	 * 传市场总额[可用资金和所有权益]到银行对账
	 * 
	 * @param filter
	 * @return double
	 * @throws SQLException
	 */
	public abstract Hashtable<String, Double> getFundsAndInterests(java.util.Date date, int moduleid) throws SQLException, ClassNotFoundException;

	/**
	 * 市场总额[可用资金和所有权益] 目前只中远期
	 * 
	 * @param filter
	 * @return double
	 * @throws SQLException
	 */
	public abstract Vector<FundsAndInterests> getFundsAndInterestsInVector(java.util.Date date, int moduleid) throws SQLException, ClassNotFoundException;

	/**
	 * 查询交易商对应的银行帐号
	 * 
	 * @param firmID
	 *            交易商代码
	 * @return LIST
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract List<String> getFirmBankList(String firmID) throws SQLException, ClassNotFoundException;

	/**
	 * 操作写日志
	 * 
	 * @param lv
	 */
	public abstract void log(LogValue lv);

	/**
	 * 查看日志
	 * 
	 * @param filter
	 */
	public abstract Vector<LogValue> logList(String filter);

	/**
	 * 查询交易商市场可用资金
	 */
	public abstract FirmBalanceValue availableBalance(String filter);

	/**
	 * 查询交易商市场可用资金
	 */
	public abstract FirmBalanceValue availableBalance(String filter, Connection conn) throws SQLException;

	/**
	 * 查询交易商 模块里的冻结资金
	 */
	public abstract List<FrozenBalanceVO> frozenBalance(String firmid, String moduleid);

	/**
	 * 查询银行帐号的签约状态
	 */
	public abstract int bankAccountIsOpen(String filter);

	/**
	 * 取得银行资金流水记录列表
	 * 
	 * @param filter
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public abstract Vector<BankCompareInfoValue> getBankCapInfoList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 查询交易商名下交易员信息
	 * 
	 * @param firmid
	 *            交易员id
	 * @return string 密码
	 */
	public abstract FirmMessageVo getFirmMSG(String firmid);

	/**
	 * 查询帐号对应关系列表 bankId 银行代码， firmId 交易商代码， cardType交易商证件类型(1是个人,8是企业)， isOpen
	 * 是否签约(1是已签约,0是未签约)， status 是否可用(0是可用,1是不可用)，
	 */
	public abstract Vector<CorrespondValue> getCorrespondList(String bankId, String firmId, String cardType, String isOpen, String status);

	/**
	 * 查询交易商和银行绑定的个数 bankId 银行代码， firmId 交易商代码， cardType交易商证件类型(1是个人,8是企业)，
	 * isOpen 是否签约(1是已签约,0是未签约)， status 是否可用(0是可用,1是不可用)，
	 */
	public abstract int countFirmAccount(String bankId, String firmId, String cardType, String isOpen, String status);

	/** 查询交易商和银行绑定的个数 */
	public abstract int countFirmAccount(String file);

	/**
	 * 修改交易商银行冻结资金 filter 查询条件， money 修改金额， conn 数据库连接对象
	 */
	public abstract int modBankFrozenFuns(String filter, double money, Connection conn) throws SQLException;

	/**
	 * 交易商签约
	 */
	public abstract int openCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException;

	/**
	 * 查询交易系统中交易商某天资金情况
	 * 
	 * @param firmID
	 *            交易商代码
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<FirmFundsValue>
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract Vector<FirmFundsValue> getFrimFunds(String firmID, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	// *************************************深发展银行的对账信息********************************************
	/** 添加清算日期 */
	public abstract int addBankQS(BankQSVO bq, Connection conn) throws SQLException;

	/** 查询最后清算日 */
	public abstract java.util.Date getMaxBankQSList(String bankID, java.util.Date date, Connection conn) throws SQLException;

	/**
	 * 添加会员的签解约信息
	 */
	public abstract int addFirmKXH(KXHfailChild child, String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 根据银行流水号查询签解约信息
	 */
	public abstract KXHfailChild getFirmKXH(String funID) throws SQLException, ClassNotFoundException;

	/**
	 * 添加银行对账不平记录
	 */
	public abstract int addBatCustDz(BatCustDzFailChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException;

	/** 查询银行对账不平文件 */
	public abstract Vector<BatCustDzFailChild> getBatCustDz(java.util.Date date, String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 删除对账不平记录
	 */
	public abstract int delBatCustDz(java.util.Date date, String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 添加交易商余额文件
	 */
	public abstract int addFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 修改交易商子账号信息
	 */
	public abstract int modFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException;

	/**
	 * 查询交易商余额信息
	 */
	public abstract Vector<BatCustDzBChild> getFirmBalanceFile(String ThirdCustId, String bankID, java.util.Date date) throws SQLException, ClassNotFoundException;

	/** 添加银行对账失败文件 */
	public abstract int addFirmBalanceError(BatFailResultChild fbe, String bankID) throws SQLException, ClassNotFoundException;

	/** 删除相应的银行对账失败文件信息 */
	public abstract int delFirmBalanceError(String bankID, String date) throws SQLException, ClassNotFoundException;

	/** 查询会员签解约不对应信息 */
	public abstract Vector<FirmOpenCloseBank> getFirmBank(String bankID, java.util.Date date) throws SQLException, ClassNotFoundException;

	/** 获取某日的清算信息 */
	public abstract Map<String, BatQsChild> getQSChild(String bankID, Set<String> firmIDs, Set<String> notFirmIDs, java.util.Date qdate, Connection conn) throws SQLException;

	/** 取非某一天的最后一天 */
	public abstract java.util.Date getlastDate(java.util.Date date, Connection conn) throws SQLException;

	/** 查询银行对账失败信息 */
	public abstract Vector<BatFailResultChild> getFirmBalanceError(String[] firmIDs, String bankID, java.util.Date date) throws SQLException, ClassNotFoundException;

	/** 查询银行对账不平文件 */
	public abstract Vector<BatCustDzFailChild> getBatCustDz(String[] firmIDs, String bankID, java.util.Date date) throws SQLException, ClassNotFoundException;

	/**
	 * 查询某一天的签约信息 status 交易状态(1:开户 2:销户 3:待确认)
	 */
	public abstract Vector<KXHfailChild> getBankOpen(String bankID, String[] firmIDs, int status, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	// 建设银行清算方法
	/**
	 * 建设银行发送市场交易商资金变化量信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            要查询的交易商编号
	 * @param qdate
	 *            查询的日期
	 * @return Vector<FirmBalance>
	 */
	public abstract Vector<FirmBalance> getFirmBalance(String bankID, String[] firmIDs, java.util.Date qdate) throws SQLException, Exception;

	/**
	 * 保存银行返回的错误信息到数据库中
	 */
	public abstract long addQSResult(QSRresult qsResult, Connection conn) throws SQLException;

	/**
	 * 删除银行错误信息
	 */
	public abstract long delQSResult(String bankID, java.util.Date tradeDate, Connection conn) throws SQLException;

	/**
	 * 查询银行错误信息表
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码列表
	 */
	public abstract Vector<QSRresult> getQSList(String bankID, String[] firmIDs, int status, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	// 华夏银行清算方法
	/**
	 * 华夏银行清算对账信息查询
	 * 
	 * @param bankID
	 *            交易商代码
	 * @param firmIDs
	 *            交易商代码集合
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<HXSentQSMsgValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<HXSentQSMsgValue> getHXQSMsg(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	/**
	 * 保存银行清算失败信息
	 * 
	 * @param qs
	 *            清算信息
	 * @param conn
	 *            数据库连接
	 * @return int 插入信息条数
	 * @throws SQLException
	 */
	public abstract int addQSError(QSChangeResult qs, Connection conn) throws SQLException;

	/**
	 * 查询银行清算失败信息
	 * 
	 * @parm filter 查询条件
	 * @parm conn 数据库连接
	 * @return Vector<QSChangeResult>
	 * @throws SQLException
	 */
	public abstract Vector<QSChangeResult> getQSError(String filter, Connection conn) throws SQLException;

	/**
	 * 修改银行清算失败信息
	 * 
	 * @parm qs 清算信息
	 * @param conn
	 *            数据库连接
	 * @return int 修改条数
	 * @throws SQLException
	 */
	public abstract int modQSError(QSChangeResult qs, Connection conn) throws SQLException;

	/**
	 * 删除银行清算失败信息
	 * 
	 * @parm qs 清算信息
	 */
	public abstract int delQSError(QSChangeResult qs, Connection conn) throws SQLException;

	// 浦发银行清算方法
	/**
	 * 提取保存改变交易商权益方法
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集合
	 * @param tradeDate
	 *            交易日期
	 * @param conn
	 *            数据库连接
	 * @return int 添加条数
	 * @throws SQLException
	 */
	public abstract int addChangeFirmRights(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws SQLException;

	/**
	 * 提取保存改变交易商冻结资金方法
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集合
	 * @param qdate
	 *            交易日期
	 * @param conn
	 *            数据库连接
	 * @return int 添加条数
	 * @throws SQLException
	 */
	public abstract int addChangeFirmFrozen(String bankID, String[] firmIDs, java.util.Date qdate, Connection conn) throws SQLException;

	/**
	 * 删除交易商权益变化量表信息信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param qdate
	 *            清算日期
	 * @param SERIAL_IDs
	 *            流水编号
	 * @param conn
	 *            数据库连接
	 * @param int 删除条数
	 * @throws SQLException
	 */
	public abstract int delChangeFirmRights(String bankID, java.util.Date qdate, String[] SERIAL_IDs, Connection conn) throws SQLException;

	/**
	 * 删除交易商冻结资金变化量表信息信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param qdate
	 *            清算日期
	 * @param SERIAL_IDs
	 *            流水编号
	 * @param conn
	 *            数据库连接
	 * @throws SQLException
	 */
	public abstract int delChangeFirmFrozen(String bankID, java.util.Date qdate, String[] SERIAL_IDs, Connection conn) throws SQLException;

	/**
	 * 修改交易商权益变化量表信息状态
	 * 
	 * @param SERIAL_ID
	 *            流水编号
	 * @param flag
	 *            修改状态
	 * @param conn
	 *            数据库连接
	 * @return int 修改条数
	 * @throws SQLException
	 */
	public abstract int modChangeFirmRights(String SERIAL_ID, String flag, Connection conn) throws SQLException;

	/**
	 * 修改交易商冻结资金变化量表信息信息
	 * 
	 * @param SERIAL_ID
	 *            流水编号
	 * @param flag
	 *            修改状态
	 * @param conn
	 *            数据库连接
	 * @return int 修改条数
	 * @throws SQLException
	 */
	public abstract int modChangeFirmFrozen(String SERIAL_ID, String flag, Connection conn) throws SQLException;

	/**
	 * 查询交易商权益变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库连接
	 * @return Vector<TradeList>
	 * @throws SQLException
	 */
	public abstract Vector<TradeList> getChangeFirmRights(String filter, Connection conn) throws SQLException;

	/**
	 * 查询交易商冻结资金变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库连接
	 * @return Vector<Margins>
	 * @throws SQLException
	 */
	public abstract Vector<Margins> getChangeFirmFrozen(String filter, Connection conn) throws SQLException;

	/**
	 * 查询交易商权益变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector<TradeList>
	 * @throws SQLException
	 * @throws ClassNotFountException
	 */
	public abstract Vector<TradeList> getChangeFirmRights(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 查询交易商冻结资金变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector<Margins>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<Margins> getChangeFirmFrozen(String filter) throws SQLException, ClassNotFoundException;

	// 工商银行清算方式
	/**
	 * 从财务和交易系统获取交易商资金余额及各交易板块权益
	 * 
	 * @param String
	 *            bankId String firmid,String qdate
	 * @return HashMap<String, TradeDataValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, java.util.Date qdate) throws SQLException, ClassNotFoundException;

	/**
	 * 从财务和交易系统获取交易商资金余额及各交易板块权益
	 * 
	 * @param String
	 *            bankId String firmid,java.util.Date date
	 * @return HashMap<String, TradeDataValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, String qdate) throws SQLException, ClassNotFoundException;

	/** 查询银行传来的对账结果 */
	public abstract Vector<BankFirmRightValue> getBankCapital(BankFirmRightValue bfrv);

	/** 查询银行传来的对账结果 */
	public abstract Vector<BankFirmRightValue> getBankCapital(String filter);

	/** 添加总分平衡监管 */
	public abstract long addProperBalance(ProperBalanceValue pbv);

	/** 修改总分平衡监管 */
	public abstract long updateProperBalance(ProperBalanceValue pbv);

	/** 删除总分平衡监管 */
	public abstract long delProperBalance(ProperBalanceValue pbv);

	/** 查询总分平衡监管 */
	public abstract Vector<ProperBalanceValue> getProperBalance(ProperBalanceValue pbv);

	/** 查询总分平衡监管 */
	public abstract Vector<ProperBalanceValue> getProperBalance(String filter);

	/** 修改银行传来的对账结果 */
	public abstract long updateBankCapital(BankFirmRightValue bfrv);

	/** 添加银行传来的对账结果 */
	public abstract long addBankCapital(BankFirmRightValue bfrv);

	// 兴业银行清算方式
	/**
	 * 取得兴业银行清算信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<XYQSValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	/**
	 * 取得兴业银行对账信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<XYQSValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	/**
	 * 添加总分平衡监管
	 * 
	 * @param afph
	 *            总分平衡信息
	 * @param conn
	 *            数据库连接
	 * @return int 插入条数
	 * @throws SQLException
	 */
	public abstract int addZFPH(ZFPHValue zfph, Connection conn) throws SQLException;

	/**
	 * 查询总分平衡监管
	 * 
	 * @param bankID
	 *            银行编号
	 * @param tradeDate
	 *            交易日期
	 * @param result
	 *            总分结果
	 * @return Vector<ZFPHValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result) throws SQLException, ClassNotFoundException;

	/**
	 * 查询总分平衡监管
	 * 
	 * @param bankID
	 *            银行编号
	 * @param tradeDate
	 *            交易日期
	 * @param result
	 *            总分结果
	 * @param conn
	 *            数据库连接
	 * @return Vector<ZFPHValue>
	 * @throws SQLException
	 */
	public abstract Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn) throws SQLException;

	/**
	 * 删除总分平衡监管
	 * 
	 * @param bankID
	 *            交易商代码
	 * @param tradeDate
	 *            交易日期
	 * @param result
	 *            监管结果
	 * @param conn
	 *            数据库连接
	 * @return int
	 * @throws SQLException
	 */
	public abstract int delZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn) throws SQLException;

	/**
	 * 添加分分核对监管
	 * 
	 * @param ffhd
	 *            分分核对数据
	 * @param conn
	 *            数据库连接
	 * @return int 添加条数
	 * @throws SQLException
	 */
	public abstract int addFFHD(FFHDValue ffhd, Connection conn) throws SQLException;

	/**
	 * 查询分分核对监管
	 * 
	 * @param firmID
	 *            交易商代码
	 * @param bankID
	 *            银行编号
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<FirmDateValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<FirmDateValue> getFFHD(String firmID, String bankID, java.util.Date tradeDate) throws SQLException, ClassNotFoundException;

	/**
	 * 查询分分核对监管
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库连接
	 * @return Vector<FirmDateValue>
	 * @throws SQLException
	 */
	public abstract Vector<FirmDateValue> getFFHD(String filter, Connection conn) throws SQLException;

	/**
	 * 删除分分核对监管
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集
	 * @param tradeDate
	 *            交易日期
	 * @param conn
	 *            数据库连接
	 * @return int 删除数量
	 * @throws SQLException
	 */
	public abstract int delFFHD(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws SQLException;

	/**
	 * 添加市场自有资金变动表
	 * 
	 * @param xymm
	 *            银行自有金额变动类
	 * @return int 添加条数
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int addMarketMoney(XYMarketMoney xymm) throws SQLException, ClassNotFoundException;

	/**
	 * 修改市场自有资金变动表
	 * 
	 * @param xymm
	 *            银行自有金额变动类
	 * @return int 修改条数
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract int modMarketMoney(XYMarketMoney xymm) throws SQLException, ClassNotFoundException;

	/**
	 * 查询市场自有资金变动表
	 */
	public abstract Vector<XYMarketMoney> getMarketMoney(String filter) throws SQLException, ClassNotFoundException;

	// --------------------------招行订制  start----------------------------------------
	/**
	 * 添加客户协议状态信息
	 * 
	 * @param val
	 *            客户协议状态信息
	 * @return long 对账信息流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public abstract long addFirmTradeStatus(FirmTradeStatus val) throws SQLException;

	/**
	 * 添加账户类交易对账信息
	 * 
	 * @param val
	 *            账户类交易对账信息
	 * @return long 对账信息流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public abstract long addTradeDetailAccount(TradeDetailAccount val) throws SQLException;

	/**
	 * 取得客户协议状态信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<FirmTradeStatus> getFirmTradeStatusList(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 取得账户类交易对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public abstract Vector<TradeDetailAccount> getTradeDetailAccountList(String filter) throws SQLException, ClassNotFoundException;

	// --------------------------招行订制   end----------------------------------------
	// ---------------------------------------------民生银行清算---------------------------------start/
	public abstract Vector<FirmBalance> getFirmBalance(String bankID, java.util.Date qdate) throws SQLException, Exception;

	// ---------------------------------------------民生银行清算---------------------------------end/
	/** 银行间轧差数据展示-平安版 2012.12.03 zjj */
	public abstract Vector<BankQSNetChild> getQSBankDate(String bankID, java.util.Date qdate) throws SQLException, Exception;

	// ---------------------------------------------国付宝 G商贸通 定制 begin  --------------------------------------------
	/**
	 * 取得市场签约流水记录列表
	 * 
	 * @param filter
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为RgstCapitalValue
	 * @throws SQLException
	 */
	public abstract Vector<RgstCapitalValue> getRgstCapitalValue(String filter, Connection conn) throws SQLException;

	/**
	 * 添加签约流水记录列表
	 * 
	 * @param bankid
	 *            银行代码
	 * @param firmid
	 *            交易商代码
	 * @param status
	 *            银行流水状态
	 * @param actionid
	 *            市场流水号
	 * @param type
	 *            交易类型
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public abstract int addRgstCapitalValue(RgstCapitalValue rc, Connection conn) throws SQLException;

	/**
	 * 修改签约流水记录列表
	 * 
	 * @param bankid
	 *            银行代码
	 * @param firmid
	 *            交易商代码
	 * @param status
	 *            银行流水状态
	 * @param actionid
	 *            市场流水号
	 * @param type
	 *            交易类型
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public abstract int modRgstCapitalValue(String bankid, String firmid, String account, Timestamp banktime, int status, long actionid, int type, Connection conn) throws SQLException;

	// ---------------------------------------------国付宝 G商贸通 定制   end---------------------------

	// ---------------------------------------------中行 定制   begin--------------------
	/**
	 * 查询客户账户状态对账信息
	 * 
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public abstract Vector<ClientState> getClientState(String filter) throws SQLException, ClassNotFoundException;

	/**
	 * 添加客户账户状态对账信息
	 * 
	 * @param state
	 * @return
	 * @throws SQLException
	 */
	public abstract int addClientState(ClientState state) throws SQLException, ClassNotFoundException;

	/**
	 * 查询转账交易明细信息
	 * 
	 * @param queryFileter
	 * @param tradeDate
	 * @param firmIDs
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, String[] firmIDs, Date tradeDate, Connection conn) throws SQLException;

	/**
	 * 查询客户账户状态信息
	 * 
	 * @param queryFileter
	 * @param firmIDs
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract List<AccountStatusReconciliation> getKHZHZT(String bankId, String[] firmIDs, Date tradeDate, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 查询存管客户资金交收明细信息
	 * 
	 * @param queryFileter
	 * @param tradeDate
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, Date tradeDate, Connection conn) throws SQLException, ClassNotFoundException;

	/**
	 * 查询存管客户资金台账余额明细信息
	 * 
	 * @param queryFileter
	 * @param tradeDate
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, Date tradeDate, Connection conn) throws SQLException, ClassNotFoundException;

	// ---------------------------------------------中行 定制  end--------------------

	/**
	 * 插入银行接口和银行通讯信息
	 */
	public abstract int interfaceLog(InterfaceLog log) throws SQLException, ClassNotFoundException;

	/**
	 * 查询银行接口和银行通讯信息
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector<InterfaceLog> 日志信息
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public abstract Vector<InterfaceLog> interfaceLogList(String filter) throws SQLException, ClassNotFoundException;
	
	/**查询交易商轧差数据 */
	public abstract List<BankQSNetChild> getQSFirmDate(String bankID,String firmID,java.util.Date qdate)throws SQLException,Exception;

	//-------------------------------跨行定制  2013.03.21 start------------------------------------
	/**获取银行间资金划转流水信息*/
	public abstract Vector<BankTransferValue> getBankTransferList(String filter)throws SQLException,ClassNotFoundException;
	/**获取银行间资金划转流水信息*/
	public abstract Vector<BankTransferValue> getBankTransferList(String filter,Connection conn)throws SQLException,ClassNotFoundException;
	/**增加银行间资金划转流水*/
	public abstract long addBankTransfer(BankTransferValue bankTransfer,Connection conn)throws SQLException,ClassNotFoundException;
	  /**增加银行间资金划转流水*/
	public abstract long addBankTransfer(BankTransferValue bankTransfer)throws SQLException,ClassNotFoundException;
	/**修改银行间资金划转流水状态*/
	public abstract long modBankTransfer(long id,int status,Connection conn)throws SQLException,ClassNotFoundException;
	/**获取银行端科目信息*/
	public abstract Vector<Account> getAccList(String filter)throws SQLException,ClassNotFoundException;
	/**------工行跨行汇拨 zjj 2012.09.27 -------*/
	public abstract TransferBank getTransferBank(String id) throws SQLException, ClassNotFoundException;

	
	
	/**------工行跨行汇拨 zjj 2012.09.27 -------*/
	//-------------------------------跨行定制  2013.03.21 start------------------------------------
	
	/**
	 * 该方法用来存储华夏他行签约所需的华夏银行以外的银行信息
	 * @param filter 查询银行的条件
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<BanksInfoValue> getBanksInfo(String filter) throws SQLException, ClassNotFoundException;
	/**
	 * 该方法用来存储华夏他行签约所需的他行所在地信息
	 * @param filter 查询银行的条件
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract Vector<CitysValue> getCityOfBank(String filter) throws SQLException, ClassNotFoundException;

}

package gnnt.trade.bank.processorrmi;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public interface CapitalProcessorRMI extends Remote{
	/**
	 * 客户端判定连通处理器方法
	 * @return String
	 */
	public String testRmi() throws RemoteException;
	/**
	 * 取得市场业务流水号
	 * @return long 市场业务流水号，返回0表示操作失败
	 */
	public long getMktActionID() throws RemoteException;
	/**
	 * 取得银行信息列表
	 * @param filter 查询条件
	 * @return Vector<BankValue> 
	 */
	public Vector<BankValue> getBankList(String filter) throws RemoteException;
	/**
	 * 取得银行信息
	 * @param bankID 银行编号
	 * @return BankValue
	 */
	public BankValue getBank(String bankID) throws RemoteException;
	/**
	 * 修改银行信息
	 * @param bankValue 银行信息表
	 * @return ReturnValue
	 */
	public ReturnValue modBank(BankValue bankValue) throws RemoteException;
	/**
	 * 银行交易禁用/恢复
	 * @param bankID 银行编号
	 * @param type 修改类型(0 修改银行可用状态,1 修改入金可用状态,2 修改出金可用状态)
	 * @param value 操作状态(0 可用,1 禁用)
	 * @return ReturnValue
	 */
	public ReturnValue changeBankTradeType(Vector<String> bankID,int type,int value) throws RemoteException;
	/**
	 * 根据银行ID获取市场银行信息
	 * @param bankID 银行代码
	 * @return Hashtable<name,value>
	 */
	public Hashtable<String,String> getBankInfo(String bankID) throws RemoteException;
	/**
	 * 获取交易账号银行对应关系
	 * @param contacts 交易账号和银行绑定号
	 * @param bankID 银行编号
	 * @return 
	 */
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> contacts,String bankID)  throws RemoteException;
	/**
	 * 获取交易账号银行对应关系
	 * @param filter 
	 * @return 
	 */
	public Vector<CorrespondValue> getCorrespondValue(String filter)  throws RemoteException;
	/**
	 * 取得交易账号信息列表
	 * @param filter 查询条件
	 * @return Vector<FirmValue>
	 */
	public Vector<FirmValue> getFirmUser(String filter) throws RemoteException;
	/**
	 * 验证密码正确性
	 * @param firmID 交易账号代码
	 * @param password 交易账号密码
	 * @return long (0 成功;1 未设置密码;-1 验证失败;-2 查询不到交易账号)
	 */
	public long isPassword(String firmID, String password) throws RemoteException;
	/**
	 * 修改交易账号密码
	 * @param firmID 交易账号代码
	 * @param password 交易账号原密码
	 * @param chpassword 交易账号将要更改密码
	 * @return long(0 成功;-1 原密码错误;-2 未找到交易账号)
	 */
	public long modPwd(String firmID, String password, String chpassword) throws RemoteException;
	/**
	 * 初始化交易账号密码
	 * @param firmID 交易账号代码
	 * @param password 交易账号密码
	 * @return ReturnValue
	 */
	public ReturnValue initializeFrimPwd(String firmID,String password) throws RemoteException;
	/**
	 * 初始化交易账号密码
	 * @param firmID 交易账号代码
	 * @return ReturnValue
	 */
	public ReturnValue initializeFrimPwd(String firmID) throws RemoteException;
	/**
	 * 禁用、恢复交易账号绑定表状态
	 * @param cav 交易账号绑定信息
	 * @return ReturnValue
	 */
	public ReturnValue modCorrespondStatus(CorrespondValue cav) throws RemoteException;
	/**
	 * 银行账号注册
	 * @param correspondValue
	 * @return  long 操作结果：0 成功  非0 失败(1:信息不完整 2：交易账号不存在 3：银行不存在  4：账号已注册 5:银行签约失败 6：数据库操作失败 7：系统异常
	 */
	public long rgstAccount(CorrespondValue correspondValue) throws RemoteException;
	/**
	 * 银行开户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 */
	public ReturnValue openAccount(CorrespondValue cv) throws RemoteException;
	/**
	 * 市场开户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 */
	public ReturnValue openAccountMarket(CorrespondValue cv) throws RemoteException;
	/**
	 * 银行账号注销
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param bankAccount 银行账号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：账号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public long delAccount(CorrespondValue correspondValue) throws RemoteException;
	/**
	 * 银行账号注销
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param bankAccount 银行账号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：账号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public ReturnValue delAccountMaket(CorrespondValue correspondValue) throws RemoteException;
	/**
	 * 变更账户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 * @param cv1 原数据
	 * @param cv2 要修改的数据
	 * @return ReturnValue
	 */
	public ReturnValue modAccount(CorrespondValue cv1,CorrespondValue cv2) throws RemoteException;
	/**
	 * 市场变更账户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 * @param cv1 原数据
	 * @param cv2 要修改的数据
	 * @return ReturnValue
	 */
	public ReturnValue modAccountMarket(CorrespondValue cv1,CorrespondValue cv2) throws RemoteException;
	/**
	 * 判断是否可以发生转账交易
	 * @param bankID 银行编号
	 * @return long
	 */
	public long tradeDate(String bankID) throws RemoteException;
	/**
	 * 判断某一天是否是交易日
	 * @param tradeDate 日期
	 * @return ReturnValue  (0 可交易，-1 数据库异常，-2 系统异常，-3 不是交易日，-4 传入日期不合法)
	 */
	public ReturnValue isTradeDate(java.util.Date tradeDate) throws RemoteException;
	/**
	 * 取交易系统结算状态
	 * @return boolean 系统状态
	 * @throws SQLException
	 */
	public boolean getTraderStatus() throws RemoteException;
	/**
	 * 查询交易账号名下交易员信息
	 * @param contact 交易账号和银行绑定号
	 * @return FirmMessageVo
	 */
	public FirmMessageVo getFirmMSG(String contact)  throws RemoteException;
	/**
	 * 判断当前是否可以发生转账
	 * @param bankID 银行编号
	 * @param firmID 交易账号代码
	 * @param contact 交易账号和银行绑定号
	 * @param type 转账类型(0 入金，1 出金)
	 * @param launcher 发起方(0 市场,1 银行)
	 * @return ReturnValue
	 */
	public ReturnValue ifbankTrade(String bankID,String firmID,String contact,int type,int launcher) throws RemoteException;
	/**
	 * 入金，由adapter调用
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易账号代码
	 * @param account 交易账号银行账号
	 * @param bankTime 银行端入金时间
	 * @param funID 银行端业务流水号
	 * @param actionID 转账模块业务流水号
	 * @param funFlag 银行端操作成功或失败的标志 0：成功 1：失败，actionID>0时有效
	 * @param remark 备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行账号 -10002：非法交易账号代码 -10003：交易账号代码和账号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误
	 */
	public long inMoney (String bankID, String firmID ,String account ,Timestamp bankTime ,double money ,String funID, long actionID, int funFlag, String remark) throws RemoteException;
	/**
	 * 入金，由入金发起端调用
	 * @param imm 入金信息
	 * @return ReturnValue
	 */
	public ReturnValue inMoneyMarket(InMoneyMarket imm) throws RemoteException;
	/**
	 * 出金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易账号代码
	 * @param bankAccount 银行账号
	 * @param operator 出金发起端
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端出金
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
	 * @throws 
	 */
	public ReturnValue outMoney(String bankID,double money,String firmID ,String bankAccount , String funid,String operator,int express,int type) throws RemoteException;
	/**
	 * 出金 (市场方调用)
	 * @param omm 市场出金类
	 * @return ReturnValue
	 */
	public ReturnValue outMoneyMarket(OutMoneyMarket omm)throws RemoteException;
	/**
	 * 审核处理中的信息
	 * @param actionID   流水号
	 * @param funID 银行流水号
	 * @param funFlag    true 通过、false 拒绝
	 * @return ReturnValue
	 */
	public ReturnValue moneyInAudit(long actionID,String funID,boolean funFlag) throws RemoteException;
	/**
	 * 冲证
	 * @param actionID  转账模块业务流水号
	 * @param funID 银行业务流水号
	 * @return  操作结果：0 处理成功；-1 参数非法；-2 未找到要冲证的数据；-3 数据处理失败；-4 系统异常
	 */
	public ReturnValue chargeAgainst(ChargeAgainstValue cav) throws RemoteException;
	/**
	 * 查询交易商银行信息
	 * @param filter 查询条件
	 * @return Vector<FirmBankMsg>
	 * @throws RemoteException
	 */
	public Vector<FirmBankMsg> getfirmBankMsg(String filter) throws RemoteException;
	/**
	 * 查询交易账号市场资金
	 * @param contact 交易账号和银行绑定号
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String contact,String bankID) throws RemoteException;
	/**
	 * 查询交易账号市场资金
	 * @param firmID 交易账号代码
	 * @param contact 交易账号和银行绑定号
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String firmID,String contact,String bankID) throws RemoteException;
	/**
	 * 查询交易账号市场可用资金和银行账号余额
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getFirmBalance(String bankid,String firmid,String pwd) throws RemoteException;
	/**
	 * 获取资金流水数据
	 * @param filter 
	 * @return Vector<CapitalValue>
	 */
	public Vector<CapitalValue> getCapitalList(String filter) throws RemoteException;
	/**
	 * 获取银行对账信息
	 * @param bankID  银行ID 如果为null则通知所有适配器取银行数据
	 * @param date 获取数据日期
	 * @return int
	 */
	public int getBankCompareInfo(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 写入银行对账信息
	 * @param list 对账信息，每项为MoneyInfoValue
	 * @return int 操作结果：0 成功  非0 失败: -1对账日期不一致  -2对账信息为空  -3系统异常
	 */
	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) throws RemoteException;
	/**
	 * 提供给适配器的保存银行数据的方法
	 * @param mv：bank data  ready: true:insertBankMoneyInfo false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv,int ready) throws RemoteException;
	/**
	 * 处理日间产生的对账不平数据
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue roughInfo(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 查询市场有，银行没有流水的记录信息
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @return Vector<CompareResult>
	 */
	public Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 查询银行有，市场没有流水的记录信息
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @return Vector<CompareResult>
	 */
	public Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 查询交易账号当天出入金求和数据
	 * @param bankID 银行编号
	 * @param firmIDs 交易账号代码集
	 * @param date 转账日期
	 * @return Vector<CapitalCompare>
	 */
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws RemoteException;
	/**
	 * 批量冲正成功流水
	 * @param actionIDs 市场流水号列表
	 * @return ReturnValue
	 */
	public ReturnValue refuseCapitalInfo(Vector<Long> actionIDs) throws RemoteException;
	/**
	 * 冲正银行没有成功的流水
	 * @param actionID 市场流水号
	 * @return ReturnValue
	 */
	public ReturnValue refuseCapitalInfo(long actionID) throws RemoteException;/**
	 * 批量导入银行成功流水
	 * @param vector 流水信息
	 * @return ReturnValue
	 */
	public ReturnValue supplyCapitalInfo(Vector<CapitalValue> vector) throws RemoteException;
	/**
	 * 添加成功资金流水
	 * @param value 流水对象
	 * @return ReturnValue
	 */
	public ReturnValue supplyCapitalInfo(CapitalValue value) throws RemoteException;
	/**
	 * 查询银行接口和银行通讯信息
	 * @param filter 查询条件
	 * @return Vector<InterfaceLog> 查询出的信息
	 */
	public Vector<InterfaceLog> interfaceLogList(String filter,int[] pageinfo) throws RemoteException;
	/**
	 * 录入银行接口和银行通讯信息
	 * @param log 日志信息
	 * @return int 插入条数
	 */
	public int interfaceLog(InterfaceLog log) throws RemoteException;
}

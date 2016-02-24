package gnnt.trade.bank.processorrmi;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.pf.sent.FundsMarg;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface CapitalProcessorRMI extends Remote{


	/**
	 * 取得市场业务流水号
	 * @return long 市场业务流水号，返回-1表示操作失败
	 */
	public long getMktActionID() throws RemoteException;

	/**
	   * 取得银行信息列表
	   * @param filter 查询条件
	   * @return Vector 每一项为BankValue 
	   * @throws SQLException,ClassNotFoundException
	   */
	public Vector<BankValue> getBankList(String filter) throws RemoteException;
	
	/**
	 * 入金，由adapter调用
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param bankTime 银行端入金时间
	 * @param funID 银行端业务流水号
	 * @param actionID 转账模块业务流水号
	 * @param funFlag 银行端操作成功或失败的标志 0：成功 1：失败，actionID>0时有效
	 * @param remark 备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误
	 */
	public long inMoney
	(String bankID, String firmID ,String account ,Timestamp bankTime ,double money ,String funID, long actionID, int funFlag, String remark) throws RemoteException;

	/**
	 * 入金，由入金发起端调用
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	public long inMoneyMarket(String bankID, String firmID ,
			String account,String accountPwd,double money ,
			String remark,String InOutStart,
			String PersonName, String AmoutDate, String BankName,
			String OutAccount) throws RemoteException;
	
	/**
	 * 入金，由入金发起端调用
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	public long inMoneyMarket(String bankID, String firmID ,
			String account,String accountPwd,double money ,
			String remark) throws RemoteException;
	/**
	 * 出金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @param operator 出金发起端
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端出金
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
	 * @throws 
	 */
	public ReturnValue outMoney(String bankID,double money,String firmID ,String bankAccount , String funid,String operator,int express,int type) throws RemoteException;
	
	/**
	 * 划转资金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param type 资金划转类型 0：划转交易手续费
	 * @param operator 资金划转发起端
	 * @param firmID 交易商代码
	 * @return long 银行接口业务流水号 <0的值表示操作失败
	 * @throws 
	 */
	public long transferMoney(String bankID , int type , double money , String operator ,String firmID) throws RemoteException;
	
	/**
	 * 银行帐号注册
	 * @param correspondValue
	 * @return  long 操作结果：0 成功  非0 失败(1:信息不完整 2：交易商不存在 3：银行不存在  4：帐号已注册 5:银行签约失败 6：数据库操作失败 7：系统异常
	 */
	public long rgstAccount(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * 银行帐号注销
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：帐号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public long delAccount(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * 银行帐号注销
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：帐号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public long delAccountMaket(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * 给银行发送数据
	 * @param bankID 银行ID 如果为null则调用所有适配器的发送数据方法
	 * @param date 发送数据日期
	 * @return int 操作结果：0 成功  非0失败  1:没有找到bankID对应的适配器 2:交易系统尚未结算完毕 3:异常 负数：出错的适配器数量
	 */
	public int setMoneyInfo(String bankID,java.util.Date date) throws RemoteException;
	
	/**
	 * 给银行发送数据
	 * @param bankID 银行ID 如果为null则调用所有适配器的发送数据方法
	 * @param date 发送数据日期
	 * @param moduleid 板块号
	 * @return int 操作结果：0 成功  非0失败  1:没有找到bankID对应的适配器 2:交易系统尚未结算完毕 3:异常 负数：出错的适配器数量
	 */
	public long setMoneyInfoAutoOrNo(String bankID,java.util.Date date,int moduleid) throws RemoteException;
	
	/**
	 * 获取银行对账信息
	 * @param bankID  银行ID 如果为null则通知所有适配器取银行数据
	 * @param date 获取数据日期
	 * @return  操作结果：0 成功   -30011:没有找到bankID对应的适配器 -30012:将对账信息插入数据库发生错误（银行代码为空） -30013：将对账信息插入数据库发生错误（银行代码不为空）
	 */
	public int getBankCompareInfo(String bankID,java.util.Date date) throws RemoteException;

	
	/**
	 * 对账检查条件，看是否先对比流水笔数
	 * @return int 0:不对比笔数 1:比对笔数，笔数相等 2：比对笔数，笔数不等 -1:异常
	 * @throws 
	 */
	public int checkMoneyByNumber() throws RemoteException;
	
	
	/**
	 * 手工对账检查
	 * @param bankID 银行代码
	 * @param date 对账日期
	 * @return Vector<CompareResult> 操作结果：对账信息不一致的数据
	 * @throws 
	 */
	public Vector<CompareResult> checkMoney(String bankID,java.util.Date date) throws RemoteException;

	/**
	 * 查询交易商当天出入金求和数据
	 * @param bankID 银行编号
	 * @param firmIDs 交易商代码集
	 * @param date 转账日期
	 * @return Vector<CapitalCompare>
	 */
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws RemoteException;
	/**
	 * 自动对账检查 以配置的自动对账时间为准
	 * @param date 对账日期
	 * @return Vector<CompareResult> 操作结果：对账信息不一致的数据
	 * @throws 
	 */
	public Vector<CompareResult> checkMoney(java.util.Date date) throws RemoteException;

	/**
	 * 写入银行对账信息
	 * @param list 对账信息，每项为MoneyInfoValue
	 * @return int 操作结果：0 成功  非0 失败: -1对账日期不一致  -2对账信息为空  -3系统异常
	 */
	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) throws RemoteException;

	/**
	 * 根据银行ID获取市场银行信息
	 * @param bankID 银行代码
	 * @return Hashtable<name,value>
	 */
	public Hashtable<String,String> getBankInfo(String bankID) throws RemoteException;

	/**
	 * 获取资金流水数据
	 * @param filter 
	 * @return 获取资金流水数据
	 */
	public Vector<CapitalValue> getCapitalList(String filter) throws RemoteException;

	/**
	 * 获取交易商银行对应关系
	 * @param filter 
	 * @return 
	 */
	public Vector<CorrespondValue> getCorrespondValue(String filter)  throws RemoteException;
	/**
	 * 获取交易商银行对应关系
	 * @param filter 
	 * @return 
	 */
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> firmIDs,String bankID)  throws RemoteException;
	/**
	 * 冲证
	 * @param actionID  转账模块业务流水号
	 * @param funID 银行业务流水号
	 * @return  操作结果：0 处理成功；-1 参数非法；-2 未找到要冲证的数据；-3 数据处理失败；-4 系统异常
	 */
	public ReturnValue chargeAgainst(ChargeAgainstValue cav) throws RemoteException;
	/**
	 * 冲证(市场方调用)
	 * @param actionID  转账模块业务流水号
	 * @param funID 银行业务流水号
	 * @return  操作结果：0 处理成功；-1 参数非法；-2 未找到要冲证的数据；-3 数据处理失败；-4 系统异常
	 */
	public ReturnValue chargeAgainstMaket(ChargeAgainstValue cav) throws RemoteException;
	
	/**
	 * 取得交易商交易系统资金明细
	 * @param Date 结算日期
	 * @return  Hashtable key 交易商代码；value 交易商资金属性集合（key 资金属性名称；value 资金属性值）
	 */
	public Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date) throws RemoteException;
	
	/**
	 * 将市场总金额[资金和权益]传给银行对账
	 * @param filter
	 * @param moduleid
	 * @return  Hashtable
	 */
	public long sendTotalMoneyToBank(String bankid,Hashtable<String, Double> ht) throws RemoteException;
	
	/**
	 * 适配器刷银行处理状态后通知处理器出金
	 * @param rv
	 * @return
	 * @throws RemoteException
	 */
	public long  outMoneyForAccess(int rvResult,String bankid,String firmid,String account,String actionid,String funcid) throws RemoteException;
	/**
	 * public String testRmi() throws RemoteException;
	 * test
	 */
	public String testRmi() throws RemoteException;
	
	/**
	 * 启动服务查询并绑定银行代码和适配器实现类名称
	 * @return hashtable<bankid,adapterClassname>
	 * 
	 */	
	public Hashtable<String,String> getBankIdAndAdapterClassname() throws RemoteException;
	
	/**
	 * 提供给适配器的保存银行数据的方法
	 * @param mv：bank data  ready: true:insertBankMoneyInfo false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv,int ready) throws RemoteException;
	
	/**
	 * 银行开户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue openAccount(CorrespondValue cv) throws RemoteException;
	/**
	 * 银行销户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue destroyAccount(CorrespondValue cv) throws RemoteException;
	
	/**
	 * 变更帐户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue modAccount(CorrespondValue cv1,CorrespondValue cv2) throws RemoteException;
	
	/**
	 * 查询交易商市场资金
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String firmid) throws RemoteException;
	
	/**
	 * 查询交易商市场可用资金和银行帐号余额
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getFirmBalance(String bankid,String firmid,String pwd) throws RemoteException;
	
	/**
	 * 给银行发送数据 以HashTable方式传
	 * @param bankID 银行ID 如果为null则调用所有适配器的发送数据方法
	 * @param date 发送数据日期
	 * @return int 操作结果：0 成功  负数失败  1:没有找到bankID对应的适配器 2:交易系统尚未结算完毕 3:异常 >100：(int-100)出错的适配器数量
	 */
	public long setMoneyInfoByHashtable(String bankID,Hashtable<String, TradeResultValue> ht)  throws RemoteException;
	
	/**
	 * 出金审核[1次]
	 * @param actionID  转账模块业务流水号
	 * @param funFlag  审核结果
	 * @param audit  手工[false]与自动[true] 
	 * @return long 0 成功 <0失败 
	 */
	public long outMoneyAudit(long actionID,boolean funFlag)  throws RemoteException;
	
	/**
	 * 出金二次审核
	 * @param actionID  转账模块业务流水号
	 * @param funFlag  审核结果 
	 * @return long 0 成功 <0失败 
	 */
	public long outMoneySecondAudit(long actionID,boolean funFlag)  throws RemoteException;
	
	/**
	 * 获取银行资金流水数据
	 * @param filter 
	 * @return 获取资金流水数据
	 */
	public Vector<BankCompareInfoValue> getBankCapList(String filter)  throws RemoteException;
	/**
	 * 查询交易商名下交易员信息
	 * @param firmid 交易员id
	 * @return string 密码
	 */
	public FirmMessageVo getFirmMSG(String firmid)  throws RemoteException;
	
	/**
	   * 取交易系统结算状态
	   * @return int 系统状态
	   * @throws SQLException
	   */
	public boolean getTraderStatus() throws RemoteException;
	/**
	 * 审核处理中的信息
	 * actionID   流水号,
	 * funFlag    通过、拒绝,
	 */
	public long moneyInAudit(long actionID,boolean funFlag) throws RemoteException;
	/**
	 * 判断是否可以发生转账交易
	 */
	public long tradeDate(String bankID) throws RemoteException;
	/**
	 * 判断密码合法性,true 验证成功;false 验证失败
	 * @param firmID 交易商代码
	 * @param password 交易商密码
	 * @return long (0 成功;1 未设置密码;-1 验证失败;-2 查询不到交易商)
	 */
	public long isPassword(String firmID, String password) throws RemoteException;
	/**
	 * 修改交易商密码
	 * @param firmID 交易商代码
	 * @param password 交易商原密码
	 * @param chpassword 交易商将要更改密码
	 * @return long(0 成功;-1 原密码错误;-2 为找到交易商)
	 */
	public long modPwd(String firmID, String password, String chpassword) throws RemoteException;
	/**
	 * 银行开户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue openAccountMarket(CorrespondValue cv) throws RemoteException;
	/**
	 * 同步子账号方法
	 */
	public ReturnValue synchroAccountMarket(CorrespondValue cv) throws RemoteException;
	/**
	 * 取得银行信息
	 */
	public BankValue getBank(String bankID) throws RemoteException;
	/**
	 * 判断系统是否已经结算
	 */
	public boolean getTradeFlag() throws RemoteException;

	/**
	 * 判断某一天是否是交易日
	 * @param tradeDate 日期
	 * @return ReturnValue 
	 */
	public ReturnValue isTradeDate(java.util.Date tradeDate) throws RemoteException;
	
//*********************************手工出入金*************************************
	/**
	 * 入金，由市场端调用
	 * @param bankID 银行代码(或银行名称)
	 * @param firmID 交易商代码V
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 * (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	@SuppressWarnings("static-access")
	public long inMoneyNoAdapter(String maketbankID, String firmID,String account,String bankName,double money ,String remark) throws RemoteException;
	/**
	 * 出金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @param funID 银行流水号
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端出金
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
	 * @throws 
	 */
	public long outMoneyNoAdapter(String maketbankID,double money,String firmID,String bankName,String account,String operator,int express,int type) throws RemoteException;
	/**
	 * 查询交易商市场资金
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getBalanceNoAdapter(String firmid) throws RemoteException;
	/**
	 * 出入金审核
	 * @param actionID  转账模块业务流水号
	 * @param funFlag  审核结果
	 * @param audit  手工[false]与自动[true] 
	 * @return long 0 成功 <0失败 
	 */
	public long moneyAuditNoAdapter(long actionID,boolean funFlag) throws RemoteException;
	/**
	 * 修改交易商信息(手工出入金)
	 */
	public ReturnValue modAccountNoAdapter(CorrespondValue correspondValue) throws RemoteException;
	/**
	 * 银行帐号注销(手工出入金)
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：帐号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public long delAccountNoAdapter(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * 判断交易商是否可以解约
	 * @param firmID 交易商代码
	 * @param bankID 银行编号
	 * @return ReturnValue
	 */
	public ReturnValue ifQuitFirm(String firmID,String bankID) throws RemoteException;
//*********************************深发特殊定制*************************************
	/**银行发起绑定子账户*/
	public ReturnValue relevanceAccount(CorrespondValue cv) throws RemoteException;
	/**添加会员开销户信息*/
	public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector,String bankID) throws RemoteException;
	/**添加会员开销户信息*/
	public ReturnValue saveFirmKXH(java.util.Date date,String bankID) throws RemoteException;
	/**获取银行发送来的对账不平文件*/
	public ReturnValue getBatCustDz(java.util.Date date,String bankID) throws RemoteException;
	/**获取银行发送来的对账不平文件*/
	public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd,java.util.Date date,String bankID) throws RemoteException;
	/**获取银行发送来的会员余额文件*/
	public ReturnValue getfirmBalanceFile(java.util.Date date,String bankID) throws RemoteException;
	/**获取银行发送来的会员余额文件*/
	public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf,java.util.Date date,String bankID) throws RemoteException;
	/**获取银行交易商对账失败文件*/
	public ReturnValue getFirmBalanceError(java.util.Date date,String bankID) throws RemoteException;
	/**获取银行交易商对账失败文件*/
	public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe,java.util.Date date,String bankID) throws RemoteException;
	/**查询银行生成文件的状态*/
	public ReturnValue getBankFileStatus(java.util.Date tradeDate,int type,String bankID) throws RemoteException;
	/**生成清算文件，通知银行对账*/
	public ReturnValue sentMaketQS(java.util.Date date,String bankID) throws RemoteException;
//********************************建设银行特殊定制*************************************
	/**
	 * 修改银行流水号
	 */
	public ReturnValue modCapitalInfoStatus(long actionID,String funID) throws RemoteException;
	/**给银行发送手续费和资金变化量*/
	public ReturnValue sentFirmBalance(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 银行查询市场流水
	 */
	public Map<String,CapitalValue> getCapitalValue(Vector<String> funids,String bankID) throws RemoteException;
	/**
	 * 保存银行清算结果信息
	 */
	public ReturnValue saveQSResult(String bankID,String tradeDate) throws RemoteException;
	/**
	 * 保存银行清算结果信息
	 */
	public ReturnValue saveQSResult(Vector<QSRresult> qsResult) throws RemoteException;
//********************************华夏银行特殊定制***************************************
	/**
	 * 给银行发送市场交易商信息
	 * @parm bankID 银行编号
	 * @param firmIDs 交易商代码集
	 * @return ReturnValue
	 */
	public ReturnValue synchronousFirms(String bankID,String[] firmIDs) throws RemoteException;
	/**
	 * 后台调用发送清算对账信息给银行
	 * @param bankID 交易商代码
	 * @param date 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue hxSentQS(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 后台调用发送清算对账信息给银行
	 * @param bankID 交易商代码
	 * @param date 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue hxSentDZ(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 适配器调用获取清算对账信息
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public Vector<HXSentQSMsgValue> hxGetQS(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 适配器调用获取清算对账信息
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public Vector<HXSentQSMsgValue> hxGetDZ(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 获取银行清算失败文件信息
	 * @param bankID 银行代码
	 * @param tradeDate 交易日期
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxGetQSError(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 获取银行对账失败文件信息
	 * @param bankID 银行代码
	 * @param tradeDate 交易日期
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxGetDZError(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 保存银行清算失败信息
	 * @param vector 信息集合
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxSaveQSError(Vector<QSChangeResult> vector) throws RemoteException;
	/**
	 * 保存银行对账失败信息
	 * @param vector 信息集合
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxSaveDZError(Vector<QSRresult> vector) throws RemoteException;
//********************************浦发银行特殊定制****************************************
	/**
	 * 后台调用保存清算信息
	 * @param bankID 银行编号
	 * @param date 保存的清算日期
	 */
	public ReturnValue pfdbQS(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 发送权益变化量
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param sendCount 每个包中所含条数
	 * @param timeOutCount 发送超时次数(超过次数停止发送)
	 * @param faileCount 银行返回错误数量(超过数量停止发送)
	 * @return ReturnValue
	 */
	public ReturnValue getTradesDateDetailsList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount) throws RemoteException;
	/**
	 * 返回权益变化量
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param flag 发送状态(N未发送 F银行处理失败 Y银行处理成功)
	 * @return Vector<TradeList>
	 */
	public Vector<TradeList> getTradesDateDetailsList(String bankID,java.util.Date date,String[] flag)throws RemoteException;
	/**
	 * 发送冻结资金
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param sendCount 每个包中所含条数
	 * @param timeOutCount 发送超时次数(超过次数停止发送)
	 * @param faileCount 银行返回错误数量(超过数量停止发送)
	 * @return ReturnValue
	 */
	public ReturnValue getDongjieDetailList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount) throws RemoteException;
	/**
	 * 返回冻结资金变化量
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param flag 发送状态(N未发送 F银行处理失败 Y银行处理成功)
	 * @return Vector<Margins>
	 */
	public Vector<Margins> getDongjieDetailList(String bankID,java.util.Date date,String[] flag) throws RemoteException;
	/**
	 * 取交易商可用余额，冻结资金，手续费
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @return Hashtable<String,FundsMarg>
	 */
	public Hashtable<String,FundsMarg> getFundsMarg(String bankID,java.util.Date date) throws RemoteException;
//******************************************************工商银行定制***************************************************8
	/**
	 * 发送工商银行清算信息
	 * @param bankId 银行编号
	 * @param firmId 交易商代码
	 * @param qdate 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue sendGHQS(String bankId,String firmId,java.util.Date qdate) throws RemoteException;
	/**
	 * 获取工商银行交易商权益
	 * @param bankId 银行编号
	 * @param firmId 交易商代码
	 * @param qdate 交易日期
	 * @return List<FirmRights>
	 */
	public List<FirmRights> getRightsList(String bankId,String firmId,java.util.Date qdate) throws RemoteException;
	/**
	 * 获取交易商当天的交易数据
	 * @param bankId 银行编号
	 * @param firmId 交易商代码
	 * @param qdate 交易日期
	 * @return List<TradingDetailsValue>
	 */
	public List<TradingDetailsValue> getChangeBalance(String bankId,String firmId,java.util.Date qdate) throws RemoteException;
	/**
	 * 获取当天签解约交易商信息
	 * @param bankId 银行编号
	 * @param qdate 交易日期
	 * @return List<OpenOrDelFirmValue>
	 */
	public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId,java.util.Date qdate) throws RemoteException;
	/**
	 * 交易商权益的分分核对
	 * @param list 分分核对数据
	 * @return long
	 */
	public long setBankFirmRightValue(List<BankFirmRightValue> list) throws RemoteException;
	/**
	 * 总分平衡监管
	 * @param pbv 总分平衡数据
	 * @return long
	 */
	public long setProperBalanceValue(ProperBalanceValue pbv) throws RemoteException;
	/**
	 * 获取分分核对数据
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public ReturnValue getBankFirmRightValue(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 获取总分平衡数据
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public ReturnValue getProperBalanceValue(String bankID,java.util.Date date) throws RemoteException;
//******************************************************兴业银行订制****************************************************
	/**
	 * 手工签到签退
	 * @param bankID 银行编号
	 * @param type (0: 签到 1: 签退)
	 */
	public ReturnValue updownBank(String bankID,int type) throws RemoteException;
	/**
	 * 发送交易商清算信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 清算日期
	 */
	public ReturnValue sendXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 获取交易商清算信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 交易日期
	 * @return Vector<XYQSValue>
	 */
	public RZQSValue getXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 获取交易商对账信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 交易日期
	 * @return Vector<XYDZValue>
	 */
	public RZDZValue getXYDZValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	/**
	 * 获取总分平衡监管
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue getZFPH(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 获取纷纷核对信息
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue getFFHD(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * 保存总分平衡监管信息
	 * @param zfph 总分平衡信息
	 * @return ReturnValue
	 */
	public ReturnValue saveZFPH(ZFPHValue zfph) throws RemoteException;
	/**
	 * 保存分分核对信息
	 * @param ffhd 分分核对信息
	 * @return ReturnValue
	 */
	public ReturnValue saveFFHD(FFHDValue ffhd) throws RemoteException;
	/**
	 * 添加市场自有资金变动表
	 * @param xymm 市场自有资金信息
	 * @return ReturnValue
	 */
	public ReturnValue addMarketMoney(XYMarketMoney xymm) throws RemoteException;
	/**
	 * 修改市场自有资金变动表
	 * @param xymm 市场自有资金信息
	 * @return ReturnValue
	 */
	public ReturnValue modMarketMoney(XYMarketMoney xymm) throws RemoteException;
	
	/**银行间资金划转结果通知*/
	public ReturnValue BankTransferResultNotice(long actionId,int optRst) throws RemoteException;
	public ReturnValue marketTransfer(BankTransferValue bankTransferValue) throws RemoteException;	
	public long bankTransfer(long id,int optFlag) throws RemoteException;	
//******************************************************哈尔滨银行订制****************************************************
	/**
	 * 发送交易商清算信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 清算日期
	 */
	public ReturnValue sendHRBQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;

//-----------------------------------招行订制  start---------------------------------------------------
	
	public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus, int ready)throws RemoteException;
	
	public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail, int ready)throws RemoteException;
	
	/**
	 * 银行开户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue preOpenAccountMarket(CorrespondValue cv) throws RemoteException;
	//-----------------------------------招行订制  end---------------------------------------------------
	//-----------------------------------民生订制  start------------------------------
	public ReturnValue sendCMBCQSValue(String bankID,Date date) throws RemoteException;
	/**
	 * 判断密码合法性,true 验证成功;false 验证失败
	 * @param firmID 交易商代码
	 * @param password 交易商密码
	 * @return long (0 成功;1 未设置密码;-1 验证失败;-2 查询不到交易商)
	 */
	public long isLogPassword(String firmID, String password) throws RemoteException;
	//-----------------------------------民生订制  end------------------------------
	/**
	 * 网上开户方法
	 */
	public ReturnValue marketOpenAccount(CorrespondValue cv) throws RemoteException;
	
	//------------------------------------国付宝 G商银通 begin------------------------------------
	/**
	 * 查询市场签约流水
	 */
	public Vector<RgstCapitalValue> getRgstCapitalValue(String file) throws RemoteException;
	/**
	 * 修改签约流水
	 * @param cv
	 * @return
	 */
	public ReturnValue modRgstCapitalValue(RgstCapitalValue rc) throws RemoteException;
	/**
	 * 增加签约流水
	 * @param cv
	 * @return
	 */
	public ReturnValue addRgstCapitalValue(CorrespondValue rc,int type) throws RemoteException;
	/**
	 * 修改资金流水状态
	 * actionID   流水号，
	 * funID 	银行流水号
	 * funFlag    通过、拒绝
	 * type       出入金
	 */
	public long modMoneyCapital(long actionID,String funID,boolean funFlag) throws RemoteException;
	
	/**
	 * 国付宝G商银通入金，由入金发起端调用
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	public long inMoneyMarketGS(String bankID, String firmID ,
			String account,String accountPwd,double money ,
			String remark) throws RemoteException;
	/**
	 * 国付宝出金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @param operator 出金发起端
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端出金
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
	 * @throws 
	 */
	public ReturnValue outMoneyGS(String bankID,double money,String firmID ,String bankAccount , String funid,String operator,int express,int type) throws RemoteException;
	/**
	 * 查询交易商市场可用资金和银行帐号余额
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getBankBalance(String bankid,String firmid,String pwd) throws RemoteException;
	//------------------------------------国付宝 G商银通 end------------------------------------
	//---------------------------------中行特殊定制 begin---------------------------------
	/**
	 * 银行端发起的预指定存管银行确认
	 * 
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:12:10
	 */
	public ReturnValue SpecifiedStorageTubeBankSure()throws RemoteException;
	/**
	 * 
	 * 中行测试通讯
	 * @param bankID
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:08:25
	 */
	public ReturnValue CommunicationsTest(String bankID) throws RemoteException;
	
	/**
	 * 中行通过证件信息进行预签约
	 * 
	 * @param bankid
	 * @param cardtype
	 * @param card
	 * @param account
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:11:14
	 */
	public ReturnValue BankYuSigning(String bankid,String cardtype,String card,String account)throws RemoteException;
	/**
	 * 银行端发起获取清算信息
	 * 
	 * @param bankID
	 * @param tradeDate
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:12:54
	 */
	public Hashtable<String, List> getZHQSValue(String bankID, Date tradeDate) throws RemoteException;
	/**
	 * 中行客户账号状态对账
	 * 
	 * @param states
	 * @param ready
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:12:47
	 */
	public long insertClientStates(Vector<ClientState> states,int  ready) throws RemoteException;
	/**
	 * 发送清算信息
	 * 
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28上午10:12:29
	 */
	public ReturnValue sendZHQS(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	
	//---------------------------------中行特殊定制 end--------------------------------
	//--------------通讯日志 start -----------------
	/**
	 * 录入银行接口和银行通讯信息
	 * @param log 日志信息
	 * @return int 插入条数
	 */
	public int interfaceLog(InterfaceLog log) throws RemoteException;
	//--------------通讯日志 end -----------------
	public Vector<CitysValue> getCitysValue(String filter) throws RemoteException;
}

package gnnt.trade.bank.util;

import java.util.Hashtable;

/**
 * 错误码 以及错误码对应的信息
 */
public class ErrorCode 
{
	
	/**
	 * 处理成功
	 */
	public static final long handle_success=0;
	/**
	 * 银行处理失败
	 */
	public static final long bankhandle_failure=-100;
	
	/**************************************银行入金***************************************************/
	/**
	 * 入金回调非法银行帐号
	 */
	public static final long inMoney_ErrorBankAcount=-10001;
	/**
	 * 入金回调非法交易商代码
	 */
	public static final long inMoney_ErrorFirmCode=-10002;
	/**
	 * 入金回调交易商代码和帐号对应关系错误
	 */
	public static final long inMoney_ErrorCorrespond=-10003;
	/**
	 * 入金回调数据库异常
	 */
	public static final long inMoney_DataBaseException=-10004;
	/**
	 * 入金回调系统异常
	 */
	public static final long inMoney_SysException=-10005;
	/**
	 * 入金回调参数错误
	 */
	public static final long inMoney_ErrorParameter = -10006;
	/**
	 * 入金回调交易商未签约
	 */
	public static final long inMoney_FirmNO = -10007;
	/**
	 * 入金回调交易商被禁用
	 */
	public static final long inMoney_FirmNOUse = -10008;
	/**
	 * 入金回调交易系统结算中
	 */
	public static final long inMoney_SystemClose = -10009;
	
	/**************************************交易所入金****************************************************/
	/**
	 * 交易所发起入金:非法银行帐号
	 */
	public static final long inMoneyM_ErrorBankAcount=-10011;
	/**
	 * 交易所发起入金:非法交易商代码
	 */
	public static final long inMoneyM_ErrorFirmCode=-10012;
	/**
	 * 交易所发起入金:交易商代码和帐号对应关系错误
	 */
	public static final long inMoneyM_ErrorCorrespond=-10013;
	/**
	 * 交易所发起入金:数据库异常
	 */
	public static final long inMoneyM_DataBaseException=-10014;
	/**
	 * 交易所发起入金:系统异常
	 */
	public static final long inMoneyM_SysException=-10015;
	/**
	 * 交易所发起入金:参数错误
	 */
	public static final long inMoneyM_ErrorParameter=-10016;
	/**
	 * 交易所发起入金:适配器提交失败
	 */
	public static final long inMoneyM_ErrorApdater=-10018;
	
	/**
	 * 交易所发起入金:交易商未签约
	 */
	public static final long inMoneyM_FirmNO=-10019;
	/**
	 * 交易所发起入金:交易商被禁用
	 */
	public static final long inMoneyM_FirmNOUse = -10020;
	/**
	 * 交易所发起入金:交易系统结算中
	 */
	public static final long inMoneyM_SystemClose = -10021;
	/**
	 * 交易所发起入金:银行返回,卡密码错误
	 */
	public static final long inMoneyM_Pwd = -10022;
	
	/**
	 * 交易所发起入金:银行端处理失败
	 */
	public static final long inMoneyM_Bank = -10023;
	/**
	 * 交易所发起入金:计算手续费错误
	 */
	public static final long inMoneyM_ErrorGetRate=-10024;
	/**
	 * 入金回调，记录资金流水错误
	 */
	public static final long inMoney_ErrorAddCapital=-10025;
	/**
	 * 交易所发起入金:写入资金流水失败
	 */
	public static final long inMoneyM_ErrorAddCapital=-10026;
	
	/*************************************银行出金***********************************************/
	
	/**
	 * 交易所发起出金:非法银行帐号
	 */
	public static final long outMoney_ErrorBankAcount=-20001;
	/**
	 * 交易所发起出金:非法交易商代码
	 */
	public static final long outMoney_ErrorFirmCode=-20002;
	/**
	 * 交易所发起出金:交易商代码和帐号对应关系错误
	 */
	public static final long outMoney_ErrorCorrespond=-20003;
	/**
	 * 交易所发起出金:数据库异常
	 */
	public static final long outMoney_DataBaseException=-20004;
	/**
	 * 交易所发起出金:系统异常
	 */
	public static final long outMoney_SysException=-20005;
	/**
	 * 交易所发起出金:银行端处理失败
	 */
	public static final long outMoney_BankProError=-20006;
	/**
	 * 交易所发起出金:参数错误
	 */
	public static final long outMoney_ErrorParameter=-20007;
	/**
	 * 交易所发起出金:交易商未签约
	 */
	public static final long outMoney_FirmNO=-20008;
	/**
	 * 交易所发起出金:交易商被禁用
	 */
	public static final long outMoney_FirmNOUse = -20009;
	/**
	 * 交易所发起出金:交易系统结算中
	 */
	public static final long outMoney_SystemClose = -20010;
	/**
	 * 交易所发起出金:银行返回，客户证件与开户时不符
	 */
	public static final long outMoney_CardNo = -20020;
	/**
	 * 交易所发起出金:银行返回，银行账号与开户时不符
	 */
	public static final long outMoney_BankNo = -20044;
	/**
	 * 交易所发起出金:记录资金流水错误
	 */
	public static final long outMoney_ErrorAddCapital=-20038;
	/**
	 * 交易所发起出金:资金余额不足
	 */
	public static final long outMoney_ErrorLackFunds=-20039;
	/**
	 * 交易所发起出金:计算出金手续费错误
	 */
	public static final long outMoney_ErrorGetRate=-20040;
	/**
	 * 交易所发起出金:交易商未签约
	 */
	public static final long outMoneyM_FirmNO=-20041;
	
	/************************************银行出金****************************************************/
	
	/**
	 * 银行发起出金:系统异常
	 */
	public static final long outMoneyB_SysException=-20029;
	/**
	 * 银行发起出金:数据库异常
	 */
	public static final long outMoneyB_DataBaseException=-20028;
	/**
	 * 银行发起出金:交易商代码和帐号对应关系错误
	 */
	public static final long outMoneyB_ErrorCorrespond=-20027;
	/**
	 * 银行发起出金:交易商被禁用
	 */
	public static final long outMoneyB_FirmNOUse = -20026;
	/**
	 * 银行发起出金:非法交易商代码
	 */
	public static final long outMoneyB_ErrorFirmCode=-20025;
	/**
	 * 银行发起出金:非法银行帐号
	 */
	public static final long outMoneyB_ErrorBankAcount=-20024;
	/**
	 * 银行发起出金:交易商未签约
	 */
	public static final long outMoneyB_FirmNO=-20023;
	/**
	 * 银行发起出金:交易系统结算中
	 */
	public static final long outMoneyB_SystemClose = -20022;
	/**
	 * 银行发起出金:参数错误
	 */
	public static final long outMoneyB_ErrorParameter=-20021;
	/**
	 * 银行发起出金:超出审核额度，出金拒绝成功
	 */
	public static final long outMoneyB_successRefused=-20043;
	
	/*********************************出金********************************************/
	
	/**
	 * 没有发现出金业务流水号
	 */
	public static final long outMoneyAudit_ErrorActionID=-20011;
	/**
	 * 交易所出金审核错误，非法交易商代码
	 */
	public static final long outMoneyAudit_ErrorFirmCode=-20012;
	/**
	 * 审核出金SQLException，数据回滚，需要手工处理出金和手续费
	 */
	public static final long outMoneyAudit_PassDataBaseException=-20013;
	/**
	 * 拒绝出金SQLException，数据回滚，需要手工处理出金和手续费
	 */
	public static final long outMoneyAudit_RefuseDataBaseException=-20014;
	/**
	 * 审核出金SQLException
	 */
	public static final long outMoneyAudit_DataBaseException=-20015;
	/**
	 * 审核出金Exception
	 */
	public static final long outMoneyAudit_SysException=-20016;
	/**
	 * 出金审核时间已过
	 */
	public static final long outMoneyAudit_ErrorTimeOut=-20017;
	/**
	 * 信息已在处理中
	 */
	public static final long outMoneyAudit_OradyAudite=-20042;
	/**
	 * 出金审核时间未到
	 */
	public static final long outMoneyAudit_ErrorTimeUn=-20018;
	/**
	 * 发送给银行 银行处理失败
	 */
	public static final long outMoneyAudit_ErrorBank=-20019;
	/***************************************入金**********************************************/
	
	/**
	 * 入金审核：入金审核时间已过
	 */
	public static final long inMoneyAudit_ErrorTimeOut=-20030;
	/**
	 * 入金审核：入金审核时间未到
	 */
	public static final long inMoneyAudit_ErrorTimeUn=-20031;
	/**
	 * 入金审核：交易所入金审核错误，非法交易商代码
	 */
	public static final long inMoneyAudit_ErrorFirmCode=-20032;
	/**
	 * 入金审核：审核入金SQLException，数据回滚，需要手工处理入金状态
	 */
	public static final long inMoneyAudit_PassDataBaseException=-20033;
	/**
	 * 入金审核：拒绝入金SQLException，数据回滚，需要手工处理入金状态
	 */
	public static final long inMoneyAudit_RefuseDataBaseException=-20034;
	/**
	 * 入金审核：没有发现入金业务流水号
	 */
	public static final long inMoneyAudit_ErrorActionID=-20035;
	/**
	 * 入金审核：数据库异常
	 */
	public static final long inMoneyAudit_DataBaseException=-20036;
	/**
	 * 入金审核：系统异常
	 */
	public static final long inMoneyAudit_SysException=-20037;
	/**
	 * 交易所入金，银行账户资金不足
	 */
	public static final long inMoneyAudit_BankMoneyNotEnough=-20042;
	
	/***********************************出入金*****************************************/
	/**
	 * 不符合出入金条件：超出每日单笔最大转账金额
	 */
	public static final long checkTrans_ErrorExceedDayMaxPerMoney=-30000;
	/**
	 * 不符合出入金条件：超出最大转账金额
	 */
	public static final long checkTrans_ErrorExceedMaxMoney=-30001;
	/**
	 * 不符合出入金条件：超出当日最大转账金额
	 */
	public static final long checkTrans_ErrorExceedDayMaxMoney=-30002;
	/**
	 * 不符合出入金条件：超出当日最大转账次数
	 */
	public static final long checkTrans_ErrorExceedDayMaxNum=-30003;
	/**
	 * 不符合出入金条件：数据库异常
	 */
	public static final long checkTrans_DataBaseException=-30004;
	/**
	 * 不符合出入金条件：系统异常
	 */
	public static final long checkTrans_SysException=-30005;
	/**
	 * 不符合出入金条件：超出当日单笔转账金额等待交易所审核
	 */
	public static final long checkTrans_ErrorSingleMaxMoney = -30006;
	/**
	 * 银行返回: 客户银行账号异常
	 */
	public static final long checkTrans_ErrorBankAccount = -30007;
	
	
	
	/******************************************签约*****************************************************/
	/**
	 * 银行帐号注册:信息不完整
	 */
	public static final long rgstAccount_InfoHalfbaked=-40001;
	/**
	 * 银行帐号注册:交易商不存在
	 */
	public static final long rgstAccount_NOFirm=-40002;
	/**
	 * 银行帐号注册:银行不存在
	 */
	public static final long rgstAccount_NOBank=-40003;
	/**
	 * 银行帐号注册:帐号已注册
	 */
	public static final long rgstAccount_GRSAcount=-40004;
	/**
	 * 银行帐号注册:银行签约失败
	 */
	public static final long rgstAccount_BankRgsFail=-40005;
	/**
	 * 银行帐号注册:数据库操作失败
	 */
	public static final long rgstAccount_DatabaseException=-40006;
	/**
	 * 银行帐号注册:系统异常
	 */
	public static final long rgstAccount_SysException=-40007;
	
	/**
	 * 银行帐号注册:密码错误
	 */
	public static final long rgstAccount_wrongPwd = -40008;
	/**
	 * 银行帐号注册:证件号码已注册
	 */
	public static final long rgstAccount_cardHaveExist = -40009;
	
	/**
	 * 银行帐号注销:信息不完整
	 */
	public static final long delAccount_InfoHalfbaked=-40011;
	/**
	 * 银行帐号注销:帐号未注册
	 */
	public static final long delAccount_NORgsAcount=-40014;
	/**
	 * 银行帐号注销:银行解约失败
	 */
	public static final long delAccount_BankDelFail=-40015;
	/**
	 * 银行帐号注销:数据库操作失败
	 */
	public static final long delAccount_DatabaseException=-40016;
	/**
	 * 银行帐号注销:系统异常
	 */
	public static final long delAccount_SysException=-40017;

	/**
	 * 查询交易所资金余额:银行接口交易商不存在
	 */
	public static final long queryMoney_NoneFirm = -50001;

	/**
	 * 查询交易所资金余额:交易系统交易商不存在
	 */
	public static final long queryMoney_TENoneFirm = -50002;

	/**
	 * 查询银行资金余额:银行接口交易商不存在
	 */
	public static final long queryBankMoney_NoneFirm = -50003;
	
	
	/**
	 * 签到：银行返回，企业本日未签到
	 */
	public static final long loginBank_None = -50004;

	/**
	 * 客户端连接：连接服务器超时
	 */
	public static final long client_timeout = -50005;

	/**
	 * 冲正：银行流水号不存在
	 */
	public static final long bankSer_None = -50006;

	/**
	 * 冲正：交易所流水号不存在
	 */
	public static final long marketSer_None = -50007;

	/**
	 * 冲正：调用交易系统失败
	 */
	public static final long marketBackMoney_False = -50008;
	/**
	 * 冲正：系统错误
	 */
	public static final long czSystem_error = -50009;

	/***************适配器*****************/
	/**
	 * 银行返回数据包为空
	 */
	public static final long bankNull = -50010;

	/**
	 * 发送数据IO异常
	 */
	public static final long sendDataError = -50011;
	/**
	 * 银行卡余额不足
	 */
	public static final long cardMoney_none = -50012;
	/**
	 * 当日卡转出累计金额超出协议限额
	 */
	public static final long beyond_AgreementMoney = -50013;
	/**
	 * 超出银行单笔转账金额
	 */
	public static final long beyond_AgreementMaxPerMoney = -50014;
	/**
	 * 冲正：传入参数为空
	 */
	public static final long charge_bankNull = -500001;
	/**
	 * 冲正：查询条件不足
	 */
	public static final long charge_noReasion = -500003;
	/**
	 * 冲正：查询交易商失败
	 */
	public static final long charge_noFirm = -500004;
	/**
	 * 冲正：数据库异常
	 */
	public static final long charge_SQLException = -500005;

	/****************************同步交易商************************/
	/**
	 * 同步交易商：数据库异常
	 */
	public static final long synFirm_DBException = -500014;

	/**
	 * 交易商未签到
	 */
	public static final long Firm_UnLogon = -600001;
	/**
	 * 7****入金、71****交易所入金、72***银行入金
	 * 8****出金、81***交易所出金、 82***银行出金
	 * 9****查询余额和其他、91***查询余额、92***其他
	 */
	/**出金，市场资金余额不足*/
	public static final long OutMoney_MaketBalanceError = -800002;
	/**银行已经闭市*/
	public static final long Bank_SysClose = -910020;
	/**银行未激活*/
	public static final long Bank_Unrecognised = -910021;
	/**银行激活：市场签约未成功*/
	public static final long RgsAccount_fail = -910030;
	/**获取银行连接器失败*/
	public static final long GetAdapter_Error = -920000;
	/**
	 * 银行返回其他异常
	 */
	public static final long Bank_OtherError=-920001;
	/**
	 * 解约交易商银行接口冻结资金不为0
	 */
	public static final long delAccount_FrozenFuns= -920002;
	/**信息不完整-兴业*/
	public static final long Error_MSG = -920002;
	/**
	 * 当前不是交易日
	 */
	public static final long Not_TradeDate = -920003;
	/**
	 * 转账交易时间未到
	 */
	public static final long Be_TradeTime = -920004;
	/**
	 * 转账交易时间已过
	 */
	public static final long Af_TradeTime = -920005;
	/**
	 * 禁止转账交易
	 */
	public static final long Co_Trade = -920006;
	/**手续费大于本金*/
	public static final long Fee_UnlessThanTransferMoney=-920007;
	/**交易所流水号冲突*/
	public static final long ActionID_Error = -920008;
	/**本条流水已处理*/
	public static final long ActionInAudit = -920009;
	/**
	 * 当笔交易已受理
	 */
	public static final long Audit_Arraidy = -930000;
	/**交易所解约可用余额不为0*/
	public static final long delAccount_NotZero=-941001;
	/**交易商当天有转账，不能解约*/
	public static final long delAccount_Transfer=-941002;
	/**银行账号被锁定*/
	public static final long inMoneyB_passwordLock=-710001;
	/**密码错误*/
	public static final long balance_FalsePwd = -910002;
	
// 交行错误码开始
	public static final long BCM_2_SP0001=14200001;//系统错误：冲正记录无对应转账流水  
	public static final long BCM_2_SP0002=14200002;//无市场编号{0}资金账号{1}的签约记录
	public static final long BCM_2_SP0003=14200003;//市场流水号{0}无对应的转账记录     
	public static final long BCM_2_SP0004=14200004;//市场流水号{0}对应的转账已冲正     
	public static final long BCM_2_SP0005=14200005;//无效密钥                          
	public static final long BCM_2_SP0006=14200006;//无效算法{0}                       
	public static final long BCM_2_SP0007=14200007;//MAC校验失败                       
	public static final long BCM_2_SP0008=14200008;//未查找当前日期是否为工作日        
	public static final long BCM_2_SP0009=14200009;//当日为休息日                      
	public static final long BCM_2_SP0099=14200099;//市场营运时段为工作日8:30~16:10
// 交行错误码结束
	
	//民生银行错误码---开始
	public static final long CMBC_1_PwdExce=-50051;//密码校验异常
	public static final long CMBC_1_NoPwd=-50052;//账户没有设置密码
	public static final long CMBC_1_PwdErr=-50053;//密码验证错误
	public static final long CMBC_1_AccErrDifferent=-50054;//资金账号错误（银行账号与资金账号不一致)
	public static final long CMBC_1_CardErr=-50055;//证件号码不正确
	public static final long CMBC_1_HavSigning=-50056;//该账号已签约，不可重复签约
	public static final long CMBC_1_AccErr=-50057;//资金账号错误
	public static final long CMBC_1_Status=-50058;//资金账户状态不正常
	public static final long CMBC_1_Cancellation_HavaMoney=-50059;//账户有余额，不能解约
	public static final long CMBC_1_Cancellation_HaveTransac=-50060;//当日有交易，不能解约
	public static final long CMBC_1_NoFoundAcc=-50061;//该账户不存在
	public static final long CMBC_1_NoSigning=-50062;//该账户未签约
	public static final long CMBC_1_MACVerificaFail=-50063;//MAC校验不通过
	public static final long CMBC_1_NoFoundFirm=-50064;//不存在该交易商或者交易商已销户
	public static final long CMBC_1_AccExce=-50065;//账户校验失败
	public static final long CMBC_1_NoMuchMoney=-50066;//资金账户可用余额不足
	public static final long CMBC_1_Change_HaveTransac=-50067;//当日有交易，不能变更账户
	public static final long CMBC_1_HadReverse=-50068;//流水已冲正，不可重复冲证
	public static final long CMBC_1_Yu_HavSigning=-50069;//该账号已签约，不可进行预签约
	public static final long CMBC_1_Y_Tosign=-50070;//该商品交易所已签退，请在交易时间内办理业务
	public static final long CMBC_1_Y_AccountsStatusNotNormal=-50071;//帐户状态不正常
	public static final long CMBC_2_Y_ReverseMoneyInconsistent=-50072;//对公活期帐户余额不足，无法冲正
	public static final long CMBC_2_Y_In_NoOut=-50073;//此帐户状态为只进不出
	public static final long CMBC_2_Y_BankHostError=-50074;//银行主机系统错误
	
	public static final long BOC_1_MACError=-42;//MAC校验错
	
	public static final long CMBC_1_Exce0101=-50101;//代码执行出现异常（签约）
	public static final long CMBC_1_Exce0102=-50102;//代码执行出现异常（解约）
	public static final long CMBC_1_Exce0103=-50103;//代码执行出现异常（入金）
	public static final long CMBC_1_Exce0104=-50104;//代码执行出现异常（出金）
	public static final long CMBC_1_Exce0105=-50105;//代码执行出现异常（冲入金）
	public static final long CMBC_1_Exce0106=-50106;//代码执行出现异常（冲出金）
	public static final long CMBC_1_Exce0107=-50107;//代码执行出现异常（账户变更）
	public static final long CMBC_1_Exce0108=-50108;//代码执行出现异常（查询余额）
	public static final long CMBC_1_Exce8888=-58888;//未知错误
	//民生银行错误码---结束
	
	//----------------- 中行错误信息----------begin
	public static final long BOC_1_Exce0101 = -50101; //签约失败
	public static final long BOC_1_Exce0102 = -50102; //解约失败
	public static final long BOC_1_Exce0103 = -50103; //入金失败
	public static final long BOC_1_Exce0104 = -50104; //出金失败
	public static final long BOC_1_Exce0105 = -50105; //冲入金失败
	public static final long BOC_1_Exce0106 = -50106; //冲出金失败
	public static final long BOC_1_Exce0107 = -50107; //账户变更失败
	public static final long BOC_1_Exce0108 = -50108; //查询余额失败
	public static final long BOC_1_Exce8888 = -58888; //失败
	public static final long BOC_1_PwdExce = -50051; //密码校验失败
	public static final long BOC_1_NoPwd = -50052; //账户没有设置密码
	public static final long BOC_1_PwdErr = -50053; //密码验证错误
	public static final long BOC_1_AccErrDifferent = -50054; //资金账号错误（银行账号与资金账号不一致）
	public static final long BOC_1_CardErr = -50055; //证件号码不正确
	public static final long BOC_1_HavSigning = -50056; //该账号已签约 ，不可重复签约
	public static final long BOC_1_AccErr = -50057; //资金账号错误或者交易商下不存在该银行账户
	public static final long BOC_1_Status = -50058; //账号不可用
	public static final long BOC_1_Cancellation_HavaMoney = -50059; //账户有余额，不能解约
	public static final long BOC_1_Cancellation_HaveTransac = -50060; //当日有交易，不能解约
	public static final long BOC_1_NoFoundAcc = -50061; //该账户不存在
	public static final long BOC_1_NoSigning = -50062; //该账户未签约
	public static final long BOC_1_MACVerificaFail = -50063; //MAC校验不通过
	public static final long BOC_1_NoFoundFirm = -50064; //不存在该交易商或者该交易商已销户
	public static final long BOC_1_AccExce = -50065; //账户校验失败
	public static final long BOC_1_NoMuchMoney = -50066; //资金账户可余额不足
	public static final long BOC_1_Change_HaveTransac = -50067; //当日有交易，不能变更账户
	public static final long BOC_1_HadReverse = -50068; //流水已冲正，不能重复冲正
	public static final long BOC_1_Yu_HavSigning = -50069; //该账号已签约，不能预签约
	public static final long BOC_1_Y_Tosign = -50070; //该商品交易所已签退，请在交易时间类办理业务
	public static final long BOC_1_Y_AccountsStatusNotNormal = -50071; //银行账户状态不正常
	public static final long BOC_2_Y_ReverseMoneyInconsistent = -50072; //对公活期账余额不足，无法冲正
	public static final long BOC_2_Y_In_NoOut = -50073;//此账户状态为只进不出
	public static final long BOC_2_Y_BankHostError = -50074; //银行主机系统错误


	
//----------------- 中行错误信息----------end
	
	/** 数据库异常* */
	public static final long dbError = -990001;

	/**
	 * 错误信息 key：错误码 value：错误信息
	 */
	public static Hashtable<Long,String> error=new Hashtable<Long,String>();
	/**
	 * 加载错误信息
	 */
	public void load()
	{
		error.put(handle_success, "处理成功");
		error.put(bankhandle_failure, "银行处理失败");

		error.put(inMoney_ErrorBankAcount, "入金回调非法银行帐号");
		error.put(inMoney_ErrorFirmCode, "入金回调非法交易商代码");
		error.put(inMoney_ErrorCorrespond, "入金回调交易商代码和帐号对应关系错误");
		error.put(inMoney_DataBaseException, "入金回调数据库异常");
		error.put(inMoney_SysException, "入金回调系统异常");
		error.put(inMoney_ErrorParameter, "入金回调参数错误");
		error.put(inMoney_FirmNO, "入金回调交易商未签约");
		error.put(inMoney_FirmNOUse, "入金回调账户被禁用");
		error.put(inMoney_SystemClose, "对不起，现在是闭市时间，不允许入金");

		error.put(inMoneyAudit_BankMoneyNotEnough, "交易商入金，银行账户资金不足");

		error.put(inMoneyM_ErrorBankAcount, "交易商发起入金:非法银行帐号");
		error.put(inMoneyM_ErrorFirmCode, "交易商发起入金:非法交易商代码");
		error.put(inMoneyM_ErrorCorrespond, "交易商发起入金:交易商代码和帐号对应关系错误");
		error.put(inMoneyM_DataBaseException, "交易商发起入金:数据库异常");
		error.put(inMoneyM_SysException, "交易商发起入金:系统异常");
		error.put(inMoneyM_ErrorParameter, "交易商发起入金:参数错误");
		error.put(inMoneyM_ErrorApdater, "交易商发起转账:适配器提交失败");
		error.put(inMoneyM_FirmNO, "交易商发起入金:交易商未签约");
		error.put(inMoneyM_FirmNOUse, "交易商发起入金：账户被禁用");
		error.put(inMoneyM_SystemClose, "对不起，现在是闭市时间，不允许入金");
		error.put(inMoneyM_Pwd, "交易商发起入金:银行返回,卡密码错误");
		error.put(inMoneyM_Bank, "交易商发起入金:银行端处理失败");
		error.put(inMoneyM_ErrorGetRate, "交易商发起入金:计算手续费错误");
		error.put(inMoney_ErrorAddCapital, "入金回调，记录资金流水错误");
		error.put(inMoneyM_ErrorAddCapital, "交易商发起入金:写入资金流水失败");

		error.put(outMoney_ErrorBankAcount, "交易商发起出金:非法银行帐号");
		error.put(outMoney_ErrorFirmCode, "交易商发起出金:非法交易商代码");
		error.put(outMoney_ErrorCorrespond, "交易商发起出金:交易商代码和帐号对应关系错误");
		error.put(outMoney_DataBaseException, "对不起，您当前的余额不足");
		error.put(outMoney_SysException, "交易商发起出金:系统异常");
		error.put(outMoney_BankProError, "交易商发起出金:银行端处理失败");
		error.put(outMoney_ErrorParameter, "交易商发起出金:参数错误");
		error.put(outMoney_FirmNO, "交易商发起出金:交易商未签约");
		error.put(outMoney_FirmNOUse, "交易商发起出金：交易商被禁用");
		error.put(outMoney_SystemClose, "对不起，现在是闭市时间，不允许出金");
		error.put(outMoney_CardNo, "交易商发起出金:银行返回，客户证件与开户时不符");
		error.put(outMoney_BankNo, "银行返回，客户银行卡号与开户时不符");
		error.put(outMoney_ErrorAddCapital, "交易商发起出金:记录资金流水错误");
		error.put(outMoney_ErrorLackFunds, "资金余额不足");
		error.put(outMoney_ErrorGetRate, "交易商发起出金:计算出金手续费错误");
		error.put(outMoneyM_FirmNO, "交易商发起出金，交易商未签约");

		error.put(outMoneyAudit_ErrorActionID, "出金审核：没有发现业务流水号");
		error.put(outMoneyAudit_ErrorFirmCode, "出金审核：非法交易商代码");
		error.put(outMoneyAudit_PassDataBaseException, "出金审核：同意出金SQLException，数据回滚，需要手工处理出金和手续费");
		error.put(outMoneyAudit_RefuseDataBaseException, "出金审核：拒绝出金SQLException，数据回滚，需要手工处理出金和手续费");
		error.put(outMoneyAudit_DataBaseException, "出金审核：SQLException");
		error.put(outMoneyAudit_SysException, "出金审核：Exception");
		error.put(outMoneyAudit_ErrorTimeOut, "出金审核：审核时间已过");
		error.put(outMoneyAudit_ErrorTimeUn, "出金审核：审核时间未到");
		error.put(outMoneyAudit_ErrorBank, "出金审核：发送给银行 银行处理失败");
		error.put(outMoneyB_successRefused, "银行发起出金:超出审核额度，出金拒绝成功");

		error.put(checkTrans_ErrorExceedDayMaxPerMoney, "不符合出金条件：超出每日单笔最大出金金额");
		error.put(checkTrans_ErrorExceedMaxMoney, "不符合出金条件：超出最大出金金额");
		error.put(checkTrans_ErrorExceedDayMaxMoney, "不符合出金条件：超出每日最大出金金额");
		error.put(checkTrans_ErrorExceedDayMaxNum, "不符合出金条件：超出每日最大出金次数");
		error.put(checkTrans_DataBaseException, "不符合出金条件：数据库异常");
		error.put(checkTrans_SysException, "不符合出金条件：系统异常");
		error.put(checkTrans_ErrorSingleMaxMoney, "出金提交成功，等待交易所审核");
		error.put(checkTrans_ErrorBankAccount, "银行提示: 客户银行账号异常");

		error.put(rgstAccount_InfoHalfbaked, "银行帐号注册:信息不完整");
		error.put(rgstAccount_NOFirm, "银行帐号注册:交易商不存在");
		error.put(rgstAccount_NOBank, "银行帐号注册:银行不存在");
		error.put(rgstAccount_GRSAcount, "银行帐号注册:帐号已注册");
		error.put(rgstAccount_BankRgsFail, "银行帐号注册:银行签约失败");
		error.put(rgstAccount_DatabaseException, "银行帐号注册:数据库操作失败");
		//rgstAccount_cardHaveExist
		error.put(rgstAccount_cardHaveExist, "银行账号注册：该证件已注册，不允许重复注册");
		error.put(rgstAccount_SysException, "银行帐号注册:系统异常");
		error.put(rgstAccount_wrongPwd, "交易商密码错误");

		error.put(delAccount_InfoHalfbaked, "银行帐号注销:信息不完整");
		error.put(delAccount_NORgsAcount, "银行帐号注销:帐号未注册");
		error.put(delAccount_BankDelFail, "银行帐号注销:银行解约失败");
		error.put(delAccount_DatabaseException, "银行帐号注销:数据库操作失败");
		error.put(delAccount_SysException, "银行帐号注销:系统异常");

		error.put(queryMoney_NoneFirm, "查询交易商资金余额:银行接口交易商不存在");
		error.put(queryMoney_TENoneFirm, "查询交易商资金余额:交易系统交易商不存在");
		error.put(queryBankMoney_NoneFirm, "查询银行资金余额:银行接口交易商不存在");

		error.put(loginBank_None, "不在转账时间内");

		error.put(outMoneyB_SysException, "银行发起出金:系统异常");
		error.put(outMoneyB_DataBaseException, "银行发起出金:数据库异常");
		error.put(outMoneyB_ErrorCorrespond, "银行发起出金:交易商代码和帐号对应关系错误");
		error.put(outMoneyB_FirmNOUse, "银行发起出金:交易商被禁用");
		error.put(outMoneyB_ErrorFirmCode, "银行发起出金:非法交易商代码");
		error.put(outMoneyB_ErrorBankAcount, "银行发起出金:非法银行帐号");
		error.put(outMoneyB_FirmNO, "银行发起出金:交易商未签约");
		error.put(outMoneyB_SystemClose, "对不起，现在是闭市时间，不允许出金");
		error.put(outMoneyB_ErrorParameter, "银行发起出金:参数错误");

		error.put(inMoneyAudit_ErrorTimeOut, "入金审核:入金审核时间已过");
		error.put(inMoneyAudit_ErrorTimeUn, "入金审核:入金审核时间未到");
		error.put(inMoneyAudit_ErrorFirmCode, "入金审核:交易商入金审核错误，非法交易商代码");
		error.put(inMoneyAudit_PassDataBaseException, "入金审核:审核入金数据库异常，需要手工处理入金状态");
		error.put(inMoneyAudit_RefuseDataBaseException, "入金审核:拒绝入金数据库异常，需要手工处理入金状态");
		error.put(inMoneyAudit_ErrorActionID, "入金审核:没有发现入金业务流水号");

		error.put(client_timeout, "连接服务器超时");

		error.put(bankSer_None, "冲正：银行流水号不存在");
		error.put(marketSer_None, "冲正：交易所流水号不存在");
		error.put(marketBackMoney_False, "冲正：调用交易系统失败");
		error.put(bankSer_None, "冲正：银行流水号不存在");
		error.put(charge_bankNull, "冲正：时传入的参数为空");
		error.put(charge_noReasion, "冲正：查询条件不足");
		error.put(charge_noFirm, "冲正：查询交易商失败");
		error.put(charge_SQLException, "冲正：数据库异常");

		error.put(bankNull, "银行返回数据包为空");
		error.put(sendDataError, "发送数据IO异常");
		error.put(cardMoney_none, "银行卡余额不足");
		error.put(beyond_AgreementMoney, "银行提示：当日卡转出累计金额超出协议限额");
		error.put(beyond_AgreementMaxPerMoney, "银行提示：超出银行最大转账金额");
		error.put(synFirm_DBException, "同步交易商：数据库异常，请检查！");
		error.put(Bank_OtherError, "银行返回其他错误");
		error.put(delAccount_FrozenFuns, "解约交易商，银行接口冻结资金不为0");
		error.put(Audit_Arraidy, "本笔交易已经受理");
		error.put(Not_TradeDate, "当前不是交易日期");
		error.put(Be_TradeTime, "未到交易时间");
		error.put(Af_TradeTime, "交易时间已过");
		error.put(Co_Trade, "系统出现异常情况，交易被禁止");
		error.put(delAccount_NotZero, "解约时交易商可用余额不为0");
		error.put(delAccount_Transfer, "交易商当天有转账不能解约");
		error.put(ActionID_Error, "交易所流水号冲突");
		error.put(ActionInAudit, "流水已经有人处理");
		error.put(inMoneyB_passwordLock, "银行账号被锁定");
		error.put(balance_FalsePwd, "密码错误");
		error.put(GetAdapter_Error, "处理器获取适配器失败");
		error.put(OutMoney_MaketBalanceError, "出金失败，等待交易所处理，请联系交易所客服");
		
// 交行错误信息开始
		error.put(BCM_2_SP0001, "系统错误：冲正记录无对应转账流水");
		error.put(BCM_2_SP0002, "无市场编号{0}资金账号{1}的签约记录");
		error.put(BCM_2_SP0003, "市场流水号{0}无对应的转账记录");
		error.put(BCM_2_SP0004, "市场流水号{0}对应的转账已冲正");
		error.put(BCM_2_SP0005, "无效密钥");
		error.put(BCM_2_SP0006, "无效算法{0}");
		error.put(BCM_2_SP0007, "MAC校验失败");
		error.put(BCM_2_SP0008, "未查找当前日期是否为工作日");
		error.put(BCM_2_SP0009, "当日为休息日");
		error.put(BCM_2_SP0099, "市场营运时段为工作日8:30~16:10");
// 交行错误信息结束
		//新加错误码2011-11-30 张锦锦
		error.put(Bank_SysClose , "银行转账时间已过");
		error.put(Bank_Unrecognised , "银行端未执行激活");
		error.put(RgsAccount_fail , "市场签约未成功");
		error.put(Error_MSG, "信息不完整");
		
		
		//民生银行错误信息---开始
		error.put(CMBC_1_Exce0101, "签约失败");
		error.put(CMBC_1_Exce0102, "解约失败");
		error.put(CMBC_1_Exce0103, "入金失败");
		error.put(CMBC_1_Exce0104, "出金失败");
		error.put(CMBC_1_Exce0105, "冲入金失败");
		error.put(CMBC_1_Exce0106, "冲出金失败");
		error.put(CMBC_1_Exce0107, "账户变更失败");
		error.put(CMBC_1_Exce0108, "查询余额失败");
		error.put(CMBC_1_Exce8888, "失败");
		error.put(CMBC_1_PwdExce, "密码校验失败");
		error.put(CMBC_1_NoPwd, "账户没有设置密码");
		error.put(CMBC_1_PwdErr, "密码验证错误");
		error.put(CMBC_1_AccErrDifferent, "资金账号错误（银行账号与资金账号不一致)");
		error.put(CMBC_1_CardErr, "证件号码不正确");
		error.put(CMBC_1_HavSigning, "该账号已签约，不可重复签约");
		error.put(CMBC_1_AccErr, "资金账号错误或交易商下不存在该银行账户");
		error.put(CMBC_1_Status, "资金账户状态不正常");
		error.put(CMBC_1_Cancellation_HavaMoney, "账户有余额，不能解约");
		error.put(CMBC_1_Cancellation_HaveTransac, "当日有交易，不能解约");
		error.put(CMBC_1_NoFoundAcc, "该账户不存在");
		error.put(CMBC_1_NoSigning, "该账户未签约");
		error.put(CMBC_1_MACVerificaFail, "MAC校验不通过");
		error.put(CMBC_1_NoFoundFirm, "不存在该交易商或者交易商已销户");
		error.put(CMBC_1_AccExce, "账户校验失败");
		error.put(CMBC_1_NoMuchMoney, "资金账户可用余额不足");
		error.put(CMBC_1_Change_HaveTransac, "当日有交易，不能变更账户");
		error.put(CMBC_1_HadReverse, "流水已冲正，不可重复冲正");	
		error.put(CMBC_1_Yu_HavSigning, "该账号已签约，不可进行预签约");
		error.put(CMBC_1_Y_Tosign, "该商品交易所已签退，请在交易时间内办理业务");
		error.put(CMBC_1_Y_AccountsStatusNotNormal, "银行帐户状态不正常");
		error.put(CMBC_2_Y_ReverseMoneyInconsistent, "对公活期帐户余额不足，无法冲正");
		error.put(CMBC_2_Y_In_NoOut, "此帐户状态为只进不出");
		error.put(BOC_1_MACError, "MAC校验错");
		error.put(CMBC_2_Y_BankHostError, "银行主机系统错误");
		
		//民生银行错误信息---结束
		
		//----------------- 中行错误信息----------begin
		error.put(BOC_1_Exce0101, "签约失败");
		error.put(BOC_1_Exce0102, "解约失败");
		error.put(BOC_1_Exce0103, "入金失败");
		error.put(BOC_1_Exce0104, "出金失败");
		error.put(BOC_1_Exce0105, "冲入金失败");
		error.put(BOC_1_Exce0106, "冲出金失败");
		error.put(BOC_1_Exce0107, "账户变更失败");
		error.put(BOC_1_Exce0108, "查询余额失败");
		error.put(BOC_1_Exce8888, "失败");
		error.put(BOC_1_PwdExce, "密码校验失败");
		error.put(BOC_1_NoPwd, "账户没有设置密码");
		error.put(BOC_1_PwdErr, "密码验证错误");
		error.put(BOC_1_AccErrDifferent, "资金账号错误（银行账号与资金账号不一致)");
		error.put(BOC_1_CardErr, "证件号码不正确");
		error.put(BOC_1_HavSigning, "该账号已签约，不可重复签约");
		error.put(BOC_1_AccErr, "资金账号错误或交易商下不存在该银行账户");
		error.put(BOC_1_Status, "账号不可用");
		error.put(BOC_1_Cancellation_HavaMoney, "账户有余额，不能解约");
		error.put(BOC_1_Cancellation_HaveTransac, "当日有交易，不能解约");
		error.put(BOC_1_NoFoundAcc, "该账户不存在");
		error.put(BOC_1_NoSigning, "该账户未签约");
		error.put(BOC_1_MACVerificaFail, "MAC校验不通过");
		error.put(BOC_1_NoFoundFirm, "不存在该交易商或者交易商已销户");
		error.put(BOC_1_AccExce, "账户校验失败");
		error.put(BOC_1_NoMuchMoney, "资金账户可用余额不足");
		error.put(BOC_1_Change_HaveTransac, "当日有交易，不能变更账户");
		error.put(BOC_1_HadReverse, "流水已冲正，不可重复冲证");
		error.put(BOC_1_Yu_HavSigning, "该账号已签约，不可进行预签约");
		
		error.put(BOC_1_Y_Tosign, "该商品交易所已签退，请在交易时间内办理业务");
		error.put(BOC_1_Y_AccountsStatusNotNormal, "银行帐户状态不正常");
		error.put(BOC_2_Y_ReverseMoneyInconsistent, "对公活期帐户余额不足，无法冲正");
		error.put(BOC_2_Y_In_NoOut, "此帐户状态为只进不出");
		error.put(BOC_2_Y_BankHostError, "银行主机系统错误");

//----------------- 中行错误信息----------end
		error.put(Firm_UnLogon, "交易所未签到");
		
		
		//-----------------------补充 start-------------------------
		error.put(Fee_UnlessThanTransferMoney, "手续费大于本金");
		error.put(dbError, "数据库异常");
		//-----------------------补充 end-------------------------
		
	}
	public static void main(String args[]){
		
		System.out.println(ErrorCode.error.get(client_timeout));
		ErrorCode ec = new ErrorCode();
		ec.load();
		System.out.println(ErrorCode.error.get(client_timeout));
	}
}

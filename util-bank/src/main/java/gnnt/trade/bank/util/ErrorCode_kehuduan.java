package gnnt.trade.bank.util;

import java.util.Hashtable;

/**
 * 错误码 以及错误码对应的信息
 */
public class ErrorCode_kehuduan
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
	
	/**************************************市场入金****************************************************/
	/**
	 * 市场发起入金:非法银行帐号
	 */
	public static final long inMoneyM_ErrorBankAcount=-10011;
	/**
	 * 市场发起入金:非法交易商代码
	 */
	public static final long inMoneyM_ErrorFirmCode=-10012;
	/**
	 * 市场发起入金:交易商代码和帐号对应关系错误
	 */
	public static final long inMoneyM_ErrorCorrespond=-10013;
	/**
	 * 市场发起入金:数据库异常
	 */
	public static final long inMoneyM_DataBaseException=-10014;
	/**
	 * 市场发起入金:系统异常
	 */
	public static final long inMoneyM_SysException=-10015;
	/**
	 * 市场发起入金:参数错误
	 */
	public static final long inMoneyM_ErrorParameter=-10016;
	/**
	 * 市场发起入金:适配器提交失败
	 */
	public static final long inMoneyM_ErrorApdater=-10018;
	
	/**
	 * 市场发起入金:交易商未签约
	 */
	public static final long inMoneyM_FirmNO=-10019;
	/**
	 * 市场发起入金:交易商被禁用
	 */
	public static final long inMoneyM_FirmNOUse = -10020;
	/**
	 * 市场发起入金:交易系统结算中
	 */
	public static final long inMoneyM_SystemClose = -10021;
	/**
	 * 市场发起入金:银行返回,卡密码错误
	 */
	public static final long inMoneyM_Pwd = -10022;
	
	/**
	 * 市场发起入金:银行端处理失败
	 */
	public static final long inMoneyM_Bank = -10023;
	/**
	 * 市场发起入金:计算手续费错误
	 */
	public static final long inMoneyM_ErrorGetRate=-10024;
	/**
	 * 入金回调，记录资金流水错误
	 */
	public static final long inMoney_ErrorAddCapital=-10025;
	/**
	 * 市场发起入金:写入资金流水失败
	 */
	public static final long inMoneyM_ErrorAddCapital=-10026;
	
	/*************************************银行出金***********************************************/
	
	/**
	 * 市场发起出金:非法银行帐号
	 */
	public static final long outMoney_ErrorBankAcount=-20001;
	/**
	 * 市场发起出金:非法交易商代码
	 */
	public static final long outMoney_ErrorFirmCode=-20002;
	/**
	 * 市场发起出金:交易商代码和帐号对应关系错误
	 */
	public static final long outMoney_ErrorCorrespond=-20003;
	/**
	 * 市场发起出金:数据库异常
	 */
	public static final long outMoney_DataBaseException=-20004;
	/**
	 * 市场发起出金:系统异常
	 */
	public static final long outMoney_SysException=-20005;
	/**
	 * 市场发起出金:银行端处理失败
	 */
	public static final long outMoney_BankProError=-20006;
	/**
	 * 市场发起出金:参数错误
	 */
	public static final long outMoney_ErrorParameter=-20007;
	/**
	 * 市场发起出金:交易商未签约
	 */
	public static final long outMoney_FirmNO=-20008;
	/**
	 * 市场发起出金:交易商被禁用
	 */
	public static final long outMoney_FirmNOUse = -20009;
	/**
	 * 市场发起出金:交易系统结算中
	 */
	public static final long outMoney_SystemClose = -20010;
	/**
	 * 市场发起出金:银行返回，客户证件与开户时不符
	 */
	public static final long outMoney_CardNo = -20020;
	/**
	 * 市场发起出金:记录资金流水错误
	 */
	public static final long outMoney_ErrorAddCapital=-20038;
	/**
	 * 市场发起出金:资金余额不足
	 */
	public static final long outMoney_ErrorLackFunds=-20039;
	/**
	 * 市场发起出金:计算出金手续费错误
	 */
	public static final long outMoney_ErrorGetRate=-20040;
	/**
	 * 市场发起出金:交易商未签约
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
	 * 市场出金审核错误，非法交易商代码
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
	 * 入金审核：市场入金审核错误，非法交易商代码
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
	 * 市场入金，银行账户资金不足
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
	 * 不符合出入金条件：超出当日单笔转账金额等待市场审核
	 */
	public static final long checkTrans_ErrorSingleMaxMoney = -30006;
	
	
	
	
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
	 * 查询市场资金余额:银行接口交易商不存在
	 */
	public static final long queryMoney_NoneFirm = -50001;
	
	/**
	 * 查询市场资金余额:交易系统交易商不存在
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
	 * 冲正：市场流水号不存在
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
		
		error.put(inMoneyAudit_BankMoneyNotEnough, "银行账户资金不足");
		
		error.put(inMoneyM_ErrorBankAcount, "非法银行帐号");
		error.put(inMoneyM_ErrorFirmCode, "非法交易商代码");
		error.put(inMoneyM_ErrorCorrespond, "交易商代码和帐号对应关系错误");
		error.put(inMoneyM_DataBaseException, "数据库异常");
		error.put(inMoneyM_SysException, "系统异常");
		error.put(inMoneyM_ErrorParameter, "参数错误");
		error.put(inMoneyM_ErrorApdater, "适配器提交失败");
		error.put(inMoneyM_FirmNO, "交易商未签约");
		error.put(inMoneyM_FirmNOUse, "账户被禁用");
		error.put(inMoneyM_SystemClose, "对不起，现在是闭市时间，不允许入金");
		error.put(inMoneyM_Pwd, "银行返回,卡密码错误");
		error.put(inMoneyM_Bank, "银行端处理失败");
		error.put(inMoneyM_ErrorGetRate, "计算手续费错误");
		error.put(inMoney_ErrorAddCapital, "入金回调，记录资金流水错误");
		error.put(inMoneyM_ErrorAddCapital, "写入资金流水失败");
		
		
		error.put(outMoney_ErrorBankAcount, "非法银行帐号");
		error.put(outMoney_ErrorFirmCode, "非法交易商代码");
		error.put(outMoney_ErrorCorrespond, "交易商代码和帐号对应关系错误");
		error.put(outMoney_DataBaseException, "对不起，您当前的余额不足");
		error.put(outMoney_SysException, "系统异常");
		error.put(outMoney_BankProError, "银行端处理失败");
		error.put(outMoney_ErrorParameter, "参数错误");
		error.put(outMoney_FirmNO, "交易商未签约");
		error.put(outMoney_FirmNOUse, "交易商被禁用");
		error.put(outMoney_SystemClose, "对不起，现在是闭市时间，不允许出金");
		error.put(outMoney_CardNo, "银行返回，客户证件与开户时不符");
		error.put(outMoney_ErrorAddCapital, "记录资金流水错误");
		error.put(outMoney_ErrorLackFunds, "资金余额不足");
		error.put(outMoney_ErrorGetRate, "计算出金手续费错误");
		error.put(outMoneyM_FirmNO, "交易商未签约");
		
		error.put(outMoneyAudit_ErrorActionID, "没有发现业务流水号");
		error.put(outMoneyAudit_ErrorFirmCode, "非法交易商代码");
		error.put(outMoneyAudit_PassDataBaseException, "同意出金SQLException，数据回滚，需要手工处理出金和手续费");
		error.put(outMoneyAudit_RefuseDataBaseException, "拒绝出金SQLException，数据回滚，需要手工处理出金和手续费");
		error.put(outMoneyAudit_DataBaseException, "SQLException");
		error.put(outMoneyAudit_SysException, "Exception");
		error.put(outMoneyAudit_ErrorTimeOut, "审核时间已过");
		error.put(outMoneyAudit_ErrorTimeUn, "审核时间未到");
		error.put(outMoneyAudit_ErrorBank, "发送给银行 银行处理失败");
		error.put(outMoneyB_successRefused, "超出审核额度，出金拒绝成功");

		error.put(checkTrans_ErrorExceedDayMaxPerMoney, "不符合出入金条件：超出每日单笔最大转账金额");
		error.put(checkTrans_ErrorExceedMaxMoney, "不符合出入金条件：超出最大转账金额");
		error.put(checkTrans_ErrorExceedDayMaxMoney, "不符合出入金条件：超出当日最大转账金额");
		error.put(checkTrans_ErrorExceedDayMaxNum, "不符合出入金条件：超出当日最大转账次数");
		error.put(checkTrans_DataBaseException, "不符合出入金条件：数据库异常");
		error.put(checkTrans_SysException, "不符合出入金条件：系统异常");
		error.put(checkTrans_ErrorSingleMaxMoney, "提交成功，等待市场审核");
		
		error.put(rgstAccount_InfoHalfbaked, "银行帐号注册:信息不完整");
		error.put(rgstAccount_NOFirm, "银行帐号注册:交易商不存在");
		error.put(rgstAccount_NOBank, "银行帐号注册:银行不存在");
		error.put(rgstAccount_GRSAcount, "银行帐号注册:帐号已注册");
		error.put(rgstAccount_BankRgsFail, "银行帐号注册:银行签约失败");
		error.put(rgstAccount_DatabaseException, "银行帐号注册:数据库操作失败");
		error.put(rgstAccount_SysException, "银行帐号注册:系统异常");
		error.put(rgstAccount_wrongPwd, "交易商密码错误");
		
		error.put(delAccount_InfoHalfbaked, "银行帐号注销:信息不完整");
		error.put(delAccount_NORgsAcount, "银行帐号注销:帐号未注册");
		error.put(delAccount_BankDelFail, "银行帐号注销:银行解约失败");
		error.put(delAccount_DatabaseException, "银行帐号注销:数据库操作失败");
		error.put(delAccount_SysException, "银行帐号注销:系统异常");

		
		error.put(queryMoney_NoneFirm, "查询市场资金余额:银行接口交易商不存在");
		error.put(queryMoney_TENoneFirm, "查询市场资金余额:交易系统交易商不存在");
		error.put(queryBankMoney_NoneFirm, "查询银行资金余额:银行接口交易商不存在");
		
		error.put(loginBank_None, "签到：银行返回，企业本日未签到");
		
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
		error.put(inMoneyAudit_ErrorFirmCode, "入金审核:市场入金审核错误，非法交易商代码");
		error.put(inMoneyAudit_PassDataBaseException, "入金审核:审核入金数据库异常，需要手工处理入金状态");
		error.put(inMoneyAudit_RefuseDataBaseException, "入金审核:拒绝入金数据库异常，需要手工处理入金状态");
		error.put(inMoneyAudit_ErrorActionID, "入金审核:没有发现入金业务流水号");
		
		
		error.put(client_timeout, "连接服务器超时");
		
		error.put(bankSer_None, "冲正：银行流水号不存在");
		error.put(marketSer_None, "冲正：市场流水号不存在");
		error.put(marketBackMoney_False, "冲正：调用交易系统失败");
		
		
		error.put(bankNull, "银行返回数据包为空");
		error.put(sendDataError, "发送数据IO异常");
		error.put(cardMoney_none, "银行卡余额不足");
		error.put(beyond_AgreementMoney, "银行提示：当日卡转出累计金额超出协议限额");
		
		error.put(synFirm_DBException, "同步交易商：数据库异常，请检查！");
	}
}

package gnnt.trade.bank.util;
/**
 * 常量类
 * @author liuzx
 */
public class ProcConstants {
	//------------------xml 配置文件常量信息
	/**配置文件名字*/
	public static final String xmlName = "BANK.Processor";
	/**处理器IP地址*/
	public static final String procAdress = "RmiIpAddress";
	/**处理器端口*/
	public static final String procPort = "RmiPortNumber";
	/**处理器服务名*/
	public static final String procServiceName = "RmiServiceName";
	/**数据库 Url*/
	public static final String dbUrl = "DBUrl";
	/**数据库用户名*/
	public static final String dbUser = "DBUser";
	/**数据库密码*/
	public static final String dbPwd = "DBPassword";
	/**数据库 JNDIName*/
	public static final String jndiName = "JNDIName";
	/**数据库 JNDIName*/
	public static final String QueryJNDIName = "QueryJNDIName";
	/**数据库连接类型*/
	public static final String dbConnType = "DBConnType";
//	/** 判断是否出金时去掉浮盈 true 去掉 else 不去 */
//	public static final String fuYing = "fuYing";
	/** 判断对账页面的按钮什么时候可以点击 */
	public static final String CompareTime = "CompareTime";
	/**默认银行账号*/
	public static final String defaultAccount = "DefaultAccount";
	/**交易商默认资金密码*/
	public static final String password = "password";
	/**光大银行发起清算线程执行睡眠时间*/
	public static final String qssleeptime = "qssleeptime";
	/**光大银行生成的清算文件路径*/
	public static final String cebqsdir = "cebqsdir";
	//------------------log4j 配置名称
	/**主实现类 CapitalProcessor log4j 名称*/
	public static final String cpLog = "Processorlog";
	/**交易系统联系类 DataProcess log4j 名称*/
	public static final String dpLog = "DataProcesslog";
	/**数据库实现类 BankDAOImpl log4j 名称*/
	public static final String daoLog = "BankDAOlog";
	//------------------交易账号状态
	/**交易账号未签约状态*/
	public static final int firmTypeUnOpen = 0;
	/**交易账号签约状态*/
	public static final int firmTypeOpen = 1;
	/**交易账号解约状态*/
	public static final int firmTypeDel = 2;
	/**交易账号可用状态*/
	public static final int firmStatusTrue = 0;
	/**交易账号不可用状态*/
	public static final int firmStatusFalse = 1;
	//------------------银行接口流水类型
	/**入金类型*/
	public static final int inMoneyType = 0;
	/**出金类型*/
	public static final int outMoneyType = 1;
	/**入金冲正类型*/
	public static final int inMoneyBlunt = 4;
	/**出金冲正类型*/
	public static final int outMoneyBlunt = 5;
	/** 市场方发起类型 */
	public static final int marketLaunch = 0;
	/** 银行方发起类型 */
	public static final int bankLaunch = 1;
	//------------------银行接口流水状态
	/**成功*/
	public static final int statusSuccess = 0;
	/**失败*/
	public static final int statusFailure = 1;
	/**银行无返回*/
	public static final int statusBankNull = 5;
	/**银行返回信息异常*/
	public static final int statusBankError = 6;
	/**银行处理中*/
	public static final int statusBankProcessing = 2;
	/**市场信息处理中*/
	public static final int statusMarketProcessing = 7;
	/** 市场处理异常 */
	public static final int statusMarketError = 8;
	/**流水被冲正*/
	public static final int statusBlunt = 9;
}

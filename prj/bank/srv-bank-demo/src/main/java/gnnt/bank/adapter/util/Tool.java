package gnnt.bank.adapter.util;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * 公用类
 */
public class Tool {
	/**xml 根节点名称*/
	private static final String xmlName = "BANK.Adapter";
	/**适配器机器IP地址*/
	public static final String AdapterIP = "AdapterIP";
	/**适配器服务端口*/
	public static final String AdapterPort = "AdapterPort";
	/**适配器服务名*/
	public static final String AdapterName = "AdapterName";
	/**处理器IP地址*/
	public static final String ProcessorIP = "ProcessorIP";
	/**处理器 RMI 端口*/
	public static final String ProcessorPort = "ProcessorPort";
	/**处理器 RMI 服务名*/
	public static final String ServiceName = "ServiceName";
	/**数据库连接名称*/
	public static final String oracle = "oracle.jdbc.driver.OracleDriver";
	/**数据库地址*/
	public static final String DBUrl = "DBUrl";
	/**数据库用户名*/
	public static final String DBUser = "DBUser";
	/**数据库密码*/
	public static final String DBPwd = "DBPwd";
	/**判断是否连接数据库*/
	public static final String connectionDB = "connectionDB";
	
	/**判断是否成功*/
	public static final String ifsuccessful = "ifsuccessful";
	/**睡眠时间*/
	public static final String sleeptime = "sleeptime";
	/**会员自动入金后银行剩余权益*/
	public static final String memberright = "memberright";
	/**客户自动入金后银行剩余权益*/
	public static final String customerright = "customerright";
	/**会员入金金额*/
	public static final String memberinmoney = "memberinmoney";
	/**客户入金金额*/
	public static final String customerinmoney = "customerinmoney";
	/**
	 * 获取 xml 配置信息
	 */
	public static String getConfig(String key){
		Properties props = new Configuration().getSection(xmlName);
		return props.getProperty(key);
	}
	/**
	 * 将异常信息导入字符串
	 * @param e 异常信息
	 * @return String
	 */
	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
       return "No Exception";
    }
	/**
	 * 写日志
	 */
	public static void log(String content){
		Logger alog = Logger.getLogger("Adapterlog");
		alog.debug(content);
	}
}

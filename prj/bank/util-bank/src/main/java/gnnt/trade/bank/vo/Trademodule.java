
package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * <P>类说明：系统模块表
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-5-17上午11:14:15|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class Trademodule implements Serializable {
	private static final long serialVersionUID = -8049427998427242593L;

	/** 模块编号 */
	public int moduleID;

	/** 中文名称 */
	public String cnName;

	/** 英文名称 */
	public String enName;

	/** 中文简称 */
	public String shortname;

	/** 添加交易商存储 */
	public String addFirmFN;

	/** 修改交易商状态存储 */
	public String updateFirmStatusFN;

	/** 删除交易商存储 */
	public String delFirmFN;

	/**
	 * 是否用于交易商设置<br/>
	 * Y 是 N 否
	 */
	public String isFirmset;

	/** 交易核心 IP 地址 */
	public String hostip;

	/** 交易核心 RMI 监听端口 */
	public int port;

	/** 交易核心数据传输端口 */
	public int rmiDataport;

	/**
	 * 是否用于结算验证<br/>
	 * Y 用于验证 N 不用于验证
	 */
	public String isBalanceCheck;
}


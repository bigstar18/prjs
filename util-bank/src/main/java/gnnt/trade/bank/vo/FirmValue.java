package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * Title: 交易商对象类
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
public class FirmValue implements Serializable {
	// /**
	// *
	// */
	// private static final long serialVersionUID = 1L;
	// /**
	// * 交易商代码
	// */
	// public String firmID;
	// /**
	// * 交易商名称
	// */
	// public String name;
	// /**
	// * 默认出金审核金额
	// */
	// public double maxAuditMoney;
	// /**
	// * 每日最大转账金额
	// */
	// public double maxPerTransMoney;
	// /**
	// * 默认每日单笔最大转账金额
	// */
	// public double maxPerSglTransMoney;
	// /**
	// * 每日最大转账次数
	// */
	// public int maxPerTransCount;
	//
	// /**
	// * 状态：0：已注册1：禁用2：已注销
	// */
	// public int status;
	// /**
	// * 注册日期
	// */
	// public Timestamp registerDate;
	// /**
	// * 注销日期
	// */
	// public Timestamp logoutDate;
	//
	// /**
	// * 市场交易密码
	// */
	// public String password;
	//
	//
	// /**
	// * 返回对象属性值
	// */
	// public String toString()
	// {
	// String sep = "\n";
	// StringBuffer sb = new StringBuffer();
	// sb.append("**"+this.getClass().getName()+"**"+sep);
	// sb.append("firmID:" + this.firmID+sep);
	// sb.append("Name:" + this.name+sep);
	// sb.append("maxAuditMoney:" + this.maxAuditMoney+sep);
	// sb.append("maxPerTransMoney:" + this.maxPerTransMoney+sep);
	// sb.append("maxPerTransCount:" + this.maxPerTransCount+sep);
	// sb.append("Status:" + this.status+sep);
	// sb.append("registerDate:" + this.registerDate+sep);
	// sb.append("logoutDate:" + this.logoutDate+sep);
	// sb.append(sep);
	// return sb.toString();
	// }
	private static final long serialVersionUID = 1L;
	public String firmID;
	public String name;
	public double maxAuditMoney;
	public double maxPerTransMoney;
	public double maxPerSglTransMoney;
	public int maxPerTransCount;
	public int status;
	public Timestamp registerDate;
	public Timestamp logoutDate;
	public String password;
	public double sysToSysBalnce;

	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**" + getClass().getName() + "**" + sep);
		sb.append("firmID:" + this.firmID + sep);
		sb.append("Name:" + this.name + sep);
		sb.append("maxAuditMoney:" + this.maxAuditMoney + sep);
		sb.append("maxPerTransMoney:" + this.maxPerTransMoney + sep);
		sb.append("maxPerTransCount:" + this.maxPerTransCount + sep);
		sb.append("Status:" + this.status + sep);
		sb.append("registerDate:" + this.registerDate + sep);
		sb.append("logoutDate:" + this.logoutDate + sep);
		sb.append(sep);
		return sb.toString();
	}

}

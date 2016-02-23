package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * <p>Title: 银行对象类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 * @author zhangzhongli
 * @version 1.0
 */
public class BankValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 银行代码 */
	public String bankID;
	/** 银行名称 */
	public String bankName;
	/** 适配器实现类名称 */
	public String adapterClassname;
	/** 是否可用状态：0：可用1：不可用 */
	public int validFlag;
	/** 入金状态：0：可用 1：不可用 */
	public int inMoneyFlag;
	/** 出金状态：0：可用 1：不可用 */
	public int outMoneyFlag;
	/** 银行转账开始时间 */
	public String beginTime;
	/** 银行转账结束时间 */
	public String endTime;
	/** 转账交易是否收到约束 0 受到双重约束，1不受约束，2受交易日约束，3受交易时间约束 */
	public int control;
	/** 银行当前状态(1 签到状态,2 签退状态) */
	public int bankType;
	/** 划分处理器RMI访问方式 */
	public String beleiveProcessor;
	/**
	 * 返回对象属性值
	 */
	public String toString() {
		String sep = "\n";
		StringBuilder sb = new StringBuilder();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("bankID:" + bankID+sep);
		sb.append("bankName:" + bankName+sep);
		sb.append("adapterClassname:" + adapterClassname+sep);
		sb.append("validFlag:" + validFlag+sep);
		sb.append("inMoneyFlag:" + inMoneyFlag +sep);
		sb.append("outMoneyFlag:" + outMoneyFlag + sep);
		sb.append("beginTime:" + beginTime+sep);
		sb.append("endTime:" + endTime+sep);
		sb.append("control:" + control+sep);
		sb.append("bankType:" + bankType+sep);
		sb.append("beleiveProcessor:" + beleiveProcessor +sep);
		sb.append(sep);
		return sb.toString();
	}
}

package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 收费标准对象
 * @author 薛计涛
 *
 */
public class FeeInfoVO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 记录流水号
	 */
	public long id;
	/**
	 * 上限
	 */
	public int upLimit;
	/**
	 * 下限
	 */
	public int downLimit;
	/**
	 * 类型 0：百分比1：绝对值
	 */
	public int tMode;
	/**
	 * 手续费
	 */
	public double rate;
	/**
	 * 最高手续费限额，<=0则无限制
	 */
	public double maxRateValue;
	/**
	 * 最低手续费限额，<=0为空则无限制
	 */
	public double minRateValue;
	
	
	/**
	 * 收费类型 
	 */
	public String type;
	/**
	 * 记录创建时间
	 */
	public Timestamp createTime;
	/**
	 * 记录修改时间
	 */
	public Timestamp updateTime;
	/**
	 * 用户ID （记录拥有者 银行或者交易商）
	 */
	public String userID;
	
	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("id:" + this.id+sep);
		sb.append("upLimit:" + this.upLimit+sep);
		sb.append("downLimit:" + this.downLimit+sep);
		sb.append("type:" + this.type+sep);
		sb.append("tMode:" + this.tMode+sep);
		sb.append("rate:" + this.rate+sep);
		sb.append("maxRateValue:" + this.maxRateValue+sep);
		sb.append("minRateValue:" + this.minRateValue+sep);
		sb.append("type:" + this.type+sep);
		sb.append("createTime:" + this.createTime+sep);
		sb.append("updateTime:" + this.updateTime+sep);
		sb.append("userID:" + this.userID+sep);
		sb.append(sep);
		return sb.toString();
	}
}

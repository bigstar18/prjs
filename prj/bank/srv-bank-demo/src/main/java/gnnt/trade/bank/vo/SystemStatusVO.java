package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.util.Date;
/**
 * 系统结算状态类
 */
public class SystemStatusVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 交易日期 */
	public Date tradeDate;
	/** 日终 */
	public Date endDate;
	/**
	 * 状态
	 * 0 初始化完成
	 * --1 闭市状态
	 * 2 结算中
	 * 3 结算完成（原财务结算完成）
	 * 4 暂停交易
	 * 5 交易中
	 * 6 节间休息
	 * 7 交易结束
	 */
	public int status;
	/** 所处交易节 */
	public int sectionID;
	/** 备注 */
	public String note;
	/** 暂停后自动回复时间 */
	public String recoverTime;
	/** 暂停类型 */
	public String pauseType;
	/** 上衣交易日 */
	public Date lastTradeDate;
	/** 下一交易日 */
	public Date nextTradeDate;
}

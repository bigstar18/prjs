package gnnt.trade.bank.vo;
import gnnt.trade.bank.util.Tool;

import java.io.Serializable;
import java.util.Date;
/**
 * 银行清算结果信息类
 */
public class ClearingStatusVO implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 信息编号 */
	public long ID;
	/** 银行编号 */
	public String bankID;
	/** 交易日期 */
	public Date tradeDate;
	/** 生成文件时间 */
	public Date generalTime;
	/** 发送数据时间 */
	public Date sendTime;
	/**
	 * 生成文件状态
	 * (1 未生成,2 已生成,3 生成失败) 
	 */
	public int generalStatus;
	/**
	 * 发送数据状态
	 * (1 未发送,2 已发送,3 发送失败)
	 */
	public int sendStatus;
	/** 记录创建时间 */
	public Date createTime;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("ID:"+ID+str);
		sb.append("bankID:"+bankID+str);
		sb.append("tradeDate:"+fmtDate(tradeDate)+str);
		sb.append("generalTime:"+fmtDate(generalTime)+str);
		sb.append("sendTime:"+fmtDate(sendTime)+str);
		sb.append("generalStatus:"+generalStatus+str);
		sb.append("sendStatus:"+sendStatus+str);
		sb.append("createTime:"+fmtDate(createTime)+str);
		return sb.toString();
	}
	private String fmtDate(Date date){
		if(date == null){
			return "";
		}
		return Tool.fmtTime(date);
	}
}

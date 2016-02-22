package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class LogValue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志编号
	 */
	public long logid;
	
	/***
	 * 操作员
	 */
	public String logopr;
	/**
	 * 操作内容
	 */
	public String logcontent;
	/**
	 * 操作时间
	 */
	public Date logdate;
	/**
	 * 主机IP
	 */
	public String ip;
	
	public LogValue(){}
			
	public LogValue(String logopr,String logcontent,Date date)
	{
		this.logopr = logopr;
		this.logcontent = logcontent;
		this.logdate = date;
	}
	//------------------------------------------------------
	
	
	public long getLogid() {
		return logid;
	}
	public void setLogid(long logid) {
		this.logid = logid;
	}
	public String getLogopr() {
		return logopr;
	}
	public void setLogopr(String logopr) {
		this.logopr = logopr;
	}
	public String getLogcontent() {
		return logcontent;
	}
	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}
	public Date getLogdate() {
		return logdate;
	}
	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}

	public void setIp(String ip){
		this.ip=ip;
	}
	public String getIp(){
		return this.ip;
	}
}

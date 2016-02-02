package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class LogValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 日志编号 */
	public long logid;
	/** 操作类型 */
	public String logtype;
	/** 操作员 */
	public String logopr;
	/** 操作端 */
	public String logoprtype;
	/** 操作内容 */
	public String logcontent;
	/** 操作结果(0 失败;1 成功) */
	public int result;
	/** 当前值 */
	public String contentvalue;
	/** 操作时间 */
	public Date logtime;
	/** 主机IP */
	public String ip;
	/** 会员编号 */
	public String mark;
	
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public LogValue(){}
			
	public LogValue(String logopr,String logcontent,Date date)
	{
		this.logopr = logopr;
		this.logcontent = logcontent;
		this.logtime = date;
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
	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getLogoprtype() {
		return logoprtype;
	}

	public void setLogoprtype(String logoprtype) {
		this.logoprtype = logoprtype;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getContentvalue() {
		return contentvalue;
	}

	public void setContentvalue(String contentvalue) {
		this.contentvalue = contentvalue;
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
	public Date getLogtime() {
		return logtime;
	}
	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	public void setIp(String ip){
		this.ip=ip;
	}
	public String getIp(){
		return this.ip;
	}
}

 package gnnt.MEBS.bank.front.model;


 import gnnt.MEBS.common.front.model.StandardModel;
 import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
 import gnnt.MEBS.common.front.model.translate.ClassDiscription;
 import java.util.Date;


 public class Log extends StandardModel
 {
	 private static final long serialVersionUID = 2872748809694940875L;
	
	 @ClassDiscription(name = "日志编号", description = "")
	 private Long logID;
	
	 @ClassDiscription(name = "操作员", description = "")
	 private String logopr;
	
	 @ClassDiscription(name = "操作内容 ", description = "")
	 private String logcontent;
	
	 @ClassDiscription(name = "日志记录日期", description = "")
	 private Date logDate;
	
	 @ClassDiscription(name = "日志记录登录机器", description = "")
	 private String logIP;

	
	 public Long getLogID()
	 {
		/* 54 */ return this.logID;
		 }

	
	 public void setLogID(Long logID)
	 {
		/* 64 */ this.logID = logID;
		 }

	
	 public String getLogopr()
	 {
		/* 74 */ return this.logopr;
		 }

	
	 public void setLogopr(String logopr)
	 {
		/* 84 */ this.logopr = logopr;
		 }

	
	 public String getLogcontent()
	 {
		/* 94 */ return this.logcontent;
		 }

	
	 public void setLogcontent(String logcontent)
	 {
		/* 104 */ this.logcontent = logcontent;
		 }

	
	 public Date getLogDate()
	 {
		/* 114 */ return this.logDate;
		 }

	
	 public void setLogDate(Date logDate)
	 {
		/* 124 */ this.logDate = logDate;
		 }

	
	 public String getLogIP()
	 {
		/* 134 */ return this.logIP;
		 }

	
	 public void setLogIP(String logIP)
	 {
		/* 144 */ this.logIP = logIP;
		 }

	
	 public StandardModel.PrimaryInfo fetchPKey()
	 {
		/* 149 */ return new StandardModel.PrimaryInfo("logID", this.logID);
		 }
	 }
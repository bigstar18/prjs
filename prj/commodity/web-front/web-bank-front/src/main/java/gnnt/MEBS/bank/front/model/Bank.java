 package gnnt.MEBS.bank.front.model;


 import gnnt.MEBS.common.front.model.StandardModel;
 import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
 import gnnt.MEBS.common.front.model.translate.ClassDiscription;
 import java.util.Set;


 public class Bank extends StandardModel
 {
	 private static final long serialVersionUID = -3602159712250065907L;
	
	 @ClassDiscription(name = "银行编号", description = "")
	 private String bankID;
	
	 @ClassDiscription(name = "银行名称", description = "")
	 private String bankName;
	
	 @ClassDiscription(name = "单笔最大转账金额", description = "")
	 private Double maxPersgltransMoney;
	
	 @ClassDiscription(name = "每日最大转账金额", description = "")
	 private Double maxPertransMoney;
	
	 @ClassDiscription(name = "每日最大转账次数", description = "")
	 private Integer maxPertransCount;
	
	 @ClassDiscription(name = "适配器实现类名称", description = "")
	 private String adapterclassName;
	
	 @ClassDiscription(name = "银行状态", description = "0 可用,1 不可用")
	 private Integer validflag;
	
	 @ClassDiscription(name = "当个银行出金审核额度", description = "")
	 private Double maxAuditMoney;
	
	 @ClassDiscription(name = "银行起始转账时间", description = "")
	 private String begintime;
	
	 @ClassDiscription(name = "银行结束转账时间", description = "")
	 private String endTime;
	
	 @ClassDiscription(name = "是否受到交易日和交易时间的约束", description = "0 受双重约束,1 不受约束,2 受交易日约束,3 受交易时间约束")
	 private Integer control;
	
	 @ClassDiscription(name = "交易商和银行绑定列表", description = "")
	 private Set<FirmIDAndAccount> firmIDAndAccountSet;

	
	 public String getBankID()
	 {
		/* 88 */ return this.bankID;
		 }

	
	 public void setBankID(String bankID)
	 {
		/* 98 */ this.bankID = bankID;
		 }

	
	 public String getBankName()
	 {
		/* 108 */ return this.bankName;
		 }

	
	 public void setBankName(String bankName)
	 {
		/* 118 */ this.bankName = bankName;
		 }

	
	 public Double getMaxPersgltransMoney()
	 {
		/* 128 */ return this.maxPersgltransMoney;
		 }

	
	 public void setMaxPersgltransMoney(Double maxPersgltransMoney)
	 {
		/* 138 */ this.maxPersgltransMoney = maxPersgltransMoney;
		 }

	
	 public Double getMaxPertransMoney()
	 {
		/* 148 */ return this.maxPertransMoney;
		 }

	
	 public void setMaxPertransMoney(Double maxPertransMoney)
	 {
		/* 158 */ this.maxPertransMoney = maxPertransMoney;
		 }

	
	 public Integer getMaxPertransCount()
	 {
		/* 168 */ return this.maxPertransCount;
		 }

	
	 public void setMaxPertransCount(Integer maxPertransCount)
	 {
		/* 178 */ this.maxPertransCount = maxPertransCount;
		 }

	
	 public String getAdapterclassName()
	 {
		/* 188 */ return this.adapterclassName;
		 }

	
	 public void setAdapterclassName(String adapterclassName)
	 {
		/* 198 */ this.adapterclassName = adapterclassName;
		 }

	
	 public Integer getValidflag()
	 {
		/* 209 */ return this.validflag;
		 }

	
	 public void setValidflag(Integer validflag)
	 {
		/* 220 */ this.validflag = validflag;
		 }

	
	 public Double getMaxAuditMoney()
	 {
		/* 230 */ return this.maxAuditMoney;
		 }

	
	 public void setMaxAuditMoney(Double maxAuditMoney)
	 {
		/* 240 */ this.maxAuditMoney = maxAuditMoney;
		 }

	
	 public String getBegintime()
	 {
		/* 250 */ return this.begintime;
		 }

	
	 public void setBegintime(String begintime)
	 {
		/* 260 */ this.begintime = begintime;
		 }

	
	 public String getEndTime()
	 {
		/* 270 */ return this.endTime;
		 }

	
	 public void setEndTime(String endTime)
	 {
		/* 280 */ this.endTime = endTime;
		 }

	
	 public Integer getControl()
	 {
		/* 291 */ return this.control;
		 }

	
	 public void setControl(Integer control)
	 {
		/* 302 */ this.control = control;
		 }

	
	 public Set<FirmIDAndAccount> getFirmIDAndAccountSet()
	 {
		/* 312 */ return this.firmIDAndAccountSet;
		 }

	
	 public void setFirmIDAndAccountSet(Set<FirmIDAndAccount> firmIDAndAccountSet)
	 {
		/* 322 */ this.firmIDAndAccountSet = firmIDAndAccountSet;
		 }

	
	 public StandardModel.PrimaryInfo fetchPKey()
	 {
		/* 327 */ return new StandardModel.PrimaryInfo("bankID", this.bankID);
		 }
	 }
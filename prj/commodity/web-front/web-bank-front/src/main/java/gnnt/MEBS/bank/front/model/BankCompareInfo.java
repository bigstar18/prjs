 package gnnt.MEBS.bank.front.model;


 import gnnt.MEBS.common.front.model.StandardModel;
 import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
 import gnnt.MEBS.common.front.model.integrated.MFirm;
 import gnnt.MEBS.common.front.model.translate.ClassDiscription;
 import java.util.Date;


 public class BankCompareInfo extends StandardModel
 {
	 private static final long serialVersionUID = -1150447736514962744L;
	
	 @ClassDiscription(name = "银行对账信息ID", description = "")
	 private Long id;
	
	 @ClassDiscription(name = " 银行流水号", description = "")
	 private String funID;
	
	 @ClassDiscription(name = "交易商", description = "")
	 private MFirm firm;
	
	 @ClassDiscription(name = "交易商账号", description = "")
	 private String account;
	
	 @ClassDiscription(name = "操作类型", description = "0 入金,1 出金")
	 private Integer type;
	
	 @ClassDiscription(name = "金额", description = "")
	 private Double money;
	
	 @ClassDiscription(name = "对账日期 ", description = "")
	 private Date compareDate;
	
	 @ClassDiscription(name = "备注", description = "")
	 private String note;
	
	 @ClassDiscription(name = "创建日期", description = "")
	 private Date createTime;
	
	 @ClassDiscription(name = "操作状态", description = "")
	 private Integer status;
	
	 @ClassDiscription(name = "银行", description = "")
	 private Bank bank;

	
	 public Long getId()
	 {
		/* 83 */ return this.id;
		 }

	
	 public void setId(Long id)
	 {
		/* 93 */ this.id = id;
		 }

	
	 public String getFunID()
	 {
		/* 103 */ return this.funID;
		 }

	
	 public void setFunID(String funID)
	 {
		/* 113 */ this.funID = funID;
		 }

	
	 public MFirm getFirm()
	 {
		/* 123 */ return this.firm;
		 }

	
	 public void setFirm(MFirm firm)
	 {
		/* 133 */ this.firm = firm;
		 }

	
	 public String getAccount()
	 {
		/* 143 */ return this.account;
		 }

	
	 public void setAccount(String account)
	 {
		/* 153 */ this.account = account;
		 }

	
	 public Integer getType()
	 {
		/* 164 */ return this.type;
		 }

	
	 public void setType(Integer type)
	 {
		/* 175 */ this.type = type;
		 }

	
	 public Double getMoney()
	 {
		/* 185 */ return this.money;
		 }

	
	 public void setMoney(Double money)
	 {
		/* 195 */ this.money = money;
		 }

	
	 public Date getCompareDate()
	 {
		/* 205 */ return this.compareDate;
		 }

	
	 public void setCompareDate(Date compareDate)
	 {
		/* 215 */ this.compareDate = compareDate;
		 }

	
	 public String getNote()
	 {
		/* 225 */ return this.note;
		 }

	
	 public void setNote(String note)
	 {
		/* 235 */ this.note = note;
		 }

	
	 public Date getCreateTime()
	 {
		/* 245 */ return this.createTime;
		 }

	
	 public void setCreateTime(Date createTime)
	 {
		/* 255 */ this.createTime = createTime;
		 }

	
	 public Integer getStatus()
	 {
		/* 265 */ return this.status;
		 }

	
	 public void setStatus(Integer status)
	 {
		/* 275 */ this.status = status;
		 }

	
	 public Bank getBank()
	 {
		/* 285 */ return this.bank;
		 }

	
	 public void setBank(Bank bank)
	 {
		/* 295 */ this.bank = bank;
		 }

	
	 public StandardModel.PrimaryInfo fetchPKey()
	 {
		/* 300 */ return new StandardModel.PrimaryInfo("id", this.id);
		 }
	 }
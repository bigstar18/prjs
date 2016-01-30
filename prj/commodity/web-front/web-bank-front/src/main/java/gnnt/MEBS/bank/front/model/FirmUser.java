/*     */ package gnnt.MEBS.bank.front.model;

/*     */
/*     */ import gnnt.MEBS.common.front.model.StandardModel;
/*     */ import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
/*     */ import gnnt.MEBS.common.front.model.integrated.MFirm;
/*     */ import gnnt.MEBS.common.front.model.translate.ClassDiscription;
/*     */ import java.util.Date;

/*     */
/*     */ public class FirmUser extends StandardModel
/*     */ {
	/*     */ private static final long serialVersionUID = -4967674470053824787L;
	/*     */
	/*     */ @ClassDiscription(name = "交易商代码", description = "")
	/*     */ private String firmID;
	/*     */
	/*     */ @ClassDiscription(name = "交易商", description = "")
	/*     */ private MFirm firm;
	/*     */
	/*     */ @ClassDiscription(name = "交易商名称", description = "")
	/*     */ private String name;
	/*     */
	/*     */ @ClassDiscription(name = "单笔最大转账金额", description = "")
	/*     */ private Double maxPersgltransMoney;
	/*     */
	/*     */ @ClassDiscription(name = "每天最大转账金额", description = "")
	/*     */ private Double maxPertransMoney;
	/*     */
	/*     */ @ClassDiscription(name = "每天最大转账次数 ", description = "")
	/*     */ private Long maxPertranscount;
	/*     */
	/*     */ @ClassDiscription(name = "交易商状态", description = "0 已注册,1 禁用或未注册,2注销")
	/*     */ private Integer status;
	/*     */
	/*     */ @ClassDiscription(name = "注册日期 ", description = "")
	/*     */ private Date registerDate;
	/*     */
	/*     */ @ClassDiscription(name = "注册日期 ", description = "")
	/*     */ private Date logoutDate;
	/*     */
	/*     */ @ClassDiscription(name = "审核额度", description = "")
	/*     */ private Double maxAuditMoney;
	/*     */
	/*     */ @ClassDiscription(name = "密码 ", description = "")
	/*     */ private String password;

	/*     */
	/*     */ public String getFirmID()
	/*     */ {
		/* 82 */ return this.firmID;
		/*     */ }

	/*     */
	/*     */ public void setFirmID(String firmID)
	/*     */ {
		/* 92 */ this.firmID = firmID;
		/*     */ }

	/*     */
	/*     */ public MFirm getFirm()
	/*     */ {
		/* 102 */ return this.firm;
		/*     */ }

	/*     */
	/*     */ public void setFirm(MFirm firm)
	/*     */ {
		/* 112 */ this.firm = firm;
		/*     */ }

	/*     */
	/*     */ public String getName()
	/*     */ {
		/* 122 */ return this.name;
		/*     */ }

	/*     */
	/*     */ public void setName(String name)
	/*     */ {
		/* 132 */ this.name = name;
		/*     */ }

	/*     */
	/*     */ public Double getMaxPersgltransMoney()
	/*     */ {
		/* 142 */ return this.maxPersgltransMoney;
		/*     */ }

	/*     */
	/*     */ public void setMaxPersgltransMoney(Double maxPersgltransMoney)
	/*     */ {
		/* 152 */ this.maxPersgltransMoney = maxPersgltransMoney;
		/*     */ }

	/*     */
	/*     */ public Double getMaxPertransMoney()
	/*     */ {
		/* 162 */ return this.maxPertransMoney;
		/*     */ }

	/*     */
	/*     */ public void setMaxPertransMoney(Double maxPertransMoney)
	/*     */ {
		/* 172 */ this.maxPertransMoney = maxPertransMoney;
		/*     */ }

	/*     */
	/*     */ public Long getMaxPertranscount()
	/*     */ {
		/* 182 */ return this.maxPertranscount;
		/*     */ }

	/*     */
	/*     */ public void setMaxPertranscount(Long maxPertranscount)
	/*     */ {
		/* 192 */ this.maxPertranscount = maxPertranscount;
		/*     */ }

	/*     */
	/*     */ public Integer getStatus()
	/*     */ {
		/* 203 */ return this.status;
		/*     */ }

	/*     */
	/*     */ public void setStatus(Integer status)
	/*     */ {
		/* 214 */ this.status = status;
		/*     */ }

	/*     */
	/*     */ public Date getRegisterDate()
	/*     */ {
		/* 224 */ return this.registerDate;
		/*     */ }

	/*     */
	/*     */ public void setRegisterDate(Date registerDate)
	/*     */ {
		/* 234 */ this.registerDate = registerDate;
		/*     */ }

	/*     */
	/*     */ public Date getLogoutDate()
	/*     */ {
		/* 244 */ return this.logoutDate;
		/*     */ }

	/*     */
	/*     */ public void setLogoutDate(Date logoutDate)
	/*     */ {
		/* 254 */ this.logoutDate = logoutDate;
		/*     */ }

	/*     */
	/*     */ public Double getMaxAuditMoney()
	/*     */ {
		/* 264 */ return this.maxAuditMoney;
		/*     */ }

	/*     */
	/*     */ public void setMaxAuditMoney(Double maxAuditMoney)
	/*     */ {
		/* 274 */ this.maxAuditMoney = maxAuditMoney;
		/*     */ }

	/*     */
	/*     */ public String getPassword()
	/*     */ {
		/* 284 */ return this.password;
		/*     */ }

	/*     */
	/*     */ public void setPassword(String password)
	/*     */ {
		/* 294 */ this.password = password;
		/*     */ }

	/*     */
	/*     */ public StandardModel.PrimaryInfo fetchPKey()
	/*     */ {
		/* 299 */ return new StandardModel.PrimaryInfo("firmID", this.firmID);
		/*     */ }
	/*     */ }
/*     */ package gnnt.MEBS.bank.front.model;

/*     */
/*     */ import gnnt.MEBS.common.front.model.StandardModel;
/*     */ import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
/*     */ import gnnt.MEBS.common.front.model.integrated.MFirm;
/*     */ import gnnt.MEBS.common.front.model.translate.ClassDiscription;
/*     */ import gnnt.trade.bank.vo.CorrespondValue;
/*     */ import java.util.Date;

/*     */
/*     */ public class FirmIDAndAccount extends StandardModel
/*     */ {
	/*     */ private static final long serialVersionUID = 6949112070939632618L;
	/*     */
	/*     */ @ClassDiscription(name = "银行", description = "")
	/*     */ private Bank bank;
	/*     */
	/*     */ @ClassDiscription(name = "交易商", description = "")
	/*     */ private MFirm firm;
	/*     */
	/*     */ @ClassDiscription(name = "银行帐号", description = "")
	/*     */ private String account;
	/*     */
	/*     */ @ClassDiscription(name = "银行内部账号", description = "")
	/*     */ private String account1;
	/*     */
	/*     */ @ClassDiscription(name = "状态", description = "")
	/*     */ private Integer status;
	/*     */
	/*     */ @ClassDiscription(name = "账户名", description = "")
	/*     */ private String accountName;
	/*     */
	/*     */ @ClassDiscription(name = "银行名称 ", description = "")
	/*     */ private String bankName;
	/*     */
	/*     */ @ClassDiscription(name = "银行省份", description = "")
	/*     */ private String bankProvince;
	/*     */
	/*     */ @ClassDiscription(name = " 银行所在市", description = "")
	/*     */ private String bankCity;
	/*     */
	/*     */ @ClassDiscription(name = "电话号码", description = "")
	/*     */ private String mobile;
	/*     */
	/*     */ @ClassDiscription(name = "邮箱", description = "")
	/*     */ private String email;
	/*     */
	/*     */ @ClassDiscription(name = "是否已签约", description = "0 未签约,1 已签约")
	/*     */ private Integer isOpen;
	/*     */
	/*     */ @ClassDiscription(name = "帐户类型", description = "\t  1 身份证,2军官证,3国内护照,4户口本,5学员证,6退休证,7临时身份证,8组织机构代码,9营业执照,A警官证,B解放军士兵证,C回乡证,D外国护照,E港澳台居民身份证,F台湾通行证及其他有效旅行证,G海外客户编号,H解放军文职干部证,I武警文职干部证,J武警士兵证,X其他有效证件,Z重号身份证")
	/*     */ private String cardType;
	/*     */
	/*     */ @ClassDiscription(name = "证件号码", description = "")
	/*     */ private String card;
	/*     */
	/*     */ @ClassDiscription(name = "冻结资金", description = "")
	/*     */ private Double frozenFuns;
	/*     */
	/*     */ @ClassDiscription(name = "银行内部账户名称", description = "")
	/*     */ private String accountName1;
	/*     */
	/*     */ @ClassDiscription(name = "银行内部账户名称", description = "")
	/*     */ private Date openTime;
	/*     */
	/*     */ @ClassDiscription(name = "销户时间 ", description = "")
	/*     */ private Date delTime;
	/*     */
	/*     */ @ClassDiscription(name = "交易商入世协议号", description = "")
	/*     */ private String inmarketCode;

	/*     */
	/*     */ public Bank getBank()
	/*     */ {
		/* 141 */ return this.bank;
		/*     */ }

	/*     */
	/*     */ public void setBank(Bank bank)
	/*     */ {
		/* 151 */ this.bank = bank;
		/*     */ }

	/*     */
	/*     */ public MFirm getFirm()
	/*     */ {
		/* 161 */ return this.firm;
		/*     */ }

	/*     */
	/*     */ public void setFirm(MFirm firm)
	/*     */ {
		/* 171 */ this.firm = firm;
		/*     */ }

	/*     */
	/*     */ public String getAccount()
	/*     */ {
		/* 181 */ return this.account;
		/*     */ }

	/*     */
	/*     */ public void setAccount(String account)
	/*     */ {
		/* 191 */ this.account = account;
		/*     */ }

	/*     */
	/*     */ public String getAccount1()
	/*     */ {
		/* 201 */ return this.account1;
		/*     */ }

	/*     */
	/*     */ public void setAccount1(String account1)
	/*     */ {
		/* 211 */ this.account1 = account1;
		/*     */ }

	/*     */
	/*     */ public Integer getStatus()
	/*     */ {
		/* 222 */ return this.status;
		/*     */ }

	/*     */
	/*     */ public void setStatus(Integer status)
	/*     */ {
		/* 233 */ this.status = status;
		/*     */ }

	/*     */
	/*     */ public String getAccountName()
	/*     */ {
		/* 243 */ return this.accountName;
		/*     */ }

	/*     */
	/*     */ public void setAccountName(String accountName)
	/*     */ {
		/* 253 */ this.accountName = accountName;
		/*     */ }

	/*     */
	/*     */ public String getBankName()
	/*     */ {
		/* 263 */ return this.bankName;
		/*     */ }

	/*     */
	/*     */ public void setBankName(String bankName)
	/*     */ {
		/* 273 */ this.bankName = bankName;
		/*     */ }

	/*     */
	/*     */ public String getBankProvince()
	/*     */ {
		/* 283 */ return this.bankProvince;
		/*     */ }

	/*     */
	/*     */ public void setBankProvince(String bankProvince)
	/*     */ {
		/* 293 */ this.bankProvince = bankProvince;
		/*     */ }

	/*     */
	/*     */ public String getBankCity()
	/*     */ {
		/* 303 */ return this.bankCity;
		/*     */ }

	/*     */
	/*     */ public void setBankCity(String bankCity)
	/*     */ {
		/* 313 */ this.bankCity = bankCity;
		/*     */ }

	/*     */
	/*     */ public String getMobile()
	/*     */ {
		/* 323 */ return this.mobile;
		/*     */ }

	/*     */
	/*     */ public void setMobile(String mobile)
	/*     */ {
		/* 333 */ this.mobile = mobile;
		/*     */ }

	/*     */
	/*     */ public String getEmail()
	/*     */ {
		/* 343 */ return this.email;
		/*     */ }

	/*     */
	/*     */ public void setEmail(String email)
	/*     */ {
		/* 353 */ this.email = email;
		/*     */ }

	/*     */
	/*     */ public Integer getIsOpen()
	/*     */ {
		/* 364 */ return this.isOpen;
		/*     */ }

	/*     */
	/*     */ public void setIsOpen(Integer isOpen)
	/*     */ {
		/* 375 */ this.isOpen = isOpen;
		/*     */ }

	/*     */
	/*     */ public String getCardType()
	/*     */ {
		/* 406 */ return this.cardType;
		/*     */ }

	/*     */
	/*     */ public void setCardType(String cardType)
	/*     */ {
		/* 437 */ this.cardType = cardType;
		/*     */ }

	/*     */
	/*     */ public String getCard()
	/*     */ {
		/* 447 */ return this.card;
		/*     */ }

	/*     */
	/*     */ public void setCard(String card)
	/*     */ {
		/* 457 */ this.card = card;
		/*     */ }

	/*     */
	/*     */ public Double getFrozenFuns()
	/*     */ {
		/* 467 */ return this.frozenFuns;
		/*     */ }

	/*     */
	/*     */ public void setFrozenFuns(Double frozenFuns)
	/*     */ {
		/* 477 */ this.frozenFuns = frozenFuns;
		/*     */ }

	/*     */
	/*     */ public String getAccountName1()
	/*     */ {
		/* 487 */ return this.accountName1;
		/*     */ }

	/*     */
	/*     */ public void setAccountName1(String accountName1)
	/*     */ {
		/* 497 */ this.accountName1 = accountName1;
		/*     */ }

	/*     */
	/*     */ public Date getOpenTime()
	/*     */ {
		/* 507 */ return this.openTime;
		/*     */ }

	/*     */
	/*     */ public void setOpenTime(Date openTime)
	/*     */ {
		/* 517 */ this.openTime = openTime;
		/*     */ }

	/*     */
	/*     */ public Date getDelTime()
	/*     */ {
		/* 527 */ return this.delTime;
		/*     */ }

	/*     */
	/*     */ public void setDelTime(Date delTime)
	/*     */ {
		/* 537 */ this.delTime = delTime;
		/*     */ }

	/*     */
	/*     */ public String getInmarketCode()
	/*     */ {
		/* 547 */ return this.inmarketCode;
		/*     */ }

	/*     */
	/*     */ public void setInmarketCode(String inmarketCode)
	/*     */ {
		/* 557 */ this.inmarketCode = inmarketCode;
		/*     */ }

	/*     */
	/*     */ public CorrespondValue getCorrespondValue()
	/*     */ {
		/* 567 */ CorrespondValue result = new CorrespondValue();
		/* 568 */ result.account = getAccount();
		/* 569 */ result.account1 = getAccount1();
		/* 570 */ result.accountName = getAccountName();
		/* 571 */ result.accountName1 = getAccountName1();
		/* 572 */ result.bankCity = getBankCity();
		/* 573 */ if (getBank() != null) {
			/* 574 */ result.bankID = getBank().getBankID();
			/*     */ }
		/* 576 */ result.bankName = getBankName();
		/* 577 */ result.bankProvince = getBankProvince();
		/* 578 */ result.card = getCard();
		/* 579 */ result.cardType = getCardType();
		/* 580 */ result.deltime = getDelTime();
		/* 581 */ result.email = getEmail();
		/* 582 */ if (getFirm() != null) {
			/* 583 */ result.firmID = getFirm().getFirmID();
			/*     */ }
		/* 585 */ if (getFrozenFuns() != null) {
			/* 586 */ result.frozenFuns = getFrozenFuns().doubleValue();
			/*     */ }
		/* 588 */ result.inMarketCode = getInmarketCode();
		/* 589 */ if (getIsOpen() != null) {
			/* 590 */ result.isOpen = getIsOpen().intValue();
			/*     */ }
		/* 592 */ result.mobile = getMobile();
		/* 593 */ result.opentime = getOpenTime();
		/* 594 */ if (getStatus() != null) {
			/* 595 */ result.status = getStatus().intValue();
			/*     */ }
		/* 597 */ return result;
		/*     */ }

	/*     */
	/*     */ public StandardModel.PrimaryInfo fetchPKey()
	/*     */ {
		/* 602 */ return null;
		/*     */ }
	/*     */ }
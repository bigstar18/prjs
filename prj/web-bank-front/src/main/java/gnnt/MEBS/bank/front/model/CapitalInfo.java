/*     */ package gnnt.MEBS.bank.front.model;

/*     */
/*     */ import gnnt.MEBS.common.front.model.StandardModel;
/*     */ import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
/*     */ import gnnt.MEBS.common.front.model.integrated.MFirm;
/*     */ import gnnt.MEBS.common.front.model.translate.ClassDiscription;
/*     */ import java.util.Date;

/*     */
/*     */ public class CapitalInfo extends StandardModel
/*     */ {
	/*     */ private static final long serialVersionUID = 4594056539993383762L;
	/*     */
	/*     */ @ClassDiscription(name = "流水ID", description = "")
	/*     */ private Long id;
	/*     */
	/*     */ @ClassDiscription(name = "交易商", description = "")
	/*     */ private MFirm firm;
	/*     */
	/*     */ @ClassDiscription(name = "银行流水号", description = "")
	/*     */ private String funID;
	/*     */
	/*     */ @ClassDiscription(name = "银行", description = "")
	/*     */ private Bank bank;
	/*     */
	/*     */ @ClassDiscription(name = "贷方代码", description = "")
	/*     */ private String debitID;
	/*     */
	/*     */ @ClassDiscription(name = "借方代码", description = "")
	/*     */ private String creditID;
	/*     */
	/*     */ @ClassDiscription(name = "流水类型", description = " 0 入金,1 出金,2出入金手续费")
	/*     */ private Integer type;
	/*     */
	/*     */ @ClassDiscription(name = "金额", description = "")
	/*     */ private Double money;
	/*     */
	/*     */ @ClassDiscription(name = "业务代码", description = "")
	/*     */ private String operator;
	/*     */
	/*     */ @ClassDiscription(name = "创建时间", description = "")
	/*     */ private Date createtime;
	/*     */
	/*     */ @ClassDiscription(name = "银行时间", description = "")
	/*     */ private Date bankTime;
	/*     */
	/*     */ @ClassDiscription(name = "状态", description = "\t * 0 成功,1 失败,2 处理中,3 一次审核,4 二次审核,5 银行返回信息为空,6 银行返回市场流水号和市场保存流水号不一致,13 市场假银行出入金待审核状态")
	/*     */ private Integer status;
	/*     */
	/*     */ @ClassDiscription(name = "备注信息", description = "")
	/*     */ private String note;
	/*     */
	/*     */ @ClassDiscription(name = " 业务流水", description = "")
	/*     */ private Long actionID;
	/*     */
	/*     */ @ClassDiscription(name = "是否加急", description = "")
	/*     */ private Integer express;
	/*     */
	/*     */ @ClassDiscription(name = "特殊加的(银行名称)", description = "")
	/*     */ private String bankName;
	/*     */
	/*     */ @ClassDiscription(name = "特殊加的(银行账号)", description = "")
	/*     */ private String account;
	/*     */
	/*     */ @ClassDiscription(name = "创建日期 ", description = "")
	/*     */ private String createDate;
	/*     */
	/*     */ @ClassDiscription(name = "流水2", description = "")
	/*     */ private String funID2;

	/*     */
	/*     */ public Long getId()
	/*     */ {
		/* 126 */ return this.id;
		/*     */ }

	/*     */
	/*     */ public void setId(Long id)
	/*     */ {
		/* 136 */ this.id = id;
		/*     */ }

	/*     */
	/*     */ public MFirm getFirm()
	/*     */ {
		/* 146 */ return this.firm;
		/*     */ }

	/*     */
	/*     */ public void setFirm(MFirm firm)
	/*     */ {
		/* 156 */ this.firm = firm;
		/*     */ }

	/*     */
	/*     */ public String getFunID()
	/*     */ {
		/* 166 */ return this.funID;
		/*     */ }

	/*     */
	/*     */ public void setFunID(String funID)
	/*     */ {
		/* 176 */ this.funID = funID;
		/*     */ }

	/*     */
	/*     */ public Bank getBank()
	/*     */ {
		/* 186 */ return this.bank;
		/*     */ }

	/*     */
	/*     */ public void setBank(Bank bank)
	/*     */ {
		/* 196 */ this.bank = bank;
		/*     */ }

	/*     */
	/*     */ public String getDebitID()
	/*     */ {
		/* 206 */ return this.debitID;
		/*     */ }

	/*     */
	/*     */ public void setDebitID(String debitID)
	/*     */ {
		/* 216 */ this.debitID = debitID;
		/*     */ }

	/*     */
	/*     */ public String getCreditID()
	/*     */ {
		/* 226 */ return this.creditID;
		/*     */ }

	/*     */
	/*     */ public void setCreditID(String creditID)
	/*     */ {
		/* 236 */ this.creditID = creditID;
		/*     */ }

	/*     */
	/*     */ public Integer getType()
	/*     */ {
		/* 249 */ return this.type;
		/*     */ }

	/*     */
	/*     */ public void setType(Integer type)
	/*     */ {
		/* 262 */ this.type = type;
		/*     */ }

	/*     */
	/*     */ public Double getMoney()
	/*     */ {
		/* 272 */ return this.money;
		/*     */ }

	/*     */
	/*     */ public void setMoney(Double money)
	/*     */ {
		/* 282 */ this.money = money;
		/*     */ }

	/*     */
	/*     */ public String getOperator()
	/*     */ {
		/* 292 */ return this.operator;
		/*     */ }

	/*     */
	/*     */ public void setOperator(String operator)
	/*     */ {
		/* 302 */ this.operator = operator;
		/*     */ }

	/*     */
	/*     */ public Date getCreatetime()
	/*     */ {
		/* 312 */ return this.createtime;
		/*     */ }

	/*     */
	/*     */ public void setCreatetime(Date createtime)
	/*     */ {
		/* 322 */ this.createtime = createtime;
		/*     */ }

	/*     */
	/*     */ public Date getBankTime()
	/*     */ {
		/* 332 */ return this.bankTime;
		/*     */ }

	/*     */
	/*     */ public void setBankTime(Date bankTime)
	/*     */ {
		/* 342 */ this.bankTime = bankTime;
		/*     */ }

	/*     */
	/*     */ public Integer getStatus()
	/*     */ {
		/* 360 */ return this.status;
		/*     */ }

	/*     */
	/*     */ public void setStatus(Integer status)
	/*     */ {
		/* 378 */ this.status = status;
		/*     */ }

	/*     */
	/*     */ public String getNote()
	/*     */ {
		/* 388 */ return this.note;
		/*     */ }

	/*     */
	/*     */ public void setNote(String note)
	/*     */ {
		/* 398 */ this.note = note;
		/*     */ }

	/*     */
	/*     */ public Long getActionID()
	/*     */ {
		/* 408 */ return this.actionID;
		/*     */ }

	/*     */
	/*     */ public void setActionID(Long actionID)
	/*     */ {
		/* 418 */ this.actionID = actionID;
		/*     */ }

	/*     */
	/*     */ public Integer getExpress()
	/*     */ {
		/* 428 */ return this.express;
		/*     */ }

	/*     */
	/*     */ public void setExpress(Integer express)
	/*     */ {
		/* 438 */ this.express = express;
		/*     */ }

	/*     */
	/*     */ public String getBankName()
	/*     */ {
		/* 448 */ return this.bankName;
		/*     */ }

	/*     */
	/*     */ public void setBankName(String bankName)
	/*     */ {
		/* 458 */ this.bankName = bankName;
		/*     */ }

	/*     */
	/*     */ public String getAccount()
	/*     */ {
		/* 468 */ return this.account;
		/*     */ }

	/*     */
	/*     */ public void setAccount(String account)
	/*     */ {
		/* 478 */ this.account = account;
		/*     */ }

	/*     */
	/*     */ public String getCreateDate()
	/*     */ {
		/* 488 */ return this.createDate;
		/*     */ }

	/*     */
	/*     */ public void setCreateDate(String createDate)
	/*     */ {
		/* 498 */ this.createDate = createDate;
		/*     */ }

	/*     */
	/*     */ public String getFunID2()
	/*     */ {
		/* 508 */ return this.funID2;
		/*     */ }

	/*     */
	/*     */ public void setFunID2(String funID2)
	/*     */ {
		/* 518 */ this.funID2 = funID2;
		/*     */ }

	/*     */
	/*     */ public StandardModel.PrimaryInfo fetchPKey()
	/*     */ {
		/* 523 */ return new StandardModel.PrimaryInfo("id", this.id);
		/*     */ }
	/*     */ }
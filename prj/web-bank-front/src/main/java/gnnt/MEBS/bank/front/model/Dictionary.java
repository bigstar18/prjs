/*     */ package gnnt.MEBS.bank.front.model;

/*     */
/*     */ import gnnt.MEBS.common.front.model.StandardModel;
/*     */ import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
/*     */ import gnnt.MEBS.common.front.model.translate.ClassDiscription;

/*     */
/*     */ public class Dictionary extends StandardModel
/*     */ {
	/*     */ private static final long serialVersionUID = -5115729512519432548L;
	/*     */
	/*     */ @ClassDiscription(name = "字典ID", description = "")
	/*     */ private Integer id;
	/*     */
	/*     */ @ClassDiscription(name = "类型 ", description = "")
	/*     */ private Integer type;
	/*     */
	/*     */ @ClassDiscription(name = "银行编号 ", description = "")
	/*     */ private String bankID;
	/*     */
	/*     */ @ClassDiscription(name = "银行", description = "")
	/*     */ private Bank bank;
	/*     */
	/*     */ @ClassDiscription(name = "字典名称", description = "")
	/*     */ private String name;
	/*     */
	/*     */ @ClassDiscription(name = "字典值", description = "")
	/*     */ private String value;
	/*     */
	/*     */ @ClassDiscription(name = "备注信息", description = "")
	/*     */ private String note;

	/*     */
	/*     */ public Integer getId()
	/*     */ {
		/* 60 */ return this.id;
		/*     */ }

	/*     */
	/*     */ public void setId(Integer id)
	/*     */ {
		/* 70 */ this.id = id;
		/*     */ }

	/*     */
	/*     */ public Integer getType()
	/*     */ {
		/* 80 */ return this.type;
		/*     */ }

	/*     */
	/*     */ public void setType(Integer type)
	/*     */ {
		/* 90 */ this.type = type;
		/*     */ }

	/*     */
	/*     */ public String getBankID()
	/*     */ {
		/* 100 */ return this.bankID;
		/*     */ }

	/*     */
	/*     */ public void setBankID(String bankID)
	/*     */ {
		/* 110 */ this.bankID = bankID;
		/*     */ }

	/*     */
	/*     */ public Bank getBank()
	/*     */ {
		/* 120 */ return this.bank;
		/*     */ }

	/*     */
	/*     */ public void setBank(Bank bank)
	/*     */ {
		/* 130 */ this.bank = bank;
		/*     */ }

	/*     */
	/*     */ public String getName()
	/*     */ {
		/* 140 */ return this.name;
		/*     */ }

	/*     */
	/*     */ public void setName(String name)
	/*     */ {
		/* 150 */ this.name = name;
		/*     */ }

	/*     */
	/*     */ public String getValue()
	/*     */ {
		/* 160 */ return this.value;
		/*     */ }

	/*     */
	/*     */ public void setValue(String value)
	/*     */ {
		/* 170 */ this.value = value;
		/*     */ }

	/*     */
	/*     */ public String getNote()
	/*     */ {
		/* 180 */ return this.note;
		/*     */ }

	/*     */
	/*     */ public void setNote(String note)
	/*     */ {
		/* 190 */ this.note = note;
		/*     */ }

	/*     */
	/*     */ public StandardModel.PrimaryInfo fetchPKey()
	/*     */ {
		/* 195 */ return new StandardModel.PrimaryInfo("id", this.id);
		/*     */ }
	/*     */ }
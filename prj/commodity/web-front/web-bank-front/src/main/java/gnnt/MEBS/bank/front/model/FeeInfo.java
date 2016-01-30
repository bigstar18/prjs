/*     */ package gnnt.MEBS.bank.front.model;

/*     */
/*     */ import gnnt.MEBS.common.front.model.StandardModel;
/*     */ import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
/*     */ import gnnt.MEBS.common.front.model.translate.ClassDiscription;
/*     */ import java.util.Date;

/*     */
/*     */ public class FeeInfo extends StandardModel
/*     */ {
	/*     */ private static final long serialVersionUID = -5669018684821401224L;
	/*     */
	/*     */ @ClassDiscription(name = "费用表ID", description = "")
	/*     */ private Long id;
	/*     */
	/*     */ @ClassDiscription(name = "结束金额", description = "")
	/*     */ private Double upLimit;
	/*     */
	/*     */ @ClassDiscription(name = "起始金额", description = "")
	/*     */ private Double downLimit;
	/*     */
	/*     */ @ClassDiscription(name = "计算方式", description = "")
	/*     */ private Integer tmode;
	/*     */
	/*     */ @ClassDiscription(name = "手续费", description = "")
	/*     */ private Double rate;
	/*     */
	/*     */ @ClassDiscription(name = "收费类型", description = "")
	/*     */ private String type;
	/*     */
	/*     */ @ClassDiscription(name = "记录时间", description = "")
	/*     */ private Date createTime;
	/*     */
	/*     */ @ClassDiscription(name = "修改时间", description = "")
	/*     */ private Date updateTime;
	/*     */
	/*     */ @ClassDiscription(name = "用户ID", description = "记录人,交易商,银行")
	/*     */ private String userID;
	/*     */
	/*     */ @ClassDiscription(name = "最大金额", description = "")
	/*     */ private Double maxRateValue;
	/*     */
	/*     */ @ClassDiscription(name = "最小金额", description = "")
	/*     */ private Double minRateValue;

	/*     */
	/*     */ public Long getId()
	/*     */ {
		/* 84 */ return this.id;
		/*     */ }

	/*     */
	/*     */ public void setId(Long id)
	/*     */ {
		/* 94 */ this.id = id;
		/*     */ }

	/*     */
	/*     */ public Double getUpLimit()
	/*     */ {
		/* 104 */ return this.upLimit;
		/*     */ }

	/*     */
	/*     */ public void setUpLimit(Double upLimit)
	/*     */ {
		/* 114 */ this.upLimit = upLimit;
		/*     */ }

	/*     */
	/*     */ public Double getDownLimit()
	/*     */ {
		/* 124 */ return this.downLimit;
		/*     */ }

	/*     */
	/*     */ public void setDownLimit(Double downLimit)
	/*     */ {
		/* 134 */ this.downLimit = downLimit;
		/*     */ }

	/*     */
	/*     */ public Integer getTmode()
	/*     */ {
		/* 145 */ return this.tmode;
		/*     */ }

	/*     */
	/*     */ public void setTmode(Integer tmode)
	/*     */ {
		/* 156 */ this.tmode = tmode;
		/*     */ }

	/*     */
	/*     */ public Double getRate()
	/*     */ {
		/* 166 */ return this.rate;
		/*     */ }

	/*     */
	/*     */ public void setRate(Double rate)
	/*     */ {
		/* 176 */ this.rate = rate;
		/*     */ }

	/*     */
	/*     */ public String getType()
	/*     */ {
		/* 186 */ return this.type;
		/*     */ }

	/*     */
	/*     */ public void setType(String type)
	/*     */ {
		/* 196 */ this.type = type;
		/*     */ }

	/*     */
	/*     */ public Date getCreateTime()
	/*     */ {
		/* 206 */ return this.createTime;
		/*     */ }

	/*     */
	/*     */ public void setCreateTime(Date createTime)
	/*     */ {
		/* 216 */ this.createTime = createTime;
		/*     */ }

	/*     */
	/*     */ public Date getUpdateTime()
	/*     */ {
		/* 226 */ return this.updateTime;
		/*     */ }

	/*     */
	/*     */ public void setUpdateTime(Date updateTime)
	/*     */ {
		/* 236 */ this.updateTime = updateTime;
		/*     */ }

	/*     */
	/*     */ public String getUserID()
	/*     */ {
		/* 247 */ return this.userID;
		/*     */ }

	/*     */
	/*     */ public void setUserID(String userID)
	/*     */ {
		/* 258 */ this.userID = userID;
		/*     */ }

	/*     */
	/*     */ public Double getMaxRateValue()
	/*     */ {
		/* 268 */ return this.maxRateValue;
		/*     */ }

	/*     */
	/*     */ public void setMaxRateValue(Double maxRateValue)
	/*     */ {
		/* 278 */ this.maxRateValue = maxRateValue;
		/*     */ }

	/*     */
	/*     */ public Double getMinRateValue()
	/*     */ {
		/* 288 */ return this.minRateValue;
		/*     */ }

	/*     */
	/*     */ public void setMinRateValue(Double minRateValue)
	/*     */ {
		/* 298 */ this.minRateValue = minRateValue;
		/*     */ }

	/*     */
	/*     */ public StandardModel.PrimaryInfo fetchPKey()
	/*     */ {
		/* 303 */ return new StandardModel.PrimaryInfo("id", this.id);
		/*     */ }
	/*     */ }
package gnnt.MEBS.timebargain.server.model;

import java.io.*;

import org.apache.commons.lang.builder.*;

/**
 * 市场参数对象.
 *
 * <p><a href="Market.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Market implements Serializable {
    private static final long serialVersionUID = 3690197650654049813L;
    private String marketCode;  //市场代码
    private String marketName;  //名称
    private Short status;  //状态  1正常；2无效
    private Short marginFBFlag;  //保证金调整方式  0结算时调整；1开市前调整
    private String shortName; //市场简称
	private Short runMode;  //运行模式 0手动；1自动
	private Short tradePriceAlgr;  //成交价计算方式  0先入价；1中间价
	private Short closeAlgr;  //平仓算法 0：先开先平；1：后开先平；2：持仓均价平仓
	private Short floatingLossComputeType;  //浮亏计算方式
    private Short delayQuoShowType;//延期行情显示类型，0：交收申报结束和中立仓申报结束显示； 1：实时显示；
    private Short delayNeedBill; // 延期交收是否需要仓单，0：不需要； 1：需要；
    private Short NeutralFlag; //中立仓标志，延期交收是否有中立仓，0无，1有';
    private Short NeutralMatchPriority; //中立仓撮合优先权，0默认，1中立仓优先';
    private Short quotationTwoSide; //行情单双边配置，1单边，2双边

	public static final short STATUS_ENABLED = 1;// 正常有效
	public static final short STATUS_DISABLED = 2;//无效
	
	public static final short RUNMODE_HAND = 0;// 手动
	public static final short RUNMODE_AUTO = 1;//自动
	
	public static final short MARGINFBFLAG_CALC = 0;// 结算时调整
	public static final short MARGINFBFLAG_OPEN = 1;//开市前调整
	//平仓算法
	public static final short CLOSEALGR_FIRSTOPEN = 0;// 先开先平
	public static final short CLOSEALGR_LASTOPEN = 1;//后开先平
	public static final short CLOSEALGR_EVEN = 2;// 持仓均价平仓
	
	//延期行情显示类型
	public static final short DELAYQUOSHOW_END = 0;// 0：交收申报结束和中立仓申报结束显示
	public static final short DELAYQUOSHOW_RT = 1;//1：实时显示；
	
	public Short getCloseAlgr() {
		return closeAlgr;
	}

	public void setCloseAlgr(Short closeAlgr) {
		this.closeAlgr = closeAlgr;
	}

	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public Short getMarginFBFlag() {
		return marginFBFlag;
	}

	public void setMarginFBFlag(Short marginFBFlag) {
		this.marginFBFlag = marginFBFlag;
	}

	public String getMarketCode() {
		return marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Short getRunMode() {
		return runMode;
	}

	public void setRunMode(Short runMode) {
		this.runMode = runMode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getTradePriceAlgr() {
		return tradePriceAlgr;
	}

	public void setTradePriceAlgr(Short tradePriceAlgr) {
		this.tradePriceAlgr = tradePriceAlgr;
	}

	public Short getFloatingLossComputeType() {
		return floatingLossComputeType;
	}

	public void setFloatingLossComputeType(Short floatingLossComputeType) {
		this.floatingLossComputeType = floatingLossComputeType;
	}
	
	public Short getDelayQuoShowType() {
		return delayQuoShowType;
	}

	public void setDelayQuoShowType(Short delayQuoShowType) {
		this.delayQuoShowType = delayQuoShowType;
	}
	
	public Short getDelayNeedBill() {
		return delayNeedBill;
	}

	public void setDelayNeedBill(Short delayNeedBill) {
		this.delayNeedBill = delayNeedBill;
	}
	
	public Short getNeutralFlag() {
		return NeutralFlag;
	}

	public Short getNeutralMatchPriority() {
		return NeutralMatchPriority;
	}

	public Short getQuotationTwoSide() {
		return quotationTwoSide;
	}

	public void setNeutralFlag(Short neutralFlag) {
		NeutralFlag = neutralFlag;
	}

	public void setNeutralMatchPriority(Short neutralMatchPriority) {
		NeutralMatchPriority = neutralMatchPriority;
	}

	public void setQuotationTwoSide(Short quotationTwoSide) {
		this.quotationTwoSide = quotationTwoSide;
	}
}

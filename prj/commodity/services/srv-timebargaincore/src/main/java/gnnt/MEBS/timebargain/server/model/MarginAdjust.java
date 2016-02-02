package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 保证金调整对象.
 *
 * <p><a href="MarginAdjust.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class MarginAdjust implements Serializable {
	private static final long serialVersionUID = 3690197650654049822L;

	private String firmID; // 交易商ID
	
	private String commodityID; // 商品代码

	private Double marginRate_B;// 买保证金比例

	private Double marginRate_S;// 卖保证金比例
	
	private Double marginAssure_B;// 买担保金比例 

	private Double marginAssure_S;// 卖担保金比例
	
	private Date settleDate1; // 交收相关日期1
	
	private Double marginItem1;// 买保证金比例1

	private Double marginItem1_S;// 卖保证金比例1
	
	private Double marginItemAssure1;// 买担保金比例1

	private Double marginItemAssure1_S;// 卖担保金比例1

	private Date settleDate2; // 交收相关日期2
	
	private Double marginItem2;// 买保证金比例2

	private Double marginItem2_S;// 卖保证金比例2
	
	private Double marginItemAssure2;// 买担保金比例2

	private Double marginItemAssure2_S;// 卖担保金比例2
	
	private Date settleDate3; // 交收相关日期3
	
	private Double marginItem3;// 买保证金比例3

	private Double marginItem3_S;// 卖保证金比例3
	
	private Double marginItemAssure3;// 买担保金比例3

	private Double marginItemAssure3_S;// 卖担保金比例3
	
	private Date settleDate4; // 交收相关日期4
	
	private Double marginItem4;// 买保证金比例4

	private Double marginItem4_S;// 卖保证金比例4
	
	private Double marginItemAssure4;// 买担保金比例4

	private Double marginItemAssure4_S;// 卖担保金比例4

	private Date settleDate5; // 交收相关日期5
	
	private Double marginItem5;// 买保证金比例5

	private Double marginItem5_S;// 卖保证金比例5
	
	private Double marginItemAssure5;// 买担保金比例5

	private Double marginItemAssure5_S;// 卖担保金比例5
	
	public static final int RATECHECKPOINT_CALC = 0;// 保证金比例调整点是闭市结算时调整
	public static final int RATECHECKPOINT_OPEN = 1;//保证金比例调整点是开市准备时调整
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


	public String getCommodityID() {
		return commodityID;
	}


	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}


	public String getFirmID() {
		return firmID;
	}


	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}


	public Double getMarginAssure_B() {
		return marginAssure_B;
	}


	public void setMarginAssure_B(Double marginAssure_B) {
		this.marginAssure_B = marginAssure_B;
	}


	public Double getMarginAssure_S() {
		return marginAssure_S;
	}


	public void setMarginAssure_S(Double marginAssure_S) {
		this.marginAssure_S = marginAssure_S;
	}


	public Double getMarginItem1() {
		return marginItem1;
	}


	public void setMarginItem1(Double marginItem1) {
		this.marginItem1 = marginItem1;
	}


	public Double getMarginItem1_S() {
		return marginItem1_S;
	}


	public void setMarginItem1_S(Double marginItem1_S) {
		this.marginItem1_S = marginItem1_S;
	}


	public Double getMarginItem2() {
		return marginItem2;
	}


	public void setMarginItem2(Double marginItem2) {
		this.marginItem2 = marginItem2;
	}


	public Double getMarginItem2_S() {
		return marginItem2_S;
	}


	public void setMarginItem2_S(Double marginItem2_S) {
		this.marginItem2_S = marginItem2_S;
	}


	public Double getMarginItem3() {
		return marginItem3;
	}


	public void setMarginItem3(Double marginItem3) {
		this.marginItem3 = marginItem3;
	}


	public Double getMarginItem3_S() {
		return marginItem3_S;
	}


	public void setMarginItem3_S(Double marginItem3_S) {
		this.marginItem3_S = marginItem3_S;
	}


	public Double getMarginItem4() {
		return marginItem4;
	}


	public void setMarginItem4(Double marginItem4) {
		this.marginItem4 = marginItem4;
	}


	public Double getMarginItem4_S() {
		return marginItem4_S;
	}


	public void setMarginItem4_S(Double marginItem4_S) {
		this.marginItem4_S = marginItem4_S;
	}


	public Double getMarginItemAssure1() {
		return marginItemAssure1;
	}


	public void setMarginItemAssure1(Double marginItemAssure1) {
		this.marginItemAssure1 = marginItemAssure1;
	}


	public Double getMarginItemAssure1_S() {
		return marginItemAssure1_S;
	}


	public void setMarginItemAssure1_S(Double marginItemAssure1_S) {
		this.marginItemAssure1_S = marginItemAssure1_S;
	}


	public Double getMarginItemAssure2() {
		return marginItemAssure2;
	}


	public void setMarginItemAssure2(Double marginItemAssure2) {
		this.marginItemAssure2 = marginItemAssure2;
	}


	public Double getMarginItemAssure2_S() {
		return marginItemAssure2_S;
	}


	public void setMarginItemAssure2_S(Double marginItemAssure2_S) {
		this.marginItemAssure2_S = marginItemAssure2_S;
	}


	public Double getMarginItemAssure3() {
		return marginItemAssure3;
	}


	public void setMarginItemAssure3(Double marginItemAssure3) {
		this.marginItemAssure3 = marginItemAssure3;
	}


	public Double getMarginItemAssure3_S() {
		return marginItemAssure3_S;
	}


	public void setMarginItemAssure3_S(Double marginItemAssure3_S) {
		this.marginItemAssure3_S = marginItemAssure3_S;
	}


	public Double getMarginItemAssure4() {
		return marginItemAssure4;
	}


	public void setMarginItemAssure4(Double marginItemAssure4) {
		this.marginItemAssure4 = marginItemAssure4;
	}


	public Double getMarginItemAssure4_S() {
		return marginItemAssure4_S;
	}


	public void setMarginItemAssure4_S(Double marginItemAssure4_S) {
		this.marginItemAssure4_S = marginItemAssure4_S;
	}


	public Double getMarginRate_B() {
		return marginRate_B;
	}


	public void setMarginRate_B(Double marginRate_B) {
		this.marginRate_B = marginRate_B;
	}


	public Double getMarginRate_S() {
		return marginRate_S;
	}


	public void setMarginRate_S(Double marginRate_S) {
		this.marginRate_S = marginRate_S;
	}


	public Date getSettleDate1() {
		return settleDate1;
	}


	public void setSettleDate1(Date settleDate1) {
		this.settleDate1 = settleDate1;
	}


	public Date getSettleDate2() {
		return settleDate2;
	}


	public void setSettleDate2(Date settleDate2) {
		this.settleDate2 = settleDate2;
	}


	public Date getSettleDate3() {
		return settleDate3;
	}


	public void setSettleDate3(Date settleDate3) {
		this.settleDate3 = settleDate3;
	}


	public Date getSettleDate4() {
		return settleDate4;
	}


	public void setSettleDate4(Date settleDate4) {
		this.settleDate4 = settleDate4;
	}


	public Double getMarginItem5() {
		return marginItem5;
	}


	public void setMarginItem5(Double marginItem5) {
		this.marginItem5 = marginItem5;
	}


	public Double getMarginItem5_S() {
		return marginItem5_S;
	}


	public void setMarginItem5_S(Double marginItem5_S) {
		this.marginItem5_S = marginItem5_S;
	}


	public Double getMarginItemAssure5() {
		return marginItemAssure5;
	}


	public void setMarginItemAssure5(Double marginItemAssure5) {
		this.marginItemAssure5 = marginItemAssure5;
	}


	public Double getMarginItemAssure5_S() {
		return marginItemAssure5_S;
	}


	public void setMarginItemAssure5_S(Double marginItemAssure5_S) {
		this.marginItemAssure5_S = marginItemAssure5_S;
	}


	public Date getSettleDate5() {
		return settleDate5;
	}


	public void setSettleDate5(Date settleDate5) {
		this.settleDate5 = settleDate5;
	}


}

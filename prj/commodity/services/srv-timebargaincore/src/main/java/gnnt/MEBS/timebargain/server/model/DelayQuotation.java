package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 延期行情对象.
 *
 * <p><a href="DelayQuotation.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.2.2
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class DelayQuotation implements Serializable {


	private static final long serialVersionUID = 3690197650654059813L;

	public String commodityID; // 商品代码
	
	public Long buySettleQty = 0l; //买交收申报
	
	public Long sellSettleQty = 0l; //卖交收申报
	
	public Long buyNeutralQty = 0l; //买中立仓
	
	public Long sellNeutralQty = 0l; //卖中立仓
	
	public Timestamp createTime = null; // 更新行情时间
	
	public Short neutralSide=0; //中立仓方向 ；0：无；1：买方；2：卖方；
	
	//中立仓方向
	public static final short NEUTRAL_SIDE_N = 0;//无
	public static final short NEUTRAL_SIDE_B = 1;//买方
	public static final short NEUTRAL_SIDE_S = 2;//卖方
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public Long getBuySettleQty() {
		return buySettleQty;
	}

	public void setBuySettleQty(Long buySettleQty) {
		this.buySettleQty = buySettleQty;
	}

	public Long getSellSettleQty() {
		return sellSettleQty;
	}

	public void setSellSettleQty(Long sellSettleQty) {
		this.sellSettleQty = sellSettleQty;
	}

	public Long getBuyNeutralQty() {
		return buyNeutralQty;
	}

	public void setBuyNeutralQty(Long buyNeutralQty) {
		this.buyNeutralQty = buyNeutralQty;
	}

	public Long getSellNeutralQty() {
		return sellNeutralQty;
	}

	public void setSellNeutralQty(Long sellNeutralQty) {
		this.sellNeutralQty = sellNeutralQty;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Short getNeutralSide() {
		return neutralSide;
	}

	public void setNeutralSide(Short neutralSide) {
		this.neutralSide = neutralSide;
	}

}

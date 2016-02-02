package gnnt.MEBS.timebargain.server.model;

import java.io.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 交收对象.
 *
 * <p><a href="Settle.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Settle implements Serializable {
	private static final long serialVersionUID = 3690197650654049826L;
	
	private String commodityID; //商品代码
	
	private Double price; // 价格
	
	private String bCustomerID; // 买客户ID
	
	private String bFirmID; // 买交易商ID
	
	private Long bHoldQty; // 买持仓数量
	
	private Long bGageQty=new Long(0); // 买抵顶数量

	private String sCustomerID; // 卖客户ID
	
	private String sFirmID; // 卖交易商ID
	
	private Long sHoldQty; // 卖持仓数量
	
	private Long sGageQty=new Long(0); // 卖抵顶数量
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getBCustomerID() {
		return bCustomerID;
	}

	public void setBCustomerID(String customerID) {
		bCustomerID = customerID;
	}

	public String getBFirmID() {
		return bFirmID;
	}

	public void setBFirmID(String firmID) {
		bFirmID = firmID;
	}

	public Long getBGageQty() {
		return bGageQty;
	}

	public void setBGageQty(Long gageQty) {
		bGageQty = gageQty;
	}

	public Long getBHoldQty() {
		return bHoldQty;
	}

	public void setBHoldQty(Long holdQty) {
		bHoldQty = holdQty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSCustomerID() {
		return sCustomerID;
	}

	public void setSCustomerID(String customerID) {
		sCustomerID = customerID;
	}

	public String getSFirmID() {
		return sFirmID;
	}

	public void setSFirmID(String firmID) {
		sFirmID = firmID;
	}

	public Long getSGageQty() {
		return sGageQty;
	}

	public void setSGageQty(Long gageQty) {
		sGageQty = gageQty;
	}

	public Long getSHoldQty() {
		return sHoldQty;
	}

	public void setSHoldQty(Long holdQty) {
		sHoldQty = holdQty;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}


}

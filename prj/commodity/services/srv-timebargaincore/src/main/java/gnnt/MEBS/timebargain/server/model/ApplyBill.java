package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 仓单处理申请对象.
 *
 * <p><a href="ApplyBill.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class ApplyBill implements Serializable{
	private static final long serialVersionUID = 3690197650654049927L;
	private Long applyID;//申请单号
	private String commodityID;//商品代码
	private String firmID_S;//卖方交易商代码
	private String customerID_S;//卖方交易客户ID
	private String billID;//仓单号
	private Long quantity;//仓单数量
	private Short applyType;//申请种类
	private Short status;//当前状态
	private Date createTime;//创建时间
	private String creator;//创建人
	private String remark1;//创建人备注
	private Date modifyTime;//最后修改时间
	private String modifier;//最后修改人
	private String remark2;//修改人备注
	private String firmID_B;//买方交易商ID
	private String customerID_B;//买方交易客户ID
	private Double price;//提前交收价格
	private Short bS_Flag;//买卖标志，目前只有卖方,1买2卖
	private Long validID;//生效仓单号
	private Long bGageQty=new Long(0);//其中买抵顶数量
	private Long sGageQty=new Long(0);//其中卖低顶数量
	
	public Long getValidID() {
		return validID;
	}

	public void setValidID(Long validID) {
		this.validID = validID;
	}

	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public Long getApplyID() {
		return applyID;
	}

	public void setApplyID(Long applyID) {
		this.applyID = applyID;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCustomerID_B() {
		return customerID_B;
	}

	public void setCustomerID_B(String customerID_B) {
		this.customerID_B = customerID_B;
	}

	public String getCustomerID_S() {
		return customerID_S;
	}

	public void setCustomerID_S(String customerID_S) {
		this.customerID_S = customerID_S;
	}

	public String getFirmID_B() {
		return firmID_B;
	}

	public void setFirmID_B(String firmID_B) {
		this.firmID_B = firmID_B;
	}

	public String getFirmID_S() {
		return firmID_S;
	}

	public void setFirmID_S(String firmID_S) {
		this.firmID_S = firmID_S;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public Short getBS_Flag() {
		return bS_Flag;
	}

	public void setBS_Flag(Short flag) {
		bS_Flag = flag;
	}

	public Short getApplyType() {
		return applyType;
	}

	public void setApplyType(Short applyType) {
		this.applyType = applyType;
	}

	public Long getBGageQty() {
		return bGageQty;
	}

	public void setBGageQty(Long gageQty) {
		bGageQty = gageQty;
	}

	public Long getSGageQty() {
		return sGageQty;
	}

	public void setSGageQty(Long gageQty) {
		sGageQty = gageQty;
	}
}

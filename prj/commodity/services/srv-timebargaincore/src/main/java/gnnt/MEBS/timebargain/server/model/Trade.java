package gnnt.MEBS.timebargain.server.model;

import java.io.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 成交回报对象.
 *
 * <p><a href="Trade.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Trade implements Serializable {
	private static final long serialVersionUID = 3690197650654049815L;

	private Long orderNo; // 委托单号

	private Long m_TradeNo; // 撮合成交号

	private Double price; // 成交价格

	private Long quantity; // 成交数量
        
    private Long m_TradeNo_Opp; // 对方撮合成交号
    
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

	public Long getM_TradeNo() {
		return m_TradeNo;
	}

	public void setM_TradeNo(Long tradeNo) {
		m_TradeNo = tradeNo;
	}

	public Long getM_TradeNo_Opp() {
		return m_TradeNo_Opp;
	}

	public void setM_TradeNo_Opp(Long tradeNo_Opp) {
		m_TradeNo_Opp = tradeNo_Opp;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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
}

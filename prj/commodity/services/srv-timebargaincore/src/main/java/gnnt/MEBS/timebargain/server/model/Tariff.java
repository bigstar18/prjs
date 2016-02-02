package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;

/**
 * 手续费套餐对象.
 *
 * <p><a href="Tariff.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:Lizs@gnnt.com.cn">Lizs</a>
 */
public class Tariff implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tariffID; // 套餐ID
	
	private String commodityID; // 商品代码
	
	private short feeAlgr; // 开仓手续费算法
	
	private double feeRate_B; // 开仓买手续费系数
	
	private double feeRate_S; // 开仓卖手续费系数

	public String getTariffID() {
		return tariffID;
	}

	public void setTariffID(String tariffID) {
		this.tariffID = tariffID;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public short getFeeAlgr() {
		return feeAlgr;
	}

	public void setFeeAlgr(short feeAlgr) {
		this.feeAlgr = feeAlgr;
	}

	public double getFeeRate_B() {
		return feeRate_B;
	}

	public void setFeeRate_B(double feeRate_B) {
		this.feeRate_B = feeRate_B;
	}

	public double getFeeRate_S() {
		return feeRate_S;
	}

	public void setFeeRate_S(double feeRate_S) {
		this.feeRate_S = feeRate_S;
	}
	
	
}

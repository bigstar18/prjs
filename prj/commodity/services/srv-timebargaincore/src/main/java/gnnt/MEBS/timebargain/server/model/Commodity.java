package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 商品对象.
 *
 * <p><a href="Commodity.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Commodity implements Serializable {
	private static final long serialVersionUID = 3690197650654049812L;

	private String commodityID; // 商品代码

	private String name; // 商品名称

	private long sortID; // 类别

	private short status; // 状态

	private double contractFactor; // 合约因子
	
	private double minPriceMove; // 最小变动价位
	
	private long breedID;  //品种ID
	
	private short spreadAlgr;//涨跌幅算法

	private double spreadUpLmt; // 涨幅上限

	private double spreadDownLmt; // 涨幅下限

	private short feeAlgr; // 开仓手续费算法

	private double feeRate_B;// 开仓买手续费系数
	
	private double feeRate_S;// 开仓卖手续费系数
	
	private short marginAlgr;// 交易保证金算法

	private double marginRate_B;// 交易买保证金系数

	private double marginRate_S;// 交易卖保证金系数

	private Date marketDate; // 上市日期

	private Date settleDate; // 交收日期

	private double lastPrice;  //最近一次结算价
	
	private double marginAssure_B;// 交易买担保金系数

	private double marginAssure_S;// 交易卖担保金系数
	
	private short marginPriceType; //保证金计算方式
	
	private long firmMaxHoldQty; // 交易商最大订货量
	
	private short firmMaxHoldQtyAlgr; //交易商订货量限制算法
	
	private long startPercentQty; // 商品百分比阀值
	
	private double maxPercentLimit;// 交易商订货量最大百分比
	
	private long oneMaxHoldQty; // 单笔最大委托量
	
	private int minQuantityMove; // 最小变动数量

	private int minSettleMoveQty; // 最小交割单位

	private long minSettleQty; // 最小交割数量     //添加最小交割数量 2011-3-14 by feijl


	//交易保证金算法
	public static final short MARGINALGR_PERCENT = 1;// 百分比
	public static final short MARGINALGR_ABSOLUTE = 2;//绝对值
	//手续费算法
	public static final short FEEALGR_PERCENT = 1;// 百分比
	public static final short FEEALGR_ABSOLUTE = 2;//绝对值	
	//状态
	public static final short STATUS_NORMAL = 0;//正常
	public static final short STATUS_SETTLE = 1;//转入交收
	public static final short STATUS_FORBID = 2;//禁止交易
	//涨跌幅算法
	public static final short SPREADALGR_PERCENT = 1;// 百分比
	public static final short SPREADALGR_ABSOLUTE = 2;//绝对值
	public static final short SPREADALGR_NOTLIMIT = 4;// 不限制
	
	//交易商订货量限制算法
	public static final short FIRMMAXHOLDQTYALGR_PERCENT = 1;// 百分比
	public static final short FIRMMAXHOLDQTYALGR_ABSOLUTE = 2;//绝对值
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public long getBreedID() {
		return breedID;
	}

	public void setBreedID(long breedID) {
		this.breedID = breedID;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public double getContractFactor() {
		return contractFactor;
	}

	public void setContractFactor(double contractFactor) {
		this.contractFactor = contractFactor;
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

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public short getMarginAlgr() {
		return marginAlgr;
	}

	public void setMarginAlgr(short marginAlgr) {
		this.marginAlgr = marginAlgr;
	}

	public double getMarginAssure_B() {
		return marginAssure_B;
	}

	public void setMarginAssure_B(double marginAssure_B) {
		this.marginAssure_B = marginAssure_B;
	}

	public double getMarginAssure_S() {
		return marginAssure_S;
	}

	public void setMarginAssure_S(double marginAssure_S) {
		this.marginAssure_S = marginAssure_S;
	}

	public double getMarginRate_B() {
		return marginRate_B;
	}

	public void setMarginRate_B(double marginRate_B) {
		this.marginRate_B = marginRate_B;
	}

	public double getMarginRate_S() {
		return marginRate_S;
	}

	public void setMarginRate_S(double marginRate_S) {
		this.marginRate_S = marginRate_S;
	}

	public Date getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(Date marketDate) {
		this.marketDate = marketDate;
	}

	public double getMinPriceMove() {
		return minPriceMove;
	}

	public void setMinPriceMove(double minPriceMove) {
		this.minPriceMove = minPriceMove;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public long getSortID() {
		return sortID;
	}

	public void setSortID(long sortID) {
		this.sortID = sortID;
	}

	public short getSpreadAlgr() {
		return spreadAlgr;
	}

	public void setSpreadAlgr(short spreadAlgr) {
		this.spreadAlgr = spreadAlgr;
	}

	public double getSpreadDownLmt() {
		return spreadDownLmt;
	}

	public void setSpreadDownLmt(double spreadDownLmt) {
		this.spreadDownLmt = spreadDownLmt;
	}

	public double getSpreadUpLmt() {
		return spreadUpLmt;
	}

	public void setSpreadUpLmt(double spreadUpLmt) {
		this.spreadUpLmt = spreadUpLmt;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public short getMarginPriceType() {
		return marginPriceType;
	}

	public void setMarginPriceType(short marginPriceType) {
		this.marginPriceType = marginPriceType;
	}

	public short getFirmMaxHoldQtyAlgr() {
		return firmMaxHoldQtyAlgr;
	}

	public void setFirmMaxHoldQtyAlgr(short firmMaxHoldQtyAlgr) {
		this.firmMaxHoldQtyAlgr = firmMaxHoldQtyAlgr;
	}

	public double getMaxPercentLimit() {
		return maxPercentLimit;
	}

	public void setMaxPercentLimit(double maxPercentLimit) {
		this.maxPercentLimit = maxPercentLimit;
	}

	public long getStartPercentQty() {
		return startPercentQty;
	}

	public void setStartPercentQty(long startPercentQty) {
		this.startPercentQty = startPercentQty;
	}

	public long getFirmMaxHoldQty() {
		return firmMaxHoldQty;
	}

	public void setFirmMaxHoldQty(long firmMaxHoldQty) {
		this.firmMaxHoldQty = firmMaxHoldQty;
	}
	
	public long getOneMaxHoldQty() {
		return oneMaxHoldQty;
	}

	public void setOneMaxHoldQty(long oneMaxHoldQty) {
		this.oneMaxHoldQty = oneMaxHoldQty;
	}
	
	public int getMinQuantityMove() {
		return minQuantityMove;
	}

	public void setMinQuantityMove(int minQuantityMove) {
		this.minQuantityMove = minQuantityMove;
	}
	public int getMinSettleMoveQty() {
		return minSettleMoveQty;
	}

	public void setMinSettleMoveQty(int minSettleMoveQty) {
		this.minSettleMoveQty = minSettleMoveQty;
	}

	public long getMinSettleQty() {
		return minSettleQty;
	}

	public void setMinSettleQty(long minSettleQty) {
		this.minSettleQty = minSettleQty;
	}
}

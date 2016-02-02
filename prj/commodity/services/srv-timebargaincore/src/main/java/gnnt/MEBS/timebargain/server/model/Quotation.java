package gnnt.MEBS.timebargain.server.model;

import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.engine.CommodityOrder;
import gnnt.MEBS.timebargain.server.engine.PriceOrder;

import java.io.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 行情对象.
 *
 * <p><a href="Quotation.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.9
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 * @modify 添加Clone方法，解决行情锁取行情线程有时候获取不到锁的问题  mod by zhangjian 2013-12-26
 */
public class Quotation implements Serializable,Cloneable{

	private transient final Log logger = LogFactory.getLog(Quotation.class);
	
	private static final long serialVersionUID = 3690197650654049813L;
	
	// 修改Date类型为Timestamp,让它精确到毫秒,解决行情比较时在同一秒之内不能比较的问题(涉及到相关的代码都已改) 2011-3-3 by feijl
	/**
	 * 放弃根据上一次更新时间判断是否同步行情到db，
	 * 原因：即使精确到毫秒级别依然存在迸发问题，导致行情不能及时更新
	 * modify by zhangjian 2011-08-11
	 */
	public Timestamp updateTime;

	public String commodityID; // 商品代码

	public Double yesterBalancePrice = 0.0; //昨结算价
	
	public Double closePrice = 0.0; // 昨收盘价
	
	public Double openPrice = 0.0; //今开市价
	
	public Double highPrice = 0.0; //最高价
	
	public Double lowPrice = 0.0; //最低价
	
	public Double curPrice = 0.0; //最新价
	
	public Long curAmount = 0l; //现量
	
	public Long openAmount = 0l; //开仓
	
	public Long buyOpenAmount = 0l; //买开仓
	
	public Long sellOpenAmount = 0l; //卖开仓
	
	public Long closeAmount = 0l; //平仓
	
	public Long buyCloseAmount = 0l; //买平仓
	
	public Long sellCloseAmount = 0l; //卖平仓
	
	public Long reserveCount = 0l; //订货量
	
	public Long reserveChange = 0l; //仓差
	
	public Double price = 0.0; //结算价
	
	public Double totalMoney = 0.0; //总成交额
	
	public Long totalAmount = 0l; //总成交量

	public Double spread = 0.0; //涨跌

	public Long outAmount = 0l; //外盘

	public Long inAmount = 0l; //内盘

	public Long tradeCue = 0l; //交易提示

	public Long no = 0l; //计数字段
	
	public Timestamp createTime = null; // 2009-09-21 增加更新行情时间，如果取db的时间则设置成null，主要解决将集合竞价行情时间更新成开市时间
	
	/**
	 * 是否更新行情字段，用来判断行情是否需要更新到数据库中，
	 * 初始值为false，当有成交，或者委托单影响到买卖五挡的时候此字段变为true，更新行情到db中
	 */
	public boolean isUpdate =false; 
	
	public double[] buy = new double[5];
	public double[] sell = new double[5];
	public long[] buyqty = new long[5];
	public long[] sellqty = new long[5];
	
	public void clearTop5(){
		for(int i=0;i<5;i++){
			buy[i] = 0;
			sell[i] = 0;
			buyqty[i] = 0;
			sellqty[i] = 0;
		}
	}
	
	/**
	 * “入委托、撤单”引起行情买卖队列前5发生变化时，应调用本方法，进行同步更新买卖队列前5
	 * Add by Tangzy 2010-07-08
	 * @param qt
	 * @param co
	 * 采用了Clone方法来解决原来取行情数据线程有时候无法获得锁的问题，不需要再进行同步处理了 mod by zhangjian 2013-12-26
	 */
	@Deprecated
	public void updateTop5Synchronized(CommodityOrder co){
		synchronized(this){
			updateTop5(co);
		}
	}
	
	/**
	 * “成交”引起行情买卖队列前5发生变化时，应调用本方法
	 * Add by Tangzy 2010-07-08
	 * @param qt
	 * @param co
	 */
	public void updateTop5(CommodityOrder co){
		Iterator<PriceOrder> itr = co.buyQueue.iterator();
		PriceOrder po = null;
		clearTop5();
		for(int i=0;itr.hasNext() && i<5;i++){
			po = itr.next();
			this.buy[i] = po.price;
			this.buyqty[i] = po.quantity;
		}
		itr = co.sellQueue.iterator();
		for(int i=0;itr.hasNext() && i<5;i++){
			po = itr.next();
			this.sell[i] = po.price;
			this.sellqty[i] = po.quantity;
		}
		/**
		 * 行情时间更改为服务器+数据库时间差
		 */
		this.createTime=new Timestamp(System.currentTimeMillis()+ServerInit.diffTime);
		//this.updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		this.isUpdate=true;//更新行情到db 中 add by zhangjian 2011-08-11
		
		
	}
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public Long getCloseAmount() {
		return closeAmount;
	}

	public void setCloseAmount(Long closeAmount) {
		this.closeAmount = closeAmount;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public Long getCurAmount() {
		return curAmount;
	}

	public void setCurAmount(Long curAmount) {
		this.curAmount = curAmount;
	}

	public Double getCurPrice() {
		return curPrice;
	}

	public void setCurPrice(Double curPrice) {
		this.curPrice = curPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Long getInAmount() {
		return inAmount;
	}

	public void setInAmount(Long inAmount) {
		this.inAmount = inAmount;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Long getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(Long openAmount) {
		this.openAmount = openAmount;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Long getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Long outAmount) {
		this.outAmount = outAmount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getReserveChange() {
		return reserveChange;
	}

	public void setReserveChange(Long reserveChange) {
		this.reserveChange = reserveChange;
	}

	public Long getReserveCount() {
		return reserveCount;
	}

	public void setReserveCount(Long reserveCount) {
		this.reserveCount = reserveCount;
	}

	public Double getSpread() {
		return spread;
	}

	public void setSpread(Double spread) {
		this.spread = spread;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Long getTradeCue() {
		return tradeCue;
	}

	public void setTradeCue(Long tradeCue) {
		this.tradeCue = tradeCue;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public Double getYesterBalancePrice() {
		return yesterBalancePrice;
	}

	public void setYesterBalancePrice(Double yesterBalancePrice) {
		this.yesterBalancePrice = yesterBalancePrice;
	}

	public Long getBuyCloseAmount() {
		return buyCloseAmount;
	}

	public void setBuyCloseAmount(Long buyCloseAmount) {
		this.buyCloseAmount = buyCloseAmount;
	}

	public Long getBuyOpenAmount() {
		return buyOpenAmount;
	}

	public void setBuyOpenAmount(Long buyOpenAmount) {
		this.buyOpenAmount = buyOpenAmount;
	}

	public Long getSellCloseAmount() {
		return sellCloseAmount;
	}

	public void setSellCloseAmount(Long sellCloseAmount) {
		this.sellCloseAmount = sellCloseAmount;
	}

	public Long getSellOpenAmount() {
		return sellOpenAmount;
	}

	public void setSellOpenAmount(Long sellOpenAmount) {
		this.sellOpenAmount = sellOpenAmount;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * 简单克隆，仅“商品id，更新时间，买1卖1”
	 * @return
	 */
	public Quotation cloneSimple(){
		Quotation q = new Quotation();
		q.buy[0] = this.buy[0];
		q.buyqty[0] = this.buyqty[0];
		q.sell[0] = this.sell[0];
		q.sellqty[0] = this.sellqty[0];
		q.commodityID = this.commodityID;
		q.updateTime = (Timestamp)this.updateTime.clone(); 
		return q;
	}
	
	@Override
	public Object clone() {  
		Quotation o = null;  
        try {  
            o = (Quotation) super.clone();  
            o.buy=this.buy.clone();
            o.sell=this.sell.clone();
            o.buyqty=this.buyqty.clone();
            o.sellqty=this.sellqty.clone();
        } catch (CloneNotSupportedException e) {  
            logger.error("clone error...");
        }  
        return o;  
    }
}

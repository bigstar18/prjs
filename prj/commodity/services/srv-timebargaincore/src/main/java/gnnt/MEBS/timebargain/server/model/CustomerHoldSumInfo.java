package gnnt.MEBS.timebargain.server.model;

import java.io.*;

/**
 * 客户持仓合计对象.
 *
 * <p><a href="CustomerHoldSumInfo.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.6
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class CustomerHoldSumInfo implements Serializable {
    private static final long serialVersionUID = 4690197650654049821L;
    /**
	 * 商品ID
	 */
	public String commodityID;
	
	/**
	 * 摊位号
	 */
	public String customerID;
	
	/**
	 * 买进订货量
	 */
	public long bHoldQty; 
	
	/**
	 * 卖出订货量
	 */
	public long sHoldQty;
	
	/**
	 * 可交收买量
	 */
	public long bCanSettleHoldQty;
	
	/**
	 * 可交收卖量
	 */
	public long sCanSettleHoldQty;
	
	/**
	 * 买平均价
	 */
	public double bPrice;
	
	/**
	 * 卖平均价
	 */
	public double sPrice;
	
	/**
	 * 浮动盈亏
	 */
	public double floatPL;
	
	/**
	 * 保证金
	 */
	public double holdMargin;
}

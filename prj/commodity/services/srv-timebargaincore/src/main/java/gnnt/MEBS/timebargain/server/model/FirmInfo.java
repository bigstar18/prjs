package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Vector;

/**
 * 交易商对象.
 *
 * <p><a href="FirmInfo.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.6
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class FirmInfo implements Serializable {
    private static final long serialVersionUID = 4690197650654049821L;
    /**
	 * 交易商ID
	 */
	public String firmID;
	
	/**
	 * 交易商名称
	 */
	public String name;
	
	/**
	 * 交易商状态
	 * 
	 * 0:正常,1:禁止交易,2:退市
	 */
	public short status;
	
	/**
	 * 交易商资金
	 */
	public double balance;
	
	/**
	 * 当前开仓量
	 */
	public long cur_Open;
	
	/**
	 * 核准最大订货量
	 */
	public long maxHoldQty;
	
	/**
	 * 当前允许最大订货量
	 */
	public long cur_MaxOpen;
	
	/**
	 * 追加资金
	 */
	public double shouldAddFund;

	/**
	 *	交易代码，存放String对象 
	 */
	public Vector vTradeCode = new Vector();
}

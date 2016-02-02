package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 交易商对象.
 *
 * <p><a href="Firm.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Firm implements Serializable {
    private static final long serialVersionUID = 4690197650654049821L;
    private String firmID;//交易商ID
    private short status;//交易商状态
    private long maxHoldQty;//交易商的最大持仓量，是指总的
    private double minClearDeposit;//交易商最低结算准备金
    private double maxOverdraft;//交易商最大透支额度
    private String tariffID;//交易商手续费套餐ID

    private Map firmMarginMap;//交易商特殊保证金Map
    private Map firmFeeMap;//交易商特殊手续费Map
    
    //private List traderList;//该交易商下有几个交易员;
    
    //交易商状态
    public static final short STATUS_NORMAL = 0;// 正常
	public static final short STATUS_FORBID = 1;// 禁止交易
	public static final short STATUS_EXIT = 2;//退市
    
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public Map getFirmFeeMap() {
		return firmFeeMap;
	}

	public void setFirmFeeMap(Map firmFeeMap) {
		this.firmFeeMap = firmFeeMap;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public Map getFirmMarginMap() {
		return firmMarginMap;
	}

	public void setFirmMarginMap(Map firmMarginMap) {
		this.firmMarginMap = firmMarginMap;
	}

	public long getMaxHoldQty() {
		return maxHoldQty;
	}

	public void setMaxHoldQty(long maxHoldQty) {
		this.maxHoldQty = maxHoldQty;
	}

	public double getMaxOverdraft() {
		return maxOverdraft;
	}

	public void setMaxOverdraft(double maxOverdraft) {
		this.maxOverdraft = maxOverdraft;
	}

	public double getMinClearDeposit() {
		return minClearDeposit;
	}

	public void setMinClearDeposit(double minClearDeposit) {
		this.minClearDeposit = minClearDeposit;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
	public String getTariffID() {
		return tariffID;
	}

	public void setTariffID(String tariffID) {
		this.tariffID = tariffID;
	}


}

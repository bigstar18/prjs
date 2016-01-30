
package gnnt.MEBS.bill.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * <P>类说明：冻结仓单表
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-4-26下午01:06:39|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class FrozenStockPO extends Clone implements Serializable {
	/** 序列编号 */
	private static final long serialVersionUID = 5977107191707281657L;

	/** 冻结仓单号 */
	private long frozenStockID;

	/** 仓单编号 */
	private String stockID;

	/** 模块编号 */
	private int moduleID;

	/**
	 * 状态<br/>
	 * 0 仓单冻结状态 1 仓单释放状态
	 */
	private int status;

	/** 记录创建时间 */
	private Date createTime;

	/** 仓单释放时间 */
	private Date releaseTime;

	/**
	 * 
	 * 冻结仓单号
	 * <br/><br/>
	 * @return long
	 */
	public long getFrozenStockID() {
		return frozenStockID;
	}

	/**
	 * 
	 * 冻结仓单号
	 * <br/><br/>
	 * @param frozenStockID
	 */
	public void setFrozenStockID(long frozenStockID) {
		this.frozenStockID = frozenStockID;
	}

	/**
	 * 
	 * 仓单编号
	 * <br/><br/>
	 * @return String
	 */
	public String getStockID() {
		return stockID;
	}

	/**
	 * 
	 * 仓单编号
	 * <br/><br/>
	 * @param stockID
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	/**
	 * 
	 * 模块编号
	 * <br/><br/>
	 * @return int
	 */
	public int getModuleID() {
		return moduleID;
	}

	/**
	 * 
	 * 模块编号
	 * <br/><br/>
	 * @param moduleID
	 */
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}

	/**
	 * 
	 * 状态<br/>
	 * 0 仓单冻结状态 1 仓单释放状态
	 * <br/><br/>
	 * @return int
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 
	 * 状态<br/>
	 * 0 仓单冻结状态 1 仓单释放状态
	 * <br/><br/>
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 
	 * 记录创建时间
	 * <br/><br/>
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * 记录创建时间
	 * <br/><br/>
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * 仓单释放时间
	 * <br/><br/>
	 * @return Date
	 */
	public Date getReleaseTime() {
		return releaseTime;
	}

	/**
	 * 
	 * 仓单释放时间
	 * <br/><br/>
	 * @param releaseTime
	 */
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	
}


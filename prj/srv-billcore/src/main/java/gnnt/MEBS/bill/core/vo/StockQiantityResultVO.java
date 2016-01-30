
package gnnt.MEBS.bill.core.vo;
/**
 * <P>类说明：通过仓单编号集合查询仓单数量时的返回结果
 * <br/>
 * <br/>
 * </p>
 * 修改记录:<br/>
 * <ul>
 * <li> 创建类                    |2013-4-19上午09:12:45|金网安泰 </li>
 * </ul>
 * @author liuzx
 */
public class StockQiantityResultVO extends ResultVO {
	/** 序列化编号 */
	private static final long serialVersionUID = 6364908498625245935L;

	/** 总体数量 */
	private double quantity;

	/**
	 * 
	 * 总体数量<br/>
	 * <br/>
	 *
	 * @return double
	 */
	public double getQuantity() {
	
		return quantity;
	}

	/**
	 * 
	 * 总体数量<br/>
	 * <br/>
	 *
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
	
		this.quantity = quantity;
	}
}


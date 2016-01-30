package gnnt.MEBS.bill.core.vo;

/**
 * 交收VO
 * 
 * @author xuejt
 * 
 */
public class DeliveryVO extends ResultVO {
	
	private static final long serialVersionUID = -5539369788199055647L;
	
	private double quantity;

	/**
	 * 交收数量
	 * 
	 * @return
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * 交收数量
	 * 
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}

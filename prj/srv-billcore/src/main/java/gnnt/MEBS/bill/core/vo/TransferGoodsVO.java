package gnnt.MEBS.bill.core.vo;


/**
 * 转入仓单返回包
 * 
 * @author xuejt
 * 
 */
public class TransferGoodsVO extends ResultVO {
	private static final long serialVersionUID = -834115733146395050L;
	
	private int moduleid;
	
	private String tradeNO;
	
	private double quantity;

	/**
	 * 仓单对应的货物数量
	 * 
	 * @return
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * 仓单对应的货物数量
	 * 
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return 模块号
	 */
	public int getModuleid() {
		return moduleid;
	}

	/**
	 * @param 模块号
	 */
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}

	/**
	 * @return 合同号
	 */
	public String getTradeNO() {
		return tradeNO;
	}

	/**
	 * @param 合同号
	 */
	public void setTradeNO(String tradeNO) {
		this.tradeNO = tradeNO;
	}

	
}

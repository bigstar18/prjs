package gnnt.MEBS.bill.core.vo;

/**
 * 添加仓单处理结果
 * 
 * @author xuejt
 * 
 */
public class AddStockResultVO extends ResultVO {

	private static final long serialVersionUID = 183417122999747317L;
	/**
	 * 仓单号
	 */
	private String stockID = "-1";

	/**
	 * 仓单号
	 */
	public String getStockID() {
		return stockID;
	}

	/**
	 * 仓单号
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
}

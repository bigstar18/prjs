package gnnt.MEBS.bill.core.vo;

/**
 * 融资申请返回对象
 * 
 * @author xuejt
 * 
 */
public class FinancingApplyVO extends ResultVO {

	private static final long serialVersionUID = 8577690642779533367L;

	/**
	 * 融资仓单编号
	 */
	private long financingStockID;

	/**
	 * @return 融资仓单编号
	 */
	public long getFinancingStockID() {
		return financingStockID;
	}

	/**
	 * @param 融资仓单编号
	 */
	public void setFinancingStockID(long financingStockID) {
		this.financingStockID = financingStockID;
	}

}

package gnnt.MEBS.bill.core.vo;

/**
 * 仓单业务枚举类
 * 
 * @author xuejt
 * 
 */
public enum StockOperation {
	/**
	 * 拆单
	 */
	DISMANTLE("拆单", 0),
	/**
	 * 融资
	 */
	FINANCING("融资", 1),
	/**
	 * 卖仓单
	 */
	SELL("卖仓单", 2),
	/**
	 * 交收
	 */
	DELIVERY("交收", 3),
	/**
	 * 冻结
	 */
	FROZEN("冻结",4);
	// 成员变量
	private String name;
	private int operation;

	// 构造方法
	private StockOperation(String name, int operation) {
		this.name = name;
		this.operation = operation;
	}

	/**
	 * 通过业务代码获取业务名称
	 * 
	 * @param operation
	 *            业务代码
	 * @return
	 */
	public static String getName(int operation) {
		for (StockOperation stockOperation : StockOperation.values()) {
			if (stockOperation.getOperation() == operation) {
				return stockOperation.name;
			}
		}
		return null;
	}

	/**
	 * 业务码
	 * 
	 * @return
	 */
	public int getOperation() {
		return this.operation;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.operation + "_" + this.name;
	}
}

package gnnt.trade.bank.vo.bankdz.pf.sent;

import java.io.Serializable;

public class FundsMarg implements Serializable{
	private static final long serialVersionUID=1L;
	/**
	 * 交易商代码
	 */
	public String FIRM_ID;

	/**
	 * 类型 1可用余额  2冻结资金  31手续费
	 */
    public String FUNDSTYPE;

    /**
     * 余额
     */
    public double FUNDS;
    /**本类信息*/
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	String str = "\n";
    	sb.append("交易商代码(FIRM_ID)["+FIRM_ID+"]"+str);
    	sb.append("类型(FUNDSTYPE)["+FUNDSTYPE+"]"+str);
    	sb.append("余额(FUNDS)["+FUNDS+"]"+str);
    	return sb.toString();
    }
}

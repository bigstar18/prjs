package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;
import java.util.Vector;

/**中行分账户余额信息类*/
public class ZHBalaceValue  implements Serializable {
	/**银行代码*/
	public String bankId;
	/**交易日期*/
	public String tradeDate;
	
	/**分账户余额异常信息*/
	private Vector<BalaceErrorValue> v_bev ;

	

	public Vector<BalaceErrorValue> getV_bev() {
		if (this.v_bev == null) {
			this.v_bev = new Vector<BalaceErrorValue>();
		}
		return v_bev;
	}



	public void setV_bev(Vector<BalaceErrorValue> vBev) {
		v_bev = vBev;
	}



	public  void pubBev(BalaceErrorValue bev){
		if (bev == null) 
			this.v_bev = new Vector<BalaceErrorValue>();
		v_bev.add(bev);
	}
	
	
	
	
}

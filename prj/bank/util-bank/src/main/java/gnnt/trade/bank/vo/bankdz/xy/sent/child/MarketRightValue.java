package gnnt.trade.bank.vo.bankdz.xy.sent.child;
import java.io.Serializable;
import java.math.BigDecimal;
/**市场清算类*/
public class MarketRightValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**自有金额(包括手续费、利息等)*/
	public BigDecimal maketMoney;
	/**银行间轧差*/
	public double bankErrorMoney;
	/**融资轧差*/
	public double maketErrorMoney;
	/**币别 001：人民币 013：港币 014：美元*/
	public String currency="001";
	/**钞汇标识（0：钞 1：汇）*/
	public int type=0;
	/**返回对象类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("maketMoney:"+this.maketMoney+str);
		sb.append("bankErrorMoney:"+this.bankErrorMoney+str);
		sb.append("maketErrorMoney:"+this.maketErrorMoney+str);
		sb.append("currency:"+this.currency+str);
		sb.append("type:"+this.type+str);
		sb.append(str);
		return sb.toString();
	}
}

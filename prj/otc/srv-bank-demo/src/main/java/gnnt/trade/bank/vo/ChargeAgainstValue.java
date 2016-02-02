package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.sql.Timestamp;
/**冲正流水类*/
public class ChargeAgainstValue implements Serializable {
	private static final long serialVersionUID = 1L;
	public String bankID;
	/**被冲正流水市场流水号*/
	public long actionID;
	/**冲正流水市场流水号*/
	public long actionIDCA;
	/**被冲正流水银行流水号*/
	public String funID;
	/**冲正流水银行流水号*/
	public String funIDCA;
	/**冲正流水银行时间*/
	public Timestamp bankTime;
	/**冲正流水市场时间*/
	public Timestamp createtime;
	/**备注*/
	public String note;
	public String toString(){
		String str = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("bankID["+this.bankID+"]"+str);
		sb.append("actionID["+this.actionID+"]"+str);
		sb.append("actionIDCA["+this.actionIDCA+"]"+str);
		sb.append("funID["+this.funID+"]"+str);
		sb.append("funIDCA["+this.funIDCA+"]"+str);
		sb.append("bankTime["+this.bankTime+"]"+str);
		sb.append("createtime["+this.createtime+"]"+str);
		sb.append(str);
		return sb.toString();
	}
}

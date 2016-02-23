package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**交易商ID */
	public String firmid;
	/**银行编号 */
	public String bankid;
	/**对应的Key值 */
	public String key;
	/**对应的value值 */
	public String value;
	
	public String toString(){
		String sep="\n";
		StringBuilder sb = new StringBuilder();
		sb.append("**"+this.getClass().getName()+"**");
		sb.append("firmid:"+firmid+sep);
		sb.append("bankid:"+bankid+sep);
		sb.append("key:"+key+sep);
		sb.append("value:"+value+sep);
		return sb.toString();
	}
}

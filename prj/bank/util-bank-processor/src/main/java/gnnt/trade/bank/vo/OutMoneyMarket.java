package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * �г������������
 * @author liuzx
 */
public class OutMoneyMarket implements Serializable{
	private static final long serialVersionUID = 1L;
	/**���д���*/
	public String bankID;
	/**�����˺Ŵ���*/
	public String firmID;
	/**����Ա*/
	public String trader;
	/**�����˺ź����а󶨺�*/
	public String contact;
	/**�����˺������˺�*/
	public String account;
	/**�������п�����*/
	public String accountPwd;
	/**������*/
	public double money;
	/**������*/
	public int type=0;
	/**��ע��Ϣ*/
	public String remark;
	/**
	 * ������Ϣ
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("bankID:"+bankID+str);
		sb.append("firmID:"+firmID+str);
		sb.append("trader:"+trader+str);
		sb.append("contact:"+contact+str);
		sb.append("account:"+account+str);
		sb.append("money:"+money+str);
		sb.append("type:"+type+str);
		sb.append("remark:"+remark+str);
		return sb.toString();
	}
}

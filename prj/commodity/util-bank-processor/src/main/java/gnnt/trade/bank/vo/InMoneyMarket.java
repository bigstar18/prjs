package gnnt.trade.bank.vo;

import java.io.Serializable;

public class InMoneyMarket implements Serializable{
	private static final long serialVersionUID = 1L;
	/** �����˺Ŵ��� */
	public String firmID;
	/** ����Ա */
	public String trader;
	/** �����˺ź����а󶨺� */
	public String contact;
	/** ���д��� */
	public String bankID;
	/** �����˺� */
	public String account;
	/** ���п����� */
	public String accountPwd;
	/** ��� */
	public double money;
	/** ��ע */
	public String remark;
	/**
	 * ������Ϣ
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("firmID:"+firmID+str);
		sb.append("trader:"+trader+str);
		sb.append("contact:"+contact+str);
		sb.append("bankID:"+bankID+str);
		sb.append("account:"+account+str);
		sb.append("accountPwd:"+accountPwd+str);
		sb.append("money:"+money+str);
		sb.append("remark:"+remark+str);
		return sb.toString();
	}
}

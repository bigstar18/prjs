package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 操作结果 
 */
public class ReturnValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**操作结果 0：成功 ；<0：失败 5:处理中*/
	public long result = 0;
	
	/**市场流水ID*/
	public long actionId;
	
	/**银行端返回的流水号*/
	public String funID;
	
	/**银行处理时间*/
	public String bankTime;
	
	/**流水状态*/
	public int type;
	
	/**备注*/
	public String remark;

	/**客户内部账号*/
	public String account1;
//------------------------------------------------------
	
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("result:" + this.result+sep);
		sb.append("funID:" + this.funID+sep);
		sb.append("remark:" + this.remark+sep);
		sb.append("bankTime:" + this.bankTime+sep);
		sb.append("actionId:" + this.actionId+sep);
		sb.append("type:" + this.type+sep);
		sb.append("account1:"+this.account1+sep);
		sb.append(sep);
		return sb.toString();
	}
}

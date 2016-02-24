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
		sb.append(sep);
		return sb.toString();
	}
	public String toStringLog()
	{
		StringBuilder sb = new StringBuilder();
		if(actionId != 0){
			sb.append("市场流水号["+actionId+"]");
		}
		if(funID != null && funID.trim().length()>0){
			sb.append("银行流水号["+funID.trim()+"]");
		}
		sb.append("返回码["+result+"]");
		if(remark != null && remark.trim().length()>0){
			sb.append("返回信息["+remark.toString()+"]");
		}
		return sb.toString();
	}
}

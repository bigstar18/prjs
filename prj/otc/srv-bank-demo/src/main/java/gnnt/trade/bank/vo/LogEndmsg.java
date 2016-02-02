package gnnt.trade.bank.vo;
/**
 * 银行接口返回信息对象
 */
public class LogEndmsg {
	/**市场流水号*/
	public long actionID;
	/**银行流水号*/
	public String funID;
	/**返回码*/
	public String code;
	/**返回信息*/
	public String remark;
	/**备注信息*/
	public String note;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(actionID != 0){
			sb.append("市场流水号["+actionID+"]");
		}
		if(funID != null && funID.trim().length()>0){
			sb.append("银行流水号["+funID.trim()+"]");
		}
		if(code != null && code.trim().length()>0){
			sb.append("返回码["+code.trim()+"]");
		}
		if(remark != null && remark.trim().length()>0){
			sb.append("返回信息["+remark.toString()+"]");
		}
		if(note != null && note.trim().length()>0){
			sb.append("备注信息["+note.trim()+"]");
		}
		return sb.toString();
	}
}

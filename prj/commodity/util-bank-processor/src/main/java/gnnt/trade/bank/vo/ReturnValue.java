package gnnt.trade.bank.vo;

import java.io.Serializable;

public class ReturnValue implements Serializable {
	private static final long serialVersionUID = 1L;
	public long result = -9999L;
	public long actionId;
	public String funID;
	public String bankTime;
	public int type;
	public String remark;
	public String payChannel;
	public String netAddress;
	public Object[] msg;

	// /**操作结果 0：成功 ；<0：失败 5:处理中*/
	// public long result = 0;
	//
	// /**市场流水ID*/
	// public long actionId;
	//
	// /**银行端返回的流水号*/
	// public String funID;
	//
	// /**银行处理时间*/
	// public String bankTime;
	//
	// /**流水状态*/
	// public int type;
	//
	// /**备注*/
	// public String remark;

	/** 客户内部账号 */
	public String account1;

	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**" + getClass().getName() + "**" + sep);
		sb.append("result:" + this.result + sep);
		sb.append("funID:" + this.funID + sep);
		sb.append("remark:" + this.remark + sep);
		sb.append("bankTime:" + this.bankTime + sep);
		sb.append("actionId:" + this.actionId + sep);
		sb.append("type:" + this.type + sep);
		sb.append(sep);
		return sb.toString();
	}

	public String toStringLog() {
		StringBuilder sb = new StringBuilder();
		if (this.actionId != 0L) {
			sb.append("市场流水号[" + this.actionId + "]");
		}
		if ((this.funID != null) && (this.funID.trim().length() > 0)) {
			sb.append("银行流水号[" + this.funID.trim() + "]");
		}
		sb.append("返回码[" + this.result + "]");
		if ((this.remark != null) && (this.remark.trim().length() > 0)) {
			sb.append("返回信息[" + this.remark.toString() + "]");
		}
		return sb.toString();
	}
}
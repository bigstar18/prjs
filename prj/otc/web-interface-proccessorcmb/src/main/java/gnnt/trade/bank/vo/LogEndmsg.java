package gnnt.trade.bank.vo;

public class LogEndmsg
{
  public long actionID;
  public String funID;
  public String code;
  public String remark;
  public String note;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    if (this.actionID != 0L) {
      sb.append("市场流水号[" + this.actionID + "]");
    }
    if ((this.funID != null) && (this.funID.trim().length() > 0)) {
      sb.append("银行流水号[" + this.funID.trim() + "]");
    }
    if ((this.code != null) && (this.code.trim().length() > 0)) {
      sb.append("返回码[" + this.code.trim() + "]");
    }
    if ((this.remark != null) && (this.remark.trim().length() > 0)) {
      sb.append("返回信息[" + this.remark.toString() + "]");
    }
    if ((this.note != null) && (this.note.trim().length() > 0)) {
      sb.append("备注信息[" + this.note.trim() + "]");
    }
    return sb.toString();
  }
}

package gnnt.trade.bank.vo;

import gnnt.bank.platform.util.Tool;
import java.io.Serializable;
import java.util.Date;

public class InterfaceLog
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long logID;
  public String bankID;
  public int launcher;
  public int type;
  public String contact;
  public String account;
  public String beginMsg;
  public String endMsg;
  public int result;
  public Date createtime;
  public String firmID;
  public String bankName;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append(str + "---" + getClass().getName() + "---" + str);
    sb.append("logID[" + this.logID + "]" + str);
    sb.append("bankID[" + this.bankID + "]" + str);
    sb.append("launcher[" + this.launcher + "]" + str);
    sb.append("type[" + this.type + "]" + str);
    sb.append("firmid[" + this.firmID + "]" + str);
    sb.append("contact[" + this.contact + "]" + str);
    sb.append("account[" + this.account + "]" + str);
    sb.append("beginMsg[" + this.beginMsg + "]" + str);
    sb.append("endMsg[" + this.endMsg + "]" + str);
    sb.append("result[" + this.result + "]" + str);
    sb.append("createtime[" + (this.createtime == null ? "为空" : Tool.fmtTime(this.createtime)) + "]" + str);
    sb.append("firmID[" + this.firmID + "]" + str);
    sb.append("bankName[" + this.bankName + "]" + str);
    return sb.toString();
  }
}

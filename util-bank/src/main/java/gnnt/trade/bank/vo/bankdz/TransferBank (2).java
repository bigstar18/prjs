package gnnt.trade.bank.vo.bankdz;

public class TransferBank
{
  public String bankID;
  public String bankName;
  public String Id = "";

  public String Name = "";
  public int St;
  public int accountType;
  public String Pwd = "";

  public String OpFlg = "Y";

  public String RegDt = "";

  public String VldDt = "";
  public String type;
  public String status;
  public String bankNum;

  public String getBankID()
  {
    return this.bankID;
  }
  public void setBankID(String bankID) {
    this.bankID = bankID;
  }
  public String getBankName() {
    return this.bankName;
  }
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }
  public String getId() {
    return this.Id;
  }
  public void setId(String id) {
    this.Id = id;
  }
  public String getName() {
    return this.Name;
  }
  public void setName(String name) {
    this.Name = name;
  }
  public int getSt() {
    return this.St;
  }
  public void setSt(int st) {
    this.St = st;
  }
  public int getAccountType() {
    return this.accountType;
  }
  public void setAccountType(int accountType) {
    this.accountType = accountType;
  }
  public String getPwd() {
    return this.Pwd;
  }
  public void setPwd(String pwd) {
    this.Pwd = pwd;
  }
  public String getOpFlg() {
    return this.OpFlg;
  }
  public void setOpFlg(String opFlg) {
    this.OpFlg = opFlg;
  }
  public String getRegDt() {
    return this.RegDt;
  }
  public void setRegDt(String regDt) {
    this.RegDt = regDt;
  }
  public String getVldDt() {
    return this.VldDt;
  }
  public void setVldDt(String vldDt) {
    this.VldDt = vldDt;
  }
  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getBankNum() {
    return this.bankNum;
  }
  public void setBankNum(String bankNum) {
    this.bankNum = bankNum;
  }
}
package gnnt.bank.platform.vo;

import java.io.Serializable;

public class CheckMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String functionID;
  public String fName;
  public String value;

  public String getBankID()
  {
    return this.bankID;
  }
  public void setBankID(String bankID) {
    this.bankID = bankID;
  }
  public String getFunctionID() {
    return this.functionID;
  }
  public void setFunctionID(String functionID) {
    this.functionID = functionID;
  }
  public String getfName() {
    return this.fName;
  }
  public void setfName(String fName) {
    this.fName = fName;
  }
  public String getValue() {
    return this.value;
  }
  public void setValue(String value) {
    this.value = value;
  }
}
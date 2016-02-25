package gnnt.bank.platform.vo;

import java.io.Serializable;

public class RequestMsg
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String bankID;
  private String methodName;
  private Object[] params;
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }
  
  public String getMethodName()
  {
    return this.methodName;
  }
  
  public void setMethodName(String methodName)
  {
    this.methodName = methodName;
  }
  
  public Object[] getParams()
  {
    return this.params;
  }
  
  public void setParams(Object[] params)
  {
    this.params = params;
  }
  
  public String toString()
  {
    String result = "银行编号[" + this.bankID + "]";
    result = result + "方法:" + this.methodName + "(";
    if ((this.params == null) || (this.params.length <= 0))
    {
      result = result + ")";
    }
    else
    {
      for (Object obj : this.params) {
        result = result + obj.getClass() + ", ";
      }
      result = result.substring(0, result.length() - 1) + ")";
    }
    return result;
  }
}

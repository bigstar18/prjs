package gnnt.MEBS.firm.model;

public class ReturnType
{
  private int State;
  private String MsgID;
  private String MsgState;
  private int Reserve;
  private String SendResult;
  
  public int getState()
  {
    return this.State;
  }
  
  public void setState(int state)
  {
    this.State = state;
  }
  
  public String getMsgID()
  {
    return this.MsgID;
  }
  
  public void setMsgID(String msgID)
  {
    this.MsgID = msgID;
  }
  
  public String getMsgState()
  {
    return this.MsgState;
  }
  
  public void setMsgState(String msgState)
  {
    this.MsgState = msgState;
  }
  
  public int getReserve()
  {
    return this.Reserve;
  }
  
  public void setReserve(int reserve)
  {
    this.Reserve = reserve;
  }
  
  public String getSendResult()
  {
    return this.SendResult;
  }
  
  public void setSendResult(String sendResult)
  {
    this.SendResult = sendResult;
  }
}

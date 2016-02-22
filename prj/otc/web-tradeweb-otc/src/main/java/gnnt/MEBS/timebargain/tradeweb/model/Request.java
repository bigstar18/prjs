package gnnt.MEBS.timebargain.tradeweb.model;

import java.nio.ByteBuffer;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class Request
{
  private String userID;
  private long sessionID;
  public static final short C_D_Q = 1;
  public static final short SYS_TIME_QUERY = 2;
  public static final short M_C_D_Q = 3;
  public static final short DEFAULT_Q = 99;
  private short cmd;
  
  protected void setCMD(short cmd)
  {
    this.cmd = cmd;
  }
  
  public short getCMD()
  {
    return this.cmd;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public long getSessionID()
  {
    return this.sessionID;
  }
  
  public void setSessionID(long sessionID)
  {
    this.sessionID = sessionID;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public static Request getRequest(ByteBuffer buffer)
    throws Exception
  {
    Request requestVO = null;
    Short len = Short.valueOf((short)0);
    byte[] dst = (byte[])null;
    short cmd = buffer.getShort();
    switch (cmd)
    {
    case 1: 
      requestVO = new RequestCDQ();
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      requestVO.setUserID(new String(dst, "UTF-8"));
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      ((RequestCDQ)requestVO).setCommodityID(new String(dst, 
        "UTF-8"));
      requestVO.setSessionID(buffer.getLong());
      
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      ((RequestCDQ)requestVO).setAgencyNO(new String(dst, 
        "UTF-8"));
      
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      ((RequestCDQ)requestVO).setPhonePWD(new String(dst, 
        "UTF-8"));
      break;
    case 2: 
      requestVO = new RequestSTQ();
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      requestVO.setUserID(new String(dst, "UTF-8"));
      
      ((RequestSTQ)requestVO).setLastID(buffer.getLong());
      ((RequestSTQ)requestVO).setTradeCount(buffer.getLong());
      ((RequestSTQ)requestVO).setSessionID(buffer.getLong());
      ((RequestSTQ)requestVO).setCurLogon(buffer.get());
      
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      ((RequestSTQ)requestVO).setAgencyNO(new String(dst, 
        "UTF-8"));
      
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      ((RequestSTQ)requestVO).setPhonePWD(new String(dst, 
        "UTF-8"));
      break;
    case 3: 
      requestVO = new RequestMCDQ();
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      requestVO.setUserID(new String(dst, "UTF-8"));
      len = Short.valueOf(buffer.getShort());
      dst = new byte[len.shortValue()];
      buffer.get(dst);
      ((RequestMCDQ)requestVO).setCommodityID(new String(dst, 
        "UTF-8"));
      requestVO.setSessionID(buffer.getLong());
    }
    return requestVO;
  }
}

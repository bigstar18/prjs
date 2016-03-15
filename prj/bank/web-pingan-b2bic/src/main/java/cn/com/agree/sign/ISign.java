package cn.com.agree.sign;

public abstract interface ISign
{
  public abstract String name();
  
  public abstract void setHashAlg(String paramString);
  
  public abstract byte[] getCert()
    throws Exception;
  
  public abstract byte[] sign(byte[] paramArrayOfByte)
    throws Exception;
  
  public abstract byte[] hashAndSign(byte[] paramArrayOfByte)
    throws Exception;
  
  public abstract byte[] hashAndSign(String paramString)
    throws Exception;
  
  public abstract boolean verify(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception;
  
  public abstract boolean hashAndVerify(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception;
  
  public abstract boolean hashAndVerify(String paramString, byte[] paramArrayOfByte)
    throws Exception;
  
  public abstract String getSubject();
}

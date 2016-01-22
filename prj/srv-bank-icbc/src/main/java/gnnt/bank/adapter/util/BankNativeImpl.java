package gnnt.bank.adapter.util;

public class BankNativeImpl
{
  static
  {
    System.loadLibrary("jniBankInterface");
  }

  public native String GenZPK();

  public native String PinDptZPKey(String paramString1, String paramString2);

  public native String GenZAK();

  public native String GenMAC(byte[] paramArrayOfByte, int paramInt, String paramString);

  public native int MACVerify(byte[] paramArrayOfByte, int paramInt, String paramString1, String paramString2);
}
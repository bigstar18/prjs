package gnnt.bank.adapter.util;

public class MAC
{
  private StringBuffer textBuffer = new StringBuffer();
  private String text;
  private String code;

  public boolean veryfy(String code)
  {
    encryption();

    BankNativeImpl bni = new BankNativeImpl();
    String key = FileUtil.readline("key.txt");
    int result = bni.MACVerify(this.textBuffer.toString().getBytes(), this.textBuffer.toString().getBytes().length, code, key);
    if (result == 0) {
      return true;
    }
    return false;
  }

  public static boolean veryfy(String localCode, String code)
  {
    BankNativeImpl bni = new BankNativeImpl();
    String key = FileUtil.readline("key.txt");
    int result = bni.MACVerify(localCode.getBytes(), localCode.getBytes().length, code, key);
    if (result == 0) {
      return true;
    }
    return false;
  }

  public String getCode()
  {
    encryption();
    return this.code;
  }

  public String getText()
  {
    this.text = this.textBuffer.toString();
    return this.text;
  }

  private void encryption()
  {
    BankNativeImpl bni = new BankNativeImpl();
    String key = FileUtil.readline("key.txt");
    this.code = bni.GenMAC(this.textBuffer.toString().getBytes(), this.textBuffer.toString().getBytes().length, key);
  }

  public void add(String field)
  {
    if (this.textBuffer.length() != 0)
      this.textBuffer.append("|");
    this.textBuffer.append(field);
  }

  public void add(int field)
  {
    if (this.textBuffer.length() != 0)
      this.textBuffer.append("|");
    this.textBuffer.append(field);
  }

  public void add(long field)
  {
    if (this.textBuffer.length() != 0)
      this.textBuffer.append("|");
    this.textBuffer.append(field);
  }
}
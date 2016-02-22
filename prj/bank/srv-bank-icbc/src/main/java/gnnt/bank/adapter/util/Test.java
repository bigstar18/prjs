package gnnt.bank.adapter.util;

import java.io.PrintStream;

public class Test
{
  public static void main(String[] args)
  {
    String a = "aaa";
    a = a + "|";
    a = a + "bbbs";
    System.out.println(a);
  }

  public static void main1(String[] args)
  {
    for (int i = 0; i < 10000; i++)
    {
      String data = new String("sdfsdfsdf|123123123||dsfsf");
      String key = new String("123456789abcdefg");
      BankNativeImpl bni1 = new BankNativeImpl();
      String MAC1 = bni1.GenMAC(data.getBytes(), data.getBytes().length, key);
      System.out.println("GenMAC:" + MAC1);

      BankNativeImpl bni2 = new BankNativeImpl();
      String MAC2 = bni2.GenMAC(data.getBytes(), data.getBytes().length, key);
      System.out.println("GenMAC:" + MAC2);

      if (!MAC1.equals("0E20DA23"))
      {
        System.out.println("MAC1:" + MAC1);
        break;
      }
    }
  }
}
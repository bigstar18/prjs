package gnnt.bank.adapter.socket;

import gnnt.bank.adapter.ABCBankImpl;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CommandService extends Thread
{
  static ABCBankImpl abc = new ABCBankImpl();

  public void run()
  {
    Connection();
  }

  public static String Connection()
  {
    while (true)
      try
      {
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, 1024);
        ds.receive(dp);
        String str = new String(dp.getData()).trim();
        System.out.println(str);
        String msg = new String(str.getBytes(), 0, dp.getLength()) + " from " + 
          dp.getAddress().getHostAddress() + ":" + dp.getPort();
        System.out.println(msg);

        if (str.equals("ceshi1"))
          abc.pay1();
        else if (str.equals("ceshi2"))
          abc.pay2();
        else if (str.equals("ceshi3")) {
          abc.pay3();
        }
        ds.close();
      }
      catch (Exception localException)
      {
      }
  }
}
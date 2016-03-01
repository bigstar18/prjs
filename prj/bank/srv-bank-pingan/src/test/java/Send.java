import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Send
{
  public static void main(String[] args)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append("     农行测试      \n");
    sb.append("**********************************\n");
    sb.append("*   ceshi1             测试1    \n");
    sb.append("*   ceshi2             测试2    \n");
    sb.append("*   ceshi3             测试3   \n");
    sb.append("*   help                   \n");
    sb.append("*******************************");
    System.out.println(sb.toString());

    BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
    String str = "";
    while (!str.equals("exit"))
      try {
        if (str.equalsIgnoreCase("help")) {
          System.out.println(sb.toString());
        }
        str = buf.readLine();
        send(str);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  public static void send(String str)
  {
    try
    {
      DatagramSocket ds = new DatagramSocket();
      DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), 
        InetAddress.getByName("127.0.0.1"), 1234);
      ds.send(dp);
      ds.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
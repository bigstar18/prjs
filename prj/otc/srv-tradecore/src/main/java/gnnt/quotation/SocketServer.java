package gnnt.quotation;

import gnnt.quotation.util.DateUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class SocketServer
  extends ServerSocket
{
  private static final int SERVER_PORT = 4321;
  Random ra;
  /* Error */
  public SocketServer()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: sipush 4321
    //   4: invokespecial 17	java/net/ServerSocket:<init>	(I)V
    //   7: aload_0
    //   8: new 20	java/util/Random
    //   11: dup
    //   12: invokespecial 22	java/util/Random:<init>	()V
    //   15: putfield 24	gnnt/quotation/SocketServer:ra	Ljava/util/Random;
    //   18: getstatic 26	java/lang/System:out	Ljava/io/PrintStream;
    //   21: ldc 32
    //   23: invokevirtual 34	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   26: aload_0
    //   27: invokevirtual 40	gnnt/quotation/SocketServer:accept	()Ljava/net/Socket;
    //   30: astore_1
    //   31: new 44	gnnt/quotation/SocketServer$CreateServerThread
    //   34: aload_0
    //   35: aload_1
    //   36: invokespecial 46	gnnt/quotation/SocketServer$CreateServerThread:<init>	(Lgnnt/quotation/SocketServer;Ljava/net/Socket;)V
    //   39: goto -13 -> 26
    //   42: astore_1
    //   43: aload_1
    //   44: invokevirtual 49	java/io/IOException:printStackTrace	()V
    //   47: getstatic 26	java/lang/System:out	Ljava/io/PrintStream;
    //   50: ldc 52
    //   52: invokevirtual 34	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   55: aload_0
    //   56: invokevirtual 54	gnnt/quotation/SocketServer:close	()V
    //   59: goto +10 -> 69
    //   62: astore_2
    //   63: aload_0
    //   64: invokevirtual 54	gnnt/quotation/SocketServer:close	()V
    //   67: aload_2
    //   68: athrow
    //   69: return
    // Line number table:
    //   Java source line #18	-> byte code offset #0
    //   Java source line #15	-> byte code offset #7
    //   Java source line #19	-> byte code offset #18
    //   Java source line #22	-> byte code offset #26
    //   Java source line #23	-> byte code offset #31
    //   Java source line #21	-> byte code offset #39
    //   Java source line #25	-> byte code offset #42
    //   Java source line #26	-> byte code offset #43
    //   Java source line #27	-> byte code offset #47
    //   Java source line #29	-> byte code offset #55
    //   Java source line #28	-> byte code offset #62
    //   Java source line #29	-> byte code offset #63
    //   Java source line #30	-> byte code offset #67
    //   Java source line #31	-> byte code offset #69
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	this	SocketServer
    //   30	6	1	socket	Socket
    //   42	2	1	e	IOException
    //   62	6	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   26	42	42	java/io/IOException
    //   26	55	62	finally
  }
  
  class CreateServerThread
    extends Thread
  {
    private Socket client;
    int i = 0;
    
    public CreateServerThread(Socket s)
      throws IOException
    {
      this.client = s;
      System.out.println("--- Welcome -------");
      start();
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          Thread.sleep(500L);
          Date curDate = new Date();
          String dateStr = DateUtil.formatDate(curDate, "yyyyMMdd");
          String timeStr = DateUtil.formatDate(curDate, "hhmmss");
          String commodityID = "";
          double price = 0.0D;
          String msg = "";
          if (this.i % 2 == 0)
          {
            commodityID = "Au";
            price = 300.0D;
            price += SocketServer.this.ra.nextDouble();
          }
          else
          {
            commodityID = "Ag";
            price = 8000.0D;
            price += SocketServer.this.ra.nextInt(10);
          }
          msg = 
            "|89|6000||3||" + commodityID + "|250|23|2|" + dateStr + "|" + timeStr + "|" + price + "|||||||";
          
          byte[] buf = msg.getBytes();
          DataOutputStream output = new DataOutputStream(this.client
            .getOutputStream());
          ByteArrayOutputStream array = new ByteArrayOutputStream();
          DataOutputStream outputArray = new DataOutputStream(array);
          
          outputArray.writeByte(255);
          outputArray.write(buf);
          outputArray.writeByte(0);
          
          outputArray.flush();
          
          buf = array.toByteArray();
          
          System.out.println("buf length=" + buf.length);
          output.write(buf);
          output.flush();
          outputArray.close();
          this.i += 1;
          if (this.i == 2147483647) {
            this.i = 0;
          }
        }
        return;
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args)
    throws IOException
  {
    new SocketServer();
  }
}

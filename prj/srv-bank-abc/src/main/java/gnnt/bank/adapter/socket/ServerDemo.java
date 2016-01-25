package gnnt.bank.adapter.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo extends Thread
{
  public static void main(String[] args)
  {
    ServerDemo s = new ServerDemo();
    s.start();
  }

  public void run()
  {
    try {
      ServerSocket server = null;
      int connects = 0;
      try {
        server = new ServerSocket(1233);
      } catch (Exception e) {
        System.out.println("can not listen to:" + e);
      }
      Socket socket = null;
      try
      {
        while (true) {
          socket = server.accept();
          ServiceClient(socket);
        }
      }
      catch (Exception e)
      {
        System.out.println("Error." + e);
      }
    } catch (Exception e) {
      System.out.println("Error:" + e);
    }
  }

  public static void ServiceClient(Socket socket) throws IOException {
    DataInputStream reader = null;
    DataOutputStream write = null;
    try
    {
      reader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      write = new DataOutputStream(socket.getOutputStream());
      System.out.println("开始接收");
      int i = 0;
      byte[] b = (byte[])null;
      b = new byte[1024];
      StringBuffer buff = new StringBuffer();
      int rc = -1;
      try {
        if ((rc = reader.read(b)) != -1) {
          buff.append(new String(b, 0, rc));
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        System.out.println("读取输入流IO异常");
      }

      System.out.println("接收完成");
      System.out.println("发送开始");
      write.write(buff.toString().getBytes());
      write.flush();
      System.out.println("发送完成");
      reader.close();
      write.close();
    }
    catch (Exception e) {
      System.out.println("Error:" + e);
    }
  }
}
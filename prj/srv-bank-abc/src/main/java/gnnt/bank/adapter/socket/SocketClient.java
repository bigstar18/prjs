package gnnt.bank.adapter.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;

public class SocketClient
{
  private Socket socket = null;
  private DataInputStream input = null;
  private DataOutputStream output = null;
  private String serverIP = null;
  private int serverPort;

  public SocketClient(String serverIP, int serverPort)
  {
    this.serverIP = serverIP;
    this.serverPort = serverPort;
  }

  public boolean Conntion() throws ConnectException
  {
    if (this.socket == null)
    {
      try {
        System.out.println("开始链接服务器");
        this.socket = new Socket(this.serverIP, this.serverPort);
        System.out.println("链接服务器成功");
        this.socket.setSoTimeout(10000);
        this.input = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
        this.output = new DataOutputStream(this.socket.getOutputStream());
      }
      catch (IOException e) {
        e.printStackTrace();
        System.out.println("链接服务器失败");
        close(this.socket, this.input, this.output);
        return false;
      }
      return true;
    }
    return false;
  }

  public int send(String sendStr)
  {
    int flag = 0;
    try {
      if (Conntion()) {
        byte[] message = sendStr.getBytes();
        try {
          System.out.println(new String(message));
          this.output.write(message);
          this.output.flush();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          flag = -50011;
          close(this.socket, this.input, this.output);
        }
      } else {
        flag = -50005;
      }
    }
    catch (ConnectException e) {
      flag = -50005;
      close(this.socket, this.input, this.output);
      e.printStackTrace();
    }

    return flag;
  }

  public String receive()
  {
    StringBuffer buff = new StringBuffer();
    int i = 0;
    byte[] b = (byte[])null;
    b = new byte[1024];
    try {
      while ((i = this.input.read(b)) != -1)
        buff.append(new String(b, 0, i));
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.out.println("读取输入流IO异常");
    }

    close(this.socket, this.input, this.output);
    return buff.toString();
  }

  public void close(Socket socket, DataInputStream input, DataOutputStream output)
  {
    try {
      if (socket != null) {
        socket.close();
      }
      if (output != null) {
        output.close();
      }
      if (input != null)
        input.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    } finally {
      socket = null;
      output = null;
      input = null;
    }
  }
}
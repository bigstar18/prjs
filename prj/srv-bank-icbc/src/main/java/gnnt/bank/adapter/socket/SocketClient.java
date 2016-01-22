package gnnt.bank.adapter.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.log4j.Logger;

public class SocketClient
{
  private Socket server;
  private DataInputStream in = null;
  private DataOutputStream out = null;
  private String ip = null;
  private int port;

  public SocketClient(String ip, int port)
  {
    this.ip = ip;
    this.port = port;
  }

  public SocketClient(InetAddress localHost, int port) {
    this.ip = localHost.getHostAddress();
    this.port = port;
  }

  public boolean checkConntion()
  {
    boolean result = true;
    if (Conntion()) {
      try {
        if (this.in != null) {
          this.in.close();
          this.out = null;
        }if (this.out != null) {
          this.out.close();
          this.in = null;
        }if (this.server != null) {
          this.server.close();
          this.server = null;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      result = true;
      System.out.println("链接服务器成功,地址：" + this.ip + ":" + this.port);
    } else {
      result = false;
    }
    return result;
  }

  public boolean Conntion()
  {
    if (this.server == null)
    {
      try {
        this.server = new Socket(this.ip, this.port);
        this.server.setSoTimeout(20000);
        this.in = new DataInputStream(new BufferedInputStream(this.server.getInputStream()));
        this.out = new DataOutputStream(this.server.getOutputStream());
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("链接服务器失败,地址：" + this.ip + ":" + this.port);
        this.server = null;
        return false;
      }
      return true;
    }
    return false;
  }

  public int send(String sendStr)
  {
    log("市场端请求报文：");
    log(sendStr);
    int flag = 0;
    if (Conntion()) {
      byte[] message = sendStr.getBytes();
      try {
        this.out.write(message);
        this.out.flush();
      } catch (IOException e) {
        e.printStackTrace();
        flag = -2;
      }
    } else {
      flag = -3;
    }

    return flag;
  }

  public String receive() {
    StringBuffer buff = new StringBuffer();
    int i = 0;
    byte[] b = (byte[])null;
    b = new byte[1024];
    try {
      while ((i = this.in.read(b)) != -1) {
        buff.append(new String(b, 0, i));
        String str = buff.toString();
        if (str.indexOf("</MsgText>") != -1)
          break;
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    try {
      this.in.close();
      this.out.close();
      this.server.close();
      this.out = null;
      this.in = null;
      this.server = null;
    } catch (IOException e) {
      e.printStackTrace();
    }
    log("银行返回报文：");
    log(buff.toString() + "\n");
    return buff.toString();
  }

  public static void log(String content)
  {
    Logger alog = Logger.getLogger("Socketlog");
    alog.debug(content);
  }

  public static void main(String[] args) throws Exception {
    SocketClient socketClient = new SocketClient("127.0.0.1", 19011);
    String sendStr = "<MsgText>helloworld</MgText>";
    int rst = socketClient.send(sendStr);
    if (rst == 0) {
      String response = socketClient.receive();
      System.out.println("服务器响应：");
      System.out.println(response);
    }
  }
}
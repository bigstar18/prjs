package gnnt.bank.adapter.socket;

import gnnt.bank.adapter.Factory;
import gnnt.bank.adapter.bankBusiness.BankBusiness;
import gnnt.bank.adapter.bankBusiness.exception.BankException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Connection extends Thread
{
  private Socket client;

  public Connection(Socket client)
  {
    this.client = client;
    try {
      client.setSoTimeout(20000);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    try {
      SocketServer.log("连接打开");
      DataInputStream in = new DataInputStream(new BufferedInputStream(this.client.getInputStream()));
      DataOutputStream out = new DataOutputStream(this.client.getOutputStream());

      StringBuffer buff = new StringBuffer();
      int i = 0;
      byte[] b = (byte[])null;
      b = new byte[1024];
      try {
        while ((i = in.read(b)) != -1) {
          buff.append(new String(b, 0, i));
          String str = buff.toString();
          if (str.indexOf("</MsgText>") != -1)
            break;
        }
      }
      catch (IOException e) {
        e.printStackTrace();
      }

      String request = buff.toString();
      SocketServer.log("银行请求报文：");
      SocketServer.log(request);
      BankBusiness bankBusiness = Factory.getInstance().getBankBusiniss();
      String response = null;
      try {
        response = bankBusiness.getResponse(request);
      } catch (BankException e) {
        response = "we received error data:" + request;
      }

      SocketServer.log("返回给银行报文：");
      SocketServer.log(response);
      byte[] message = response.getBytes();

      out.write(message);
      out.flush();
      out.close();
      in.close();

      this.client.close();
      SocketServer.log("连接关闭\n");
    }
    catch (IOException localIOException1)
    {
    }
  }
}
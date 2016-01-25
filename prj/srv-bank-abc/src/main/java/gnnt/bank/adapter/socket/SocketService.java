package gnnt.bank.adapter.socket;

import gnnt.bank.adapter.ABCBankImpl;
import gnnt.bank.platform.CapitalProcessor;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService extends Thread
{
  private static SocketService bankService = null;
  private CapitalProcessor processor = null;
  private ABCBankImpl abcBank = null;

  public static SocketService getInstance()
  {
    if (bankService == null) {
      synchronized (SocketService.class) {
        bankService = new SocketService();
        bankService.start();
      }
    }
    return bankService;
  }

  public void setProcessor(CapitalProcessor processor) {
    this.processor = processor;
  }

  public void setabcBank(ABCBankImpl bank) {
    this.abcBank = bank;
  }

  public void run() {
    ServerSocket rServer = null;
    Socket socket = null;
    Thread receiveThread = null;
    try {
      rServer = new ServerSocket(Integer.parseInt(ABCBankImpl.getConfig("fundPort")));
      while (true) {
        socket = rServer.accept();
      }
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
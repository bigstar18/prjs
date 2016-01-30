package gnnt.bank.adapter.socket;

import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.timeTask.TimeTaskServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

public class SocketServer extends Thread
{
  ServerSocket ss = null;

  public SocketServer(int port) throws IOException, SchedulerException, ParseException
  {
    try {
      this.ss = new ServerSocket(port);
      BankAdapter.log("银行监听启动成功!");
      TimeTaskServer.start();
    } catch (IOException ioe) {
      BankAdapter.log("银行监听启动失败!");
      throw ioe;
    } catch (SchedulerException e) {
      BankAdapter.log("银行监听启动失败!");
      throw e;
    } catch (ParseException e) {
      BankAdapter.log("银行监听启动失败!");
      throw e;
    }
  }

  public void run() {
    while (true) {
      Connection connection = null;
      try {
        Socket client = this.ss.accept();
        connection = new Connection(client);
      } catch (IOException e) {
        e.printStackTrace();
      }
      connection.start();
    }
  }

  public static void log(String content)
  {
    Logger alog = Logger.getLogger("Socketlog");
    alog.debug(content);
  }
}
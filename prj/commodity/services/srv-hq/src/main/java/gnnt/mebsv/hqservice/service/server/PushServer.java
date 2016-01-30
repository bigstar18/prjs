package gnnt.mebsv.hqservice.service.server;

import gnnt.mebsv.hqservice.model.ClientSocket;
import gnnt.mebsv.hqservice.model.OutPutObj;
import gnnt.mebsv.hqservice.service.HQService;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PushServer
{
  private static Log log = LogFactory.getLog(PushServer.class);
  private HQService hqService;
  private BlockingQueue<OutPutObj> outPutTask;

  public PushServer(HQService paramHQService, BlockingQueue<OutPutObj> paramBlockingQueue)
  {
    this.hqService = paramHQService;
    this.outPutTask = paramBlockingQueue;
  }

  public void pushHQ(ClientSocket paramClientSocket, DataOutputStream paramDataOutputStream)
    throws Exception
  {
    byte[] arrayOfByte = new byte[0];
    switch (paramClientSocket.iStatus)
    {
    case 0:
      arrayOfByte = this.hqService.GetQuoteList(paramClientSocket);
      flushData(paramClientSocket.socket, arrayOfByte);
      break;
    case 4:
      arrayOfByte = this.hqService.getBillData(paramClientSocket);
      flushData(paramClientSocket.socket, arrayOfByte);
      break;
    case 1:
      arrayOfByte = this.hqService.getCurStatusByCodes(paramClientSocket);
      flushData(paramClientSocket.socket, arrayOfByte);
      arrayOfByte = this.hqService.getBillData(paramClientSocket);
      flushData(paramClientSocket.socket, arrayOfByte);
      break;
    case 2:
      arrayOfByte = this.hqService.getCurStatusByCodes(paramClientSocket);
      flushData(paramClientSocket.socket, arrayOfByte);
      arrayOfByte = this.hqService.getBillData(paramClientSocket);
      flushData(paramClientSocket.socket, arrayOfByte);
      break;
    case 3:
    case 5:
    }
    if (System.currentTimeMillis() - paramClientSocket.lastSysTime > 10000L)
    {
      arrayOfByte = this.hqService.getDate();
      flushData(paramClientSocket.socket, arrayOfByte);
      paramClientSocket.lastSysTime = new Date().getTime();
    }
  }

  public void flushData(Socket paramSocket, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length > 0)
      if (ReceiverThread.DY_ST_POOL.equals("0"))
        this.outPutTask.offer(new OutPutObj(paramSocket, paramArrayOfByte));
      else if (ReceiverThread.DY_ST_POOL.equals("1"))
        try
        {
          if (paramArrayOfByte.length > 0)
          {
            DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(paramSocket.getOutputStream()));
            localDataOutputStream.write(paramArrayOfByte);
            localDataOutputStream.flush();
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
  }
}
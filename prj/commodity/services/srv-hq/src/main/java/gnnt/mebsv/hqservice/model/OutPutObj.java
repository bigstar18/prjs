package gnnt.mebsv.hqservice.model;

import java.net.Socket;

public class OutPutObj
{
  private Socket socket;
  private byte[] data;

  public OutPutObj(Socket paramSocket, byte[] paramArrayOfByte)
  {
    this.socket = paramSocket;
    this.data = paramArrayOfByte;
  }

  public Socket getSocket()
  {
    return this.socket;
  }

  public void setSocket(Socket paramSocket)
  {
    this.socket = paramSocket;
  }

  public byte[] getData()
  {
    return this.data;
  }

  public void setData(byte[] paramArrayOfByte)
  {
    this.data = paramArrayOfByte;
  }
}
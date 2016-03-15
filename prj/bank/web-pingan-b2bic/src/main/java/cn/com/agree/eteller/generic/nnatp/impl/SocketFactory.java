package cn.com.agree.eteller.generic.nnatp.impl;

import cn.com.agree.eteller.generic.utils.CommonType;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketFactory
{
  private static final Log logger = LogFactory.getLog(SocketFactory.class);
  
  public static Socket createSocket(String serverName)
    throws NumberFormatException, UnknownHostException, IOException
  {
    String hostPort = null;
    CommonType[] ct = Tools.loadConfigFromFile("nnatp", 1);
    for (int i = 0; i < ct.length; i++) {
      if (ct[i].getId().equals(serverName))
      {
        hostPort = ct[i].getValue();
        break;
      }
    }
    if (hostPort == null) {
      new Exception("没有服务器“" + serverName + "”的配置信息！");
    }
    String[] segs = hostPort.split(":");
    if (segs.length < 3) {
      new Exception("服务器“" + serverName + "”的配置错误，格式为“IP地址:端口号”！");
    }
    Socket socket = getRandomSocket(segs[0], Integer.parseInt(segs[1]), 
      InetAddress.getLocalHost(), 0);
    socket.setSoTimeout(Integer.parseInt(segs[2]) * 1000);
    logger.info("连接到服务器[" + segs[0] + "]端口号[" + segs[1] + "]成功!");
    return socket;
  }
  
  public static Socket getRandomSocket(String ip, int port, InetAddress localhost, int port2)
  {
    Socket socket = null;
    try
    {
      socket = new Socket(ip, port);
    }
    catch (Exception e)
    {
      socket = getRandomSocket(ip, port, localhost, port2);
    }
    return socket;
  }
}

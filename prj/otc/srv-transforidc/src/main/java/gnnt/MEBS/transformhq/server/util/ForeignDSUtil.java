package gnnt.MEBS.transformhq.server.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ForeignDSUtil
{
  private static Log log = LogFactory.getLog(ForeignDSUtil.class);
  
  public static byte[] assembleCMD(byte[] cmd)
    throws IOException
  {
    ByteArrayOutputStream array = new ByteArrayOutputStream();
    DataOutputStream output = new DataOutputStream(array);
    
    output.writeByte(4);
    output.writeByte(32);
    output.writeInt(cmd.length);
    output.write(cmd);
    output.writeByte(3);
    output.flush();
    output.close();
    array.flush();
    array.close();
    return array.toByteArray();
  }
  
  public static String readResponse(DataInputStream serverStr)
    throws IOException
  {
    Byte frameStart = Byte.valueOf(serverStr.readByte());
    while (!frameStart.equals(Byte.valueOf((byte)4))) {
      frameStart = Byte.valueOf(serverStr.readByte());
    }
    serverStr.readByte();
    int size = serverStr.readInt();
    
    byte[] result = new byte[size];
    serverStr.read(result);
    serverStr.readByte();
    String res = new String(result, "US-ASCII");
    if (res.trim().getBytes().length != size)
    {
      log.debug("接收到的数据有问题，有问题的数据为：" + res);
      return "";
    }
    return res;
  }
  
  public static Map<String, String> parseResponse(String response)
  {
    String[] results = response.split("\\|");
    Map<String, String> map = new HashMap();
    for (String result : results)
    {
      String[] cmdStr = result.split("=");
      map.put(cmdStr[0], cmdStr[1]);
    }
    return map;
  }
}

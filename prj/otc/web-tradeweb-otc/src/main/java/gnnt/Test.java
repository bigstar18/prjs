package gnnt;

import gnnt.MEBS.timebargain.tradeweb.webapp.util.CIOUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.rmi.RemoteException;

public class Test
{
  public static byte b = 1;
  public static int i = 123456543;
  public static double d = 123123.0989D;
  public static String s = "我是字符串";
  public static long l = 123456789L;
  public static String Filename = "d:\\test_nbuf.data";
  
  public static void WriteByByteBuffer()
    throws IOException
  {
    System.out.println(ByteOrder.nativeOrder());
    ByteBuffer _nbuffer = ByteBuffer.allocate(1024);
    _nbuffer.clear();
    _nbuffer.order(ByteOrder.nativeOrder());
    
    _nbuffer.put(b);
    _nbuffer.putInt(i);
    _nbuffer.putDouble(d);
    byte[] byteArr = s.getBytes("UTF-8");
    _nbuffer.putShort((short)byteArr.length);
    _nbuffer.put(byteArr);
    _nbuffer.putLong(l);
    
    _nbuffer.flip();
    
    FileOutputStream _nfou = new FileOutputStream(Filename);
    _nfou.write(_nbuffer.array());
    _nfou.close();
  }
  
  public static void WriteByCIOUtil()
    throws IOException
  {
    FileOutputStream _nfou = new FileOutputStream(Filename);
    
    DataOutputStream os = new DataOutputStream(_nfou);
    
    CIOUtil.writeByte(os, b);
    CIOUtil.writeInt(os, i);
    CIOUtil.writeDouble(os, d);
    CIOUtil.writeUTF(os, s);
    CIOUtil.writeLong(os, l);
    
    _nfou.close();
  }
  
  public static void ReadByChannel()
    throws IOException
  {
    System.out.println("--------------ReadByChannel-----------------");
    FileChannel channel = new FileInputStream(Filename).getChannel();
    
    ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
    buffer.clear();
    int count = channel.read(buffer);
    buffer.order(ByteOrder.nativeOrder());
    buffer.flip();
    System.out.println("count=" + count);
    System.out.println(buffer.get());
    System.out.println(buffer.getInt());
    System.out.println(buffer.getDouble());
    byte[] dst = new byte[buffer.getShort()];
    buffer.get(dst);
    System.out.println(new String(dst, "UTF-8"));
    System.out.println(buffer.getLong());
  }
  
  public static void ReadByStream()
    throws IOException
  {
    System.out.println("--------------ReadByStream-----------------");
    ByteArrayOutputStream array = new ByteArrayOutputStream();
    DataOutputStream outputArray = new DataOutputStream(array);
    FileInputStream stream = new FileInputStream(Filename);
    byte[] tempByte = new byte[1];
    while (stream.read(tempByte) != -1) {
      outputArray.write(tempByte);
    }
    outputArray.flush();
    byte[] buf = array.toByteArray();
    outputArray.close();
    
    System.out.println("buf=" + buf.length);
    ByteBuffer buffer = ByteBuffer.wrap(buf);
    buffer.order(ByteOrder.nativeOrder());
    
    System.out.println(buffer.get());
    System.out.println(buffer.getInt());
    System.out.println(buffer.getDouble());
    byte[] dst = new byte[buffer.getShort()];
    buffer.get(dst);
    System.out.println(new String(dst, "UTF-8"));
    System.out.println(buffer.getLong());
  }
  
  public static void ReadByCIOUtil()
    throws IOException
  {
    System.out.println("--------------ReadByCIOUtil-----------------");
    FileInputStream stream = new FileInputStream(Filename);
    DataInputStream is = new DataInputStream(stream);
    System.out.println(CIOUtil.readByte(is));
    System.out.println(CIOUtil.readInt(is));
    System.out.println(CIOUtil.readDouble(is));
    System.out.println(CIOUtil.readUTF(is));
    System.out.println(CIOUtil.readLong(is));
    is.close();
    stream.close();
  }
  
  public void test()
    throws Exception
  {
    testRMIException();
  }
  
  public void testRMIException()
    throws RemoteException
  {
    throw new RemoteException("测试");
  }
  
  public static void main(String[] args)
    throws IOException
  {
    Test test = new Test();
    try
    {
      test.test();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}

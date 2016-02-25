package gnnt.MEBS.timebargain.manage.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StreamGobbler
  extends Thread
{
  private InputStream in = null;
  private PrintWriter pwOut = null;
  private boolean quit = false;
  private static final String CLASS_NAME = "StreamGobbler";
  private static final String VERSION = "CVS $Revision: 1.5 $";
  
  public StreamGobbler() {}
  
  public StreamGobbler(InputStream paramInputStream)
  {
    this();
    this.in = paramInputStream;
    this.pwOut = new PrintWriter(System.out, true);
  }
  
  public StreamGobbler(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    this();
    this.in = paramInputStream;
    this.pwOut = new PrintWriter(paramOutputStream, true);
  }
  
  public StreamGobbler(InputStream paramInputStream, PrintWriter paramPrintWriter)
  {
    this();
    this.in = paramInputStream;
    this.pwOut = paramPrintWriter;
  }
  
  public final Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
  
  public void quit()
  {
    this.quit = true;
  }
  
  private final void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException
  {
    throw new IOException("Object cannot be deserialized");
  }
  
  public void run()
  {
    try
    {
      InputStreamReader localInputStreamReader = new InputStreamReader(this.in);
      BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
      String str = null;
      while (((str = localBufferedReader.readLine()) != null) && (!this.quit)) {
        this.pwOut.println(str);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private final void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    throw new IOException("Object cannot be serialized");
  }
}

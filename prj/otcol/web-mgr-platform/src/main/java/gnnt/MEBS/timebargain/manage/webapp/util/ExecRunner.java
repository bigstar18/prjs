package gnnt.MEBS.timebargain.manage.webapp.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.StringTokenizer;

public class ExecRunner
{
  private static final String WINDOWS_NT_2000_COMMAND_1 = "cmd.exe";
  private static final String WINDOWS_NT_2000_COMMAND_2 = "/C";
  private static final String WINDOWS_9X_ME_COMMAND_1 = "command.exe";
  private static final String WINDOWS_9X_ME_COMMAND_2 = "/C";
  private static final String MAX_RUN_TIME_EXCEEDED_STRING = "MAX_RUN_TIME_EXCEEDED";
  private String out = new String();
  private String err = new String();
  private int maxRunTimeSecs = 0;
  private boolean maxRunTimeExceeded = false;
  private static final String CLASS_NAME = "ExecRunner";
  private static final String VERSION = "CVS $Revision: 1.5 $";
  private static final int POLL_DELAY_MS = 100;
  
  public ExecRunner() {}
  
  public ExecRunner(String paramString)
    throws ExceptionInInitializerError
  {
    this();
    try
    {
      exec(paramString);
    }
    catch (IOException localIOException)
    {
      throw new ExceptionInInitializerError(localIOException.getMessage());
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new ExceptionInInitializerError(localInterruptedException.getMessage());
    }
  }
  
  public final Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
  
  public int exec(String paramString)
    throws IOException, InterruptedException
  {
    StringWriter localStringWriter1 = new StringWriter();
    PrintWriter localPrintWriter1 = new PrintWriter(localStringWriter1, true);
    StringWriter localStringWriter2 = new StringWriter();
    PrintWriter localPrintWriter2 = new PrintWriter(localStringWriter2, true);
    int i = exec(paramString, localPrintWriter1, localPrintWriter2);
    this.out = localStringWriter1.toString();
    this.err = localStringWriter2.toString();
    return i;
  }
  
  public int exec(String paramString, OutputStream paramOutputStream1, OutputStream paramOutputStream2)
    throws IOException, InterruptedException
  {
    PrintWriter localPrintWriter1 = new PrintWriter(paramOutputStream1, true);
    PrintWriter localPrintWriter2 = new PrintWriter(paramOutputStream2, true);
    return exec(paramString, localPrintWriter1, localPrintWriter2);
  }
  
  public int exec(String paramString, PrintWriter paramPrintWriter1, PrintWriter paramPrintWriter2)
    throws IOException, InterruptedException
  {
    int i = 1;
    Runtime localRuntime = Runtime.getRuntime();
    String[] arrayOfString = null;
    Date localDate1 = new Date();
    long l1 = localDate1.getTime();
    long l2 = l1 + this.maxRunTimeSecs * 1000;
    String str1 = System.getProperty("os.name");
    if ((str1.equals("Windows NT")) || (str1.equals("Windows 2000")))
    {
      arrayOfString = new String[3];
      arrayOfString[0] = "cmd.exe";
      arrayOfString[1] = "/C";
      arrayOfString[2] = paramString;
    }
    else if ((str1.equals("Windows 95")) || (str1.equals("Windows 98")) || (str1.equals("Windows ME")))
    {
      arrayOfString = new String[3];
      arrayOfString[0] = "command.exe";
      arrayOfString[1] = "/C";
      arrayOfString[2] = paramString;
    }
    else
    {
      localObject = new StringTokenizer(paramString, " ");
      arrayOfString = new String[((StringTokenizer)localObject).countTokens()];
      int j = 0;
      while (((StringTokenizer)localObject).hasMoreTokens())
      {
        String str2 = ((StringTokenizer)localObject).nextToken();
        arrayOfString[(j++)] = str2;
      }
    }
    Process localProcess;
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      localProcess = localRuntime.exec(arrayOfString);
    } else {
      throw new IOException("Insufficient commands!");
    }
    Object localObject = new StreamGobbler(localProcess.getInputStream(), paramPrintWriter1);
    StreamGobbler localStreamGobbler = new StreamGobbler(localProcess.getErrorStream(), paramPrintWriter2);
    ((StreamGobbler)localObject).start();
    localStreamGobbler.start();
    for (;;)
    {
      try
      {
        i = localProcess.exitValue();
      }
      catch (IllegalThreadStateException localIllegalThreadStateException)
      {
        if (this.maxRunTimeSecs > 0)
        {
          Date localDate2 = new Date();
          long l3 = localDate2.getTime();
          if (l3 > l2)
          {
            localProcess.destroy();
            this.maxRunTimeExceeded = true;
            paramPrintWriter2.println("MAX_RUN_TIME_EXCEEDED");
            ((StreamGobbler)localObject).quit();
            localStreamGobbler.quit();
            return i;
          }
          Thread.sleep(100L);
        }
      }
    }
    while ((((StreamGobbler)localObject).isAlive()) || (localStreamGobbler.isAlive())) {}
    paramPrintWriter1.flush();
    paramPrintWriter2.flush();
    return i;
  }
  
  public String getErrString()
  {
    return this.err;
  }
  
  public boolean isMaxRunTimeExceeded()
  {
    return this.maxRunTimeExceeded;
  }
  
  public int getMaxRunTimeSecs()
  {
    return this.maxRunTimeSecs;
  }
  
  public String getOutString()
  {
    return this.out;
  }
  
  public static void main(String[] paramArrayOfString)
    throws IOException
  {
    try
    {
      ExecRunner localExecRunner = new ExecRunner();
      localExecRunner = new ExecRunner();
      localExecRunner.setMaxRunTimeSecs(30);
      localExecRunner.exec(paramArrayOfString[0]);
      System.exit(0);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.exit(1);
    }
  }
  
  private final void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException
  {
    throw new IOException("Object cannot be deserialized");
  }
  
  public void setMaxRunTimeSecs(int paramInt)
  {
    this.maxRunTimeSecs = paramInt;
  }
  
  private final void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    throw new IOException("Object cannot be serialized");
  }
}

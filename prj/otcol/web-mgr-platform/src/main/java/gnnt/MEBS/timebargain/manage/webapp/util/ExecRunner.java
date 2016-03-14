// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package gnnt.MEBS.timebargain.manage.webapp.util;

import java.io.*;
import java.util.Date;
import java.util.StringTokenizer;

// Referenced classes of package gnnt.MEBS.timebargain.manage.webapp.util:
//            StreamGobbler

public class ExecRunner
{

    private static final String WINDOWS_NT_2000_COMMAND_1 = "cmd.exe";
    private static final String WINDOWS_NT_2000_COMMAND_2 = "/C";
    private static final String WINDOWS_9X_ME_COMMAND_1 = "command.exe";
    private static final String WINDOWS_9X_ME_COMMAND_2 = "/C";
    private static final String MAX_RUN_TIME_EXCEEDED_STRING = "MAX_RUN_TIME_EXCEEDED";
    private String out;
    private String err;
    private int maxRunTimeSecs;
    private boolean maxRunTimeExceeded;
    private static final String CLASS_NAME = "ExecRunner";
    private static final String VERSION = "CVS $Revision: 1.5 $";
    private static final int POLL_DELAY_MS = 100;

    public ExecRunner()
    {
        out = new String();
        err = new String();
        maxRunTimeSecs = 0;
        maxRunTimeExceeded = false;
    }

    public ExecRunner(String s)
        throws ExceptionInInitializerError
    {
        this();
        try
        {
            exec(s);
        }
        catch(IOException ioexception)
        {
            throw new ExceptionInInitializerError(ioexception.getMessage());
        }
        catch(InterruptedException interruptedexception)
        {
            throw new ExceptionInInitializerError(interruptedexception.getMessage());
        }
    }

    public final Object clone()
        throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    public int exec(String s)
        throws IOException, InterruptedException
    {
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter(stringwriter, true);
        StringWriter stringwriter1 = new StringWriter();
        PrintWriter printwriter1 = new PrintWriter(stringwriter1, true);
        int i = exec(s, printwriter, printwriter1);
        out = stringwriter.toString();
        err = stringwriter1.toString();
        return i;
    }

    public int exec(String s, OutputStream outputstream, OutputStream outputstream1)
        throws IOException, InterruptedException
    {
        PrintWriter printwriter = new PrintWriter(outputstream, true);
        PrintWriter printwriter1 = new PrintWriter(outputstream1, true);
        return exec(s, printwriter, printwriter1);
    }

    public int exec(String s, PrintWriter printwriter, PrintWriter printwriter1)
        throws IOException, InterruptedException
    {
        int i = 1;
        Runtime runtime = Runtime.getRuntime();
        String as[] = null;
        Date date = new Date();
        long l = date.getTime();
        long l1 = l + (long)(maxRunTimeSecs * 1000);
        String s1 = System.getProperty("os.name");
        if(s1.equals("Windows NT") || s1.equals("Windows 2000"))
        {
            as = new String[3];
            as[0] = "cmd.exe";
            as[1] = "/C";
            as[2] = s;
        } else
        if(s1.equals("Windows 95") || s1.equals("Windows 98") || s1.equals("Windows ME"))
        {
            as = new String[3];
            as[0] = "command.exe";
            as[1] = "/C";
            as[2] = s;
        } else
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, " ");
            as = new String[stringtokenizer.countTokens()];
            int j = 0;
            while(stringtokenizer.hasMoreTokens()) 
            {
                String s2 = stringtokenizer.nextToken();
                as[j++] = s2;
            }
        }
        Process process;
        if(as != null && as.length > 0)
            process = runtime.exec(as);
        else
            throw new IOException("Insufficient commands!");
        StreamGobbler streamgobbler = new StreamGobbler(process.getInputStream(), printwriter);
        StreamGobbler streamgobbler1 = new StreamGobbler(process.getErrorStream(), printwriter1);
        streamgobbler.start();
        streamgobbler1.start();
        do
            try
            {
                i = process.exitValue();
                break;
            }
            catch(IllegalThreadStateException illegalthreadstateexception)
            {
                if(maxRunTimeSecs > 0)
                {
                    Date date1 = new Date();
                    long l2 = date1.getTime();
                    if(l2 > l1)
                    {
                        process.destroy();
                        maxRunTimeExceeded = true;
                        printwriter1.println("MAX_RUN_TIME_EXCEEDED");
                        streamgobbler.quit();
                        streamgobbler1.quit();
                        return i;
                    }
                    Thread.sleep(100L);
                }
            }
        while(true);
        while(streamgobbler.isAlive() || streamgobbler1.isAlive()) ;
        printwriter.flush();
        printwriter1.flush();
        return i;
    }

    public String getErrString()
    {
        return err;
    }

    public boolean isMaxRunTimeExceeded()
    {
        return maxRunTimeExceeded;
    }

    public int getMaxRunTimeSecs()
    {
        return maxRunTimeSecs;
    }

    public String getOutString()
    {
        return out;
    }

    public static void main(String args[])
        throws IOException
    {
        try
        {
            ExecRunner execrunner = new ExecRunner();
            execrunner = new ExecRunner();
            execrunner.setMaxRunTimeSecs(30);
            execrunner.exec(args[0]);
            System.exit(0);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    private final void readObject(ObjectInputStream objectinputstream)
        throws IOException
    {
        throw new IOException("Object cannot be deserialized");
    }

    public void setMaxRunTimeSecs(int i)
    {
        maxRunTimeSecs = i;
    }

    private final void writeObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        throw new IOException("Object cannot be serialized");
    }
}

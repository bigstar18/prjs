package gnnt.MEBS.finance.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class TextFile
  extends ArrayList
{
  public static String read(String paramString)
    throws IOException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
    String str;
    while ((str = localBufferedReader.readLine()) != null)
    {
      localStringBuffer.append(str);
      localStringBuffer.append("\n");
    }
    localBufferedReader.close();
    return localStringBuffer.toString();
  }
  
  public static void write(String paramString1, String paramString2)
    throws IOException
  {
    PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(paramString1)));
    localPrintWriter.print(paramString2);
    localPrintWriter.close();
  }
  
  public TextFile(String paramString)
    throws IOException
  {
    super(Arrays.asList(read(paramString).split("\n")));
  }
  
  public TextFile() {}
  
  public void write(String paramString)
    throws IOException
  {
    PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(paramString)));
    for (int i = 0; i < size(); i++) {
      localPrintWriter.println(get(i));
    }
    localPrintWriter.close();
  }
  
  public String toText()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < size(); i++)
    {
      localStringBuffer.append((String)get(i));
      localStringBuffer.append("\n");
    }
    return localStringBuffer.toString();
  }
  
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    TextFile localTextFile = new TextFile("C:\\Tomcat5.0\\LICENSE");
    System.out.print(localTextFile.toText());
  }
}

package gnnt.trade.bank.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class FileUtil
{
  public static void main(String[] args)
  {
    try
    {
      write("12354131321321edf", "key.txt");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    System.out.println(readline("key.txt"));
  }
  
  public static void write(String str, String filePath)
    throws IOException
  {
    try
    {
      FileWriter fileWriter = new FileWriter(filePath);
      BufferedWriter bfW = new BufferedWriter(fileWriter);
      bfW.write(str);
      bfW.flush();
      bfW.close();
      fileWriter.close();
    }
    catch (IOException e)
    {
      throw e;
    }
  }
  
  public static String readline(String filePath)
  {
    String str = "";
    try
    {
      FileReader fileReader = new FileReader(filePath);
      BufferedReader bfR = new BufferedReader(fileReader);
      str = bfR.readLine();
      
      fileReader.close();
      bfR.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return str;
  }
}

package gnnt.bank.adapter.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil
{
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
    } catch (IOException e) {
      throw e;
    }
  }

  public static String readline(String filePath)
  {
    String str = "";
    try {
      FileReader fileReader = new FileReader(filePath);
      BufferedReader bfR = new BufferedReader(fileReader);
      str = bfR.readLine();

      fileReader.close();
      bfR.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str;
  }

  public static final String readF1(String filePath)
    throws IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(
      new FileInputStream(filePath)));
    StringBuffer sb = new StringBuffer();
    for (String line = br.readLine(); line != null; line = br.readLine())
    {
      sb.append(line);
      sb.append("\n");
    }
    br.close();
    return sb.toString();
  }
}
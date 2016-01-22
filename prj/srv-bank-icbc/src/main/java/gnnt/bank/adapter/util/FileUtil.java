package gnnt.bank.adapter.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class FileUtil
{
  public static void main(String[] args)
  {
    try
    {
      write("12354131321321edf", "key.txt");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(readline("key.txt"));
  }

  public static void write(String str, String filePath) throws IOException {
    try {
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

  public static String readline(String filePath) {
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

  public static Map<String, String> getTZMessage(String fileName)
  {
    Map result = new HashMap();
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bfR = new BufferedReader(fileReader);
      String str = null;
      while ((str = bfR.readLine()) != null) {
        String[] strA = str.split("\\|", 0);
        result.put(strA[0], strA[1]);
      }
      fileReader.close();
      bfR.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  public static boolean fileIsExist(String fileFullName)
  {
    File file = new File(fileFullName);
    if (file.exists()) {
      return true;
    }
    return false;
  }
}
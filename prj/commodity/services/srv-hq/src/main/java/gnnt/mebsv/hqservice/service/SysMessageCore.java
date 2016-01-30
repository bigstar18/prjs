package gnnt.mebsv.hqservice.service;

import java.util.HashMap;
import java.util.Map;

public class SysMessageCore extends Thread
{
  public static Map messageMap;

  public void init()
  {
    messageMap = new HashMap();
  }

  public void run()
  {
  }
}
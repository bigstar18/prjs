package gnnt.mebsv.hqservice.tools;

import gnnt.mebsv.hqservice.model.OutPutObj;
import java.util.concurrent.LinkedBlockingDeque;

public class Glob
{
  public static LinkedBlockingDeque<OutPutObj> outPutTask = new LinkedBlockingDeque();
}
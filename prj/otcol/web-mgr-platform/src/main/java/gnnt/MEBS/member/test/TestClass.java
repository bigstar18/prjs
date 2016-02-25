package gnnt.MEBS.member.test;

import gnnt.MEBS.member.firm.unit.FirmExtendData;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class TestClass
{
  public static void main(String[] paramArrayOfString)
  {
    FirmExtendData localFirmExtendData = new FirmExtendData();
    Class localClass = localFirmExtendData.getClass();
    Method[] arrayOfMethod = localClass.getMethods();
    for (int i = 0; i < arrayOfMethod.length; i++) {
      System.out.println(arrayOfMethod[i].getName());
    }
  }
}

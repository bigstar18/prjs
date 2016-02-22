package gnnt.MEBS.firm.common;

import java.util.ResourceBundle;

public class MessageProperties
{
  private static ResourceBundle rb = ResourceBundle.getBundle("message_product");
  
  public static int getBeginTime()
  {
    return Integer.parseInt(rb.getString("begin_time"));
  }
  
  public static int getEndTime()
  {
    return Integer.parseInt(rb.getString("end_time"));
  }
  
  public static String getBankRMI()
  {
    return rb.getString("bankRMI").trim();
  }
}

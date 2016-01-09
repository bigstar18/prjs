package gnnt.MEBS.bank.mgr.core;

import gnnt.trade.bank.util.ErrorCode;
import java.util.Hashtable;

public class BankCoreCode
{
  private static ErrorCode ec;

  public static String getCode(long code)
  {
    if (ec == null) {
      synchronized (BankCoreCode.class) {
        if (ec == null) {
          ec = new ErrorCode();
          ec.load();
        }
      }
    }
    String result = null;
    if (ec != null) {
      result = (String)ErrorCode.error.get(Long.valueOf(code));
    }
    if ((result == null) || (result.trim().length() <= 0)) {
      result = "银行核心返回错误码：" + code;
    }
    return result;
  }
}
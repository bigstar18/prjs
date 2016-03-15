package cn.com.agree.eteller.generic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Amount2RMB
{
  private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
  private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
  private static final String[] UNITS = { "元", "角", "分", "整" };
  private static final String[] U1 = { "", "拾", "佰", "仟" };
  private static final String[] U2 = { "", "万", "亿" };
  
  public static String convert(String amount)
    throws IllegalArgumentException
  {
    amount = amount.replace(",", "");
    if (amount.equals("0.00")) {
      return "零元";
    }
    Matcher matcher = AMOUNT_PATTERN.matcher(amount);
    if (!matcher.find()) {
      throw new IllegalArgumentException("输入金额有误.");
    }
    String integer = matcher.group(1);
    String fraction = matcher.group(2);
    
    String result = "";
    if (!integer.equals("0")) {
      result = result + integer2rmb(integer) + UNITS[0];
    }
    if (fraction.equals("00")) {
      result = result + UNITS[3];
    } else if ((fraction.startsWith("0")) && (integer.equals("0"))) {
      result = result + fraction2rmb(fraction).substring(1);
    } else {
      result = result + fraction2rmb(fraction);
    }
    return result;
  }
  
  private static String fraction2rmb(String fraction)
  {
    char jiao = fraction.charAt(0);
    char fen = fraction.charAt(1);
    return RMB_NUMS[(jiao - '0')] + (jiao > '0' ? UNITS[1] : "") + (
      fen > '0' ? RMB_NUMS[(fen - '0')] + UNITS[2] : "");
  }
  
  private static String integer2rmb(String integer)
  {
    StringBuilder buffer = new StringBuilder();
    

    int i = integer.length() - 1;
    for (int j = 0; i >= 0; j++)
    {
      char n = integer.charAt(i);
      if (n == '0')
      {
        if ((i < integer.length() - 1) && (integer.charAt(i + 1) != '0')) {
          buffer.append(RMB_NUMS[0]);
        }
        if ((j % 4 == 0) && (
          ((i > 0) && (integer.charAt(i - 1) != '0')) || 
          ((i > 1) && (integer.charAt(i - 2) != '0')) || (
          (i > 2) && (integer.charAt(i - 3) != '0')))) {
          buffer.append(U2[(j / 4)]);
        }
      }
      else
      {
        if (j % 4 == 0) {
          buffer.append(U2[(j / 4)]);
        }
        buffer.append(U1[(j % 4)]);
        buffer.append(RMB_NUMS[(n - '0')]);
      }
      i--;
    }
    return buffer.reverse().toString();
  }
}

package gnnt.MEBS.vendue.manage.util;

import javax.servlet.http.HttpServletRequest;

public class CheckBean
{
  private String year = "";
  private String month = "";
  private String day = "";
  private String fromName = "";
  private String fromAccount = "";
  private String fromProvince = "";
  private String fromCity = "";
  private String fromBank = "";
  private String toName = "";
  private String toAccount = "";
  private String toProvince = "";
  private String toCity = "";
  private String toBank = "";
  private double amount = 0.0D;
  private String cashInCH = "";
  private String cashYi = " ";
  private String cashQianW = " ";
  private String cashBaiW = " ";
  private String cashShiW = " ";
  private String cashWan = " ";
  private String cashQian = " ";
  private String cashBai = " ";
  private String cashShi = " ";
  private String cashYuan = " ";
  private String cashJiao = " ";
  private String cashFen = " ";
  private String comments = "";
  private int[] digits = new int[11];
  private String[] cashNum = new String[11];
  
  public void populateBean(HttpServletRequest paramHttpServletRequest)
    throws NumberFormatException
  {
    this.year = paramHttpServletRequest.getParameter("year");
    this.month = paramHttpServletRequest.getParameter("month");
    this.day = paramHttpServletRequest.getParameter("day");
    this.fromName = paramHttpServletRequest.getParameter("fromName");
    this.fromAccount = paramHttpServletRequest.getParameter("fromAccount");
    this.fromProvince = paramHttpServletRequest.getParameter("fromProvince");
    this.fromCity = paramHttpServletRequest.getParameter("fromCity");
    this.fromBank = paramHttpServletRequest.getParameter("fromBank");
    this.toName = paramHttpServletRequest.getParameter("toName");
    this.toAccount = paramHttpServletRequest.getParameter("toAccount");
    this.toProvince = paramHttpServletRequest.getParameter("toProvince");
    this.toCity = paramHttpServletRequest.getParameter("toCity");
    this.toBank = paramHttpServletRequest.getParameter("toBank");
    this.comments = paramHttpServletRequest.getParameter("comments");
    try
    {
      this.amount = new Double(paramHttpServletRequest.getParameter("amount")).doubleValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new NumberFormatException("错误：所填的金额，格式不正确。");
    }
    if (this.amount < 0.01D) {
      throw new NumberFormatException("错误：汇款金额，不能小于1分。");
    }
    if (this.amount >= 1000000000.0D) {
      throw new NumberFormatException("错误：汇款金额，不能大于或等于十亿。");
    }
    formatAmount();
  }
  
  public double getFontSize(double paramDouble1, double paramDouble2)
  {
    int i = this.cashInCH.length();
    if (i * paramDouble2 * 1.1D <= paramDouble1) {
      return paramDouble2;
    }
    return paramDouble1 / i / 1.1D;
  }
  
  private void formatAmount()
  {
    getDigits(this.amount);
    getCashNum(this.digits);
    this.cashYi = getCashNum(this.cashNum[0]);
    this.cashQianW = getCashNum(this.cashNum[1]);
    this.cashBaiW = getCashNum(this.cashNum[2]);
    this.cashShiW = getCashNum(this.cashNum[3]);
    this.cashWan = getCashNum(this.cashNum[4]);
    this.cashQian = getCashNum(this.cashNum[5]);
    this.cashBai = getCashNum(this.cashNum[6]);
    this.cashShi = getCashNum(this.cashNum[7]);
    this.cashYuan = getCashNum(this.cashNum[8]);
    this.cashJiao = getCashNum(this.cashNum[9]);
    this.cashFen = getCashNum(this.cashNum[10]);
    this.cashInCH = getCashWords();
  }
  
  private String getCashWords()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.digits.length; i++) {
      switch (i)
      {
      case 0: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "亿");
        }
        break;
      case 1: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "仟");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 2: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "佰");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 3: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "拾");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 4: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]));
        }
        if (!allZeroes(1, 4)) {
          localStringBuffer.append("万");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 5: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "仟");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 6: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "佰");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 7: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "拾");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 8: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]));
        }
        if (!allZeroes(0, 8)) {
          localStringBuffer.append("元");
        }
        break;
      case 9: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "角");
        } else if (addZero(i)) {
          localStringBuffer.append("零");
        }
        break;
      case 10: 
        if (this.digits[i] != 0) {
          localStringBuffer.append(getWords(this.digits[i]) + "分");
        }
        if (!allZeroes(0, 10)) {
          localStringBuffer.append("整");
        } else {
          localStringBuffer.append("没有钱");
        }
        break;
      }
    }
    return localStringBuffer.toString();
  }
  
  private boolean addZero(int paramInt)
  {
    return (!allZeroes(0, paramInt)) && (this.digits[(paramInt + 1)] != 0);
  }
  
  private boolean allNonZeroes(int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (this.digits[i] == 0) {
        return false;
      }
    }
    return true;
  }
  
  private boolean allZeroes(int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (this.digits[i] != 0) {
        return false;
      }
    }
    return true;
  }
  
  private String getWords(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return "零";
    case 1: 
      return "壹";
    case 2: 
      return "贰";
    case 3: 
      return "叁";
    case 4: 
      return "肆";
    case 5: 
      return "伍";
    case 6: 
      return "陆";
    case 7: 
      return "柒";
    case 8: 
      return "捌";
    case 9: 
      return "玖";
    }
    return "";
  }
  
  private void getCashNum(int[] paramArrayOfInt)
  {
    for (int i = 0; paramArrayOfInt[i] == 0; i++) {}
    if (i >= 1) {
      this.cashNum[(i - 1)] = "￥";
    }
    for (int j = i; j < this.cashNum.length; j++) {
      this.cashNum[j] = new Integer(paramArrayOfInt[j]).toString();
    }
  }
  
  private void getDigits(double paramDouble)
  {
    double d1 = 100000000.0D;
    double d2 = paramDouble;
    for (int i = 0; i < this.digits.length; i++)
    {
      d2 = Arith.format(d2, 2);
      this.digits[i] = divide(d2, d1);
      d2 = remainder(d2, d1);
      d1 /= 10.0D;
    }
  }
  
  private String getCashNum(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString;
  }
  
  private int divide(double paramDouble1, double paramDouble2)
  {
    return (int)(paramDouble1 / paramDouble2);
  }
  
  private double remainder(double paramDouble1, double paramDouble2)
  {
    int i = (int)(paramDouble1 / paramDouble2);
    return paramDouble1 - i * paramDouble2;
  }
  
  public String getFromName()
  {
    return this.fromName;
  }
  
  public String getFromAccount()
  {
    return this.fromAccount;
  }
  
  public String getFromProvince()
  {
    return this.fromProvince;
  }
  
  public String getFromCity()
  {
    return this.fromCity;
  }
  
  public String getFromBank()
  {
    return this.fromBank;
  }
  
  public String getToName()
  {
    return this.toName;
  }
  
  public String getToAccount()
  {
    return this.toAccount;
  }
  
  public String getToProvince()
  {
    return this.toProvince;
  }
  
  public String getToCity()
  {
    return this.toCity;
  }
  
  public String getToBank()
  {
    return this.toBank;
  }
  
  public String getCashInCH()
  {
    return this.cashInCH;
  }
  
  public String getCashYi()
  {
    return this.cashYi;
  }
  
  public String getCashQianW()
  {
    return this.cashQianW;
  }
  
  public String getCashBaiW()
  {
    return this.cashBaiW;
  }
  
  public String getCashShiW()
  {
    return this.cashShiW;
  }
  
  public String getCashWan()
  {
    return this.cashWan;
  }
  
  public String getCashQian()
  {
    return this.cashQian;
  }
  
  public String getCashBai()
  {
    return this.cashBai;
  }
  
  public String getCashShi()
  {
    return this.cashShi;
  }
  
  public String getCashYuan()
  {
    return this.cashYuan;
  }
  
  public String getCashJiao()
  {
    return this.cashJiao;
  }
  
  public String getCashFen()
  {
    return this.cashFen;
  }
  
  public String getComments()
  {
    return this.comments;
  }
  
  public String getYear()
  {
    return this.year;
  }
  
  public String getMonth()
  {
    return this.month;
  }
  
  public String getDay()
  {
    return this.day;
  }
}

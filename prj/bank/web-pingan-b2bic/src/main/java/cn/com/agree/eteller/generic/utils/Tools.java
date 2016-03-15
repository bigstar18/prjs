package cn.com.agree.eteller.generic.utils;

import cn.com.agree.eteller.afa.persistence.AfaSubunitadm;
import cn.com.agree.eteller.afa.persistence.AfaSubunitadmPK;
import cn.com.agree.eteller.afa.persistence.AfaSystem;
import cn.com.agree.eteller.afa.persistence.AfaUnitadm;
import cn.com.agree.eteller.afa.persistence.AfaUnitadmPK;
import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.afa.persistence.AfapSubdict;
import cn.com.agree.eteller.afa.persistence.AfapSubdictPK;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools
{
  private static String pwd = Base64Encoder.encode("888888");
  public static final String INITIAL_PASSWORD = pwd;
  public static final String TELLER_STATE_FREEZE = "1";
  public static final String TELLER_STATE_ACTIVE = "0";
  public static final char ROLE_FINALFLAG_YES = '1';
  public static final char ROLE_FINALFLAG_NO = '0';
  public static final char DEP_FINALFLAG_YES = '1';
  public static final char DEP_FINALFLAG_NO = '0';
  public static final String SELECT_FINALFLAG_ALL = "00000";
  public static final char SER_FINALFLAG_START = '1';
  public static final char SER_FINALFLAG_STOP = '2';
  public static final char SER_FINALFLAG_PLANT = '3';
  public static final int DEPARTMENT_LEVEL_FENHANG = 1;
  public static final int DEPARTMENT_LEVEL_ZHIHANG = 2;
  public static final int DEPARTMENT_LEVEL_WANGDIAN = 3;
  public static final DecimalFormat PERCENT_FORMAT = new DecimalFormat(
    "#.##%");
  
  public static String translateTellerState(String level)
  {
    if ("1".equals(level)) {
      return "冻结";
    }
    if ("0".equals(level)) {
      return "正常";
    }
    return level;
  }
  
  public static String formatRoleNames(Rolelist[] roles)
  {
    String fmtStr = "";
    if (roles != null) {
      for (int i = 0; i < roles.length; i++)
      {
        if (i > 0) {
          fmtStr = fmtStr + ",";
        }
        fmtStr = fmtStr + roles[i].getRoleName();
      }
    }
    return fmtStr;
  }
  
  public static void checkNullInput(String componentInput)
    throws Exception
  {
    if ("".equals(componentInput)) {
      throw new Exception("请确认输入是否完成");
    }
  }
  
  public static void checkCanModify(boolean flag)
    throws Exception
  {
    if (!flag) {
      throw new Exception("该单位已有人员在指定行制卡，因此无法修改对应行");
    }
  }
  
  public static String computeDate(int i)
  {
    Calendar c = new GregorianCalendar();
    int j = 0 - i;
    c.add(5, j);
    int year = c.get(1);
    int month = c.get(2) + 1;
    int day = c.get(5);
    String sy = String.valueOf(year);
    String sm = String.valueOf(month);
    String sd = String.valueOf(day);
    if (month < 10) {
      sm = "0" + sm;
    }
    if (day < 10) {
      sd = "0" + sd;
    }
    return sy + sm + sd;
  }
  
  public static String computeDate(String expDat, int i)
  {
    int year = Integer.valueOf(expDat.substring(0, 4)).intValue();
    int month = Integer.valueOf(expDat.substring(4, 6)).intValue() - 1;
    int date = Integer.valueOf(expDat.substring(6, 8)).intValue();
    Calendar c = new GregorianCalendar(year, month, date);
    c.add(5, i);
    year = c.get(1);
    month = c.get(2) + 1;
    int day = c.get(5);
    String sy = String.valueOf(year);
    String sm = String.valueOf(month);
    String sd = String.valueOf(day);
    if (month < 10) {
      sm = "0" + sm;
    }
    if (day < 10) {
      sd = "0" + sd;
    }
    return sy + sm + sd;
  }
  
  private static Pattern NUMBER_PATTERN = Pattern.compile("\\d*");
  
  public static boolean isNumber(String str)
  {
    return NUMBER_PATTERN.matcher(str).matches();
  }
  
  public static String transToLable(AfapMaindict[] maindict, AfapSubdict[] subdict, String itemename, String code)
  {
    String sysid = "";
    String lable = "无此类型";
    for (int i = 0; i < maindict.length; i++) {
      if (maindict[i].getItemename().equals(itemename))
      {
        sysid = maindict[i].getItem();
        break;
      }
    }
    if (!sysid.equals("")) {
      for (int i = 0; i < subdict.length; i++) {
        if ((subdict[i].getComp_id().getItem().equals(sysid)) && 
          (subdict[i].getComp_id().getCode().equals(code)))
        {
          lable = subdict[i].getCodename();
          break;
        }
      }
    }
    return lable;
  }
  
  public static String transCASHToLable(AfapMaindict[] maindict, AfapSubdict[] subdict, String itemename, String code)
  {
    String sysid = "";
    String lable = "无此类型";
    if ((code.equals("")) || (code.equals("null")))
    {
      lable = "";
    }
    else
    {
      for (int i = 0; i < maindict.length; i++) {
        if (maindict[i].getItemename().equals(itemename))
        {
          sysid = maindict[i].getItem();
          break;
        }
      }
      if (!sysid.equals("")) {
        for (int i = 0; i < subdict.length; i++) {
          if ((subdict[i].getComp_id().getItem().equals(sysid)) && 
            (subdict[i].getComp_id().getCode().equals(code)))
          {
            lable = subdict[i].getCodename();
            break;
          }
        }
      }
    }
    return lable;
  }
  
  public static String transSystem(AfaSystem[] system, String id)
  {
    String lable = "无此类型";
    for (int i = 0; i < system.length; i++) {
      if (system[i].getSysid().equals(id))
      {
        lable = system[i].getSyscname();
        break;
      }
    }
    return lable;
  }
  
  public static String transUnitadm(AfaUnitadm[] unitadm, String sysid, String unitno)
  {
    String lable = "无此类型";
    for (int i = 0; i < unitadm.length; i++) {
      if ((unitadm[i].getComp_id().getSysid().equals(sysid)) && 
        (unitadm[i].getComp_id().getUnitno().equals(unitno)))
      {
        lable = unitadm[i].getUnitname();
        break;
      }
    }
    return lable;
  }
  
  public static String transSubunitadm(AfaSubunitadm[] subunitadm, String sysid, String unitno, String mode, String subunitno)
  {
    String lable = "无此类型";
    if ((subunitno.equals("00000000")) && (!mode.equals("2"))) {
      lable = "全部商户分支";
    } else {
      for (int i = 0; i < subunitadm.length; i++) {
        if ((subunitadm[i].getComp_id().getSysid().equals(sysid)) && 
          (subunitadm[i].getComp_id().getUnitno().equals(unitno)) && 
          (subunitadm[i].getComp_id().getSubunitno().equals(subunitno)))
        {
          lable = subunitadm[i].getSubunitname();
          break;
        }
      }
    }
    return lable;
  }
}

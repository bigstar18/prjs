package gnnt.bank.adapter.util;

import gnnt.bank.adapter.bankBusiness.enumElmt.ReturnCode;
import java.util.Hashtable;
import java.util.Map;

public class ErrorCodeRelation
{
  public Map ErrorCodeM2B = new Hashtable();

  public Map ErrorCodeB2M = new Hashtable();

  public static Hashtable<String, String> error = new Hashtable();

  public ErrorCodeRelation()
  {
    load();
  }

  public void load()
  {
    this.ErrorCodeM2B.put(Long.valueOf(0L), ReturnCode.CODE0000.getValue());
    this.ErrorCodeM2B.put(Long.valueOf(-40001L), ReturnCode.CODE2011.getValue());
    this.ErrorCodeM2B.put(Long.valueOf(-10019L), ReturnCode.CODE2013.getValue());
    this.ErrorCodeM2B.put(Long.valueOf(-10003L), ReturnCode.CODE2013.getValue());
    this.ErrorCodeM2B.put(Long.valueOf(-40011L), ReturnCode.CODE2013.getValue());
    this.ErrorCodeM2B.put(Long.valueOf(-20039L), ReturnCode.CODE2002.getValue());

    this.ErrorCodeM2B.put(Long.valueOf(-1L), ReturnCode.CODE2042.getValue());
    this.ErrorCodeM2B.put(Long.valueOf(-2L), ReturnCode.CODE2042.getValue());

    Object put = this.ErrorCodeB2M.put(ReturnCode.CODE0000.getValue(), Long.valueOf(0L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1001.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1002.getValue(), Long.valueOf(-20042L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1003.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1004.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1005.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1006.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1007.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1008.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1011.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1012.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1013.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1014.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1015.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1016.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1017.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1018.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1019.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1020.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1021.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1022.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1023.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1024.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1025.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1026.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1027.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1028.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1032.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1033.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1034.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1037.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1038.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1039.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1040.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1041.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1042.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1043.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1044.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1050.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1051.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1052.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1053.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1054.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1055.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1056.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1057.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1058.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1059.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1061.getValue(), Long.valueOf(-50004L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1062.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1063.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1064.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1065.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1066.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1067.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1068.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1069.getValue(), Long.valueOf(-50004L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1070.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1071.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1072.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1073.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1074.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1075.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1076.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1077.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1078.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1079.getValue(), Long.valueOf(-920007L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1080.getValue(), Long.valueOf(-20020L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1081.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE1082.getValue(), Long.valueOf(-100L));
    this.ErrorCodeB2M.put(ReturnCode.CODE2369.getValue(), Long.valueOf(-100L));
  }

  public static void loadError()
  {
  }

  public static String getMsg(String code)
  {
    return (String)error.get(code);
  }
}
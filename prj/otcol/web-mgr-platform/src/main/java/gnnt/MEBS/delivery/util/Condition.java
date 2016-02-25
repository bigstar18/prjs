package gnnt.MEBS.delivery.util;

public class Condition
{
  public static final String ENTERAPPLYKIND = "Enter_Apply";
  public static final String ENTERAPPLYOPERATION = "1";
  public static final String ENTERWAREKIND = "Enter_Ware";
  public static final String ENTERWAREOPERATION = "2";
  public static final String OUTWAREKIND = "Out_Ware";
  public static final String OUTWAREOPERATION = "3";
  public static final String SUCCESS = "操作成功！";
  public static final String BADSTATUS = "状态不允许操作！";
  public static final String LOWERACCESS = "操作员权限不对，无法进行！";
  public static final String NOOPERATOR = "未提交操作人员，无法进行！";
  public static final String OPERATESTATUSDIFFERENCEDATASTATUS = "操作状态与数据状态不符！";
  public static final String NOSTATUS = "无状态可操作！";
  public static final String OUTWAREUPTOEXIST = "申请出口数量大于入库单中现存数量！";
  public static final String ENTERWARECANTOUT = "入库单不能进行出库！";
  public static final String OPERATEEXCEPTION = "操作异常！";
  public static final String NOWAYENTERWARESPLIT = "可用数量不足拆单";
}

package gnnt.MEBS.config.constant;

public class NoticeForStatusConstant
{
  public static String NOTICEFORSTATUS = "您的状态从：%%%%%%修改为：######";
  public static String NOTICESPMEMTEMPLATE = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><groups isNotAllDefault=\"Y\"><group id=\"##memberNo##\"><isNotDefaultRole flag=\"Y\"></isNotDefaultRole></group></groups><appoint></appoint></root>";
  public static String NOTICEMEMTEMPLATE = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"member\"><groups isNotAllDefault=\"Y\"><group id=\"##memberNo##\"><isNotDefaultRole flag=\"Y\"></isNotDefaultRole></group></groups><appoint></appoint></range></root>";
  public static String NOTICECOUSOMERTEMPLATE = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"trader\"><group isNotAllCustomers=\"Y\"><member></member></group><appoint>##customerNo##</appoint></range></root>";
  public static String SOURCE = "E";
  public static String TITLE = "状态修改";
  public static int EXPIRYTIME = 30;
}

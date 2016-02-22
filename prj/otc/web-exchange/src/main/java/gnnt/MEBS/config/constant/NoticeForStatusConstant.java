package gnnt.MEBS.config.constant;

public class NoticeForStatusConstant
{
  public static String NOTICEFORSTATUS = "您的状态从：%%%%%%修改为：######";
  public static String NOTICEFORCUSTOMERSTATUS = "##memberNo##的状态从：%%%%%%修改为：######";
  public static String NOTICESPMEMTEMPLATE = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"specialMemeber\"><group isNotAllSpecialMemeber=\"Y\"><specialMemeber>'##memberNo##'</specialMemeber></group><appoint></appoint></range></root>";
  public static String NOTICEMEMTEMPLATE = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"member\"><group isNotAllMember=\"Y\"><member>'##memberNo##'</member><organization></organization><relation>N</relation><brokerage>N</brokerage></group><appoint></appoint></range></root>";
  public static String NOTICECOUSOMERTEMPLATE = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"trader\"><group isNotAllCustomers=\"Y\"><member></member><organization></organization><relation>N</relation></group><appoint>##customerNo##</appoint></range></root>";
  public static String NOTICEMEMBERFORCOUSOMERTEM = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"trader\"><group isNotAllCustomers=\"Y\"><member>'##memberNo##'</member></group></range></root>";
  public static String SOURCE = "E";
  public static String TITLE = "状态修改";
  public static int EXPIRYTIME = 30;
  public static String AUTHORORGANIZATION = "交易所";
  public static String NOTICEMEMBERFORSTATUS = "&&&&&&的状态从：%%%%%%修改为：######";
  public static String NOTICEMEMTEMPLATE1 = "<?xml version=\"1.0\" encoding=\"GBK\" ?><root><range type=\"member\"><group isNotAllMember=\"Y\"><member>##memberNo##</member><organization></organization><relation>N</relation><brokerage>N</brokerage></group><appoint></appoint></range></root>";
}

package gnnt.MEBS.member.ActiveUser;

import java.io.Serializable;

public class TraderInfo
  implements Serializable
{
  public static final String TRADER_TYPE_ADMIN = "A";
  public static final String TRADER_TYPE_NORMAL = "N";
  public String traderId;
  public String traderName;
  public String firmId;
  public String firmName;
  public String type;
  public long auSessionId;
  public int forceChangePwd;
  public String lastTime;
  public String lastIP;
}

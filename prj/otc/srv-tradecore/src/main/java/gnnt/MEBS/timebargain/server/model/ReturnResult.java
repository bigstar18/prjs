package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReturnResult
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  public int retCode;
  public String errMsg = "";
  public List vResult = new ArrayList();
  public List vResult2 = new ArrayList();
  public static final int RETCODE_SUCCESS = 0;
  public static final int RETCODE_ILLEGAL = 1;
  public static final int RETCODE_DATEERR = 2;
}

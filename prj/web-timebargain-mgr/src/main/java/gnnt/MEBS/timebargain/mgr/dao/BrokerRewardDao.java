package gnnt.MEBS.timebargain.mgr.dao;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public abstract interface BrokerRewardDao
{
  public abstract List getBreedStartList();

  public abstract List getBreedEndList();

  public abstract List getBrokerStartList();

  public abstract List getBrokerEndList();

  public abstract List getFirmRewardSum(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract List getBrokerRewardSum(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract List getBreedRewardSum(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract List getBrokerTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List getBreedTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List getFirmTradeFee(HttpServletRequest paramHttpServletRequest);

  public abstract List getBrokerUserFeeList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

  public abstract List getHisDealDetailList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
}
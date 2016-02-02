package gnnt.MEBS.transformhq.server.PricefiterInterFace;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import java.util.LinkedList;
import java.util.Map;

public abstract interface BurrPriceFiterInterFace
{
  public abstract int checkPrice(HQBean paramHQBean);
  
  public abstract void setInCommodity(Map<String, InCommodity> paramMap);
  
  public abstract LinkedList<HQBean> getGlitchPLst(String paramString);
}

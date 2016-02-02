package gnnt.MEBS.transformhq.server.PricefiterInterFace;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.quotation.CheckAndSendHQBean;

public abstract interface SecondPriceFiterInterFace
{
  public abstract void init(CheckAndSendHQBean paramCheckAndSendHQBean);
  
  public abstract void setHQBean(HQBean paramHQBean);
}

package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class FattachExplainType extends StandardModel
{
  private static final long serialVersionUID = 4891757725063823698L;
  private Long attachType;
  private String attachTypeExplain;

  public Long getAttachType()
  {
    return this.attachType;
  }

  public void setAttachType(Long paramLong)
  {
    this.attachType = paramLong;
  }

  public String getAttachTypeExplain()
  {
    return this.attachTypeExplain;
  }

  public void setAttachTypeExplain(String paramString)
  {
    this.attachTypeExplain = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "attachType", this.attachType);
  }
}
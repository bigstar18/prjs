package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CmdtySort extends StandardModel
{

  @ClassDiscription(name="商品分类ID", description="")
  private Long sortID;

  @ClassDiscription(name="分类名称", description="")
  private String sortName;

  public Long getSortID()
  {
    return this.sortID;
  }

  public void setSortID(Long sortID) {
    this.sortID = sortID;
  }

  public String getSortName() {
    return this.sortName;
  }

  public void setSortName(String sortName) {
    this.sortName = sortName;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "sortID", this.sortID);
  }
}
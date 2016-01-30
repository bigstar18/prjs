package gnnt.MEBS.timebargain.mgr.dao;

import gnnt.MEBS.timebargain.mgr.model.firmSet.TradePrivilege;
import java.util.List;

public abstract interface TradePrivilegeDao
{
  public abstract List getFirmPrivilege(String paramString);

  public abstract List getCustomerPrivilege(String paramString);

  public abstract List getTraderPrivilege(String paramString);

  public abstract List getBreed();

  public abstract List getCommodity();

  public abstract String getOperateCode(String paramString);

  public abstract List getCodeNotChoose(String paramString1, String paramString2);

  public abstract void deleteTradePrivilege(TradePrivilege paramTradePrivilege)
    throws Exception;

  public abstract void batchSetInsertFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, int paramInt4);

  public abstract void batchEmptyDeleteFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2);
}
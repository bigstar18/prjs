package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.TraderDAO;
import gnnt.MEBS.timebargain.manage.model.Consigner;
import gnnt.MEBS.timebargain.manage.model.TradeTime;
import gnnt.MEBS.timebargain.manage.model.Trader;
import gnnt.MEBS.timebargain.manage.service.TraderManager;
import java.util.List;
import java.util.Map;

public class TraderManagerImpl
  extends BaseManager
  implements TraderManager
{
  private TraderDAO dao;
  
  public void setTraderDAO(TraderDAO paramTraderDAO)
  {
    this.dao = paramTraderDAO;
  }
  
  public Trader getTraderById(String paramString)
  {
    return this.dao.getTraderById(paramString);
  }
  
  public List getTraders(Trader paramTrader)
  {
    return this.dao.getTraders(paramTrader);
  }
  
  public void insertTrader(Trader paramTrader)
  {
    this.dao.insertTrader(paramTrader);
  }
  
  public void updateTrader(Trader paramTrader)
  {
    this.dao.updateTrader(paramTrader);
  }
  
  public void deleteTraderById(String paramString)
  {
    this.dao.deleteTraderById(paramString);
  }
  
  public int login(String paramString1, String paramString2)
  {
    return this.dao.login(paramString1, paramString2);
  }
  
  public void updateTraderPassword(String paramString1, String paramString2, String paramString3)
  {
    this.dao.updateTraderPassword(paramString1, paramString2, paramString3);
  }
  
  public String getCodeNotChoose(Trader paramTrader)
    throws Exception
  {
    return getNotSelectOption(this.dao.getCodeNotChoose(paramTrader));
  }
  
  public String getOperateCode(Trader paramTrader)
    throws Exception
  {
    return getSelectOption(this.dao.getOperateCode(paramTrader));
  }
  
  private String getNotSelectOption(List paramList)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      Map localMap = (Map)paramList.get(j);
      localStringBuffer.append("<option value=\"" + (String)localMap.get("code") + "\">" + (String)localMap.get("code") + "</option>");
    }
    return localStringBuffer.toString();
  }
  
  private String getSelectOption(String paramString)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString != null) && (!"".equals(paramString)))
    {
      String[] arrayOfString = paramString.split(",");
      for (int i = 0; i < arrayOfString.length; i++) {
        localStringBuffer.append("<option value=\"" + arrayOfString[i] + "\">" + arrayOfString[i] + "</option>");
      }
    }
    return localStringBuffer.toString();
  }
  
  public void updateOperateCode(Trader paramTrader)
  {
    this.dao.updateOperateCode(paramTrader);
  }
  
  public String getOperateFirm(Consigner paramConsigner)
    throws Exception
  {
    return getSelectOptionFirm(this.dao.getOperateFirm(paramConsigner));
  }
  
  private String getSelectOptionFirm(String paramString)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString != null) && (!"".equals(paramString)))
    {
      String[] arrayOfString = paramString.split(",");
      if (arrayOfString != null) {
        for (int i = 0; i < arrayOfString.length; i++) {
          localStringBuffer.append("<option value=\"" + arrayOfString[i] + "\">" + arrayOfString[i] + "</option>");
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public String getNotOperateFirm(Consigner paramConsigner)
    throws Exception
  {
    return getNotSelectOptionFirm(this.dao.getNotOperateFirm(paramConsigner));
  }
  
  private String getNotSelectOptionFirm(List paramList)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      Map localMap = (Map)paramList.get(j);
      localStringBuffer.append("<option value=\"" + (String)localMap.get("firmID") + "\">" + (String)localMap.get("firmID") + "</option>");
    }
    return localStringBuffer.toString();
  }
  
  public List getTradeTimes(TradeTime paramTradeTime)
  {
    return this.dao.getTradeTimes(paramTradeTime);
  }
  
  public TradeTime getTradeTime(String paramString)
  {
    return this.dao.getTradeTime(paramString);
  }
  
  public void insertTradeTime(TradeTime paramTradeTime)
  {
    this.dao.insertTradeTime(paramTradeTime);
  }
  
  public void updateTradeTime(TradeTime paramTradeTime)
  {
    this.dao.updateTradeTime(paramTradeTime);
  }
  
  public void deleteTradeTimeById(String paramString)
  {
    this.dao.deleteTradeTimeById(paramString);
  }
  
  public void updateStatusT(TradeTime paramTradeTime)
  {
    this.dao.updateStatusT(paramTradeTime);
  }
  
  public void updateStatusTrader(Trader paramTrader)
  {
    this.dao.updateStatusTrader(paramTrader);
  }
  
  public List getTradeTimeBreed(String paramString)
  {
    return this.dao.getTradeTimeBreed(paramString);
  }
  
  public List getConsigner()
  {
    return this.dao.getConsigner();
  }
  
  public Consigner getConsigner(String paramString)
  {
    return this.dao.getConsigner(paramString);
  }
  
  public void insertConsigner(Consigner paramConsigner)
  {
    this.dao.insertConsigner(paramConsigner);
  }
  
  public void updateConsigner(Consigner paramConsigner)
  {
    this.dao.updateConsigner(paramConsigner);
  }
  
  public void deleteConsignerById(String paramString)
  {
    this.dao.deleteConsignerById(paramString);
  }
  
  public void updateStatusConsigner(Consigner paramConsigner)
  {
    this.dao.updateStatusConsigner(paramConsigner);
  }
  
  public void updateOperateFirm(Consigner paramConsigner)
  {
    this.dao.updateOperateFirm(paramConsigner);
  }
  
  public void updateConsignerPassword(String paramString1, String paramString2, String paramString3)
  {
    this.dao.updateConsignerPassword(paramString1, paramString2, paramString3);
  }
  
  public List getTradeTimeRelBreed(String paramString)
  {
    return this.dao.getTradeTimeRelBreed(paramString);
  }
  
  public TradeTime getNotTradeDay()
  {
    return this.dao.getNotTradeDay();
  }
  
  public void insertNotTradeDay(TradeTime paramTradeTime)
  {
    this.dao.insertNotTradeDay(paramTradeTime);
  }
  
  public void updateNotTradeDay(TradeTime paramTradeTime)
  {
    this.dao.updateNotTradeDay(paramTradeTime);
  }
  
  public List getTraderPrivilege(String paramString)
  {
    return this.dao.getTraderPrivilege(paramString);
  }
  
  public void deleteTraderPrivilege(String paramString)
  {
    this.dao.deleteTraderPrivilege(paramString);
  }
  
  public void insertTraderPrivilege(Trader paramTrader)
  {
    this.dao.insertTraderPrivilege(paramTrader);
  }
  
  public Trader getTraderPrivilegeById(Trader paramTrader)
  {
    return this.dao.getTraderPrivilegeById(paramTrader);
  }
  
  public void insertNewTraderPrivilege(Trader paramTrader)
  {
    this.dao.insertNewTraderPrivilege(paramTrader);
  }
  
  public void updateNewTraderPrivilege(Trader paramTrader)
  {
    this.dao.updateNewTraderPrivilege(paramTrader);
  }
  
  public void deleteTraderPrivilegeById(Trader paramTrader)
  {
    this.dao.deleteTraderPrivilegeById(paramTrader);
  }
  
  public String getSysdate()
  {
    return this.dao.getSysdate();
  }
  
  public List getTradeTimeId(String paramString)
  {
    return this.dao.getTradeTimeId(paramString);
  }
  
  public Map getDaySectionInfo()
  {
    return this.dao.getDaySectionInfo();
  }
  
  public void updateDaySection(Map paramMap)
  {
    this.dao.updateDaySection(paramMap);
  }
}

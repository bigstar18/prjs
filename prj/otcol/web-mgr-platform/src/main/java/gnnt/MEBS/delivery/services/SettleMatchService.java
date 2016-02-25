package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.SettleMatchDao;
import gnnt.MEBS.delivery.model.inner.SettleMatchRelated;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_settleMatchService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SettleMatchService
{
  private final transient Log logger = LogFactory.getLog(SettleMatchService.class);
  @Autowired
  @Qualifier("w_settleMatchDao")
  private SettleMatchDao settleMatchDao;
  @Autowired
  @Qualifier("w_toolService")
  private ToolService toolService;
  @Autowired
  @Qualifier("w_moneyDoService")
  private MoneyDoService moneyDoService;
  
  public List getSettleMatchList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.settleMatchDao.getSettleMatchList(paramQueryConditions, paramPageInfo);
  }
  
  public List getMatchSettleholdRelevanceList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.settleMatchDao.getMatchSettleholdRelevanceList(paramQueryConditions, paramPageInfo);
  }
  
  public void updateSettleMatch(SettleMatch paramSettleMatch)
  {
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public SettleMatch getSettleMatchById(String paramString)
  {
    QueryConditions localQueryConditions = new QueryConditions("matchId", "=", paramString);
    SettleMatch localSettleMatch = null;
    List localList = this.settleMatchDao.getSettleMatch(localQueryConditions);
    if (localList.size() > 0) {
      localSettleMatch = (SettleMatch)localList.get(0);
    }
    return localSettleMatch;
  }
  
  public SettleMatch getSettleMatchLock(String paramString)
  {
    return this.settleMatchDao.getSettleMatchLock(paramString);
  }
  
  public long createSettleMatch(SettleMatch paramSettleMatch, String paramString)
  {
    return this.settleMatchDao.createSettleMatch(paramSettleMatch, paramString);
  }
  
  public void addRelated(SettleMatchRelated paramSettleMatchRelated)
  {
    this.settleMatchDao.addRelated(paramSettleMatchRelated);
  }
  
  public void HLSettle(SettleMatch paramSettleMatch, double paramDouble, boolean paramBoolean)
  {
    double d = paramSettleMatch.getHL_Amount();
    if (paramBoolean) {
      d += paramDouble;
    } else {
      d -= paramDouble;
    }
    this.logger.debug("HL_Amount:" + d);
    paramSettleMatch.setStatus(1);
    paramSettleMatch.setModifyTime(new Date());
    paramSettleMatch.setHL_Amount(d);
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public void inoutSettleMoney(SettleMatch paramSettleMatch, double paramDouble, boolean paramBoolean)
  {
    double d1 = paramSettleMatch.getBuyPayout();
    double d2 = paramSettleMatch.getSellIncome();
    String str1 = paramSettleMatch.getCommodityId();
    String str2 = "";
    String str3 = "";
    if (paramBoolean)
    {
      str2 = paramSettleMatch.getFirmID_B();
      str3 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "Payout");
      d1 += paramDouble;
      this.logger.debug("BfirmId:" + str2);
    }
    else
    {
      str2 = paramSettleMatch.getFirmID_S();
      str3 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "Income");
      d2 += paramDouble;
      this.logger.debug("SfirmId:" + str2);
    }
    this.moneyDoService.updateFirmFunds(str2, str3, paramDouble, paramSettleMatch.getMatchId(), str1);
    paramSettleMatch.setStatus(1);
    paramSettleMatch.setModifyTime(new Date());
    paramSettleMatch.setSellIncome(d2);
    paramSettleMatch.setBuyPayout(d1);
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public void settleChangeStatus(SettleMatch paramSettleMatch, int paramInt)
  {
    paramSettleMatch.setResult(paramInt);
    paramSettleMatch.setModifyTime(new Date());
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public void returnMargin(SettleMatch paramSettleMatch, double paramDouble, boolean paramBoolean)
  {
    double d1 = paramSettleMatch.getSellIncome_Ref();
    double d2 = paramSettleMatch.getBuyPayout_Ref();
    double d3 = d1 - d2;
    double d4 = paramSettleMatch.getBuyPayout();
    double d5 = paramSettleMatch.getSellIncome();
    double d6 = paramSettleMatch.getBuyMargin();
    double d7 = paramSettleMatch.getSellMargin();
    this.logger.debug("sellMargin:" + d7);
    String str1 = "";
    if (paramBoolean)
    {
      str1 = paramSettleMatch.getFirmID_B();
      d6 -= paramDouble;
    }
    else
    {
      str1 = paramSettleMatch.getFirmID_S();
      d7 -= paramDouble;
    }
    this.logger.debug("sellMargin1:" + d7);
    long l = paramSettleMatch.getBreedId();
    String str2 = paramSettleMatch.getCommodityId();
    String str3 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "ReturnMargin");
    this.moneyDoService.updateFirmFunds(str1, str3, paramDouble, paramSettleMatch.getMatchId(), str2);
    paramSettleMatch.setStatus(1);
    paramSettleMatch.setModifyTime(new Date());
    paramSettleMatch.setBuyMargin(d6);
    paramSettleMatch.setSellMargin(d7);
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
    this.moneyDoService.updateSettleMargin(paramSettleMatch.getModuleId(), str1, paramDouble);
  }
  
  public void receiveOrPayPenalty(SettleMatch paramSettleMatch, double paramDouble, boolean paramBoolean1, boolean paramBoolean2)
  {
    String str1 = "";
    double d1 = paramSettleMatch.getPenalty_B();
    double d2 = paramSettleMatch.getPenalty_S();
    long l = paramSettleMatch.getBreedId();
    String str2 = paramSettleMatch.getCommodityId();
    if (paramBoolean1)
    {
      str1 = paramSettleMatch.getFirmID_B();
      d1 += paramDouble;
    }
    else
    {
      str1 = paramSettleMatch.getFirmID_S();
      d2 += paramDouble;
    }
    String str3 = "";
    if (paramBoolean2) {
      str3 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "ReceivePenalty");
    } else {
      str3 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "PayPenalty");
    }
    this.moneyDoService.updateFirmFunds(str1, str3, paramDouble, paramSettleMatch.getMatchId(), str2);
    paramSettleMatch.setStatus(1);
    paramSettleMatch.setModifyTime(new Date());
    paramSettleMatch.setPenalty_B(d1);
    paramSettleMatch.setPenalty_S(d2);
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public void addSettlePL(SettleMatch paramSettleMatch, double paramDouble, boolean paramBoolean)
  {
    String str1 = "";
    String str2 = "";
    double d1 = paramSettleMatch.getSettlePL_B();
    double d2 = paramSettleMatch.getSettlePL_S();
    long l = paramSettleMatch.getBreedId();
    String str3 = paramSettleMatch.getCommodityId();
    if (paramBoolean)
    {
      str1 = paramSettleMatch.getFirmID_B();
      d1 += paramDouble;
    }
    else
    {
      str1 = paramSettleMatch.getFirmID_S();
      d2 += paramDouble;
    }
    if (paramDouble > 0.0D)
    {
      str2 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "ReceivePL");
    }
    else
    {
      str2 = this.toolService.getOprcode(paramSettleMatch.getModuleId(), "PayPL");
      paramDouble = -paramDouble;
    }
    this.moneyDoService.updateFirmFunds(str1, str2, paramDouble, paramSettleMatch.getMatchId(), str3);
    paramSettleMatch.setStatus(1);
    paramSettleMatch.setModifyTime(new Date());
    paramSettleMatch.setSettlePL_B(d1);
    paramSettleMatch.setSettlePL_S(d2);
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public Map getSettleMatchInit(SettleMatch paramSettleMatch)
  {
    HashMap localHashMap = null;
    if (paramSettleMatch != null)
    {
      String str1 = paramSettleMatch.getInitXml();
      if ((str1 != null) && (!"".equals(str1)))
      {
        Document localDocument = null;
        try
        {
          localDocument = DocumentHelper.parseText(str1);
        }
        catch (DocumentException localDocumentException)
        {
          localDocumentException.printStackTrace();
        }
        Element localElement1 = localDocument.getRootElement();
        Iterator localIterator = localElement1.elementIterator();
        localHashMap = new HashMap();
        while (localIterator.hasNext())
        {
          Element localElement2 = (Element)localIterator.next();
          String str2 = localElement2.getName();
          String str3 = localElement2.getText();
          localHashMap.put(str2, str3);
        }
      }
    }
    return localHashMap;
  }
  
  public void settleMatchRestore(SettleMatch paramSettleMatch)
  {
    String str1 = paramSettleMatch.getCommodityId();
    long l = this.moneyDoService.reverseFundflow(paramSettleMatch.getMatchId(), Long.parseLong(this.toolService.getXmlNode(paramSettleMatch.getInitXml(), "LASTFUNDFLOWID")), str1, paramSettleMatch.getModuleId());
    String str2 = paramSettleMatch.getRegStockId();
    Map localMap = getSettleMatchInit(paramSettleMatch);
    paramSettleMatch = (SettleMatch)this.toolService.rsToObject(localMap, paramSettleMatch);
    paramSettleMatch.setSellIncome(0.0D);
    paramSettleMatch.setPenalty_B(0.0D);
    paramSettleMatch.setPenalty_S(0.0D);
    paramSettleMatch.setHL_Amount(0.0D);
    paramSettleMatch.setSettlePL_B(0.0D);
    paramSettleMatch.setSettlePL_S(0.0D);
    paramSettleMatch.setModifyTime(new Date());
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
    this.settleMatchDao.updateFundflowId(paramSettleMatch.getMatchId(), l);
  }
  
  public void receivedSettleInvoice(SettleMatch paramSettleMatch)
  {
    paramSettleMatch.setRecvInvoice(1);
    paramSettleMatch.setModifyTime(new Date());
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public void cancelSettle(SettleMatch paramSettleMatch)
  {
    long l = paramSettleMatch.getBreedId();
    String str = paramSettleMatch.getCommodityId();
    this.moneyDoService.reverseFundflow(paramSettleMatch.getMatchId(), Long.parseLong(this.toolService.getXmlNode(paramSettleMatch.getInitXml(), "LASTFUNDFLOWID")), str, paramSettleMatch.getModuleId());
    paramSettleMatch.setStatus(3);
    paramSettleMatch.setModifyTime(new Date());
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public void cancelT_SettleMatch(String paramString)
  {
    this.settleMatchDao.cancelT_SettleMatch(paramString);
  }
  
  public void finshSettle(SettleMatch paramSettleMatch)
  {
    paramSettleMatch.setStatus(2);
    paramSettleMatch.setModifyTime(new Date());
    this.settleMatchDao.updateSettleMatch(paramSettleMatch);
  }
  
  public double getRelatedChildAmount(String paramString)
  {
    double d = 0.0D;
    QueryConditions localQueryConditions = new QueryConditions("matchid", "in", "(select t.childmatchid from s_settlematchrelated t where t.parentmatchid='" + paramString + "')");
    List localList = this.settleMatchDao.getSettleMatch(localQueryConditions);
    if (localList.size() > 0)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        SettleMatch localSettleMatch = (SettleMatch)localIterator.next();
        d += localSettleMatch.getWeight();
      }
    }
    return d;
  }
  
  public int balance()
  {
    return this.settleMatchDao.balance();
  }
  
  public void restoreMatchQuantity(String paramString)
  {
    List localList = this.settleMatchDao.getMatchSettleholdRelevanceList(paramString);
    if (localList.size() > 0)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        this.settleMatchDao.updateMatchQuantity(Long.parseLong(((Map)localObject).get("SettleID").toString()), Long.parseLong(((Map)localObject).get("Quantity").toString()));
      }
      this.settleMatchDao.deleteMatchSettleholdRel(paramString);
    }
  }
  
  public void releaseRegStock(String paramString1, String paramString2)
  {
    try
    {
      this.settleMatchDao.releaseRegStock(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public double getContractFactor(String paramString)
  {
    double d = 0.0D;
    try
    {
      d = this.settleMatchDao.getContractFactor(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return d;
  }
}

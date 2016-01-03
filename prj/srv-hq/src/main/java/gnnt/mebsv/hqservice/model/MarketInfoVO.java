package gnnt.mebsv.hqservice.model;

import gnnt.mebsv.hqservice.model.pojo.MarketVO;
import java.util.HashMap;
import java.util.Map;

public class MarketInfoVO
{
  private Map<String, MarketVO> marketInfo = new HashMap();

  public Map<String, MarketVO> getMarketInfo()
  {
    return this.marketInfo;
  }

  public void setMarketInfo(Map<String, MarketVO> paramMap)
  {
    this.marketInfo = paramMap;
  }

  public MarketVO getMaket(String paramString)
  {
    return (MarketVO)this.marketInfo.get(paramString);
  }

  public void putMarket(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    putMarket(initMarket(paramString1, paramString2, paramString3, paramString4));
  }

  public void putMarket(MarketVO paramMarketVO)
  {
    this.marketInfo.put(paramMarketVO.getMarketID(), paramMarketVO);
  }

  public MarketVO initMarket(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    MarketVO localMarketVO = new MarketVO();
    localMarketVO.setMarketID(paramString1);
    localMarketVO.setMarketName(paramString2);
    localMarketVO.setDirection(paramString3);
    localMarketVO.setDescription(paramString4);
    return localMarketVO;
  }
}
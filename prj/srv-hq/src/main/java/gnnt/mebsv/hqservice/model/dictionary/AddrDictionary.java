package gnnt.mebsv.hqservice.model.dictionary;

import gnnt.mebsv.hqservice.model.pojo.AddrVO;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class AddrDictionary
{
  private Map<String, AddrVO> addrDic = new HashMap();

  public Map<String, AddrVO> getAddrDic()
  {
    return this.addrDic;
  }

  public void setAddrCode(Map<String, AddrVO> paramMap)
  {
    this.addrDic = paramMap;
  }

  public AddrVO getAddr(String paramString)
  {
    return (AddrVO)this.addrDic.get(paramString);
  }

  public void putAddr(String paramString1, String paramString2, Date paramDate)
  {
    putAddr(initAddr(paramString1, paramString2, paramDate));
  }

  public void putAddr(AddrVO paramAddrVO)
  {
    this.addrDic.put(paramAddrVO.getAddrCode(), paramAddrVO);
  }

  public AddrVO initAddr(String paramString1, String paramString2, Date paramDate)
  {
    AddrVO localAddrVO = new AddrVO();
    localAddrVO.setAddrCode(paramString1);
    localAddrVO.setAddrName(paramString2);
    localAddrVO.setUpdateTime(paramDate);
    return localAddrVO;
  }
}
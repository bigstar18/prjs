package gnnt.mebsv.hqservice.model.dictionary;

import gnnt.mebsv.hqservice.model.pojo.IndustryVO;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class IndustryDictionary
{
  private Map<String, IndustryVO> industryDic = new HashMap();

  public Map<String, IndustryVO> getIndustryDic()
  {
    return this.industryDic;
  }

  public void setIndustryDic(Map<String, IndustryVO> paramMap)
  {
    this.industryDic = paramMap;
  }

  public IndustryVO getIndustryr(String paramString)
  {
    return (IndustryVO)this.industryDic.get(paramString);
  }

  public void putIndustry(String paramString1, String paramString2, Date paramDate)
  {
    putIndustry(initIndustryVO(paramString1, paramString2, paramDate));
  }

  public void putIndustry(IndustryVO paramIndustryVO)
  {
    this.industryDic.put(paramIndustryVO.getIndustryCode(), paramIndustryVO);
  }

  public IndustryVO initIndustryVO(String paramString1, String paramString2, Date paramDate)
  {
    IndustryVO localIndustryVO = new IndustryVO();
    localIndustryVO.setIndustryCode(paramString1);
    localIndustryVO.setIndustryName(paramString2);
    localIndustryVO.setUpdateTime(paramDate);
    return localIndustryVO;
  }
}
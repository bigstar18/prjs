package gnnt.mebsv.hqservice.service.dictionaryserver.impl;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.dictionary.AddrDictionary;
import gnnt.mebsv.hqservice.model.dictionary.IndustryDictionary;
import gnnt.mebsv.hqservice.service.dictionaryserver.IDictionaryServer;
import java.util.Date;

public class DictionaryServer
  implements IDictionaryServer
{
  HQDAO dao;

  public DictionaryServer()
  {
    try
    {
      QuotationServer.getInstance();
      this.dao = QuotationServer.getCurDataDAO();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public IndustryDictionary getIndustryDictionary(Date paramDate)
    throws Exception
  {
    return this.dao.getIndustryDictionary(paramDate);
  }

  public AddrDictionary getAddrDictionary(Date paramDate)
    throws Exception
  {
    return this.dao.getAddrDictionary(paramDate);
  }
}
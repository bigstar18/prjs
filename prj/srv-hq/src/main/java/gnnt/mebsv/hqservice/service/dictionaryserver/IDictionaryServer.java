package gnnt.mebsv.hqservice.service.dictionaryserver;

import gnnt.mebsv.hqservice.model.dictionary.AddrDictionary;
import gnnt.mebsv.hqservice.model.dictionary.IndustryDictionary;
import java.util.Date;

public abstract interface IDictionaryServer
{
  public abstract IndustryDictionary getIndustryDictionary(Date paramDate)
    throws Exception;

  public abstract AddrDictionary getAddrDictionary(Date paramDate)
    throws Exception;
}
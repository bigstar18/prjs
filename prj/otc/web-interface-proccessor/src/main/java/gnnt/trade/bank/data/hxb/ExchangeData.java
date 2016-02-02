package gnnt.trade.bank.data.hxb;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public abstract interface ExchangeData
{
  public abstract List<QS> getQS(String paramString, Date paramDate);
  
  public abstract List<DZ> getDZ(String paramString, Date paramDate);
  
  public abstract List<ZZH> getZZH(String paramString1, String paramString2, Connection paramConnection);
}

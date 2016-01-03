package gnnt.mebsv.hqservice.service.flash.flashcurdata.subclass;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.service.flash.flashcurdata.BFlashDataServer;
import java.sql.SQLException;
import java.util.Date;

public class FlashDataForDB extends BFlashDataServer
{
  HQDAO curDataDAO = QuotationServer.getCurDataDAO();

  public FlashDataForDB(QuotationServer paramQuotationServer)
  {
    super(paramQuotationServer);
  }

  public boolean flashCurData()
    throws SQLException
  {
    int i = this.curDataDAO.getAllCurData(QuotationServer.htProductData, quotationServer.cclass_id, quotationServer.cclass_bq, quotationServer.buySell);
    quotationServer.m_time = this.curDataDAO.getHqTime();
    calculateProductDataVO();
    return i != 0;
  }

  public Date getHQTime()
  {
    return this.curDataDAO.getHQDate();
  }
}
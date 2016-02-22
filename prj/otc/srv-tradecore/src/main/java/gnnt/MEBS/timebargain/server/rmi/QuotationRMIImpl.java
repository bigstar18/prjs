package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.quotation.QuotationEngine;
import gnnt.MEBS.timebargain.server.quotation.QuotationInterface;
import gnnt.quotation.ReceiveQuotation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationRMIImpl
  extends UnicastRemoteObject
  implements QuotationRMI
{
  private static final long serialVersionUID = 2690197350654049816L;
  private Log log = LogFactory.getLog(getClass());
  private QuotationInterface quotationInterface;
  private Server server;
  
  public QuotationRMIImpl(Server server)
    throws RemoteException
  {
    this.server = server;
    this.quotationInterface = ((QuotationInterface)DAOBeanFactory.getBean("quotationInterface"));
  }
  
  public int setQuotation(Quotation quotation)
    throws RemoteException
  {
    int ret = 0;
    try
    {
      this.quotationInterface.setQuotation(quotation);
      ret = 1;
    }
    catch (Exception e)
    {
      ret = -1;
      e.printStackTrace();
      this.log.debug("error in class QuotationRMIImpl setQuotation method.....");
    }
    finally
    {
      return ret;
    }
  }
  
  public void refreshHQserverinfo()
    throws RemoteException
  {
    this.log.info("refreshHQserverinfo!");
    this.server.getQuotationEngine().getReceiveQuotation().refreshHQserverinfo();
  }
}

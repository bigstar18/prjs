package gnnt.trade.bank.dao;

import gnnt.trade.bank.dao.ceb.CEBBankDao;
import gnnt.trade.bank.dao.cgb.CGBBankDAO;
import gnnt.trade.bank.dao.citic.CITICBankDAO;
import gnnt.trade.bank.dao.hxb.HXBBankDAO;
import gnnt.trade.bank.dao.icbc.ICBCBankDAO;
import gnnt.trade.bank.dao.icbce.ICBCEBankDAO;
import gnnt.trade.bank.dao.jsb.JSBBankDAO;
import gnnt.trade.bank.dao.up.UPBankDAO;

public class BankDAOFactory
{
  public static BankDAO getDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    BankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.BankDAOOracle";
    dao = (BankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CCBBankDAO getCCBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CCBBankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.CCBBankDAOOracle";
    dao = (CCBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static ABCBankDAO getABCDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    ABCBankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.ABCBankDAOOracle";
    dao = (ABCBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static YjfBankDAO getYjfDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    YjfBankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.YjfBankDAOOracle";
    dao = (YjfBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static BOCBankDAO getBOCDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    BOCBankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.BOCBankDAOOracle";
    dao = (BOCBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static JSBBankDAO getJSBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    JSBBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.jsb.JSBBankDAOOracle";
    dao = (JSBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static HZBankDAO getHZDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    HZBankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.HZBankDAOOracle";
    dao = (HZBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static ICBCBankDAO getICBCBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    ICBCBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.icbc.ICBCBankDAOOracle";
    dao = (ICBCBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static HXBBankDAO getHXBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    HXBBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.hxb.HXBBankDAOOracle";
    dao = (HXBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static GFBBankDAO getGFBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    GFBBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.GFBBankDAOOracle";
    dao = (GFBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CMBBankDAO getCMBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CMBBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.CMBBankDAOOracle";
    dao = (CMBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CEBBankDao getCEBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CEBBankDao dao = null;
    String className = "gnnt.trade.bank.dao.ceb.CEBBankDaoOracle";
    dao = (CEBBankDao)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CIBBankDAO getZXDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CIBBankDAO dao = null;
    

    String className = "gnnt.trade.bank.dao.CIBBankDAOOracle";
    dao = (CIBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CMBCBankDAO getCMBCDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CMBCBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.CMBCBankDAOOracle";
    dao = (CMBCBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static ICBCEBankDAO getICBCEDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    ICBCEBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.icbce.ICBCEBankDAOOracle";
    dao = (ICBCEBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CITICBankDAO getCITICDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CITICBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.citic.CITICBankDAOOracle";
    dao = (CITICBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static PABankDAO getPADAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    PABankDAO dao = null;
    String className = "gnnt.trade.bank.dao.PABankDAOOracle";
    dao = (PABankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static UPBankDAO getUPDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    UPBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.up.UPBankDAOOracle";
    dao = (UPBankDAO)Class.forName(className).newInstance();
    return dao;
  }
  
  public static CGBBankDAO getCGBDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    CGBBankDAO dao = null;
    String className = "gnnt.trade.bank.dao.cgb.CGBBankDAOOracle";
    dao = (CGBBankDAO)Class.forName(className).newInstance();
    return dao;
  }
}

package gnnt.MEBS.billWarehoursInterface.encrypt;

import gnnt.MEBS.billWarehoursInterface.client.impl.Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DES3EncryptIMP
  implements IEnCrypt
{
  protected final transient Log logger = LogFactory.getLog(Client.class);
  String strKey;
  
  public String getStrKey()
  {
    return this.strKey;
  }
  
  public void setStrKey(String strKey)
  {
    this.strKey = strKey;
  }
  
  public String decrypt(String msg)
  {
    try
    {
      return DES3Encrypt.decrypt(this.strKey, msg);
    }
    catch (EncryptException e)
    {
      e.printStackTrace();
      this.logger.error(e.toString());
    }
    return null;
  }
  
  public String encrypt(String msg)
  {
    try
    {
      return DES3Encrypt.encrypt(this.strKey, msg);
    }
    catch (EncryptException e)
    {
      e.printStackTrace();
      this.logger.error(e.toString());
    }
    return null;
  }
}

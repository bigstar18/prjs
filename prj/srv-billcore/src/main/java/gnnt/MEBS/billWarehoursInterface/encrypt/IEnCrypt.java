package gnnt.MEBS.billWarehoursInterface.encrypt;

public abstract interface IEnCrypt
{
  public abstract String encrypt(String paramString);
  
  public abstract String decrypt(String paramString);
}

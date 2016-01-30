package gnnt.mebsv.hqservice.service;

import java.util.Vector;

public abstract class ResponderX extends Thread
{
  public Vector clientPool = null;
  private ServiceManagerX manager = null;

  public void setSocketPool(Vector paramVector)
  {
    this.clientPool = paramVector;
  }

  public void setManager(ServiceManagerX paramServiceManagerX)
  {
    this.manager = paramServiceManagerX;
  }

  public void initResponder()
  {
  }

  public ServiceManagerX getManager()
  {
    return this.manager;
  }
}
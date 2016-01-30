package gnnt.MEBS.common.broker.model;

public class MyMenu extends StandardModel
{
  private static final long serialVersionUID = 1L;
  private Broker broker;
  private Right right;

  public Broker getBroker()
  {
    return this.broker;
  }

  public void setBroker(Broker paramBroker)
  {
    this.broker = paramBroker;
  }

  public Right getRight()
  {
    return this.right;
  }

  public void setRight(Right paramRight)
  {
    this.right = paramRight;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
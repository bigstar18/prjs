package gnnt.MEBS.delivery.workflow;

public abstract interface Precondition
{
  public abstract int doPrecondition(OriginalModel paramOriginalModel);
}

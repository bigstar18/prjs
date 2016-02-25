package gnnt.MEBS.delivery.workflow;

public abstract interface Condition
{
  public abstract boolean check(OriginalModel paramOriginalModel, Handle paramHandle);
}

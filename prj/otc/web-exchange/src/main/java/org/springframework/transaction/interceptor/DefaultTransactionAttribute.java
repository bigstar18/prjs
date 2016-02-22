package org.springframework.transaction.interceptor;

import org.springframework.transaction.support.DefaultTransactionDefinition;

public class DefaultTransactionAttribute
  extends DefaultTransactionDefinition
  implements TransactionAttribute
{
  public DefaultTransactionAttribute() {}
  
  public DefaultTransactionAttribute(TransactionAttribute other)
  {
    super(other);
  }
  
  public DefaultTransactionAttribute(int propagationBehavior)
  {
    super(propagationBehavior);
  }
  
  public boolean rollbackOn(Throwable ex)
  {
    boolean sign = ((ex instanceof Exception)) || ((ex instanceof Error));
    return sign;
  }
}

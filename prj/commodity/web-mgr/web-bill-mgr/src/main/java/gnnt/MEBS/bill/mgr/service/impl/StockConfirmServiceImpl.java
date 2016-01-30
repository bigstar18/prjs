package gnnt.MEBS.bill.mgr.service.impl;

import gnnt.MEBS.bill.mgr.service.StockConfirmService;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("stockConfirmService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class StockConfirmServiceImpl
  extends StandardService
  implements StockConfirmService
{
  public int stockConfirm(String stockId, User user)
    throws Exception
  {
    String procedureName = "{? = call FN_T_OUTSTOCKCONFIRM(?,?) }";
    Object[] params = { stockId, user.getUserId() };
    int result = ((BigDecimal)getDao().executeProcedure(procedureName, params)).intValue();
    return result;
  }
}

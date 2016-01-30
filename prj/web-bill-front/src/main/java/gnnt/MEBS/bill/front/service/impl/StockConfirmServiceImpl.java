package gnnt.MEBS.bill.front.service.impl;

import gnnt.MEBS.bill.front.service.StockConfirmService;
import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
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
    Object[] params = { stockId, user.getUserID() };
    int result = ((BigDecimal)getDao().executeProcedure(procedureName, params)).intValue();
    return result;
  }
}

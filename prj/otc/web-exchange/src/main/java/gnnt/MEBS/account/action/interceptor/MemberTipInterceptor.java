package gnnt.MEBS.account.action.interceptor;

import gnnt.MEBS.account.model.MemberInfoVO;
import gnnt.MEBS.settlement.model.MarketParameters;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.core.context.WebContext;
import org.ecside.table.interceptor.ColumnInterceptor;

public class MemberTipInterceptor
  implements ColumnInterceptor
{
  public void addColumnAttributes(TableModel tablemodel, Column column) {}
  
  public void modifyColumnAttributes(TableModel tablemodel, Column column)
  {
    List list = (List)tablemodel.getContext().getRequestAttribute("market");
    MarketParameters market = (MarketParameters)list.get(0);
    
    StringBuilder returnValue = new StringBuilder();
    MemberInfoVO memberInfo = (MemberInfoVO)tablemodel.getCurrentRowBean();
    if (memberInfo.getMemberRelations().isEmpty()) {
      returnValue.append("未关联特别会员；");
    }
    if ("B".equals(memberInfo.getMemberType()))
    {
      BigDecimal subtractValue = market.getBrokeMemberOpenMinFunds().subtract(memberInfo.getUseFunds());
      if (subtractValue.doubleValue() > 0.0D) {
        returnValue.append("至少需再入金" + subtractValue + "才能激活；");
      }
    }
    else
    {
      BigDecimal subtractValue = market.getMemberOpenMinFunds().subtract(memberInfo.getUseFunds());
      if (subtractValue.doubleValue() > 0.0D) {
        returnValue.append("至少需再入金" + subtractValue + "才能激活；");
      }
    }
    if ("".equals(returnValue.toString())) {
      returnValue.append("可以激活");
    }
    column.setValue(returnValue);
  }
}

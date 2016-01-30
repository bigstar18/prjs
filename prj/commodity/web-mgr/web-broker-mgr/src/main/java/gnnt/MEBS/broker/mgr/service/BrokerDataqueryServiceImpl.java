package gnnt.MEBS.broker.mgr.service;

import gnnt.MEBS.broker.mgr.dao.BrokerDataqueryDAO;
import gnnt.MEBS.broker.mgr.model.brokerDataquery.BrokerReward;
import gnnt.MEBS.common.mgr.service.StandardService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_brokerDataqueryService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerDataqueryServiceImpl extends StandardService
  implements BrokerDataqueryService
{

  @Autowired
  @Qualifier("brokerDataqueryDAO")
  public BrokerDataqueryDAO brokerDataqueryDAO;

  public List brokerTradeFee(HttpServletRequest request)
  {
    return this.brokerDataqueryDAO.getBrokerTradeFee(request);
  }

  public List breedTradeFee(HttpServletRequest request)
  {
    return this.brokerDataqueryDAO.getBreedTradeFee(request);
  }

  public List firmTradeFee(HttpServletRequest request)
  {
    return this.brokerDataqueryDAO.getFirmTradeFee(request);
  }

  public BrokerDataqueryDAO getBrokerDataqueryDAO()
  {
    return this.brokerDataqueryDAO;
  }

  public void setBrokerDataqueryDAO(BrokerDataqueryDAO brokerDataqueryDAO) {
    this.brokerDataqueryDAO = brokerDataqueryDAO;
  }

  public int handleReward(BrokerReward brokerReward, double money, String firmID)
  {
    int result = 1;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    List list = getListBySql("select * from  BR_BROKERREWARD where brokerId = '" + 
      brokerReward.getBrokerId() + 
      "' and occurdate = to_date('" + 
      format.format(brokerReward.getOccurDate()) + 
      "','yyyy-mm-dd') and moduleid = " + 
      brokerReward.getModuleId() + " for update");
    Map map = (Map)list.get(0);

    double amount = Double.parseDouble(map.get("AMOUNT").toString());

    if (amount >= money) {
      String procedureName = "{?=call FN_F_UpdateFunds(?,?,?,?) }";
      Object[] params = (Object[])null;

      if (map.get("MODULEID").equals(new BigDecimal(15)))
        params = new Object[] { firmID, Integer.valueOf(15019), Double.valueOf(money) };
      else if (map.get("MODULEID").equals(new BigDecimal(18)))
        params = new Object[] { firmID, Integer.valueOf(18007), Double.valueOf(money) };
      try
      {
        executeProcedure("{?=call FN_F_UpdateFunds(?,?,?,?) }", params).toString();

        brokerReward.setAmount(Double.valueOf(brokerReward.getAmount().doubleValue() - money));
        brokerReward.setPaidAmount(Double.valueOf(brokerReward.getPaidAmount().doubleValue() + money));

        update(brokerReward);
      } catch (Exception e) {
        e.printStackTrace();
        result = -2;
      }
    }
    else {
      result = -1;
    }

    return result;
  }
}
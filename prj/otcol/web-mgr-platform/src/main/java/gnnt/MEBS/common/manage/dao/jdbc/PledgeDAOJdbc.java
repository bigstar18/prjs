package gnnt.MEBS.common.manage.dao.jdbc;

import gnnt.MEBS.common.manage.dao.PledgeDAO;
import gnnt.MEBS.common.manage.model.Apply_T_PledgeMoney;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PledgeDAOJdbc
  extends JdbcDaoSupport
  implements PledgeDAO
{
  public int CheckFirmAndBillFund(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    String str = "select t.MaxOverdraft MaxOverdraft from T_Firm t where t.firmID = '" + paramApply_T_PledgeMoney.getFirmId() + "'";
    int i = 0;
    List localList = getJdbcTemplate().queryForList(str);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if (localMap.get("MaxOverdraft") != null)
      {
        double d = Double.parseDouble(localMap.get("MaxOverdraft").toString());
        if (d + paramApply_T_PledgeMoney.getBillFund() < 0.0D) {
          i = 2;
        }
      }
    }
    else
    {
      i = 1;
    }
    return i;
  }
  
  public void updatePledge(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    String str1 = "select count(*) from T_E_ApplyBill a where (a.status = 1 or a.status = 2) and a.BillID = '" + paramApply_T_PledgeMoney.getBillID() + "'";
    this.logger.debug("sql: " + str1);
    if (getJdbcTemplate().queryForInt(str1) > 0) {
      throw new RuntimeException("此仓单号已有记录，不能重复添加！");
    }
    Long localLong = new Long(0L);
    if (paramApply_T_PledgeMoney.getQuantity() != 0.0D) {
      localLong = Long.valueOf(paramApply_T_PledgeMoney.getQuantity());
    }
    String str2 = "insert into T_E_Pledge (ID,BillID,BillFund,FirmID,CommodityName,Quantity,CreateTime,Creator,ModifyTime,Modifier) values (SEQ_T_E_PLEDGE.nextval,?,?,?,?,?,sysdate,?,sysdate,?)";
    Object[] arrayOfObject = { paramApply_T_PledgeMoney.getBillID(), Double.valueOf(paramApply_T_PledgeMoney.getBillFund()), paramApply_T_PledgeMoney.getFirmId(), paramApply_T_PledgeMoney.getCommodityName(), localLong, paramApply_T_PledgeMoney.getApprover(), paramApply_T_PledgeMoney.getApprover() };
    this.logger.debug("sql: " + str2);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str2, arrayOfObject);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw new RuntimeException("此仓单号已有记录，不能重复添加！");
    }
    str2 = "update T_Firm set MaxOverdraft = (MaxOverdraft + ?) where firmID = ?";
    arrayOfObject = new Object[] { Double.valueOf(paramApply_T_PledgeMoney.getBillFund()), paramApply_T_PledgeMoney.getFirmId() };
    this.logger.debug("sql: " + str2);
    for (int j = 0; j < arrayOfObject.length; j++) {
      this.logger.debug("params[" + j + "]: " + arrayOfObject[j]);
    }
    try
    {
      getJdbcTemplate().update(str2, arrayOfObject);
    }
    catch (Exception localException2)
    {
      throw new RuntimeException("修改交易商质押资金失败！");
    }
  }
  
  public int deletePledgeChe(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    int i = 0;
    String str1 = "select nvl(BillFund,0) from T_E_Pledge where billID = '" + paramApply_T_PledgeMoney.getBillID() + "'";
    this.logger.debug(str1);
    Object localObject1 = null;
    try
    {
      localObject1 = getJdbcTemplate().queryForObject(str1, Object.class);
    }
    catch (Exception localException1) {}
    Double localDouble = new Double(0.0D);
    if (localObject1 != null)
    {
      localDouble = Double.valueOf(Double.parseDouble(localObject1.toString()));
    }
    else
    {
      i = 3;
      return i;
    }
    str1 = "select firmID from T_E_Pledge where billID = '" + paramApply_T_PledgeMoney.getBillID() + "'";
    Object localObject2 = null;
    try
    {
      localObject2 = getJdbcTemplate().queryForObject(str1, Object.class);
    }
    catch (Exception localException2) {}
    String str2 = "";
    if (localObject2 != null)
    {
      str2 = localObject2.toString();
    }
    else
    {
      i = 3;
      return i;
    }
    str1 = "update T_firm set MaxOverdraft = (MaxOverdraft - " + localDouble + " ) where firmID = '" + str2 + "'";
    try
    {
      getJdbcTemplate().update(str1);
    }
    catch (Exception localException3)
    {
      localException3.printStackTrace();
      throw new RuntimeException("修改交易商最大透支额度失败！");
    }
    str1 = "delete from T_E_Pledge where billID = ?";
    Object[] arrayOfObject = { paramApply_T_PledgeMoney.getBillID() };
    this.logger.debug("sql: " + str1);
    for (int j = 0; j < arrayOfObject.length; j++) {
      this.logger.debug("params[" + j + "]: " + arrayOfObject[j]);
    }
    try
    {
      getJdbcTemplate().update(str1, arrayOfObject);
      return i;
    }
    catch (Exception localException4)
    {
      localException4.printStackTrace();
      throw new RuntimeException("删除失败！");
    }
  }
  
  public Apply_T_PledgeMoney getApply_T_PledgeMoneyByBillID(Apply_T_PledgeMoney paramApply_T_PledgeMoney)
  {
    String str = "select t.* from T_E_Pledge t where t.billID = '" + paramApply_T_PledgeMoney.getBillID() + "'";
    this.logger.debug("sql: " + str);
    List localList = getJdbcTemplate().queryForList(str);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if (localMap.get("firmID") != null) {
        paramApply_T_PledgeMoney.setFirmId(localMap.get("firmID").toString());
      }
      if (localMap.get("billID") != null) {
        paramApply_T_PledgeMoney.setBillID(localMap.get("billID").toString());
      }
      if (localMap.get("billFund") != null) {
        paramApply_T_PledgeMoney.setBillFund(Double.parseDouble(localMap.get("billFund").toString()));
      }
      if (localMap.get("commodityName") != null) {
        paramApply_T_PledgeMoney.setCommodityName(localMap.get("commodityName").toString());
      }
      if (localMap.get("quantity") != null) {
        paramApply_T_PledgeMoney.setQuantity(Double.parseDouble(localMap.get("quantity").toString()));
      }
    }
    return paramApply_T_PledgeMoney;
  }
}

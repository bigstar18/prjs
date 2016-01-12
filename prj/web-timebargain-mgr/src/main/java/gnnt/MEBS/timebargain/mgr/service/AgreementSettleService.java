package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.settle.ApplyTreatySettle;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_agreementService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class AgreementSettleService extends StandardService
{
  private final transient Log logger = LogFactory.getLog(AgreementSettleService.class);

  public int addAgreement(String commodityid, String customerID_S, String customerID_B, String price, String quantity, StandardModel entity)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's addTransfer");
    }

    try
    {
      String sql_check = "select commodityid from T_COMMODITY where commodityid = '" + commodityid + "'";
      List list_check = getListBySql(sql_check);
      if (list_check.size() == 0) {
        return -2;
      }

      String firmID_b = "";
      String firmID_s = "";
      sql_check = "select firmid from t_customer where customerid = '" + customerID_B + "'";
      list_check = getListBySql(sql_check);
      if (list_check.size() == 0)
        return -3;
      if (list_check.size() > 0) {
        firmID_b = ((Map)list_check.get(0)).get("FIRMID").toString();
      }

      sql_check = "select firmid from t_customer where customerid = '" + customerID_S + "'";
      list_check = getListBySql(sql_check);
      if (list_check.size() == 0)
        return -4;
      if (list_check.size() > 0) {
        firmID_s = ((Map)list_check.get(0)).get("FIRMID").toString();
      }

      sql_check = "select holdqty-frozenqty as qty from T_CustomerHoldSum where customerid='" + customerID_B + "' and commodityid='" + commodityid + "' and bs_flag=1";
      list_check = getListBySql(sql_check);
      if (list_check.size() == 0)
        return -5;
      if (list_check.size() > 0) {
        int qty = Integer.parseInt(((Map)list_check.get(0)).get("QTY").toString());
        if (qty < Integer.parseInt(quantity)) {
          return -5;
        }
      }

      sql_check = "select holdqty-frozenqty as qty from T_CustomerHoldSum where customerid='" + customerID_S + "' and commodityid='" + commodityid + "' and bs_flag=2";
      list_check = getListBySql(sql_check);
      if (list_check.size() == 0)
        return -6;
      if (list_check.size() > 0) {
        int qty = Integer.parseInt(((Map)list_check.get(0)).get("QTY").toString());
        if (qty < Integer.parseInt(quantity)) {
          return -6;
        }

      }

      ApplyTreatySettle applyModel = (ApplyTreatySettle)entity;

      add(entity);

      String sql = "update T_CustomerHoldSum set frozenQty = frozenQty + " + quantity + " where CommodityID = '" + commodityid + "' and customerid = '" + customerID_B + "' and bs_flag = 1";
      executeUpdateBySql(sql);

      sql = "insert into T_Holdfrozen(ID,Operation,FirmID,CustomerID,CommodityID,BS_Flag,frozentype,FrozenQTY,frozenTime) values(SEQ_T_HOLDFROZEN.nextval," + applyModel.getApplyID() + ",'" + firmID_b + "','" + customerID_B + "','" + commodityid + "',1,1," + quantity + ",sysdate)";
      executeUpdateBySql(sql);

      sql = "update T_CustomerHoldSum set frozenQty = frozenQty + " + quantity + " where CommodityID = '" + commodityid + "' and customerid = '" + customerID_S + "' and bs_flag = 2";
      executeUpdateBySql(sql);

      sql = "insert into T_Holdfrozen(ID,Operation,FirmID,CustomerID,CommodityID,BS_Flag,frozentype,FrozenQTY,frozenTime) values(SEQ_T_HOLDFROZEN.nextval," + applyModel.getApplyID() + ",'" + firmID_s + "','" + customerID_S + "','" + commodityid + "',2,1," + quantity + ",sysdate)";
      executeUpdateBySql(sql);
    }
    catch (Exception e) {
      return -1;
    }

    return 1;
  }

  public int auditSuccess(String ID, String commodityID, String customerID_B, String customerID_S, String price, String quantity)
  {
    String procedureName = "{?=call FN_T_ConferCloseAudit(?,?,?,?,?,?) }";
    Object[] params = { ID, commodityID, customerID_B, customerID_S, price, quantity };
    int result = -1;
    try {
      result = Integer.parseInt(executeProcedure("{?=call FN_T_ConferCloseAudit(?,?,?,?,?,?) }", params).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public int auditFail(String ID, String commodityID, String customerID_B, String customerID_S, String price, String quantity)
  {
    try
    {
      String sql = "update T_E_ApplyTreatySettle set Status=3,modifytime = sysdate where APPLYID = " + ID;
      executeUpdateBySql(sql);

      sql = "update T_CustomerHoldSum set frozenQty = frozenQty - " + quantity + " where CommodityID = '" + commodityID + "' and customerID = '" + customerID_B + "' and bs_flag = 1";
      executeUpdateBySql(sql);
      sql = "update T_CustomerHoldSum set frozenQty = frozenQty - " + quantity + " where CommodityID = '" + commodityID + "' and customerID = '" + customerID_S + "' and bs_flag = 2";
      executeUpdateBySql(sql);

      sql = "delete T_Holdfrozen where Operation = to_char(" + ID + ") and frozentype = 1 ";
      executeUpdateBySql(sql);
    }
    catch (Exception e) {
      return -1;
    }
    return 1;
  }
}
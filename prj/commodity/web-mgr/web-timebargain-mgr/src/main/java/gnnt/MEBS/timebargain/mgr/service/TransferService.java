package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.settle.TransferModel;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_transferService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class TransferService extends StandardService
{
  private final transient Log logger = LogFactory.getLog(TransferService.class);

  public int addTransfer(String commodityid, String customerID_s, String bs_flag, String quantity, StandardModel entity)
  {
    if (this.logger.isDebugEnabled())
      this.logger.debug("enter TradeParamsService's addTransfer");
    try
    {
      TransferModel tm = (TransferModel)entity;
      tm.setCreateTime(new Date());
      tm.setModifyTime(new Date());

      add(tm);
      TransferModel transferModel = (TransferModel)entity;

      String sql = "update T_CustomerHoldSum set frozenQty = frozenQty + " + quantity + " where CommodityID = '" + commodityid + "' and customerid = '" + customerID_s + "' and bs_flag = " + bs_flag;
      executeUpdateBySql(sql);

      sql = "insert into T_Holdfrozen(ID,Operation,FirmID,CustomerID,CommodityID,BS_Flag,frozentype,FrozenQTY,frozenTime) values(SEQ_T_HOLDFROZEN.nextval,'" + transferModel.getTransferID() + "','" + customerID_s.substring(0, customerID_s.length() - 2) + "','" + customerID_s + "','" + commodityid + "'," + bs_flag + ",2," + quantity + ",sysdate)";
      executeUpdateBySql(sql);
    }
    catch (Exception e) {
      return -1;
    }

    return 1;
  }

  public int deleteTransfer(String[] ids)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's deleteTransfer");
    }
    long status = 0L;
    String commodityid = "";
    String customerID_s = "";
    long bs_flag = 0L;
    int quantity = 0;
    String sql = "";
    try {
      for (int i = 0; i < ids.length; i++) {
        TransferModel transferModel = new TransferModel();
        transferModel.setTransferID(Long.valueOf(Long.parseLong(ids[i])));
        transferModel = (TransferModel)get(transferModel);
        status = transferModel.getStatus().intValue();
        if (status == 0L) {
          commodityid = transferModel.getCommodityID();
          customerID_s = transferModel.getCustomerID_s();
          bs_flag = transferModel.getBs_flag().intValue();
          quantity = transferModel.getQuantity().intValue();

          sql = sql + "update T_CustomerHoldSum set frozenQty = frozenQty - " + quantity + " where CommodityID = '" + commodityid + "' and firmid = '" + customerID_s + "' and bs_flag = " + bs_flag + ";";

          sql = sql + "delete T_Holdfrozen where Operation = to_char(" + ids[i] + ") and frozentype = 2;";
        } else {
          return -2;
        }
        delete(transferModel);
      }

      String[] sqls = sql.split(";");
      for (int a = 0; a < sqls.length; a++) {
        executeUpdateBySql(sqls[a]);
      }

    }
    catch (SecurityException e)
    {
      e.printStackTrace();
    } catch (Exception e) {
      return -1;
    }

    return 1;
  }

  public int auditSuccess(String ID, String commodityID, String bs_flag, String customerID_b, String customerID_s, String quantity)
  {
    String procedureName = "{?=call FN_T_UnTradeTransfer(?,?,?,?,?,?) }";
    Object[] params = { ID, commodityID, bs_flag, customerID_b, customerID_s, quantity };
    int result = -1;
    try {
      result = Integer.parseInt(executeProcedure("{?=call FN_T_UnTradeTransfer(?,?,?,?,?,?) }", params).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public int auditFail(String ID, String commodityID, String bs_flag, String customerID_s, String quantity)
  {
    try
    {
      String sql = "update T_UnTradeTransfer set Status=2,modifytime=sysdate where Transferid = " + ID;
      executeUpdateBySql(sql);

      sql = "update T_CustomerHoldSum set frozenQty = frozenQty - " + quantity + " where CommodityID = '" + commodityID + "' and customerid = '" + customerID_s + "' and bs_flag = " + bs_flag;
      executeUpdateBySql(sql);

      sql = "delete T_Holdfrozen where Operation = to_char(" + ID + ") and frozentype = 2 ";
      executeUpdateBySql(sql);
    }
    catch (Exception e) {
      return -1;
    }
    return 1;
  }
}
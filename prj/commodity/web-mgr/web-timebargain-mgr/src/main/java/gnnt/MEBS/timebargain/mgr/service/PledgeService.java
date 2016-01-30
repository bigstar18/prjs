package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.PledgeDao;
import gnnt.MEBS.timebargain.mgr.model.settle.PledgeModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("com_pledgeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class PledgeService extends StandardService
{
  private final transient Log logger = LogFactory.getLog(PledgeService.class);

  @Autowired
  @Qualifier("billTradeService")
  private ITradeService itradeService;

  @Autowired
  @Qualifier("pledgeDao")
  private PledgeDao pledgeDao;

  public ITradeService getItradeService() { return this.itradeService; }


  public void setItradeService(ITradeService itradeService)
  {
    this.itradeService = itradeService;
  }

  public int addPledge(String billID, StandardModel entity)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's addTransfer");
    }
    try
    {
      this.pledgeDao.add(entity);
      PledgeModel pledgeModel = (PledgeModel)entity;

      long type = pledgeModel.getType().intValue();
      if (type == 0L)
      {
        String sql = "insert into T_billFrozen(ID,Operation,BillID,Operationtype,ModifyTime) values(SEQ_T_BILLFROZEN.NEXTVAL,'" + pledgeModel.getPledgeID() + "','" + billID + "',0,sysdate)";
        this.pledgeDao.executeUpdateBySql(sql);

        String[] str = { billID };

        ResultVO resultVO = this.itradeService.frozenStocks(15, str);
        if (resultVO.getResult() == -1L) {
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          return -2;
        }
      }
    } catch (Exception e) {
      return -1;
    }

    return 1;
  }

  public int auditSuccess(String ID, String FirmID, String billID, long type, double billFund, String userID)
  {
    try
    {
      if (type == 0L)
      {
        String sql = "update T_Firm set MaxOverdraft=MaxOverdraft+" + billFund + " where FirmID = '" + FirmID + "'";
        executeUpdateBySql(sql);

        sql = "update T_E_Pledge set Status=1,ModifyTime=sysdate,Modifier='" + userID + "' where ID = " + ID;
        executeUpdateBySql(sql);
      }
      else {
        String sql = "update T_Firm set MaxOverdraft=MaxOverdraft-" + billFund + " where FirmID = '" + FirmID + "'";
        executeUpdateBySql(sql);

        sql = "delete T_billFrozen where Operation = '" + ID + "'";
        executeUpdateBySql(sql);

        sql = "update T_E_Pledge set Status=1,ModifyTime=sysdate,Modifier='" + userID + "' where ID = " + ID;
        executeUpdateBySql(sql);

        sql = "update T_E_Pledge set Status=3,ModifyTime=sysdate,Modifier='" + userID + "' where BillID ='" + billID + "' and Status=1 and type=0";
        executeUpdateBySql(sql);

        String[] str = { billID };
        ResultVO resultVO = this.itradeService.unFrozenStocks(15, str);
        if (resultVO.getResult() == -1L) {
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          return -2;
        }
      }
    }
    catch (Exception e) {
      return -1;
    }

    return 1;
  }

  public int auditFail(String ID, String billID, long type, String userID)
  {
    try
    {
      if (type == 0L)
      {
        String sql = "update T_E_Pledge set Status=2,ModifyTime=sysdate,Modifier='" + userID + "' where ID = " + ID;
        executeUpdateBySql(sql);

        sql = "delete T_billFrozen where Operation = to_char(" + ID + ")";
        executeUpdateBySql(sql);

        String[] str = { billID };
        ResultVO resultVO = this.itradeService.unFrozenStocks(15, str);
        if (resultVO.getResult() == -1L) {
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          return -2;
        }
      }
      else
      {
        String sql = "update T_E_Pledge set Status=2,ModifyTime=sysdate,Modifier='" + userID + "' where ID = " + ID;
        executeUpdateBySql(sql);
      }
    }
    catch (Exception e) {
      return -1;
    }
    return 1;
  }
}
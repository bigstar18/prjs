package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.AheadSettleDao;
import gnnt.MEBS.timebargain.mgr.model.settleMatch.BillFrozenM;
import gnnt.MEBS.timebargain.mgr.model.settleMatch.SettleMatchM;
import gnnt.MEBS.timebargain.mgr.service.SettleMatchService;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("settleMatchService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SettleMatchServiceImpl
  implements SettleMatchService
{
  private final transient Log logger = LogFactory.getLog(TradeParamsService.class);

  private final int moduleId = 15;

  @Autowired
  @Qualifier("billTradeService")
  private ITradeService tradeService;

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;

  @Autowired
  @Qualifier("aheadSettleDao")
  private AheadSettleDao aheadSettleDao;

  public ITradeService getTradeService() { return this.tradeService; }

  public void setTradeService(ITradeService tradeService)
  {
    this.tradeService = tradeService;
  }

  public AheadSettleDao getAheadSettleDao()
  {
    return this.aheadSettleDao;
  }

  public void setAheadSettleDao(AheadSettleDao aheadSettleDao)
  {
    this.aheadSettleDao = aheadSettleDao;
  }

  public int addSettleMatch(String[] ids, SettleMatchM settleMatch, String operator) throws Exception {
    int result = 0;
    String matchId = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String settleDate = null;
    try
    {
      String sql = "select seq_T_SettleMatch.nextval id from dual";
      List list = this.standardService.getListBySql(sql);
      matchId = "TS_" + ((Map)list.get(0)).get("ID").toString();
      String procedureName = "{?=call FN_T_SettleMatch(?,?,?,?,?,?,?,?,?) }";
      if (settleMatch.getSettleDate() != null) {
        settleDate = sdf.format(settleMatch.getSettleDate());
      }
      Object[] params = { settleMatch.getCommodityId(), settleMatch.getQuantity(), settleMatch.getStatus(), settleMatch.getResult(), settleMatch.getFirmID_B(), settleMatch.getFirmID_S(), settleDate, matchId, operator };
      result = Integer.parseInt(this.standardService.executeProcedure("{?=call FN_T_SettleMatch(?,?,?,?,?,?,?,?,?) }", params).toString());
      if ((result > 0) && 
        (settleMatch.getResult().intValue() == 1))
      {
        for (int i = 0; i < ids.length; i++) {
          BillFrozenM billFrozen = new BillFrozenM();
          billFrozen.setOperation(matchId);
          billFrozen.setBillID(ids[i]);
          billFrozen.setOperationType(Integer.valueOf(2));
          billFrozen.setModifyTime(new Date());
          this.standardService.add(billFrozen);
        }

        ResultVO resultVO = this.tradeService.performTransferGoods(15, matchId, ids, settleMatch.getFirmID_S());
        if (resultVO.getResult() == -1L) {
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
          result = -3;
        }
      }
    }
    catch (Exception e) {
      throw new Exception();
    }

    return result;
  }
}
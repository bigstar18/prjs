package gnnt.trade.bank;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.bank.adapter.bankBusiness.message.REQ_BODY_F612;
import gnnt.bank.adapter.bankBusiness.message.RSP_BODY_F612;
import gnnt.bank.adapter.bankBusiness.message.RSP_F612;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.ceb.CEBBankDao;
import gnnt.trade.bank.data.ceb.CEBExDataImpl;
import gnnt.trade.bank.data.ceb.vo.CebConstant;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CEBCapitalProcessor
  extends CapitalProcessor
{
  private static CEBBankDao DAO = null;
  private static final int INMONEY = 0;
  private static final int OUTMONEY = 1;
  private static final int RATE = 2;
  
  public CEBCapitalProcessor()
  {
    try
    {
      if (DAO == null) {
        DAO = BankDAOFactory.getCEBDAO();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log("Dao初始化失败！");
    }
  }
  
  public CEB_RSP CEB(CEB_param param, String id)
  {
    log("CEB_RSP开始");
    BankAdapterRMI adapter = getAdapter(CebConstant.bankID);
    CEB_RSP rsp = new CEB_RSP();
    Connection conn = null;
    if (adapter == null)
    {
      log("错误码：");
      return new CEB_RSP();
    }
    log("id=" + id);
    try
    {
      conn = DAO.getConnection();
      CEB_RSP localCEB_RSP1;
      if ("CEB_F623".equals(id))
      {
        rsp = adapter.CEB(param, id);
        System.out.println("rsp.flag=" + rsp.flag);
        System.out.println("param.transSerialNum=" + 
          param.transSerialNum);
        ReturnValue re;
        if (("0".equals(rsp.flag)) && ("21".equals(param.type)))
        {
          ChargeAgainstValue cav = new ChargeAgainstValue();
          cav.funID = param.transSerialNum;
          cav.bankID = CebConstant.bankID;
          re = chargeAgainstMaket_ceb(cav);
        }
        else if (("0".equals(rsp.flag)) && ("20".equals(param.type)))
        {
          DAO.modCapitalInfoStatus_ceb_f623(param.transSerialNum, -1, 
            new Timestamp(System.currentTimeMillis()), conn);
        }
        return rsp;
      }
      if ("CEB_F610".equals(id))
      {
        if ("1".equals(param.depositType))
        {
          Vector<CorrespondValue> cList = DAO.getCorrespondList(
            "and bankid='" + CebConstant.bankID + "' and contact='" + 
            param.firmId + "'", conn);
          if (cList.size() > 0)
          {
            param.memNum = ((CorrespondValue)cList.get(0)).account1;
            param.sourceAccount = ((CorrespondValue)cList.get(0)).account;
            System.out.println("memNum=" + param.memNum);
          }
          ReturnValue rv = ifbankTrade(CebConstant.bankID, null, param.firmId, 0, 1);
          if (rv.result != 0L)
          {
            log(rv.remark);
            rsp.flag = "1";
            rsp.errorInfo = rv.remark;
            return rsp;
          }
        }
        rsp = adapter.CEB(param, id);
        


















        return rsp;
      }
      if ("CEB_F612".equals(id))
      {
        CorrespondValue source = null;
        CorrespondValue target = null;
        if (("2".equals(param.flag)) || ("4".equals(param.flag)))
        {
          Vector MarketAcountList01 = DAO
            .getMarketAcount(" and type = '01' ");
          source = (CorrespondValue)MarketAcountList01.get(0);
          Vector MarketAcountList02 = DAO
            .getMarketAcount(" and type = '02' ");
          target = (CorrespondValue)MarketAcountList02.get(0);
          param.isCrossLine = "01";
        }
        else if ("9".equals(param.flag))
        {
          Vector MarketAcountList01 = DAO
            .getMarketAcount(" and type = '02' ");
          source = (CorrespondValue)MarketAcountList01.get(0);
          Vector MarketAcountList02 = DAO
            .getMarketAcount(" and type = '01' ");
          target = (CorrespondValue)MarketAcountList02.get(0);
          param.isCrossLine = "01";
        }
        else if ("91".equals(param.flag))
        {
          Vector MarketAcountList01 = DAO
            .getMarketAcount(" and type = '01' ");
          source = (CorrespondValue)MarketAcountList01.get(0);
          Vector MarketAcountList02 = DAO
            .getMarketAcount(" and type = '02' ");
          target = (CorrespondValue)MarketAcountList02.get(0);
          param.isCrossLine = "01";
          param.flag = "9";
        }
        else if ("92".equals(param.flag))
        {
          Vector MarketAcountList02 = DAO
            .getMarketAcount(" and (type = '03' or account = '" + 
            param.targetAccount + "') ");
          target = (CorrespondValue)MarketAcountList02.get(0);
          Vector MarketAcountList01 = DAO
            .getMarketAcount(" and type = '01' ");
          source = (CorrespondValue)MarketAcountList01.get(0);
          param.isCrossLine = "02";
          param.flag = "9";
          param.targetAccountBankNum = target.accountBankNum;
          log(target.accountBankNum);
        }
        param.targetAccountBank = target.accountBank;
        param.targetAccountBankName = target.bankName;
        param.targetAccountName = target.accountName;
        param.targetAccount = target.account;
        param.sourceAccountName = source.accountName;
        param.sourceAccount = source.account;
        System.out.println("adapter.CEB 612");
        rsp = adapter.CEB(param, id);
        if ("0".equals(rsp.flag))
        {
          param.bankId = CebConstant.bankID;
          param.flag = rsp.req_body_F612.flag;
          param.busiSerialNum = rsp.req_body_F612.busiSerialNum;
          param.amount = rsp.req_body_F612.amount;
          param.tradeTime = rsp.req_body_F612.tradeTime;
          param.atOnce = rsp.req_body_F612.atOnce;
          param.isCrossLine = rsp.req_body_F612.isCrossLine;
          param.targetAccountBankNum = rsp.req_body_F612.targetAccountBankNum;
          param.targetAccountBank = rsp.req_body_F612.targetAccountBank;
          param.targetAccountBankName = rsp.req_body_F612.targetAccountBankName;
          param.targetAccountName = rsp.req_body_F612.targetAccountName;
          param.targetAccount = rsp.req_body_F612.targetAccount;
          param.sourceAccountName = rsp.req_body_F612.sourceAccountName;
          param.sourceAccount = rsp.req_body_F612.sourceAccount;
          param.fcsSerialNum = rsp.rsp_F612.body.fcsSerialNum;
          param.resultCode = rsp.rsp_F612.body.resultCode;
          DAO.addTransfer(param);
        }
        return rsp;
      }
      if ("CEB_F619".equals(id))
      {
        ReturnValue result = new CEBExDataImpl().send(Tool.strToDate(param.tradeTime));
        
        rsp.flag = result.result;
        rsp.errorInfo = result.remark;
        return rsp;
      }
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    DAO.closeStatement(null, null, conn);
    try
    {
      System.out.println("return adapter.CEB");
      return adapter.CEB(param, id);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return new CEB_RSP();
  }
  
  public ReturnValue chargeAgainstMaket_ceb(ChargeAgainstValue cav)
  {
    System.out.println("{{{{{{{{{{{{{{{{{{{{{冲证(市场方调用){{{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("cav[" + cav.toString() + "]");
    System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + 
      Tool.fmtTime(new Date()) + 
      "}}}}}}}}}}}}}}}}}}}}}}}}");
    Connection conn = null;
    String filter = null;
    ReturnValue rv = new ReturnValue();
    rv.result = 0L;
    if (cav == null)
    {
      rv.result = -500001L;
      rv.remark = "传入的参数为空";
    }
    Map<String, FirmBalanceValue> firmMsg = new HashMap();
    try
    {
      conn = DAO.getConnection();
      



      filter = " and bankID='" + cav.bankID + 
        "' and status=0 and (type=0 or type=1 or type=2) ";
      if (cav.actionID > 0L)
      {
        filter = filter + " and actionID=" + cav.actionID;
      }
      else if ((cav.funID != null) && (cav.funID.trim().length() > 0))
      {
        filter = filter + " and funID=" + cav.funID.trim();
      }
      else
      {
        rv.result = -500003L;
        rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
      }
      int status = 0;
      CapitalValue capVal = null;
      if (rv.result == 0L)
      {
        Vector<CapitalValue> capList = DAO.getCapitalInfoList(filter, 
          conn);
        if ((capList != null) && (capList.size() > 0))
        {
          conn.setAutoCommit(false);
          FirmBalanceValue fbv = null;
          CapitalValue cv = new CapitalValue();
          cv.actionID = DAO.getActionID(conn);
          cv.money = 0.0D;
          cv.bankID = cav.bankID;
          cv.note = "maket_charge冲正，记录号：";
          for (int i = 0; i < capList.size(); i++)
          {
            capVal = (CapitalValue)capList.get(i);
            fbv = null;
            status = capVal.status;
            if (firmMsg.get(capVal.firmID) != null)
            {
              fbv = (FirmBalanceValue)firmMsg.get(capVal.firmID);
            }
            else
            {
              String filter2 = " and firmid='" + capVal.firmID + 
                "' for update ";
              fbv = DAO.availableBalance(filter2, conn);
              if (fbv != null)
              {
                if ("true".equalsIgnoreCase(
                  Tool.getConfig("fuYing"))) {
                  try
                  {
                    Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { capVal.firmID });
                    if ((floatingloss != null) && 
                      (floatingloss.size() > 0)) {
                      for (FirmBalanceValue tp : floatingloss) {
                        if (tp != null) {
                          if (capVal.firmID.equals(tp.firmId))
                          {
                            fbv.floatingloss = tp.floatingloss;
                            if (tp.floatingloss <= 0.0D) {
                              break;
                            }
                            fbv.avilableBalance -= 
                              tp.floatingloss;
                            
                            break;
                          }
                        }
                      }
                    }
                  }
                  catch (SQLException e)
                  {
                    throw e;
                  }
                  catch (Exception e)
                  {
                    e.printStackTrace();
                  }
                }
                firmMsg.put(capVal.firmID, fbv);
              }
              else
              {
                rv.result = -500004L;
                rv.remark = ("没有查询到交易商" + capVal.firmID);
                throw new Exception("没有查询到当个交易商" + 
                  capVal.firmID);
              }
            }
            cv.firmID = capVal.firmID;
            cv.contact = capVal.contact;
            cv.money += capVal.money;
            cv.note = (cv.note + capVal.iD + " ");
            capVal.status = 9;
            if (capVal.type == 0) {
              cv.type = 4;
            } else if (capVal.type == 1) {
              cv.type = 5;
            }
            DAO.modCapitalInfoStatus(capVal.iD, capVal.funID, 
              capVal.status, capVal.bankTime, conn);
          }
          if ((fbv == null) || (cv.money + fbv.avilableBalance < 0.0D))
          {
            rv.result = -500004L;
            rv.remark = "没有查询到交易商，或本交易商当前资金不足以冲正";
            throw new Exception("在交易系统的交易商资金表查询不到本交易商");
          }
          DAO.addCapitalInfo(cv, conn);
          if (status == 0) {
            dataProcess.updateFundsFull(cv.bankID, cv.firmID, String.valueOf(cv.actionID), cv.money, cv.type, conn);
          } else if (cv.type == 5) {
            dataProcess.updateFrozenFunds(cv.firmID, -1.0D * cv.money, conn);
          }
          conn.commit();
        }
        else
        {
          rv.result = -50007L;
          rv.remark = 
            ("查询流水不存在：市场流水号[" + cav.actionID + "]银行流水号[" + cav.funID + "]");
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      if (rv.result == 0L)
      {
        rv.result = -500005L;
        rv.remark = "冲正操作中出现数据库异常";
      }
      try
      {
        conn.rollback();
      }
      catch (SQLException e1)
      {
        e1.printStackTrace();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      if (rv.result == 0L)
      {
        rv.result = -50009L;
        rv.remark = "冲正操作中出现系统异常";
      }
      try
      {
        conn.rollback();
      }
      catch (SQLException e1)
      {
        e1.printStackTrace();
      }
    }
    finally
    {
      try
      {
        conn.setAutoCommit(true);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      DAO.closeStatement(null, null, conn);
    }
    return rv;
  }
  
  public List<TradingDetailsValue> getChangeBalance(String bankId, String firmId, Date qdate)
  {
    Connection conn = null;
    List<TradingDetailsValue> result = null;
    try
    {
      ReturnValue rv = isTradeDate(qdate);
      if (rv.result != 0L) {
        return result;
      }
      if ((Tool.fmtDate(new Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus())) {
        return result;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log(e.getMessage());
      return result;
    }
    List<FirmRights> selectDate = getRightsList(bankId, firmId, qdate);
    try
    {
      conn = DAO.getConnection();
      
      Date upData = DAO.getlastDate(qdate, conn);
      Map<String, FirmRights> lastDate = getRightsMap(bankId, firmId, upData);
      for (FirmRights fr : selectDate)
      {
        double incomeMoney = DAO.sumCapitalInfo("where firmid='" + fr.firmId + "' and  trunc(b_date)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') and code='Deposit' ", conn);
        double outcomeMoney = DAO.sumCapitalInfo("where firmid='" + fr.firmId + "' and  trunc(b_date)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') and code='Fetch' ", conn);
        double changeBalance = fr.getMoney() - incomeMoney + outcomeMoney - (lastDate.get(fr.firmId) == null ? 0.0D : ((FirmRights)lastDate.get(fr.firmId)).getMoney());
        System.out.println(" 交易商 " + fr.firmId + " 资金变化为 本日权益[" + fr.getMoney() + "] 入金[" + incomeMoney + "] 出金[" + outcomeMoney + "] 上日权益[" + (lastDate.get(fr.firmId) == null ? 0.0D : ((FirmRights)lastDate.get(fr.firmId)).getMoney()) + "]");
        if (changeBalance != 0.0D)
        {
          TradingDetailsValue tdv = new TradingDetailsValue();
          tdv.maketNum = getMktActionID();
          tdv.account = fr.account;
          tdv.firmId = fr.firmId;
          tdv.firmName = fr.firmName;
          if (changeBalance > 0.0D)
          {
            tdv.money = changeBalance;
            tdv.updown = "2";
          }
          else
          {
            tdv.money = (-changeBalance);
            tdv.updown = "1";
          }
          if (result == null) {
            result = new ArrayList();
          }
          result.add(tdv);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    if (result == null)
    {
      System.out.println("返回前 结果为 null 需要初始化");
      result = new ArrayList();
    }
    System.out.println(result.size());
    return result;
  }
  
  public List<FirmRights> getRightsList(String bankId, String firmId, Date qdate)
  {
    try
    {
      ReturnValue rv = isTradeDate(qdate);
      if (rv.result != 0L) {
        return null;
      }
      if ((Tool.fmtDate(new Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus())) {
        return null;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log(e.getMessage());
      return null;
    }
    List<FirmRights> rights = new ArrayList();
    List<FirmRightsValue> frvs = getTradeDataMsg(bankId, firmId, qdate);
    if ((frvs != null) && (frvs.size() > 0)) {
      for (FirmRightsValue frv : frvs)
      {
        FirmRights fr = new FirmRights();
        fr.firmId = frv.firmid;
        fr.firmName = frv.accountname;
        fr.account = frv.account;
        fr.money = (frv.getAvilableBlance() + frv.getTimebargainBalance() + frv.getZcjsBalance() + frv.getVendueBalance());
        rights.add(fr);
      }
    }
    return rights;
  }
  
  public List<FirmRightsValue> getTradeDataMsg(String bankId, String firmId, Date qdate)
  {
    List<FirmRightsValue> rightList = null;
    try
    {
      ReturnValue rv = isTradeDate(qdate);
      if (rv.result != 0L) {
        return rightList;
      }
      if ((Tool.fmtDate(new Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus())) {
        return rightList;
      }
      rightList = DAO.getTradeDataMsg(bankId, firmId, qdate);
    }
    catch (SQLException e)
    {
      System.out.println("获取交易商权益信息数据库异常");
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("获取交易商权益信息找不到类");
      e.printStackTrace();
    }
    return rightList;
  }
  
  public Map<String, FirmRights> getRightsMap(String bankId, String firmId, Date qdate)
  {
    log(Tool.fmtDate(qdate) + "]");
    try
    {
      ReturnValue rv = isTradeDate(qdate);
      if (rv.result != 0L) {
        return null;
      }
      if ((Tool.fmtDate(new Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus())) {
        return null;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log(e.getMessage());
      return null;
    }
    Map<String, FirmRights> rights = new HashMap();
    List<FirmRightsValue> frvs = getTradeDataMsg(bankId, firmId, qdate);
    if ((frvs != null) && (frvs.size() > 0)) {
      for (FirmRightsValue frv : frvs)
      {
        FirmRights fr = new FirmRights();
        fr.firmId = frv.firmid;
        fr.firmName = frv.accountname;
        fr.account = frv.account;
        fr.money = (frv.getAvilableBlance() + frv.getTimebargainBalance() + frv.getZcjsBalance() + frv.getVendueBalance());
        rights.put(frv.firmid, fr);
      }
    }
    return rights;
  }
  
  public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId, Date qdate)
  {
    List<OpenOrDelFirmValue> result = null;
    String openFilter = " where 1=1 ";
    String delFilter = " where 1=1 ";
    if ((bankId != null) && (!bankId.trim().equals("")))
    {
      openFilter = openFilter + "and bankId='" + bankId + "' ";
      delFilter = delFilter + "and bankId='" + bankId + "_D' ";
    }
    if (qdate != null)
    {
      openFilter = openFilter + "and trunc(openTime)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
      delFilter = delFilter + "and trunc(delTime)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
    }
    Vector<CorrespondValue> openFirmVector = null;
    
    Vector<CorrespondValue> delFirmVector = null;
    try
    {
      openFirmVector = DAO.getCorrespondList(openFilter);
      
      delFirmVector = DAO.getCorrespondList(delFilter);
      if (openFirmVector == null) {
        openFirmVector = new Vector();
      }
      if (delFirmVector == null) {
        delFirmVector = new Vector();
      }
      for (CorrespondValue cv : openFirmVector)
      {
        OpenOrDelFirmValue odf = new OpenOrDelFirmValue();
        if (result == null) {
          result = new ArrayList();
        }
        odf.firmId = cv.firmID;
        odf.firmName = cv.accountName;
        odf.maketNum = getMktActionID();
        odf.openordel = "0";
        result.add(odf);
      }
      for (CorrespondValue cv : delFirmVector)
      {
        OpenOrDelFirmValue odf = new OpenOrDelFirmValue();
        if (result == null) {
          result = new ArrayList();
        }
        odf.firmId = cv.firmID;
        odf.firmName = cv.accountName;
        odf.maketNum = getMktActionID();
        odf.openordel = "1";
        result.add(odf);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    if (result == null) {
      result = new ArrayList();
    }
    return result;
  }
  
  public FirmBalanceValue getMarketBalance(String firmid)
  {
    FirmBalanceValue fbv = null;
    String filter = " where 1=1 ";
    if (firmid != null) {
      filter = filter + "  and firmid='" + firmid + "'  ";
    }
    fbv = DAO.availableBalance(filter);
    if ("true".equalsIgnoreCase(getConfig("fuYing"))) {
      try
      {
        Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { firmid });
        if ((floatingloss != null) && (floatingloss.size() > 0)) {
          for (FirmBalanceValue fb : floatingloss) {
            if ((fb != null) && (firmid.equals(fb.firmId)))
            {
              fbv.floatingloss = fb.floatingloss;
              if (fb.floatingloss <= 0.0D) {
                break;
              }
              fbv.avilableBalance -= fb.floatingloss;
              
              break;
            }
          }
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    System.out.println(fbv.toString());
    return fbv;
  }
  
  public String getConfig(String key)
  {
    return Tool.getConfig(key);
  }
  
  public String insertFcs(Vector<FCS_10_Result> fcs10_v, Vector<FCS_11_Result> fcs11_v, Vector<FCS_13_Result> fcs13_v, Vector<FCS_99> fcs99_v)
  {
    String result = "0";
    String result_temp = "0";
    result_temp = insertFcs10(fcs10_v);
    if (!result_temp.equals("0")) {
      result = "-1";
    }
    result_temp = insertFcs11(fcs11_v);
    if (!result_temp.equals("0")) {
      result = "-1";
    }
    result_temp = insertFcs13(fcs13_v);
    if (!result_temp.equals("0")) {
      result = "-1";
    }
    result_temp = insertFcs99(fcs99_v);
    if (!result_temp.equals("0")) {
      result = "-1";
    }
    return result;
  }
  
  public String insertFcs10(Vector<FCS_10_Result> fcs10_v)
  {
    System.out.println("insertFcsInfo");
    System.out.println("}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}");
    String result = "0";
    int k = 0;
    if (fcs10_v != null) {
      try
      {
        for (int i = 0; i < fcs10_v.size(); i++)
        {
          FCS_10_Result fcs10 = (FCS_10_Result)fcs10_v.get(i);
          k = DAO.addFCS_10(fcs10);
          if (k < 0) {
            result = "-1";
          }
        }
      }
      catch (SQLException e)
      {
        result = "-1";
        e.printStackTrace();
      }
      catch (Exception e)
      {
        result = "-1";
        e.printStackTrace();
      }
    } else {
      result = "-1";
    }
    System.out.println("===保存银行清算结果数据FCS_10------处理结果result---：" + result);
    return result;
  }
  
  public String insertFcs11(Vector<FCS_11_Result> fcs11_v)
  {
    System.out.println("}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}");
    String result = "0";
    int k = 0;
    if (fcs11_v != null) {
      try
      {
        for (int i = 0; i < fcs11_v.size(); i++)
        {
          FCS_11_Result fcs11 = (FCS_11_Result)fcs11_v.get(i);
          k = DAO.addFCS_11(fcs11);
          if (k < 0) {
            result = "-1";
          }
        }
      }
      catch (SQLException e)
      {
        result = "-1";
        e.printStackTrace();
      }
      catch (Exception e)
      {
        result = "-1";
        e.printStackTrace();
      }
    } else {
      result = "-1";
    }
    System.out.println("===保存银行清算结果数据FCS_11------处理结果result---：" + result);
    return result;
  }
  
  public String insertFcs99(Vector<FCS_99> fcs99_v)
  {
    System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
    String result = "0";
    int k = 0;
    if (fcs99_v != null) {
      try
      {
        for (int i = 0; i < fcs99_v.size(); i++)
        {
          FCS_99 fcs99 = (FCS_99)fcs99_v.get(i);
          k = DAO.addFCS_99(fcs99);
          if (k < 0) {
            result = "-1";
          }
        }
      }
      catch (SQLException e)
      {
        result = "-1";
        e.printStackTrace();
      }
      catch (Exception e)
      {
        result = "-1";
        e.printStackTrace();
      }
    } else {
      result = "-1";
    }
    System.out.println("===保存银行清算结果数据FCS_99------处理结果result---：" + result);
    return result;
  }
  
  public String insertFcs13(Vector<FCS_13_Result> fcs13_v)
  {
    System.out.println("}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}");
    String result = "0";
    int k = 0;
    if (fcs13_v != null) {
      try
      {
        for (int i = 0; i < fcs13_v.size(); i++)
        {
          FCS_13_Result fcs13 = (FCS_13_Result)fcs13_v.get(i);
          k = DAO.addFCS_13(fcs13);
          if (k < 0) {
            result = "-1";
          }
        }
      }
      catch (SQLException e)
      {
        result = "-1";
        e.printStackTrace();
      }
      catch (Exception e)
      {
        result = "-1";
        e.printStackTrace();
      }
    } else {
      result = "-1";
    }
    System.out.println("===保存银行清算结果数据FCS_13------处理结果result---：" + result);
    return result;
  }
  
  public ReturnValue synchroAccount(CorrespondValue cv)
  {
    log("市场开户方法 openAccountMarket cv:" + (cv == null ? "为 null" : new StringBuilder("\n").append(cv.toString()).append("\n").toString()));
    ReturnValue rv = ifbankTrade(cv.bankID, cv.firmID, cv.contact, 3, 0);
    if (rv.result < 0L) {
      return rv;
    }
    Connection conn = null;
    synchronized (cv.contact)
    {
      try
      {
        List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and contact='" + cv.contact.trim() + "'");
        ReturnValue localReturnValue1;
        ReturnValue localReturnValue2;
        if ((cvresult == null) || (cvresult.size() <= 0))
        {
          cvresult = DAO.getCorrespondList(" and trim(contact)='" + cv.contact.trim() + "' and trim(bankID) is null ");
          if ((cvresult != null) && (cvresult.size() > 0))
          {
            CorrespondValue corr = (CorrespondValue)cvresult.get(0);
            corr.account = cv.account;
            corr.isOpen = 1;
            corr.status = 0;
            corr.accountName = cv.accountName;
            corr.bankCardPassword = cv.bankCardPassword;
            corr.card = cv.card;
            corr.cardType = cv.cardType;
            String firmID = cv.firmID;
            corr.firmID = cv.contact;
            corr.bankID = cv.bankID;
            corr.frozenFuns = cv.frozenFuns;
            corr.bankName = cv.bankName;
            corr.isCrossline = cv.isCrossline;
            corr.OpenBankCode = cv.OpenBankCode;
            conn = DAO.getConnection();
            try
            {
              conn.setAutoCommit(false);
              BankAdapterRMI bankadapter = getAdapter(cv.bankID);
              if (bankadapter == null)
              {
                rv.result = -920000L;
                rv.remark = "获取连接银行适配器失败";
                localReturnValue1 = rv;
                





















                conn.setAutoCommit(true);
                





















































































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              rv = dataProcess.changeFirmIsOpen(firmID, 1, corr.bankID, conn);
              if (rv.result < 0L)
              {
                conn.rollback();
                localReturnValue1 = rv;
                















                conn.setAutoCommit(true);
                





















































































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              rv = bankadapter.rgstAccountQuery(corr);
              if (rv.result < 0L)
              {
                conn.rollback();
                localReturnValue1 = rv;
                









                conn.setAutoCommit(true);
                





















































































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              corr.firmID = firmID;
              corr.account1 = rv.account1;
              
              rv.result = DAO.openCorrespond(corr, conn);
              conn.commit();
            }
            catch (Exception er)
            {
              conn.rollback();
              throw er;
            }
            finally
            {
              conn.setAutoCommit(true);
            }
            conn.setAutoCommit(true);
          }
          else
          {
            Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='" + cv.contact + "'");
            if ((vfv == null) || (vfv.size() != 1))
            {
              rv.result = -40002L;
              rv.remark = "通过签约号查询客户信息异常";
              localReturnValue2 = rv;
              














































































































              DAO.closeStatement(null, null, conn);return localReturnValue2;
            }
            cv.isOpen = 1;
            cv.status = 0;
            String firmID = cv.firmID;
            cv.firmID = cv.contact;
            conn = DAO.getConnection();
            try
            {
              conn.setAutoCommit(false);
              BankAdapterRMI bankadapter = getAdapter(cv.bankID);
              if (bankadapter == null)
              {
                rv.result = -920000L;
                rv.remark = "获取连接银行适配器失败";
                localReturnValue1 = rv;
                























                conn.setAutoCommit(true);
                







































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              rv = dataProcess.changeFirmIsOpen(firmID, 1, cv.bankID, conn);
              if (rv.result < 0L)
              {
                conn.rollback();
                localReturnValue1 = rv;
                

















                conn.setAutoCommit(true);
                







































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              rv = bankadapter.rgstAccountQuery(cv);
              if (rv.result < 0L)
              {
                conn.rollback();
                localReturnValue1 = rv;
                











                conn.setAutoCommit(true);
                







































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              cv.firmID = firmID;
              cv.account1 = rv.account1;
              DAO.addCorrespond(cv, conn);
              conn.commit();
            }
            catch (SQLException er)
            {
              conn.rollback();
              throw er;
            }
            catch (Exception e)
            {
              conn.rollback();
              throw e;
            }
            finally
            {
              conn.setAutoCommit(true);
            }
            conn.setAutoCommit(true);
          }
        }
        else
        {
          if (cvresult.size() != 1)
          {
            rv.result = -920014L;
            rv.remark = "通过银行编号、签约号查询出信息重复";
            localReturnValue2 = rv;
            

































































            DAO.closeStatement(null, null, conn);return localReturnValue2;
          }
          CorrespondValue cv2 = (CorrespondValue)cvresult.get(0);
          if ((cv.firmID == null) || (!cv.firmID.equals(cv2.firmID)))
          {
            rv.result = -40002L;
            rv.remark = "传入客户编号错误";
            localReturnValue2 = rv;
            



























































            DAO.closeStatement(null, null, conn);return localReturnValue2;
          }
          cv2.account = cv.account;
          cv2.isOpen = 1;
          cv2.status = 0;
          cv2.accountName = cv.accountName;
          cv2.bankCardPassword = cv.bankCardPassword;
          cv2.card = cv.card;
          cv2.cardType = cv.cardType;
          String firmID = cv.firmID;
          cv2.firmID = cv.contact;
          cv2.frozenFuns = cv.frozenFuns;
          cv2.bankName = cv.bankName;
          cv2.isCrossline = cv.isCrossline;
          cv2.OpenBankCode = cv.OpenBankCode;
          conn = DAO.getConnection();
          try
          {
            conn.setAutoCommit(false);
            BankAdapterRMI bankadapter = getAdapter(cv.bankID);
            if (bankadapter == null)
            {
              rv.result = -920000L;
              rv.remark = "获取连接银行适配器失败";
              localReturnValue1 = rv;
              


















              conn.setAutoCommit(true);
              

















              DAO.closeStatement(null, null, conn);return localReturnValue1;
            }
            rv = dataProcess.changeFirmIsOpen(firmID, 1, cv2.bankID, conn);
            if (rv.result < 0L)
            {
              conn.rollback();
              localReturnValue1 = rv;
              













              conn.setAutoCommit(true);
              

















              DAO.closeStatement(null, null, conn);return localReturnValue1;
            }
            rv = bankadapter.rgstAccountQuery(cv2);
            if (rv.result < 0L)
            {
              conn.rollback();
              localReturnValue1 = rv;
              








              conn.setAutoCommit(true);
              

















              DAO.closeStatement(null, null, conn);return localReturnValue1;
            }
            cv2.firmID = firmID;
            cv2.account1 = rv.account1;
            rv.result = DAO.openCorrespond(cv2, conn);
            conn.commit();
          }
          catch (Exception er)
          {
            conn.rollback();
            throw er;
          }
          finally
          {
            conn.setAutoCommit(true);
          }
          conn.setAutoCommit(true);
        }
        if (rv.result >= 0L) {
          try
          {
            dataProcess.openAccount(cv.firmID, cv.bankID, conn);
          }
          catch (Exception e)
          {
            log(Tool.getExceptionTrace(e));
          }
        }
      }
      catch (SQLException e)
      {
        rv.result = -40006L;
        rv.remark = "数据库异常";
        log("银行开户 交易账号与银行账号对应SQLException," + Tool.getExceptionTrace(e));
      }
      catch (Exception e)
      {
        rv.result = -40007L;
        rv.remark = "系统异常";
        log("银行开户 交易账号与银行账号对应Exception," + Tool.getExceptionTrace(e));
      }
      finally
      {
        DAO.closeStatement(null, null, conn);
      }
    }
    return rv;
  }
  
  public int getTradeTime(String startTime, String endTime)
  {
    log("判断是否超出了交易时间范围");
    int result = 1;
    if ((startTime == null) || (startTime.trim().length() <= 0) || (endTime == null) || (endTime.trim().length() <= 0)) {
      return 0;
    }
    startTime = startTime.trim();
    endTime = endTime.trim();
    if (startTime.length() < 6) {
      for (int i = 0; i < 6 - startTime.length(); i++) {
        startTime = startTime + "0";
      }
    }
    if (endTime.length() < 6) {
      for (int i = 0; i < 6 - startTime.length(); i++) {
        endTime = endTime + "0";
      }
    }
    Date now = new Date();
    Date start = Tool.getDate(startTime);
    Date end = Tool.getDate(endTime);
    if (now.getTime() <= start.getTime()) {
      result = 1;
    } else if (now.getTime() >= end.getTime()) {
      result = 2;
    } else {
      result = 0;
    }
    return result;
  }
}

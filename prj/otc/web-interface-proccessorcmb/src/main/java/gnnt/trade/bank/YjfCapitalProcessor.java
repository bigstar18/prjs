package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.YjfBankDAO;
import gnnt.trade.bank.util.Arith;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class YjfCapitalProcessor
  extends CapitalProcessor
{
  private static final int INMONEY = 0;
  private static final int OUTMONEY = 1;
  private static final int RATE = 3;
  private static YjfBankDAO DAO;
  
  public YjfCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getYjfDAO();
    }
    catch (Exception e)
    {
      log("初始化国付宝DAO对象处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public CorrespondValue getCorrespond(String bankID, String firmID, String contact, String account)
    throws SQLException, ClassNotFoundException
  {
    log("根据交易账号代码和银行账号等，查询交易账号绑定信息 getCorrespond bankID[" + bankID + "]firmID[" + contact + "]account[" + account + "]");
    CorrespondValue result = null;
    try
    {
      String filter = " and bankID='" + bankID.trim() + "' and CONTACT='" + contact.trim() + "' ";
      if ((firmID != null) && (firmID.trim().length() > 0)) {
        filter = filter + " and firmID='" + firmID.trim() + "'";
      }
      if ((account != null) && (account.trim().length() > 0)) {
        filter = filter + " and account='" + account.trim() + "'";
      }
      Vector<CorrespondValue> cvs = DAO.getCorrespondList(filter);
      if ((cvs == null) || (cvs.size() <= 0)) {
        log("根据传入信息filter[" + filter + "]未查到信息");
      } else if (cvs.size() != 1) {
        log("根据传入信息filter[" + filter + "]查到信息不为一");
      } else {
        result = (CorrespondValue)cvs.get(0);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    return result;
  }
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm)
  {
    log("市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
    result = new ReturnValue();
    long capitalID = 0L;
    Connection conn = null;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    try
    {
      ReturnValue localReturnValue1;
      if (imm.money <= 0.0D)
      {
        result.result = -920019L;
        result.remark = "入金金额必须为正数";
        localReturnValue1 = result;return localReturnValue1;
      }
      conn = DAO.getConnection();
      CorrespondValue cv = getCorrespond(imm.bankID, imm.firmID, imm.contact, imm.account);
      if (cv == null)
      {
        result.result = -920013L;
        result.remark = "未查询到交易账号信息";
        localReturnValue1 = result;return localReturnValue1;
      }
      imm.contact = cv.contact;
      imm.account = cv.account;
      imm.firmID = cv.firmID;
      imm.bankFlag = String.valueOf(cv.isCrossline);
      result = ifbankTrade(imm.bankID, imm.firmID, imm.contact, 0, 0);
      if (result.result != 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.isOpen != 1)
      {
        result.result = -10019L;
        result.remark = "交易账号未签约";
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.status != 0)
      {
        result.result = -10020L;
        result.remark = "交易账号不可用";
        localReturnValue1 = result;return localReturnValue1;
      }
      log(imm.toString());
      result.actionId = DAO.getActionID(conn);
      result.result = result.actionId;
      try
      {
        CapitalValue cVal = new CapitalValue();
        cVal.trader = "";
        cVal.actionID = result.actionId;
        cVal.firmID = imm.firmID;
        cVal.contact = imm.contact;
        cVal.bankID = imm.bankID;
        cVal.type = 0;
        cVal.launcher = 0;
        cVal.money = imm.money;
        
        cVal.status = 7;
        cVal.note = "市场端入金";
        log(cVal.toString());
        capitalID = DAO.addCapitalInfo(cVal, conn);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        result.result = -10026L;
        result.remark = "添加流水信息时数据库异常";
        log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
        localReturnValue1 = result;
        
















































































        DAO.closeStatement(null, null, conn);return localReturnValue1;
      }
      if (result.result <= 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID, 0, conn);
      TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);
      

      InMoneyVO inMoneyInfo = new InMoneyVO(imm.amoutDate, imm.bankName, imm.outAccount, imm.personName, imm.inOutStart, imm.bankID, null, imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark, result.actionId, imm.payChannel);
      inMoneyInfo.setBankFlag(imm.bankFlag);
      BankAdapterRMI bankadapter = getAdapter(imm.bankID);
      if (bankadapter == null)
      {
        result.result = -920000L;
        result.remark = "网络异常，处理器无法连接适配器";
        log(result.remark);
      }
      else
      {
        result = bankadapter.inMoneyQueryBank(inMoneyInfo);
      }
      log("市场端调用适配器入金，市场返回信息：" + result.toString());
      































































      return result;
    }
    catch (SQLException e)
    {
      result.result = -10014L;
      result.remark = "数据库连接异常";
      log("市场端发起入金SQLException:" + result + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -10015L;
      result.remark = "系统异常";
      log("市场端发起入金Exception:" + result + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
  }
  
  public ReturnValue synchroAccountMarket(CorrespondValue cv)
  {
    System.out.println(">>>>市场同步账号方法>>>>时间：" + Tool.fmtTime(new Date()) + ">>>>\ncv[" + cv.toString() + "]");
    long checkResult = chenckCorrespondValue(cv);
    ReturnValue rv = new ReturnValue();
    CorrespondValue cv2 = null;
    if (checkResult == 0L)
    {
      rv.actionId = getMktActionID();
      try
      {
        List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim() + "' and account like '%" + cv.account.trim() + "%'");
        if ((cvresult == null) || (cvresult.size() <= 0))
        {
          rv.result = -40001L;
        }
        else
        {
          cv2 = (CorrespondValue)cvresult.get(0);
          cv2.isOpen = 3;
          cv2.status = cv.status;
          BankAdapterRMI bankadapter = getAdapter(cv2.bankID);
          if (bankadapter == null)
          {
            rv.result = -920000L;
          }
          else
          {
            rv = bankadapter.synchroAccount(cv2);
            if (rv.result == 0L) {
              rv.result = DAO.modCorrespond(cv2);
            }
          }
        }
      }
      catch (SQLException e)
      {
        rv.result = -40006L;
        e.printStackTrace();
        log("市场同步账号 交易商代码与银行帐号对应SQLException," + e);
      }
      catch (Exception e)
      {
        rv.result = -40007L;
        e.printStackTrace();
        log("市场同步账号 交易商代码与银行帐号对应SQLException," + e);
      }
    }
    else
    {
      rv.result = ((int)checkResult);
      rv.remark = "交易商代码与银行、帐号对应有误!";
    }
    return rv;
  }
  
  public ReturnValue modCapitalInfoStatus(long actionID, String funID)
  {
    ReturnValue rv = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      DAO.modCapitalInfoStatus(actionID, funID, conn);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      rv.result = -1L;
      rv.remark = ("修改 " + actionID + " 银行流水号 " + funID + " 数据库异常");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      rv.result = -1L;
      rv.remark = ("修改 " + actionID + " 银行流水号 " + funID + " 系统异常");
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return rv;
  }
  
  public long outMoneyForAccess(int rvResult, String bankid, String firmid, String account, String actionid, String funcid)
  {
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{适配器线程扫描银行，获得流水状态改变并通知市场{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("rvResult[" + rvResult + "]bankid[" + bankid + "]firmid[" + firmid + "]account[" + account + "]actionid[" + actionid + "]funcid[" + funcid + "]");
    System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
    
    long result = 0L;
    result = tradeDate(bankid);
    if (result != 0L) {
      return result;
    }
    Connection conn = null;
    
    long actionID = 0L;
    
    String funId = null;
    
    CapitalValue capital = null;
    
    CapitalValue rate = null;
    
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    try
    {
      result = rvResult;
      conn = DAO.getConnection();
      String filter = " where 1=1 and status in (2,3,4) and bankid='" + bankid + "' ";
      if (tf(firmid))
      {
        filter = filter + " and firmid='" + firmid + "' ";
        System.out.println("firmid非空，值为：" + firmid);
      }
      filter = filter + "  and actionid=" + actionid + " and funid='" + funcid + "' ";
      System.out.println(filter);
      
      Vector<CapitalValue> list = DAO.getCapitalInfoList(filter, conn);
      if (list.size() > 0)
      {
        for (int i = 0; i < list.size(); i++)
        {
          CapitalValue val = (CapitalValue)list.get(i);
          if (val.type == 1) {
            capital = val;
          } else if (val.type == 3) {
            rate = val;
          }
        }
        conn.setAutoCommit(false);
        Vector<CapitalValue> ll = DAO.getCapitalInfoList(filter + " for update ", conn);
        if ((ll == null) || (ll.size() <= 0))
        {
          result = -20042L;
          log("信息已在处理中");
          long l1 = result;return l1;
        }
        if (result == 0L)
        {
          dataProcess.updateFundsFull(capital.bankID, capital.firmID, capital.actionID, capital.money, 1, conn);
          
          dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);
          
          bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
          
          DAO.modCapitalInfoStatus(capital.iD, capital.funID, 0, curTime, conn);
          DAO.modCapitalInfoStatus(rate.iD, capital.funID, 0, curTime, conn);
          
          System.out.println("===适配器刷银行，返回成功---流水号：---" + capital.iD);
          DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理成功，出金成功", conn);
          DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理成功，扣除手续费成功", conn);
        }
        else
        {
          System.out.println("===适配器刷银行，返回失败------");
          if (getConfig("OutFailProcess").equalsIgnoreCase("true"))
          {
            dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);
            bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
            

            DAO.modCapitalInfoStatus(capital.iD, capital.funID, 1, curTime, conn);
            DAO.modCapitalInfoStatus(rate.iD, capital.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理失败，出金金额全部解冻", conn);
            DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理失败，手续费金额全部解冻", conn);
            result = 0L;
            log("审核通过，银行处理失败退还全部出金和手续费，错误码=" + result);
          }
          else
          {
            DAO.modCapitalInfoStatus(capital.iD, capital.funID, 1, curTime, conn);
            DAO.modCapitalInfoStatus(rate.iD, capital.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理失败，需手工解冻出金", conn);
            DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理失败，需手工解冻手续费", conn);
            result = -20019L;
            log("审核通过，银行处理失败，需要手工解冻出金和手续费，错误码=" + result);
          }
        }
        conn.commit();
      }
      else
      {
        System.out.println("===适配器刷银行，市场发起出金:记录资金流水错误------");
        result = -20038L;
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
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue sendHRBQSValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    BankAdapterRMI bankAdapter = getAdapter(bankID);
    Connection conn = null;
    try
    {
      result = isTradeDate(tradeDate);
      if (result.result != 0L) {
        return result;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log(e.getMessage());
      result.result = -1L;
      result.remark = "取得是否是交易日期异常";
      return result;
    }
    if (bankAdapter == null)
    {
      result.result = -920000L;
      result.remark = ("发送[" + bankID + "]银行交易商清算信息，连接适配器失败");
    }
    else
    {
      RZQSValue qs = getHRBQSValue(bankID, firmIDs, tradeDate);
      RZDZValue dz = getHRBDZValue(bankID, firmIDs, tradeDate);
      try
      {
        try
        {
          conn = DAO.getConnection();
          BankQSVO bq2 = new BankQSVO();
          bq2.bankID = bankID;
          bq2.tradeDate = tradeDate;
          bq2.tradeStatus = 2;
          bq2.note = ("银行" + bankID + "清算");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          String traded = sdf.format(tradeDate);
          System.out.println("===================传进来的交易日期：" + traded);
          Vector<BankQSVO> vt = DAO.getBankQSDate(" where trunc(tradedate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " and bankID='" + bankID + "'");
          System.out.println("####################确认清算日期表中是否已经当天信息,确认结果为：" + vt.size());
          if ((vt == null) || (vt.size() == 0))
          {
            System.out.println("##############如果没有相关记录就添加");
            int n = DAO.addBankQS(bq2, conn);
            System.out.println("#############添加结果：" + n);
          }
        }
        catch (SQLException e)
        {
          result.result = -1L;
          result.remark = "发送清算成功，插入清算表数据库异常";
          throw e;
        }
        catch (Exception e)
        {
          result.result = -1L;
          result.remark = "发送清算成功，插入清算表系统异常";
          throw e;
        }
        System.out.println("#####################开始调用适配器日终方法");
        result = bankAdapter.setRZ(qs, dz, tradeDate);
        System.out.println("#####################适配器的结果：" + result);
        if ((result != null) && (result.result == 0L)) {
          try
          {
            conn = DAO.getConnection();
            BankQSVO bq2 = new BankQSVO();
            bq2.bankID = bankID;
            bq2.tradeDate = tradeDate;
            bq2.tradeStatus = 1;
            bq2.note = ("银行" + bankID + "清算");
            Vector<BankQSVO> vt = DAO.getBankQSDate(" where trunc(tradedate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " and bankID='" + bankID + "'");
            if (((BankQSVO)vt.get(0)).tradeStatus != 0)
            {
              System.out.println("##############如果有相关记录切状态不为清算结果成功就修改状态为发送成功");
              int m = DAO.modBankQS(bq2, conn);
              System.out.println("#############修改结果：" + m);
            }
          }
          catch (SQLException e)
          {
            result.result = -1L;
            result.remark = "发送清算成功，插入清算表数据库异常";
            throw e;
          }
          catch (Exception e)
          {
            result.result = -1L;
            result.remark = "发送清算成功，插入清算表系统异常";
            throw e;
          }
        }
      }
      catch (Exception e)
      {
        result.result = -1L;
        result.remark = ("发送[" + bankID + "]银行交易商清算信息，适配器抛出异常");
        e.printStackTrace();
      }
    }
    return result;
  }
  
  public boolean tf(String str)
  {
    if ((str == null) || ("".equals(str)) || ("0".equals(str))) {
      return false;
    }
    return true;
  }
  
  public String getConfig(String key)
  {
    return Tool.getConfig(key);
  }
  
  public RZQSValue getHRBQSValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    RZQSValue result = null;
    try
    {
      ReturnValue rv = isTradeDate(tradeDate);
      if (rv.result != 0L) {
        return result;
      }
      if ((Tool.fmtDate(new Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
        return result;
      }
      result = DAO.getXYQSValue(bankID, firmIDs, tradeDate);
      
      Connection conn = DAO.getConnection();
      boolean flag = true;
      Date usdate = tradeDate;
      Map<String, FirmRightValue> todayDate = new HashMap();
      Vector<String> firmsTody = new Vector();
      Vector<FirmRightValue> vcTody = result.getFrv();
      for (int i = 0; i < vcTody.size(); i++)
      {
        todayDate.put(((FirmRightValue)vcTody.get(i)).firmID, (FirmRightValue)vcTody.get(i));
        firmsTody.add(((FirmRightValue)vcTody.get(i)).firmID);
      }
      Date lastQSDate = DAO.getMaxBankQSSuccessDate(bankID, usdate, conn);
      while (flag)
      {
        usdate = DAO.getlastDate(usdate, conn);
        if (usdate == null)
        {
          flag = false;
        }
        else if ((lastQSDate != null) && (usdate.after(lastQSDate)))
        {
          Map<String, FirmRightValue> lastDay = new HashMap();
          RZQSValue rzqsv = DAO.getXYQSValue(bankID, firmIDs, usdate);
          Vector<FirmRightValue> vc = rzqsv.getFrv();
          for (int i = 0; i < vc.size(); i++) {
            lastDay.put(((FirmRightValue)vc.get(i)).firmID, (FirmRightValue)vc.get(i));
          }
          Iterator<Map.Entry<String, FirmRightValue>> it = lastDay.entrySet().iterator();
          while (it.hasNext())
          {
            Map.Entry<String, FirmRightValue> ent = (Map.Entry)it.next();
            FirmRightValue firmLast = (FirmRightValue)ent.getValue();
            FirmRightValue firmTody = (FirmRightValue)todayDate.get(ent.getKey());
            if (firmTody != null)
            {
              firmTody.tradeFee = (Double.parseDouble(firmTody.tradeFee) + Double.parseDouble(firmLast.tradeFee));
              firmTody.settleFee = (Double.parseDouble(firmTody.settleFee) + Double.parseDouble(firmLast.settleFee));
              firmTody.firmErrorMoney += firmLast.firmErrorMoney;
              firmTody.cashMoney += firmLast.cashMoney;
              firmTody.billMoney += firmLast.billMoney;
              firmTody.availableBalance += firmLast.availableBalance;
              firmTody.cash += firmLast.cash;
            }
          }
        }
        else
        {
          flag = false;
        }
      }
      Vector<FirmRightValue> vcTodyFinal = new Vector();
      for (int i = 0; i < firmsTody.size(); i++) {
        vcTodyFinal.add((FirmRightValue)todayDate.get(firmsTody.get(i)));
      }
      log("清算记录数[" + vcTodyFinal.size() + "]");
      result.setFrv(vcTodyFinal);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      log(e.getMessage());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result == null) {
      result = new RZQSValue();
    }
    return result;
  }
  
  public RZDZValue getHRBDZValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    RZDZValue result = null;
    try
    {
      ReturnValue rv = isTradeDate(tradeDate);
      if (rv.result != 0L) {
        return result;
      }
      if ((Tool.fmtDate(new Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
        return result;
      }
      result = DAO.getXYDZValue(bankID, firmIDs, tradeDate);
      
      Connection conn = DAO.getConnection();
      boolean flag = true;
      Date usdate = tradeDate;
      Map<String, FirmDZValue> todayDate = new HashMap();
      Vector<String> firmsTody = new Vector();
      Vector<FirmDZValue> vcTody = result.getFdv();
      for (int i = 0; i < vcTody.size(); i++)
      {
        todayDate.put(((FirmDZValue)vcTody.get(i)).firmID, (FirmDZValue)vcTody.get(i));
        firmsTody.add(((FirmDZValue)vcTody.get(i)).firmID);
      }
      Date lastQSDate = DAO.getMaxBankQSSuccessDate(bankID, usdate, conn);
      while (flag)
      {
        usdate = DAO.getlastDate(usdate, conn);
        if (usdate == null)
        {
          flag = false;
        }
        else
        {
          Iterator<Map.Entry<String, FirmDZValue>> it;
          if ((lastQSDate != null) && (usdate.after(lastQSDate)))
          {
            Map<String, FirmDZValue> lastDay = new HashMap();
            RZDZValue rzdzv = DAO.getXYDZValue(bankID, firmIDs, usdate);
            Vector<FirmDZValue> vc = rzdzv.getFdv();
            for (int i = 0; i < vc.size(); i++) {
              lastDay.put(((FirmDZValue)vc.get(i)).firmID, (FirmDZValue)vc.get(i));
            }
            it = lastDay.entrySet().iterator();
          }
          else
          {
            flag = false;
          }
        }
      }
      Vector<FirmDZValue> vcTodyFinal = new Vector();
      for (int i = 0; i < firmsTody.size(); i++) {
        vcTodyFinal.add((FirmDZValue)todayDate.get(firmsTody.get(i)));
      }
      log("对账交易商数量[" + vcTodyFinal.size() + "]");
      result.setFdv(vcTodyFinal);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      log(e.getMessage());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result == null) {
      result = new RZDZValue();
    }
    return result;
  }
  
  public ReturnValue modBankQS(BankQSVO bq)
  {
    System.out.println("################处理器修改清算日期表方法被调用，传入参数：" + bq.toString());
    ReturnValue rv = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      try
      {
        conn.setAutoCommit(false);
        if (bq != null)
        {
          bq.createDate = new Date();
          rv.result = DAO.modBankQS(bq, conn);
        }
        rv.remark = "修改清算日期表成功";
        conn.commit();
      }
      catch (SQLException e)
      {
        conn.rollback();
        rv.result = -1L;
        rv.remark = "修改清算日期表，数据库异常";
        e.printStackTrace();
      }
      finally
      {
        conn.setAutoCommit(true);
      }
    }
    catch (SQLException e)
    {
      rv.result = -1L;
      rv.remark = "修改清算日期表，数据库异常";
      e.printStackTrace();
    }
    catch (Exception e)
    {
      rv.result = -1L;
      rv.remark = "传入清算对象，系统异常";
      e.printStackTrace();
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return rv;
  }
  
  public ReturnValue removeChro(CorrespondValue corr)
  {
    ReturnValue rv = new ReturnValue();
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("市场端发起解约");
    System.out.println("correspondValue[" + corr.toString() + "]");
    System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
    long result = 0L;
    Connection conn = null;
    if ((corr.bankID == null) || (corr.bankID.length() == 0) || (corr.firmID == null) || (corr.firmID.length() == 0) || (corr.account == null) || (corr.account.length() == 0))
    {
      rv.result = -40011L;
      rv.remark = "账户信息不完整";
      return rv;
    }
    label665:
    try
    {
      conn = DAO.getConnection();
      Vector<CorrespondValue> vector = DAO.getCorrespondList(" where bankID='" + corr.bankID + "' and firmID='" + corr.firmID + "' and account='" + corr.account + "'", conn);
      if ((vector == null) || (vector.size() <= 0))
      {
        result = -40014L;
        log("银行帐号注销，帐号未注册，错误码=" + result);
      }
      else
      {
        CorrespondValue cv = (CorrespondValue)vector.get(0);
        if (cv.frozenFuns > 0.0D)
        {
          rv.result = -920002L;
          rv.remark = "交易商冻结资金不为0";
          ReturnValue localReturnValue1 = rv;return localReturnValue1;
        }
        if ((cv.isOpen == 1) || (cv.isOpen == 3) || (cv.isOpen == 2))
        {
          BankAdapterRMI bankadapter = getAdapter(corr.bankID);
          ReturnValue returnValue = null;
          if (bankadapter != null) {
            returnValue = bankadapter.delAccount(corr);
          }
          if ((returnValue != null) && (returnValue.result == 0L))
          {
            returnValue.result = DAO.modCorrespond(corr);
          }
          else
          {
            log(returnValue.remark);
            result = -40015L;
            rv.result = result;
            rv.remark = "银行处理失败";
            log("银行帐号注销，银行解约失败，错误码=" + result);
          }
          if (returnValue.result == 0L)
          {
            if (bankadapter != null)
            {
              corr.isOpen = 3;
              corr.status = 1;
              returnValue = bankadapter.cancelAccount(corr);
              if (returnValue != null)
              {
                if (returnValue.result != 0L) {
                  break label665;
                }
                int m = DAO.delCorrespond(corr);
                if (m == 0)
                {
                  rv.result = 0L;
                  break label665;
                }
              }
            }
          }
          else {
            rv = returnValue;
          }
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -40016L;
      log("银行帐号注销SQLException，错误码=" + result + "  " + e);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result = -40017L;
      log("银行帐号注销Exception，错误码=" + result + "  " + e);
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return rv;
  }
  
  public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2, String bankID)
  {
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("市场端改约方法");
    System.out.println("cv1[" + cv1.toString() + "]\ncv2[" + cv2.toString() + "]");
    System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
    long checkResult = chenckCorrespondValue(cv1);
    System.out.println("银行开户销户变更帐户检验参数方法返回结果：" + checkResult);
    
    ReturnValue rv = new ReturnValue();
    if (checkResult == 0L)
    {
      rv.actionId = getMktActionID();
      try
      {
        BankAdapterRMI bankadapter = getAdapter(bankID);
        
        rv = bankadapter.modAccount(cv1, cv2);
        if (rv.result != 0L) {
          break label289;
        }
        rv.result = DAO.modCorrespond(cv2);
      }
      catch (RemoteException e)
      {
        rv.result = -920000L;
        e.printStackTrace();
        log("交易商改约提交适配器异常," + e);
      }
      catch (Exception e)
      {
        rv.result = -920000L;
        e.printStackTrace();
        log("交易商改约异常," + e);
      }
    }
    else
    {
      rv.result = ((int)checkResult);
    }
    label289:
    return rv;
  }
  
  public ReturnValue inMoneyResultQuery(String actionID, String bankID)
  {
    ReturnValue returnValue = null;
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("市场端发起入金结果查询");
    System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
    try
    {
      BankAdapterRMI bankadapter = getAdapter(bankID);
      returnValue = bankadapter.inMoneyResultQuery(actionID);
      
      String funID = returnValue.funID;
      if (returnValue.result == 0L)
      {
        returnValue = moneyInAudit(Long.parseLong(actionID), funID, true);
      }
      else if (returnValue.result == 5L)
      {
        log(actionID + "银行处理处理中");
        returnValue.type = 2;
      }
      else
      {
        log(returnValue.remark);
        returnValue = moneyInAudit(Long.parseLong(actionID), funID, false);
        returnValue.result = -100L;
        returnValue.remark = "银行处理失败";
        log("市场端查询入金结果，银行处理失败。错误码=" + returnValue.result);
      }
    }
    catch (RemoteException e)
    {
      returnValue.result = -920000L;
      e.printStackTrace();
      log("交易商改约提交适配器异常," + e);
    }
    catch (Exception e)
    {
      returnValue.result = -920000L;
      e.printStackTrace();
      log("交易商改约异常," + e);
    }
    return returnValue;
  }
  
  public ReturnValue outMoneyResultQuery(String actionID, String bankID)
  {
    ReturnValue returnValue = null;
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("市场端发起出金结果查询");
    System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
    try
    {
      BankAdapterRMI bankadapter = getAdapter(bankID);
      returnValue = bankadapter.outMoneyResultQuery(actionID);
      String funID = returnValue.funID;
      if (returnValue.result == 0L)
      {
        returnValue = moneyInAudit(Long.parseLong(actionID), funID, true);
      }
      else if (returnValue.result == 5L)
      {
        log(actionID + "银行处理处理中");
        returnValue.type = 2;
      }
      else
      {
        log(returnValue.remark);
        returnValue = moneyInAudit(Long.parseLong(actionID), funID, false);
        returnValue.result = -100L;
        returnValue.remark = "银行处理失败";
        log("市场端查询出金结果，银行处理失败。错误码=" + returnValue.result);
      }
    }
    catch (RemoteException e)
    {
      returnValue.result = -920000L;
      e.printStackTrace();
      log("交易商改约提交适配器异常," + e);
    }
    catch (Exception e)
    {
      returnValue.result = -920000L;
      e.printStackTrace();
      log("交易商改约异常," + e);
    }
    return returnValue;
  }
}

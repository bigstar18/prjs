package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.PABankDAO;
import gnnt.trade.bank.data.sfz.vo.BatCustDzBChild;
import gnnt.trade.bank.data.sfz.vo.BatCustDzFailChild;
import gnnt.trade.bank.data.sfz.vo.BatFailResultChild;
import gnnt.trade.bank.data.sfz.vo.BatQs;
import gnnt.trade.bank.data.sfz.vo.BatQsChild;
import gnnt.trade.bank.data.sfz.vo.KXHfailChild;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class PACapitalProcessor
  extends CapitalProcessor
{
  private static PABankDAO DAO = null;
  private static final int INMONEY = 0;
  private static final int OUTMONEY = 1;
  private static final int RATE = 2;
  
  public PACapitalProcessor()
  {
    try
    {
      if (DAO == null) {
        DAO = BankDAOFactory.getPADAO();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log("Dao初始化失败！");
    }
  }
  
  public String getConfig(String key)
  {
    return Tool.getConfig(key);
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
        Vector<FirmBalanceValue> floatingloss = DAO
          .getFlote(new String[] { firmid });
        if ((floatingloss != null) && (floatingloss.size() > 0)) {
          for (FirmBalanceValue fb : floatingloss) {
            if ((fb != null) && (firmid.equals(fb.firmId)))
            {
              fbv.floatingloss = fb.floatingloss;
              if (fb.floatingloss <= 0.0D) {
                break;
              }
              fbv.avilableBalance -= 
                fb.floatingloss;
              
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
  
  public ReturnValue destroyAccount(CorrespondValue cv)
  {
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("银行销户方法");
    System.out.println("cv[" + cv.toString() + "]");
    System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + 
      Tool.fmtTime(new Date()) + 
      "}}}}}}}}}}}}}}}}}}}}}}}}}");
    long checkResult = 0L;
    ReturnValue rv = new ReturnValue();
    if (checkResult == 0L)
    {
      rv.actionId = getMktActionID();
      try
      {
        cv.isOpen = 0;
        cv.status = 1;
        rv.result = DAO.modCorrespond(cv);
      }
      catch (SQLException e)
      {
        rv.result = -40016L;
        e.printStackTrace();
        log("银行销户 交易商代码与银行帐号对应SQLException," + e);
      }
      catch (Exception e)
      {
        rv.result = -40017L;
        e.printStackTrace();
        log("银行销户 交易商代码与银行帐号对应SQLException," + e);
      }
    }
    else
    {
      rv.result = -40011L;
      rv.remark = "交易商代码与银行、帐号对应有误!";
    }
    return rv;
  }
  
  public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe, Date date, String bankID)
  {
    log("获取银行交易商对账失败文件  getFirmBalanceError  时间：" + 
      Tool.fmtTime(new Date()));
    ReturnValue rv = new ReturnValue();
    if (fbe == null)
    {
      rv.result = -1L;
      rv.remark = "获取银行交易商对账失败文件，银行文件不存在";
    }
    else if (fbe.size() <= 0)
    {
      rv.result = 0L;
      rv.remark = "获取银行交易商对账失败文件，银行对账成功";
    }
    else
    {
      try
      {
        for (int i = 0; i < fbe.size(); i++)
        {
          BatFailResultChild bfr = (BatFailResultChild)fbe.get(i);
          bfr.TranDateTime = Tool.fmtDate(date);
          if (i == 0) {
            DAO.delFirmBalanceError(bankID, Tool.fmtDate(date));
          }
          DAO.addFirmBalanceError(bfr, bankID);
        }
        rv.result = 0L;
        rv.remark = "获取银行交易商对账失败文件，数据库保存成功";
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
    log("返回结果：\n" + rv.toString());
    return rv;
  }
  
  public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf, Date date, String bankID)
  {
    log("获取银行发送来的会员余额文件  getfirmBalanceFile  时间：" + 
      Tool.fmtTime(new Date()));
    ReturnValue rv = new ReturnValue();
    if (fbf == null)
    {
      rv.result = -1L;
      rv.remark = "获取银行发送来的会员余额文件，传入的信息为空";
    }
    else if (fbf.size() == 0)
    {
      rv.result = -1L;
      rv.remark = "获取银行发送来的会员余额文件，余额文件为空";
    }
    else
    {
      try
      {
        for (BatCustDzBChild bc : fbf)
        {
          Vector<BatCustDzBChild> vv = DAO.getFirmBalanceFile(
            bc.ThirdCustId, bankID, date);
          if ((vv != null) && (vv.size() > 0)) {
            DAO.modFirmBalanceFile(bc, date, bankID);
          } else {
            DAO.addFirmBalanceFile(bc, date, bankID);
          }
        }
        rv.result = 0L;
        rv.remark = "获取银行发送来的会员余额文件，市场保存信息成功";
      }
      catch (SQLException e)
      {
        rv.result = -1L;
        rv.remark = "获取银行发送来的会员余额文件，数据库异常";
      }
      catch (Exception e)
      {
        rv.result = -1L;
        rv.remark = "获取银行发送来的会员余额文件，系统异常";
      }
    }
    log("返回结果：\n" + rv.toString());
    return rv;
  }
  
  public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector, String bankID)
  {
    log("添加会员开销户信息，时间:" + Tool.fmtTime(new Date()));
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    ReturnValue rv = new ReturnValue();
    Vector<KXHfailChild> vt = null;
    KXHfailChild vv = null;
    if (vector == null)
    {
      rv.result = -1L;
      rv.remark = "添加会员开销户信息，传入的参数为空";
    }
    else
    {
      if (bankID.equals("21"))
      {
        String file = " and tradeDate=to_date('" + dateFormat.format(((KXHfailChild)vector.get(0)).tradeDate) + "','yyyy-MM-dd') and bankID='" + bankID + "' ";
        try
        {
          vt = DAO.getFirmKXH1(file);
          if (vt.size() > 0)
          {
            rv.result = -999L;
            rv.remark = "数据重复！";
            return rv;
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
      }
      for (KXHfailChild kxh : vector) {
        try
        {
          vv = DAO.getFirmKXH(kxh.funID);
          if (vv == null)
          {
            rv.result = DAO.addFirmKXH(kxh, bankID);
            if (rv.result != 0L) {
              rv.remark = "取银行签解约文件成功!";
            }
          }
        }
        catch (SQLException e)
        {
          e.printStackTrace();
          log("添加数据SQL异常：\n" + kxh.toString());
        }
        catch (Exception e)
        {
          e.printStackTrace();
          log("添加数据系统异常：\n" + kxh.toString());
        }
      }
    }
    log("返回结果：\n" + rv.toString());
    return rv;
  }
  
  public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd, Date date, String bankID)
  {
    log("获取银行发送来的对账不平文件  getBatCustDz  时间：" + 
      Tool.fmtTime(new Date()));
    ReturnValue rv = new ReturnValue();
    if (bcd == null)
    {
      rv.result = -1L;
      rv.remark = "传入的对账不平信息为空";
    }
    else if (bcd.size() == 0)
    {
      rv.result = 0L;
      rv.remark = "传入的对账不平文件为空，银行对账成功";
    }
    else
    {
      try
      {
        DAO.delBatCustDz(date, bankID);
        for (BatCustDzFailChild cd : bcd) {
          DAO.addBatCustDz(cd, date, bankID);
        }
        rv.result = 0L;
        rv.remark = "传入的对账不平文件不为空，信息已经添加入数据库中";
      }
      catch (SQLException e)
      {
        rv.result = -1L;
        rv.remark = "修改对账不平信息，数据库异常";
        e.printStackTrace();
      }
      catch (Exception e)
      {
        rv.result = -1L;
        rv.remark = "修改对账不平信息，系统异常";
        e.printStackTrace();
      }
    }
    log("返回结果：\n" + rv.toString());
    return rv;
  }
  
  public ReturnValue sentMaketQS(Date date, String bankID)
  {
    log("发送市场清算文件  sentMaketQS  时间：" + Tool.fmtTime(new Date()));
    log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
    ReturnValue rv = new ReturnValue();
    Connection conn = null;
    BatQs bq = new BatQs();
    try
    {
      bq.child = getQSChild(date, bankID);
      bq.rowCount = bq.child.size();
      BankAdapterRMI bankadapter = getAdapter(bankID);
      if (bankadapter != null)
      {
        ReturnValue rrv = bankadapter.sentMaketQS(bq);
        if (rrv.result >= 0L)
        {
          conn = DAO.getConnection();
          





          rv.result = 0L;
          rv.remark = "发送给银行清算文件成功";
        }
        else
        {
          rv.result = rrv.result;
          rv.remark = rrv.remark;
        }
      }
      else
      {
        rv.result = -920000L;
        rv.remark = "发送市场清算文件，处理器获取适配器失败";
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      rv.result = -2L;
      rv.remark = "发送市场清算文件异常";
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    log("返回结果：\n" + rv.toString());
    
    return rv;
  }
  
  private Vector<BatQsChild> getQSChild(Date date, String bankID)
  {
    log("获取清算信息  getQSChild  时间：" + Tool.fmtTime(new Date()));
    














    Vector<BatQsChild> result = new Vector();
    Map<String, BatQsChild> today = null;
    Connection conn = null;
    boolean flag = true;
    Date usdate = date;
    try
    {
      conn = DAO.getConnection();
      today = DAO.getQSChild(bankID, null, null, usdate, conn);
      System.out.println(today.size());
      















































































































































































      Iterator<Map.Entry<String, BatQsChild>> it = today.entrySet()
        .iterator();
      while (it.hasNext())
      {
        Map.Entry<String, BatQsChild> ent = 
          (Map.Entry)it.next();
        result.add((BatQsChild)ent.getValue());
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
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    System.out.println("返回信息条数：" + result.size());
    return result;
  }
  
  public ReturnValue getBankFileStatus(Date tradeDate, int type, String bankID)
  {
    log("查询银行生成文件的状态  getBankFileStatus");
    log("参数：tradeDate[" + Tool.fmtDate(tradeDate) + "]type[" + type + 
      "]bankID[" + bankID + "]");
    ReturnValue rv = new ReturnValue();
    BankAdapterRMI bankadapter = getAdapter(bankID);
    if (bankadapter == null)
    {
      rv.result = -920000L;
      rv.remark = "连接适配器时失败";
    }
    else
    {
      try
      {
        rv = bankadapter.getBankFileStatus(tradeDate, tradeDate, type);
        if (type == 1) {
          if (rv.result == 8L) {
            rv.remark = "清算处理全部成功！";
          } else if (rv.result == 6L) {
            rv.remark = "清算处理部分成功，请查看清算对账不平记录！";
          } else if (rv.result == 7L) {
            rv.remark = "清算处理全部失败！";
          } else if (rv.result == 9L) {
            rv.remark = "处理完成,但生成处理结果文件失败!";
          } else {
            rv.remark = "文件读取失败！";
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        rv.result = -920001L;
        rv.remark = "适配器查询银行文件生成状态时异常";
      }
    }
    return rv;
  }
  
  public long modBankQS(String bankid, int status, Date date)
  {
    System.out.println("<<<<<更新银行清算结果状态 " + new Date().toLocaleString() + 
      "  >>>>>>>>");
    Connection conn = null;
    long result = -1L;
    try
    {
      conn = DAO.getConnection();
      BankQSVO bq = new BankQSVO();
      bq.bankID = bankid;
      bq.tradeStatus = status;
      bq.tradeDate = date;
      result = DAO.modBankQS(bq, conn);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}

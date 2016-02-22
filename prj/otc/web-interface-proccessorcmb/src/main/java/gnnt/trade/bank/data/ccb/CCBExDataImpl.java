package gnnt.trade.bank.data.ccb;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CCBCapitalProcessor;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.CCBBankDAO;
import gnnt.trade.bank.data.ccb.vo.CCBConstant;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CCBExDataImpl
{
  private CCBBankDAO DAO;
  
  public CCBExDataImpl()
  {
    try
    {
      this.DAO = BankDAOFactory.getCCBDAO();
    }
    catch (ClassNotFoundException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (IllegalAccessException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (InstantiationException e)
    {
      log(Tool.getExceptionTrace(e));
    }
  }
  
  private Date getNextTraDate(Date tradeDate)
  {
    Calendar ca = Calendar.getInstance();
    ca.setTime(tradeDate);
    CCBCapitalProcessor cp = new CCBCapitalProcessor();
    for (int i = 0; i < 365; i++)
    {
      ca.add(5, 1);
      if (cp.isTradeDate(ca.getTime()).result == 0L) {
        break;
      }
    }
    return ca.getTime();
  }
  
  private String fmtDate(Date time)
  {
    String result = "";
    try
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      result = sdf.format(time);
    }
    catch (Exception e)
    {
      Tool.getExceptionTrace(e);
    }
    return result;
  }
  
  private BankAdapterRMI getAdapter()
  {
    return new CapitalProcessor().getAdapter(CCBConstant.bankID);
  }
  
  public ReturnValue send(Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    Vector<FirmBalance> list = getFirmBalance(tradeDate);
    ReturnValue result1 = send("ZYQQS_", list, tradeDate);
    if (result1.result < 0L) {
      result.result = -1L;
    }
    result.remark += result1.remark;
    return result;
  }
  
  public ReturnValue sendTZData(Date tradeDate, List<String> list1)
  {
    ReturnValue result = new ReturnValue();
    Vector<FirmBalance> list = getFirmBalance(tradeDate);
    ReturnValue result1 = sendTZ("ZYQQS_", list, tradeDate, list1);
    if (result1.result < 0L) {
      result.result = -1L;
    }
    result.remark += result1.remark;
    return result;
  }
  
  private ReturnValue send(String fileName, Vector<FirmBalance> list, Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    ReturnValue result1 = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = this.DAO.getConnection();
      if ("XH".equalsIgnoreCase(Tool.getConfig("qs")))
      {
        Vector<FirmBalance> vector1 = this.DAO.getCCBQS(conn, " and bankiD='" + CCBConstant.bankID + "' and trunc(CREATEDATE)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ");
        if (vector1.size() > 0)
        {
          result.result = -1L;
          result.remark = ("给银行[" + CCBConstant.bankID + "]发送数据，已发送过");
          return result;
        }
        this.DAO.addCCBQS(list, tradeDate, conn);
        list = this.DAO.getCCBQS(conn, " and bankiD='" + CCBConstant.bankID + "' and trunc(CREATEDATE)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')and flag=0");
        
        vector1 = this.DAO.getCCBQS(conn, " and bankiD='" + CCBConstant.bankID + "' and trunc(CREATEDATE)<to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') and flag=1");
        log("取到数据了，准备发送数据");
        result1 = getAdapter().sendTradeDate(list, vector1);
      }
      else
      {
        result1 = getAdapter().sendTradeDate(list);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result1.result >= 0L)
    {
      result.remark = ("文件 " + fileName + "发送到适配器成功");
    }
    else
    {
      result.result = -1L;
      result.remark = ("文件 " + fileName + "发送到适配器失败");
    }
    return result;
  }
  
  private ReturnValue sendTZ(String fileName, Vector<FirmBalance> list, Date tradeDate, List<String> list1)
  {
    ReturnValue result = new ReturnValue();
    ReturnValue result1 = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = this.DAO.getConnection();
      if ("XH".equalsIgnoreCase(Tool.getConfig("qs")))
      {
        Vector<FirmBalance> vector1 = this.DAO.getCCBQS(conn, " and bankiD='" + CCBConstant.bankID + "' and trunc(CREATEDATE)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ");
        if (list1.size() > 0) {
          list = this.DAO.getCCBQS(conn, list1);
        }
        vector1 = this.DAO.getCCBQS(conn, " and bankiD='" + CCBConstant.bankID + "' and trunc(CREATEDATE)<to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') and flag=1");
        log("取到数据了，准备发送数据");
        result1 = getAdapter().sendTradeDate(list, vector1);
      }
      else
      {
        result1 = getAdapter().sendTradeDate(list);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result1.result >= 0L)
    {
      result.remark = ("文件 " + fileName + "发送到适配器成功");
    }
    else
    {
      result.result = -1L;
      result.remark = ("文件 " + fileName + "发送到适配器失败");
    }
    return result;
  }
  
  private String getContent(List<FirmBalance> vec, Date tradeDate)
  {
    int[] fieldlen_ZYQQS = { 11, 10, 8, 2, 17, 17 };
    StringBuffer head = new StringBuffer();
    StringBuilder sb = new StringBuilder();
    double sunFee = 0.0D;
    double sunQYChange = 0.0D;
    int id = 0;
    if ((vec != null) && (vec.size() > 0)) {
      for (FirmBalance obj : vec)
      {
        id++;
        sb.append(obj.toString(id));
        sunFee += obj.FeeMoney;
        

        sunQYChange += obj.QYChangeMoney;
      }
    }
    head.append(Common.fmtStrField(vec.size(), fieldlen_ZYQQS[0], "0", 0));
    head.append(Common.fmtStrField(CCBConstant.marketID, fieldlen_ZYQQS[1], 
      " ", 0));
    
    head.append(Common.fmtStrField(fmtDate(tradeDate), fieldlen_ZYQQS[2], 
      " ", 0));
    head.append(Common.fmtStrField("1", fieldlen_ZYQQS[3], "0", 0));
    head.append(Common.fmtStrField(sunQYChange, fieldlen_ZYQQS[4], "0", 0));
    head.append(Common.fmtStrField(sunFee, fieldlen_ZYQQS[5], "0", 0));
    head.append("\n");
    head.append(sb);
    return head.toString();
  }
  
  private Vector<FirmBalance> getFirmBalance(Date tradeDate)
  {
    Vector<FirmBalance> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    
















    String sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-ffb.tradefee) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,ffb.tradefee fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
    










      CCBConstant.bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + CCBConstant.bankID + "' and f.isOpen=1 ) fbf ," + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      
      " and fbf.firmID=tf.firmid(+)";
    try
    {
      log(sql);
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      FirmBalance value = null;
      while (rs.next())
      {
        value = new FirmBalance();
        value.firmID = rs.getString("contact");
        value.bankID = CCBConstant.bankID;
        value.account = rs.getString("account");
        value.accountName = rs.getString("accountName");
        value.FeeMoney = rs.getDouble("fee");
        value.QYChangeMoney = rs.getDouble("qyChange");
        value.MQYChangeMoney = rs.getDouble("MQYChange");
        value.QYMoney = rs.getDouble("capital");
        value.date = tradeDate;
        value.fundio = rs.getDouble("fundio");
        value.firmtype = rs.getString("firmtype");
        

        result.add(value);
      }
      if (result.size() <= 0)
      {
        log("当天没有查询到清算数据，将所有签约建行的交易商的清算数据置为0，确保清算成功");
        String filter = " and bankid='" + CCBConstant.bankID + "' and isopen=1";
        Vector<CorrespondValue> vect = this.DAO.getCorrespondList(filter, conn);
        if ((vect != null) && (vect.size() > 0)) {
          for (CorrespondValue cspv : vect)
          {
            value = new FirmBalance();
            value.firmID = cspv.firmID;
            value.bankID = cspv.bankID;
            value.account = cspv.account;
            value.accountName = cspv.accountName;
            value.FeeMoney = 0.0D;
            value.QYChangeMoney = 0.0D;
            value.MQYChangeMoney = 0.0D;
            value.QYMoney = 0.0D;
            value.date = tradeDate;
            value.fundio = 0.0D;
            

            result.add(value);
          }
        }
      }
      log("清算sql查询到的记录： " + result.size() + " 条");
    }
    catch (SQLException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      this.DAO.closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
}

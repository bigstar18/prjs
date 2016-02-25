package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.server.GlobalContainer;
import gnnt.MEBS.vendue.server.KernelEngineBID;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.BroadcastVO;
import gnnt.MEBS.vendue.server.vo.CommodityPropertyVO;
import gnnt.MEBS.vendue.server.vo.CommodityTypeVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.CurSubmitVO;
import gnnt.MEBS.vendue.server.vo.FlowControlVO;
import gnnt.MEBS.vendue.server.vo.MoneyVO;
import gnnt.MEBS.vendue.server.vo.QueryValue;
import gnnt.MEBS.vendue.server.vo.SysCurStatusVO;
import gnnt.MEBS.vendue.server.vo.SysPartitionVO;
import gnnt.MEBS.vendue.server.vo.SysPropertyVO;
import gnnt.MEBS.vendue.server.vo.TradeQuotationVO;
import gnnt.MEBS.vendue.server.vo.TradeResuleValue;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.util.Configuration;
import gnnt.MEBS.vendue.util.Tool;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.naming.NamingException;
import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.CLOB;

public class TradeDAOImpl
  extends TradeDAO
{
  protected TradeDAOImpl()
    throws NamingException
  {}
  
  public long addCurSubmit(CurSubmitVO paramCurSubmitVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    long l = -1L;
    try
    {
      str = "select sp_v_cursubmit.nextval from dual";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        l = localResultSet.getLong(1);
      }
      str = "insert into v_cursubmit(tradepartition,  code, price, amount, userid, submittime, tradeflag, validamount, modifytime,id,traderId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramCurSubmitVO.tradePartition);
      localPreparedStatement.setString(2, paramCurSubmitVO.code);
      localPreparedStatement.setDouble(3, paramCurSubmitVO.price);
      localPreparedStatement.setLong(4, paramCurSubmitVO.amount);
      localPreparedStatement.setString(5, paramCurSubmitVO.userID);
      localPreparedStatement.setTimestamp(6, paramCurSubmitVO.submitTime);
      localPreparedStatement.setInt(7, paramCurSubmitVO.tradeFlag);
      localPreparedStatement.setLong(8, paramCurSubmitVO.validAmount);
      localPreparedStatement.setTimestamp(9, paramCurSubmitVO.modifytime);
      localPreparedStatement.setLong(10, l);
      localPreparedStatement.setString(11, paramCurSubmitVO.traderID);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
    return l;
  }
  
  public void modifyCurSubmit(CurSubmitVO paramCurSubmitVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_cursubmit set tradePartition=?,code=?,price=?,amount=?,userID=?,tradeFlag=?,validAmount=?,modifytime=?,traderId=? where id=?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramCurSubmitVO.tradePartition);
      localPreparedStatement.setString(2, paramCurSubmitVO.code);
      localPreparedStatement.setDouble(3, paramCurSubmitVO.price);
      localPreparedStatement.setLong(4, paramCurSubmitVO.amount);
      localPreparedStatement.setString(5, paramCurSubmitVO.userID);
      localPreparedStatement.setInt(6, paramCurSubmitVO.tradeFlag);
      localPreparedStatement.setLong(7, paramCurSubmitVO.validAmount);
      localPreparedStatement.setTimestamp(8, paramCurSubmitVO.modifytime);
      localPreparedStatement.setString(9, paramCurSubmitVO.traderID);
      localPreparedStatement.setLong(10, paramCurSubmitVO.iD);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public CurSubmitVO getCurSubmit(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CurSubmitVO localCurSubmitVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select tradepartition, code, price, amount, userid, submittime, tradeflag, validamount, modifytime,traderID from v_cursubmit where id=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCurSubmitVO = new CurSubmitVO();
        localCurSubmitVO.tradePartition = localResultSet.getInt(1);
        localCurSubmitVO.iD = paramLong;
        localCurSubmitVO.code = localResultSet.getString(2);
        localCurSubmitVO.price = localResultSet.getDouble(3);
        localCurSubmitVO.amount = localResultSet.getLong(4);
        localCurSubmitVO.userID = localResultSet.getString(5);
        localCurSubmitVO.submitTime = localResultSet.getTimestamp(6);
        localCurSubmitVO.tradeFlag = localResultSet.getInt(7);
        localCurSubmitVO.validAmount = localResultSet.getLong(8);
        localCurSubmitVO.modifytime = localResultSet.getTimestamp(9);
        localCurSubmitVO.traderID = localResultSet.getString(10);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localCurSubmitVO;
  }
  
  public CurSubmitVO[] getCurSubmitList(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CurSubmitVO[] arrayOfCurSubmitVO = null;
    CurSubmitVO localCurSubmitVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select tradepartition, code, price, amount, userid, submittime, tradeflag, validamount, modifytime,id,traderID from v_cursubmit " + paramString;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCurSubmitVO = new CurSubmitVO();
        localCurSubmitVO.tradePartition = localResultSet.getInt(1);
        localCurSubmitVO.code = localResultSet.getString(2);
        localCurSubmitVO.price = localResultSet.getDouble(3);
        localCurSubmitVO.amount = localResultSet.getLong(4);
        localCurSubmitVO.userID = localResultSet.getString(5);
        localCurSubmitVO.submitTime = localResultSet.getTimestamp(6);
        localCurSubmitVO.tradeFlag = localResultSet.getInt(7);
        localCurSubmitVO.validAmount = localResultSet.getLong(8);
        localCurSubmitVO.modifytime = localResultSet.getTimestamp(9);
        localCurSubmitVO.iD = localResultSet.getLong(10);
        localCurSubmitVO.traderID = localResultSet.getString(11);
        localVector.add(localCurSubmitVO);
      }
      arrayOfCurSubmitVO = new CurSubmitVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCurSubmitVO[i] = ((CurSubmitVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCurSubmitVO;
  }
  
  public TradeQuotationVO[] getTradeQuotationList(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TradeQuotationVO[] arrayOfTradeQuotationVO = null;
    TradeQuotationVO localTradeQuotationVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select tradepartition, id, submitid, code, price, vaidamount, userid, lasttime, countdownstart, countdowntime, section from v_tradequotation " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localTradeQuotationVO = new TradeQuotationVO();
        localTradeQuotationVO.tradePartition = localResultSet.getInt(1);
        localTradeQuotationVO.iD = localResultSet.getLong(2);
        localTradeQuotationVO.submitID = localResultSet.getLong(3);
        localTradeQuotationVO.code = localResultSet.getString(4);
        localTradeQuotationVO.price = localResultSet.getDouble(5);
        localTradeQuotationVO.vaidAmount = localResultSet.getLong(6);
        localTradeQuotationVO.userID = localResultSet.getString(7);
        localTradeQuotationVO.lastTime = localResultSet.getTimestamp(8);
        localTradeQuotationVO.countdownStart = localResultSet.getTimestamp(9);
        localTradeQuotationVO.countdownTime = localResultSet.getInt(10);
        localTradeQuotationVO.section = localResultSet.getInt(11);
        localVector.add(localTradeQuotationVO);
      }
      arrayOfTradeQuotationVO = new TradeQuotationVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfTradeQuotationVO[i] = ((TradeQuotationVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfTradeQuotationVO;
  }
  
  public CurSubmitVO[] getCurSubmitList(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CurSubmitVO[] arrayOfCurSubmitVO = null;
    CurSubmitVO localCurSubmitVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select tradepartition, code, price, amount, userid, submittime, tradeflag, validamount, modifytime,id,traderID from v_cursubmit " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCurSubmitVO = new CurSubmitVO();
        localCurSubmitVO.tradePartition = localResultSet.getInt(1);
        localCurSubmitVO.code = localResultSet.getString(2);
        localCurSubmitVO.price = localResultSet.getDouble(3);
        localCurSubmitVO.amount = localResultSet.getLong(4);
        localCurSubmitVO.userID = localResultSet.getString(5);
        localCurSubmitVO.submitTime = localResultSet.getTimestamp(6);
        localCurSubmitVO.tradeFlag = localResultSet.getInt(7);
        localCurSubmitVO.validAmount = localResultSet.getLong(8);
        localCurSubmitVO.modifytime = localResultSet.getTimestamp(9);
        localCurSubmitVO.iD = localResultSet.getLong(10);
        localCurSubmitVO.traderID = localResultSet.getString(11);
        localVector.add(localCurSubmitVO);
      }
      arrayOfCurSubmitVO = new CurSubmitVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCurSubmitVO[i] = ((CurSubmitVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfCurSubmitVO;
  }
  
  public void delCurSubmit(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "delete from v_cursubmit where id=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void delCurSubmit(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "delete from v_cursubmit " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void delCurCommodity(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "delete from v_curcommodity " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void dealCommodity(long paramLong, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_curCommodity set bargainFlag=1 where commodityid=?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localPreparedStatement.executeUpdate();
      str = "update v_commodity set status=1 where id=?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void addTradeQuotation(TradeQuotationVO paramTradeQuotationVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_tradequotation(tradepartition, id, submitid, code, price, vaidamount, userid, lasttime, countdownstart, countdowntime, section) values(?, sp_v_tradequotation.nextval, ?, ?, ?, ?, ?, ?, sysdate, ?, ?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramTradeQuotationVO.tradePartition);
      localPreparedStatement.setLong(2, paramTradeQuotationVO.submitID);
      localPreparedStatement.setString(3, paramTradeQuotationVO.code);
      localPreparedStatement.setDouble(4, paramTradeQuotationVO.price);
      localPreparedStatement.setLong(5, paramTradeQuotationVO.vaidAmount);
      localPreparedStatement.setString(6, paramTradeQuotationVO.userID);
      localPreparedStatement.setTimestamp(7, paramTradeQuotationVO.lastTime);
      localPreparedStatement.setInt(8, paramTradeQuotationVO.countdownTime);
      localPreparedStatement.setInt(9, paramTradeQuotationVO.section);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void addQuotationWithCountdownstart(TradeQuotationVO paramTradeQuotationVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_tradequotation(tradepartition, id, submitid, code, price, vaidamount, userid, lasttime, countdownstart, countdowntime, section) values(?, sp_v_tradequotation.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramTradeQuotationVO.tradePartition);
      localPreparedStatement.setLong(2, paramTradeQuotationVO.submitID);
      localPreparedStatement.setString(3, paramTradeQuotationVO.code);
      localPreparedStatement.setDouble(4, paramTradeQuotationVO.price);
      localPreparedStatement.setLong(5, paramTradeQuotationVO.vaidAmount);
      localPreparedStatement.setString(6, paramTradeQuotationVO.userID);
      localPreparedStatement.setTimestamp(7, paramTradeQuotationVO.lastTime);
      localPreparedStatement.setTimestamp(8, paramTradeQuotationVO.countdownStart);
      localPreparedStatement.setInt(9, paramTradeQuotationVO.countdownTime);
      localPreparedStatement.setInt(10, paramTradeQuotationVO.section);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void addTradeQuotation(TradeQuotationVO paramTradeQuotationVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "insert into v_tradequotation(tradepartition, id, submitid, code, price, vaidamount, userid, lasttime, countdownstart, countdowntime, section) values(?, sp_v_tradequotation.nextval, ?, ?, ?, ?, ?, ?, sysdate, ?, ?)";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramTradeQuotationVO.tradePartition);
      localPreparedStatement.setLong(2, paramTradeQuotationVO.submitID);
      localPreparedStatement.setString(3, paramTradeQuotationVO.code);
      localPreparedStatement.setDouble(4, paramTradeQuotationVO.price);
      localPreparedStatement.setLong(5, paramTradeQuotationVO.vaidAmount);
      localPreparedStatement.setString(6, paramTradeQuotationVO.userID);
      localPreparedStatement.setTimestamp(7, paramTradeQuotationVO.lastTime);
      localPreparedStatement.setInt(8, paramTradeQuotationVO.countdownTime);
      localPreparedStatement.setInt(9, paramTradeQuotationVO.section);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void delTradeQuotation(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "delete from v_tradequotation " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public String getContractTemplet(int paramInt, Connection paramConnection)
    throws SQLException
  {
    Statement localStatement = null;
    CLOB localCLOB = null;
    String str1 = "select templet from v_contracttemplet where type=" + paramInt;
    localStatement = paramConnection.createStatement();
    OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str1);
    while (localOracleResultSet.next()) {
      localCLOB = localOracleResultSet.getCLOB(1);
    }
    localOracleResultSet.close();
    localOracleResultSet = null;
    localStatement.close();
    localStatement = null;
    String str2 = null;
    if (localCLOB == null) {
      str2 = "<html><head><metacontent='text/html;charset=gb2312'http-equiv='Content-Type'/><style type='text/css'>.top_txt{\tfont-size:18px;\tfont-weight:bold;\tcolor:#FFFF00;}body{\tmargin-left:0px;\tmargin-top:0px;\tmargin-right:0px;\tmargin-bottom:0px;}.txt{\tline-height:18px;\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;\ttext-decoration:none;}.xbt{\tfont-size:12px;\tfont-weight:bold;\tcolor:#175768;\ttext-decoration:none;}.imp{\tfont-size:12px;\tfont-weight:normal;\tcolor:#FF3300;\ttext-decoration:none;}.td_nr{\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;\ttext-decoration:none;\ttext-align:left;}td{\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;}.k{\theight:16px;\tborder-top-width:1px;border-right-width:1px;\tborder-bottom-width:1px;border-left-width:1px;\tborder-top-style:dotted;border-right-style:dotted;\tborder-bottom-style:solid;border-left-style:dotted;\tborder-top-color:#FFFFFF;border-right-color:#FFFFFF;\tborder-bottom-color:#175768;border-left-color:#FFFFFF;\tfont-size:12px;\tfont-weight:normal;color:#175768;\ttext-decoration:none;}</style><metacontent='MSHTML6.00.2900.2802'name='GENERATOR'/><title>合同</title></head><body><table style='width:15.6cm'height='340'cellspacing='0'cellpadding='0'align='center'border='0'><tbody>\t<tr><td valign='top'><table height='35'cellspacing='0'cellpadding='0'width='100%'border='0'><tbody><tr><td bgcolor='#175768'><div class='top_txt'align='center'>竞价交易合同</div></td></tr></tbody></table><table height='270'cellspacing='0'cellpadding='0'width='100%'align='center'border='0'><tbody><tr><td valign='top'bgcolor='#ffffff'><table height='761'cellspacing='0'cellpadding='0'width='98%'align='center'border='0'><tbody><tr><td class='txt'valign='top'><br/><table cellspacing='0'cellpadding='0'width='100%'border='0'><tbody><tr><td width='10%'><div class='xbt'align='right'>卖方：</div></td><td width='55%'>buy_no</td><td width='15%'height='24'><div class='xbt'align='right'>合同编号：</div></td><td class='td_nr'>t_contractid号</td></tr><tr><td height='24'><div class='xbt'align='right'>买方：</div></td><td class='td_nr'>sell_no</td><td height='24'><divclass='xbt'align='right'>&nbsp;</div></td><td class='td_nr'>&nbsp;</td></tr></tbody></table>\t<p><strong>第一条</strong>&nbsp;&nbsp;中标标的号、品种、数量、单价、价款：</p><div align='center'><table style='border-collapse:collapse'bordercolor='#175768'cellspacing='0'cellpadding='0'bgcolor='#175768'border='1'><tbody><tr><td width='91'bgcolor='#ffffff'height='24'><p class='xbt'align='center'>标的号</p></td><td width='98'bgcolor='#ffffff'><p class='xbt'align='center'>品种</p></td><td width='112'bgcolor='#ffffff'><p class='xbt'align='center'>数量（units）</p></td><td width='140'bgcolor='#ffffff'><p class='xbt'align='center'>单价（元/units）</p></td><td width='147'bgcolor='#ffffff'><p class='xbt'align='center'>金额（元）</p></td></tr><tr><td width='91'bgcolor='#ffffff'height='24'><p align='center'>&nbsp;t_code</p></td><td width='98'bgcolor='#ffffff'><p align='center'>&nbsp;t_type</p></td><td width='112'bgcolor='#ffffff'><p align='center'>&nbsp;t_amount</p></td><td width='140'bgcolor='#ffffff'><p align='center'>&nbsp;t_price</p></td><td width='147'bgcolor='#ffffff'><p align='center'>&nbsp;t_money</p></td></tr><tr><td width='588'bgcolor='#ffffff'colspan='6'height='24'><p>&nbsp;&nbsp;<span class='xbt'>合计人民币（大写金额）：</span>&nbsp;t_bmoney</p></td></tr></tbody></table></div><span class='xbt'>第二条</span>&nbsp;&nbsp;质量标准、用途：中标标的号对应的质量等级。<br/><span class='xbt'>第三条</span>&nbsp;&nbsp;交（提）货期限：见《竞价销售交易细则》第二十七条之规定。<br/><span class='xbt'>第四条</span>&nbsp;&nbsp;交（提）货地点：中标标的号对应的存储地点。<br/><span class='xbt'>第五条</span>&nbsp;&nbsp;包装物标准及费用负担（含打包、上汽车费用）：见《竞价销售交易细则》第二十九条。<br/><span class='xbt'>第六条</span>&nbsp;&nbsp;交货方式：卖方承储库仓内交货。<br/><span class='xbt'>第七条</span>&nbsp;&nbsp;运输方式及到达站（港）和费用负担：买方自负。<br/><span class='xbt'>第八条</span>&nbsp;&nbsp;货物验收办法：买方提货时验收，数量以卖方交货仓库计量衡器为准。<br/>\t<span class='xbt'>第九条</span>&nbsp;&nbsp;结算方式：委托交易市场结算。<br/><span class='xbt'>第十条</span>&nbsp;&nbsp;违约责任：按照《竞价销售交易细则》第三十八、三十九、四十、四十一条执行。<br/><span class='xbt'>第十一条</span>&nbsp;&nbsp;合同争议的解决方式：本合同在履行过程中发生的争议由当事人协商解决，交易市场负责调解；当事人协商调解不成的，提交行业争议仲裁中心仲裁。<br/>&nbsp;&nbsp;&nbsp;附则：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）本合同签订双方承认并遵守《竞价销售交易细则》及《竞价销售清单》。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）本合同签订双方需按成交金额的一定比例分别向交易市场交纳手续费。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（3）卖方、买方签订合同前按一定比例交纳履约保证金交交易市场。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（4）本合同经双方交易代表签字盖章后生效。<br/><span class='xbt'>第十二条</span>&nbsp;&nbsp;其他约定事项：<br/><span class='xbt'>第十三条</span>&nbsp;&nbsp;本合同一式三份，买卖双方各执一份，交易市场留存一份。<p>&nbsp;</p><table cellspacing='0'cellpadding='0'width='90%'align='center'border='0'><tbody><tr><td width='24%'height='29%'><div align='right'>卖方（代章）：</div></td><td width='27%'>&nbsp;</td><td width='24%'height='24%'><div align='right'>买方名称：</div></td><td width='21%'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>承储库点名称：</div></td><td>&nbsp;</td><td height='24%'><div align='right'>地&nbsp;&nbsp;址：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='29%'height='24%'><div align='right'>法定代表人（签字）：</div></td><td>&nbsp;</td><td height='29%'height='24%'><div align='right'>委托代理人（签字）：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>电&nbsp;&nbsp;话：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>电&nbsp;&nbsp;话：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>开户银行：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>开户银行：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>帐&nbsp;&nbsp;号：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>帐&nbsp;&nbsp;号：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>邮政编码：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>邮政编码：</div></td><td>&nbsp;</td></tr></tbody></table><table height='43'cellspacing='0'cellpadding='0'width='94%'align='center'border='0'><tbody><tr><td>&nbsp;</td><td width='25%'><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>年</span><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>月</span><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>日</span></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></body></html>";
    } else {
      str2 = Tool.CLOBToString(localCLOB);
    }
    return str2;
  }
  
  public long addBargain(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    long l = -1L;
    try
    {
      str = "select sp_v_bargain.nextval from dual";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        l = localResultSet.getLong(1);
      }
      str = "insert into v_bargain(tradepartition, submitid, code,  price, amount, userid, tradedate, section, contractcontent, commodityid, b_bail, b_poundage,s_bail, s_poundage, status,contractid) values(?, ?, ?, ?, ?, ?, ?, ?, empty_clob(),?,?, ?, ?, ?, ?,?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramBargainVO.tradePartition);
      localPreparedStatement.setLong(2, paramBargainVO.submitID);
      localPreparedStatement.setString(3, paramBargainVO.code);
      localPreparedStatement.setDouble(4, paramBargainVO.price);
      localPreparedStatement.setLong(5, paramBargainVO.amount);
      localPreparedStatement.setString(6, paramBargainVO.userID);
      localPreparedStatement.setTimestamp(7, paramBargainVO.tradeDate);
      localPreparedStatement.setInt(8, paramBargainVO.section);
      localPreparedStatement.setLong(9, paramBargainVO.commodityID);
      localPreparedStatement.setDouble(10, paramBargainVO.b_bail);
      localPreparedStatement.setDouble(11, paramBargainVO.b_poundage);
      localPreparedStatement.setDouble(12, paramBargainVO.s_bail);
      localPreparedStatement.setDouble(13, paramBargainVO.s_poundage);
      localPreparedStatement.setInt(14, paramBargainVO.status);
      localPreparedStatement.setLong(15, l);
      localPreparedStatement.executeUpdate();
      closeStatement(null, localPreparedStatement, null);
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return l;
  }
  
  public void modBargainContent(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    try
    {
      str = "select contractContent from v_bargain where contractID=" + paramBargainVO.contractID + " for update";
      CLOB localCLOB = null;
      Statement localStatement = paramConnection.createStatement();
      OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
      while (localOracleResultSet.next())
      {
        localCLOB = localOracleResultSet.getCLOB(1);
        localCLOB.putString(1L, paramBargainVO.contractContent);
      }
      localOracleResultSet.close();
      localOracleResultSet = null;
      localStatement.close();
      localStatement = null;
      str = "update v_bargain set contractContent=? where contractID=" + paramBargainVO.contractID;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setClob(1, localCLOB);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
  }
  
  public void delBargain(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "delete from v_bargain " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public BargainVO[] getBargainList(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO[] arrayOfBargainVO = null;
    BargainVO localBargainVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select tradepartition, submitid, code, contractid, price, amount, userid, tradedate, section, contractcontent, commodityid, b_bail,b_poundage,s_bail,s_poundage, status from v_bargain " + paramString;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradePartition = localResultSet.getInt(1);
        localBargainVO.submitID = localResultSet.getLong(2);
        localBargainVO.code = localResultSet.getString(3);
        localBargainVO.contractID = localResultSet.getLong(4);
        localBargainVO.price = localResultSet.getDouble(5);
        localBargainVO.amount = localResultSet.getLong(6);
        localBargainVO.userID = localResultSet.getString(7);
        localBargainVO.tradeDate = localResultSet.getTimestamp(8);
        localBargainVO.section = localResultSet.getInt(9);
        localBargainVO.commodityID = localResultSet.getLong(11);
        localBargainVO.b_bail = localResultSet.getDouble(12);
        localBargainVO.b_poundage = localResultSet.getDouble(13);
        localBargainVO.s_bail = localResultSet.getDouble(14);
        localBargainVO.s_poundage = localResultSet.getDouble(15);
        localBargainVO.status = localResultSet.getInt(16);
        localVector.add(localBargainVO);
      }
      arrayOfBargainVO = new BargainVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfBargainVO[i] = ((BargainVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfBargainVO;
  }
  
  public BargainVO[] getBargainList(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO[] arrayOfBargainVO = null;
    BargainVO localBargainVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select tradepartition, submitid, code, contractid, price, amount, userid, tradedate, section, contractcontent, commodityid, b_bail, b_poundage, s_bail, s_poundage, status from v_bargain " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradePartition = localResultSet.getInt(1);
        localBargainVO.submitID = localResultSet.getLong(2);
        localBargainVO.code = localResultSet.getString(3);
        localBargainVO.contractID = localResultSet.getLong(4);
        localBargainVO.price = localResultSet.getDouble(5);
        localBargainVO.amount = localResultSet.getLong(6);
        localBargainVO.userID = localResultSet.getString(7);
        localBargainVO.tradeDate = localResultSet.getTimestamp(8);
        localBargainVO.section = localResultSet.getInt(9);
        localBargainVO.commodityID = localResultSet.getLong(11);
        localBargainVO.b_bail = localResultSet.getDouble(12);
        localBargainVO.b_poundage = localResultSet.getDouble(13);
        localBargainVO.s_bail = localResultSet.getDouble(14);
        localBargainVO.s_poundage = localResultSet.getDouble(15);
        localBargainVO.status = localResultSet.getInt(16);
        localVector.add(localBargainVO);
      }
      localPreparedStatement.close();
      localPreparedStatement = null;
      arrayOfBargainVO = new BargainVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++)
      {
        arrayOfBargainVO[i] = ((BargainVO)localVector.get(i));
        Statement localStatement = null;
        CLOB localCLOB = null;
        str = "select contractContent from v_bargain where contractID=" + arrayOfBargainVO[i].contractID;
        localStatement = paramConnection.createStatement();
        OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
        while (localOracleResultSet.next()) {
          localCLOB = localOracleResultSet.getCLOB(1);
        }
        localOracleResultSet.close();
        localOracleResultSet = null;
        localStatement.close();
        localStatement = null;
        arrayOfBargainVO[i].contractContent = Tool.CLOBToString(localCLOB);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfBargainVO;
  }
  
  public BargainVO[] getCurBargainList(int paramInt1, int paramInt2, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO[] arrayOfBargainVO = null;
    BargainVO localBargainVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select t.submitID v1,t.code v2,c.commodityid v3,t.price v4,t.vaidAmount v5,t.userID v6,t.section v7,u.feecut v8,t.lastTime v9,e.str17 v10 from v_curCommodity c,v_tradeQuotation t,v_tradeUser u,v_commExt e where e.commID(+)=c.commodityid and c.section=" + paramInt2 + " and c.tradePartition=" + paramInt1 + " " + "and t.vaidAmount>0 and t.tradePartition=" + paramInt1 + " and c.code=t.code and t.userid=u.usercode";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradePartition = paramInt1;
        localBargainVO.submitID = localResultSet.getLong(1);
        localBargainVO.code = localResultSet.getString(2);
        localBargainVO.price = localResultSet.getDouble(4);
        localBargainVO.amount = localResultSet.getLong(5);
        localBargainVO.userID = localResultSet.getString(6);
        localBargainVO.tradeDate = localResultSet.getTimestamp(9);
        localBargainVO.section = paramInt2;
        localBargainVO.commodityID = localResultSet.getLong(3);
        localBargainVO.status = 0;
        localVector.add(localBargainVO);
      }
      arrayOfBargainVO = new BargainVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfBargainVO[i] = ((BargainVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfBargainVO;
  }
  
  public BargainVO getCurBargain(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO localBargainVO = null;
    String str = null;
    try
    {
      localConnection = getJDBCConnection();
      str = "select tradepartition, submitid, code, contractid, price, amount, userid, tradedate, section, contractcontent, commodityid, b_bail, b_poundage, s_bail, s_poundage, status from v_bargain where contractid=" + paramLong;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradePartition = localResultSet.getInt(1);
        localBargainVO.submitID = localResultSet.getLong(2);
        localBargainVO.code = localResultSet.getString(3);
        localBargainVO.price = localResultSet.getDouble(5);
        localBargainVO.amount = localResultSet.getLong(6);
        localBargainVO.userID = localResultSet.getString(7);
        localBargainVO.tradeDate = localResultSet.getTimestamp(8);
        localBargainVO.section = localResultSet.getInt(9);
        localBargainVO.commodityID = localResultSet.getLong(11);
        localBargainVO.b_bail = localResultSet.getDouble(12);
        localBargainVO.b_poundage = localResultSet.getDouble(13);
        localBargainVO.s_bail = localResultSet.getDouble(14);
        localBargainVO.s_poundage = localResultSet.getDouble(15);
        localBargainVO.status = localResultSet.getInt(16);
        localBargainVO.contractID = paramLong;
      }
      Statement localStatement = null;
      CLOB localCLOB = null;
      str = "select contractContent from v_bargain where contractID=" + paramLong;
      localStatement = localConnection.createStatement();
      OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
      while (localOracleResultSet.next()) {
        localCLOB = localOracleResultSet.getCLOB(1);
      }
      localOracleResultSet.close();
      localOracleResultSet = null;
      localStatement.close();
      localStatement = null;
      localBargainVO.contractContent = Tool.CLOBToString(localCLOB);
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localBargainVO;
  }
  
  public BargainVO getHisBargain(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO localBargainVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select tradedate, tradepartition, submitid, code, commodityid, contractid, price, amount, userid, section, b_bail, b_lastbail, s_bail, s_lastbail,lastamount, b_poundage, s_poundage, status, patchstatus, actualamount, fellbackamount, result, note from v_hisbargain where contractid=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradeDate = localResultSet.getTimestamp(1);
        localBargainVO.tradePartition = localResultSet.getInt(2);
        localBargainVO.submitID = localResultSet.getLong(3);
        localBargainVO.code = localResultSet.getString(4);
        localBargainVO.commodityID = localResultSet.getLong(5);
        localBargainVO.contractID = localResultSet.getLong(6);
        localBargainVO.price = localResultSet.getDouble(7);
        localBargainVO.amount = localResultSet.getLong(8);
        localBargainVO.userID = localResultSet.getString(9);
        localBargainVO.section = localResultSet.getInt(10);
        localBargainVO.b_bail = localResultSet.getDouble(11);
        localBargainVO.b_lastBail = localResultSet.getDouble(12);
        localBargainVO.s_bail = localResultSet.getDouble(13);
        localBargainVO.s_lastBail = localResultSet.getDouble(14);
        localBargainVO.lastAmount = localResultSet.getLong(15);
        localBargainVO.b_poundage = localResultSet.getDouble(16);
        localBargainVO.s_poundage = localResultSet.getDouble(17);
        localBargainVO.status = localResultSet.getInt(18);
        localBargainVO.patchStatus = localResultSet.getInt(19);
        localBargainVO.actualAmount = localResultSet.getDouble(20);
        localBargainVO.fellBackAmount = localResultSet.getDouble(21);
        localBargainVO.result = localResultSet.getInt(22);
        localBargainVO.note = localResultSet.getString(23);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localBargainVO;
  }
  
  public BargainVO[] getHisBargainList(int paramInt)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO[] arrayOfBargainVO = null;
    BargainVO localBargainVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select tradepartition, submitid, code, contractid, price, amount, userid, tradedate, section, contractcontent, commodityid, b_bail, b_poundage, s_bail, s_poundage, status,patchStatus from v_hisbargain where tradepartition=" + paramInt;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradePartition = localResultSet.getInt(1);
        localBargainVO.submitID = localResultSet.getLong(2);
        localBargainVO.code = localResultSet.getString(3);
        localBargainVO.contractID = localResultSet.getLong(4);
        localBargainVO.price = localResultSet.getDouble(5);
        localBargainVO.amount = localResultSet.getLong(6);
        localBargainVO.userID = localResultSet.getString(7);
        localBargainVO.tradeDate = localResultSet.getTimestamp(8);
        localBargainVO.section = localResultSet.getInt(9);
        localBargainVO.commodityID = localResultSet.getLong(11);
        localBargainVO.b_bail = localResultSet.getDouble(12);
        localBargainVO.b_poundage = localResultSet.getDouble(13);
        localBargainVO.s_bail = localResultSet.getDouble(14);
        localBargainVO.s_poundage = localResultSet.getDouble(15);
        localBargainVO.status = localResultSet.getInt(16);
        localBargainVO.patchStatus = localResultSet.getInt(17);
        localVector.add(localBargainVO);
      }
      arrayOfBargainVO = new BargainVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfBargainVO[i] = ((BargainVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfBargainVO;
  }
  
  public FlowControlVO[] getFlowControlList(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    FlowControlVO[] arrayOfFlowControlVO = null;
    FlowControlVO localFlowControlVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select tradepartition, unitid, unittype, startmode, starttime, durativetime from v_flowcontrol " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localFlowControlVO = new FlowControlVO();
        localFlowControlVO.tradePartition = localResultSet.getInt(1);
        localFlowControlVO.unitID = localResultSet.getInt(2);
        localFlowControlVO.unitType = localResultSet.getInt(3);
        localFlowControlVO.startMode = localResultSet.getInt(4);
        localFlowControlVO.startTime = localResultSet.getString(5);
        localFlowControlVO.durativeTime = localResultSet.getLong(6);
        localVector.add(localFlowControlVO);
      }
      arrayOfFlowControlVO = new FlowControlVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfFlowControlVO[i] = ((FlowControlVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfFlowControlVO;
  }
  
  public CommodityVO[] getCurCommodityList(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO[] arrayOfCommodityVO = null;
    CommodityVO localCommodityVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select tradepartition, code, commodityid, section, lpflag, bargainflag, modifytime from v_curcommodity " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.tradePartition = localResultSet.getInt(1);
        localCommodityVO.code = localResultSet.getString(2);
        localCommodityVO.commodityid = localResultSet.getLong(3);
        localCommodityVO.section = localResultSet.getInt(4);
        localCommodityVO.lpFlag = localResultSet.getInt(5);
        localCommodityVO.bargainFlag = localResultSet.getInt(6);
        localCommodityVO.modifytime = localResultSet.getTimestamp(7);
        localVector.add(localCommodityVO);
      }
      arrayOfCommodityVO = new CommodityVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCommodityVO[i] = ((CommodityVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfCommodityVO;
  }
  
  public CommodityVO[] getCurCommodityList(int paramInt)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO[] arrayOfCommodityVO = null;
    CommodityVO localCommodityVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select c.code,t.price,t.userID,t.VAIDAMOUNT,t.lastTime,d.tradeUnit,d.b_security,d.b_fee,d.s_security,d.s_fee,c.section,e.str1,e.str2,e.str3,e.str4,d.amount,d.beginPrice,d.stepPrice,e.str5,e.str11,e.str12,e.str16,e.str17,c.bargainFlag,e.str7,d.id,t.submitID,d.trademode,d.alertprice,d.userid,e.str6,e.str8,e.str9,e.str10,e.str13,e.str14,e.str15,e.str18,e.str19,e.str20,d.id from v_curCommodity c,v_tradeQuotation t,v_commodity d,v_commExt e where e.commID(+)=d.ID and d.id=c.commodityID and t.tradePartition(+)=" + paramInt + " and c.tradePartition=" + paramInt + " and c.code=t.code(+)";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.code = localResultSet.getString(1);
        localCommodityVO.lastPrice = localResultSet.getDouble(2);
        localCommodityVO.lastUserID = localResultSet.getString(3);
        localCommodityVO.amount = localResultSet.getLong(4);
        localCommodityVO.lastTime = localResultSet.getTimestamp(5);
        localCommodityVO.tradeUnit = localResultSet.getDouble(6);
        localCommodityVO.b_security = localResultSet.getDouble(7);
        localCommodityVO.b_fee = localResultSet.getDouble(8);
        localCommodityVO.s_security = localResultSet.getDouble(9);
        localCommodityVO.s_fee = localResultSet.getDouble(10);
        localCommodityVO.section = localResultSet.getInt(11);
        localCommodityVO.str1 = localResultSet.getString(12);
        localCommodityVO.str2 = localResultSet.getString(13);
        localCommodityVO.str3 = localResultSet.getString(14);
        localCommodityVO.str4 = localResultSet.getString(15);
        localCommodityVO.totalAmount = localResultSet.getLong(16);
        localCommodityVO.beginPrice = localResultSet.getDouble(17);
        localCommodityVO.stepPrice = localResultSet.getDouble(18);
        localCommodityVO.str5 = localResultSet.getString(19);
        localCommodityVO.str11 = localResultSet.getString(20);
        localCommodityVO.str12 = localResultSet.getString(21);
        localCommodityVO.str16 = localResultSet.getString(22);
        localCommodityVO.str17 = localResultSet.getString(23);
        localCommodityVO.bargainFlag = localResultSet.getInt(24);
        localCommodityVO.str7 = localResultSet.getString(25);
        localCommodityVO.commodityid = localResultSet.getLong(26);
        localCommodityVO.lastSubmitID = localResultSet.getLong(27);
        localCommodityVO.tradeMode = localResultSet.getInt(28);
        localCommodityVO.alertPrice = localResultSet.getDouble(29);
        localCommodityVO.tradePartition = paramInt;
        localCommodityVO.userID = localResultSet.getString(30);
        localCommodityVO.str6 = localResultSet.getString(31);
        localCommodityVO.str8 = localResultSet.getString(32);
        localCommodityVO.str9 = localResultSet.getString(33);
        localCommodityVO.str10 = localResultSet.getString(34);
        localCommodityVO.str13 = localResultSet.getString(35);
        localCommodityVO.str14 = localResultSet.getString(36);
        localCommodityVO.str15 = localResultSet.getString(37);
        localCommodityVO.str18 = localResultSet.getString(38);
        localCommodityVO.str19 = localResultSet.getString(39);
        localCommodityVO.str20 = localResultSet.getString(40);
        localCommodityVO.id = localResultSet.getLong(41);
        localVector.add(localCommodityVO);
      }
      arrayOfCommodityVO = new CommodityVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCommodityVO[i] = ((CommodityVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCommodityVO;
  }
  
  public CommodityVO getCommodity(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO localCommodityVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select d.code, d.status,d.beginprice, d.stepprice, d.amount, d.tradeunit, d.b_security, d.b_fee,d.s_security, d.s_fee,d.userid,d.trademode,d.alertprice,e.str1,e.str2,e.str3,e.str4,e.str5,e.str6,str7,e.str8,e.str9,e.str10,e.str11,e.str12,e.str13,e.str14,e.str15,e.str16,e.str17,e.str18,e.str19,e.str20 from v_commodity d,v_commExt e where id=? and e.commID(+)=d.ID";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.code = localResultSet.getString(1);
        if (localResultSet.getInt(2) == 1) {
          localCommodityVO.bargainFlag = 1;
        } else if (localResultSet.getInt(2) == 2) {
          localCommodityVO.bargainFlag = 0;
        }
        localCommodityVO.beginPrice = localResultSet.getDouble(3);
        localCommodityVO.stepPrice = localResultSet.getDouble(4);
        localCommodityVO.totalAmount = localResultSet.getLong(5);
        localCommodityVO.tradeUnit = localResultSet.getDouble(6);
        localCommodityVO.b_security = localResultSet.getDouble(7);
        localCommodityVO.b_fee = localResultSet.getDouble(8);
        localCommodityVO.s_security = localResultSet.getDouble(9);
        localCommodityVO.s_fee = localResultSet.getDouble(10);
        localCommodityVO.userID = localResultSet.getString(11);
        localCommodityVO.tradeMode = localResultSet.getInt(12);
        localCommodityVO.alertPrice = localResultSet.getDouble(13);
        localCommodityVO.str1 = localResultSet.getString(14);
        localCommodityVO.str2 = localResultSet.getString(15);
        localCommodityVO.str3 = localResultSet.getString(16);
        localCommodityVO.str4 = localResultSet.getString(17);
        localCommodityVO.str5 = localResultSet.getString(18);
        localCommodityVO.str6 = localResultSet.getString(19);
        localCommodityVO.str7 = localResultSet.getString(20);
        localCommodityVO.str8 = localResultSet.getString(21);
        localCommodityVO.str9 = localResultSet.getString(22);
        localCommodityVO.str10 = localResultSet.getString(23);
        localCommodityVO.str11 = localResultSet.getString(24);
        localCommodityVO.str12 = localResultSet.getString(25);
        localCommodityVO.str13 = localResultSet.getString(26);
        localCommodityVO.str14 = localResultSet.getString(27);
        localCommodityVO.str15 = localResultSet.getString(28);
        localCommodityVO.str16 = localResultSet.getString(29);
        localCommodityVO.str17 = localResultSet.getString(30);
        localCommodityVO.str18 = localResultSet.getString(31);
        localCommodityVO.str19 = localResultSet.getString(32);
        localCommodityVO.str20 = localResultSet.getString(33);
        localCommodityVO.commodityid = paramLong;
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localCommodityVO;
  }
  
  public CommodityVO getCommodity(long paramLong, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO localCommodityVO = null;
    String str = null;
    try
    {
      str = "select d.code, d.status,d.beginprice, d.stepprice, d.amount, d.tradeunit, d.b_security, d.b_fee,d.s_security, d.s_fee,d.userid,d.trademode,d.alertprice,e.str1,e.str2,e.str3,e.str4,e.str5,e.str6,e.str7,e.str8,e.str9,e.str10,e.str11,e.str12,e.str13,e.str14,e.str15,e.str16,e.str17,e.str18,e.str19,e.str20 from v_commodity d,v_commExt e where id=? and e.commID(+)=d.ID";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.code = localResultSet.getString(1);
        if (localResultSet.getInt(2) == 1) {
          localCommodityVO.bargainFlag = 1;
        } else if (localResultSet.getInt(2) == 2) {
          localCommodityVO.bargainFlag = 0;
        }
        localCommodityVO.beginPrice = localResultSet.getDouble(3);
        localCommodityVO.stepPrice = localResultSet.getDouble(4);
        localCommodityVO.totalAmount = localResultSet.getLong(5);
        localCommodityVO.tradeUnit = localResultSet.getDouble(6);
        localCommodityVO.b_security = localResultSet.getDouble(7);
        localCommodityVO.b_fee = localResultSet.getDouble(8);
        localCommodityVO.s_security = localResultSet.getDouble(9);
        localCommodityVO.s_fee = localResultSet.getDouble(10);
        localCommodityVO.userID = localResultSet.getString(11);
        localCommodityVO.tradeMode = localResultSet.getInt(12);
        localCommodityVO.alertPrice = localResultSet.getDouble(13);
        localCommodityVO.str1 = localResultSet.getString(14);
        localCommodityVO.str2 = localResultSet.getString(15);
        localCommodityVO.str3 = localResultSet.getString(16);
        localCommodityVO.str4 = localResultSet.getString(17);
        localCommodityVO.str5 = localResultSet.getString(18);
        localCommodityVO.str6 = localResultSet.getString(19);
        localCommodityVO.str7 = localResultSet.getString(20);
        localCommodityVO.str8 = localResultSet.getString(21);
        localCommodityVO.str9 = localResultSet.getString(22);
        localCommodityVO.str10 = localResultSet.getString(23);
        localCommodityVO.str11 = localResultSet.getString(24);
        localCommodityVO.str12 = localResultSet.getString(25);
        localCommodityVO.str13 = localResultSet.getString(26);
        localCommodityVO.str14 = localResultSet.getString(27);
        localCommodityVO.str15 = localResultSet.getString(28);
        localCommodityVO.str16 = localResultSet.getString(29);
        localCommodityVO.str17 = localResultSet.getString(30);
        localCommodityVO.str18 = localResultSet.getString(31);
        localCommodityVO.str19 = localResultSet.getString(32);
        localCommodityVO.str20 = localResultSet.getString(33);
        localCommodityVO.commodityid = paramLong;
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localCommodityVO;
  }
  
  public CommodityVO[] getCurCommodityListBySection(int paramInt1, int paramInt2)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO[] arrayOfCommodityVO = null;
    CommodityVO localCommodityVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select c.code,e.str1,e.str2,d.amount,e.str3,e.str4,d.beginprice,d.stepprice,t.price,t.countdownStart,t.countdownTime,sysdate,t.id,t.vaidAmount,e.str5 from v_tradeQuotation t,v_curCommodity c,v_commodity d,v_commExt e where t.price(+)>=0 and c.bargainFlag=0 and c.section=" + paramInt2 + " and d.id=e.commID(+) and " + "d.ID=c.commodityID and t.code(+)=c.code and " + "c.tradepartition=" + paramInt1 + " and t.tradepartition(+)=" + paramInt1 + " order by c.code";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.code = localResultSet.getString(1);
        localCommodityVO.str1 = localResultSet.getString(2);
        localCommodityVO.str2 = localResultSet.getString(3);
        localCommodityVO.amount = localResultSet.getLong(4);
        localCommodityVO.str3 = localResultSet.getString(5);
        localCommodityVO.str4 = localResultSet.getString(6);
        localCommodityVO.beginPrice = localResultSet.getDouble(7);
        localCommodityVO.stepPrice = localResultSet.getDouble(8);
        if (localResultSet.getLong(14) > 0L) {
          localCommodityVO.lastPrice = localResultSet.getDouble(9);
        }
        long l1 = 0L;
        if (localResultSet.getTimestamp(10) != null) {
          l1 = localResultSet.getTimestamp(10).getTime();
        }
        int j = localResultSet.getInt(11);
        long l2 = localResultSet.getTimestamp(12).getTime();
        if (j > 0)
        {
          int k = (int)(l1 + j * 1000 - l2) / 1000;
          if (k >= 0) {
            localCommodityVO.countDownTime = k;
          } else {
            localCommodityVO.countDownTime = 0;
          }
        }
        localCommodityVO.hqID = localResultSet.getLong(13);
        localCommodityVO.str5 = localResultSet.getString(15);
        localVector.add(localCommodityVO);
      }
      arrayOfCommodityVO = new CommodityVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCommodityVO[i] = ((CommodityVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCommodityVO;
  }
  
  public CommodityVO[] getCurCommodityListBySectionHQ(int paramInt1, int paramInt2)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO[] arrayOfCommodityVO = null;
    CommodityVO localCommodityVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select c.code,e.str1,e.str2,d.amount,e.str3,e.str4,d.beginprice,d.stepprice,t.price,t.countdownStart,t.countdownTime,sysdate,t.id,t.vaidAmount,e.str5 from v_tradeQuotation t,v_curCommodity c,v_commodity d,v_commExt e where t.price>=0 and c.bargainFlag=0 and c.section=" + paramInt2 + " and d.id=e.commID(+) " + "and d.ID=c.commodityID and t.code=c.code and c.tradepartition=" + paramInt1 + " " + "and t.tradepartition=" + paramInt1 + " order by c.code";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.code = localResultSet.getString(1);
        localCommodityVO.str1 = localResultSet.getString(2);
        localCommodityVO.str2 = localResultSet.getString(3);
        localCommodityVO.amount = localResultSet.getLong(4);
        localCommodityVO.str3 = localResultSet.getString(5);
        localCommodityVO.str4 = localResultSet.getString(6);
        localCommodityVO.beginPrice = localResultSet.getDouble(7);
        localCommodityVO.stepPrice = localResultSet.getDouble(8);
        if (localResultSet.getLong(14) > 0L) {
          localCommodityVO.lastPrice = localResultSet.getDouble(9);
        }
        long l1 = 0L;
        if (localResultSet.getTimestamp(10) != null) {
          l1 = localResultSet.getTimestamp(10).getTime();
        }
        int j = localResultSet.getInt(11);
        long l2 = localResultSet.getTimestamp(12).getTime();
        if (j > 0)
        {
          int k = (int)(l1 + j * 1000 - l2) / 1000;
          if (k >= 0) {
            localCommodityVO.countDownTime = k;
          } else {
            localCommodityVO.countDownTime = 0;
          }
        }
        localCommodityVO.hqID = localResultSet.getLong(13);
        localCommodityVO.str5 = localResultSet.getString(15);
        localVector.add(localCommodityVO);
      }
      arrayOfCommodityVO = new CommodityVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCommodityVO[i] = ((CommodityVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCommodityVO;
  }
  
  public SysPropertyVO getSysProperty(int paramInt, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    SysPropertyVO localSysPropertyVO = null;
    String str = null;
    try
    {
      str = "select durativetime, spacetime, countdownstart, countdowntime, optmode from v_sysproperty where tradepartition=" + paramInt;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localSysPropertyVO = new SysPropertyVO();
        localSysPropertyVO.durativeTime = localResultSet.getLong(1);
        localSysPropertyVO.spaceTime = localResultSet.getLong(2);
        localSysPropertyVO.countdownStart = localResultSet.getString(3);
        localSysPropertyVO.countdownTime = localResultSet.getString(4);
        localSysPropertyVO.optMode = localResultSet.getInt(5);
        localSysPropertyVO.tradePartition = paramInt;
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localSysPropertyVO;
  }
  
  public SysPropertyVO getSysProperty(int paramInt)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    SysPropertyVO localSysPropertyVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select durativetime, spacetime, countdownstart, countdowntime, optmode from v_sysproperty where tradepartition=" + paramInt;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localSysPropertyVO = new SysPropertyVO();
        localSysPropertyVO.durativeTime = localResultSet.getLong(1);
        localSysPropertyVO.spaceTime = localResultSet.getLong(2);
        localSysPropertyVO.countdownStart = localResultSet.getString(3);
        localSysPropertyVO.countdownTime = localResultSet.getString(4);
        localSysPropertyVO.optMode = localResultSet.getInt(5);
        localSysPropertyVO.tradePartition = paramInt;
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localSysPropertyVO;
  }
  
  public void modifySysCurStatus(SysCurStatusVO paramSysCurStatusVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_syscurstatus set status = ?,section = ?,modifytime = sysdate,starttime = ?,endtime = ? where tradepartition = ?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramSysCurStatusVO.status);
      localPreparedStatement.setInt(2, paramSysCurStatusVO.section);
      localPreparedStatement.setTimestamp(3, paramSysCurStatusVO.starttime);
      localPreparedStatement.setTimestamp(4, paramSysCurStatusVO.endtime);
      localPreparedStatement.setInt(5, paramSysCurStatusVO.tradePartition);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void modifySysCurStatus(SysCurStatusVO paramSysCurStatusVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "update v_syscurstatus set status = ?,section = ?,modifytime = sysdate,starttime = ?,endtime = ? where tradepartition = ?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramSysCurStatusVO.status);
      localPreparedStatement.setInt(2, paramSysCurStatusVO.section);
      localPreparedStatement.setTimestamp(3, paramSysCurStatusVO.starttime);
      localPreparedStatement.setTimestamp(4, paramSysCurStatusVO.endtime);
      localPreparedStatement.setInt(5, paramSysCurStatusVO.tradePartition);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public SysCurStatusVO getSysCurStatus(int paramInt)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    SysCurStatusVO localSysCurStatusVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select status, section, modifytime, starttime, endtime from v_syscurstatus where tradepartition=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localSysCurStatusVO = new SysCurStatusVO();
        localSysCurStatusVO.tradePartition = paramInt;
        localSysCurStatusVO.status = localResultSet.getInt(1);
        localSysCurStatusVO.section = localResultSet.getInt(2);
        localSysCurStatusVO.modifytime = localResultSet.getTimestamp(3);
        localSysCurStatusVO.starttime = localResultSet.getTimestamp(4);
        localSysCurStatusVO.endtime = localResultSet.getTimestamp(5);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localSysCurStatusVO;
  }
  
  public SysPartitionVO[] getSysPartitionList(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    SysPartitionVO[] arrayOfSysPartitionVO = null;
    SysPartitionVO localSysPartitionVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select partitionid, engineclass, quotationclass, submitactionclass, validflag, description from v_syspartition " + paramString;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localSysPartitionVO = new SysPartitionVO();
        localSysPartitionVO.partitionID = localResultSet.getInt(1);
        localSysPartitionVO.engineClass = localResultSet.getString(2);
        localSysPartitionVO.quotationClass = localResultSet.getString(3);
        localSysPartitionVO.submitActionClass = localResultSet.getString(4);
        localSysPartitionVO.validFlag = localResultSet.getInt(5);
        localSysPartitionVO.description = localResultSet.getString(6);
        localVector.add(localSysPartitionVO);
      }
      arrayOfSysPartitionVO = new SysPartitionVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfSysPartitionVO[i] = ((SysPartitionVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfSysPartitionVO;
  }
  
  public void addHisCommodity(Timestamp paramTimestamp, CommodityVO paramCommodityVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_hiscommodity(tradedate, tradepartition, code, commodityid, section, lpflag, bargainflag, modifytime) values(?, ?, ?, ?, ?, ?, ?, ?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setTimestamp(1, paramTimestamp);
      localPreparedStatement.setInt(2, paramCommodityVO.tradePartition);
      localPreparedStatement.setString(3, paramCommodityVO.code);
      localPreparedStatement.setLong(4, paramCommodityVO.commodityid);
      localPreparedStatement.setInt(5, paramCommodityVO.section);
      localPreparedStatement.setInt(6, paramCommodityVO.lpFlag);
      localPreparedStatement.setInt(7, paramCommodityVO.bargainFlag);
      localPreparedStatement.setTimestamp(8, paramCommodityVO.modifytime);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void addHisBargain(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_hisbargain(tradedate, tradepartition, submitid, code, commodityid, contractid, price, amount, userid, section, b_bail, b_lastbail,s_bail, s_lastbail, lastamount, b_poundage,s_poundage, status, contractcontent,  patchstatus, actualamount, fellbackamount, result) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, empty_clob(), ?, ?, ?, ?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setTimestamp(1, paramBargainVO.tradeDate);
      localPreparedStatement.setInt(2, paramBargainVO.tradePartition);
      localPreparedStatement.setLong(3, paramBargainVO.submitID);
      localPreparedStatement.setString(4, paramBargainVO.code);
      localPreparedStatement.setLong(5, paramBargainVO.commodityID);
      localPreparedStatement.setLong(6, paramBargainVO.contractID);
      localPreparedStatement.setDouble(7, paramBargainVO.price);
      localPreparedStatement.setLong(8, paramBargainVO.amount);
      localPreparedStatement.setString(9, paramBargainVO.userID);
      localPreparedStatement.setInt(10, paramBargainVO.section);
      localPreparedStatement.setDouble(11, paramBargainVO.b_bail);
      localPreparedStatement.setDouble(12, paramBargainVO.b_bail);
      localPreparedStatement.setDouble(13, paramBargainVO.s_bail);
      localPreparedStatement.setDouble(14, paramBargainVO.s_bail);
      localPreparedStatement.setLong(15, paramBargainVO.amount);
      localPreparedStatement.setDouble(16, paramBargainVO.b_poundage);
      localPreparedStatement.setDouble(17, paramBargainVO.s_poundage);
      localPreparedStatement.setInt(18, paramBargainVO.status);
      localPreparedStatement.setInt(19, 0);
      localPreparedStatement.setDouble(20, 0.0D);
      localPreparedStatement.setDouble(21, 0.0D);
      localPreparedStatement.setInt(22, -1);
      localPreparedStatement.executeUpdate();
      closeStatement(null, localPreparedStatement, null);
      Statement localStatement = null;
      CLOB localCLOB = null;
      str = "select contractContent from v_hisbargain where contractID=" + paramBargainVO.contractID + " for update";
      localStatement = paramConnection.createStatement();
      OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str);
      while (localOracleResultSet.next())
      {
        localCLOB = localOracleResultSet.getCLOB(1);
        localCLOB.putString(1L, paramBargainVO.contractContent);
      }
      localOracleResultSet.close();
      localOracleResultSet = null;
      localStatement.close();
      localStatement = null;
      str = "update v_hisbargain set contractContent=? where contractID=" + paramBargainVO.contractID;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setClob(1, localCLOB);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void modifyHisBargain(BargainVO paramBargainVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_hisbargain set b_lastbail = ?,s_lastbail = ?,lastamount = ?,status = ?,patchstatus = ?,actualamount = ?,fellbackamount = ?,result = ?,note = ? where contractid = ?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setDouble(1, paramBargainVO.b_lastBail);
      localPreparedStatement.setDouble(2, paramBargainVO.s_lastBail);
      localPreparedStatement.setLong(3, paramBargainVO.lastAmount);
      localPreparedStatement.setInt(4, paramBargainVO.status);
      localPreparedStatement.setInt(5, paramBargainVO.patchStatus);
      localPreparedStatement.setDouble(6, paramBargainVO.actualAmount);
      localPreparedStatement.setDouble(7, paramBargainVO.fellBackAmount);
      localPreparedStatement.setInt(8, paramBargainVO.result);
      localPreparedStatement.setString(9, paramBargainVO.note);
      localPreparedStatement.setLong(10, paramBargainVO.contractID);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public BargainVO getHisBargain(long paramLong, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BargainVO localBargainVO = null;
    String str = null;
    try
    {
      str = "select tradedate, tradepartition, submitid, code, commodityid, contractid, price, amount, userid, section, b_bail, b_lastbail,s_bail, s_lastbail, lastamount, b_poundage, s_poundage,status, patchstatus, actualamount, fellbackamount, result, note from v_hisbargain where contractid=?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBargainVO = new BargainVO();
        localBargainVO.tradeDate = localResultSet.getTimestamp(1);
        localBargainVO.tradePartition = localResultSet.getInt(2);
        localBargainVO.submitID = localResultSet.getLong(3);
        localBargainVO.code = localResultSet.getString(4);
        localBargainVO.commodityID = localResultSet.getLong(5);
        localBargainVO.contractID = localResultSet.getLong(6);
        localBargainVO.price = localResultSet.getDouble(7);
        localBargainVO.amount = localResultSet.getLong(8);
        localBargainVO.userID = localResultSet.getString(9);
        localBargainVO.section = localResultSet.getInt(10);
        localBargainVO.b_bail = localResultSet.getDouble(11);
        localBargainVO.b_lastBail = localResultSet.getDouble(12);
        localBargainVO.s_bail = localResultSet.getDouble(13);
        localBargainVO.s_lastBail = localResultSet.getDouble(14);
        localBargainVO.lastAmount = localResultSet.getLong(15);
        localBargainVO.b_poundage = localResultSet.getDouble(16);
        localBargainVO.s_poundage = localResultSet.getDouble(17);
        localBargainVO.status = localResultSet.getInt(18);
        localBargainVO.patchStatus = localResultSet.getInt(19);
        localBargainVO.actualAmount = localResultSet.getDouble(20);
        localBargainVO.fellBackAmount = localResultSet.getDouble(21);
        localBargainVO.result = localResultSet.getInt(22);
        localBargainVO.note = localResultSet.getString(23);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localBargainVO;
  }
  
  public void addHisSubmit(Timestamp paramTimestamp, CurSubmitVO paramCurSubmitVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_hissubmit(tradedate, tradepartition, id,  price, amount, userid, submittime, modifytime, validamount, tradeflag,code,totalamount,traderId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setTimestamp(1, paramTimestamp);
      localPreparedStatement.setInt(2, paramCurSubmitVO.tradePartition);
      localPreparedStatement.setLong(3, paramCurSubmitVO.iD);
      localPreparedStatement.setDouble(4, paramCurSubmitVO.price);
      localPreparedStatement.setLong(5, paramCurSubmitVO.amount);
      localPreparedStatement.setString(6, paramCurSubmitVO.userID);
      localPreparedStatement.setTimestamp(7, paramCurSubmitVO.submitTime);
      localPreparedStatement.setTimestamp(8, paramCurSubmitVO.modifytime);
      localPreparedStatement.setLong(9, paramCurSubmitVO.validAmount);
      localPreparedStatement.setInt(10, paramCurSubmitVO.tradeFlag);
      localPreparedStatement.setString(11, paramCurSubmitVO.code);
      localPreparedStatement.setLong(12, paramCurSubmitVO.totalamount);
      localPreparedStatement.setString(13, paramCurSubmitVO.traderID);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void modifyTradeUser(TradeUserVO paramTradeUserVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_tradeuser set tradecount = ?,totalsecurity = ? where usercode = ?";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramTradeUserVO.tradeCount);
      localPreparedStatement.setDouble(2, paramTradeUserVO.totalSecurity);
      localPreparedStatement.setString(3, paramTradeUserVO.userCode);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void releaseTraderFrozencapital(Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_tradeuser set frozencapital=0 where frozencapital>0";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void modifyTradeUser(TradeUserVO paramTradeUserVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "update v_tradeuser set tradecount = ?,totalsecurity = ?where usercode = ?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramTradeUserVO.tradeCount);
      localPreparedStatement.setDouble(2, paramTradeUserVO.totalSecurity);
      localPreparedStatement.setString(3, paramTradeUserVO.userCode);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void delTotalSecurity(Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "update v_tradeUser set totalSecurity=0";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public TradeUserVO getTradeUser(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TradeUserVO localTradeUserVO = null;
    String str = null;
    try
    {
      str = "select feecut,tradecount, totalsecurity,feecutfee,balance,FROZENFUNDS from v_tradeuser v,f_firmfunds f where v.usercode=? and v.usercode=firmId";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localTradeUserVO = new TradeUserVO();
        localTradeUserVO.userCode = paramString;
        localTradeUserVO.tradeCount = localResultSet.getInt(2);
        localTradeUserVO.totalSecurity = localResultSet.getDouble(3);
        localTradeUserVO.feecutfee = localResultSet.getDouble(4);
        localTradeUserVO.feecut = localResultSet.getDouble(1);
        localTradeUserVO.balance = localResultSet.getDouble(5);
        localTradeUserVO.frozenCapital = localResultSet.getDouble(6);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localTradeUserVO;
  }
  
  public String getfirmId(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = null;
    try
    {
      localConnection = getConnection();
      str2 = "select firmId from m_trader where traderId=?";
      localPreparedStatement = localConnection.prepareStatement(str2);
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        str1 = localResultSet.getString(1);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return str1;
  }
  
  public TradeUserVO getTradeUser(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TradeUserVO localTradeUserVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select feecut,tradecount, totalsecurity,feecutfee,balance,FROZENFUNDS from v_tradeuser v,f_firmfunds f where v.usercode=? and v.usercode=firmId";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localTradeUserVO = new TradeUserVO();
        localTradeUserVO.userCode = paramString;
        localTradeUserVO.tradeCount = localResultSet.getInt(2);
        localTradeUserVO.totalSecurity = localResultSet.getDouble(3);
        localTradeUserVO.feecutfee = localResultSet.getDouble(4);
        localTradeUserVO.feecut = localResultSet.getDouble(1);
        localTradeUserVO.balance = localResultSet.getDouble(5);
        localTradeUserVO.frozenCapital = localResultSet.getDouble(6);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localTradeUserVO;
  }
  
  public TradeUserVO getTradeUserForUpdate(String paramString, Connection paramConnection)
    throws SQLException
  {
    CallableStatement localCallableStatement = null;
    ResultSet localResultSet = null;
    TradeUserVO localTradeUserVO = null;
    Object localObject1 = null;
    try
    {
      localTradeUserVO = new TradeUserVO();
      localTradeUserVO.userCode = paramString;
      localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
      localCallableStatement.setString(2, localTradeUserVO.userCode);
      localCallableStatement.setInt(3, 1);
      localCallableStatement.registerOutParameter(1, 8);
      localCallableStatement.executeQuery();
      localTradeUserVO.balance = localCallableStatement.getDouble(1);
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localCallableStatement, null);
    }
    return localTradeUserVO;
  }
  
  public BroadcastVO getBroadcast(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BroadcastVO localBroadcastVO = null;
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select title, author, content, type, sendtime, createtime, updatetime, endtime from v_broadcast where id=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBroadcastVO = new BroadcastVO();
        localBroadcastVO.iD = paramLong;
        localBroadcastVO.title = localResultSet.getString(1);
        localBroadcastVO.author = localResultSet.getString(2);
        localBroadcastVO.content = localResultSet.getString(3);
        localBroadcastVO.type = localResultSet.getInt(4);
        localBroadcastVO.sendtime = localResultSet.getTimestamp(5);
        localBroadcastVO.createtime = localResultSet.getTimestamp(6);
        localBroadcastVO.updatetime = localResultSet.getTimestamp(7);
        localBroadcastVO.endtime = localResultSet.getTimestamp(8);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localBroadcastVO;
  }
  
  public BroadcastVO[] getBroadcastList(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BroadcastVO[] arrayOfBroadcastVO = null;
    BroadcastVO localBroadcastVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select title, author, content, type, sendtime, createtime, updatetime, endtime,id from v_broadcast " + paramString;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localBroadcastVO = new BroadcastVO();
        localBroadcastVO.title = localResultSet.getString(1);
        localBroadcastVO.author = localResultSet.getString(2);
        localBroadcastVO.content = localResultSet.getString(3);
        localBroadcastVO.type = localResultSet.getInt(4);
        localBroadcastVO.sendtime = localResultSet.getTimestamp(5);
        localBroadcastVO.createtime = localResultSet.getTimestamp(6);
        localBroadcastVO.updatetime = localResultSet.getTimestamp(7);
        localBroadcastVO.endtime = localResultSet.getTimestamp(8);
        localBroadcastVO.iD = localResultSet.getLong(9);
        localVector.add(localBroadcastVO);
      }
      arrayOfBroadcastVO = new BroadcastVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfBroadcastVO[i] = ((BroadcastVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfBroadcastVO;
  }
  
  public void addDailymoney(MoneyVO paramMoneyVO, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_dailymoney(id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type,paymentForGoods,note) values(sp_v_dailymoney.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setTimestamp(1, paramMoneyVO.infoDate);
      localPreparedStatement.setString(2, paramMoneyVO.firmID);
      localPreparedStatement.setInt(3, paramMoneyVO.operation);
      localPreparedStatement.setLong(4, paramMoneyVO.contractNo);
      localPreparedStatement.setDouble(5, paramMoneyVO.money);
      localPreparedStatement.setDouble(6, paramMoneyVO.balance);
      localPreparedStatement.setDouble(7, paramMoneyVO.overdraft);
      localPreparedStatement.setDouble(8, paramMoneyVO.frozenCapital);
      localPreparedStatement.setInt(9, 0);
      localPreparedStatement.setDouble(10, paramMoneyVO.paymentForGoods);
      localPreparedStatement.setString(11, paramMoneyVO.note);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void truncateDailymoney(Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "delete from v_dailymoney";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public void selectIntoHismoney(Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      str = "insert into v_hismoney(id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type, note,paymentForGoods) select id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type, note,paymentForGoods from v_dailymoney";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, null);
    }
  }
  
  public MoneyVO[] getMoneyList(String paramString)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MoneyVO[] arrayOfMoneyVO = null;
    MoneyVO localMoneyVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select * from (select id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type,note from v_dailymoney union all select id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type,note from v_hismoney) " + paramString;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localMoneyVO = new MoneyVO();
        localMoneyVO.iD = localResultSet.getLong(1);
        localMoneyVO.infoDate = localResultSet.getTimestamp(2);
        localMoneyVO.firmID = localResultSet.getString(3);
        localMoneyVO.operation = localResultSet.getInt(4);
        localMoneyVO.contractNo = localResultSet.getLong(5);
        localMoneyVO.money = localResultSet.getDouble(6);
        localMoneyVO.balance = localResultSet.getDouble(7);
        localMoneyVO.overdraft = localResultSet.getDouble(8);
        localMoneyVO.frozenCapital = localResultSet.getDouble(9);
        localMoneyVO.type = localResultSet.getInt(10);
        localMoneyVO.note = localResultSet.getString(11);
        localVector.add(localMoneyVO);
      }
      arrayOfMoneyVO = new MoneyVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfMoneyVO[i] = ((MoneyVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfMoneyVO;
  }
  
  public MoneyVO[] getMoneyList(String paramString, Connection paramConnection)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MoneyVO[] arrayOfMoneyVO = null;
    MoneyVO localMoneyVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      str = "select * from (select id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type,note from v_dailymoney union all select id, infodate, firmid, operation, contractno, money, balance, overdraft, frozencapital, type,note from v_hismoney) " + paramString;
      localPreparedStatement = paramConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localMoneyVO = new MoneyVO();
        localMoneyVO.iD = localResultSet.getLong(1);
        localMoneyVO.infoDate = localResultSet.getTimestamp(2);
        localMoneyVO.firmID = localResultSet.getString(3);
        localMoneyVO.operation = localResultSet.getInt(4);
        localMoneyVO.contractNo = localResultSet.getLong(5);
        localMoneyVO.money = localResultSet.getDouble(6);
        localMoneyVO.balance = localResultSet.getDouble(7);
        localMoneyVO.overdraft = localResultSet.getDouble(8);
        localMoneyVO.frozenCapital = localResultSet.getDouble(9);
        localMoneyVO.type = localResultSet.getInt(10);
        localMoneyVO.note = localResultSet.getString(11);
        localVector.add(localMoneyVO);
      }
      arrayOfMoneyVO = new MoneyVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfMoneyVO[i] = ((MoneyVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return arrayOfMoneyVO;
  }
  
  public CommodityTypeVO[] getCommodityType(QueryValue paramQueryValue)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityTypeVO[] arrayOfCommodityTypeVO = null;
    CommodityTypeVO localCommodityTypeVO = null;
    int i = paramQueryValue.pageIndex;
    int j = paramQueryValue.pageSize;
    String str1 = paramQueryValue.filter;
    int k = (i - 1) * j + 1;
    int m = i * j;
    Vector localVector = new Vector();
    String str2 = null;
    try
    {
      localConnection = getConnection();
      str2 = "select id,name,reservedchar1,reservedchar2,reservedchar3,reservedchar4,rown from (select id,name,reservedchar1,reservedchar2,reservedchar3,reservedchar4,rownum rown from (select * from v_commoditytype where 1=1 " + str1 + " )) where rown between " + k + " and " + m + " ";
      localPreparedStatement = localConnection.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityTypeVO = new CommodityTypeVO();
        localCommodityTypeVO.id = localResultSet.getInt(1);
        localCommodityTypeVO.name = localResultSet.getString(2);
        localCommodityTypeVO.str1 = localResultSet.getString(3);
        localCommodityTypeVO.str2 = localResultSet.getString(4);
        localCommodityTypeVO.str3 = localResultSet.getString(5);
        localCommodityTypeVO.str4 = localResultSet.getString(6);
        localVector.add(localCommodityTypeVO);
      }
      arrayOfCommodityTypeVO = new CommodityTypeVO[localVector.size()];
      for (int n = 0; n < localVector.size(); n++) {
        arrayOfCommodityTypeVO[n] = ((CommodityTypeVO)localVector.get(n));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCommodityTypeVO;
  }
  
  public int getCommodityTypeCount(QueryValue paramQueryValue)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = "";
    int i = 0;
    try
    {
      localConnection = getConnection();
      str = "select count(*) from v_commoditytype where 1=1 " + paramQueryValue.filter;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
    return i;
  }
  
  public int getCommodityTypePGCount(QueryValue paramQueryValue)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = "";
    int i = 0;
    try
    {
      localConnection = getConnection();
      str = "select count(*) from v_commoditytype where 1=1 " + paramQueryValue.filter;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
    if (i % paramQueryValue.pageSize > 0) {
      return i / paramQueryValue.pageSize + 1;
    }
    return i / paramQueryValue.pageSize;
  }
  
  public void deleteCommodityType(CommodityTypeVO paramCommodityTypeVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "delete from v_commoditytype where id=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramCommodityTypeVO.id);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void addCommodityType(CommodityTypeVO paramCommodityTypeVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "insert into v_commoditytype(id,name,reservedchar1,reservedchar2,reservedchar3,reservedchar4) values(?,?,?,?,?,?)";
      localPreparedStatement = localConnection.prepareStatement("select sp_v_commoditytype.nextval from dual");
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        paramCommodityTypeVO.id = localResultSet.getInt(1);
      }
      localPreparedStatement.close();
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramCommodityTypeVO.id);
      localPreparedStatement.setString(2, paramCommodityTypeVO.name);
      localPreparedStatement.setString(3, paramCommodityTypeVO.str1);
      localPreparedStatement.setString(4, paramCommodityTypeVO.str2);
      localPreparedStatement.setString(5, paramCommodityTypeVO.str3);
      localPreparedStatement.setString(6, paramCommodityTypeVO.str4);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void modifyCommodityType(CommodityTypeVO paramCommodityTypeVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "update v_commoditytype set name=?,reservedchar1=?,reservedchar2=?,reservedchar3=?,reservedchar4=? where id=? ";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramCommodityTypeVO.name);
      localPreparedStatement.setString(2, paramCommodityTypeVO.str1);
      localPreparedStatement.setString(3, paramCommodityTypeVO.str2);
      localPreparedStatement.setString(4, paramCommodityTypeVO.str3);
      localPreparedStatement.setString(5, paramCommodityTypeVO.str4);
      localPreparedStatement.setInt(6, paramCommodityTypeVO.id);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void addCommodityProperty(CommodityPropertyVO paramCommodityPropertyVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "insert into v_commodityproperty(id,cpid,name,type,isnull,charvartext,reservedchar1,reservedchar2,reservedchar3,reservedchar4,reservedchar5) values(?,?,?,?,?,?,?,?,?,?,?)";
      localPreparedStatement = localConnection.prepareStatement("select sp_v_commodityproperty.nextval from dual");
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        paramCommodityPropertyVO.id = localResultSet.getInt(1);
      }
      localPreparedStatement.close();
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramCommodityPropertyVO.id);
      localPreparedStatement.setInt(2, paramCommodityPropertyVO.cpid);
      localPreparedStatement.setString(3, paramCommodityPropertyVO.name);
      localPreparedStatement.setInt(4, paramCommodityPropertyVO.type);
      localPreparedStatement.setInt(5, paramCommodityPropertyVO.isnull);
      localPreparedStatement.setString(6, paramCommodityPropertyVO.charvartext);
      localPreparedStatement.setString(7, paramCommodityPropertyVO.str1);
      localPreparedStatement.setString(8, paramCommodityPropertyVO.str2);
      localPreparedStatement.setString(9, paramCommodityPropertyVO.str3);
      localPreparedStatement.setString(10, paramCommodityPropertyVO.str4);
      localPreparedStatement.setString(11, paramCommodityPropertyVO.str5);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public void deleteCommodityProperty(CommodityPropertyVO paramCommodityPropertyVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "delete from v_commodityproperty where id=? and cpid=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramCommodityPropertyVO.id);
      localPreparedStatement.setInt(2, paramCommodityPropertyVO.cpid);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public CommodityPropertyVO[] getCommodityProperty(QueryValue paramQueryValue)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityPropertyVO[] arrayOfCommodityPropertyVO = null;
    CommodityPropertyVO localCommodityPropertyVO = null;
    int i = paramQueryValue.pageIndex;
    int j = paramQueryValue.pageSize;
    String str1 = paramQueryValue.filter;
    int k = (i - 1) * j + 1;
    int m = i * j;
    Vector localVector = new Vector();
    String str2 = null;
    try
    {
      localConnection = getConnection();
      str2 = "select id,cpid,name,type,isnull,charvartext,reservedchar1,reservedchar2,reservedchar3,reservedchar4,reservedchar5,rown from (select id,cpid,name,type,isnull,charvartext,reservedchar1,reservedchar2,reservedchar3,reservedchar4,reservedchar5,rownum rown from (select * from v_commodityproperty where 1=1" + str1 + " )) where rown between " + k + " and " + m + " ";
      localPreparedStatement = localConnection.prepareStatement(str2);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityPropertyVO = new CommodityPropertyVO();
        localCommodityPropertyVO.id = localResultSet.getInt(1);
        localCommodityPropertyVO.cpid = localResultSet.getInt(2);
        localCommodityPropertyVO.name = localResultSet.getString(3);
        localCommodityPropertyVO.type = localResultSet.getInt(4);
        localCommodityPropertyVO.isnull = localResultSet.getInt(5);
        localCommodityPropertyVO.charvartext = localResultSet.getString(6);
        localCommodityPropertyVO.str1 = localResultSet.getString(7);
        localCommodityPropertyVO.str2 = localResultSet.getString(8);
        localCommodityPropertyVO.str3 = localResultSet.getString(9);
        localCommodityPropertyVO.str4 = localResultSet.getString(10);
        localCommodityPropertyVO.str4 = localResultSet.getString(11);
        localVector.add(localCommodityPropertyVO);
      }
      arrayOfCommodityPropertyVO = new CommodityPropertyVO[localVector.size()];
      for (int n = 0; n < localVector.size(); n++) {
        arrayOfCommodityPropertyVO[n] = ((CommodityPropertyVO)localVector.get(n));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCommodityPropertyVO;
  }
  
  public void modifyCommodityProperty(CommodityPropertyVO paramCommodityPropertyVO)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "update v_commodityproperty set name=?,type=?,isnull=?,charvartext=?,reservedchar1=?,reservedchar2=?,reservedchar3=?,reservedchar4=?,reservedchar5=? where id=? and cpid=? ";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramCommodityPropertyVO.name);
      localPreparedStatement.setInt(2, paramCommodityPropertyVO.type);
      localPreparedStatement.setInt(3, paramCommodityPropertyVO.isnull);
      localPreparedStatement.setString(4, paramCommodityPropertyVO.charvartext);
      localPreparedStatement.setString(5, paramCommodityPropertyVO.str1);
      localPreparedStatement.setString(6, paramCommodityPropertyVO.str2);
      localPreparedStatement.setString(7, paramCommodityPropertyVO.str3);
      localPreparedStatement.setString(8, paramCommodityPropertyVO.str4);
      localPreparedStatement.setString(9, paramCommodityPropertyVO.str5);
      localPreparedStatement.setInt(10, paramCommodityPropertyVO.id);
      localPreparedStatement.setInt(11, paramCommodityPropertyVO.cpid);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(null, localPreparedStatement, localConnection);
    }
  }
  
  public CommodityVO[] getCommodityList(String paramString, int paramInt)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    CommodityVO[] arrayOfCommodityVO = null;
    CommodityVO localCommodityVO = null;
    Vector localVector = new Vector();
    String str = null;
    try
    {
      localConnection = getConnection();
      str = "select * from v_commodity where userid='" + paramString + "' and tradeMode=" + paramInt;
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        localCommodityVO = new CommodityVO();
        localCommodityVO.code = localResultSet.getString("code");
        localCommodityVO.beginPrice = localResultSet.getDouble("beginPrice");
        localCommodityVO.stepPrice = localResultSet.getDouble("stepPrice");
        localCommodityVO.tradeUnit = localResultSet.getDouble("tradeUnit");
        localCommodityVO.b_security = localResultSet.getDouble("b_security");
        localCommodityVO.b_fee = localResultSet.getDouble("b_fee");
        localCommodityVO.s_security = localResultSet.getDouble("s_security");
        localCommodityVO.s_fee = localResultSet.getDouble("s_fee");
        localCommodityVO.userID = localResultSet.getString("userID");
        localCommodityVO.tradeMode = localResultSet.getInt("tradeMode");
        localCommodityVO.alertPrice = localResultSet.getDouble("alertPrice");
        localCommodityVO.commodityid = localResultSet.getLong("id");
        localVector.add(localCommodityVO);
      }
      arrayOfCommodityVO = new CommodityVO[localVector.size()];
      for (int i = 0; i < localVector.size(); i++) {
        arrayOfCommodityVO[i] = ((CommodityVO)localVector.get(i));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return arrayOfCommodityVO;
  }
  
  public List makeTradeBID(int paramInt, Connection paramConnection)
  {
    PreparedStatement localPreparedStatement1 = null;
    CallableStatement localCallableStatement = null;
    ResultSet localResultSet1 = null;
    String str1 = "";
    KernelEngineBID localKernelEngineBID = null;
    List localList = null;
    try
    {
      localKernelEngineBID = (KernelEngineBID)GlobalContainer.getEngine(3);
      str1 = "select c.code v1,c.commodityid v2,d.beginPrice v3,d.amount v4,e.str17 v5,c.section v6,d.category v7,d.userid v8 from v_curCommodity c,v_commExt e,v_commodity d where d.id=c.commodityid and e.commID(+)=c.commodityid and c.section=" + paramInt + " and c.tradePartition=3" + " and c.code=d.code and d.status=6 ";
      localPreparedStatement1 = paramConnection.prepareStatement(str1);
      localResultSet1 = localPreparedStatement1.executeQuery();
      double d1 = 0.0D;
      double d2 = 0.0D;
      while (localResultSet1.next())
      {
        PreparedStatement localPreparedStatement2 = null;
        ResultSet localResultSet2 = null;
        CommodityVO localCommodityVO = getCommodity(localResultSet1.getLong(2));
        double d3 = localCommodityVO.beginPrice;
        double d4 = localCommodityVO.alertPrice;
        int i = (int)localCommodityVO.stepPrice;
        str1 = "update v_cursubmit set tradeFlag=3,validAmount=-1,modifytime=? where code='" + localResultSet1.getString(1) + "' and (price>" + d3 + " or price<" + d4 + " or mod(price," + i + ")!=0) and tradePartition=3";
        localPreparedStatement2 = paramConnection.prepareStatement(str1);
        Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
        localPreparedStatement2.setTimestamp(1, localTimestamp);
        localPreparedStatement2.executeUpdate();
        localPreparedStatement2.close();
        localPreparedStatement2 = null;
        int j = 1;
        str1 = "select * from v_cursubmit where code='" + localResultSet1.getString(1) + "' and tradeFlag=2 and tradePartition=3";
        localPreparedStatement2 = paramConnection.prepareStatement(str1);
        localResultSet2 = localPreparedStatement2.executeQuery();
        while (localResultSet2.next()) {
          j = 0;
        }
        localResultSet2.close();
        localPreparedStatement2.close();
        localResultSet2 = null;
        localPreparedStatement2 = null;
        if (j != 0)
        {
          str1 = "update v_commodity set status=2 where id=" + localResultSet1.getString(2) + " ";
          localPreparedStatement2 = paramConnection.prepareStatement(str1);
          localPreparedStatement2.executeUpdate();
          localPreparedStatement2.close();
          localPreparedStatement2 = null;
          str1 = "select id,userid,price,code,amount from v_cursubmit where tradeFlag=3 and code='" + localResultSet1.getString(1) + "' and tradePartition=3";
          localPreparedStatement2 = paramConnection.prepareStatement(str1);
          localResultSet2 = localPreparedStatement2.executeQuery();
          while (localResultSet2.next())
          {
            String str2 = localResultSet2.getString(1);
            String str3 = localResultSet2.getString(2);
            String str4 = localResultSet2.getString(4);
            double d5 = localResultSet2.getDouble(3);
            long l3 = localResultSet2.getLong(5);
            double d6 = 0.0D;
            double d8 = 0.0D;
            PreparedStatement localPreparedStatement3 = null;
            ResultSet localResultSet3 = null;
            str1 = "select * from v_tradeuser where usercode='" + str3 + "' for update";
            localPreparedStatement3 = paramConnection.prepareStatement(str1);
            localResultSet3 = localPreparedStatement3.executeQuery();
            while (localResultSet3.next())
            {
              d6 = localResultSet3.getDouble("feecut");
              d8 = localResultSet3.getDouble("feecutfee");
            }
            localResultSet3.close();
            localPreparedStatement3.close();
            localResultSet3 = null;
            localPreparedStatement3 = null;
            localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
            localCallableStatement.setString(2, str3);
            localCallableStatement.setInt(3, 1);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeQuery();
            double d10 = localKernelEngineBID.getCommodityCharge(str4, d5, l3, d6, d8);
            System.out.println("---------------------- 退招标保证金手续费 : " + str3 + "  金额为 : " + d10);
            localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
            localCallableStatement.setString(2, str3);
            localCallableStatement.setDouble(3, -1.0D * d10);
            localCallableStatement.setString(4, "4");
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
          }
          localResultSet2.close();
          localResultSet2 = null;
          localPreparedStatement2.close();
          localPreparedStatement2 = null;
        }
        else
        {
          long l1 = localResultSet1.getLong(4);
          long l2 = 0L;
          localList = judgeCommodity(paramConnection, localResultSet1.getLong(2), localResultSet1.getString(1), localResultSet1.getDouble(3), localResultSet1.getLong(4));
          Object localObject1;
          if ((localList != null) && (localList.size() > 0)) {
            for (int k = 0; k < localList.size(); k++)
            {
              localObject1 = (TradeResuleValue)localList.get(k);
              l2 += ((TradeResuleValue)localObject1).getAmount();
              settingBargain((TradeResuleValue)localObject1, paramConnection);
            }
          }
          String str5 = "";
          if (l1 != l2) {
            str5 = "7";
          } else {
            str5 = "1";
          }
          str1 = "update v_curCommodity set bargainFlag=1 where code='" + localResultSet1.getString(1) + "' and tradePartition=3";
          localPreparedStatement2 = paramConnection.prepareStatement(str1);
          localPreparedStatement2.executeUpdate();
          localPreparedStatement2.close();
          localPreparedStatement2 = null;
          localKernelEngineBID.delCommodityCharge(localResultSet1.getLong(2), paramConnection);
          if (localCommodityVO != null) {
            localCommodityVO.bargainFlag = 1;
          }
          str1 = "select id,userid,price,code,amount from v_cursubmit where tradeFlag!=1 and code='" + localResultSet1.getString(1) + "' and tradePartition=3";
          localPreparedStatement2 = paramConnection.prepareStatement(str1);
          localResultSet2 = localPreparedStatement2.executeQuery();
          while (localResultSet2.next())
          {
            localObject1 = localResultSet2.getString(1);
            String str6 = localResultSet2.getString(2);
            String str7 = localResultSet2.getString(4);
            double d7 = localResultSet2.getDouble(3);
            long l4 = localResultSet2.getLong(5);
            double d9 = 0.0D;
            double d11 = 0.0D;
            PreparedStatement localPreparedStatement4 = null;
            ResultSet localResultSet4 = null;
            str1 = "select * from v_tradeuser where usercode='" + str6 + "' for update";
            localPreparedStatement4 = paramConnection.prepareStatement(str1);
            localResultSet4 = localPreparedStatement4.executeQuery();
            while (localResultSet4.next())
            {
              d9 = localResultSet4.getDouble("feecut");
              d11 = localResultSet4.getDouble("feecutfee");
            }
            localResultSet4.close();
            localPreparedStatement4.close();
            localResultSet4 = null;
            localPreparedStatement4 = null;
            double d12 = localKernelEngineBID.getCommodityCharge(str7, d7, l4, d9, d11);
            System.out.println("---------------------- 退招标保证金手续费 : " + str6 + "  金额为 : " + d12);
            localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
            localCallableStatement.setString(2, str6);
            localCallableStatement.setInt(3, 1);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeQuery();
            localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
            localCallableStatement.setString(2, str6);
            localCallableStatement.setDouble(3, -1.0D * d12);
            localCallableStatement.setString(4, "4");
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
          }
          localResultSet2.close();
          localResultSet2 = null;
          localPreparedStatement2.close();
          localPreparedStatement2 = null;
          str1 = "update v_cursubmit set tradeFlag=0 where (tradeFlag!=1 and tradeFlag!=3) and code='" + localResultSet1.getString(1) + "' and  tradePartition=3";
          localPreparedStatement2 = paramConnection.prepareStatement(str1);
          localPreparedStatement2.executeUpdate();
          localPreparedStatement2.close();
          localPreparedStatement2 = null;
        }
      }
      localResultSet1.close();
      localResultSet1 = null;
      localPreparedStatement1.close();
      localPreparedStatement1 = null;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    catch (NamingException localNamingException)
    {
      localNamingException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet1, localCallableStatement, null);
    }
    return localList;
  }
  
  public List judgeCommodity(Connection paramConnection, long paramLong1, String paramString, double paramDouble, long paramLong2)
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    long l1 = paramLong2;
    int i = 0;
    try
    {
      String str1 = "select result from (select distinct(trunc((price*1000+1/amount),10)) result from v_curSubmit where code='" + paramString + "' and tradeFlag=2 and tradePartition=3) a order by result asc";
      localPreparedStatement = paramConnection.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        localArrayList1.add(localResultSet.getString("result"));
      }
      localPreparedStatement.close();
      localPreparedStatement = null;
      localResultSet.close();
      localResultSet = null;
      int j = 0;
      while (l1 > 0L)
      {
        String str2 = (String)localArrayList1.get(i);
        str1 = "select count(*) count from (select userID,price,amount from v_curSubmit where code='" + paramString + "' and tradeFlag=2 and trunc((price*1000+1/amount),10)=" + str2 + " and tradePartition=3)";
        localPreparedStatement = paramConnection.prepareStatement(str1);
        localResultSet = localPreparedStatement.executeQuery();
        if (localResultSet.next()) {
          j = localResultSet.getInt("count");
        }
        localPreparedStatement.close();
        localPreparedStatement = null;
        localResultSet.close();
        localResultSet = null;
        if (j == 1)
        {
          str1 = "select * from (select id,userID,price,amount from v_curSubmit where code='" + paramString + "' and tradeFlag=2 and trunc((price*1000+1/amount),10)=" + str2 + " and tradePartition=3)";
          localPreparedStatement = paramConnection.prepareStatement(str1);
          localResultSet = localPreparedStatement.executeQuery();
          if (localResultSet.next())
          {
            TradeResuleValue localTradeResuleValue1 = new TradeResuleValue();
            localTradeResuleValue1.setCode(paramString);
            localTradeResuleValue1.setCommodityID(paramLong1);
            localTradeResuleValue1.setPrice(localResultSet.getDouble("price"));
            if (localResultSet.getLong("amount") < l1)
            {
              localTradeResuleValue1.setAmount(localResultSet.getLong("amount"));
              l1 -= localResultSet.getLong("amount");
            }
            else
            {
              localTradeResuleValue1.setAmount(l1);
              l1 = 0L;
            }
            localTradeResuleValue1.setUserId(localResultSet.getString("userID"));
            localTradeResuleValue1.setSubmitId(localResultSet.getLong("id"));
            localTradeResuleValue1.setOldamount(localResultSet.getLong("amount"));
            localArrayList2.add(localTradeResuleValue1);
          }
          localPreparedStatement.close();
          localPreparedStatement = null;
          localResultSet.close();
          localResultSet = null;
        }
        else if (j > 1)
        {
          int k = Integer.parseInt(new Configuration().getSection("MEBS.TradeParams").getProperty("equalityRule"));
          long l2;
          TradeResuleValue localTradeResuleValue2;
          if (k == 0)
          {
            str1 = "select * from (select id,userID,price,amount from v_curSubmit where code='" + paramString + "' and tradeFlag=2 and trunc((price*1000+1/amount),10)=" + str2 + " and tradePartition=3)";
            localPreparedStatement = paramConnection.prepareStatement(str1);
            localResultSet = localPreparedStatement.executeQuery();
            l2 = 0L;
            while (localResultSet.next())
            {
              localTradeResuleValue2 = new TradeResuleValue();
              localTradeResuleValue2.setCode(paramString);
              localTradeResuleValue2.setCommodityID(paramLong1);
              localTradeResuleValue2.setPrice(localResultSet.getDouble("price"));
              if (localResultSet.getDouble("amount") * j < l1)
              {
                localTradeResuleValue2.setAmount(localResultSet.getLong("amount"));
                l2 = localResultSet.getLong("amount") * j;
              }
              else
              {
                localTradeResuleValue2.setAmount(l1 / j);
                l2 = l1;
              }
              localTradeResuleValue2.setUserId(localResultSet.getString("userID"));
              localTradeResuleValue2.setSubmitId(localResultSet.getLong("id"));
              localArrayList2.add(localTradeResuleValue2);
            }
            l1 -= l2;
            localPreparedStatement.close();
            localPreparedStatement = null;
            localResultSet.close();
            localResultSet = null;
          }
          else if (k == 1)
          {
            str1 = "select * from (select id,userID,price,amount,submitTime from v_curSubmit where code='" + paramString + "' and tradeFlag=2 and trunc((price*1000+1/amount),10)=" + str2 + " and tradePartition=3) order by submitTime";
            localPreparedStatement = paramConnection.prepareStatement(str1);
            localResultSet = localPreparedStatement.executeQuery();
            l2 = 0L;
            while ((localResultSet.next()) && (l1 != 0L))
            {
              localTradeResuleValue2 = new TradeResuleValue();
              localTradeResuleValue2.setCode(paramString);
              localTradeResuleValue2.setCommodityID(paramLong1);
              localTradeResuleValue2.setPrice(localResultSet.getDouble("price"));
              if (localResultSet.getDouble("amount") < l1)
              {
                localTradeResuleValue2.setAmount(localResultSet.getLong("amount"));
                l2 = localResultSet.getLong("amount");
              }
              else
              {
                localTradeResuleValue2.setAmount(l1);
                l2 = l1;
              }
              l1 -= l2;
              localTradeResuleValue2.setUserId(localResultSet.getString("userID"));
              localTradeResuleValue2.setSubmitId(localResultSet.getLong("id"));
              localArrayList2.add(localTradeResuleValue2);
            }
            localPreparedStatement.close();
            localPreparedStatement = null;
            localResultSet.close();
            localResultSet = null;
          }
        }
        j++;
        i++;
        if ((localArrayList1 == null) || (localArrayList1.size() <= i)) {
          break;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
    return localArrayList2;
  }
  
  public void settingBargain(TradeResuleValue paramTradeResuleValue, Connection paramConnection)
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    try
    {
      Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
      str = "update v_curSubmit set validamount=?,tradeFlag=1,modifytime=? where id=" + paramTradeResuleValue.getSubmitId() + " and tradePartition=3";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.setLong(1, paramTradeResuleValue.getAmount());
      localPreparedStatement.setTimestamp(2, localTimestamp);
      int i = localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
      localPreparedStatement = null;
      str = "update v_curSubmit set tradeFlag=1 where ID=" + paramTradeResuleValue.getSubmitId() + " and tradePartition=3";
      localPreparedStatement = paramConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
      localPreparedStatement = null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, null);
    }
  }
  
  public double getBargainMoney(long paramLong)
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    double d = 0.0D;
    try
    {
      localConnection = getConnection();
      str = "select sum(money) as m from (select ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,FrozenCapital from v_dailymoney where contractno=" + paramLong + " " + "and operation=406 UNION select ID,InfoDate,FirmID,Operation,ContractNo,Money" + ",Balance,Overdraft,FrozenCapital from v_hismoney  where contractno=" + paramLong + " " + "and operation=406)";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        d = localResultSet.getDouble("m");
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return d;
  }
  
  public int bargainMod(String paramString1, String paramString2)
    throws SQLException
  {
    int i = -1;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = "";
    try
    {
      localConnection = getJDBCConnection();
      localConnection.setAutoCommit(false);
      str1 = "update v_contracttemplet set templet=empty_clob() where id=?";
      localPreparedStatement = localConnection.prepareStatement(str1);
      localPreparedStatement.setLong(1, Long.parseLong(paramString1));
      localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
      localPreparedStatement = null;
      String str2 = paramString2.trim().substring(0, 3);
      if ("<p>".equals(str2))
      {
        paramString2 = paramString2.substring(3, paramString2.length());
        paramString2 = paramString2.replaceFirst("</p>", "<title>合同</title></head><body>");
        paramString2 = "<html>" + paramString2 + "</body></html>";
      }
      str1 = "select templet from v_contracttemplet where id=" + paramString1 + " for update";
      CLOB localCLOB = null;
      Statement localStatement = localConnection.createStatement();
      OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str1);
      while (localOracleResultSet.next())
      {
        localCLOB = localOracleResultSet.getCLOB(1);
        localCLOB.putString(1L, paramString2);
      }
      localOracleResultSet.close();
      localOracleResultSet = null;
      localStatement.close();
      localStatement = null;
      str1 = "update v_contracttemplet set templet=? where id=?";
      localPreparedStatement = localConnection.prepareStatement(str1);
      localPreparedStatement.setClob(1, localCLOB);
      localPreparedStatement.setLong(2, Long.parseLong(paramString1));
      localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
      localPreparedStatement = null;
      i = 1;
    }
    catch (SQLException localSQLException)
    {
      localConnection.rollback();
      localSQLException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      localConnection.setAutoCommit(true);
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return i;
  }
  
  public void addBargainTmp(String paramString, int paramInt)
    throws SQLException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = null;
    try
    {
      localConnection = getJDBCConnection();
      localConnection.setAutoCommit(false);
      str1 = "select DESCRIPTION from v_syspartition  where PARTITIONID=" + paramInt;
      localPreparedStatement = localConnection.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next()) {
        str2 = localResultSet.getString("DESCRIPTION");
      }
      str1 = "insert into v_contracttemplet (id,type,TEMPLET,name) values(?, ?, empty_clob(),?)";
      localPreparedStatement = localConnection.prepareStatement(str1);
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setInt(2, paramInt);
      localPreparedStatement.setString(3, str2);
      localPreparedStatement.executeUpdate();
      str1 = "select TEMPLET from v_contracttemplet where id=" + paramInt + " for update";
      CLOB localCLOB = null;
      Statement localStatement = localConnection.createStatement();
      OracleResultSet localOracleResultSet = (OracleResultSet)localStatement.executeQuery(str1);
      while (localOracleResultSet.next())
      {
        localCLOB = localOracleResultSet.getCLOB(1);
        localCLOB.putString(1L, paramString);
      }
      localOracleResultSet.close();
      localOracleResultSet = null;
      localStatement.close();
      localStatement = null;
      str1 = "update v_contracttemplet set TEMPLET=? where id=" + paramInt;
      localPreparedStatement = localConnection.prepareStatement(str1);
      localPreparedStatement.setClob(1, localCLOB);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      localConnection.rollback();
      localSQLException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      localConnection.setAutoCommit(true);
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
  }
  
  public Map getRMIUrl()
    throws SQLException
  {
    HashMap localHashMap = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = "";
    try
    {
      localConnection = getConnection();
      str = "select hostip,rmi_port from m_trademodule where moduleId=4";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localHashMap = new HashMap();
        localHashMap.put("host", localResultSet.getString(1));
        localHashMap.put("port", Integer.valueOf(localResultSet.getInt(2)));
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      closeStatement(localResultSet, localPreparedStatement, localConnection);
    }
    return localHashMap;
  }
}

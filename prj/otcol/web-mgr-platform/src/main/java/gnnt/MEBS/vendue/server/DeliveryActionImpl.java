package gnnt.MEBS.vendue.server;

import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.dao.TradeDAOFactory;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.MoneyVO;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.util.Arith;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

public class DeliveryActionImpl
  extends DeliveryAction
{
  public DeliveryActionImpl()
    throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, Exception
  {
    TRADEDAO = TradeDAOFactory.getDAO();
  }
  
  public void convertBailToPartitionPrePayment(int paramInt)
    throws SQLException
  {
    BargainVO[] arrayOfBargainVO = TRADEDAO.getHisBargainList(paramInt);
    if (arrayOfBargainVO != null)
    {
      System.out.println();
      System.out.println("==============剩余资金转货款(全部)=================");
      for (int i = 0; i < arrayOfBargainVO.length; i++)
      {
        BargainVO localBargainVO = arrayOfBargainVO[i];
        if (localBargainVO.patchStatus == 0)
        {
          System.out.println("合同号是 : " + localBargainVO.commodityID);
          convertBailToPrepayment(localBargainVO.contractID);
        }
      }
    }
  }
  
  public void convertBailToPrepayment(long paramLong)
    throws SQLException
  {
    Connection localConnection = null;
    Statement localStatement = null;
    try
    {
      localConnection = TRADEDAO.getConnection();
      localConnection.setAutoCommit(false);
      BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong, localConnection);
      localBargainVO.patchStatus = 1;
      TRADEDAO.modifyHisBargain(localBargainVO, localConnection);
      localConnection.commit();
    }
    catch (SQLException localSQLException)
    {
      localConnection.rollback();
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      TRADEDAO.closeStatement(null, localStatement, localConnection);
    }
  }
  
  public int convertPrePaymentToContract(long paramLong, double paramDouble, Connection paramConnection)
    throws SQLException
  {
    int i = 1;
    Calendar localCalendar = Calendar.getInstance();
    String str = localCalendar.get(1) + "-" + localCalendar.get(2) + "-" + localCalendar.get(5) + "  " + localCalendar.get(11) + " : " + localCalendar.get(12);
    CallableStatement localCallableStatement = null;
    try
    {
      BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong, paramConnection);
      TradeUserVO localTradeUserVO = TRADEDAO.getTradeUser(getBuyerID(localBargainVO.contractID, paramConnection), paramConnection);
      CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID, paramConnection);
      double d1 = localTradeUserVO.balance - localTradeUserVO.frozenCapital;
      double d2 = localBargainVO.price;
      System.out.println("============成交价格是  is " + d2 + "     " + str);
      double d3 = localBargainVO.b_bail;
      System.out.println("=============买家的保证金是   is " + d3 + "     " + str);
      double d4 = localBargainVO.amount * localCommodityVO.tradeUnit;
      System.out.println("=================成交数量(吨)是  is " + d4 + "     " + str);
      double d5 = Arith.mul(paramDouble, Arith.sub(d2, Arith.div(d3, d4)));
      System.out.println("出库金额  neededPrepayment is  " + d5 + "     " + str);
      if ((paramDouble >= 0.0D) && ((Arith.compareTo(paramDouble, d4) == -1) || (Arith.compareTo(paramDouble, d4) == 0)) && ((Arith.compareTo(d5, d1) == -1) || (Arith.compareTo(d5, d1) == 0) || (Arith.compareTo(Arith.sub(d5, d1), 1.0F) == -1)))
      {
        localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
        localCallableStatement.setString(2, localTradeUserVO.userCode);
        localCallableStatement.setInt(3, 406);
        localCallableStatement.setDouble(4, d5);
        localCallableStatement.setLong(5, paramLong);
        localCallableStatement.registerOutParameter(1, 8);
        localCallableStatement.executeUpdate();
        double d6 = localCallableStatement.getDouble(1);
        addDailyMoney(d6, localTradeUserVO.paymentForGoods, paramLong, localTradeUserVO.userCode, d5, 406, "", paramConnection);
      }
      else
      {
        i = 0;
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      TRADEDAO.closeStatement(null, localCallableStatement, null);
    }
    return i;
  }
  
  public int delCommodityCharge(long paramLong, int paramInt, Connection paramConnection)
    throws Exception
  {
    int i = 0;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(paramInt);
      i = localKernelEngine.delCommodityCharge(paramLong, paramConnection);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    return i;
  }
  
  public int finishContract(long paramLong, int paramInt, double paramDouble1, double paramDouble2)
    throws SQLException
  {
    Connection localConnection = null;
    CallableStatement localCallableStatement = null;
    int i = 2;
    try
    {
      localConnection = TRADEDAO.getConnection();
      localConnection.setAutoCommit(false);
      BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong, localConnection);
      CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID, localConnection);
      TradeUserVO localTradeUserVO1 = TRADEDAO.getTradeUser(getBuyerID(localBargainVO.contractID, localConnection), localConnection);
      TradeUserVO localTradeUserVO2 = TRADEDAO.getTradeUser(getSellerID(localBargainVO.contractID, localConnection), localConnection);
      double d1 = getPaidMoneyForContract(paramLong, localConnection);
      double d2 = 0.0D;
      double d3 = 0.0D;
      double d4 = localBargainVO.amount - paramDouble1 - paramDouble2;
      double d5 = Arith.mul(paramDouble1, localBargainVO.price);
      double d6 = Arith.mul((float)localBargainVO.amount, localBargainVO.price);
      double d7 = 0.0D;
      double d8 = 0.0D;
      double d9 = 0.0D;
      System.out.println(" fellBackAmount = " + paramDouble2);
      System.out.println(" handledAmount = " + paramDouble1);
      d3 = Arith.div(Arith.mul(localBargainVO.amount - paramDouble2 - paramDouble1, localBargainVO.b_bail), Arith.mul((float)localBargainVO.amount, localCommodityVO.tradeUnit));
      d8 = TRADEDAO.getBargainMoney(paramLong);
      if (localBargainVO.price - localBargainVO.b_lastBail / localBargainVO.amount > 0.0D) {
        d9 = Arith.div(d8, localBargainVO.price - Arith.div(localBargainVO.b_lastBail, (float)localBargainVO.amount));
      }
      if ((paramInt == 0) || (paramInt == 2)) {
        d2 = localBargainVO.b_bail;
      } else if (paramInt == 1) {
        d2 = localBargainVO.b_bail - Arith.div(Arith.mul(paramDouble2, localBargainVO.b_bail), Arith.mul((float)localBargainVO.amount, localCommodityVO.tradeUnit));
      }
      if (Arith.compareTo(d5, Arith.add(d2, d1)) == 1)
      {
        i = 0;
      }
      else if (localBargainVO.status != 0)
      {
        i = 1;
      }
      else
      {
        double d10;
        if (paramInt == 0)
        {
          localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
          localCallableStatement.setString(2, localTradeUserVO1.userCode);
          localCallableStatement.setInt(3, 407);
          localCallableStatement.setDouble(4, d2);
          localCallableStatement.setLong(5, localBargainVO.contractID);
          localCallableStatement.registerOutParameter(1, 8);
          localCallableStatement.executeUpdate();
          d7 = localCallableStatement.getDouble(1);
          addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d2, 407, "208", localConnection);
          localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
          localCallableStatement.setString(2, localTradeUserVO1.userCode);
          localCallableStatement.setInt(3, 406);
          localCallableStatement.setDouble(4, d2);
          localCallableStatement.setLong(5, localBargainVO.contractID);
          localCallableStatement.registerOutParameter(1, 8);
          localCallableStatement.executeUpdate();
          d7 = localCallableStatement.getDouble(1);
          addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d2, 406, "bail", localConnection);
          d10 = 0.0D;
          if (d9 > paramDouble1) {
            d10 = Arith.mul(d9 - paramDouble1, localBargainVO.price - Arith.div(localBargainVO.b_lastBail, (float)localBargainVO.amount)) + d3;
          } else {
            d10 = d3;
          }
          localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
          localCallableStatement.setString(2, localTradeUserVO1.userCode);
          localCallableStatement.setInt(3, 409);
          localCallableStatement.setDouble(4, d10);
          localCallableStatement.setLong(5, localBargainVO.contractID);
          localCallableStatement.registerOutParameter(1, 8);
          localCallableStatement.executeUpdate();
          d7 = localCallableStatement.getDouble(1);
          addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d10, 409, "", localConnection);
          localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
          localCallableStatement.setString(2, localTradeUserVO2.userCode);
          localCallableStatement.setInt(3, 407);
          localCallableStatement.setDouble(4, localBargainVO.s_bail);
          localCallableStatement.setLong(5, localBargainVO.contractID);
          localCallableStatement.registerOutParameter(1, 8);
          localCallableStatement.executeUpdate();
          d7 = localCallableStatement.getDouble(1);
          addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, localBargainVO.s_bail, 407, "", localConnection);
          localTradeUserVO2.balance += d5;
          localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
          localCallableStatement.setString(2, localTradeUserVO2.userCode);
          localCallableStatement.setInt(3, 408);
          localCallableStatement.setDouble(4, d5);
          localCallableStatement.setLong(5, localBargainVO.contractID);
          localCallableStatement.registerOutParameter(1, 8);
          localCallableStatement.executeUpdate();
          d7 = localCallableStatement.getDouble(1);
          addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, d5, 408, "", localConnection);
          TRADEDAO.modifyTradeUser(localTradeUserVO2, localConnection);
        }
        else
        {
          double d11;
          if (paramInt == 1)
          {
            d10 = Arith.div(Arith.mul(paramDouble2, localBargainVO.b_bail), Arith.mul((float)localBargainVO.amount, localCommodityVO.tradeUnit));
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 407);
            localCallableStatement.setDouble(4, Arith.sub(localBargainVO.b_bail, d10));
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, Arith.sub(localBargainVO.b_bail, d10), 407, "208", localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 406);
            localCallableStatement.setDouble(4, d2);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d2, 406, "bail", localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO2.userCode);
            localCallableStatement.setInt(3, 415);
            localCallableStatement.setDouble(4, d10);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, d10, 415, "", localConnection);
            TRADEDAO.modifyTradeUser(localTradeUserVO2, localConnection);
            d11 = 0.0D;
            if (d9 > paramDouble1) {
              d11 = Arith.mul(d9 - paramDouble1, localBargainVO.price - Arith.div(localBargainVO.b_lastBail, (float)localBargainVO.amount)) + d3;
            } else {
              d11 = d3;
            }
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 409);
            localCallableStatement.setDouble(4, d11);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d11, 409, "", localConnection);
            TRADEDAO.modifyTradeUser(localTradeUserVO1, localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO2.userCode);
            localCallableStatement.setInt(3, 407);
            localCallableStatement.setDouble(4, localBargainVO.s_bail);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, localBargainVO.s_bail, 407, "", localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO2.userCode);
            localCallableStatement.setInt(3, 408);
            localCallableStatement.setDouble(4, d5);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, d5, 408, "", localConnection);
            TRADEDAO.modifyTradeUser(localTradeUserVO2, localConnection);
          }
          else if (paramInt == 2)
          {
            d10 = Arith.mul(Arith.div(d2, (float)localBargainVO.amount), paramDouble1);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 407);
            localCallableStatement.setDouble(4, d2);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d2, 407, "208", localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 406);
            localCallableStatement.setDouble(4, d2);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d2, 406, "bail", localConnection);
            d11 = 0.0D;
            if (d9 > paramDouble1) {
              d11 = Arith.mul(d9 - paramDouble1, localBargainVO.price - Arith.div(localBargainVO.b_lastBail, (float)localBargainVO.amount)) + (d2 - d10);
            } else {
              d11 = d2 - d10;
            }
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 409);
            localCallableStatement.setDouble(4, d11);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d11, 409, "", localConnection);
            TRADEDAO.modifyTradeUser(localTradeUserVO1, localConnection);
            double d12 = Arith.div(Arith.mul(paramDouble2, localBargainVO.s_bail), Arith.mul((float)localBargainVO.amount, localCommodityVO.tradeUnit));
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO1.userCode);
            localCallableStatement.setInt(3, 415);
            localCallableStatement.setDouble(4, d12);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO1.paymentForGoods, paramLong, localTradeUserVO1.userCode, d12, 415, "", localConnection);
            TRADEDAO.modifyTradeUser(localTradeUserVO1, localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO2.userCode);
            localCallableStatement.setInt(3, 407);
            localCallableStatement.setDouble(4, localBargainVO.s_bail - d12);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, localBargainVO.s_bail - d12, 407, "", localConnection);
            localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
            localCallableStatement.setString(2, localTradeUserVO2.userCode);
            localCallableStatement.setInt(3, 408);
            localCallableStatement.setDouble(4, d5);
            localCallableStatement.setLong(5, localBargainVO.contractID);
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            d7 = localCallableStatement.getDouble(1);
            addDailyMoney(d7, localTradeUserVO2.paymentForGoods, paramLong, localTradeUserVO2.userCode, d5, 408, "", localConnection);
            TRADEDAO.modifyTradeUser(localTradeUserVO2, localConnection);
          }
        }
        localBargainVO.status = 1;
        localBargainVO.actualAmount = paramDouble1;
        localBargainVO.fellBackAmount = paramDouble2;
        localBargainVO.result = paramInt;
        localBargainVO.s_lastBail = 0.0D;
        localBargainVO.b_lastBail = 0.0D;
        TRADEDAO.modifyHisBargain(localBargainVO, localConnection);
      }
      localConnection.commit();
    }
    catch (SQLException localSQLException)
    {
      localConnection.rollback();
      localSQLException.printStackTrace();
      throw localSQLException;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      TRADEDAO.closeStatement(null, localCallableStatement, localConnection);
    }
    return i;
  }
  
  public BargainVO getHisBargain(long paramLong)
    throws SQLException
  {
    return TRADEDAO.getHisBargain(paramLong);
  }
  
  private double getContractPayment(long paramLong, Connection paramConnection)
    throws SQLException
  {
    double d = 0.0D;
    BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong, paramConnection);
    CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID, paramConnection);
    d = Arith.mul(localBargainVO.price, localCommodityVO.tradeUnit) * localBargainVO.amount;
    return d;
  }
  
  private double getPaidMoneyForContract(long paramLong, Connection paramConnection)
    throws SQLException
  {
    double d = 0.0D;
    MoneyVO[] arrayOfMoneyVO = TRADEDAO.getMoneyList("where (operation=406 or operation=409) and contractno=" + paramLong, paramConnection);
    if (arrayOfMoneyVO != null) {
      for (int i = 0; i < arrayOfMoneyVO.length; i++) {
        if (arrayOfMoneyVO[i].operation == 406) {
          d += arrayOfMoneyVO[i].money;
        } else if (arrayOfMoneyVO[i].operation == 409) {
          d -= arrayOfMoneyVO[i].money;
        }
      }
    }
    return d;
  }
  
  public double getPaidMoneyForContract(long paramLong)
    throws SQLException
  {
    double d = 0.0D;
    MoneyVO[] arrayOfMoneyVO = TRADEDAO.getMoneyList("where (operation=406 or operation=409) and contractno=" + paramLong);
    if (arrayOfMoneyVO != null) {
      for (int i = 0; i < arrayOfMoneyVO.length; i++) {
        if (arrayOfMoneyVO[i].operation == 406) {
          d += arrayOfMoneyVO[i].money;
        } else if (arrayOfMoneyVO[i].operation == 409) {
          d -= arrayOfMoneyVO[i].money;
        }
      }
    }
    return d;
  }
  
  public TradeUserVO getTradeUser(String paramString)
    throws SQLException
  {
    return TRADEDAO.getTradeUser(paramString);
  }
  
  public MoneyVO[] getMoneyList(String paramString)
    throws SQLException
  {
    return TRADEDAO.getMoneyList(paramString);
  }
  
  public CommodityVO getCommodity(long paramLong)
    throws SQLException
  {
    return TRADEDAO.getCommodity(paramLong);
  }
  
  private void addDailyMoney(double paramDouble1, double paramDouble2, long paramLong, String paramString1, double paramDouble3, int paramInt, String paramString2, Connection paramConnection)
    throws SQLException
  {
    Calendar localCalendar = Calendar.getInstance();
    String str = localCalendar.get(1) + "-" + localCalendar.get(2) + "-" + localCalendar.get(5) + "  " + localCalendar.get(11) + " : " + localCalendar.get(12);
    MoneyVO localMoneyVO = new MoneyVO();
    localMoneyVO.balance = Arith.format(paramDouble1, 2);
    localMoneyVO.contractNo = paramLong;
    localMoneyVO.firmID = paramString1;
    localMoneyVO.infoDate = new Timestamp(System.currentTimeMillis());
    localMoneyVO.money = Arith.format(paramDouble3, 2);
    localMoneyVO.operation = paramInt;
    localMoneyVO.paymentForGoods = Arith.format(paramDouble2, 2);
    localMoneyVO.note = paramString2;
    System.out.println("===========================写当日流水=========================     " + str);
    System.out.println("交易商 " + paramString1 + " 现有资金是 is " + paramDouble1);
    System.out.println();
    TRADEDAO.addDailymoney(localMoneyVO, paramConnection);
  }
  
  private String getBuyerID(long paramLong, Connection paramConnection)
    throws SQLException
  {
    String str = "";
    BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong, paramConnection);
    CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID, paramConnection);
    if (localCommodityVO.tradeMode == 0) {
      str = localBargainVO.userID;
    } else if (localCommodityVO.tradeMode == 1) {
      str = localCommodityVO.userID;
    } else if (localCommodityVO.tradeMode == 2) {
      str = localCommodityVO.userID;
    }
    return str;
  }
  
  private String getSellerID(long paramLong, Connection paramConnection)
    throws SQLException
  {
    String str = "";
    BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong, paramConnection);
    CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID, paramConnection);
    if (localCommodityVO.tradeMode == 0) {
      str = localCommodityVO.userID;
    } else if (localCommodityVO.tradeMode == 1) {
      str = localBargainVO.userID;
    } else if (localCommodityVO.tradeMode == 2) {
      str = localBargainVO.userID;
    }
    return str;
  }
  
  public String getBuyerID(long paramLong)
    throws SQLException
  {
    String str = "";
    BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong);
    CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID);
    if (localCommodityVO.tradeMode == 0) {
      str = localBargainVO.userID;
    } else if (localCommodityVO.tradeMode == 1) {
      str = localCommodityVO.userID;
    } else if (localCommodityVO.tradeMode == 2) {
      str = localCommodityVO.userID;
    }
    return str;
  }
  
  public String getSellerID(long paramLong)
    throws SQLException
  {
    String str = "";
    BargainVO localBargainVO = TRADEDAO.getHisBargain(paramLong);
    CommodityVO localCommodityVO = TRADEDAO.getCommodity(localBargainVO.commodityID);
    if (localCommodityVO.tradeMode == 0) {
      str = localCommodityVO.userID;
    } else if (localCommodityVO.tradeMode == 1) {
      str = localBargainVO.userID;
    } else if (localCommodityVO.tradeMode == 2) {
      str = localBargainVO.userID;
    }
    return str;
  }
  
  public int addCommodityCharge(String paramString, long paramLong, int paramInt, Connection paramConnection)
    throws Exception
  {
    int i = 0;
    try
    {
      KernelEngine localKernelEngine = GlobalContainer.getEngine(paramInt);
      i = localKernelEngine.addCommodityCharge(paramString, paramLong, paramConnection);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    return i;
  }
}

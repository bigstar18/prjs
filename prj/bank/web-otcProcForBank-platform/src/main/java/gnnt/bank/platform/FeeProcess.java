package gnnt.bank.platform;

import gnnt.bank.platform.dao.BankDAO;
import gnnt.bank.platform.dao.BankDAOFactory;
import gnnt.bank.platform.util.Arith;
import gnnt.bank.platform.util.Tool;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeInfoVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

public class FeeProcess
{
  private static BankDAO DAO;
  
  public FeeProcess()
  {
    try
    {
      DAO = BankDAOFactory.getDAO();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Tool.log("取得数据库访问对象失败：" + e);
    }
  }
  
  private Hashtable<String, String> getBankFeeList(String bankID)
  {
    Hashtable<String, String> allFee = new Hashtable();
    try
    {
      Vector<DicValue> dicList = DAO.getDicList("where type=3 and bankid='" + bankID + "'");
      for (int i = 0; i < dicList.size(); i++)
      {
        DicValue dVal = (DicValue)dicList.get(i);
        allFee.put(dVal.name, dVal.value);
      }
    }
    catch (Exception e)
    {
      Tool.log("银行所有费用标准Exception:" + e);
      e.printStackTrace();
    }
    return allFee;
  }
  
  private Hashtable<String, String> getFeeList()
  {
    Hashtable<String, String> allFee = new Hashtable();
    try
    {
      Vector<DicValue> dicList = DAO.getDicList("where type=3 and ( bankid IS  NULL or length(bankid)=0 ) ");
      for (int i = 0; i < dicList.size(); i++)
      {
        DicValue dVal = (DicValue)dicList.get(i);
        allFee.put(dVal.name, dVal.value);
      }
    }
    catch (Exception e)
    {
      Tool.log("获取交易商所有费用标准Exception:" + e);
      e.printStackTrace();
    }
    return allFee;
  }
  
  private boolean isTownsAccont(String bankID, String account)
  {
    boolean isTowns = false;
    try
    {
      Vector<DicValue> dicList = DAO.getDicList("where type=2 and name='townsAccount' and bankid='" + bankID + "'");
      if (dicList.size() > 0)
      {
        DicValue dVal = (DicValue)dicList.get(0);
        String townsAccount = dVal.value;
        if (townsAccount.length() > 0)
        {
          String[] acountStart = townsAccount.split(";");
          for (int i = 0; i < acountStart.length; i++) {
            if (account.startsWith(acountStart[i]))
            {
              isTowns = true;
              break;
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      Tool.log("判断是否同一个城市的银行账号Exception:" + e);
      e.printStackTrace();
    }
    return isTowns;
  }
  
  private double calculateFee(Vector<FeeInfoVO> feeList, double money, double example)
  {
    double result = 0.0D;
    for (int i = 0; i < feeList.size(); i++)
    {
      FeeInfoVO feeInfoVO = (FeeInfoVO)feeList.get(i);
      if (((money > feeInfoVO.downLimit) && (money <= feeInfoVO.upLimit)) || (
        (money > feeInfoVO.downLimit) && (feeInfoVO.upLimit == -1)))
      {
        if (feeInfoVO.tMode == 0) {
          result = Arith.mul(example, feeInfoVO.rate);
        } else if (feeInfoVO.tMode == 1) {
          result = feeInfoVO.rate;
        }
        if ((feeInfoVO.maxRateValue > 0.0D) && (Arith.compareTo(result, feeInfoVO.maxRateValue) == 1)) {
          result = feeInfoVO.maxRateValue;
        }
        if (Arith.compareTo(result, feeInfoVO.minRateValue) != -1) {
          break;
        }
        result = feeInfoVO.minRateValue;
        break;
      }
    }
    return result;
  }
  
  protected double getInRate(String bankID, String firmID, double money, int express, String account, Connection conn)
  {
    double result = 0.0D;
    try
    {
      Hashtable<String, String> fFee = getFeeList();
      Hashtable<String, String> bFee = getBankFeeList(bankID);
      if (bFee.containsKey("inRateFee"))
      {
        Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)bFee.get("inRateFee") + "' and userID='" + bankID + "'", conn);
        if ((feeList != null) && (feeList.size() > 0)) {
          result = calculateFee(feeList, money, money);
        }
      }
      if (fFee.containsKey("inRateFee"))
      {
        Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)fFee.get("inRateFee") + "' and userID='" + firmID + "'", conn);
        if ((feeList != null) && (feeList.size() > 0)) {
          result = calculateFee(feeList, money, money);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      Tool.log("取得入金手续费SQLException:" + e);
      result = -1.0D;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Tool.log("取得入金手续费Exception:" + e);
      result = -2.0D;
    }
    return result;
  }
  
  protected double getOutRate(String bankID, String firmID, double money, int express, String account, Connection conn)
  {
    double result = 0.0D;
    try
    {
      Hashtable<String, String> fFee = getFeeList();
      Hashtable<String, String> bFee = getBankFeeList(bankID);
      double outRateFee = 0.0D;
      if (bFee.containsKey("outRateFee"))
      {
        Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)bFee.get("outRateFee") + "' and userID='" + bankID + "'", conn);
        if ((feeList != null) && (feeList.size() > 0)) {
          outRateFee = calculateFee(feeList, money, money);
        }
      }
      if (fFee.containsKey("outRateFee"))
      {
        Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)fFee.get("outRateFee") + "' and userID='" + firmID + "'", conn);
        if ((feeList != null) && (feeList.size() > 0)) {
          outRateFee = calculateFee(feeList, money, money);
        }
      }
      result = outRateFee;
      if (isTownsAccont(bankID, account))
      {
        if (bFee.containsKey("townsOutRateFee"))
        {
          Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)bFee.get("townsOutRateFee") + "' and userID='" + bankID + "'", conn);
          if ((feeList != null) && (feeList.size() > 0)) {
            result = Arith.add(result, calculateFee(feeList, money, money));
          }
        }
      }
      else
      {
        if (bFee.containsKey("difTransFee"))
        {
          double difTransFee = 0.0D;
          Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)bFee.get("difTransFee") + "' and userID='" + bankID + "'", conn);
          if ((feeList != null) && (feeList.size() > 0))
          {
            difTransFee = calculateFee(feeList, money, money);
            result = Arith.add(result, difTransFee);
          }
          if (express == 1) {
            if (bFee.containsKey("expressFee"))
            {
              feeList = DAO.getFeeInfoList(" where type='" + (String)bFee.get("expressFee") + "' and userID='" + bankID + "'", conn);
              if ((feeList != null) && (feeList.size() > 0)) {
                result = Arith.add(result, calculateFee(feeList, money, difTransFee));
              }
            }
          }
        }
        if (bFee.containsKey("difOutRateFee"))
        {
          Vector<FeeInfoVO> feeList = DAO.getFeeInfoList(" where type='" + (String)bFee.get("difOutRateFee") + "' and userID='" + bankID + "'", conn);
          if ((feeList != null) && (feeList.size() > 0)) {
            result = Arith.add(result, calculateFee(feeList, money, money));
          }
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      Tool.log("取得出金手续费SQLException:" + e);
      result = -1.0D;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Tool.log("取得出金手续费Exception:" + e);
      result = -2.0D;
    }
    return result;
  }
}

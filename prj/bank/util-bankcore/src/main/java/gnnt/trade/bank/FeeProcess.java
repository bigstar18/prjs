package gnnt.trade.bank;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Arith;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeInfoVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;
/**
 * 费用计算
 * @author 薛计涛
 *
 */
public class FeeProcess 
{
	/**
	 * 数据库访问对象
	 */
	private static BankDAO DAO;
	/**
	 * 构造函数
	 */
	public FeeProcess()
	{
		try 
		{
			//取得数据库访问对象
			DAO = BankDAOFactory.getDAO();		
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			log("取得数据库访问对象失败："+e);
		}		
	}
	
	/**
	 * 向指定日志文件写入日志信息
	 * @param content 日志内容
	 * @return void
	 */
	private void log(String content)
	{
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);		
	}
	
	
	/**
	 * 获取银行所有费用标准
	 * @param bankID 银行ID
	 * @return Hashtable key：费用名称 value：费用代码
	 */
	private Hashtable<String,String> getBankFeeList(String bankID)
	{
		Hashtable<String,String> allFee = new Hashtable<String,String>();
		try 
		{
			Vector<DicValue> dicList = DAO.getDicList("where type=3 and bankid='"+bankID+"'");
			for (int i = 0; i < dicList.size(); i++) 
			{
				DicValue dVal = (DicValue)dicList.get(i);
				allFee.put(dVal.name, dVal.value);
			}
		}
		catch(Exception e) 
		{
			log("银行所有费用标准Exception:"+e);
			e.printStackTrace();
		}
		finally
		{

		}
		return allFee;
	}
	
	/**
	 * 获取交易商所有费用标准
	 * @return Hashtable key：费用名称 value：费用代码
	 */
	private Hashtable<String,String> getFeeList()
	{
		Hashtable<String,String> allFee = new Hashtable<String,String>();
		try 
		{
			Vector<DicValue> dicList = DAO.getDicList("where type=3 and ( bankid IS  NULL or length(bankid)=0 ) ");
			for (int i = 0; i < dicList.size(); i++) 
			{
				DicValue dVal = (DicValue)dicList.get(i);
				allFee.put(dVal.name, dVal.value);
			}
		}
		catch(Exception e) 
		{
			log("获取交易商所有费用标准Exception:"+e);
			e.printStackTrace();
		}
		finally
		{

		}
		return allFee;
	}
	
	/**
	 * 判断是否一个城市的账号
	 * @param bankID 银行ID
	 * @param account 银行账号
	 * @return true：同城账号 false：异地账号
	 */
	private boolean isTownsAccont(String bankID,String account)
	{
		boolean isTowns=false;
		try 
		{
			Vector<DicValue> dicList = DAO.getDicList("where type=2 and name='townsAccount' and bankid='"+bankID+"'");
			if(dicList.size()>0)
			{
				DicValue dVal = (DicValue)dicList.get(0);
				String townsAccount=dVal.value;//同城账户开头账号 以;分割
				if(townsAccount.length()>0)
				{
					String[] acountStart=townsAccount.split(";");
					for(int i=0;i<acountStart.length;i++)
					{
						if(account.startsWith(acountStart[i]))
						{
							isTowns=true;
							break;
						}
					}
				}	
			}
		}
		catch(Exception e) 
		{
			log("判断是否同一个城市的银行账号Exception:"+e);
			e.printStackTrace();
		}
		finally
		{

		}
		return isTowns;
	}
	
	/**
	 * 根据费用标准计算费用
	 * @param feeList 费用标准 
	 * @param money   发生金额
	 * @return		  费用金额
	 */
	private double calculateFee(Vector<FeeInfoVO> feeList,double money,double example)
	{
		//应收费用金额
		double result = 0;
		for(int i=0;i<feeList.size();i++)
		{
			FeeInfoVO feeInfoVO=feeList.get(i);
			if((money>feeInfoVO.downLimit && money<=feeInfoVO.upLimit)
					|| (money>feeInfoVO.downLimit && feeInfoVO.upLimit==-1))
			{
				if(feeInfoVO.tMode==0)//百分比
				{
					result=Arith.mul(example, feeInfoVO.rate);
				}
				else if(feeInfoVO.tMode==1)//绝对值
				{
					result=feeInfoVO.rate;
				}
				
				if(feeInfoVO.maxRateValue>0 && Arith.compareTo(result, feeInfoVO.maxRateValue) == 1) result = feeInfoVO.maxRateValue;
				if(Arith.compareTo(result, feeInfoVO.minRateValue) == -1) result = feeInfoVO.minRateValue;
				break;
			}
		}
		return result;
	}
	/**
	* 取得入金手续费
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param money 转账金额
	 * @param express 0：正常 1：加急
	 * @param account 银行账号
	 * @param conn 数据库连接
	 * @return double 手续费金额>=0有效；-1 数据库异常 -2 系统异常
	 * （规则：如果设置了交易商的入金手续费则按照交易商入金手续费标准收取 否则按照市场设置的银行入金手续费标准收取）
	 * @throws 
	 */
	protected double getInRate(String bankID, String firmID, double money,int express, String account, Connection conn)
	{
		//应收入金手续费
		double result = 0;
		try 
		{			
			Hashtable<String, String> fFee=getFeeList();
			Hashtable<String, String> bFee=getBankFeeList(bankID);
			if(bFee.containsKey("inRateFee"))//市场设置银行入金手续费
			{
				Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+bFee.get("inRateFee")+"' and userID='"+bankID+"'", conn);
				if(feeList!=null && feeList.size()>0)
				{
					result=calculateFee(feeList,money,money);
				}
			}
			if(fFee.containsKey("inRateFee"))//市场设置的交易商入金手续费
			{
				Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+fFee.get("inRateFee")+"' and userID='"+firmID+"'", conn);
				if(feeList!=null && feeList.size()>0)
				{
					result=calculateFee(feeList,money,money);
				}
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			log("取得入金手续费SQLException:"+e);
			result = -1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log("取得入金手续费Exception:"+e);
			result = -2;
		}
		finally
		{
		}
		return result;
	}
	
	/**
	* 取得出金手续费
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param money 转账金额
	 * @param express 0：正常 1：加急
	 * @param account 银行账号
	 * @param conn 数据库连接
	 * @return double 手续费金额>=0有效；-1 数据库异常 -2 系统异常
	 * @throws 
	 */
	protected double getOutRate(String bankID, String firmID, double money,int express,String account,Connection conn)
	{
		//应收出金手续费
		double result = 0;

		try 
		{			
			Hashtable<String, String> fFee=getFeeList();
			Hashtable<String, String> bFee=getBankFeeList(bankID);
			double outRateFee=0;//出金手续费 属于市场手续费（规则：如果设置了交易商的出金手续费则按照交易商出金手续费标准收取 否则按照市场设置的银行出金手续费标准收取）
			if(bFee.containsKey("outRateFee"))//银行出金手续费
			{
				Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+bFee.get("outRateFee")+"' and userID='"+bankID+"'", conn);
				if(feeList!=null && feeList.size()>0)
				{
					outRateFee=calculateFee(feeList,money,money);
				}
			}

			if(fFee.containsKey("outRateFee"))//交易商出金手续费
			{
				Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+fFee.get("outRateFee")+"' and userID='"+firmID+"'", conn);
				if(feeList!=null && feeList.size()>0)
				{
					outRateFee=calculateFee(feeList,money,money);
				}
			}
			
			result=outRateFee;
			/********市场代银行收取的费用********/
			if(isTownsAccont(bankID,account))//同城账号
			{
				if(bFee.containsKey("townsOutRateFee"))//同城出金手续费标准 属于银行手续费
				{
					Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+bFee.get("townsOutRateFee")+"' and userID='"+bankID+"'", conn);
					if(feeList!=null && feeList.size()>0)
					{
						result=Arith.add(result, calculateFee(feeList,money,money));
					}
				}
			}
			else
			{
				if(bFee.containsKey("difTransFee"))//异地汇划费 属于银行手续费
				{
					double difTransFee=0;//异地汇划费
					Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+bFee.get("difTransFee")+"' and userID='"+bankID+"'", conn);
					if(feeList!=null && feeList.size()>0)
					{
						difTransFee=calculateFee(feeList,money,money);
						result=Arith.add(result, difTransFee);
					}
					
					if(express==1)//加急处理
					{
						if(bFee.containsKey("expressFee"))//加急费用 在异地汇划费基础上加收
						{
							feeList=DAO.getFeeInfoList(" where type='"+bFee.get("expressFee")+"' and userID='"+bankID+"'", conn);
							if(feeList!=null && feeList.size()>0)
							{
								result=Arith.add(result, calculateFee(feeList,money,difTransFee));
							}
						}
					}
					
				}
				
				if(bFee.containsKey("difOutRateFee"))//异地手续费 属于银行手续费
				{
					Vector<FeeInfoVO> feeList=DAO.getFeeInfoList(" where type='"+bFee.get("difOutRateFee")+"' and userID='"+bankID+"'", conn);
					if(feeList!=null && feeList.size()>0)
					{
						result=Arith.add(result, calculateFee(feeList,money,money));
					}
				}
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			log("取得出金手续费SQLException:"+e);
			result = -1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log("取得出金手续费Exception:"+e);
			result = -2;
		}
		finally
		{
		}
		return result;
	}
}

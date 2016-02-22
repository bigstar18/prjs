package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferMoneyVO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * <p>Title: 划转交易手续费</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class TransMoneyTradeRate extends TransMoney {

	/**
     * 划转交易手续费
     * @param bankID 银行代码
     * @param money 金额
     * @param operator 资金划转发起端
     * @param processor 处理器对象
     * @return long 银行接口业务流水号,<0表示操作失败
     * @throws 
     */
	protected  long tranfer(String bankID , double money , String operator, String firmID, CapitalProcessor processor)
	{		
		//操作结果
		long result = -1;
		//数据库连接
		Connection conn = null;
		//数据库操作类
		BankDAO dao = processor.getBankDAO();
		//资金划转流水号
		long capitalID = 0;
		//适配器对象
		BankAdapterRMI adapter;
		//系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		
		try 
		{
			//记录资金流水
			try 
			{
				conn = dao.getConnection();
				
				//取得市场业务流水号
				result = dao.getActionID(conn);
				
				//启动数据库事务
				conn.setAutoCommit(false);
				
				//记录转帐模块资金流水
				CapitalValue cVal = new CapitalValue();
				cVal.actionID = result;
				cVal.bankID = bankID;
				//cVal.bankTime = bankTime;
				cVal.creditID = "";
				cVal.debitID = "";
				cVal.firmID = firmID;
				cVal.funID = "";
				cVal.money = money;
				cVal.note = "";
				cVal.oprcode = "";
				cVal.status = 0;
				cVal.type = processor.getTransType();
				
				capitalID = dao.addCapitalInfo(cVal, conn);//资金划转流水			
				
				conn.commit();
			} 
			catch(SQLException e) 
			{
				result = -4;
				e.printStackTrace();
				conn.rollback();
			}
			finally
			{
				conn.setAutoCommit(true);
				dao.closeStatement(null, null, conn);
			}
			
			//记录资金流水成功则调用适配器接口通知银行转账
			if(result > 0)
			{
				//取得适配器对象
				adapter = processor.getAdapter(bankID);
				
				TransferMoneyVO transferMoneyVO=new TransferMoneyVO(bankID,firmID, money, null, null, -1);
				//调用适配器接口通知银行进行资金划转
				ReturnValue rVal = adapter.transferMoney(transferMoneyVO);
				
				//如果银行端操作成功，则记录交易系统凭证
				if(rVal.result == 0)
				{
					try 
					{
						conn = processor.getBankDAO().getConnection();
						
						//启动数据库事务
						conn.setAutoCommit(false);
						
						//调用交易系统接口
						//CapitalProcessor.dataProcess.updateFundsFull(firmID, CapitalProcessor.dataProcess.getFEESUMMARY()+"","", -1*money,"100",conn);
						conn.commit();
					} 
					catch(SQLException e) 
					{
						e.printStackTrace();
						conn.rollback();
						result = -4;
					}
					finally
					{
						conn.setAutoCommit(true);
						dao.closeStatement(null, null, conn);
					}
					
					//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
					if(result == -4)
					{
						dao.modCapitalInfoStatus(capitalID,"", 1,curTime, conn);
					}
				}
			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			result = -5;
		}
		finally
		{
			
		}
		
		return result;
	}
}

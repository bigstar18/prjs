package gnnt.trade.bank;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;
/**
 * 处理与中远期有关的数据
 * @author 薛计涛
 *
 */
public class DataProcess 
{
	/**入金财务摘要号*/
	private  int INSUMMARY;//入金财务摘要号
	/**出金财务摘要号*/
	private  int OUTSUMMARY;//出金财务摘要号
	/**收出入金手续费财务摘要号*/
	private  int FEESUMMARY;//收出入金手续费财务摘要号
	private  int CHSUMMARY;//冲正财务摘要号
	private  Hashtable<String,String> BANKSUB = new Hashtable<String,String>();//银行资金科目
    /**
     * 银行资金科目
     * @return 银行资金科目
     */
	protected Hashtable<String, String> getBANKSUB() {
		return BANKSUB;
	}
	/**
     * 收出入金手续费财务摘要号
     * @return 收出入金手续费财务摘要号
     */
	protected int getFEESUMMARY() {
		return FEESUMMARY;
	}
	/**
     * 入金财务摘要号
     * @return 入金财务摘要号
     */
	protected int getINSUMMARY() {
		return INSUMMARY;
	}
	/**
     * 出金财务摘要号
     * @return 出金财务摘要号
     */
	protected int getOUTSUMMARY() {
		return OUTSUMMARY;
	}
	/**
	 * 冲正财务摘要号
	 */
	protected int getCHSUMMARY(){
		return CHSUMMARY;
	}
	/**
	 * 加载科目与摘要信息配置信息
	 * @return int 0成功 -1失败
	 */
	protected int loadConfig()
	{
		
		int result = 0;

		try 
		{
			//取得数据库访问对象
			BankDAO DAO= BankDAOFactory.getDAO();
			/**************加载摘要信息******************/
			Vector<DicValue> dicList = DAO.getDicList("where type=0");
			for (int i = 0; i < dicList.size(); i++) 
			{
				DicValue dVal = (DicValue)dicList.get(i);
				if(dVal.name.equals("insummary"))
				{
					INSUMMARY = Integer.parseInt(dVal.value);
				}
				else if(dVal.name.equals("outsummary"))
				{
					OUTSUMMARY = Integer.parseInt(dVal.value);
				}
				else if(dVal.name.equals("feesummary"))
				{
					FEESUMMARY = Integer.parseInt(dVal.value);
				}
				else if(dVal.name.equalsIgnoreCase("chsummary"))
				{
					CHSUMMARY = Integer.parseInt(dVal.value);
				}
			}
			/******************************************/

			/**************加载科目信息******************/
			dicList = DAO.getDicList("where type=1");
			for (int i = 0; i < dicList.size(); i++) 
			{
				DicValue dVal = (DicValue)dicList.get(i);		
				if(dVal.name.equals("banksub"))
				{
					BANKSUB.put(dVal.bankID, dVal.value);
				}
			}
			/******************************************/
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			log("加载配置信息异常："+e);
			result = -1;
		}
		finally
		{
		}
		return result;
	}
	/**
	 * 获取可用资金
	 * @param firmID 交易商代码
	 * @param conn   数据库连接
	 * @return       交易商可用余额
	 * @throws SQLException
	 */
	protected double getRealFunds(String firmID,Connection conn) throws SQLException
	{
		double result=0;
		CallableStatement proc = null;
		try 
		{
			proc = conn.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");

	        proc.setString(2, firmID);

	        proc.setInt(3, 1);

	        proc.registerOutParameter(1, Types.DOUBLE);

	        proc.executeQuery();

	        result = proc.getDouble(1); 
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(proc != null)
			{
				try 
				{
					proc.close();
					proc = null;
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}		
			}
		}
		this.log("交易商["+firmID+"]减去浮盈前可用资金["+result+"]");
		if("true".equalsIgnoreCase(Tool.getConfig("fuYing"))){//如果市场记录浮盈
			try{
				BankDAO DAO= BankDAOFactory.getDAO();
				Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[]{firmID});
				if(floatingloss != null && floatingloss.size()>0){
					for(FirmBalanceValue tp : floatingloss){
						if(tp != null && firmID.equals(tp.firmId)){
							if(tp.floatingloss>0){
								result = result-tp.floatingloss;
								this.log("交易商["+firmID+"]当前浮盈["+tp.floatingloss+"]");
							}
							break;
						}
					}
				}
			}catch(SQLException e){
				throw e;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		this.log("交易商["+firmID+"]减去浮盈后可用资金["+result+"]");
		return result;
	}
	
	/**
	 * 更新冻结资金
	 * @param firmID 交易商代码
	 * @param money  发生额（正值增加，负值减少）
	 * @param conn
	 * @return       冻结资金余额
	 * @throws SQLException
	 */
	protected double updateFrozenFunds(String firmID,double money,Connection conn) throws SQLException
	{
		double result=0;
		CallableStatement proc = null;
		try 
		{
			proc = conn.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");

            proc.setString(2, firmID);

            proc.setDouble(3, money);

            proc.setString(4, "1");

            proc.registerOutParameter(1, Types.DOUBLE);

            proc.executeUpdate();

	        result = proc.getDouble(1); 
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(proc != null)
			{
				try 
				{
					proc.close();
					proc = null;
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}		
			}
		}
		return result;
	}
	
	/**
	 * 更新资金
	 * @param firmID 交易商代码
	 * @param summary  业务代码
	 * @param subject  科目代码
	 * @param money    发生金额
	 * @param conn
	 * @return         资金余额
	 * @throws SQLException
	 */
	protected double updateFundsFull(String firmID,String summary,String subject,double money,long actionID,Connection conn) throws SQLException
	{System.out.println("firmID["+firmID+"]summary["+summary+"]subject["+subject+"]money["+money+"]actionID["+actionID+"]");
		double result=0;
		CallableStatement proc = null;
		try 
		{
			proc = conn.prepareCall("{ ?=call  FN_F_UpdateFundsFull(?,?,?,?,?,null,null) }");

	        proc.setString(2, firmID);

	        proc.setString(3, summary);
	        
	        proc.setDouble(4, money);
	        
	        proc.setString(5, actionID+"");
	        
	        proc.setString(6, subject);
	        
	        proc.registerOutParameter(1, Types.DOUBLE);

	        proc.executeQuery();

	        result = proc.getDouble(1); 
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(proc != null)
			{
				try 
				{
					proc.close();
					proc = null;
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}		
			}
		}
		return result;
	}
	
	/**
	 * 取得交易商交易系统资金明细
	 * @param Date 结算日期
	 * @return  Hashtable key 交易商代码；value 交易商资金属性集合（key 资金属性名称；value 资金属性值）
	 */
	protected Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date)
	{
		BankDAO DAO = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement state = null;
		ResultSet rs1 = null;
		PreparedStatement state1 = null;
		Hashtable<String, Hashtable<String, String>> result = new Hashtable<String, Hashtable<String, String>>();
		
		try 
		{
			//取得数据库访问对象
			DAO = BankDAOFactory.getDAO();
			conn = DAO.getConnection();
			
			String sql = "select distinct(firmid) from  F_FirmBalance where b_date=to_date('"+ b_date +"','yyyy-mm-dd')";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next())
			{
				String firmid = rs.getString(1);
				sql = "select t1.code, t2.name ,t1.value " +
						"from  F_ClientLedger t1,f_ledgerfield t2 where t1.b_date=to_date('"+ b_date +"','yyyy-mm-dd')" +
								" and t1.code=t2.code and firmid='"+ firmid +"'";
				state1 = conn.prepareStatement(sql);
				rs1 = state1.executeQuery();
				Hashtable<String, String> values = new Hashtable<String, String>();
				while(rs1.next())
				{
					values.put(rs1.getString(1), rs1.getString(3));
				}
				result.put(firmid, values);
				rs1.close();
				rs1 = null;
				state1.close();
				state1 = null;
			}
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DAO.closeStatement(rs, state, conn);
		}
		return result;
	}		
	
	/**
	 * 从交易系统同步交易商
	 * @return  void
	 */
	protected void synchronizedFirm()
	{
		BankDAO DAO = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement state = null;
		ResultSet rs1 = null;
		PreparedStatement state1 = null;
		
		try 
		{
			//取得数据库访问对象
			DAO = BankDAOFactory.getDAO();
			conn = DAO.getConnection();
			
			String sql = "select firmid,name from m_firm";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next())
			{
				String firmid = rs.getString(1);
				String name = rs.getString(2);
				
				sql = "select count(firmid) from f_b_firmuser where firmid='"+ firmid +"'";
				state1 = conn.prepareStatement(sql);
				rs1 = state1.executeQuery();
				int cnt = 0;
				while(rs1.next())
				{
					cnt = rs1.getInt(1);
				}
				rs1.close();
				rs1 = null;
				state1.close();
				state1 = null;
				
				if(cnt == 0)
				{
					sql = "insert into f_b_firmuser" +
							"(firmid, name, status, registerdate)" +
							"values" +
							"(?, ?, 1, sysdate)";
					state1 = conn.prepareStatement(sql);
					state1.setString(1, firmid);
					state1.setString(2, name);
					state1.executeUpdate();
					
					state1.close();
					state1 = null;
				}
			}
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DAO.closeStatement(rs, state, conn);
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
	 * 跨行汇拨更新市场自有资金
	 */
	public long updateMarketMoney(String auditor, double money,Connection conn) throws SQLException
	{
		log("跨行汇拨更新市场自有资金，操作人员["+auditor+"],金额["+money+"] "+ new Date());
		CallableStatement proc = null;
		PreparedStatement state = null;
		long result=0;
		try{
			
			proc = conn.prepareCall("{ ?=call  FN_F_CreateVoucher('106','结转交易手续费','20302','204',?,null,?) }");
			proc.setDouble(2, money);
	        proc.setString(3, auditor);
	        proc.registerOutParameter(1, Types.INTEGER);
	        proc.executeQuery();
	        result = proc.getLong(1); 
	        
	        if(result>0){
	        	log("跨行汇拨更新市场自有资金-添加凭证成功");
		        String sql=" update F_Voucher  set auditor=?,audittime=sysdate,status='audited' "+
		         "where voucherno=? ";
		        state = conn.prepareStatement(sql);
				state.setString(1, auditor);
				state.setLong(2, result);
				state.executeUpdate();
				log("跨行汇拨更新市场自有资金-更新资金成功");
	        }else{
	        	log("跨行汇拨更新市场自有资金-添加凭证失败");
	        }
	        
		} catch(SQLException e) 
		{
			log("跨行汇拨更新市场自有资金-sql异常");
			result=-1;
			e.printStackTrace();
			throw e;
		}
		catch(Exception e) 
		{
			log("跨行汇拨更新市场自有资金-异常");
			result=-1;
			e.printStackTrace();
		}
		finally
		{
			if(proc != null)
			{
				try 
				{
					proc.close();
					proc = null;
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}		
			}
			
			if(state!=null){
				try
				{
					state.close();
					state=null;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}

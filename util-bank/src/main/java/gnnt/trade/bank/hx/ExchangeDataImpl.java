package gnnt.trade.bank.hx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 资金清算服务类
 * @author Administrator
 *
 */
public class ExchangeDataImpl implements ExchangeData{

	/**
	 * 得到清算文件
	 * @param Date
	 * @return
	 */
	public List<QS> getQS(String bankId,String date,Connection conn){
		
		List<QS> rst = new ArrayList<QS>();
		
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		QS val = null;
		try
		{
			sql="select '0' flag,f.firmid firm_id ,'1' dcFlag,sum(nvl(value,0)) amt,'借' remarks from( "+
"select firmid,sum(value)value from f_clientledger "+
"where code in ('Payout','Payout_Z','Payout_V','TradeFee','TradeFee_Z','TradeFee_V','SettleFee') "+
"and b_date=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid "+
"union "+
"select firmid,sum(abs(value))value from f_clientledger "+
"where ((code ='TradePL' and value<0) or (code='SettlePL' and value<0)) "+
"and b_date=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid "+
"union "+
"select th1.firmid firmid ,(case when th1.floatingloss<nvl(th2.floatingloss,0) then (nvl(th2.floatingloss,0)-th1.floatingloss)/1.17 else 0 end) value from "+
"(select firmid,sum(floatingloss)floatingloss from t_h_firmholdsum where cleardate=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid)th1, "+
"(select firmid,sum(floatingloss)floatingloss from t_h_firmholdsum where cleardate=(select max(cleardate) from t_h_firmholdsum where cleardate<to_date('" + date + "','yyyy-mm-dd')) "+
"group by firmid)th2 "+
"where th1.firmid=th2.firmid(+)  "+
")a,f_b_firmidandaccount f "+
"where f.firmid=a.firmid(+) and f.bankid='"+bankId+"' "+
"group by f.firmid "+
"union "+
"select '0' flag,f.firmid firm_id ,'2' dcFlag,sum(nvl(value,0)) amt,'贷' remarks from(  "+
"select firmid,sum(value)value from f_clientledger "+
"where code in ('Income','Income_Z','Income_V','OtherItem')  "+
"and b_date=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid "+
"union  "+
"select firmid,sum(abs(value))value from f_clientledger "+
"where ((code ='TradePL' and value>0) or (code='SettlePL' and value>0)) "+
"and b_date=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid "+
"union "+
"select th1.firmid firmid ,(case when th1.floatingloss>nvl(th2.floatingloss,0) then abs(nvl(th2.floatingloss,0)-th1.floatingloss)/1.17 else 0 end) value from "+
"(select firmid,sum(floatingloss)floatingloss from t_h_firmholdsum where cleardate=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid)th1, "+
"(select firmid,sum(floatingloss)floatingloss from t_h_firmholdsum where cleardate=(select max(cleardate) from t_h_firmholdsum where cleardate<to_date('" + date + "','yyyy-mm-dd')) "+
"group by firmid)th2 "+
"where th1.firmid=th2.firmid(+)  "+
")a,f_b_firmidandaccount f "+
"where f.firmid=a.firmid(+) and f.bankid='"+bankId+"' "+
"group by f.firmid "+
"union "+
"select '1' flag,fb.firmid firm_id,'' dcFlag,nvl(RuntimeMargin,0)+(nvl(RuntimeFL,0)+nvl(floatingloss,0)+nvl(RuntimeFL,0)*0.17)/1.17 amt,'' remarks "+
"from f_b_firmidandaccount fb,t_h_firm th, "+
"(select firmid,sum(floatingloss)floatingloss from t_h_firmholdsum where cleardate=to_date('" + date + "','yyyy-mm-dd') "+
"group by firmid )t "+
"where fb.firmid=th.firmid(+) and fb.firmid=t.firmid(+) and th.cleardate=to_date('" + date + "','yyyy-mm-dd') and fb.bankid='"+bankId+"' "+
"union select '0' flag,'AAAAAA' firm_id,'1' dcFlag, nvl(sum(amount),0)amt,'借' remarks from f_accountbook where summaryno in ('208','301','406','409') and b_date=to_date('" + date + "','yyyy-mm-dd') "+
"union select '0' flag,'AAAAAA' firm_id,'2' dcFlag, nvl(sum(amount),0)amt,'贷' remarks from f_accountbook where summaryno in ('209','305','408') and b_date=to_date('" + date + "','yyyy-mm-dd') "+
"union select '1' flag,'AAAAAA' firm_id,'' dcFlag, 0 amt,'' remarks from dual "+
"order by firm_id";
			System.out.println("sql="+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next())
			{
				val = new QS();
				if(rs.getString("firm_id")!=null && !rs.getString("firm_id").trim().startsWith("111")){
					val.flag = rs.getInt("flag");
					val.firmId = rs.getString("firm_id");
					val.direction = rs.getInt("dcFlag");
					val.amt = rs.getDouble("amt");
					val.abstct = rs.getString("remarks");
					rst.add(val);
					System.out.println("flag="+val.flag + "  " + "firmId="+val.firmId + "  " + "amt="+val.amt);
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();			
		}
		finally
		{
			closeStatement(rs,state,conn);
		}
		
		return rst;
	}
	/**
	 * 得到对账文件
	 * @param Date
	 * @return
	 */
	public List<DZ> getDZ(String bankId,String date,Connection conn){
		
		List<DZ> rst = new ArrayList<DZ>();
		
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		DZ val = null;
		try
		{
			sql="select fb.firmid firm_id,nvl(todaybalance,0)+nvl(RuntimeMargin,0)+nvl(RuntimeFL,0)+nvl(floatingloss,0)/1.17 fund1,nvl(todaybalance,0) fund2 "+
			  "from f_b_firmidandaccount fb,t_h_firm th,f_firmbalance ff, "+
			  "(select firmid,sum(floatingloss)floatingloss from t_h_firmholdsum where cleardate=to_date('" + date + "','yyyy-mm-dd') "+
			  "group by firmid )t "+
			  "where fb.firmid=th.firmid(+) and fb.firmid=t.firmid(+) and fb.firmid=ff.firmid(+)  "+
			  "and th.cleardate=ff.b_date and th.cleardate=to_date('" + date + "','yyyy-mm-dd') and fb.bankid='"+bankId+"' "+
			  "union "+
			  "select  'AAAAAA' firm_id,nvl(sum(case when summaryno in ('209','305','408') then amount else 0-amount end),0) fund1,nvl(sum(case when summaryno in ('209','305','408') then amount else 0 end),0) fund2   "+
			  "from f_accountbook where summaryno in ('208','301','406','409','209','305','408') and b_date<=to_date('" + date + "','yyyy-mm-dd') "+
			  "order by firm_id";
			System.out.println("sql="+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next())
			{
				if(rs.getString("firm_id")!=null && !rs.getString("firm_id").trim().startsWith("111")){
					val = new DZ();
					val.firmId = rs.getString("firm_id");
					val.amt = rs.getDouble("fund1");
					val.useAmt = rs.getDouble("fund2");
					rst.add(val);
					System.out.println("firmId="+val.firmId + "  " + "amt="+val.amt + "  " + "useAmt="+val.useAmt);
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();			
		}
		finally
		{
			closeStatement(rs,state,conn);
		}
		
		return rst;
	}
	/**
	 * 得到子账户文件
	 * @param Date
	 * @return
	 */
	public List<ZZH> getZZH(String bankId,String date,Connection conn){
		
		List<ZZH> rst = new ArrayList<ZZH>();
		
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		ZZH val = null;
		try
		{
			sql="select f.firmid firm_id,name,todaybalance fund1,'2' type "+
			"from f_firmbalance f,m_firm m,f_b_firmidandaccount fb "+
			"where f.firmid=m.firmid and f.firmid=fb.firmid and bankid='"+bankId+"' " +
			"and b_date=to_date('" + date + "','yyyy-mm-dd') order by f.firmid";
			
			System.out.println("sql="+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next())
			{
				if(rs.getString("firm_id")!=null && !rs.getString("firm_id").trim().startsWith("111")){
					val = new ZZH();
					val.firmId = rs.getString("firm_id");
					val.firmName = rs.getString("name");
					val.amt = rs.getDouble("fund1");
					val.type = rs.getInt("type");
					rst.add(val);
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();			
		}
		finally
		{
			closeStatement(rs,state,conn);
		}
		
		return rst;
	}
	
	/**
	   * 关闭数据库连接
	   * @param rs ResultSet
	   * @param state Statement
	   * @param conn Connection
	   */
	  private void closeStatement(ResultSet rs, Statement state,Connection conn) 
	  {
	    try {
	      if (rs != null)
	        rs.close();
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	    }

	    try {
	      if (state != null)
	        state.close();
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	    }
	    try {
	      if (conn != null)
	        conn.close();
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	    }
	  }
}

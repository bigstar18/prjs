package gnnt.trade.bank.fileouter;
import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.Tool;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Vector;
/**
 * 银行有流水市场没有的流水
 */
public class MarketNoCapitalOuter extends FileOutPut{
	public MarketNoCapitalOuter(OutputStream os){
		super(os);
	}
	public void output(String bankID,java.util.Date date,int[] pageinfo){
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try{
			conn = DAO.getConnection();
			sql = "select bank.bankName bankName,fbb.bankid bankID,fbf.firmid firmID,'"+Tool.fmtDate(date)+"' tradeDate " +
				",fbb.firmid contact,fbb.funid funID,fbb.comparedate tradeDate,fbb.money money,'' actionID,'市场无流水' result " +
				",case when fbf.bankid='005' and fbf.ACCOUNT1 is null then '-' when fbf.bankid='005' then fbf.ACCOUNT1 when fbf.ACCOUNT is null then '-' else fbf.ACCOUNT end ACCOUNT " +
				//",case when fbf.account is null then '-' when fbb.bankid='005' and fbf.account='999999999999999999' then '-' else fbf.account end account" +
				",case when fbb.type="+ProcConstants.inMoneyType+" then '入金' else '出金' end type " +
				"from f_b_bankcompareinfo fbb,f_b_firmidandaccount fbf,f_b_banks bank where fbb.firmid=fbf.contact(+) " +
				" and fbb.bankid=fbf.bankid(+) and fbb.bankid=bank.bankid and fbb.status="+ProcConstants.statusSuccess+" " +
				" and not exists (select funid from f_b_capitalinfo fbc where fbb.funid=fbc.funid and fbb.firmid=fbc.contact " +
				" and fbb.bankid=fbc.bankid and fbb.type=fbc.type and fbb.money=fbc.money " +
				" and trunc(fbb.comparedate)=trunc(fbc.banktime) and fbc.status="+ProcConstants.statusSuccess+") " +
				" and fbb.bankid='"+bankID+"' and trunc(fbb.comparedate)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd')";
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			puter.outputMsg(getHead(), getTypes(), rs, os);
		}catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(DAO != null){
				DAO.closeStatement(rs, state, conn);
			}
		}
	}
	private LinkedHashMap<String,Integer> getTypes(){
		LinkedHashMap<String,Integer> result = new LinkedHashMap<String,Integer>();
		result.put("bankName", Types.CHAR);
		result.put("contact", Types.CHAR);
		result.put("account", Types.CHAR);
		result.put("funID", Types.CHAR);
		result.put("type", Types.CHAR);
		result.put("money", Types.DOUBLE);
		result.put("result", Types.CHAR);
		//result.put("tradeDate", Types.CHAR);
		return result;
	}
	/**
	 * 获取转账流水导出表头
	 * @return Vector<String>
	 */
	private Vector<String> getHead(){
		Vector<String> result = new Vector<String>();
		result.add("银行名称");
		result.add("交易账号");
		result.add("银行账号");
		result.add("银行流水号");
		result.add("转账类型");
		result.add("转账金额");
		result.add("对账结果");
		//result.add("日期");
		return result;
	}
}

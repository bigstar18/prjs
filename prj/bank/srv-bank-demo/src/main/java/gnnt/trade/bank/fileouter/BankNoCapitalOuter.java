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
 * 市场有流水银行没有的流水
 */
public class BankNoCapitalOuter extends FileOutPut{
	public BankNoCapitalOuter(OutputStream os){
		super(os);
	}
	public void output(String bankID,java.util.Date date,int[] pageinfo){
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try{
			conn = DAO.getConnection();
			sql = "select fbb.bankName bankName,fbc.firmid firmID,fbc.contact contact,fbc.money money " +
				",case when fbc.type="+ProcConstants.inMoneyType+" then '入金' else '出金' end type " +
				",case when fbc.funid is null then '-' else fbc.funid end funID " +
				",case when fbf.bankid='005' and fbf.ACCOUNT1 is null then '-' when fbf.bankid='005' then fbf.ACCOUNT1 when fbf.ACCOUNT is null then '-' else fbf.ACCOUNT end ACCOUNT " +
				//",case when fbf.account is null then '-' when '005'='"+bankID+"' and fbf.account='999999999999999999' then '-' else fbf.account end account" +
				",fbc.actionid actionID,fbc.bankid bankID,'"+Tool.fmtDate(date)+"' tradeDate " +
				",case when fbc.status="+ProcConstants.statusSuccess+" then '成功' else '处理中' end status,'银行无流水' result " +
				" from f_b_capitalinfo fbc,f_b_firmidandaccount fbf,f_b_banks fbb" +
				" where fbc.type in ("+ProcConstants.inMoneyType+","+ProcConstants.outMoneyType+") and fbc.bankid=fbb.bankid(+) and fbc.firmid = fbf.firmid(+) and fbc.bankid=fbf.bankid(+) and fbc.status not in ("+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") " +
				" and not exists (select funid from f_b_bankcompareinfo fbb where fbc.funid=fbb.funid " +
				" and fbc.contact=fbb.firmid and fbc.money=fbb.money and trunc(fbc.banktime)=trunc(fbb.comparedate) " +
				" and fbc.bankid=fbb.bankid and fbc.type=fbb.type and fbb.status="+ProcConstants.statusSuccess+") " +
				" and fbc.bankid='"+bankID+"' and trunc(createtime)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd')";
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
		result.put("funID", Types.CHAR);
		result.put("actionID", Types.INTEGER);
		result.put("contact", Types.CHAR);
		result.put("account", Types.CHAR);
		result.put("type", Types.CHAR);
		result.put("money", Types.DOUBLE);
		result.put("status", Types.CHAR);
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
		result.add("银行流水号");
		result.add("交易所流水号");
		result.add("交易账号");
		result.add("银行账号");
		result.add("转账类型");
		result.add("转账金额");
		result.add("转账状态");
		result.add("对账结果");
		//result.add("日期");
		return result;
	}
}

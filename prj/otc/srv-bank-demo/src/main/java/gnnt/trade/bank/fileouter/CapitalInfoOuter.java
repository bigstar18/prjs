package gnnt.trade.bank.fileouter;
import gnnt.trade.bank.dao.page.PageQuery;

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
 * 出入金流水导出类
 */
public class CapitalInfoOuter extends FileOutPut{
	public CapitalInfoOuter(OutputStream os){
		super(os);
	}
	public CapitalInfoOuter(OutputStream os,String operator){
		super(os,operator);
	}
	public void output(String filter,int[] pageinfo){
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			conn = DAO.getConnection();
			
			if(filter != null && filter.indexOf("organizationno")>=0){
				sql = "select fbc.CREATETIME CREATETIME,fbc.ACTIONID ACTIONID,fbb.bankname bankName,fbf.contact contact,fbf.account account,fbc.MONEY MONEY,fbc.NOTE NOTE " +
				",case when fbc.FUNID is null then '-' else fbc.FUNID end FUNID " +
				",case when mf.firmtype='C' then '-' else fbc.FIRMID end FIRMID " +
				",case when mf.firmtype='C' then '客户' when mf.firmtype='M' then '会员' else '特别会员' end firmtype " +
				",case when fbf.accountName is null then fbf.accountName else fbf.accountName end accountName " +
				",case when fbc.TYPE=0 then '入金' when fbc.TYPE=1 then '出金' when fbc.TYPE=4 then '入金冲正' when fbc.TYPE=5 then '出金冲正' else '其他' end TYPE " +
				",case when fbc.STATUS=0 then '成功' when fbc.STATUS=1 then '失败' when fbc.STATUS=9 then '被冲正' else '处理中' end STATUS " +
				" from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,v_customerrelateorganization mc " +
				" where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid "+filter;
			}else if(filter != null && filter.indexOf("memberno")>=0){
				sql = "select fbc.CREATETIME CREATETIME,fbc.ACTIONID ACTIONID,fbb.bankname bankName,fbf.contact contact,fbf.account account,fbc.MONEY MONEY,fbc.NOTE NOTE " +
				",case when fbc.FUNID is null then '-' else fbc.FUNID end FUNID " +
				",case when mf.firmtype='C' then '-' else fbc.FIRMID end FIRMID " +
				",case when mf.firmtype='C' then '客户' when mf.firmtype='M' then '会员' else '特别会员' end firmtype " +
				",case when fbf.accountName is null then fbf.accountName else fbf.accountName end accountName " +
				",case when fbc.TYPE=0 then '入金' when fbc.TYPE=1 then '出金' when fbc.TYPE=4 then '入金冲正' when fbc.TYPE=5 then '出金冲正' else '其他' end TYPE " +
				",case when fbc.STATUS=0 then '成功' when fbc.STATUS=1 then '失败' when fbc.STATUS=9 then '被冲正' else '处理中' end STATUS " +
				" from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,m_customerinfo mc " +
				" where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid "+filter;
			}else{
				sql = "select fbc.CREATETIME CREATETIME,fbc.ACTIONID ACTIONID,fbb.bankname bankName,fbf.contact contact,fbf.account account,fbc.MONEY MONEY,fbc.NOTE NOTE " +
				",case when fbc.FUNID is null then '-' else fbc.FUNID end FUNID " +
				",case when mf.firmtype='C' then '-' else fbc.FIRMID end FIRMID " +
				",case when mf.firmtype='C' then '客户' when mf.firmtype='M' then '会员' else '特别会员' end firmtype " +
				",case when fbf.accountName is null then fbf.accountName else fbf.accountName end accountName " +
				",case when fbc.TYPE=0 then '入金' when fbc.TYPE=1 then '出金' when fbc.TYPE=4 then '入金冲正' when fbc.TYPE=5 then '出金冲正' else '其他' end TYPE " +
				",case when fbc.STATUS=0 then '成功' when fbc.STATUS=1 then '失败' when fbc.STATUS=9 then '被冲正' else '处理中' end STATUS " +
				" from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb " +
				" where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) "+filter;
			}
			
			
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			if("M".equalsIgnoreCase(operator)){
				puter.outputMsg(getHeadM(), getTypesM(), rs, os);
			}else{
				puter.outputMsg(getHeadE(), getTypesE(), rs, os);
			}
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
	private LinkedHashMap<String,Integer> getTypesE(){
		LinkedHashMap<String,Integer> result = new LinkedHashMap<String,Integer>();
		result.put("CREATETIME", Types.TIMESTAMP);
		result.put("ACTIONID", Types.INTEGER);
		result.put("FUNID", Types.CHAR);
		result.put("bankName", Types.CHAR);
		result.put("contact", Types.CHAR);
		result.put("firmType", Types.CHAR);
		result.put("accountName", Types.CHAR);
		result.put("FIRMID", Types.CHAR);
		result.put("MONEY", Types.DOUBLE);
		result.put("TYPE", Types.CHAR);
		result.put("STATUS", Types.CHAR);
		result.put("NOTE", Types.CHAR);
		return result;
	}
	private Vector<String> getHeadE(){
		Vector<String> result = new Vector<String>();
		result.add("时间");
		result.add("交易所流水号");
		result.add("银行流水号");
		result.add("银行名称");
		result.add("交易账号");
		result.add("账号类型");
		result.add("账号名称");
		result.add("会员编号");
		result.add("转账金额");
		result.add("转账类型");
		result.add("状态");
		result.add("备注");
		return result;
	}
	private LinkedHashMap<String,Integer> getTypesM(){
		LinkedHashMap<String,Integer> result = new LinkedHashMap<String,Integer>();
		result.put("CREATETIME", Types.TIMESTAMP);
		result.put("ACTIONID", Types.INTEGER);
		result.put("FUNID", Types.CHAR);
		result.put("bankName", Types.CHAR);
		result.put("accountName", Types.CHAR);
		result.put("contact", Types.CHAR);
		result.put("MONEY", Types.DOUBLE);
		result.put("TYPE", Types.CHAR);
		result.put("STATUS", Types.CHAR);
		result.put("NOTE", Types.CHAR);
		return result;
	}
	private Vector<String> getHeadM(){
		Vector<String> result = new Vector<String>();
		result.add("时间");
		result.add("交易所流水号");
		result.add("银行流水号");
		result.add("银行");
		result.add("账号名称");
		result.add("交易账号");
		result.add("转账金额");
		result.add("转账类型");
		result.add("处理状态");
		result.add("备注");
		return result;
	}
}

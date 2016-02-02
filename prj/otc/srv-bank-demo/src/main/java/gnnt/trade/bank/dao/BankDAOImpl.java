package gnnt.trade.bank.dao;
import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalMoneyVO;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ClearingStatusVO;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CompareSumMoney;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmFundsBankValue;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmUserValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.SystemStatusVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
/**
 * <p>Title: 数据库访问对象实现类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 */
public class BankDAOImpl extends BankDAO{
	/**
	 * 构造函数
	 */
	public BankDAOImpl() throws Exception {
		super();
	}
	/**
	 * 写log日志
	 * @param string
	 */
	private void log(String string) {
		Logger plog = Logger.getLogger(ProcConstants.daoLog);
		plog.debug(string);
	}
	public long getActionID() throws SQLException,ClassNotFoundException {
		Connection conn = null;
		long id = -168;
		try {
			conn = getConnection();
			id = getActionID(conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(null,null,conn);
		}
		return id;
	}
	public long getActionID(Connection conn) throws SQLException {
		log("===>>>取得市场业务流水号   getActionID  ");
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -168;
		try {			
			sql = "select seq_F_B_action.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				id = rs.getLong(1);
			}								
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return id;
	}
	public SystemStatusVO getSystemStatus() throws SQLException,ClassNotFoundException{
		Connection conn = null;
		SystemStatusVO result = null;
		try{
			conn = this.getConnection();
			result = getSystemStatus(conn);
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			closeStatement(null,null,conn);
		}
		return result;
	}
	public SystemStatusVO getSystemStatus(Connection conn) throws SQLException{
		log("获取交易系统当前状态信息");
		SystemStatusVO result = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			sql = "select * from t_systemstatus ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()){
				result = new SystemStatusVO();
				result.endDate = rs.getDate("endDate");
				result.lastTradeDate = rs.getDate("lastTradeDate");
				result.nextTradeDate = rs.getDate("nextTradeDate");
				result.note = rs.getString("note");
				result.pauseType = rs.getString("pauseType");
				result.recoverTime = rs.getString("recoverTime");
				result.sectionID = rs.getInt("sectionID");
				result.status = rs.getInt("status");
				result.tradeDate = rs.getDate("tradeDate");
			}
		}catch(SQLException e){
			throw e;
		}finally{
			closeStatement(rs,state,null);
		}
		return result;
	}
	public int addClearing(ClearingStatusVO value)throws SQLException,ClassNotFoundException{
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try{
			sql = "insert into F_B_CLEARINGSTATUS (ID,bankID,tradeDate) values (SEQ_F_B_CLEARINGSTATUS.nextval,?,?)";
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, value.bankID);
			state.setDate(2, new java.sql.Date(value.tradeDate.getTime()));
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			closeStatement(null,state,conn);
		}
		return result;
	}
	public int modClearing(ClearingStatusVO value)throws SQLException,ClassNotFoundException{
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try{
			sql = "update F_B_CLEARINGSTATUS set generalTime=?,sendTime=?,generalStatus=?,sendStatus=? where ID=?";
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.setDate(1, value.generalTime == null ? null : new java.sql.Date(value.generalTime.getTime()));
			state.setDate(2, value.sendTime == null ? null : new java.sql.Date(value.sendTime.getTime()));
			state.setInt(3, value.generalStatus);
			state.setInt(4, value.sendStatus);
			state.setLong(5, value.ID);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			closeStatement(null,state,conn);
		}
		return result;
	}
	public ClearingStatusVO getMaxClearing(String bankID)throws SQLException,ClassNotFoundException{
		ClearingStatusVO result = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			sql = "select * from F_B_CLEARINGSTATUS where tradeDate in (select max(tradeDate) from F_B_CLEARINGSTATUS where bankID=?)";
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, bankID);
			rs = state.executeQuery();
			if(rs.next()){
				result = new ClearingStatusVO();
				result.bankID = rs.getString("bankID");
				result.createTime = rs.getDate("createTime");
				result.generalStatus = rs.getInt("generalStatus");
				result.generalTime = rs.getDate("generalTime");
				result.ID = rs.getLong("ID");
				result.sendStatus = rs.getInt("sendStatus");
				result.sendTime = rs.getDate("sendTime");
				result.tradeDate = rs.getDate("tradeDate");
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			closeStatement(rs,state,conn);
		}
		return result;
	}
	public Vector<DicValue> getDicList(String filter) throws SQLException,ClassNotFoundException {
		log("===>>>查询表   getDicList  "+new java.util.Date());
		Vector<DicValue> result = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = getDicList(filter,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public Vector<DicValue> getDicList(String filter,Connection conn) throws SQLException {
		log("===>>>查询表   getDicList filter["+filter+"] ");
		Vector<DicValue> result = new Vector<DicValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		DicValue value = null;
		try {			
			sql = "select * from F_B_dictionary where 1=1 "+ filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				value = new DicValue();
				value.bankID = rs.getString("bankID");
				value.id = rs.getLong("id");
				value.name = rs.getString("name");
				value.note = rs.getString("note");
				value.type = rs.getInt("type");
				value.value = rs.getString("value");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public Vector<BankValue> getBankList2(String filter,int[] pageinfo) throws SQLException,ClassNotFoundException{
		log("===>>>取得银行信息列表   getBankList  ");
		Vector<BankValue> result = new Vector<BankValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		try {
			conn = this.getConnection();
			sql = "select * from F_B_banks where 1=1 "+ filter;
//			state = conn.prepareStatement(sql);
//			rs = state.executeQuery();
			
			
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			
			
			
			while(rs.next()) {
				value = new BankValue();
				value.bankID = rs.getString("BANKID");
				value.bankName = rs.getString("BANKNAME");
				value.adapterClassname = rs.getString("ADAPTERCLASSNAME");
				value.validFlag = rs.getInt("VALIDFLAG");
				value.inMoneyFlag = rs.getInt("INMONEYFLAG");
				value.outMoneyFlag = rs.getInt("OUTMONEYFLAG");
				value.beginTime = rs.getString("BEGINTIME");
				value.endTime = rs.getString("ENDTIME");
				value.control = rs.getInt("CONTROL");
				value.bankType = rs.getInt("BANKTYPE");
				value.beleiveProcessor = rs.getString("BELEIVEPROCESSOR");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,conn);
		}
		return result;
	}
	public Vector<BankValue> getBankList(String filter) throws SQLException,ClassNotFoundException {
		Connection conn = null;
		Vector<BankValue> result = null;
		try {			
			conn = getConnection();
			result = getBankList(filter,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public Vector<BankValue> getBankList(String filter,Connection conn) throws SQLException {
		log("===>>>取得银行信息列表   getBankList  ");
		Vector<BankValue> result = new Vector<BankValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		try {			
			sql = "select * from F_B_banks where 1=1 "+ filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				value = new BankValue();
				value.bankID = rs.getString("BANKID");
				value.bankName = rs.getString("BANKNAME");
				value.adapterClassname = rs.getString("ADAPTERCLASSNAME");
				value.validFlag = rs.getInt("VALIDFLAG");
				value.inMoneyFlag = rs.getInt("INMONEYFLAG");
				value.outMoneyFlag = rs.getInt("OUTMONEYFLAG");
				value.beginTime = rs.getString("BEGINTIME");
				value.endTime = rs.getString("ENDTIME");
				value.control = rs.getInt("CONTROL");
				value.bankType = rs.getInt("BANKTYPE");
				value.beleiveProcessor = rs.getString("BELEIVEPROCESSOR");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public int modBank(BankValue val) throws SQLException,ClassNotFoundException {
		log("===>>>修改银行   modBank  ");
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		try {
			conn = getConnection();
			sql = "update F_B_banks set BANKNAME = ?,VALIDFLAG = ?," +
				"INMONEYFLAG = ?,OUTMONEYFLAG = ?,BEGINTIME=?,ENDTIME=?,CONTROL=? where BANKID = ?";
			int n=1;
			state = conn.prepareStatement(sql);
			state.setString(n++, val.bankName);
			state.setInt(n++, val.validFlag);
			state.setInt(n++, val.inMoneyFlag);
			state.setInt(n++, val.outMoneyFlag);
			state.setString(n++, val.beginTime);
			state.setString(n++, val.endTime);
			state.setInt(n++, val.control);
			state.setString(n++, val.bankID);
			result = state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,conn);
		}
		return result;
	}
	public int chBankValid(Vector<String> bankIDs,int value)throws SQLException,ClassNotFoundException{
		log("批量禁用、回复银行使用 chBankValid bankIDs["+bankIDs+"]value["+value+"]");
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql;
		String filter = getBankFilter(bankIDs);
		if(filter == null || filter.trim().length()<=0){
			return result;
		}
		try{
			conn = this.getConnection();
			sql = "update F_B_Banks set VALIDFLAG = ? where 1=1 "+filter;
			state = conn.prepareStatement(sql);
			state.setInt(1, value);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public int chBankInMoney(Vector<String> bankIDs,int value)throws SQLException,ClassNotFoundException{
		log("批量禁用、回复银行使用 chBankInMoney bankIDs["+bankIDs+"]value["+value+"]");
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql;
		String filter = getBankFilter(bankIDs);
		if(filter == null || filter.trim().length()<=0){
			return result;
		}
		try{
			conn = this.getConnection();
			sql = "update F_B_Banks set INMONEYFLAG = ? where 1=1 "+filter;
			state = conn.prepareStatement(sql);
			state.setInt(1, value);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public int chBankOutMoney(Vector<String> bankIDs,int value)throws SQLException,ClassNotFoundException{
		log("批量禁用、回复银行使用 chBankOutMoney bankIDs["+bankIDs+"]value["+value+"]");
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql;
		String filter = getBankFilter(bankIDs);
		if(filter == null || filter.trim().length()<=0){
			return result;
		}
		try{
			conn = this.getConnection();
			sql = "update F_B_Banks set OUTMONEYFLAG = ? where 1=1 "+filter;
			state = conn.prepareStatement(sql);
			state.setInt(1, value);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	/**
	 * 根据传入银行信息组织条件语句
	 * @param bankIDs 银行编号集合
	 * @return String
	 */
	private String getBankFilter(Vector<String> bankIDs){
		String filter = "";
		if(bankIDs == null || bankIDs.size()<=0){
			return filter;
		}else{
			String banks = "";
			for(String bankID : bankIDs){
				if(bankID != null && bankID.trim().length()>0){
					banks += "'"+bankID+"',";
				}
			}
			if(banks == null || banks.trim().length()<=0){
				return filter;
			}else{
				filter += " and bankID in("+banks.trim().substring(0,banks.trim().lastIndexOf(','))+")";
			}
		}
		return filter;
	}
	public BankValue getBank(String bankID) throws SQLException,ClassNotFoundException {
		BankValue result = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = getBank(bankID,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public BankValue getBank(String bankID,Connection conn) throws SQLException {
		log("===>>>取得银行信息   getBank  ");
		BankValue result = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {			
			sql = "select * from F_B_banks where bankid=? ";
			state = conn.prepareStatement(sql);
			state.setString(1, bankID);
			rs = state.executeQuery();
			if(rs.next()) {
				result = new BankValue();
				result.bankID = rs.getString("BANKID");
				result.bankName = rs.getString("BANKNAME");
				result.adapterClassname = rs.getString("ADAPTERCLASSNAME");
				result.validFlag = rs.getInt("VALIDFLAG");
				result.inMoneyFlag = rs.getInt("INMONEYFLAG");
				result.outMoneyFlag = rs.getInt("OUTMONEYFLAG");
				result.beginTime = rs.getString("BEGINTIME");
				result.endTime = rs.getString("ENDTIME");
				result.control = rs.getInt("CONTROL");
				result.bankType = rs.getInt("BANKTYPE");
				result.beleiveProcessor = rs.getString("BELEIVEPROCESSOR");
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public Vector<FirmValue> getFirmList(String filter) throws SQLException,ClassNotFoundException {
		log("===>>>取得交易账号信息列表   getFirmList  ");
		Vector<FirmValue> list = new Vector<FirmValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmValue value = null;
		try {			
			conn = getConnection();
			sql = "select * from F_B_FirmUser where 1=1 "+ filter;
			log(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				value = new FirmValue();
				value.firmID = rs.getString("FIRMID");
				value.firmName = rs.getString("FIRMNAME");
				value.status = rs.getInt("STATUS");
				value.registerDate = rs.getDate("REGISTERDATE");
				value.logoutDate = rs.getDate("LOGOUTDATE");
				value.password = rs.getString("PASSWORD");
				value.contact = rs.getString("contact");
				value.cardType = rs.getString("cardtype");
				value.card = rs.getString("card");
				list.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,conn);
		}
		return list;
	}
	public Vector<FirmValue> getFirmList2(String filter) throws SQLException,ClassNotFoundException{
		Vector<FirmValue> result = new Vector<FirmValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmValue value = null;
		try{
			conn = this.getConnection();
			sql = "select fbu.firmid firmid,fbu.firmname firmName,fbu.status status,fbu.registerdate registerdate " +
					",fbu.logoutdate logoutdate,fbu.password password,nvl(fba.isopen,2) isopen,nvl(fba.account,'') account " +
					",nvl(fba.contact,'') contact " +
					" from f_b_firmuser fbu,f_b_firmidandaccount fba where fbu.firmid=fba.firmid(+) " + filter;
			log("sql:"+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()){
				value = new FirmValue();
				value.firmID = rs.getString("firmID");
				value.firmName = rs.getString("firmName");
				value.status = rs.getInt("status");
				value.registerDate = rs.getDate("registerdate");
				value.logoutDate = rs.getDate("logoutdate");
				value.password = rs.getString("password");
				value.isOpen = rs.getInt("isOpen");
				value.account = rs.getString("account");
				value.contact = rs.getString("contact");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public FirmValue getFirm(String firmID) throws SQLException,ClassNotFoundException {
		FirmValue result = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = getFirm(firmID,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public FirmValue getFirm(String firmID,Connection conn) throws SQLException {
		log("===>>>取得交易账号   getFirm  ");
		FirmValue result = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {			
			sql = "select * from F_B_FirmUser where firmid=? ";
			state = conn.prepareStatement(sql);
			state.setString(1, firmID);
			rs = state.executeQuery();
			if(rs.next()) {
				result = new FirmValue();
				result.firmID = rs.getString("FIRMID");
				result.firmName = rs.getString("FIRMNAME");
				result.status = rs.getInt("STATUS");
				result.registerDate = rs.getDate("REGISTERDATE");
				result.logoutDate = rs.getDate("LOGOUTDATE");
				result.password = rs.getString("PASSWORD");
				result.contact = rs.getString("contact");
				result.cardType = rs.getString("cardtype");
				result.card = rs.getString("card");
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public int modFirmStatus(FirmValue val) throws SQLException,ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = modFirmStatus(val,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public int modFirmStatus(FirmValue val,Connection conn) throws SQLException {
		log("===>>>修改交易账号   modFirmStatus  ");
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {			
			sql = "update F_B_FirmUser  set STATUS = ? where firmid = ?";
			state = conn.prepareStatement(sql);
			state.setInt(1, val.status);
			result = state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int delFirm(String firmID) throws SQLException,ClassNotFoundException {
		Connection conn = null;
	  	PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection(); 
			sql = "delete from F_B_FirmUser where firmid='"+firmID+"'"; 
			state = conn.prepareStatement(sql); 
			state.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null,state,conn);
		}
		return result;
	}
	public int modFirmPassword(FirmValue val) throws SQLException,ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = modFirmPassword(val,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public int modFirmPassword(FirmValue val,Connection conn) throws SQLException {
		log("===>>>修改交易账号   modFirmPassword  ");
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {			
			sql = "update F_B_FirmUser  set PASSWORD = ? where firmid = ?";
			state = conn.prepareStatement(sql);
			state.setString(1, val.password);
			state.setString(2, val.firmID);
			result = state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public FirmBalanceValue availableBalance(String filter) throws SQLException,ClassNotFoundException {
		FirmBalanceValue fbv = null;
		Connection conn = null;
		try {
			conn = getConnection();
			fbv = availableBalance(filter,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(null,null,conn);
		}
		return fbv;
	}
	public FirmBalanceValue availableBalance(String filter,Connection conn) throws SQLException {
		log("===>>>查询交易账号市场可用资金  availableBalance  ");
		FirmBalanceValue fbv =null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			sql = "select firmID,funds balance,runtimefundio,lastbalance,nvl(FN_F_CanOutFunds(firmID,0,bankcode),0) canOutFunds from f_firmfunds_bank t where 1=1 "+filter;
			log("sql:"+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()){
				fbv = new FirmBalanceValue();
				fbv.setFirmId(rs.getString("firmID"));
				fbv.setLastBalance(rs.getDouble("lastbalance"));
				fbv.setMarketBalance(rs.getDouble("balance"));
				fbv.setInOutMoney(rs.getDouble("runtimefundio"));
				fbv.setAvilableBalance(rs.getDouble("balance"));
				fbv.setCanOutMoney(rs.getDouble("canOutFunds"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			closeStatement(rs,state,null);
		}
		return fbv;
	}
	/**
	 * 根据传入参数生成条件语句
	 * @param bankId 银行编号
	 * @param firmId 交易账号代码
	 * @param cardType 证件类型 (null 不做条件,1 个人,其他 8)
	 * @param isOpen 签约状态 (null 不做条件,1 签约,其他 未签约)
	 * @param status 客户状态 (null 不做条件,0 可用, 其他 不可用)
	 * @return String
	 */
	private String FirmAccountFile(String bankId,String firmId,String cardType,String isOpen,String status){
		String file="";
		if(bankId!=null && bankId.trim().length()>0){
			file=file+" and bankId='"+bankId.trim()+"' ";
		}
		if(firmId!=null && firmId.trim().length()>0){
			file=file+" and firmId='"+firmId.trim()+"' ";
		}
		if(cardType!=null && cardType.trim().length()>0){
			if(cardType.trim().equals("1")){
				file=file+" and cardType='1' ";
			}else{
				file=file+" and cardType='8' ";
			}
		}
		if(isOpen!=null && isOpen.trim().length()>0){
			if(isOpen.trim().equals("1")){
				file=file+" and isOpen=1 ";
			}else{
				file=file+" and isOpen=0 ";
			}
		}
		if(status!=null && status.trim().length()>0){
			if(status.trim().equals("0")){
				file=file+" and status=0 ";
			}else{
				file=file+" and status=1 ";
			}
		}
		file=file+" order by bankId,firmId ";
		return file;
	}
	/**
	 * 判断是否超出了转账时间段限制
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @return int 0 可以交易,1 未到交易时间,2 交易时间已过
	 */
	private int getTradeTime(String startTime,String endTime){
		this.log("判断是否超出了交易时间范围");
		int result = 1;
		if(startTime==null || startTime.trim().length()<=0 || endTime==null || endTime.trim().length()<=0){
			return 0;
		}else {
			startTime=startTime.trim();
			endTime = endTime.trim();
			if(startTime.length()<6){
				for(int i=0;i<6-startTime.length();i++){
					startTime+="0";
				}
			}
			if(endTime.length()<6){
				for(int i=0;i<6-startTime.length();i++){
					endTime+="0";
				}
			}
		}
		java.util.Date now = new java.util.Date();
		java.util.Date start = Tool.getDate(startTime);
		java.util.Date end = Tool.getDate(endTime);
		if(now.getTime()<=start.getTime()){
			result = 1;
		}else if(now.getTime()>=end.getTime()){
			result = 2;
		}else{
			result = 0;
		}
		return result;
	}
	public Vector<CorrespondValue> getCorrespondList(String bankId,String firmId,String cardType,String isOpen,String status){
		try {
			return getCorrespondList(FirmAccountFile(bankId,firmId,cardType,isOpen,status));
		} catch (SQLException e) {
			log("查询交易账号和银行的绑定关系，数据库异常"+Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log("查询交易账号和银行的绑定关系，找不到类异常"+Tool.getExceptionTrace(e));
		}
		return new Vector<CorrespondValue>();
	}
	public Vector<CorrespondValue> getCorrespondList(String filter) throws SQLException,ClassNotFoundException {
		Vector<CorrespondValue> result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			result = getCorrespondList(filter,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public Vector<CorrespondValue> getCorrespondList(String filter,Connection conn) throws SQLException {
		log("===>>>查询账号对应关系列表   getCorrespondList  ");
		Vector<CorrespondValue> result = new Vector<CorrespondValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		try {			
			sql = "select * from F_B_firmidandaccount where 1=1 "+ filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				value = new CorrespondValue();
				value.id = rs.getLong("ID");
				value.bankID = rs.getString("BANKID");
				value.firmID = rs.getString("FIRMID");
				value.contact = rs.getString("CONTACT");
				value.status = rs.getInt("STATUS");
				value.account = rs.getString("ACCOUNT");
				value.account1 = rs.getString("ACCOUNT1");
				value.accountName = rs.getString("ACCOUNTNAME");
				value.accountName1 = rs.getString("ACCOUNTNAME1");
				value.bankName = rs.getString("BANKNAME");
				value.bankProvince = rs.getString("BANKPROVINCE");
				value.bankCity = rs.getString("BANKCITY");
				value.mobile = rs.getString("MOBILE");
				value.email = rs.getString("EMAIL");
				value.isOpen = rs.getInt("ISOPEN");
				value.cardType = rs.getString("CARDTYPE");
				value.card = rs.getString("CARD");
				value.frozenFuns = rs.getDouble("FROZENFUNS");
				value.opentime = rs.getDate("OPENTIME");
				value.deltime = rs.getDate("DELTIME");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public Vector<CorrespondValue> getCorrespondList2(String filter,int[] pageinfo) throws SQLException,ClassNotFoundException{
		Vector<CorrespondValue> result = new Vector<CorrespondValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		try{
			conn = this.getConnection();
			
			
			if(filter != null && filter.indexOf("organizationno")>=0){
				sql = "select fbf.*,fbb.bankname name,mf.firmType firmType,ff.bankcode mainBank " +
					",case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember " +
					" from f_b_firmidandaccount fbf,f_b_banks fbb,m_firm mf,F_FirmFunds ff,v_customerrelateorganization mc " +
					" where fbf.bankid=fbb.bankid(+) and fbf.firmid=mf.firmid(+) and fbf.firmid=ff.firmid(+) and mc.customerno=fbf.firmid " + filter;
			}else if(filter != null && filter.indexOf("memberno")>=0){
				sql = "select fbf.*,fbb.bankname name,mf.firmType firmType,ff.bankcode mainBank " +
					",case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember " +
					" from f_b_firmidandaccount fbf,f_b_banks fbb,m_firm mf,F_FirmFunds ff,m_customerinfo mc " +
					" where fbf.bankid=fbb.bankid(+) and fbf.firmid=mf.firmid(+) and fbf.firmid=ff.firmid(+) and mc.customerno=fbf.firmid " + filter;
			}else{
				sql = "select fbf.*,fbb.bankname name,mf.firmType firmType,ff.bankcode mainBank " +
					",case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember " +
					" from f_b_firmidandaccount fbf,f_b_banks fbb,m_firm mf,F_FirmFunds ff " +
					" where fbf.bankid=fbb.bankid(+) and fbf.firmid=mf.firmid(+) and fbf.firmid=ff.firmid(+) " + filter;
			}
			log("sql:"+sql);
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			while(rs.next()){
				value = new CorrespondValue();
				value.id = rs.getLong("ID");
				value.bankID = rs.getString("BANKID");
				value.firmID = rs.getString("FIRMID");
				value.contact = rs.getString("CONTACT");
				value.status = rs.getInt("STATUS");
				value.account = rs.getString("ACCOUNT");
				value.account1 = rs.getString("ACCOUNT1");
				value.accountName = rs.getString("ACCOUNTNAME");
				value.accountName1 = rs.getString("ACCOUNTNAME1");
				value.bankName = rs.getString("BANKNAME");
				value.bankProvince = rs.getString("BANKPROVINCE");
				value.bankCity = rs.getString("BANKCITY");
				value.mobile = rs.getString("MOBILE");
				value.email = rs.getString("EMAIL");
				value.isOpen = rs.getInt("ISOPEN");
				value.cardType = rs.getString("CARDTYPE");
				value.card = rs.getString("CARD");
				value.frozenFuns = rs.getDouble("FROZENFUNS");
				value.opentime = rs.getDate("OPENTIME");
				value.deltime = rs.getDate("DELTIME");
				value.name = rs.getString("name");
				value.firmType = rs.getString("firmType");
				value.mainBank = rs.getString("mainBank");
				value.belevemember = rs.getString("belevemember");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public Vector<FirmBankMsg> getfirmBankMsg(String filter)throws SQLException,ClassNotFoundException{
		Vector<FirmBankMsg> result = new Vector<FirmBankMsg>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmBankMsg value = null;
		try{
			sql = "select account.firmID firmID,account.contact contact,nvl(account.account,'') account " +
				",nvl(account.accountName,'') accountName,nvl(account.account1,'') account1,nvl(account.frozenfuns,0) frozenfuns " +
				",account.bankID bankID,nvl(bank.bankName,'') bankName,nvl(fund.bankCode,'') mainBank " +
				",nvl(fundbank.TransferFund,0) transferFund,nvl(fundbank.Funds,0) funds,nvl(FN_F_CanOutFunds(account.firmID,0,account.bankID),0) canOutMoney " +
				"from f_b_firmIDandAccount account,f_b_banks bank,F_FirmFunds_Bank fundbank,F_FirmFunds fund " +
				"where account.bankID=bank.bankID(+) and account.bankID=fundbank.bankCode(+) " +
				"and account.firmID=fundbank.firmID(+) and account.firmID=fund.firmID(+) and account.bankID is not null "+filter+" order by account.firmID,account.bankID ";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()){
				value = new FirmBankMsg();
				value.firmID = rs.getString("firmID");
				value.contact = rs.getString("contact");
				value.account = rs.getString("account");
				value.accountName = rs.getString("accountName");
				value.account1 = rs.getString("account1");
				value.frozenfuns = rs.getDouble("frozenfuns");
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.mainBank = rs.getString("mainBank");
				value.transferFund = rs.getDouble("transferFund");
				value.funds = rs.getDouble("funds");
				value.canOutMoney = rs.getDouble("canOutMoney");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public CorrespondValue getCorrespond(String bankID, String firmID, String account) throws SQLException,ClassNotFoundException {
		CorrespondValue result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			result = getCorrespond(bankID,firmID,account,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public CorrespondValue getCorrespond(String bankID, String firmID, String account, Connection conn) throws SQLException {
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue result = null;
		try {
			sql = "select fbf.*,fbb.bankname name from f_b_firmidandaccount fbf,f_b_banks fbb where fbf.bankid=fbb.bankid(+) and fbf.bankID=? and fbf.firmID=? and fbf.account=? ";
			state = conn.prepareStatement(sql);
			state.setString(1, bankID);
			state.setString(2, firmID);
			state.setString(3, account);
			rs = state.executeQuery();
			if(rs.next()) {
				result = new CorrespondValue();
				result.bankID = rs.getString("BANKID");
				result.firmID = rs.getString("FIRMID");
				result.contact = rs.getString("CONTACT");
				result.status = rs.getInt("STATUS");
				result.account = rs.getString("ACCOUNT");
				result.account1 = rs.getString("ACCOUNT1");
				result.accountName = rs.getString("ACCOUNTNAME");
				result.accountName1 = rs.getString("ACCOUNTNAME1");
				result.bankName = rs.getString("BANKNAME");
				result.bankProvince = rs.getString("BANKPROVINCE");
				result.bankCity = rs.getString("BANKCITY");
				result.mobile = rs.getString("MOBILE");
				result.email = rs.getString("EMAIL");
				result.isOpen = rs.getInt("ISOPEN");
				result.cardType = rs.getString("CARDTYPE");
				result.card = rs.getString("CARD");
				result.frozenFuns = rs.getDouble("FROZENFUNS");
				result.opentime = rs.getDate("OPENTIME");
				result.deltime = rs.getDate("DELTIME");
				result.name = rs.getString("name");
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public int countFirmAccount(String bankId,String firmId,String cardType,String isOpen,String status) throws SQLException,ClassNotFoundException{
		return countFirmAccount(FirmAccountFile(bankId,firmId,cardType,isOpen,status));
	}
	public int countFirmAccount(String file) throws SQLException,ClassNotFoundException{
		int result = 0;
		String sql="select nvl(count(*),0) countnum from F_B_firmidandaccount where 1=1 "+file;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()){
				result = rs.getInt("countnum");
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public int addCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		try {			
			conn = getConnection();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public int addCorrespond(CorrespondValue val,Connection conn) throws SQLException {
		log("===>>>添加账号对应关系   addCorrespond  ");
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {			
			sql = "insert into F_B_firmidandaccount(ID,BANKID, FIRMID, CONTACT, STATUS, ACCOUNT" +
				",ACCOUNT1, ACCOUNTNAME, ACCOUNTNAME1, BANKNAME, BANKPROVINCE, BANKCITY" +
				",MOBILE,EMAIL,ISOPEN,CARDTYPE,CARD,FROZENFUNS,OPENTIME) " +
				"values(SEQ_F_B_FIRMIDANDACCOUNT.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?)";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++, val.bankID);
			state.setString(n++, val.firmID);
			state.setString(n++, val.contact);
			state.setInt(n++, val.status);
			state.setString(n++, val.account);
			state.setString(n++, val.account1);
			state.setString(n++, val.accountName);
			state.setString(n++, val.accountName1);
			state.setString(n++, val.bankName);
			state.setString(n++, val.bankProvince);
			state.setString(n++, val.bankCity);
			state.setString(n++, val.mobile);
			state.setString(n++, val.email);
			state.setInt(n++, val.isOpen);
			state.setString(n++, val.cardType);
			state.setString(n++, val.card);
			state.setTimestamp(n++, (val.isOpen==ProcConstants.firmTypeOpen ? new java.sql.Timestamp(new java.util.Date().getTime()) : null));
			result = state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int modCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException {
		Connection conn = null;
		int result = 0;
		try {			
			conn = getConnection();
			result = modCorrespond(val,conn);
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public int modCorrespond(CorrespondValue val,Connection conn) throws SQLException {
		log("===>>>修改账号对应关系   modCorrespond conn  ");
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update f_b_firmidandaccount set ACCOUNT=?,ACCOUNT1=?,ACCOUNTNAME=?" +
				",isOpen=?,status=?,bankid=?,MODTIME=sysdate where id=? ";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++, val.account);
			state.setString(n++, val.account1);
			state.setString(n++, val.accountName);
			state.setInt(n++, val.isOpen);
			state.setInt(n++, val.status);
			state.setString(n++, val.bankID);
			state.setLong(n++, val.id);
			state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int openCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		try {
			conn = this.getConnection();
			result = openCorrespond(val,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public int openCorrespond(CorrespondValue val,Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update f_b_firmidandaccount set ISOPEN="+ProcConstants.firmTypeOpen+",STATUS="+ProcConstants.firmStatusTrue +
					",ACCOUNT=?,ACCOUNT1=?,ACCOUNTNAME=?,ACCOUNTNAME1=?,CARDTYPE=?" +
					",OPENTIME=sysdate,CARD=?,bankID=? " +
			" where ID=?";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++, val.account);
			state.setString(n++, val.account1);
			state.setString(n++, val.accountName);
			state.setString(n++, val.accountName1);
			state.setString(n++, val.cardType);
			state.setString(n++, val.card);
			state.setString(n++, val.bankID);
			state.setLong(n++, val.id);
//			state.setString(n++, val.contact);
			result = state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int modCorrespondStatus(CorrespondValue val)throws SQLException,ClassNotFoundException{
		log("恢复、禁用银行账号");
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try{
			conn = this.getConnection();
			sql = "update F_B_firmidandaccount set STATUS=(case when ISOPEN=1 then "+val.status+" else 1 end) where 1=1 and (firmID='"+val.firmID+"' and CONTACT='"+val.contact+"') and bankID='"+val.bankID+"'";
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, state, conn);
		}
		return result;
	}
	public int modBankFrozenFuns(String filter,double money,Connection conn) throws SQLException{
		log("修改交易账号银行接口冻结资金 "+filter+" "+money);
		int result = 0 ;
		PreparedStatement state = null;
		String sql = null;
		try{
			sql = "update F_B_firmidandaccount set frozenFuns=frozenFuns+"+money+" where 1=1 "+filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}finally{
			this.closeStatement(null, state, null);
		}
		return result;
	}
	public int delCorrespond(CorrespondValue val) throws SQLException,ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			result = delCorrespond(val,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public int delCorrespond(CorrespondValue val,Connection conn) throws SQLException {
		log("===>>>删除账号对应关系   delCorrespond  ");
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "delete F_B_firmidandaccount where BANKID=? and CONTACT=? and ACCOUNT=? ";
			state = conn.prepareStatement(sql);
			state.setString(1, val.bankID);
			state.setString(2, val.contact);
			state.setString(3, val.account);
			state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int destroyAccount(CorrespondValue val) throws SQLException,ClassNotFoundException {
		log("注销账号对应关系   destroyAccount ");
		Connection conn = null;
		int result = 0;
		try{
			conn = this.getConnection();
			result = destroyAccount(val,conn);
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, null, conn);
		}
		return result;
	}
	public int destroyAccount(CorrespondValue val,Connection conn) throws SQLException {
		log("注销账号对应关系   destroyAccount ");
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try{
			sql = "update f_b_firmidandaccount set DELTIME=sysdate,STATUS="+ProcConstants.firmStatusFalse+",isopen="+ProcConstants.firmTypeDel+" where CONTACT=? and bankID=? and account=? ";
			state = conn.prepareStatement(sql);
			state.setString(1, val.contact);
			state.setString(2, val.bankID);
			state.setString(3, val.account);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}finally{
			this.closeStatement(null, state, null);
		}
		return result;
	}
	public int useBank(String bankID)throws SQLException,ClassNotFoundException{
		this.log("判断现在是否可以转账 useBank bankID["+bankID+"]");
		int result = 4;
		BankValue bv = this.getBank(bankID);
		if(bv==null){
			return result;
		} else {
			if(bv.control==1){
				result = 0;
			}else if(bv.control==2){
				if(this.getTradeDate(new java.util.Date())){
					result = 0;
				} else {
					result = 1;
				}
			}else if(bv.control == 3){
				int n = this.getTradeTime(bv.beginTime, bv.endTime);
				switch(n){
				case 0 : result = 0 ; break;
				case 1 : result = 2 ; break;
				case 2 : result = 3 ; break;
				}
			}else if(bv.control == 0){
				if(this.getTradeDate(new java.util.Date())){
					int n = this.getTradeTime(bv.beginTime, bv.endTime);
					switch(n){
					case 0 : result = 0 ; break;
					case 1 : result = 2 ; break;
					case 2 : result = 3 ; break;
					}
				} else {
					result = 1;
				}
			}
		}
		return result;
	}
	public boolean getTradeDate(java.util.Date tradeDate)throws SQLException,ClassNotFoundException{
		this.log("判断是否为交易日 getTradeDate tradeDate["+tradeDate+"]");
		boolean flag = true;
		Calendar c = Calendar.getInstance();
		c.setTime(tradeDate);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);//取得星期
		String date = Tool.fmtDate(tradeDate);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select * from t_a_nottradeday";
		try{
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()){
				String nweeks = rs.getString("week");
				String ndates = rs.getString("day");
				if(nweeks==null){
					nweeks="";
				}
				if(ndates==null){
					ndates="";
				}
				String [] weeks = nweeks.split(",");
				String [] dates = ndates.split(",");
				for(String week : weeks){
					if((dayOfWeek+"").equalsIgnoreCase(week)){
						flag = false;
					}
				}
				for(String ndate : dates){
					if(date.equals(ndate)){
						flag = false;
					}
				}
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return flag;
	}
	public boolean getTraderStatus() throws SQLException,ClassNotFoundException {
		log("===>>>取交易系统结算状态  getTraderStatus  ");
		boolean traderStatus = false;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " select to_char(max(B_Date),'yyyy-MM-dd') maxDate from f_DailyBalance ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()) {
				String settlementDate = rs.getString(1);
				String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				if(settlementDate!=null && settlementDate.indexOf(now)==0) {
					traderStatus = true;
				}
			}
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		} finally {
			closeStatement(rs,state,conn);
		}
		return traderStatus;
	}
	public FirmMessageVo getFirmMSG(String firmid) throws SQLException,ClassNotFoundException {
		log("===>>>查询交易账号名下交易员的密码  getFirmPwd  ");
		FirmMessageVo fmv = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "select firmid,firmtype" +
				",case when firmtype='C' then customerstatus when firmtype='M' then memberstatus else specialstatus end status " +
				"from " +
					"(select mf.firmid firmid,mf.firmtype firmtype" +
					",tc.status customerstatus,tm.status memberstatus,ts.status specialstatus " +
					"from " +
						"m_firm mf,t_customer tc,t_compmember tm,t_specialmember ts " +
						"where mf.firmid=tc.firmid(+) and mf.firmid=tm.m_firmid(+) " +
							"and mf.firmid=ts.m_firmid(+) and mf.firmid=?" +
					") a";
			state = conn.prepareStatement(sql);
			state.setString(1, firmid);
			rs = state.executeQuery();
			if(rs.next()) {
				fmv = new FirmMessageVo();
				fmv.setFirmid(rs.getString("firmid"));
				fmv.setFirmType(rs.getString("firmtype"));
				fmv.setStatus(rs.getString("status"));
			}
		} catch(SQLException e){
			throw e;
		} catch(ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(rs,state,conn);
		}
		return fmv;
	}
	public long addCapitalInfo(CapitalValue val,Connection conn) throws SQLException {
		log("===>>>增加资金流水记录   addCapitalInfo  "+new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try {
			sql = "select seq_F_B_capitalInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_capitalInfo(ID, TRADER, FUNID, ACTIONID, FIRMID, CONTACT, BANKID" +
			", TYPE, LAUNCHER, MONEY, STATUS, BANKTIME, CREATETIME,NOTE,FUNID2,CREATEDATE) " +
			"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?,to_char(sysdate,'yyyy-MM-dd'))";
			state = conn.prepareStatement(sql);
			int n=1;
			state.setLong(n++, id);
			state.setString(n++, val.trader);
			state.setString(n++, val.funID);
			state.setLong(n++, val.actionID);
			state.setString(n++, val.firmID);
			state.setString(n++, val.contact);
			state.setString(n++, val.bankID);
			state.setInt(n++, val.type);
			state.setInt(n++, val.launcher);
			state.setDouble(n++, val.money);
			state.setInt(n++, val.status);
			state.setTimestamp(n++, val.bankTime);
			state.setString(n++, val.note);
			state.setString(n++, (val.funID == null || val.funID.trim().length()<=0) ? "b_"+id : val.funID);
			state.executeUpdate();
			log("【新增流水】:流水号["+id+"]"+"银行流水号["+val.funID+"]"+"市场流水号["+val.actionID+"]"+"金额["+val.money+"]");
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return id;
	}
	public Vector<CapitalValue> getCapitalInfoList(String filter) throws SQLException,ClassNotFoundException {
		Vector<CapitalValue> result = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = getCapitalInfoList(filter,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public Vector<CapitalValue> getCapitalInfoList(String filter,Connection conn) throws SQLException {
		log("===>>>取得资金流水记录列表   getCapitalInfoList  ");
		Vector<CapitalValue> result = new Vector<CapitalValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		try {			
			sql = "select * from F_B_capitalInfo where 1=1 "+ filter;
			System.out.println("取得资金流水记录列表:sql="+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				value = new CapitalValue();
				value.iD = rs.getLong("ID");
				value.trader = rs.getString("TRADER");
				value.funID = rs.getString("FUNID");
				value.actionID = rs.getLong("ACTIONID");
				value.firmID = rs.getString("FIRMID");
				value.contact = rs.getString("CONTACT");
				value.bankID = rs.getString("BANKID");
				value.type = rs.getInt("TYPE");
				value.launcher = rs.getInt("LAUNCHER");
				value.money = rs.getDouble("MONEY");
				value.status = rs.getInt("STATUS");
				value.bankTime = rs.getTimestamp("BANKTIME");
				value.createtime = rs.getTimestamp("CREATETIME");
				value.note = rs.getString("NOTE");
				value.funID2 = rs.getString("FUNID2");
				value.createdate = rs.getString("CREATEDATE");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public CapitalValue getCapitalInfo(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得单个资金流水记录  getCapitalInfo  ");
	  	Connection conn = null;
	    PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		try {			
			conn = getConnection();
			sql = "select * from F_B_capitalInfo where 1=1 "+ filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()) {
				value = new CapitalValue();
				value.iD = rs.getLong("ID");
				value.trader = rs.getString("TRADER");
				value.funID = rs.getString("FUNID");
				value.actionID = rs.getLong("ACTIONID");
				value.firmID = rs.getString("FIRMID");
				value.contact = rs.getString("CONTACT");
				value.bankID = rs.getString("BANKID");
				value.type = rs.getInt("TYPE");
				value.launcher = rs.getInt("LAUNCHER");
				value.money = rs.getDouble("MONEY");
				value.status = rs.getInt("STATUS");
				value.bankTime = rs.getTimestamp("BANKTIME");
				value.createtime = rs.getTimestamp("CREATETIME");
				value.note = rs.getString("NOTE");
				value.funID2 = rs.getString("FUNID2");
				value.createdate = rs.getString("CREATEDATE");
			}
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(rs,state,conn);
		}
		return value;
	}
	public int modCapitalInfoStatus(long id,String funID, int status,Timestamp bankTime, Connection conn) throws SQLException {
		log("===>>>修改资金流水记录状态   modCapitalInfoStatus  "+new java.util.Date());
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			String funidf = "";
			String bankTimef = "";
			int n=1;
			if(bankTime != null){
				bankTimef = ",bankTime=?";
			}
			if(funID==null || funID.trim().length()<=0){
				funID="";
			}else{
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo set OLDFUNID=FUNID,OLDSTATUS=STATUS, status=?,FUNID=?"+bankTimef+funidf+" where id=?";
			state = conn.prepareStatement(sql);
			state.setInt(n++, status);
			state.setString(n++, funID.trim());
			if(bankTime != null){
				state.setTimestamp(n++, bankTime);
			}
			if(funidf != null && funidf.trim().length()>0){
				state.setString(n++, funID);
			}
			state.setLong(n++, id);
			state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int  modCapitalInfoNote(long id, String note,Connection conn) throws SQLException {
		log("===>>>修改资金流水记录描述   modCapitalInfoNote  "+new java.util.Date());
		if(note==null){
			note = "";
		}
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_capitalInfo set note=note || '，' || '"+note.trim()+"' where id="+id+" ";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public int modCapitalInfoTrader(long id, String trader,Connection conn) throws SQLException{
		log("===>>>修改资金流水记录交易员   modCapitalInfoTrader  "+new java.util.Date());
		if(trader==null){
			trader = "";
		}
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {			
			sql = "update F_B_capitalInfo set trader=trader || ',' || '"+trader.trim()+"' where id="+id+" ";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public Vector<CapitalValue> getCapitalInfoList2(String filter,int[] pageinfo) throws SQLException,ClassNotFoundException {
		log("===>>>取得资金流水记录列表   getCapitalInfoList2  "+new java.util.Date());
		Vector<CapitalValue> result = new Vector<CapitalValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		try {
			conn = getConnection();
			//sql = "select fbc.*,mf.firmtype firmType,fbf.account account,fbf.accountName accountName,fbb.bankname bankName from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) "+ filter;
			
			
			if(filter != null && filter.indexOf("organizationno")>=0){
				sql = "select fbc.*,mf.firmtype firmType,fbf.account account,fbf.accountName accountName,fbb.bankname bankName from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,v_customerrelateorganization mc where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid " + filter;
			}else if(filter != null && filter.indexOf("memberno")>=0){
				sql = "select fbc.*,mf.firmtype firmType,fbf.account account,fbf.accountName accountName,fbb.bankname bankName from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,m_customerinfo mc where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid " + filter;
			}else{
				sql = "select fbc.*,mf.firmtype firmType,fbf.account account,fbf.accountName accountName,fbb.bankname bankName from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) " + filter;
			}
			
			
			
			log("sql:"+sql);
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			while(rs.next()) {
				value = new CapitalValue();
				value.trader = rs.getString("TRADER");
				value.funID = rs.getString("FUNID");
				value.actionID = rs.getLong("ACTIONID");
				value.firmID = rs.getString("FIRMID");
				value.contact = rs.getString("CONTACT");
				value.bankID = rs.getString("BANKID");
				value.type = rs.getInt("TYPE");
				value.launcher = rs.getInt("LAUNCHER");
				value.money = rs.getDouble("MONEY");
				value.status = rs.getInt("STATUS");
				value.bankTime = rs.getTimestamp("BANKTIME");
				value.createtime = rs.getTimestamp("CREATETIME");
				value.note = rs.getString("NOTE");
				value.funID2 = rs.getString("FUNID2");
				value.createdate = rs.getString("CREATEDATE");
				value.firmType = rs.getString("firmType");
				value.account = rs.getString("account");
				value.accountName = rs.getString("accountName");
				value.bankName = rs.getString("bankName");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(rs,state,conn);
		}
		return result;
	}
	public Vector<CapitalMoneyVO> getCapitalInfoMoney(String filter) throws SQLException,ClassNotFoundException {
		log("===>>>求和市场流水中资金   getCapitalInfoMoney  "+new java.util.Date());
		Vector<CapitalMoneyVO> result = new Vector<CapitalMoneyVO>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalMoneyVO value11 = new CapitalMoneyVO(ProcConstants.inMoneyType,ProcConstants.statusSuccess);
		CapitalMoneyVO value12 = new CapitalMoneyVO(ProcConstants.outMoneyType,ProcConstants.statusSuccess);
		CapitalMoneyVO value21 = new CapitalMoneyVO(ProcConstants.inMoneyType,ProcConstants.statusFailure);
		CapitalMoneyVO value22 = new CapitalMoneyVO(ProcConstants.outMoneyType,ProcConstants.statusFailure);
		CapitalMoneyVO value31 = new CapitalMoneyVO(ProcConstants.inMoneyType,ProcConstants.statusBankNull);
		CapitalMoneyVO value32 = new CapitalMoneyVO(ProcConstants.outMoneyType,ProcConstants.statusBankNull);
		try {
			conn = getConnection();
			
			
			if(filter != null && filter.indexOf("organizationno")>=0){
				sql = "select count(*) rowcount,sum(fbc.money) money,fbc.type type,fbc.status status from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,v_customerrelateorganization mc where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid "+ filter +"  group by fbc.type,fbc.status ";
			}else if(filter != null && filter.indexOf("memberno")>=0){
				sql = "select count(*) rowcount,sum(fbc.money) money,fbc.type type,fbc.status status from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,m_customerinfo mc where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid "+ filter +"  group by fbc.type,fbc.status ";
			}else{
				sql = "select count(*) rowcount,sum(fbc.money) money,fbc.type type,fbc.status status from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) "+ filter +"  group by fbc.type,fbc.status ";
			}
			
			
			log("sql:"+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				if(rs.getInt("type")==ProcConstants.inMoneyType){
					if(rs.getInt("status")==ProcConstants.statusSuccess){
						value11.rowcount = rs.getInt("rowcount");
						value11.money = rs.getDouble("money");
					}else if(rs.getInt("status")==ProcConstants.statusFailure || rs.getInt("status")==ProcConstants.statusBlunt){
						value21.rowcount += rs.getInt("rowcount");
						value21.money += rs.getDouble("money");
					}else{
						value31.rowcount += rs.getInt("rowcount");
						value31.money += rs.getDouble("money");
					}
				}else if(rs.getInt("type")==ProcConstants.outMoneyType){
					if(rs.getInt("status")==ProcConstants.statusSuccess){
						value12.rowcount = rs.getInt("rowcount");
						value12.money = rs.getDouble("money");
					}else if(rs.getInt("status")==ProcConstants.statusFailure || rs.getInt("status")==ProcConstants.statusBlunt){
						value22.rowcount += rs.getInt("rowcount");
						value22.money += rs.getDouble("money");
					}else{
						value32.rowcount += rs.getInt("rowcount");
						value32.money += rs.getDouble("money");
					}
				}
			}
			result.add(value11);
			result.add(value12);
			result.add(value21);
			result.add(value22);
			result.add(value31);
			result.add(value32);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(rs,state,conn);
		}
		return result;
	}
	public Vector<MoneyInfoValue> getMoneyInfoList(String filter) throws SQLException,ClassNotFoundException {
		log("===>>>取得对账信息列表   getMoneyInfoList  "+new java.util.Date());
		Vector<MoneyInfoValue> result = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			result = getMoneyInfoList(filter,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(null,null,conn);
		}
		return result;
	}
	public Vector<MoneyInfoValue> getMoneyInfoList(String filter,Connection conn) throws SQLException {
		log("===>>>取得对账信息列表   getMoneyInfoList  "+new java.util.Date());
		Vector<MoneyInfoValue> result = new Vector<MoneyInfoValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		try {			
			sql = "select * from F_B_BANKCOMPAREINFO where 1=1 "+ filter;
			log("sql:"+sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				value = new MoneyInfoValue();
				value.account = rs.getString("account");
				value.compareDate = rs.getDate("compareDate");
				value.createtime = rs.getTimestamp("createtime");
				value.firmID = rs.getString("firmID");
				value.id = rs.getString("funid");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.bankID = rs.getString("bankID");
				result.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public Vector<LogValue> logList(String filter,int[] pageinfo) {
		log("查看log日志列表 filter["+filter+"]");
		Vector<LogValue> result = new Vector<LogValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " select logid,logopr,logcontent,LOGTIME,logIp from f_b_log " + filter;
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			while(rs.next()) {
				LogValue lv = new LogValue();
				lv.setLogid(rs.getLong("logid"));
				lv.setLogopr(rs.getString("logopr"));
				lv.setLogcontent(rs.getString("logcontent"));
				lv.setLogtime(rs.getTimestamp("LOGTIME"));
				lv.setIp(rs.getString("logIp"));
				result.add(lv);
			}
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs,state,conn);
		}
		return result;
	}
	public void log(LogValue lv) {
		log("添加日志列表 lv:"+(lv==null ? "为 null" : "\n" + lv.toString() + "\n"));
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " insert into c_globallog_all " +
				"(ID,OPERATOR,OPERATETIME,OPERATETYPE,OPERATEIP,OPERATORTYPE,MARK,OPERATECONTENT,CURRENTVALUE,OPERATERESULT) " +
				"values (SEQ_C_GLOBALLOG.Nextval,?,sysdate,?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, lv.getLogopr());
			state.setString(2, lv.getLogtype());
			state.setString(3, lv.getIp());
			state.setString(4, lv.getLogoprtype());
			state.setString(5, lv.getMark());
			state.setString(6, lv.getLogcontent());
			state.setString(7, lv.getContentvalue());
			state.setInt(8, lv.getResult());
			state.executeUpdate();
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			closeStatement(null,state,conn);
		}
	}
	public Vector<InterfaceLog> interfaceLogList(String filter,int[] pageinfo)throws SQLException,ClassNotFoundException{
		log("查询银行接口和银行通讯信息  interfaceLogList filter["+filter+"]");
		Vector<InterfaceLog> result = new Vector<InterfaceLog>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		InterfaceLog value = null;
		
		String sql = "select fbi.*, fbf.firmID firmID, fbb.bankName bankName from F_B_INTERFACELOG fbi, F_B_FIRMUSER fbf, F_B_BANKS fbb where trim(fbi.CONTACT) = trim(fbf.CONTACT(+)) and trim(fbi.bankID) = trim(fbb.bankID) "+filter;
		try{
			conn = this.getQueryConnection();
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			
			while(rs.next()){
				value = new InterfaceLog();
				value.bankID = Tool.trim(rs.getString("bankID"));
				value.account = Tool.trim(rs.getString("account"));
				value.beginMsg = Tool.trim(rs.getString("beginMsg"));
				value.contact = Tool.trim(rs.getString("contact"));
				value.createtime = rs.getTimestamp("createtime");
				value.endMsg = Tool.trim(rs.getString("endMsg"));
				value.launcher = rs.getInt("launcher");
				value.logID = rs.getLong("logID");
				value.type = rs.getInt("type");
				value.firmID = Tool.trim(rs.getString("firmID"));
				value.bankName = Tool.trim(rs.getString("bankName"));
				value.result = rs.getInt("result");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public int interfaceLog(InterfaceLog log)throws SQLException,ClassNotFoundException{
		log("插入银行接口和银行通讯信息  interfaceLog log["+log.toString()+"]");
		Connection conn = null;
		PreparedStatement state = null;
		int result = 0;
		String sql = "insert into F_B_INTERFACELOG (LOGID,BANKID,LAUNCHER,TYPE,CONTACT,ACCOUNT,BEGINMSG,ENDMSG,RESULT,CREATETIME) values (SEQ_F_B_INTERFACELOG.NEXTVAL,?,?,?,?,?,?,?,?,sysdate)";
		try{
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			int n=1;
			state.setString(n++, Tool.trim(log.bankID));
			state.setInt(n++, log.launcher);
			state.setInt(n++, log.type);
			state.setString(n++, Tool.trim(log.contact));
			state.setString(n++, Tool.trim(log.account));
			state.setString(n++, Tool.trim(log.beginMsg));
			state.setString(n++, Tool.trim(log.endMsg));
			state.setInt(n++, log.result);
			result = state.executeUpdate();
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, state, conn);
		}
		return result;
	}
	public double sumCapitalInfo(String filter,Connection conn) throws SQLException {
		log("===>>>合计资金流水金额   sumCapitalInfo  "+new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		double result = 0;
		try {
			sql = "select nvl(sum(value),0) money from f_clientledger "+ filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()) {
				result = rs.getDouble(1);
			}
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return result;
	}
	public long addMoneyInfo(MoneyInfoValue val) throws SQLException,ClassNotFoundException {
		Connection conn = null;
		long id = -1;
		try {			
			conn = getConnection();
			id = addMoneyInfo(val,conn);
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(null,null,conn);
		}
		return id;
	  }
	public long addMoneyInfo(MoneyInfoValue val,Connection conn) throws SQLException {
		log("===>>>添加对账信息   addMoneyInfo  ");
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try{
			sql = "select seq_F_B_bankCompareInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()) {
				id = rs.getLong(1);
			}
		}catch(SQLException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, null);
		}
		try {
			sql = "insert into F_B_BANKCOMPAREINFO(id,funid, firmid, account, type, money, comparedate, note, status,createtime,bankid) " +
					"values(?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,?)";
			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setString(2, val.id);
			state.setString(3, val.firmID==null ? val.firmID : val.firmID.trim());
			state.setString(4, val.account);
			state.setInt(5, val.type);
			state.setDouble(6, val.money);
			state.setDate(7, val.compareDate);
			state.setString(8, val.note);
			state.setInt(9, val.status);
			state.setString(10, val.bankID);

			state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(rs,state,null);
		}
		return id;
	}
	public int delMoneyInfo(String filter,Connection conn) throws SQLException {
		log("===>>>删除对账信息   delMoneyInfo  "+new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "delete F_B_BANKCOMPAREINFO where 1=1 "+filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			closeStatement(null,state,null);
		}
		return result;
	}
	public Vector<MoneyInfoValue> qureyBankCompareInfo(String date) throws SQLException,ClassNotFoundException {
		log("===>>>----  qureyBankCompareInfo  "+new java.util.Date());
	  	Connection conn = null;
	  	PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MoneyInfoValue> ve = new Vector<MoneyInfoValue>();
		try {
			conn = getConnection();
			sql = "select * from F_B_BANKCOMPAREINFO where trunc(COMPAREDATE)=to_date('"+ date +"','yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()) {
				MoneyInfoValue value = new MoneyInfoValue();
				value.id = rs.getString("FUNID");
				value.firmID = rs.getString("FIRMID");
				value.bankID = rs.getString("BANKID");
				value.account = rs.getString("ACCOUNT");
				value.type = rs.getInt("TYPE");
				value.money = rs.getDouble("MONEY");
				value.compareDate = rs.getDate("COMPAREDate");
				value.createtime = rs.getTimestamp("CREATETIME");
				value.status = rs.getInt("STATUS");
				value.note = rs.getString("NOTE");
				ve.add(value);
			}
		} catch(SQLException e) {
			throw e;
		} catch(ClassNotFoundException e){
			throw e;
		}finally {
			closeStatement(rs,state,conn);
		}
		return ve;
	}

	public Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date date,int[] pageinfo) throws SQLException,ClassNotFoundException{
		Vector<CompareResult> result = null;
		Connection conn = null;
		try{
			conn = this.getConnection();
			result = this.getBankNoInfo(bankID, date,conn, pageinfo);
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, null, conn);
		}
		return result;
	}
	public Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date date,Connection conn,int[] pageinfo) throws SQLException{
		log("查询市场有，银行没有的流水：");
		Vector<CompareResult> result = new Vector<CompareResult>();
		PreparedStatement state = null;
		String sql = null;
		ResultSet rs = null;
		CompareResult value = null;
		try{
			sql = "select fbc.firmid firmID,fbc.contact contact,fbf.account1 account1,fbf.account account,fbc.type type,fbc.money money " +
					",fbc.banktime tradeDate,fbc.actionid actionID,fbc.funid funID,fbc.bankid bankID,fbc.status status " +
					" from f_b_capitalinfo fbc,f_b_firmidandaccount fbf " +
					" where fbc.type in (0,1) and fbc.firmid = fbf.firmid(+) and fbc.bankid=fbf.bankid(+) and fbc.status not in ("+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") " +
					" and not exists (select funid from f_b_bankcompareinfo fbb where fbc.funid=fbb.funid " +
					" and fbc.contact=fbb.firmid and fbc.money=fbb.money and trunc(fbc.banktime)=trunc(fbb.comparedate) " +
					" and fbc.bankid=fbb.bankid and fbc.type=fbb.type and fbb.status="+ProcConstants.statusSuccess+") " +
					" and fbc.bankid='"+bankID+"' and trunc(createtime)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd')";
			log("查询市场有，银行没有的流水sql："+sql);
			if(pageinfo==null || pageinfo.length<=0){
				state = conn.prepareStatement(sql);
				rs = state.executeQuery();
			}else{
				rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			}
			while(rs.next()){
				value = new CompareResult();
				value.account = rs.getString("account");
				value.account1 = rs.getString("account1");
				value.actionID = rs.getLong("actionID");
				value.bankID = rs.getString("bankID");
				value.contact = rs.getString("contact");
				value.errorType = 3;
				value.firmID = rs.getString("firmID");
				value.funID = rs.getString("funID");
				value.money = rs.getDouble("money");
				value.tradeDate = rs.getDate("tradeDate");
				value.type = rs.getInt("type");
				value.status = rs.getInt("status");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}
		return result;
	}
	public Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date date,int[] pageinfo) throws SQLException,ClassNotFoundException{
		Vector<CompareResult> result = null;
		Connection conn = null;
		try{
			conn = this.getConnection();
			result = this.getMarketNoInfo(bankID, date, conn, pageinfo);
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(null, null, conn);
		}
		return result;
	}
	public Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date date,Connection conn,int[] pageinfo) throws SQLException{
		log("查询银行有，市场没有的流水");
		Vector<CompareResult> result = new Vector<CompareResult>();
		PreparedStatement state = null;
		String sql = null;
		ResultSet rs = null;
		CompareResult value = null;
		try{
			sql = "select fbf.firmid firmID,fbf.account1 account1,fbf.account account,fbb.firmid contact,fbb.funid funID,fbb.comparedate tradeDate," +
					"fbb.money money,fbb.type type,fbb.bankid bankID " +
					"from f_b_bankcompareinfo fbb,f_b_firmidandaccount fbf where fbb.firmid=fbf.contact(+) " +
					" and fbb.bankid=fbf.bankid(+) and fbb.status="+ProcConstants.statusSuccess+" " +
					" and not exists (select funid from f_b_capitalinfo fbc where fbb.funid=fbc.funid and fbb.firmid=fbc.contact " +
					" and fbb.bankid=fbc.bankid and fbb.type=fbc.type and fbb.money=fbc.money " +
					" and trunc(fbb.comparedate)=trunc(fbc.banktime) and fbc.status="+ProcConstants.statusSuccess+") " +
					" and fbb.bankid='"+bankID+"' and trunc(fbb.comparedate)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd')";
			log("查询银行有，市场没有的流水sql:"+sql);
			if(pageinfo==null || pageinfo.length<=0){
				state = conn.prepareStatement(sql);
				rs = state.executeQuery();
			}else{
				rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			}
			while(rs.next()){
				value = new CompareResult();
				value.account = rs.getString("account");
				value.account1 = rs.getString("account1");
				value.bankID = rs.getString("bankID");
				value.contact = rs.getString("contact");
				value.errorType = 2;
				value.firmID = rs.getString("firmID");
				value.funID = rs.getString("funID");
				value.money = rs.getDouble("money");
				value.tradeDate = rs.getDate("tradeDate");
				value.type = rs.getInt("type");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}
		return result;
	}
	public Vector<CompareSumMoney> sumCompareMoney(String bankID,java.util.Date date) throws SQLException,ClassNotFoundException{
		log("查询市场和银行转账成功出入金信息    sumCompareMoney  ");
		Vector<CompareSumMoney> result = new Vector<CompareSumMoney>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			CompareSumMoney bankmoney = new CompareSumMoney(bankID,date,1);
			CompareSumMoney marketmoney = new CompareSumMoney(bankID,date,2);
			result.add(bankmoney);
			result.add(marketmoney);
			conn = this.getConnection();
			String bank = "select count(*) rowsize,nvl(sum(info.money),0) money,info.type type,info.bankid bankID,bank.bankname bankname,1 mb from f_b_bankcompareinfo info,f_b_banks bank where info.bankid=bank.bankid(+) and info.status=0 and info.bankid='"+bankID+"' and trunc(info.comparedate)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') group by info.bankID,info.type,bank.bankname ";
			String market = "select count(*) rowsize,nvl(sum(info.money), 0) money,info.type type, info.bankid bankID, bank.bankname bankname, 2 mb from f_b_capitalinfo info, f_b_banks bank where info.bankid = bank.bankid(+) and info.status=0 and info.type in ("+ProcConstants.inMoneyType+","+ProcConstants.outMoneyType+") and info.bankid = '"+bankID+"' and trunc(info.banktime) = to_date('"+Tool.fmtDate(date)+"', 'yyyy-MM-dd') group by info.bankID, info.type, bank.bankname ";
			sql = bank + " union all " + market;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()){
				if(1==rs.getInt("mb")){//银行
					bankmoney.bankName = rs.getString("bankname");
					if(rs.getInt("type")==ProcConstants.inMoneyType){
						bankmoney.inMoneyCount = rs.getInt("rowsize");
						bankmoney.inMoney = rs.getDouble("money");
					}else{
						bankmoney.outMoneyCount = rs.getInt("rowsize");
						bankmoney.outMoney = rs.getDouble("money");
					}
				}else{//市场
					marketmoney.bankName = rs.getString("bankname");
					if(rs.getInt("type")==ProcConstants.inMoneyType){
						marketmoney.inMoneyCount = rs.getInt("rowsize");
						marketmoney.inMoney = rs.getDouble("money");
					}else{
						marketmoney.outMoneyCount = rs.getInt("rowsize");
						marketmoney.outMoney = rs.getDouble("money");
					}
				}
			}
			if(bankmoney.bankName == null){
				bankmoney.bankName = marketmoney.bankName;
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws SQLException,ClassNotFoundException{
		log("查询交易账号当天出入金求和数据，bankID["+bankID+"]firmIDs["+firmIDs+"]date["+date+"]");
		Vector<CapitalCompare> result = new Vector<CapitalCompare>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		String filter2 = null;
		String filter3 = null;
		if(bankID != null && bankID.trim().length()>0){
			filter += " and bankID='"+bankID.trim()+"' ";
		}
		if(firmIDs != null && firmIDs.length>0){
			String firms = "";
			for(String firmID : firmIDs){
				if(firmID != null && firmID.trim().length()>0){
					firms += "'" + firmID.trim() + "',";
				}
			}
			if(firms != null && firms.trim().length()>0){
				filter += " and firmID in ("+firms.substring(0, firms.lastIndexOf(','))+") ";
			}
		}
		filter2=filter;
		filter3=filter;
		if(date != null){
			filter2 += " and trunc(banktime)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') ";
			filter3 += " and trunc(comparedate)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') ";
		}
		try{
			sql = "select nvl(a.inmoney,0) minmoney,nvl(a.outmoney,0) moutmoney,nvl(b.inmoney,0) binmoney,nvl(b.outmoney,0) boutmoney,c.firmid firmid,case when a.tradedate is null then b.tradedate else a.tradedate end tradedate,case when a.bankid is null then b.bankid else a.bankid end bankid from " +
				"(select nvl(sum(case when type="+ProcConstants.inMoneyType+" then money else 0 end),0) inmoney,nvl(sum(case when type="+ProcConstants.outMoneyType+" then money else 0 end),0) outmoney,bankid,trim(CONTACT) firmid,trunc(banktime) tradedate from f_b_capitalinfo t where banktime is not null and status="+ProcConstants.statusSuccess+" "+filter2+" group by trunc(banktime),bankid,firmid,trim(CONTACT) order by trunc(banktime) desc,bankid,firmid) a, " +
				"(select nvl(sum(case when type="+ProcConstants.inMoneyType+" then money else 0 end),0) inmoney,nvl(sum(case when type="+ProcConstants.outMoneyType+" then money else 0 end),0) outmoney,trunc(comparedate) tradedate,trim(firmid) firmid,bankid from f_b_bankcompareinfo t where status="+ProcConstants.statusSuccess+" "+filter3+" group by trunc(comparedate),bankid,firmid order by trunc(comparedate) desc,bankid,firmid) b, " +
				"(select distinct firmid,tradedate from " +
					"(select distinct trim(CONTACT) firmid,trunc(banktime) tradedate from f_b_capitalinfo where 1=1 and status="+ProcConstants.statusSuccess+" " + filter2 +
					"union  " +
					"select distinct trim(firmid) firmid,trunc(comparedate) tradedate from f_b_bankcompareinfo where 1=1 and status="+ProcConstants.statusSuccess+" " + filter3 +
					")where firmid is not null " +
				"order by firmid) c " +
				"where c.firmid=a.firmid(+) and c.firmid=b.firmid(+) and c.tradedate=a.tradedate(+) and c.tradedate=b.tradedate(+) order by case when a.tradedate is null then b.tradedate else a.tradedate end desc,firmid";
			log("sql:"+sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while(rs.next()){
				CapitalCompare value = new CapitalCompare();
				value.firmID = rs.getString("firmid");
				value.bankID = rs.getString("bankid");
				value.bInmoney = rs.getDouble("binmoney");
				value.bOutmoney = rs.getDouble("boutmoney");
				value.mInmoney = rs.getDouble("minmoney");
				value.mOutmoney = rs.getDouble("moutmoney");
				value.tradeDate = rs.getDate("tradedate");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
//---------------------------------------光大清算
	
//---------------------------------------增加
	public Vector<FirmFundsBankValue> getFirmFundsBank(String firmID)throws SQLException,ClassNotFoundException{
		log("查询客户在每个银行的分资金信息  getFirmFundsBank  firmID["+firmID+"]");
		Vector<FirmFundsBankValue> result = new Vector<FirmFundsBankValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			conn = this.getConnection();
			sql = "select fbf.bankid bankID,fbb.bankname bankName,fbf.firmid firmID,fbf.contact contact,fbf.account account " +
				",fbf.account1 account1,ff.bankcode mainBank,ffb.lastbalance lastBalance,ffb.runtimefundio inOutMoney " +
				",FN_F_CanOutFunds(fbf.firmid,0,fbf.bankid) canOutFunds,fbf.frozenfuns frozenFunds " +
				" from f_firmfunds_bank ffb,f_firmfunds ff,f_b_firmidandaccount fbf,f_b_banks fbb " +
				" where fbf.bankid=ffb.bankcode(+) and fbf.isopen=1 and fbf.bankid=fbb.bankid(+) " +
				" and ffb.firmid='"+firmID+"' and ff.firmid='"+firmID+"' and fbf.firmid='"+firmID+"' " +
				" order by fbf.opentime ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			FirmFundsBankValue value = null;
			while(rs.next()){
				value = new FirmFundsBankValue();
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.firmID = rs.getString("firmID");
				value.contact = rs.getString("contact");
				value.account = rs.getString("account");
				value.account1 = rs.getString("account1");
				value.mainBank = rs.getString("mainBank");
				value.lastBalance = rs.getDouble("lastBalance");
				value.inOutMoney = rs.getDouble("inOutMoney");
				value.canOutFunds = rs.getDouble("canOutFunds");
				value.frozenFunds = rs.getDouble("frozenFunds");
				result.add(value);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public FirmFundsValue getFirmFunds(String firmID)throws SQLException,ClassNotFoundException{
		log("查询客户总资金信息  getFirmFunds  firmID["+firmID+"]");
		FirmFundsValue result = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try{
			conn = this.getConnection();
			sql = "select fbf.contact contact,ff.LastCapital lastBalance,ff.balance - ff.frozenfunds as firmFunds " +
				",nvl(ff.runtimefundio,0) as inOutFunds,FN_F_CanUseFunds(ff.firmid,0) canOutFunds " +
				" from f_b_firmuser fbf,f_firmfunds ff where fbf.firmid='"+firmID+"' and ff.firmid='"+firmID+"'";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if(rs.next()){
				result = new FirmFundsValue();
				result.firmID = firmID;
				result.contact = rs.getString("contact");
				result.firmFunds = rs.getDouble("firmFunds");
				result.lastBalance = rs.getDouble("lastBalance");
				result.inOutFunds = rs.getDouble("inOutFunds");
				result.canOutFunds = rs.getDouble("canOutFunds");
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	public Vector<FirmUserValue> getFirmUserList2(String filter,int[] pageinfo ,String bankId,String strKey) throws SQLException,ClassNotFoundException {
		log("查询客户预签约状态列表  getFirmUserList  ");
		Vector<FirmUserValue> resultlist = new Vector<FirmUserValue>();
		FirmUserValue result = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		
		try{
			conn = this.getConnection();
			
			if(filter != null && filter.indexOf("organizationno")>=0){
				sql = "select fbf.firmID firmid,fbf.contact contact,fbf.firmName firmname,fbf.cardType cardtype,fbf.card card,fbfi.value checkSignHX,fbf.status status,mf.firmType firmType " +
				",case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember " +
				" from f_b_firmuser fbf,m_firm mf,v_customerrelateorganization mc,(select * from f_b_firminfo where f_b_firminfo.bankid='"+bankId+"' and f_b_firminfo.key='"+strKey+"') fbfi " +
				" where fbf.firmid=mf.firmid(+) and fbf.firmid=fbfi.firmid(+) and mc.customerno=fbf.firmid " + filter ;
			}else if(filter != null && filter.indexOf("memberno")>=0){
				sql = "select fbf.firmID firmid,fbf.contact contact,fbf.firmName firmname,fbf.cardType cardtype,fbf.card card,fbfi.value checkSignHX,fbf.status status,mf.firmType firmType " +
				",case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember " +
				" from f_b_firmuser fbf,m_firm mf,m_customerinfo mc,(select * from f_b_firminfo where f_b_firminfo.bankid='"+bankId+"' and f_b_firminfo.key='"+strKey+"') fbfi " +
				" where fbf.firmid=mf.firmid(+) and fbf.firmid=fbfi.firmid(+) and mc.customerno=fbf.firmid " + filter ;
			}else{
				sql = "select fbf.firmID firmid,fbf.contact contact,fbf.firmName firmname,fbf.cardType cardtype,fbf.card card,fbfi.value checkSignHX,fbf.status status,mf.firmType firmType " +
				",case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember " +
				" from f_b_firmuser fbf,m_firm mf,(select * from f_b_firminfo where f_b_firminfo.bankid='"+bankId+"' and f_b_firminfo.key='"+strKey+"') fbfi " +
				" where fbf.firmid=mf.firmid(+) and fbf.firmid=fbfi.firmid(+) " + filter ;
			}
			System.out.println("sql________________bankdaoimpl_hxb--------:"+sql);
			log("sql________________bankdaoimpl_hxb--------:"+sql);
			rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			while(rs.next()){
				result = new FirmUserValue();
				result.firmID = rs.getString("firmid");
				result.contact = rs.getString("contact");
				result.firmName = rs.getString("firmname");
				result.cardType = rs.getString("cardtype");
				result.card = rs.getString("card");
				result.checkSignHX = rs.getString("checkSignHX");
				result.bankID = bankId;
				result.strkey = strKey;
				result.belevemember = rs.getString("belevemember");
				result.status = rs.getInt("status");
				result.firmType = rs.getString("firmType");
				resultlist.add(result);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			this.closeStatement(rs, state, conn);
		}
		
		return resultlist;
	}
}


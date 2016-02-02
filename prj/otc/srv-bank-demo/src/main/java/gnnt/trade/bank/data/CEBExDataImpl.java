package gnnt.trade.bank.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.FileOperate;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.Tool;

/**
 * 光大 清算文件实现类
 * 
 * @author tanglt 2011-5-29
 */
public class CEBExDataImpl implements ExchangeData {
	/** 银行编号 */
	private String bankID = "003";
	/** 银行机构代码 */
	private String bankCode = "3031100";
	/** 券商机构代码 */
	private String marketCode = "90010000";
	/** 证券机构分支号 */
	private String marketNum = "0001";
	private BankDAO DAO;
	private Connection conn = null;
	private PreparedStatement state = null;
	private ResultSet rs = null;
	private String sql = null;
	private FileOperate fo = new FileOperate();
	// 转账交易明细对账文件 字段长度数组
	private static final int[] fieldlen_CHK01 = { 8, 8, 4, 8, 6, 20, 20, 32, 15, 32, 1, 5, 3, 1, 16 };
	// 客户账户状态对账文件 字段长度数组
	private static final int[] fieldlen_CHK02 = { 8, 8, 4, 8, 32, 15, 32, 3, 1, 1 };
	// 客户账户状态对账文件 字段长度数组
	private static final int[] fieldlen_CHK03 = { 8, 8, 4, 8, 6, 20, 20, 22, 32, 15, 32, 2, 20, 1, 5, 3, 1, 16 };
	// 客户资金台账余额明细对账文件
	private static final int[] fieldlen_CHK04 = { 8, 8, 4, 8, 15, 32, 3, 1, 16 };
	// 客户资金交收明细文件
	private static final int[] fieldlen_DAT02 = { 8, 8, 4, 8, 15, 32, 3, 1, 16 };
	// 存管银行资金交收汇总文件
	private static final int[] fieldlen_DAT03 = { 8, 8, 32, 60, 8, 32, 60, 3, 1, 16 };

	public CEBExDataImpl(String bankID) {
		this.bankID = bankID;
	}

	/**
	 * 发送光大清算文件
	 */
	@Override
	public synchronized int getDataFile(BankAdapterRMI bankadapter, Date date) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 在处理器和适配器端生成光大清算文件
	 */
	@Override
	public synchronized int createDataFile(BankAdapterRMI bankadapter, Date date) {
		int result = 0;
		try {
			String basePath = getdir(date);
			System.out.println(basePath);
			if (!fo.newFolder(basePath)) {
				result = -1;
			}
			LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
			DAO = BankDAOFactory.getDAO();
			conn = DAO.getConnection();
			try {
				int S_CHK01 = createChk01(date, conn, basePath, bankadapter);
				map.put("S_CHK01", S_CHK01);
			} catch (Exception e) {
				result = -2;
				e.printStackTrace();
			}
			try {
				int S_DAT01 = createDAT01(date, conn, basePath, bankadapter);
				map.put("S_DAT01", S_DAT01);
			} catch (Exception e) {
				result = -8;
				e.printStackTrace();
			}
			try {
				int S_CHK02 = createChk02(date, conn, basePath, bankadapter);
				map.put("S_CHK02", S_CHK02);
			} catch (Exception e) {
				result = -3;
				e.printStackTrace();
			}
			try {
				int S_CHK03 = createChk03(date, conn, basePath, bankadapter);
				map.put("S_CHK03", S_CHK03);
			} catch (Exception e) {
				result = -4;
				e.printStackTrace();
			}
			try {
				int S_CHK04 = createChk04(date, conn, basePath, bankadapter);
				map.put("S_CHK04", S_CHK04);
			} catch (Exception e) {
				result = -5;
				e.printStackTrace();
			}
			try {
				int S_DAT02 = createDAT02(date, conn, basePath, bankadapter);
				map.put("S_DAT02", S_DAT02);
			} catch (Exception e) {
				result = -6;
				e.printStackTrace();
			}
			try {
				int S_DAT03 = createDAT03(date, conn, basePath, bankadapter);
				map.put("S_DAT03", S_DAT03);
			} catch (Exception e) {
				result = -7;
				e.printStackTrace();
			}
			try {
				createFileSize(date, map, basePath, bankadapter);
			} catch (Exception e) {
				result = -8;
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (DAO != null) {
				DAO.closeStatement(rs, state, conn);
			}
		}
		return result;
	}

	public static String fmtDouble2Money0(double num) {
		String res = Tool.fmtDouble2(num);
		res = res.replaceAll("\\.", "");
		if (num >= 0) {
			res = Common.fmtStrField(res, 16, "0", 0);
		} else {
			res = res.replaceAll("-", "");
			res = Common.fmtStrField(res, 15, "0", 0);
			res = "-" + res;
		}
		return res;
	}

	/**
	 * 生成Chk01并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createChk01(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		sql = "select to_char(t.banktime, 'yyyymmdd') as c3," + "to_char(t.banktime, 'hh24miss') as c4," + "to_char(t.funid) as c5,"
				+ "to_char(t.actionid) as c6," + "to_char(f.account) as c7," + "to_char(t.contact) as c8," + "to_char(f.accountname) as c9,"
				+ "(case t.launcher when 0 then 'S' when 1 then 'B' else 'U' end) as c10,"
				+ "(case t.type when 0 then '12001' when 1 then '12002' when 4 then '12003' when 5 then '12004' else '' end) as c11,"
				+ "t.money as c14 " + "from f_b_capitalinfo t, F_B_FIRMIDANDACCOUNT f " + "where t.bankID='" + bankID + "' and f.bankID='" + bankID
				+ "' and t.firmid = f.firmid and t.type in (0,1,4,5) and to_char(t.banktime,'yyyy-mm-dd')='" + Common.df2.format(date)
				+ "' and t.status=0 " + "order by t.banktime";

		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				content.append(Common.fmtStrField(Common.delNull(bankCode), fieldlen_CHK01[0], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketCode), fieldlen_CHK01[1], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketNum), fieldlen_CHK01[2], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c3")), fieldlen_CHK01[3], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c4")), fieldlen_CHK01[4], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c5")), fieldlen_CHK01[5], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c6")), fieldlen_CHK01[6], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c7")), fieldlen_CHK01[7], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c8")), fieldlen_CHK01[8], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c9")), fieldlen_CHK01[9], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c10")), fieldlen_CHK01[10], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c11")), fieldlen_CHK01[11], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("RMB"), fieldlen_CHK01[12], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("2"), fieldlen_CHK01[13], "0", 0) + "|");
				content.append(fmtDouble2Money0(rs.getDouble("c14")) + "|");
				content.append("\n");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAO.closeStatement(rs, state, null);
		}
		String fileName = "S_CHK01_" + Common.df7.format(date);
		String fileContent = content.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	/**
	 * 生成Chk02并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createChk02(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		sql = "select t.account as c4, t.contact as c5, t.accountname as c6,(case t.isopen when 1 then '0' when 2 then '2' else '' end) as c9  "
				+ "from f_b_firmidandaccount t " + "where t.bankID='" + bankID + "' and (to_char(t.opentime,'yyyy-mm-dd')='" + Common.df2.format(date)
				+ "' or to_char(t.deltime,'yyyy-mm-dd')='" + Common.df2.format(date) + "')";

		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				content.append(Common.fmtStrField(Common.delNull(bankCode), fieldlen_CHK02[0], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketCode), fieldlen_CHK02[1], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketNum), fieldlen_CHK02[2], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(Common.df7.format(date)), fieldlen_CHK02[3], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c4")), fieldlen_CHK02[4], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c5")), fieldlen_CHK02[5], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c6")), fieldlen_CHK02[6], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("RMB"), fieldlen_CHK02[7], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("2"), fieldlen_CHK02[8], "0", 0) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c9")), fieldlen_CHK02[9], " ", 1) + "|");
				content.append("\n");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAO.closeStatement(rs, state, null);
		}
		String fileName = "S_CHK02_" + Common.df7.format(date);
		String fileContent = content.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	/**
	 * 生成Chk03并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createChk03(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		// sql =
		// "select t.account as c4, t.contact as c5, t.accountname as c6,(case t.isopen when 1 then '0' when 2 then '2' else '' end) as c9 "
		// +
		// "from f_b_firmidandaccount t " +
		// "where to_char(t.opentime,'yyyy-mm-dd')='"+Common.df2.format(date)+"' or to_char(t.deltime,'yyyy-mm-dd')='"+Common.df2.format(date)+"'";

		String time = "";
		String code = "";
		sql = "";
		time = "opentime";
		code = "'11001'";
		sql += " select to_char(" + time + ",'yyyyMMdd') c3,to_char(" + time
				+ ",'hh24miss') c4,account c7,account c8,contact c9,accountName c10,cardtype c11,card c12," + code
				+ " c14 from f_b_firmidandaccount where bankID='" + bankID + "' and trunc(" + time + ")=to_date('" + Tool.fmtDate(date)
				+ "','yyyy-MM-dd') ";
		sql += " union all ";
		time = "modtime";
		code = "'11006'";
		sql += " select to_char(" + time + ",'yyyyMMdd') c3,to_char(" + time
				+ ",'hh24miss') c4,account c7,account c8,contact c9,accountName c10,cardtype c11,card c12," + code
				+ " c14 from f_b_firmidandaccount where bankID='" + bankID + "' and trunc(" + time + ")=to_date('" + Tool.fmtDate(date)
				+ "','yyyy-MM-dd') ";
		sql += " union all ";
		time = "deltime";
		code = "'11004'";
		sql += " select to_char(" + time + ",'yyyyMMdd') c3,to_char(" + time
				+ ",'hh24miss') c4,account c7,account c8,contact c9,accountName c10,cardtype c11,card c12," + code
				+ " c14 from f_b_firmidandaccount where bankID='" + bankID + "' and trunc(" + time + ")=to_date('" + Tool.fmtDate(date)
				+ "','yyyy-MM-dd') ";

		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				content.append(Common.fmtStrField(Common.delNull(bankCode), fieldlen_CHK03[0], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketCode), fieldlen_CHK03[1], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketNum), fieldlen_CHK03[2], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c3")), fieldlen_CHK03[3], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c4")), fieldlen_CHK03[4], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_CHK03[5], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_CHK03[6], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c7")), fieldlen_CHK03[7], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c8")), fieldlen_CHK03[8], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c9")), fieldlen_CHK03[9], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c10")), fieldlen_CHK03[10], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c11")), fieldlen_CHK03[11], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c12")), fieldlen_CHK03[12], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_CHK03[13], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c14")), fieldlen_CHK03[14], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("RMB"), fieldlen_CHK03[15], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_CHK03[16], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_CHK03[17], "0", 0) + "|");
				content.append("\n");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAO.closeStatement(rs, state, null);
		}
		String fileName = "S_CHK03_" + Common.df7.format(date);
		String fileContent = content.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	/**
	 * 生成Chk04并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createChk04(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		sql = "select to_char(ffb.b_date,'yyyyMMdd') c3,fbf.contact c4,fbf.accountName c5,ffb.capital*100 c8 "
				+ "from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf " + "where fbf.firmID=ffb.firmID(+) " + "and fbf.bankID='" + bankID
				+ "' and fbf.isOpen=1 and ffb.bankcode = '" + bankID + "' " + " and trunc(ffb.b_date)=to_date('" + Tool.fmtDate(date)
				+ "','yyyy-MM-dd')";
		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				content.append(Common.fmtStrField(Common.delNull(bankCode), fieldlen_CHK04[0], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketCode), fieldlen_CHK04[1], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketNum), fieldlen_CHK04[2], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c3")), fieldlen_CHK04[3], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c4")), fieldlen_CHK04[4], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c5")), fieldlen_CHK04[5], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("RMB"), fieldlen_CHK04[6], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("2"), fieldlen_CHK04[7], " ", 1) + "|");
				content.append(Common.fmtStrField(rs.getLong("c8"), fieldlen_CHK04[8], "0", 0) + "|");
				content.append("\n");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAO.closeStatement(rs, state, null);
		}
		String fileName = "S_CHK04_" + Common.df7.format(date);
		String fileContent = content.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	/**
	 * 生成DAT01并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createDAT01(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		String fileName = "S_DAT01_" + Common.df7.format(date);
		String fileContent = "";
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	/**
	 * 生成DAT02并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createDAT02(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		sql = "select to_char(ffb.b_date,'yyyyMMdd') c3,fbf.contact c4,fbf.accountName c5,(ffb.capital-ffb.lastcapital-ffb.fundio)*100 c8 "
				+ "from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf " + "where fbf.firmID=ffb.firmID(+) " + "and fbf.bankID='" + bankID
				+ "' and fbf.isOpen=1 and ffb.bankcode = '" + bankID + "' " + " and trunc(ffb.b_date)=to_date('" + Tool.fmtDate(date)
				+ "','yyyy-MM-dd') ";
		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				content.append(Common.fmtStrField(Common.delNull(bankCode), fieldlen_DAT02[0], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketCode), fieldlen_DAT02[1], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(marketNum), fieldlen_DAT02[2], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c3")), fieldlen_DAT02[3], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c4")), fieldlen_DAT02[4], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c5")), fieldlen_DAT02[5], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("RMB"), fieldlen_DAT02[6], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("2"), fieldlen_DAT02[7], " ", 1) + "|");
				content.append(Common.fmtStrField(rs.getLong("c8"), fieldlen_DAT02[8], "0", 0) + "|");
				content.append("\n");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAO.closeStatement(rs, state, null);
		}
		String fileName = "S_DAT02_" + Common.df7.format(date);
		String fileContent = content.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	/**
	 * 生成DAT03并发送给适配器
	 * 
	 * @param date
	 * @param conn
	 * @param path
	 * @param bankadapter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @author tanglt 2011-5-29
	 */
	private int createDAT03(Date date, Connection conn, String path, BankAdapterRMI bankadapter) throws SQLException, IOException {
		int result = 0;
		// sql = "select to_char(ffb.b_date,'yyyyMMdd') c1,sum((ffb.FundIO+ffb.TransferFund+ffb.TradeDiff)*100) c9 "
		// + "from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf " + "where fbf.firmID=ffb.firmID(+) and fbf.bankID='" + bankID + "' and
		// fbf.isOpen=1 "
		// + " and trunc(ffb.b_date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') group by to_char(ffb.b_date,'yyyyMMdd')";
		sql = "select to_char(ffb.b_date,'yyyyMMdd') c1,sum((ffb.capital-ffb.lastcapital-ffb.fundio)*100) c9 "
				+ "from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf " + "where fbf.firmID=ffb.firmID(+) and fbf.bankID='" + bankID
				+ "'and ffb.bankcode='" + bankID + "' and fbf.isOpen=1 " + " and trunc(ffb.b_date)=to_date('" + Tool.fmtDate(date)
				+ "','yyyy-MM-dd') group by to_char(ffb.b_date,'yyyyMMdd')";
		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				content.append(Common.fmtStrField(Common.delNull(marketCode), fieldlen_DAT03[0], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(rs.getString("c1")), fieldlen_DAT03[1], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("168026256"), fieldlen_DAT03[2], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_DAT03[3], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_DAT03[4], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_DAT03[5], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull(""), fieldlen_DAT03[6], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("RMB"), fieldlen_DAT03[7], " ", 1) + "|");
				content.append(Common.fmtStrField(Common.delNull("2"), fieldlen_DAT03[8], " ", 1) + "|");
				content.append(Common.fmtStrField(rs.getLong("c9"), fieldlen_DAT03[9], "0", 0) + "|");
				content.append("\n");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAO.closeStatement(rs, state, null);
		}
		String fileName = "S_DAT03_" + Common.df7.format(date);
		String fileContent = content.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	private int createFileSize(Date date, LinkedHashMap<String, Integer> map, String path, BankAdapterRMI bankadapter) throws Exception {
		int result = 0;
		String fileName = Common.df7.format(date) + "证券清算文件.txt";
		StringBuilder sb = new StringBuilder();
		if (map != null && map.size() > 0) {
			Iterator<String> it = map.keySet().iterator();
			if (it != null) {
				while (it.hasNext()) {
					String name = it.next();
					sb.append(name + "_" + Common.df7.format(date) + "|" + map.get(name) + "|" + Common.df9.format(new java.util.Date()) + "|"
							+ Common.df10.format(new java.util.Date()) + "\n");
				}
			}
		}
		String fileContent = sb.toString();
		result = fileContent.length();
		if (fo.newFile(path + "/" + fileName, fileContent)) {
			// TODO bankadapter.createDataFile(fileName, fileContent, date);
		}
		return result;
	}

	private String getdir(Date date) {
		String userdir = null;// this.getClass().getClassLoader().getResource("").getPath();
		userdir = Tool.getConfig(ProcConstants.cebqsdir);
		if (userdir == null || userdir.trim().length() <= 0) {
			userdir = "/home";
		}
		if (!"/".equalsIgnoreCase(userdir.trim().substring(userdir.trim().length() - 1, userdir.trim().length()))) {
			userdir = userdir.trim() + "/";
		}
		String result = userdir + "dayData/CEB_sendFile/" + Common.df7.format(date);
		return result;
	}

	public static void main(String args[]) throws IOException {
		// CEBExDataImpl impl = new CEBExDataImpl("101");
		// impl.createDataFile(null, Tool.getDateTime("20110725"));
		// System.exit(0);
		// String sql = "select to_char(ffb.b_date,'yyyyMMdd') c3,fbf.contact c4,fbf.accountName c5,ffb.capital*100 c8 "
		// + "from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf " + "where fbf.firmID=ffb.firmID(+) and fbf.bankID='" + "003" + "' and fbf.isOpen=1
		// "
		// + " and trunc(ffb.b_date)=to_date('" + Tool.fmtDate(new Date()) + "','yyyy-MM-dd')";
		String sql = "select t.account as c4, t.contact as c5, t.accountname as c6,(case t.isopen when 1 then '0' when 2 then '2' else '' end) as c9  "
				+ "from f_b_firmidandaccount t " + "where t.bankID='" + "003" + "' and (to_char(t.opentime,'yyyy-mm-dd')='"
				+ Common.df2.format(new Date()) + "' or to_char(t.deltime,'yyyy-mm-dd')='" + Common.df2.format(new Date()) + "')";
		System.out.println(sql);
		StringBuffer content = new StringBuffer();
		FileOperate fo = new FileOperate();
		String fileName = "S_DAT03_" + Common.df7.format(new Date());
		fo.newFile("c:" + "/" + fileName, content.toString());
	}

}

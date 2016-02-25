package gnnt.bank.otc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import gnnt.bank.otc.util.Util;
import gnnt.trade.bank.vo.ReturnValue;

public class DataProcess {
	private int INSUMMARY;
	private int OUTSUMMARY;
	private int FEESUMMARY;
	private int CHSUMMARY;
	private Hashtable<String, String> BANKSUB = new Hashtable();

	protected Hashtable<String, String> getBANKSUB() {
		return this.BANKSUB;
	}

	protected int getFEESUMMARY() {
		return this.FEESUMMARY;
	}

	protected int getINSUMMARY() {
		return this.INSUMMARY;
	}

	protected int getOUTSUMMARY() {
		return this.OUTSUMMARY;
	}

	protected int getCHSUMMARY() {
		return this.CHSUMMARY;
	}

	protected int openAccount(String firmID, String bankID, Connection conn) throws SQLException {
		int result = -1;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{?=call FN_CUSTOMERADDANDACTIVE(?,?)}");
			proc.setString(2, firmID);
			proc.setString(3, bankID);
			proc.registerOutParameter(1, 4);
			proc.executeQuery();
			result = proc.getInt(1);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			Util.log(Util.getExceptionTrace(e));
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					Util.log(Util.getExceptionTrace(e1));
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		Util.log("调用交易系统签约激活存储firmID[" + firmID + "]bankID[" + bankID + "]result[" + result + "]");
		return result;
	}

	protected int ifFirmDelAccount(String firmID, String bankID, Connection conn) throws SQLException {
		int result = -1;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{?=call FN_F_CanUnSign(?,?)}");
			proc.setString(2, firmID);
			proc.setString(3, bankID);
			proc.registerOutParameter(1, 4);
			proc.executeQuery();
			result = proc.getInt(1);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			Util.log(Util.getExceptionTrace(e));
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					Util.log(Util.getExceptionTrace(e1));
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		Util.log("判断客户是否可以解约   ifFirmDelAccount   firmID[" + firmID + "]bankID[" + bankID + "]   FN_F_CanUnSign   返回结果[" + result + "]");
		return result;
	}

	protected ReturnValue changeFirmIsOpen(String firmID, int flag, String bankID, Connection conn) throws SQLException {
		Util.log("修改客户签约状态 FN_M_FirmSign firmID[" + firmID + "]flag[" + flag + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_M_FirmSign(?,?,?) }");
			proc.setString(2, firmID);
			proc.setInt(3, flag);
			proc.setString(4, bankID);
			proc.registerOutParameter(1, 4);
			proc.executeQuery();
			rv.result = proc.getInt(1);
			if (rv.result >= 0L) {
				rv.remark = "交易系统签约";
			} else if (rv.result == -1L) {
				if (flag == 1) {
					rv.remark = "客户已终止";
				} else {
					rv.remark = "会员未终止";
				}
			} else if (rv.result == -900L) {
				rv.remark = "客户状态不正确";
			} else if (rv.result == -901L) {
				rv.remark = "资金不为0";
			} else if (rv.result == -902L) {
				rv.remark = "持仓不为0";
			} else if (rv.result == -903L) {
				rv.remark = "不存在的客户";
			} else if (rv.result == -904L) {
				rv.remark = "会员状态不正确";
			} else if (rv.result == -905L) {
				rv.remark = "资金不为0";
			} else if (rv.result == -906L) {
				rv.remark = "客户数不为0";
			} else if (rv.result == -907L) {
				rv.remark = "持仓不为0";
			} else if (rv.result == -908L) {
				rv.remark = "会员状态不正确";
			} else if (rv.result == -909L) {
				rv.remark = "资金不为0";
			} else if (rv.result == -910L) {
				rv.remark = "客户数不为0";
			} else if (rv.result == -911L) {
				rv.remark = "持仓不为0";
			} else if (rv.result == -912L) {
				rv.remark = "会员不存在";
			} else if (rv.result == -1000L) {
				rv.remark = "资金不足";
			} else if (rv.result == -1002L) {
				rv.remark = "机构数不为0";
			} else if (rv.result == -1003L) {
				rv.remark = "佣金不为0";
			} else {
				rv.remark = "交易系统签(解)约失败";
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			rv.result = -1L;
			rv.remark = "调用交易系统时系统异常";
			Util.log(Util.getExceptionTrace(e));
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					Util.log(Util.getExceptionTrace(e1));
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		Util.log("修改客户签约状态 FN_M_FirmSign firmID[" + firmID + "]flag[" + flag + "]bankID[" + bankID + "]返回值[" + rv.toString() + "]");
		return rv;
	}

	protected double getRealFunds(String bankID, String firmID, int type, Connection conn) throws SQLException {
		Util.log("获取可出金额 getRealFunds bankID[" + bankID + "]firmID[" + firmID + "]");
		double result = 0.0D;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_F_CanOutFunds(?,?,?) }");
			proc.setString(2, firmID);
			proc.setInt(3, type);
			proc.setString(4, bankID);
			proc.registerOutParameter(1, 8);
			proc.executeQuery();
			result = proc.getDouble(1);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			Util.log(Util.getExceptionTrace(e));
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					Util.log(Util.getExceptionTrace(e1));
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		return result;
	}

	protected double updateFrozenFunds(String firmID, double money, Connection conn) throws SQLException {
		double result = 0.0D;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
			proc.setString(2, firmID);
			proc.setDouble(3, money);
			proc.setString(4, "1");
			proc.registerOutParameter(1, 8);
			proc.executeUpdate();
			result = proc.getDouble(1);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			Util.log(Util.getExceptionTrace(e));
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					Util.log(Util.getExceptionTrace(e1));
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		return result;
	}

	protected double updateFundsFull(String bankID, String firmID, String contractno, double money, int type, Connection conn) throws SQLException {
		Util.log("修改交易账号资金bankID[" + bankID + "] firmID[" + firmID + "]contractno[" + contractno + "]money[" + money + "]type[" + type + "]");
		double result = 0.0D;
		if (type == 0) {
			result = inMoneyFunds(bankID, firmID, contractno, money, conn);
		} else if (type == 1) {
			result = outMoneyFunds(bankID, firmID, contractno, money, conn);
		} else if (type == 4) {
			result = inMoneyFunds(bankID, firmID, contractno, -1.0D * money, conn);
		} else if (type == 5) {
			result = outMoneyFunds(bankID, firmID, contractno, -1.0D * money, conn);
		} else {
			throw new SQLException("修改交易账号[" + firmID + "]资金，传入的类型为[" + type + "]不识别");
		}
		return result;
	}

	protected double updateFundsFull(String firmID, String summary, String subject, double money, long actionID, Connection conn)
			throws SQLException {
		System.out.println("firmID[" + firmID + "]summary[" + summary + "]subject[" + subject + "]money[" + money + "]actionID[" + actionID + "]");
		double result = 0.0D;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call  FN_F_UpdateFundsFull(?,?,?,?,?,null,null) }");
			proc.setString(2, firmID);
			proc.setString(3, summary);
			proc.setDouble(4, money);
			proc.setLong(5, actionID);
			proc.setString(6, subject);
			proc.registerOutParameter(1, 8);
			proc.executeQuery();
			result = proc.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private double inMoneyFunds(String bankID, String firmID, String contractno, double money, Connection conn) throws SQLException {
		Util.log("调用交易系统入金存储过程 bankID[" + bankID + "]firmID[" + firmID + "]contractno[" + contractno + "]money[" + money + "]");
		double result = 0.0D;
		String sql = "";
		if ("true".equalsIgnoreCase(Util.getConfig("actionInto"))) {
			sql = "{ ?=call FN_F_Fund_In(?,?,?,?)}";
		} else {
			sql = "{ ?=call FN_F_Fund_In(?,?,?)}";
		}
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall(sql);
			proc.setString(2, firmID);
			proc.setDouble(3, money);
			proc.setString(4, bankID);
			if ("true".equalsIgnoreCase(Util.getConfig("actionInto"))) {
				proc.setString(5, contractno);
			}
			proc.registerOutParameter(1, 8);
			proc.executeQuery();
			result = proc.getDouble(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		return result;
	}

	private double outMoneyFunds(String bankID, String firmID, String contractno, double money, Connection conn) throws SQLException {
		Util.log("调用交易系统出金存储过程bankID[" + bankID + "]firmID[" + firmID + "]contractno[" + contractno + "]money[" + money + "]");
		double result = 0.0D;
		String sql = "";
		if ("true".equalsIgnoreCase(Util.getConfig("actionInto"))) {
			sql = "{ ?=call FN_F_Fund_Out(?,?,?,?)}";
		} else {
			sql = "{ ?=call FN_F_Fund_Out(?,?,?)}";
		}
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall(sql);
			proc.setString(2, firmID);
			proc.setDouble(3, money);
			proc.setString(4, bankID);
			if ("true".equalsIgnoreCase(Util.getConfig("actionInto"))) {
				proc.setString(5, contractno);
			}
			proc.registerOutParameter(1, 8);
			proc.executeQuery();
			result = proc.getDouble(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					Util.log(Util.getExceptionTrace(e));
				}
			}
		}
		return result;
	}
}

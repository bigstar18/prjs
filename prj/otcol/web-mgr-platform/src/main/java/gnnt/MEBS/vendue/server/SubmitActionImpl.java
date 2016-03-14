package gnnt.MEBS.vendue.server;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.naming.NamingException;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.BroadcastVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.CurSubmitVO;
import gnnt.MEBS.vendue.server.vo.TradeQuotationVO;
import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.util.Configuration;

public class SubmitActionImpl extends SubmitAction {
	protected void setTradeDAO(TradeDAO paramTradeDAO) {
		this.TRADEDAO = paramTradeDAO;
	}

	public BargainVO[] getBargainList(String paramString) throws SQLException {
		BargainVO[] arrayOfBargainVO = this.TRADEDAO.getBargainList("where submitID in (select id from v_curSubmit where traderId='" + paramString
				+ "') and tradepartition=" + this.PARTITIONID + " order by contractID desc");
		return arrayOfBargainVO;
	}

	public BargainVO getBargain(long paramLong) throws SQLException {
		return this.TRADEDAO.getCurBargain(paramLong);
	}

	public CommodityVO[] getCurCommodityList() {
		CommodityVO[] arrayOfCommodityVO = (CommodityVO[]) null;
		Vector localVector = new Vector();
		try {
			KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
			arrayOfCommodityVO = localKernelEngine.getCurCommodityList();
			TradeStatusValue localTradeStatusValue = localKernelEngine.getTradeStatus();
			long l = System.currentTimeMillis();
			if ((localTradeStatusValue.getCurStatus() == 2) && (arrayOfCommodityVO != null)) {
				for (int i = 0; i < arrayOfCommodityVO.length; i++) {
					if ((localTradeStatusValue.getLastPartID() == arrayOfCommodityVO[i].section) && (arrayOfCommodityVO[i].bargainFlag == 0)) {
						arrayOfCommodityVO[i].countDownTime = localKernelEngine.getNewCountdown(arrayOfCommodityVO[i].code);
						if (arrayOfCommodityVO[i].countDownTime > 0) {
							if (arrayOfCommodityVO[i].lastTime != null) {
								arrayOfCommodityVO[i].countDownTime = ((int) ((arrayOfCommodityVO[i].lastTime.getTime()
										+ arrayOfCommodityVO[i].countDownTime * 1000 - l) / 1000L));
							} else {
								arrayOfCommodityVO[i].countDownTime = ((int) ((localTradeStatusValue.getPartEndTime().getTime()
										- localTradeStatusValue.getCountdownTime() * 1000 + arrayOfCommodityVO[i].countDownTime * 1000 - l) / 1000L));
							}
						}
						localVector.add(arrayOfCommodityVO[i]);
					}
				}
			}
			arrayOfCommodityVO = new CommodityVO[localVector.size()];
			for (int i = 0; i < arrayOfCommodityVO.length; i++) {
				arrayOfCommodityVO[i] = ((CommodityVO) localVector.get(i));
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return arrayOfCommodityVO;
	}

	public CommodityVO getCurCommodity(String paramString) {
		CommodityVO localCommodityVO = null;
		try {
			KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
			localCommodityVO = localKernelEngine.getCurCommodity(paramString);
			TradeStatusValue localTradeStatusValue = localKernelEngine.getTradeStatus();
			if (localTradeStatusValue.getCurStatus() != 2) {
				localCommodityVO = null;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localCommodityVO;
	}

	public String getLastXML(String paramString1, String paramString2) throws SQLException {
		String str1 = "";
		String str2 = "";
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
		DecimalFormat localDecimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
		localDecimalFormat.applyPattern("###0.00");
		CurSubmitVO[] arrayOfCurSubmitVO = this.TRADEDAO
				.getCurSubmitList("where To_date('" + paramString2 + "','yyyy-MM-dd/HH24:mi:ss')<modifytime and traderid='" + paramString1
						+ "' and tradepartition=" + this.PARTITIONID + " order by modifytime desc");
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<R>");
		if (arrayOfCurSubmitVO != null) {
			for (int i = 0; i < arrayOfCurSubmitVO.length; i++) {
				CurSubmitVO localCurSubmitVO = arrayOfCurSubmitVO[i];
				if (str2.equals("")) {
					str2 = localSimpleDateFormat.format(localCurSubmitVO.modifytime);
				}
				localStringBuffer.append("<S>");
				localStringBuffer.append("<SID>" + localCurSubmitVO.iD + "</SID>");
				localStringBuffer.append("<C>" + localCurSubmitVO.code + "</C>");
				localStringBuffer.append("<P>" + localDecimalFormat.format(localCurSubmitVO.price) + "</P>");
				localStringBuffer.append("<A>" + localCurSubmitVO.amount + "</A>");
				localStringBuffer.append("<VA>" + localCurSubmitVO.validAmount + "</VA>");
				localStringBuffer.append("<ST>" + localSimpleDateFormat.format(localCurSubmitVO.submitTime) + "</ST>");
				localStringBuffer.append("<MT>" + localSimpleDateFormat.format(localCurSubmitVO.modifytime) + "</MT>");
				localStringBuffer.append("</S>");
			}
		}
		if (str2.equals("")) {
			str2 = paramString2;
		}
		localStringBuffer.append("<T>" + str2 + "</T>");
		localStringBuffer.append("</R>");
		str1 = localStringBuffer.toString();
		return str1;
	}

	public BargainVO[] getMarketBargain() throws SQLException {
		BargainVO[] arrayOfBargainVO = this.TRADEDAO.getBargainList("where tradepartition=" + this.PARTITIONID + " order by contractID desc");
		return arrayOfBargainVO;
	}

	public CurSubmitVO[] getSubmit(String paramString) throws SQLException {
		CurSubmitVO[] arrayOfCurSubmitVO = this.TRADEDAO
				.getCurSubmitList("where traderID='" + paramString + "' and tradepartition=" + this.PARTITIONID + " order by modifytime desc");
		return arrayOfCurSubmitVO;
	}

	public TradeUserVO getTradeUser(String paramString) throws SQLException {
		TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUser(paramString);
		return localTradeUserVO;
	}

	public BroadcastVO getBroadcast(long paramLong) throws SQLException {
		return this.TRADEDAO.getBroadcast(paramLong);
	}

	public int chgTraderPwd(String paramString1, String paramString2, String paramString3) throws SQLException {
		int i = -1;
		LogonManager localLogonManager = LogonManager.getInstance();
		i = localLogonManager.changePassowrd(paramString1, paramString2, paramString3);
		return i;
	}

	protected void setPartition(int paramInt) {
		this.PARTITIONID = paramInt;
	}

	public int submit(String paramString1, String paramString2, String paramString3, double paramDouble, long paramLong)
			throws Exception, SQLException, ClassNotFoundException, IllegalAccessException, NamingException, InstantiationException {
		KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
		CommodityVO localCommodityVO = localKernelEngine.getCurCommodity(paramString3);
		CallableStatement localCallableStatement = null;
		int i = localKernelEngine.getTradeStatus().getLastPartID();
		if ((localCommodityVO == null) || (localCommodityVO.bargainFlag == 1) || (localCommodityVO.section != i)) {
			return 5;
		}
		Configuration localConfiguration = new Configuration();
		int j = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("Myself"));
		if ((j == 0) && (paramString1.equals(localCommodityVO.userID))) {
			return 8;
		}
		BigDecimal localBigDecimal1 = new BigDecimal(paramDouble).setScale(10, 4);
		BigDecimal localBigDecimal2 = new BigDecimal(localCommodityVO.beginPrice).setScale(10, 4);
		BigDecimal localBigDecimal3 = new BigDecimal(localCommodityVO.stepPrice).setScale(10, 4);
		if (this.PARTITIONID == 1) {
			if ((paramDouble > localCommodityVO.alertPrice) || (paramDouble < localCommodityVO.beginPrice)
					|| (paramDouble <= localCommodityVO.lastPrice)
					|| (localBigDecimal1.subtract(localBigDecimal2).remainder(localBigDecimal3).doubleValue() > 0.0D)) {
				return 1;
			}
		} else if ((paramDouble < localCommodityVO.alertPrice) || (paramDouble > localCommodityVO.beginPrice)
				|| ((paramDouble >= localCommodityVO.lastPrice) && (localCommodityVO.lastPrice > 0.0D))
				|| (localBigDecimal1.subtract(localBigDecimal2).remainder(localBigDecimal3).doubleValue() > 0.0D)) {
			return 1;
		}
		if (paramLong <= 0L) {
			return 7;
		}
		int k = localKernelEngine.getNewCountdown(paramString3);
		if (k == 0) {
			return 3;
		}
		int m = -1;
		synchronized (localCommodityVO) {
			int n = 0;
			try {
				k = localKernelEngine.getNewCountdown(paramString3);
				TradeUserVO localTradeUserVO1 = getTradeUser(paramString1);
				if (localBigDecimal1.subtract(localBigDecimal2).remainder(localBigDecimal3).doubleValue() > 0.0D) {
					if (this.PARTITIONID == 1) {
						if (paramDouble <= localCommodityVO.lastPrice) {
							m = 1;
						}
					} else if ((paramDouble >= localCommodityVO.lastPrice) && (localCommodityVO.lastPrice > 0.0D)) {
						m = 1;
					}
				} else if (paramLong <= 0L) {
					m = 7;
				} else if ((localTradeUserVO1 == null)
						|| ((paramString1.equals(localCommodityVO.lastUserID)) && (paramString2.equals(localCommodityVO.lastTraderID)))) {
					m = 4;
				} else if (k == 0) {
					m = 3;
				} else {
					Connection localConnection = null;
					double d1 = 0.0D;
					double d2 = 0.0D;
					Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
					try {
						synchronized (this.TRADELOCK) {
							localConnection = this.TRADEDAO.getConnection();
							localConnection.setAutoCommit(false);
							TradeUserVO localTradeUserVO2 = this.TRADEDAO.getTradeUserForUpdate(paramString1, localConnection);
							TradeUserVO localTradeUserVO3 = this.TRADEDAO.getTradeUser(localCommodityVO.lastUserID, localConnection);
							d1 = localKernelEngine.getBail(paramString3, paramDouble, paramLong, paramString1, localConnection)
									+ localKernelEngine.getFee(paramString3, paramDouble, paramLong, paramString1, localConnection);
							if (localTradeUserVO3 != null) {
								d2 = localKernelEngine.getBail(paramString3, localCommodityVO.lastPrice, paramLong, localCommodityVO.lastUserID,
										localConnection)
										+ localKernelEngine.getFee(paramString3, localCommodityVO.lastPrice, paramLong, localCommodityVO.lastUserID,
												localConnection);
							}
							if (localTradeUserVO2.balance - localTradeUserVO2.frozenCapital + localTradeUserVO2.overdraft - d1 < 0.0D) {
								localConnection.rollback();
								m = 2;
							} else {
								localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
								localCallableStatement.setString(2, localTradeUserVO2.userCode);
								localCallableStatement.setDouble(3, d1);
								localCallableStatement.setString(4, "4");
								localCallableStatement.registerOutParameter(1, 8);
								localCallableStatement.executeUpdate();
								if (localTradeUserVO3 != null) {
									localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
									localCallableStatement.setString(2, localTradeUserVO3.userCode);
									localCallableStatement.setDouble(3, -1.0D * d2);
									localCallableStatement.setString(4, "4");
									localCallableStatement.registerOutParameter(1, 8);
									localCallableStatement.executeUpdate();
								}
								CurSubmitVO localCurSubmitVO = new CurSubmitVO();
								localCurSubmitVO.amount = paramLong;
								localCurSubmitVO.code = paramString3;
								localCurSubmitVO.price = paramDouble;
								localCurSubmitVO.submitTime = localTimestamp;
								localCurSubmitVO.tradeFlag = 1;
								localCurSubmitVO.tradePartition = this.PARTITIONID;
								localCurSubmitVO.userID = paramString1;
								localCurSubmitVO.traderID = paramString2;
								localCurSubmitVO.validAmount = paramLong;
								localCurSubmitVO.modifytime = localTimestamp;
								long l = this.TRADEDAO.addCurSubmit(localCurSubmitVO, localConnection);
								localCurSubmitVO = this.TRADEDAO.getCurSubmit(localCommodityVO.lastSubmitID);
								if (localCurSubmitVO != null) {
									localCurSubmitVO.validAmount = 0L;
									localCurSubmitVO.tradeFlag = 0;
									localCurSubmitVO.modifytime = localTimestamp;
									this.TRADEDAO.modifyCurSubmit(localCurSubmitVO, localConnection);
								}
								this.TRADEDAO.delTradeQuotation("where tradePartition=" + this.PARTITIONID + " and code='" + paramString3 + "'",
										localConnection);
								TradeQuotationVO localTradeQuotationVO = new TradeQuotationVO();
								localTradeQuotationVO.code = paramString3;
								localTradeQuotationVO.countdownTime = k;
								localTradeQuotationVO.lastTime = localTimestamp;
								localTradeQuotationVO.price = paramDouble;
								localTradeQuotationVO.section = i;
								localTradeQuotationVO.submitID = l;
								localTradeQuotationVO.tradePartition = this.PARTITIONID;
								localTradeQuotationVO.userID = paramString1;
								localTradeQuotationVO.vaidAmount = paramLong;
								this.TRADEDAO.addTradeQuotation(localTradeQuotationVO, localConnection);
								localConnection.commit();
								localKernelEngine.touch(paramString3, paramLong, paramDouble, paramString1, localTimestamp, l);
								if (k > 0) {
									localKernelEngine.getTradeStatus().setPartLastEndTime(new Timestamp(localTimestamp.getTime() + k * 1000));
								}
								m = 0;
							}
						}
					} catch (Exception localException2) {
						n = 1;
						localConnection.rollback();
						localException2.printStackTrace();
					} finally {
						localConnection.setAutoCommit(true);
						this.TRADEDAO.closeStatement(null, localCallableStatement, localConnection);
					}
				}
			} catch (Exception localException1) {
				n = 1;
				localException1.printStackTrace();
				throw localException1;
			}
			if (n != 0) {
				m = 6;
			}
		}
		return m;
	}

	public Vector getSubmit_v(String paramString) throws SQLException {
		return null;
	}
}

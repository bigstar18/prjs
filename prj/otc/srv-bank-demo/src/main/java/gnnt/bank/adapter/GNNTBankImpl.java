package gnnt.bank.adapter;
import gnnt.bank.adapter.dao.AdapterDAO;
import gnnt.bank.adapter.dao.DAOFactory;
import gnnt.bank.adapter.util.InMoneyThread;
import gnnt.bank.adapter.util.Tool;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;
public class GNNTBankImpl extends BankAdapter{
	private static final long serialVersionUID = -4543226988931470939L;
	
	/**
     * 银行账户注册申请
     * @param correspondValue 交易商代码银行账户对应关系对象
     * @return
     */
	@Override
	public ReturnValue rgstAccountQuery(CorrespondValue correspondValue) {
		Tool.log("银行账户注册申请  rgstAccountQuery  correspondValue:"+correspondValue.toString());
		ReturnValue result = new ReturnValue();
		try{
			if(this.connectionDB()){
				try{
					AdapterFirmVO value = this.getDAO().getFirmVO(correspondValue.bankID,correspondValue.firmID);
					if(value != null){
						result.remark = "银行端签约成功";
						result.account1 = correspondValue.firmID;
						return result;
					}
					value = new AdapterFirmVO();
					value.setFirmID(correspondValue.firmID);
					value.setBankID(correspondValue.bankID);
					value.setMoney(getMoney(correspondValue.firmID,1));
					this.getDAO().addFirm(value);
					//new InMoneyThread(correspondValue,getMoney(correspondValue.firmID,2)).start();
					result.remark = "银行端签约成功";
					result.account1 = correspondValue.firmID;
					return result;
				}catch(Exception e){
					result.result = ErrorCode.rgstAccount_SysException;
					result.remark = "银行端签约异常";
					getExceptionTrace(e);
				}
			}else{
				if(!ifsuccessful()){
					result.result = ErrorCode.rgstAccount_BankRgsFail;
					result.remark = "银行端设置签约自动失败";
				}else{
					//new InMoneyThread(correspondValue,getMoney(correspondValue.firmID,2)).start();
					result.remark = "银行端设置签约自动成功";
				}
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}finally{
			InterfaceLog log = new InterfaceLog();
			log.bankID = correspondValue.bankID;
			log.account = correspondValue.account;
			log.contact = correspondValue.firmID;
			log.launcher = 0;
			log.type = 3;
			LogEndmsg le = new LogEndmsg();
			le.code = ""+result.result;
			le.remark = result.remark;
			if(!"0".equalsIgnoreCase(le.code)){
				log.result = 1;
			}
			log.endMsg = le.toString();
			this.interfaceLog(log);
		}
		return result;
	}
	/**
	 * 银行账户注销申请
	 * @param CorrespondValue 交易商代码银行账户对应关系对象
	 * @return ReturnValue
	 * @throws
	 */
	@Override
	public ReturnValue delAccountQuery(CorrespondValue correspondValue) {
		ReturnValue result = new ReturnValue();
		try{
			if(this.connectionDB()){
				try{
					this.getDAO().delFirm(correspondValue.bankID,correspondValue.firmID);
					result.remark = "银行端解约成功";
				}catch(Exception e){
					result.result = ErrorCode.delAccount_BankDelFail;
					result.remark = "银行端解约时异常";
					getExceptionTrace(e);
				}
			}else{
				if(!ifsuccessful()){
					result.result = ErrorCode.delAccount_BankDelFail;
					result.remark = "银行端设置市场端解约自动失败";
				}else{
					result.remark = "银行端设置市场端解约自动成功";
				}
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}finally{
			InterfaceLog log = new InterfaceLog();
			log.bankID = correspondValue.bankID;
			log.account = correspondValue.account;
			log.contact = correspondValue.firmID;
			log.launcher = 0;
			log.type = 4;
			LogEndmsg le = new LogEndmsg();
			le.code = ""+result.result;
			le.remark = result.remark;
			if(!"0".equalsIgnoreCase(le.code)){
				log.result = 1;
			}
			log.endMsg = le.toString();
			this.interfaceLog(log);
		}
		return result;
	}
	/**
     * 入金
     * @param bankID 银行代码
     * @param money 金额
     * @param firmID 交易商代码
     * @param payInfo 付款方信息
     * @param payPwd 付款方密码
	 * @param receiveInfo 收款方信息
	 * @param operator 入金发起端
     * @param actionID 转账模块业务流水号
	 * @return ReturnValue
	 */
	@Override
	public ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO) {
		Tool.log("入金：inMoneyVO:"+inMoneyVO.toString());
		ReturnValue result = new ReturnValue();
		long BankSer = new java.util.Date().getTime();
		try{
			if(inMoneyVO.getMoney()>=2011.83 && (inMoneyVO.getMoney()-2011.83)%10000==0){
				long time = new Date().getTime();
				Tool.log("当前时间："+time);
				if(time%2==0){
					result.result = ErrorCode.inMoneyB_passwordLock;
					result.remark = "测试数据，入金失败";
					return result;
				}
				result.result = ErrorCode.bankNull;
				result.remark = "测试数据，银行无应答";
				return result;
			}
			if(this.connectionDB()){
				try{
					AdapterFirmVO value = this.getDAO().getFirmVO(inMoneyVO.getBankID(),inMoneyVO.getFirmID());
					if(value == null){
						result.result = ErrorCode.inMoney_FirmNO;
						result.remark = "银行端未查询到客户信息";
						return result;
					}
					if(value.getMoney()<inMoneyVO.getMoney()){
						result.result = ErrorCode.inMoneyAudit_BankMoneyNotEnough;
						result.remark = "银行记录客户银行卡资金不足";
						return result;
					}
					value.setMoney(inMoneyVO.getMoney());
					this.getDAO().inMoney(value);
					result = this.bankProcInMoney(inMoneyVO,BankSer);
					result.remark = "客户入金成功";
					if(result.result<0){
						this.getDAO().outMoney(value);
						result.remark = "银行入金成功，市场入金失败，银行冲正成功";
					}
				}catch(Exception e){
					getExceptionTrace(e);
					result.result = ErrorCode.inMoney_ErrorParameter;
					result.remark = "入金时出现异常";
				}
			}else{
				if(ifsuccessful()){
					result = this.bankProcInMoney(inMoneyVO,BankSer);
					result.remark = "银行端设置为市场端入金自动成功，回调市场端入金，市场端返回["+result+"]";
				}else{
					result.result = ErrorCode.bankhandle_failure;
					result.remark = "银行端设置为市场端入金自动失败";
				}
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}finally{
			InterfaceLog log = new InterfaceLog();
			log.bankID = inMoneyVO.getBankID();
			log.account = inMoneyVO.getPayInfo().account;
			log.contact = inMoneyVO.getFirmID();
			log.launcher = 0;
			log.type = 7;
			LogEndmsg le = new LogEndmsg();
			le.actionID = inMoneyVO.getActionID();
			le.funID = ""+BankSer;
			le.code = ""+result.result;
			le.remark = result.remark;
			if(!"0".equalsIgnoreCase(le.code)){
				log.result = 1;
			}
			log.endMsg = le.toString();
			this.interfaceLog(log);
		}
		Tool.log("市场入金返回：\n"+result.toString());
		return result;
	}
	/**
     * 出金
     * @param bankID 银行代码
     * @param money 金额
     * @param firmID 交易商代码
     * @param payInfo 付款方信息
     * @param receiveInfo 收款方信息
     * @param actionID 转账模块业务流水号
     * @return ReturnValue -40001：银行返回包为空
     */
	@Override
	public ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO) {
		Tool.log(outMoneyVO.toString());
		ReturnValue result = new ReturnValue();
		try{
			if(outMoneyVO.money>=2011.83 && (outMoneyVO.money-2011.83)%10000==0){
				long time = new Date().getTime();
				Tool.log("当前时间："+time);
				if(time%2==0){
					result.result = ErrorCode.inMoneyB_passwordLock;
					result.remark = "测试数据，出金失败";
					return result;
				}
				result.result = ErrorCode.bankNull;
				result.remark = "测试数据，银行无应答";
				return result;
			}
			if(this.connectionDB()){
				try{
					AdapterFirmVO value = this.getDAO().getFirmVO(outMoneyVO.bankID,outMoneyVO.firmID);
					if(value == null){
						result.result = ErrorCode.inMoney_FirmNO;
						result.remark = "未查询到客户签约信息";
					}else{
						value.setMoney(outMoneyVO.money);
						this.getDAO().outMoney(value);
						result.funID = new java.util.Date().getTime()+"";
						result.remark = "出金成功";
					}
				}catch(Exception e){
					result.result = ErrorCode.outMoney_CardNo;
					result.remark = "银行端处理异常";
					getExceptionTrace(e);
				}
			}else{
				if(ifsuccessful()){
					result.funID = new java.util.Date().getTime()+"";
					result.remark = "银行端设置出金自动成功";
				}else{
					result.result = ErrorCode.outMoney_BankProError;
					result.remark = "银行端设置出金自动失败";
				}
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}finally{
			InterfaceLog log = new InterfaceLog();
			log.bankID = outMoneyVO.bankID;
			log.account = outMoneyVO.receiveInfo.account;
			log.contact = outMoneyVO.firmID;
			log.launcher = 0;
			log.type = 6;
			LogEndmsg le = new LogEndmsg();
			le.actionID = outMoneyVO.actionID;
			le.funID = result.funID;
			le.code = ""+result.result;
			le.remark = result.remark;
			if(!"0".equalsIgnoreCase(le.code)){
				log.result = 1;
			}
			log.endMsg = le.toString();
			this.interfaceLog(log);
		}
		Tool.log("出金返回：\n"+result.toString());
		return result;
	}
	/**银行卡余额查询*/
	@Override
	public double accountQuery(CorrespondValue correspondValue,String password) {
		double result = 0;
		Tool.log(correspondValue.toString());
		try{
			if(this.connectionDB()){
				try{
					AdapterFirmVO value = this.getDAO().getFirmVO(correspondValue.bankID,correspondValue.firmID);
					if(value == null){
						result = ErrorCode.queryBankMoney_NoneFirm;
					}else{
						result = value.getMoney();
					}
				}catch(Exception e){
					result = ErrorCode.queryBankMoney_NoneFirm;
					getExceptionTrace(e);
				}
			}else{
				if(ifsuccessful()){
					result = getMoney(correspondValue.firmID,1);
				}else{
					result = ErrorCode.queryBankMoney_NoneFirm;
				}
			}
			Tool.log("查询银行余额，返回值："+result);
		}catch(Exception e){
			getExceptionTrace(e);
		}finally{
			InterfaceLog log = new InterfaceLog();
			log.bankID = correspondValue.bankID;
			log.account = correspondValue.account;
			log.contact = correspondValue.firmID;
			LogEndmsg le = new LogEndmsg();
			if(result<0){
				le.code = ""+(long)result;
				ErrorCode ec = new ErrorCode();
				ec.load();
				if(ErrorCode.error.get((long)result) != null){
					le.remark = ErrorCode.error.get((long)result);
				}
			}else{
				le.code = "0";
				le.remark = "查询余额成功";
			}
			if(!"0".equalsIgnoreCase(le.code)){
				log.result = 1;
			}
			log.endMsg = le.toString();
			log.launcher = 0;
			log.type = 5;
			this.interfaceLog(log);
		}
		return result;
	}
	/**
     * 返回银行对帐信息
     * @return Vector 每项数据为 MoneyInfoValue
     * @throws 
     */
	public Vector<MoneyInfoValue> getBankMoneyInfo(Date date,Vector v){
		Vector<MoneyInfoValue> result = null;
		String bankID = "101";
		try{
			if(v != null && v.size()>0){
				bankID = (String) v.get(0);
			}
			Vector<CapitalValue> capitals = getCapital(date,bankID);
			if(capitals == null){
				return result;
			}
			MoneyInfoValue value = null;
			result = new Vector<MoneyInfoValue>();
			for(CapitalValue capital : capitals){
				value = new MoneyInfoValue();
				value.account = capital.account;
				value.bankID = capital.bankID;
				value.compareDate = new java.sql.Date(capital.bankTime.getTime());
				value.firmID = capital.contact;
				value.m_Id = capital.actionID;
				value.m_money = capital.money;
				value.money = capital.money;
				value.status = 0;
				value.type = capital.type;
				value.id = capital.funID;
				result.add(value);
				Tool.log(value.toString());
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}
		return result;
	}
	/**
	 * 取市场流水信息
	 */
	private Vector<CapitalValue> getCapital(Date date,String bankID){
		Vector<CapitalValue> result = null;
		try{
			String firmID = this.getDAO().getfirmID(date,bankID);
			Tool.log("交易商代码为："+firmID);
			result = this.getDAO().getCapitalValue(bankID, date,firmID);
			if(result == null){
				return result;
			}
			if(firmID != null && firmID.trim().length()>0){
				boolean flag = true;
				boolean ifchange = true;
				CapitalValue changefunID = null;
				int num = 0;
				for(int i=0;i<result.size();i++){
					CapitalValue value = result.get(i);
					if(value.funID==null || value.funID.trim().length()<=0){
						value.funID = i+""+new Date().getTime()+"a";
					}
					if(value.funID.indexOf("b")>=0){
						ifchange = false;
					}
					if(firmID.trim().equals(value.firmID)){
						if(flag && value.status==0 && value.money!= 2011.83 && (value.money-2011.83)%10000!=0){
							num = i;
							changefunID = value;
							flag = false;
						}
						if(value.money>0 && (value.money== 2011.83 || value.money-2011.83%10000==0)){
							if(value.actionID%2==0){
								result.remove(value);
								i--;
							}
						}
					}
				}
				if(changefunID != null && ifchange){
					changefunID.money = changefunID.money+1;
					changefunID.funID = num+new Date().getTime()+"b";
				}
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}
		return result;
	}
	/**
	 * 适配器回调处理器入金方法
	 * @param inMoneyVO 入金对象
	 * @return ReturnValue
	 */
	private ReturnValue bankProcInMoney(InMoneyVO inMoneyVO,long BankSer){
		ReturnValue result = new ReturnValue();
		long m = 0;
		try {
			m = getProcessor().inMoney(inMoneyVO.getBankID(), inMoneyVO.getFirmID(), 
					inMoneyVO.getPayInfo().account, new Timestamp(BankSer),
					inMoneyVO.getMoney(), BankSer+"", inMoneyVO.getActionID(), 0, "BFMem");
			Tool.log("市场入金调用inMoney返回: "+m);
		} catch (NumberFormatException e) {
			getExceptionTrace(e);
			m = -1;
		} catch (RemoteException e) {
			getExceptionTrace(e);
			m = -1;
		}
		if(m >= 0){
			result.result = 0;
			result.remark = "市场端发起入金成功";	
			result.funID = BankSer+"";
		}else{
			result.result = (int)m;
			result.remark = "市场端发起入金失败"+m;
		}
		return result;
	}
	/**
	 * 录入银行接口和银行通信的信息
	 * @param log
	 */
	private void interfaceLog(InterfaceLog log){
		try{
			Tool.log(log.toString());
			getProcessor().interfaceLog(log);
		}catch(Exception e){
			getExceptionTrace(e);
		}
	}
	/**
	 * 判断返回成功还是失败
	 * @return boolean true 成功 false 失败
	 */
	private boolean ifsuccessful(){
		boolean flag = true;
		if("false".equalsIgnoreCase(Tool.getConfig(Tool.ifsuccessful))){
			flag = false;
		}
		return flag;
	}
	/**
	 * 判断是否连接数据库
	 * return boolean true 连接 false 不连接
	 */
	private boolean connectionDB(){
		boolean result = false;
		if("true".equalsIgnoreCase(Tool.getConfig(Tool.connectionDB))){
			result = true;
		}
		return result;
	}
	/**
	 * 取得配置资金
	 * @param contact 签约号
	 * @param type 类型(1 银行权益,2 入金金额)
	 * @return double 配置资金
	 */
	private double getMoney(String contact,int type){
		double result = 1000000;
		try{
			if(contact.length()>=15){//客户
				if(type==1){
					result = Double.valueOf(Tool.getConfig(Tool.customerright));
				}else{
					result = Double.valueOf(Tool.getConfig(Tool.customerinmoney));
				}
			}else{//会员
				if(type==1){
					result = Double.valueOf(Tool.getConfig(Tool.memberright));
				}else{
					result = Double.valueOf(Tool.getConfig(Tool.memberinmoney));
				}
			}
		}catch(Exception e){
			getExceptionTrace(e);
		}
		return result;
	}
	private void getExceptionTrace(Exception e){
		Tool.log(Tool.getExceptionTrace(e));
	}
	/**
	 * 取得适配器 DAO
	 */
	private AdapterDAO getDAO(){
		return DAOFactory.getDAO();
	}
	public String getStr(long second){
		String result = "服务端马上返回了成功信息";
//		Connection conn = null;
//		PreparedStatement state = null;
//		String sql = "update f_b_dictionary set value='GJS修改' where id=302 ";
//		try{
//			conn = this.getDAO().getConnection();
//			try{
//				conn.setAutoCommit(false);
//				state = conn.prepareStatement(sql);
//				int num = state.executeUpdate();
				if(second>0){
					result = "服务端通过["+second+"]秒的等待后返回了成功信息";
					try{
						Thread.sleep(second*1000);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
//				conn.commit();
//			}catch(Exception e){
//				e.printStackTrace();
//				conn.rollback();
//			}finally{
//				Tool.log("finally   conn.setAutoCommit(true)");
//				conn.setAutoCommit(true);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			this.getDAO().closeStatement(null, state, conn);
//			Tool.log("finally   closeStatement(null, state, conn)");
//		}
		return result;
	}
}

<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.rmi.Naming"%>
<jsp:directive.page import="gnnt.trade.bank.dao.BankDAOFactory"/>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%>
<%@ page import = "com.hitrust.trustpay.client.market.IMarketTagName" %>
<%@ page import = "com.hitrust.trustpay.client.IFunctionID" %>
<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>
		</title>
	</head>
	<body>
<%	
	boolean icbc_b2cFlag=false;//工行银企直连是否是B2C模式

    String bankID = request.getParameter("bank");
    ABCCapitalProcessorRMI cp1 = null;
	HXBCapitalProcessorRMI cphx = null;
	CapitalProcessorRMI cp = null;
	YjfCapitalProcessorRMI cpyjf=null;
	try {
		if("050".equals(bankID)){
			cp1 = getABCBankUrl("050");
		}else if("006".equals(bankID)){
		    cphx=getHXBBankUrl(bankID);
		}else if("79".equals(bankID)){
		    cpyjf=getYjfBankUrl(bankID);
		}
		cp = getBankUrl(bankID);
	} catch(Exception e) {
		e.printStackTrace();
	}


	//String computerIP = request.getRemoteAddr();
	String datetime=Tool.fmtDateTime(new java.util.Date());
	String money = request.getParameter("money");
	String inoutMoney = request.getParameter("inoutMoney");
	String bankPwd = request.getParameter("bankPassword");
	String pwd = request.getParameter("password");
	String FIRMID = (String)session.getAttribute("REGISTERID");
	String acount = null;
	String bankName = request.getParameter("BankName");
	String PersonName = request.getParameter("PersonName");
	//解决乱码问题添加的转码
	//String bankName = new String(Tool.delNull(request.getParameter("BankName")).getBytes("ISO-8859-1"),"GBK");
	//String PersonName = new String(Tool.delNull(request.getParameter("PersonName")).getBytes("ISO-8859-1"),"GBK");	
	String InOutStart = request.getParameter("InOutStart");
	String OutAccount = request.getParameter("OutAccount");
	String AmoutDate = Tool.delNull(request.getParameter("AmoutDate"));
	String payChannel = request.getParameter("payChannel");
	String[] strArr = AmoutDate.split("-");
	String res = "";
	for(int i=0;i<strArr.length;i++){
		res = res + strArr[i];
	}
	AmoutDate = res;
	String isExpress = request.getParameter("IsExpress");
	
	String code = request.getParameter("code");
	ReturnValue rv=new ReturnValue();
	long result = -1;
	String results = result+"";
	String falg = request.getParameter("falg");
	long actionid=0;
	long actionidpay=0;
	AbcInfoValue value = new AbcInfoValue();
	boolean flag1 = true;
	CorrespondValue cv = null;
	if((cp1 == null && "050".equals(bankID)) || cp == null) {
		results="调用Rmi错误";
	}else 
	if(Double.parseDouble("10000000000")<=Double.parseDouble(money)){
		results="单笔出入金范围超出限制";
	}else {
		
		String filter = " ";
		
		
		if(FIRMID!=null)
		{
			filter = filter + " and FIRMID='"+FIRMID+"'";
		}
		if(bankID!=null)
		{
			filter = filter + " and bankid='"+bankID+"'";
		}
		else{
			flag1 = false;
			results = "客户编号、银行不能为空";
		}
		if(flag1){
			Vector<CorrespondValue> vcv = cp.getCorrespondValue(filter);
			if(vcv != null && vcv.size()>0) {
				cv = vcv.get(0);
					
	            bankID=cv.bankID;
			}
			else{
				flag1 = false;
				results = "查询交易商绑定信息失败";
			}
		}
		if(flag1){
			if(cv==null) {
				flag1 = false;
				results = "取得绑定银行信息失败";
			}else if(cv.isOpen!=1){
				flag1 = false;
				results = "您的帐号非签约状态，不可以做转账";
			}else{
				acount = cv.account;
				value.firmID=cv.contact;
			    value.account1=cv.account1;
			}
		}
		
		if(flag1 && falg.equals("sh")){
		
			if(showPasswordBank(inoutMoney,bankID,cv.cardType)){
				result = cp.isPassword((String)session.getAttribute("REGISTERID"),pwd);
				if(result == 1){
					flag1 = false;
					results="请先设置密码";
				}else if(result == -1){
					flag1 = false;
					results="资金密码验证失败";
				}else if(result == -2){
					flag1 = false;
					results="未查到交易商";
				}
			}
		}
		if(flag1){
		 try
	        {
		    
				if (cv.bankID.equals("050")){
					if(falg.equals("sh")){
						actionid=cp.getMktActionID();
						actionidpay=cp.getMktActionID();//发请求前获得流水												
					}else if(falg.equals("pay")){
						actionid=Long.valueOf(request.getParameter("RequestID"));
						actionidpay=Long.valueOf(request.getParameter("RequestIDpay"));
						value.orderNo=String.valueOf(actionid);
						value.actionID=actionidpay;
						value.signInfo=request.getParameter("CustSignInfo");
						
						if((""+ProcConstants.outMoneyType).equals(inoutMoney)){
						    value.type=1;
							rv=cp1.insertAbcInfo(value);
							if(rv.result==0){
								OutMoneyMarket omm = new OutMoneyMarket();
								omm.bankID = bankID;
								omm.firmID = (String)session.getAttribute("REGISTERID");
								omm.trader = (String)session.getAttribute("REGISTERID")+"_trader";
								omm.account = acount;
								omm.contact = cv.contact;
								omm.money = Double.parseDouble(money);
								omm.remark = "市场端出金";
								rv = cp1.outMoneyMarket(omm,actionid);
								result = rv.result;
								
							}
							results = rv.remark;	
									
						}
						else{
							value.type=0;
							rv=cp1.insertAbcInfo(value);
							if(rv.result==0){						
								InMoneyMarket imm = new InMoneyMarket();
								imm.firmID = (String)session.getAttribute("REGISTERID");
								imm.trader = (String)session.getAttribute("REGISTERID")+ "_trader";
								imm.bankID = bankID;
								imm.account = acount;
								imm.contact = cv.contact;
								imm.money = Double.parseDouble(money);
								imm.accountPwd = bankPwd;
								imm.remark = "市场端发起入金";
								rv = cp1.inMoneyMarket(imm,actionid);
								result = rv.result;
													
							}
							results = rv.remark;							
						}
					}				
	            }else{ 				
					if((""+ProcConstants.outMoneyType).equals(inoutMoney)) {
						OutMoneyMarket omm = new OutMoneyMarket();
						omm.bankID = bankID;
						omm.firmID = (String)session.getAttribute("REGISTERID");
						omm.trader = (String)session.getAttribute("REGISTERID")+"_trader";
						omm.account = acount;
						omm.contact = cv.contact;
						omm.money = Double.parseDouble(money);
						omm.remark = "市场端出金";
						rv = cp.outMoneyMarket(omm);
						result = rv.result;
						results = rv.remark;
					} else {
						InMoneyMarket imm = new InMoneyMarket();
						imm.firmID = (String)session.getAttribute("REGISTERID");
						imm.trader = (String)session.getAttribute("REGISTERID")+ "_trader";
						imm.bankID = bankID;
						imm.account = acount;
						imm.contact = cv.contact;
						imm.money = Double.parseDouble(money);
						imm.accountPwd = bankPwd;
						imm.remark = "市场端发起入金";
						imm.inOutStart = InOutStart;
						imm.personName = PersonName;
						imm.amoutDate = AmoutDate;
						imm.bankName = bankName;
						imm.outAccount = OutAccount;
						if("006".equals(bankID)){
						    rv=cphx.inMoneyMarketHx(imm);
						}else if("79".equals(bankID)){
							imm.payChannel = payChannel;
							rv=cpyjf.inMoneyMarket(imm);
						}
						else{
						    rv = cp.inMoneyMarket(imm);
						}
						//rv = cp.inMoneyMarket(imm);
						result = rv.result;
						results = rv.remark;
					}
						}
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		if(results == null || results.trim().length()<=0){
			results = ErrorCode.error.get(result);
			if(results == null || results.trim().length()<=0){
				results = ""+result;
			}
		}
	}
	%>
<object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==" type="application/x-oleobject">
    </embed> 
</object>
<form name="frm" action="" method="post">
	
		<input type="hidden" value="<%=actionidpay%>" name="RequestIDpay" >
		<input type="hidden" value="<%=actionid%>" name="RequestID" >
		<input type="hidden" value="<%=actionid%>" name="actionid" >
		<input type="hidden" value="<%=money%>" name="money" >
		<input type="hidden" value="<%=inoutMoney%>" name="inoutMoney">
		<input type="hidden" value="<%=Tool.fmtDouble2(Double.parseDouble(money))%>" name="PayAmount" >
		<input type="hidden" value="<%=bankID%>" name="bank" >
		<input type="hidden" value="<%=FIRMID%>" name="FIRMID" >
		<input type="hidden" name="CustSignInfo" value="">
		<input type="hidden" name="CustSignInfo2" value="">
		<input type="hidden" name="falg" value="">
		
		<input name="remark1" type="hidden" value="<%=rv.actionId%>"/>
		<input name="remark2" type="hidden" value="<%=money%>"/>
		<input name="remark3" type="hidden" value="<%=acount%>"/>
		<input name="remark4" type="hidden" value="<%=cv.contact%>"/>
		<input name="remark5" type="hidden" value="<%=FIRMID%>"/>
		<input name="remark6" type="hidden" value="<%=results%>"/>
	</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">

<%if("050".equals(bankID)&&"sh".equals(falg) && flag1){%>

    var now = new Date();
	var year = now.getYear(); 
	var month = eval(now.getMonth())+1 ;
	month = eval(month)<10?'0'+month:month;
	var day = now.getDate() ;
	day = eval(day)<10?'0'+day:day;
	var reqID = "RQP"+(year+'').substring(2,4)+month+day+"001";
	var sign_merchantid = '<%=IMarketTagName.MARKET_SIGN_MERCHANTID%>'
	var sign_merchantid_desc = '<%=IMarketTagName.MARKET_SIGN_MERCHANTID_DESC%>'
	var sign_merchanttrxno = '<%=IMarketTagName.MARKET_SIGN_TRXNO%>'
	var sign_merchanttrxno_desc = '<%=IMarketTagName.MARKET_SIGN_TRXNO_DESC%>'
	var sign_functionid = '<%=IMarketTagName.MARKET_SIGN_FUNCTIONID%>'
	var sign_functionid_desc = '<%=IMarketTagName.MARKET_SIGN_FUNCTIONID_DESC%>'
	var sign_payamount = '<%=IMarketTagName.MARKET_SIGN_PAYAMOUNT%>'
	var sign_payamount_desc = '<%=IMarketTagName.MARKET_SIGN_PAYAMOUNT_DESC%>'
	var sign_fee = '<%=IMarketTagName.MARKET_SIGN_FEE%>'
	var sign_fee_desc = '<%=IMarketTagName.MARKET_SIGN_FEE_DESC%>'	
    var sign_orderno = '<%=IMarketTagName.MARKET_SIGN_ORDERNO%>'
	var sign_orderno_desc = '<%=IMarketTagName.MARKET_SIGN_ORDERNO_DESC%>'		
	var sign_trxtime = '<%=IMarketTagName.MARKET_SIGN_TRXTIME%>'
	var sign_trxtime_desc = '<%=IMarketTagName.MARKET_SIGN_TRXTIME_DESC%>'
    var trxdate = new Date();
		//客户端签名必须用的javascript方法，可和其它javascript方法合并
    //把form.name放进[[...]]中，和form.name.value组成字符串。所有签名字段合成一个字符串
    var signstring =   sign_merchanttrxno + frm.RequestIDpay.value
                     + sign_functionid + '<%=IFunctionID.MARKET_ORDERPAY%>'
                     + sign_payamount + frm.PayAmount.value                    
                     + sign_orderno + frm.actionid.value
                     + sign_trxtime + trxdate;
                     
	var TempString;
	TempString="<订单支付确认>\n\n";
	TempString= TempString+sign_functionid_desc+"交易市场账单支付"+"\n";
	TempString= TempString+sign_merchanttrxno_desc+frm.RequestIDpay.value+"\n";
	TempString= TempString+sign_payamount_desc+frm.PayAmount.value+"\n";
	TempString= TempString+sign_orderno_desc+frm.actionid.value+"\n";
	TempString= TempString+sign_trxtime_desc+trxdate;

    //var sure = confirm("请确认您要提交的签名信息:\n\n" + TempString);
    //if(sure == false) {
	
  		//window.location.href="MoneyAction.jsp&falg=pay1111&msg=客户取消&code=1111";
    //}else{
    
    if(typeof(InfoSecNetSign1)  ==  "undefined"){  
		alert("请插入U盾并安装K宝驱动");  
	    }else {
			try{ 		
				InfoSecNetSign1.addFormItem(signstring);
				InfoSecNetSign1.addFormItem(TempString);
				
				InfoSecNetSign1.makeAttachedSign();
				frm.CustSignInfo.value = InfoSecNetSign1.attachedSign;
				
			}catch(err){
				alert("请插入U盾并安装K宝驱动");  
				document.frm.action="Money.jsp";
			}
        }
		if(frm.CustSignInfo.value.length == 0){
			document.frm.action="Money.jsp";
		}else{
            frm.falg.value="pay";
			
		    document.frm.action="MoneyAction.jsp";
		}
	//}
<%}else if("66".equals(bankID)&&"0".equals(inoutMoney)&&result>=0&& flag1){%>
	document.frm.action="GopayMoney.jsp";
	frm.submit();
	
<%}else if("79".equals(bankID) && "0".equals(inoutMoney)&& rv!=null){%>
	var resultRemart = '<%=rv.remark%>';
	var resultPayChannel = '<%=rv.payChannel%>';
	var netAddress = '<%=rv.netAddress%>';
	alert(resultRemart);
	var EBankInMoneyRsult;
	if("0002" == resultPayChannel || "0003" == resultPayChannel){
		EBankInMoneyRsult = window.showModalDialog(netAddress,'','');
	}
	if(EBankInMoneyRsult){
		window.location = "Money.jsp";
	}
<%}else if(icbc_b2cFlag&&"011".equals(bankID)&&"0".equals(inoutMoney)&&result>=0&& flag1){%>
	document.frm.action="BankCaptailProcess/cap_011_icbce/ICBCEMoney.jsp";
	frm.submit();
<%}else if("028".equals(bankID)&&"0".equals(inoutMoney)&&result>=0&& flag1){%>
document.frm.action="BankCaptailProcess/cap_028_tlgpay/TLGatewayMoney.jsp";
frm.submit();

<%}else{%>

alert("<%=results%>");
window.location = "Money.jsp";
<%}if(flag1 && "050".equals(bankID)){%>
frm.submit();
<%}%>
</SCRIPT>
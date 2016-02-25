<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="Params.jsp"%>
<%@ page import="gnnt.bank.platform.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String inoutMoney = request.getParameter("inoutMoney");//出入金标志
String bankID= request.getParameter("bankID");
String systemID = request.getParameter("collectionSys");
String money = request.getParameter("money");
String fundsPwd=request.getParameter("password");
String bankPwd=request.getParameter("bankPwd");
String RequestID = (String)request.getAttribute("RequestID");
String actionidpay = (String)request.getAttribute("RequestIDpay");
String accountName = (String)request.getAttribute("accountName");
%>

<body>
<object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==" type="application/x-oleobject">
    </embed> 
</object>
<form name="frm" action="${basePath}/bank/money/moneyTransfer.action" method="post">
<input type="hidden" name="RequestID" value="<%=RequestID%>">
<input type="hidden" value="<%=actionidpay%>" name="RequestIDpay" >
<input type="hidden" value="" name="CustSignInfo">
<input type="hidden" name="MerchantID" value="232010200532E01">
<input type="hidden" name="MerchantName" value="长三角交易所">
<input type="hidden" value="<%=accountName%>" name="CustName">
<input type="hidden" value="<%=bankID%>" name="bankID">
<input type="hidden" value="<%=systemID%>" name="collectionSys">
<input type="hidden" value="<%=Tool.fmtDouble2(Double.parseDouble(money))%>" name="money">
<input type="hidden" value="<%=fundsPwd%>" name="password">
<input type="hidden" value="<%=bankPwd%>" name="bankPwd">
<input type="hidden" value="<%=inoutMoney%>" name="inoutMoney">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
var now = new Date();
var back = false;
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
                     + sign_payamount + frm.money.value                    
                     + sign_orderno + frm.RequestID.value
                     + sign_trxtime + trxdate;
                     
	var TempString;
	TempString="<订单支付确认>\n\n";
	TempString= TempString+sign_functionid_desc+"交易市场账单支付"+"\n";
	TempString= TempString+sign_merchanttrxno_desc+frm.RequestIDpay.value+"\n";
	TempString= TempString+sign_payamount_desc+frm.money.value+"\n";
	TempString= TempString+sign_orderno_desc+frm.RequestID.value+"\n";
	TempString= TempString+sign_trxtime_desc+trxdate;

    var sure = confirm("请确认您要提交的签名信息:\n\n" + TempString);
    if(sure) {
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
				back = true;
			}
        }
		if(frm.CustSignInfo.value.length == 0){
			back = true;
		}
	}else{
		back = true;
	}
if(back){
	frm.action = "${basePath}/bank/returnBack/returnBack.action";
}
frm.submit();
</SCRIPT>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
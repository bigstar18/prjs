<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="Params.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String addOrDel = (String)request.getAttribute("addOrDel");//签解约标志
String bankID= request.getParameter("bankID");
String account=request.getParameter("account");
String accountName=request.getParameter("accountName");
String cardType=request.getParameter("cardType");
String card=request.getParameter("card");
String fundsPwd=request.getParameter("pwd");
String bankPwd=request.getParameter("bankpwd");
String RequestID = (String)request.getAttribute("RequestID");

%>

<body>
<object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==" type="application/x-oleobject">
    </embed> 
</object>

<form name="frm" action="" method="post">
<input type="hidden" name="RequestID" value="<%=RequestID%>">
<input type="hidden" value="" name="CustSignInfo">
<input type="hidden" name="MerchantID" value="232010200532E01">
<input type="hidden" name="MerchantName" value="长三角交易所">
<input type="hidden" value="<%=accountName%>" name="CustName">
<input type="hidden" value="<%=bankID%>" name="bankID">
<input type="hidden" value="<%=account%>" name="account">
<input type="hidden" value="<%=accountName%>" name="accountName">
<input type="hidden" value="<%=fundsPwd%>" name="pwd">
<input type="hidden" value="<%=bankPwd%>" name="bankpwd">
<input type="hidden" value="<%=card%>" name="card">
<input type="hidden" value="<%=cardType%>" name="cardType">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
var back = false;
var now = new Date();
var year = now.getYear(); 
var month = eval(now.getMonth())+1 ;
month = eval(month)<10?'0'+month:month;
var day = now.getDate() ;
day = eval(day)<10?'0'+day:day;
var reqID = "RSU"+(year+'').substring(2,4)+month+day+"001";

var sign_merchantid = '<%=IMarketTagName.MARKET_SIGN_MERCHANTID%>'
var sign_merchantid_desc = '<%=IMarketTagName.MARKET_SIGN_MERCHANTID_DESC%>'
var sign_merchanttrxno = '<%=IMarketTagName.MARKET_SIGN_TRXNO%>'
var sign_merchanttrxno_desc = '<%=IMarketTagName.MARKET_SIGN_TRXNO_DESC%>'
var sign_functionid = '<%=IMarketTagName.MARKET_SIGN_FUNCTIONID%>'
var sign_functionid_desc = '<%=IMarketTagName.MARKET_SIGN_FUNCTIONID_DESC%>'
var sign_MerchantName = '<%=IMarketTagName.MARKET_SIGN_MERCHANTNAME%>'
var sign_MerchantName_desc = '<%=IMarketTagName.MARKET_SIGN_MERCHANTNAME_DESC%>'
var sign_CustName = '<%=IMarketTagName.MARKET_SIGN_CUSTNAME%>'
var sign_CustName_desc = '<%=IMarketTagName.MARKET_SIGN_CUSTNAME_DESC%>'
var sign_time = '<%=IMarketTagName.MARKET_SIGN_SIGNTIME%>'
var sign_time_desc = '<%=IMarketTagName.MARKET_SIGN_SIGNTIME_DESC%>'
var sign_offtime = '<%=IMarketTagName.MARKET_SIGN_SIGNOFFTIME%>'
var sign_offtime_desc = '<%=IMarketTagName.MARKET_SIGN_SIGNOFFTIME_DESC%>'
var signdate = new Date();
			//客户端签名必须用的javascript方法，可和其它javascript方法合并
		//把form.name放进[[...]]中，和form.name.value组成字符串。所有签名字段合成一个字符串

<% if("1".equals(addOrDel)){//签约  %>

       var signstring =   sign_merchantid + frm.MerchantID.value
						 + sign_merchanttrxno + frm.RequestID.value
						 + sign_functionid + '<%=IFunctionID.MARKET_SIGNUP%>'
						 + sign_MerchantName + frm.MerchantName.value
						 + sign_CustName + frm.CustName.value
						 + sign_time + signdate;
			 
		var TempString;	
        TempString="<客户签约确认>\n\n";
		TempString= TempString+sign_functionid_desc+"交易市场签约"+"\n";				
		TempString= TempString+sign_merchanttrxno_desc+frm.RequestID.value+"\n";
		TempString= TempString+sign_MerchantName_desc+frm.MerchantName.value+"\n";
		TempString= TempString+sign_merchantid_desc+frm.MerchantID.value+"\n";
		TempString= TempString+sign_CustName_desc+frm.CustName.value+"\n";
		TempString= TempString+sign_time_desc+now;

		var sure = confirm("请确认您要提交的签名信息:\n\n" + TempString);
		if(sure){//确认提交
			if(typeof(InfoSecNetSign1)  ==  "undefined"){
				alert("请插入K宝并安装K宝驱动");  
			}else {
				try{ 					
					InfoSecNetSign1.addFormItem(signstring);
					InfoSecNetSign1.addFormItem(TempString);
					InfoSecNetSign1.makeAttachedSign();
					frm.CustSignInfo.value = InfoSecNetSign1.attachedSign;
				}catch(err){
					alert("请插入K宝并安装K宝驱动");
				}
			}
		}else{
			back = true;
		}
		if(frm.CustSignInfo.value.length == 0){
			back = true;
		}
<%}else if("2".equals(addOrDel)){//解约    %>
		var signstring =  sign_functionid + '<%=IFunctionID.MARKET_SIGNOFF%>'
                     + sign_merchanttrxno + frm.RequestID.value
                     + sign_MerchantName + frm.MerchantName.value
                     + sign_merchantid + frm.MerchantID.value                     
                     + sign_CustName + frm.CustName.value
                     + sign_offtime + signdate;
						 
		var TempString;	
        TempString="<客户解约确认>\n\n";
		TempString= TempString+sign_functionid_desc+"交易市场解约"+"\n";				
		TempString= TempString+sign_merchanttrxno_desc+frm.RequestID.value+"\n";
		TempString= TempString+sign_MerchantName_desc+frm.MerchantName.value+"\n";
		TempString= TempString+sign_merchantid_desc+frm.MerchantID.value+"\n";
		TempString= TempString+sign_CustName_desc+frm.CustName.value+"\n";
		TempString= TempString+sign_offtime_desc+now;

		var sure = confirm("请确认您要提交的签名信息:\n\n" + TempString);
		if(sure){//确认提交
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
				}
			}
		}else{
			back = true;
		}
		if(frm.CustSignInfo.value.length == 0){
			back = true;
		}
<%}if("1".equals(addOrDel)){%>
	frm.action = "${basePath}/bank/firmInfo/PreRgsAccPage.action";
<%}else if("2".equals(addOrDel)){%>
	frm.action = "${basePath}/bank/firmInfo/delRegistByPlat.action";
<%}%>
if(back){
	frm.action = "${basePath}/bank/returnBack/returnBack.action";
}
frm.submit();
</SCRIPT>
</html>
<%@include file="/front/public/jsp/commonmsg.jsp"%>
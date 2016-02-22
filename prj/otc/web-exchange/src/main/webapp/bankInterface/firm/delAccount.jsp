<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and bankID='005' and firmid='"+firmID+"'");
	CorrespondValue corr = null;
	if(cps != null && cps.size()>0){
		corr = cps.get(0);
		corr.status = 0;
		ErrorCode errorcode = new ErrorCode();
		errorcode.load();
	}
	if(corr == null){
		out.print("未查寻到客户信息");
		return ;
	}
	if("do".equals(request.getParameter("submitFlag"))){
		long bak = -1;
		String headmsg = "交易资金账号管理";
		String str=headmsg+"-解约农行账号["+firmID+"]";
		String strs = "";
		if(corr == null){
			strs = "未查寻到账号信息";
		}else{
			if(corr.bankID == null || corr.bankID.trim().length()<=0){
				strs = "银行不能为空";
			}else if(!delAccountBank(corr.bankID)){
				strs = corr.bankID+"银行不能市场端解约";
			}else{
				try {
					CapitalProcessorRMI cp = null;
					try{
						cp = getBankUrl(corr.bankID);
					}catch(Exception e){
						e.printStackTrace();
					}
					ReturnValue result=cp.delAccountMaket(corr);
					if(result.result<0){
						if(result.remark != null && result.remark.trim().length()>0){
							strs = result.remark;
						}else if(ErrorCode.error.get(result.result) != null){
							strs = ErrorCode.error.get(result.result);
						}else{
							strs = "返回码 ["+ result.result+"]";
						}
					}else{
						bak = 0;
						strs = "资金账号["+corr.firmID+"]解约成功";
					}
				} catch(Exception e) {
					strs = "资金账号["+corr.firmID+"]解约系统异常";
					e.printStackTrace();
				}
				LogValue lv = new LogValue();
				lv.setLogtype("2110");
				lv.setLogoprtype("E");
				lv.setResult(bak==0 ? 0 : 1);
				lv.setLogopr(AclCtrl.getLogonID(request));
				lv.setIp(computerIP);
				lv.setLogcontent(str+"-"+strs);
				dao.log(lv);
			}
		}
	%>
		<script>
			alert('<%=strs%>');
			<%
				if(bak == 0){
			%>
				window.returnValue="1";
				window.close();
			<%
				}
			%>
		</script>
	<%
	}
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../lib/jquery/style/validator.css"></link>
    <title>(农行)交易所发起解约</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag" id="submitFlag">
	<div style="overflow:auto;height:150;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;(农行)交易所发起解约</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="25%">&nbsp;</td>
					<td align="left" width="30%">
						<input name="firmID" disabled='disabled' value="<%=firmID%>" readonly type="hidden"  class="input_text" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>：&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=contact%>" type=text  class="input_text" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">农行客户编号：&nbsp;</td>
					<td align="left">
						<input name="account1" disabled='disabled' readonly value="<%=corr.account1%>" type=text  class="input_text" style="width: 140px">
					</td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="sub_btn" class="btn_sec" onclick="delAccount();">解约</button>&nbsp;
					<button id="bak_btn" class="btn_cz" onclick="window.close();">&nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;&nbsp;</button>&nbsp;
				</td>
			</tr>
		</table>
		<input type="hidden" name="bankID" id="bankID" value="005">
		<input type="hidden" name="005" id="005" value="农业银行">
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function delAccount(){
	if('<%=corr.isOpen%>'=='1'){
		var msg = "您确定要解约账户：";
			msg += "\n客户或会员编号：["+idobj('firmID').value+"]";
			msg += "\n<%=CONTACTTITLE%>：["+idobj('contact').value+"]";
			msg += "\n农行客户编号["+idobj('account1').value+"]";
			msg += "\n银行["+idobj(idobj('bankID').value).value+"]";
		if(confirm(msg)){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
			idobj("submitFlag").value="do";
			frm.submit();
		}
	}else{
		if(confirm("交易账号当前不是签约状态，是否发起解约?")){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
			idobj("submitFlag").value="do";
			frm.submit();
		}
	}
}
function idobj(userID){
	return document.getElementById(userID);
}
//-->
</SCRIPT>
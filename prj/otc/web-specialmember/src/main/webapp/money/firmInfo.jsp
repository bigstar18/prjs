<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<%@page import="gnnt.trade.bank.CapitalProcessor"%>

<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import = "com.hitrust.trustpay.client.market.IMarketTagName" %>
<%@ page import = "com.hitrust.trustpay.client.IFunctionID" %>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%>

<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<%@include file="ajax.jsp"%>

<%
   CapitalProcessorRMI cp = null;
   
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	

String firmID = (String)session.getAttribute("REGISTERID");
if(firmID == null)
{
	out.println("无效的交易商代码！");
	return;
}

//交易商的银行绑定列表
Vector<CorrespondValue> cvs = cp.getCorrespondValue("and firmId='"+((String)session.getAttribute("REGISTERID"))+"'");



	String contact="";
	String flag="";
	String YjfMarkCode= Tool.getConfig("YjfMarkCode");
	long result = -1;
	
	String results = result+"";
	if(cp==null){
		results="调用Rmi错误";
	}
	if(cp!=null){
		String filter = " ";
		if(firmID!=null){
			filter = filter + " and FIRMID='"+firmID+"' and bankid='79'";
		}
		Vector<CorrespondValue> vcv = cp.getCorrespondValue(filter);
		CorrespondValue cv = null;
		if(vcv != null && vcv.size()>0){
			cv = vcv.get(0);
			contact=cv.contact;
			flag=YjfMarkCode+contact+cv.card+cv.account;
			if(cv.isOpen == 3){
				result = 0;
			}
		}	
	}
	String md5Flag ="";
	try{
		md5Flag=Tool.md5(flag);
		
	} catch (Exception e){
		e.printStackTrace();
	}


%>
<script language="javascript">
function checkBank(bankid){
	if(bankid == 79){
		frm.openAccountCheck.disabled=true;
	}else{
		frm.openAccountCheck.disabled=false;
	}
}




	var contact = '<%=contact%>';
	var YjfMarkCode = '<%=YjfMarkCode%>';
	var md5Flag = '<%=md5Flag%>';
	if(<%=result%> == 0){
		window.open("http://218.70.82.178:20115/subscribe?FuturesAccountId=" + contact+"&MarketCode="+YjfMarkCode+"&CheckCode="+md5Flag,'newwindow','height=650,width=1000,top=100,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
	}




	
	
function doSign(frm,tableList,checkName,remark){
	
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "不安全操作，请先登录!" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择交易商!" );
		return false;
	}
	
	
    frm.falg.value = remark;
	if('rgst'==remark){//发起签约
		var firmID = frm.firmID.value;
		var CustName = frm.CustName.value;	
		var result = window.showModalDialog("ext_connector/02_openAccount.jsp?firmID="+firmID,"","dialogWidth=680px; dialogHeight=480px; status=yes;scroll=yes;help=no;");
		frm.action="firmInfo.jsp";
		frm.submit();
	}	
    if('del'==remark){//发起解约
	
	
		frm.action="firminfo3.jsp";
				frm.submit();
			
	}		
   
}
</script>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gbk">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
	<script language="javascript" src="tools.js"></script>
    <IMPORT namespace="MEBS" implementation="public/jstools/calendar.htc">
    <title>交易员签约</title>
  </head>
 
  <body oncontextmenu="return false">
  	<object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==">
    </embed> 
</object>

		<form id="frm" action="demo/ModifCustAccNo.jsp" method="post" name="frm">
		<font style="font-size: 10pt;font-weight: bold;">交易商&nbsp;<%=session.getAttribute("REGISTERID")%>&nbsp;银行帐号管理</font>
		<input type="hidden" name="firmID" value="<%=session.getAttribute("REGISTERID")%>">
		<input type="hidden" value="start" name="falg" >
		
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"></td>
				<td class="panel_tHead_MB">银行代码</td>
				<td class="panel_tHead_MB" >银行帐号</td>
				<td class="panel_tHead_MB" >银行内部帐号</td>
				<td class="panel_tHead_MB" >账户名</td>
				<td class="panel_tHead_MB" >开户行名称</td>
				<td class="panel_tHead_MB" >开户行省份</td>
				<!--<td class="panel_tHead_MB" align=left>开户行所在市</td>-->
				<td class="panel_tHead_MB" >签约状态</td>
				<td class="panel_tHead_MB" >帐号状态</td>
				<td class="panel_tHead_MB" >银行帐号密码</td>
				
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<cvs.size();i++)
			{
				CorrespondValue corr = (CorrespondValue)cvs.get(i);
				%>
				<tr height="22" align=center >
					<td class="panel_tBody_LB">&nbsp;</td>
					
					
					<td class="underLine" ><input name="ck" type="radio" onclick="checkBank('<%=corr.bankID%>');" value='<%=corr.bankID%>,<%=corr.firmID%>,<%=corr.contact%>,<%=corr.account%>,<%=corr.account1%>,<%=corr.card%>,<%=corr.cardType%>,<%=corr.isOpen%>,<%=corr.accountName%>,<%=corr.mobile%>,<%=corr.id%>'></td>
					
					
					
					<td class="underLine"><input name="bankID" type="hidden" value="<%=corr.bankID%>"><%=corr.bankID==null ?  "--" : corr.bankID %>&nbsp;</td>
					<td class="underLine" >
					<input name="OldCustAccNo" type="hidden" value="<%=corr.account%>">
					
					<%=corr.account==null ?  "--" : corr.account %>
					
					<input name="OldCustAccNo" type="hidden" value="<%=corr.account%>">
					<input name="CustName" type="hidden" value="<%=corr.accountName%>">
					<input type="hidden" name="CustNo" value="<%=corr.account1%>">
					<input type="hidden" name="CustType" value="<%=corr.cardType%>">
					<input name="RequestID" type="hidden" value="">
					</td>
					
					<td class="underLine" ><%=corr.account1==null ?  "--" : corr.account1 %>&nbsp;</td>
					
					<td class="underLine" ><%=corr.accountName==null ?  "--" : corr.accountName %>&nbsp;</td>
					<td class="underLine" >
					<%
						if(corr.bankName == null || "0".equals(corr.bankName) || "".equals(corr.bankName) || "null".equals(corr.bankName)){
							out.print("-");
						}else{
							if(BankCode.codeForBank.get(corr.bankName) == null){
								out.print(corr.bankName);
							}else{
								out.print(BankCode.codeForBank.get(corr.bankName));
							}
						}
					%>&nbsp;
					</td>
					<td class="underLine" >
					<%
						if(corr.bankProvince == null || "0".equals(corr.bankProvince) || "".equals(corr.bankProvince) || "null".equals(corr.bankProvince)){
							out.print("-");
						}else{
							if(map.get(corr.bankProvince) == null){
								out.print(corr.bankProvince);
							}else{
								out.print(map.get(corr.bankProvince).getCityName());
							}
						}
					%>&nbsp;
					</td>
					<!--<td class="underLine" align=left><%=corr.bankCity%>&nbsp;</td>-->
					<td class="underLine" >
					<%
						if(corr.isOpen == 0){
							out.println("未签约");
						}else if (corr.isOpen == 2){
							out.println("已解约");
						}else if(corr.isOpen == 1){
							out.println("已签约");
						}else if(corr.isOpen == 3){
							out.println("预签约");
						}else{
							out.println("状态未知" + corr.isOpen);
						}
					%>
					&nbsp;</td>
					<td class="underLine">
					<%
						if(corr.status == 0)
						{
							out.println("可用");
						}
						else if(corr.status == 1)
						{
							out.println("不可用");
						}
					%>
					&nbsp;</td>
					<% if("005".equals(corr.bankID)&&corr.isOpen == 0&&corr.status == 1){%>
					<td class="underLine" ><input type="password" name="pwd" id="pwd"></td>
					
					<%}else{ %>
					<td class="underLine" >--</td>
					
					<%} %>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<TABLE width=100%>
	<TR align=center>
		<TD>
		
		<%if(cvs.size()>0){
		CorrespondValue corr = (CorrespondValue)cvs.get(0);
		
		%>
		
		
			<button type="button" name="openAccountCheck" class="smlbtn" onclick="doSign(frm,tableList,'ck','rgst');">签约账号</button>&nbsp;
			<button type="button" class="smlbtn" onclick="doSign(frm,tableList,'ck','del');">解约账号</button>&nbsp;
			
		<!--	<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','update');">修改账号</button>&nbsp;-->
		<%}%>
		
		
		<input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</form>
	
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function deleteRec(frm,tableList,checkName,remark)
{
	//var pass=document.getElementById("pwd").value;
	var num = document.getElementById("NewCustAccNo").value;
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "不安全操作，请先登录!" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择交易商!" );
		return false;
	}
	if(num == ""||num == null)
	{
		alert ( "请输入新银行卡号!" );
		return false;
	}
	if(confirm("你确定要操作选中数据吗"))
	{
		frm.falg.value = remark;
        frm.RequestID.value=<%=cp.getMktActionID()%>;
		frm.submit();
	}
	else
	{
		return false;
	}
}
function isSelNothing( tblObj , chkId )
{
	var collCheck = tblObj.children[1].all.namedItem(chkId);
    if(!collCheck)
        return -1;
    if(collCheck.checked)
	{
		if(collCheck.checked == true)
		 return false
		else
		  return true
	}
	if( collCheck.length < 1 )
	{
		return -1;
	}
	var noSelect = true;
	if(collCheck.checked == true)
	{
			noSelect = false;
	}
	else
	{
		for(var i=0;i < collCheck.length;i++ )
		{
			if( collCheck[i].checked == true )
			{
				noSelect = false;			
			}
		
		}
	}
	return noSelect;	
}
//-->
</SCRIPT>
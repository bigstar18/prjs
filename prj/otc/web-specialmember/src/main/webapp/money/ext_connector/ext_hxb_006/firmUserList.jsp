<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
int[] pageinfo = new int[4];
boolean medoBank = false;
String bankIdHX = request.getParameter("bankID");

if(bankIdHX==null ||bankIdHX.trim().length()<=0){
	bankIdHX="006";
}
String firmID = request.getParameter("firmID");
HXBBankDAO dao = BankDAOFactory.getHXBDAO();

int pageSize = BANKPAGESIZE;
int size = Tool.strToInt((String)request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
pageinfo[2]=pageSize;
int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
if(pageIndex <= 0)  pageIndex = 1;
pageinfo[1]=pageIndex;
String cardType = request.getParameter("cardType");
String firmType = request.getParameter("firmType");
String contact = request.getParameter("contact");
String accountName = request.getParameter("accountName");
String checkSignHX = request.getParameter("checkSignHX");
Vector<CorrespondValue> vc = null;
Vector<CorrespondValue> vc2 = null;
CorrespondValue sv = null;
String filter1="";

//在多银行的签约的条件下判断该交易商能否与华夏银行执行“子账号签约操作”
if(bankIdHX != null && bankIdHX.trim().length()>0 && firmID != null && firmID.trim().length()>0){
	vc = dao.getCorrespondList(" and firmid = '" + firmID + "' and bankid = '" + bankIdHX + "' ");
	if(vc.size() == 1){  //校验的是：刚注册的账号、华夏已签约的账号、华夏已解约的账号、农行签约成功且华夏签解约操作的账号、农行解约且华夏未签约的账号、农行解约且华夏已签解约的账号
		sv = vc.get(0);
		if(sv.isOpen != 0){
			out.println("<script>");
			out.println("alert('该交易商已与华夏银行签约或者解约，不能执行此操作！');");
			out.println("window.close();");
			out.println("</script>");
		}else{
			vc2 = dao.getCorrespondList(" and firmid = '" + firmID + "' ");
			if(vc2.size() > 1){
				filter1 += " and fbfa.bankid = '" + bankIdHX + "' ";
			}
		}
	}
}

if(contact != null && contact.trim().length()>0){
	filter1 += " and fbf.contact='"+contact.trim()+"' ";
}
if(accountName != null && accountName.trim().length()>0){
	filter1 += " and fbf.firmName like '"+accountName.trim()+"%' ";
}
if(checkSignHX != null && checkSignHX.trim().length()>0){
	if("Y".equals(checkSignHX.trim())){
		filter1 += " and fbfi.bankid = '"+bankIdHX+"' and Upper(fbfi.key) = 'HXBPS' and Upper(fbfi.value) = 'Y' ";
	}else{
		filter1 += " and fbf.firmID not in (select firmID from f_b_firminfo fb1 where fb1.bankid='"+bankIdHX+"' and Upper(fb1.key)='HXBPS' and Upper(fb1.value)= 'Y' ) ";
	}
}
filter1 += " and fbf.firmID='" + firmID + "'";
Vector dicList1 = dao.getFirmUserList2(filter1,pageinfo,bankIdHX,"HXBPS");
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/report.css" type="text/css"/>
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>交易账号华夏预签约管理</title>
  </head>
  
  <body>
	<form id="frm" action="" method="post">
		<input type="hidden" id="submitFlag" name="submitFlag">
		<input type="hidden" id="thispage" name="thispage">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0" id="tableList">
				<tr>
					<td>
						 <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
							<tHead>
								<tr align=center>
									<td class="panel_tHead_LB">&nbsp;</td>
									<td class="panel_tHead_MB">序号</td>
									<td  class="panel_tHead_MB" align="center"><%=CONTACTTITLE%></td>
									<td class="panel_tHead_MB" align="center">账号名称</td>
									<td  class="panel_tHead_MB" align="center">所属会员</td>
									<td  class="panel_tHead_MB" align="center">银行账号</td>
									<td  class="panel_tHead_MB" align="center">证件类型</td>
									<td  class="panel_tHead_MB" align="center">预签约状态</td>
									<td  class="panel_tHead_MB" align="center">子账户签约状态</td>
									<td class="panel_tHead_RB"></td>
								</tr>
							</tHead>
							<tBody>
	  		<%
			for(int i=0;i<dicList1.size();i++) {
				FirmUserValue firmer = (FirmUserValue)dicList1.get(i);
				//if(firmer.contact.equals(firmID)){
				%>
				<tr align="center"  onclick="selectTr();" height="22" >
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++ %></td>
					<td class="underLine" align="center"><%=Tool.replaceNull(firmer.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.replaceNull(firmer.firmName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.replaceNull(firmer.belevemember)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.replaceNull(firmer.account)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.replaceNull(firmer.getCardType())%>&nbsp;</td>
					<td class="underLine" align="center">
						<%
						if((firmer.checkSignHX==null || firmer.checkSignHX.trim().length()<=0)){
							out.print("<a href='#' onclick=\"modFirm('"+firmer.firmID+"','')\" >本行预签约</a>");
						}else if(firmer.checkSignHX.trim().equalsIgnoreCase("Y")){
							out.print("<a href='#' onclick=\"modFirm('"+firmer.firmID+"','"+firmer.checkSignHX+"')\" ><font color='#FF0000'>预签约完成</font></a>");
						}else{
							out.print("<font color='silver'>未知状态</font>");
						}
						%>
					</td>
					<td class="underLine" align="center">
						<%
							out.print("<a href='#' onclick=\"childModFirm('"+firmer.contact+"','006','" + firmer.firmID + "')\" >非华夏签约</a>");
						%>&nbsp;
					</td>
					<td class="panel_tBody_RB" ></td>
	  			</tr>	
				<%
			  //}
			}
			%>
			
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8" align=right>
				<input name="pageSize" class="text" maxlength="4" type="hidden" style="width:25px;" value="" onkeydown="return pgJumpChk()"> &nbsp;&nbsp;
				
				<span style="cursor:hand" onclick="pgTurn(0)"></span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)"></span> &nbsp;&nbsp;	
				<input class="text" type="hidden" maxlength="4" style="width:25px;" name="pageJumpIdx" value="" onkeydown="return pgJumpChk()">

				<input name=pageIndex type=hidden value="">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
						
					</td>
				</tr>
			</table>
		</div>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">

function myreset(){
	frm.action="";
	myReset();
}
function doQuery(){
	var firmID = frm.firmID;
	var accountName = frm.accountName;
	var contact = frm.contact;
	if(!isStr(firmID.value.trim(),false,null)){
		alert("交易账号非法");
		firmID.focus();
	}else if(!isStr(contact.value.trim(),false,null)){
		alert("签约账号非法");
		contact.focus();
	}else if(!isStr(accountName.value.trim(),true,new Array('·'))){
		alert("账号名称非法");
		accountName.focus();
	}else {
		frm.pageIndex.value=1;
		frm.action="firmUserList.jsp";
		frm.submit();
	}
}

function objmsg(userID,msg){
	document.getElementById(userID).value=""+msg;
}
function pgTurn(i){
	frm.action="";
	if(i == 0) {
		frm.pageIndex.value = 1;
		frm.submit();
	} else if(i == 1) {		
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
		frm.submit();
	} else if(i == 2) {
		frm.pageIndex.value = <%=pageinfo[3]%>;
		frm.submit();
	} else if(i == -1) {
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function pgJumpChk() {
	frm.action="";
	if(event.keyCode == 13) {
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )) {
			alert("请输入正确数字！");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
function output(){
	var url = "accountExcle.jsp";
	if(confirm("导出全部数据吗?\n\n(取消为导出当前页)")){
		frm.thispage.value="0";
	}else{
		frm.thispage.value="1";
	}
	frm.action=url;
	frm.submit();
}
function modFirm(firmID,checkSignHX){
	var result = window.showModalDialog("./synchroCorr.jsp?firmID="+firmID+"&checkSignHX="+checkSignHX,"","dialogWidth=500px; dialogHeight=450px; status=yes;scroll=yes;help=no;");
	//var result = window.location("./synchroCorr.jsp?firmID="+firmID+"&checkSignHX="+checkSignHX);
	if(result){
		frm.action="#";
		frm.submit();
		window.close();
	}
}

function childModFirm(contact,bankID,firmID){
	var contact = contact;
	var bankid = bankID;
	var firmID = firmID;
	var flag = confirm("只有华夏银行支持该功能，是否继续此操作？");
	
	if(flag){
		var result = window.showModalDialog("./childopenAccountmedo.jsp?bankID=" + bankID +"&contact=" + contact + "&firmID=" + firmID,"","dialogWidth=500px; dialogHeight=545px; status=yes;scroll=yes;help=no;");
		//var result = window.showModalDialog("./childopenAccountmedo.jsp?bankID=" + bankID +"&contact=" + contact + "&firmID=" + firmID,"","dialogWidth=1000px; dialogHeight=900px; status=yes;scroll=yes;help=no;");
		if(result){
			frm.action="#";
			frm.submit();
			window.close();
		}
	}
}

function findBankAndFirms(firm){
	var firmID=firm.value;
}
</SCRIPT>

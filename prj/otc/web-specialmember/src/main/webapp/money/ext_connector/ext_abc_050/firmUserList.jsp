<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
int[] pageinfo = new int[4];
boolean medoBank = false;
String bankIdHX = request.getParameter("bankID");
if(bankIdHX==null ||bankIdHX.trim().length()<=0){
	//System.out.println("******reset*bankisnull*********"+bankIdHX);
	bankIdHX="050";
}
String firmID = request.getParameter("firmID");
BankDAO dao = BankDAOFactory.getDAO();
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
//String firmID = request.getParameter("firmID");
String contact = request.getParameter("contact");
String accountName = request.getParameter("accountName");
String checkSignHX = request.getParameter("checkSignHX");
String filter1 = " and mf.firmType='C' ";
String ORGANIZATIONID = (String)session.getAttribute("ORGANIZATIONID");
if(ORGANIZATIONID==null || ORGANIZATIONID.trim().length()<=0){
	filter1 += " and mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' ";
	//filter1 += " and exists (select customerno from m_customerinfo mc where mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' and fbf.firmID=mc.customerno) ";
}else{
	filter1 += " and mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' ";
	//filter1 += " and exists (select customerno from v_customerrelateorganization mc where mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' and fbf.firmID=mc.customerno) ";
}

if(cardType != null && cardType.length()>0){
	if("other".equals(cardType)){
		filter1 += " and fbf.cardType not in ('1','8','9') ";
	}else{
		filter1 += " and fbf.cardType='"+cardType+"' ";
	}
}
if(firmID != null && firmID.trim().length()>0){
	filter1 += " and fbf.firmID='"+firmID.trim()+"' ";
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
	filter1 += " order by fbf.firmID";
Vector dicList1 = dao.getFirmUserList2(filter1,pageinfo,bankIdHX,"HXBPS");
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;

%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>交易账号农行预签约管理</title>
  </head>
  
  <body>
	<form id="frm" action="" method="post">
		<input type="hidden" id="submitFlag" name="submitFlag">
		<input type="hidden" id="thispage" name="thispage">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
								<td class="table2_td_widthcdmax">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0" style="table-layout:fixed;">
												<tr>
												   <td width="210" align="left">
														&nbsp;交易账号：&nbsp;
														<input type="text" maxlength="15" class="input_text"  name="firmID" value=""/>
													</td>
													<td align="left" width="210">
														&nbsp;签约账号：&nbsp;
														<input type="text" maxlength="32" class="input_text" name="contact" value=""/>
													</td>
													<td align="left" width="210">
														&nbsp;账号名称：&nbsp;
														<input type="text" maxlength="64" class="input_text"  name="accountName" value=""/>&nbsp;
													</td>
													
												</tr>
												<tr>	
													<td align="left" width="210">
														&nbsp;证件类型：&nbsp;
														<select name="cardType" style="width:120px;">
															<option value="">全部</option>
															<option value="1">身份证</option>
															<option value="8">机构代码</option>
															<option value="9">营业执照</option>
															<option value="other">其他</option>
														</select>
													</td>
													<td align="left" width="600">
														&nbsp;预签约状态：&nbsp;
														<select name="checkSignHX" style="width:72px">
															<option value="">全部</option>
															<option value="Y">是</option>
															<option value="N">否</option>
														</select>&nbsp;
													</td>
													<td>&nbsp;&nbsp;</td>
												</tr>
												<tr>
													<td>&nbsp;&nbsp;</td>
													<td align="right" colspan="3">&nbsp;&nbsp;
														<button class="btn_sec" onclick="doQuery();">
															查询
														</button>&nbsp;
														<button class="btn_cz" onclick="myreset();">
															重置
														</button>&nbsp;
													</td>
													<td>&nbsp;&nbsp;</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr><td>&nbsp;&nbsp;</td></tr>
				<tr>
					<td>
						 <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
							<tHead>
								<tr align=center>
									<td class="panel_tHead_LB">&nbsp;</td>
									<td class="panel_tHead_MB">序号</td>
									<td  class="panel_tHead_MB" align="center">交易账号</td>
									<td  class="panel_tHead_MB" align="center">签约账号</td>
									<td class="panel_tHead_MB" align="center">账号名称</td>
									<td  class="panel_tHead_MB" align="center">证件类型</td>
									<td  class="panel_tHead_MB" align="center">预签约状态</td>
									<td class="panel_tHead_RB"></td>
								</tr>
							</tHead>
							<tBody>
	  		<%
			for(int i=0;i<dicList1.size();i++) {
				FirmUserValue firmer = (FirmUserValue)dicList1.get(i);
				%>
				<tr align="center"  onclick="selectTr();" height="22" >
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++ %></td>
					<td class="underLine" align="center"><%=replaceNull(firmer.firmID)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(firmer.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(firmer.firmName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(firmer.getCardType())%>&nbsp;</td>
					<td class="underLine" align="center">
						<%
						if(firmer.checkSignHX==null || firmer.checkSignHX.trim().length()<=0){
							out.print("<a href='#' onclick=\"modFirm('"+firmer.firmID+"','')\" >预签约</a>");
						}else if(firmer.checkSignHX.trim().equalsIgnoreCase("Y")){
							out.print("<a href='#' onclick=\"modFirm('"+firmer.firmID+"','"+firmer.checkSignHX+"')\" ><font color='#FF0000'>预签约完成</font></a>");
							//out.print("<font color='#FF0000'>预签约完成</font>");
						}else{
							out.print("<font color='silver'>未知状态</font>");
						}
						%>
					</td>
					<td class="panel_tBody_RB" ></td>
	  			</tr>	
				<%
			}
			%>
			
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="6">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6" align=right>
				第<%=pageinfo[1]%>/<%=pageinfo[3]%>页 &nbsp;&nbsp;共<%=pageinfo[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">首页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">上页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;	
					<%
				}

				if(pageinfo[1] < pageinfo[3])
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">下页</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">尾页</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;	
					<%
				}

				%>
				到<input class="text" type="text" maxlength="4" style="width:25px;" name="pageJumpIdx" value="<%=pageinfo[1]%>" onkeydown="return pgJumpChk()">页

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="6"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
						
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" id="bankID" name="bankID" value="<%=bankIdHX %>">
	</form>
  </body>
</html>
<script>
frm.cardType.value = '<%=Tool.delNull(cardType)%>';
frm.firmID.value = '<%=Tool.delNull(firmID)%>';
frm.contact.value = '<%=Tool.delNull(contact)%>';
frm.accountName.value = '<%=Tool.delNull(accountName)%>';
frm.checkSignHX.value = '<%=Tool.delNull(checkSignHX)%>';
</script>

<SCRIPT LANGUAGE="JavaScript">

function myreset(){
	frm.action="";
	myReset();
}
function doQuery(){
	var firmID = frm.firmID;
	var accountName = frm.accountName;
	var contact = frm.contact;
	if(!is_Str(firmID.value.trim(),false,null)){
		alert("交易账号非法");
		firmID.focus();
	}else if(!is_Str(contact.value.trim(),false,null)){
		alert("签约账号非法");
		contact.focus();
	}else if(!is_Str(accountName.value.trim(),true,new Array('·'))){
		alert("账号名称非法");
		accountName.focus();
	}else {
		frm.pageIndex.value=1;
		frm.action="firmUserList.jsp";
		frm.submit();
	}
}


function is_Str(s,ch,vec){
	if(isEmpty(s)){
		return true;
	}
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec != null){
		for(var i=0;i<vec.length;i++){
			strs += "|\\"+vec[i];
		}
	}
	var matchs='\^[0-9A-Za-z'+china+strs+']{1,}\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
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
	if(result){
		frm.action="#";
		frm.submit();
	}
}

function findBankAndFirms(firm){
	var firmID=firm.value;
	
}
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<%
String CCBbankID="015";
int[] pageinfo = new int[4];
boolean medoBank = false;
CCBCapitalProcessorRMI cp=getCCBBankUrl(CCBbankID);
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt((String)request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
pageinfo[2]=pageSize;
int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
if(pageIndex <= 0)  pageIndex = 1;
pageinfo[1]=pageIndex;
String filter = " ";
String status = request.getParameter("status");
String cardType = request.getParameter("cardType");
String firmid = request.getParameter("firmID");
String contact = request.getParameter("contact");
String card = request.getParameter("card");
String hadUp = request.getParameter("hadUp");
String accountName = request.getParameter("accountName");
String filter1="";
String endsql=" order by fbfu.contact";
if(status != null && status.length()>0){
	filter1 += " and fbfu.status="+status+" ";
}
if(cardType != null && cardType.length()>0){
	if("other".equals(cardType)){
		filter1 += " and fbfu.cardType not in ('1','8','9') ";
	}else{
		filter1 += " and fbfu.cardType='"+cardType+"' ";
	}
}
if(contact != null && contact.trim().length()>0){
	filter1 += " and fbfu.contact = '"+contact.trim()+"' ";
}
if(firmid != null && firmid.trim().length()>0){
	filter1 += " and fbfu.firmid = '"+firmid.trim()+"' ";
	endsql=" order by fbfu.firmid";
}
if(card != null && card.trim().length()>0){
	filter1 += " and fbfu.card='"+card.trim()+"' ";
}
if(accountName != null && accountName.trim().length()>0){
	filter1 += " and fbfu.firmName like '"+accountName.trim()+"%' ";
}
if(hadUp != null && hadUp.trim().length()>0){
	if("0".equals(hadUp)){
		filter1 += " and fbfi.value is not null";
	}else if("1".equals(hadUp)){
		filter1 += " and fbfi.value is null";
	}
}
	filter1 +=endsql;
Vector dicList1 = cp.getFirmUserList(filter1,pageinfo,"Backstage","CCBAc",CCBbankID);
		pageinfo=cp.getPageinfo();
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�����˺Ź���</title>
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
														&nbsp;�����˺ţ�&nbsp;
														<input type="text" maxlength="15" class="input_text"  name="contact" value=""/>
													</td>
													<td align="left" width="210">
														&nbsp;�˺����ƣ�&nbsp;
														<input type="text" maxlength="64" class="input_text"  name="accountName" value=""/>&nbsp;
													</td>
													<td align="left" width="210">
														&nbsp;�Ƿ���ã�&nbsp;
														<select name="status" style="width:80px">
															<option value="">ȫ��</option>
															<option value="0">����</option>
															<option value="1">������</option>
														</select>&nbsp;
													</td>
													<td>&nbsp;&nbsp;</td>
												</tr>
												<tr>	
												<td align="left" width="210">
														&nbsp;֤�����ͣ�&nbsp;
														<select name="cardType" style="width:80px;">
															<option value="">ȫ��</option>
															<option value="1">���֤</option>
															<option value="8">��������</option>
															<option value="9">Ӫҵִ��</option>
															<option value="other">����</option>
														</select>
													</td>												
													<td align="left" width="210">
														&nbsp;֤�����룺&nbsp;
														<input type="text" maxlength="32" class="input_text" name="card" value=""/>
													</td>
													<td align="left" width="210">
														&nbsp;�Ƿ����޸����ݣ�&nbsp;
														<select name="hadUp" style="width:80px;">
															<option value="">ȫ��</option>
															<option value="0">���޸�</option>
															<option value="1">δ�޸�</option>
														</select>
													</td>
													<td align="right" colspan="3">
														<button class="btn_sec" onclick="doQuery();">
															��ѯ
														</button>&nbsp;
														<button class="btn_cz" onclick="myreset();">
															����
														</button>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						 <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
							<tHead>
								<tr align=center>
									<td class="panel_tHead_LB">&nbsp;</td>
									<td class="panel_tHead_MB">���</td>
									<td  class="panel_tHead_MB" align="center">�����˺�</td>
									<td class="panel_tHead_MB" align="center">�˺�����</td>
									<td  class="panel_tHead_MB" align="center">֤������</td>
									<td  class="panel_tHead_MB" align="center">֤������</td>
									<td  class="panel_tHead_MB" align="center">�Ƿ����</td>
									<td  class="panel_tHead_MB" align="center">���������˺�</td>
									<td class="panel_tHead_RB"></td>
								</tr>
							</tHead>
							<tBody>
	  		<%
			for(int i=0;i<dicList1.size();i++) {
				FirmValue firmer = (FirmValue)dicList1.get(i);
				%>
				<tr align="center"  onclick="selectTr();" height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=rownum++ %></td>
					<td class="underLine" align="center"><%=replaceNull(firmer.contact)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(firmer.firmName)%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(firmer.getCardType())%>&nbsp;</td>
					<td class="underLine" align="center"><%=replaceNull(firmer.card)%>&nbsp;</td>
					<td class="underLine" align="center"><%
						if(0==firmer.status){
							out.print("����");
						}else if(1==firmer.status){
							out.print("<font color='#FF0000'>������</font>");
						}else{
							out.print("<font color='#FF0000'>״̬δ֪</font>");
						}
					%></td>
					<td class="underLine" align="center">
					<%if(firmer.firminfo.value!=null&&firmer.firminfo.value.length()>0&&firmer.firminfo.bankid!=null&&firmer.firminfo.bankid.equals(CCBbankID)){
						out.print("<a href='#' onclick=\"modFirm('"+firmer.firmID+"')\">"+firmer.firminfo.value+"</a>");
					}else{
						out.print("<a href='#' onclick=\"modFirm('"+firmer.firmID+"')\">����</a>");
					}
					%>
					</td>
					<td class="panel_tBody_RB" >&nbsp;</td>
	  			</tr>	
				<%
			}
			%>
			
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="7" align=right>
				��<%=pageinfo[1]%>/<%=pageinfo[3]%>ҳ &nbsp;&nbsp;��<%=pageinfo[0]%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1)
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
					<%
				}

				if(pageinfo[1] < pageinfo[3])
				{
					%>
					<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span> &nbsp;&nbsp;	
					<%
				}
				else
				{
					%>
					��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
					<%
				}

				%>
				��<input class="text" type="text" maxlength="4" style="width:25px;" name="pageJumpIdx" value="<%=pageinfo[1]%>" onkeydown="return pgJumpChk()">ҳ

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="7"></td>
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
<script>
frm.status.value = '<%=Tool.delNull(status)%>';
frm.cardType.value = '<%=Tool.delNull(cardType)%>';
frm.contact.value = '<%=Tool.delNull(contact)%>';
frm.card.value = '<%=Tool.delNull(card)%>';
frm.hadUp.value = '<%=Tool.delNull(hadUp)%>';
frm.accountName.value = '<%=Tool.delNull(accountName)%>';
</script>

<SCRIPT LANGUAGE="JavaScript">

function myreset(){
	frm.action="";
	myReset();
}
function doQuery(){
		frm.pageIndex.value=1;
		frm.action="firmUserList.jsp";
		frm.submit();
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
			alert("��������ȷ���֣�");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}
function output(){
	var url = "accountExcle.jsp";
	if(confirm("����ȫ��������?\n\n(ȡ��Ϊ������ǰҳ)")){
		frm.thispage.value="0";
	}else{
		frm.thispage.value="1";
	}
	frm.action=url;
	frm.submit();
}
function modFirm(firmID){
	var result=window.showModalDialog("synchroCorr.jsp?firmID="+firmID,"","dialogWidth=530px; dialogHeight=500px; status=yes;scroll=yes;help=no;");
	if(result){
		frm.action="#";
		frm.submit();
	}
}

function findBankAndFirms(firm){
	var firmid=firm.value;
	
}
</SCRIPT>
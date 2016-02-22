<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
int[] pageinfo = new int[4];
int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
pageinfo[2]=pageSize;
boolean modright = false;
if(session.getAttribute("rightMap") != null){
	Map<String,Boolean> rightMap = (Map<String,Boolean>) session.getAttribute("rightMap");
	if(rightMap.get("update") != null){
		if(rightMap.get("update")==true){
			modright = true;
		}
	}
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
pageinfo[1]=pageIndex;
String filter = " order by bankid ";
Vector dicList = dao.getBankList2(filter,pageinfo);
int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>

<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>银行列表</title>
  </head>
  
  <body>
  <form name="frm" id="frm" action="list2.jsp" method="post">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<%
					if(modright){
				%>
				<tr>
					<td>
						 <div class="div_gn">
							<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="table3_td" align="left">&nbsp;&nbsp;
										<!-- <button class="anniu_btnmax" onclick="bankStatus(1);">
											禁用出金
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="bankStatus(2);">
											禁用入金
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="bankStatus(3);">
											恢复出金
										</button>&nbsp;&nbsp;
										<button class="anniu_btnmax" onclick="bankStatus(4);">
											恢复入金
										</button>&nbsp;&nbsp; -->
										<button class="anniu_btnmax" onclick="bankStatus(5);">
											详细设置
										</button>
									</td>
								</tr>
							</table>
							<input type="hidden" name="submitFlag">
						</div>
					</td>
				</tr>
				<%}%>
				<tr>
					<td>
						<div class="div_list">
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB">序号</td>
				<td class="panel_tHead_MB" align="center">银行名称</td>
				<td class="panel_tHead_MB" align="center">出金状态</td>
				<td class="panel_tHead_MB" align="center">入金状态</td>
				<td class="panel_tHead_MB" align="center">接口状态</td>
				<td class="panel_tHead_MB" align="center">转账开始时间</td>
				<td class="panel_tHead_MB_last" align="center">转账结束时间</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<dicList.size();i++){
				BankValue bank = (BankValue)dicList.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><input name="ck" type="radio" value='<%=bank.bankID%>'></td>
					<td class="underLine" align="center"><%=rownum++%></td>
					<td class="underLine" align="center"><%=Tool.delNull(bank.bankName)%>&nbsp;</td>
					<td class="underLine" align="center"><%
					if(bank.outMoneyFlag==0){
						out.print("可用");
					}else{
						out.print("<font color=ret>禁用<font>");
					}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
					if(bank.inMoneyFlag==0){
						out.print("可用");
					}else{
						out.print("<font color=ret>禁用<font>");
					}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%
					if(bank.validFlag==0){
						out.print("可用");
					}else{
						out.print("<font color=ret>禁用<font>");
					}
					%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.delNull(bank.beginTime)%>&nbsp;</td>
					<td class="underLine_last" align="center"><%=Tool.delNull(bank.endTime)%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
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
				第<%=pageinfo[1]%>/<%=pageinfo[3]%>页 &nbsp;&nbsp;共<%=pageinfo[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
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
				到<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
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
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function bankInfo(v){
	
}
function bankStatus(i){
	var flag = checked('ck');
	if(!flag){
		alert("请您先选择要操作的银行");
	}else if(i==1){//禁用出金
		if(confirm("您确认要禁用所选银行出金吗？")){
			frm.submitFlag.value="disOutMoney";
			frm.submit();
		}
	}else if(i==2){//禁用入金
		if(confirm("您确认要禁用所选银行入金吗？")){
			frm.submitFlag.value="disInMoney";
			frm.submit();
		}
	}else if(i==3){//恢复出金
		if(confirm("您确认要恢复所选银行出金吗？")){
			frm.submitFlag.value="recOutMoney";
			frm.submit();
		}
	}else if(i==4){//恢复入金
		if(confirm("您确认要恢复所选银行入金吗？")){
			frm.submitFlag.value="recInMoney";
			frm.submit();
		}
	}else if(i==5){
		var result = window.showModalDialog("modBank.jsp?bankID="+flag+"","","dialogWidth=500px; dialogHeight=500px; status=yes;scroll=yes;help=no;toolbar=no;");	
		if(result){
			frm.submitFlag.value="";
			frm.action="";
			frm.submit();
		}
	}
}
function checked(name){
	var checks = document.getElementsByName(name);
	var leng = checks.length;
	var flag = false;
	for(var i=0;i<leng;i++){
		if(checks[i].checked==true){
			flag = checks[i].value;
		}
	}
	return flag;
}
function pgTurn(i){
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
	if(event.keyCode == 13) {
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )) {
			alert("请输入1 - <%=pageinfo[3]%>间的数字！");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}


//-->
</SCRIPT>
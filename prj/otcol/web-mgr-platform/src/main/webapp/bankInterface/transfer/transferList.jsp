<%@ page contentType="text/html;charset=GB2312" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();




int pageSize = PAGESIZE;
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));

int statusQry= -1;
if(request.getParameter("statusQry")!=null&&!request.getParameter("statusQry").equals("")){
	statusQry= Tool.strToInt(request.getParameter("statusQry"));
}
String moneyQry = Tool.delNull(request.getParameter("moneyQry"));

if(pageIndex < 0)  pageIndex = 1;

String filter = "";
if(statusQry!=-1){
	filter += " and status = "+statusQry;
}
if(moneyQry!=null&&!moneyQry.equals("")){
	filter += " and money = "+moneyQry;
}
filter += " order by updatetime desc";



Vector bankTransferList = dao.getBankTransferList(filter);
ObjSet obj = ObjSet.getInstance(bankTransferList, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
	<script language="javascript" src="../lib/tools.js"></script>
  </head>
  
  <body>
  	<form id="frm" action="" method="post" name="frm">
		<fieldset width="95%">
			<legend>银行间资金划转查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">状态&nbsp;</td>
					<td align="left">
						<select name="statusQry" class="normal" style="width: 100px">
							<OPTION value="-1">全部</OPTION>
							<option value="0" <%=statusQry==0?"selected":""%>>成功</option>
              				<option value="1" <%=statusQry==1?"selected":""%>>失败</option>
              				<option value="2" <%=statusQry==2?"selected":""%>>待审核</option>
							<option value="3" <%=statusQry==3?"selected":""%>>待银行处理结果</option>
							<option value="3" <%=statusQry==4?"selected":""%>>审核拒绝</option>
						</select>
					</td>
					<td align="right">金额&nbsp;</td>
					<td align="left">
						<input name="moneyQry" value="<%=moneyQry%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" >记录流水号</td>
				<td class="panel_tHead_MB" >交易流水号</td>
				<td class="panel_tHead_MB" >付款银行</td>
				<td class="panel_tHead_MB" >收款银行</td>
				<td class="panel_tHead_MB" >创建时间</td>
				<td class="panel_tHead_MB" >金额</td>
				<td class="panel_tHead_MB" >附言</td>
				<!--<td class="panel_tHead_MB" >类型</td>-->
				<td class="panel_tHead_MB" >状态</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				BankTransferValue bankTransferValue = (BankTransferValue)obj.get(i);
				%>
				<tr height="22" align=center>
					<td class="panel_tBody_LB">&nbsp;</td>					
					<td class="underLine" ><a href="audit.jsp?id=<%=bankTransferValue.id%>&flag=0"><%=bankTransferValue.id%></a>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.actionId%>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.payBankName%>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.recBankName%>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.createTime%>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.money%>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.note==null?"--":bankTransferValue.note%>&nbsp;</td>
					<!--<td class="underLine" ><%
											String typeStr = null;
											switch(bankTransferValue.type)
											{
												case 0:typeStr="其他";
												break;
												case 1:typeStr="为出金手续费";
												break;
												case 2:typeStr="市场出金";
												break;
												case 3:typeStr="挂账匹配";
												break;
												case 4:typeStr="挂账出金";
												break;
												case 5:typeStr="银行间轧差划转";
												break;												
											}	
					%><%=typeStr%>&nbsp;</td>
					-->
					<td class="underLine" ><%
											String statusStr = null;
											switch(bankTransferValue.status)
											{
												case 0:statusStr="成功";
												break;
												case 1:statusStr="失败";
												break;
												case 2:statusStr="待审核";
												break;
												case 3:statusStr="待银行处理结果";
												break;
												case 4:statusStr="审核拒绝";
												break;	
											}		
					%><%=statusStr%>&nbsp;</td>
					<td class="underLine" ><%=bankTransferValue.info==null?"--":bankTransferValue.info%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>					
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="12">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="12" align=right>
				第<%=pageIndex%>/<%=obj.getPageCount()%>页 &nbsp;&nbsp;共<%=obj.getTotalCount()%>条 &nbsp;&nbsp;每页<%=pageSize%>条 &nbsp;&nbsp;
				<%
				if(pageIndex != 1)
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

				if(pageIndex != obj.getPageCount())
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

				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="12"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<TABLE width=100%>
	<TR align=center>
		<TD>
		<button type="button" class="smlbtn" onclick="add();">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function pgTurn(i)
{
	if(i == 0)
	{
		frm.pageIndex.value = 1;
		frm.submit();
	}
	else if(i == 1)
	{		
		frm.pageIndex.value = frm.pageIndex.value * 1 + 1;	
		frm.submit();
	}
	else if(i == 2)
	{
		frm.pageIndex.value = <%=obj.getPageCount()%>;
		frm.submit();
	}
	else if(i == -1)
	{
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}

function pgJumpChk()
{
	if(event.keyCode == 13)
	{
		if(isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>)
		{
			alert("请输入1 - <%=obj.getPageCount()%>间的数字！");			
		}
		else
		{
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}	
}

function doQuery()
{
	if(isNaN(frm.moneyQry.value)){
		alert("请输入正确的金额!");
		frm.moneyQry.value = "";
		frm.moneyQry.focus();
		return;
	}
	frm.pageIndex.value = 1;
	frm.submit();
}

function add(){
	document.location = "transfer.jsp";
}

//-->
</SCRIPT>
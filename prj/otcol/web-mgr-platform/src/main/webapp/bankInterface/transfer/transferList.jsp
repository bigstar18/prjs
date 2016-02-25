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
			<legend>���м��ʽ�ת��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">״̬&nbsp;</td>
					<td align="left">
						<select name="statusQry" class="normal" style="width: 100px">
							<OPTION value="-1">ȫ��</OPTION>
							<option value="0" <%=statusQry==0?"selected":""%>>�ɹ�</option>
              				<option value="1" <%=statusQry==1?"selected":""%>>ʧ��</option>
              				<option value="2" <%=statusQry==2?"selected":""%>>�����</option>
							<option value="3" <%=statusQry==3?"selected":""%>>�����д�����</option>
							<option value="3" <%=statusQry==4?"selected":""%>>��˾ܾ�</option>
						</select>
					</td>
					<td align="right">���&nbsp;</td>
					<td align="left">
						<input name="moneyQry" value="<%=moneyQry%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" >��¼��ˮ��</td>
				<td class="panel_tHead_MB" >������ˮ��</td>
				<td class="panel_tHead_MB" >��������</td>
				<td class="panel_tHead_MB" >�տ�����</td>
				<td class="panel_tHead_MB" >����ʱ��</td>
				<td class="panel_tHead_MB" >���</td>
				<td class="panel_tHead_MB" >����</td>
				<!--<td class="panel_tHead_MB" >����</td>-->
				<td class="panel_tHead_MB" >״̬</td>
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
												case 0:typeStr="����";
												break;
												case 1:typeStr="Ϊ����������";
												break;
												case 2:typeStr="�г�����";
												break;
												case 3:typeStr="����ƥ��";
												break;
												case 4:typeStr="���˳���";
												break;
												case 5:typeStr="���м����ת";
												break;												
											}	
					%><%=typeStr%>&nbsp;</td>
					-->
					<td class="underLine" ><%
											String statusStr = null;
											switch(bankTransferValue.status)
											{
												case 0:statusStr="�ɹ�";
												break;
												case 1:statusStr="ʧ��";
												break;
												case 2:statusStr="�����";
												break;
												case 3:statusStr="�����д�����";
												break;
												case 4:statusStr="��˾ܾ�";
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
				��<%=pageIndex%>/<%=obj.getPageCount()%>ҳ &nbsp;&nbsp;��<%=obj.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ<%=pageSize%>�� &nbsp;&nbsp;
				<%
				if(pageIndex != 1)
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

				if(pageIndex != obj.getPageCount())
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
				��<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ

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
		<button type="button" class="smlbtn" onclick="add();">���</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
			alert("������1 - <%=obj.getPageCount()%>������֣�");			
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
		alert("��������ȷ�Ľ��!");
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
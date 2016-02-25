<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%

BankDAO dao = BankDAOFactory.getDAO();

int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;

String stateSet = Tool.delNull(request.getParameter("state1"));
String operator = Tool.delNull(request.getParameter("operator"));
String remark = Tool.delNull(request.getParameter("remark"));

Vector <BankValue>bankList=dao.getBankList(" where validflag = 0");
String bankID = Tool.delNull(request.getParameter("bankID"));
String s_time = Tool.delNull(request.getParameter("s_time"));
if(s_time==null||s_time.trim().length()<=0){
	s_time=Tool.fmtDate(new Date());
}
java.util.Date data=new java.util.Date();
if(s_time!=null&&s_time.trim().length()>0){
	data=Tool.strToDate(s_time);
}
Vector dicList = dao.getQSBankDate(bankID,data);

int maxpage = dicList.size()%pageSize==0 ? dicList.size()/pageSize : dicList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(dicList, pageSize, pageIndex);

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
  </head>
  
  <body>
  	<form id="frm" action="" method="post" name="frm">
		<fieldset width="95%">
			<legend>����������������</legend>
			<input type=hidden name=operator value="${CURRENUSERID }">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">���д���&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px">
							<OPTION value="-1">��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++)
							{
								BankValue bv = (BankValue)bankList.get(i);
								
								%>
								<option value="<%=bv.bankID%>" <%=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
								<%
								
							}
							%>
						</select>
					</td>		
					<td align="right">����&nbsp;</td>
					<td align="left" colspan="3">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
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
				<td class="panel_tHead_MB" align=center>��������</td>
				<td class="panel_tHead_MB" align=center>����Ȩ��</td>	
				<td class="panel_tHead_MB" align=center>����Ȩ��</td>
				<td class="panel_tHead_MB" align=center>���տ���</td>	
				<td class="panel_tHead_MB" align=center>���տ���</td>					
				<td class="panel_tHead_MB" align=center>�������</td>
				<td class="panel_tHead_MB" align=center>���ճ���</td>
				<td class="panel_tHead_MB" align=center>����������</td>
				<td class="panel_tHead_MB" align=center>���տ��ñ仯��</td>
				<td class="panel_tHead_MB" align=center>����Ȩ��仯��</td>
				<td class="panel_tHead_MB" align=center>�鿴������Ȩ������</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
			BankQSNetChild bqc=(BankQSNetChild)obj.get(i);
			%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><%=bqc.bankName%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.lastDayQY)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.toDayQY)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.lastKY)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.todayKY)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.InMoney)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.OutMoney)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.todayFee)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.KYchange)%>&nbsp;</td>
					<td class="underLine" align=center><%=Tool.fmtDouble2(bqc.QYchange)%>&nbsp;</td>
					
					<td class="underLine" align=center><a href="#" onclick="window.location='firmBalance_new.jsp?bankID=<%=bqc.bankId%>&s_time=<%=s_time %>'" >�鿴</a>&nbsp;</td>	
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
		<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="11">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="11" align=right>
				��<%=pageIndex%>/<%=obj.getPageCount()%>ҳ &nbsp;&nbsp;��<%=obj.getTotalCount()%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
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
				<td class="panel_tFoot_MB" colspan="11"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<input type="hidden" name="bankID" value="<%=bankID %>"> 
	<input type="hidden" name="s_time" value="<%=s_time %>"> 
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doQuery()
{
	frm.submit();
}
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
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
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
//-->
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
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
String filter = " ";

String tradeID = Tool.delNull(request.getParameter("tradeID"));
if(!tradeID.trim().equals(""))
{
	filter += " and t.firmid='"+ tradeID +"'";
}

String systemID = Tool.delNull(request.getParameter("systemID"));
if(!systemID.trim().equals("-1")&&!systemID.trim().equals(""))
{
	filter += " and t.systemID='"+ systemID +"'";
}

String s_time = Tool.delNull(request.getParameter("s_time"));
if(s_time == null || "".equals(s_time)){
	s_time = Tool.fmtDate(new java.util.Date());
}
if(!s_time.trim().equals(""))
{
	filter += " and trunc(createdate) = to_date('"+s_time+"','yyyy-mm-dd')";
}
filter += " order by createdate desc";

Vector moneyList = dao.marketTradeQuery(null,filter);

int maxpage = moneyList.size()%pageSize==0 ? moneyList.size()/pageSize : moneyList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(moneyList, pageSize, pageIndex);

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>���г�������ϸ</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>���г�������ϸ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td align="right">�����̴���&nbsp;</td>
					<td align="left">
						<input name="tradeID" value="<%=tradeID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>
					<td align="right">ģ��&nbsp;</td>
					<td align="left">
						<select name="systemID" class="normal" style="width: 100px">
							<OPTION value="-1">ȫ��</OPTION>
							<%
								for(int i=0;i<vecSystemList.size();i++){
									SystemMessage vslist = vecSystemList.get(i);
									%>
									<option value="<%=vslist.systemID%>" <%=vslist.systemID.equals(systemID)?"selected":""%>><%=vslist.systemID%>|<%=vslist.systemName%></option>
									<%
								}
							%>
						</select>
					</td>
					<td align="right">��������&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				
					<td colspan="2" align="center">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">�����̴���</td>
				<td class="panel_tHead_MB" align="center">ģ���</td>
				<td class="panel_tHead_MB" align="center">ģ������</td>
				<td class="panel_tHead_MB" align="center">�������</td>
				<td class="panel_tHead_MB" align="center">�������</td>
				<td class="panel_tHead_MB" align="center">����Ȩ��</td>
				<td class="panel_tHead_MB" align="center">����Ȩ��</td>
				<td class="panel_tHead_MB" align="center">���ճ����</td>
				<td class="panel_tHead_MB" align="center">����������</td>
				<td class="panel_tHead_MB" align="center">��������</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				QueryTradeData money = (QueryTradeData)obj.get(i);
				%>
				<tr height="22" align=center >
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=money.tradeID%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.systemID%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.systemName%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.lastBalance)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.balance)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.lastRights)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.rights)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.fundIO)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.fee)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.tradeDate%>&nbsp;</td>
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
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10" align=right>
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
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
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

function doQuery()
{
	frm.pageIndex.value = 1;
	frm.submit();
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
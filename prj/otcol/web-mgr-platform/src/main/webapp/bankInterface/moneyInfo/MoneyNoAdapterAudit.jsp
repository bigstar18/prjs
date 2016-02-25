<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	String filter = " where status=13 and type!=2 ";
	
	String firmID = Tool.delNull((String)request.getParameter("firmID"));
	if(!firmID.trim().equals(""))
	{
		filter += " and firmid='"+ firmID +"'";
	}
	
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	if(!bankID.trim().equals(""))
	{
		filter += " and bankID='"+ bankID +"'";
	}
	
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(!s_time.trim().equals(""))
	{
		filter += " and createtime>=to_date('"+s_time+" 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	}
	
	String e_time = Tool.delNull((String)request.getParameter("e_time"));
	if(!e_time.trim().equals(""))
	{
		filter += " and createtime<=to_date('"+e_time+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
	}
	
	filter += " order by createtime desc,id desc ";
	
	Vector moneyList = dao.getCapitalInfoList(filter);
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
	<script language="javascript" src="../lib/validate.js"></script>
    <title>出金审核列表</title>
  </head>
  
  <body>
  	<form id="frm" action="MoneyNoAdapterAudit2.jsp" method="post">
		<fieldset width="95%">
			<legend>出金审核查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td align="right">交易商代码&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="right">银行代码&nbsp;</td>
					<td align="left">
						<input name="bankID" value="<%=bankID%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
				
					<td align="right">日期&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
						&nbsp;至&nbsp;
						<MEBS:calendar eltName="e_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=e_time%>"/>
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1600px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
  				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'ck')"></td>
  				<td class="panel_tHead_MB" align="center">记录流水号</td>
				<td class="panel_tHead_MB" align=center>市场流水号</td>
				<td class="panel_tHead_MB" align=left>交易商代码</td>
				<td class="panel_tHead_MB" align=left>银行代码</td>
				<!--<td class="panel_tHead_MB" align=left>是否加急</td>-->
				<td class="panel_tHead_MB" align=right>金额&nbsp;</td>
				<td class="panel_tHead_MB" align=left>&nbsp;创建时间</td>
				<td class="panel_tHead_MB" align=left>备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				CapitalValue money = (CapitalValue)obj.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><input name="ck" type="checkbox" value='<%=money.actionID%>'></td>
					<td class="underLine" align=center><%=money.iD%></td>
					<td class="underLine" align=center><%=money.actionID%>&nbsp;</td>
					<td class="underLine" align=left><%=money.firmID%>&nbsp;</td>
					<td class="underLine" align=left><%=money.bankID%>&nbsp;</td>
					<!--<td class="underLine" align=left>
					<%=money.express==1 ?"加急" : "正常"%>&nbsp;
					</td>-->
					<td class="underLine" align=right><%=Tool.fmtDouble2(money.money)%>&nbsp;</td>
					<td class="underLine" align=left>&nbsp;<%=Tool.fmtTime(money.createtime)%>&nbsp;</td>
					<td class="underLine" align=left><%=Tool.delNull(money.note)%>&nbsp;</td>
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
				第<%=pageIndex%>/<%=obj.getPageCount()%>页 &nbsp;&nbsp;共<%=obj.getTotalCount()%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
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
				<td class="panel_tFoot_MB" colspan="8"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	<TABLE width=100%>
	<TR align=center>
		<TD>
		<button type="button" id="audioBtn" class="smlbtn" onclick="handleData(frm,tableList,'ck','yes')">审核通过</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="smlbtn" onclick="deleteRec(frm,tableList,'ck','no')">审核拒绝</button>
		<input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function handleData(v1,v2,v3,v4)
{
	hiddenBtn();
	if(!deleteRec(v1,v2,v3,v4))
	{
		//document.getElementById("audioBtn").disabled="true";
		document.getElementById("audioBtn").disabled=false;	
	}
}
function hiddenBtn()
{
	document.getElementById("audioBtn").disabled=true;
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

function doQuery()
{
	var firmID = frm.firmID.value;
	var bankID = frm.bankID.value;
	if(!calibration("str",firmID)){
		alert("交易商信息非法字符");
		frm.firmID.focus();
	}else if(!calibration("str",bankID)){
		alert("银行编号信息非法字符");
		frm.bankID.focus();
	}else{
		frm.pageIndex.value = 1;
		frm.submit();
	}
}

function pgJumpChk()
{
	if(event.keyCode == 13)
	{
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=obj.getPageCount()%>) && (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 ))
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


//-->
</SCRIPT>
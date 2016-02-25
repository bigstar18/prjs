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
	String filter = " where 1=1 ";
	
	String logopr = Tool.delNull(request.getParameter("logopr"));
	if(!logopr.trim().equals(""))
	{
		filter += " and logopr='"+ logopr +"'";
	}
	String logdate = Tool.delNull(request.getParameter("logdate"));
	if(!logdate.trim().equals(""))
	{
		filter += " and logdate>=to_date('"+ logdate +" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		filter += " and logdate<=to_date('"+ logdate +" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
	}
	filter += " order by logid desc ";
	
	Vector logList = dao.logList(filter);
	ObjSet obj = ObjSet.getInstance(logList, pageSize, pageIndex);
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
	<script language="javascript" src="../lib/validate.js"></script>
    <title>凭证列表</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>操作日志查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1200px" height="35">
				<tr height="35">
					<td align="right">操作员代码&nbsp;</td>
					<td align="left">
						<input name="logopr" value="<%=logopr %>" type="text"  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="right">操作日期&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="logdate" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=logdate%>"/>
					</td>					
					<td colspan="4" align="right">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="frm.reset();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
			<br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="1200px" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align="center">日志编号</td>
				<td class="panel_tHead_MB" align="left">操作员代码</td>
				<td class="panel_tHead_MB" align="left">操作内容</td>
				<td class="panel_tHead_MB" align="left">操作日期</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				LogValue lv = (LogValue)obj.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=lv.getLogid()%>&nbsp;</td>
					<td class="underLine" align="left"><%=lv.getLogopr() %>&nbsp;</td>
					<td class="underLine" align="left"><%=lv.getLogcontent() %>&nbsp;</td>
					<td class="underLine" align="left"><%=lv.getLogdate() %>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="4" align=right>
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
				<td class="panel_tFoot_MB" colspan="4"></td>
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
	var logopr = frm.logopr.value;
	if(!calibration("str",logopr)){
		alert("操作员信息非法字符");
		frm.logopr.focus();
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
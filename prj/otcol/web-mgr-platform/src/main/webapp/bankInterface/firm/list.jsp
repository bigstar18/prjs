<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("del"))
{
	String[] firmIDs = request.getParameterValues("ck");
	if(firmIDs != null)
	{		
		for(int i=0;i<firmIDs.length;i++)
		{
			try
			{
				dao.delFirm(firmIDs[i]);
				String filter=" where userID='"+firmIDs[i]+"'";
				dao.delFeeInfo(filter);//删除交易商的手续费用列表
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}	
}



int pageSize = BANKPAGESIZE;
int size = Tool.strToInt(request.getParameter("pageSize"));
if(size>0){
	pageSize = size;
}
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
if(pageIndex < 0)  pageIndex = 1;
String filter = " where 1=1 ";

String firmID = Tool.delNull(request.getParameter("firmID"));
if(!firmID.trim().equals(""))
{
	filter += " and firmid='"+ firmID +"'";
}

String name = Tool.delNull(request.getParameter("name"));
if(!name.trim().equals(""))
{
	filter += " and name like '%"+ name +"%'";
}

filter += " order by firmid ";


Vector dicList = dao.getFirmList(filter);
int maxpage = dicList.size()%pageSize==0 ? dicList.size()/pageSize : dicList.size()/pageSize+1;
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
ObjSet obj = ObjSet.getInstance(dicList, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
	<script language="javascript" src="../lib/validate.js"></script>
  </head>
  
  <body>
  	<form id="frm" action="" method="post" name="frm">
		<fieldset width="95%">
			<legend>平台账户查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1200px" height="35">
				<tr height="35">
					<td align="right">平台账户&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>		
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
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
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'ck')"></td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB" align=right>单笔最大转账金额</td>				
				<td class="panel_tHead_MB" align=right>每日最大转账次数</td>
				<td class="panel_tHead_MB" align=right>每日最大转账金额</td>
				<td class="panel_tHead_MB" align=right>出金审核额度</td>
				<!--td class="panel_tHead_MB" align=center>手续费设置></td--><td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB" align=center>银行帐号管理</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				FirmValue firm = (FirmValue)obj.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><input name="ck" type="checkbox" value='<%=firm.firmID%>'></td>
					<td class="underLine"><a href="#" onclick="firmInfo('<%=firm.firmID%>');"><%=firm.firmID%></a>&nbsp;</td>
					<td class="underLine" align=right><%=firm.maxPerSglTransMoney<=0?"--":Tool.fmtDouble2(firm.maxPerSglTransMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=firm.maxPerTransCount<=0?"--":firm.maxPerTransCount%>&nbsp;</td>
					<td class="underLine" align=right><%=firm.maxPerTransMoney<=0?"--":Tool.fmtDouble2(firm.maxPerTransMoney)%>&nbsp;</td>
					<td class="underLine" align=right><%=firm.maxAuditMoney<=0?"--":Tool.fmtDouble2(firm.maxAuditMoney)%>&nbsp;</td>
					<!--td class="underLine" align=center><a href="#" onclick="window.location='feeSet.jsp?firmID=<%=firm.firmID%>';">设置</a>&nbsp;</td--><td class="underLine">&nbsp;</td>
					<td class="underLine" align=center><a href="accountMng.jsp?firmID=<%=firm.firmID%>">管理</a>&nbsp;</td>
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
		<TD><input type=hidden name=submitFlag value="">
		</TD>
	</TR>
	</TABLE>
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function goToAddFirm(){
	var result = window.showModalDialog("addFirm.jsp","","dialogWidth=500px; dialogHeight=400px; status=yes;scroll=yes;help=no;");	
	if(result){
			window.location.reload();
		}
}
function firmInfo(v){
	var result = window.showModalDialog("modFirm.jsp?firmID="+v+"","","dialogWidth=500px; dialogHeight=400px; status=yes;scroll=yes;help=no;");	
	if(result){
			window.location.reload();
		}
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
	var firmID = frm.firmID.value;
	if(!calibration("str",firmID)){
		alert("交易商信息非法字符");
		frm.firmID.focus();
	}else{
		frm.pageIndex.value = 1;
		frm.submit();
	}
}

//-->
</SCRIPT>
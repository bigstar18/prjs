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

String firmID = Tool.delNull(request.getParameter("firmID"));
if(!firmID.trim().equals(""))
{
	filter += " and firmid='"+ firmID +"'";
}

String bankID = Tool.delNull(request.getParameter("bankID"));
if(!bankID.trim().equals("")&&!bankID.trim().equals("-1"))
{
	filter += " and bankID='"+ bankID +"'";
}

String primaryBankFlag = Tool.delNull(request.getParameter("primaryBankFlag"));
if(!primaryBankFlag.trim().equals("")&&!primaryBankFlag.trim().equals("-1"))
{
	filter += " and primaryBankFlag="+ primaryBankFlag +"";
}

String s_time = Tool.delNull(request.getParameter("s_time"));
if(s_time == null || "".equals(s_time)){
	s_time = Tool.fmtDate(new java.util.Date());
}
if(!s_time.trim().equals(""))
{
	filter += " and b_date = to_date('"+s_time+"','yyyy-mm-dd')";
}

filter +=" order by b_date desc";

Vector moneyList = dao.getFirmBankFundsHis(filter);

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
    <title>历史资金查询</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>历史资金查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="35">
				<tr height="35">
					<td align="right">平台代码&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="20" style="width: 100px">
					</td>
					<td align="right">银行代码&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width: 100px">
							<OPTION value="-1">全部</OPTION>
							<%
								for(int i=0;i<vecBankList.size();i++){
									BankValue bankv = vecBankList.get(i);
									%><option value="<%=bankv.bankID%>" <%=bankv.bankID.equals(bankID)?"selected":""%>><%=bankv.bankID%>|<%=bankv.bankName%></option><%
								}
							%>
						</select>
					</td>
					<td align="right">主次结算行&nbsp;</td>
					<td align="left">
						<select name="primaryBankFlag" class="normal" style="width: 100px">
							<OPTION value="-1">全部</OPTION>
							<option value="1" <%="1".equals(primaryBankFlag)?"selected":""%>>主结算行</option>
              				<option value="2" <%="2".equals(primaryBankFlag)?"selected":""%>>次结算行</option>
						</select>
					</td>
					<td align="right">结算日期&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				
					<td colspan="2" align="center">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
				<td class="panel_tHead_MB" align="center">平台代码</td>
				<td class="panel_tHead_MB" align="center">银行编号</td>
				<td class="panel_tHead_MB" align="center">银行名称</td>
				<td class="panel_tHead_MB" align="center">余额</td>
				<td class="panel_tHead_MB" align="center">出金冻结</td>
				<td class="panel_tHead_MB" align="center">出入金差额</td>
				<td class="panel_tHead_MB" align="center">权益冻结</td>
				<td class="panel_tHead_MB" align="center">权益</td>
				<td class="panel_tHead_MB" align="center">手续费</td>
				<td class="panel_tHead_MB" align="center">主次银行</td>
				<td class="panel_tHead_MB" align="center">结算日期</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				FirmBankFunds money = (FirmBankFunds)obj.get(i);
				%>
				<tr height="22" align=center >
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align="center"><%=money.firmID%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.bankID%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.bankName%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.balance)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.outMoneyFrozenFunds)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.OutInMoney)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.RIGHTSFROZENFUNDS)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.RIGHTS)%>&nbsp;</td>
					<td class="underLine" align="center"><%=Tool.fmtDouble2(money.FIRMFEE)%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.primaryBankFlag==1?"主结算行":"次结算行"%>&nbsp;</td>
					<td class="underLine" align="center"><%=money.b_date%>&nbsp;</td>
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
				<td class="panel_tFoot_MB" colspan="11"></td>
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
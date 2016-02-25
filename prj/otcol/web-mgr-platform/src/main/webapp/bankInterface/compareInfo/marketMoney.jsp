<%@ page contentType="text/html;charset=GBK"%>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
	int pageSize = PAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex = Tool.strToInt(request.getParameter("pageIndex"));
	if (pageIndex < 0)
		pageIndex = 1;
	ObjSet obj =null;
	String resultToBank = (String)request.getSession().getAttribute("resultToBank");
	String resultToBankDate = (String)request.getSession().getAttribute("resultToBankDate");
	//获得市场总额
	BankDAO dao = BankDAOFactory.getDAO();
	boolean isOK = false;
	String message = "";
	if(resultToBankDate!=null)
	{
		Date  result2BankDate = Tool.strToDate(resultToBankDate);
		Vector<FundsAndInterests> v = dao.getFundsAndInterestsInVector(result2BankDate,2);
		obj = ObjSet.getInstance(v, pageSize, pageIndex);
		if("OK".equals(resultToBank))
		{
			isOK = true;
			message = "查看"+resultToBankDate+"各交易商的权益信息。";
		}
		else
		{
			message = "暂未发送交易后数据到银行。";
		}
	}	
	else
	{
		obj = ObjSet.getInstance(new Vector(), pageSize, pageIndex);
		message = "出入金明细对账暂未成功。";
	}
	
	String optValue = request.getParameter("opt");
	if(isOK && "MoneyMsgToBank".equals(optValue))
	{
		Date  resultBankDate = Tool.strToDate(resultToBankDate);
		Hashtable ht = dao.getFundsAndInterests(resultBankDate,2);
		Enumeration firmids = ht.keys();

		int htlen = 0;
		int listlen = 0;
		int resultNum = 0;
		while (firmids.hasMoreElements()) {
		    String key = (String)firmids.nextElement();
		    List bankidList = dao.getFirmBankList(key);
		    if(bankidList!=null)
		    {
		    	listlen = bankidList.size();
		    	htlen = htlen + listlen;//记录非空list的大小之和
		    }
		    Hashtable innerht = new Hashtable();
		    innerht.put(key,ht.get(key));
		    for(int i=0;bankidList!=null&&i<bankidList.size();i++)
			{
				int result = 0;//cp.sendTotalMoneyToBank((String)bankidList.get(i),innerht);
				if(result == 0)
				{
					resultNum = resultNum + 1;//记录非空list传送后成功的数目
				}
			}
		}
		if(htlen == resultNum)
		{
			message = "市场总额全部成功发送。";
		}
		else
		{
			message = "市场总额部分成功发送。";
		}
		
		LogValue lv = new LogValue(AclCtrl.getLogonID(request),"发送市场总额到银行"+Tool.fmtTime(new java.util.Date()),new Date());
		lv.setIp(computerIP);
		dao.log(lv);
		
	}
%>

<html xmlns:MEBS>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=GBK">
		<IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
		<title></title>
	</head>

	<body>
		<form id="frm" name="frm" action="" method="post">
			<fieldset width="95%">
				<legend>发送市场总额到银行</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
					<tr height="35">
						<td align="left">发市场总额到银行条件：</td>
					</tr>
					<tr height="35">
						<td align="left">&nbsp;&nbsp;1、出入金明细对账成功</td>
					</tr>
					<tr height="35">
						<td align="left">&nbsp;&nbsp;2、发交易数据到银行成功</td>
					</tr>
					<tr height="35">
						<td align="left">目前状态：<font style='color: red'><%=message %>&nbsp;</font> </td>
					</tr>
			</table>
			</fieldset><br>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="300">
				<tHead>
					<tr height="25" align=center>
						<td class="panel_tHead_LB"> &nbsp; </td>
						<td class="panel_tHead_MB"> 交易商代码 </td>
						<td class="panel_tHead_MB"> 资金余额 </td>
						<td class="panel_tHead_MB"> 交易保证金 </td>
						<td class="panel_tHead_MB"> 交收保证金 </td>
						<td class="panel_tHead_MB"> 当日盈亏 </td>
						<td class="panel_tHead_MB"> 浮动盈亏 </td>
						<td class="panel_tHead_MB"> 当前权益 </td>
						<td class="panel_tHead_RB"> &nbsp; </td>
					</tr>
				</tHead>
				
				<%if(!isOK){ %>
				<tBody>
					<tr height="22" align=center>
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine" colspan="7"><font style='color: red'><%=message %></font></td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
				</tBody>
				<%}else{ %>
				<tBody>
					<%
							for (int i = 0; i < obj.getCurNum(); i++) {
							FundsAndInterests fai = (FundsAndInterests) obj.get(i);
					%>
					<tr height="22" align=center>
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine"> <%=fai.getFirmid()%>&nbsp; </td>
						<td class="underLine" align=right> <%=fai.getBalance()%>&nbsp; </td>
						<td class="underLine" align=right> <%=fai.getRuntimeMargin()%>&nbsp; </td>
						<td class="underLine" align=right> <%=fai.getRuntimeSettleMargin()%>&nbsp; </td>
						<td class="underLine" align=right> <%=fai.getRuntimeFL()%>&nbsp; </td>
						<td class="underLine" align=right> <%=fai.getFloatingloss()%>&nbsp; </td>
						<td class="underLine" align=right> <%=fai.getFundsInterests()%>&nbsp; </td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
					<%
					}
					%>
				</tBody>
				<%} %>
				<tFoot>
					<tr height="100%">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td colspan="7">&nbsp;</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="pager" colspan="7" align=right>
							第
							<%=pageIndex%>
							/
							<%=obj.getPageCount()%>
							页 &nbsp;&nbsp;共
							<%=obj.getTotalCount()%>
							条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" type="text" style="width:25px;" value="<%=pageSize%>" onkeydown="return pgJumpChk()">
							条 &nbsp;&nbsp;
							<%
							if (pageIndex != 1) {
							%>
							<span style="cursor:hand" onclick="pgTurn(0)">首页</span>
							&nbsp;&nbsp;
							<span style="cursor:hand" onclick="pgTurn(-1)">上页</span>
							&nbsp;&nbsp;
							<%
							} else {
							%>
							首页 &nbsp;&nbsp;上页 &nbsp;&nbsp;
							<%
								}

								if (pageIndex != obj.getPageCount()) {
							%>
							<span style="cursor:hand" onclick="pgTurn(1)">下页</span>
							&nbsp;&nbsp;
							<span style="cursor:hand" onclick="pgTurn(2)">尾页</span>
							&nbsp;&nbsp;
							<%
							} else {
							%>
							下页 &nbsp;&nbsp;尾页 &nbsp;&nbsp;
							<%
							}
							%>
							到
							<input class="text" type="text" style="width:25px;"
								name="pageJumpIdx" onkeydown="return pgJumpChk()">
							页

							<input name=pageIndex type=hidden value="<%=pageIndex%>">
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
			<TABLE width=100%>
				<TR align=center>
					<TD>
					<%if(isOK){ %>
						<input type="button" class="bigbtn" value="传市场总额到银行" onclick="sendMoneyMsgToBank()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%} %>
					</TD>
				</TR>
			</TABLE>
			<input type="hidden" name="opt">
			</from>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function sendMoneyMsgToBank()
{
	frm.opt.value="MoneyMsgToBank";
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

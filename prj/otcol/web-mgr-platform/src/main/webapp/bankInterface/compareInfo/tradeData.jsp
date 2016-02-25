<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
	String resultToBankDate = (String)request.getSession().getAttribute("resultToBankDate");
	BankDAO dao = BankDAOFactory.getDAO();

	int pageSize = PAGESIZE;
	int size = Tool.strToInt(request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex = Tool.strToInt(request.getParameter("pageIndex"));
	if (pageIndex < 0)
		pageIndex = 1;
	ObjSet obj =null;
	
	boolean checkSuccess = false;
	String checkMsg = "";
	boolean marketStutas = dao.getTraderStatus();
	Vector<CompareResult> compareResultList = null;
	if(resultToBankDate!=null)
	{
		if(marketStutas)
		{
			java.util.Date result2BankDate = Tool.strToDate(resultToBankDate);
			compareResultList = cp.checkMoney(result2BankDate);	
			if(compareResultList.size() == 0)
			{
				checkSuccess = true;
			}
			else 
			{
				checkSuccess = false;
				checkMsg = "系统结算完毕，对账不平！";
			}
		}
		else
		{
			checkSuccess = false;
			checkMsg = "系统尚未结算！";
		}
	}
	else
	{
		checkMsg = "未对账或对账失败，暂时不能发送交易数据！";
	}
		
	String filter = " 1=1 ";
	String firmID = Tool.delNull(request.getParameter("firmID"));
	if(firmID==null||"".equals(firmID))
	{
		firmID="-1";
	}
	String moduleId = Tool.delNull(request.getParameter("moduleid"));
	int moduleid = -1;
	if(!"".equals(moduleId.trim()))
	{
		moduleid = Integer.parseInt(moduleId);
	}
	if (!firmID.trim().equals("")&&!"-1".equals(firmID)) {
		filter += " and f.firmID='" + firmID + "' ";
	}
	String s_time = (String)request.getAttribute("s_time");
	if(s_time==null)
	{
		s_time = Tool.fmtDate(new java.util.Date()) ;
		filter += " and f.b_date>=to_date('" + s_time+ "','yyyy-mm-dd')";
	}
	String e_time = (String)request.getAttribute("e_time");
	if(e_time==null)
	{
		e_time = Tool.fmtDate(new java.util.Date()) ;
		filter += " and f.b_date<=to_date('" + e_time+ "','yyyy-mm-dd')";
	}
	List list = dao.getTradeDataInList(filter,moduleid);
	obj = ObjSet.getInstance(list, pageSize, pageIndex);
	
	String oprValue = request.getParameter("opt");
	//核对成功,传数据到银行，返回结果成功则传市场总额到银行对账
	int resultToBank = 0;//发数据到银行的操作结果
	String showMsg = "";
	boolean showSendMsg = false;
	if(checkSuccess && oprValue!=null && "DataToBank".equals(oprValue.trim()))
	{
		Hashtable ht = dao.getTradeDataInHashTable(filter,moduleid);
		resultToBank = 0;//cp.setMoneyInfoByHashtable(null,ht);
		if(resultToBank==0)
		{
			showMsg = "传数据到银行成功！";
			request.getSession().setAttribute("resultToBank","OK");
		}
		else if(resultToBank==1)
		{
		showMsg = "没有找到对应的适配器！";
		}
		else if(resultToBank==3)
		{
		showMsg = "异常！";
		}
		else if(resultToBank>100)
		{
		showMsg = "处理过程中有"+(resultToBank-100)+"个适配器出现错误！";
		}
		showSendMsg = true;
		
		LogValue  = new LogValue(AclCtrl.getLogonID(request),"传交易数据到银行");
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
				<legend>交易数据</legend>
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					height="35">
					<tr height="35">
					<td align="right"> 交易商代码&nbsp; </td>
						<td align="left">
							<select id="firmID" name="firmID" class="normal" style="width: 100px">
							<OPTION value="-1">请选择</OPTION>
							<%
							Vector firmList = dao.getFirmList("");
							for(int i=0;i<firmList.size();i++)
							{
								FirmValue bv = (FirmValue)firmList.get(i);
								%>
								<option value="<%=bv.firmID%>"><%=bv.name%></option>		
								<%
							}
							%>							
						</select>
						<script type="text/javascript">
							frm.firmID.value='<%=firmID %>';
						</script>
						</td>
						<td align="right"> 交易板块&nbsp; </td>
						<td align="left">
							<select name="moduleid" class="normal" style="width: 50px">
								<OPTION value="-1">全部</OPTION>
								<option value="2">远期</option>
								<option value="3">现货</option>
								<option value="4">竞价</option>
							</select>
						</td>
						<td align="right">
							日期&nbsp;
						</td>
						<td align="left">
							<MEBS:calendar eltName="s_time" eltStyle="width:80px"
								eltImgPath="../skin/default/images/" eltValue="<%=s_time%>" />
							&nbsp;至&nbsp;
							<MEBS:calendar eltName="e_time" eltStyle="width:80px"
								eltImgPath="../skin/default/images/" eltValue="<%=e_time%>" />
						</td>
						<td align="left">
							<button <%if(!checkSuccess){out.print(" disabled='disabled' ");} %> type="button" class="smlbtn" onclick="doQuery();">
								查询
							</button>
							&nbsp;
							<button type="button" class="smlbtn" onclick="frm.reset();">
								重置
							</button>
							&nbsp;
						</td>
					</tr>
				</table>
			</fieldset><br>
			<table id="tableList" border="0" cellspacing="0" cellpadding="0"
				width="100%" height="400">
				<tHead>
					<tr height="25" align=center>
						<td class="panel_tHead_LB"> &nbsp; </td>
						<td class="panel_tHead_MB"> 市场流水号 </td>
						<td class="panel_tHead_MB"> 交易商代码 </td>
						<td class="panel_tHead_MB"> 银行代码 </td>
						<td class="panel_tHead_MB"> 银行账号 </td>
						<td class="panel_tHead_MB"> 业务代码 </td>
						<td class="panel_tHead_MB"> 交易发生额 </td>
						<td class="panel_tHead_MB"> 资金余额 </td>
						<td class="panel_tHead_MB"> 附加帐金额 </td>
						<td class="panel_tHead_MB"> 结算日期 </td>
						<td class="panel_tHead_RB"> &nbsp; </td>
					</tr>
				</tHead>
				
				<%if(!checkSuccess&&!showSendMsg){ %>
				<tBody>
					<tr height="22" align=center  onclick="selectTr();">
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine" colspan="9"><font style="color: red"><%=checkMsg %></font></td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
				</tBody>
				<%}else if(checkSuccess&&!showSendMsg){ %>
				<tBody>
					<tr height="22" align=center onclick="selectTr();">
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine" colspan="9"><%=showMsg %></td>
						<td class="panel_tBody_RB"> &nbsp; </td>
					</tr>
				</tBody>
				<%}else{ %>
				<tBody>
					<%
							for (int i = 0; i < obj.getCurNum(); i++) {
							TradeResultValue trv = (TradeResultValue) obj.get(i);
					%>
					<tr height="22" align=center>
						<td class="panel_tBody_LB"> &nbsp; </td>
						<td class="underLine"><%=trv.fundFlowId%> &nbsp; </td>
						<td class="underLine"><%=trv.firmid%> &nbsp; </td>
						<td class="underLine"><%=trv.bankid%> &nbsp; </td>
						<td class="underLine"><%=trv.account%> &nbsp; </td>
						<td class="underLine"><%=trv.oprCode%> &nbsp; </td>
						<td class="underLine" align=right><%=trv.amount%> &nbsp; </td>
						<td class="underLine" align=right><%=trv.balance%> &nbsp; </td>
						<td class="underLine" align=right><%=trv.appendAmount%> &nbsp; </td>
						<td class="underLine"><%=trv.date%> &nbsp; </td>
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
						<td colspan="9">&nbsp;</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tBody_LB">&nbsp;</td>
						<td class="pager" colspan="9" align=right>
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
							<input class="text" type="text" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">
							页
							<input name=pageIndex type=hidden value="<%=pageIndex%>">
						</td>
						<td class="panel_tBody_RB">&nbsp;</td>
					</tr>
					<tr height="22">
						<td class="panel_tFoot_LB">&nbsp;</td>
						<td class="panel_tFoot_MB" colspan="9"></td>
						<td class="panel_tFoot_RB">&nbsp;</td>
					</tr>
				</tFoot>
			</table>
			<TABLE width=100%>
				<TR align=center>
					<TD>
					<%if(checkSuccess){ %>
						<input type="button" class="bigbtn" value="传交易数据到银行" onclick="sendDataToBank()">
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
	var bankid = frm.firmID.value;
	//var s_time = frm.s_time.value;
	//var e_time = frm.s_time.value;
	if(bankid == -1)
	{
		alert("请选择交易商！");
	}
	else
	{
		frm.pageIndex.value = 1;
		frm.opt.value="do";
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
function sendDataToBank()
{
	frm.opt.value="DataToBank";
	frm.submit();
}
//-->
</SCRIPT>

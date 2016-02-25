<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../../globalDef.jsp"%>
<%@ include file="../../../session.jsp"%>

<%
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	BankFirmRightValue bfr = new BankFirmRightValue();
	String firmID = Tool.delNull((String)request.getParameter("firmID"));
	if(!firmID.trim().equals(""))
	{
		bfr.firmId = firmID;
	}
	
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	if(!bankID.trim().equals("") && !bankID.trim().equals("-1"))
	{
		bfr.bankId = bankID;
	}
	
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(!s_time.trim().equals(""))
	{
		bfr.bdate = new java.sql.Timestamp(Tool.getDateTime(s_time).getTime());
	}
	Vector moneyList = new Vector(dao.getBankCapital(bfr));
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
    <script language="javascript" src="../../../lib/tools.js"></script>
    <title>工商银行分分合对</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>工商银行分分合对</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易商代码&nbsp;</td>
					<td align="left">
						<input name="firmID" value="<%=firmID%>" type=text  class="text" maxlength="10" style="width: 100px">
					</td>
					<td align="right">银行代码&nbsp;</td>
					<td align="left">
						<select name="bankID" class="normal" style="width:120px">
							<OPTION value="-1">请选择</OPTION>
							<%
							for(int i=0;i<bankList.size();i++)
							{
								BankValue bv = (BankValue)bankList.get(i);
								if(!noAdapterBank(bv.bankID)){
								%>
								<option value="<%=bv.bankID%>" <%=bankID.equals(bv.bankID)?"selected":""%>><%=bv.bankName%></option>		
								<%
								}
							}
							%>
						</select>
						<script type="text/javascript">
							frm.bankID.value='<%="".equals(bankID)?-1:bankID %>';
						</script>
					</td>
				
					<td align="right">日期&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="<%=request.getContextPath()%>/bankInterface/skin/default/images/" eltValue="<%=s_time%>"/>
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
  				<td class="panel_tHead_MB" align="center">银行编号</td>
				<td class="panel_tHead_MB" align="center">日期</td>
				<td class="panel_tHead_MB" align="center">交易商编号</td>
				<td class="panel_tHead_MB" align=right>银行端权益&nbsp;</td>
				<td class="panel_tHead_MB" align=right>市场端权益&nbsp;</td>
				<td class="panel_tHead_MB" align="center">不平原因</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>

	  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				BankFirmRightValue bfr2 = (BankFirmRightValue)obj.get(i);
				%>
				<tr height="22" align=center onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine" align=center><%=bfr2.bankId%></td>
					<td class="underLine" align=center><%=Tool.fmtDate(bfr2.bdate)%>&nbsp;</td>
					<td class="underLine" align=center>&nbsp;<%=bfr2.firmId%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(bfr2.bankRight)%>&nbsp;</td>
					<td class="underLine" align=right><%=Tool.fmtMoney(bfr2.maketRight)%>&nbsp;</td>
					<td class="underLine" align=center><%
					if(bfr2.reason == 0){
						out.print("金额不平");
					}else if(bfr2.reason == 1){
						out.print("银行端账户未建立");
					}else if(bfr2.reason == 2){
						out.print("市场端账户未建立");
					}
					%>&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
	  			</tr>	
				<%
			}
			%>				  	
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="6">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="6" align=right>
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
				<td class="panel_tFoot_MB" colspan="6"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
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
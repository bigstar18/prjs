<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>

<%
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	boolean disableBtn = false;
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = BANKPAGESIZE;
	int size = Tool.strToInt((String)request.getParameter("pageSize"));
	if(size>0){
		pageSize = size;
	}
	int pageIndex= Tool.strToInt((String)request.getParameter("pageIndex"));
	if(pageIndex < 0)  pageIndex = 1;
	String bankID = Tool.delNull((String)request.getParameter("bankID"));
	
	String s_time = Tool.delNull((String)request.getParameter("s_time"));
	if(s_time.equals(""))
	{
		s_time = Tool.fmtDate(new Timestamp(System.currentTimeMillis()));
	}
	ObjSet obj =null;
	Vector bankList = dao.getBankList(" where validFlag=0 ");
	String result = "";
	Vector BatFailResult =null;
	
	if("turnPage".equals(request.getParameter("opt")) || request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{//查询银行对账不平信息
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			BatFailResult = dao.getFirmBalanceError(null,bankID,Tool.strToDate(s_time));
		}
		if(BatFailResult==null)
		{
			result = "查询银行对账不平信息异常，银行代码："+bankID+"，对账时间："+s_time+"时间："+new java.util.Date();
		}
		else if(BatFailResult.size() == 0)
		{
			result = "银行对账成功，银行代码："+bankID+"，对账时间："+s_time+"时间："+new java.util.Date();
		}
		else 
		{
			result = "银行对账不成功："+bankID+"，对账时间："+s_time+"时间："+new java.util.Date();
			obj = ObjSet.getInstance(BatFailResult, pageSize, pageIndex);
		}
		lv.setLogcontent("对银行和市场的签解约信息:"+result);
		dao.log(lv);
	}
	else if("getData".equals(request.getParameter("submitFlag")))
	{
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogdate(new Date());
		if(!noAdapterBank(bankID)){
			ReturnValue rv = cp.getFirmBalanceError(Tool.strToDate(s_time),bankID);
			if(rv.remark==null || rv.remark.trim().length()<=0){
				result = "返回信息为空";
			}else{
				result = rv.remark;
			}
			
		}
		lv.setLogcontent("取银行和市场的签解约信息:"+result);
		dao.log(lv);
	}

int maxpage = 0;
if(BatFailResult!=null){
	maxpage = BatFailResult.size()%pageSize==0 ? BatFailResult.size()/pageSize : BatFailResult.size()/pageSize+1;
}
if(pageIndex>maxpage){
	pageIndex=maxpage;
}
	obj = ObjSet.getInstance(BatFailResult, pageSize, pageIndex);
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
    <title>交易商签约信息对应列表</title>
  </head>
  
  <body>
  	<form id="frm" method="post">
		<fieldset width="100%">
			<legend>比对交易商签约信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
				<td align="right">银行：&nbsp;</td>
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
					</tr>
				<tr height="35">
					<td align="right">比对日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltName="s_time" eltStyle="width:80px" eltImgPath="../skin/default/images/" eltValue="<%=s_time%>"/>
					</td>
				</tr>
				<tr height="35">
					<td align="right">比对结果：&nbsp;</td>
					<td align="left">
						<font color=red><%=result%></font>
					</td>
				</tr>
				<tr height="35">
					<td align="center" colspan=2>
						<input type="button" name="compareBtn1" class="mdlbtn" <%if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(1);" value="取银行信息">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="compareBtn2" class="mdlbtn" <%if(disableBtn){out.print("disabled='disabled'");} %> onclick="doCompare(2);" value="比对银行信息">
						<input type=hidden name=submitFlag>
					</td>
				</tr>
			</table>
		</fieldset>
		<%
		if(obj!=null && obj.size()!=0){
		%>
		<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25" align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB">交易商代码</td>
				<td class="panel_tHead_MB">当天总冻结金额</td>
				<td class="panel_tHead_MB">当天总解冻余额</td>
				<td class="panel_tHead_MB">卖方货款</td>
				<td class="panel_tHead_MB">买方货款</td>
				<td class="panel_tHead_MB">盈利金额</td>
				<td class="panel_tHead_MB">亏损金额</td>
				<td class="panel_tHead_MB">手续费</td>
				<td class="panel_tHead_MB">市场资金</td>
				<td class="panel_tHead_MB">市场冻结</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		
		<tBody>
  		<%
			for(int i=0;i<obj.getCurNum();i++)
			{
				BatFailResultChild bfr = (BatFailResultChild)obj.get(i);
				%>
				<tr height="22" align=center  onclick="selectTr();">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td class="underLine"><%=Tool.delNull(bfr.ThirdCustId)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.FreezeAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.UnfreezeAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.AddTranAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.CutTranAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.ProfitAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.LossAmount)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.TranHandFee)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.NewBalance)%>&nbsp;</td>
					<td class="underLine"><%=Tool.fmtDouble2(bfr.NewFreezeAmount)%>&nbsp;</td>
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
	<%}%>
	
				<input name=pageIndex type=hidden value="<%=pageIndex%>">
				<input name=opt type=hidden>
	</form>
  </body>
</html>
<script type="text/javascript">  
	function doCompare(v)
	{
		frm.pageIndex.value = 1;
		if(frm.bankID.value == -1)
		{
			alert("请选择银行！");
		}
		else if(frm.s_time.value == "")
		{
			alert("请选择对账日期！");
		}
		else
		{
			var now1 = new Date()+"";
			if(CompareDate((frm.s_time.value),now1))
			{
				if(v==2)
				{
					frm.submitFlag.value = "do";
					frm.submit();
				}
				else if(v==1)
				{
					frm.submitFlag.value = "getData";
					frm.submit();	
				}
			}
			else
			{
				alert("选择的对账日期不合法!");
				frm.s_time.value="";
			}
		}
	}
	
	function pgTurn(i)
	{
		frm.submitFlag.value = "getData";
		frm.opt.value="turnPage";
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
			<%if(obj!=null){ %> frm.pageIndex.value = <%=obj.getPageCount()%><%}%>;
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
				frm.submitFlag.value = "getData";
				frm.pageIndex.value = frm.pageJumpIdx.value;
				frm.submit();
			}
		}	
	}
	function doQuery()
	{
		var selectDay = frm.s_time.value;
		if(frm.bankID.value == -1)
		{
			alert("请选择银行！");
		}	
		else
		{
			window.location = "list.jsp?bankID="+frm.bankID.value;
		}
	}
	function CompareDate(d1,d2)
 	{
 	   return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
	}
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.trade.bank.dao.*"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<%
	int[] pageinfo = new int[4];
	BankDAO dao = BankDAOFactory.getDAO();
	int pageSize = 20;
	int size = 0;
	try{
		size = Tool.strToInt(request.getParameter("pageSize"));
	}catch(Exception e){}
	if(size>0){
		pageSize = size;
	}
	pageinfo[2] = pageSize;
	int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
	if(pageIndex <= 0)  pageIndex = 1;
	pageinfo[1]=pageIndex;
	String today = Tool.fmtDate(new java.util.Date());
	String qrDate = request.getParameter("qrDate");
	String srDate = request.getParameter("srDate");
	String submitFlag = Tool.delNull(request.getParameter("submitFlag"));
	if(submitFlag == null || submitFlag.trim().length()<=0){
		qrDate=today;
		srDate=today;
	}
	String FIRMID = (String)session.getAttribute("FIRMID");
	String filter=" and (fbc.type='"+ProcConstants.inMoneyType+"' or fbc.type='"+ProcConstants.outMoneyType+"') and fbc.FIRMID='"+FIRMID+"' ";
	if(qrDate != null && qrDate.trim().length()>0){
		//filter += " and trunc(createTime)>=to_date('"+qrDate+"','yyyy-MM-dd') ";
		filter += " and createTime>=to_date('"+qrDate+"','yyyy-MM-dd') ";
	}
	if(srDate != null && srDate.trim().length()>0){
		//filter += " and trunc(createTime)<=to_date('"+srDate+"','yyyy-MM-dd') ";
		filter += " and createTime<to_date('"+srDate+"','yyyy-MM-dd')+1 ";
	}
	Vector capitalMoney = dao.getCapitalInfoMoney(filter);
	filter += " order by fbc.id";
	Vector capitalList=dao.getCapitalInfoList2(filter,pageinfo);
	int inMoneyNum = 0;
	int outMoneyNum = 0;
	double inMoney = 0;
	double outMoney = 0;
	if(capitalList != null){
		for(int i=0;i<capitalList.size();i++){
			CapitalValue capitalValue=(CapitalValue)capitalList.get(i);
			if(capitalValue.status==0){
				if(capitalValue.type==0){
					inMoneyNum++;
					inMoney += capitalValue.money;
				}else if(capitalValue.type==1){
					outMoneyNum++;
					outMoney += capitalValue.money;
				}
			}
		}
	}
	int rownum = (pageinfo[1]-1)*pageinfo[2]+1;
%>
<script language ="javascript" src="global.js"></script>
<script language="javascript" type="text/javascript" src="public/jstools/My97DatePicker/WdatePicker.js"></script>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=gb2312">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	
	<link rel="stylesheet" href="css/button.css" type="text/css"/>
	<link rel="stylesheet" href="css/print.css" type="text/css"/>
	<link rel="stylesheet" href="css/report.css" type="text/css"/>
    <title>出 入 金</title>
	<script language="javascript" src="public/jstools/common.js"></script>
	
  </head>
  
  <body oncontextmenu="return false">
	<form id="frm_delete" name="frm" action="list.jsp?fid=<%=fid%>&sid=<%=sid%>" method="post" targetType="hidden">
		<input type="hidden" name="submitFlag" id="submitFlag">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" style="width: 100%;" height="25"  style="table-layout:fixed;">
	  		<tr>
	  			<td width="220px" align="right""> 
	  				查询日期:
	  				<input type="text" id="exTime1" name="qrDate" readonly="readonly" maxlength="10" value=""  class="Wdate"  onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
	  			</td>
	  			<td width="200px" align="left">
	  			&nbsp;至&nbsp;
	  				<input type="text" id="exTime1" name="srDate" readonly="readonly"  maxlength="10" value=""  class="Wdate"  onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
	  			</td>
	  			<td id=t2 width="48" height="17" class="top_b1" align="left"><div align="center"><a href="#" onclick="return logIn();" class="but_t">查 询</a></div></td>
	  			<td>&nbsp;</td>
	  		</tr>
	  </table>
	  <br>
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" style="width: 780px;">
  		<tHead>
  			<tr height="25">
				<td class="panel_tHead_LB" align="center">&nbsp;</td>
				<td class="panel_tHead_MB" align="center" width="5%">序号</td>
				<td class="panel_tHead_MB" align="center" width="8%">流水</td>
				<td class="panel_tHead_MB" align="center" width="22%">操作时间</td>
				<td class="panel_tHead_MB" align="center" width="12%">银行</td>
				<td class="panel_tHead_MB" align="center">出金</td>
				<td class="panel_tHead_MB" align="center">入金</td>
				<td class="panel_tHead_MB" align="center" width="8%">状态</td>
				<td class="panel_tHead_MB_last" align="center" width="25%">备注</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		 <tBody>
		 <%
			 for(int i=0;i<capitalList.size();i++) {
			  	CapitalValue capitalValue=(CapitalValue)capitalList.get(i);
				%>
				<tr >
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine"><%=rownum++%></td>
				<td class="underLine"><%=capitalValue.actionID%>&nbsp;</td>
				<td class="underLine" align="center"><%=capitalValue.createtime==null?"----":Tool.fmtTime(capitalValue.createtime)%></td>
				<td class="underLine" align="center"><%=Tool.delNull(capitalValue.bankName)%></td>
				<td class="underLine" align="right"><%=capitalValue.type==1 ? Tool.fmtMoney(capitalValue.money) : "-"%>&nbsp;</td>
				<td class="underLine" align="right"><%=capitalValue.type==0 ? Tool.fmtMoney(capitalValue.money) : "-"%>&nbsp;</td>
				<td class="underLine" align="center">
					<%if(capitalValue.status==ProcConstants.statusSuccess)
						out.print("<font color=blue>成功</font>");
					  else  if(capitalValue.status==ProcConstants.statusFailure)
						out.print("<font color=red>失败</font>");
					  else  if(capitalValue.status==ProcConstants.statusBlunt)
						out.print("<font color=red>被冲正</font>");
					  else 
						out.print("处理中");
					%>
				</td>
				<td class="underLine_last"><%=Tool.delNull(capitalValue.note)%>&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
				</tr>	
				<%
			}
			%>
			<tr height="30">
			<td class="panel_tBody_LB">&nbsp;</td>
			<td colspan="8" align="center"></td>
			<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%
			if(capitalMoney != null && capitalMoney.size()==6){
				CapitalMoneyVO cm11 = (CapitalMoneyVO) capitalMoney.get(0);
				CapitalMoneyVO cm12 = (CapitalMoneyVO) capitalMoney.get(1);
			%>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8" align="left">成功出金合计：<%=cm12.rowcount%>笔；共计<%=Tool.fmtMoney(cm12.money)%>元</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8" align="left">成功入金合计：<%=cm11.rowcount%>笔；共计<%=Tool.fmtMoney(cm11.money)%>元</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%}%>
		</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="8" align="center">
				第<%=pageinfo[1]%>/<%=pageinfo[3]%>页 &nbsp;&nbsp;共<%=pageinfo[0]%>条 &nbsp;&nbsp;每页
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">条 &nbsp;&nbsp;
				<%
				if(pageinfo[1] > 1)
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

				if(pageinfo[1] < pageinfo[3])
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
				到<input class="text" type="text" style="width:25px;" value="<%=pageinfo[1]%>" name="pageJumpIdx" onkeydown="return pgJumpChk()">页

				<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
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
	</form>
  </body>
</html>
<script>
	frm.qrDate.value = '<%=qrDate%>';
	frm.srDate.value = '<%=srDate%>';
</script>
<SCRIPT LANGUAGE="JavaScript">
function logIn(){
	var qrDate = frm.qrDate.value;
	var srDate = frm.srDate.value;
	var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
	if(qrDate != "" && !CompareDate(qrDate,now1)){
		alert("起始日期格式错误或起始日期超过今天");
		frm.qrDate.value="";
	} else if(srDate != "" && !CompareDate(srDate,now1)){
		alert("结束日期格式错误或结束日期超过今天");
		frm.srDate.value="";
	} else if(qrDate != "" && srDate != "" && !CompareDate(qrDate,srDate)){
		alert("输入的开始日期不能超过结束日期");
		frm.qrDate.value="";
		frm.srDate.value="";
	}else{
		frm.pageIndex.value = 1;
		frm.submitFlag.value="do";
		frm.submit();
	}
}
function pgTurn(i)
{
	frm.submitFlag.value="do";
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
		frm.pageIndex.value = <%=pageinfo[3]%>;
		frm.submit();
	}
	else if(i == -1)
	{
		frm.pageIndex.value = frm.pageIndex.value - 1;
		frm.submit();
	}
}
function pgJumpChk(){
	if(event.keyCode == 13){
		if((isNaN(frm.pageJumpIdx.value) || frm.pageJumpIdx.value < 1 || frm.pageJumpIdx.value > <%=pageinfo[3]%>) || (isNaN(frm.pageSize.value) || frm.pageSize.value < 1 )) {
			alert("请输入正确数字！");			
		} else {
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submitFlag.value="do";
			frm.submit();
		}
	}
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
</SCRIPT>
<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="../../globalDef.jsp"%>
<%@include file="../../session.jsp"%>
<%
	CITICBankDAO dao = BankDAOFactory.getCITICDAO();
	int[] pageinfo = new int[4];
	int pageSize = 15;
	int size = 0;
	try{
		size = Tool.strToInt(request.getParameter("pageSize"));
	}catch(Exception e){}
	if(size>0){
		pageSize = size;
	}
	pageinfo[2]=pageSize;
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
	String FIRMID = (String)session.getAttribute("REGISTERID");
	String filter=" and fbc.type='"+ProcConstants.outMoney2AccountType+"' and fbc.FIRMID='"+FIRMID+"' ";
	if(qrDate != null && qrDate.trim().length()>0){
		filter += " and createTime>=to_date('"+qrDate+"','yyyy-MM-dd') ";
	}
	if(srDate != null && srDate.trim().length()>0){
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
<script language="javascript" type="text/javascript" src="../../public/jstools/My97DatePicker/WdatePicker.js"></script>
<script language ="javascript" src="../../global.js"></script>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>提 现 查 询</title>
    <link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/report.css" type="text/css"/>
	<script language="javascript" src="../../lib/tools.js"></script>
	
  </head>
  
  <body>
  	<div id="main_body">
	<form id="frm_delete" name="frm" action="" method="post" targetType="hidden" style="width: 80%;">
		<input type="hidden" name="submitFlag" id="submitFlag">
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_tj">
					<table id="tableList" border="0" cellpadding="0" cellspacing="0" class="table2_style"  style="table-layout:fixed;">
						<tr>
							<td class="table2_td_widthmax"> 
								查询日期:
								<input type="text" id="exTime1" name="qrDate" readonly  maxlength="10" value=""  class="Wdate"  onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
								&nbsp;至&nbsp;
								<input type="text" id="exTime1" name="srDate" readonly  maxlength="10" value=""  class="Wdate"  onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
							<button class="btn_sec" onclick="doQuery();">查 询</button>&nbsp;
							<button class="btn_sec" onclick="goBack();">返回</button>
							</td>
						</tr>
					</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
						<tHead>
							<tr height="25">
								<td class="panel_tHead_LB">&nbsp;</td>
								<td class="panel_tHead_MB" align="center">序号</td>
								<td class="panel_tHead_MB" align="center" width="15%">交易所流水号</td>
								<td class="panel_tHead_MB" align="center">操作时间</td>
								<td class="panel_tHead_MB" align="center">银行</td>
								<td class="panel_tHead_MB" align="center">提现</td>
								<td class="panel_tHead_MB" align="center">状态</td>
								<td class="panel_tHead_MB_last" align="center" width="25%">备注</td>
								<td class="panel_tHead_RB">&nbsp;</td>
							</tr>
						</tHead>
						 <tBody>
								 <%
									 for(int i=0;i<capitalList.size();i++) {
										CapitalValue capitalValue=(CapitalValue)capitalList.get(i);
										%>
										<tr height="25" >
										<td class="panel_tBody_LB">&nbsp;</td>
										<td class="underLine" align="center"><%=rownum++%></td>
										<td class="underLine" align="center"><%=capitalValue.actionID%>&nbsp;</td>
										<td class="underLine" align="center"><%=capitalValue.createtime==null?"----":Tool.fmtTime(capitalValue.createtime)%>&nbsp;</td>
										<td class="underLine" align="center"><%=Tool.delNull(capitalValue.bankName)%></td>
										<td class="underLine" align="right"><%=capitalValue.type==3 ? Tool.fmtMoney(capitalValue.money) : "-"%>&nbsp;</td>
										<td class="underLine" align="center">
											<%if(capitalValue.status==ProcConstants.statusSuccess){
												out.print("<font color=blue>成功</font>");
											  }else  if(capitalValue.status==ProcConstants.statusFailure){
												out.print("<font color=red>失败</font>");
											  }else  if(capitalValue.status==ProcConstants.statusBlunt){
												out.print("<font color=red>被冲正</font>");
											  }else {
												if("79".equals(capitalValue.bankID)){
													out.print("<a href='confim.jsp?actionID="+capitalValue.actionID+"&bankID="+capitalValue.bankID+"&type="+capitalValue.type+"'>待处理</a>");
												}else{
													out.print("待处理");
												}
											  }
											%>&nbsp;
										</td>
										<td class="underLine_last" align="left"><%=Tool.delNull(capitalValue.note)%>&nbsp;</td>
										<td class="panel_tBody_RB">&nbsp;</td>
										</tr>	
										<%
									}
									%>
									</tBody>
						<tFoot>
							<tr height="100%">
								<td class="panel_tBody_LB">&nbsp;</td>
								<td colspan="7">&nbsp;</td>
								<td class="panel_tBody_RB">&nbsp;</td>
							</tr>
							<%
			if(capitalMoney != null && capitalMoney.size()==9){
				CapitalMoneyVO cm13 = (CapitalMoneyVO) capitalMoney.get(6);
			%>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="7" align="left">成功提现合计：<%=cm13.rowcount%>笔；共计<%=Tool.fmtMoney(cm13.money)%>元</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			
			<%}%>
							<tr height="22">
								<td class="panel_tBody_LB">&nbsp;</td>
								<td class="pager" colspan="7" align="right">
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
								到<input class="text" type="text" maxlength="4" value="<%=pageinfo[1]%>" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">页
								<input name=pageIndex type=hidden value="<%=pageinfo[1]%>">
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
				</td>
			</tr>
		</table>
	</form>
	</div>
  </body>
</html>
<script>
	frm.qrDate.value = '<%=qrDate%>';
	frm.srDate.value = '<%=srDate%>';
</script>
<SCRIPT LANGUAGE="JavaScript">
function window_onload(){
	initDocument();
	document.forms[0].exTime1.setAttribute("dropDown_fixed", 1);
	document.forms[0].exTime1.setAttribute("autoDropDown", 1);
}
function goBack(){
	window.location="../../Money.jsp";
}
function doQuery(){
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
			frm.submitFlag.value="do";
			frm.pageIndex.value = frm.pageJumpIdx.value;
			frm.submit();
		}
	}
}
function CompareDate(d1,d2) {
	return ((new Date(d1.replace(/-/g,"\/"))) <= (new Date(d2.replace(/-/g,"\/"))));   
}
</SCRIPT>
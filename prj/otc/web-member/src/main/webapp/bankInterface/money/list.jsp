<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="../globalDef.jsp"%>
<%@include file="../session.jsp"%>
<%
	BankDAO dao = BankDAOFactory.getDAO();
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
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>�� �� �� �� ѯ</title>
    <script language="javascript" src="public/jstools/tools.js"></script>
	<script language="javascript" src="public/jstools/common.js"></script>
	
  </head>
  
  <body>
  	<div id="main_body">
	<form id="frm_delete" name="frm" action="" method="post" targetType="hidden">
		<input type="hidden" name="submitFlag" id="submitFlag">
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_tj">
					<table id="tableList" border="0" cellpadding="0" cellspacing="0" class="table2_style"  style="table-layout:fixed;">
						<tr>
							<td class="table2_td_widthmax"> 
								��ѯ����:
								<input type="text" id="exTime1" name="qrDate" readonly  maxlength="10" value=""  class="Wdate"  onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
								&nbsp;��&nbsp;
								<input type="text" id="exTime1" name="srDate" readonly  maxlength="10" value=""  class="Wdate"  onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
							<button class="btn_sec" onclick="doQuery();">�� ѯ</button>
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
								<td class="panel_tHead_MB" align="center">���</td>
								<td class="panel_tHead_MB" align="center" width="10%">��������ˮ��</td>
								<td class="panel_tHead_MB" align="center">����ʱ��</td>
								<td class="panel_tHead_MB" align="center">����</td>
								<td class="panel_tHead_MB" align="center" >����</td>
								<td class="panel_tHead_MB" align="center" >���</td>
								<td class="panel_tHead_MB" align="center">״̬</td>
								<td class="panel_tHead_MB_last" align="center" width="25%">��ע</td>
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
										<td class="underLine" align="center"><%=replaceNull(capitalValue.bankName)%></td>
										<td class="underLine" align="right"><%=capitalValue.type==1 ? Tool.fmtMoney(capitalValue.money) : "-"%>&nbsp;</td>
										<td class="underLine" align="right"><%=capitalValue.type==0 ? Tool.fmtMoney(capitalValue.money) : "-"%>&nbsp;</td>
										<td class="underLine" align="center">
											<%if(capitalValue.status==ProcConstants.statusSuccess)
												out.print("<font color=blue>�ɹ�</font>");
											  else  if(capitalValue.status==ProcConstants.statusFailure)
												out.print("<font color=red>ʧ��</font>");
											  else  if(capitalValue.status==ProcConstants.statusBlunt)
												out.print("<font color=red>������</font>");
											  else 
												out.print("������");
											%>&nbsp;
										</td>
										<td class="underLine_last" align="left"><%=replaceNull(capitalValue.note)%>&nbsp;</td>
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
							<%
			if(capitalMoney != null && capitalMoney.size()==6){
				CapitalMoneyVO cm11 = (CapitalMoneyVO) capitalMoney.get(0);
				CapitalMoneyVO cm12 = (CapitalMoneyVO) capitalMoney.get(1);
			%>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8" align="left">�ɹ�����ϼƣ�<%=cm12.rowcount%>�ʣ�����<%=Tool.fmtMoney(cm12.money)%>Ԫ</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr>
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="8" align="left">�ɹ����ϼƣ�<%=cm11.rowcount%>�ʣ�����<%=Tool.fmtMoney(cm11.money)%>Ԫ</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<%}%>
							<tr height="22">
								<td class="panel_tBody_LB">&nbsp;</td>
								<td class="pager" colspan="8" align="right">
								��<%=pageinfo[1]%>/<%=pageinfo[3]%>ҳ &nbsp;&nbsp;��<%=pageinfo[0]%>�� &nbsp;&nbsp;ÿҳ
				<input name="pageSize" class="text" maxlength="4" type="text" style="width:25px;" value="<%=pageinfo[2]%>" onkeydown="return pgJumpChk()">�� &nbsp;&nbsp;
								<%
								if(pageinfo[1] > 1)
								{
									%>
									<span style="cursor:hand" onclick="pgTurn(0)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(-1)">��ҳ</span> &nbsp;&nbsp;	
									<%
								}
								else
								{
									%>
									��ҳ &nbsp;&nbsp;��ҳ &nbsp;&nbsp;	
									<%
								}

								if(pageinfo[1] < pageinfo[3])
								{
									%>
									<span style="cursor:hand" onclick="pgTurn(1)">��ҳ</span> &nbsp;&nbsp;<span style="cursor:hand" onclick="pgTurn(2)">βҳ</span> &nbsp;&nbsp;	
									<%
								}
								else
								{
									%>
									��ҳ &nbsp;&nbsp;βҳ &nbsp;&nbsp;	
									<%
								}

								%>
								��<input class="text" type="text" maxlength="4" value="<%=pageinfo[1]%>" style="width:25px;" name="pageJumpIdx" onkeydown="return pgJumpChk()">ҳ
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
function doQuery(){
	var qrDate = frm.qrDate.value;
	var srDate = frm.srDate.value;
	var now1 = '<%=Tool.fmtDate(new java.util.Date())%>';
	if(qrDate != "" && !CompareDate(qrDate,now1)){
		alert("��ʼ���ڸ�ʽ�������ʼ���ڳ�������");
		frm.qrDate.value="";
	} else if(srDate != "" && !CompareDate(srDate,now1)){
		alert("�������ڸ�ʽ�����������ڳ�������");
		frm.srDate.value="";
	} else if(qrDate != "" && srDate != "" && !CompareDate(qrDate,srDate)){
		alert("����Ŀ�ʼ���ڲ��ܳ�����������");
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
			alert("��������ȷ���֣�");			
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
<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.common.manage.service.*"%>
<%@ page import="gnnt.MEBS.common.manage.model.*"%>
<%@ page import="gnnt.MEBS.finance.base.util.SysData"%>
<html>
	<head>
		<title>
		审核与查看虚拟资金
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    setReadOnly(virtualFundForm.note);
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function ok_onclick(){
	virtualFundForm.status.value = "2";//1为通过
	virtualFundForm.submit();
  	virtualFundForm.query.disabled = true;  
	//window.close();
}

function fail_onclick(){
	virtualFundForm.status.value = "3";//2为不通过
	virtualFundForm.submit();
  	virtualFundForm.query.disabled = true;  
	//window.close();
	//document.location.href = "<c:url value="/apply/apply.do?method=applyWaitListCheckFail&applyID="/>" + virtualFundForm.applyID.value;
	
} 

</script>
</head>
	<body leftmargin="2" topmargin="0" onload="window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="200" width="380" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
											//ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
											ApplyManage mgr = (ApplyManage) SysData.getBean("applyManager");
											Apply_T_VirtualMoney apply=new Apply_T_VirtualMoney();
											String id = request.getParameter("id");
											long relID = 0;
											if (id != null && !"".equals(id)) {
												relID = Long.parseLong(id);
												apply.setId(relID);
											}
											apply = (Apply_T_VirtualMoney)mgr.getApplyById(apply, false);
											
											String firmId = "";
											String money = "";
											String applytype = "";
											String status = "";
											String proposer ="";
											String note = "";
											String approver = "";
											
											if (apply != null) {
												firmId = apply.getFirmId();
												money = apply.getMoney()+"";
												if ("1".equals(apply.getApplyType()+"")) {
													applytype = "虚拟资金";
												}else {
													applytype = "质押";
												}
												
												if ("1".equals(apply.getStatus()+"")) {
													status = "待审核";
												}else if ("2".equals(apply.getStatus()+"")) {
													status = "审核通过";
												}else if ("3".equals(apply.getStatus()+"")) {
													status = "审核不通过";
												}
												
												proposer = apply.getProposer();
												note = apply.getNote();
												approver = apply.getApprover();
												if(note==null)
												{
												  note="";
												}
											}
								%>
					<form id="virtualFundForm" action=""
						method="POST" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>审核与查看虚拟资金
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="5" border="5" class="common">
								<tr>
									<td width="80" align="right">交易商代码：</td>
									<td width="80">
										<%=firmId%>
									</td>
									<td width="80" align="right">虚拟资金：</td>
									<td width="80">
										<fmt:formatNumber value="<%=money%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">
									</td>
								</tr>
								<tr>
									<td width="80" align="right">当前状态：</td>
									<td width="80">
										<%=status%>
									</td>
									<td width="80" align="right">申请人：</td>
									<td width="80">
										<%=proposer%>
									</td>
								</tr>
								<%
									if (apply.getStatus() == 2 || apply.getStatus() == 3) {
								%>
									<tr>
									<td align="right">
										审核人：
									</td>
									<td>
										<%=approver%>
									</td>
									</tr>
								<%		
									}
								%>
								<tr>
									<td >备注：</td>
								</tr>
								<tr>
									
									<td colspan="4">
										<textarea name="note" rows="3" cols="95"  style="width:320" class="text" readOnly><%=note%></textarea>
									</td>
								</tr>
								<tr></tr>
									<tr>
									 
									<td colspan="4" align="center" >
										<input type="button" name="close" value="关闭" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
						
								
								
								
							</table>
						</fieldset>
						<input type="hidden" name="id" value="<%=id%>"/>
						<input type="hidden" name="status"/>
					</form>
					
					
					
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>

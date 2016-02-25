<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>

<%@ page import="java.util.*"%>

	<%
		String traderID = (String)request.getAttribute("traderID");
	%>

<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
			交易员权限
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
  
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		var boxes = traderForm.privilegeCode_B;
	   if (boxes[0].checked == false && boxes[1].checked == false && boxes[2].checked == false && boxes[3].checked == false && boxes[4].checked == false && boxes[5].checked == false ) {
	   		alert("请至少选择一个！");
	   		return false;
	   }
	   
	   traderForm.submit();	
	   traderForm.save.disabled = true;
	}
   
}
//cancel
function cancel_onclick()
{
	window.history.back(-1);
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="500" width="500" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/trader.do?funcflg=saveTraderPrivilege"
						method="POST" styleClass="form" target="mainFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>交易员<%=traderID%>权限维护
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<%
									List list = (List)request.getAttribute("traderPrivilegeList");
								%>					
								<tr>
									<td>
										<input type="checkbox" name="privilegeCode_B" value="301" style="border:0px;" <%if (list != null && list.size() > 0) {for (int i = 0; i < list.size(); i++) {Map map = (Map)list.get(i); if (map.get("privilegeCode_B") != null) {if ("301".equals(map.get("privilegeCode_B").toString())) {out.print("checked");}}}} %> />查看市场行情
										<input type="checkbox" name="privilegeCode_B" value="302" style="border:0px;" <%if (list != null && list.size() > 0) {for (int i = 0; i < list.size(); i++) {Map map = (Map)list.get(i); if (map.get("privilegeCode_B") != null) {if ("302".equals(map.get("privilegeCode_B").toString())) {out.print("checked");}}}} %> />查看成交结果
										<input type="checkbox" name="privilegeCode_B" value="303" style="border:0px;" <%if (list != null && list.size() > 0) {for (int i = 0; i < list.size(); i++) {Map map = (Map)list.get(i); if (map.get("privilegeCode_B") != null) {if ("303".equals(map.get("privilegeCode_B").toString())) {out.print("checked");}}}} %> />输入委托单
										
									</td>
									
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="privilegeCode_B" value="304" style="border:0px;" <%if (list != null && list.size() > 0) {for (int i = 0; i < list.size(); i++) {Map map = (Map)list.get(i); if (map.get("privilegeCode_B") != null) {if ("304".equals(map.get("privilegeCode_B").toString())) {out.print("checked");}}}} %> />查询委托单
										<input type="checkbox" name="privilegeCode_B" value="305" style="border:0px;" <%if (list != null && list.size() > 0) {for (int i = 0; i < list.size(); i++) {Map map = (Map)list.get(i); if (map.get("privilegeCode_B") != null) {if ("305".equals(map.get("privilegeCode_B").toString())) {out.print("checked");}}}} %> />查询会员公司
										<input type="checkbox" name="privilegeCode_B" value="306" style="border:0px;" <%if (list != null && list.size() > 0) {for (int i = 0; i < list.size(); i++) {Map map = (Map)list.get(i); if (map.get("privilegeCode_B") != null) {if ("306".equals(map.get("privilegeCode_B").toString())) {out.print("checked");}}}} %> />公司管理员
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											取  消
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						
						<html:hidden property="traderID" value="<%=traderID%>"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>

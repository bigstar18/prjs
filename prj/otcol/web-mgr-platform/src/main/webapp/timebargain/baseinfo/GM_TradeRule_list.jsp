<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/tradeRule.do?crud=create&funcflg=editGroup"/>";
}
// -->
</script>
</head>
<body>
<div id="content">
    <table width="600">
  <tr><td>
	<ec:table items="tradeRuleList" var="tradeRule" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeRule.do?funcflg=searchGroup"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="tradeRuleList.xls" 
			csvFileName="tradeRuleList.csv"
			showPrint="true" 
			listWidth="100%"
			rowsDisplayed="20"
	>
    <ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tradeRule.groupID},${tradeRule.commodityID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>						
            <ec:column property="_1" title="�޸�" width="60" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeRule.do?crud=update&funcflg=editGroup&groupID=<c:out value="${tradeRule.groupID}"/>&commodityID=<c:out value="${tradeRule.commodityID}"/>"><img title="�����޸���Ϣ" src="<c:url value="/timebargain/images/fly.gif"/>"/></a> 
            </ec:column>
            <ec:column property="groupName" title="������" width="80" style="text-align:center;">
              
            </ec:column>
            <ec:column property="name" title="��Ʒ����" width="80" style="text-align:center;"/>
            <ec:column property="marginAlgr" title="��֤���㷨" width="80" style="text-align:center;"/>
			<ec:column property="marginRate_B" title="��֤��" width="80" style="text-align:right;"/>
			<ec:column property="marginRate_S" title="����֤��" width="80" style="text-align:right;"/>
			<ec:column property="marginAssure_B" title="�򵣱���" width="80" style="text-align:right;"/>
			<ec:column property="marginAssure_S" title="��������" width="80" style="text-align:right;"/>
			<ec:column property="feeAlgr" title="�������㷨" width="80" style="text-align:center;"/>
			<ec:column property="feeRate_B" title="��������" width="80" style="text-align:right;"/>
			<ec:column property="feeRate_S" title="���������㷨" width="80" style="text-align:right;"/>			
		</ec:row>
   
	  <ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('<fmt:message key="customerGroupForm.delbutton"/>','<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=deleteGroup"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>	
	</ec:table>
	</td></tr>
</table>	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	
</div>
	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

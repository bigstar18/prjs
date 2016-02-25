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
    document.location.href = "<c:url value="/timebargain/baseinfo/tradeRule.do?crud=create&funcflg=editFirmFee"/>";
}
// -->
</script>
</head>
<body>
<div id="content">
    <table width="100%">
  <tr><td>
	<ec:table items="firmFeeList" var="tradeRule" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeRule.do?funcflg=searchFirmFee"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="tradeRuleList.xls" 
			csvFileName="tradeRuleList.csv"
			showPrint="true" 
			listWidth="100%"
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
    <ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tradeRule.firmID},${tradeRule.commodityID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>						
            <ec:column property="_1" title="�޸�" width="20%" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeRule.do?crud=update&funcflg=editFirmFee&firmID=<c:out value="${tradeRule.firmID}"/>&commodityID=<c:out value="${tradeRule.commodityID}"/>"><img title="�����޸���Ϣ" src="<c:url value="/timebargain/images/fly.gif"/>"/></a> 
            </ec:column>
            <ec:column property="firmID" title="�����̴���" width="20%" style="text-align:center;">
              
            </ec:column>
            <ec:column property="commodityID" title="��Ʒ����" width="20%" style="text-align:center;"/>
            <ec:column property="feeAlgr" title="�������㷨" width="20%" style="text-align:center;" editTemplate="ecs_t_feeAlgr" mappingItem="FEEALGR"/>
			<ec:column property="modifyTime" title="�޸�ʱ��" cell="date" width="20%" style="text-align:center;"/>
		</ec:row>
   
	  <ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('ɾ������������','<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=deleteFirmFee"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
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
	<textarea id="ecs_t_feeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="feeAlgr" >
			<ec:options items="FEEALGR" />
		</select>
	</textarea>	
</div>
	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
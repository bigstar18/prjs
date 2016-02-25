<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
	function add_onclick(){
		parent.location.href = "<c:url value="/timebargain/statquery/statQuery.do?crud=create&funcflg=editPledge"/>";
	}
	
	function update(id){
		parent.location.href = "<c:url value="/timebargain/statquery/statQuery.do?crud=update&funcflg=editPledge&id="/>" + id;
	}
</script>
</head>
<body leftmargin="2" topmargin="0">
<table width="100%">
  <tr><td>
	<ec:table items="pledgeList" var="pledge" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listPledge"	
			xlsFileName="pledgeList.xls" 
			csvFileName="pledgeList.csv"
			showPrint="true" 	
			rowsDisplayed="20"
			listWidth="100%"	
			minHeight="300"		  
	>
		<ec:row>	
            <ec:column property="billID" title="�ֵ���"  width="90" style="text-align:center;">
            	<a href="#" onclick="update('<c:out value="${pledge.id}"/>')"><c:out value="${pledge.billID}"/></a>
            </ec:column>
			<ec:column property="billFund" title="��Ѻ���"  width="90" style="text-align:right;"/>
			<ec:column property="firmID" title="�����̴���" width="90" style="text-align:center;"/>
			<ec:column property="commodityName" title="Ʒ������" width="90"  style="text-align:center;"/>	
			<ec:column property="quantity" title="��Ѻ����"  width="90"  style="text-align:right;"/>
			<ec:column property="createTime" title="��������" cell="date" format="date" width="130" style="text-align:center;"/>
			<ec:column property="modifier" title="����޸���"  width="130" style="text-align:center;"/>
		</ec:row>
		
		<ec:extend>
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
			<ec:options items="OPERATION" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--


<%
  if(request.getAttribute("pledgeList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.statQueryForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
// -->
</script>
</body>
</html>

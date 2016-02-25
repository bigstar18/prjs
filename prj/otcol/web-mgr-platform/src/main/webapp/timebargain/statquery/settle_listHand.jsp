<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.StatQueryManager"%>
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%>

<%
	StatQueryManager mgr = (StatQueryManager)SysData.getBean("statQueryManager");
	boolean cnt = true;
%>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	function isConfirm(commodityID){
		if (confirm("��ȷ��Ҫ�ύ��")) {
			parent.HiddFrame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=listHandCheck&commodityID="/>" + commodityID;
			//return true;
		}else {
			return false;
		}
	}
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="list" var="settleHand" 
			action="${pageContext.request.contextPath}/timebargain/statquery/statQuery.do?funcflg=listHand"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="name" title="��Ʒ����" width="20%" style="text-align:center;"/>	
  			<ec:column property="commodityID" title="��Ʒ����" width="20%" style="text-align:center;"/> 
  			<ec:column property="marketDate" title="��������" cell="date" format="date" width="20%" style="text-align:center;"/>
			<ec:column property="settleDate" title="��������" cell="date" format="date" width="20%" style="text-align:center;"/>			
  			<ec:column property="_1" title="����" width="20%" tipTitle="�ֹ�����" style="text-align:center;"> 
  				<a href="#" onclick="return isConfirm('<c:out value="${settleHand.commodityID}"/>')"><img src="<c:url value="/timebargain/images/053753220.gif"/>"></a>
  			</ec:column>
  		
  		</ec:row>
  			
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
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--

// -->
</script>

  </body>
</html>

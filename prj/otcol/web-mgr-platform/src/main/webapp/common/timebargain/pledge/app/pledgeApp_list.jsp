<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*"%>
<html>
<head>
<%@ include file="../../common/ecside_head.jsp" %>

<script type="text/javascript">
	function add_pledge(){
		parent.location.href = "<%=basePath%>/timebargain/pledgeAppEdit.spr";
	}
	
	function check_pledge(id){
		pTop("<%=basePath%>/timebargain/pledge/app/pledgeChe_U_App.jsp?id=" + id,500,500);
	}
</script>

<title>
default
</title>
<%
response.setHeader("Charset","GBK");
 %>

</head>
<body >
			

<table width="100%">
  <tr><td>
	<ec:table items="pledgeList" var="pledge" 
			action="${basePath}/timebargain/pledgeAppList.spr"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="virtualFund.xls" 
			csvFileName="virtualFund.csv"
			showPrint="true" 
            listWidth="100%"		  
			rowsDisplayed="20"
			minHeight="300"
			title=""
	>
		<ec:row>
      <ec:column property="billID" title="�ֵ���" width="80"  style="text-align:center;"/>
      <ec:column property="billFund" title="��Ѻ���" width="120" cell="currency" format="###,###,##0.##" style="text-align:right;"/>
      <ec:column property="firmId" title="�����̴���" width="90" style="text-align:center;">
      </ec:column>
			<ec:column property="commodityName" title="Ʒ������" width="90"  style="text-align:center;"/>
			<ec:column property="quantity" title="��Ѻ����" width="120" cell="number" format="###,###,##0.####" style="text-align:right;"/>
			<ec:column property="status" title="��ǰ״̬" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="90" style="text-align:center;"/>
			<ec:column property="type" title="�������" editTemplate="ecs_t_type" mappingItem="OPERATECHECK" width="90" style="text-align:center;"/>
			<ec:column property="proposer" title="������" width="90" style="text-align:center;"/>
			<ec:column property="applytime" title="����ʱ��" cell="date" width="90" style="text-align:center;"/>
			<ec:column property="_1" title="�鿴"  width="90" style="text-align:center;">
			<c:choose>
					<c:when test="${pledge.status == 1}">
						<a href="#" onclick="check_pledge('<c:out value="${pledge.id}"/>')"><img height="20" title="�鿴" src="<%=basePath%>/timebargain/images/weishen.gif"/></a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="check_pledge('<c:out value="${pledge.id}"/>')"><img height="20" title="�鿴" src="<%=basePath%>/timebargain/images/yishen.gif"/></a>
					</c:otherwise>
			</c:choose>
			</ec:column>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_pledge()">�ύ����</a>			
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
	<textarea id="ecs_t_type" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="type" >
			<ec:options items="OPERATECHECK" />
		</select>
	</textarea>	
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="VIRTUALFUNDSTATUS" />
		</select>
	</textarea>	

<%@ include file="../../common/messages.jsp" %>
<SCRIPT language="javascript">
<!--
<%
  if(request.getAttribute("pledgeList") != null)
  {
%>
    parent.TopFrame.pledgeForm.query.disabled = false;
<%
  }
%>

// -->
</SCRIPT>

</body>

</html>

<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*"%>
<html>
<head>
<%@ include file="../../common/ecside_head.jsp" %>

<script type="text/javascript">
	function add_virtualFund(){
		parent.location.href = "<%=basePath%>/timebargain/virtualFundEdit.spr";
	}
	
	function check_virtualFund(id){
		pTop("<%=basePath%>/timebargain/virtualfund/app/virtualFundChe_U_App.jsp?id=" + id,400,400);
	}
</script>

<title>

</title>
<%
response.setHeader("Charset","GBK");

 %>

</head>
<body >
			

<table width="100%">
  <tr><td>
	<ec:table items="virtualFundList" var="virtualFund" 
			action="${basePath}/timebargain/virtualFundList.spr"	
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
            
      <ec:column property="firmId" title="�����̴���" width="15%" style="text-align:center;">
      </ec:column>
			<ec:column property="money" title="�����ʽ�" width="15%" cell="currency" style="text-align:right;"/>
			<ec:column property="status" title="��ǰ״̬" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="15%" style="text-align:center;"/>
			<ec:column property="proposer" title="������" width="20%" style="text-align:center;"/>
			<ec:column property="applytime" title="����ʱ��" cell="date" width="20%" style="text-align:center;"/>
			<ec:column property="_1" title="�����鿴"  width="15%" style="text-align:center;">
				<c:choose>
					<c:when test="${virtualFund.status == 1}">
						<a href="#" onclick="check_virtualFund('<c:out value="${virtualFund.id}"/>')"><img height="20" title="�����鿴" src="<%=basePath%>/timebargain/images/weishen.gif"/></a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="check_virtualFund('<c:out value="${virtualFund.id}"/>')"><img height="20" title="�����鿴" src="<%=basePath%>/timebargain/images/yishen.gif"/></a>
					</c:otherwise>
				</c:choose>
				
			</ec:column>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_virtualFund()">�ύ����</a>			
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
	<textarea id="ecs_t_applytype" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="applytype" >
			<ec:options items="VIRTUALFUNDAPPLYTYPE" />
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
  if(request.getAttribute("virtualFundList") != null)
  {
%>
    parent.TopFrame.form.query.disabled = false;
<%
  }
%>

// -->
</SCRIPT>

</body>

</html>

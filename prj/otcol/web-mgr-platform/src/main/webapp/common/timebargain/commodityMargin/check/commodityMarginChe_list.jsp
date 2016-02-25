<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="../../common/ecside_head.jsp" %>

<script type="text/javascript">
	
	function check_commodityMargin(id){
		pTop("<%=basePath%>/timebargain/commodityMargin/check/commodityMarginChe_U.jsp?id=" + id,700,650);
	}
	
	function search(){
		document.location.href = "<%=basePath%>/timebargain/commodityMargin/check/commodityMarginChe.jsp";
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
	<ec:table items="commodityMarginList" var="commodityMargin" 
			action="${basePath}/timebargain/commodityMarginCheList.spr"	
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
      <ec:column property="commodityID" title="��Ʒ����" width="80"  style="text-align:center;"/>
      <ec:column property="marginAlgr" title="��֤���㷨" editTemplate="ecs_t_feeAlgr" mappingItem="FEEALGR" width="120" style="text-align:right;"/>
      <ec:column property="marginPriceType" title="��֤���������" editTemplate="ecs_t_marginPriceType" mappingItem="MARGINPRICETYPE" width="120" style="text-align:center;"/>
			<ec:column property="payoutAlgr" title="���ջ����㷨" editTemplate="ecs_t_settleFeeAlgr" mappingItem="SETTLEFEEALGR" width="140"  style="text-align:center;"/>
			<ec:column property="status" title="��ǰ״̬" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="90" style="text-align:center;"/>
			<ec:column property="proposer" title="������" width="90" style="text-align:center;"/>
			<ec:column property="applytime" title="����ʱ��" cell="date" width="90" style="text-align:center;"/>
			<ec:column property="_1" title="�����鿴"  width="90" style="text-align:center;">
			<c:choose>
					<c:when test="${commodityMargin.status == 1}">
						<a href="#" onclick="check_commodityMargin('<c:out value="${commodityMargin.id}"/>')"><img height="20" title="�����鿴" src="<%=basePath%>/timebargain/images/weishen.gif"/></a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="check_commodityMargin('<c:out value="${commodityMargin.id}"/>')"><img height="20" title="�����鿴" src="<%=basePath%>/timebargain/images/yishen.gif"/></a>
					</c:otherwise>
			</c:choose>
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
	<textarea id="ecs_t_feeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="feeAlgr" >
			<ec:options items="FEEALGR" />
		</select>
	</textarea>	
	<textarea id="ecs_t_settleFeeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="settleFeeAlgr" >
			<ec:options items="SETTLEFEEALGR" />
		</select>
	</textarea>	
	<textarea id="ecs_t_marginPriceType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="marginPriceType" >
			<ec:options items="MARGINPRICETYPE" />
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
  if(request.getAttribute("commodityMarginList") != null)
  {
%>
    parent.TopFrame.commodityMarginForm.query.disabled = false;
<%
  }
%>

// -->
</SCRIPT>

</body>

</html>

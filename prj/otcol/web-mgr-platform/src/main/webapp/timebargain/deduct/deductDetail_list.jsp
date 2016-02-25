<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
<SCRIPT language="javascript">
	function next(){
		
		parent.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=deductGo&date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>' + "&id=" + '<%=(String)request.getAttribute("id")%>';
	}
	
	function goFrontTep(){
		parent.location.href = "<c:url value="/timebargain/deduct/deductKeepFirm.jsp?date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>' + "&id=" + '<%=(String)request.getAttribute("id")%>';
		
	}
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="deductDetailList" var="deduct" 
			action="${pageContext.request.contextPath}/timebargain/deduct/deduct.do?funcflg=nextDeductDetail"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
  		>
  		<ec:row>
  			<ec:column property="customerID" title="���׿ͻ�ID" width="100" style="text-align:center;"/> 
  			
  			<ec:column property="buyQty" title="�򶩻���" format="quantity" calcTitle= "�ϼ�" calc="total" width="100"  style="text-align:right;"/> 
  			<ec:column property="sellQty" title="��������" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/> 
  			<ec:column property="buyKeepQty" title="������" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			
  			<ec:column property="sellKeepQty" title="��������" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="relPureHoldQty" title="��������" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="pL" title="ӯ���ϼ�" width="100" style="text-align:right;"/> 
  			<ec:column property="pL_ratio" title="ӯ����" width="100" style="text-align:right;"/> 
  			<ec:column property="counteractQty" title="�Գ���" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="orderQty" title="ί����" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="deductableQty" title="��ǿ����" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="estimateQty" title="����ǿ����" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="deductQty" title="Ӧǿ����" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="deductedQty" title="��ǿ����" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  			<ec:column property="counteractedQty" title="�ѶԳ���" format="quantity" calcTitle= "�ϼ�" calc="total" width="100" style="text-align:right;"/>
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
  <tr></tr><tr></tr><tr></tr><tr></tr>

  <tr>
  	<td align="left">
  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="next" styleClass="button" onclick="return next()">ִ��ǿ��</html:button>
		<html:button property="next" styleClass="button" onclick="return goFrontTep()">������һ��</html:button>
  	</td>
  </tr>
</table>

	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="Qty" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="bS_Flag" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>		
	<textarea id="ecs_t_WL" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="wL" >
			<ec:options items="WL" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--



// -->
</script>

  </body>
</html>

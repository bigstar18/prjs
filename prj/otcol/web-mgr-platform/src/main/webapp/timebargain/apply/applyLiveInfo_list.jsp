<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	
	function giveUp(billID,billType){

		var billID = billID;
		var billType = billType;
		parent.location.href = "<c:url value="/timebargain/apply/apply.do?funcflg=giveUp&billID="/>" + billID + "&billType=" + billType;
	}
	
	function before(billID,billType){

		var billID = billID;
		var billType = billType;
		parent.location.href = "<c:url value="/timebargain/apply/apply.do?funcflg=before&billID="/>" + billID + "&billType=" + billType;
	}
	
	function giveUpDelaySettle(billID,billType){
		var billID = billID;
		var billType = billType;
		parent.location.href = "<c:url value="/timebargain/apply/apply.do?funcflg=giveUp&billID="/>" + billID + "&billType=" + billType;
	}
	
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="applyLiveInfoList" var="app" 
			action="${pageContext.request.contextPath}/timebargain/apply/apply.do?funcflg=applyLiveInfoList"	
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
  			<ec:column property="billID" title="�ֵ���" width="60" style="text-align:center;"/>
  			<ec:column property="firmID_S" title="�������̴���" width="80" style="text-align:center;"/> 
  			<ec:column property="commodityID" title="��Ʒ����" width="80" style="text-align:center;"/> 
  			
  			<ec:column property="quantity" title="�ֵ�����" width="80" format="quantity" calcTitle= "�ϼ�" calc="total" style="text-align:right;"/> 
  			<ec:column property="billType" title="ҵ������" width="60" editTemplate="ecs_t_BILLTYPE" mappingItem="BILLTYPE" style="text-align:center;"/> 
  			<ec:column property="status" title="��ǰ״̬"  width="60" editTemplate="ecs_t_status" mappingItem="ALIVEPRESENTSTATUS" style="text-align:center;"/>
  			<ec:column property="createTime" title="����ʱ��" cell="date" format="date" width="100"  style="text-align:center;"/> 
  			<ec:column property="_1" title="����" width="60" style="text-align:center;">
  				<c:choose>
  					<c:when test="${app.billType == 1}">
  						<a href="#" onclick="giveUp('<c:out value="${app.billID}"/>','<c:out value="${app.billType}"/>')"><img title="�����ֶ�" src="<c:url value="/timebargain/images/chex.gif"/>"/></a>
  					</c:when>
  					<c:when test="${app.billType == 5}">
  						<a href="#" onclick="giveUpDelaySettle('<c:out value="${app.billID}"/>','<c:out value="${app.billType}"/>')"><img title="�������ڽ���" src="<c:url value="/timebargain/images/chex.gif"/>"/></a>
  					</c:when>
  					<c:otherwise></c:otherwise>
  				</c:choose>
  			</ec:column>
  			<ec:column property="_1" title="��ǰ����" width="60" style="text-align:center;">
  				<c:choose>
  					<c:when test="${app.billType == 1}">
  						<a href="#" onclick="before('<c:out value="${app.billID}"/>','<c:out value="${app.billType}"/>')"><img title="��ǰ����" src="<c:url value="/timebargain/images/tq.gif"/>"/></a>
  					</c:when>
  					<c:when test="${app.billType == 5}">
  						<img title="���ڽ��ղ���ת��ǰ����" src="<c:url value="/timebargain/images/tq.gif"/>"/>
  					</c:when>
  					<c:otherwise></c:otherwise>
  				</c:choose>
  			</ec:column>
  			
  		</ec:row>
  		<ec:extend >
  			
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>

	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="Qty" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ALIVEPRESENTSTATUS" />
		</select>
	</textarea>		
	<textarea id="ecs_t_BILLTYPE" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="billType" >
			<ec:options items="BILLTYPE" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("applyLiveInfoList") != null)
  {
%>
	if (parent.TopFrame) {
		parent.TopFrame.applyForm.query.disabled = false;
    	parent.TopFrame.wait.style.visibility = "hidden";
	}
<%
  }
%>
//for (var i=0;i<ec_table.rows.length;i++)                
 // {
    //alert(i);
 //   ec_table.rows(i).cells(9).style.display = "none";	
    //ec_table.rows(i).cells(10).style.display = "none";	
 // }


// -->
</script>

  </body>
</html>

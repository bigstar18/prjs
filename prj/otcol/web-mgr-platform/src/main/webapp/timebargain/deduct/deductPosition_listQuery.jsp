<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	function add_onclick(){
		parent.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=editUpdate"/>";
	}
	
	function customer(id){
		//alert(id);
		pTop("<c:url value="/timebargain/deduct/deduct.do?funcflg=deductKeepFirmListQuery&deductID="/>" + id+"&d="+Date(),450,300);
	}
	function operate(id){
		parent.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=editDeductGuide&deductID="/>" + id+"&d="+Date();
	}
	
	function info(id){
	//alert(id);
		pTop("<c:url value="/timebargain/deduct/deduct.do?funcflg=deductPositionInfo&deductID="/>" + id+"&d="+Date(),650,600);
	}
	
	function detail(id){
		window.showModalDialog("<c:url value="/timebargain/deduct/deduct.do?funcflg=nextDeductDetailQueryMenu&deductID="/>" + id+"&d="+Date(),"", "dialogWidth=900px; dialogHeight=700px; status=no;scroll=yes;help=no;resizable=yes");
		//pTop("<c:url value="/timebargain/deduct/deduct.do?funcflg=nextDeductDetailQueryMenu&deductID="/>" + id,700,500);
	}
</SCRIPT>
  </head>
			
  <body>
   <table width="100%">
  <tr><td>
  		<ec:table items="deductPositionListQuery" var="deduct" 
			action="${pageContext.request.contextPath}/timebargain/deduct/deduct.do?funcflg=deductPositionListQuery"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="deduct.xls" 
			csvFileName="deduct.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="deductDate" title="ǿ������" cell="date" format="date" width="15%" style="text-align:center;"/>	
  			<ec:column property="commodityID" title="��Ʒ����" width="10%" style="text-align:center;"/> 
  			<ec:column property="deductStatus" title="ǿ��״̬" editTemplate="ecs_t_DeductStatus" mappingItem="DEDUCTSTATUS" width="10%" style="text-align:center;"/> 
  			<ec:column property="loserMode" title="����ǿ��ģʽ" editTemplate="ecs_t_status" mappingItem="LOSERMODE" width="15%" style="text-align:right;"/> 
  			<ec:column property="lossRate" title="�������" width="10%" style="text-align:right;"/> 
  			
  			<ec:column property="_1" title="��ϸ��Ϣ" width="10%"  style="text-align:center;"> 
  				<a href="#" onclick="info('<c:out value="${deduct.deductID}"/>')"><img title="�鿴" src="<c:url value="/timebargain/images/info.gif"/>"/></a>
  			</ec:column>
  			<ec:column property="_2" title="�ͻ�����" width="10%" editTemplate="ecs_t_status2" mappingItem="BS_FLAG"  style="text-align:center;"> 
  				<a href="#" onclick="customer('<c:out value="${deduct.deductID}"/>')"><img title="�鿴" src="<c:url value="/timebargain/images/customer.gif"/>"/></a>
  			</ec:column>
  			<ec:column property="_3" title="ǿ����ϸ"  width="10%"   editTemplate="ecs_t_status" mappingItem="LOSERMODE"   style="text-align:center;">
  				<c:choose>
					<c:when test="${deduct.deductStatus eq 'Y'}">
						<a href="#" onclick="detail('<c:out value="${deduct.deductID}"/>')"><img title="�鿴" src="<c:url value="/timebargain/images/detail.gif"/>"/></a>
						
					</c:when>
					<c:when test="${deduct.deductStatus eq 'P'}">
						<a href="#" onclick="detail('<c:out value="${deduct.deductID}"/>')"><img title="�鿴" src="<c:url value="/timebargain/images/detail.gif"/>"/></a>
						
					</c:when>
					<c:otherwise>
						<img title="δ����" src="<c:url value="/timebargain/images/oldDetail.gif"/>"/>
					</c:otherwise>
				</c:choose>
  				
  			</ec:column>
  			<ec:column property="deductID" title="ǿ������" width="10%"  style="text-align:center;"> 
  				<c:choose>
					<c:when test="${deduct.deductStatus eq 'Y'}">
						<img title="������" src="<c:url value="/timebargain/images/right.gif"/>"/>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="operate('<c:out value="${deduct.deductID}"/>')"><img title="����" src="<c:url value="/timebargain/images/point.gif"/>"/></a>
					</c:otherwise>
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
			<ec:options items="LOSERMODE" />
		</select>
	</textarea>		
	<textarea id="ecs_t_DeductStatus" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="deductStatus" >
			<ec:options items="DEDUCTSTATUS" />
		</select>
	</textarea>	
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="loserBSFlag" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--



// -->
</script>

  </body>
</html>

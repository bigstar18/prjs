<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<SCRIPT language="javascript">
	function add_onclick(){
		top.MainFrame.location.href = "<c:url value="/timebargain/deduct/deduct.do?funcflg=editUpdate"/>";
	}
	
</SCRIPT>
  </head>
			
  <body>
   <table width="650">
  <tr><td>
  		<ec:table items="deductPositionList" var="deduct" 
			action="${pageContext.request.contextPath}/timebargain/deduct/deduct.do?/timebargain=deductPositionList"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
  		>
  		<ec:row>
  			<ec:column property="deductDate" title="强减日期" cell="date" format="date" width="130" style="text-align:center;">	
  				<a href="<c:out value="${ctx}"/>/timebargain/deduct/deduct.do?funcflg=editUpdate&date=<c:out value="${deduct.deductDate}"/>&code=<c:out value="${deduct.uni_Cmdty_Code}"/>"><c:out value="${deduct.deductDate}"/></a>
  			</ec:column>
  			<ec:column property="uni_Cmdty_Code" title="商品统一代码" width="100" style="text-align:center;"/> 
  			<ec:column property="deductStatus" title="强减状态" width="100" style="text-align:center;"/> 
  			
  			
  			<ec:column property="deductPrice" title="强减价格" width="100" format="quantity" calcTitle= "ec.calcTitle" calc="total" style="text-align:right;"/> 
  			<ec:column property="loserBSFlag" title="亏损方买卖标志" width="120" editTemplate="ecs_t_status2" mappingItem="BS_FLAG"  style="text-align:center;"/> 
  			<ec:column property="loserMode" title="亏损方强减模式"  width="120"   editTemplate="ecs_t_status" mappingItem="PRESENTSTATUS"   style="text-align:center;"/>
  			<ec:column property="lossRate" title="亏损比例"  width="100"  style="text-align:center;"/> 
  			<ec:column property="selfCounteract" title="自我对冲"  width="100"  style="text-align:center;"/> 
  		</ec:row>
  		<ec:extend >
  			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
  		</ec:extend>
  		</ec:table>
  </td></tr>
</table>

	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="Qty" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="PRESENTSTATUS" />
		</select>
	</textarea>		
	<textarea id="ecs_t_APPLYTYPE" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="applyType" >
			<ec:options items="APPLYTYPE" />
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

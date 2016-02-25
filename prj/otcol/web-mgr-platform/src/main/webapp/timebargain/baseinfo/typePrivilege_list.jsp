<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

	
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	
</SCRIPT>
  </head>
			
  <body>
  	
   <table width="100%">
  <tr><td>
  		<ec:table items="typePrivilegeList" var="typePrivilege" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=typePrivilegeList"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="typePrivilege.xls" 
			csvFileName="typePrivilege.csv"
			showPrint="true" 
			listWidth="100%"
			title=""	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  			<ec:column property="typeID" title="交易码" width="130" style="text-align:center;"/>
  			<ec:column property="type" title="交易码类型" editTemplate="ecs_t_status_B" mappingItem="TYPE" width="100" style="text-align:center;"/> 
  			<ec:column property="kind" title="品种/商品" editTemplate="ecs_t_kind" mappingItem="KIND" width="100" style="text-align:center;"/>
  			<ec:column property="kindID" title="品种/商品代码" width="130" style="text-align:center;"/>
  			<ec:column property="privilegeCode_B" title="买方权限" editTemplate="ecs_t_status_B" mappingItem="FIRMPRIVILEGE_B" width="100" style="text-align:center;"/> 
  			<ec:column property="privilegeCode_S" title="卖方权限" editTemplate="ecs_t_status_S" mappingItem="FIRMPRIVILEGE_S" width="100" style="text-align:right;"/> 
  			
  			
  		</ec:row>
  		<ec:extend >
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
	<textarea id="ecs_t_status_B" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="privilegeCode_B" >
			<ec:options items="FIRMPRIVILEGE_B" />
		</select>
	</textarea>		
	<textarea id="ecs_t_status_S" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="privilegeCode_S" >
			<ec:options items="FIRMPRIVILEGE_S" />
		</select>
	</textarea>		
	<textarea id="ecs_t_status_type" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="type" >
			<ec:options items="TYPE" />
		</select>
	</textarea>	
	<textarea id="ecs_t_kind" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="kind" >
			<ec:options items="KIND" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--



// -->
</script>

  </body>
</html>

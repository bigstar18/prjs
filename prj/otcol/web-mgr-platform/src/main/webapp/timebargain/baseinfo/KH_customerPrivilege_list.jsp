<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

	<%
  		String customerID = (String)request.getAttribute("customerID");
  		request.setAttribute("customerID",customerID);
  		String firmID = (String)request.getAttribute("firmID");
  		
  	%>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	function updatePrivilege(id){
		pTop("<c:url value="/timebargain/baseinfo/KH_customerPrivilege_U.jsp?id="/>" + id,600,400);
		//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=update&funcflg=updateCustomerPrivilege&id="/>" + id;
	}
	
	function add_onclick(){
		//alert(document.getElementById("firmID").value);
		//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=create&funcflg=updateCustomerPrivilege&customerID="/>" + document.getElementById("customerID").value;
		pTop("<c:url value="/timebargain/baseinfo/KH_customerPrivilege_A.jsp?customerID="/>" + document.getElementById("customerID").value,600,400);
	}
	
	function deleteFirmPrivilege(){
		var ecForm = document.forms.ec;
    	ecForm.target = "mainFrame";
    	batch_do('删除此交易客户权限','<c:url value="/timebargain/baseinfo/customer.do?funcflg=deleteCustomerPrivilege&customerID="/>' + document.getElementById("customerID").value);    
    	ecForm.target = "mainFrame";
	}
	
	function searchKHPri(){
		document.location.reload();
	}
	
	function gobackToCustomer(){
		document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=searchKH&funcflg=searchKH&firmID="/>" + document.getElementById("firmID").value;
	}
</SCRIPT>
  </head>
			
  <body>
  	
  	<input type="hidden" id="customerID" name="customerID" value="<%=customerID%>"/>
  	<input type="hidden" id="firmID" name="customerID" value="<%=firmID%>"/>
   <table width="100%">
  <tr><td>
  		<ec:table items="customerPrivilegeList" var="customerPrivilege" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/customer.do?funcflg=customerPrivilege"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="customerPrivilege.xls" 
			csvFileName="customerPrivilege.csv"
			showPrint="true" 
			listWidth="100%"
			title="<%="二级代码" + customerID + "交易权限维护"%>"	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  		<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${customerPrivilege.id}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>								
  			<ec:column property="kindID" title="上市品种/商品" width="30%" style="text-align:center;">
  				<a href="#" onclick="updatePrivilege('<c:out value="${customerPrivilege.id}"/>')"><c:out value="${customerPrivilege.kindID}"/></a>
  			</ec:column>	
  			<ec:column property="privilegeCode_B" title="买方权限" editTemplate="ecs_t_status_B" mappingItem="FIRMPRIVILEGE_B" width="35%" style="text-align:center;"/> 
  			<ec:column property="privilegeCode_S" title="卖方权限" editTemplate="ecs_t_status_S" mappingItem="FIRMPRIVILEGE_S" width="35%" style="text-align:center;"/> 
  			
  			
  		</ec:row>
  		<ec:extend >
  			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="deleteFirmPrivilege()"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
  			<a href="#" onclick="gobackToCustomer()">返回客户列表</a>
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
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--



// -->
</script>

  </body>
</html>

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

	<%
  		String firmID = (String)request.getAttribute("firmID");
  		request.setAttribute("firmID",firmID);
  	%>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	function updatePrivilege(id){
		//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=update&funcflg=updateFirmPrivilege&id="/>" + id;
		//pTop("<c:url value="/timebargain/baseinfo/firm.do?crud=update&funcflg=updateFirmPrivilege&id="/>" + id,500,500);
		var r = pTop("<c:url value="/timebargain/baseinfo/customerPrivilege_U.jsp?id="/>" + id,500,500);
		if(!r){
			document.location.href = "${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=firmPrivilege&firmID="+document.getElementById("firmID").value;
		}
	}
	
	function add_onclick(){
		//alert(document.getElementById("firmID").value);
		//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=create&funcflg=updateFirmPrivilege&firmID="/>" + document.getElementById("firmID").value;
		//pTop("<c:url value="/timebargain/baseinfo/firm.do?crud=create&funcflg=updateFirmPrivilege&firmID="/>" + document.getElementById("firmID").value,500,500);
		var r = pTop("<c:url value="/timebargain/baseinfo/customerPrivilege_A.jsp?firmID="/>" + document.getElementById("firmID").value,500,500);
		if(!r){
			document.location.href = "${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=firmPrivilege&firmID="+document.getElementById("firmID").value;
		}
	}
	
	function deleteFirmPrivilege(){
		var ecForm = document.forms.ec;
    	ecForm.target = "mainFrame";
    	batch_do('删除此交易商权限','<c:url value="/timebargain/baseinfo/firm.do?funcflg=deleteFirmPrivilege&firmID="/>' + document.getElementById("firmID").value);    
    	ecForm.target = "mainFrame";
	}
	
	function searchFirmPri(){
		//alert('list');
		document.location.reload();
	}
	
function gobackToFirm(){
	document.location.href = "<c:url value="/timebargain/menu/Firm.do"/>";
}
</SCRIPT>
  </head>
			
  <body>
  	
  	<input type="hidden" id="firmID" name="firmID" value="<%=firmID%>"/>
   <table width="100%">
  <tr><td>
  		<ec:table items="privilegeList" var="firmPrivilege" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=firmPrivilege"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="virtualFund.xls" 
			csvFileName="virtualFund.csv"
			showPrint="true" 
			listWidth="100%"
			title="<%="交易商" + firmID + "交易权限维护"%>"	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  		<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${firmPrivilege.id}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>								
  			<ec:column property="kindID" title="上市品种/商品" width="30%" style="text-align:center;">
  				<a href="#" onclick="updatePrivilege('<c:out value="${firmPrivilege.id}"/>')"><c:out value="${firmPrivilege.kindID}"/></a>
  			</ec:column>	
  			<ec:column property="privilegeCode_B" title="买方权限" editTemplate="ecs_t_status_B" mappingItem="FIRMPRIVILEGE_B" width="35%" style="text-align:center;"/> 
  			<ec:column property="privilegeCode_S" title="卖方权限" editTemplate="ecs_t_status_S" mappingItem="FIRMPRIVILEGE_S" width="35%" style="text-align:center;"/> 
  			
  			
  		</ec:row>
  		<ec:extend >
  			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="deleteFirmPrivilege()"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
  			<a href="#" onclick="gobackToFirm()">返回交易商列表</a>
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

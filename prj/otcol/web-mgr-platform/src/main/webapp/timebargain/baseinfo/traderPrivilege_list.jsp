<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

	<%
  		String traderID = (String)request.getAttribute("traderID");
  		request.setAttribute("traderID",traderID);
  		String firmID = (String)request.getAttribute("firmID");
  	%>
<html>
  <head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<SCRIPT language="javascript">
	function updatePrivilege(id){
		//alert(id);
		pTop("<c:url value="/timebargain/baseinfo/traderPrivilege_U.jsp?id="/>"+id,600,400);
		//alert(id);
		//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/trader.do?crud=update&funcflg=updateTraderPrivilege&id="/>" + id;
	}
	
	function add_onclick(){
		//alert(document.getElementById("firmID").value);
		//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/trader.do?crud=create&funcflg=updateTraderPrivilege&traderID="/>" + document.getElementById("traderID").value;
		pTop("<c:url value="/timebargain/baseinfo/traderPrivilege_A.jsp?traderID="/>" + document.getElementById("traderID").value,600,400);
	}
	
	function deleteTraderPrivilege(){
		var ecForm = document.forms.ec;
    	ecForm.target = "mainFrame";
    	batch_do('删除此交易员权限','<c:url value="/timebargain/baseinfo/trader.do?funcflg=deleteTraderPrivilege&traderID="/>' + document.getElementById("traderID").value);    
    	ecForm.target = "mainFrame";
	}
	
	function searchTradePri(){
		document.location.reload();
	}
	
	function gobackToTrader(){
		document.location.href = "<c:url value="/timebargain/baseinfo/trader.do?funcflg=search&firmID="/>" + document.getElementById("firmID").value;
	}
</SCRIPT>
  </head>
			
  <body>
  	
  	<input type="hidden" id="traderID" name="traderID" value="<%=traderID%>"/>
  	<input type="hidden" id="firmID" name="firmID" value="<%=firmID%>"/>
   <table width="100%">
  <tr><td>
  		<ec:table items="traderPrivilegeList" var="traderPrivilege" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/trader.do?funcflg=tradePrivilege"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="virtualFund.xls" 
			csvFileName="virtualFund.csv"
			showPrint="true" 
			listWidth="100%"
			title="<%="交易员" + traderID + "交易权限维护"%>"	
			rowsDisplayed="20"
			minHeight="300"
  		>
  		<ec:row>
  		<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${traderPrivilege.id}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>								
  			<ec:column property="breedName" title="上市品种" width="30%" style="text-align:center;">
  				<a href="#" onclick="updatePrivilege('<c:out value="${traderPrivilege.id}"/>')"><c:out value="${traderPrivilege.breedName}"/></a>
  			</ec:column>	
  			<ec:column property="privilegeCode_B" title="买方权限" editTemplate="ecs_t_status_B" mappingItem="FIRMPRIVILEGE_B" width="35%" style="text-align:center;"/> 
  			<ec:column property="privilegeCode_S" title="卖方权限" editTemplate="ecs_t_status_S" mappingItem="FIRMPRIVILEGE_S" width="35%" style="text-align:center;"/> 
  			
  			
  		</ec:row>
  		<ec:extend >
  			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="deleteTraderPrivilege()"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
  			<a href="#" onclick="gobackToTrader()">返回交易员列表</a>
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

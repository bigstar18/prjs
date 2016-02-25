<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>交易商列表</title>
    <import namespace="MEBS" implementation="<%=memberPath%>/public/calendar.htc">
    <%@ include file="/timebargain/common/ecside_head.jsp"%>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		
    <script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
		
	<script language="JavaScript">
	    function init(){
			  changeOrder(sign);  
		}
		function createNew(){ 
			var returnValue = openDialog("<%=basePath%>/firmController.mem?funcflg=firmAddForward","_blank",700,650);
			if(returnValue)
			    frm_query.submit(); 
		}
		function doQuery(){
		
			var startDate = document.getElementById("beginDate").value;
			var endDate =  document.getElementById("endDate").value;
			
		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			if(startDate == ""){
				alert("开始日期不能为空！");
				frm_query.beginDate.focus();
				return false;
				
			}
			if(endDate == ""){
				alert("结束日期不能为空！");
				frm_query.endDate.focus();
				return false;
				
			}
			if(!isDateFomat(startDate))
		    {
		        alert("开始日期格式不正确！\n如：" + '<%=nowDate%>');
		        frm_query.beginDate.value = "";
		        frm_query.endDate.focus();
		        return false;
		    }
		    if(!isDateFomat(endDate))
		    {
		        alert("结束日期格式不正确！\n如：" + '<%=nowDate%>');
		        frm_query.beginDate.value = "";
		        frm_query.endDate.focus();
		        return false;
		    }
		  
		    if ( startDate > '<%=nowDate%>' ) { 
		        alert("开始日期不能大于当天日期!"); 
		        frm_query.beginDate.focus();
		        return false; 
		    } 
		    if ( startDate > endDate ) { 
		        alert("开始日期不能大于结束日期!"); 
		        return false; 
		    } 
		  }
		  frm_query.submit();	
		}
		function resetForm(){
			frm_query.firmId.value = "";
			frm_query.name.value = "";
			frm_query.type.value = "";
			frm_query.contactMan.value = "";
			frm_query.tariffID.value="";
			frm_query.beginDate.value="";
			frm_query.endDate.value="";
			frm_query.brokerId.value="";
			frm_query.bankAccount.value="";
			frm_query.firmCategoryId.value="";
			frm_query.phone.value="";
			frm_query.legalRepresentative.value="";
			frm_query.LRphoneNo.value="";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/firmController.mem?funcflg=firmView&firmId="+vCode+"&date="+Date() ,"_blank",700,700);
			if(returnValue)
			    frm_query.submit();
		}
		function setStatus(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}
			if(frm_delete.status.value=="")
			{
			    alert("未设置状态");
			    return;
			}
			if(confirm("您确实要处理选中数据吗？"))
			{
				frm_delete.submit();
			}
		}
		function deleteFirm(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}
			if(confirm("您确实要处理选中数据吗？"))
			{
			    frm_delete.action="<%=basePath%>/firmController.mem?funcflg=firmDelete";
				frm_delete.submit();
			}
		}
		function outputExcel(){
			var action = frm_query.action;
			frm_query.action="<%=basePath%>/firmController.mem?funcflg=firmList&excel=1";
			frm_query.submit();
			frm_query.action = action;
		}
		function repairInfo(vCode){
			window.location="<%=basePath%>/firmController.mem?funcflg=traderList&firmId=" +vCode;
		}
	</script>
  </head>
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/firmController.mem?funcflg=firmList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		
		<fieldset width="95%">
			<legend>交易商查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=FIRMID%>：&nbsp;</td>
					<td align="left">
						<input id="firmId" name="_m.firmId[like]" value="<c:out value='${oldParams["m.firmId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right"><%=FIRMNAME%>：&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16"">
					</td>
					<td align="right">加盟商号：&nbsp;</td>
					<td align="left">
						<input id="brokerId" name="brokerId" type=text class="text" style="width: 100px" maxlength="19">
						<script>
							document.getElementById("brokerId").value="${brokerId}";
						</script>
					</td>
					
					<td align="right">创建日期：</td>
					<td align="left">														
								<MEBS:calendar eltName="_m.createTime[>=][date]" eltCSS="date" eltID="beginDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" 
								eltValue="<c:out value='${beginDate}'/>"/>
				&nbsp;至														
						      <MEBS:calendar eltName="_m.createTime[<=][date]" eltCSS="date" eltID="endDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" 
								eltValue="<c:out value='${endDate}'/>"/>
					</td>
					
				</tr>
				<tr height="35">
<%--					<td align="right">联系电话：&nbsp;</td>--%>
<%--					<td align="left">--%>
<%--						<input id="phone" name="_phone[like]" value="<c:out value='${oldParams["phone[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="numberPass()" maxlength="16">--%>
<%--					</td>--%>
					<td align="right">联系人：&nbsp;</td>
					<td align="left">
						<input id="contactMan" name="_contactMan[like]" value="<c:out value='${oldParams["contactMan[like]"]}'/>" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">银行卡号：&nbsp;</td>
					<td align="left">
						<input id="bankAccount" name="_m.bankAccount[like]" value="<c:out value='${oldParams["m.bankAccount[like]"]}'/>" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="19">
					</td>
					
					<td align="right">手续费套餐：&nbsp;</td>
					<td align="left">
						<select id="tariffID" name="_m.tariffID[=]" class="normal" style="width: 80px">
							<OPTION value="">全部</OPTION>
							<c:forEach items="${tariffList}" var="result">
							<option value="${result.tariffID}" <c:if test="${result.tariffID==oldParams['m.tariffID[=]']}">selected</c:if>>${result.tariffName }</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">类型：&nbsp;</td>
					<td align="left">
						<select id="type" name="_type[=]" class="normal" style="width: 60px">
							<c:choose>
							<c:when test="${oldParams['type[=]']==1}">
								<OPTION value="">全部</OPTION>
								<option value="1" selected="selected">法人</option>
								<option value="2" >代理</option>
								<option value="3" >个人</option>
							</c:when>
							<c:when test="${oldParams['type[=]']==2}">
								<OPTION value="">全部</OPTION>
								<option value="1" >法人</option>
								<option value="2" selected="selected">代理</option>
								<option value="3" >个人</option>
							</c:when>
							<c:when test="${oldParams['type[=]']==3}">
								<OPTION value="">全部</OPTION>
								<option value="1" >法人</option>
								<option value="2" >代理</option>
								<option value="3" selected="selected">个人</option>
							</c:when>
							<c:otherwise>
								<OPTION value="">全部</OPTION>
								<option value="1" >法人</option>
								<option value="2" >代理</option>
								<option value="3" >个人</option>
							</c:otherwise>
							</c:choose>
						</select>
					
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类别 ：
		                  <select id="firmCategoryId" name="_firmCategoryId[like]" >
		                  <option value="" >全部</option>
						   <c:forEach items="${users}" var="user">
						   		<option value="${user.id}" <c:if test="${user.id==oldParams['firmCategoryId[like]'] && not empty oldParams['firmCategoryId[like]']}">selected</c:if>>${user.name }</option>
							</c:forEach>
			    		  </select>
		                </td>
				</tr>
				<tr height="35">
					<td align="right">联系电话：&nbsp;</td>
					<td align="left">
						<input id="phone" name="_phone[like]" value="<c:out value='${oldParams["phone[like]"]}'/>" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">法人：&nbsp;</td>
					<td align="left">
						<input id="legalRepresentative" name="_legalRepresentative[like]" value="" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<script>
							document.getElementById("legalRepresentative").value="${legalRepresentative}";
						</script>
					<td align="right">法人电话：&nbsp;</td>
					<td align="left">
						<input id="LRphoneNo" name="_LRphoneNo[like]" value="" type=text class="text" style="width: 100px"onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<script>
							document.getElementById("LRphoneNo").value="${LRphoneNo}";
						</script>
					<td></td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/firmController.mem?funcflg=setStatusFirm" method="post" targetType="hidden" callback="doQuery();">
	 	 <%@ include file="listFirmTable.jsp" %>
	 
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
   		
	    <tr height="35">
	    	<td>
	    	  <!--select name="status">
	              <option value="">请选择</option>
	              <option value="N">正常</option>
	              <option value="D">禁止</option>
	              <option value="E">退市</option>
              </select-->&nbsp;&nbsp;
              <!--button class="mdlbtn" type="button" onclick="setStatus(frm_delete,tableList,'delCheck')">设置状态</button-->&nbsp;&nbsp;
	    	</td>
            <td><div align="right">
              <!--button class="mdlbtn" type="button" onclick="createNew()">添加用户</button>&nbsp;&nbsp;
  			  <button class="mdlbtn" type="button" onclick="deleteFirm(frm_delete,tableList,'delCheck')">删除用户</button>&nbsp;&nbsp;-->
              <button class="mdlbtn" type="button" onclick="outputExcel()">导出</button>
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
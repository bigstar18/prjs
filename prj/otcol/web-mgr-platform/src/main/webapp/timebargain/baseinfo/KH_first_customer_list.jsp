<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<%
response.setHeader("Charset","GBK");
 %>
<script type="text/javascript">
<!--

parent.CustomerMain.rows = "88,*,0";

//add
function add_onclick()
{
	//var firmID = customerForm.firmID.value;
    parent.CustomerMain.rows = "0,*,0";
    document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=create&funcflg=edit"/>";
}
function searchCustomer(groupID)
{ 
  pTop("<c:url value="/timebargain/baseinfo/customer.do?funcflg=searchGroupCustomer&mkName=customer&groupID="/>" + groupID,620,480);  
}
//chgGroup
function chgGroup()
{
    var ecForm = document.forms.ec;
    ecForm.target = "HiddFrame";
	if(ecForm.chgGroupID.value == "")
	{
	      alert("请选择要调至的组");
	      ecForm.chgGroupID.focus();
	      return false;		
	}
    batch_do('调整客户','<c:url value="/timebargain/baseinfo/customer.do?funcflg=chgGroup&groupID="/>' + ecForm.chgGroupID.value);
    ecForm.target = "ListFrame";
}
//deleteCustomer
function deleteCustomer()
{
    var ecForm = document.forms.ec;
    ecForm.target = "HiddFrame";
    batch_do('<fmt:message key="customerForm.delbutton"/>','<c:url value="/timebargain/baseinfo/customer.do?funcflg=delete"/>');    
    ecForm.target = "ListFrame";
}
// -->
</script>
</head>
<body >
	<%
		//List list = (List)request.getAttribute("customerCounts");
		//System.out.println(list.size());
	%>
<table width="740">
  <tr><td>
	<ec:table items="firstcustomerList" var="customer" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/customer.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="customer.xls" 
			csvFileName="customer.csv"
			showPrint="true" 
            listWidth="100%"		  
			rowsDisplayed="20"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${customer.firmID}" viewsAllowed="html" style="text-align:center;padding-left:9px;width:30;"/>
           <ec:column property="firmID" title="交易商ID" width="100" style="text-align:center;">   
            </ec:column>
            <ec:column property="firmName" title="交易商名称" width="100" style="text-align:center;"/>   
            <ec:column property="customerCounts" title="客户数量" width="130">
            	<a href="<c:out value="${ctx}"/>/timebargain/baseinfo/customer.do?firmID=<c:out value="${customer.firmID}"/>" onclick="javascript:parent.CustomerMain.rows = '0,*,0';"><c:out value="${customer.customerCounts}"/></a> 
            </ec:column>
            <ec:column property="null" title="增加客户" width="130">
				<a href="<c:out value="${ctx}"/>/timebargain/baseinfo/customer.do?crud=create&funcflg=edit&firmID=<c:out value="${customer.firmID}"/>">增加客户</a>
			</ec:column>
          
		</ec:row>
		<ec:extend>
		<!--	<a href="#" onclick="add_onclick()"><img src="<c:url value="/images/girdadd.gif"/>"></a> -->
		<!--	<a href="#" onclick="deleteCustomer()"><img src="<c:url value="/images/girddel.gif"/>"></a> -->
			&nbsp;&nbsp;调组：<select name="groupID" id="chgGroupID" style="width:80;valign:top;">
							  <c:forEach items="${customerGroupSelect}" var="customerGroup">
	                              <option value='<c:out value="${customerGroup.value}"/>'><c:out value="${customerGroup.label}"/></option>
	                          </c:forEach>
	                        </select>
			<a href="#" onclick="chgGroup()"><img src="<c:url value="/timebargain/images/046.GIF"/>"></a>
		</ec:extend>		
	</ec:table>
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	

<%@ include file="/timebargain/common/messages.jsp" %>

</body>

</html>

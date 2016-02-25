<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<title>
default
</title>
<%
response.setHeader("Charset","GBK");
 %>
<script type="text/javascript">

//add
function add_onclick()
{
	
	var firmID = document.forms(0).firmID.value;
	//alert(firmID);
    //parent.CustomerMain.rows = "0,*,0";
    document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=create&funcflg=edit&firmID="/>" + firmID;
}

function update_onclick(rowid){
	var firmID = document.forms(0).firmID.value;
	var customerID = rowid;
	document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=update&funcflg=edit&firmID="/>" + firmID + "&customerID=" + customerID;
}

function searchCustomer(groupID)
{ 
  pTop("<c:url value="/timebargain/baseinfo/customer.do?funcflg=searchGroupCustomer&mkName=customer&groupID="/>" + groupID,620,480);  
}
//chgGroup

//deleteCustomer
function deleteCustomer()
{
    var ecForm = document.forms.ec;
    ecForm.target = "mainFrame";
    batch_do('删除交易客户','<c:url value="/timebargain/baseinfo/customer.do?funcflg=delete"/>');    
    //ecForm.target = "mainFrame";
}

function correct(){
	var firmID = document.forms(0).firmID.value;
	document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=correct&funcflg=updateStatusKH&firmID"/>" + firmID;
}

function incorrect(){
	var firmID = document.forms(0).firmID.value;
	document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=incorrect&funcflg=updateStatusKH&firmID"/>" + firmID;
}

function batch_do1(entityName, action)
{
	var firmID = document.forms(0).firmID.value;
	//document.location.href = ;
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = "<c:url value="/timebargain/baseinfo/customer.do?crud=correct&funcflg=updateStatusKH&firmID"/>" + firmID + '&autoInc=false';
        form.submit();
    }
}
function batch_do2(entityName, action)
{
	var firmID = document.forms(0).firmID.value;
	//document.location.href = 
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = "<c:url value="/timebargain/baseinfo/customer.do?crud=incorrect&funcflg=updateStatusKH&firmID"/>" + firmID + '&autoInc=false';
        form.submit();
    }
}

function privilege(customerID){
	var firmID = document.forms(0).firmID.value;
	document.location.href = "<c:url value="/timebargain/baseinfo/customer.do?funcflg=customerPrivilege&customerID="/>" + customerID + "&firmID=" + firmID;
}


function status2(){
	var status = document.getElementById("status").value;
	if (status == "1") {
		batch_do1('修改交易客户状态','');
	}
	if (status == "2") {
	batch_do2('修改交易客户状态','');
	}
}

function gobackToFirm(){
	document.location.href = "<c:url value="/timebargain/menu/Firm.do"/>";
}
</script>
</head>
<body >
<table width="100%">


	<%
		String firmID = "";
		List list = (List)request.getAttribute("customerList");
		if (list != null && list.size() > 0) {
			Map map = (Map)list.get(0);
			if (map.get("firmID") != null) {
				firmID = map.get("firmID").toString();
			}
		}
		
		
	%>

	<tr>
		<td>
			
			<div align="center"><B>交易商代码: <%=firmID%></B></div>
		</td>
		<input type="hidden" id="firmID" name="firmID" value="<%=firmID%>"/>
	</tr>
  <tr>
  <td>
	<ec:table items="customerList" var="customer" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=searchKH"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="customer.xls" 
			csvFileName="customer.csv"
			showPrint="true" 
            listWidth="100%"		  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${customer.customerID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>								
            <ec:column property="customerID" title="二级代码" width="25%" style="text-align:center;">
              <a href="#" onclick="update_onclick('<c:out value="${customer.customerID}"/>')" ><c:out value="${customer.customerID}"/></a> 
            </ec:column>
            
            		
			<ec:column property="status" title="状态" width="25%" editTemplate="ecs_t_status" mappingItem="CUSTOMER_STATUS" style="text-align:center;"/>							
			
            <ec:column property="CreateTime" title="创建时间" width="25%" cell="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" style="text-align:center;"/>
			
			<ec:column property="_1" title="二级代码权限" width="25%" style="text-align:center;" tipTitle="查看权限">			
			  <a href="#" onclick="privilege('<c:out value="${customer.customerID}"/>')"><img src="<c:url value="/timebargain/images/wheel.gif"/>"/></a>
            </ec:column>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a> 
			<a href="#" onclick="deleteCustomer()"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
			<a href="#" onclick="gobackToFirm()">返回交易商列表</a>
		</ec:extend>		
	</ec:table>
</td></tr>

<tr></tr><tr></tr>
<tr>
	<td>
		<select id="status">
			<option value="1">正常</option>
			<option value="2">禁止交易</option>
		</select>
		<input type="button" class="button" value="修改状态"  onclick="status2()">
	</td>
</tr>
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

<script type="text/javascript">
<!--

	
// -->
</script>
</body>

</html>

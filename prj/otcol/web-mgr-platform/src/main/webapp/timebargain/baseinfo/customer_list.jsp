<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*"%>
<html:html>
<head>
<base target="_self" />
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
   
    top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=create&funcflg=edit"/>";
}
function searchCustomer(groupID)
{ 
  pTop("<c:url value="/timebargain/baseinfo/firm.do?funcflg=searchGroupCustomer&mkName=customer&groupID="/>" + groupID,620,480);  
}

//deleteCustomer
function deleteCustomer()
{
    var ecForm = document.forms.ec;
    ecForm.target = "HiddFrame";
    batch_do('<fmt:message key="customerForm.delbutton"/>','<c:url value="/timebargain/baseinfo/firm.do?funcflg=delete"/>');    
    ecForm.target = "ListFrame";
}

function submitKH(id){
	var firmID = id;
	parent.parent.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=searchKH&funcflg=searchKH&firmID="/>" + firmID;
}

function submitSeller(id){
	var firmID = id;
	parent.parent.location.href = "<c:url value="/timebargain/baseinfo/trader.do?funcflg=search&firmID="/>" + firmID;
}

function privilege(firmID){
	parent.parent.location.href = "<c:url value="/timebargain/baseinfo/firm.do?funcflg=firmPrivilege&firmID="/>" + firmID;
}

function status2(){
	//alert(document.getElementById("status").value);
	var status = document.getElementById("status").value;
	//alert(status);
	if (status == "1") {
		batch_do('�޸Ľ�����״̬','<c:url value="/timebargain/baseinfo/firm.do?crud=correct&funcflg=updateStatusF"/>');
	}
	if (status == "2") {
		batch_do('�޸Ľ�����״̬','<c:url value="/timebargain/baseinfo/firm.do?crud=incorrect&funcflg=updateStatusF"/>');
	}
	if (status == "3") {
		batch_do('�޸Ľ�����״̬','<c:url value="/timebargain/baseinfo/firm.do?crud=out&funcflg=updateStatusF"/>');
	}
}
function batchSet(){
	//alert(document.getElementById("firmID").value);
	//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=create&funcflg=updateCustomerPrivilege&customerID="/>" + document.getElementById("customerID").value;
	pTop("<c:url value="/timebargain/baseinfo/batchSetPower.jsp"/>",700,500);
}
function batchEmpty(){
	//alert(document.getElementById("firmID").value);
	//top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/customer.do?crud=create&funcflg=updateCustomerPrivilege&customerID="/>" + document.getElementById("customerID").value;
	pTop("<c:url value="/timebargain/baseinfo/batchEmptyPower.jsp"/>",700,500);
}
</script>
</head>
<body >
			

<table width="100%">
  <tr><td>
	<ec:table items="customerList" var="customer" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/firm.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="customer.xls" 
			csvFileName="customer.csv"
			showPrint="true" 
            		  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${customer.firmID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>								
            
            <ec:column property="firmID" title="�����̴���" width="80" style="text-align:center;">
            </ec:column>
            <ec:column property="firmName" title="����������" width="120" style="text-align:center;"/>			
			<ec:column property="status" title="״̬" width="80" editTemplate="ecs_t_status" mappingItem="CUSTOMER_STATUS" style="text-align:center;"/>
			
			<ec:column property="customerCounts" title="�����ͻ�����" width="100" style="text-align:center;">
              <c:choose>
              <c:when test="${customer.customerCounts > 0}">
              <a href="#" onclick="submitKH('<c:out value="${customer.firmID}"/>')"><c:out value="${customer.customerCounts}"/>&nbsp;&nbsp;<img title="�޸Ķ����ͻ�" src="<c:url value="/timebargain/images/customer.gif"/>"></a> 
              </c:when>
              <c:otherwise>
              <c:out value="${0}"/>
		    &nbsp;<img title="�޸Ľ��׿ͻ�" src="<c:url value="/timebargain/images/customer.gif"/>">
              </c:otherwise>
              </c:choose>
            </ec:column>	
            
			<ec:column property="tcounts" title="����Ա����" width="100" style="text-align:center;">
              <c:choose>
		  <c:when test="${customer.tcounts > 0}">
		  <a href="#" onclick="submitSeller('<c:out value="${customer.firmID}"/>')">
		    <c:out value="${customer.tcounts}"/>&nbsp;&nbsp;<img title="�޸Ľ���Ա" src="<c:url value="/timebargain/images/customer.gif"/>"></a> 
		  </c:when>
		  <c:otherwise>
		    <c:out value="${0}"/>
		    &nbsp;<img title="�޸Ľ���Ա" src="<c:url value="/timebargain/images/customer.gif"/>">
		  </c:otherwise>
		</c:choose>  
            </ec:column>	
            				
            <ec:column property="CreateTime" title="����ʱ��" width="100" cell="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" style="text-align:center;"/>
			<ec:column property="_1" title="������Ȩ��" width="80" style="text-align:center;" tipTitle="�鿴Ȩ��">			
			  <a href="#" onclick="privilege('<c:out value="${customer.firmID}"/>')"><img src="<c:url value="/timebargain/images/wheel.gif"/>"/></a>
            </ec:column>
		
		</ec:row>
		<ec:extend>
			
			
		</ec:extend>		
	</ec:table>
</td></tr>
<tr></tr><tr></tr>
<tr>
	<td>
		<select id="status">
			<option value="1">����</option>
			<option value="2">��ֹ����</option>
		</select>
		<input type="button" class="button" value="�޸�״̬"  onclick="status2()">
		<input type="button" class="button" value="��������"  onclick="batchSet()">
	</td>
	<td>
	
	</td>
</tr>


	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	

<%@ include file="/timebargain/common/messages.jsp" %>
<SCRIPT language="javascript">


</SCRIPT>

</body>

</html:html>

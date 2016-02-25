<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
  <head>
    <META HTTP-EQUIV="expires" CONTENT="0">
    <%@ include file="../public/headInc.jsp" %>
    <title>����Ա�б�</title>
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
			var returnValue = openDialog("<%=basePath%>/firmController.mem?funcflg=traderAddForward&firmId=${oldParams["firmId[like]"]}","_blank",600,400);
			if(returnValue)
			    frm_query.submit();
		}
		function doQuery(){
			var startDate = document.getElementById("beginDate").value;
			var endDate =  document.getElementById("endDate").value;
			
		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			if(startDate == ""){
				alert("��ʼ���ڲ���Ϊ�գ�");
				frm_query.beginDate.focus();
				return false;
				
			}
			if(endDate == ""){
				alert("�������ڲ���Ϊ�գ�");
				frm_query.endDate.focus();
				return false;
				
			}
			if(!isDateFomat(startDate))
		    {
		        alert("��ʼ���ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
		        frm_query.beginDate.value = "";
		        frm_query.endDate.focus();
		        return false;
		    }
		    if(!isDateFomat(endDate))
		    {
		        alert("�������ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
		        frm_query.beginDate.value = "";
		        frm_query.endDate.focus();
		        return false;
		    }
		  
		    if ( startDate > '<%=nowDate%>' ) { 
		        alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
		        frm_query.beginDate.focus();
		        return false; 
		    } 
		    if ( startDate > endDate ) { 
		        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
		        return false; 
		    } 
		  }
			frm_query.submit();
		}
		function resetForm(){
			frm_query.traderId.value = "";
			frm_query.name.value = "";
			frm_query.firmId.value = "";
			frm_query.enableKey.value = "";
			frm_query.beginDate.value="";
			frm_query.endDate.value="";
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/firmController.mem?funcflg=traderView&traderId="+vCode ,"_blank",600,400);
			  if(returnValue)
			    frm_query.submit();
		}
		function setStatus(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "û�п��Բ��������ݣ�" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "��ѡ����Ҫ���������ݣ�" );
				return;
			}
			if(frm_delete.status.value=="")
			{
			    alert("δ����״̬");
			    return;
			}
			if(confirm("��ȷʵҪ����ѡ��������"))
			{
				frm_delete.submit();
			}
		}
		
		function deleteTrader(frm_delete,tableList,checkName)
		{
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "û�п��Բ��������ݣ�" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "��ѡ����Ҫ���������ݣ�" );
				return;
			}
			if(confirm("��ȷʵҪ����ѡ��������"))
			{
			    frm_delete.action="<%=basePath%>/firmController.mem?funcflg=traderDelete";
				frm_delete.submit();
			}
		}
		function outputExcel(){
			var action = frm_query.action;
			frm_query.action="<%=basePath%>/firmController.mem?funcflg=traderList&excel=1";
			frm_query.submit();	
			frm_query.action = action;
		}
		function repairInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/trader/changePwd.jsp?traderId="+vCode ,"_blank",500,350);
			if(returnValue){
				frm_query.submit();
			}
		}
		function cancel_onclick()
		{
		   document.location.href = "<c:url value="/member/firmController.mem?funcflg=firmList"/>";
		   
		}
	</script>
  </head>
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/firmController.mem?funcflg=traderList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<c:if test="${firmId != null}">
			<input type="hidden" name="firmId" value="${firmId}">
		</c:if>
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo" />
		<fieldset width="95%">
			<legend>����Ա��ѯ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right"><%=TRADERID%>��&nbsp;</td>
					<td align="left">
						<input id="traderId" name="_traderId[like]" value="<c:out value='${oldParams["traderId[like]"]}'/>" type=text class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">����Ա���ƣ�&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="right">�������ڣ�</td>
					<td align="left">
							<MEBS:calendar eltName="_createTime[>=][date]" eltCSS="date" eltID="beginDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" 
								eltValue="<c:out value='${beginDate}'/>"/>
				&nbsp;��														
						      <MEBS:calendar eltName="_createTime[<=][date]" eltCSS="date" eltID="endDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" 
								eltValue="<c:out value='${endDate}'/>"/>
					</td>
					
					</tr><tr>
					<td align="right">���֤�ţ�&nbsp;</td>
					<td align="left">
						<input id="keyCode" name="_keyCode[like]" value="<c:out value='${oldParams["keyCode[like]"]}'/>" type=text  class="text" style="width: 100px" >
					</td>
					<c:if test="${firmId == null}">
					<td align="right"><%=FIRMID%>��&nbsp;</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					</c:if>
					<input type = "hidden" name="_firmId[=]" value = "<c:out value='${oldParams["firmId[=]"]}'/>"/>
					<td align="right">key��&nbsp;</td>
					<td align="left">
						<select id="enableKey" name="_enableKey[=]" class="normal" style="width: 60px">
							<OPTION value="">ȫ��</OPTION>
							<option value="N">������</option>
        		            <option value="Y">����</option>
						</select>
				
					<script>
						frm_query.enableKey.value = "<c:out value='${oldParams["enableKey[=]"]}'/>"
					</script>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/firmController.mem?funcflg=setStatusTrader" method="post" targetType="hidden" callback="doQuery();">
	 <%@ include file="listTraderTable.jsp" %>
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
	    	<td>
	    	  <select name="status">
	              <option value="">��ѡ��</option>
	              <option value="N">����</option>
	              <option value="D">��ֹ</option>
              </select>&nbsp;&nbsp;
              <button class="mdlbtn" type="button" onclick="setStatus(frm_delete,tableList,'delCheck')">����״̬</button>&nbsp;&nbsp;
	    	</td>
            <td><div align="right">
              <button class="mdlbtn" type="button" onclick="createNew()">���</button>&nbsp;&nbsp;
  			  <button class="mdlbtn" type="button" onclick="deleteTrader(frm_delete,tableList,'delCheck')">ɾ��</button>&nbsp;&nbsp;
  			  <button class="mdlbtn" type="button" onclick="outputExcel()">����</button>&nbsp;&nbsp;
  			  <c:if test="${firmId != null}">
			  	<button class="mdlbtn" type="button" onclick="cancel_onclick()">����</button> 
			  </c:if>
            </div></td>
        </tr>
    </table>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!-- Hide
function killErrors() {
return true;
}
window.onerror = killErrors;
// -->
</SCRIPT>

<%@ include file="../public/footInc.jsp" %>
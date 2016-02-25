<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "firmId";
}
if (orderType == null || orderType.equals("")) {
	orderType = "false";
}
String order = "";
if (orderType.equals("false")) {
	order = "asc";
}
if (orderType.equals("true")){
	order = "desc";
}
String a = orderField+" "+order;
request.setAttribute("a", a);
%>
<!--��������-->
<c:set var="sqlFilter" value=""/>
<!--��ѯ����-->
<c:choose> 
 <c:when test="${empty param.pageIndex}"> 
   <c:set var="pageIndex" value="1"/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="pageIndex" value="${param.pageIndex}"/> 
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.userCode}"> 
   <c:set var="userCode" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="userCode" value="${param.userCode}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u2.usercode like '${userCode}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.name}"> 
   <c:set var="name" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="name" value="${param.name}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u2.usercode=u3.firmid and  u3.name like '${name}' "/>
 </c:otherwise> 
</c:choose>
<!-- �����ѯ����-->
<c:choose>
  <c:when test="${empty param.pageSize}">
  	 <c:set var="pageSize" value="${PAGESIZE}"/>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  </c:otherwise>
</c:choose>

 
<html>
<head>
<title>�����û����</title>
</head>
<body onload="init();">
<form name=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
		<fieldset width="100%">
		<legend>�����û���Ϣ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right">�����̴��룺</td>
                <td align="left">
                	<input name="userCode" type="text" class="text" style="width: 100px;" value="${param.userCode}"onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                <td align="right">��ҵȫ�ƣ�</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" value="${param.name}"onkeypress="onlyNumberAndCharInput()" maxlength="32">
                </td>
                <td align="left" colspan="1">
                	<span align="right">
                	  <input type="button" onclick="queryBtn('traderUserList.jsp');" class="btn" value="��ѯ">&nbsp;&nbsp;
                      <!-- add by yangpei 2011-11-22 �������ù��� -->
		            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
		            	<script>
		            		function resetForm(){
		            			frm.userCode.value="";
		            			frm.name.value="";
		            		}
		            	</script>
                    </span>
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  				<td class="panel_tHead_LB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left>&nbsp;</td>
			        <td class="panel_tHead_MB" abbr="firmId">�����̴���</td>
			        <td class="panel_tHead_MB" abbr="name">��ҵȫ��</td>
			        <td class="panel_tHead_MB" abbr="feecut">��֤���ۿ�</td>
			        <td class="panel_tHead_MB" abbr="feecutfee">�������ۿ�</td>
				    <td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>

                <c:set var="editRight" value="true"/>
			    <db:selectPG_TraderUser var="row" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where=" u3.firmid=u2.usercode and u3.firmId=u1.firmId${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine" align=left>&nbsp;</td>
		  			<td class="underLine">
		  			<c:choose>
		  			  <c:when test="${editRight=='true'}">
		  			<a href="javascript:editUserInfo('${row.firmId}');" class="normal">${row.firmId}</a>
		  			</c:when>
		  			  <c:otherwise>
		  			    ${row.firmid}
		  			  </c:otherwise>
		  			  </c:choose>
		  			</td>
		  			<td class="underLine" align="center">${row.name}</td>
		  			<td class="underLine">
		  			<c:choose>
		  			<c:when test="${not empty row.feecut}">
		  			  ${row.feecut}
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>	
		  			</td>
					<td class="underLine">
		  			<c:choose>
		  			<c:when test="${not empty row.feecutfee}">
		  			  ${row.feecutfee}
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>	
		  			</td>
 	
		  		   <td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG_TraderUser>
		  	</tBody>
			<!--������ҳ��-->
	  
			<db:cnt_TraderUser var="c" where="1=1 and u3.firmid=u2.usercode and u3.firmId = u1.firmId${sqlFilter}">
				<c:set var="totalCnt" value="${c.n}"/>
			</db:cnt_TraderUser>
		 
			 
			<c:choose> 
			 <c:when test="${totalCnt%pageSize==0}"> 
			   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
			 </c:when> 
			 <c:otherwise>
			   <c:choose> 
				 <c:when test="${(totalCnt%pageSize)*10>=5*pageSize}"> 
				   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
				 </c:when> 
				 <c:otherwise>
				   <c:set var="pageCnt" value="${totalCnt/pageSize+1}"/>
				 </c:otherwise> 
				</c:choose>
			 </c:otherwise> 
			</c:choose>
			<jsp:include page="../../public/pageTurn1.jsp">
				<jsp:param name="colspan" value="5"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>
</form>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
//�޸Ļ�Ա�û���Ϣ
function editUserInfo(userCode){
  var result = window.showModalDialog("traderMod.jsp?flag=query&userCode="+userCode,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
  if(result){
  	queryBtn('traderUserList.jsp');
  	}
}
//-->
</SCRIPT>
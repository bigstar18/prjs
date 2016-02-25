<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.Date'%>
<%
   
 %>
<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %>
	<title>设置结算日期</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
	function setVoucherBDate(){
		   var minDate=formNew.minDate.value;
		   var b_date=formNew.b_date.value;
		   if(b_date=="-1")
		   {
		       alert("请设置归属日期");
		       return false;
		   }
		   if(confirm("确认凭证日期为"+minDate+"的凭证结算日期设置为"+b_date+"！"))
		   {
					disableBtn();
					formNew.submit();
		   }
		}
	function disableBtn(){
			formNew.submitBtn.disabled = true;
			formNew.submitBtn1.disabled = true;
		}
		
		function setBalance(){
            if(confirm("确认进行下一步？"))
				{
					disableBtn();
					formNew.noCheck.value="Y";
					formNew.action="<%=basePath%>/voucherController.spr?funcflg=voucherCheckDate";
					formNew.submit();
				}
		}
		<c:if test="${empty listValue&&empty listNoValue}">
		alert("无需设置凭证，请进行下一步");
		</c:if>
	</script> 
</head>
<body>
<form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=setVoucherBDate" method="POST">
          <fieldset>
		  <legend>财务结算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
                <td align="center"><br><br>
                	<b><font size="3">第二步：设置凭证归属日期</font></b><br><br>
                </td>
              </tr>
			  <tr>
			    <td>
			       <table border="0" cellspacing="0" cellpadding="0" width="70%" height="200" align="center" valign="middle">
			       <tr>
			  <td>
				<fieldset>
			    <legend>设置归属日期</legend>
					    <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100" align="center">
					    <tr height="25">
						    <td width="15%">&nbsp;</td>
						    <td align="center" width="30%"><b>凭证发生日期</b></td>
			                <td align="center" width="40%"><b>凭证归属日期</b></td>
			                <td width="15%">&nbsp;</td>
			              </tr>
					    <tr height="20">
					        <td width="15%">&nbsp;</td>
			                <td align="center" width="30%">
			                <b><font color="blue">${minDate}</font></b>
			                <input type="hidden" name="minDate" value="${minDate}">
			                </td>
			                 <td align="center" width="40%">
			                    <select name="b_date" <c:if test="${empty minDate}">disabled</c:if>>
			                        <option value="-1">请选择</option>
			                        <c:if test="${not empty maxDate}">
			                        <option value="${maxDate}">${maxDate}</option>
			                        </c:if>
				                	<c:forEach items="${resultList}" var="result">
				                	<c:if test="${(not empty maxDate&&result!=maxDate)||empty maxDate}">
				                	<option value="${result}">${result}</option>
				                	</c:if>
				                	</c:forEach>
			                    </select>
			                </td>
			                <td width="15%">&nbsp;</td>
			                </tr>
			                
			                <c:forEach items="${listNoValue}" var="result">
				              <c:if test="${result.createTime!=minDate}">
				              <tr height="30">
				              <td width="15%">&nbsp;</td>
				              <td align="center" width="30%">
			                   ${result.createTime}
			                </td>
			                 <td align="center" width="40%">
			                   &nbsp;
			                </td>
			                <td width="15%">&nbsp;</td>
			                </tr>
				              </c:if>
				             </c:forEach>
			                 <tr height="30">
			                 <td width="15%">&nbsp;</td>
			                 <td align="center" colspan="2">
			                 <button id="submitBtn" class="lgrbtn" type="button" onclick="setVoucherBDate();" <c:if test="${empty minDate}">disabled</c:if>>确认设置</button>&nbsp;
			                 </td>
			                 <input type="hidden" name="noCheck">
			                 <td width="15%">&nbsp;</td>
			                 </tr>
			              
					    </table>
		           </fieldset>
		           </td>
		           </tr>
			       <tr>
			       <td>
					<fieldset>
					<legend>已设置归属日期</legend>
					    <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100" align="center">
					    <tr height="35">
						    <td width="15%">&nbsp;</td>
						    <td align="center" width="30%"><b>凭证发生日期</b></td>
			                <td align="center" width="40%"><b>凭证归属日期</b></td>
			                <td width="15%">&nbsp;</td>
			              </tr>
			              <c:forEach items="${listValue}" var="result">
			              <tr height="35">
			                <td width="15%">&nbsp;</td>
			                <td align="center" width="30%"><c:out value="${result.createTime}"/></td>
			                <td align="center" width="40%"><c:out value="${result.b_date}"/>
			                 
			                </td>
			                <td width="15%">&nbsp;</td>
			              </tr>
			              </c:forEach>
			              <tr>
			              <td colspan="4" align="center">
			              <button id="submitBtn1" class="lgrbtn" type="button" onclick="setBalance();" <c:if test="${empty listValue&&not empty listNoValue}">disabled</c:if>>下一步</button>&nbsp;
			              </td>
			              </tr>
			              <tr>
			              <td colspan="4"></td>
			              </tr>
					    </table>
			     </fieldset>
			   </td>
			  </tr>
		       </table>
			</td>
			</tr>
			 </table>
		     </fieldset>
			
</form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>
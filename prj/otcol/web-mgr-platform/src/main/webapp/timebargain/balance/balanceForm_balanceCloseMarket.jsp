<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
	String prompt = (String)request.getAttribute("prompt");
	if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
	%>
	    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
	<%
	}
	else
	{
	%>
		<c:choose>
		  <c:when test="${chkRet == 1}">
		      parent.ContFrame.document.getElementById("sp4").style.display='inline';
		      parent.ContFrame.document.getElementById("bl4").className="";
             // parent.ContFrame.document.getElementById("bl1").className="";
             // parent.ContFrame.document.getElementById("bl2").className="";
              parent.ContFrame.document.getElementById("bl3").className="";
              //window.location.href = "<c:url value="/balance/balance.do?method=balanceCloseMarket"/>";
              alert("������ɣ�");
              //alert(1111111);
		  </c:when>
		  <c:when test="${chkRet == -2}">
		  	alert("���մ������");
		  </c:when>
		  <c:otherwise>
		    alert("��������");
		  </c:otherwise>
		</c:choose>  
	<%
	}
	%>
	//parent.ContFrame.ordersForm.balanceChk.disabled = false;
</script>



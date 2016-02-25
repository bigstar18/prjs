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
		  
		   <c:when test="${chkRet == 0}">
		    
		      //parent.ContFrame.document.getElementById("sp3").style.display='inline';
		      //parent.ContFrame.document.getElementById("bl4").className="req";
             // parent.ContFrame.document.getElementById("bl1").className="";
             // parent.ContFrame.document.getElementById("bl2").className="";
             // parent.ContFrame.document.getElementById("bl3").className="";
              window.location.href = "<c:url value="/timebargain/balance/balance.do?funcflg=balanceCloseMarketEXC"/>";
		  </c:when>
		  <c:when test="${chkRet == 2}">
		    if(confirm("存在冻结数量,是否继续？"))
		    {
		      //parent.ContFrame.document.getElementById("sp3").style.display='inline';
		     // parent.ContFrame.document.getElementById("bl4").className="req";
             // parent.ContFrame.document.getElementById("bl1").className="";
             // parent.ContFrame.document.getElementById("bl2").className="";
             // parent.ContFrame.document.getElementById("bl3").className="";
              window.location.href = "<c:url value="/timebargain/balance/balance.do?funcflg=balanceCloseMarketEXC"/>";
		    }  
		  </c:when>
		  <c:otherwise>
		  </c:otherwise>
		</c:choose>  
	<%
	}
	%>
	//parent.ContFrame.ordersForm.balanceChk.disabled = false;
</script>



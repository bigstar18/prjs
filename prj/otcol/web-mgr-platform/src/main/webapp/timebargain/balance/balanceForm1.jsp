<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<title></title>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script type="text/javascript">
//window_onload
function window_onload()
{
    highlightFormElements();
    //ordersForm.balance.disabled = true;
    <%
   if(request.getAttribute("r")!=null)
   {
   %>
   //document.getElementById("bl1").className="req";
   //document.getElementById("bl2").className="req";
   //document.getElementById("sp2").style.display='inline';
   //document.getElementById("sp1").style.display='inline';
   <%
   }
   %>
}
//���н�����
function balanceChk_onclick()
{
   <%
   if(request.getAttribute("r")==null)
   {
   %>
 
    if (confirm("��ȷ��Ҫ������"))
    {
    ordersForm.balanceChk.disabled = true;
    document.getElementById("bl3").className="req";
    parent.HiddFrame.location.href = "<c:url value="/timebargain/balance/balance.do?funcflg=balanceChkFroenFund"/>";
    }
   // }
    <%
    }
    else
    {
    %>
    if (confirm("��ȷ��Ҫ������"))
    {
    ordersForm.balanceChk.disabled = true;
    document.getElementById("bl3").className="req";
    parent.HiddFrame.location.href = "<c:url value="/timebargain/balance/balance.do?funcflg=balanceChkFroenFund"/>";
    }
    <%
    }
    %>
}
//�쳣����


//�����г�


//���н���

</script>
</head>
<%
  String result=(String)request.getAttribute("result");
  String result2=(String)request.getAttribute("result2");
 %>
<body  leftmargin="0" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
<table border="0" height="190" align="center">
<tr><td>
<html:form method="post" action="/timebargain/balance/balance.do?funcflg=balanceChkMarketHold" target="HiddFrame">
<fieldset class="pickList">
	<legend class="common">
	<b>���н���</b>
	</legend>
      <table border="0" align="center" cellpadding="5" cellspacing="5" class="commonTest" height="180">
      <tr height="20">
        <td>
        �г�״̬��<span class="req"><%=result2%></span>
        </td>
        </tr>	
        
        <tr height="20">
        <td>
        <span id="bl3" name="bl3" class="">��һ��������������� </span>&nbsp;&nbsp;&nbsp;<span id="sp3" name="sp3" style="display:none"><img src="<c:url value="/timebargain/images/right.gif"/>" align="absmiddle"></span>
        </td>
        </tr>
        <tr height="20">
        <td>
        <span id="bl4" name="bl4" class="">�ڶ��������н���</span>&nbsp;&nbsp;&nbsp;<span id="sp4" name="sp4" style="display:none"><img src="<c:url value="/timebargain/images/right.gif"/>" align="absmiddle"></span>
        </td>
        </tr>
        <tr><td align="center" width="300" height="100">
          <input type="hidden" name="sign" value="0">
		  <html:button  property="balanceChk" styleClass="button" onclick="javascript:balanceChk_onclick();">����</html:button>&nbsp;&nbsp;
		  
        </td></tr>
        <tr>
        	<td>
        		<font color="red">ע�����׽�����������ʽ����</font>
        	</td>
        </tr>
      </table>
</fieldset>    
</html:form>
</td></tr>
</table>

<%@ include file="/timebargain/common/messages.jsp" %> 
</body>
</html>
<script type="text/javascript">
<%
 if("1".equals(result))
  {
  %>
  
   ordersForm.balanceChk.disabled = false;
  
  <%
  }else {
  %>
  	ordersForm.balanceChk.disabled = true;
  <%
  }
%>
</script>

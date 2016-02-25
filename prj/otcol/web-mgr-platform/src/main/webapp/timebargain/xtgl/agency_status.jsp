<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<%
  String result=(String)request.getAttribute("result");
  String market=(String)request.getAttribute("market");
 %>
<title></title>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script type="text/javascript">
function window_onload()
{
    paraminfo_onclick();
}
function balanceChk_onclick(id)
{
  var name;
  if(id==2)
  {
    name="<fmt:message key="agencyForm.marketStatus2"/>";
  }
  if(id==3)
  {
   name="<fmt:message key="agencyForm.marketStatus3"/>";
  }
  if(id==4)
  {
  name="<fmt:message key="agencyForm.marketStatus4"/>";
  }
  if(id==5)
  {
  name="<fmt:message key="agencyForm.marketStatus5"/>";
  }
  if (confirm("您确定要" + name + "吗？"))
    {
  agencyForm.marketStatus.value=id;
  agencyForm.submit();
  agencyForm.ok1.disabled = true;
  agencyForm.ok2.disabled = true;
  agencyForm.ok3.disabled = true;
  agencyForm.ok4.disabled = true;
     }
}
//---------------------------start paraminfo-------------------------------
var tmp_paraminfo;
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    agencyForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    agencyForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}
//-----------------------------end paraminfo-----------------------------
</script>
</head>
<body  leftmargin="0" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
<table border="0" height="300" align="center">
<tr><td>
<html:form method="post" action="/xtgl/agency.do?method=operate" target="HiddFrame">
<fieldset class="pickList">
	<legend class="common">
	<b><fmt:message key="marketForm.status"/></b>
	</legend>
      <table border="0" align="center" cellpadding="5" cellspacing="5" class="commonTest" height="250" width="200">
      <tr height="20">
        <td>
        <fmt:message key="marketForm.status"/>:<span class="req"><%=result%></span>
        </td>
        </tr>	
        <tr class="common">
          <td colspan="10" valign="top">
            <fieldset class="pickList">
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b>异常处理</b></td>
                    <td><hr width="99%" class="pickList"/></td>
                    <td ><img id="paraminfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:paraminfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
                <span id="paraminfo">
                    <table cellSpacing="0" cellPadding="0" width="100%" border="0" height="180" align="center" class="common">        												
								<tr height="20">
						        <td>
						        <html:button  property="ok1" styleClass="button" onclick="javascript:balanceChk_onclick('2');">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="agencyForm.marketStatus2"/>&nbsp;&nbsp;&nbsp;&nbsp;</html:button>
						        </td>
						        </tr>
						        <tr height="20">
						        <td>
						        <html:button  property="ok2" styleClass="button" onclick="javascript:balanceChk_onclick('3');">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="agencyForm.marketStatus3"/>&nbsp;&nbsp;&nbsp;&nbsp;</html:button>
						        </td>
						        </tr>
						        <tr height="20">
						        <td>
						        <html:button  property="ok3" styleClass="button" onclick="javascript:balanceChk_onclick('4');">&nbsp;&nbsp;<fmt:message key="agencyForm.marketStatus4"/>&nbsp;&nbsp;</html:button>
						        </td>
						        </tr>
						        <tr height="20">
						        <td>
						        <html:button  property="ok4" styleClass="button" onclick="javascript:balanceChk_onclick('5');"><fmt:message key="agencyForm.marketStatus5"/></html:button>
						        </td>
						        </tr>
																
					</table >
					</span>
            </fieldset>
          </td>
        </tr>        						
        
        
        
        <html:hidden property="marketStatus"/>
        <html:hidden property="type" value="09"/>
        <html:hidden property="marketID" value="<%=market%>"/>
      </table>
</fieldset>    
</html:form>
</td></tr>
</table>

<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>

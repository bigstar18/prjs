<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="javascript">
function window_onload()
{
  changeColor("customer");
}

function customer_onclick()
{
  changeColor("customer");
  parent.ContFrame.location.href = "<c:url value="/timebargain/baseinfo/customer.jsp"/>";
}

function typePrivilege_onclick(){
	changeColor("type");
	parent.ContFrame.location.href = "<c:url value="/timebargain/baseinfo/typePrivilege.jsp"/>";
}

function changeColor(name)
{
  
  if(name == "customer")
  {
  	customer.style.color = "red";
  	type.style.color = "#26548B";
  }
  else if(name == "type")
  {
  	type.style.color = "red";
  	customer.style.color = "#26548B";
  }
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    <td width="76" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="customer" class="common" onclick="customer_onclick()">交易商信息</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
        <td width="70" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="type" class="common" onclick="typePrivilege_onclick()">权限查询</a></td>   
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
</body>
</html>

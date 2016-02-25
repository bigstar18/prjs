<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<%
	System.out.println("=======================");
 %>
<script language="javascript">
function window_onload()
{
  changeColor("margin");
  query();
}
function margin_onclick()
{
  changeColor("margin");
  query();
}

function qty_onclick()
{
  changeColor("qty");    
  query();
}

function fee_onclick()
{
  changeColor("fee");    
  query();
}

function changeColor(name)
{
  oper.value = name;
  if(name == "margin")
  {
  	margin.style.color = "red";
  	qty.style.color = "#26548B";
  	fee.style.color = "#26548B";
  }
  else if(name == "qty")
  {
  	margin.style.color = "#26548B";
  	fee.style.color = "#26548B";
  	qty.style.color = "red";
  } 
  else if(name == "fee")
  {
  	margin.style.color = "#26548B";
  	qty.style.color = "#26548B";
  	fee.style.color = "red";
  } 
}

function query()
{
  if(oper.value == "margin")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/menu/firmBreedMargin.do"/>";
  }
  else if(oper.value == "qty")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/menu/firmBreedMaxHoldQty.do"/>";
  } 
  else if(oper.value == "fee")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/menu/firmBreedFee.do"/>";
  } 
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    <td width="120" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="margin" class="common" onclick="margin_onclick()">特殊保证金</a></td>
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
    <td width="120" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="qty" class="common" onclick="qty_onclick()">特殊订货量</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="120" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="fee" class="common" onclick="fee_onclick()">特殊手续费</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="oper" value="" >
</body>
</html>

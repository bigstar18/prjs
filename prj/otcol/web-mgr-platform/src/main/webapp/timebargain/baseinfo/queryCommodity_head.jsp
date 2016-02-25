<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="javascript">
function window_onload()
{
  changeColor("cur");
  
 
  query();
}
function cur_onclick()
{
  changeColor("cur");
  query();
}

function his_onclick()
{
  changeColor("his");    
  query();
}

function changeColor(name)
{
  oper.value = name;
  if(name == "cur")
  {
  	cur.style.color = "red";
  	his.style.color = "#26548B";
  }
  else if(name == "his")
  {
  	cur.style.color = "#26548B";
  	his.style.color = "red";
  } 
}

function query()
{
  if(oper.value == "cur")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/queryCurOrHisCommodity.jsp?oper=cur"/>";
  }
  else if(oper.value == "his")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/queryCurOrHisCommodity.jsp?oper=his"/>";
  } 
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    <td width="47" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="cur" class="common" onclick="cur_onclick()">当前</a></td>
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
    <td width="46" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="his" class="common" onclick="his_onclick()">历史</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="oper" value="" >
</body>
</html>

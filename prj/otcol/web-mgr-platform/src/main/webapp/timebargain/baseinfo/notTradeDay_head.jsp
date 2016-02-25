<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="javascript">
function window_onload()
{
  changeColor("trade");
  
  query();
}
function trade_onclick()
{
  changeColor("trade");
  query();
}

function cal_onclick()
{
  changeColor("cal");    
  query();
}

function changeColor(name)
{
  oper.value = name;
  if(name == "trade")
  {
  	trade.style.color = "red";
  	cal.style.color = "#26548B";
  }
  else if(name == "cal")
  {
  	trade.style.color = "#26548B";
  	cal.style.color = "red";
  } 
}

function query()
{
  if(oper.value == "trade")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=editNotTradeDay"/>";
  }
  else if(oper.value == "cal")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/calendar.jsp"/>";
  } 
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    <td width="55" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="trade" class="common" onclick="trade_onclick()">非交易日</a></td>
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
    <td width="46" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="cal" class="common" onclick="cal_onclick()">日历</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="oper" value="" >
</body>
</html>

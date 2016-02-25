<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="javascript">
function window_onload()
{
  changeColor("hisQ");
  
  //breedID.value = "<c:out value="${param['breedID']}"/>";
  //condition.value = "<c:out value="${param['condition']}"/>";
  query();
}


function hisQ_onclick()
{
  changeColor("hisQ");    
  query();
}

function hisZ_onclick()
{
  changeColor("hisZ");    
  query();
}

function hisY_onclick()
{
  changeColor("hisY");    
  query();
}

function hisS_onclick()
{
  changeColor("hisS");    
  query();
}

function changeColor(name)
{
  oper.value = name;
  if(name == "hisQ")
  {
  	hisQ.style.color = "red";
  	hisZ.style.color = "#26548B";
  	hisY.style.color = "#26548B";
  	hisS.style.color = "#26548B";
  	
  }
  else if(name == "hisZ")
  {
  	hisZ.style.color = "red";
  	hisQ.style.color = "#26548B";
  	hisY.style.color = "#26548B";
  	hisS.style.color = "#26548B";
  } 
  else if(name == "hisY")
  {
  	hisQ.style.color = "#26548B";
  	hisY.style.color = "red";
  	hisZ.style.color = "#26548B";
  	hisS.style.color = "#26548B";
  } 
  else if(name == "hisS")
  {
  	hisQ.style.color = "#26548B";
  	hisS.style.color = "red";
  	hisZ.style.color = "#26548B";
  	hisY.style.color = "#26548B";
  } 
}

function query()
{
  if(oper.value == "hisQ")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/customerDefProps.jsp"/>";
  }
  else if(oper.value == "hisZ")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/groupTradeProps.jsp"/>";
  }
  else if(oper.value == "hisY")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/customerTradeProps.jsp"/>";
  } 
  else if(oper.value == "hisS")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/baseinfo/queryTradeProps.jsp"/>";
  }  
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="793" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    <td width="60" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="hisQ" class="common" onclick="hisQ_onclick()">缺省设置</a></td>
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
    <td width="60" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="hisZ" class="common" onclick="hisZ_onclick()">组设置</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
     <td width="60" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="hisY" class="common" onclick="hisY_onclick()">用户设置</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
     <td width="60" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="hisS" class="common" onclick="hisS_onclick()">生效查询</a></td>
    <td width="13" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="condition" value="" ><input type="hidden" name="breedID" value="" >
<input type="hidden" name="oper" value="" >
</body>
</html>

  

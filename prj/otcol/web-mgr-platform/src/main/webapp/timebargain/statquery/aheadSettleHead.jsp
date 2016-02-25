<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="javascript">
function window_onload()
{
  changeColor("q");
  
  //breedID.value = "<c:out value="${param['breedID']}"/>";
  //condition.value = "<c:out value="${param['condition']}"/>";
  query();
}


function add_onclick()
{
  changeColor("add");    
  query();
}

function query_onclick()
{
  changeColor("q");    
  query();
}



function changeColor(name)
{
  oper.value = name;
  if(name == "add")
  {
  	add.style.color = "red";
  	q.style.color = "#26548B";
  	
  }
  else if(name == "q")
  {
  	q.style.color = "red";
  	add.style.color = "#26548B";
  } 
}

function query()
{
  if(oper.value == "add")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=aheadSettle"/>";
  }
  else if(oper.value == "q")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=queryAheadSettle"/>";
  }
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="793" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td> 
    <td width="60" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="q" class="common" onclick="query_onclick()">≤È—Ø</a></td>  
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
	<td width="60" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="add" class="common" onclick="add_onclick()">ÃÌº”</a></td>
    <td width="30" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="condition" value="" ><input type="hidden" name="breedID" value="" >
<input type="hidden" name="oper" value="" >
</body>
</html>

  

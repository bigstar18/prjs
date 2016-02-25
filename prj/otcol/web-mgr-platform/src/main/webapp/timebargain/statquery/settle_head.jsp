<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<%@ page import="java.util.*" %>
<%
DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
List list=dao.queryBySQL("select * from t_a_market");
Map innerMap =(Map)list.get(0);
String settlemode = innerMap.get("settlemode").toString();
%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>"/>
<script language="javascript">
function window_onload()
{
	var settlemode = <%=settlemode%>
	if(settlemode==0){
	document.getElementById("handtd1").style.display="none";
	document.getElementById("handtd2").style.display="none";
	 changeColor("settle");
	} else{
	 changeColor("hand");
	}

  //breedID.value = "<c:out value="${param['breedID']}"/>";
  //condition.value = "<c:out value="${param['condition']}"/>";
  query();
}


function settle_onclick()
{
  changeColor("settle");    
  query();
}

function hand_onclick()
{
  changeColor("hand");    
  query();
}


function settleTogether_onclick(){
	changeColor("settleTogether");   
	query();
}

function pair_onclick(){
	changeColor("pair");   
	query();
}


function changeColor(name)
{
  oper.value = name;
  if(name == "settle")
  {
  	settle.style.color = "red";
  	hand.style.color = "#26548B";
  	settleTogether.style.color = "#26548B";
  	pair.style.color = "#26548B";
  }
  else if(name == "hand")
  {
  	hand.style.color = "red";
  	settle.style.color = "#26548B";
  	settleTogether.style.color = "#26548B";
  	pair.style.color = "#26548B";
  } 

  else if(name == "settleTogether")
  {
  	settleTogether.style.color = "red";
  	settle.style.color = "#26548B";
  	hand.style.color = "#26548B";
  	pair.style.color = "#26548B";
  }
  else if (name == "pair") {
 	pair.style.color = "red";
  	settle.style.color = "#26548B";
 	hand.style.color = "#26548B";
  	settleTogether.style.color = "#26548B";
  }
}

function query()
{
  if(oper.value == "settle")
  {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/statquery/settleNumber.jsp"/>";
  	//parent.ListFrame.location.href = "<c:url value="/statquery/statQuery.do?method=topSettle"/>";
  }
  else if(oper.value == "hand")
  {
  	//parent.List2Frame.location.href = "<c:url value="/statquery/blank.jsp"/>";
  	parent.ListFrame.location.href = "<c:url value="/timebargain/statquery/settleListHandHead.jsp"/>";
  	//parent.ListFrame.location.href = "<c:url value="/statquery/statQuery.do?method=listHand"/>";
  }
   else if(oper.value == "settleTogether")
  {
  	//parent.ListFrame.location.href = "<c:url value="/statquery/statQuery.do?method=listSum"/>";
  	//parent.List2Frame.location.href = "<c:url value="/statquery/blank.jsp"/>";
  	parent.ListFrame.location.href = "<c:url value="/timebargain/statquery/settleListTogetherHead.jsp"/>";
  	//parent.ListFrame.location.href = "<c:url value="/statquery/statQuery.do?method=listTogether"/>";
  }
  else if (oper.value == "pair") {
  	parent.ListFrame.location.href = "<c:url value="/timebargain/statquery/settlePair.jsp"/>";
  }
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="/timebargain/images/bgimage.gif"/>"></td>
    <td id="handtd1" width="17" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>   
    <td id="handtd2" width="160" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="hand" class="common" onclick="hand_onclick()">手工生成交收数据</a></td>
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
	<td width="120" background="<c:url value="/timebargain/images/bgimage.gif"/>"><a href="#" id="settle" class="common" onclick="settle_onclick()">交收数据明细</a></td>
    <td width="30" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
	<td width="80" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="settleTogether" class="common" onclick="settleTogether_onclick()">交收合计</a></td>
    <td width="30" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></td>
    <td width="120" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" ><a href="#" id="pair" class="common" onclick="pair_onclick()">交收配对数据</a></td>
    <td width="29" background="<c:url value="/timebargain/images/bgimage.gif"/>"><div align="left"><img src="<c:url value="/timebargain/images/line.gif"/>" width="3" height="31"></div></td>
    <td width="333" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="167" background="<c:url value="/timebargain/images/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="condition" value="" ><input type="hidden" name="breedID" value="" >
<input type="hidden" name="oper" value="" >
</body>
</html>

  

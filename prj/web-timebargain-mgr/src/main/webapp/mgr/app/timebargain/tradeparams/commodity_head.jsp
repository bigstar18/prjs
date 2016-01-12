<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="gnnt.MEBS.common.mgr.model.User"%>
<%@page import="gnnt.MEBS.common.mgr.common.Global"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//记录的样式名称
	String skinName = null;
	User sessinUser = (User) request.getSession().getAttribute(
			Global.CURRENTUSERSTR);
	if (sessinUser == null) {//如果用户为空，则样式名称为默认样式名称
		skinName = "default";
	} else {//如果用户不为空，则样式名称为用户样式名称
		skinName = sessinUser.getSkin();
	}
	//市场名称：common_demo
	String path = request.getContextPath();
	//web url：http://127.0.0.1:8080/common_demo
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	//mgr 路径：http://127.0.0.1:8080/common_demo/mgr
	String mgrPath=basePath+"/mgr";
	//public 路径：http://127.0.0.1:8080/common_demo/mgr/public
	String publicPath = mgrPath + "/public";
	//公共加载页面路径：http://127.0.0.1:8080/common_demo/mgr/public/includefiles
	String includePath = publicPath + "/includefiles";
	//当前样式路径：http://127.0.0.1:8080/common_demo/mgr/skinstyle/default
	String skinPath = mgrPath + "/skinstyle/" + skinName;
%>
<c:set var="skinPath" value="<%=skinPath %>" />
<c:set var="basePath" value="<%=basePath %>" />
<html>
<head>
<script language="javascript">
function window_onload()
{
  changeColor(oper.value);
  
  breedID.value = "<c:out value="${param['breedID']}"/>";
  //condition.value = "<c:out value="${param['condition']}"/>";
  //query();
}
function cur_onclick()
{
  changeColor("cur");
  query("cur");
}

function his_onclick()
{
  changeColor("his");    
  query("his");
}

function changeColor(name)
{
  //oper.value = name;
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

function query(name)
{
  if(name == "cur")
  {
  	document.location.href = "${basePath}/timebargain/tradeparams/detailToCommodity.action?breedID=${breedID}&sortColumns=order+by+commodityID+asc";
  }
  else if(name == "his")
  {
  	document.location.href = "${basePath}/timebargain/tradeparams/detailToCommodityHis.action?breedID=${breedID}&sortColumns=order+by+settleProcessDate+desc";
  } 
}
</script>
</head>
<body leftmargin="0" topmargin="0" onLoad="return window_onload()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11" height="31" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"></td>
    <td width="17" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>   
    <td width="47" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><a href="#" id="cur" class="common" onclick="cur_onclick()">当前</a></td>
    <td width="29" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>
    <td width="46" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><a href="#" id="his" class="common" onclick="his_onclick()">交收</a></td>
    <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="condition" value="" ><input type="hidden" name="breedID" value="" >
<input type="hidden" name="oper" value="${opr }" >
</body>
</html>

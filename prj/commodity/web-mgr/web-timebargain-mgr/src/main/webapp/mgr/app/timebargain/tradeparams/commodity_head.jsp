<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="gnnt.MEBS.common.mgr.model.User"%>
<%@page import="gnnt.MEBS.common.mgr.common.Global"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//��¼����ʽ����
	String skinName = null;
	User sessinUser = (User) request.getSession().getAttribute(
			Global.CURRENTUSERSTR);
	if (sessinUser == null) {//����û�Ϊ�գ�����ʽ����ΪĬ����ʽ����
		skinName = "default";
	} else {//����û���Ϊ�գ�����ʽ����Ϊ�û���ʽ����
		skinName = sessinUser.getSkin();
	}
	//�г����ƣ�common_demo
	String path = request.getContextPath();
	//web url��http://127.0.0.1:8080/common_demo
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	//mgr ·����http://127.0.0.1:8080/common_demo/mgr
	String mgrPath=basePath+"/mgr";
	//public ·����http://127.0.0.1:8080/common_demo/mgr/public
	String publicPath = mgrPath + "/public";
	//��������ҳ��·����http://127.0.0.1:8080/common_demo/mgr/public/includefiles
	String includePath = publicPath + "/includefiles";
	//��ǰ��ʽ·����http://127.0.0.1:8080/common_demo/mgr/skinstyle/default
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
    <td width="47" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><a href="#" id="cur" class="common" onclick="cur_onclick()">��ǰ</a></td>
    <td width="29" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>"><div align="left"><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></div></td>
    <td width="46" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><a href="#" id="his" class="common" onclick="his_onclick()">����</a></td>
    <td width="13" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" ><img src="<c:url value="${skinPath }/image/app/timebargain/line.gif"/>" width="3" height="31"></td>
    <td width="333" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp;</td>
    <td width="267" background="<c:url value="${skinPath }/image/app/timebargain/bgimage.gif"/>" class="common" >&nbsp; </td>
  </tr>
</table>
<input type="hidden" name="condition" value="" ><input type="hidden" name="breedID" value="" >
<input type="hidden" name="oper" value="${opr }" >
</body>
</html>
